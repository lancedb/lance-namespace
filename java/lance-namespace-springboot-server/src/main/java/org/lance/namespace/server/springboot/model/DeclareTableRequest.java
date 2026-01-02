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

/** Request for declaring a table. */
@Schema(name = "DeclareTableRequest", description = "Request for declaring a table. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DeclareTableRequest {

  private Identity identity;

  @Valid private Map<String, String> context = new HashMap<>();

  @Valid private List<String> id = new ArrayList<>();

  private String location;

  private Boolean vendCredentials;

  public DeclareTableRequest identity(Identity identity) {
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

  public DeclareTableRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public DeclareTableRequest putContextItem(String key, String contextItem) {
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

  public DeclareTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public DeclareTableRequest addIdItem(String idItem) {
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
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public DeclareTableRequest location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Optional storage location for the table. If not provided, the namespace implementation should
   * determine the table location.
   *
   * @return location
   */
  @Schema(
      name = "location",
      description =
          "Optional storage location for the table. If not provided, the namespace implementation should determine the table location. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public DeclareTableRequest vendCredentials(Boolean vendCredentials) {
    this.vendCredentials = vendCredentials;
    return this;
  }

  /**
   * Whether to include vended credentials in the response `storage_options`. When true, the
   * implementation should provide vended credentials for accessing storage. When not set, the
   * implementation can decide whether to return vended credentials.
   *
   * @return vendCredentials
   */
  @Schema(
      name = "vend_credentials",
      description =
          "Whether to include vended credentials in the response `storage_options`. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vend_credentials")
  public Boolean getVendCredentials() {
    return vendCredentials;
  }

  public void setVendCredentials(Boolean vendCredentials) {
    this.vendCredentials = vendCredentials;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeclareTableRequest declareTableRequest = (DeclareTableRequest) o;
    return Objects.equals(this.identity, declareTableRequest.identity)
        && Objects.equals(this.context, declareTableRequest.context)
        && Objects.equals(this.id, declareTableRequest.id)
        && Objects.equals(this.location, declareTableRequest.location)
        && Objects.equals(this.vendCredentials, declareTableRequest.vendCredentials);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, id, location, vendCredentials);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeclareTableRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    vendCredentials: ").append(toIndentedString(vendCredentials)).append("\n");
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
