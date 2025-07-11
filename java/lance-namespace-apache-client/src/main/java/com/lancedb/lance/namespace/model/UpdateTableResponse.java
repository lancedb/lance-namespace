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
import java.util.Objects;
import java.util.StringJoiner;

/** UpdateTableResponse */
@JsonPropertyOrder({
  UpdateTableResponse.JSON_PROPERTY_UPDATED_ROWS,
  UpdateTableResponse.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateTableResponse {
  public static final String JSON_PROPERTY_UPDATED_ROWS = "updated_rows";
  @javax.annotation.Nonnull private Long updatedRows;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nonnull private Long version;

  public UpdateTableResponse() {}

  public UpdateTableResponse updatedRows(@javax.annotation.Nonnull Long updatedRows) {

    this.updatedRows = updatedRows;
    return this;
  }

  /**
   * Number of rows updated minimum: 0
   *
   * @return updatedRows
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UPDATED_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getUpdatedRows() {
    return updatedRows;
  }

  @JsonProperty(JSON_PROPERTY_UPDATED_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUpdatedRows(@javax.annotation.Nonnull Long updatedRows) {
    this.updatedRows = updatedRows;
  }

  public UpdateTableResponse version(@javax.annotation.Nonnull Long version) {

    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
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
    UpdateTableResponse updateTableResponse = (UpdateTableResponse) o;
    return Objects.equals(this.updatedRows, updateTableResponse.updatedRows)
        && Objects.equals(this.version, updateTableResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(updatedRows, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTableResponse {\n");
    sb.append("    updatedRows: ").append(toIndentedString(updatedRows)).append("\n");
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

    // add `updated_rows` to the URL query string
    if (getUpdatedRows() != null) {
      try {
        joiner.add(
            String.format(
                "%supdated_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUpdatedRows()), "UTF-8")
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
