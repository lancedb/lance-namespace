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

/** PhraseQuery */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class PhraseQuery {

  private String column = null;

  private Integer slop;

  private String terms;

  public PhraseQuery() {
    super();
  }

  /** Constructor with only required parameters */
  public PhraseQuery(String terms) {
    this.terms = terms;
  }

  public PhraseQuery column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Get column
   *
   * @return column
   */
  @Schema(name = "column", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public PhraseQuery slop(Integer slop) {
    this.slop = slop;
    return this;
  }

  /**
   * Get slop minimum: 0
   *
   * @return slop
   */
  @Min(0)
  @Schema(name = "slop", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("slop")
  public Integer getSlop() {
    return slop;
  }

  public void setSlop(Integer slop) {
    this.slop = slop;
  }

  public PhraseQuery terms(String terms) {
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
    PhraseQuery phraseQuery = (PhraseQuery) o;
    return Objects.equals(this.column, phraseQuery.column)
        && Objects.equals(this.slop, phraseQuery.slop)
        && Objects.equals(this.terms, phraseQuery.terms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, slop, terms);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PhraseQuery {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    slop: ").append(toIndentedString(slop)).append("\n");
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
