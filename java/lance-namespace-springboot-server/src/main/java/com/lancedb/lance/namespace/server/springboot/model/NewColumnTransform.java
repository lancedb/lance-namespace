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
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** NewColumnTransform */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class NewColumnTransform {

  private String name;

  private String expression;

  public NewColumnTransform() {
    super();
  }

  /** Constructor with only required parameters */
  public NewColumnTransform(String name, String expression) {
    this.name = name;
    this.expression = expression;
  }

  public NewColumnTransform name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the new column
   *
   * @return name
   */
  @NotNull
  @Schema(
      name = "name",
      description = "Name of the new column",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public NewColumnTransform expression(String expression) {
    this.expression = expression;
    return this;
  }

  /**
   * SQL expression to compute the column value
   *
   * @return expression
   */
  @NotNull
  @Schema(
      name = "expression",
      description = "SQL expression to compute the column value",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("expression")
  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewColumnTransform newColumnTransform = (NewColumnTransform) o;
    return Objects.equals(this.name, newColumnTransform.name)
        && Objects.equals(this.expression, newColumnTransform.expression);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, expression);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewColumnTransform {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    expression: ").append(toIndentedString(expression)).append("\n");
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
