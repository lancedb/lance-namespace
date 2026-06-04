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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateTableBranchRequest */
@JsonPropertyOrder({
  CreateTableBranchRequest.JSON_PROPERTY_IDENTITY,
  CreateTableBranchRequest.JSON_PROPERTY_CONTEXT,
  CreateTableBranchRequest.JSON_PROPERTY_ID,
  CreateTableBranchRequest.JSON_PROPERTY_NAME,
  CreateTableBranchRequest.JSON_PROPERTY_FROM_BRANCH,
  CreateTableBranchRequest.JSON_PROPERTY_FROM_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableBranchRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_FROM_BRANCH = "from_branch";
  @javax.annotation.Nullable private String fromBranch;

  public static final String JSON_PROPERTY_FROM_VERSION = "from_version";
  @javax.annotation.Nullable private Long fromVersion;

  public CreateTableBranchRequest() {}

  public CreateTableBranchRequest identity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public CreateTableBranchRequest context(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
    return this;
  }

  public CreateTableBranchRequest putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context for a request as key-value pairs. How to use the context is custom to the
   * specific implementation. REST NAMESPACE ONLY Context entries are passed via HTTP headers using
   * the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a
   * context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the
   * header &#x60;x-lance-ctx-trace_id: abc123&#x60;.
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

  public CreateTableBranchRequest id(@javax.annotation.Nullable List<String> id) {
    this.id = id;
    return this;
  }

  public CreateTableBranchRequest addIdItem(String idItem) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public CreateTableBranchRequest name(@javax.annotation.Nonnull String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the branch to create
   *
   * @return name
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(@javax.annotation.Nonnull String name) {
    this.name = name;
  }

  public CreateTableBranchRequest fromBranch(@javax.annotation.Nullable String fromBranch) {
    this.fromBranch = fromBranch;
    return this;
  }

  /**
   * Source branch to create the new branch from. When omitted, the new branch is created from the
   * main branch.
   *
   * @return fromBranch
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FROM_BRANCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getFromBranch() {
    return fromBranch;
  }

  @JsonProperty(JSON_PROPERTY_FROM_BRANCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFromBranch(@javax.annotation.Nullable String fromBranch) {
    this.fromBranch = fromBranch;
  }

  public CreateTableBranchRequest fromVersion(@javax.annotation.Nullable Long fromVersion) {
    this.fromVersion = fromVersion;
    return this;
  }

  /**
   * Version of the source (branch or main) to create from. When omitted, the latest version of the
   * source is used. minimum: 0
   *
   * @return fromVersion
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FROM_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getFromVersion() {
    return fromVersion;
  }

  @JsonProperty(JSON_PROPERTY_FROM_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFromVersion(@javax.annotation.Nullable Long fromVersion) {
    this.fromVersion = fromVersion;
  }

  /** Return true if this CreateTableBranchRequest object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableBranchRequest createTableBranchRequest = (CreateTableBranchRequest) o;
    return Objects.equals(this.identity, createTableBranchRequest.identity)
        && Objects.equals(this.context, createTableBranchRequest.context)
        && Objects.equals(this.id, createTableBranchRequest.id)
        && Objects.equals(this.name, createTableBranchRequest.name)
        && Objects.equals(this.fromBranch, createTableBranchRequest.fromBranch)
        && Objects.equals(this.fromVersion, createTableBranchRequest.fromVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, id, name, fromBranch, fromVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableBranchRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    fromBranch: ").append(toIndentedString(fromBranch)).append("\n");
    sb.append("    fromVersion: ").append(toIndentedString(fromVersion)).append("\n");
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

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

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        joiner.add(
            String.format(
                "%sid%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getId().get(i)))));
      }
    }

    // add `name` to the URL query string
    if (getName() != null) {
      joiner.add(
          String.format(
              "%sname%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getName()))));
    }

    // add `from_branch` to the URL query string
    if (getFromBranch() != null) {
      joiner.add(
          String.format(
              "%sfrom_branch%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getFromBranch()))));
    }

    // add `from_version` to the URL query string
    if (getFromVersion() != null) {
      joiner.add(
          String.format(
              "%sfrom_version%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getFromVersion()))));
    }

    return joiner.toString();
  }
}
