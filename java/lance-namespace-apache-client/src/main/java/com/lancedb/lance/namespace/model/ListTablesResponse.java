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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/** ListTablesResponse */
@JsonPropertyOrder({
  ListTablesResponse.JSON_PROPERTY_TABLES,
  ListTablesResponse.JSON_PROPERTY_PAGE_TOKEN
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class ListTablesResponse {
  public static final String JSON_PROPERTY_TABLES = "tables";
  @javax.annotation.Nonnull private Set<String> tables = new LinkedHashSet<>();

  public static final String JSON_PROPERTY_PAGE_TOKEN = "page_token";
  @javax.annotation.Nullable private String pageToken;

  public ListTablesResponse() {}

  public ListTablesResponse tables(@javax.annotation.Nonnull Set<String> tables) {

    this.tables = tables;
    return this;
  }

  public ListTablesResponse addTablesItem(String tablesItem) {
    if (this.tables == null) {
      this.tables = new LinkedHashSet<>();
    }
    this.tables.add(tablesItem);
    return this;
  }

  /**
   * The list of names of the tables relative to the parent namespace &#x60;id&#x60; in the request.
   *
   * @return tables
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TABLES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Set<String> getTables() {
    return tables;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  @JsonProperty(JSON_PROPERTY_TABLES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTables(@javax.annotation.Nonnull Set<String> tables) {
    this.tables = tables;
  }

  public ListTablesResponse pageToken(@javax.annotation.Nullable String pageToken) {

    this.pageToken = pageToken;
    return this;
  }

  /**
   * An opaque token that allows pagination for list operations (e.g. ListNamespaces). For an
   * initial request of a list operation, if the implementation cannot return all items in one
   * response, or if there are more items than the page limit specified in the request, the
   * implementation must return a page token in the response, indicating there are more results
   * available. After the initial request, the value of the page token from each response must be
   * used as the page token value for the next request. Caller must interpret either
   * &#x60;null&#x60;, missing value or empty string value of the page token from the
   * implementation&#39;s response as the end of the listing results.
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListTablesResponse listTablesResponse = (ListTablesResponse) o;
    return Objects.equals(this.tables, listTablesResponse.tables)
        && Objects.equals(this.pageToken, listTablesResponse.pageToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tables, pageToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListTablesResponse {\n");
    sb.append("    tables: ").append(toIndentedString(tables)).append("\n");
    sb.append("    pageToken: ").append(toIndentedString(pageToken)).append("\n");
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

    // add `tables` to the URL query string
    if (getTables() != null) {
      int i = 0;
      for (String _item : getTables()) {
        try {
          joiner.add(
              String.format(
                  "%stables%s%s=%s",
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

    // add `page_token` to the URL query string
    if (getPageToken() != null) {
      try {
        joiner.add(
            String.format(
                "%spage_token%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPageToken()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
