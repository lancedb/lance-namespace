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

/** AlterTransactionResponse */
@JsonPropertyOrder({
  AlterTransactionResponse.JSON_PROPERTY_CONTEXT,
  AlterTransactionResponse.JSON_PROPERTY_STATUS,
  AlterTransactionResponse.JSON_PROPERTY_PROPERTIES
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionResponse {
  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_STATUS = "status";
  @javax.annotation.Nonnull private String status;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public AlterTransactionResponse() {}

  public AlterTransactionResponse context(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
    return this;
  }

  public AlterTransactionResponse putContextItem(String key, String contextItem) {
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

  public AlterTransactionResponse status(@javax.annotation.Nonnull String status) {
    this.status = status;
    return this;
  }

  /**
   * The status of a transaction. Case insensitive, supports both PascalCase and snake_case. Valid
   * values are: - Queued: the transaction is queued and not yet started - Running: the transaction
   * is currently running - Succeeded: the transaction has completed successfully - Failed: the
   * transaction has failed - Canceled: the transaction was canceled
   *
   * @return status
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getStatus() {
    return status;
  }

  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(@javax.annotation.Nonnull String status) {
    this.status = status;
  }

  public AlterTransactionResponse properties(
      @javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public AlterTransactionResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
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

  /** Return true if this AlterTransactionResponse object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionResponse alterTransactionResponse = (AlterTransactionResponse) o;
    return Objects.equals(this.context, alterTransactionResponse.context)
        && Objects.equals(this.status, alterTransactionResponse.status)
        && Objects.equals(this.properties, alterTransactionResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, status, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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

    // add `status` to the URL query string
    if (getStatus() != null) {
      joiner.add(
          String.format(
              "%sstatus%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getStatus()))));
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

    return joiner.toString();
  }
}
