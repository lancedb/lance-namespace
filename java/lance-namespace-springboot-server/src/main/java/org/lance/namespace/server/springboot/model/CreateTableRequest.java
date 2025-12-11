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

/** Request for creating a table, excluding the Arrow IPC stream. */
@Schema(
    name = "CreateTableRequest",
    description = "Request for creating a table, excluding the Arrow IPC stream. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String mode;

  public CreateTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public CreateTableRequest addIdItem(String idItem) {
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

  public CreateTableRequest mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * There are three modes when trying to create a table, to differentiate the behavior when a table
   * of the same name already exists. Case insensitive, supports both PascalCase and snake_case.
   * Valid values are: * Create: the operation fails with 409. * ExistOk: the operation succeeds and
   * the existing table is kept. * Overwrite: the existing table is dropped and a new table with
   * this name is created.
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description =
          "There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing table is kept.   * Overwrite: the existing table is dropped and a new table with this name is created. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableRequest createTableRequest = (CreateTableRequest) o;
    return Objects.equals(this.id, createTableRequest.id)
        && Objects.equals(this.mode, createTableRequest.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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
