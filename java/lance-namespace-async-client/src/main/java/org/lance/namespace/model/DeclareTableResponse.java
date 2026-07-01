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

/** Response for declaring a table. */
@JsonPropertyOrder({
  DeclareTableResponse.JSON_PROPERTY_CONTEXT,
  DeclareTableResponse.JSON_PROPERTY_TRANSACTION_ID,
  DeclareTableResponse.JSON_PROPERTY_LOCATION,
  DeclareTableResponse.JSON_PROPERTY_STORAGE_OPTIONS,
  DeclareTableResponse.JSON_PROPERTY_PROPERTIES,
  DeclareTableResponse.JSON_PROPERTY_MANAGED_VERSIONING
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DeclareTableResponse {
  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_TRANSACTION_ID = "transaction_id";
  @javax.annotation.Nullable private String transactionId;

  public static final String JSON_PROPERTY_LOCATION = "location";
  @javax.annotation.Nullable private String location;

  public static final String JSON_PROPERTY_STORAGE_OPTIONS = "storage_options";
  @javax.annotation.Nullable private Map<String, String> storageOptions = new HashMap<>();

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public static final String JSON_PROPERTY_MANAGED_VERSIONING = "managed_versioning";
  @javax.annotation.Nullable private Boolean managedVersioning;

  public DeclareTableResponse() {}

  public DeclareTableResponse context(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
    return this;
  }

  public DeclareTableResponse putContextItem(String key, String contextItem) {
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

  public DeclareTableResponse transactionId(@javax.annotation.Nullable String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier
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

  public DeclareTableResponse location(@javax.annotation.Nullable String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getLocation() {
    return location;
  }

  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocation(@javax.annotation.Nullable String location) {
    this.location = location;
  }

  public DeclareTableResponse storageOptions(
      @javax.annotation.Nullable Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
    return this;
  }

  public DeclareTableResponse putStorageOptionsItem(String key, String storageOptionsItem) {
    if (this.storageOptions == null) {
      this.storageOptions = new HashMap<>();
    }
    this.storageOptions.put(key, storageOptionsItem);
    return this;
  }

  /**
   * Configuration options to be used to access storage. The available options depend on the type of
   * storage in use. These will be passed directly to Lance to initialize storage access.
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

  public DeclareTableResponse properties(
      @javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public DeclareTableResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * If the implementation does not support table properties, it should return null for this field.
   * Otherwise it should return the properties.
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

  public DeclareTableResponse managedVersioning(
      @javax.annotation.Nullable Boolean managedVersioning) {
    this.managedVersioning = managedVersioning;
    return this;
  }

  /**
   * When true, the caller should use namespace table version operations (CreateTableVersion,
   * BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to
   * manage table versions instead of relying on Lance&#39;s native version management.
   *
   * @return managedVersioning
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MANAGED_VERSIONING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getManagedVersioning() {
    return managedVersioning;
  }

  @JsonProperty(JSON_PROPERTY_MANAGED_VERSIONING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setManagedVersioning(@javax.annotation.Nullable Boolean managedVersioning) {
    this.managedVersioning = managedVersioning;
  }

  /** Return true if this DeclareTableResponse object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeclareTableResponse declareTableResponse = (DeclareTableResponse) o;
    return Objects.equals(this.context, declareTableResponse.context)
        && Objects.equals(this.transactionId, declareTableResponse.transactionId)
        && Objects.equals(this.location, declareTableResponse.location)
        && Objects.equals(this.storageOptions, declareTableResponse.storageOptions)
        && Objects.equals(this.properties, declareTableResponse.properties)
        && Objects.equals(this.managedVersioning, declareTableResponse.managedVersioning);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        context, transactionId, location, storageOptions, properties, managedVersioning);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeclareTableResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    storageOptions: ").append(toIndentedString(storageOptions)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    managedVersioning: ").append(toIndentedString(managedVersioning)).append("\n");
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

    // add `transaction_id` to the URL query string
    if (getTransactionId() != null) {
      joiner.add(
          String.format(
              "%stransaction_id%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTransactionId()))));
    }

    // add `location` to the URL query string
    if (getLocation() != null) {
      joiner.add(
          String.format(
              "%slocation%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getLocation()))));
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

    // add `managed_versioning` to the URL query string
    if (getManagedVersioning() != null) {
      joiner.add(
          String.format(
              "%smanaged_versioning%s=%s",
              prefix,
              suffix,
              ApiClient.urlEncode(ApiClient.valueToString(getManagedVersioning()))));
    }

    return joiner.toString();
  }
}
