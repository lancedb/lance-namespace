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
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** AlterTableAlterColumnsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableAlterColumnsRequest {

  @Valid private List<String> id = new ArrayList<>();

  @Valid private List<@Valid ColumnAlteration> alterations = new ArrayList<>();

  public AlterTableAlterColumnsRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTableAlterColumnsRequest(List<@Valid ColumnAlteration> alterations) {
    this.alterations = alterations;
  }

  public AlterTableAlterColumnsRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTableAlterColumnsRequest addIdItem(String idItem) {
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

  public AlterTableAlterColumnsRequest alterations(List<@Valid ColumnAlteration> alterations) {
    this.alterations = alterations;
    return this;
  }

  public AlterTableAlterColumnsRequest addAlterationsItem(ColumnAlteration alterationsItem) {
    if (this.alterations == null) {
      this.alterations = new ArrayList<>();
    }
    this.alterations.add(alterationsItem);
    return this;
  }

  /**
   * List of column alterations to perform
   *
   * @return alterations
   */
  @NotNull
  @Valid
  @Schema(
      name = "alterations",
      description = "List of column alterations to perform",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("alterations")
  public List<@Valid ColumnAlteration> getAlterations() {
    return alterations;
  }

  public void setAlterations(List<@Valid ColumnAlteration> alterations) {
    this.alterations = alterations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableAlterColumnsRequest alterTableAlterColumnsRequest = (AlterTableAlterColumnsRequest) o;
    return Objects.equals(this.id, alterTableAlterColumnsRequest.id)
        && Objects.equals(this.alterations, alterTableAlterColumnsRequest.alterations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, alterations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableAlterColumnsRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    alterations: ").append(toIndentedString(alterations)).append("\n");
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
