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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

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

  private String table;

  @Valid private List<String> namespace = new ArrayList<>();

  private Long version;

  private String location;

  private String tableUri;

  private JsonArrowSchema schema;

  @Valid private Map<String, String> storageOptions = new HashMap<>();

  private TableBasicStats stats;

  public DescribeTableResponse table(String table) {
    this.table = table;
    return this;
  }

  /**
   * Table name
   *
   * @return table
   */
  @Schema(
      name = "table",
      description = "Table name",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("table")
  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
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
   * The namespace identifier as a list of parts
   *
   * @return namespace
   */
  @Schema(
      name = "namespace",
      description = "The namespace identifier as a list of parts",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
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
  @Min(0L)
  @Schema(name = "version", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public DescribeTableResponse location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Table storage location (e.g., S3/GCS path)
   *
   * @return location
   */
  @Schema(
      name = "location",
      description = "Table storage location (e.g., S3/GCS path)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public DescribeTableResponse tableUri(String tableUri) {
    this.tableUri = tableUri;
    return this;
  }

  /**
   * Table URI. Unlike location, this field must be a complete and valid URI
   *
   * @return tableUri
   */
  @Schema(
      name = "table_uri",
      description = "Table URI. Unlike location, this field must be a complete and valid URI ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("table_uri")
  public String getTableUri() {
    return tableUri;
  }

  public void setTableUri(String tableUri) {
    this.tableUri = tableUri;
  }

  public DescribeTableResponse schema(JsonArrowSchema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   *
   * @return schema
   */
  @Valid
  @Schema(name = "schema", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("schema")
  public JsonArrowSchema getSchema() {
    return schema;
  }

  public void setSchema(JsonArrowSchema schema) {
    this.schema = schema;
  }

  public DescribeTableResponse storageOptions(Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
    return this;
  }

  public DescribeTableResponse putStorageOptionsItem(String key, String storageOptionsItem) {
    if (this.storageOptions == null) {
      this.storageOptions = new HashMap<>();
    }
    this.storageOptions.put(key, storageOptionsItem);
    return this;
  }

  /**
   * Configuration options to be used to access storage. The available options depend on the type of
   * storage in use. These will be passed directly to Lance to initialize storage access.
   *
   * @return storageOptions
   */
  @Schema(
      name = "storage_options",
      description =
          "Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("storage_options")
  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  public void setStorageOptions(Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
  }

  public DescribeTableResponse stats(TableBasicStats stats) {
    this.stats = stats;
    return this;
  }

  /**
   * Table statistics
   *
   * @return stats
   */
  @Valid
  @Schema(
      name = "stats",
      description = "Table statistics",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stats")
  public TableBasicStats getStats() {
    return stats;
  }

  public void setStats(TableBasicStats stats) {
    this.stats = stats;
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
    return Objects.equals(this.table, describeTableResponse.table)
        && Objects.equals(this.namespace, describeTableResponse.namespace)
        && Objects.equals(this.version, describeTableResponse.version)
        && Objects.equals(this.location, describeTableResponse.location)
        && Objects.equals(this.tableUri, describeTableResponse.tableUri)
        && Objects.equals(this.schema, describeTableResponse.schema)
        && Objects.equals(this.storageOptions, describeTableResponse.storageOptions)
        && Objects.equals(this.stats, describeTableResponse.stats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        table, namespace, version, location, tableUri, schema, storageOptions, stats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableResponse {\n");
    sb.append("    table: ").append(toIndentedString(table)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    tableUri: ").append(toIndentedString(tableUri)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    storageOptions: ").append(toIndentedString(storageOptions)).append("\n");
    sb.append("    stats: ").append(toIndentedString(stats)).append("\n");
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
