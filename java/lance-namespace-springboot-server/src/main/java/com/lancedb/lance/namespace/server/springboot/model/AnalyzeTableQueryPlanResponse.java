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
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** AnalyzeTableQueryPlanResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AnalyzeTableQueryPlanResponse {

  private String analysis;

  public AnalyzeTableQueryPlanResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public AnalyzeTableQueryPlanResponse(String analysis) {
    this.analysis = analysis;
  }

  public AnalyzeTableQueryPlanResponse analysis(String analysis) {
    this.analysis = analysis;
    return this;
  }

  /**
   * Detailed analysis of the query execution plan
   *
   * @return analysis
   */
  @NotNull
  @Schema(
      name = "analysis",
      description = "Detailed analysis of the query execution plan",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("analysis")
  public String getAnalysis() {
    return analysis;
  }

  public void setAnalysis(String analysis) {
    this.analysis = analysis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnalyzeTableQueryPlanResponse analyzeTableQueryPlanResponse = (AnalyzeTableQueryPlanResponse) o;
    return Objects.equals(this.analysis, analyzeTableQueryPlanResponse.analysis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(analysis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnalyzeTableQueryPlanResponse {\n");
    sb.append("    analysis: ").append(toIndentedString(analysis)).append("\n");
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
