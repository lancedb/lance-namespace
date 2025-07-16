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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** MultiMatchQuery */
@JsonPropertyOrder({MultiMatchQuery.JSON_PROPERTY_MATCH_QUERIES})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MultiMatchQuery {
  public static final String JSON_PROPERTY_MATCH_QUERIES = "match_queries";
  @javax.annotation.Nonnull private List<MatchQuery> matchQueries = new ArrayList<>();

  public MultiMatchQuery() {}

  public MultiMatchQuery matchQueries(@javax.annotation.Nonnull List<MatchQuery> matchQueries) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MATCH_QUERIES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<MatchQuery> getMatchQueries() {
    return matchQueries;
  }

  @JsonProperty(JSON_PROPERTY_MATCH_QUERIES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMatchQueries(@javax.annotation.Nonnull List<MatchQuery> matchQueries) {
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

    // add `match_queries` to the URL query string
    if (getMatchQueries() != null) {
      for (int i = 0; i < getMatchQueries().size(); i++) {
        if (getMatchQueries().get(i) != null) {
          joiner.add(
              getMatchQueries()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%smatch_queries%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}
