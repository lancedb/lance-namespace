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

/** FtsQueryOneOf3 */
@JsonTypeName("FtsQuery_oneOf_3")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf3 implements FtsQuery {

  private MultiMatchQuery multiMatch;

  public FtsQueryOneOf3() {
    super();
  }

  /** Constructor with only required parameters */
  public FtsQueryOneOf3(MultiMatchQuery multiMatch) {
    this.multiMatch = multiMatch;
  }

  public FtsQueryOneOf3 multiMatch(MultiMatchQuery multiMatch) {
    this.multiMatch = multiMatch;
    return this;
  }

  /**
   * Get multiMatch
   *
   * @return multiMatch
   */
  @NotNull
  @Valid
  @Schema(name = "multi_match", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("multi_match")
  public MultiMatchQuery getMultiMatch() {
    return multiMatch;
  }

  public void setMultiMatch(MultiMatchQuery multiMatch) {
    this.multiMatch = multiMatch;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FtsQueryOneOf3 ftsQueryOneOf3 = (FtsQueryOneOf3) o;
    return Objects.equals(this.multiMatch, ftsQueryOneOf3.multiMatch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(multiMatch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FtsQueryOneOf3 {\n");
    sb.append("    multiMatch: ").append(toIndentedString(multiMatch)).append("\n");
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
