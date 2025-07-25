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

/**
 * Alter a transaction with a list of actions. The server should either succeed and apply all
 * actions, or fail and apply no action.
 */
@Schema(
    name = "AlterTransactionRequest",
    description =
        "Alter a transaction with a list of actions. The server should either succeed and apply all actions, or fail and apply no action. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionRequest {

  @Valid private List<String> id = new ArrayList<>();

  @Valid private List<@Valid AlterTransactionAction> actions = new ArrayList<>();

  public AlterTransactionRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTransactionRequest(List<@Valid AlterTransactionAction> actions) {
    this.actions = actions;
  }

  public AlterTransactionRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTransactionRequest addIdItem(String idItem) {
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

  public AlterTransactionRequest actions(List<@Valid AlterTransactionAction> actions) {
    this.actions = actions;
    return this;
  }

  public AlterTransactionRequest addActionsItem(AlterTransactionAction actionsItem) {
    if (this.actions == null) {
      this.actions = new ArrayList<>();
    }
    this.actions.add(actionsItem);
    return this;
  }

  /**
   * Get actions
   *
   * @return actions
   */
  @NotNull
  @Valid
  @Size(min = 1)
  @Schema(name = "actions", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("actions")
  public List<@Valid AlterTransactionAction> getActions() {
    return actions;
  }

  public void setActions(List<@Valid AlterTransactionAction> actions) {
    this.actions = actions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionRequest alterTransactionRequest = (AlterTransactionRequest) o;
    return Objects.equals(this.id, alterTransactionRequest.id)
        && Objects.equals(this.actions, alterTransactionRequest.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, actions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
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
