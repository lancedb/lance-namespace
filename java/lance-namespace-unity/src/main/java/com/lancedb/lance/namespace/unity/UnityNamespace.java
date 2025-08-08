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
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
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
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.util.HttpClient;
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnityNamespace implements LanceNamespace, AutoCloseable {

  private static final Logger LOG = LoggerFactory.getLogger(UnityNamespace.class);

  private static final String TABLE_FORMAT_KEY = "table_format";
  private static final String TABLE_FORMAT_VALUE = "lance";
  private static final String MANAGED_BY_KEY = "managed_by";
  private static final String VERSION_KEY = "version";
  private static final String DEFAULT_MANAGED_BY = "storage";
  private static final String API_PREFIX = "/api/2.1/unity-catalog";

  private UnityNamespaceConfig config;
  private HttpClient httpClient;
  private BufferAllocator allocator;

  public UnityNamespace() {
    // Empty constructor for initialization via initialize method
  }

  @Override
  public void initialize(Map<String, String> properties, BufferAllocator allocator) {
    this.config = new UnityNamespaceConfig(properties);
    this.httpClient = new HttpClient(config.getEndpoint() + API_PREFIX, config.getToken());
    this.allocator = allocator;
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request)
      throws LanceNamespaceException {
    List<String> id = request.getId();
    if (id == null || id.isEmpty() || id.size() > 2) {
      throw LanceNamespaceException.badRequest(
          "Unity namespace requires 1 (catalog) or 2 (catalog, schema) level identifiers",
          "INVALID_ARGUMENT",
          "createNamespace",
          "Invalid namespace identifier structure");
    }

    try {
      if (id.size() == 1) {
        // Create catalog
        String catalogName = id.get(0);
        ObjectNode catalogRequest = JsonUtil.createObjectNode();
        catalogRequest.put("name", catalogName);
        if (request.getProperties() != null) {
          ObjectNode props = JsonUtil.createObjectNode();
          request.getProperties().forEach(props::put);
          catalogRequest.set("properties", props);
        }

        JsonNode response = httpClient.post("/catalogs", catalogRequest);

        CreateNamespaceResponse result = new CreateNamespaceResponse();
        // Unity catalog doesn't return specific properties for catalog creation
        return result;
      } else {
        // Create schema
        String catalogName = id.get(0);
        String schemaName = id.get(1);
        ObjectNode schemaRequest = JsonUtil.createObjectNode();
        schemaRequest.put("catalog_name", catalogName);
        schemaRequest.put("name", schemaName);
        if (request.getProperties() != null) {
          ObjectNode props = JsonUtil.createObjectNode();
          request.getProperties().forEach(props::put);
          schemaRequest.set("properties", props);
        }

        JsonNode response = httpClient.post("/schemas", schemaRequest);

        CreateNamespaceResponse result = new CreateNamespaceResponse();
        // Unity catalog doesn't return specific properties for schema creation
        return result;
      }
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Create namespace");
    }
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request)
      throws LanceNamespaceException {
    List<String> parent = request.getId();

    try {
      ListNamespacesResponse response = new ListNamespacesResponse();
      Set<String> namespaces = new HashSet<>();

      if (parent == null || parent.isEmpty()) {
        // List catalogs
        JsonNode catalogsResponse = httpClient.get("/catalogs");
        ArrayNode catalogs = (ArrayNode) catalogsResponse.get("catalogs");
        if (catalogs != null) {
          for (JsonNode catalog : catalogs) {
            namespaces.add(catalog.get("name").asText());
          }
        }
      } else if (parent.size() == 1) {
        // List schemas in catalog
        String catalogName = parent.get(0);
        JsonNode schemasResponse = httpClient.get("/schemas?catalog_name=" + catalogName);
        ArrayNode schemas = (ArrayNode) schemasResponse.get("schemas");
        if (schemas != null) {
          for (JsonNode schema : schemas) {
            namespaces.add(catalogName + "." + schema.get("name").asText());
          }
        }
      } else {
        throw LanceNamespaceException.badRequest(
            "Unity namespace supports at most 2 levels",
            "INVALID_ARGUMENT",
            "listNamespaces",
            "Invalid namespace level structure");
      }

      response.setNamespaces(namespaces);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "List namespaces");
    }
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request)
      throws LanceNamespaceException {
    List<String> id = request.getId();
    if (id == null || id.isEmpty() || id.size() > 2) {
      throw LanceNamespaceException.badRequest(
          "Unity namespace requires 1 (catalog) or 2 (catalog, schema) level identifiers",
          "INVALID_ARGUMENT",
          "describeNamespace",
          "Invalid namespace identifier structure");
    }

    try {
      DescribeNamespaceResponse response = new DescribeNamespaceResponse();
      Map<String, String> properties = new HashMap<>();

      if (id.size() == 1) {
        // Describe catalog
        String catalogName = id.get(0);
        JsonNode catalogResponse = httpClient.get("/catalogs/" + catalogName);
        
        if (catalogResponse.has("properties")) {
          JsonNode props = catalogResponse.get("properties");
          props.fields().forEachRemaining(
              entry -> properties.put(entry.getKey(), entry.getValue().asText()));
        }
        if (catalogResponse.has("comment") && !catalogResponse.get("comment").isNull()) {
          properties.put("comment", catalogResponse.get("comment").asText());
        }
      } else {
        // Describe schema
        String catalogName = id.get(0);
        String schemaName = id.get(1);
        String path = String.format("/schemas/%s.%s", catalogName, schemaName);
        JsonNode schemaResponse = httpClient.get(path);
        
        if (schemaResponse.has("properties")) {
          JsonNode props = schemaResponse.get("properties");
          props.fields().forEachRemaining(
              entry -> properties.put(entry.getKey(), entry.getValue().asText()));
        }
        if (schemaResponse.has("comment") && !schemaResponse.get("comment").isNull()) {
          properties.put("comment", schemaResponse.get("comment").asText());
        }
      }

      response.setProperties(properties);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Describe namespace");
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request)
      throws LanceNamespaceException {
    List<String> id = request.getId();
    if (id == null || id.isEmpty() || id.size() > 2) {
      throw LanceNamespaceException.badRequest(
          "Unity namespace requires 1 (catalog) or 2 (catalog, schema) level identifiers",
          "INVALID_ARGUMENT",
          "dropNamespace",
          "Invalid namespace identifier structure");
    }

    try {
      if (id.size() == 1) {
        // Drop catalog
        String catalogName = id.get(0);
        httpClient.delete("/catalogs/" + catalogName);
      } else {
        // Drop schema
        String catalogName = id.get(0);
        String schemaName = id.get(1);
        String path = String.format("/schemas/%s.%s", catalogName, schemaName);
        httpClient.delete(path);
      }

      DropNamespaceResponse response = new DropNamespaceResponse();
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Drop namespace");
    }
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) throws LanceNamespaceException {
    List<String> id = request.getId();
    if (id == null || id.isEmpty() || id.size() > 2) {
      throw LanceNamespaceException.badRequest(
          "Unity namespace requires 1 (catalog) or 2 (catalog, schema) level identifiers",
          "INVALID_ARGUMENT",
          "namespaceExists",
          "Invalid namespace identifier structure");
    }

    try {
      if (id.size() == 1) {
        // Check if catalog exists
        String catalogName = id.get(0);
        httpClient.get("/catalogs/" + catalogName);
      } else {
        // Check if schema exists
        String catalogName = id.get(0);
        String schemaName = id.get(1);
        String path = String.format("/schemas/%s.%s", catalogName, schemaName);
        httpClient.get(path);
      }
      // If we get here without exception, namespace exists
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Namespace exists check");
    }
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData)
      throws LanceNamespaceException {
    List<String> name = request.getId();
    if (name == null || name.size() != 3) {
      throw LanceNamespaceException.badRequest(
          "Unity table requires 3-level identifier: catalog.schema.table",
          "INVALID_ARGUMENT",
          "createTable",
          "Invalid table identifier structure");
    }

    String catalogName = name.get(0);
    String schemaName = name.get(1);
    String tableName = name.get(2);

    try {
      // Build storage location
      String location = buildTableLocation(catalogName, schemaName, tableName);

      // Create table request
      ObjectNode tableRequest = JsonUtil.createObjectNode();
      tableRequest.put("catalog_name", catalogName);
      tableRequest.put("schema_name", schemaName);
      tableRequest.put("name", tableName);
      tableRequest.put("table_type", "EXTERNAL");
      tableRequest.put("storage_location", location);

      // Set properties for Lance table
      ObjectNode properties = JsonUtil.createObjectNode();
      if (request.getProperties() != null) {
        request.getProperties().forEach(properties::put);
      }
      properties.put(TABLE_FORMAT_KEY, TABLE_FORMAT_VALUE);
      if (!properties.has(MANAGED_BY_KEY)) {
        properties.put(MANAGED_BY_KEY, DEFAULT_MANAGED_BY);
      }
      tableRequest.set("properties", properties);

      JsonNode tableResponse = httpClient.post("/tables", tableRequest);

      // Create the actual Lance table if schema is provided
      if (request.getSchema() != null) {
        Schema arrowSchema = JsonArrowSchemaConverter.convertToArrowSchema(request.getSchema());
        Dataset.create(allocator, location, arrowSchema, new WriteParams.Builder().build());
      }

      CreateTableResponse response = new CreateTableResponse();
      response.setLocation(location);

      // Convert properties back
      Map<String, String> responseProperties = new HashMap<>();
      if (tableResponse.has("properties")) {
        JsonNode props = tableResponse.get("properties");
        props
            .fields()
            .forEachRemaining(
                entry -> responseProperties.put(entry.getKey(), entry.getValue().asText()));
      }
      response.setProperties(responseProperties);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Create table");
    }
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request)
      throws LanceNamespaceException {
    List<String> name = request.getId();
    if (name == null || name.size() != 3) {
      throw LanceNamespaceException.badRequest(
          "Unity table requires 3-level identifier: catalog.schema.table",
          "INVALID_ARGUMENT",
          "registerTable",
          "Invalid table identifier structure");
    }

    String catalogName = name.get(0);
    String schemaName = name.get(1);
    String tableName = name.get(2);
    String location = request.getLocation();

    if (location == null || location.isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Table location is required for registration",
          "INVALID_ARGUMENT",
          "registerTable",
          "Missing table location");
    }

    try {
      // Read schema from existing Lance table
      Dataset dataset = Dataset.open(allocator, location);
      Schema arrowSchema = dataset.getSchema();
      String jsonSchema = JsonArrowSchemaConverter.convertToJsonSchema(arrowSchema);
      dataset.close();

      // Create table request
      ObjectNode tableRequest = JsonUtil.createObjectNode();
      tableRequest.put("catalog_name", catalogName);
      tableRequest.put("schema_name", schemaName);
      tableRequest.put("name", tableName);
      tableRequest.put("table_type", "EXTERNAL");
      tableRequest.put("storage_location", location);

      // Set column schema from Lance table
      ArrayNode columns = JsonUtil.MAPPER.createArrayNode();
      JsonNode schemaNode = JsonUtil.parseJson(jsonSchema);
      if (schemaNode.has("fields")) {
        for (JsonNode field : schemaNode.get("fields")) {
          ObjectNode column = JsonUtil.createObjectNode();
          column.put("name", field.get("name").asText());
          column.put("type_name", convertArrowTypeToSql(field.get("type")));
          column.put("type_text", convertArrowTypeToSql(field.get("type")));
          column.put("nullable", field.get("nullable").asBoolean());
          if (field.has("metadata") && field.get("metadata").has("comment")) {
            column.put("comment", field.get("metadata").get("comment").asText());
          }
          columns.add(column);
        }
      }
      tableRequest.set("columns", columns);

      // Set properties for Lance table
      ObjectNode properties = JsonUtil.createObjectNode();
      if (request.getProperties() != null) {
        request.getProperties().forEach(properties::put);
      }
      properties.put(TABLE_FORMAT_KEY, TABLE_FORMAT_VALUE);
      if (!properties.has(MANAGED_BY_KEY)) {
        properties.put(MANAGED_BY_KEY, DEFAULT_MANAGED_BY);
      }
      tableRequest.set("properties", properties);

      JsonNode tableResponse = httpClient.post("/tables", tableRequest);

      RegisterTableResponse response = new RegisterTableResponse();
      response.setId(name);
      response.setLocation(location);

      // Convert properties back
      Map<String, String> responseProperties = new HashMap<>();
      if (tableResponse.has("properties")) {
        JsonNode props = tableResponse.get("properties");
        props
            .fields()
            .forEachRemaining(
                entry -> responseProperties.put(entry.getKey(), entry.getValue().asText()));
      }
      response.setProperties(responseProperties);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Register table");
    }
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request)
      throws LanceNamespaceException {
    List<String> name = request.getId();
    if (name == null || name.size() != 3) {
      throw LanceNamespaceException.badRequest(
          "Unity table requires 3-level identifier: catalog.schema.table",
          "INVALID_ARGUMENT",
          "deregisterTable",
          "Invalid table identifier structure");
    }

    String catalogName = name.get(0);
    String schemaName = name.get(1);
    String tableName = name.get(2);

    try {
      // Get table info first to verify it's a Lance table
      String path = String.format("/tables/%s.%s.%s", catalogName, schemaName, tableName);
      JsonNode tableInfo = httpClient.get(path);
      
      // Verify it's a Lance table
      if (!isLanceTable(tableInfo)) {
        throw LanceNamespaceException.badRequest(
            "Table is not a Lance table",
            "INVALID_ARGUMENT",
            "deregisterTable",
            "Only Lance tables can be deregistered through this namespace");
      }

      // Delete only from Unity Catalog (keep the data)
      httpClient.delete(path);

      DeregisterTableResponse response = new DeregisterTableResponse();
      response.setId(name);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Deregister table");
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) throws LanceNamespaceException {
    List<String> namespace = request.getId();
    if (namespace == null || namespace.size() != 2) {
      throw LanceNamespaceException.badRequest(
          "Unity requires catalog and schema for listing tables",
          "INVALID_ARGUMENT",
          "listTables",
          "Invalid namespace identifier structure");
    }

    String catalogName = namespace.get(0);
    String schemaName = namespace.get(1);

    try {
      String path =
          String.format("/tables?catalog_name=%s&schema_name=%s", catalogName, schemaName);
      JsonNode tablesResponse = httpClient.get(path);

      ListTablesResponse response = new ListTablesResponse();
      Set<String> tables = new HashSet<>();

      ArrayNode tablesArray = (ArrayNode) tablesResponse.get("tables");
      if (tablesArray != null) {
        for (JsonNode table : tablesArray) {
          // Only include Lance tables
          if (isLanceTable(table)) {
            tables.add(catalogName + "." + schemaName + "." + table.get("name").asText());
          }
        }
      }

      response.setTables(tables);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "List tables");
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request)
      throws LanceNamespaceException {
    List<String> name = request.getId();
    if (name == null || name.size() != 3) {
      throw LanceNamespaceException.badRequest(
          "Unity table requires 3-level identifier: catalog.schema.table",
          "INVALID_ARGUMENT",
          "describeTable",
          "Invalid table identifier structure");
    }

    String catalogName = name.get(0);
    String schemaName = name.get(1);
    String tableName = name.get(2);

    try {
      // Get table info from Unity Catalog
      String path = String.format("/tables/%s.%s.%s", catalogName, schemaName, tableName);
      JsonNode tableInfo = httpClient.get(path);
      
      // Verify it's a Lance table
      if (!isLanceTable(tableInfo)) {
        throw LanceNamespaceException.badRequest(
            "Table is not a Lance table",
            "INVALID_ARGUMENT",
            "describeTable",
            "Only Lance tables can be described through this namespace");
      }

      DescribeTableResponse response = new DescribeTableResponse();
      response.setId(name);
      
      // Set location
      if (tableInfo.has("storage_location")) {
        response.setLocation(tableInfo.get("storage_location").asText());
      }
      
      // Set properties
      Map<String, String> properties = new HashMap<>();
      if (tableInfo.has("properties")) {
        JsonNode props = tableInfo.get("properties");
        props.fields().forEachRemaining(
            entry -> properties.put(entry.getKey(), entry.getValue().asText()));
      }
      
      // Add additional metadata from Unity Catalog
      if (tableInfo.has("table_type")) {
        properties.put("table_type", tableInfo.get("table_type").asText());
      }
      if (tableInfo.has("created_at")) {
        properties.put("created_at", tableInfo.get("created_at").asText());
      }
      if (tableInfo.has("updated_at")) {
        properties.put("updated_at", tableInfo.get("updated_at").asText());
      }
      if (tableInfo.has("comment") && !tableInfo.get("comment").isNull()) {
        properties.put("comment", tableInfo.get("comment").asText());
      }
      response.setProperties(properties);
      
      // Convert Unity Catalog column schema to Lance format
      if (tableInfo.has("columns")) {
        ArrayNode columns = (ArrayNode) tableInfo.get("columns");
        String jsonSchema = convertUnityColumnsToLanceSchema(columns);
        response.setSchema(jsonSchema);
      } else if (response.getLocation() != null) {
        // Fallback: read schema from actual Lance dataset
        try {
          Dataset dataset = Dataset.open(allocator, response.getLocation());
          Schema arrowSchema = dataset.getSchema();
          String jsonSchema = JsonArrowSchemaConverter.convertToJsonSchema(arrowSchema);
          response.setSchema(jsonSchema);
          dataset.close();
        } catch (Exception e) {
          LOG.warn("Failed to read schema from Lance dataset at {}: {}", 
              response.getLocation(), e.getMessage());
        }
      }
      
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Describe table");
    }
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) throws LanceNamespaceException {
    List<String> name = request.getId();
    if (name == null || name.size() != 3) {
      throw LanceNamespaceException.badRequest(
          "Unity table requires 3-level identifier: catalog.schema.table",
          "INVALID_ARGUMENT",
          "dropTable",
          "Invalid table identifier structure");
    }

    String catalogName = name.get(0);
    String schemaName = name.get(1);
    String tableName = name.get(2);

    try {
      // Get table info first to check if it's a Lance table and get location
      String path = String.format("/tables/%s.%s.%s", catalogName, schemaName, tableName);
      JsonNode tableInfo = httpClient.get(path);
      
      // Verify it's a Lance table
      if (!isLanceTable(tableInfo)) {
        throw LanceNamespaceException.badRequest(
            "Table is not a Lance table",
            "INVALID_ARGUMENT",
            "dropTable",
            "Only Lance tables can be dropped through this namespace");
      }

      // Delete from Unity Catalog
      httpClient.delete(path);

      // If purge is requested, also delete the data
      if (request.isPurge() && tableInfo.has("storage_location")) {
        String location = tableInfo.get("storage_location").asText();
        try {
          // Delete the Lance dataset files
          Dataset dataset = Dataset.open(allocator, location);
          dataset.delete();
        } catch (Exception e) {
          LOG.warn("Failed to purge table data at {}: {}", location, e.getMessage());
        }
      }

      DropTableResponse response = new DropTableResponse();
      response.setId(name);
      return response;
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Drop table");
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) throws LanceNamespaceException {
    List<String> name = request.getId();
    if (name == null || name.size() != 3) {
      throw LanceNamespaceException.badRequest(
          "Unity table requires 3-level identifier: catalog.schema.table",
          "INVALID_ARGUMENT",
          "tableExists",
          "Invalid table identifier structure");
    }

    String catalogName = name.get(0);
    String schemaName = name.get(1);
    String tableName = name.get(2);

    try {
      // Check if table exists in Unity Catalog
      String path = String.format("/tables/%s.%s.%s", catalogName, schemaName, tableName);
      JsonNode tableInfo = httpClient.get(path);
      
      // Verify it's a Lance table
      if (!isLanceTable(tableInfo)) {
        throw LanceNamespaceException.notFound(
            "Lance table not found",
            "NOT_FOUND",
            "tableExists",
            "Table exists but is not a Lance table");
      }
      
      // If we get here without exception, Lance table exists
    } catch (Exception e) {
      throw UnityToLanceErrorConverter.wrapException(e, "Table exists check");
    }
  }

  @Override
  public void close() throws IOException {
    if (httpClient != null) {
      httpClient.close();
    }
  }

  private String buildTableLocation(String catalog, String schema, String table) {
    return Paths.get(config.getRoot(), catalog, schema, table).toString();
  }

  private boolean isLanceTable(JsonNode table) {
    if (!table.has("properties")) {
      return false;
    }
    JsonNode properties = table.get("properties");
    if (!properties.has(TABLE_FORMAT_KEY)) {
      return false;
    }
    String tableFormat = properties.get(TABLE_FORMAT_KEY).asText();
    return TABLE_FORMAT_VALUE.equalsIgnoreCase(tableFormat);
  }

  private String convertArrowTypeToSql(JsonNode arrowType) {
    String typeName = arrowType.get("name").asText();
    switch (typeName.toLowerCase()) {
      case "bool":
        return "BOOLEAN";
      case "int8":
        return "TINYINT";
      case "int16":
        return "SMALLINT";
      case "int32":
        return "INT";
      case "int64":
        return "BIGINT";
      case "uint8":
        return "TINYINT";
      case "uint16":
        return "SMALLINT";
      case "uint32":
        return "INT";
      case "uint64":
        return "BIGINT";
      case "float":
        return "FLOAT";
      case "double":
        return "DOUBLE";
      case "utf8":
      case "string":
        return "STRING";
      case "binary":
        return "BINARY";
      case "date32":
        return "DATE";
      case "timestamp":
        return "TIMESTAMP";
      case "decimal":
        if (arrowType.has("precision") && arrowType.has("scale")) {
          return String.format("DECIMAL(%d,%d)", 
              arrowType.get("precision").asInt(), 
              arrowType.get("scale").asInt());
        }
        return "DECIMAL";
      case "list":
        if (arrowType.has("children") && arrowType.get("children").size() > 0) {
          JsonNode childType = arrowType.get("children").get(0).get("type");
          return "ARRAY<" + convertArrowTypeToSql(childType) + ">";
        }
        return "ARRAY<STRING>";
      case "struct":
        return "STRUCT";
      case "map":
        return "MAP<STRING,STRING>";
      default:
        return "STRING";
    }
  }

  private String convertUnityColumnsToLanceSchema(ArrayNode columns) {
    ObjectNode schema = JsonUtil.createObjectNode();
    ArrayNode fields = JsonUtil.MAPPER.createArrayNode();
    
    for (JsonNode column : columns) {
      ObjectNode field = JsonUtil.createObjectNode();
      field.put("name", column.get("name").asText());
      field.put("nullable", column.has("nullable") ? column.get("nullable").asBoolean() : true);
      
      // Convert SQL type back to Arrow type
      ObjectNode arrowType = convertSqlTypeToArrow(column.get("type_name").asText());
      field.set("type", arrowType);
      
      // Add metadata if comment exists
      if (column.has("comment") && !column.get("comment").isNull()) {
        ObjectNode metadata = JsonUtil.createObjectNode();
        metadata.put("comment", column.get("comment").asText());
        field.set("metadata", metadata);
      }
      
      fields.add(field);
    }
    
    schema.set("fields", fields);
    return JsonUtil.toJsonString(schema);
  }
  
  private ObjectNode convertSqlTypeToArrow(String sqlType) {
    ObjectNode arrowType = JsonUtil.createObjectNode();
    String normalizedType = sqlType.toLowerCase();
    
    if (normalizedType.equals("boolean")) {
      arrowType.put("name", "bool");
    } else if (normalizedType.equals("tinyint")) {
      arrowType.put("name", "int8");
    } else if (normalizedType.equals("smallint")) {
      arrowType.put("name", "int16");
    } else if (normalizedType.equals("int") || normalizedType.equals("integer")) {
      arrowType.put("name", "int32");
    } else if (normalizedType.equals("bigint")) {
      arrowType.put("name", "int64");
    } else if (normalizedType.equals("float")) {
      arrowType.put("name", "float");
    } else if (normalizedType.equals("double")) {
      arrowType.put("name", "double");
    } else if (normalizedType.equals("string") || normalizedType.startsWith("varchar") || normalizedType.startsWith("char")) {
      arrowType.put("name", "utf8");
    } else if (normalizedType.equals("binary")) {
      arrowType.put("name", "binary");
    } else if (normalizedType.equals("date")) {
      arrowType.put("name", "date32");
    } else if (normalizedType.equals("timestamp")) {
      arrowType.put("name", "timestamp");
      arrowType.put("unit", "MICROSECOND");
    } else if (normalizedType.startsWith("decimal")) {
      arrowType.put("name", "decimal");
      // Try to extract precision and scale from DECIMAL(p,s) format
      if (normalizedType.contains("(")) {
        String params = normalizedType.substring(normalizedType.indexOf("(") + 1, normalizedType.indexOf(")"));
        String[] parts = params.split(",");
        if (parts.length >= 1) {
          arrowType.put("precision", Integer.parseInt(parts[0].trim()));
        }
        if (parts.length >= 2) {
          arrowType.put("scale", Integer.parseInt(parts[1].trim()));
        }
      }
    } else if (normalizedType.startsWith("array")) {
      arrowType.put("name", "list");
      // For simplicity, assume string elements
      ArrayNode children = JsonUtil.MAPPER.createArrayNode();
      ObjectNode childField = JsonUtil.createObjectNode();
      childField.put("name", "item");
      ObjectNode childType = JsonUtil.createObjectNode();
      childType.put("name", "utf8");
      childField.set("type", childType);
      children.add(childField);
      arrowType.set("children", children);
    } else {
      // Default to string for unknown types
      arrowType.put("name", "utf8");
    }
    
    return arrowType;
  }
}
