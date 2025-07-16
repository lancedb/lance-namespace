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

/** FtsQueryOneOf2 */
@JsonTypeName("FtsQuery_oneOf_2")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf2 implements FtsQuery {

  private BoostQuery boost;

  public FtsQueryOneOf2() {
    super();
  }

  /** Constructor with only required parameters */
  public FtsQueryOneOf2(BoostQuery boost) {
    this.boost = boost;
  }

  public FtsQueryOneOf2 boost(BoostQuery boost) {
    this.boost = boost;
    return this;
  }

  /**
   * Get boost
   *
   * @return boost
   */
  @NotNull
  @Valid
  @Schema(name = "boost", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("boost")
  public BoostQuery getBoost() {
    return boost;
  }

  public void setBoost(BoostQuery boost) {
    this.boost = boost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FtsQueryOneOf2 ftsQueryOneOf2 = (FtsQueryOneOf2) o;
    return Objects.equals(this.boost, ftsQueryOneOf2.boost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FtsQueryOneOf2 {\n");
    sb.append("    boost: ").append(toIndentedString(boost)).append("\n");
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
