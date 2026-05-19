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

/** GetTableStatsResponse */
@JsonPropertyOrder({
  GetTableStatsResponse.JSON_PROPERTY_TOTAL_BYTES,
  GetTableStatsResponse.JSON_PROPERTY_NUM_ROWS,
  GetTableStatsResponse.JSON_PROPERTY_NUM_INDICES,
  GetTableStatsResponse.JSON_PROPERTY_FRAGMENT_STATS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class GetTableStatsResponse {
  public static final String JSON_PROPERTY_TOTAL_BYTES = "total_bytes";
  @javax.annotation.Nonnull private Long totalBytes;

  public static final String JSON_PROPERTY_NUM_ROWS = "num_rows";
  @javax.annotation.Nonnull private Long numRows;

  public static final String JSON_PROPERTY_NUM_INDICES = "num_indices";
  @javax.annotation.Nonnull private Long numIndices;

  public static final String JSON_PROPERTY_FRAGMENT_STATS = "fragment_stats";
  @javax.annotation.Nonnull private FragmentStats fragmentStats;

  public GetTableStatsResponse() {}

  public GetTableStatsResponse totalBytes(@javax.annotation.Nonnull Long totalBytes) {

    this.totalBytes = totalBytes;
    return this;
  }

  /**
   * The total number of bytes in the table minimum: 0
   *
   * @return totalBytes
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TOTAL_BYTES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getTotalBytes() {
    return totalBytes;
  }

  @JsonProperty(JSON_PROPERTY_TOTAL_BYTES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTotalBytes(@javax.annotation.Nonnull Long totalBytes) {
    this.totalBytes = totalBytes;
  }

  public GetTableStatsResponse numRows(@javax.annotation.Nonnull Long numRows) {

    this.numRows = numRows;
    return this;
  }

  /**
   * The number of rows in the table minimum: 0
   *
   * @return numRows
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getNumRows() {
    return numRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumRows(@javax.annotation.Nonnull Long numRows) {
    this.numRows = numRows;
  }

  public GetTableStatsResponse numIndices(@javax.annotation.Nonnull Long numIndices) {

    this.numIndices = numIndices;
    return this;
  }

  /**
   * The number of indices in the table minimum: 0
   *
   * @return numIndices
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_INDICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getNumIndices() {
    return numIndices;
  }

  @JsonProperty(JSON_PROPERTY_NUM_INDICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumIndices(@javax.annotation.Nonnull Long numIndices) {
    this.numIndices = numIndices;
  }

  public GetTableStatsResponse fragmentStats(
      @javax.annotation.Nonnull FragmentStats fragmentStats) {

    this.fragmentStats = fragmentStats;
    return this;
  }

  /**
   * Statistics on table fragments
   *
   * @return fragmentStats
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_FRAGMENT_STATS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public FragmentStats getFragmentStats() {
    return fragmentStats;
  }

  @JsonProperty(JSON_PROPERTY_FRAGMENT_STATS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFragmentStats(@javax.annotation.Nonnull FragmentStats fragmentStats) {
    this.fragmentStats = fragmentStats;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetTableStatsResponse getTableStatsResponse = (GetTableStatsResponse) o;
    return Objects.equals(this.totalBytes, getTableStatsResponse.totalBytes)
        && Objects.equals(this.numRows, getTableStatsResponse.numRows)
        && Objects.equals(this.numIndices, getTableStatsResponse.numIndices)
        && Objects.equals(this.fragmentStats, getTableStatsResponse.fragmentStats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalBytes, numRows, numIndices, fragmentStats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTableStatsResponse {\n");
    sb.append("    totalBytes: ").append(toIndentedString(totalBytes)).append("\n");
    sb.append("    numRows: ").append(toIndentedString(numRows)).append("\n");
    sb.append("    numIndices: ").append(toIndentedString(numIndices)).append("\n");
    sb.append("    fragmentStats: ").append(toIndentedString(fragmentStats)).append("\n");
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

    // add `total_bytes` to the URL query string
    if (getTotalBytes() != null) {
      try {
        joiner.add(
            String.format(
                "%stotal_bytes%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTotalBytes()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_rows` to the URL query string
    if (getNumRows() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumRows()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_indices` to the URL query string
    if (getNumIndices() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_indices%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumIndices()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `fragment_stats` to the URL query string
    if (getFragmentStats() != null) {
      joiner.add(getFragmentStats().toUrlQueryString(prefix + "fragment_stats" + suffix));
    }

    return joiner.toString();
  }
}
