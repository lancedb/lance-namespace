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
 * Request to atomically commit a batch of table operations. This replaces
 * &#x60;BatchCreateTableVersionsRequest&#x60; with a more general interface that supports mixed
 * operations (DeclareTable, CreateTableVersion, DeleteTableVersions, DeregisterTable) within a
 * single atomic transaction at the metadata layer. All operations are committed atomically: either
 * all succeed or none are applied.
 */
@Schema(
    name = "BatchCommitTablesRequest",
    description =
        "Request to atomically commit a batch of table operations. This replaces `BatchCreateTableVersionsRequest` with a more general interface that supports mixed operations (DeclareTable, CreateTableVersion, DeleteTableVersions, DeregisterTable) within a single atomic transaction at the metadata layer.  All operations are committed atomically: either all succeed or none are applied. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class BatchCommitTablesRequest {

  private Identity identity;

  @Valid private Map<String, String> context = new HashMap<>();

  @Valid private List<@Valid CommitTableOperation> operations = new ArrayList<>();

  public BatchCommitTablesRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public BatchCommitTablesRequest(List<@Valid CommitTableOperation> operations) {
    this.operations = operations;
  }

  public BatchCommitTablesRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @Valid
  @Schema(name = "identity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identity")
  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public BatchCommitTablesRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public BatchCommitTablesRequest putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context for a request as key-value pairs. How to use the context is custom to the
   * specific implementation. REST NAMESPACE ONLY Context entries are passed via HTTP headers using
   * the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry
   * `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.
   *
   * @return context
   */
  @Schema(
      name = "context",
      description =
          "Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }

  public BatchCommitTablesRequest operations(List<@Valid CommitTableOperation> operations) {
    this.operations = operations;
    return this;
  }

  public BatchCommitTablesRequest addOperationsItem(CommitTableOperation operationsItem) {
    if (this.operations == null) {
      this.operations = new ArrayList<>();
    }
    this.operations.add(operationsItem);
    return this;
  }

  /**
   * List of operations to commit atomically. Supported operation types: DeclareTable,
   * CreateTableVersion, DeleteTableVersions, DeregisterTable.
   *
   * @return operations
   */
  @NotNull
  @Valid
  @Schema(
      name = "operations",
      description =
          "List of operations to commit atomically. Supported operation types: DeclareTable, CreateTableVersion, DeleteTableVersions, DeregisterTable. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("operations")
  public List<@Valid CommitTableOperation> getOperations() {
    return operations;
  }

  public void setOperations(List<@Valid CommitTableOperation> operations) {
    this.operations = operations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCommitTablesRequest batchCommitTablesRequest = (BatchCommitTablesRequest) o;
    return Objects.equals(this.identity, batchCommitTablesRequest.identity)
        && Objects.equals(this.context, batchCommitTablesRequest.context)
        && Objects.equals(this.operations, batchCommitTablesRequest.operations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, operations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCommitTablesRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    operations: ").append(toIndentedString(operations)).append("\n");
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
