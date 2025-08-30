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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/** Data models for Gravitino REST API. */
public class GravitinoModels {
  
  /** Base response wrapper for Gravitino API responses. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ResponseWrapper<T> {
    @JsonProperty("code")
    private int code;
    
    @JsonProperty("data")
    private T data;
    
    public int getCode() {
      return code;
    }
    
    public void setCode(int code) {
      this.code = code;
    }
    
    public T getData() {
      return data;
    }
    
    public void setData(T data) {
      this.data = data;
    }
  }
  
  /** Audit information for entities. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Audit {
    @JsonProperty("creator")
    private String creator;
    
    @JsonProperty("createTime")
    private String createTime;
    
    @JsonProperty("lastModifier")
    private String lastModifier;
    
    @JsonProperty("lastModifiedTime")
    private String lastModifiedTime;
    
    public String getCreator() {
      return creator;
    }
    
    public void setCreator(String creator) {
      this.creator = creator;
    }
    
    public String getCreateTime() {
      return createTime;
    }
    
    public void setCreateTime(String createTime) {
      this.createTime = createTime;
    }
    
    public String getLastModifier() {
      return lastModifier;
    }
    
    public void setLastModifier(String lastModifier) {
      this.lastModifier = lastModifier;
    }
    
    public String getLastModifiedTime() {
      return lastModifiedTime;
    }
    
    public void setLastModifiedTime(String lastModifiedTime) {
      this.lastModifiedTime = lastModifiedTime;
    }
  }
  
  /** Schema (database) model. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Schema {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("comment")
    private String comment;
    
    @JsonProperty("properties")
    private Map<String, String> properties;
    
    @JsonProperty("audit")
    private Audit audit;
    
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
    
    public Audit getAudit() {
      return audit;
    }
    
    public void setAudit(Audit audit) {
      this.audit = audit;
    }
  }
  
  /** Request to create a schema. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class CreateSchemaRequest {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("comment")
    private String comment;
    
    @JsonProperty("properties")
    private Map<String, String> properties;
    
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
  }
  
  /** Column definition for tables. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Column {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("type")
    private JsonNode type;  // Can be string or complex type object
    
    @JsonProperty("comment")
    private String comment;
    
    @JsonProperty("nullable")
    private Boolean nullable;
    
    @JsonProperty("autoIncrement")
    private Boolean autoIncrement;
    
    @JsonProperty("defaultValue")
    private JsonNode defaultValue;
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public JsonNode getType() {
      return type;
    }
    
    public void setType(JsonNode type) {
      this.type = type;
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
    
    public Boolean getAutoIncrement() {
      return autoIncrement;
    }
    
    public void setAutoIncrement(Boolean autoIncrement) {
      this.autoIncrement = autoIncrement;
    }
    
    public JsonNode getDefaultValue() {
      return defaultValue;
    }
    
    public void setDefaultValue(JsonNode defaultValue) {
      this.defaultValue = defaultValue;
    }
  }
  
  /** Table model. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Table {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("columns")
    private List<Column> columns;
    
    @JsonProperty("comment")
    private String comment;
    
    @JsonProperty("properties")
    private Map<String, String> properties;
    
    @JsonProperty("partitioning")
    private List<JsonNode> partitioning;
    
    @JsonProperty("sortOrders")
    private List<JsonNode> sortOrders;
    
    @JsonProperty("distribution")
    private JsonNode distribution;
    
    @JsonProperty("indexes")
    private List<JsonNode> indexes;
    
    @JsonProperty("audit")
    private Audit audit;
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public List<Column> getColumns() {
      return columns;
    }
    
    public void setColumns(List<Column> columns) {
      this.columns = columns;
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
    
    public List<JsonNode> getPartitioning() {
      return partitioning;
    }
    
    public void setPartitioning(List<JsonNode> partitioning) {
      this.partitioning = partitioning;
    }
    
    public List<JsonNode> getSortOrders() {
      return sortOrders;
    }
    
    public void setSortOrders(List<JsonNode> sortOrders) {
      this.sortOrders = sortOrders;
    }
    
    public JsonNode getDistribution() {
      return distribution;
    }
    
    public void setDistribution(JsonNode distribution) {
      this.distribution = distribution;
    }
    
    public List<JsonNode> getIndexes() {
      return indexes;
    }
    
    public void setIndexes(List<JsonNode> indexes) {
      this.indexes = indexes;
    }
    
    public Audit getAudit() {
      return audit;
    }
    
    public void setAudit(Audit audit) {
      this.audit = audit;
    }
  }
  
  /** Request to create a table. */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class CreateTableRequest {
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("columns")
    private List<Column> columns;
    
    @JsonProperty("comment")
    private String comment;
    
    @JsonProperty("properties")
    private Map<String, String> properties;
    
    @JsonProperty("partitioning")
    private List<JsonNode> partitioning;
    
    @JsonProperty("sortOrders")
    private List<JsonNode> sortOrders;
    
    @JsonProperty("distribution")
    private JsonNode distribution;
    
    @JsonProperty("indexes")
    private List<JsonNode> indexes;
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
    
    public List<Column> getColumns() {
      return columns;
    }
    
    public void setColumns(List<Column> columns) {
      this.columns = columns;
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
    
    public List<JsonNode> getPartitioning() {
      return partitioning;
    }
    
    public void setPartitioning(List<JsonNode> partitioning) {
      this.partitioning = partitioning;
    }
    
    public List<JsonNode> getSortOrders() {
      return sortOrders;
    }
    
    public void setSortOrders(List<JsonNode> sortOrders) {
      this.sortOrders = sortOrders;
    }
    
    public JsonNode getDistribution() {
      return distribution;
    }
    
    public void setDistribution(JsonNode distribution) {
      this.distribution = distribution;
    }
    
    public List<JsonNode> getIndexes() {
      return indexes;
    }
    
    public void setIndexes(List<JsonNode> indexes) {
      this.indexes = indexes;
    }
  }
  
  /** Entity list response for list operations. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class EntityListResponse {
    @JsonProperty("identifiers")
    private List<NameIdentifier> identifiers;
    
    public List<NameIdentifier> getIdentifiers() {
      return identifiers;
    }
    
    public void setIdentifiers(List<NameIdentifier> identifiers) {
      this.identifiers = identifiers;
    }
  }
  
  /** Name identifier for entities. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class NameIdentifier {
    @JsonProperty("namespace")
    private List<String> namespace;
    
    @JsonProperty("name")
    private String name;
    
    public List<String> getNamespace() {
      return namespace;
    }
    
    public void setNamespace(List<String> namespace) {
      this.namespace = namespace;
    }
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
  }
  
  /** Drop response. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class DropResponse {
    @JsonProperty("dropped")
    private boolean dropped;
    
    public boolean isDropped() {
      return dropped;
    }
    
    public void setDropped(boolean dropped) {
      this.dropped = dropped;
    }
  }
  
  /** Error response from Gravitino API. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ErrorResponse {
    @JsonProperty("code")
    private int code;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("stack")
    private List<String> stack;
    
    public int getCode() {
      return code;
    }
    
    public void setCode(int code) {
      this.code = code;
    }
    
    public String getType() {
      return type;
    }
    
    public void setType(String type) {
      this.type = type;
    }
    
    public String getMessage() {
      return message;
    }
    
    public void setMessage(String message) {
      this.message = message;
    }
    
    public List<String> getStack() {
      return stack;
    }
    
    public void setStack(List<String> stack) {
      this.stack = stack;
    }
  }
}