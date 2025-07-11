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

/** IndexStatsResponse */
@JsonPropertyOrder({
  IndexStatsResponse.JSON_PROPERTY_DISTANCE_TYPE,
  IndexStatsResponse.JSON_PROPERTY_INDEX_TYPE,
  IndexStatsResponse.JSON_PROPERTY_NUM_INDEXED_ROWS,
  IndexStatsResponse.JSON_PROPERTY_NUM_UNINDEXED_ROWS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class IndexStatsResponse {
  public static final String JSON_PROPERTY_DISTANCE_TYPE = "distance_type";
  @javax.annotation.Nullable private String distanceType;

  public static final String JSON_PROPERTY_INDEX_TYPE = "index_type";
  @javax.annotation.Nullable private String indexType;

  public static final String JSON_PROPERTY_NUM_INDEXED_ROWS = "num_indexed_rows";
  @javax.annotation.Nullable private Long numIndexedRows;

  public static final String JSON_PROPERTY_NUM_UNINDEXED_ROWS = "num_unindexed_rows";
  @javax.annotation.Nullable private Long numUnindexedRows;

  public IndexStatsResponse() {}

  public IndexStatsResponse distanceType(@javax.annotation.Nullable String distanceType) {

    this.distanceType = distanceType;
    return this;
  }

  /**
   * Distance type for vector indexes
   *
   * @return distanceType
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getDistanceType() {
    return distanceType;
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDistanceType(@javax.annotation.Nullable String distanceType) {
    this.distanceType = distanceType;
  }

  public IndexStatsResponse indexType(@javax.annotation.Nullable String indexType) {

    this.indexType = indexType;
    return this;
  }

  /**
   * Type of the index
   *
   * @return indexType
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getIndexType() {
    return indexType;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIndexType(@javax.annotation.Nullable String indexType) {
    this.indexType = indexType;
  }

  public IndexStatsResponse numIndexedRows(@javax.annotation.Nullable Long numIndexedRows) {

    this.numIndexedRows = numIndexedRows;
    return this;
  }

  /**
   * Number of indexed rows minimum: 0
   *
   * @return numIndexedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_INDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumIndexedRows() {
    return numIndexedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_INDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumIndexedRows(@javax.annotation.Nullable Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
  }

  public IndexStatsResponse numUnindexedRows(@javax.annotation.Nullable Long numUnindexedRows) {

    this.numUnindexedRows = numUnindexedRows;
    return this;
  }

  /**
   * Number of unindexed rows minimum: 0
   *
   * @return numUnindexedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_UNINDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumUnindexedRows() {
    return numUnindexedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_UNINDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumUnindexedRows(@javax.annotation.Nullable Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndexStatsResponse indexStatsResponse = (IndexStatsResponse) o;
    return Objects.equals(this.distanceType, indexStatsResponse.distanceType)
        && Objects.equals(this.indexType, indexStatsResponse.indexType)
        && Objects.equals(this.numIndexedRows, indexStatsResponse.numIndexedRows)
        && Objects.equals(this.numUnindexedRows, indexStatsResponse.numUnindexedRows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(distanceType, indexType, numIndexedRows, numUnindexedRows);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndexStatsResponse {\n");
    sb.append("    distanceType: ").append(toIndentedString(distanceType)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    numIndexedRows: ").append(toIndentedString(numIndexedRows)).append("\n");
    sb.append("    numUnindexedRows: ").append(toIndentedString(numUnindexedRows)).append("\n");
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

    // add `distance_type` to the URL query string
    if (getDistanceType() != null) {
      try {
        joiner.add(
            String.format(
                "%sdistance_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getDistanceType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `index_type` to the URL query string
    if (getIndexType() != null) {
      try {
        joiner.add(
            String.format(
                "%sindex_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getIndexType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_indexed_rows` to the URL query string
    if (getNumIndexedRows() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_indexed_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumIndexedRows()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_unindexed_rows` to the URL query string
    if (getNumUnindexedRows() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_unindexed_rows%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumUnindexedRows()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
