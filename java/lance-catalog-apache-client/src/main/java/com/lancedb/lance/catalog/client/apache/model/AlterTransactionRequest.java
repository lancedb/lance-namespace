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
package com.lancedb.lance.catalog.client.apache.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Alter a transaction with a list of actions. The server should either succeed and apply all
 * actions, or fail and apply no action.
 */
@JsonPropertyOrder({AlterTransactionRequest.JSON_PROPERTY_ACTIONS})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionRequest {
  public static final String JSON_PROPERTY_ACTIONS = "actions";
  @javax.annotation.Nonnull private List<AlterTransactionAction> actions = new ArrayList<>();

  public AlterTransactionRequest() {}

  public AlterTransactionRequest actions(
      @javax.annotation.Nonnull List<AlterTransactionAction> actions) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ACTIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<AlterTransactionAction> getActions() {
    return actions;
  }

  @JsonProperty(JSON_PROPERTY_ACTIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setActions(@javax.annotation.Nonnull List<AlterTransactionAction> actions) {
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
    return Objects.equals(this.actions, alterTransactionRequest.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionRequest {\n");
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

    // add `actions` to the URL query string
    if (getActions() != null) {
      for (int i = 0; i < getActions().size(); i++) {
        if (getActions().get(i) != null) {
          joiner.add(
              getActions()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%sactions%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}
