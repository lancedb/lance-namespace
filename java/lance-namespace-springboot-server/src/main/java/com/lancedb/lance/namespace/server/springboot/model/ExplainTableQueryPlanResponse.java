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

/** ExplainTableQueryPlanResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ExplainTableQueryPlanResponse {

  private String plan;

  public ExplainTableQueryPlanResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public ExplainTableQueryPlanResponse(String plan) {
    this.plan = plan;
  }

  public ExplainTableQueryPlanResponse plan(String plan) {
    this.plan = plan;
    return this;
  }

  /**
   * Human-readable query execution plan
   *
   * @return plan
   */
  @NotNull
  @Schema(
      name = "plan",
      description = "Human-readable query execution plan",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("plan")
  public String getPlan() {
    return plan;
  }

  public void setPlan(String plan) {
    this.plan = plan;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExplainTableQueryPlanResponse explainTableQueryPlanResponse = (ExplainTableQueryPlanResponse) o;
    return Objects.equals(this.plan, explainTableQueryPlanResponse.plan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExplainTableQueryPlanResponse {\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
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
