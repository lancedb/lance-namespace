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
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.ValidationUtil;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.FloatingPointPrecision;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        String catalog = nsId.levelAtListPos(0);
        if (!catalog.equals(config.getCatalog())) {
          throw LanceNamespaceException.notFound(
              "Catalog not found",
              "CATALOG_NOT_FOUND",
              catalog,
              "Expected: " + config.getCatalog());
        }

        Map<String, String> params = new HashMap<>();
        params.put("catalog_name", catalog);
        if (request.getLimit() != null) {
          params.put("max_results", request.getLimit().toString());
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
      Set<String> resultNamespaces = new LinkedHashSet<>(namespaces);

      ListNamespacesResponse response = new ListNamespacesResponse();
      response.setNamespaces(resultNamespaces);
      return response;

    } catch (IOException e) {
      throw new LanceNamespaceException(500, "Failed to list namespaces: " + e.getMessage());
    }
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.levelAtListPos(0);
    String schema = nsId.levelAtListPos(1);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.badRequest(
          "Cannot create namespace in catalog",
          "INVALID_CATALOG",
          catalog,
          "Expected: " + config.getCatalog());
    }

    try {
      UnityModels.CreateSchema createSchema = new UnityModels.CreateSchema();
      createSchema.setName(schema);
      createSchema.setCatalogName(catalog);
      createSchema.setProperties(request.getProperties());

      UnityModels.SchemaInfo schemaInfo =
          restClient.post("/schemas", createSchema, UnityModels.SchemaInfo.class);

      CreateNamespaceResponse response = new CreateNamespaceResponse();
      response.setProperties(schemaInfo.getProperties());
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 409) {
        throw LanceNamespaceException.conflict(
            "Namespace already exists",
            "NAMESPACE_EXISTS",
            request.getId().toString(),
            e.getResponseBody());
      }
      throw new LanceNamespaceException(500, "Failed to create namespace: " + e.getMessage());
    } catch (IOException e) {
      throw new LanceNamespaceException(500, "Failed to create namespace: " + e.getMessage());
    }
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.levelAtListPos(0);
    String schema = nsId.levelAtListPos(1);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.notFound(
          "Catalog not found", "CATALOG_NOT_FOUND", catalog, "Expected: " + config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema;
      UnityModels.SchemaInfo schemaInfo =
          restClient.get("/schemas/" + fullName, UnityModels.SchemaInfo.class);

      DescribeNamespaceResponse response = new DescribeNamespaceResponse();
      response.setProperties(schemaInfo.getProperties());
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 404) {
        throw LanceNamespaceException.notFound(
            "Namespace not found",
            "NAMESPACE_NOT_FOUND",
            request.getId().toString(),
            e.getResponseBody());
      }
      throw new LanceNamespaceException(500, "Failed to describe namespace: " + e.getMessage());
    } catch (IOException e) {
      throw new LanceNamespaceException(500, "Failed to describe namespace: " + e.getMessage());
    }
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    describeNamespace(new DescribeNamespaceRequest().id(request.getId()));
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.levelAtListPos(0);
    String schema = nsId.levelAtListPos(1);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.badRequest(
          "Cannot drop namespace in catalog",
          "INVALID_CATALOG",
          catalog,
          "Expected: " + config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema;
      Map<String, String> params = new HashMap<>();
      if (request.getBehavior() != null
          && request.getBehavior() == DropNamespaceRequest.BehaviorEnum.CASCADE) {
        params.put("force", "true");
      }

      restClient.delete("/schemas/" + fullName, params);

      DropNamespaceResponse response = new DropNamespaceResponse();
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 404) {
        DropNamespaceResponse response = new DropNamespaceResponse();
        return response;
      }
      throw new LanceNamespaceException(500, "Failed to drop namespace: " + e.getMessage());
    } catch (IOException e) {
      throw new LanceNamespaceException(500, "Failed to drop namespace: " + e.getMessage());
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.levelAtListPos(0);
    String schema = nsId.levelAtListPos(1);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.notFound(
          "Catalog not found", "CATALOG_NOT_FOUND", catalog, "Expected: " + config.getCatalog());
    }

    try {
      Map<String, String> params = new HashMap<>();
      params.put("catalog_name", catalog);
      params.put("schema_name", schema);
      if (request.getLimit() != null) {
        params.put("max_results", request.getLimit().toString());
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
      Set<String> resultTables = new LinkedHashSet<>(tables);

      ListTablesResponse response = new ListTablesResponse();
      response.setTables(resultTables);
      return response;

    } catch (IOException e) {
      throw new LanceNamespaceException(500, "Failed to list tables: " + e.getMessage());
    }
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect a 3-level table identifier but get %s", tableId);

    String catalog = tableId.levelAtListPos(0);
    String schema = tableId.levelAtListPos(1);
    String table = tableId.levelAtListPos(2);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.badRequest(
          "Cannot create table in catalog",
          "INVALID_CATALOG",
          catalog,
          "Expected: " + config.getCatalog());
    }

    try {
      // First create an empty Lance table dataset
      String tablePath = config.getRoot() + "/" + catalog + "/" + schema + "/" + table;
      ValidationUtil.checkNotNull(request.getSchema(), "Schema is required in CreateTableRequest");
      Schema arrowSchema = JsonArrowSchemaConverter.convertToArrowSchema(request.getSchema());

      WriteParams writeParams =
          new WriteParams.Builder().withMode(WriteParams.WriteMode.CREATE).build();

      Dataset dataset = Dataset.create(allocator, tablePath, arrowSchema, writeParams);
      dataset.close();

      // Create Unity table metadata
      UnityModels.CreateTable createTable = new UnityModels.CreateTable();
      createTable.setName(table);
      createTable.setCatalogName(catalog);
      createTable.setSchemaName(schema);
      createTable.setTableType(TABLE_TYPE_EXTERNAL);
      // Unity doesn't recognize LANCE format, use TEXT as a generic format for external tables
      // The actual format is determined by the table_type=lance property
      createTable.setDataSourceFormat("TEXT");
      createTable.setColumns(convertArrowSchemaToUnityColumns(arrowSchema));
      createTable.setStorageLocation(tablePath);

      Map<String, String> properties = new HashMap<>();
      properties.put(TABLE_TYPE_KEY, TABLE_TYPE_LANCE);
      properties.put(MANAGED_BY_KEY, "storage");
      properties.put(VERSION_KEY, "0");
      if (request.getProperties() != null) {
        properties.putAll(request.getProperties());
      }
      createTable.setProperties(properties);

      UnityModels.TableInfo tableInfo =
          restClient.post("/tables", createTable, UnityModels.TableInfo.class);

      CreateTableResponse response = new CreateTableResponse();
      response.setLocation(tablePath);
      response.setVersion(1L);
      response.setProperties(tableInfo.getProperties());
      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 409) {
        throw LanceNamespaceException.conflict(
            "Table already exists",
            "TABLE_EXISTS",
            request.getId().toString(),
            e.getResponseBody());
      }
      throw new LanceNamespaceException(500, "Failed to create table: " + e.getMessage());
    } catch (Exception e) {
      throw new LanceNamespaceException(500, "Failed to create table: " + e.getMessage());
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect a 3-level table identifier but get %s", tableId);

    String catalog = tableId.levelAtListPos(0);
    String schema = tableId.levelAtListPos(1);
    String table = tableId.levelAtListPos(2);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.notFound(
          "Catalog not found", "CATALOG_NOT_FOUND", catalog, "Expected: " + config.getCatalog());
    }

    try {
      String fullName = catalog + "." + schema + "." + table;
      UnityModels.TableInfo tableInfo =
          restClient.get("/tables/" + fullName, UnityModels.TableInfo.class);

      if (!isLanceTable(tableInfo)) {
        throw LanceNamespaceException.badRequest(
            "Not a Lance table",
            "INVALID_TABLE",
            request.getId().toString(),
            "Table is not managed by Lance");
      }

      // Get the actual schema from the Lance dataset
      Dataset dataset = Dataset.open(tableInfo.getStorageLocation(), allocator);
      Schema arrowSchema = dataset.getSchema();
      dataset.close();

      DescribeTableResponse response = new DescribeTableResponse();
      response.setLocation(tableInfo.getStorageLocation());
      response.setProperties(tableInfo.getProperties());
      // For now, we'll just return the schema that Unity has stored
      // TODO: Convert from Arrow Schema to JsonArrowSchema
      // response.setSchema(convertArrowSchemaToJson(arrowSchema));

      return response;

    } catch (RestClient.RestClientException e) {
      if (e.getStatusCode() == 404) {
        throw LanceNamespaceException.notFound(
            "Table not found", "TABLE_NOT_FOUND", request.getId().toString(), e.getResponseBody());
      }
      throw new LanceNamespaceException(500, "Failed to describe table: " + e.getMessage());
    } catch (Exception e) {
      throw new LanceNamespaceException(500, "Failed to describe table: " + e.getMessage());
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    describeTable(new DescribeTableRequest().id(request.getId()));
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect a 3-level table identifier but get %s", tableId);

    String catalog = tableId.levelAtListPos(0);
    String schema = tableId.levelAtListPos(1);
    String table = tableId.levelAtListPos(2);

    if (!catalog.equals(config.getCatalog())) {
      throw LanceNamespaceException.badRequest(
          "Cannot drop table in catalog",
          "INVALID_CATALOG",
          catalog,
          "Expected: " + config.getCatalog());
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
          response.setId(request.getId());
          return response;
        }
        throw e;
      }

      if (!isLanceTable(tableInfo)) {
        throw LanceNamespaceException.badRequest(
            "Not a Lance table",
            "INVALID_TABLE",
            request.getId().toString(),
            "Table is not managed by Lance");
      }

      // Delete from Unity
      restClient.delete("/tables/" + fullName);

      // Delete Lance dataset data
      try {
        Dataset.drop(tableInfo.getStorageLocation(), Collections.emptyMap());
      } catch (Exception e) {
        // Log warning but continue - Unity metadata already deleted
        LOG.warn(
            "Failed to delete Lance dataset at {}: {}",
            tableInfo.getStorageLocation(),
            e.getMessage());
      }

      DropTableResponse response = new DropTableResponse();
      response.setId(request.getId());
      response.setLocation(tableInfo.getStorageLocation());
      return response;

    } catch (IOException e) {
      throw new LanceNamespaceException(500, "Failed to drop table: " + e.getMessage());
    }
  }

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

  private List<UnityModels.ColumnInfo> convertArrowSchemaToUnityColumns(Schema arrowSchema) {
    List<UnityModels.ColumnInfo> columns = new ArrayList<>();
    for (Field field : arrowSchema.getFields()) {
      UnityModels.ColumnInfo columnInfo = new UnityModels.ColumnInfo();
      columnInfo.setName(field.getName());
      String unityType = convertArrowTypeToUnityType(field.getType());
      columnInfo.setTypeText(unityType);
      columnInfo.setTypeJson(convertArrowTypeToUnityTypeJson(field.getType()));
      columnInfo.setTypeName(unityType);
      columnInfo.setTypeScale(null);
      columnInfo.setTypePrecision(null);
      columnInfo.setTypeIntervalType(null);
      columnInfo.setPosition(columns.size());
      columnInfo.setComment(null);
      columnInfo.setNullable(field.isNullable());
      columnInfo.setPartitionIndex(null);
      columns.add(columnInfo);
    }
    return columns;
  }

  private String convertArrowTypeToUnityType(ArrowType arrowType) {
    if (arrowType instanceof ArrowType.Utf8) {
      return "STRING";
    } else if (arrowType instanceof ArrowType.Int) {
      ArrowType.Int intType = (ArrowType.Int) arrowType;
      if (intType.getBitWidth() == 32) {
        return "INT";
      } else if (intType.getBitWidth() == 64) {
        return "BIGINT";
      }
    } else if (arrowType instanceof ArrowType.FloatingPoint) {
      ArrowType.FloatingPoint fpType = (ArrowType.FloatingPoint) arrowType;
      if (fpType.getPrecision() == FloatingPointPrecision.SINGLE) {
        return "FLOAT";
      } else if (fpType.getPrecision() == FloatingPointPrecision.DOUBLE) {
        return "DOUBLE";
      }
    } else if (arrowType instanceof ArrowType.Bool) {
      return "BOOLEAN";
    } else if (arrowType instanceof ArrowType.Date) {
      return "DATE";
    } else if (arrowType instanceof ArrowType.Timestamp) {
      return "TIMESTAMP";
    }
    // Default fallback
    return "STRING";
  }

  private String convertArrowTypeToUnityTypeJson(ArrowType arrowType) {
    if (arrowType instanceof ArrowType.Utf8) {
      return "{\"type\":\"string\"}";
    } else if (arrowType instanceof ArrowType.Int) {
      ArrowType.Int intType = (ArrowType.Int) arrowType;
      if (intType.getBitWidth() == 32) {
        return "{\"type\":\"integer\"}";
      } else if (intType.getBitWidth() == 64) {
        return "{\"type\":\"long\"}";
      }
    } else if (arrowType instanceof ArrowType.FloatingPoint) {
      ArrowType.FloatingPoint fpType = (ArrowType.FloatingPoint) arrowType;
      if (fpType.getPrecision() == FloatingPointPrecision.SINGLE) {
        return "{\"type\":\"float\"}";
      } else if (fpType.getPrecision() == FloatingPointPrecision.DOUBLE) {
        return "{\"type\":\"double\"}";
      }
    } else if (arrowType instanceof ArrowType.Bool) {
      return "{\"type\":\"boolean\"}";
    } else if (arrowType instanceof ArrowType.Date) {
      return "{\"type\":\"date\"}";
    } else if (arrowType instanceof ArrowType.Timestamp) {
      return "{\"type\":\"timestamp\"}";
    }
    // Default fallback
    return "{\"type\":\"string\"}";
  }
}
