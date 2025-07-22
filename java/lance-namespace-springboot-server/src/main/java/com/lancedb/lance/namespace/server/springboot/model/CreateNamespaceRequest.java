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

  private String name;

  @Valid private List<String> parent = new ArrayList<>();

  /** Gets or Sets mode */
  public enum ModeEnum {
    CREATE("CREATE"),

    EXIST_OK("EXIST_OK"),

    OVERWRITE("OVERWRITE");

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

  @Valid private Map<String, String> options = new HashMap<>();

  public CreateNamespaceRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateNamespaceRequest(ModeEnum mode) {
    this.mode = mode;
  }

  public CreateNamespaceRequest name(String name) {
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

  public CreateNamespaceRequest parent(List<String> parent) {
    this.parent = parent;
    return this;
  }

  public CreateNamespaceRequest addParentItem(String parentItem) {
    if (this.parent == null) {
      this.parent = new ArrayList<>();
    }
    this.parent.add(parentItem);
    return this;
  }

  /**
   * Get parent
   *
   * @return parent
   */
  @Schema(name = "parent", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parent")
  public List<String> getParent() {
    return parent;
  }

  public void setParent(List<String> parent) {
    this.parent = parent;
  }

  public CreateNamespaceRequest mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   *
   * @return mode
   */
  @NotNull
  @Schema(name = "mode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mode")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  public CreateNamespaceRequest options(Map<String, String> options) {
    this.options = options;
    return this;
  }

  public CreateNamespaceRequest putOptionsItem(String key, String optionsItem) {
    if (this.options == null) {
      this.options = new HashMap<>();
    }
    this.options.put(key, optionsItem);
    return this;
  }

  /**
   * Get options
   *
   * @return options
   */
  @Schema(name = "options", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("options")
  public Map<String, String> getOptions() {
    return options;
  }

  public void setOptions(Map<String, String> options) {
    this.options = options;
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
    return Objects.equals(this.name, createNamespaceRequest.name)
        && Objects.equals(this.parent, createNamespaceRequest.parent)
        && Objects.equals(this.mode, createNamespaceRequest.mode)
        && Objects.equals(this.options, createNamespaceRequest.options);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent, mode, options);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNamespaceRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
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
