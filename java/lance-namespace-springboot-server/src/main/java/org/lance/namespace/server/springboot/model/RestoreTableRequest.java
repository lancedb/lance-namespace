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
import org.lance.namespace.server.springboot.model.Identity;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RestoreTableRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class RestoreTableRequest {

  private Identity identity;

  @Valid
  private Map<String, String> context = new HashMap<>();

  @Valid
  private List<String> id = new ArrayList<>();

  private Long version;

  public RestoreTableRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RestoreTableRequest(Long version) {
    this.version = version;
  }

  public RestoreTableRequest identity(Identity identity) {
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

  public RestoreTableRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public RestoreTableRequest putContextItem(String key, String contextItem) {
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

  public RestoreTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public RestoreTableRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public RestoreTableRequest version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Version to restore to
   * minimum: 0
   * @return version
   */
  @NotNull @Min(0L) 
  @Schema(name = "version", description = "Version to restore to", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestoreTableRequest restoreTableRequest = (RestoreTableRequest) o;
    return Objects.equals(this.identity, restoreTableRequest.identity) &&
        Objects.equals(this.context, restoreTableRequest.context) &&
        Objects.equals(this.id, restoreTableRequest.id) &&
        Objects.equals(this.version, restoreTableRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, id, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestoreTableRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

