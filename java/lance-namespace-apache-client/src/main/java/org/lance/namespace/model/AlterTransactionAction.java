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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * A single action that could be performed to alter a transaction. This action holds the model
 * definition for all types of specific actions models, this is to minimize difference and
 * compatibility issue across codegen in different languages. When used, only one of the actions
 * should be non-null for each action. If you would like to perform multiple actions, set a list of
 * actions in the AlterTransactionRequest.
 */
@JsonPropertyOrder({
  AlterTransactionAction.JSON_PROPERTY_SET_STATUS_ACTION,
  AlterTransactionAction.JSON_PROPERTY_SET_PROPERTY_ACTION,
  AlterTransactionAction.JSON_PROPERTY_UNSET_PROPERTY_ACTION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionAction {
  public static final String JSON_PROPERTY_SET_STATUS_ACTION = "setStatusAction";
  @javax.annotation.Nullable private AlterTransactionSetStatus setStatusAction;

  public static final String JSON_PROPERTY_SET_PROPERTY_ACTION = "setPropertyAction";
  @javax.annotation.Nullable private AlterTransactionSetProperty setPropertyAction;

  public static final String JSON_PROPERTY_UNSET_PROPERTY_ACTION = "unsetPropertyAction";
  @javax.annotation.Nullable private AlterTransactionUnsetProperty unsetPropertyAction;

  public AlterTransactionAction() {}

  public AlterTransactionAction setStatusAction(
      @javax.annotation.Nullable AlterTransactionSetStatus setStatusAction) {

    this.setStatusAction = setStatusAction;
    return this;
  }

  /**
   * Get setStatusAction
   *
   * @return setStatusAction
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SET_STATUS_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public AlterTransactionSetStatus getSetStatusAction() {
    return setStatusAction;
  }

  @JsonProperty(JSON_PROPERTY_SET_STATUS_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSetStatusAction(
      @javax.annotation.Nullable AlterTransactionSetStatus setStatusAction) {
    this.setStatusAction = setStatusAction;
  }

  public AlterTransactionAction setPropertyAction(
      @javax.annotation.Nullable AlterTransactionSetProperty setPropertyAction) {

    this.setPropertyAction = setPropertyAction;
    return this;
  }

  /**
   * Get setPropertyAction
   *
   * @return setPropertyAction
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SET_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public AlterTransactionSetProperty getSetPropertyAction() {
    return setPropertyAction;
  }

  @JsonProperty(JSON_PROPERTY_SET_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSetPropertyAction(
      @javax.annotation.Nullable AlterTransactionSetProperty setPropertyAction) {
    this.setPropertyAction = setPropertyAction;
  }

  public AlterTransactionAction unsetPropertyAction(
      @javax.annotation.Nullable AlterTransactionUnsetProperty unsetPropertyAction) {

    this.unsetPropertyAction = unsetPropertyAction;
    return this;
  }

  /**
   * Get unsetPropertyAction
   *
   * @return unsetPropertyAction
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_UNSET_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public AlterTransactionUnsetProperty getUnsetPropertyAction() {
    return unsetPropertyAction;
  }

  @JsonProperty(JSON_PROPERTY_UNSET_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUnsetPropertyAction(
      @javax.annotation.Nullable AlterTransactionUnsetProperty unsetPropertyAction) {
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `setStatusAction` to the URL query string
    if (getSetStatusAction() != null) {
      joiner.add(getSetStatusAction().toUrlQueryString(prefix + "setStatusAction" + suffix));
    }

    // add `setPropertyAction` to the URL query string
    if (getSetPropertyAction() != null) {
      joiner.add(getSetPropertyAction().toUrlQueryString(prefix + "setPropertyAction" + suffix));
    }

    // add `unsetPropertyAction` to the URL query string
    if (getUnsetPropertyAction() != null) {
      joiner.add(
          getUnsetPropertyAction().toUrlQueryString(prefix + "unsetPropertyAction" + suffix));
    }

    return joiner.toString();
  }
}
