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

/** DescribeTableRequestV2 */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableRequestV2 {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  public DescribeTableRequestV2() {
    super();
  }

  /** Constructor with only required parameters */
  public DescribeTableRequestV2(String name, List<String> namespace) {
    this.name = name;
    this.namespace = namespace;
  }

  public DescribeTableRequestV2 name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @NotNull
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DescribeTableRequestV2 namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public DescribeTableRequestV2 addNamespaceItem(String namespaceItem) {
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
  @NotNull
  @Schema(name = "namespace", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableRequestV2 describeTableRequestV2 = (DescribeTableRequestV2) o;
    return Objects.equals(this.name, describeTableRequestV2.name)
        && Objects.equals(this.namespace, describeTableRequestV2.namespace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableRequestV2 {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
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
