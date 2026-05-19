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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** Boolean query with must, should, and must_not clauses */
@JsonPropertyOrder({
  BooleanQuery.JSON_PROPERTY_MUST,
  BooleanQuery.JSON_PROPERTY_MUST_NOT,
  BooleanQuery.JSON_PROPERTY_SHOULD
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BooleanQuery {
  public static final String JSON_PROPERTY_MUST = "must";
  @javax.annotation.Nonnull private List<FtsQuery> must = new ArrayList<>();

  public static final String JSON_PROPERTY_MUST_NOT = "must_not";
  @javax.annotation.Nonnull private List<FtsQuery> mustNot = new ArrayList<>();

  public static final String JSON_PROPERTY_SHOULD = "should";
  @javax.annotation.Nonnull private List<FtsQuery> should = new ArrayList<>();

  public BooleanQuery() {}

  public BooleanQuery must(@javax.annotation.Nonnull List<FtsQuery> must) {

    this.must = must;
    return this;
  }

  public BooleanQuery addMustItem(FtsQuery mustItem) {
    if (this.must == null) {
      this.must = new ArrayList<>();
    }
    this.must.add(mustItem);
    return this;
  }

  /**
   * Queries that must match (AND)
   *
   * @return must
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MUST)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<FtsQuery> getMust() {
    return must;
  }

  @JsonProperty(JSON_PROPERTY_MUST)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMust(@javax.annotation.Nonnull List<FtsQuery> must) {
    this.must = must;
  }

  public BooleanQuery mustNot(@javax.annotation.Nonnull List<FtsQuery> mustNot) {

    this.mustNot = mustNot;
    return this;
  }

  public BooleanQuery addMustNotItem(FtsQuery mustNotItem) {
    if (this.mustNot == null) {
      this.mustNot = new ArrayList<>();
    }
    this.mustNot.add(mustNotItem);
    return this;
  }

  /**
   * Queries that must not match (NOT)
   *
   * @return mustNot
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MUST_NOT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<FtsQuery> getMustNot() {
    return mustNot;
  }

  @JsonProperty(JSON_PROPERTY_MUST_NOT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMustNot(@javax.annotation.Nonnull List<FtsQuery> mustNot) {
    this.mustNot = mustNot;
  }

  public BooleanQuery should(@javax.annotation.Nonnull List<FtsQuery> should) {

    this.should = should;
    return this;
  }

  public BooleanQuery addShouldItem(FtsQuery shouldItem) {
    if (this.should == null) {
      this.should = new ArrayList<>();
    }
    this.should.add(shouldItem);
    return this;
  }

  /**
   * Queries that should match (OR)
   *
   * @return should
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SHOULD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<FtsQuery> getShould() {
    return should;
  }

  @JsonProperty(JSON_PROPERTY_SHOULD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setShould(@javax.annotation.Nonnull List<FtsQuery> should) {
    this.should = should;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BooleanQuery booleanQuery = (BooleanQuery) o;
    return Objects.equals(this.must, booleanQuery.must)
        && Objects.equals(this.mustNot, booleanQuery.mustNot)
        && Objects.equals(this.should, booleanQuery.should);
  }

  @Override
  public int hashCode() {
    return Objects.hash(must, mustNot, should);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BooleanQuery {\n");
    sb.append("    must: ").append(toIndentedString(must)).append("\n");
    sb.append("    mustNot: ").append(toIndentedString(mustNot)).append("\n");
    sb.append("    should: ").append(toIndentedString(should)).append("\n");
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

    // add `must` to the URL query string
    if (getMust() != null) {
      for (int i = 0; i < getMust().size(); i++) {
        if (getMust().get(i) != null) {
          joiner.add(
              getMust()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%smust%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    // add `must_not` to the URL query string
    if (getMustNot() != null) {
      for (int i = 0; i < getMustNot().size(); i++) {
        if (getMustNot().get(i) != null) {
          joiner.add(
              getMustNot()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%smust_not%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    // add `should` to the URL query string
    if (getShould() != null) {
      for (int i = 0; i < getShould().size(); i++) {
        if (getShould().get(i) != null) {
          joiner.add(
              getShould()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%sshould%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}
