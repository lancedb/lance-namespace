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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** AlterTransactionUnsetProperty */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionUnsetProperty implements AlterTransactionAction {

  private String type = "UnsetProperty";

  private String key;

  /** the behavior if the property key to unset does not exist */
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

  private ModeEnum mode = ModeEnum.SKIP;

  public AlterTransactionUnsetProperty() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTransactionUnsetProperty(String type) {
    this.type = type;
  }

  public AlterTransactionUnsetProperty type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @NotNull
  @Pattern(regexp = "^UnsetProperty$")
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public AlterTransactionUnsetProperty key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   *
   * @return key
   */
  @Schema(name = "key", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("key")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public AlterTransactionUnsetProperty mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

  /**
   * the behavior if the property key to unset does not exist
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description = "the behavior if the property key to unset does not exist",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionUnsetProperty alterTransactionUnsetProperty = (AlterTransactionUnsetProperty) o;
    return Objects.equals(this.type, alterTransactionUnsetProperty.type)
        && Objects.equals(this.key, alterTransactionUnsetProperty.key)
        && Objects.equals(this.mode, alterTransactionUnsetProperty.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, key, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionUnsetProperty {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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
