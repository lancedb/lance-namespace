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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** CreateIndexRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateIndexRequest {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private String column;

  /** Type of index to create */
  public enum IndexTypeEnum {
    BTREE("BTREE"),

    BITMAP("BITMAP"),

    LABEL_LIST("LABEL_LIST"),

    IVF_FLAT("IVF_FLAT"),

    IVF_HNSW_SQ("IVF_HNSW_SQ"),

    IVF_PQ("IVF_PQ"),

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

  private Integer numPartitions;

  private Integer numSubVectors;

  private Integer numBits;

  private Integer maxIterations;

  private Integer sampleRate;

  public CreateIndexRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateIndexRequest(
      String name, List<String> namespace, String column, IndexTypeEnum indexType) {
    this.name = name;
    this.namespace = namespace;
    this.column = column;
    this.indexType = indexType;
  }

  public CreateIndexRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The table name
   *
   * @return name
   */
  @NotNull
  @Schema(
      name = "name",
      description = "The table name",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateIndexRequest namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public CreateIndexRequest addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * The namespace identifier
   *
   * @return namespace
   */
  @NotNull
  @Schema(
      name = "namespace",
      description = "The namespace identifier",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
  }

  public CreateIndexRequest column(String column) {
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

  public CreateIndexRequest indexType(IndexTypeEnum indexType) {
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

  public CreateIndexRequest metricType(MetricTypeEnum metricType) {
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

  public CreateIndexRequest numPartitions(Integer numPartitions) {
    this.numPartitions = numPartitions;
    return this;
  }

  /**
   * Number of partitions for IVF indexes minimum: 1
   *
   * @return numPartitions
   */
  @Min(1)
  @Schema(
      name = "num_partitions",
      description = "Number of partitions for IVF indexes",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_partitions")
  public Integer getNumPartitions() {
    return numPartitions;
  }

  public void setNumPartitions(Integer numPartitions) {
    this.numPartitions = numPartitions;
  }

  public CreateIndexRequest numSubVectors(Integer numSubVectors) {
    this.numSubVectors = numSubVectors;
    return this;
  }

  /**
   * Number of sub-vectors for PQ indexes minimum: 1
   *
   * @return numSubVectors
   */
  @Min(1)
  @Schema(
      name = "num_sub_vectors",
      description = "Number of sub-vectors for PQ indexes",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_sub_vectors")
  public Integer getNumSubVectors() {
    return numSubVectors;
  }

  public void setNumSubVectors(Integer numSubVectors) {
    this.numSubVectors = numSubVectors;
  }

  public CreateIndexRequest numBits(Integer numBits) {
    this.numBits = numBits;
    return this;
  }

  /**
   * Number of bits for scalar quantization minimum: 1 maximum: 8
   *
   * @return numBits
   */
  @Min(1)
  @Max(8)
  @Schema(
      name = "num_bits",
      description = "Number of bits for scalar quantization",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_bits")
  public Integer getNumBits() {
    return numBits;
  }

  public void setNumBits(Integer numBits) {
    this.numBits = numBits;
  }

  public CreateIndexRequest maxIterations(Integer maxIterations) {
    this.maxIterations = maxIterations;
    return this;
  }

  /**
   * Maximum iterations for index building minimum: 1
   *
   * @return maxIterations
   */
  @Min(1)
  @Schema(
      name = "max_iterations",
      description = "Maximum iterations for index building",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_iterations")
  public Integer getMaxIterations() {
    return maxIterations;
  }

  public void setMaxIterations(Integer maxIterations) {
    this.maxIterations = maxIterations;
  }

  public CreateIndexRequest sampleRate(Integer sampleRate) {
    this.sampleRate = sampleRate;
    return this;
  }

  /**
   * Sample rate for index building minimum: 1
   *
   * @return sampleRate
   */
  @Min(1)
  @Schema(
      name = "sample_rate",
      description = "Sample rate for index building",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sample_rate")
  public Integer getSampleRate() {
    return sampleRate;
  }

  public void setSampleRate(Integer sampleRate) {
    this.sampleRate = sampleRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateIndexRequest createIndexRequest = (CreateIndexRequest) o;
    return Objects.equals(this.name, createIndexRequest.name)
        && Objects.equals(this.namespace, createIndexRequest.namespace)
        && Objects.equals(this.column, createIndexRequest.column)
        && Objects.equals(this.indexType, createIndexRequest.indexType)
        && Objects.equals(this.metricType, createIndexRequest.metricType)
        && Objects.equals(this.numPartitions, createIndexRequest.numPartitions)
        && Objects.equals(this.numSubVectors, createIndexRequest.numSubVectors)
        && Objects.equals(this.numBits, createIndexRequest.numBits)
        && Objects.equals(this.maxIterations, createIndexRequest.maxIterations)
        && Objects.equals(this.sampleRate, createIndexRequest.sampleRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        namespace,
        column,
        indexType,
        metricType,
        numPartitions,
        numSubVectors,
        numBits,
        maxIterations,
        sampleRate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateIndexRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    metricType: ").append(toIndentedString(metricType)).append("\n");
    sb.append("    numPartitions: ").append(toIndentedString(numPartitions)).append("\n");
    sb.append("    numSubVectors: ").append(toIndentedString(numSubVectors)).append("\n");
    sb.append("    numBits: ").append(toIndentedString(numBits)).append("\n");
    sb.append("    maxIterations: ").append(toIndentedString(maxIterations)).append("\n");
    sb.append("    sampleRate: ").append(toIndentedString(sampleRate)).append("\n");
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
