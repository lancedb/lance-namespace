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

/** CountTableRowsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CountTableRowsRequest {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private Long version;

  private String filter;

  public CountTableRowsRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CountTableRowsRequest namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public CountTableRowsRequest addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * Get namespace
   *
   * @return namespace
   */
  @Schema(name = "namespace", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
  }

  public CountTableRowsRequest version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Version of the table to describe. If not specified, server should resolve it to the latest
   * version. minimum: 0
   *
   * @return version
   */
  @Min(0L)
  @Schema(
      name = "version",
      description =
          "Version of the table to describe. If not specified, server should resolve it to the latest version. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public CountTableRowsRequest filter(String filter) {
    this.filter = filter;
    return this;
  }

  /**
   * SQL filter expression to be applied
   *
   * @return filter
   */
  @Schema(
      name = "filter",
      description = "SQL filter expression to be applied ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filter")
  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CountTableRowsRequest countTableRowsRequest = (CountTableRowsRequest) o;
    return Objects.equals(this.name, countTableRowsRequest.name)
        && Objects.equals(this.namespace, countTableRowsRequest.namespace)
        && Objects.equals(this.version, countTableRowsRequest.version)
        && Objects.equals(this.filter, countTableRowsRequest.filter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, version, filter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CountTableRowsRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
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
