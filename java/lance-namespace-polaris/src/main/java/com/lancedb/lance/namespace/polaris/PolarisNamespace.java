/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lancedb.lance.namespace.polaris;

import com.lancedb.lance.Dataset;
import com.lancedb.lance.WriteParams;
import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.rest.RestClient;
import com.lancedb.lance.namespace.util.ValidationUtil;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Polaris Catalog namespace implementation for Lance. */
public class PolarisNamespace implements LanceNamespace {
  private static final Logger LOG = LoggerFactory.getLogger(PolarisNamespace.class);
  private static final String TABLE_FORMAT_LANCE = "lance";
  private static final String MANAGED_BY_KEY = "managed_by";
  private static final String TABLE_TYPE_KEY = "table_type";
  private static final String VERSION_KEY = "version";
  private static final String CREATED_AT_KEY = "created_at";
  private static final String UPDATED_AT_KEY = "updated_at";

  private PolarisNamespaceConfig config;
  private RestClient restClient;
  private BufferAllocator allocator;

  public PolarisNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    this.config = new PolarisNamespaceConfig(configProperties);

    // Build REST client with authentication if provided
    RestClient.Builder clientBuilder =
        RestClient.builder()
            .baseUrl(config.getFullApiUrl())
            .connectTimeout(config.getConnectTimeout())
            .readTimeout(config.getReadTimeout())
            .maxRetries(config.getMaxRetries());

    // Add auth token if provided
    if (config.getAuthToken() != null) {
      Map<String, String> headers = new HashMap<>();
      headers.put("Authorization", "Bearer " + config.getAuthToken());
      clientBuilder.defaultHeaders(headers);
    }

