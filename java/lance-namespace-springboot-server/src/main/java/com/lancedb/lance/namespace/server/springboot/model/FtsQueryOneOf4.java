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
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** FtsQueryOneOf4 */
@JsonTypeName("FtsQuery_oneOf_4")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf4 implements FtsQuery {

  private BooleanQuery _boolean;

  public FtsQueryOneOf4() {
    super();
  }

  /** Constructor with only required parameters */
  public FtsQueryOneOf4(BooleanQuery _boolean) {
    this._boolean = _boolean;
  }

  public FtsQueryOneOf4 _boolean(BooleanQuery _boolean) {
    this._boolean = _boolean;
    return this;
  }

  /**
   * Get _boolean
   *
   * @return _boolean
   */
  @NotNull
  @Valid
  @Schema(name = "boolean", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("boolean")
  public BooleanQuery getBoolean() {
    return _boolean;
  }

  public void setBoolean(BooleanQuery _boolean) {
    this._boolean = _boolean;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FtsQueryOneOf4 ftsQueryOneOf4 = (FtsQueryOneOf4) o;
    return Objects.equals(this._boolean, ftsQueryOneOf4._boolean);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_boolean);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FtsQueryOneOf4 {\n");
    sb.append("    _boolean: ").append(toIndentedString(_boolean)).append("\n");
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
