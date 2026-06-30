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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Response for a batch commit of table operations. Contains the results of each operation in the
 * same order as the request.
 */
@JsonPropertyOrder({
  BatchCommitTablesResponse.JSON_PROPERTY_CONTEXT,
  BatchCommitTablesResponse.JSON_PROPERTY_TRANSACTION_ID,
  BatchCommitTablesResponse.JSON_PROPERTY_RESULTS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BatchCommitTablesResponse {
  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_TRANSACTION_ID = "transaction_id";
  @javax.annotation.Nullable private String transactionId;

  public static final String JSON_PROPERTY_RESULTS = "results";
  @javax.annotation.Nonnull private List<CommitTableResult> results = new ArrayList<>();

  public BatchCommitTablesResponse() {}

  public BatchCommitTablesResponse context(@javax.annotation.Nullable Map<String, String> context) {

    this.context = context;
    return this;
  }

  public BatchCommitTablesResponse putContextItem(String key, String contextItem) {
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

  public BatchCommitTablesResponse transactionId(@javax.annotation.Nullable String transactionId) {

    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier for the batch commit
   *
   * @return transactionId
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TRANSACTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getTransactionId() {
    return transactionId;
  }

  @JsonProperty(JSON_PROPERTY_TRANSACTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTransactionId(@javax.annotation.Nullable String transactionId) {
    this.transactionId = transactionId;
  }

  public BatchCommitTablesResponse results(
      @javax.annotation.Nonnull List<CommitTableResult> results) {

    this.results = results;
    return this;
  }

  public BatchCommitTablesResponse addResultsItem(CommitTableResult resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Results for each operation, in the same order as the request operations. Each result contains
   * the outcome of the corresponding operation.
   *
   * @return results
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RESULTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<CommitTableResult> getResults() {
    return results;
  }

  @JsonProperty(JSON_PROPERTY_RESULTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setResults(@javax.annotation.Nonnull List<CommitTableResult> results) {
    this.results = results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCommitTablesResponse batchCommitTablesResponse = (BatchCommitTablesResponse) o;
    return Objects.equals(this.context, batchCommitTablesResponse.context)
        && Objects.equals(this.transactionId, batchCommitTablesResponse.transactionId)
        && Objects.equals(this.results, batchCommitTablesResponse.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, transactionId, results);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCommitTablesResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
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
        try {
          joiner.add(
              String.format(
                  "%scontext%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getContext().get(_key),
                  URLEncoder.encode(String.valueOf(getContext().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `transaction_id` to the URL query string
    if (getTransactionId() != null) {
      try {
        joiner.add(
            String.format(
                "%stransaction_id%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTransactionId()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `results` to the URL query string
    if (getResults() != null) {
      for (int i = 0; i < getResults().size(); i++) {
        if (getResults().get(i) != null) {
          joiner.add(
              getResults()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%sresults%s%s",
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
