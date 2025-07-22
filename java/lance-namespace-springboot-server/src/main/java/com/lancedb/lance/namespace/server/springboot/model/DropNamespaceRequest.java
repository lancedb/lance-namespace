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
import java.util.List;
import java.util.Objects;

/** DropNamespaceRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DropNamespaceRequest {

  private String name;

  @Valid private List<String> parent = new ArrayList<>();

  /**
   * The mode for dropping a namespace, deciding the server behavior when the namespace to drop is
   * not found. - FAIL (default): the server must return 400 indicating the namespace to drop does
   * not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded.
   */
  public enum ModeEnum {
    SKIP("SKIP"),

    FAIL("FAIL");

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

  /**
   * The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain
   * any table or child namespace when drop is initiated. If tables are found, the server should
   * return error and not drop the namespace. - CASCADE: all tables and child namespaces in the
   * namespace are dropped before the namespace is dropped.
   */
  public enum BehaviorEnum {
    RESTRICT("RESTRICT"),

    CASCADE("CASCADE");

    private String value;

    BehaviorEnum(String value) {
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
    public static BehaviorEnum fromValue(String value) {
      for (BehaviorEnum b : BehaviorEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private BehaviorEnum behavior;

  public DropNamespaceRequest name(String name) {
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

  public DropNamespaceRequest parent(List<String> parent) {
    this.parent = parent;
    return this;
  }

  public DropNamespaceRequest addParentItem(String parentItem) {
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

  public DropNamespaceRequest mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

  /**
   * The mode for dropping a namespace, deciding the server behavior when the namespace to drop is
   * not found. - FAIL (default): the server must return 400 indicating the namespace to drop does
   * not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded.
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description =
          "The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. - FAIL (default): the server must return 400 indicating the namespace to drop does not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  public DropNamespaceRequest behavior(BehaviorEnum behavior) {
    this.behavior = behavior;
    return this;
  }

  /**
   * The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain
   * any table or child namespace when drop is initiated. If tables are found, the server should
   * return error and not drop the namespace. - CASCADE: all tables and child namespaces in the
   * namespace are dropped before the namespace is dropped.
   *
   * @return behavior
   */
  @Schema(
      name = "behavior",
      description =
          "The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - CASCADE: all tables and child namespaces in the namespace are dropped before the namespace is dropped. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("behavior")
  public BehaviorEnum getBehavior() {
    return behavior;
  }

  public void setBehavior(BehaviorEnum behavior) {
    this.behavior = behavior;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DropNamespaceRequest dropNamespaceRequest = (DropNamespaceRequest) o;
    return Objects.equals(this.name, dropNamespaceRequest.name)
        && Objects.equals(this.parent, dropNamespaceRequest.parent)
        && Objects.equals(this.mode, dropNamespaceRequest.mode)
        && Objects.equals(this.behavior, dropNamespaceRequest.behavior);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent, mode, behavior);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DropNamespaceRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    behavior: ").append(toIndentedString(behavior)).append("\n");
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
