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
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** MultiMatchQuery */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class MultiMatchQuery {

  @Valid private List<@Valid MatchQuery> matchQueries = new ArrayList<>();

  public MultiMatchQuery() {
    super();
  }

  /** Constructor with only required parameters */
  public MultiMatchQuery(List<@Valid MatchQuery> matchQueries) {
    this.matchQueries = matchQueries;
  }

  public MultiMatchQuery matchQueries(List<@Valid MatchQuery> matchQueries) {
    this.matchQueries = matchQueries;
    return this;
  }

  public MultiMatchQuery addMatchQueriesItem(MatchQuery matchQueriesItem) {
    if (this.matchQueries == null) {
      this.matchQueries = new ArrayList<>();
    }
    this.matchQueries.add(matchQueriesItem);
    return this;
  }

  /**
   * Get matchQueries
   *
   * @return matchQueries
   */
  @NotNull
  @Valid
  @Schema(name = "match_queries", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("match_queries")
  public List<@Valid MatchQuery> getMatchQueries() {
    return matchQueries;
  }

  public void setMatchQueries(List<@Valid MatchQuery> matchQueries) {
    this.matchQueries = matchQueries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiMatchQuery multiMatchQuery = (MultiMatchQuery) o;
    return Objects.equals(this.matchQueries, multiMatchQuery.matchQueries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(matchQueries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MultiMatchQuery {\n");
    sb.append("    matchQueries: ").append(toIndentedString(matchQueries)).append("\n");
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
