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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** CreateTableIndexRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableIndexRequest {

  private Identity identity;

  @Valid private Map<String, String> context = new HashMap<>();

  @Valid private List<String> id = new ArrayList<>();

  private String branch;

  private String column;

  private String indexType;

  private String name;

  private String distanceType;

  private Integer numPartitions;

  private Integer numSubVectors;

  private Integer numBits;

  private Integer sampleRate;

  private Integer maxIterations;

  private Integer targetPartitionSize;

  private Integer m;

  private Integer efConstruction;

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
  public CreateTableIndexRequest(String column, String indexType) {
    this.column = column;
    this.indexType = indexType;
  }

  public CreateTableIndexRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @Valid
  @Schema(name = "identity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identity")
  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public CreateTableIndexRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public CreateTableIndexRequest putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context as key-value pairs. How to use the context is custom to the specific
   * implementation. On a request, it carries caller-provided context to the implementation. On a
   * response, it carries implementation-provided context back to the caller. REST NAMESPACE ONLY
   * Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request,
   * any entry whose key starts with `header.` is sent as an HTTP request header with the prefix
   * stripped. For example, the entry `{\"header.Authorization\": \"Bearer abc\"}` is sent as the
   * request header `Authorization: Bearer abc`. - On a response, every HTTP response header is
   * returned as an entry whose key is the header name prefixed with `header.`. For example, the
   * response header `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\":
   * \"abc123\"}`.
   *
   * @return context
   */
  @Schema(
      name = "context",
      description =
          "Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
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

  public CreateTableIndexRequest branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Branch to target. When not specified, the main branch is used.
   *
   * @return branch
   */
  @Schema(
      name = "branch",
      description = "Branch to target. When not specified, the main branch is used. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branch")
  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public CreateTableIndexRequest column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Lance field path to create the index on. Nested fields use dot-separated segments; use
   * backtick-quoted segments for literal dots and double backticks inside quoted segments. Use
   * canonical full paths for display and errors; leaf names alone only identify top-level fields;
   * invalid or unresolved paths should return InvalidInput or TableColumnNotFound.
   *
   * @return column
   */
  @NotNull
  @Size(min = 1)
  @Schema(
      name = "column",
      description =
          "Lance field path to create the index on. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound.",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public CreateTableIndexRequest indexType(String indexType) {
    this.indexType = indexType;
    return this;
  }

  /**
   * Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS)
   *
   * @return indexType
   */
  @NotNull
  @Schema(
      name = "index_type",
      description =
          "Type of index to create (e.g., BTREE, BITMAP, LABEL_LIST, IVF_FLAT, IVF_PQ, IVF_HNSW_SQ, FTS)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("index_type")
  public String getIndexType() {
    return indexType;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  public CreateTableIndexRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Optional name for the index. If not provided, a name will be auto-generated.
   *
   * @return name
   */
  @Schema(
      name = "name",
      description = "Optional name for the index. If not provided, a name will be auto-generated.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateTableIndexRequest distanceType(String distanceType) {
    this.distanceType = distanceType;
    return this;
  }

  /**
   * Distance metric type for vector indexes (e.g., l2, cosine, dot)
   *
   * @return distanceType
   */
  @Schema(
      name = "distance_type",
      description = "Distance metric type for vector indexes (e.g., l2, cosine, dot)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("distance_type")
  public String getDistanceType() {
    return distanceType;
  }

  public void setDistanceType(String distanceType) {
    this.distanceType = distanceType;
  }

  public CreateTableIndexRequest numPartitions(Integer numPartitions) {
    this.numPartitions = numPartitions;
    return this;
  }

  /**
   * Optional vector index parameter for the number of IVF partitions. Applies to all IVF index
   * types. minimum: 1
   *
   * @return numPartitions
   */
  @Min(1)
  @Schema(
      name = "num_partitions",
      description =
          "Optional vector index parameter for the number of IVF partitions. Applies to all IVF index types.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_partitions")
  public Integer getNumPartitions() {
    return numPartitions;
  }

  public void setNumPartitions(Integer numPartitions) {
    this.numPartitions = numPartitions;
  }

  public CreateTableIndexRequest numSubVectors(Integer numSubVectors) {
    this.numSubVectors = numSubVectors;
    return this;
  }

  /**
   * Optional vector index parameter for the number of PQ sub-vectors. Applies to IVF_PQ only.
   * minimum: 1
   *
   * @return numSubVectors
   */
  @Min(1)
  @Schema(
      name = "num_sub_vectors",
      description =
          "Optional vector index parameter for the number of PQ sub-vectors. Applies to IVF_PQ only.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_sub_vectors")
  public Integer getNumSubVectors() {
    return numSubVectors;
  }

  public void setNumSubVectors(Integer numSubVectors) {
    this.numSubVectors = numSubVectors;
  }

  public CreateTableIndexRequest numBits(Integer numBits) {
    this.numBits = numBits;
    return this;
  }

  /**
   * Optional vector index parameter for the number of bits used by the quantizer. minimum: 1
   *
   * @return numBits
   */
  @Min(1)
  @Schema(
      name = "num_bits",
      description = "Optional vector index parameter for the number of bits used by the quantizer.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_bits")
  public Integer getNumBits() {
    return numBits;
  }

  public void setNumBits(Integer numBits) {
    this.numBits = numBits;
  }

  public CreateTableIndexRequest sampleRate(Integer sampleRate) {
    this.sampleRate = sampleRate;
    return this;
  }

  /**
   * Optional vector index parameter for the per-partition sample rate used during IVF training.
   * minimum: 1
   *
   * @return sampleRate
   */
  @Min(1)
  @Schema(
      name = "sample_rate",
      description =
          "Optional vector index parameter for the per-partition sample rate used during IVF training.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("sample_rate")
  public Integer getSampleRate() {
    return sampleRate;
  }

  public void setSampleRate(Integer sampleRate) {
    this.sampleRate = sampleRate;
  }

  public CreateTableIndexRequest maxIterations(Integer maxIterations) {
    this.maxIterations = maxIterations;
    return this;
  }

  /**
   * Optional vector index parameter for the maximum number of IVF k-means training iterations.
   * minimum: 1
   *
   * @return maxIterations
   */
  @Min(1)
  @Schema(
      name = "max_iterations",
      description =
          "Optional vector index parameter for the maximum number of IVF k-means training iterations.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_iterations")
  public Integer getMaxIterations() {
    return maxIterations;
  }

  public void setMaxIterations(Integer maxIterations) {
    this.maxIterations = maxIterations;
  }

  public CreateTableIndexRequest targetPartitionSize(Integer targetPartitionSize) {
    this.targetPartitionSize = targetPartitionSize;
    return this;
  }

  /**
   * Optional vector index parameter for the target partition size. Alternative to num_partitions.
   * minimum: 1
   *
   * @return targetPartitionSize
   */
  @Min(1)
  @Schema(
      name = "target_partition_size",
      description =
          "Optional vector index parameter for the target partition size. Alternative to num_partitions.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("target_partition_size")
  public Integer getTargetPartitionSize() {
    return targetPartitionSize;
  }

  public void setTargetPartitionSize(Integer targetPartitionSize) {
    this.targetPartitionSize = targetPartitionSize;
  }

  public CreateTableIndexRequest m(Integer m) {
    this.m = m;
    return this;
  }

  /**
   * Optional vector index parameter for the number of edges per node in the HNSW graph. Applies to
   * HNSW index types. minimum: 1
   *
   * @return m
   */
  @Min(1)
  @Schema(
      name = "m",
      description =
          "Optional vector index parameter for the number of edges per node in the HNSW graph. Applies to HNSW index types.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("m")
  public Integer getM() {
    return m;
  }

  public void setM(Integer m) {
    this.m = m;
  }

  public CreateTableIndexRequest efConstruction(Integer efConstruction) {
    this.efConstruction = efConstruction;
    return this;
  }

  /**
   * Optional vector index parameter for the number of candidates evaluated during HNSW graph
   * construction. Applies to HNSW index types. minimum: 1
   *
   * @return efConstruction
   */
  @Min(1)
  @Schema(
      name = "ef_construction",
      description =
          "Optional vector index parameter for the number of candidates evaluated during HNSW graph construction. Applies to HNSW index types.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ef_construction")
  public Integer getEfConstruction() {
    return efConstruction;
  }

  public void setEfConstruction(Integer efConstruction) {
    this.efConstruction = efConstruction;
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
    return Objects.equals(this.identity, createTableIndexRequest.identity)
        && Objects.equals(this.context, createTableIndexRequest.context)
        && Objects.equals(this.id, createTableIndexRequest.id)
        && Objects.equals(this.branch, createTableIndexRequest.branch)
        && Objects.equals(this.column, createTableIndexRequest.column)
        && Objects.equals(this.indexType, createTableIndexRequest.indexType)
        && Objects.equals(this.name, createTableIndexRequest.name)
        && Objects.equals(this.distanceType, createTableIndexRequest.distanceType)
        && Objects.equals(this.numPartitions, createTableIndexRequest.numPartitions)
        && Objects.equals(this.numSubVectors, createTableIndexRequest.numSubVectors)
        && Objects.equals(this.numBits, createTableIndexRequest.numBits)
        && Objects.equals(this.sampleRate, createTableIndexRequest.sampleRate)
        && Objects.equals(this.maxIterations, createTableIndexRequest.maxIterations)
        && Objects.equals(this.targetPartitionSize, createTableIndexRequest.targetPartitionSize)
        && Objects.equals(this.m, createTableIndexRequest.m)
        && Objects.equals(this.efConstruction, createTableIndexRequest.efConstruction)
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
        identity,
        context,
        id,
        branch,
        column,
        indexType,
        name,
        distanceType,
        numPartitions,
        numSubVectors,
        numBits,
        sampleRate,
        maxIterations,
        targetPartitionSize,
        m,
        efConstruction,
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
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    distanceType: ").append(toIndentedString(distanceType)).append("\n");
    sb.append("    numPartitions: ").append(toIndentedString(numPartitions)).append("\n");
    sb.append("    numSubVectors: ").append(toIndentedString(numSubVectors)).append("\n");
    sb.append("    numBits: ").append(toIndentedString(numBits)).append("\n");
    sb.append("    sampleRate: ").append(toIndentedString(sampleRate)).append("\n");
    sb.append("    maxIterations: ").append(toIndentedString(maxIterations)).append("\n");
    sb.append("    targetPartitionSize: ")
        .append(toIndentedString(targetPartitionSize))
        .append("\n");
    sb.append("    m: ").append(toIndentedString(m)).append("\n");
    sb.append("    efConstruction: ").append(toIndentedString(efConstruction)).append("\n");
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
