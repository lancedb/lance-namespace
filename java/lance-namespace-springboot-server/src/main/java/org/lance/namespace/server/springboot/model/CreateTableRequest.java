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
 * Request for creating a table, excluding the Arrow IPC stream. The table location and any
 * credential vending behavior are determined by the implementation and returned in the response,
 * rather than specified in this request.
 */
@Schema(
    name = "CreateTableRequest",
    description =
        "Request for creating a table, excluding the Arrow IPC stream. The table location and any credential vending behavior are determined by the implementation and returned in the response, rather than specified in this request. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableRequest {

  private Identity identity;

  @Valid private Map<String, String> context = new HashMap<>();

  @Valid private List<String> id = new ArrayList<>();

  private String mode;

  @Valid private Map<String, String> properties = new HashMap<>();

  @Valid private Map<String, String> storageOptions = new HashMap<>();

  public CreateTableRequest identity(Identity identity) {
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

  public CreateTableRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public CreateTableRequest putContextItem(String key, String contextItem) {
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

  public CreateTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public CreateTableRequest addIdItem(String idItem) {
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

  public CreateTableRequest mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * There are three modes when trying to create a table, to differentiate the behavior when a table
   * of the same name already exists. Case insensitive, supports both PascalCase and snake_case.
   * Valid values are: * Create: the operation fails with 409. * ExistOk: the operation succeeds and
   * the existing table is kept. * Overwrite: the existing table is dropped and a new table with
   * this name is created.
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description =
          "There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing table is kept.   * Overwrite: the existing table is dropped and a new table with this name is created. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public CreateTableRequest properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public CreateTableRequest putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Business logic properties stored and managed by the namespace implementation outside Lance
   * context, if supported by the implementation.
   *
   * @return properties
   */
  @Schema(
      name = "properties",
      description =
          "Business logic properties stored and managed by the namespace implementation outside Lance context, if supported by the implementation. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public CreateTableRequest storageOptions(Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
    return this;
  }

  public CreateTableRequest putStorageOptionsItem(String key, String storageOptionsItem) {
    if (this.storageOptions == null) {
      this.storageOptions = new HashMap<>();
    }
    this.storageOptions.put(key, storageOptionsItem);
    return this;
  }

  /**
   * Storage options that configure overrides for writing table data and metadata during table
   * creation. These are passed to Lance for the write path.
   *
   * @return storageOptions
   */
  @Schema(
      name = "storage_options",
      description =
          "Storage options that configure overrides for writing table data and metadata during table creation. These are passed to Lance for the write path. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("storage_options")
  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  public void setStorageOptions(Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableRequest createTableRequest = (CreateTableRequest) o;
    return Objects.equals(this.identity, createTableRequest.identity)
        && Objects.equals(this.context, createTableRequest.context)
        && Objects.equals(this.id, createTableRequest.id)
        && Objects.equals(this.mode, createTableRequest.mode)
        && Objects.equals(this.properties, createTableRequest.properties)
        && Objects.equals(this.storageOptions, createTableRequest.storageOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, id, mode, properties, storageOptions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    storageOptions: ").append(toIndentedString(storageOptions)).append("\n");
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
