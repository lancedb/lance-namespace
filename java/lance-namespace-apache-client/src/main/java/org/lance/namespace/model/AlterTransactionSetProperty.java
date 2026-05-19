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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.StringJoiner;

/** AlterTransactionSetProperty */
@JsonPropertyOrder({
  AlterTransactionSetProperty.JSON_PROPERTY_KEY,
  AlterTransactionSetProperty.JSON_PROPERTY_VALUE,
  AlterTransactionSetProperty.JSON_PROPERTY_MODE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionSetProperty {
  public static final String JSON_PROPERTY_KEY = "key";
  @javax.annotation.Nullable private String key;

  public static final String JSON_PROPERTY_VALUE = "value";
  @javax.annotation.Nullable private String value;

  public static final String JSON_PROPERTY_MODE = "mode";
  @javax.annotation.Nullable private String mode;

  public AlterTransactionSetProperty() {}

  public AlterTransactionSetProperty key(@javax.annotation.Nullable String key) {

    this.key = key;
    return this;
  }

  /**
   * Get key
   *
   * @return key
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getKey() {
    return key;
  }

  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setKey(@javax.annotation.Nullable String key) {
    this.key = key;
  }

  public AlterTransactionSetProperty value(@javax.annotation.Nullable String value) {

    this.value = value;
    return this;
  }

  /**
   * Get value
   *
   * @return value
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getValue() {
    return value;
  }

  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValue(@javax.annotation.Nullable String value) {
    this.value = value;
  }

  public AlterTransactionSetProperty mode(@javax.annotation.Nullable String mode) {

    this.mode = mode;
    return this;
  }

  /**
   * The behavior if the property key already exists. Case insensitive, supports both PascalCase and
   * snake_case. Valid values are: - Overwrite (default): overwrite the existing value with the
   * provided value - Fail: fail the entire operation - Skip: keep the existing value and skip
   * setting the provided value
   *
   * @return mode
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getMode() {
    return mode;
  }

  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMode(@javax.annotation.Nullable String mode) {
    this.mode = mode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionSetProperty alterTransactionSetProperty = (AlterTransactionSetProperty) o;
    return Objects.equals(this.key, alterTransactionSetProperty.key)
        && Objects.equals(this.value, alterTransactionSetProperty.value)
        && Objects.equals(this.mode, alterTransactionSetProperty.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionSetProperty {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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

    // add `key` to the URL query string
    if (getKey() != null) {
      try {
        joiner.add(
            String.format(
                "%skey%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getKey()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `value` to the URL query string
    if (getValue() != null) {
      try {
        joiner.add(
            String.format(
                "%svalue%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getValue()), "UTF-8").replaceAll("\\+", "%20")));
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

    return joiner.toString();
  }
}
