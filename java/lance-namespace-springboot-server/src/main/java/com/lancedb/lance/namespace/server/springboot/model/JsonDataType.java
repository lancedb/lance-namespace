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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** JSON representation of an Apache Arrow DataType */
@Schema(name = "JsonDataType", description = "JSON representation of an Apache Arrow DataType")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class JsonDataType {

  @Valid private List<@Valid JsonField> fields = new ArrayList<>();

  private Long length;

  private String type;

  public JsonDataType() {
    super();
  }

  /** Constructor with only required parameters */
  public JsonDataType(String type) {
    this.type = type;
  }

  public JsonDataType fields(List<@Valid JsonField> fields) {
    this.fields = fields;
    return this;
  }

  public JsonDataType addFieldsItem(JsonField fieldsItem) {
    if (this.fields == null) {
      this.fields = new ArrayList<>();
    }
    this.fields.add(fieldsItem);
    return this;
  }

  /**
   * Fields for complex types like Struct, Union, etc.
   *
   * @return fields
   */
  @Valid
  @Schema(
      name = "fields",
      description = "Fields for complex types like Struct, Union, etc.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fields")
  public List<@Valid JsonField> getFields() {
    return fields;
  }

  public void setFields(List<@Valid JsonField> fields) {
    this.fields = fields;
  }

  public JsonDataType length(Long length) {
    this.length = length;
    return this;
  }

  /**
   * Length for fixed-size types minimum: 0
   *
   * @return length
   */
  @Min(0L)
  @Schema(
      name = "length",
      description = "Length for fixed-size types",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("length")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public JsonDataType type(String type) {
    this.type = type;
    return this;
  }

  /**
   * The data type name
   *
   * @return type
   */
  @NotNull
  @Schema(
      name = "type",
      description = "The data type name",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonDataType jsonDataType = (JsonDataType) o;
    return Objects.equals(this.fields, jsonDataType.fields)
        && Objects.equals(this.length, jsonDataType.length)
        && Objects.equals(this.type, jsonDataType.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fields, length, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonDataType {\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
