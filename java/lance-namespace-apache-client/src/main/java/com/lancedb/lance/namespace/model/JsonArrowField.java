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

/** JSON representation of an Apache Arrow field. */
@JsonPropertyOrder({
  JsonArrowField.JSON_PROPERTY_METADATA,
  JsonArrowField.JSON_PROPERTY_NAME,
  JsonArrowField.JSON_PROPERTY_NULLABLE,
  JsonArrowField.JSON_PROPERTY_TYPE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class JsonArrowField {
  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nullable private Map<String, String> metadata = new HashMap<>();

  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NULLABLE = "nullable";
  @javax.annotation.Nonnull private Boolean nullable;

  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nonnull private JsonArrowDataType type;

  public JsonArrowField() {}

  public JsonArrowField metadata(@javax.annotation.Nullable Map<String, String> metadata) {

    this.metadata = metadata;
    return this;
  }

  public JsonArrowField putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Get metadata
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

  public JsonArrowField name(@javax.annotation.Nonnull String name) {

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

  public JsonArrowField nullable(@javax.annotation.Nonnull Boolean nullable) {

    this.nullable = nullable;
    return this;
  }

  /**
   * Get nullable
   *
   * @return nullable
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Boolean getNullable() {
    return nullable;
  }

  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNullable(@javax.annotation.Nonnull Boolean nullable) {
    this.nullable = nullable;
  }

  public JsonArrowField type(@javax.annotation.Nonnull JsonArrowDataType type) {

    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public JsonArrowDataType getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setType(@javax.annotation.Nonnull JsonArrowDataType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonArrowField jsonArrowField = (JsonArrowField) o;
    return Objects.equals(this.metadata, jsonArrowField.metadata)
        && Objects.equals(this.name, jsonArrowField.name)
        && Objects.equals(this.nullable, jsonArrowField.nullable)
        && Objects.equals(this.type, jsonArrowField.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, name, nullable, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonArrowField {\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

    // add `metadata` to the URL query string
    if (getMetadata() != null) {
      for (String _key : getMetadata().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%smetadata%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getMetadata().get(_key),
                  URLEncoder.encode(String.valueOf(getMetadata().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

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

    // add `nullable` to the URL query string
    if (getNullable() != null) {
      try {
        joiner.add(
            String.format(
                "%snullable%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNullable()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `type` to the URL query string
    if (getType() != null) {
      joiner.add(getType().toUrlQueryString(prefix + "type" + suffix));
    }

    return joiner.toString();
  }
}
