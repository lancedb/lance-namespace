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
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Boolean query with must, should, and must_not clauses */
@Schema(
    name = "BooleanQuery",
    description = "Boolean query with must, should, and must_not clauses")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class BooleanQuery {

  @Valid private List<@Valid FtsQuery> must = new ArrayList<>();

  @Valid private List<@Valid FtsQuery> mustNot = new ArrayList<>();

  @Valid private List<@Valid FtsQuery> should = new ArrayList<>();

  public BooleanQuery() {
    super();
  }

  /** Constructor with only required parameters */
  public BooleanQuery(
      List<@Valid FtsQuery> must, List<@Valid FtsQuery> mustNot, List<@Valid FtsQuery> should) {
    this.must = must;
    this.mustNot = mustNot;
    this.should = should;
  }

  public BooleanQuery must(List<@Valid FtsQuery> must) {
    this.must = must;
    return this;
  }

  public BooleanQuery addMustItem(FtsQuery mustItem) {
    if (this.must == null) {
      this.must = new ArrayList<>();
    }
    this.must.add(mustItem);
    return this;
  }

  /**
   * Queries that must match (AND)
   *
   * @return must
   */
  @NotNull
  @Valid
  @Schema(
      name = "must",
      description = "Queries that must match (AND)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("must")
  public List<@Valid FtsQuery> getMust() {
    return must;
  }

  public void setMust(List<@Valid FtsQuery> must) {
    this.must = must;
  }

  public BooleanQuery mustNot(List<@Valid FtsQuery> mustNot) {
    this.mustNot = mustNot;
    return this;
  }

  public BooleanQuery addMustNotItem(FtsQuery mustNotItem) {
    if (this.mustNot == null) {
      this.mustNot = new ArrayList<>();
    }
    this.mustNot.add(mustNotItem);
    return this;
  }

  /**
   * Queries that must not match (NOT)
   *
   * @return mustNot
   */
  @NotNull
  @Valid
  @Schema(
      name = "must_not",
      description = "Queries that must not match (NOT)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("must_not")
  public List<@Valid FtsQuery> getMustNot() {
    return mustNot;
  }

  public void setMustNot(List<@Valid FtsQuery> mustNot) {
    this.mustNot = mustNot;
  }

  public BooleanQuery should(List<@Valid FtsQuery> should) {
    this.should = should;
    return this;
  }

  public BooleanQuery addShouldItem(FtsQuery shouldItem) {
    if (this.should == null) {
      this.should = new ArrayList<>();
    }
    this.should.add(shouldItem);
    return this;
  }

  /**
   * Queries that should match (OR)
   *
   * @return should
   */
  @NotNull
  @Valid
  @Schema(
      name = "should",
      description = "Queries that should match (OR)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("should")
  public List<@Valid FtsQuery> getShould() {
    return should;
  }

  public void setShould(List<@Valid FtsQuery> should) {
    this.should = should;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BooleanQuery booleanQuery = (BooleanQuery) o;
    return Objects.equals(this.must, booleanQuery.must)
        && Objects.equals(this.mustNot, booleanQuery.mustNot)
        && Objects.equals(this.should, booleanQuery.should);
  }

  @Override
  public int hashCode() {
    return Objects.hash(must, mustNot, should);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BooleanQuery {\n");
    sb.append("    must: ").append(toIndentedString(must)).append("\n");
    sb.append("    mustNot: ").append(toIndentedString(mustNot)).append("\n");
    sb.append("    should: ").append(toIndentedString(should)).append("\n");
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
