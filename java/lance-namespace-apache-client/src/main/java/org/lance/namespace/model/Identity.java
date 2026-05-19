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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.StringJoiner;

/** Identity information of a request. */
@JsonPropertyOrder({Identity.JSON_PROPERTY_API_KEY, Identity.JSON_PROPERTY_AUTH_TOKEN})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class Identity {
  public static final String JSON_PROPERTY_API_KEY = "api_key";
  @javax.annotation.Nullable private String apiKey;

  public static final String JSON_PROPERTY_AUTH_TOKEN = "auth_token";
  @javax.annotation.Nullable private String authToken;

  public Identity() {}

  public Identity apiKey(@javax.annotation.Nullable String apiKey) {

    this.apiKey = apiKey;
    return this;
  }

  /**
   * API key for authentication. REST NAMESPACE ONLY This is passed via the &#x60;x-api-key&#x60;
   * header.
   *
   * @return apiKey
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_API_KEY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getApiKey() {
    return apiKey;
  }

  @JsonProperty(JSON_PROPERTY_API_KEY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setApiKey(@javax.annotation.Nullable String apiKey) {
    this.apiKey = apiKey;
  }

  public Identity authToken(@javax.annotation.Nullable String authToken) {

    this.authToken = authToken;
    return this;
  }

  /**
   * Bearer token for authentication. REST NAMESPACE ONLY This is passed via the
   * &#x60;Authorization&#x60; header with the Bearer scheme (e.g., &#x60;Bearer
   * &lt;token&gt;&#x60;).
   *
   * @return authToken
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_AUTH_TOKEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getAuthToken() {
    return authToken;
  }

  @JsonProperty(JSON_PROPERTY_AUTH_TOKEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAuthToken(@javax.annotation.Nullable String authToken) {
    this.authToken = authToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Identity identity = (Identity) o;
    return Objects.equals(this.apiKey, identity.apiKey)
        && Objects.equals(this.authToken, identity.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKey, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Identity {\n");
    sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
    sb.append("    authToken: ").append(toIndentedString(authToken)).append("\n");
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

    // add `api_key` to the URL query string
    if (getApiKey() != null) {
      try {
        joiner.add(
            String.format(
                "%sapi_key%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getApiKey()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `auth_token` to the URL query string
    if (getAuthToken() != null) {
      try {
        joiner.add(
            String.format(
                "%sauth_token%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getAuthToken()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
