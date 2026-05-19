package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lance.namespace.server.springboot.model.CreateTableVersionEntry;
import org.lance.namespace.server.springboot.model.Identity;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Request to atomically create new version entries for multiple tables. The operation is atomic: all versions are created or none are. 
 */

@Schema(name = "BatchCreateTableVersionsRequest", description = "Request to atomically create new version entries for multiple tables. The operation is atomic: all versions are created or none are. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class BatchCreateTableVersionsRequest {

  private Identity identity;

  @Valid
  private Map<String, String> context = new HashMap<>();

  @Valid
  private List<@Valid CreateTableVersionEntry> entries = new ArrayList<>();

  public BatchCreateTableVersionsRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BatchCreateTableVersionsRequest(List<@Valid CreateTableVersionEntry> entries) {
    this.entries = entries;
  }

  public BatchCreateTableVersionsRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
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

  public BatchCreateTableVersionsRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public BatchCreateTableVersionsRequest putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`. 
   * @return context
   */
  
  @Schema(name = "context", description = "Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }

  public BatchCreateTableVersionsRequest entries(List<@Valid CreateTableVersionEntry> entries) {
    this.entries = entries;
    return this;
  }

  public BatchCreateTableVersionsRequest addEntriesItem(CreateTableVersionEntry entriesItem) {
    if (this.entries == null) {
      this.entries = new ArrayList<>();
    }
    this.entries.add(entriesItem);
    return this;
  }

  /**
   * List of table version entries to create atomically
   * @return entries
   */
  @NotNull @Valid 
  @Schema(name = "entries", description = "List of table version entries to create atomically", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("entries")
  public List<@Valid CreateTableVersionEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<@Valid CreateTableVersionEntry> entries) {
    this.entries = entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCreateTableVersionsRequest batchCreateTableVersionsRequest = (BatchCreateTableVersionsRequest) o;
    return Objects.equals(this.identity, batchCreateTableVersionsRequest.identity) &&
        Objects.equals(this.context, batchCreateTableVersionsRequest.context) &&
        Objects.equals(this.entries, batchCreateTableVersionsRequest.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, entries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCreateTableVersionsRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    entries: ").append(toIndentedString(entries)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

