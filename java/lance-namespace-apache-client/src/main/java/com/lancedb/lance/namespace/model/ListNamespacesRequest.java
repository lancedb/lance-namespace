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
package com.lancedb.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** ListNamespacesRequest */
@JsonPropertyOrder({
  ListNamespacesRequest.JSON_PROPERTY_PARENT,
  ListNamespacesRequest.JSON_PROPERTY_PAGE_TOKEN,
  ListNamespacesRequest.JSON_PROPERTY_PAGE_SIZE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class ListNamespacesRequest {
  public static final String JSON_PROPERTY_PARENT = "parent";
  @javax.annotation.Nullable private List<String> parent = new ArrayList<>();

  public static final String JSON_PROPERTY_PAGE_TOKEN = "pageToken";
  @javax.annotation.Nullable private String pageToken;

  public static final String JSON_PROPERTY_PAGE_SIZE = "pageSize";
  @javax.annotation.Nullable private Integer pageSize;

  public ListNamespacesRequest() {}

  public ListNamespacesRequest parent(@javax.annotation.Nullable List<String> parent) {

    this.parent = parent;
    return this;
  }

  public ListNamespacesRequest addParentItem(String parentItem) {
    if (this.parent == null) {
      this.parent = new ArrayList<>();
    }
    this.parent.add(parentItem);
    return this;
  }

  /**
   * Get parent
   *
   * @return parent
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PARENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getParent() {
    return parent;
  }

  @JsonProperty(JSON_PROPERTY_PARENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setParent(@javax.annotation.Nullable List<String> parent) {
    this.parent = parent;
  }

  public ListNamespacesRequest pageToken(@javax.annotation.Nullable String pageToken) {

    this.pageToken = pageToken;
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
   * @return pageToken
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PAGE_TOKEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getPageToken() {
    return pageToken;
  }

  @JsonProperty(JSON_PROPERTY_PAGE_TOKEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPageToken(@javax.annotation.Nullable String pageToken) {
    this.pageToken = pageToken;
  }

  public ListNamespacesRequest pageSize(@javax.annotation.Nullable Integer pageSize) {

    this.pageSize = pageSize;
    return this;
  }

  /**
   * An inclusive upper bound of the number of results that a client will receive.
   *
   * @return pageSize
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PAGE_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getPageSize() {
    return pageSize;
  }

  @JsonProperty(JSON_PROPERTY_PAGE_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPageSize(@javax.annotation.Nullable Integer pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListNamespacesRequest listNamespacesRequest = (ListNamespacesRequest) o;
    return Objects.equals(this.parent, listNamespacesRequest.parent)
        && Objects.equals(this.pageToken, listNamespacesRequest.pageToken)
        && Objects.equals(this.pageSize, listNamespacesRequest.pageSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parent, pageToken, pageSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListNamespacesRequest {\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    pageToken: ").append(toIndentedString(pageToken)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
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

    // add `parent` to the URL query string
    if (getParent() != null) {
      for (int i = 0; i < getParent().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sparent%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getParent().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `pageToken` to the URL query string
    if (getPageToken() != null) {
      try {
        joiner.add(
            String.format(
                "%spageToken%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPageToken()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `pageSize` to the URL query string
    if (getPageSize() != null) {
      try {
        joiner.add(
            String.format(
                "%spageSize%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPageSize()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
