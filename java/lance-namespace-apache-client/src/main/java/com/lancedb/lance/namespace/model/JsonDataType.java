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
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/** JSON representation of an Apache Arrow [DataType]. */
@JsonPropertyOrder({
  JsonDataType.JSON_PROPERTY_FIELDS,
  JsonDataType.JSON_PROPERTY_LENGTH,
  JsonDataType.JSON_PROPERTY_TYPE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class JsonDataType {
  public static final String JSON_PROPERTY_FIELDS = "fields";
  @javax.annotation.Nullable private Object fields;

  public static final String JSON_PROPERTY_LENGTH = "length";

  @javax.annotation.Nullable
  private JsonNullable<Integer> length = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nonnull private String type;

  public JsonDataType() {}

  public JsonDataType fields(@javax.annotation.Nullable Object fields) {

    this.fields = fields;
    return this;
  }

  /**
   * Get fields
   *
   * @return fields
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FIELDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Object getFields() {
    return fields;
  }

  @JsonProperty(JSON_PROPERTY_FIELDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFields(@javax.annotation.Nullable Object fields) {
    this.fields = fields;
  }

  public JsonDataType length(@javax.annotation.Nullable Integer length) {
    this.length = JsonNullable.<Integer>of(length);

    return this;
  }

  /**
   * Get length minimum: 0
   *
   * @return length
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getLength() {
    return length.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_LENGTH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getLength_JsonNullable() {
    return length;
  }

  @JsonProperty(JSON_PROPERTY_LENGTH)
  public void setLength_JsonNullable(JsonNullable<Integer> length) {
    this.length = length;
  }

  public void setLength(@javax.annotation.Nullable Integer length) {
    this.length = JsonNullable.<Integer>of(length);
  }

  public JsonDataType type(@javax.annotation.Nonnull String type) {

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
  public String getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setType(@javax.annotation.Nonnull String type) {
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
    JsonDataType jsonDataType = (JsonDataType) o;
    return Objects.equals(this.fields, jsonDataType.fields)
        && equalsNullable(this.length, jsonDataType.length)
        && Objects.equals(this.type, jsonDataType.type);
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
    return Objects.hash(fields, hashCodeNullable(length), type);
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
    sb.append("class JsonDataType {\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
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

    // add `fields` to the URL query string
    if (getFields() != null) {
      try {
        joiner.add(
            String.format(
                "%sfields%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getFields()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `length` to the URL query string
    if (getLength() != null) {
      try {
        joiner.add(
            String.format(
                "%slength%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLength()), "UTF-8").replaceAll("\\+", "%20")));
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

    return joiner.toString();
  }
}
