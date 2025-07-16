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

/** FtsQueryOneOf2 */
@JsonPropertyOrder({FtsQueryOneOf2.JSON_PROPERTY_BOOST})
@JsonTypeName("FtsQuery_oneOf_2")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class FtsQueryOneOf2 {
  public static final String JSON_PROPERTY_BOOST = "boost";
  @javax.annotation.Nonnull private BoostQuery boost;

  public FtsQueryOneOf2() {}

  public FtsQueryOneOf2 boost(@javax.annotation.Nonnull BoostQuery boost) {

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

    // add `boost` to the URL query string
    if (getBoost() != null) {
      joiner.add(getBoost().toUrlQueryString(prefix + "boost" + suffix));
    }

    return joiner.toString();
  }
}
