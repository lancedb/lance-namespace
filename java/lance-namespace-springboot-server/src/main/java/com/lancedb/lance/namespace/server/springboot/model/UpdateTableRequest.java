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

/** UpdateTableRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateTableRequest {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private String predicate;

  @Valid private List<List<String>> updates = new ArrayList<>();

  public UpdateTableRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateTableRequest(String name, List<String> namespace, List<List<String>> updates) {
    this.name = name;
    this.namespace = namespace;
    this.updates = updates;
  }

  public UpdateTableRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The table name
   *
   * @return name
   */
  @NotNull
  @Schema(
      name = "name",
      description = "The table name",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UpdateTableRequest namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public UpdateTableRequest addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * The namespace identifier
   *
   * @return namespace
   */
  @NotNull
  @Schema(
      name = "namespace",
      description = "The namespace identifier",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
  }

  public UpdateTableRequest predicate(String predicate) {
    this.predicate = predicate;
    return this;
  }

  /**
   * Optional SQL predicate to filter rows for update
   *
   * @return predicate
   */
  @Schema(
      name = "predicate",
      description = "Optional SQL predicate to filter rows for update",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("predicate")
  public String getPredicate() {
    return predicate;
  }

  public void setPredicate(String predicate) {
    this.predicate = predicate;
  }

  public UpdateTableRequest updates(List<List<String>> updates) {
    this.updates = updates;
    return this;
  }

  public UpdateTableRequest addUpdatesItem(List<String> updatesItem) {
    if (this.updates == null) {
      this.updates = new ArrayList<>();
    }
    this.updates.add(updatesItem);
    return this;
  }

  /**
   * List of column updates as [column_name, expression] pairs
   *
   * @return updates
   */
  @NotNull
  @Valid
  @Schema(
      name = "updates",
      description = "List of column updates as [column_name, expression] pairs",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updates")
  public List<List<String>> getUpdates() {
    return updates;
  }

  public void setUpdates(List<List<String>> updates) {
    this.updates = updates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTableRequest updateTableRequest = (UpdateTableRequest) o;
    return Objects.equals(this.name, updateTableRequest.name)
        && Objects.equals(this.namespace, updateTableRequest.namespace)
        && Objects.equals(this.predicate, updateTableRequest.predicate)
        && Objects.equals(this.updates, updateTableRequest.updates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, predicate, updates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    predicate: ").append(toIndentedString(predicate)).append("\n");
    sb.append("    updates: ").append(toIndentedString(updates)).append("\n");
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
