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
package com.lancedb.lance.catalog.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** ListNamespacesResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ListNamespacesResponse {

  @Valid private Set<String> namespaces = new LinkedHashSet<>();

  public ListNamespacesResponse namespaces(Set<String> namespaces) {
    this.namespaces = namespaces;
    return this;
  }

  public ListNamespacesResponse addNamespacesItem(String namespacesItem) {
    if (this.namespaces == null) {
      this.namespaces = new LinkedHashSet<>();
    }
    this.namespaces.add(namespacesItem);
    return this;
  }

  /**
   * An array of namespace names in the catalog.
   *
   * @return namespaces
   */
  @Schema(
      name = "namespaces",
      description = "An array of namespace names in the catalog.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("namespaces")
  public Set<String> getNamespaces() {
    return namespaces;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  public void setNamespaces(Set<String> namespaces) {
    this.namespaces = namespaces;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListNamespacesResponse listNamespacesResponse = (ListNamespacesResponse) o;
    return Objects.equals(this.namespaces, listNamespacesResponse.namespaces);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespaces);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListNamespacesResponse {\n");
    sb.append("    namespaces: ").append(toIndentedString(namespaces)).append("\n");
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
