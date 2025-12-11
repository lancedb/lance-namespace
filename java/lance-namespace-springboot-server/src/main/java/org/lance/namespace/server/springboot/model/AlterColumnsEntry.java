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
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** AlterColumnsEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterColumnsEntry {

  private String path;

  private Object dataType;

  private String rename;

  private Boolean nullable;

  private AlterVirtualColumnEntry virtualColumn;

  public AlterColumnsEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterColumnsEntry(String path, Object dataType) {
    this.path = path;
    this.dataType = dataType;
  }

  public AlterColumnsEntry path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Column path to alter
   *
   * @return path
   */
  @NotNull
  @Schema(
      name = "path",
      description = "Column path to alter",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public AlterColumnsEntry dataType(Object dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * New data type for the column using JSON representation (optional)
   *
   * @return dataType
   */
  @NotNull
  @Schema(
      name = "data_type",
      description = "New data type for the column using JSON representation (optional)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("data_type")
  public Object getDataType() {
    return dataType;
  }

  public void setDataType(Object dataType) {
    this.dataType = dataType;
  }

  public AlterColumnsEntry rename(String rename) {
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

  public AlterColumnsEntry nullable(Boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  /**
   * Whether the column should be nullable (optional)
   *
   * @return nullable
   */
  @Schema(
      name = "nullable",
      description = "Whether the column should be nullable (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nullable")
  public Boolean getNullable() {
    return nullable;
  }

  public void setNullable(Boolean nullable) {
    this.nullable = nullable;
  }

  public AlterColumnsEntry virtualColumn(AlterVirtualColumnEntry virtualColumn) {
    this.virtualColumn = virtualColumn;
    return this;
  }

  /**
   * Virtual column alterations (optional)
   *
   * @return virtualColumn
   */
  @Valid
  @Schema(
      name = "virtual_column",
      description = "Virtual column alterations (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("virtual_column")
  public AlterVirtualColumnEntry getVirtualColumn() {
    return virtualColumn;
  }

  public void setVirtualColumn(AlterVirtualColumnEntry virtualColumn) {
    this.virtualColumn = virtualColumn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterColumnsEntry alterColumnsEntry = (AlterColumnsEntry) o;
    return Objects.equals(this.path, alterColumnsEntry.path)
        && Objects.equals(this.dataType, alterColumnsEntry.dataType)
        && Objects.equals(this.rename, alterColumnsEntry.rename)
        && Objects.equals(this.nullable, alterColumnsEntry.nullable)
        && Objects.equals(this.virtualColumn, alterColumnsEntry.virtualColumn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, dataType, rename, nullable, virtualColumn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterColumnsEntry {\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    rename: ").append(toIndentedString(rename)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    virtualColumn: ").append(toIndentedString(virtualColumn)).append("\n");
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
