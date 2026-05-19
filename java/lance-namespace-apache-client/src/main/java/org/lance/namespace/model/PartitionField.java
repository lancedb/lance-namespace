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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** Partition field definition */
@JsonPropertyOrder({
  PartitionField.JSON_PROPERTY_FIELD_ID,
  PartitionField.JSON_PROPERTY_SOURCE_IDS,
  PartitionField.JSON_PROPERTY_TRANSFORM,
  PartitionField.JSON_PROPERTY_EXPRESSION,
  PartitionField.JSON_PROPERTY_RESULT_TYPE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class PartitionField {
  public static final String JSON_PROPERTY_FIELD_ID = "field_id";
  @javax.annotation.Nonnull private String fieldId;

  public static final String JSON_PROPERTY_SOURCE_IDS = "source_ids";
  @javax.annotation.Nonnull private List<Integer> sourceIds = new ArrayList<>();

  public static final String JSON_PROPERTY_TRANSFORM = "transform";
  @javax.annotation.Nullable private PartitionTransform transform;

  public static final String JSON_PROPERTY_EXPRESSION = "expression";
  @javax.annotation.Nullable private String expression;

  public static final String JSON_PROPERTY_RESULT_TYPE = "result_type";
  @javax.annotation.Nonnull private JsonArrowDataType resultType;

  public PartitionField() {}

  public PartitionField fieldId(@javax.annotation.Nonnull String fieldId) {

    this.fieldId = fieldId;
    return this;
  }

  /**
   * Unique identifier for this partition field (must not be renamed)
   *
   * @return fieldId
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_FIELD_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getFieldId() {
    return fieldId;
  }

  @JsonProperty(JSON_PROPERTY_FIELD_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFieldId(@javax.annotation.Nonnull String fieldId) {
    this.fieldId = fieldId;
  }

  public PartitionField sourceIds(@javax.annotation.Nonnull List<Integer> sourceIds) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SOURCE_IDS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<Integer> getSourceIds() {
    return sourceIds;
  }

  @JsonProperty(JSON_PROPERTY_SOURCE_IDS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSourceIds(@javax.annotation.Nonnull List<Integer> sourceIds) {
    this.sourceIds = sourceIds;
  }

  public PartitionField transform(@javax.annotation.Nullable PartitionTransform transform) {

    this.transform = transform;
    return this;
  }

  /**
   * Well-known partition transform. Exactly one of transform or expression must be specified.
   *
   * @return transform
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TRANSFORM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public PartitionTransform getTransform() {
    return transform;
  }

  @JsonProperty(JSON_PROPERTY_TRANSFORM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTransform(@javax.annotation.Nullable PartitionTransform transform) {
    this.transform = transform;
  }

  public PartitionField expression(@javax.annotation.Nullable String expression) {

    this.expression = expression;
    return this;
  }

  /**
   * DataFusion SQL expression using col0, col1, ... as column references. Exactly one of transform
   * or expression must be specified.
   *
   * @return expression
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_EXPRESSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getExpression() {
    return expression;
  }

  @JsonProperty(JSON_PROPERTY_EXPRESSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setExpression(@javax.annotation.Nullable String expression) {
    this.expression = expression;
  }

  public PartitionField resultType(@javax.annotation.Nonnull JsonArrowDataType resultType) {

    this.resultType = resultType;
    return this;
  }

  /**
   * The output type of the partition value (JsonArrowDataType format)
   *
   * @return resultType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RESULT_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public JsonArrowDataType getResultType() {
    return resultType;
  }

  @JsonProperty(JSON_PROPERTY_RESULT_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setResultType(@javax.annotation.Nonnull JsonArrowDataType resultType) {
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `field_id` to the URL query string
    if (getFieldId() != null) {
      try {
        joiner.add(
            String.format(
                "%sfield_id%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getFieldId()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `source_ids` to the URL query string
    if (getSourceIds() != null) {
      for (int i = 0; i < getSourceIds().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%ssource_ids%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getSourceIds().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `transform` to the URL query string
    if (getTransform() != null) {
      joiner.add(getTransform().toUrlQueryString(prefix + "transform" + suffix));
    }

    // add `expression` to the URL query string
    if (getExpression() != null) {
      try {
        joiner.add(
            String.format(
                "%sexpression%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getExpression()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `result_type` to the URL query string
    if (getResultType() != null) {
      joiner.add(getResultType().toUrlQueryString(prefix + "result_type" + suffix));
    }

    return joiner.toString();
  }
}
