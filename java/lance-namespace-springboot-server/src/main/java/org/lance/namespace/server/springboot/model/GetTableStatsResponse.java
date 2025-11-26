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
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** GetTableStatsResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class GetTableStatsResponse {

  private Long numRows;

  private Long sizeBytes;

  private Long numFragments;

  public GetTableStatsResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public GetTableStatsResponse(Long numRows, Long sizeBytes) {
    this.numRows = numRows;
    this.sizeBytes = sizeBytes;
  }

  public GetTableStatsResponse numRows(Long numRows) {
    this.numRows = numRows;
    return this;
  }

  /**
   * Total number of rows in the table minimum: 0
   *
   * @return numRows
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "num_rows",
      description = "Total number of rows in the table",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_rows")
  public Long getNumRows() {
    return numRows;
  }

  public void setNumRows(Long numRows) {
    this.numRows = numRows;
  }

  public GetTableStatsResponse sizeBytes(Long sizeBytes) {
    this.sizeBytes = sizeBytes;
    return this;
  }

  /**
   * Total size of the table in bytes minimum: 0
   *
   * @return sizeBytes
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "size_bytes",
      description = "Total size of the table in bytes",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("size_bytes")
  public Long getSizeBytes() {
    return sizeBytes;
  }

  public void setSizeBytes(Long sizeBytes) {
    this.sizeBytes = sizeBytes;
  }

  public GetTableStatsResponse numFragments(Long numFragments) {
    this.numFragments = numFragments;
    return this;
  }

  /**
   * Number of data fragments minimum: 0
   *
   * @return numFragments
   */
  @Min(0L)
  @Schema(
      name = "num_fragments",
      description = "Number of data fragments",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_fragments")
  public Long getNumFragments() {
    return numFragments;
  }

  public void setNumFragments(Long numFragments) {
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
}
