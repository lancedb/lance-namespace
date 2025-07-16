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
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** FtsQueryOneOf */
@JsonTypeName("FtsQuery_oneOf")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf implements FtsQuery {

  private MatchQuery match;

  public FtsQueryOneOf() {
    super();
  }

  /** Constructor with only required parameters */
  public FtsQueryOneOf(MatchQuery match) {
    this.match = match;
  }

  public FtsQueryOneOf match(MatchQuery match) {
    this.match = match;
    return this;
  }

  /**
   * Get match
   *
   * @return match
   */
  @NotNull
  @Valid
  @Schema(name = "match", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("match")
  public MatchQuery getMatch() {
    return match;
  }

  public void setMatch(MatchQuery match) {
    this.match = match;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FtsQueryOneOf ftsQueryOneOf = (FtsQueryOneOf) o;
    return Objects.equals(this.match, ftsQueryOneOf.match);
  }

  @Override
  public int hashCode() {
    return Objects.hash(match);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FtsQueryOneOf {\n");
    sb.append("    match: ").append(toIndentedString(match)).append("\n");
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
