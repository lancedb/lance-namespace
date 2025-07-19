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

/** Boost query that scores documents matching positive query higher and negative query lower */
@JsonPropertyOrder({
  BoostQuery.JSON_PROPERTY_POSITIVE,
  BoostQuery.JSON_PROPERTY_NEGATIVE,
  BoostQuery.JSON_PROPERTY_NEGATIVE_BOOST
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BoostQuery {
  public static final String JSON_PROPERTY_POSITIVE = "positive";
  @javax.annotation.Nonnull private FtsQuery positive;

  public static final String JSON_PROPERTY_NEGATIVE = "negative";
  @javax.annotation.Nonnull private FtsQuery negative;

  public static final String JSON_PROPERTY_NEGATIVE_BOOST = "negative_boost";
  @javax.annotation.Nullable private Float negativeBoost = 0.5f;

  public BoostQuery() {}

  public BoostQuery positive(@javax.annotation.Nonnull FtsQuery positive) {

    this.positive = positive;
    return this;
  }

  /**
   * Get positive
   *
   * @return positive
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_POSITIVE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public FtsQuery getPositive() {
    return positive;
  }

  @JsonProperty(JSON_PROPERTY_POSITIVE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPositive(@javax.annotation.Nonnull FtsQuery positive) {
    this.positive = positive;
  }

  public BoostQuery negative(@javax.annotation.Nonnull FtsQuery negative) {

    this.negative = negative;
    return this;
  }

  /**
   * Get negative
   *
   * @return negative
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NEGATIVE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public FtsQuery getNegative() {
    return negative;
  }

  @JsonProperty(JSON_PROPERTY_NEGATIVE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNegative(@javax.annotation.Nonnull FtsQuery negative) {
    this.negative = negative;
  }

  public BoostQuery negativeBoost(@javax.annotation.Nullable Float negativeBoost) {

    this.negativeBoost = negativeBoost;
    return this;
  }

  /**
   * Boost factor for negative query (default: 0.5)
   *
   * @return negativeBoost
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NEGATIVE_BOOST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Float getNegativeBoost() {
    return negativeBoost;
  }

  @JsonProperty(JSON_PROPERTY_NEGATIVE_BOOST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNegativeBoost(@javax.annotation.Nullable Float negativeBoost) {
    this.negativeBoost = negativeBoost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BoostQuery boostQuery = (BoostQuery) o;
    return Objects.equals(this.positive, boostQuery.positive)
        && Objects.equals(this.negative, boostQuery.negative)
        && Objects.equals(this.negativeBoost, boostQuery.negativeBoost);
  }

  @Override
  public int hashCode() {
    return Objects.hash(positive, negative, negativeBoost);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BoostQuery {\n");
    sb.append("    positive: ").append(toIndentedString(positive)).append("\n");
    sb.append("    negative: ").append(toIndentedString(negative)).append("\n");
    sb.append("    negativeBoost: ").append(toIndentedString(negativeBoost)).append("\n");
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

    // add `positive` to the URL query string
    if (getPositive() != null) {
      joiner.add(getPositive().toUrlQueryString(prefix + "positive" + suffix));
    }

    // add `negative` to the URL query string
    if (getNegative() != null) {
      joiner.add(getNegative().toUrlQueryString(prefix + "negative" + suffix));
    }

    // add `negative_boost` to the URL query string
    if (getNegativeBoost() != null) {
      try {
        joiner.add(
            String.format(
                "%snegative_boost%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNegativeBoost()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
