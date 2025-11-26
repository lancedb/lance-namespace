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

/** ColumnAlteration */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ColumnAlteration {

  private String column;

  private String rename;

  private String castTo;

  public ColumnAlteration() {
    super();
  }

  /** Constructor with only required parameters */
  public ColumnAlteration(String column) {
    this.column = column;
  }

  public ColumnAlteration column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Name of the column to alter
   *
   * @return column
   */
  @NotNull
  @Schema(
      name = "column",
      description = "Name of the column to alter",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public ColumnAlteration rename(String rename) {
    this.rename = rename;
    return this;
  }

  /**
   * New name for the column (optional)
   *
   * @return rename
   */
  @Schema(
      name = "rename",
      description = "New name for the column (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("rename")
  public String getRename() {
    return rename;
  }

  public void setRename(String rename) {
    this.rename = rename;
  }

  public ColumnAlteration castTo(String castTo) {
    this.castTo = castTo;
    return this;
  }

  /**
   * New data type to cast the column to (optional)
   *
   * @return castTo
   */
  @Schema(
      name = "cast_to",
      description = "New data type to cast the column to (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cast_to")
  public String getCastTo() {
    return castTo;
  }

  public void setCastTo(String castTo) {
    this.castTo = castTo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnAlteration columnAlteration = (ColumnAlteration) o;
    return Objects.equals(this.column, columnAlteration.column)
        && Objects.equals(this.rename, columnAlteration.rename)
        && Objects.equals(this.castTo, columnAlteration.castTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, rename, castTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ColumnAlteration {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    rename: ").append(toIndentedString(rename)).append("\n");
    sb.append("    castTo: ").append(toIndentedString(castTo)).append("\n");
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
