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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** ExplainTableQueryPlanRequest */
@JsonPropertyOrder({
  ExplainTableQueryPlanRequest.JSON_PROPERTY_IDENTITY,
  ExplainTableQueryPlanRequest.JSON_PROPERTY_ID,
  ExplainTableQueryPlanRequest.JSON_PROPERTY_QUERY,
  ExplainTableQueryPlanRequest.JSON_PROPERTY_VERBOSE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class ExplainTableQueryPlanRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_QUERY = "query";
  @javax.annotation.Nonnull private QueryTableRequest query;

  public static final String JSON_PROPERTY_VERBOSE = "verbose";
  @javax.annotation.Nullable private Boolean verbose = false;

  public ExplainTableQueryPlanRequest() {}

  public ExplainTableQueryPlanRequest identity(@javax.annotation.Nullable Identity identity) {

    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public ExplainTableQueryPlanRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public ExplainTableQueryPlanRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public ExplainTableQueryPlanRequest query(@javax.annotation.Nonnull QueryTableRequest query) {

    this.query = query;
    return this;
  }

  /**
   * Get query
   *
   * @return query
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_QUERY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public QueryTableRequest getQuery() {
    return query;
  }

  @JsonProperty(JSON_PROPERTY_QUERY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setQuery(@javax.annotation.Nonnull QueryTableRequest query) {
    this.query = query;
  }

  public ExplainTableQueryPlanRequest verbose(@javax.annotation.Nullable Boolean verbose) {

    this.verbose = verbose;
    return this;
  }

  /**
   * Whether to return verbose explanation
   *
   * @return verbose
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VERBOSE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getVerbose() {
    return verbose;
  }

  @JsonProperty(JSON_PROPERTY_VERBOSE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVerbose(@javax.annotation.Nullable Boolean verbose) {
    this.verbose = verbose;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExplainTableQueryPlanRequest explainTableQueryPlanRequest = (ExplainTableQueryPlanRequest) o;
    return Objects.equals(this.identity, explainTableQueryPlanRequest.identity)
        && Objects.equals(this.id, explainTableQueryPlanRequest.id)
        && Objects.equals(this.query, explainTableQueryPlanRequest.query)
        && Objects.equals(this.verbose, explainTableQueryPlanRequest.verbose);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, id, query, verbose);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExplainTableQueryPlanRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
    sb.append("    verbose: ").append(toIndentedString(verbose)).append("\n");
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `query` to the URL query string
    if (getQuery() != null) {
      joiner.add(getQuery().toUrlQueryString(prefix + "query" + suffix));
    }

    // add `verbose` to the URL query string
    if (getVerbose() != null) {
      try {
        joiner.add(
            String.format(
                "%sverbose%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVerbose()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
