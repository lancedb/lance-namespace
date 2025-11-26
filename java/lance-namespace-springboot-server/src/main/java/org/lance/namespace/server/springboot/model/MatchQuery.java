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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** MatchQuery */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class MatchQuery {

  private Float boost;

  private String column;

  private Integer fuzziness;

  private Integer maxExpansions;

  private Operator operator;

  private Integer prefixLength;

  private String terms;

  public MatchQuery() {
    super();
  }

  /** Constructor with only required parameters */
  public MatchQuery(String column, String terms) {
    this.column = column;
    this.terms = terms;
  }

  public MatchQuery boost(Float boost) {
    this.boost = boost;
    return this;
  }

  /**
   * Get boost
   *
   * @return boost
   */
  @Schema(name = "boost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("boost")
  public Float getBoost() {
    return boost;
  }

  public void setBoost(Float boost) {
    this.boost = boost;
  }

  public MatchQuery column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Get column
   *
   * @return column
   */
  @NotNull
  @Schema(name = "column", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public MatchQuery fuzziness(Integer fuzziness) {
    this.fuzziness = fuzziness;
    return this;
  }

  /**
   * Get fuzziness minimum: 0
   *
   * @return fuzziness
   */
  @Min(0)
  @Schema(name = "fuzziness", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fuzziness")
  public Integer getFuzziness() {
    return fuzziness;
  }

  public void setFuzziness(Integer fuzziness) {
    this.fuzziness = fuzziness;
  }

  public MatchQuery maxExpansions(Integer maxExpansions) {
    this.maxExpansions = maxExpansions;
    return this;
  }

  /**
   * The maximum number of terms to expand for fuzzy matching. Default to 50. minimum: 0
   *
   * @return maxExpansions
   */
  @Min(0)
  @Schema(
      name = "max_expansions",
      description = "The maximum number of terms to expand for fuzzy matching. Default to 50.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_expansions")
  public Integer getMaxExpansions() {
    return maxExpansions;
  }

  public void setMaxExpansions(Integer maxExpansions) {
    this.maxExpansions = maxExpansions;
  }

  public MatchQuery operator(Operator operator) {
    this.operator = operator;
    return this;
  }

  /**
   * The operator to use for combining terms. This can be either `And` or `Or`, it's 'Or' by
   * default. - `And`: All terms must match. - `Or`: At least one term must match.
   *
   * @return operator
   */
  @Valid
  @Schema(
      name = "operator",
      description =
          "The operator to use for combining terms. This can be either `And` or `Or`, it's 'Or' by default. - `And`: All terms must match. - `Or`: At least one term must match.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("operator")
  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public MatchQuery prefixLength(Integer prefixLength) {
    this.prefixLength = prefixLength;
    return this;
  }

  /**
   * The number of beginning characters being unchanged for fuzzy matching. Default to 0. minimum: 0
   *
   * @return prefixLength
   */
  @Min(0)
  @Schema(
      name = "prefix_length",
      description =
          "The number of beginning characters being unchanged for fuzzy matching. Default to 0.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("prefix_length")
  public Integer getPrefixLength() {
    return prefixLength;
  }

  public void setPrefixLength(Integer prefixLength) {
    this.prefixLength = prefixLength;
  }

  public MatchQuery terms(String terms) {
    this.terms = terms;
    return this;
  }

  /**
   * Get terms
   *
   * @return terms
   */
  @NotNull
  @Schema(name = "terms", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("terms")
  public String getTerms() {
    return terms;
  }

  public void setTerms(String terms) {
    this.terms = terms;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MatchQuery matchQuery = (MatchQuery) o;
    return Objects.equals(this.boost, matchQuery.boost)
        && Objects.equals(this.column, matchQuery.column)
        && Objects.equals(this.fuzziness, matchQuery.fuzziness)
        && Objects.equals(this.maxExpansions, matchQuery.maxExpansions)
        && Objects.equals(this.operator, matchQuery.operator)
        && Objects.equals(this.prefixLength, matchQuery.prefixLength)
        && Objects.equals(this.terms, matchQuery.terms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boost, column, fuzziness, maxExpansions, operator, prefixLength, terms);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MatchQuery {\n");
    sb.append("    boost: ").append(toIndentedString(boost)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    fuzziness: ").append(toIndentedString(fuzziness)).append("\n");
    sb.append("    maxExpansions: ").append(toIndentedString(maxExpansions)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    prefixLength: ").append(toIndentedString(prefixLength)).append("\n");
    sb.append("    terms: ").append(toIndentedString(terms)).append("\n");
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
