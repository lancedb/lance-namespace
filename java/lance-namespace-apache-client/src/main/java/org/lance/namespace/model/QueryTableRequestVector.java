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
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Query vector(s) for similarity search. Provide either single_vector or multi_vector, not both.
 */
@JsonPropertyOrder({
  QueryTableRequestVector.JSON_PROPERTY_SINGLE_VECTOR,
  QueryTableRequestVector.JSON_PROPERTY_MULTI_VECTOR
})
@JsonTypeName("QueryTableRequest_vector")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableRequestVector {
  public static final String JSON_PROPERTY_SINGLE_VECTOR = "single_vector";
  @javax.annotation.Nullable private List<Float> singleVector = new ArrayList<>();

  public static final String JSON_PROPERTY_MULTI_VECTOR = "multi_vector";
  @javax.annotation.Nullable private List<List<Float>> multiVector = new ArrayList<>();

  public QueryTableRequestVector() {}

  public QueryTableRequestVector singleVector(@javax.annotation.Nullable List<Float> singleVector) {

    this.singleVector = singleVector;
    return this;
  }

  public QueryTableRequestVector addSingleVectorItem(Float singleVectorItem) {
    if (this.singleVector == null) {
      this.singleVector = new ArrayList<>();
    }
    this.singleVector.add(singleVectorItem);
    return this;
  }

  /**
   * Single query vector
   *
   * @return singleVector
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SINGLE_VECTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<Float> getSingleVector() {
    return singleVector;
  }

  @JsonProperty(JSON_PROPERTY_SINGLE_VECTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSingleVector(@javax.annotation.Nullable List<Float> singleVector) {
    this.singleVector = singleVector;
  }

  public QueryTableRequestVector multiVector(
      @javax.annotation.Nullable List<List<Float>> multiVector) {

    this.multiVector = multiVector;
    return this;
  }

  public QueryTableRequestVector addMultiVectorItem(List<Float> multiVectorItem) {
    if (this.multiVector == null) {
      this.multiVector = new ArrayList<>();
    }
    this.multiVector.add(multiVectorItem);
    return this;
  }

  /**
   * Multiple query vectors for batch search
   *
   * @return multiVector
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MULTI_VECTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<List<Float>> getMultiVector() {
    return multiVector;
  }

  @JsonProperty(JSON_PROPERTY_MULTI_VECTOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMultiVector(@javax.annotation.Nullable List<List<Float>> multiVector) {
    this.multiVector = multiVector;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryTableRequestVector queryTableRequestVector = (QueryTableRequestVector) o;
    return Objects.equals(this.singleVector, queryTableRequestVector.singleVector)
        && Objects.equals(this.multiVector, queryTableRequestVector.multiVector);
  }

  @Override
  public int hashCode() {
    return Objects.hash(singleVector, multiVector);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryTableRequestVector {\n");
    sb.append("    singleVector: ").append(toIndentedString(singleVector)).append("\n");
    sb.append("    multiVector: ").append(toIndentedString(multiVector)).append("\n");
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

    // add `single_vector` to the URL query string
    if (getSingleVector() != null) {
      for (int i = 0; i < getSingleVector().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%ssingle_vector%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getSingleVector().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `multi_vector` to the URL query string
    if (getMultiVector() != null) {
      for (int i = 0; i < getMultiVector().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%smulti_vector%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getMultiVector().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    return joiner.toString();
  }
}
