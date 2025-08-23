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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/** Unity Catalog API model classes. */
public class UnityModels {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CatalogInfo {
    private String name;
    private String comment;
    private Map<String, String> properties;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }

    public Long getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
      this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
      this.updatedAt = updatedAt;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SchemaInfo {
    private String name;

    @JsonProperty("catalog_name")
    private String catalogName;

    private String comment;
    private Map<String, String> properties;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCatalogName() {
      return catalogName;
    }

    public void setCatalogName(String catalogName) {
      this.catalogName = catalogName;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }

    public String getFullName() {
      return fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
    }

    public Long getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
      this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
      this.updatedAt = updatedAt;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TableInfo {
    private String name;

    @JsonProperty("catalog_name")
    private String catalogName;

    @JsonProperty("schema_name")
    private String schemaName;

    @JsonProperty("table_type")
    private String tableType;

    @JsonProperty("data_source_format")
    private String dataSourceFormat;

    private List<ColumnInfo> columns;

    @JsonProperty("storage_location")
    private String storageLocation;

    private String comment;
    private Map<String, String> properties;

    @JsonProperty("table_id")
    private String tableId;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("updated_at")
    private Long updatedAt;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCatalogName() {
      return catalogName;
    }

    public void setCatalogName(String catalogName) {
      this.catalogName = catalogName;
    }

    public String getSchemaName() {
      return schemaName;
    }

    public void setSchemaName(String schemaName) {
      this.schemaName = schemaName;
    }

    public String getTableType() {
      return tableType;
    }

    public void setTableType(String tableType) {
      this.tableType = tableType;
    }

    public String getDataSourceFormat() {
      return dataSourceFormat;
    }

    public void setDataSourceFormat(String dataSourceFormat) {
      this.dataSourceFormat = dataSourceFormat;
    }

    public List<ColumnInfo> getColumns() {
      return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
      this.columns = columns;
    }

    public String getStorageLocation() {
      return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
      this.storageLocation = storageLocation;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }

    public String getTableId() {
      return tableId;
    }

    public void setTableId(String tableId) {
      this.tableId = tableId;
    }

    public Long getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
      this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
      this.updatedAt = updatedAt;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ColumnInfo {
    private String name;

    @JsonProperty("type_text")
    private String typeText;

    @JsonProperty("type_json")
    private String typeJson;

    @JsonProperty("type_name")
    private String typeName;

    @JsonProperty("type_precision")
    private Integer typePrecision;

    @JsonProperty("type_scale")
    private Integer typeScale;

    @JsonProperty("type_interval_type")
    private String typeIntervalType;

    private Integer position;
    private String comment;
    private Boolean nullable;

    @JsonProperty("partition_index")
    private Integer partitionIndex;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getTypeText() {
      return typeText;
    }

    public void setTypeText(String typeText) {
      this.typeText = typeText;
    }

    public String getTypeJson() {
      return typeJson;
    }

    public void setTypeJson(String typeJson) {
      this.typeJson = typeJson;
    }

    public String getTypeName() {
      return typeName;
    }

    public void setTypeName(String typeName) {
      this.typeName = typeName;
    }

    public Integer getTypePrecision() {
      return typePrecision;
    }

    public void setTypePrecision(Integer typePrecision) {
      this.typePrecision = typePrecision;
    }

    public Integer getTypeScale() {
      return typeScale;
    }

    public void setTypeScale(Integer typeScale) {
      this.typeScale = typeScale;
    }

    public String getTypeIntervalType() {
      return typeIntervalType;
    }

    public void setTypeIntervalType(String typeIntervalType) {
      this.typeIntervalType = typeIntervalType;
    }

    public Integer getPosition() {
      return position;
    }

    public void setPosition(Integer position) {
      this.position = position;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public Boolean getNullable() {
      return nullable;
    }

    public void setNullable(Boolean nullable) {
      this.nullable = nullable;
    }

    public Integer getPartitionIndex() {
      return partitionIndex;
    }

    public void setPartitionIndex(Integer partitionIndex) {
      this.partitionIndex = partitionIndex;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CreateTable {
    private String name;

    @JsonProperty("catalog_name")
    private String catalogName;

    @JsonProperty("schema_name")
    private String schemaName;

    @JsonProperty("table_type")
    private String tableType;

    @JsonProperty("data_source_format")
    private String dataSourceFormat;

    private List<ColumnInfo> columns;

    @JsonProperty("storage_location")
    private String storageLocation;

    private String comment;
    private Map<String, String> properties;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCatalogName() {
      return catalogName;
    }

    public void setCatalogName(String catalogName) {
      this.catalogName = catalogName;
    }

    public String getSchemaName() {
      return schemaName;
    }

    public void setSchemaName(String schemaName) {
      this.schemaName = schemaName;
    }

    public String getTableType() {
      return tableType;
    }

    public void setTableType(String tableType) {
      this.tableType = tableType;
    }

    public String getDataSourceFormat() {
      return dataSourceFormat;
    }

    public void setDataSourceFormat(String dataSourceFormat) {
      this.dataSourceFormat = dataSourceFormat;
    }

    public List<ColumnInfo> getColumns() {
      return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
      this.columns = columns;
    }

    public String getStorageLocation() {
      return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
      this.storageLocation = storageLocation;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CreateSchema {
    private String name;

    @JsonProperty("catalog_name")
    private String catalogName;

    private String comment;
    private Map<String, String> properties;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getCatalogName() {
      return catalogName;
    }

    public void setCatalogName(String catalogName) {
      this.catalogName = catalogName;
    }

    public String getComment() {
      return comment;
    }

    public void setComment(String comment) {
      this.comment = comment;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ListSchemasResponse {
    private List<SchemaInfo> schemas;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    public List<SchemaInfo> getSchemas() {
      return schemas;
    }

    public void setSchemas(List<SchemaInfo> schemas) {
      this.schemas = schemas;
    }

    public String getNextPageToken() {
      return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
      this.nextPageToken = nextPageToken;
    }
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ListTablesResponse {
    private List<TableInfo> tables;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    public List<TableInfo> getTables() {
      return tables;
    }

    public void setTables(List<TableInfo> tables) {
      this.tables = tables;
    }

    public String getNextPageToken() {
      return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
      this.nextPageToken = nextPageToken;
    }
  }
}
