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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** DescribeTableResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableResponse {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private String location;

  @Valid private Map<String, String> properties = new HashMap<>();

  private JsonSchema schema;

  private TableBasicStats stats;

  private String table;

  private String tableUri = null;

  private Long version;

  public DescribeTableResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public DescribeTableResponse(
      String name,
      List<String> namespace,
      String location,
      JsonSchema schema,
      TableBasicStats stats,
      String table,
      Long version) {
    this.name = name;
    this.namespace = namespace;
    this.location = location;
    this.schema = schema;
    this.stats = stats;
    this.table = table;
    this.version = version;
  }

  public DescribeTableResponse name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @NotNull
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DescribeTableResponse namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public DescribeTableResponse addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * Get namespace
   *
   * @return namespace
   */
  @NotNull
  @Schema(name = "namespace", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
  }

  public DescribeTableResponse location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location
   */
  @NotNull
  @Schema(name = "location", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public DescribeTableResponse properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public DescribeTableResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   *
   * @return properties
   */
  @Schema(name = "properties", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public DescribeTableResponse schema(JsonSchema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   *
   * @return schema
   */
  @NotNull
  @Valid
  @Schema(name = "schema", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("schema")
  public JsonSchema getSchema() {
    return schema;
  }

  public void setSchema(JsonSchema schema) {
    this.schema = schema;
  }

  public DescribeTableResponse stats(TableBasicStats stats) {
    this.stats = stats;
    return this;
  }

  /**
   * Get stats
   *
   * @return stats
   */
  @NotNull
  @Valid
  @Schema(name = "stats", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stats")
  public TableBasicStats getStats() {
    return stats;
  }

  public void setStats(TableBasicStats stats) {
    this.stats = stats;
  }

  public DescribeTableResponse table(String table) {
    this.table = table;
    return this;
  }

  /**
   * Get table
   *
   * @return table
   */
  @NotNull
  @Schema(name = "table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("table")
  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public DescribeTableResponse tableUri(String tableUri) {
    this.tableUri = tableUri;
    return this;
  }

  /**
   * Table URI, optional
   *
   * @return tableUri
   */
  @Schema(
      name = "table_uri",
      description = "Table URI, optional",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("table_uri")
  public String getTableUri() {
    return tableUri;
  }

  public void setTableUri(String tableUri) {
    this.tableUri = tableUri;
  }

  public DescribeTableResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Get version minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(name = "version", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableResponse describeTableResponse = (DescribeTableResponse) o;
    return Objects.equals(this.name, describeTableResponse.name)
        && Objects.equals(this.namespace, describeTableResponse.namespace)
        && Objects.equals(this.location, describeTableResponse.location)
        && Objects.equals(this.properties, describeTableResponse.properties)
        && Objects.equals(this.schema, describeTableResponse.schema)
        && Objects.equals(this.stats, describeTableResponse.stats)
        && Objects.equals(this.table, describeTableResponse.table)
        && Objects.equals(this.tableUri, describeTableResponse.tableUri)
        && Objects.equals(this.version, describeTableResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name, namespace, location, properties, schema, stats, table, tableUri, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableResponse {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    stats: ").append(toIndentedString(stats)).append("\n");
    sb.append("    table: ").append(toIndentedString(table)).append("\n");
    sb.append("    tableUri: ").append(toIndentedString(tableUri)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
