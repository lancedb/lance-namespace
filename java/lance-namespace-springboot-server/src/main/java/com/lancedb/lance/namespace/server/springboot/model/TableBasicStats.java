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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** TableBasicStats */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class TableBasicStats {

  private Integer numDeletedRows;

  private Integer numFragments;

  public TableBasicStats() {
    super();
  }

  /** Constructor with only required parameters */
  public TableBasicStats(Integer numDeletedRows, Integer numFragments) {
    this.numDeletedRows = numDeletedRows;
    this.numFragments = numFragments;
  }

  public TableBasicStats numDeletedRows(Integer numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
    return this;
  }

  /**
   * Get numDeletedRows minimum: 0
   *
   * @return numDeletedRows
   */
  @NotNull
  @Min(0)
  @Schema(name = "num_deleted_rows", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_deleted_rows")
  public Integer getNumDeletedRows() {
    return numDeletedRows;
  }

  public void setNumDeletedRows(Integer numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
  }

  public TableBasicStats numFragments(Integer numFragments) {
    this.numFragments = numFragments;
    return this;
  }

  /**
   * Get numFragments minimum: 0
   *
   * @return numFragments
   */
  @NotNull
  @Min(0)
  @Schema(name = "num_fragments", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_fragments")
  public Integer getNumFragments() {
    return numFragments;
  }

  public void setNumFragments(Integer numFragments) {
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
}
