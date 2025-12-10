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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** RenameTableRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class RenameTableRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String newTableName;

  @Valid private List<String> newNamespaceId = new ArrayList<>();

  public RenameTableRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public RenameTableRequest(String newTableName) {
    this.newTableName = newTableName;
  }

  public RenameTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public RenameTableRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * The table identifier
   *
   * @return id
   */
  @Schema(
      name = "id",
      description = "The table identifier",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public RenameTableRequest newTableName(String newTableName) {
    this.newTableName = newTableName;
    return this;
  }

  /**
   * New name for the table
   *
   * @return newTableName
   */
  @NotNull
  @Schema(
      name = "new_table_name",
      description = "New name for the table",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("new_table_name")
  public String getNewTableName() {
    return newTableName;
  }

  public void setNewTableName(String newTableName) {
    this.newTableName = newTableName;
  }

  public RenameTableRequest newNamespaceId(List<String> newNamespaceId) {
    this.newNamespaceId = newNamespaceId;
    return this;
  }

  public RenameTableRequest addNewNamespaceIdItem(String newNamespaceIdItem) {
    if (this.newNamespaceId == null) {
      this.newNamespaceId = new ArrayList<>();
    }
    this.newNamespaceId.add(newNamespaceIdItem);
    return this;
  }

  /**
   * New namespace identifier to move the table to (optional, if not specified the table stays in
   * the same namespace)
   *
   * @return newNamespaceId
   */
  @Schema(
      name = "new_namespace_id",
      description =
          "New namespace identifier to move the table to (optional, if not specified the table stays in the same namespace)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("new_namespace_id")
  public List<String> getNewNamespaceId() {
    return newNamespaceId;
  }

  public void setNewNamespaceId(List<String> newNamespaceId) {
    this.newNamespaceId = newNamespaceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RenameTableRequest renameTableRequest = (RenameTableRequest) o;
    return Objects.equals(this.id, renameTableRequest.id)
        && Objects.equals(this.newTableName, renameTableRequest.newTableName)
        && Objects.equals(this.newNamespaceId, renameTableRequest.newNamespaceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, newTableName, newNamespaceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RenameTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    newTableName: ").append(toIndentedString(newTableName)).append("\n");
    sb.append("    newNamespaceId: ").append(toIndentedString(newNamespaceId)).append("\n");
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
