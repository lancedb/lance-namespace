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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/**
 * Full-text search query. Exactly one query type field must be provided. This structure follows the
 * same pattern as AlterTransactionAction to minimize differences and compatibility issues across
 * codegen in different languages.
 */
@Schema(
    name = "FtsQuery",
    description =
        "Full-text search query. Exactly one query type field must be provided. This structure follows the same pattern as AlterTransactionAction to minimize differences and compatibility issues across codegen in different languages. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQuery {

  private MatchQuery match;

  private PhraseQuery phrase;

  private BoostQuery boost;

  private MultiMatchQuery multiMatch;

  private BooleanQuery _boolean;

  public FtsQuery match(MatchQuery match) {
    this.match = match;
    return this;
  }

  /**
   * Get match
   *
   * @return match
   */
  @Valid
  @Schema(name = "match", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("match")
  public MatchQuery getMatch() {
    return match;
  }

  public void setMatch(MatchQuery match) {
    this.match = match;
  }

  public FtsQuery phrase(PhraseQuery phrase) {
    this.phrase = phrase;
    return this;
  }

  /**
   * Get phrase
   *
   * @return phrase
   */
  @Valid
  @Schema(name = "phrase", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phrase")
  public PhraseQuery getPhrase() {
    return phrase;
  }

  public void setPhrase(PhraseQuery phrase) {
    this.phrase = phrase;
  }

  public FtsQuery boost(BoostQuery boost) {
    this.boost = boost;
    return this;
  }

  /**
   * Get boost
   *
   * @return boost
   */
  @Valid
  @Schema(name = "boost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("boost")
  public BoostQuery getBoost() {
    return boost;
  }

  public void setBoost(BoostQuery boost) {
    this.boost = boost;
  }

  public FtsQuery multiMatch(MultiMatchQuery multiMatch) {
    this.multiMatch = multiMatch;
    return this;
  }

  /**
   * Get multiMatch
   *
   * @return multiMatch
   */
  @Valid
  @Schema(name = "multi_match", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("multi_match")
  public MultiMatchQuery getMultiMatch() {
    return multiMatch;
  }

  public void setMultiMatch(MultiMatchQuery multiMatch) {
    this.multiMatch = multiMatch;
  }

  public FtsQuery _boolean(BooleanQuery _boolean) {
    this._boolean = _boolean;
    return this;
  }

  /**
   * Get _boolean
   *
   * @return _boolean
   */
  @Valid
  @Schema(name = "boolean", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("boolean")
  public BooleanQuery getBoolean() {
    return _boolean;
  }

  public void setBoolean(BooleanQuery _boolean) {
    this._boolean = _boolean;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FtsQuery ftsQuery = (FtsQuery) o;
    return Objects.equals(this.match, ftsQuery.match)
        && Objects.equals(this.phrase, ftsQuery.phrase)
        && Objects.equals(this.boost, ftsQuery.boost)
        && Objects.equals(this.multiMatch, ftsQuery.multiMatch)
        && Objects.equals(this._boolean, ftsQuery._boolean);
  }

  @Override
  public int hashCode() {
    return Objects.hash(match, phrase, boost, multiMatch, _boolean);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FtsQuery {\n");
    sb.append("    match: ").append(toIndentedString(match)).append("\n");
    sb.append("    phrase: ").append(toIndentedString(phrase)).append("\n");
    sb.append("    boost: ").append(toIndentedString(boost)).append("\n");
    sb.append("    multiMatch: ").append(toIndentedString(multiMatch)).append("\n");
    sb.append("    _boolean: ").append(toIndentedString(_boolean)).append("\n");
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
