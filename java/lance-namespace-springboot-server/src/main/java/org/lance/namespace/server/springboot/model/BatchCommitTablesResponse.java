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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Response for a batch commit of table operations. Contains the results of each operation in the
 * same order as the request.
 */
@Schema(
    name = "BatchCommitTablesResponse",
    description =
        "Response for a batch commit of table operations. Contains the results of each operation in the same order as the request. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class BatchCommitTablesResponse {

  @Valid private Map<String, String> context = new HashMap<>();

  private String transactionId;

  @Valid private List<@Valid CommitTableResult> results = new ArrayList<>();

  public BatchCommitTablesResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public BatchCommitTablesResponse(List<@Valid CommitTableResult> results) {
    this.results = results;
  }

  public BatchCommitTablesResponse context(Map<String, String> context) {
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
   * Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request,
   * any entry whose key starts with `header.` is sent as an HTTP request header with the prefix
   * stripped. For example, the entry `{\"header.Authorization\": \"Bearer abc\"}` is sent as the
   * request header `Authorization: Bearer abc`. - On a response, every HTTP response header is
   * returned as an entry whose key is the header name prefixed with `header.`. For example, the
   * response header `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\":
   * \"abc123\"}`.
   *
   * @return context
   */
  @Schema(
      name = "context",
      description =
          "Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }

  public BatchCommitTablesResponse transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier for the batch commit
   *
   * @return transactionId
   */
  @Schema(
      name = "transaction_id",
      description = "Optional transaction identifier for the batch commit",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("transaction_id")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public BatchCommitTablesResponse results(List<@Valid CommitTableResult> results) {
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
  @NotNull
  @Valid
  @Schema(
      name = "results",
      description =
          "Results for each operation, in the same order as the request operations. Each result contains the outcome of the corresponding operation. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("results")
  public List<@Valid CommitTableResult> getResults() {
    return results;
  }

  public void setResults(List<@Valid CommitTableResult> results) {
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
}
