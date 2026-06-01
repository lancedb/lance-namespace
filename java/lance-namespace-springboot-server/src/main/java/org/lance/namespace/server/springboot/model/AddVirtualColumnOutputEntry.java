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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** AddVirtualColumnOutputEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AddVirtualColumnOutputEntry {

  private String column;

  private String structField;

  private Object dataType;

  private Boolean nullable;

  @Valid private Map<String, String> metadata = new HashMap<>();

  public AddVirtualColumnOutputEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public AddVirtualColumnOutputEntry(
      String column, String structField, Object dataType, Boolean nullable) {
    this.column = column;
    this.structField = structField;
    this.dataType = dataType;
    this.nullable = nullable;
  }

  public AddVirtualColumnOutputEntry column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Physical output column name
   *
   * @return column
   */
  @NotNull
  @Schema(
      name = "column",
      description = "Physical output column name",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public AddVirtualColumnOutputEntry structField(String structField) {
    this.structField = structField;
    return this;
  }

  /**
   * Field name in the UDF output struct
   *
   * @return structField
   */
  @NotNull
  @Schema(
      name = "struct_field",
      description = "Field name in the UDF output struct",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("struct_field")
  public String getStructField() {
    return structField;
  }

  public void setStructField(String structField) {
    this.structField = structField;
  }

  public AddVirtualColumnOutputEntry dataType(Object dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the output column using JSON representation
   *
   * @return dataType
   */
  @NotNull
  @Schema(
      name = "data_type",
      description = "Data type of the output column using JSON representation",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("data_type")
  public Object getDataType() {
    return dataType;
  }

  public void setDataType(Object dataType) {
    this.dataType = dataType;
  }

  public AddVirtualColumnOutputEntry nullable(Boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  /**
   * Whether the output column is nullable
   *
   * @return nullable
   */
  @NotNull
  @Schema(
      name = "nullable",
      description = "Whether the output column is nullable",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nullable")
  public Boolean getNullable() {
    return nullable;
  }

  public void setNullable(Boolean nullable) {
    this.nullable = nullable;
  }

  public AddVirtualColumnOutputEntry metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public AddVirtualColumnOutputEntry putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * User-supplied output field metadata (string key-value pairs)
   *
   * @return metadata
   */
  @Schema(
      name = "metadata",
      description = "User-supplied output field metadata (string key-value pairs)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddVirtualColumnOutputEntry addVirtualColumnOutputEntry = (AddVirtualColumnOutputEntry) o;
    return Objects.equals(this.column, addVirtualColumnOutputEntry.column)
        && Objects.equals(this.structField, addVirtualColumnOutputEntry.structField)
        && Objects.equals(this.dataType, addVirtualColumnOutputEntry.dataType)
        && Objects.equals(this.nullable, addVirtualColumnOutputEntry.nullable)
        && Objects.equals(this.metadata, addVirtualColumnOutputEntry.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, structField, dataType, nullable, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddVirtualColumnOutputEntry {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    structField: ").append(toIndentedString(structField)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
