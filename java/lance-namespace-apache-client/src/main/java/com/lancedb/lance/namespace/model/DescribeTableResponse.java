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
package com.lancedb.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** DescribeTableResponse */
@JsonPropertyOrder({
  DescribeTableResponse.JSON_PROPERTY_NAME,
  DescribeTableResponse.JSON_PROPERTY_NAMESPACE,
  DescribeTableResponse.JSON_PROPERTY_LOCATION,
  DescribeTableResponse.JSON_PROPERTY_PROPERTIES,
  DescribeTableResponse.JSON_PROPERTY_SCHEMA,
  DescribeTableResponse.JSON_PROPERTY_STATS,
  DescribeTableResponse.JSON_PROPERTY_TABLE,
  DescribeTableResponse.JSON_PROPERTY_TABLE_URI,
  DescribeTableResponse.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableResponse {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nonnull private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_LOCATION = "location";
  @javax.annotation.Nonnull private String location;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  @javax.annotation.Nonnull private JsonSchema schema;

  public static final String JSON_PROPERTY_STATS = "stats";
  @javax.annotation.Nonnull private TableBasicStats stats;

  public static final String JSON_PROPERTY_TABLE = "table";
  @javax.annotation.Nonnull private String table;

  public static final String JSON_PROPERTY_TABLE_URI = "table_uri";

  @javax.annotation.Nullable
  private JsonNullable<String> tableUri = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nonnull private Long version;

  public DescribeTableResponse() {}

  public DescribeTableResponse name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(@javax.annotation.Nonnull String name) {
    this.name = name;
  }

  public DescribeTableResponse namespace(@javax.annotation.Nonnull List<String> namespace) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getNamespace() {
    return namespace;
  }

  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNamespace(@javax.annotation.Nonnull List<String> namespace) {
    this.namespace = namespace;
  }

  public DescribeTableResponse location(@javax.annotation.Nonnull String location) {

    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getLocation() {
    return location;
  }

  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLocation(@javax.annotation.Nonnull String location) {
    this.location = location;
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
   * Get properties
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

  public DescribeTableResponse schema(@javax.annotation.Nonnull JsonSchema schema) {

    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   *
   * @return schema
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public JsonSchema getSchema() {
    return schema;
  }

  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSchema(@javax.annotation.Nonnull JsonSchema schema) {
    this.schema = schema;
  }

  public DescribeTableResponse stats(@javax.annotation.Nonnull TableBasicStats stats) {

    this.stats = stats;
    return this;
  }

  /**
   * Get stats
   *
   * @return stats
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public TableBasicStats getStats() {
    return stats;
  }

  @JsonProperty(JSON_PROPERTY_STATS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStats(@javax.annotation.Nonnull TableBasicStats stats) {
    this.stats = stats;
  }

  public DescribeTableResponse table(@javax.annotation.Nonnull String table) {

    this.table = table;
    return this;
  }

  /**
   * Get table
   *
   * @return table
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getTable() {
    return table;
  }

  @JsonProperty(JSON_PROPERTY_TABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTable(@javax.annotation.Nonnull String table) {
    this.table = table;
  }

  public DescribeTableResponse tableUri(@javax.annotation.Nullable String tableUri) {
    this.tableUri = JsonNullable.<String>of(tableUri);

    return this;
  }

  /**
   * Table URI, optional
   *
   * @return tableUri
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getTableUri() {
    return tableUri.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_TABLE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getTableUri_JsonNullable() {
    return tableUri;
  }

  @JsonProperty(JSON_PROPERTY_TABLE_URI)
  public void setTableUri_JsonNullable(JsonNullable<String> tableUri) {
    this.tableUri = tableUri;
  }

  public void setTableUri(@javax.annotation.Nullable String tableUri) {
    this.tableUri = JsonNullable.<String>of(tableUri);
  }

  public DescribeTableResponse version(@javax.annotation.Nonnull Long version) {

    this.version = version;
    return this;
  }

  /**
   * Get version minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(@javax.annotation.Nonnull Long version) {
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
        && equalsNullable(this.tableUri, describeTableResponse.tableUri)
        && Objects.equals(this.version, describeTableResponse.version);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        namespace,
        location,
        properties,
        schema,
        stats,
        table,
        hashCodeNullable(tableUri),
        version);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
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

    // add `name` to the URL query string
    if (getName() != null) {
      try {
        joiner.add(
            String.format(
                "%sname%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getName()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `namespace` to the URL query string
    if (getNamespace() != null) {
      for (int i = 0; i < getNamespace().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%snamespace%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getNamespace().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `location` to the URL query string
    if (getLocation() != null) {
      try {
        joiner.add(
            String.format(
                "%slocation%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLocation()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sproperties%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getProperties().get(_key),
                  URLEncoder.encode(String.valueOf(getProperties().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `schema` to the URL query string
    if (getSchema() != null) {
      joiner.add(getSchema().toUrlQueryString(prefix + "schema" + suffix));
    }

    // add `stats` to the URL query string
    if (getStats() != null) {
      joiner.add(getStats().toUrlQueryString(prefix + "stats" + suffix));
    }

    // add `table` to the URL query string
    if (getTable() != null) {
      try {
        joiner.add(
            String.format(
                "%stable%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTable()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `table_uri` to the URL query string
    if (getTableUri() != null) {
      try {
        joiner.add(
            String.format(
                "%stable_uri%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTableUri()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sversion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVersion()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
