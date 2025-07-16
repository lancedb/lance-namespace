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
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;
import java.util.StringJoiner;

/** FtsQueryOneOf */
@JsonPropertyOrder({FtsQueryOneOf.JSON_PROPERTY_MATCH})
@JsonTypeName("FtsQuery_oneOf")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf {
  public static final String JSON_PROPERTY_MATCH = "match";
  @javax.annotation.Nonnull private MatchQuery match;

  public FtsQueryOneOf() {}

  public FtsQueryOneOf match(@javax.annotation.Nonnull MatchQuery match) {

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

    return joiner.toString();
  }
}
