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
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** GetNamespaceResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.13.0")
public class GetNamespaceResponse {

  private String namespace;

  @Valid private Map<String, String> properties = new HashMap<>();

  public GetNamespaceResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public GetNamespaceResponse(String namespace) {
    this.namespace = namespace;
  }

  public GetNamespaceResponse namespace(String namespace) {
    this.namespace = namespace;
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
  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public GetNamespaceResponse properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public GetNamespaceResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Properties stored on the namespace, if supported by the server. If the server does not support
   * namespace properties, it should return null for this field. If namespace properties are
   * supported, but none are set, it should return an empty object.
   *
   * @return properties
   */
  @Schema(
      name = "properties",
      example = "{owner=Ralph, created_at=1452120468}",
      description =
          "Properties stored on the namespace, if supported by the server. If the server does not support namespace properties, it should return null for this field. If namespace properties are supported, but none are set, it should return an empty object.",
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
    GetNamespaceResponse getNamespaceResponse = (GetNamespaceResponse) o;
    return Objects.equals(this.namespace, getNamespaceResponse.namespace)
        && Objects.equals(this.properties, getNamespaceResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespace, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetNamespaceResponse {\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
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
