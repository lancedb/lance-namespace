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

/** QueryRequest */
@JsonPropertyOrder({
  QueryRequest.JSON_PROPERTY_NAME,
  QueryRequest.JSON_PROPERTY_NAMESPACE,
  QueryRequest.JSON_PROPERTY_VECTOR,
  QueryRequest.JSON_PROPERTY_K,
  QueryRequest.JSON_PROPERTY_FILTER,
  QueryRequest.JSON_PROPERTY_COLUMNS,
  QueryRequest.JSON_PROPERTY_DISTANCE_TYPE,
  QueryRequest.JSON_PROPERTY_PREFILTER,
  QueryRequest.JSON_PROPERTY_BYPASS_VECTOR_INDEX,
  QueryRequest.JSON_PROPERTY_EF,
  QueryRequest.JSON_PROPERTY_NPROBES,
  QueryRequest.JSON_PROPERTY_REFINE_FACTOR,
  QueryRequest.JSON_PROPERTY_WITH_ROW_ID,
  QueryRequest.JSON_PROPERTY_OFFSET,
  QueryRequest.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class QueryRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nonnull private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_VECTOR = "vector";
  @javax.annotation.Nonnull private List<Float> vector = new ArrayList<>();

  public static final String JSON_PROPERTY_K = "k";
  @javax.annotation.Nonnull private Integer k;

  public static final String JSON_PROPERTY_FILTER = "filter";
  @javax.annotation.Nullable private String filter;

  public static final String JSON_PROPERTY_COLUMNS = "columns";
  @javax.annotation.Nullable private List<String> columns = new ArrayList<>();

  /** Distance metric to use */
  public enum DistanceTypeEnum {
    L2(String.valueOf("l2")),

    COSINE(String.valueOf("cosine")),

    DOT(String.valueOf("dot"));

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

  public static final String JSON_PROPERTY_DISTANCE_TYPE = "distance_type";
  @javax.annotation.Nullable private DistanceTypeEnum distanceType;

  public static final String JSON_PROPERTY_PREFILTER = "prefilter";
  @javax.annotation.Nullable private Boolean prefilter;

  public static final String JSON_PROPERTY_BYPASS_VECTOR_INDEX = "bypass_vector_index";
  @javax.annotation.Nullable private Boolean bypassVectorIndex;

  public static final String JSON_PROPERTY_EF = "ef";
  @javax.annotation.Nullable private Integer ef;

  public static final String JSON_PROPERTY_NPROBES = "nprobes";
  @javax.annotation.Nullable private Integer nprobes;

  public static final String JSON_PROPERTY_REFINE_FACTOR = "refine_factor";
  @javax.annotation.Nullable private Integer refineFactor;

  public static final String JSON_PROPERTY_WITH_ROW_ID = "with_row_id";
  @javax.annotation.Nullable private Boolean withRowId;

  public static final String JSON_PROPERTY_OFFSET = "offset";
  @javax.annotation.Nullable private Integer offset;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

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
   * Query vector for similarity search
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

  public QueryRequest k(@javax.annotation.Nonnull Integer k) {

    this.k = k;
    return this;
  }

  /**
   * Number of results to return minimum: 1
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

  public QueryRequest filter(@javax.annotation.Nullable String filter) {

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

  public QueryRequest distanceType(@javax.annotation.Nullable DistanceTypeEnum distanceType) {

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
  public DistanceTypeEnum getDistanceType() {
    return distanceType;
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDistanceType(@javax.annotation.Nullable DistanceTypeEnum distanceType) {
    this.distanceType = distanceType;
  }

  public QueryRequest prefilter(@javax.annotation.Nullable Boolean prefilter) {

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

  public QueryRequest bypassVectorIndex(@javax.annotation.Nullable Boolean bypassVectorIndex) {

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

  public QueryRequest ef(@javax.annotation.Nullable Integer ef) {

    this.ef = ef;
    return this;
  }

  /**
   * Search effort parameter for HNSW index minimum: 1
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

  public QueryRequest nprobes(@javax.annotation.Nullable Integer nprobes) {

    this.nprobes = nprobes;
    return this;
  }

  /**
   * Number of probes for IVF index minimum: 1
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

  public QueryRequest refineFactor(@javax.annotation.Nullable Integer refineFactor) {

    this.refineFactor = refineFactor;
    return this;
  }

  /**
   * Refine factor for search minimum: 1
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

  public QueryRequest withRowId(@javax.annotation.Nullable Boolean withRowId) {

    this.withRowId = withRowId;
    return this;
  }

  /**
   * Whether to include row ID in results
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

  public QueryRequest offset(@javax.annotation.Nullable Integer offset) {

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

  public QueryRequest version(@javax.annotation.Nullable Long version) {

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

    return joiner.toString();
  }
}
