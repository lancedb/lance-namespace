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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.StringJoiner;

/** PhraseQuery */
@JsonPropertyOrder({
  PhraseQuery.JSON_PROPERTY_COLUMN,
  PhraseQuery.JSON_PROPERTY_SLOP,
  PhraseQuery.JSON_PROPERTY_TERMS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class PhraseQuery {
  public static final String JSON_PROPERTY_COLUMN = "column";
  @javax.annotation.Nullable private String column;

  public static final String JSON_PROPERTY_SLOP = "slop";
  @javax.annotation.Nullable private Integer slop;

  public static final String JSON_PROPERTY_TERMS = "terms";
  @javax.annotation.Nonnull private String terms;

  public PhraseQuery() {}

  public PhraseQuery column(@javax.annotation.Nullable String column) {

    this.column = column;
    return this;
  }

  /**
   * Get column
   *
   * @return column
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getColumn() {
    return column;
  }

  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setColumn(@javax.annotation.Nullable String column) {
    this.column = column;
  }

  public PhraseQuery slop(@javax.annotation.Nullable Integer slop) {

    this.slop = slop;
    return this;
  }

  /**
   * Get slop minimum: 0
   *
   * @return slop
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SLOP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getSlop() {
    return slop;
  }

  @JsonProperty(JSON_PROPERTY_SLOP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSlop(@javax.annotation.Nullable Integer slop) {
    this.slop = slop;
  }

  public PhraseQuery terms(@javax.annotation.Nonnull String terms) {

    this.terms = terms;
    return this;
  }

  /**
   * Get terms
   *
   * @return terms
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TERMS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getTerms() {
    return terms;
  }

  @JsonProperty(JSON_PROPERTY_TERMS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTerms(@javax.annotation.Nonnull String terms) {
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

    // add `column` to the URL query string
    if (getColumn() != null) {
      try {
        joiner.add(
            String.format(
                "%scolumn%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getColumn()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `slop` to the URL query string
    if (getSlop() != null) {
      try {
        joiner.add(
            String.format(
                "%sslop%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getSlop()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `terms` to the URL query string
    if (getTerms() != null) {
      try {
        joiner.add(
            String.format(
                "%sterms%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTerms()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
