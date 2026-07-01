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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Query results. This model is not used by the REST namespace, which returns the Arrow IPC file
 * binary data directly (see the QueryTable operation). It is provided as a standard data model for
 * non-REST LanceNamespace interfaces (e.g. Java, Python).
 */
@Schema(
    name = "QueryTableResponse",
    description =
        "Query results.  This model is not used by the REST namespace, which returns the Arrow IPC file binary data directly (see the QueryTable operation). It is provided as a standard data model for non-REST LanceNamespace interfaces (e.g. Java, Python). ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableResponse {

  @Valid private Map<String, String> context = new HashMap<>();

  private org.springframework.core.io.Resource data;

  public QueryTableResponse context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public QueryTableResponse putContextItem(String key, String contextItem) {
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
   * Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request,
   * any entry whose key starts with `header.` is sent as an HTTP request header with the prefix
   * stripped. For example, the entry `{\"header.Authorization\": \"Bearer abc\"}` is sent as the
   * request header `Authorization: Bearer abc`. - On a response, every HTTP response header is
   * returned as an entry whose key is the header name prefixed with `header.`. For example, the
   * response header `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\":
   * \"abc123\"}`.
   *
   * @return context
   */
  @Schema(
      name = "context",
      description =
          "Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }

  public QueryTableResponse data(org.springframework.core.io.Resource data) {
    this.data = data;
    return this;
  }

  /**
   * Query results as Arrow IPC file binary data.
   *
   * @return data
   */
  @Valid
  @Schema(
      name = "data",
      description = "Query results as Arrow IPC file binary data.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("data")
  public org.springframework.core.io.Resource getData() {
    return data;
  }

  public void setData(org.springframework.core.io.Resource data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryTableResponse queryTableResponse = (QueryTableResponse) o;
    return Objects.equals(this.context, queryTableResponse.context)
        && Objects.equals(this.data, queryTableResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryTableResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
