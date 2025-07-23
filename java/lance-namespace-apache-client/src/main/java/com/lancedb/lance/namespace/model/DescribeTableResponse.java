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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** DescribeTableResponse */
@JsonPropertyOrder({
  DescribeTableResponse.JSON_PROPERTY_VERSION,
  DescribeTableResponse.JSON_PROPERTY_LOCATION,
  DescribeTableResponse.JSON_PROPERTY_SCHEMA,
  DescribeTableResponse.JSON_PROPERTY_PROPERTIES,
  DescribeTableResponse.JSON_PROPERTY_STORAGE_OPTIONS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableResponse {
  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

  public static final String JSON_PROPERTY_LOCATION = "location";
  @javax.annotation.Nonnull private String location;

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  @javax.annotation.Nonnull private JsonSchema schema;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public static final String JSON_PROPERTY_STORAGE_OPTIONS = "storage_options";
  @javax.annotation.Nullable private Map<String, String> storageOptions = new HashMap<>();

  public DescribeTableResponse() {}

  public DescribeTableResponse version(@javax.annotation.Nullable Long version) {

    this.version = version;
    return this;
  }

  /**
   * Get version minimum: 0
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
   * Get storageOptions
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableResponse describeTableResponse = (DescribeTableResponse) o;
    return Objects.equals(this.version, describeTableResponse.version)
        && Objects.equals(this.location, describeTableResponse.location)
        && Objects.equals(this.schema, describeTableResponse.schema)
        && Objects.equals(this.properties, describeTableResponse.properties)
        && Objects.equals(this.storageOptions, describeTableResponse.storageOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, location, schema, properties, storageOptions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    storageOptions: ").append(toIndentedString(storageOptions)).append("\n");
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

    // add `schema` to the URL query string
    if (getSchema() != null) {
      joiner.add(getSchema().toUrlQueryString(prefix + "schema" + suffix));
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

    // add `storage_options` to the URL query string
    if (getStorageOptions() != null) {
      for (String _key : getStorageOptions().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sstorage_options%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getStorageOptions().get(_key),
                  URLEncoder.encode(String.valueOf(getStorageOptions().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    return joiner.toString();
  }
}
