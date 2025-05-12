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
package com.lancedb.lance.namespace.client.apache.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/** ListNamespacesResponse */
@JsonPropertyOrder({
  ListNamespacesResponse.JSON_PROPERTY_NAMESPACES,
  ListNamespacesResponse.JSON_PROPERTY_NEXT_PAGE_TOKEN
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.13.0")
public class ListNamespacesResponse {
  public static final String JSON_PROPERTY_NAMESPACES = "namespaces";
  @javax.annotation.Nonnull private Set<String> namespaces = new LinkedHashSet<>();

  public static final String JSON_PROPERTY_NEXT_PAGE_TOKEN = "nextPageToken";
  @javax.annotation.Nullable private String nextPageToken;

  public ListNamespacesResponse() {}

  public ListNamespacesResponse namespaces(@javax.annotation.Nonnull Set<String> namespaces) {

    this.namespaces = namespaces;
    return this;
  }

  public ListNamespacesResponse addNamespacesItem(String namespacesItem) {
    if (this.namespaces == null) {
      this.namespaces = new LinkedHashSet<>();
    }
    this.namespaces.add(namespacesItem);
    return this;
  }

  /**
   * Get namespaces
   *
   * @return namespaces
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAMESPACES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Set<String> getNamespaces() {
    return namespaces;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  @JsonProperty(JSON_PROPERTY_NAMESPACES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNamespaces(@javax.annotation.Nonnull Set<String> namespaces) {
    this.namespaces = namespaces;
  }

  public ListNamespacesResponse nextPageToken(@javax.annotation.Nullable String nextPageToken) {

    this.nextPageToken = nextPageToken;
    return this;
  }

  /**
   * An opaque token that allows pagination for list APIs (e.g. ListNamespaces). For an initial
   * client request for a list API, if the server cannot return all items in one response, or if
   * there are more items than the &#x60;pageSize&#x60; specified in the client request, the server
   * must return a &#x60;nextPageToken&#x60; in the response indicating there are more results
   * available. After the initial request, the value of &#x60;nextPageToken&#x60; from each response
   * must be used by the client as the &#x60;pageToken&#x60; parameter value for the next request.
   * Clients must interpret either &#x60;null&#x60;, missing value or empty string value of
   * &#x60;nextPageToken&#x60; from a server response as the end of the listing results.
   *
   * @return nextPageToken
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NEXT_PAGE_TOKEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getNextPageToken() {
    return nextPageToken;
  }

  @JsonProperty(JSON_PROPERTY_NEXT_PAGE_TOKEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNextPageToken(@javax.annotation.Nullable String nextPageToken) {
    this.nextPageToken = nextPageToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListNamespacesResponse listNamespacesResponse = (ListNamespacesResponse) o;
    return Objects.equals(this.namespaces, listNamespacesResponse.namespaces)
        && Objects.equals(this.nextPageToken, listNamespacesResponse.nextPageToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespaces, nextPageToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListNamespacesResponse {\n");
    sb.append("    namespaces: ").append(toIndentedString(namespaces)).append("\n");
    sb.append("    nextPageToken: ").append(toIndentedString(nextPageToken)).append("\n");
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

    // add `namespaces` to the URL query string
    if (getNamespaces() != null) {
      int i = 0;
      for (String _item : getNamespaces()) {
        try {
          joiner.add(
              String.format(
                  "%snamespaces%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(_item), "UTF-8").replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
      i++;
    }

    // add `nextPageToken` to the URL query string
    if (getNextPageToken() != null) {
      try {
        joiner.add(
            String.format(
                "%snextPageToken%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNextPageToken()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
