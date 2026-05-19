package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AnalyzeTableQueryPlanResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AnalyzeTableQueryPlanResponse {

  private String analysis;

  public AnalyzeTableQueryPlanResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AnalyzeTableQueryPlanResponse(String analysis) {
    this.analysis = analysis;
  }

  public AnalyzeTableQueryPlanResponse analysis(String analysis) {
    this.analysis = analysis;
    return this;
  }

  /**
   * Detailed analysis of the query execution plan
   * @return analysis
   */
  @NotNull 
  @Schema(name = "analysis", description = "Detailed analysis of the query execution plan", requiredMode = Schema.RequiredMode.REQUIRED)
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

