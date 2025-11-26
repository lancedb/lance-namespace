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

/** Response from merge insert operation */
@JsonPropertyOrder({
  MergeInsertIntoTableResponse.JSON_PROPERTY_NUM_UPDATED_ROWS,
  MergeInsertIntoTableResponse.JSON_PROPERTY_NUM_INSERTED_ROWS,
  MergeInsertIntoTableResponse.JSON_PROPERTY_NUM_DELETED_ROWS,
  MergeInsertIntoTableResponse.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MergeInsertIntoTableResponse {
  public static final String JSON_PROPERTY_NUM_UPDATED_ROWS = "num_updated_rows";
  @javax.annotation.Nullable private Long numUpdatedRows;

  public static final String JSON_PROPERTY_NUM_INSERTED_ROWS = "num_inserted_rows";
  @javax.annotation.Nullable private Long numInsertedRows;

  public static final String JSON_PROPERTY_NUM_DELETED_ROWS = "num_deleted_rows";
  @javax.annotation.Nullable private Long numDeletedRows;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

  public MergeInsertIntoTableResponse() {}

  public MergeInsertIntoTableResponse numUpdatedRows(
      @javax.annotation.Nullable Long numUpdatedRows) {

    this.numUpdatedRows = numUpdatedRows;
    return this;
  }

  /**
   * Number of rows updated minimum: 0
   *
   * @return numUpdatedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_UPDATED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumUpdatedRows() {
    return numUpdatedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_UPDATED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumUpdatedRows(@javax.annotation.Nullable Long numUpdatedRows) {
    this.numUpdatedRows = numUpdatedRows;
  }

  public MergeInsertIntoTableResponse numInsertedRows(
      @javax.annotation.Nullable Long numInsertedRows) {

    this.numInsertedRows = numInsertedRows;
    return this;
  }

  /**
   * Number of rows inserted minimum: 0
   *
   * @return numInsertedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_INSERTED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumInsertedRows() {
    return numInsertedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_INSERTED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumInsertedRows(@javax.annotation.Nullable Long numInsertedRows) {
    this.numInsertedRows = numInsertedRows;
  }

  public MergeInsertIntoTableResponse numDeletedRows(
      @javax.annotation.Nullable Long numDeletedRows) {

    this.numDeletedRows = numDeletedRows;
    return this;
  }

  /**
   * Number of rows deleted (typically 0 for merge insert) minimum: 0
   *
   * @return numDeletedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_DELETED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumDeletedRows() {
    return numDeletedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_DELETED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumDeletedRows(@javax.annotation.Nullable Long numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
  }

  public MergeInsertIntoTableResponse version(@javax.annotation.Nullable Long version) {

    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MergeInsertIntoTableResponse mergeInsertIntoTableResponse = (MergeInsertIntoTableResponse) o;
    return Objects.equals(this.numUpdatedRows, mergeInsertIntoTableResponse.numUpdatedRows)
        && Objects.equals(this.numInsertedRows, mergeInsertIntoTableResponse.numInsertedRows)
        && Objects.equals(this.numDeletedRows, mergeInsertIntoTableResponse.numDeletedRows)
        && Objects.equals(this.version, mergeInsertIntoTableResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numUpdatedRows, numInsertedRows, numDeletedRows, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MergeInsertIntoTableResponse {\n");
    sb.append("    numUpdatedRows: ").append(toIndentedString(numUpdatedRows)).append("\n");
    sb.append("    numInsertedRows: ").append(toIndentedString(numInsertedRows)).append("\n");
    sb.append("    numDeletedRows: ").append(toIndentedString(numDeletedRows)).append("\n");
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

    // add `num_updated_rows` to the URL query string
    if (getNumUpdatedRows() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_updated_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumUpdatedRows()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_inserted_rows` to the URL query string
    if (getNumInsertedRows() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_inserted_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumInsertedRows()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_deleted_rows` to the URL query string
    if (getNumDeletedRows() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_deleted_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumDeletedRows()), "UTF-8")
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
