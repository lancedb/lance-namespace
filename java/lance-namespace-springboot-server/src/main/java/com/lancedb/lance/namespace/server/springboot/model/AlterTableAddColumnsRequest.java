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
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** AlterTableAddColumnsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableAddColumnsRequest {

  @Valid private List<String> id = new ArrayList<>();

  @Valid private List<@Valid NewColumnTransform> newColumns = new ArrayList<>();

  public AlterTableAddColumnsRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTableAddColumnsRequest(List<@Valid NewColumnTransform> newColumns) {
    this.newColumns = newColumns;
  }

  public AlterTableAddColumnsRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTableAddColumnsRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public AlterTableAddColumnsRequest newColumns(List<@Valid NewColumnTransform> newColumns) {
    this.newColumns = newColumns;
    return this;
  }

  public AlterTableAddColumnsRequest addNewColumnsItem(NewColumnTransform newColumnsItem) {
    if (this.newColumns == null) {
      this.newColumns = new ArrayList<>();
    }
    this.newColumns.add(newColumnsItem);
    return this;
  }

  /**
   * List of new columns to add
   *
   * @return newColumns
   */
  @NotNull
  @Valid
  @Schema(
      name = "new_columns",
      description = "List of new columns to add",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("new_columns")
  public List<@Valid NewColumnTransform> getNewColumns() {
    return newColumns;
  }

  public void setNewColumns(List<@Valid NewColumnTransform> newColumns) {
    this.newColumns = newColumns;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableAddColumnsRequest alterTableAddColumnsRequest = (AlterTableAddColumnsRequest) o;
    return Objects.equals(this.id, alterTableAddColumnsRequest.id)
        && Objects.equals(this.newColumns, alterTableAddColumnsRequest.newColumns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, newColumns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableAddColumnsRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    newColumns: ").append(toIndentedString(newColumns)).append("\n");
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
