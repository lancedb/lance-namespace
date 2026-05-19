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
package org.lance.namespace.model;

import org.lance.namespace.client.async.ApiClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** DescribeTableResponse */
@JsonPropertyOrder({
  DescribeTableResponse.JSON_PROPERTY_TABLE,
  DescribeTableResponse.JSON_PROPERTY_NAMESPACE,
  DescribeTableResponse.JSON_PROPERTY_VERSION,
  DescribeTableResponse.JSON_PROPERTY_LOCATION,
  DescribeTableResponse.JSON_PROPERTY_TABLE_URI,
  DescribeTableResponse.JSON_PROPERTY_SCHEMA,
  DescribeTableResponse.JSON_PROPERTY_STORAGE_OPTIONS,
  DescribeTableResponse.JSON_PROPERTY_STATS,
  DescribeTableResponse.JSON_PROPERTY_METADATA,
  DescribeTableResponse.JSON_PROPERTY_PROPERTIES,
  DescribeTableResponse.JSON_PROPERTY_MANAGED_VERSIONING,
  DescribeTableResponse.JSON_PROPERTY_IS_ONLY_DECLARED
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableResponse {
  public static final String JSON_PROPERTY_TABLE = "table";
  @javax.annotation.Nullable private String table;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nullable private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

  public static final String JSON_PROPERTY_LOCATION = "location";
  @javax.annotation.Nullable private String location;

  public static final String JSON_PROPERTY_TABLE_URI = "table_uri";
  @javax.annotation.Nullable private String tableUri;

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  @javax.annotation.Nullable private JsonArrowSchema schema;

  public static final String JSON_PROPERTY_STORAGE_OPTIONS = "storage_options";
  @javax.annotation.Nullable private Map<String, String> storageOptions = new HashMap<>();

  public static final String JSON_PROPERTY_STATS = "stats";
  @javax.annotation.Nullable private TableBasicStats stats;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nullable private Map<String, String> metadata = new HashMap<>();

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public static final String JSON_PROPERTY_MANAGED_VERSIONING = "managed_versioning";
  @javax.annotation.Nullable private Boolean managedVersioning;

  public static final String JSON_PROPERTY_IS_ONLY_DECLARED = "is_only_declared";
  @javax.annotation.Nullable private Boolean isOnlyDeclared;

  public DescribeTableResponse() {}

  public DescribeTableResponse table(@javax.annotation.Nullable String table) {
    this.table = table;
    return this;
  }

  /**
   * Table name. Only populated when &#x60;load_detailed_metadata&#x60; is true.
   *
   * @return table
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getTable() {
    return table;
  }

  @JsonProperty(JSON_PROPERTY_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTable(@javax.annotation.Nullable String table) {
    this.table = table;
  }

  public DescribeTableResponse namespace(@javax.annotation.Nullable List<String> namespace) {
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
   * The namespace identifier as a list of parts. Only populated when
   * &#x60;load_detailed_metadata&#x60; is true.
   *
   * @return namespace
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getNamespace() {
    return namespace;
  }

  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNamespace(@javax.annotation.Nullable List<String> namespace) {
    this.namespace = namespace;
  }

  public DescribeTableResponse version(@javax.annotation.Nullable Long version) {
    this.version = version;
    return this;
  }

  /**
   * Table version number. Only populated when &#x60;load_detailed_metadata&#x60; is true. minimum:
   * 0
   *
   * @return version
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(@javax.annotation.Nullable Long version) {
    this.version = version;
  }

  public DescribeTableResponse location(@javax.annotation.Nullable String location) {
    this.location = location;
    return this;
  }

  /**
   * Table storage location (e.g., S3/GCS path).
   *
   * @return location
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getLocation() {
    return location;
  }

  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocation(@javax.annotation.Nullable String location) {
    this.location = location;
  }

  public DescribeTableResponse tableUri(@javax.annotation.Nullable String tableUri) {
    this.tableUri = tableUri;
    return this;
  }

  /**
   * Table URI. Unlike location, this field must be a complete and valid URI. Only returned when
   * &#x60;with_table_uri&#x60; is true.
   *
   * @return tableUri
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TABLE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getTableUri() {
    return tableUri;
  }

  @JsonProperty(JSON_PROPERTY_TABLE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTableUri(@javax.annotation.Nullable String tableUri) {
    this.tableUri = tableUri;
  }

  public DescribeTableResponse schema(@javax.annotation.Nullable JsonArrowSchema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Table schema in JSON Arrow format. Only populated when &#x60;load_detailed_metadata&#x60; is
   * true.
   *
   * @return schema
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonArrowSchema getSchema() {
    return schema;
  }

  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSchema(@javax.annotation.Nullable JsonArrowSchema schema) {
    this.schema = schema;
  }

  public DescribeTableResponse storageOptions(
      @javax.annotation.Nullable Map<String, String> storageOptions) {
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
   * &#x60;vend_credentials&#x60; is true, this field may include vended credentials. If the vended
   * credentials are temporary, the &#x60;expires_at_millis&#x60; key should be included to indicate
   * the millisecond timestamp when the credentials expire.
   *
   * @return storageOptions
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STORAGE_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  @JsonProperty(JSON_PROPERTY_STORAGE_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStorageOptions(@javax.annotation.Nullable Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
  }

  public DescribeTableResponse stats(@javax.annotation.Nullable TableBasicStats stats) {
    this.stats = stats;
    return this;
  }

  /**
   * Table statistics. Only populated when &#x60;load_detailed_metadata&#x60; is true.
   *
   * @return stats
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STATS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public TableBasicStats getStats() {
    return stats;
  }

  @JsonProperty(JSON_PROPERTY_STATS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStats(@javax.annotation.Nullable TableBasicStats stats) {
    this.stats = stats;
  }

  public DescribeTableResponse metadata(@javax.annotation.Nullable Map<String, String> metadata) {
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
   * requires loading the table. It is only populated when &#x60;load_detailed_metadata&#x60; is
   * true.
   *
   * @return metadata
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getMetadata() {
    return metadata;
  }

  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetadata(@javax.annotation.Nullable Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public DescribeTableResponse properties(
      @javax.annotation.Nullable Map<String, String> properties) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getProperties() {
    return properties;
  }

  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(@javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
  }

  public DescribeTableResponse managedVersioning(
      @javax.annotation.Nullable Boolean managedVersioning) {
    this.managedVersioning = managedVersioning;
    return this;
  }

  /**
   * When true, the caller should use namespace table version operations (CreateTableVersion,
   * BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to
   * manage table versions instead of relying on Lance&#39;s native version management.
   *
   * @return managedVersioning
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MANAGED_VERSIONING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getManagedVersioning() {
    return managedVersioning;
  }

  @JsonProperty(JSON_PROPERTY_MANAGED_VERSIONING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setManagedVersioning(@javax.annotation.Nullable Boolean managedVersioning) {
    this.managedVersioning = managedVersioning;
  }

  public DescribeTableResponse isOnlyDeclared(@javax.annotation.Nullable Boolean isOnlyDeclared) {
    this.isOnlyDeclared = isOnlyDeclared;
    return this;
  }

  /**
   * When true, indicates that the table has been declared in the namespace but not yet created on
   * storage. This means the table exists in the namespace but has no data files on the underlying
   * storage. When false, the table has storage components (data and metadata files). When null, the
   * implementation did not check whether the table is only declared. Clients should treat an
   * omitted value as null. Implementations should populate this field when
   * &#x60;check_declared&#x60; is true or another option such as &#x60;load_detailed_metadata&#x60;
   * requires checking declared-only table state. Operations like describe_table with
   * load_detailed_metadata&#x3D;true may fail for declared-only tables.
   *
   * @return isOnlyDeclared
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IS_ONLY_DECLARED)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getIsOnlyDeclared() {
    return isOnlyDeclared;
  }

  @JsonProperty(JSON_PROPERTY_IS_ONLY_DECLARED)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIsOnlyDeclared(@javax.annotation.Nullable Boolean isOnlyDeclared) {
    this.isOnlyDeclared = isOnlyDeclared;
  }

  /** Return true if this DescribeTableResponse object is equal to o. */
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `table` to the URL query string
    if (getTable() != null) {
      joiner.add(
          String.format(
              "%stable%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTable()))));
    }

    // add `namespace` to the URL query string
    if (getNamespace() != null) {
      for (int i = 0; i < getNamespace().size(); i++) {
        joiner.add(
            String.format(
                "%snamespace%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getNamespace().get(i)))));
      }
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      joiner.add(
          String.format(
              "%sversion%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getVersion()))));
    }

    // add `location` to the URL query string
    if (getLocation() != null) {
      joiner.add(
          String.format(
              "%slocation%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getLocation()))));
    }

    // add `table_uri` to the URL query string
    if (getTableUri() != null) {
      joiner.add(
          String.format(
              "%stable_uri%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTableUri()))));
    }

    // add `schema` to the URL query string
    if (getSchema() != null) {
      joiner.add(getSchema().toUrlQueryString(prefix + "schema" + suffix));
    }

    // add `storage_options` to the URL query string
    if (getStorageOptions() != null) {
      for (String _key : getStorageOptions().keySet()) {
        joiner.add(
            String.format(
                "%sstorage_options%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getStorageOptions().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getStorageOptions().get(_key)))));
      }
    }

    // add `stats` to the URL query string
    if (getStats() != null) {
      joiner.add(getStats().toUrlQueryString(prefix + "stats" + suffix));
    }

    // add `metadata` to the URL query string
    if (getMetadata() != null) {
      for (String _key : getMetadata().keySet()) {
        joiner.add(
            String.format(
                "%smetadata%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getMetadata().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getMetadata().get(_key)))));
      }
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        joiner.add(
            String.format(
                "%sproperties%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getProperties().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getProperties().get(_key)))));
      }
    }

    // add `managed_versioning` to the URL query string
    if (getManagedVersioning() != null) {
      joiner.add(
          String.format(
              "%smanaged_versioning%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(ApiClient.valueToString(getManagedVersioning()))));
    }

    // add `is_only_declared` to the URL query string
    if (getIsOnlyDeclared() != null) {
      joiner.add(
          String.format(
              "%sis_only_declared%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getIsOnlyDeclared()))));
    }

    return joiner.toString();
  }
}
