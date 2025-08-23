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
package com.lancedb.lance.namespace.unity;

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
import com.lancedb.lance.namespace.util.CommonUtil;
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.PageUtil;
import com.lancedb.lance.namespace.util.ValidationUtil;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Unity Catalog namespace implementation for Lance. */
public class UnityNamespace implements LanceNamespace {
  private static final Logger LOG = LoggerFactory.getLogger(UnityNamespace.class);
  private static final String TABLE_TYPE_LANCE = "lance";
  private static final String TABLE_TYPE_EXTERNAL = "EXTERNAL";
  private static final String MANAGED_BY_KEY = "managed_by";
  private static final String TABLE_TYPE_KEY = "table_type";
  private static final String VERSION_KEY = "version";

  private UnityNamespaceConfig config;
  private RestClient restClient;
  private BufferAllocator allocator;

  public UnityNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    this.config = new UnityNamespaceConfig(configProperties);

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
    LOG.info("Initialized Unity namespace with endpoint: {}", config.getEndpoint());
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    // Unity supports 3-level namespace: catalog.schema.table
    ValidationUtil.checkArgument(
        nsId.levels() <= 2, "Expect at most 2-level namespace but get %s", nsId);

    try {
      List<String> namespaces;

      if (nsId.levels() == 0) {
        // Return the configured catalog as the only top-level namespace
        namespaces = Collections.singletonList(config.getCatalog());
      } else if (nsId.levels() == 1) {
        // List schemas in the catalog
        String catalog = nsId.namespace(0);
        if (!catalog.equals(config.getCatalog())) {
          throw new LanceNamespaceException(
              "Catalog %s not found. Expected: %s", catalog, config.getCatalog());
        }

        Map<String, String> params = new HashMap<>();
        params.put("catalog_name", catalog);
        if (request.getMaxResults() != null) {
          params.put("max_results", request.getMaxResults().toString());
        }
        if (request.getPageToken() != null) {
          params.put("page_token", request.getPageToken());
        }

        UnityModels.ListSchemasResponse response =
            restClient.get("/schemas", params, UnityModels.ListSchemasResponse.class);

        if (response != null && response.getSchemas() != null) {
          namespaces =
              response.getSchemas().stream()
                  .map(UnityModels.SchemaInfo::getName)
                  .collect(Collectors.toList());
        } else {
          namespaces = Collections.emptyList();
        }
      } else {
        namespaces = Collections.emptyList();
      }

      Collections.sort(namespaces);
      PageUtil.PageResult<String> result =
          PageUtil.paginate(namespaces, request.getPageToken(), request.getMaxResults());

      ListNamespacesResponse response = new ListNamespacesResponse();
      response.setIds(result.getData());
      response.setPageToken(result.getNextPageToken());
      return response;

    } catch (IOException e) {
      throw new LanceNamespaceException(e, "Failed to list namespaces");
    }
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.namespace(0);
    String schema = nsId.namespace(1);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Cannot create namespace in catalog %s. Expected: %s", catalog, config.getCatalog());
    }

    try {
      UnityModels.CreateSchema createSchema = new UnityModels.CreateSchema();
      createSchema.setName(schema);
      createSchema.setCatalogName(catalog);
      createSchema.setProperties(request.getProperties());

      UnityModels.SchemaInfo schemaInfo =
          restClient.post("/schemas", createSchema, UnityModels.SchemaInfo.class);

      CreateNamespaceResponse response = new CreateNamespaceResponse();
      response.setId(request.getId());
      response.setProperties(schemaInfo.getProperties());
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 409) {
        throw new LanceNamespaceException("Namespace %s already exists", request.getId());
      }
      throw new LanceNamespaceException(e, "Failed to create namespace %s", request.getId());
    } catch (IOException e) {
      throw new LanceNamespaceException(e, "Failed to create namespace %s", request.getId());
    }
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.namespace(0);
    String schema = nsId.namespace(1);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Catalog %s not found. Expected: %s", catalog, config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema;
      UnityModels.SchemaInfo schemaInfo =
          restClient.get("/schemas/" + fullName, UnityModels.SchemaInfo.class);

      DescribeNamespaceResponse response = new DescribeNamespaceResponse();
      response.setId(request.getId());
      response.setProperties(schemaInfo.getProperties());
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 404) {
        throw new LanceNamespaceException("Namespace %s not found", request.getId());
      }
      throw new LanceNamespaceException(e, "Failed to describe namespace %s", request.getId());
    } catch (IOException e) {
      throw new LanceNamespaceException(e, "Failed to describe namespace %s", request.getId());
    }
  }

  @Override
  public boolean namespaceExists(NamespaceExistsRequest request) {
    try {
      describeNamespace(new DescribeNamespaceRequest().id(request.getId()));
      return true;
    } catch (LanceNamespaceException e) {
      if (e.getMessage().contains("not found")) {
        return false;
      }
      throw e;
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.namespace(0);
    String schema = nsId.namespace(1);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Cannot drop namespace in catalog %s. Expected: %s", catalog, config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema;
      Map<String, String> params = new HashMap<>();
      if (request.isPurge() != null && request.isPurge()) {
        params.put("force", "true");
      }

      restClient.delete("/schemas/" + fullName, params);

      DropNamespaceResponse response = new DropNamespaceResponse();
      response.setDropped(true);
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 404) {
        DropNamespaceResponse response = new DropNamespaceResponse();
        response.setDropped(false);
        return response;
      }
      throw new LanceNamespaceException(e, "Failed to drop namespace %s", request.getId());
    } catch (IOException e) {
      throw new LanceNamespaceException(e, "Failed to drop namespace %s", request.getId());
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.namespace(0);
    String schema = nsId.namespace(1);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Catalog %s not found. Expected: %s", catalog, config.getCatalog());
    }

    try {
      Map<String, String> params = new HashMap<>();
      params.put("catalog_name", catalog);
      params.put("schema_name", schema);
      if (request.getMaxResults() != null) {
        params.put("max_results", request.getMaxResults().toString());
      }
      if (request.getPageToken() != null) {
        params.put("page_token", request.getPageToken());
      }

      UnityModels.ListTablesResponse unityResponse =
          restClient.get("/tables", params, UnityModels.ListTablesResponse.class);

      List<String> tables = Collections.emptyList();
      if (unityResponse != null && unityResponse.getTables() != null) {
        // Filter only Lance tables
        tables =
            unityResponse.getTables().stream()
                .filter(this::isLanceTable)
                .map(UnityModels.TableInfo::getName)
                .collect(Collectors.toList());
      }

      Collections.sort(tables);
      PageUtil.PageResult<String> result =
          PageUtil.paginate(tables, request.getPageToken(), request.getMaxResults());

      ListTablesResponse response = new ListTablesResponse();
      response.setNames(result.getData());
      response.setPageToken(result.getNextPageToken());
      return response;

    } catch (IOException e) {
      throw new LanceNamespaceException(
          e, "Failed to list tables in namespace %s", request.getId());
    }
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect a 3-level table identifier but get %s", tableId);

    String catalog = tableId.namespace(0);
    String schema = tableId.namespace(1);
    String table = tableId.namespace(2);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Cannot create table in catalog %s. Expected: %s", catalog, config.getCatalog());
    }

    try {
      // First create an empty Lance table dataset
      String tablePath =
          CommonUtil.getTablePath(config.getRoot(), config.getStorageProperties(), tableId);
      Dataset dataset =
          Dataset.create(
              allocator,
              tablePath,
              request.getJsonArrowSchema(),
              new WriteParams.Builder().build());
      dataset.close();

      // Create Unity table metadata
      UnityModels.CreateTable createTable = new UnityModels.CreateTable();
      createTable.setName(table);
      createTable.setCatalogName(catalog);
      createTable.setSchemaName(schema);
      createTable.setTableType(TABLE_TYPE_EXTERNAL);
      createTable.setDataSourceFormat(null); // Lance has its own format
      createTable.setColumns(null); // Lance manages its own schema
      createTable.setStorageLocation(tablePath);

      Map<String, String> properties = new HashMap<>();
      properties.put(TABLE_TYPE_KEY, TABLE_TYPE_LANCE);
      properties.put(
          MANAGED_BY_KEY, request.getManagedBy() != null ? request.getManagedBy() : "storage");
      properties.put(VERSION_KEY, "0");
      if (request.getProperties() != null) {
        properties.putAll(request.getProperties());
      }
      createTable.setProperties(properties);

      UnityModels.TableInfo tableInfo =
          restClient.post("/tables", createTable, UnityModels.TableInfo.class);

      CreateTableResponse response = new CreateTableResponse();
      response.setId(request.getId());
      response.setPath(tablePath);
      response.setProperties(tableInfo.getProperties());
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 409) {
        throw new LanceNamespaceException("Table %s already exists", request.getId());
      }
      throw new LanceNamespaceException(e, "Failed to create table %s", request.getId());
    } catch (Exception e) {
      throw new LanceNamespaceException(e, "Failed to create table %s", request.getId());
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect a 3-level table identifier but get %s", tableId);

    String catalog = tableId.namespace(0);
    String schema = tableId.namespace(1);
    String table = tableId.namespace(2);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Catalog %s not found. Expected: %s", catalog, config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema + "." + table;
      UnityModels.TableInfo tableInfo =
          restClient.get("/tables/" + fullName, UnityModels.TableInfo.class);

      if (!isLanceTable(tableInfo)) {
        throw new LanceNamespaceException("Table %s is not a Lance table", request.getId());
      }

      // Get the actual schema from the Lance dataset
      Dataset dataset =
          Dataset.open(allocator, tableInfo.getStorageLocation(), config.getStorageProperties());
      Schema arrowSchema = dataset.getSchema();
      dataset.close();

      DescribeTableResponse response = new DescribeTableResponse();
      response.setId(request.getId());
      response.setPath(tableInfo.getStorageLocation());
      response.setProperties(tableInfo.getProperties());
      response.setJsonArrowSchema(JsonArrowSchemaConverter.toJsonString(arrowSchema));
      response.setManagedBy(tableInfo.getProperties().getOrDefault(MANAGED_BY_KEY, "storage"));

      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 404) {
        throw new LanceNamespaceException("Table %s not found", request.getId());
      }
      throw new LanceNamespaceException(e, "Failed to describe table %s", request.getId());
    } catch (Exception e) {
      throw new LanceNamespaceException(e, "Failed to describe table %s", request.getId());
    }
  }

  @Override
  public boolean tableExists(TableExistsRequest request) {
    try {
      describeTable(new DescribeTableRequest().id(request.getId()));
      return true;
    } catch (LanceNamespaceException e) {
      if (e.getMessage().contains("not found")) {
        return false;
      }
      throw e;
    }
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect a 3-level table identifier but get %s", tableId);

    String catalog = tableId.namespace(0);
    String schema = tableId.namespace(1);
    String table = tableId.namespace(2);

    if (!catalog.equals(config.getCatalog())) {
      throw new LanceNamespaceException(
          "Cannot drop table in catalog %s. Expected: %s", catalog, config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema + "." + table;

      // First get the table info to check if it's a Lance table
      UnityModels.TableInfo tableInfo = null;
      try {
        tableInfo = restClient.get("/tables/" + fullName, UnityModels.TableInfo.class);
      } catch (RestClient.RestClientException e) {
        if (e.getStatusCode() == 404) {
          DropTableResponse response = new DropTableResponse();
          response.setDropped(false);
          return response;
        }
        throw e;
      }

      if (!isLanceTable(tableInfo)) {
        throw new LanceNamespaceException("Table %s is not a Lance table", request.getId());
      }

      // Delete from Unity
      restClient.delete("/tables/" + fullName);

      // If purge is requested, also delete the data
      if (request.isPurge() != null && request.isPurge()) {
        CommonUtil.dropTable(tableInfo.getStorageLocation(), config.getStorageProperties());
      }

      DropTableResponse response = new DropTableResponse();
      response.setDropped(true);
      return response;

    } catch (IOException e) {
      throw new LanceNamespaceException(e, "Failed to drop table %s", request.getId());
    }
  }

  @Override
  public void close() throws IOException {
    if (restClient != null) {
      restClient.close();
    }
  }

  private boolean isLanceTable(UnityModels.TableInfo tableInfo) {
    if (tableInfo == null || tableInfo.getProperties() == null) {
      return false;
    }
    String tableType = tableInfo.getProperties().get(TABLE_TYPE_KEY);
    return TABLE_TYPE_LANCE.equalsIgnoreCase(tableType);
  }
}
