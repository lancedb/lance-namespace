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
import java.util.Objects;

/**
 * A single action that could be performed to alter a transaction. This action holds the model
 * definition for all types of specific actions models, this is to minimize difference and
 * compatibility issue across codegen in different languages. When used, only one of the actions
 * should be non-null for each action. If you would like to perform multiple actions, set a list of
 * actions in the AlterTransactionRequest.
 */
@Schema(
    name = "AlterTransactionAction",
    description =
        "A single action that could be performed to alter a transaction. This action holds the model definition for all types of specific actions models, this is to minimize difference and compatibility issue across codegen in different languages. When used, only one of the actions should be non-null for each action. If you would like to perform multiple actions, set a list of actions in the AlterTransactionRequest. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionAction {

  private AlterTransactionSetStatus setStatusAction;

  private AlterTransactionSetProperty setPropertyAction;

  private AlterTransactionUnsetProperty unsetPropertyAction;

  public AlterTransactionAction setStatusAction(AlterTransactionSetStatus setStatusAction) {
    this.setStatusAction = setStatusAction;
    return this;
  }

  /**
   * Get setStatusAction
   *
   * @return setStatusAction
   */
  @Valid
  @Schema(name = "setStatusAction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("setStatusAction")
  public AlterTransactionSetStatus getSetStatusAction() {
    return setStatusAction;
  }

  public void setSetStatusAction(AlterTransactionSetStatus setStatusAction) {
    this.setStatusAction = setStatusAction;
  }

  public AlterTransactionAction setPropertyAction(AlterTransactionSetProperty setPropertyAction) {
    this.setPropertyAction = setPropertyAction;
    return this;
  }

  /**
   * Get setPropertyAction
   *
   * @return setPropertyAction
   */
  @Valid
  @Schema(name = "setPropertyAction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("setPropertyAction")
  public AlterTransactionSetProperty getSetPropertyAction() {
    return setPropertyAction;
  }

  public void setSetPropertyAction(AlterTransactionSetProperty setPropertyAction) {
    this.setPropertyAction = setPropertyAction;
  }

  public AlterTransactionAction unsetPropertyAction(
      AlterTransactionUnsetProperty unsetPropertyAction) {
    this.unsetPropertyAction = unsetPropertyAction;
    return this;
  }

  /**
   * Get unsetPropertyAction
   *
   * @return unsetPropertyAction
   */
  @Valid
  @Schema(name = "unsetPropertyAction", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("unsetPropertyAction")
  public AlterTransactionUnsetProperty getUnsetPropertyAction() {
    return unsetPropertyAction;
  }

  public void setUnsetPropertyAction(AlterTransactionUnsetProperty unsetPropertyAction) {
    this.unsetPropertyAction = unsetPropertyAction;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionAction alterTransactionAction = (AlterTransactionAction) o;
    return Objects.equals(this.setStatusAction, alterTransactionAction.setStatusAction)
        && Objects.equals(this.setPropertyAction, alterTransactionAction.setPropertyAction)
        && Objects.equals(this.unsetPropertyAction, alterTransactionAction.unsetPropertyAction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(setStatusAction, setPropertyAction, unsetPropertyAction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionAction {\n");
    sb.append("    setStatusAction: ").append(toIndentedString(setStatusAction)).append("\n");
    sb.append("    setPropertyAction: ").append(toIndentedString(setPropertyAction)).append("\n");
    sb.append("    unsetPropertyAction: ")
        .append(toIndentedString(unsetPropertyAction))
        .append("\n");
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
