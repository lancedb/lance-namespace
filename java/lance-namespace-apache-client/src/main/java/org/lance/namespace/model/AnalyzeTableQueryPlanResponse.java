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
import java.util.Objects;
import java.util.StringJoiner;

/** AnalyzeTableQueryPlanResponse */
@JsonPropertyOrder({AnalyzeTableQueryPlanResponse.JSON_PROPERTY_ANALYSIS})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AnalyzeTableQueryPlanResponse {
  public static final String JSON_PROPERTY_ANALYSIS = "analysis";
  @javax.annotation.Nonnull private String analysis;

  public AnalyzeTableQueryPlanResponse() {}

  public AnalyzeTableQueryPlanResponse analysis(@javax.annotation.Nonnull String analysis) {

    this.analysis = analysis;
    return this;
  }

  /**
   * Detailed analysis of the query execution plan
   *
   * @return analysis
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ANALYSIS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getAnalysis() {
    return analysis;
  }

  @JsonProperty(JSON_PROPERTY_ANALYSIS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAnalysis(@javax.annotation.Nonnull String analysis) {
    this.analysis = analysis;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnalyzeTableQueryPlanResponse analyzeTableQueryPlanResponse = (AnalyzeTableQueryPlanResponse) o;
    return Objects.equals(this.analysis, analyzeTableQueryPlanResponse.analysis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(analysis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnalyzeTableQueryPlanResponse {\n");
    sb.append("    analysis: ").append(toIndentedString(analysis)).append("\n");
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

    // add `analysis` to the URL query string
    if (getAnalysis() != null) {
      try {
        joiner.add(
            String.format(
                "%sanalysis%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getAnalysis()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
