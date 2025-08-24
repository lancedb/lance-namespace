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
package com.lancedb.lance.namespace.gravitino;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.lancedb.lance.Dataset;
import com.lancedb.lance.WriteParams;
import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.*;
import com.lancedb.lance.namespace.rest.RestClient;
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.ValidationUtil;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/** Gravitino catalog namespace implementation for Lance. */
public class GravitinoNamespace implements LanceNamespace {
  private static final Logger LOG = LoggerFactory.getLogger(GravitinoNamespace.class);
  private static final String TABLE_TYPE_LANCE = "lance";
  private static final String TABLE_FORMAT_KEY = "format";
  private static final String TABLE_LOCATION_KEY = "location";
  private static final String TABLE_PROVIDER_KEY = "provider";
  
  private GravitinoNamespaceConfig config;
  private RestClient restClient;
  private BufferAllocator allocator;
  private ObjectMapper objectMapper;
  
  public GravitinoNamespace() {}
  
  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    this.config = new GravitinoNamespaceConfig(configProperties);
    this.objectMapper = new ObjectMapper();
    
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
    LOG.info("Initialized Gravitino namespace with endpoint: {} for metalake: {}, catalog: {}", 
        config.getEndpoint(), config.getMetalake(), config.getCatalog());
  }
  
  @Override
  public BufferAllocator getAllocator() {
    return allocator;
  }
  
  @Override
  public void close() throws IOException {
    if (restClient != null) {
      restClient.close();
    }
  }
  
  // ============= Namespace (Schema) Operations =============
  
  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    ValidationUtil.validateCreateNamespaceRequest(request);
    
    try {
      // Prepare Gravitino create schema request
      GravitinoModels.CreateSchemaRequest createRequest = new GravitinoModels.CreateSchemaRequest();
      createRequest.setName(request.getNamespace().getName());
      createRequest.setComment(request.getProperties().get("comment"));
      
      // Copy properties excluding comment (as it's a separate field in Gravitino)
      Map<String, String> props = new HashMap<>(request.getProperties());
      props.remove("comment");
      if (!props.isEmpty()) {
        createRequest.setProperties(props);
      }
      
      // Make REST API call
      String response = restClient.post(config.getSchemasPath(), createRequest);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.Schema> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.Schema>>() {});
      
      if (wrapper.getCode() != 0) {
        throw new LanceNamespaceException("Failed to create schema: " + response);
      }
      
      GravitinoModels.Schema schema = wrapper.getData();
      
      // Build response
      CreateNamespaceResponse result = new CreateNamespaceResponse();
      result.setNamespace(request.getNamespace());
      
      Map<String, String> resultProps = new HashMap<>();
      if (schema.getProperties() != null) {
        resultProps.putAll(schema.getProperties());
      }
      if (schema.getComment() != null) {
        resultProps.put("comment", schema.getComment());
      }
      result.setProperties(resultProps);
      
      return result;
      
    } catch (IOException e) {
      throw new LanceNamespaceException("Failed to create namespace: " + e.getMessage(), e);
    }
  }
  
  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    ValidationUtil.validateDescribeNamespaceRequest(request);
    
    try {
      String schemaPath = config.getSchemaPath(request.getNamespace().getName());
      String response = restClient.get(schemaPath);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.Schema> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.Schema>>() {});
      
      if (wrapper.getCode() != 0) {
        throw new LanceNamespaceException("Failed to describe schema: " + response);
      }
      
      GravitinoModels.Schema schema = wrapper.getData();
      
      // Build response
      DescribeNamespaceResponse result = new DescribeNamespaceResponse();
      result.setNamespace(request.getNamespace());
      
      Map<String, String> props = new HashMap<>();
      if (schema.getProperties() != null) {
        props.putAll(schema.getProperties());
      }
      if (schema.getComment() != null) {
        props.put("comment", schema.getComment());
      }
      result.setProperties(props);
      
      return result;
      
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw new LanceNamespaceException(
            String.format("Namespace '%s' does not exist", request.getNamespace().getName()));
      }
      throw new LanceNamespaceException("Failed to describe namespace: " + e.getMessage(), e);
    }
  }
  
  @Override
  public boolean namespaceExists(NamespaceExistsRequest request) {
    ValidationUtil.validateNamespaceExistsRequest(request);
    
    try {
      String schemaPath = config.getSchemaPath(request.getNamespace().getName());
      restClient.get(schemaPath);
      return true;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        return false;
      }
      throw new LanceNamespaceException("Failed to check namespace existence: " + e.getMessage(), e);
    }
  }
  
  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    ValidationUtil.validateDropNamespaceRequest(request);
    
    try {
      String schemaPath = config.getSchemaPath(request.getNamespace().getName());
      String response = restClient.delete(schemaPath);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.DropResponse> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.DropResponse>>() {});
      
      DropNamespaceResponse result = new DropNamespaceResponse();
      result.setDropped(wrapper.getData() != null && wrapper.getData().isDropped());
      
      return result;
      
    } catch (IOException e) {
      throw new LanceNamespaceException("Failed to drop namespace: " + e.getMessage(), e);
    }
  }
  
  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    try {
      String response = restClient.get(config.getSchemasPath());
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.EntityListResponse> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.EntityListResponse>>() {});
      
      if (wrapper.getCode() != 0) {
        throw new LanceNamespaceException("Failed to list schemas: " + response);
      }
      
      // Convert to namespace list
      List<ObjectIdentifier> namespaces = new ArrayList<>();
      if (wrapper.getData() != null && wrapper.getData().getIdentifiers() != null) {
        for (GravitinoModels.NameIdentifier identifier : wrapper.getData().getIdentifiers()) {
          namespaces.add(ObjectIdentifier.of(
              ObjectIdentifier.Type.NAMESPACE, identifier.getName()));
        }
      }
      
      ListNamespacesResponse result = new ListNamespacesResponse();
      result.setNamespaces(namespaces);
      
      return result;
      
    } catch (IOException e) {
      throw new LanceNamespaceException("Failed to list namespaces: " + e.getMessage(), e);
    }
  }
  
  // ============= Table Operations =============
  
  @Override
  public CreateTableResponse createTable(CreateTableRequest request) {
    ValidationUtil.validateCreateTableRequest(request);
    
    try {
      String schemaName = request.getTableIdentifier().getNamespace();
      String tableName = request.getTableIdentifier().getName();
      
      // Prepare Gravitino create table request
      GravitinoModels.CreateTableRequest createRequest = new GravitinoModels.CreateTableRequest();
      createRequest.setName(tableName);
      
      // Convert Arrow schema to Gravitino columns
      List<GravitinoModels.Column> columns = convertArrowSchemaToGravitinoColumns(request.getSchema());
      createRequest.setColumns(columns);
      
      // Set table properties - marking it as a Lance table
      Map<String, String> props = new HashMap<>();
      if (request.getProperties() != null) {
        props.putAll(request.getProperties());
      }
      props.put(TABLE_FORMAT_KEY, TABLE_TYPE_LANCE);
      props.put(TABLE_PROVIDER_KEY, TABLE_TYPE_LANCE);
      
      // Set storage location
      String location = request.getLocation();
      if (location != null && !location.isEmpty()) {
        props.put(TABLE_LOCATION_KEY, location);
      }
      
      createRequest.setProperties(props);
      
      // Make REST API call
      String tablesPath = config.getTablesPath(schemaName);
      String response = restClient.post(tablesPath, createRequest);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.Table> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.Table>>() {});
      
      if (wrapper.getCode() != 0) {
        throw new LanceNamespaceException("Failed to create table: " + response);
      }
      
      GravitinoModels.Table table = wrapper.getData();
      
      // Build response
      CreateTableResponse result = new CreateTableResponse();
      result.setTableIdentifier(request.getTableIdentifier());
      
      // Convert back to Arrow schema
      Schema resultSchema = convertGravitinoColumnsToArrowSchema(table.getColumns());
      result.setSchema(resultSchema);
      
      Map<String, String> resultProps = new HashMap<>();
      if (table.getProperties() != null) {
        resultProps.putAll(table.getProperties());
      }
      result.setProperties(resultProps);
      
      // Extract location from properties
      if (resultProps.containsKey(TABLE_LOCATION_KEY)) {
        result.setLocation(resultProps.get(TABLE_LOCATION_KEY));
      }
      
      return result;
      
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("409")) {
        throw new LanceNamespaceException(
            String.format("Table '%s' already exists", request.getTableIdentifier()));
      }
      throw new LanceNamespaceException("Failed to create table: " + e.getMessage(), e);
    }
  }
  
  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ValidationUtil.validateDescribeTableRequest(request);
    
    try {
      String schemaName = request.getTableIdentifier().getNamespace();
      String tableName = request.getTableIdentifier().getName();
      String tablePath = config.getTablePath(schemaName, tableName);
      
      String response = restClient.get(tablePath);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.Table> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.Table>>() {});
      
      if (wrapper.getCode() != 0) {
        throw new LanceNamespaceException("Failed to describe table: " + response);
      }
      
      GravitinoModels.Table table = wrapper.getData();
      
      // Build response
      DescribeTableResponse result = new DescribeTableResponse();
      result.setTableIdentifier(request.getTableIdentifier());
      
      // Convert to Arrow schema
      Schema schema = convertGravitinoColumnsToArrowSchema(table.getColumns());
      result.setSchema(schema);
      
      Map<String, String> props = new HashMap<>();
      if (table.getProperties() != null) {
        props.putAll(table.getProperties());
      }
      result.setProperties(props);
      
      // Extract location from properties
      if (props.containsKey(TABLE_LOCATION_KEY)) {
        result.setLocation(props.get(TABLE_LOCATION_KEY));
      }
      
      return result;
      
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        throw new LanceNamespaceException(
            String.format("Table '%s' does not exist", request.getTableIdentifier()));
      }
      throw new LanceNamespaceException("Failed to describe table: " + e.getMessage(), e);
    }
  }
  
  @Override
  public boolean tableExists(TableExistsRequest request) {
    ValidationUtil.validateTableExistsRequest(request);
    
    try {
      String schemaName = request.getTableIdentifier().getNamespace();
      String tableName = request.getTableIdentifier().getName();
      String tablePath = config.getTablePath(schemaName, tableName);
      
      restClient.get(tablePath);
      return true;
    } catch (IOException e) {
      if (e.getMessage() != null && e.getMessage().contains("404")) {
        return false;
      }
      throw new LanceNamespaceException("Failed to check table existence: " + e.getMessage(), e);
    }
  }
  
  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ValidationUtil.validateDropTableRequest(request);
    
    try {
      String schemaName = request.getTableIdentifier().getNamespace();
      String tableName = request.getTableIdentifier().getName();
      String tablePath = config.getTablePath(schemaName, tableName);
      
      // Add purge parameter if requested
      String fullPath = tablePath;
      if (request.isPurge()) {
        fullPath += "?purge=true";
      }
      
      String response = restClient.delete(fullPath);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.DropResponse> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.DropResponse>>() {});
      
      DropTableResponse result = new DropTableResponse();
      result.setDropped(wrapper.getData() != null && wrapper.getData().isDropped());
      
      return result;
      
    } catch (IOException e) {
      throw new LanceNamespaceException("Failed to drop table: " + e.getMessage(), e);
    }
  }
  
  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    ValidationUtil.validateListTablesRequest(request);
    
    try {
      String schemaName = request.getNamespace().getName();
      String tablesPath = config.getTablesPath(schemaName);
      
      String response = restClient.get(tablesPath);
      
      // Parse response
      GravitinoModels.ResponseWrapper<GravitinoModels.EntityListResponse> wrapper =
          objectMapper.readValue(response,
              new TypeReference<GravitinoModels.ResponseWrapper<GravitinoModels.EntityListResponse>>() {});
      
      if (wrapper.getCode() != 0) {
        throw new LanceNamespaceException("Failed to list tables: " + response);
      }
      
      // Convert to table list - filter for Lance tables
      List<ObjectIdentifier> tables = new ArrayList<>();
      if (wrapper.getData() != null && wrapper.getData().getIdentifiers() != null) {
        for (GravitinoModels.NameIdentifier identifier : wrapper.getData().getIdentifiers()) {
          // We might want to filter by checking table properties,
          // but for now include all tables
          tables.add(ObjectIdentifier.of(
              ObjectIdentifier.Type.TABLE, schemaName, identifier.getName()));
        }
      }
      
      ListTablesResponse result = new ListTablesResponse();
      result.setTables(tables);
      
      return result;
      
    } catch (IOException e) {
      throw new LanceNamespaceException("Failed to list tables: " + e.getMessage(), e);
    }
  }
  
  @Override
  public Dataset openTable(String tablePath, Map<String, String> options) {
    // This would open the actual Lance dataset from the storage location
    // For now, throw unsupported as it requires Lance Java bindings
    throw new UnsupportedOperationException(
        "Opening Lance datasets directly is not yet implemented");
  }
  
  @Override
  public Dataset createTable(String tablePath, Schema schema, WriteParams params,
                             Map<String, String> options) {
    // This would create the actual Lance dataset
    // For now, throw unsupported as it requires Lance Java bindings
    throw new UnsupportedOperationException(
        "Creating Lance datasets directly is not yet implemented");
  }
  
  // ============= Helper Methods =============
  
  private List<GravitinoModels.Column> convertArrowSchemaToGravitinoColumns(Schema arrowSchema) {
    List<GravitinoModels.Column> columns = new ArrayList<>();
    
    for (Field field : arrowSchema.getFields()) {
      GravitinoModels.Column column = new GravitinoModels.Column();
      column.setName(field.getName());
      column.setNullable(field.isNullable());
      
      // Convert Arrow type to Gravitino type string
      String typeString = arrowTypeToGravitinoType(field.getType());
      column.setType(new TextNode(typeString));
      
      // Add metadata as comment if present
      if (field.getMetadata() != null && field.getMetadata().containsKey("comment")) {
        column.setComment(field.getMetadata().get("comment"));
      }
      
      columns.add(column);
    }
    
    return columns;
  }
  
  private Schema convertGravitinoColumnsToArrowSchema(List<GravitinoModels.Column> columns) {
    List<Field> fields = new ArrayList<>();
    
    for (GravitinoModels.Column column : columns) {
      // Parse Gravitino type to Arrow type
      String typeString = column.getType().isTextual() ? 
          column.getType().asText() : column.getType().toString();
      
      Field field = gravitinoTypeToArrowField(
          column.getName(), typeString, column.getNullable() != null ? column.getNullable() : true);
      
      // Add comment as metadata if present
      if (column.getComment() != null) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("comment", column.getComment());
        field = field.addMetadata(metadata);
      }
      
      fields.add(field);
    }
    
    return new Schema(fields);
  }
  
  /**
   * Convert Arrow type to Gravitino type string.
   */
  private String arrowTypeToGravitinoType(org.apache.arrow.vector.types.pojo.ArrowType arrowType) {
    if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Bool) {
      return "boolean";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Int) {
      org.apache.arrow.vector.types.pojo.ArrowType.Int intType = 
          (org.apache.arrow.vector.types.pojo.ArrowType.Int) arrowType;
      int bitWidth = intType.getBitWidth();
      boolean signed = intType.getIsSigned();
      if (bitWidth == 8) {
        return signed ? "byte" : "byte";
      } else if (bitWidth == 16) {
        return signed ? "short" : "short";
      } else if (bitWidth == 32) {
        return signed ? "integer" : "integer";
      } else if (bitWidth == 64) {
        return signed ? "long" : "long";
      }
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.FloatingPoint) {
      org.apache.arrow.vector.types.pojo.ArrowType.FloatingPoint floatType = 
          (org.apache.arrow.vector.types.pojo.ArrowType.FloatingPoint) arrowType;
      if (floatType.getPrecision() == org.apache.arrow.vector.types.FloatingPointPrecision.SINGLE) {
        return "float";
      } else if (floatType.getPrecision() == org.apache.arrow.vector.types.FloatingPointPrecision.DOUBLE) {
        return "double";
      }
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Utf8) {
      return "string";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Binary) {
      return "binary";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeBinary) {
      org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeBinary fixedBinary = 
          (org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeBinary) arrowType;
      return "fixed(" + fixedBinary.getByteWidth() + ")";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Date) {
      return "date";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Timestamp) {
      return "timestamp";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Decimal) {
      org.apache.arrow.vector.types.pojo.ArrowType.Decimal decimal = 
          (org.apache.arrow.vector.types.pojo.ArrowType.Decimal) arrowType;
      return "decimal(" + decimal.getPrecision() + "," + decimal.getScale() + ")";
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.List) {
      return "array<string>"; // Simplified - would need to recurse for element type
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Struct) {
      return "struct<>"; // Simplified - would need to build struct field definitions
    } else if (arrowType instanceof org.apache.arrow.vector.types.pojo.ArrowType.Map) {
      return "map<string,string>"; // Simplified - would need key/value types
    }
    
    // Default fallback
    return "string";
  }
  
  /**
   * Convert Gravitino type string to Arrow field.
   */
  private Field gravitinoTypeToArrowField(String name, String typeString, boolean nullable) {
    org.apache.arrow.vector.types.pojo.ArrowType arrowType;
    
    // Parse Gravitino type string
    String lowerType = typeString.toLowerCase();
    
    if (lowerType.equals("boolean") || lowerType.equals("bool")) {
      arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Bool.INSTANCE;
    } else if (lowerType.equals("byte") || lowerType.equals("tinyint")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Int(8, true);
    } else if (lowerType.equals("short") || lowerType.equals("smallint")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Int(16, true);
    } else if (lowerType.equals("integer") || lowerType.equals("int")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Int(32, true);
    } else if (lowerType.equals("long") || lowerType.equals("bigint")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Int(64, true);
    } else if (lowerType.equals("float")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.FloatingPoint(
          org.apache.arrow.vector.types.FloatingPointPrecision.SINGLE);
    } else if (lowerType.equals("double")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.FloatingPoint(
          org.apache.arrow.vector.types.FloatingPointPrecision.DOUBLE);
    } else if (lowerType.equals("string") || lowerType.startsWith("varchar") || lowerType.startsWith("char")) {
      arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Utf8.INSTANCE;
    } else if (lowerType.equals("binary") || lowerType.equals("varbinary")) {
      arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Binary.INSTANCE;
    } else if (lowerType.startsWith("fixed(") || lowerType.startsWith("fixedbinary(")) {
      // Extract size from fixed(n) or fixedbinary(n)
      int start = lowerType.indexOf('(');
      int end = lowerType.indexOf(')');
      if (start > 0 && end > start) {
        try {
          int size = Integer.parseInt(lowerType.substring(start + 1, end));
          arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.FixedSizeBinary(size);
        } catch (NumberFormatException e) {
          arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Binary.INSTANCE;
        }
      } else {
        arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Binary.INSTANCE;
      }
    } else if (lowerType.equals("date")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Date(
          org.apache.arrow.vector.types.DateUnit.DAY);
    } else if (lowerType.equals("timestamp") || lowerType.startsWith("timestamp_")) {
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Timestamp(
          org.apache.arrow.vector.types.TimeUnit.MICROSECOND, null);
    } else if (lowerType.startsWith("decimal")) {
      // Parse decimal(p,s)
      int precision = 38;
      int scale = 18;
      int start = lowerType.indexOf('(');
      int end = lowerType.indexOf(')');
      if (start > 0 && end > start) {
        String params = lowerType.substring(start + 1, end);
        String[] parts = params.split(",");
        try {
          if (parts.length >= 1) {
            precision = Integer.parseInt(parts[0].trim());
          }
          if (parts.length >= 2) {
            scale = Integer.parseInt(parts[1].trim());
          }
        } catch (NumberFormatException e) {
          // Use defaults
        }
      }
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Decimal(precision, scale, 128);
    } else if (lowerType.startsWith("array<") || lowerType.startsWith("list<")) {
      // Simplified list type - would need recursive parsing for element type
      arrowType = org.apache.arrow.vector.types.pojo.ArrowType.List.INSTANCE;
    } else if (lowerType.startsWith("struct<")) {
      // Simplified struct type - would need field parsing
      arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Struct.INSTANCE;
    } else if (lowerType.startsWith("map<")) {
      // Simplified map type - would need key/value type parsing
      arrowType = new org.apache.arrow.vector.types.pojo.ArrowType.Map(false);
    } else {
      // Default to string for unknown types
      arrowType = org.apache.arrow.vector.types.pojo.ArrowType.Utf8.INSTANCE;
    }
    
    return Field.nullable(name, arrowType);
  }
}