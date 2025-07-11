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
package com.lancedb.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateIndexRequest */
@JsonPropertyOrder({
  CreateIndexRequest.JSON_PROPERTY_NAME,
  CreateIndexRequest.JSON_PROPERTY_NAMESPACE,
  CreateIndexRequest.JSON_PROPERTY_COLUMN,
  CreateIndexRequest.JSON_PROPERTY_INDEX_TYPE,
  CreateIndexRequest.JSON_PROPERTY_METRIC_TYPE,
  CreateIndexRequest.JSON_PROPERTY_NUM_PARTITIONS,
  CreateIndexRequest.JSON_PROPERTY_NUM_SUB_VECTORS,
  CreateIndexRequest.JSON_PROPERTY_NUM_BITS,
  CreateIndexRequest.JSON_PROPERTY_MAX_ITERATIONS,
  CreateIndexRequest.JSON_PROPERTY_SAMPLE_RATE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateIndexRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nonnull private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_COLUMN = "column";
  @javax.annotation.Nonnull private String column;

  /** Type of index to create */
  public enum IndexTypeEnum {
    BTREE(String.valueOf("BTREE")),

    BITMAP(String.valueOf("BITMAP")),

    LABEL_LIST(String.valueOf("LABEL_LIST")),

    IVF_FLAT(String.valueOf("IVF_FLAT")),

    IVF_HNSW_SQ(String.valueOf("IVF_HNSW_SQ")),

    IVF_PQ(String.valueOf("IVF_PQ")),

    FTS(String.valueOf("FTS"));

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

  public static final String JSON_PROPERTY_INDEX_TYPE = "index_type";
  @javax.annotation.Nonnull private IndexTypeEnum indexType;

  /** Distance metric type for vector indexes */
  public enum MetricTypeEnum {
    L2(String.valueOf("l2")),

    COSINE(String.valueOf("cosine")),

    DOT(String.valueOf("dot"));

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

  public static final String JSON_PROPERTY_METRIC_TYPE = "metric_type";
  @javax.annotation.Nullable private MetricTypeEnum metricType;

  public static final String JSON_PROPERTY_NUM_PARTITIONS = "num_partitions";
  @javax.annotation.Nullable private Integer numPartitions;

  public static final String JSON_PROPERTY_NUM_SUB_VECTORS = "num_sub_vectors";
  @javax.annotation.Nullable private Integer numSubVectors;

  public static final String JSON_PROPERTY_NUM_BITS = "num_bits";
  @javax.annotation.Nullable private Integer numBits;

  public static final String JSON_PROPERTY_MAX_ITERATIONS = "max_iterations";
  @javax.annotation.Nullable private Integer maxIterations;

  public static final String JSON_PROPERTY_SAMPLE_RATE = "sample_rate";
  @javax.annotation.Nullable private Integer sampleRate;

  public CreateIndexRequest() {}

  public CreateIndexRequest name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * The table name
   *
   * @return name
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(@javax.annotation.Nonnull String name) {
    this.name = name;
  }

  public CreateIndexRequest namespace(@javax.annotation.Nonnull List<String> namespace) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getNamespace() {
    return namespace;
  }

  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNamespace(@javax.annotation.Nonnull List<String> namespace) {
    this.namespace = namespace;
  }

  public CreateIndexRequest column(@javax.annotation.Nonnull String column) {

    this.column = column;
    return this;
  }

  /**
   * Name of the column to create index on
   *
   * @return column
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getColumn() {
    return column;
  }

  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setColumn(@javax.annotation.Nonnull String column) {
    this.column = column;
  }

  public CreateIndexRequest indexType(@javax.annotation.Nonnull IndexTypeEnum indexType) {

    this.indexType = indexType;
    return this;
  }

  /**
   * Type of index to create
   *
   * @return indexType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public IndexTypeEnum getIndexType() {
    return indexType;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndexType(@javax.annotation.Nonnull IndexTypeEnum indexType) {
    this.indexType = indexType;
  }

  public CreateIndexRequest metricType(@javax.annotation.Nullable MetricTypeEnum metricType) {

    this.metricType = metricType;
    return this;
  }

  /**
   * Distance metric type for vector indexes
   *
   * @return metricType
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METRIC_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public MetricTypeEnum getMetricType() {
    return metricType;
  }

  @JsonProperty(JSON_PROPERTY_METRIC_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetricType(@javax.annotation.Nullable MetricTypeEnum metricType) {
    this.metricType = metricType;
  }

  public CreateIndexRequest numPartitions(@javax.annotation.Nullable Integer numPartitions) {

    this.numPartitions = numPartitions;
    return this;
  }

  /**
   * Number of partitions for IVF indexes minimum: 1
   *
   * @return numPartitions
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_PARTITIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getNumPartitions() {
    return numPartitions;
  }

  @JsonProperty(JSON_PROPERTY_NUM_PARTITIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumPartitions(@javax.annotation.Nullable Integer numPartitions) {
    this.numPartitions = numPartitions;
  }

  public CreateIndexRequest numSubVectors(@javax.annotation.Nullable Integer numSubVectors) {

    this.numSubVectors = numSubVectors;
    return this;
  }

  /**
   * Number of sub-vectors for PQ indexes minimum: 1
   *
   * @return numSubVectors
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_SUB_VECTORS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getNumSubVectors() {
    return numSubVectors;
  }

  @JsonProperty(JSON_PROPERTY_NUM_SUB_VECTORS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumSubVectors(@javax.annotation.Nullable Integer numSubVectors) {
    this.numSubVectors = numSubVectors;
  }

  public CreateIndexRequest numBits(@javax.annotation.Nullable Integer numBits) {

    this.numBits = numBits;
    return this;
  }

  /**
   * Number of bits for scalar quantization minimum: 1 maximum: 8
   *
   * @return numBits
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_BITS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getNumBits() {
    return numBits;
  }

  @JsonProperty(JSON_PROPERTY_NUM_BITS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumBits(@javax.annotation.Nullable Integer numBits) {
    this.numBits = numBits;
  }

  public CreateIndexRequest maxIterations(@javax.annotation.Nullable Integer maxIterations) {

    this.maxIterations = maxIterations;
    return this;
  }

  /**
   * Maximum iterations for index building minimum: 1
   *
   * @return maxIterations
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MAX_ITERATIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getMaxIterations() {
    return maxIterations;
  }

  @JsonProperty(JSON_PROPERTY_MAX_ITERATIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMaxIterations(@javax.annotation.Nullable Integer maxIterations) {
    this.maxIterations = maxIterations;
  }

  public CreateIndexRequest sampleRate(@javax.annotation.Nullable Integer sampleRate) {

    this.sampleRate = sampleRate;
    return this;
  }

  /**
   * Sample rate for index building minimum: 1
   *
   * @return sampleRate
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SAMPLE_RATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getSampleRate() {
    return sampleRate;
  }

  @JsonProperty(JSON_PROPERTY_SAMPLE_RATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSampleRate(@javax.annotation.Nullable Integer sampleRate) {
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

    // add `name` to the URL query string
    if (getName() != null) {
      try {
        joiner.add(
            String.format(
                "%sname%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getName()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `namespace` to the URL query string
    if (getNamespace() != null) {
      for (int i = 0; i < getNamespace().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%snamespace%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getNamespace().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `column` to the URL query string
    if (getColumn() != null) {
      try {
        joiner.add(
            String.format(
                "%scolumn%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getColumn()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `index_type` to the URL query string
    if (getIndexType() != null) {
      try {
        joiner.add(
            String.format(
                "%sindex_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getIndexType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `metric_type` to the URL query string
    if (getMetricType() != null) {
      try {
        joiner.add(
            String.format(
                "%smetric_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMetricType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_partitions` to the URL query string
    if (getNumPartitions() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_partitions%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumPartitions()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_sub_vectors` to the URL query string
    if (getNumSubVectors() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_sub_vectors%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumSubVectors()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `num_bits` to the URL query string
    if (getNumBits() != null) {
      try {
        joiner.add(
            String.format(
                "%snum_bits%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNumBits()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `max_iterations` to the URL query string
    if (getMaxIterations() != null) {
      try {
        joiner.add(
            String.format(
                "%smax_iterations%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMaxIterations()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `sample_rate` to the URL query string
    if (getSampleRate() != null) {
      try {
        joiner.add(
            String.format(
                "%ssample_rate%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getSampleRate()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
