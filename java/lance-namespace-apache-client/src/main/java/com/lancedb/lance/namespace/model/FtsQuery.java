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
package com.lancedb.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.StringJoiner;

/** FtsQuery */
@JsonPropertyOrder({
  FtsQuery.JSON_PROPERTY_MATCH,
  FtsQuery.JSON_PROPERTY_PHRASE,
  FtsQuery.JSON_PROPERTY_BOOST,
  FtsQuery.JSON_PROPERTY_MULTI_MATCH,
  FtsQuery.JSON_PROPERTY_BOOLEAN
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQuery {
  public static final String JSON_PROPERTY_MATCH = "match";
  @javax.annotation.Nonnull private MatchQuery match;

  public static final String JSON_PROPERTY_PHRASE = "phrase";
  @javax.annotation.Nonnull private PhraseQuery phrase;

  public static final String JSON_PROPERTY_BOOST = "boost";
  @javax.annotation.Nonnull private BoostQuery boost;

  public static final String JSON_PROPERTY_MULTI_MATCH = "multi_match";
  @javax.annotation.Nonnull private MultiMatchQuery multiMatch;

  public static final String JSON_PROPERTY_BOOLEAN = "boolean";
  @javax.annotation.Nonnull private BooleanQuery _boolean;

  public FtsQuery() {}

  public FtsQuery match(@javax.annotation.Nonnull MatchQuery match) {

    this.match = match;
    return this;
  }

  /**
   * Get match
   *
   * @return match
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MATCH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public MatchQuery getMatch() {
    return match;
  }

  @JsonProperty(JSON_PROPERTY_MATCH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMatch(@javax.annotation.Nonnull MatchQuery match) {
    this.match = match;
  }

  public FtsQuery phrase(@javax.annotation.Nonnull PhraseQuery phrase) {

    this.phrase = phrase;
    return this;
  }

  /**
   * Get phrase
   *
   * @return phrase
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PHRASE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public PhraseQuery getPhrase() {
    return phrase;
  }

  @JsonProperty(JSON_PROPERTY_PHRASE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPhrase(@javax.annotation.Nonnull PhraseQuery phrase) {
    this.phrase = phrase;
  }

  public FtsQuery boost(@javax.annotation.Nonnull BoostQuery boost) {

    this.boost = boost;
    return this;
  }

  /**
   * Get boost
   *
   * @return boost
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_BOOST)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public BoostQuery getBoost() {
    return boost;
  }

  @JsonProperty(JSON_PROPERTY_BOOST)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setBoost(@javax.annotation.Nonnull BoostQuery boost) {
    this.boost = boost;
  }

  public FtsQuery multiMatch(@javax.annotation.Nonnull MultiMatchQuery multiMatch) {

    this.multiMatch = multiMatch;
    return this;
  }

  /**
   * Get multiMatch
   *
   * @return multiMatch
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MULTI_MATCH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public MultiMatchQuery getMultiMatch() {
    return multiMatch;
  }

  @JsonProperty(JSON_PROPERTY_MULTI_MATCH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMultiMatch(@javax.annotation.Nonnull MultiMatchQuery multiMatch) {
    this.multiMatch = multiMatch;
  }

  public FtsQuery _boolean(@javax.annotation.Nonnull BooleanQuery _boolean) {

    this._boolean = _boolean;
    return this;
  }

  /**
   * Get _boolean
   *
   * @return _boolean
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_BOOLEAN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public BooleanQuery getBoolean() {
    return _boolean;
  }

  @JsonProperty(JSON_PROPERTY_BOOLEAN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setBoolean(@javax.annotation.Nonnull BooleanQuery _boolean) {
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `match` to the URL query string
    if (getMatch() != null) {
      joiner.add(getMatch().toUrlQueryString(prefix + "match" + suffix));
    }

    // add `phrase` to the URL query string
    if (getPhrase() != null) {
      joiner.add(getPhrase().toUrlQueryString(prefix + "phrase" + suffix));
    }

    // add `boost` to the URL query string
    if (getBoost() != null) {
      joiner.add(getBoost().toUrlQueryString(prefix + "boost" + suffix));
    }

    // add `multi_match` to the URL query string
    if (getMultiMatch() != null) {
      joiner.add(getMultiMatch().toUrlQueryString(prefix + "multi_match" + suffix));
    }

    // add `boolean` to the URL query string
    if (getBoolean() != null) {
      joiner.add(getBoolean().toUrlQueryString(prefix + "boolean" + suffix));
    }

    return joiner.toString();
  }
}
