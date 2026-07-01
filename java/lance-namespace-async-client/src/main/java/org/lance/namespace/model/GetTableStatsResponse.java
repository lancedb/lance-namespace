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

import org.lance.namespace.client.async.ApiClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** GetTableStatsResponse */
@JsonPropertyOrder({
  GetTableStatsResponse.JSON_PROPERTY_CONTEXT,
  GetTableStatsResponse.JSON_PROPERTY_TOTAL_BYTES,
  GetTableStatsResponse.JSON_PROPERTY_NUM_ROWS,
  GetTableStatsResponse.JSON_PROPERTY_NUM_INDICES,
  GetTableStatsResponse.JSON_PROPERTY_FRAGMENT_STATS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class GetTableStatsResponse {
  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_TOTAL_BYTES = "total_bytes";
  @javax.annotation.Nonnull private Long totalBytes;

  public static final String JSON_PROPERTY_NUM_ROWS = "num_rows";
  @javax.annotation.Nonnull private Long numRows;

  public static final String JSON_PROPERTY_NUM_INDICES = "num_indices";
  @javax.annotation.Nonnull private Long numIndices;

  public static final String JSON_PROPERTY_FRAGMENT_STATS = "fragment_stats";
  @javax.annotation.Nonnull private FragmentStats fragmentStats;

  public GetTableStatsResponse() {}

  public GetTableStatsResponse context(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
    return this;
  }

  public GetTableStatsResponse putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context as key-value pairs. How to use the context is custom to the specific
   * implementation. On a request, it carries caller-provided context to the implementation. On a
   * response, it carries implementation-provided context back to the caller. REST NAMESPACE ONLY
   * Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On
   * a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP request
   * header with the prefix stripped. For example, the entry
   * &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the
   * request header &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response
   * header is returned as an entry whose key is the header name prefixed with &#x60;header.&#x60;.
   * For example, the response header &#x60;x-request-id: abc123&#x60; is returned as the entry
   * &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.
   *
   * @return context
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getContext() {
    return context;
  }

  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setContext(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
  }

  public GetTableStatsResponse totalBytes(@javax.annotation.Nonnull Long totalBytes) {
    this.totalBytes = totalBytes;
    return this;
  }

  /**
   * The total number of bytes in the table minimum: 0
   *
   * @return totalBytes
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TOTAL_BYTES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getTotalBytes() {
    return totalBytes;
  }

  @JsonProperty(JSON_PROPERTY_TOTAL_BYTES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTotalBytes(@javax.annotation.Nonnull Long totalBytes) {
    this.totalBytes = totalBytes;
  }

  public GetTableStatsResponse numRows(@javax.annotation.Nonnull Long numRows) {
    this.numRows = numRows;
    return this;
  }

  /**
   * The number of rows in the table minimum: 0
   *
   * @return numRows
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getNumRows() {
    return numRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_ROWS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumRows(@javax.annotation.Nonnull Long numRows) {
    this.numRows = numRows;
  }

  public GetTableStatsResponse numIndices(@javax.annotation.Nonnull Long numIndices) {
    this.numIndices = numIndices;
    return this;
  }

  /**
   * The number of indices in the table minimum: 0
   *
   * @return numIndices
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NUM_INDICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getNumIndices() {
    return numIndices;
  }

  @JsonProperty(JSON_PROPERTY_NUM_INDICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNumIndices(@javax.annotation.Nonnull Long numIndices) {
    this.numIndices = numIndices;
  }

  public GetTableStatsResponse fragmentStats(
      @javax.annotation.Nonnull FragmentStats fragmentStats) {
    this.fragmentStats = fragmentStats;
    return this;
  }

  /**
   * Statistics on table fragments
   *
   * @return fragmentStats
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_FRAGMENT_STATS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public FragmentStats getFragmentStats() {
    return fragmentStats;
  }

  @JsonProperty(JSON_PROPERTY_FRAGMENT_STATS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFragmentStats(@javax.annotation.Nonnull FragmentStats fragmentStats) {
    this.fragmentStats = fragmentStats;
  }

  /** Return true if this GetTableStatsResponse object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetTableStatsResponse getTableStatsResponse = (GetTableStatsResponse) o;
    return Objects.equals(this.context, getTableStatsResponse.context)
        && Objects.equals(this.totalBytes, getTableStatsResponse.totalBytes)
        && Objects.equals(this.numRows, getTableStatsResponse.numRows)
        && Objects.equals(this.numIndices, getTableStatsResponse.numIndices)
        && Objects.equals(this.fragmentStats, getTableStatsResponse.fragmentStats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, totalBytes, numRows, numIndices, fragmentStats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTableStatsResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    totalBytes: ").append(toIndentedString(totalBytes)).append("\n");
    sb.append("    numRows: ").append(toIndentedString(numRows)).append("\n");
    sb.append("    numIndices: ").append(toIndentedString(numIndices)).append("\n");
    sb.append("    fragmentStats: ").append(toIndentedString(fragmentStats)).append("\n");
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

    // add `context` to the URL query string
    if (getContext() != null) {
      for (String _key : getContext().keySet()) {
        joiner.add(
            String.format(
                "%scontext%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getContext().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getContext().get(_key)))));
      }
    }

    // add `total_bytes` to the URL query string
    if (getTotalBytes() != null) {
      joiner.add(
          String.format(
              "%stotal_bytes%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTotalBytes()))));
    }

    // add `num_rows` to the URL query string
    if (getNumRows() != null) {
      joiner.add(
          String.format(
              "%snum_rows%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumRows()))));
    }

    // add `num_indices` to the URL query string
    if (getNumIndices() != null) {
      joiner.add(
          String.format(
              "%snum_indices%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumIndices()))));
    }

    // add `fragment_stats` to the URL query string
    if (getFragmentStats() != null) {
      joiner.add(getFragmentStats().toUrlQueryString(prefix + "fragment_stats" + suffix));
    }

    return joiner.toString();
  }
}
