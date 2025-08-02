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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** AnalyzeTableQueryPlanRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AnalyzeTableQueryPlanRequest {

  @Valid private List<String> id = new ArrayList<>();

  private QueryTableRequest query;

  public AnalyzeTableQueryPlanRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public AnalyzeTableQueryPlanRequest(QueryTableRequest query) {
    this.query = query;
  }

  public AnalyzeTableQueryPlanRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AnalyzeTableQueryPlanRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public AnalyzeTableQueryPlanRequest query(QueryTableRequest query) {
    this.query = query;
    return this;
  }

  /**
   * Get query
   *
   * @return query
   */
  @NotNull
  @Valid
  @Schema(name = "query", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("query")
  public QueryTableRequest getQuery() {
    return query;
  }

  public void setQuery(QueryTableRequest query) {
    this.query = query;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest = (AnalyzeTableQueryPlanRequest) o;
    return Objects.equals(this.id, analyzeTableQueryPlanRequest.id)
        && Objects.equals(this.query, analyzeTableQueryPlanRequest.query);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, query);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnalyzeTableQueryPlanRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
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
