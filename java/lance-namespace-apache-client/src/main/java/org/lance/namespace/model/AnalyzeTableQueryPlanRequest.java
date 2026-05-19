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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** AnalyzeTableQueryPlanRequest */
@JsonPropertyOrder({
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_IDENTITY,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_CONTEXT,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_ID,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_BYPASS_VECTOR_INDEX,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_COLUMNS,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_DISTANCE_TYPE,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_EF,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_FAST_SEARCH,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_FILTER,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_FULL_TEXT_QUERY,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_K,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_LOWER_BOUND,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_NPROBES,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_OFFSET,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_PREFILTER,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_REFINE_FACTOR,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_UPPER_BOUND,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_VECTOR,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_VECTOR_COLUMN,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_VERSION,
  AnalyzeTableQueryPlanRequest.JSON_PROPERTY_WITH_ROW_ID
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AnalyzeTableQueryPlanRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_BYPASS_VECTOR_INDEX = "bypass_vector_index";
  @javax.annotation.Nullable private Boolean bypassVectorIndex;

  public static final String JSON_PROPERTY_COLUMNS = "columns";
  @javax.annotation.Nullable private QueryTableRequestColumns columns;

  public static final String JSON_PROPERTY_DISTANCE_TYPE = "distance_type";
  @javax.annotation.Nullable private String distanceType;

  public static final String JSON_PROPERTY_EF = "ef";
  @javax.annotation.Nullable private Integer ef;

  public static final String JSON_PROPERTY_FAST_SEARCH = "fast_search";
  @javax.annotation.Nullable private Boolean fastSearch;

  public static final String JSON_PROPERTY_FILTER = "filter";
  @javax.annotation.Nullable private String filter;

  public static final String JSON_PROPERTY_FULL_TEXT_QUERY = "full_text_query";
  @javax.annotation.Nullable private QueryTableRequestFullTextQuery fullTextQuery;

  public static final String JSON_PROPERTY_K = "k";
  @javax.annotation.Nonnull private Integer k;

  public static final String JSON_PROPERTY_LOWER_BOUND = "lower_bound";
  @javax.annotation.Nullable private Float lowerBound;

  public static final String JSON_PROPERTY_NPROBES = "nprobes";
  @javax.annotation.Nullable private Integer nprobes;

  public static final String JSON_PROPERTY_OFFSET = "offset";
  @javax.annotation.Nullable private Integer offset;

  public static final String JSON_PROPERTY_PREFILTER = "prefilter";
  @javax.annotation.Nullable private Boolean prefilter;

  public static final String JSON_PROPERTY_REFINE_FACTOR = "refine_factor";
  @javax.annotation.Nullable private Integer refineFactor;

  public static final String JSON_PROPERTY_UPPER_BOUND = "upper_bound";
  @javax.annotation.Nullable private Float upperBound;

  public static final String JSON_PROPERTY_VECTOR = "vector";
  @javax.annotation.Nonnull private QueryTableRequestVector vector;

  public static final String JSON_PROPERTY_VECTOR_COLUMN = "vector_column";
  @javax.annotation.Nullable private String vectorColumn;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

  public static final String JSON_PROPERTY_WITH_ROW_ID = "with_row_id";
  @javax.annotation.Nullable private Boolean withRowId;

  public AnalyzeTableQueryPlanRequest() {}

  public AnalyzeTableQueryPlanRequest identity(@javax.annotation.Nullable Identity identity) {

    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public AnalyzeTableQueryPlanRequest context(
      @javax.annotation.Nullable Map<String, String> context) {

    this.context = context;
    return this;
  }

  public AnalyzeTableQueryPlanRequest putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context for a request as key-value pairs. How to use the context is custom to the
   * specific implementation. REST NAMESPACE ONLY Context entries are passed via HTTP headers using
   * the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a
   * context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the
   * header &#x60;x-lance-ctx-trace_id: abc123&#x60;.
   *
   * @return context
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getContext() {
    return context;
  }

  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setContext(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
  }

  public AnalyzeTableQueryPlanRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public AnalyzeTableQueryPlanRequest addIdItem(String idItem) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public AnalyzeTableQueryPlanRequest bypassVectorIndex(
      @javax.annotation.Nullable Boolean bypassVectorIndex) {

    this.bypassVectorIndex = bypassVectorIndex;
    return this;
  }

  /**
   * Whether to bypass vector index
   *
   * @return bypassVectorIndex
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BYPASS_VECTOR_INDEX)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getBypassVectorIndex() {
    return bypassVectorIndex;
  }

  @JsonProperty(JSON_PROPERTY_BYPASS_VECTOR_INDEX)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBypassVectorIndex(@javax.annotation.Nullable Boolean bypassVectorIndex) {
    this.bypassVectorIndex = bypassVectorIndex;
  }

  public AnalyzeTableQueryPlanRequest columns(
      @javax.annotation.Nullable QueryTableRequestColumns columns) {

    this.columns = columns;
    return this;
  }

  /**
   * Get columns
   *
   * @return columns
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public QueryTableRequestColumns getColumns() {
    return columns;
  }

  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setColumns(@javax.annotation.Nullable QueryTableRequestColumns columns) {
    this.columns = columns;
  }

  public AnalyzeTableQueryPlanRequest distanceType(@javax.annotation.Nullable String distanceType) {

    this.distanceType = distanceType;
    return this;
  }

  /**
   * Distance metric to use
   *
   * @return distanceType
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getDistanceType() {
    return distanceType;
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDistanceType(@javax.annotation.Nullable String distanceType) {
    this.distanceType = distanceType;
  }

  public AnalyzeTableQueryPlanRequest ef(@javax.annotation.Nullable Integer ef) {

    this.ef = ef;
    return this;
  }

  /**
   * Search effort parameter for HNSW index minimum: 0
   *
   * @return ef
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_EF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getEf() {
    return ef;
  }

  @JsonProperty(JSON_PROPERTY_EF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEf(@javax.annotation.Nullable Integer ef) {
    this.ef = ef;
  }

  public AnalyzeTableQueryPlanRequest fastSearch(@javax.annotation.Nullable Boolean fastSearch) {

    this.fastSearch = fastSearch;
    return this;
  }

  /**
   * Whether to use fast search
   *
   * @return fastSearch
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FAST_SEARCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getFastSearch() {
    return fastSearch;
  }

  @JsonProperty(JSON_PROPERTY_FAST_SEARCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFastSearch(@javax.annotation.Nullable Boolean fastSearch) {
    this.fastSearch = fastSearch;
  }

  public AnalyzeTableQueryPlanRequest filter(@javax.annotation.Nullable String filter) {

    this.filter = filter;
    return this;
  }

  /**
   * Optional SQL filter expression
   *
   * @return filter
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FILTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getFilter() {
    return filter;
  }

  @JsonProperty(JSON_PROPERTY_FILTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFilter(@javax.annotation.Nullable String filter) {
    this.filter = filter;
  }

  public AnalyzeTableQueryPlanRequest fullTextQuery(
      @javax.annotation.Nullable QueryTableRequestFullTextQuery fullTextQuery) {

    this.fullTextQuery = fullTextQuery;
    return this;
  }

  /**
   * Get fullTextQuery
   *
   * @return fullTextQuery
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FULL_TEXT_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public QueryTableRequestFullTextQuery getFullTextQuery() {
    return fullTextQuery;
  }

  @JsonProperty(JSON_PROPERTY_FULL_TEXT_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFullTextQuery(
      @javax.annotation.Nullable QueryTableRequestFullTextQuery fullTextQuery) {
    this.fullTextQuery = fullTextQuery;
  }

  public AnalyzeTableQueryPlanRequest k(@javax.annotation.Nonnull Integer k) {

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

  public AnalyzeTableQueryPlanRequest lowerBound(@javax.annotation.Nullable Float lowerBound) {

    this.lowerBound = lowerBound;
    return this;
  }

  /**
   * Lower bound for search
   *
   * @return lowerBound
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOWER_BOUND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Float getLowerBound() {
    return lowerBound;
  }

  @JsonProperty(JSON_PROPERTY_LOWER_BOUND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLowerBound(@javax.annotation.Nullable Float lowerBound) {
    this.lowerBound = lowerBound;
  }

  public AnalyzeTableQueryPlanRequest nprobes(@javax.annotation.Nullable Integer nprobes) {

    this.nprobes = nprobes;
    return this;
  }

  /**
   * Number of probes for IVF index minimum: 0
   *
   * @return nprobes
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NPROBES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getNprobes() {
    return nprobes;
  }

  @JsonProperty(JSON_PROPERTY_NPROBES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNprobes(@javax.annotation.Nullable Integer nprobes) {
    this.nprobes = nprobes;
  }

  public AnalyzeTableQueryPlanRequest offset(@javax.annotation.Nullable Integer offset) {

    this.offset = offset;
    return this;
  }

  /**
   * Number of results to skip minimum: 0
   *
   * @return offset
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getOffset() {
    return offset;
  }

  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOffset(@javax.annotation.Nullable Integer offset) {
    this.offset = offset;
  }

  public AnalyzeTableQueryPlanRequest prefilter(@javax.annotation.Nullable Boolean prefilter) {

    this.prefilter = prefilter;
    return this;
  }

  /**
   * Whether to apply filtering before vector search
   *
   * @return prefilter
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PREFILTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getPrefilter() {
    return prefilter;
  }

  @JsonProperty(JSON_PROPERTY_PREFILTER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPrefilter(@javax.annotation.Nullable Boolean prefilter) {
    this.prefilter = prefilter;
  }

  public AnalyzeTableQueryPlanRequest refineFactor(
      @javax.annotation.Nullable Integer refineFactor) {

    this.refineFactor = refineFactor;
    return this;
  }

  /**
   * Refine factor for search minimum: 0
   *
   * @return refineFactor
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_REFINE_FACTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getRefineFactor() {
    return refineFactor;
  }

  @JsonProperty(JSON_PROPERTY_REFINE_FACTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRefineFactor(@javax.annotation.Nullable Integer refineFactor) {
    this.refineFactor = refineFactor;
  }

  public AnalyzeTableQueryPlanRequest upperBound(@javax.annotation.Nullable Float upperBound) {

    this.upperBound = upperBound;
    return this;
  }

  /**
   * Upper bound for search
   *
   * @return upperBound
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_UPPER_BOUND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Float getUpperBound() {
    return upperBound;
  }

  @JsonProperty(JSON_PROPERTY_UPPER_BOUND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUpperBound(@javax.annotation.Nullable Float upperBound) {
    this.upperBound = upperBound;
  }

  public AnalyzeTableQueryPlanRequest vector(
      @javax.annotation.Nonnull QueryTableRequestVector vector) {

    this.vector = vector;
    return this;
  }

  /**
   * Get vector
   *
   * @return vector
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VECTOR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public QueryTableRequestVector getVector() {
    return vector;
  }

  @JsonProperty(JSON_PROPERTY_VECTOR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVector(@javax.annotation.Nonnull QueryTableRequestVector vector) {
    this.vector = vector;
  }

  public AnalyzeTableQueryPlanRequest vectorColumn(@javax.annotation.Nullable String vectorColumn) {

    this.vectorColumn = vectorColumn;
    return this;
  }

  /**
   * Name of the vector column to search
   *
   * @return vectorColumn
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VECTOR_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getVectorColumn() {
    return vectorColumn;
  }

  @JsonProperty(JSON_PROPERTY_VECTOR_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVectorColumn(@javax.annotation.Nullable String vectorColumn) {
    this.vectorColumn = vectorColumn;
  }

  public AnalyzeTableQueryPlanRequest version(@javax.annotation.Nullable Long version) {

    this.version = version;
    return this;
  }

  /**
   * Table version to query minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(@javax.annotation.Nullable Long version) {
    this.version = version;
  }

  public AnalyzeTableQueryPlanRequest withRowId(@javax.annotation.Nullable Boolean withRowId) {

    this.withRowId = withRowId;
    return this;
  }

  /**
   * If true, return the row id as a column called &#x60;_rowid&#x60;
   *
   * @return withRowId
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WITH_ROW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWithRowId() {
    return withRowId;
  }

  @JsonProperty(JSON_PROPERTY_WITH_ROW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWithRowId(@javax.annotation.Nullable Boolean withRowId) {
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
    AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest = (AnalyzeTableQueryPlanRequest) o;
    return Objects.equals(this.identity, analyzeTableQueryPlanRequest.identity)
        && Objects.equals(this.context, analyzeTableQueryPlanRequest.context)
        && Objects.equals(this.id, analyzeTableQueryPlanRequest.id)
        && Objects.equals(this.bypassVectorIndex, analyzeTableQueryPlanRequest.bypassVectorIndex)
        && Objects.equals(this.columns, analyzeTableQueryPlanRequest.columns)
        && Objects.equals(this.distanceType, analyzeTableQueryPlanRequest.distanceType)
        && Objects.equals(this.ef, analyzeTableQueryPlanRequest.ef)
        && Objects.equals(this.fastSearch, analyzeTableQueryPlanRequest.fastSearch)
        && Objects.equals(this.filter, analyzeTableQueryPlanRequest.filter)
        && Objects.equals(this.fullTextQuery, analyzeTableQueryPlanRequest.fullTextQuery)
        && Objects.equals(this.k, analyzeTableQueryPlanRequest.k)
        && Objects.equals(this.lowerBound, analyzeTableQueryPlanRequest.lowerBound)
        && Objects.equals(this.nprobes, analyzeTableQueryPlanRequest.nprobes)
        && Objects.equals(this.offset, analyzeTableQueryPlanRequest.offset)
        && Objects.equals(this.prefilter, analyzeTableQueryPlanRequest.prefilter)
        && Objects.equals(this.refineFactor, analyzeTableQueryPlanRequest.refineFactor)
        && Objects.equals(this.upperBound, analyzeTableQueryPlanRequest.upperBound)
        && Objects.equals(this.vector, analyzeTableQueryPlanRequest.vector)
        && Objects.equals(this.vectorColumn, analyzeTableQueryPlanRequest.vectorColumn)
        && Objects.equals(this.version, analyzeTableQueryPlanRequest.version)
        && Objects.equals(this.withRowId, analyzeTableQueryPlanRequest.withRowId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity,
        context,
        id,
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
    sb.append("class AnalyzeTableQueryPlanRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

    // add `context` to the URL query string
    if (getContext() != null) {
      for (String _key : getContext().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%scontext%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getContext().get(_key),
                  URLEncoder.encode(String.valueOf(getContext().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
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
      joiner.add(getColumns().toUrlQueryString(prefix + "columns" + suffix));
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
      joiner.add(getVector().toUrlQueryString(prefix + "vector" + suffix));
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
