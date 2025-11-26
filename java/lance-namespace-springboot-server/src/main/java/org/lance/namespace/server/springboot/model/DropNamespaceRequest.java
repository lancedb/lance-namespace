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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** DropNamespaceRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DropNamespaceRequest {

  @Valid private List<String> id = new ArrayList<>();

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

  public DropNamespaceRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public DropNamespaceRequest addIdItem(String idItem) {
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
    return Objects.equals(this.id, dropNamespaceRequest.id)
        && Objects.equals(this.mode, dropNamespaceRequest.mode)
        && Objects.equals(this.behavior, dropNamespaceRequest.behavior);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, mode, behavior);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DropNamespaceRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
