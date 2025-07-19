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
import java.util.Objects;

/** Boost query that scores documents matching positive query higher and negative query lower */
@Schema(
    name = "BoostQuery",
    description =
        "Boost query that scores documents matching positive query higher and negative query lower")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class BoostQuery {

  private FtsQuery positive;

  private FtsQuery negative;

  private Float negativeBoost = 0.5f;

  public BoostQuery() {
    super();
  }

  /** Constructor with only required parameters */
  public BoostQuery(FtsQuery positive, FtsQuery negative) {
    this.positive = positive;
    this.negative = negative;
  }

  public BoostQuery positive(FtsQuery positive) {
    this.positive = positive;
    return this;
  }

  /**
   * Get positive
   *
   * @return positive
   */
  @NotNull
  @Valid
  @Schema(name = "positive", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("positive")
  public FtsQuery getPositive() {
    return positive;
  }

  public void setPositive(FtsQuery positive) {
    this.positive = positive;
  }

  public BoostQuery negative(FtsQuery negative) {
    this.negative = negative;
    return this;
  }

  /**
   * Get negative
   *
   * @return negative
   */
  @NotNull
  @Valid
  @Schema(name = "negative", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("negative")
  public FtsQuery getNegative() {
    return negative;
  }

  public void setNegative(FtsQuery negative) {
    this.negative = negative;
  }

  public BoostQuery negativeBoost(Float negativeBoost) {
    this.negativeBoost = negativeBoost;
    return this;
  }

  /**
   * Boost factor for negative query (default: 0.5)
   *
   * @return negativeBoost
   */
  @Schema(
      name = "negative_boost",
      description = "Boost factor for negative query (default: 0.5)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("negative_boost")
  public Float getNegativeBoost() {
    return negativeBoost;
  }

  public void setNegativeBoost(Float negativeBoost) {
    this.negativeBoost = negativeBoost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoostQuery boostQuery = (BoostQuery) o;
    return Objects.equals(this.positive, boostQuery.positive)
        && Objects.equals(this.negative, boostQuery.negative)
        && Objects.equals(this.negativeBoost, boostQuery.negativeBoost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(positive, negative, negativeBoost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoostQuery {\n");
    sb.append("    positive: ").append(toIndentedString(positive)).append("\n");
    sb.append("    negative: ").append(toIndentedString(negative)).append("\n");
    sb.append("    negativeBoost: ").append(toIndentedString(negativeBoost)).append("\n");
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
