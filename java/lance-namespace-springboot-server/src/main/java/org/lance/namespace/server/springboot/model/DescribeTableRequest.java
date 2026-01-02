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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** DescribeTableRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableRequest {

  private Identity identity;

  @Valid private Map<String, String> context = new HashMap<>();

  @Valid private List<String> id = new ArrayList<>();

  private Long version;

  private Boolean withTableUri = false;

  private Boolean loadDetailedMetadata;

  private Boolean vendCredentials;

  public DescribeTableRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @Valid
  @Schema(name = "identity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identity")
  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public DescribeTableRequest context(Map<String, String> context) {
    this.context = context;
    return this;
  }

  public DescribeTableRequest putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context for a request as key-value pairs. How to use the context is custom to the
   * specific implementation. REST NAMESPACE ONLY Context entries are passed via HTTP headers using
   * the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry
   * `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.
   *
   * @return context
   */
  @Schema(
      name = "context",
      description =
          "Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }

  public DescribeTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public DescribeTableRequest addIdItem(String idItem) {
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
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public DescribeTableRequest version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Version of the table to describe. If not specified, server should resolve it to the latest
   * version. minimum: 0
   *
   * @return version
   */
  @Min(0L)
  @Schema(
      name = "version",
      description =
          "Version of the table to describe. If not specified, server should resolve it to the latest version. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public DescribeTableRequest withTableUri(Boolean withTableUri) {
    this.withTableUri = withTableUri;
    return this;
  }

  /**
   * Whether to include the table URI in the response. Default is false.
   *
   * @return withTableUri
   */
  @Schema(
      name = "with_table_uri",
      description = "Whether to include the table URI in the response. Default is false. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("with_table_uri")
  public Boolean getWithTableUri() {
    return withTableUri;
  }

  public void setWithTableUri(Boolean withTableUri) {
    this.withTableUri = withTableUri;
  }

  public DescribeTableRequest loadDetailedMetadata(Boolean loadDetailedMetadata) {
    this.loadDetailedMetadata = loadDetailedMetadata;
    return this;
  }

  /**
   * Whether to load detailed metadata that requires opening the dataset. When true, the response
   * must include all detailed metadata such as `version`, `schema`, and `stats` which require
   * reading the dataset. When not set, the implementation can decide whether to return detailed
   * metadata and which parts of detailed metadata to return.
   *
   * @return loadDetailedMetadata
   */
  @Schema(
      name = "load_detailed_metadata",
      description =
          "Whether to load detailed metadata that requires opening the dataset. When true, the response must include all detailed metadata such as `version`, `schema`, and `stats` which require reading the dataset. When not set, the implementation can decide whether to return detailed metadata and which parts of detailed metadata to return. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("load_detailed_metadata")
  public Boolean getLoadDetailedMetadata() {
    return loadDetailedMetadata;
  }

  public void setLoadDetailedMetadata(Boolean loadDetailedMetadata) {
    this.loadDetailedMetadata = loadDetailedMetadata;
  }

  public DescribeTableRequest vendCredentials(Boolean vendCredentials) {
    this.vendCredentials = vendCredentials;
    return this;
  }

  /**
   * Whether to include vended credentials in the response `storage_options`. When true, the
   * implementation should provide vended credentials for accessing storage. When not set, the
   * implementation can decide whether to return vended credentials.
   *
   * @return vendCredentials
   */
  @Schema(
      name = "vend_credentials",
      description =
          "Whether to include vended credentials in the response `storage_options`. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("vend_credentials")
  public Boolean getVendCredentials() {
    return vendCredentials;
  }

  public void setVendCredentials(Boolean vendCredentials) {
    this.vendCredentials = vendCredentials;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableRequest describeTableRequest = (DescribeTableRequest) o;
    return Objects.equals(this.identity, describeTableRequest.identity)
        && Objects.equals(this.context, describeTableRequest.context)
        && Objects.equals(this.id, describeTableRequest.id)
        && Objects.equals(this.version, describeTableRequest.version)
        && Objects.equals(this.withTableUri, describeTableRequest.withTableUri)
        && Objects.equals(this.loadDetailedMetadata, describeTableRequest.loadDetailedMetadata)
        && Objects.equals(this.vendCredentials, describeTableRequest.vendCredentials);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity, context, id, version, withTableUri, loadDetailedMetadata, vendCredentials);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    withTableUri: ").append(toIndentedString(withTableUri)).append("\n");
    sb.append("    loadDetailedMetadata: ")
        .append(toIndentedString(loadDetailedMetadata))
        .append("\n");
    sb.append("    vendCredentials: ").append(toIndentedString(vendCredentials)).append("\n");
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
