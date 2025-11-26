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

/** Common JSON error response model */
@JsonPropertyOrder({
  ErrorResponse.JSON_PROPERTY_ERROR,
  ErrorResponse.JSON_PROPERTY_CODE,
  ErrorResponse.JSON_PROPERTY_TYPE,
  ErrorResponse.JSON_PROPERTY_DETAIL,
  ErrorResponse.JSON_PROPERTY_INSTANCE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class ErrorResponse {
  public static final String JSON_PROPERTY_ERROR = "error";
  @javax.annotation.Nullable private String error;

  public static final String JSON_PROPERTY_CODE = "code";
  @javax.annotation.Nullable private Integer code;

  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nullable private String type;

  public static final String JSON_PROPERTY_DETAIL = "detail";
  @javax.annotation.Nullable private String detail;

  public static final String JSON_PROPERTY_INSTANCE = "instance";
  @javax.annotation.Nullable private String instance;

  public ErrorResponse() {}

  public ErrorResponse error(@javax.annotation.Nullable String error) {

    this.error = error;
    return this;
  }

  /**
   * a brief, human-readable message about the error
   *
   * @return error
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getError() {
    return error;
  }

  @JsonProperty(JSON_PROPERTY_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setError(@javax.annotation.Nullable String error) {
    this.error = error;
  }

  public ErrorResponse code(@javax.annotation.Nullable Integer code) {

    this.code = code;
    return this;
  }

  /**
   * HTTP style response code, where 4XX represents client side errors and 5XX represents server
   * side errors. For implementations that uses HTTP (e.g. REST namespace), this field can be
   * optional in favor of the HTTP response status code. In case both values exist and do not match,
   * the HTTP response status code should be used. minimum: 400 maximum: 600
   *
   * @return code
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getCode() {
    return code;
  }

  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCode(@javax.annotation.Nullable Integer code) {
    this.code = code;
  }

  public ErrorResponse type(@javax.annotation.Nullable String type) {

    this.type = type;
    return this;
  }

  /**
   * An optional type identifier string for the error. This allows the implementation to specify
   * their internal error type, which could be more detailed than the HTTP standard status code.
   *
   * @return type
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(@javax.annotation.Nullable String type) {
    this.type = type;
  }

  public ErrorResponse detail(@javax.annotation.Nullable String detail) {

    this.detail = detail;
    return this;
  }

  /**
   * an optional human-readable explanation of the error. This can be used to record information
   * such as stack trace.
   *
   * @return detail
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DETAIL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getDetail() {
    return detail;
  }

  @JsonProperty(JSON_PROPERTY_DETAIL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDetail(@javax.annotation.Nullable String detail) {
    this.detail = detail;
  }

  public ErrorResponse instance(@javax.annotation.Nullable String instance) {

    this.instance = instance;
    return this;
  }

  /**
   * a string that identifies the specific occurrence of the error. This can be a URI, a request or
   * response ID, or anything that the implementation can recognize to trace specific occurrence of
   * the error.
   *
   * @return instance
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INSTANCE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getInstance() {
    return instance;
  }

  @JsonProperty(JSON_PROPERTY_INSTANCE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInstance(@javax.annotation.Nullable String instance) {
    this.instance = instance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.error, errorResponse.error)
        && Objects.equals(this.code, errorResponse.code)
        && Objects.equals(this.type, errorResponse.type)
        && Objects.equals(this.detail, errorResponse.detail)
        && Objects.equals(this.instance, errorResponse.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code, type, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
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

    // add `error` to the URL query string
    if (getError() != null) {
      try {
        joiner.add(
            String.format(
                "%serror%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getError()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `code` to the URL query string
    if (getCode() != null) {
      try {
        joiner.add(
            String.format(
                "%scode%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getCode()), "UTF-8").replaceAll("\\+", "%20")));
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

    // add `detail` to the URL query string
    if (getDetail() != null) {
      try {
        joiner.add(
            String.format(
                "%sdetail%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getDetail()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `instance` to the URL query string
    if (getInstance() != null) {
      try {
        joiner.add(
            String.format(
                "%sinstance%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getInstance()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
