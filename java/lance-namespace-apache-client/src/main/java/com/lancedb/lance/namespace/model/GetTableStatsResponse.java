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

/** GetTableStatsResponse */
@JsonPropertyOrder({
  GetTableStatsResponse.JSON_PROPERTY_NUM_ROWS,
  GetTableStatsResponse.JSON_PROPERTY_SIZE_BYTES,
  GetTableStatsResponse.JSON_PROPERTY_NUM_FRAGMENTS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class GetTableStatsResponse {
  public static final String JSON_PROPERTY_NUM_ROWS = "num_rows";
  @javax.annotation.Nonnull private Long numRows;

  public static final String JSON_PROPERTY_SIZE_BYTES = "size_bytes";
  @javax.annotation.Nonnull private Long sizeBytes;

  public static final String JSON_PROPERTY_NUM_FRAGMENTS = "num_fragments";
  @javax.annotation.Nullable private Long numFragments;

  public GetTableStatsResponse() {}

  public GetTableStatsResponse numRows(@javax.annotation.Nonnull Long numRows) {

    this.numRows = numRows;
    return this;
  }

  /**
   * Total number of rows in the table minimum: 0
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

  public GetTableStatsResponse sizeBytes(@javax.annotation.Nonnull Long sizeBytes) {

    this.sizeBytes = sizeBytes;
    return this;
  }

  /**
   * Total size of the table in bytes minimum: 0
   *
   * @return sizeBytes
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SIZE_BYTES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getSizeBytes() {
    return sizeBytes;
  }

  @JsonProperty(JSON_PROPERTY_SIZE_BYTES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSizeBytes(@javax.annotation.Nonnull Long sizeBytes) {
    this.sizeBytes = sizeBytes;
  }

  public GetTableStatsResponse numFragments(@javax.annotation.Nullable Long numFragments) {

    this.numFragments = numFragments;
    return this;
  }

  /**
   * Number of data fragments minimum: 0
   *
   * @return numFragments
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumFragments() {
    return numFragments;
  }

  @JsonProperty(JSON_PROPERTY_NUM_FRAGMENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumFragments(@javax.annotation.Nullable Long numFragments) {
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
    GetTableStatsResponse getTableStatsResponse = (GetTableStatsResponse) o;
    return Objects.equals(this.numRows, getTableStatsResponse.numRows)
        && Objects.equals(this.sizeBytes, getTableStatsResponse.sizeBytes)
        && Objects.equals(this.numFragments, getTableStatsResponse.numFragments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numRows, sizeBytes, numFragments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTableStatsResponse {\n");
    sb.append("    numRows: ").append(toIndentedString(numRows)).append("\n");
    sb.append("    sizeBytes: ").append(toIndentedString(sizeBytes)).append("\n");
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

    // add `size_bytes` to the URL query string
    if (getSizeBytes() != null) {
      try {
        joiner.add(
            String.format(
                "%ssize_bytes%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getSizeBytes()), "UTF-8")
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
