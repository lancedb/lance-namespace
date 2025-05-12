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
package com.lancedb.lance.namespace.client.apache.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.StringJoiner;

/** JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807) */
@JsonPropertyOrder({
  ErrorResponse.JSON_PROPERTY_TYPE,
  ErrorResponse.JSON_PROPERTY_TITLE,
  ErrorResponse.JSON_PROPERTY_STATUS,
  ErrorResponse.JSON_PROPERTY_DETAIL,
  ErrorResponse.JSON_PROPERTY_INSTANCE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class ErrorResponse {
  public static final String JSON_PROPERTY_TYPE = "type";
  @javax.annotation.Nonnull private String type;

  public static final String JSON_PROPERTY_TITLE = "title";
  @javax.annotation.Nullable private String title;

  public static final String JSON_PROPERTY_STATUS = "status";
  @javax.annotation.Nullable private Integer status;

  public static final String JSON_PROPERTY_DETAIL = "detail";
  @javax.annotation.Nullable private String detail;

  public static final String JSON_PROPERTY_INSTANCE = "instance";
  @javax.annotation.Nullable private String instance;

  public ErrorResponse() {}

  public ErrorResponse type(@javax.annotation.Nonnull String type) {

    this.type = type;
    return this;
  }

  /**
   * a URI identifier that categorizes the error
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

  public ErrorResponse title(@javax.annotation.Nullable String title) {

    this.title = title;
    return this;
  }

  /**
   * a brief, human-readable message about the error
   *
   * @return title
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getTitle() {
    return title;
  }

  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTitle(@javax.annotation.Nullable String title) {
    this.title = title;
  }

  public ErrorResponse status(@javax.annotation.Nullable Integer status) {

    this.status = status;
    return this;
  }

  /**
   * HTTP response code, (if present) it must match the actual HTTP code returned by the service
   * minimum: 400 maximum: 600
   *
   * @return status
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getStatus() {
    return status;
  }

  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStatus(@javax.annotation.Nullable Integer status) {
    this.status = status;
  }

  public ErrorResponse detail(@javax.annotation.Nullable String detail) {

    this.detail = detail;
    return this;
  }

  /**
   * a human-readable explanation of the error
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
   * a URI that identifies the specific occurrence of the error
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
    return Objects.equals(this.type, errorResponse.type)
        && Objects.equals(this.title, errorResponse.title)
        && Objects.equals(this.status, errorResponse.status)
        && Objects.equals(this.detail, errorResponse.detail)
        && Objects.equals(this.instance, errorResponse.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, title, status, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

    // add `title` to the URL query string
    if (getTitle() != null) {
      try {
        joiner.add(
            String.format(
                "%stitle%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTitle()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `status` to the URL query string
    if (getStatus() != null) {
      try {
        joiner.add(
            String.format(
                "%sstatus%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getStatus()), "UTF-8").replaceAll("\\+", "%20")));
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
