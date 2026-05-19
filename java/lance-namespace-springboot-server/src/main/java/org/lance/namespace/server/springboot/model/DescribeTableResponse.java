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

  @Valid private Map<String, String> metadata = new HashMap<>();

  @Valid private Map<String, String> properties = new HashMap<>();

  private Boolean managedVersioning;

  private Boolean isOnlyDeclared;

  public DescribeTableResponse table(String table) {
    this.table = table;
    return this;
  }

  /**
   * Table name. Only populated when `load_detailed_metadata` is true.
   *
   * @return table
   */
  @Schema(
      name = "table",
      description = "Table name. Only populated when `load_detailed_metadata` is true. ",
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
   * The namespace identifier as a list of parts. Only populated when `load_detailed_metadata` is
   * true.
   *
   * @return namespace
   */
  @Schema(
      name = "namespace",
      description =
          "The namespace identifier as a list of parts. Only populated when `load_detailed_metadata` is true. ",
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
   * Table version number. Only populated when `load_detailed_metadata` is true. minimum: 0
   *
   * @return version
   */
  @Min(0L)
  @Schema(
      name = "version",
      description = "Table version number. Only populated when `load_detailed_metadata` is true. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
   * Table storage location (e.g., S3/GCS path).
   *
   * @return location
   */
  @Schema(
      name = "location",
      description = "Table storage location (e.g., S3/GCS path). ",
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
   * Table URI. Unlike location, this field must be a complete and valid URI. Only returned when
   * `with_table_uri` is true.
   *
   * @return tableUri
   */
  @Schema(
      name = "table_uri",
      description =
          "Table URI. Unlike location, this field must be a complete and valid URI. Only returned when `with_table_uri` is true. ",
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
   * Table schema in JSON Arrow format. Only populated when `load_detailed_metadata` is true.
   *
   * @return schema
   */
  @Valid
  @Schema(
      name = "schema",
      description =
          "Table schema in JSON Arrow format. Only populated when `load_detailed_metadata` is true. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
   * storage in use. These will be passed directly to Lance to initialize storage access. When
   * `vend_credentials` is true, this field may include vended credentials. If the vended
   * credentials are temporary, the `expires_at_millis` key should be included to indicate the
   * millisecond timestamp when the credentials expire.
   *
   * @return storageOptions
   */
  @Schema(
      name = "storage_options",
      description =
          "Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access. When `vend_credentials` is true, this field may include vended credentials. If the vended credentials are temporary, the `expires_at_millis` key should be included to indicate the millisecond timestamp when the credentials expire. ",
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
   * Table statistics. Only populated when `load_detailed_metadata` is true.
   *
   * @return stats
   */
  @Valid
  @Schema(
      name = "stats",
      description = "Table statistics. Only populated when `load_detailed_metadata` is true. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stats")
  public TableBasicStats getStats() {
    return stats;
  }

  public void setStats(TableBasicStats stats) {
    this.stats = stats;
  }

  public DescribeTableResponse metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public DescribeTableResponse putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Optional table metadata as key-value pairs. This records the information of the table and
   * requires loading the table. It is only populated when `load_detailed_metadata` is true.
   *
   * @return metadata
   */
  @Schema(
      name = "metadata",
      description =
          "Optional table metadata as key-value pairs. This records the information of the table and requires loading the table. It is only populated when `load_detailed_metadata` is true. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
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
   * Properties stored on the table, if supported by the server. This records the information
   * managed by the namespace. If the server does not support table properties, it should return
   * null for this field. If table properties are supported, but none are set, it should return an
   * empty object.
   *
   * @return properties
   */
  @Schema(
      name = "properties",
      example = "{owner=Ralph, created_at=1452120468}",
      description =
          "Properties stored on the table, if supported by the server. This records the information managed by the namespace. If the server does not support table properties, it should return null for this field. If table properties are supported, but none are set, it should return an empty object.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public DescribeTableResponse managedVersioning(Boolean managedVersioning) {
    this.managedVersioning = managedVersioning;
    return this;
  }

  /**
   * When true, the caller should use namespace table version operations (CreateTableVersion,
   * BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to
   * manage table versions instead of relying on Lance's native version management.
   *
   * @return managedVersioning
   */
  @Schema(
      name = "managed_versioning",
      description =
          "When true, the caller should use namespace table version operations (CreateTableVersion, BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to manage table versions instead of relying on Lance's native version management. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("managed_versioning")
  public Boolean getManagedVersioning() {
    return managedVersioning;
  }

  public void setManagedVersioning(Boolean managedVersioning) {
    this.managedVersioning = managedVersioning;
  }

  public DescribeTableResponse isOnlyDeclared(Boolean isOnlyDeclared) {
    this.isOnlyDeclared = isOnlyDeclared;
    return this;
  }

  /**
   * When true, indicates that the table has been declared in the namespace but not yet created on
   * storage. This means the table exists in the namespace but has no data files on the underlying
   * storage. When false, the table has storage components (data and metadata files). When null, the
   * implementation did not check whether the table is only declared. Clients should treat an
   * omitted value as null. Implementations should populate this field when `check_declared` is true
   * or another option such as `load_detailed_metadata` requires checking declared-only table state.
   * Operations like describe_table with load_detailed_metadata=true may fail for declared-only
   * tables.
   *
   * @return isOnlyDeclared
   */
  @Schema(
      name = "is_only_declared",
      description =
          "When true, indicates that the table has been declared in the namespace but not yet created on storage. This means the table exists in the namespace but has no data files on the underlying storage. When false, the table has storage components (data and metadata files). When null, the implementation did not check whether the table is only declared. Clients should treat an omitted value as null. Implementations should populate this field when `check_declared` is true or another option such as `load_detailed_metadata` requires checking declared-only table state. Operations like describe_table with load_detailed_metadata=true may fail for declared-only tables. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("is_only_declared")
  public Boolean getIsOnlyDeclared() {
    return isOnlyDeclared;
  }

  public void setIsOnlyDeclared(Boolean isOnlyDeclared) {
    this.isOnlyDeclared = isOnlyDeclared;
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
        && Objects.equals(this.stats, describeTableResponse.stats)
        && Objects.equals(this.metadata, describeTableResponse.metadata)
        && Objects.equals(this.properties, describeTableResponse.properties)
        && Objects.equals(this.managedVersioning, describeTableResponse.managedVersioning)
        && Objects.equals(this.isOnlyDeclared, describeTableResponse.isOnlyDeclared);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        table,
        namespace,
        version,
        location,
        tableUri,
        schema,
        storageOptions,
        stats,
        metadata,
        properties,
        managedVersioning,
        isOnlyDeclared);
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
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    managedVersioning: ").append(toIndentedString(managedVersioning)).append("\n");
    sb.append("    isOnlyDeclared: ").append(toIndentedString(isOnlyDeclared)).append("\n");
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
