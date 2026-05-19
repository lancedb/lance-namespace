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
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** FragmentSummary */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FragmentSummary {

  private Long min;

  private Long max;

  private Long mean;

  private Long p25;

  private Long p50;

  private Long p75;

  private Long p99;

  public FragmentSummary() {
    super();
  }

  /** Constructor with only required parameters */
  public FragmentSummary(Long min, Long max, Long mean, Long p25, Long p50, Long p75, Long p99) {
    this.min = min;
    this.max = max;
    this.mean = mean;
    this.p25 = p25;
    this.p50 = p50;
    this.p75 = p75;
    this.p99 = p99;
  }

  public FragmentSummary min(Long min) {
    this.min = min;
    return this;
  }

  /**
   * Get min minimum: 0
   *
   * @return min
   */
  @NotNull
  @Min(0L)
  @Schema(name = "min", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("min")
  public Long getMin() {
    return min;
  }

  public void setMin(Long min) {
    this.min = min;
  }

  public FragmentSummary max(Long max) {
    this.max = max;
    return this;
  }

  /**
   * Get max minimum: 0
   *
   * @return max
   */
  @NotNull
  @Min(0L)
  @Schema(name = "max", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("max")
  public Long getMax() {
    return max;
  }

  public void setMax(Long max) {
    this.max = max;
  }

  public FragmentSummary mean(Long mean) {
    this.mean = mean;
    return this;
  }

  /**
   * Get mean minimum: 0
   *
   * @return mean
   */
  @NotNull
  @Min(0L)
  @Schema(name = "mean", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mean")
  public Long getMean() {
    return mean;
  }

  public void setMean(Long mean) {
    this.mean = mean;
  }

  public FragmentSummary p25(Long p25) {
    this.p25 = p25;
    return this;
  }

  /**
   * Get p25 minimum: 0
   *
   * @return p25
   */
  @NotNull
  @Min(0L)
  @Schema(name = "p25", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("p25")
  public Long getP25() {
    return p25;
  }

  public void setP25(Long p25) {
    this.p25 = p25;
  }

  public FragmentSummary p50(Long p50) {
    this.p50 = p50;
    return this;
  }

  /**
   * Get p50 minimum: 0
   *
   * @return p50
   */
  @NotNull
  @Min(0L)
  @Schema(name = "p50", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("p50")
  public Long getP50() {
    return p50;
  }

  public void setP50(Long p50) {
    this.p50 = p50;
  }

  public FragmentSummary p75(Long p75) {
    this.p75 = p75;
    return this;
  }

  /**
   * Get p75 minimum: 0
   *
   * @return p75
   */
  @NotNull
  @Min(0L)
  @Schema(name = "p75", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("p75")
  public Long getP75() {
    return p75;
  }

  public void setP75(Long p75) {
    this.p75 = p75;
  }

  public FragmentSummary p99(Long p99) {
    this.p99 = p99;
    return this;
  }

  /**
   * Get p99 minimum: 0
   *
   * @return p99
   */
  @NotNull
  @Min(0L)
  @Schema(name = "p99", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("p99")
  public Long getP99() {
    return p99;
  }

  public void setP99(Long p99) {
    this.p99 = p99;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FragmentSummary fragmentSummary = (FragmentSummary) o;
    return Objects.equals(this.min, fragmentSummary.min)
        && Objects.equals(this.max, fragmentSummary.max)
        && Objects.equals(this.mean, fragmentSummary.mean)
        && Objects.equals(this.p25, fragmentSummary.p25)
        && Objects.equals(this.p50, fragmentSummary.p50)
        && Objects.equals(this.p75, fragmentSummary.p75)
        && Objects.equals(this.p99, fragmentSummary.p99);
  }

  @Override
  public int hashCode() {
    return Objects.hash(min, max, mean, p25, p50, p75, p99);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FragmentSummary {\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
    sb.append("    mean: ").append(toIndentedString(mean)).append("\n");
    sb.append("    p25: ").append(toIndentedString(p25)).append("\n");
    sb.append("    p50: ").append(toIndentedString(p50)).append("\n");
    sb.append("    p75: ").append(toIndentedString(p75)).append("\n");
    sb.append("    p99: ").append(toIndentedString(p99)).append("\n");
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
