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

/** QueryRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class QueryRequest {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  @Valid private List<Float> vector = new ArrayList<>();

  private Integer k;

  private String filter;

  @Valid private List<String> columns = new ArrayList<>();

  /** Distance metric to use */
  public enum DistanceTypeEnum {
    L2("l2"),

    COSINE("cosine"),

    DOT("dot");

    private String value;

    DistanceTypeEnum(String value) {
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
    public static DistanceTypeEnum fromValue(String value) {
      for (DistanceTypeEnum b : DistanceTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private DistanceTypeEnum distanceType;

  private Boolean prefilter;

  private Boolean bypassVectorIndex;

  private Integer ef;

  private Integer nprobes;

  private Integer refineFactor;

  private Boolean withRowId;

  private Integer offset;

  private Long version;

  public QueryRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public QueryRequest(String name, List<String> namespace, List<Float> vector, Integer k) {
    this.name = name;
    this.namespace = namespace;
    this.vector = vector;
    this.k = k;
  }

  public QueryRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @NotNull
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public QueryRequest namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public QueryRequest addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * Get namespace
   *
   * @return namespace
   */
  @NotNull
  @Schema(name = "namespace", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("namespace")
  public List<String> getNamespace() {
    return namespace;
  }

  public void setNamespace(List<String> namespace) {
    this.namespace = namespace;
  }

  public QueryRequest vector(List<Float> vector) {
    this.vector = vector;
    return this;
  }

  public QueryRequest addVectorItem(Float vectorItem) {
    if (this.vector == null) {
      this.vector = new ArrayList<>();
    }
    this.vector.add(vectorItem);
    return this;
  }

  /**
   * Query vector for similarity search
   *
   * @return vector
   */
  @NotNull
  @Schema(
      name = "vector",
      description = "Query vector for similarity search",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("vector")
  public List<Float> getVector() {
    return vector;
  }

  public void setVector(List<Float> vector) {
    this.vector = vector;
  }

  public QueryRequest k(Integer k) {
    this.k = k;
    return this;
  }

  /**
   * Number of results to return minimum: 1
   *
   * @return k
   */
  @NotNull
  @Min(1)
  @Schema(
      name = "k",
      description = "Number of results to return",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("k")
  public Integer getK() {
    return k;
  }

  public void setK(Integer k) {
    this.k = k;
  }

  public QueryRequest filter(String filter) {
    this.filter = filter;
    return this;
  }

  /**
   * Optional SQL filter expression
   *
   * @return filter
   */
  @Schema(
      name = "filter",
      description = "Optional SQL filter expression",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filter")
  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public QueryRequest columns(List<String> columns) {
    this.columns = columns;
    return this;
  }

  public QueryRequest addColumnsItem(String columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<>();
    }
    this.columns.add(columnsItem);
    return this;
  }

  /**
   * Optional list of columns to return
   *
   * @return columns
   */
  @Schema(
      name = "columns",
      description = "Optional list of columns to return",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("columns")
  public List<String> getColumns() {
    return columns;
  }

  public void setColumns(List<String> columns) {
    this.columns = columns;
  }

  public QueryRequest distanceType(DistanceTypeEnum distanceType) {
    this.distanceType = distanceType;
    return this;
  }

  /**
   * Distance metric to use
   *
   * @return distanceType
   */
  @Schema(
      name = "distance_type",
      description = "Distance metric to use",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("distance_type")
  public DistanceTypeEnum getDistanceType() {
    return distanceType;
  }

  public void setDistanceType(DistanceTypeEnum distanceType) {
    this.distanceType = distanceType;
  }

  public QueryRequest prefilter(Boolean prefilter) {
    this.prefilter = prefilter;
    return this;
  }

  /**
   * Whether to apply filtering before vector search
   *
   * @return prefilter
   */
  @Schema(
      name = "prefilter",
      description = "Whether to apply filtering before vector search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prefilter")
  public Boolean getPrefilter() {
    return prefilter;
  }

  public void setPrefilter(Boolean prefilter) {
    this.prefilter = prefilter;
  }

  public QueryRequest bypassVectorIndex(Boolean bypassVectorIndex) {
    this.bypassVectorIndex = bypassVectorIndex;
    return this;
  }

  /**
   * Whether to bypass vector index
   *
   * @return bypassVectorIndex
   */
  @Schema(
      name = "bypass_vector_index",
      description = "Whether to bypass vector index",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bypass_vector_index")
  public Boolean getBypassVectorIndex() {
    return bypassVectorIndex;
  }

  public void setBypassVectorIndex(Boolean bypassVectorIndex) {
    this.bypassVectorIndex = bypassVectorIndex;
  }

  public QueryRequest ef(Integer ef) {
    this.ef = ef;
    return this;
  }

  /**
   * Search effort parameter for HNSW index minimum: 1
   *
   * @return ef
   */
  @Min(1)
  @Schema(
      name = "ef",
      description = "Search effort parameter for HNSW index",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ef")
  public Integer getEf() {
    return ef;
  }

  public void setEf(Integer ef) {
    this.ef = ef;
  }

  public QueryRequest nprobes(Integer nprobes) {
    this.nprobes = nprobes;
    return this;
  }

  /**
   * Number of probes for IVF index minimum: 1
   *
   * @return nprobes
   */
  @Min(1)
  @Schema(
      name = "nprobes",
      description = "Number of probes for IVF index",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nprobes")
  public Integer getNprobes() {
    return nprobes;
  }

  public void setNprobes(Integer nprobes) {
    this.nprobes = nprobes;
  }

  public QueryRequest refineFactor(Integer refineFactor) {
    this.refineFactor = refineFactor;
    return this;
  }

  /**
   * Refine factor for search minimum: 1
   *
   * @return refineFactor
   */
  @Min(1)
  @Schema(
      name = "refine_factor",
      description = "Refine factor for search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("refine_factor")
  public Integer getRefineFactor() {
    return refineFactor;
  }

  public void setRefineFactor(Integer refineFactor) {
    this.refineFactor = refineFactor;
  }

  public QueryRequest withRowId(Boolean withRowId) {
    this.withRowId = withRowId;
    return this;
  }

  /**
   * Whether to include row ID in results
   *
   * @return withRowId
   */
  @Schema(
      name = "with_row_id",
      description = "Whether to include row ID in results",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("with_row_id")
  public Boolean getWithRowId() {
    return withRowId;
  }

  public void setWithRowId(Boolean withRowId) {
    this.withRowId = withRowId;
  }

  public QueryRequest offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Number of results to skip minimum: 0
   *
   * @return offset
   */
  @Min(0)
  @Schema(
      name = "offset",
      description = "Number of results to skip",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("offset")
  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public QueryRequest version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Table version to query minimum: 0
   *
   * @return version
   */
  @Min(0L)
  @Schema(
      name = "version",
      description = "Table version to query",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryRequest queryRequest = (QueryRequest) o;
    return Objects.equals(this.name, queryRequest.name)
        && Objects.equals(this.namespace, queryRequest.namespace)
        && Objects.equals(this.vector, queryRequest.vector)
        && Objects.equals(this.k, queryRequest.k)
        && Objects.equals(this.filter, queryRequest.filter)
        && Objects.equals(this.columns, queryRequest.columns)
        && Objects.equals(this.distanceType, queryRequest.distanceType)
        && Objects.equals(this.prefilter, queryRequest.prefilter)
        && Objects.equals(this.bypassVectorIndex, queryRequest.bypassVectorIndex)
        && Objects.equals(this.ef, queryRequest.ef)
        && Objects.equals(this.nprobes, queryRequest.nprobes)
        && Objects.equals(this.refineFactor, queryRequest.refineFactor)
        && Objects.equals(this.withRowId, queryRequest.withRowId)
        && Objects.equals(this.offset, queryRequest.offset)
        && Objects.equals(this.version, queryRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        namespace,
        vector,
        k,
        filter,
        columns,
        distanceType,
        prefilter,
        bypassVectorIndex,
        ef,
        nprobes,
        refineFactor,
        withRowId,
        offset,
        version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    vector: ").append(toIndentedString(vector)).append("\n");
    sb.append("    k: ").append(toIndentedString(k)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    distanceType: ").append(toIndentedString(distanceType)).append("\n");
    sb.append("    prefilter: ").append(toIndentedString(prefilter)).append("\n");
    sb.append("    bypassVectorIndex: ").append(toIndentedString(bypassVectorIndex)).append("\n");
    sb.append("    ef: ").append(toIndentedString(ef)).append("\n");
    sb.append("    nprobes: ").append(toIndentedString(nprobes)).append("\n");
    sb.append("    refineFactor: ").append(toIndentedString(refineFactor)).append("\n");
    sb.append("    withRowId: ").append(toIndentedString(withRowId)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
