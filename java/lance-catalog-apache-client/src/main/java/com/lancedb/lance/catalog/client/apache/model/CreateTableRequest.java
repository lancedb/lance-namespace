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
package com.lancedb.lance.catalog.client.apache.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateTableRequest */
@JsonPropertyOrder({
  CreateTableRequest.JSON_PROPERTY_NAME,
  CreateTableRequest.JSON_PROPERTY_MODE,
  CreateTableRequest.JSON_PROPERTY_TYPE,
  CreateTableRequest.JSON_PROPERTY_LOCATION,
  CreateTableRequest.JSON_PROPERTY_SCHEMA,
  CreateTableRequest.JSON_PROPERTY_WRITER_VERSION,
  CreateTableRequest.JSON_PROPERTY_CONFIG
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  /** Gets or Sets mode */
  public enum ModeEnum {
    CREATE(String.valueOf("CREATE")),

    EXIST_OK(String.valueOf("EXIST_OK")),

    OVERWRITE(String.valueOf("OVERWRITE"));

    private String value;

    ModeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ModeEnum fromValue(String value) {
      for (ModeEnum b : ModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_MODE = "mode";
  @javax.annotation.Nullable private ModeEnum mode = ModeEnum.CREATE;

  /** Gets or Sets type */
  public enum TypeEnum {
    STORAGE_MANAGED(String.valueOf("STORAGE_MANAGED")),

    CATALOG_MANAGED(String.valueOf("CATALOG_MANAGED"));

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nullable private TypeEnum type = TypeEnum.STORAGE_MANAGED;

  public static final String JSON_PROPERTY_LOCATION = "location";
  @javax.annotation.Nullable private String location;

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  @javax.annotation.Nonnull private Schema schema;

  public static final String JSON_PROPERTY_WRITER_VERSION = "writerVersion";
  @javax.annotation.Nullable private WriterVersion writerVersion;

  public static final String JSON_PROPERTY_CONFIG = "config";
  @javax.annotation.Nullable private Map<String, String> config = new HashMap<>();

  public CreateTableRequest() {}

  public CreateTableRequest name(@javax.annotation.Nonnull String name) {

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

  public CreateTableRequest mode(@javax.annotation.Nullable ModeEnum mode) {

    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   *
   * @return mode
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public ModeEnum getMode() {
    return mode;
  }

  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMode(@javax.annotation.Nullable ModeEnum mode) {
    this.mode = mode;
  }

  public CreateTableRequest type(@javax.annotation.Nullable TypeEnum type) {

    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public TypeEnum getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(@javax.annotation.Nullable TypeEnum type) {
    this.type = type;
  }

  public CreateTableRequest location(@javax.annotation.Nullable String location) {

    this.location = location;
    return this;
  }

  /**
   * Get location
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

  public CreateTableRequest schema(@javax.annotation.Nonnull Schema schema) {

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
  public Schema getSchema() {
    return schema;
  }

  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSchema(@javax.annotation.Nonnull Schema schema) {
    this.schema = schema;
  }

  public CreateTableRequest writerVersion(@javax.annotation.Nullable WriterVersion writerVersion) {

    this.writerVersion = writerVersion;
    return this;
  }

  /**
   * Get writerVersion
   *
   * @return writerVersion
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WRITER_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public WriterVersion getWriterVersion() {
    return writerVersion;
  }

  @JsonProperty(JSON_PROPERTY_WRITER_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWriterVersion(@javax.annotation.Nullable WriterVersion writerVersion) {
    this.writerVersion = writerVersion;
  }

  public CreateTableRequest config(@javax.annotation.Nullable Map<String, String> config) {

    this.config = config;
    return this;
  }

  public CreateTableRequest putConfigItem(String key, String configItem) {
    if (this.config == null) {
      this.config = new HashMap<>();
    }
    this.config.put(key, configItem);
    return this;
  }

  /**
   * optional configurations for the table. Keys with the prefix \&quot;lance.\&quot; are reserved
   * for the Lance library. Other libraries may wish to similarly prefix their configuration keys
   * appropriately.
   *
   * @return config
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getConfig() {
    return config;
  }

  @JsonProperty(JSON_PROPERTY_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConfig(@javax.annotation.Nullable Map<String, String> config) {
    this.config = config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableRequest createTableRequest = (CreateTableRequest) o;
    return Objects.equals(this.name, createTableRequest.name)
        && Objects.equals(this.mode, createTableRequest.mode)
        && Objects.equals(this.type, createTableRequest.type)
        && Objects.equals(this.location, createTableRequest.location)
        && Objects.equals(this.schema, createTableRequest.schema)
        && Objects.equals(this.writerVersion, createTableRequest.writerVersion)
        && Objects.equals(this.config, createTableRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, mode, type, location, schema, writerVersion, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    writerVersion: ").append(toIndentedString(writerVersion)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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

    // add `mode` to the URL query string
    if (getMode() != null) {
      try {
        joiner.add(
            String.format(
                "%smode%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMode()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `type` to the URL query string
    if (getType() != null) {
      try {
        joiner.add(
            String.format(
                "%stype%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getType()), "UTF-8").replaceAll("\\+", "%20")));
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

    // add `writerVersion` to the URL query string
    if (getWriterVersion() != null) {
      joiner.add(getWriterVersion().toUrlQueryString(prefix + "writerVersion" + suffix));
    }

    // add `config` to the URL query string
    if (getConfig() != null) {
      for (String _key : getConfig().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sconfig%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getConfig().get(_key),
                  URLEncoder.encode(String.valueOf(getConfig().get(_key)), "UTF-8")
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
