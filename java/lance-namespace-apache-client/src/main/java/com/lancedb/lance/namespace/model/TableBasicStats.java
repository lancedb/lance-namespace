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

/** TableBasicStats */
@JsonPropertyOrder({
  TableBasicStats.JSON_PROPERTY_NUM_DELETED_ROWS,
  TableBasicStats.JSON_PROPERTY_NUM_FRAGMENTS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class TableBasicStats {
  public static final String JSON_PROPERTY_NUM_DELETED_ROWS = "num_deleted_rows";
  @javax.annotation.Nonnull private Integer numDeletedRows;

  public static final String JSON_PROPERTY_NUM_FRAGMENTS = "num_fragments";
  @javax.annotation.Nonnull private Integer numFragments;

  public TableBasicStats() {}

  public TableBasicStats numDeletedRows(@javax.annotation.Nonnull Integer numDeletedRows) {

    this.numDeletedRows = numDeletedRows;
    return this;
  }

  /**
   * Get numDeletedRows minimum: 0
   *
   * @return numDeletedRows
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_DELETED_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getNumDeletedRows() {
    return numDeletedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_DELETED_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumDeletedRows(@javax.annotation.Nonnull Integer numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
  }

  public TableBasicStats numFragments(@javax.annotation.Nonnull Integer numFragments) {

    this.numFragments = numFragments;
    return this;
  }

  /**
   * Get numFragments minimum: 0
   *
   * @return numFragments
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getNumFragments() {
    return numFragments;
  }

  @JsonProperty(JSON_PROPERTY_NUM_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumFragments(@javax.annotation.Nonnull Integer numFragments) {
    this.numFragments = numFragments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TableBasicStats tableBasicStats = (TableBasicStats) o;
    return Objects.equals(this.numDeletedRows, tableBasicStats.numDeletedRows)
        && Objects.equals(this.numFragments, tableBasicStats.numFragments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numDeletedRows, numFragments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TableBasicStats {\n");
    sb.append("    numDeletedRows: ").append(toIndentedString(numDeletedRows)).append("\n");
    sb.append("    numFragments: ").append(toIndentedString(numFragments)).append("\n");
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

    // add `num_fragments` to the URL query string
    if (getNumFragments() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_fragments%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumFragments()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
