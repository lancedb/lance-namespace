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

/** DeleteFromTableRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DeleteFromTableRequest {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private String predicate;

  public DeleteFromTableRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public DeleteFromTableRequest(String name, List<String> namespace, String predicate) {
    this.name = name;
    this.namespace = namespace;
    this.predicate = predicate;
  }

  public DeleteFromTableRequest name(String name) {
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

  public DeleteFromTableRequest namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public DeleteFromTableRequest addNamespaceItem(String namespaceItem) {
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
    return Objects.equals(this.name, deleteFromTableRequest.name)
        && Objects.equals(this.namespace, deleteFromTableRequest.namespace)
        && Objects.equals(this.predicate, deleteFromTableRequest.predicate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, predicate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteFromTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
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
