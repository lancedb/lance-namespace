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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Partition field definition */
@Schema(name = "PartitionField", description = "Partition field definition")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class PartitionField {

  private String fieldId;

  @Valid private List<Integer> sourceIds = new ArrayList<>();

  private PartitionTransform transform;

  private String expression;

  private JsonArrowDataType resultType;

  public PartitionField() {
    super();
  }

  /** Constructor with only required parameters */
  public PartitionField(String fieldId, List<Integer> sourceIds, JsonArrowDataType resultType) {
    this.fieldId = fieldId;
    this.sourceIds = sourceIds;
    this.resultType = resultType;
  }

  public PartitionField fieldId(String fieldId) {
    this.fieldId = fieldId;
    return this;
  }

  /**
   * Unique identifier for this partition field (must not be renamed)
   *
   * @return fieldId
   */
  @NotNull
  @Schema(
      name = "field_id",
      example = "event_year",
      description = "Unique identifier for this partition field (must not be renamed)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("field_id")
  public String getFieldId() {
    return fieldId;
  }

  public void setFieldId(String fieldId) {
    this.fieldId = fieldId;
  }

  public PartitionField sourceIds(List<Integer> sourceIds) {
    this.sourceIds = sourceIds;
    return this;
  }

  public PartitionField addSourceIdsItem(Integer sourceIdsItem) {
    if (this.sourceIds == null) {
      this.sourceIds = new ArrayList<>();
    }
    this.sourceIds.add(sourceIdsItem);
    return this;
  }

  /**
   * Field IDs of the source columns in the schema
   *
   * @return sourceIds
   */
  @NotNull
  @Schema(
      name = "source_ids",
      example = "[1]",
      description = "Field IDs of the source columns in the schema",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("source_ids")
  public List<Integer> getSourceIds() {
    return sourceIds;
  }

  public void setSourceIds(List<Integer> sourceIds) {
    this.sourceIds = sourceIds;
  }

  public PartitionField transform(PartitionTransform transform) {
    this.transform = transform;
    return this;
  }

  /**
   * Well-known partition transform. Exactly one of transform or expression must be specified.
   *
   * @return transform
   */
  @Valid
  @Schema(
      name = "transform",
      description =
          "Well-known partition transform. Exactly one of transform or expression must be specified.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("transform")
  public PartitionTransform getTransform() {
    return transform;
  }

  public void setTransform(PartitionTransform transform) {
    this.transform = transform;
  }

  public PartitionField expression(String expression) {
    this.expression = expression;
    return this;
  }

  /**
   * DataFusion SQL expression using col0, col1, ... as column references. Exactly one of transform
   * or expression must be specified.
   *
   * @return expression
   */
  @Schema(
      name = "expression",
      example = "date_part('year', col0)",
      description =
          "DataFusion SQL expression using col0, col1, ... as column references. Exactly one of transform or expression must be specified.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("expression")
  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public PartitionField resultType(JsonArrowDataType resultType) {
    this.resultType = resultType;
    return this;
  }

  /**
   * The output type of the partition value (JsonArrowDataType format)
   *
   * @return resultType
   */
  @NotNull
  @Valid
  @Schema(
      name = "result_type",
      example = "{type=int32}",
      description = "The output type of the partition value (JsonArrowDataType format)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("result_type")
  public JsonArrowDataType getResultType() {
    return resultType;
  }

  public void setResultType(JsonArrowDataType resultType) {
    this.resultType = resultType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartitionField partitionField = (PartitionField) o;
    return Objects.equals(this.fieldId, partitionField.fieldId)
        && Objects.equals(this.sourceIds, partitionField.sourceIds)
        && Objects.equals(this.transform, partitionField.transform)
        && Objects.equals(this.expression, partitionField.expression)
        && Objects.equals(this.resultType, partitionField.resultType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fieldId, sourceIds, transform, expression, resultType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartitionField {\n");
    sb.append("    fieldId: ").append(toIndentedString(fieldId)).append("\n");
    sb.append("    sourceIds: ").append(toIndentedString(sourceIds)).append("\n");
    sb.append("    transform: ").append(toIndentedString(transform)).append("\n");
    sb.append("    expression: ").append(toIndentedString(expression)).append("\n");
    sb.append("    resultType: ").append(toIndentedString(resultType)).append("\n");
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
