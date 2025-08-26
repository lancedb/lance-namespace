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
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** Optional full-text search query. Provide either string_query or structured_query, not both. */
@Schema(
    name = "QueryTableRequest_full_text_query",
    description =
        "Optional full-text search query. Provide either string_query or structured_query, not both.")
@JsonTypeName("QueryTableRequest_full_text_query")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableRequestFullTextQuery {

  private StringFtsQuery stringQuery;

  private StructuredFtsQuery structuredQuery;

  public QueryTableRequestFullTextQuery stringQuery(StringFtsQuery stringQuery) {
    this.stringQuery = stringQuery;
    return this;
  }

  /**
   * Get stringQuery
   *
   * @return stringQuery
   */
  @Valid
  @Schema(name = "string_query", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("string_query")
  public StringFtsQuery getStringQuery() {
    return stringQuery;
  }

  public void setStringQuery(StringFtsQuery stringQuery) {
    this.stringQuery = stringQuery;
  }

  public QueryTableRequestFullTextQuery structuredQuery(StructuredFtsQuery structuredQuery) {
    this.structuredQuery = structuredQuery;
    return this;
  }

  /**
   * Get structuredQuery
   *
   * @return structuredQuery
   */
  @Valid
  @Schema(name = "structured_query", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("structured_query")
  public StructuredFtsQuery getStructuredQuery() {
    return structuredQuery;
  }

  public void setStructuredQuery(StructuredFtsQuery structuredQuery) {
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
}
