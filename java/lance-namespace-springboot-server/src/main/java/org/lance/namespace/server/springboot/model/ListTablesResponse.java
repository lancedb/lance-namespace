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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** ListTablesResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ListTablesResponse {

  @Valid private Set<String> tables = new LinkedHashSet<>();

  private String pageToken;

  public ListTablesResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public ListTablesResponse(Set<String> tables) {
    this.tables = tables;
  }

  public ListTablesResponse tables(Set<String> tables) {
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
   * The list of names of all the tables under the connected namespace implementation. This should
   * recursively list all the tables in all child namespaces. Each string in the list is the full
   * identifier in string form.
   *
   * @return tables
   */
  @NotNull
  @Schema(
      name = "tables",
      description =
          "The list of names of all the tables under the connected namespace implementation. This should recursively list all the tables in all child namespaces. Each string in the list is the full identifier in string form. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tables")
  public Set<String> getTables() {
    return tables;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  public void setTables(Set<String> tables) {
    this.tables = tables;
  }

  public ListTablesResponse pageToken(String pageToken) {
    this.pageToken = pageToken;
    return this;
  }

  /**
   * An opaque token that allows pagination for list operations (e.g. ListNamespaces). For an
   * initial request of a list operation, if the implementation cannot return all items in one
   * response, or if there are more items than the page limit specified in the request, the
   * implementation must return a page token in the response, indicating there are more results
   * available. After the initial request, the value of the page token from each response must be
   * used as the page token value for the next request. Caller must interpret either `null`, missing
   * value or empty string value of the page token from the implementation's response as the end of
   * the listing results.
   *
   * @return pageToken
   */
  @Schema(
      name = "page_token",
      description =
          "An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation,  if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request,  the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either `null`,  missing value or empty string value of the page token from the implementation's response as the end of the listing results. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("page_token")
  public String getPageToken() {
    return pageToken;
  }

  public void setPageToken(String pageToken) {
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
}
