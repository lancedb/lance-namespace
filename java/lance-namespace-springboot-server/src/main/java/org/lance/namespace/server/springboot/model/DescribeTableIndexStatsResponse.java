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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** DescribeTableIndexStatsResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableIndexStatsResponse {

  @Valid private Map<String, String> context = new HashMap<>();

  private String distanceType;

  private String indexType;

  private Long numIndexedRows;

  private Long numUnindexedRows;

  private Integer numIndices;

  public DescribeTableIndexStatsResponse context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public DescribeTableIndexStatsResponse putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context as key-value pairs. How to use the context is custom to the specific
   * implementation. On a request, it carries caller-provided context to the implementation. On a
   * response, it carries implementation-provided context back to the caller. REST NAMESPACE ONLY
   * Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request,
   * any entry whose key starts with `header.` is sent as an HTTP request header with the prefix
   * stripped. For example, the entry `{\"header.Authorization\": \"Bearer abc\"}` is sent as the
   * request header `Authorization: Bearer abc`. - On a response, every HTTP response header is
   * returned as an entry whose key is the header name prefixed with `header.`. For example, the
   * response header `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\":
   * \"abc123\"}`.
   *
   * @return context
   */
  @Schema(
      name = "context",
      description =
          "Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }

  public DescribeTableIndexStatsResponse distanceType(String distanceType) {
    this.distanceType = distanceType;
    return this;
  }

  /**
   * Distance type for vector indexes
   *
   * @return distanceType
   */
  @Schema(
      name = "distance_type",
      description = "Distance type for vector indexes",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("distance_type")
  public String getDistanceType() {
    return distanceType;
  }

  public void setDistanceType(String distanceType) {
    this.distanceType = distanceType;
  }

  public DescribeTableIndexStatsResponse indexType(String indexType) {
    this.indexType = indexType;
    return this;
  }

  /**
   * Type of the index
   *
   * @return indexType
   */
  @Schema(
      name = "index_type",
      description = "Type of the index",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("index_type")
  public String getIndexType() {
    return indexType;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  public DescribeTableIndexStatsResponse numIndexedRows(Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
    return this;
  }

  /**
   * Number of indexed rows minimum: 0
   *
   * @return numIndexedRows
   */
  @Min(0L)
  @Schema(
      name = "num_indexed_rows",
      description = "Number of indexed rows",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_indexed_rows")
  public Long getNumIndexedRows() {
    return numIndexedRows;
  }

  public void setNumIndexedRows(Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
  }

  public DescribeTableIndexStatsResponse numUnindexedRows(Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
    return this;
  }

  /**
   * Number of unindexed rows minimum: 0
   *
   * @return numUnindexedRows
   */
  @Min(0L)
  @Schema(
      name = "num_unindexed_rows",
      description = "Number of unindexed rows",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_unindexed_rows")
  public Long getNumUnindexedRows() {
    return numUnindexedRows;
  }

  public void setNumUnindexedRows(Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
  }

  public DescribeTableIndexStatsResponse numIndices(Integer numIndices) {
    this.numIndices = numIndices;
    return this;
  }

  /**
   * Number of indices minimum: 0
   *
   * @return numIndices
   */
  @Min(0)
  @Schema(
      name = "num_indices",
      description = "Number of indices",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_indices")
  public Integer getNumIndices() {
    return numIndices;
  }

  public void setNumIndices(Integer numIndices) {
    this.numIndices = numIndices;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableIndexStatsResponse describeTableIndexStatsResponse =
        (DescribeTableIndexStatsResponse) o;
    return Objects.equals(this.context, describeTableIndexStatsResponse.context)
        && Objects.equals(this.distanceType, describeTableIndexStatsResponse.distanceType)
        && Objects.equals(this.indexType, describeTableIndexStatsResponse.indexType)
        && Objects.equals(this.numIndexedRows, describeTableIndexStatsResponse.numIndexedRows)
        && Objects.equals(this.numUnindexedRows, describeTableIndexStatsResponse.numUnindexedRows)
        && Objects.equals(this.numIndices, describeTableIndexStatsResponse.numIndices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        context, distanceType, indexType, numIndexedRows, numUnindexedRows, numIndices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableIndexStatsResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    distanceType: ").append(toIndentedString(distanceType)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    numIndexedRows: ").append(toIndentedString(numIndexedRows)).append("\n");
    sb.append("    numUnindexedRows: ").append(toIndentedString(numUnindexedRows)).append("\n");
    sb.append("    numIndices: ").append(toIndentedString(numIndices)).append("\n");
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
}
