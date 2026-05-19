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

/** Response from merge insert operation */
@Schema(name = "MergeInsertIntoTableResponse", description = "Response from merge insert operation")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class MergeInsertIntoTableResponse {

  private String transactionId;

  private Long numUpdatedRows;

  private Long numInsertedRows;

  private Long numDeletedRows;

  private Long version;

  public MergeInsertIntoTableResponse transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier
   *
   * @return transactionId
   */
  @Schema(
      name = "transaction_id",
      description = "Optional transaction identifier",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("transaction_id")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public MergeInsertIntoTableResponse numUpdatedRows(Long numUpdatedRows) {
    this.numUpdatedRows = numUpdatedRows;
    return this;
  }

  /**
   * Number of rows updated minimum: 0
   *
   * @return numUpdatedRows
   */
  @Min(0L)
  @Schema(
      name = "num_updated_rows",
      description = "Number of rows updated",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_updated_rows")
  public Long getNumUpdatedRows() {
    return numUpdatedRows;
  }

  public void setNumUpdatedRows(Long numUpdatedRows) {
    this.numUpdatedRows = numUpdatedRows;
  }

  public MergeInsertIntoTableResponse numInsertedRows(Long numInsertedRows) {
    this.numInsertedRows = numInsertedRows;
    return this;
  }

  /**
   * Number of rows inserted minimum: 0
   *
   * @return numInsertedRows
   */
  @Min(0L)
  @Schema(
      name = "num_inserted_rows",
      description = "Number of rows inserted",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_inserted_rows")
  public Long getNumInsertedRows() {
    return numInsertedRows;
  }

  public void setNumInsertedRows(Long numInsertedRows) {
    this.numInsertedRows = numInsertedRows;
  }

  public MergeInsertIntoTableResponse numDeletedRows(Long numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
    return this;
  }

  /**
   * Number of rows deleted (typically 0 for merge insert) minimum: 0
   *
   * @return numDeletedRows
   */
  @Min(0L)
  @Schema(
      name = "num_deleted_rows",
      description = "Number of rows deleted (typically 0 for merge insert)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_deleted_rows")
  public Long getNumDeletedRows() {
    return numDeletedRows;
  }

  public void setNumDeletedRows(Long numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
  }

  public MergeInsertIntoTableResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
   *
   * @return version
   */
  @Min(0L)
  @Schema(
      name = "version",
      description = "The commit version associated with the operation",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
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
    return Objects.equals(this.transactionId, mergeInsertIntoTableResponse.transactionId)
        && Objects.equals(this.numUpdatedRows, mergeInsertIntoTableResponse.numUpdatedRows)
        && Objects.equals(this.numInsertedRows, mergeInsertIntoTableResponse.numInsertedRows)
        && Objects.equals(this.numDeletedRows, mergeInsertIntoTableResponse.numDeletedRows)
        && Objects.equals(this.version, mergeInsertIntoTableResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, numUpdatedRows, numInsertedRows, numDeletedRows, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MergeInsertIntoTableResponse {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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
}
