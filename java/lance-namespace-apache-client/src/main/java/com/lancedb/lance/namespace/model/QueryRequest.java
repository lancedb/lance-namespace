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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** QueryRequest */
@JsonPropertyOrder({
  QueryRequest.JSON_PROPERTY_NAME,
  QueryRequest.JSON_PROPERTY_NAMESPACE,
  QueryRequest.JSON_PROPERTY_BYPASS_VECTOR_INDEX,
  QueryRequest.JSON_PROPERTY_COLUMNS,
  QueryRequest.JSON_PROPERTY_DISTANCE_TYPE,
  QueryRequest.JSON_PROPERTY_EF,
  QueryRequest.JSON_PROPERTY_FAST_SEARCH,
  QueryRequest.JSON_PROPERTY_FILTER,
  QueryRequest.JSON_PROPERTY_FULL_TEXT_QUERY,
  QueryRequest.JSON_PROPERTY_K,
  QueryRequest.JSON_PROPERTY_LOWER_BOUND,
  QueryRequest.JSON_PROPERTY_NPROBES,
  QueryRequest.JSON_PROPERTY_OFFSET,
  QueryRequest.JSON_PROPERTY_PREFILTER,
  QueryRequest.JSON_PROPERTY_REFINE_FACTOR,
  QueryRequest.JSON_PROPERTY_UPPER_BOUND,
  QueryRequest.JSON_PROPERTY_VECTOR,
  QueryRequest.JSON_PROPERTY_VECTOR_COLUMN,
  QueryRequest.JSON_PROPERTY_VERSION,
  QueryRequest.JSON_PROPERTY_WITH_ROW_ID
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class QueryRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nonnull private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_BYPASS_VECTOR_INDEX = "bypass_vector_index";

  @javax.annotation.Nullable
  private JsonNullable<Boolean> bypassVectorIndex = JsonNullable.<Boolean>undefined();

  public static final String JSON_PROPERTY_COLUMNS = "columns";
  @javax.annotation.Nullable private List<String> columns = new ArrayList<>();

  public static final String JSON_PROPERTY_DISTANCE_TYPE = "distance_type";

  @javax.annotation.Nullable
  private JsonNullable<String> distanceType = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_EF = "ef";
  @javax.annotation.Nullable private JsonNullable<Integer> ef = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_FAST_SEARCH = "fast_search";

  @javax.annotation.Nullable
  private JsonNullable<Boolean> fastSearch = JsonNullable.<Boolean>undefined();

  public static final String JSON_PROPERTY_FILTER = "filter";
  @javax.annotation.Nullable private JsonNullable<String> filter = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_FULL_TEXT_QUERY = "full_text_query";
  @javax.annotation.Nullable private StringFtsQuery fullTextQuery;

  public static final String JSON_PROPERTY_K = "k";
  @javax.annotation.Nonnull private Integer k;

  public static final String JSON_PROPERTY_LOWER_BOUND = "lower_bound";

  @javax.annotation.Nullable
  private JsonNullable<Float> lowerBound = JsonNullable.<Float>undefined();

  public static final String JSON_PROPERTY_NPROBES = "nprobes";

  @javax.annotation.Nullable
  private JsonNullable<Integer> nprobes = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_OFFSET = "offset";

  @javax.annotation.Nullable
  private JsonNullable<Integer> offset = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_PREFILTER = "prefilter";

  @javax.annotation.Nullable
  private JsonNullable<Boolean> prefilter = JsonNullable.<Boolean>undefined();

  public static final String JSON_PROPERTY_REFINE_FACTOR = "refine_factor";

  @javax.annotation.Nullable
  private JsonNullable<Integer> refineFactor = JsonNullable.<Integer>undefined();

  public static final String JSON_PROPERTY_UPPER_BOUND = "upper_bound";

  @javax.annotation.Nullable
  private JsonNullable<Float> upperBound = JsonNullable.<Float>undefined();

  public static final String JSON_PROPERTY_VECTOR = "vector";
  @javax.annotation.Nonnull private List<Float> vector = new ArrayList<>();

  public static final String JSON_PROPERTY_VECTOR_COLUMN = "vector_column";

  @javax.annotation.Nullable
  private JsonNullable<String> vectorColumn = JsonNullable.<String>undefined();

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private JsonNullable<Long> version = JsonNullable.<Long>undefined();

  public static final String JSON_PROPERTY_WITH_ROW_ID = "with_row_id";

  @javax.annotation.Nullable
  private JsonNullable<Boolean> withRowId = JsonNullable.<Boolean>undefined();

  public QueryRequest() {}

  public QueryRequest name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * Get name
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

  public QueryRequest namespace(@javax.annotation.Nonnull List<String> namespace) {

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

  public QueryRequest bypassVectorIndex(@javax.annotation.Nullable Boolean bypassVectorIndex) {
    this.bypassVectorIndex = JsonNullable.<Boolean>of(bypassVectorIndex);

    return this;
  }

  /**
   * Whether to bypass vector index
   *
   * @return bypassVectorIndex
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getBypassVectorIndex() {
    return bypassVectorIndex.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_BYPASS_VECTOR_INDEX)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getBypassVectorIndex_JsonNullable() {
    return bypassVectorIndex;
  }

  @JsonProperty(JSON_PROPERTY_BYPASS_VECTOR_INDEX)
  public void setBypassVectorIndex_JsonNullable(JsonNullable<Boolean> bypassVectorIndex) {
    this.bypassVectorIndex = bypassVectorIndex;
  }

  public void setBypassVectorIndex(@javax.annotation.Nullable Boolean bypassVectorIndex) {
    this.bypassVectorIndex = JsonNullable.<Boolean>of(bypassVectorIndex);
  }

  public QueryRequest columns(@javax.annotation.Nullable List<String> columns) {

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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getColumns() {
    return columns;
  }

  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setColumns(@javax.annotation.Nullable List<String> columns) {
    this.columns = columns;
  }

  public QueryRequest distanceType(@javax.annotation.Nullable String distanceType) {
    this.distanceType = JsonNullable.<String>of(distanceType);

    return this;
  }

  /**
   * Distance metric to use
   *
   * @return distanceType
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getDistanceType() {
    return distanceType.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getDistanceType_JsonNullable() {
    return distanceType;
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  public void setDistanceType_JsonNullable(JsonNullable<String> distanceType) {
    this.distanceType = distanceType;
  }

  public void setDistanceType(@javax.annotation.Nullable String distanceType) {
    this.distanceType = JsonNullable.<String>of(distanceType);
  }

  public QueryRequest ef(@javax.annotation.Nullable Integer ef) {
    this.ef = JsonNullable.<Integer>of(ef);

    return this;
  }

  /**
   * Search effort parameter for HNSW index minimum: 0
   *
   * @return ef
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getEf() {
    return ef.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_EF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getEf_JsonNullable() {
    return ef;
  }

  @JsonProperty(JSON_PROPERTY_EF)
  public void setEf_JsonNullable(JsonNullable<Integer> ef) {
    this.ef = ef;
  }

  public void setEf(@javax.annotation.Nullable Integer ef) {
    this.ef = JsonNullable.<Integer>of(ef);
  }

  public QueryRequest fastSearch(@javax.annotation.Nullable Boolean fastSearch) {
    this.fastSearch = JsonNullable.<Boolean>of(fastSearch);

    return this;
  }

  /**
   * Whether to use fast search
   *
   * @return fastSearch
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getFastSearch() {
    return fastSearch.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_FAST_SEARCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getFastSearch_JsonNullable() {
    return fastSearch;
  }

  @JsonProperty(JSON_PROPERTY_FAST_SEARCH)
  public void setFastSearch_JsonNullable(JsonNullable<Boolean> fastSearch) {
    this.fastSearch = fastSearch;
  }

  public void setFastSearch(@javax.annotation.Nullable Boolean fastSearch) {
    this.fastSearch = JsonNullable.<Boolean>of(fastSearch);
  }

  public QueryRequest filter(@javax.annotation.Nullable String filter) {
    this.filter = JsonNullable.<String>of(filter);

    return this;
  }

  /**
   * Optional SQL filter expression
   *
   * @return filter
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getFilter() {
    return filter.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_FILTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getFilter_JsonNullable() {
    return filter;
  }

  @JsonProperty(JSON_PROPERTY_FILTER)
  public void setFilter_JsonNullable(JsonNullable<String> filter) {
    this.filter = filter;
  }

  public void setFilter(@javax.annotation.Nullable String filter) {
    this.filter = JsonNullable.<String>of(filter);
  }

  public QueryRequest fullTextQuery(@javax.annotation.Nullable StringFtsQuery fullTextQuery) {

    this.fullTextQuery = fullTextQuery;
    return this;
  }

  /**
   * Optional full-text search query (only string query supported)
   *
   * @return fullTextQuery
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FULL_TEXT_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public StringFtsQuery getFullTextQuery() {
    return fullTextQuery;
  }

  @JsonProperty(JSON_PROPERTY_FULL_TEXT_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFullTextQuery(@javax.annotation.Nullable StringFtsQuery fullTextQuery) {
    this.fullTextQuery = fullTextQuery;
  }

  public QueryRequest k(@javax.annotation.Nonnull Integer k) {

    this.k = k;
    return this;
  }

  /**
   * Number of results to return minimum: 0
   *
   * @return k
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_K)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getK() {
    return k;
  }

  @JsonProperty(JSON_PROPERTY_K)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setK(@javax.annotation.Nonnull Integer k) {
    this.k = k;
  }

  public QueryRequest lowerBound(@javax.annotation.Nullable Float lowerBound) {
    this.lowerBound = JsonNullable.<Float>of(lowerBound);

    return this;
  }

  /**
   * Lower bound for search
   *
   * @return lowerBound
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Float getLowerBound() {
    return lowerBound.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_LOWER_BOUND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Float> getLowerBound_JsonNullable() {
    return lowerBound;
  }

  @JsonProperty(JSON_PROPERTY_LOWER_BOUND)
  public void setLowerBound_JsonNullable(JsonNullable<Float> lowerBound) {
    this.lowerBound = lowerBound;
  }

  public void setLowerBound(@javax.annotation.Nullable Float lowerBound) {
    this.lowerBound = JsonNullable.<Float>of(lowerBound);
  }

  public QueryRequest nprobes(@javax.annotation.Nullable Integer nprobes) {
    this.nprobes = JsonNullable.<Integer>of(nprobes);

    return this;
  }

  /**
   * Number of probes for IVF index minimum: 0
   *
   * @return nprobes
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getNprobes() {
    return nprobes.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_NPROBES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getNprobes_JsonNullable() {
    return nprobes;
  }

  @JsonProperty(JSON_PROPERTY_NPROBES)
  public void setNprobes_JsonNullable(JsonNullable<Integer> nprobes) {
    this.nprobes = nprobes;
  }

  public void setNprobes(@javax.annotation.Nullable Integer nprobes) {
    this.nprobes = JsonNullable.<Integer>of(nprobes);
  }

  public QueryRequest offset(@javax.annotation.Nullable Integer offset) {
    this.offset = JsonNullable.<Integer>of(offset);

    return this;
  }

  /**
   * Number of results to skip minimum: 0
   *
   * @return offset
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getOffset() {
    return offset.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getOffset_JsonNullable() {
    return offset;
  }

  @JsonProperty(JSON_PROPERTY_OFFSET)
  public void setOffset_JsonNullable(JsonNullable<Integer> offset) {
    this.offset = offset;
  }

  public void setOffset(@javax.annotation.Nullable Integer offset) {
    this.offset = JsonNullable.<Integer>of(offset);
  }

  public QueryRequest prefilter(@javax.annotation.Nullable Boolean prefilter) {
    this.prefilter = JsonNullable.<Boolean>of(prefilter);

    return this;
  }

  /**
   * Whether to apply filtering before vector search
   *
   * @return prefilter
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getPrefilter() {
    return prefilter.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_PREFILTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getPrefilter_JsonNullable() {
    return prefilter;
  }

  @JsonProperty(JSON_PROPERTY_PREFILTER)
  public void setPrefilter_JsonNullable(JsonNullable<Boolean> prefilter) {
    this.prefilter = prefilter;
  }

  public void setPrefilter(@javax.annotation.Nullable Boolean prefilter) {
    this.prefilter = JsonNullable.<Boolean>of(prefilter);
  }

  public QueryRequest refineFactor(@javax.annotation.Nullable Integer refineFactor) {
    this.refineFactor = JsonNullable.<Integer>of(refineFactor);

    return this;
  }

  /**
   * Refine factor for search minimum: 0
   *
   * @return refineFactor
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Integer getRefineFactor() {
    return refineFactor.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_REFINE_FACTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Integer> getRefineFactor_JsonNullable() {
    return refineFactor;
  }

  @JsonProperty(JSON_PROPERTY_REFINE_FACTOR)
  public void setRefineFactor_JsonNullable(JsonNullable<Integer> refineFactor) {
    this.refineFactor = refineFactor;
  }

  public void setRefineFactor(@javax.annotation.Nullable Integer refineFactor) {
    this.refineFactor = JsonNullable.<Integer>of(refineFactor);
  }

  public QueryRequest upperBound(@javax.annotation.Nullable Float upperBound) {
    this.upperBound = JsonNullable.<Float>of(upperBound);

    return this;
  }

  /**
   * Upper bound for search
   *
   * @return upperBound
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Float getUpperBound() {
    return upperBound.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_UPPER_BOUND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Float> getUpperBound_JsonNullable() {
    return upperBound;
  }

  @JsonProperty(JSON_PROPERTY_UPPER_BOUND)
  public void setUpperBound_JsonNullable(JsonNullable<Float> upperBound) {
    this.upperBound = upperBound;
  }

  public void setUpperBound(@javax.annotation.Nullable Float upperBound) {
    this.upperBound = JsonNullable.<Float>of(upperBound);
  }

  public QueryRequest vector(@javax.annotation.Nonnull List<Float> vector) {

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
   * Query vector for similarity search (single vector only)
   *
   * @return vector
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VECTOR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<Float> getVector() {
    return vector;
  }

  @JsonProperty(JSON_PROPERTY_VECTOR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVector(@javax.annotation.Nonnull List<Float> vector) {
    this.vector = vector;
  }

  public QueryRequest vectorColumn(@javax.annotation.Nullable String vectorColumn) {
    this.vectorColumn = JsonNullable.<String>of(vectorColumn);

    return this;
  }

  /**
   * Name of the vector column to search
   *
   * @return vectorColumn
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getVectorColumn() {
    return vectorColumn.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_VECTOR_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getVectorColumn_JsonNullable() {
    return vectorColumn;
  }

  @JsonProperty(JSON_PROPERTY_VECTOR_COLUMN)
  public void setVectorColumn_JsonNullable(JsonNullable<String> vectorColumn) {
    this.vectorColumn = vectorColumn;
  }

  public void setVectorColumn(@javax.annotation.Nullable String vectorColumn) {
    this.vectorColumn = JsonNullable.<String>of(vectorColumn);
  }

  public QueryRequest version(@javax.annotation.Nullable Long version) {
    this.version = JsonNullable.<Long>of(version);

    return this;
  }

  /**
   * Table version to query minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Long getVersion() {
    return version.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Long> getVersion_JsonNullable() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  public void setVersion_JsonNullable(JsonNullable<Long> version) {
    this.version = version;
  }

  public void setVersion(@javax.annotation.Nullable Long version) {
    this.version = JsonNullable.<Long>of(version);
  }

  public QueryRequest withRowId(@javax.annotation.Nullable Boolean withRowId) {
    this.withRowId = JsonNullable.<Boolean>of(withRowId);

    return this;
  }

  /**
   * If true, return the row id as a column called &#x60;_rowid&#x60;
   *
   * @return withRowId
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getWithRowId() {
    return withRowId.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_WITH_ROW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getWithRowId_JsonNullable() {
    return withRowId;
  }

  @JsonProperty(JSON_PROPERTY_WITH_ROW_ID)
  public void setWithRowId_JsonNullable(JsonNullable<Boolean> withRowId) {
    this.withRowId = withRowId;
  }

  public void setWithRowId(@javax.annotation.Nullable Boolean withRowId) {
    this.withRowId = JsonNullable.<Boolean>of(withRowId);
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
        && equalsNullable(this.bypassVectorIndex, queryRequest.bypassVectorIndex)
        && Objects.equals(this.columns, queryRequest.columns)
        && equalsNullable(this.distanceType, queryRequest.distanceType)
        && equalsNullable(this.ef, queryRequest.ef)
        && equalsNullable(this.fastSearch, queryRequest.fastSearch)
        && equalsNullable(this.filter, queryRequest.filter)
        && Objects.equals(this.fullTextQuery, queryRequest.fullTextQuery)
        && Objects.equals(this.k, queryRequest.k)
        && equalsNullable(this.lowerBound, queryRequest.lowerBound)
        && equalsNullable(this.nprobes, queryRequest.nprobes)
        && equalsNullable(this.offset, queryRequest.offset)
        && equalsNullable(this.prefilter, queryRequest.prefilter)
        && equalsNullable(this.refineFactor, queryRequest.refineFactor)
        && equalsNullable(this.upperBound, queryRequest.upperBound)
        && Objects.equals(this.vector, queryRequest.vector)
        && equalsNullable(this.vectorColumn, queryRequest.vectorColumn)
        && equalsNullable(this.version, queryRequest.version)
        && equalsNullable(this.withRowId, queryRequest.withRowId);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        namespace,
        hashCodeNullable(bypassVectorIndex),
        columns,
        hashCodeNullable(distanceType),
        hashCodeNullable(ef),
        hashCodeNullable(fastSearch),
        hashCodeNullable(filter),
        fullTextQuery,
        k,
        hashCodeNullable(lowerBound),
        hashCodeNullable(nprobes),
        hashCodeNullable(offset),
        hashCodeNullable(prefilter),
        hashCodeNullable(refineFactor),
        hashCodeNullable(upperBound),
        vector,
        hashCodeNullable(vectorColumn),
        hashCodeNullable(version),
        hashCodeNullable(withRowId));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryRequest {\n");
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

    // add `bypass_vector_index` to the URL query string
    if (getBypassVectorIndex() != null) {
      try {
        joiner.add(
            String.format(
                "%sbypass_vector_index%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getBypassVectorIndex()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `columns` to the URL query string
    if (getColumns() != null) {
      for (int i = 0; i < getColumns().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%scolumns%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getColumns().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `distance_type` to the URL query string
    if (getDistanceType() != null) {
      try {
        joiner.add(
            String.format(
                "%sdistance_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getDistanceType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `ef` to the URL query string
    if (getEf() != null) {
      try {
        joiner.add(
            String.format(
                "%sef%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getEf()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `fast_search` to the URL query string
    if (getFastSearch() != null) {
      try {
        joiner.add(
            String.format(
                "%sfast_search%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getFastSearch()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `filter` to the URL query string
    if (getFilter() != null) {
      try {
        joiner.add(
            String.format(
                "%sfilter%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getFilter()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `full_text_query` to the URL query string
    if (getFullTextQuery() != null) {
      joiner.add(getFullTextQuery().toUrlQueryString(prefix + "full_text_query" + suffix));
    }

    // add `k` to the URL query string
    if (getK() != null) {
      try {
        joiner.add(
            String.format(
                "%sk%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getK()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `lower_bound` to the URL query string
    if (getLowerBound() != null) {
      try {
        joiner.add(
            String.format(
                "%slower_bound%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLowerBound()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `nprobes` to the URL query string
    if (getNprobes() != null) {
      try {
        joiner.add(
            String.format(
                "%snprobes%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNprobes()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `offset` to the URL query string
    if (getOffset() != null) {
      try {
        joiner.add(
            String.format(
                "%soffset%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getOffset()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `prefilter` to the URL query string
    if (getPrefilter() != null) {
      try {
        joiner.add(
            String.format(
                "%sprefilter%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPrefilter()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `refine_factor` to the URL query string
    if (getRefineFactor() != null) {
      try {
        joiner.add(
            String.format(
                "%srefine_factor%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getRefineFactor()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `upper_bound` to the URL query string
    if (getUpperBound() != null) {
      try {
        joiner.add(
            String.format(
                "%supper_bound%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUpperBound()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `vector` to the URL query string
    if (getVector() != null) {
      for (int i = 0; i < getVector().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%svector%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getVector().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `vector_column` to the URL query string
    if (getVectorColumn() != null) {
      try {
        joiner.add(
            String.format(
                "%svector_column%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVectorColumn()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sversion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVersion()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `with_row_id` to the URL query string
    if (getWithRowId() != null) {
      try {
        joiner.add(
            String.format(
                "%swith_row_id%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWithRowId()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
