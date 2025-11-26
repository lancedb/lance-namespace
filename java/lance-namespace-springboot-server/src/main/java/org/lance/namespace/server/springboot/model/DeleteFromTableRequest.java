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

/**
 * Delete data from table based on a SQL predicate. Returns the number of rows that were deleted.
 */
@Schema(
    name = "DeleteFromTableRequest",
    description =
        "Delete data from table based on a SQL predicate. Returns the number of rows that were deleted. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DeleteFromTableRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String predicate;

  public DeleteFromTableRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public DeleteFromTableRequest(String predicate) {
    this.predicate = predicate;
  }

  public DeleteFromTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public DeleteFromTableRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * The namespace identifier
   *
   * @return id
   */
  @Schema(
      name = "id",
      description = "The namespace identifier",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public DeleteFromTableRequest predicate(String predicate) {
    this.predicate = predicate;
    return this;
  }

  /**
   * SQL predicate to filter rows for deletion
   *
   * @return predicate
   */
  @NotNull
  @Schema(
      name = "predicate",
      description = "SQL predicate to filter rows for deletion",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("predicate")
  public String getPredicate() {
    return predicate;
  }

  public void setPredicate(String predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteFromTableRequest deleteFromTableRequest = (DeleteFromTableRequest) o;
    return Objects.equals(this.id, deleteFromTableRequest.id)
        && Objects.equals(this.predicate, deleteFromTableRequest.predicate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, predicate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteFromTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    predicate: ").append(toIndentedString(predicate)).append("\n");
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
