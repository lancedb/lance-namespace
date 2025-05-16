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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** ListNamespacesRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ListNamespacesRequest {

  @Valid private List<String> parent = new ArrayList<>();

  private String pageToken;

  private Integer pageSize;

  public ListNamespacesRequest parent(List<String> parent) {
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
  @Schema(name = "parent", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parent")
  public List<String> getParent() {
    return parent;
  }

  public void setParent(List<String> parent) {
    this.parent = parent;
  }

  public ListNamespacesRequest pageToken(String pageToken) {
    this.pageToken = pageToken;
    return this;
  }

  /**
   * An opaque token that allows pagination for list APIs (e.g. ListNamespaces). For an initial
   * client request for a list API, if the server cannot return all items in one response, or if
   * there are more items than the `pageSize` specified in the client request, the server must
   * return a `nextPageToken` in the response indicating there are more results available. After the
   * initial request, the value of `nextPageToken` from each response must be used by the client as
   * the `pageToken` parameter value for the next request. Clients must interpret either `null`,
   * missing value or empty string value of `nextPageToken` from a server response as the end of the
   * listing results.
   *
   * @return pageToken
   */
  @Schema(
      name = "pageToken",
      description =
          "An opaque token that allows pagination for list APIs (e.g. ListNamespaces). For an initial client request for a list API, if the server cannot return all items in one response, or if there are more items than the `pageSize` specified in the client request, the server must return a `nextPageToken` in the response indicating there are more results available. After the initial request, the value of `nextPageToken` from each response must be used by the client as the `pageToken` parameter value for the next request. Clients must interpret either `null`, missing value or empty string value of `nextPageToken` from a server response as the end of the listing results.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pageToken")
  public String getPageToken() {
    return pageToken;
  }

  public void setPageToken(String pageToken) {
    this.pageToken = pageToken;
  }

  public ListNamespacesRequest pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * An inclusive upper bound of the number of results that a client will receive.
   *
   * @return pageSize
   */
  @Schema(
      name = "pageSize",
      description = "An inclusive upper bound of the number of results that a client will receive.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
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
}
