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
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** BoostQuery */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class BoostQuery {

  private Object negative;

  private Float negativeBoost;

  private Object positive;

  public BoostQuery() {
    super();
  }

  /** Constructor with only required parameters */
  public BoostQuery(Object negative, Object positive) {
    this.negative = negative;
    this.positive = positive;
  }

  public BoostQuery negative(Object negative) {
    this.negative = negative;
    return this;
  }

  /**
   * Get negative
   *
   * @return negative
   */
  @NotNull
  @Schema(name = "negative", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("negative")
  public Object getNegative() {
    return negative;
  }

  public void setNegative(Object negative) {
    this.negative = negative;
  }

  public BoostQuery negativeBoost(Float negativeBoost) {
    this.negativeBoost = negativeBoost;
    return this;
  }

  /**
   * Get negativeBoost
   *
   * @return negativeBoost
   */
  @Schema(name = "negative_boost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("negative_boost")
  public Float getNegativeBoost() {
    return negativeBoost;
  }

  public void setNegativeBoost(Float negativeBoost) {
    this.negativeBoost = negativeBoost;
  }

  public BoostQuery positive(Object positive) {
    this.positive = positive;
    return this;
  }

  /**
   * Get positive
   *
   * @return positive
   */
  @NotNull
  @Schema(name = "positive", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("positive")
  public Object getPositive() {
    return positive;
  }

  public void setPositive(Object positive) {
    this.positive = positive;
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
    return Objects.equals(this.negative, boostQuery.negative)
        && Objects.equals(this.negativeBoost, boostQuery.negativeBoost)
        && Objects.equals(this.positive, boostQuery.positive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(negative, negativeBoost, positive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoostQuery {\n");
    sb.append("    negative: ").append(toIndentedString(negative)).append("\n");
    sb.append("    negativeBoost: ").append(toIndentedString(negativeBoost)).append("\n");
    sb.append("    positive: ").append(toIndentedString(positive)).append("\n");
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
