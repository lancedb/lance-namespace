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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** AlterTransactionSetProperty */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionSetProperty {

  private String key;

  private String value;

  private String mode;

  public AlterTransactionSetProperty key(String key) {
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

  public AlterTransactionSetProperty value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   *
   * @return value
   */
  @Schema(name = "value", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("value")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public AlterTransactionSetProperty mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * The behavior if the property key already exists. Case insensitive, supports both PascalCase and
   * snake_case. Valid values are: - Overwrite (default): overwrite the existing value with the
   * provided value - Fail: fail the entire operation - Skip: keep the existing value and skip
   * setting the provided value
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description =
          "The behavior if the property key already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Overwrite (default): overwrite the existing value with the provided value - Fail: fail the entire operation - Skip: keep the existing value and skip setting the provided value ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
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
    AlterTransactionSetProperty alterTransactionSetProperty = (AlterTransactionSetProperty) o;
    return Objects.equals(this.key, alterTransactionSetProperty.key)
        && Objects.equals(this.value, alterTransactionSetProperty.value)
        && Objects.equals(this.mode, alterTransactionSetProperty.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionSetProperty {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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
