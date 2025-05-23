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
import java.util.Objects;

/** AlterTransactionUnsetProperty */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionUnsetProperty {

  private String key;

  private UnsetPropertyMode mode;

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

  public AlterTransactionUnsetProperty mode(UnsetPropertyMode mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   *
   * @return mode
   */
  @Valid
  @Schema(name = "mode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public UnsetPropertyMode getMode() {
    return mode;
  }

  public void setMode(UnsetPropertyMode mode) {
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
    return Objects.equals(this.key, alterTransactionUnsetProperty.key)
        && Objects.equals(this.mode, alterTransactionUnsetProperty.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionUnsetProperty {\n");
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
