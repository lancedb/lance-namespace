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
import java.util.Objects;

/**
 * Result of a single operation within a batch commit. Each result corresponds to one operation in
 * the request, in the same order. Exactly one of the result fields will be set.
 */
@Schema(
    name = "CommitTableResult",
    description =
        "Result of a single operation within a batch commit. Each result corresponds to one operation in the request, in the same order. Exactly one of the result fields will be set. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CommitTableResult {

  private DeclareTableResponse declareTable;

  private CreateTableVersionResponse createTableVersion;

  private BatchDeleteTableVersionsResponse deleteTableVersions;

  private DeregisterTableResponse deregisterTable;

  public CommitTableResult declareTable(DeclareTableResponse declareTable) {
    this.declareTable = declareTable;
    return this;
  }

  /**
   * Result of a DeclareTable operation
   *
   * @return declareTable
   */
  @Valid
  @Schema(
      name = "declare_table",
      description = "Result of a DeclareTable operation",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("declare_table")
  public DeclareTableResponse getDeclareTable() {
    return declareTable;
  }

  public void setDeclareTable(DeclareTableResponse declareTable) {
    this.declareTable = declareTable;
  }

  public CommitTableResult createTableVersion(CreateTableVersionResponse createTableVersion) {
    this.createTableVersion = createTableVersion;
    return this;
  }

  /**
   * Result of a CreateTableVersion operation
   *
   * @return createTableVersion
   */
  @Valid
  @Schema(
      name = "create_table_version",
      description = "Result of a CreateTableVersion operation",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("create_table_version")
  public CreateTableVersionResponse getCreateTableVersion() {
    return createTableVersion;
  }

  public void setCreateTableVersion(CreateTableVersionResponse createTableVersion) {
    this.createTableVersion = createTableVersion;
  }

  public CommitTableResult deleteTableVersions(
      BatchDeleteTableVersionsResponse deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
    return this;
  }

  /**
   * Result of a DeleteTableVersions operation
   *
   * @return deleteTableVersions
   */
  @Valid
  @Schema(
      name = "delete_table_versions",
      description = "Result of a DeleteTableVersions operation",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("delete_table_versions")
  public BatchDeleteTableVersionsResponse getDeleteTableVersions() {
    return deleteTableVersions;
  }

  public void setDeleteTableVersions(BatchDeleteTableVersionsResponse deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
  }

  public CommitTableResult deregisterTable(DeregisterTableResponse deregisterTable) {
    this.deregisterTable = deregisterTable;
    return this;
  }

  /**
   * Result of a DeregisterTable operation
   *
   * @return deregisterTable
   */
  @Valid
  @Schema(
      name = "deregister_table",
      description = "Result of a DeregisterTable operation",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deregister_table")
  public DeregisterTableResponse getDeregisterTable() {
    return deregisterTable;
  }

  public void setDeregisterTable(DeregisterTableResponse deregisterTable) {
    this.deregisterTable = deregisterTable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitTableResult commitTableResult = (CommitTableResult) o;
    return Objects.equals(this.declareTable, commitTableResult.declareTable)
        && Objects.equals(this.createTableVersion, commitTableResult.createTableVersion)
        && Objects.equals(this.deleteTableVersions, commitTableResult.deleteTableVersions)
        && Objects.equals(this.deregisterTable, commitTableResult.deregisterTable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(declareTable, createTableVersion, deleteTableVersions, deregisterTable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitTableResult {\n");
    sb.append("    declareTable: ").append(toIndentedString(declareTable)).append("\n");
    sb.append("    createTableVersion: ").append(toIndentedString(createTableVersion)).append("\n");
    sb.append("    deleteTableVersions: ")
        .append(toIndentedString(deleteTableVersions))
        .append("\n");
    sb.append("    deregisterTable: ").append(toIndentedString(deregisterTable)).append("\n");
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
