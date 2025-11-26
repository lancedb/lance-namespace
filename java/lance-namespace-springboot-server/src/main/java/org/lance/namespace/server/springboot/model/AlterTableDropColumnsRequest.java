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

/** AlterTableDropColumnsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableDropColumnsRequest {

  @Valid private List<String> id = new ArrayList<>();

  @Valid private List<String> columns = new ArrayList<>();

  public AlterTableDropColumnsRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTableDropColumnsRequest(List<String> columns) {
    this.columns = columns;
  }

  public AlterTableDropColumnsRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTableDropColumnsRequest addIdItem(String idItem) {
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

  public AlterTableDropColumnsRequest columns(List<String> columns) {
    this.columns = columns;
    return this;
  }

  public AlterTableDropColumnsRequest addColumnsItem(String columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<>();
    }
    this.columns.add(columnsItem);
    return this;
  }

  /**
   * Names of columns to drop
   *
   * @return columns
   */
  @NotNull
  @Schema(
      name = "columns",
      description = "Names of columns to drop",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("columns")
  public List<String> getColumns() {
    return columns;
  }

  public void setColumns(List<String> columns) {
    this.columns = columns;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableDropColumnsRequest alterTableDropColumnsRequest = (AlterTableDropColumnsRequest) o;
    return Objects.equals(this.id, alterTableDropColumnsRequest.id)
        && Objects.equals(this.columns, alterTableDropColumnsRequest.columns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, columns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableDropColumnsRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
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