    this.restClient = clientBuilder.build();
    LOG.info("Initialized Polaris namespace with endpoint: {}", config.getEndpoint());
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    ObjectIdentifier namespaceId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        namespaceId.levels() >= 1, "Namespace must have at least one level");

    try {
      // Convert request to Polaris format
      List<String> namespace = namespaceId.listStyleId();

      PolarisModels.CreateNamespaceRequest polarisRequest =
          new PolarisModels.CreateNamespaceRequest(namespace, request.getProperties());

      // Create namespace using Iceberg REST API endpoint
      PolarisModels.NamespaceResponse response =
          restClient.post("/namespaces", polarisRequest, PolarisModels.NamespaceResponse.class);

      LOG.info("Created namespace: {}", String.join(".", namespace));

      CreateNamespaceResponse result = new CreateNamespaceResponse();
      result.setProperties(response.getProperties());
      return result;
    } catch (IOException e) {
      throw LanceNamespaceException.serverError(
          "Failed to create namespace", "ServerError", namespaceId.stringStyleId(), e.getMessage());
    }
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    ObjectIdentifier namespaceId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        namespaceId.levels() >= 1, "Namespace must have at least one level");

    try {
      String namespacePath = namespaceId.stringStyleId();

      // Get namespace properties using Iceberg REST API
      PolarisModels.NamespaceResponse response =
          restClient.get("/namespaces/" + namespacePath, PolarisModels.NamespaceResponse.class);

      DescribeNamespaceResponse result = new DescribeNamespaceResponse();
      result.setProperties(response.getProperties());
      return result;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Namespace not found",
            "NoSuchNamespace",
            namespaceId.stringStyleId(),
            "Namespace not found: " + namespaceId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to describe namespace",
          "ServerError",
          namespaceId.stringStyleId(),
          e.getMessage());
    }
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier parentId =
        request.getId() != null
            ? ObjectIdentifier.of(request.getId())
            : ObjectIdentifier.of(Collections.emptyList());

    try {
      String path = "/namespaces";
      if (!parentId.isRoot()) {
        path += "/" + parentId.stringStyleId() + "/namespaces";
      }

      // List namespaces using Iceberg REST API
      PolarisModels.ListNamespacesResponse response =
          restClient.get(path, PolarisModels.ListNamespacesResponse.class);

      ListNamespacesResponse result = new ListNamespacesResponse();
      // Convert namespace identifiers to Set<String> with full paths
      Set<String> namespaceSet = new LinkedHashSet<>();
      if (response.getNamespaces() != null) {
        for (PolarisModels.ListNamespacesResponse.Namespace ns : response.getNamespaces()) {
          namespaceSet.add(String.join(".", ns.getNamespace()));
        }
      }
      result.setNamespaces(namespaceSet);
      return result;
    } catch (IOException e) {
      throw LanceNamespaceException.serverError(
          "Failed to list namespaces", "ServerError", "listNamespaces", e.getMessage());
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    ObjectIdentifier namespaceId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        namespaceId.levels() >= 1, "Namespace must have at least one level");

    try {
      String namespacePath = namespaceId.stringStyleId();

      // Drop namespace using Iceberg REST API
      restClient.delete("/namespaces/" + namespacePath);

      LOG.info("Dropped namespace: {}", namespacePath);

      DropNamespaceResponse result = new DropNamespaceResponse();
      // DropNamespaceResponse has no fields to set
      return result;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Namespace not found",
            "NoSuchNamespace",
            namespaceId.stringStyleId(),
            "Namespace not found: " + namespaceId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to drop namespace", "ServerError", namespaceId.stringStyleId(), e.getMessage());
    }
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    ObjectIdentifier namespaceId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        namespaceId.levels() >= 1, "Namespace must have at least one level");

    try {
      String namespacePath = namespaceId.stringStyleId();
      // Use GET request to check if namespace exists
      restClient.get("/namespaces/" + namespacePath, PolarisModels.NamespaceResponse.class);
      // If we get here, namespace exists - return normally
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Namespace not found",
            "NoSuchNamespace",
            namespaceId.stringStyleId(),
            "Namespace not found: " + namespaceId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to check namespace existence",
          "ServerError",
          namespaceId.stringStyleId(),
          e.getMessage());
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() >= 2, "Table identifier must have at least 2 levels");

    try {
      // Split into namespace and table name
      List<String> parts = tableId.listStyleId();
      String tableName = parts.get(parts.size() - 1);
      List<String> namespaceParts = parts.subList(0, parts.size() - 1);
      String namespacePath = String.join(".", namespaceParts);

      // Use GET request to check if table exists
      restClient.get(
          "/namespaces/" + namespacePath + "/generic-tables/" + tableName,
          PolarisModels.LoadGenericTableResponse.class);
      // If we get here, table exists - return normally
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Table not found",
            "NoSuchTable",
            tableId.stringStyleId(),
            "Table not found: " + tableId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to check table existence",
          "ServerError",
          tableId.stringStyleId(),
          e.getMessage());
    }
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() >= 2, "Table identifier must have at least 2 levels");

    try {
      // Split into namespace and table name
      List<String> parts = tableId.listStyleId();
      String tableName = parts.get(parts.size() - 1);
      List<String> namespaceParts = parts.subList(0, parts.size() - 1);
      String namespacePath = String.join(".", namespaceParts);

      // Prepare table properties
      Map<String, String> properties = new HashMap<>();
      String comment = null;
      if (request.getProperties() != null) {
        properties.putAll(request.getProperties());
        // Extract comment to use as doc field
        comment = properties.remove("comment");
      }

      // Add Lance-specific properties
      properties.put(TABLE_TYPE_KEY, TABLE_FORMAT_LANCE);
      properties.put(MANAGED_BY_KEY, "lance-namespace");
      properties.put(VERSION_KEY, "1");
      properties.put(CREATED_AT_KEY, Instant.now().toString());

      // Create generic table request
      PolarisModels.CreateGenericTableRequest tableRequest =
          new PolarisModels.CreateGenericTableRequest(
              tableName,
              TABLE_FORMAT_LANCE,
              request.getLocation(), // location from request
              comment, // doc field from comment property
              properties);

      // Create table using Generic Table API
      PolarisModels.LoadGenericTableResponse response =
          restClient.post(
              "/namespaces/" + namespacePath + "/generic-tables",
              tableRequest,
              PolarisModels.LoadGenericTableResponse.class);

      LOG.info("Created Lance table: {}.{}", namespacePath, tableName);

      CreateTableResponse result = new CreateTableResponse();
      result.setLocation(response.getTable().getBaseLocation());
      Map<String, String> resultProps = new HashMap<>(response.getTable().getProperties());
      if (response.getTable().getDoc() != null) {
        resultProps.put("comment", response.getTable().getDoc());
      }
      result.setProperties(resultProps);
      return result;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("409")) {
        throw LanceNamespaceException.conflict(
            "Table already exists",
            "TableAlreadyExists",
            tableId.stringStyleId(),
            "Table already exists: " + tableId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to create table", "ServerError", tableId.stringStyleId(), e.getMessage());
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() >= 2, "Table identifier must have at least 2 levels");

    try {
      // Split into namespace and table name
      List<String> parts = tableId.listStyleId();
      String tableName = parts.get(parts.size() - 1);
      List<String> namespaceParts = parts.subList(0, parts.size() - 1);
      String namespacePath = String.join(".", namespaceParts);

      // Get table using Generic Table API
      PolarisModels.LoadGenericTableResponse response =
          restClient.get(
              "/namespaces/" + namespacePath + "/generic-tables/" + tableName,
              PolarisModels.LoadGenericTableResponse.class);

      PolarisModels.GenericTable table = response.getTable();

      // Verify it's a Lance table
      if (!TABLE_FORMAT_LANCE.equals(table.getFormat())) {
        throw LanceNamespaceException.badRequest(
            "Invalid table format",
            "InvalidTableFormat",
            tableId.stringStyleId(),
            String.format(
                "Table %s is not a Lance table (format: %s)",
                tableId.stringStyleId(), table.getFormat()));
      }

      DescribeTableResponse result = new DescribeTableResponse();
      result.setLocation(table.getBaseLocation());
      Map<String, String> resultProps = new HashMap<>();
      if (table.getProperties() != null) {
        resultProps.putAll(table.getProperties());
      }
      if (table.getDoc() != null) {
        resultProps.put("comment", table.getDoc());
      }
      result.setProperties(resultProps);
      return result;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Table not found",
            "NoSuchTable",
            tableId.stringStyleId(),
            "Table not found: " + tableId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to describe table", "ServerError", tableId.stringStyleId(), e.getMessage());
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    ObjectIdentifier namespaceId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        namespaceId.levels() >= 1, "Namespace must have at least one level");

    try {
      String namespacePath = namespaceId.stringStyleId();

      // List tables using Generic Table API
      PolarisModels.ListGenericTablesResponse response =
          restClient.get(
              "/namespaces/" + namespacePath + "/generic-tables",
              PolarisModels.ListGenericTablesResponse.class);

      ListTablesResponse result = new ListTablesResponse();
      // Convert table identifiers to table names only
      Set<String> tableNames = new LinkedHashSet<>();
      if (response.getIdentifiers() != null) {
        for (PolarisModels.TableIdentifier id : response.getIdentifiers()) {
          tableNames.add(id.getName());
        }
      }
      result.setTables(tableNames);
      return result;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Namespace not found",
            "NoSuchNamespace",
            namespaceId.stringStyleId(),
            "Namespace not found: " + namespaceId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to list tables", "ServerError", namespaceId.stringStyleId(), e.getMessage());
    }
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() >= 2, "Table identifier must have at least 2 levels");

    try {
      // Split into namespace and table name
      List<String> parts = tableId.listStyleId();
      String tableName = parts.get(parts.size() - 1);
      List<String> namespaceParts = parts.subList(0, parts.size() - 1);
      String namespacePath = String.join(".", namespaceParts);

      // Drop table using Generic Table API
      restClient.delete("/namespaces/" + namespacePath + "/generic-tables/" + tableName);

      LOG.info("Dropped table: {}.{}", namespacePath, tableName);

      DropTableResponse result = new DropTableResponse();
      // DropTableResponse has no fields to set based on the model
      return result;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw LanceNamespaceException.notFound(
            "Table not found",
            "NoSuchTable",
            tableId.stringStyleId(),
            "Table not found: " + tableId.stringStyleId());
      }
      throw LanceNamespaceException.serverError(
          "Failed to drop table", "ServerError", tableId.stringStyleId(), e.getMessage());
    }
  }

  // These methods are not part of the LanceNamespace interface
  // They were removed as they don't exist in the interface
  private Dataset openTableInternal(String location, Schema schema) {
    try {
      return Dataset.open(location, allocator);
    } catch (Exception e) {
      throw LanceNamespaceException.serverError(
          "Failed to open Lance table",
          "DatasetError",
          location,
          "Failed to open Lance table at: " + location + ": " + e.getMessage());
    }
  }

  private Dataset createTableInternal(String location, Schema schema, WriteParams params) {
    try {
      return Dataset.create(allocator, location, schema, params);
    } catch (Exception e) {
      throw LanceNamespaceException.serverError(
          "Failed to create Lance table",
          "DatasetError",
          location,
          "Failed to create Lance table at: " + location + ": " + e.getMessage());
    }
  }

  public void close() {
    if (restClient != null) {
      try {
        restClient.close();
      } catch (IOException e) {
        LOG.warn("Failed to close REST client", e);
      }
    }
  }
}
