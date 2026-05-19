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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Response for a batch commit of table operations. Contains the results of each operation in the
 * same order as the request.
 */
@JsonPropertyOrder({
  BatchCommitTablesResponse.JSON_PROPERTY_TRANSACTION_ID,
  BatchCommitTablesResponse.JSON_PROPERTY_RESULTS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BatchCommitTablesResponse {
  public static final String JSON_PROPERTY_TRANSACTION_ID = "transaction_id";
  @javax.annotation.Nullable private String transactionId;

  public static final String JSON_PROPERTY_RESULTS = "results";
  @javax.annotation.Nonnull private List<CommitTableResult> results = new ArrayList<>();

  public BatchCommitTablesResponse() {}

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

  /** Return true if this BatchCommitTablesResponse object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCommitTablesResponse batchCommitTablesResponse = (BatchCommitTablesResponse) o;
    return Objects.equals(this.transactionId, batchCommitTablesResponse.transactionId)
        && Objects.equals(this.results, batchCommitTablesResponse.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, results);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCommitTablesResponse {\n");
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

    // add `transaction_id` to the URL query string
    if (getTransactionId() != null) {
      joiner.add(
          String.format(
              "%stransaction_id%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTransactionId()))));
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
