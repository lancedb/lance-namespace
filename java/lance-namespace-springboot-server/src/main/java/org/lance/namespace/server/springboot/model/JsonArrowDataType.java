package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.JsonArrowField;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * JSON representation of an Apache Arrow DataType
 */

@Schema(name = "JsonArrowDataType", description = "JSON representation of an Apache Arrow DataType")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class JsonArrowDataType {

  @Valid
  private List<@Valid JsonArrowField> fields = new ArrayList<>();

  private Long length;

  private String type;

  public JsonArrowDataType() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public JsonArrowDataType(String type) {
    this.type = type;
  }

  public JsonArrowDataType fields(List<@Valid JsonArrowField> fields) {
    this.fields = fields;
    return this;
  }

  public JsonArrowDataType addFieldsItem(JsonArrowField fieldsItem) {
    if (this.fields == null) {
      this.fields = new ArrayList<>();
    }
    this.fields.add(fieldsItem);
    return this;
  }

  /**
   * Fields for complex types like Struct, Union, etc.
   * @return fields
   */
  @Valid 
  @Schema(name = "fields", description = "Fields for complex types like Struct, Union, etc.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fields")
  public List<@Valid JsonArrowField> getFields() {
    return fields;
  }

  public void setFields(List<@Valid JsonArrowField> fields) {
    this.fields = fields;
  }

  public JsonArrowDataType length(Long length) {
    this.length = length;
    return this;
  }

  /**
   * Length for fixed-size types
   * minimum: 0
   * @return length
   */
  @Min(0L) 
  @Schema(name = "length", description = "Length for fixed-size types", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("length")
  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }

  public JsonArrowDataType type(String type) {
    this.type = type;
    return this;
  }

  /**
   * The data type name
   * @return type
   */
  @NotNull 
  @Schema(name = "type", description = "The data type name", requiredMode = Schema.RequiredMode.REQUIRED)
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
    JsonArrowDataType jsonArrowDataType = (JsonArrowDataType) o;
    return Objects.equals(this.fields, jsonArrowDataType.fields) &&
        Objects.equals(this.length, jsonArrowDataType.length) &&
        Objects.equals(this.type, jsonArrowDataType.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fields, length, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonArrowDataType {\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

