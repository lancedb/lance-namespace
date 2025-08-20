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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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

/** CreateNamespaceRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateNamespaceRequest {

  @Valid private List<String> id = new ArrayList<>();

  /**
   * There are three modes when trying to create a namespace, to differentiate the behavior when a
   * namespace of the same name already exists: * create: the operation fails with 409. * exist_ok:
   * the operation succeeds and the existing namespace is kept. * overwrite: the existing namespace
   * is dropped and a new empty namespace with this name is created.
   */
  public enum ModeEnum {
    CREATE("create"),

    EXIST_OK("exist_ok"),

    OVERWRITE("overwrite");

    private String value;

    ModeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ModeEnum fromValue(String value) {
      for (ModeEnum b : ModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ModeEnum mode;

  @Valid private Map<String, String> properties = new HashMap<>();

  public CreateNamespaceRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public CreateNamespaceRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public CreateNamespaceRequest mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

  /**
   * There are three modes when trying to create a namespace, to differentiate the behavior when a
   * namespace of the same name already exists: * create: the operation fails with 409. * exist_ok:
   * the operation succeeds and the existing namespace is kept. * overwrite: the existing namespace
   * is dropped and a new empty namespace with this name is created.
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description =
          "There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * create: the operation fails with 409.   * exist_ok: the operation succeeds and the existing namespace is kept.   * overwrite: the existing namespace is dropped and a new empty namespace with this name is created. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  public CreateNamespaceRequest properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public CreateNamespaceRequest putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   *
   * @return properties
   */
  @Schema(name = "properties", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    CreateNamespaceRequest createNamespaceRequest = (CreateNamespaceRequest) o;
    return Objects.equals(this.id, createNamespaceRequest.id)
        && Objects.equals(this.mode, createNamespaceRequest.mode)
        && Objects.equals(this.properties, createNamespaceRequest.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, mode, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNamespaceRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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
