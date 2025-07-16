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

/** FtsQueryOneOf1 */
@JsonTypeName("FtsQuery_oneOf_1")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf1 implements FtsQuery {

  private PhraseQuery phrase;

  public FtsQueryOneOf1() {
    super();
  }

  /** Constructor with only required parameters */
  public FtsQueryOneOf1(PhraseQuery phrase) {
    this.phrase = phrase;
  }

  public FtsQueryOneOf1 phrase(PhraseQuery phrase) {
    this.phrase = phrase;
    return this;
  }

  /**
   * Get phrase
   *
   * @return phrase
   */
  @NotNull
  @Valid
  @Schema(name = "phrase", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("phrase")
  public PhraseQuery getPhrase() {
    return phrase;
  }

  public void setPhrase(PhraseQuery phrase) {
    this.phrase = phrase;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FtsQueryOneOf1 ftsQueryOneOf1 = (FtsQueryOneOf1) o;
    return Objects.equals(this.phrase, ftsQueryOneOf1.phrase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phrase);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FtsQueryOneOf1 {\n");
    sb.append("    phrase: ").append(toIndentedString(phrase)).append("\n");
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
