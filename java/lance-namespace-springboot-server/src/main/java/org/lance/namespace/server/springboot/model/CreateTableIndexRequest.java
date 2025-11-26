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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** CreateTableIndexRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableIndexRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String column;

  /** Type of index to create */
  public enum IndexTypeEnum {
    BTREE("BTREE"),

    BITMAP("BITMAP"),

    LABEL_LIST("LABEL_LIST"),

    IVF_FLAT("IVF_FLAT"),

    IVF_PQ("IVF_PQ"),

    IVF_HNSW_SQ("IVF_HNSW_SQ"),

    FTS("FTS");

    private String value;

    IndexTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static IndexTypeEnum fromValue(String value) {
      for (IndexTypeEnum b : IndexTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private IndexTypeEnum indexType;

  /** Distance metric type for vector indexes */
  public enum MetricTypeEnum {
    L2("l2"),

    COSINE("cosine"),

    DOT("dot");

    private String value;

    MetricTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MetricTypeEnum fromValue(String value) {
      for (MetricTypeEnum b : MetricTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private MetricTypeEnum metricType;

  private Boolean withPosition;

  private String baseTokenizer;

  private String language;

  private Integer maxTokenLength;

  private Boolean lowerCase;

  private Boolean stem;

  private Boolean removeStopWords;

  private Boolean asciiFolding;

  public CreateTableIndexRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTableIndexRequest(String column, IndexTypeEnum indexType) {
    this.column = column;
    this.indexType = indexType;
  }

  public CreateTableIndexRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public CreateTableIndexRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public CreateTableIndexRequest column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Name of the column to create index on
   *
   * @return column
   */
  @NotNull
  @Schema(
      name = "column",
      description = "Name of the column to create index on",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public CreateTableIndexRequest indexType(IndexTypeEnum indexType) {
    this.indexType = indexType;
    return this;
  }

  /**
   * Type of index to create
   *
   * @return indexType
   */
  @NotNull
  @Schema(
      name = "index_type",
      description = "Type of index to create",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("index_type")
  public IndexTypeEnum getIndexType() {
    return indexType;
  }

  public void setIndexType(IndexTypeEnum indexType) {
    this.indexType = indexType;
  }

  public CreateTableIndexRequest metricType(MetricTypeEnum metricType) {
    this.metricType = metricType;
    return this;
  }

  /**
   * Distance metric type for vector indexes
   *
   * @return metricType
   */
  @Schema(
      name = "metric_type",
      description = "Distance metric type for vector indexes",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metric_type")
  public MetricTypeEnum getMetricType() {
    return metricType;
  }

  public void setMetricType(MetricTypeEnum metricType) {
    this.metricType = metricType;
  }

  public CreateTableIndexRequest withPosition(Boolean withPosition) {
    this.withPosition = withPosition;
    return this;
  }

  /**
   * Optional FTS parameter for position tracking
   *
   * @return withPosition
   */
  @Schema(
      name = "with_position",
      description = "Optional FTS parameter for position tracking",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("with_position")
  public Boolean getWithPosition() {
    return withPosition;
  }

  public void setWithPosition(Boolean withPosition) {
    this.withPosition = withPosition;
  }

  public CreateTableIndexRequest baseTokenizer(String baseTokenizer) {
    this.baseTokenizer = baseTokenizer;
    return this;
  }

  /**
   * Optional FTS parameter for base tokenizer
   *
   * @return baseTokenizer
   */
  @Schema(
      name = "base_tokenizer",
      description = "Optional FTS parameter for base tokenizer",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("base_tokenizer")
  public String getBaseTokenizer() {
    return baseTokenizer;
  }

  public void setBaseTokenizer(String baseTokenizer) {
    this.baseTokenizer = baseTokenizer;
  }

  public CreateTableIndexRequest language(String language) {
    this.language = language;
    return this;
  }

  /**
   * Optional FTS parameter for language
   *
   * @return language
   */
  @Schema(
      name = "language",
      description = "Optional FTS parameter for language",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("language")
  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public CreateTableIndexRequest maxTokenLength(Integer maxTokenLength) {
    this.maxTokenLength = maxTokenLength;
    return this;
  }

  /**
   * Optional FTS parameter for maximum token length minimum: 0
   *
   * @return maxTokenLength
   */
  @Min(0)
  @Schema(
      name = "max_token_length",
      description = "Optional FTS parameter for maximum token length",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_token_length")
  public Integer getMaxTokenLength() {
    return maxTokenLength;
  }

  public void setMaxTokenLength(Integer maxTokenLength) {
    this.maxTokenLength = maxTokenLength;
  }

  public CreateTableIndexRequest lowerCase(Boolean lowerCase) {
    this.lowerCase = lowerCase;
    return this;
  }

  /**
   * Optional FTS parameter for lowercase conversion
   *
   * @return lowerCase
   */
  @Schema(
      name = "lower_case",
      description = "Optional FTS parameter for lowercase conversion",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lower_case")
  public Boolean getLowerCase() {
    return lowerCase;
  }

  public void setLowerCase(Boolean lowerCase) {
    this.lowerCase = lowerCase;
  }

  public CreateTableIndexRequest stem(Boolean stem) {
    this.stem = stem;
    return this;
  }

  /**
   * Optional FTS parameter for stemming
   *
   * @return stem
   */
  @Schema(
      name = "stem",
      description = "Optional FTS parameter for stemming",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("stem")
  public Boolean getStem() {
    return stem;
  }

  public void setStem(Boolean stem) {
    this.stem = stem;
  }

  public CreateTableIndexRequest removeStopWords(Boolean removeStopWords) {
    this.removeStopWords = removeStopWords;
    return this;
  }

  /**
   * Optional FTS parameter for stop word removal
   *
   * @return removeStopWords
   */
  @Schema(
      name = "remove_stop_words",
      description = "Optional FTS parameter for stop word removal",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("remove_stop_words")
  public Boolean getRemoveStopWords() {
    return removeStopWords;
  }

  public void setRemoveStopWords(Boolean removeStopWords) {
    this.removeStopWords = removeStopWords;
  }

  public CreateTableIndexRequest asciiFolding(Boolean asciiFolding) {
    this.asciiFolding = asciiFolding;
    return this;
  }

  /**
   * Optional FTS parameter for ASCII folding
   *
   * @return asciiFolding
   */
  @Schema(
      name = "ascii_folding",
      description = "Optional FTS parameter for ASCII folding",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ascii_folding")
  public Boolean getAsciiFolding() {
    return asciiFolding;
  }

  public void setAsciiFolding(Boolean asciiFolding) {
    this.asciiFolding = asciiFolding;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableIndexRequest createTableIndexRequest = (CreateTableIndexRequest) o;
    return Objects.equals(this.id, createTableIndexRequest.id)
        && Objects.equals(this.column, createTableIndexRequest.column)
        && Objects.equals(this.indexType, createTableIndexRequest.indexType)
        && Objects.equals(this.metricType, createTableIndexRequest.metricType)
        && Objects.equals(this.withPosition, createTableIndexRequest.withPosition)
        && Objects.equals(this.baseTokenizer, createTableIndexRequest.baseTokenizer)
        && Objects.equals(this.language, createTableIndexRequest.language)
        && Objects.equals(this.maxTokenLength, createTableIndexRequest.maxTokenLength)
        && Objects.equals(this.lowerCase, createTableIndexRequest.lowerCase)
        && Objects.equals(this.stem, createTableIndexRequest.stem)
        && Objects.equals(this.removeStopWords, createTableIndexRequest.removeStopWords)
        && Objects.equals(this.asciiFolding, createTableIndexRequest.asciiFolding);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        column,
        indexType,
        metricType,
        withPosition,
        baseTokenizer,
        language,
        maxTokenLength,
        lowerCase,
        stem,
        removeStopWords,
        asciiFolding);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableIndexRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    metricType: ").append(toIndentedString(metricType)).append("\n");
    sb.append("    withPosition: ").append(toIndentedString(withPosition)).append("\n");
    sb.append("    baseTokenizer: ").append(toIndentedString(baseTokenizer)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    maxTokenLength: ").append(toIndentedString(maxTokenLength)).append("\n");
    sb.append("    lowerCase: ").append(toIndentedString(lowerCase)).append("\n");
    sb.append("    stem: ").append(toIndentedString(stem)).append("\n");
    sb.append("    removeStopWords: ").append(toIndentedString(removeStopWords)).append("\n");
    sb.append("    asciiFolding: ").append(toIndentedString(asciiFolding)).append("\n");
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
