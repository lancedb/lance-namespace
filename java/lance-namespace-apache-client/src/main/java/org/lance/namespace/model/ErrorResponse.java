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
  @javax.annotation.Nonnull private Integer code;

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
   * A brief, human-readable message about the error.
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

  public ErrorResponse code(@javax.annotation.Nonnull Integer code) {

    this.code = code;
    return this;
  }

  /**
   * Lance Namespace error code identifying the error type. Error codes: 0 - Unsupported: Operation
   * not supported by this backend 1 - NamespaceNotFound: The specified namespace does not exist 2 -
   * NamespaceAlreadyExists: A namespace with this name already exists 3 - NamespaceNotEmpty:
   * Namespace contains tables or child namespaces 4 - TableNotFound: The specified table does not
   * exist 5 - TableAlreadyExists: A table with this name already exists 6 - TableIndexNotFound: The
   * specified table index does not exist 7 - TableIndexAlreadyExists: A table index with this name
   * already exists 8 - TableTagNotFound: The specified table tag does not exist 9 -
   * TableTagAlreadyExists: A table tag with this name already exists 10 - TransactionNotFound: The
   * specified transaction does not exist 11 - TableVersionNotFound: The specified table version
   * does not exist 12 - TableColumnNotFound: The specified table column does not exist 13 -
   * InvalidInput: Malformed request or invalid parameters 14 - ConcurrentModification: Optimistic
   * concurrency conflict 15 - PermissionDenied: User lacks permission for this operation 16 -
   * Unauthenticated: Authentication credentials are missing or invalid 17 - ServiceUnavailable:
   * Service is temporarily unavailable 18 - Internal: Unexpected server/implementation error 19 -
   * InvalidTableState: Table is in an invalid state for the operation 20 -
   * TableSchemaValidationError: Table schema validation failed minimum: 0
   *
   * @return code
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getCode() {
    return code;
  }

  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCode(@javax.annotation.Nonnull Integer code) {
    this.code = code;
  }

  public ErrorResponse detail(@javax.annotation.Nullable String detail) {

    this.detail = detail;
    return this;
  }

  /**
   * An optional human-readable explanation of the error. This can be used to record additional
   * information such as stack trace.
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
   * A string that identifies the specific occurrence of the error. This can be a URI, a request or
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
        && Objects.equals(this.detail, errorResponse.detail)
        && Objects.equals(this.instance, errorResponse.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
