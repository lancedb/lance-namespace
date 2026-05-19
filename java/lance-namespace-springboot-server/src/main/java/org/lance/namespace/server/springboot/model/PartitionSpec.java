package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.PartitionField;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Partition spec definition
 */

@Schema(name = "PartitionSpec", description = "Partition spec definition")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class PartitionSpec {

  private Integer id;

  @Valid
  private List<@Valid PartitionField> fields = new ArrayList<>();

  public PartitionSpec() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PartitionSpec(Integer id, List<@Valid PartitionField> fields) {
    this.id = id;
    this.fields = fields;
  }

  public PartitionSpec id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * The spec version ID
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "1", description = "The spec version ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PartitionSpec fields(List<@Valid PartitionField> fields) {
    this.fields = fields;
    return this;
  }

  public PartitionSpec addFieldsItem(PartitionField fieldsItem) {
    if (this.fields == null) {
      this.fields = new ArrayList<>();
    }
    this.fields.add(fieldsItem);
    return this;
  }

  /**
   * Array of partition field definitions
   * @return fields
   */
  @NotNull @Valid 
  @Schema(name = "fields", description = "Array of partition field definitions", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fields")
  public List<@Valid PartitionField> getFields() {
    return fields;
  }

  public void setFields(List<@Valid PartitionField> fields) {
    this.fields = fields;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartitionSpec partitionSpec = (PartitionSpec) o;
    return Objects.equals(this.id, partitionSpec.id) &&
        Objects.equals(this.fields, partitionSpec.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, fields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartitionSpec {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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

