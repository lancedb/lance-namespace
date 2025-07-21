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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** CreateTableIndexResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableIndexResponse {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private String location;

  @Valid private Map<String, String> properties = new HashMap<>();

  public CreateTableIndexResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTableIndexResponse(String name, List<String> namespace, String location) {
    this.name = name;
    this.namespace = namespace;
    this.location = location;
  }

  public CreateTableIndexResponse name(String name) {
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

  public CreateTableIndexResponse namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public CreateTableIndexResponse addNamespaceItem(String namespaceItem) {
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

  public CreateTableIndexResponse location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Table location (usually empty)
   *
   * @return location
   */
  @NotNull
  @Schema(
      name = "location",
      description = "Table location (usually empty)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public CreateTableIndexResponse properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public CreateTableIndexResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Additional properties (usually empty)
   *
   * @return properties
   */
  @Schema(
      name = "properties",
      description = "Additional properties (usually empty)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableIndexResponse createTableIndexResponse = (CreateTableIndexResponse) o;
    return Objects.equals(this.name, createTableIndexResponse.name)
        && Objects.equals(this.namespace, createTableIndexResponse.namespace)
        && Objects.equals(this.location, createTableIndexResponse.location)
        && Objects.equals(this.properties, createTableIndexResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, location, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableIndexResponse {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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
