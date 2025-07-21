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

/** QueryTableRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableRequest {

  private String name;

  @Valid private List<String> namespace = new ArrayList<>();

  private Boolean bypassVectorIndex = null;

  @Valid private List<String> columns = new ArrayList<>();

  private String distanceType = null;

  private Integer ef = null;

  private Boolean fastSearch = null;

  private String filter = null;

  private QueryTableRequestFullTextQuery fullTextQuery;

  private Integer k;

  private Float lowerBound = null;

  private Integer nprobes = null;

  private Integer offset = null;

  private Boolean prefilter = null;

  private Integer refineFactor = null;

  private Float upperBound = null;

  private QueryTableRequestVector vector;

  private String vectorColumn = null;

  private Long version = null;

  private Boolean withRowId = null;

  public QueryTableRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public QueryTableRequest(
      String name, List<String> namespace, Integer k, QueryTableRequestVector vector) {
    this.name = name;
    this.namespace = namespace;
    this.k = k;
    this.vector = vector;
  }

  public QueryTableRequest name(String name) {
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

  public QueryTableRequest namespace(List<String> namespace) {
    this.namespace = namespace;
    return this;
  }

  public QueryTableRequest addNamespaceItem(String namespaceItem) {
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

  public QueryTableRequest bypassVectorIndex(Boolean bypassVectorIndex) {
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

  public QueryTableRequest columns(List<String> columns) {
    this.columns = columns;
    return this;
  }

  public QueryTableRequest addColumnsItem(String columnsItem) {
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

  public QueryTableRequest distanceType(String distanceType) {
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
  public String getDistanceType() {
    return distanceType;
  }

  public void setDistanceType(String distanceType) {
    this.distanceType = distanceType;
  }

  public QueryTableRequest ef(Integer ef) {
    this.ef = ef;
    return this;
  }

  /**
   * Search effort parameter for HNSW index minimum: 0
   *
   * @return ef
   */
  @Min(0)
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

  public QueryTableRequest fastSearch(Boolean fastSearch) {
    this.fastSearch = fastSearch;
    return this;
  }

  /**
   * Whether to use fast search
   *
   * @return fastSearch
   */
  @Schema(
      name = "fast_search",
      description = "Whether to use fast search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fast_search")
  public Boolean getFastSearch() {
    return fastSearch;
  }

  public void setFastSearch(Boolean fastSearch) {
    this.fastSearch = fastSearch;
  }

  public QueryTableRequest filter(String filter) {
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

  public QueryTableRequest fullTextQuery(QueryTableRequestFullTextQuery fullTextQuery) {
    this.fullTextQuery = fullTextQuery;
    return this;
  }

  /**
   * Get fullTextQuery
   *
   * @return fullTextQuery
   */
  @Valid
  @Schema(name = "full_text_query", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("full_text_query")
  public QueryTableRequestFullTextQuery getFullTextQuery() {
    return fullTextQuery;
  }

  public void setFullTextQuery(QueryTableRequestFullTextQuery fullTextQuery) {
    this.fullTextQuery = fullTextQuery;
  }

  public QueryTableRequest k(Integer k) {
    this.k = k;
    return this;
  }

  /**
   * Number of results to return minimum: 0
   *
   * @return k
   */
  @NotNull
  @Min(0)
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

  public QueryTableRequest lowerBound(Float lowerBound) {
    this.lowerBound = lowerBound;
    return this;
  }

  /**
   * Lower bound for search
   *
   * @return lowerBound
   */
  @Schema(
      name = "lower_bound",
      description = "Lower bound for search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lower_bound")
  public Float getLowerBound() {
    return lowerBound;
  }

  public void setLowerBound(Float lowerBound) {
    this.lowerBound = lowerBound;
  }

  public QueryTableRequest nprobes(Integer nprobes) {
    this.nprobes = nprobes;
    return this;
  }

  /**
   * Number of probes for IVF index minimum: 0
   *
   * @return nprobes
   */
  @Min(0)
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

  public QueryTableRequest offset(Integer offset) {
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

  public QueryTableRequest prefilter(Boolean prefilter) {
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

  public QueryTableRequest refineFactor(Integer refineFactor) {
    this.refineFactor = refineFactor;
    return this;
  }

  /**
   * Refine factor for search minimum: 0
   *
   * @return refineFactor
   */
  @Min(0)
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

  public QueryTableRequest upperBound(Float upperBound) {
    this.upperBound = upperBound;
    return this;
  }

  /**
   * Upper bound for search
   *
   * @return upperBound
   */
  @Schema(
      name = "upper_bound",
      description = "Upper bound for search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("upper_bound")
  public Float getUpperBound() {
    return upperBound;
  }

  public void setUpperBound(Float upperBound) {
    this.upperBound = upperBound;
  }

  public QueryTableRequest vector(QueryTableRequestVector vector) {
    this.vector = vector;
    return this;
  }

  /**
   * Get vector
   *
   * @return vector
   */
  @NotNull
  @Valid
  @Schema(name = "vector", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("vector")
  public QueryTableRequestVector getVector() {
    return vector;
  }

  public void setVector(QueryTableRequestVector vector) {
    this.vector = vector;
  }

  public QueryTableRequest vectorColumn(String vectorColumn) {
    this.vectorColumn = vectorColumn;
    return this;
  }

  /**
   * Name of the vector column to search
   *
   * @return vectorColumn
   */
  @Schema(
      name = "vector_column",
      description = "Name of the vector column to search",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vector_column")
  public String getVectorColumn() {
    return vectorColumn;
  }

  public void setVectorColumn(String vectorColumn) {
    this.vectorColumn = vectorColumn;
  }

  public QueryTableRequest version(Long version) {
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

  public QueryTableRequest withRowId(Boolean withRowId) {
    this.withRowId = withRowId;
    return this;
  }

  /**
   * If true, return the row id as a column called `_rowid`
   *
   * @return withRowId
   */
  @Schema(
      name = "with_row_id",
      description = "If true, return the row id as a column called `_rowid`",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("with_row_id")
  public Boolean getWithRowId() {
    return withRowId;
  }

  public void setWithRowId(Boolean withRowId) {
    this.withRowId = withRowId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryTableRequest queryTableRequest = (QueryTableRequest) o;
    return Objects.equals(this.name, queryTableRequest.name)
        && Objects.equals(this.namespace, queryTableRequest.namespace)
        && Objects.equals(this.bypassVectorIndex, queryTableRequest.bypassVectorIndex)
        && Objects.equals(this.columns, queryTableRequest.columns)
        && Objects.equals(this.distanceType, queryTableRequest.distanceType)
        && Objects.equals(this.ef, queryTableRequest.ef)
        && Objects.equals(this.fastSearch, queryTableRequest.fastSearch)
        && Objects.equals(this.filter, queryTableRequest.filter)
        && Objects.equals(this.fullTextQuery, queryTableRequest.fullTextQuery)
        && Objects.equals(this.k, queryTableRequest.k)
        && Objects.equals(this.lowerBound, queryTableRequest.lowerBound)
        && Objects.equals(this.nprobes, queryTableRequest.nprobes)
        && Objects.equals(this.offset, queryTableRequest.offset)
        && Objects.equals(this.prefilter, queryTableRequest.prefilter)
        && Objects.equals(this.refineFactor, queryTableRequest.refineFactor)
        && Objects.equals(this.upperBound, queryTableRequest.upperBound)
        && Objects.equals(this.vector, queryTableRequest.vector)
        && Objects.equals(this.vectorColumn, queryTableRequest.vectorColumn)
        && Objects.equals(this.version, queryTableRequest.version)
        && Objects.equals(this.withRowId, queryTableRequest.withRowId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        namespace,
        bypassVectorIndex,
        columns,
        distanceType,
        ef,
        fastSearch,
        filter,
        fullTextQuery,
        k,
        lowerBound,
        nprobes,
        offset,
        prefilter,
        refineFactor,
        upperBound,
        vector,
        vectorColumn,
        version,
        withRowId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    bypassVectorIndex: ").append(toIndentedString(bypassVectorIndex)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    distanceType: ").append(toIndentedString(distanceType)).append("\n");
    sb.append("    ef: ").append(toIndentedString(ef)).append("\n");
    sb.append("    fastSearch: ").append(toIndentedString(fastSearch)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
    sb.append("    fullTextQuery: ").append(toIndentedString(fullTextQuery)).append("\n");
    sb.append("    k: ").append(toIndentedString(k)).append("\n");
    sb.append("    lowerBound: ").append(toIndentedString(lowerBound)).append("\n");
    sb.append("    nprobes: ").append(toIndentedString(nprobes)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    prefilter: ").append(toIndentedString(prefilter)).append("\n");
    sb.append("    refineFactor: ").append(toIndentedString(refineFactor)).append("\n");
    sb.append("    upperBound: ").append(toIndentedString(upperBound)).append("\n");
    sb.append("    vector: ").append(toIndentedString(vector)).append("\n");
    sb.append("    vectorColumn: ").append(toIndentedString(vectorColumn)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    withRowId: ").append(toIndentedString(withRowId)).append("\n");
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
