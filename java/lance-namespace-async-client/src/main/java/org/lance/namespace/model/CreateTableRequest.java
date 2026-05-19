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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Request for creating a table, excluding the Arrow IPC stream. The table location and any
 * credential vending behavior are determined by the implementation and returned in the response,
 * rather than specified in this request.
 */
@JsonPropertyOrder({
  CreateTableRequest.JSON_PROPERTY_IDENTITY,
  CreateTableRequest.JSON_PROPERTY_CONTEXT,
  CreateTableRequest.JSON_PROPERTY_ID,
  CreateTableRequest.JSON_PROPERTY_MODE,
  CreateTableRequest.JSON_PROPERTY_PROPERTIES,
  CreateTableRequest.JSON_PROPERTY_STORAGE_OPTIONS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_MODE = "mode";
  @javax.annotation.Nullable private String mode;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public static final String JSON_PROPERTY_STORAGE_OPTIONS = "storage_options";
  @javax.annotation.Nullable private Map<String, String> storageOptions = new HashMap<>();

  public CreateTableRequest() {}

  public CreateTableRequest identity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public CreateTableRequest context(@javax.annotation.Nullable Map<String, String> context) {
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
   * the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a
   * context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the
   * header &#x60;x-lance-ctx-trace_id: abc123&#x60;.
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

  public CreateTableRequest id(@javax.annotation.Nullable List<String> id) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public CreateTableRequest mode(@javax.annotation.Nullable String mode) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getMode() {
    return mode;
  }

  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMode(@javax.annotation.Nullable String mode) {
    this.mode = mode;
  }

  public CreateTableRequest properties(@javax.annotation.Nullable Map<String, String> properties) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getProperties() {
    return properties;
  }

  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(@javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
  }

  public CreateTableRequest storageOptions(
      @javax.annotation.Nullable Map<String, String> storageOptions) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STORAGE_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  @JsonProperty(JSON_PROPERTY_STORAGE_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStorageOptions(@javax.annotation.Nullable Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
  }

  /** Return true if this CreateTableRequest object is equal to o. */
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

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

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        joiner.add(
            String.format(
                "%sid%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getId().get(i)))));
      }
    }

    // add `mode` to the URL query string
    if (getMode() != null) {
      joiner.add(
          String.format(
              "%smode%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getMode()))));
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        joiner.add(
            String.format(
                "%sproperties%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getProperties().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getProperties().get(_key)))));
      }
    }

    // add `storage_options` to the URL query string
    if (getStorageOptions() != null) {
      for (String _key : getStorageOptions().keySet()) {
        joiner.add(
            String.format(
                "%sstorage_options%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getStorageOptions().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getStorageOptions().get(_key)))));
      }
    }

    return joiner.toString();
  }
}
