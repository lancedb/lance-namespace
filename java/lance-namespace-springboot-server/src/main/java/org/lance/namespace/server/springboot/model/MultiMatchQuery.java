package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.MatchQuery;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MultiMatchQuery
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class MultiMatchQuery {

  @Valid
  private List<@Valid MatchQuery> matchQueries = new ArrayList<>();

  public MultiMatchQuery() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
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
   * @return matchQueries
   */
  @NotNull @Valid 
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
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

