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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;
import java.util.StringJoiner;

/** Optional full-text search query. Provide either string_query or structured_query, not both. */
@JsonPropertyOrder({
  QueryTableRequestFullTextQuery.JSON_PROPERTY_STRING_QUERY,
  QueryTableRequestFullTextQuery.JSON_PROPERTY_STRUCTURED_QUERY
})
@JsonTypeName("QueryTableRequest_full_text_query")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableRequestFullTextQuery {
  public static final String JSON_PROPERTY_STRING_QUERY = "string_query";
  @javax.annotation.Nullable private StringFtsQuery stringQuery;

  public static final String JSON_PROPERTY_STRUCTURED_QUERY = "structured_query";
  @javax.annotation.Nullable private StructuredFtsQuery structuredQuery;

  public QueryTableRequestFullTextQuery() {}

  public QueryTableRequestFullTextQuery stringQuery(
      @javax.annotation.Nullable StringFtsQuery stringQuery) {

    this.stringQuery = stringQuery;
    return this;
  }

  /**
   * Get stringQuery
   *
   * @return stringQuery
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STRING_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public StringFtsQuery getStringQuery() {
    return stringQuery;
  }

  @JsonProperty(JSON_PROPERTY_STRING_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStringQuery(@javax.annotation.Nullable StringFtsQuery stringQuery) {
    this.stringQuery = stringQuery;
  }

  public QueryTableRequestFullTextQuery structuredQuery(
      @javax.annotation.Nullable StructuredFtsQuery structuredQuery) {

    this.structuredQuery = structuredQuery;
    return this;
  }

  /**
   * Get structuredQuery
   *
   * @return structuredQuery
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STRUCTURED_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public StructuredFtsQuery getStructuredQuery() {
    return structuredQuery;
  }

  @JsonProperty(JSON_PROPERTY_STRUCTURED_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStructuredQuery(@javax.annotation.Nullable StructuredFtsQuery structuredQuery) {
    this.structuredQuery = structuredQuery;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryTableRequestFullTextQuery queryTableRequestFullTextQuery =
        (QueryTableRequestFullTextQuery) o;
    return Objects.equals(this.stringQuery, queryTableRequestFullTextQuery.stringQuery)
        && Objects.equals(this.structuredQuery, queryTableRequestFullTextQuery.structuredQuery);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stringQuery, structuredQuery);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryTableRequestFullTextQuery {\n");
    sb.append("    stringQuery: ").append(toIndentedString(stringQuery)).append("\n");
    sb.append("    structuredQuery: ").append(toIndentedString(structuredQuery)).append("\n");
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

    // add `string_query` to the URL query string
    if (getStringQuery() != null) {
      joiner.add(getStringQuery().toUrlQueryString(prefix + "string_query" + suffix));
    }

    // add `structured_query` to the URL query string
    if (getStructuredQuery() != null) {
      joiner.add(getStructuredQuery().toUrlQueryString(prefix + "structured_query" + suffix));
    }

    return joiner.toString();
  }
}
