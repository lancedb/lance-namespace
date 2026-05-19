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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Request to atomically create new version entries for multiple tables. The operation is atomic:
 * all versions are created or none are.
 */
@JsonPropertyOrder({
  BatchCreateTableVersionsRequest.JSON_PROPERTY_IDENTITY,
  BatchCreateTableVersionsRequest.JSON_PROPERTY_CONTEXT,
  BatchCreateTableVersionsRequest.JSON_PROPERTY_ENTRIES
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BatchCreateTableVersionsRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_ENTRIES = "entries";
  @javax.annotation.Nonnull private List<CreateTableVersionEntry> entries = new ArrayList<>();

  public BatchCreateTableVersionsRequest() {}

  public BatchCreateTableVersionsRequest identity(@javax.annotation.Nullable Identity identity) {

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

  public BatchCreateTableVersionsRequest context(
      @javax.annotation.Nullable Map<String, String> context) {

    this.context = context;
    return this;
  }

  public BatchCreateTableVersionsRequest putContextItem(String key, String contextItem) {
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

  public BatchCreateTableVersionsRequest entries(
      @javax.annotation.Nonnull List<CreateTableVersionEntry> entries) {

    this.entries = entries;
    return this;
  }

  public BatchCreateTableVersionsRequest addEntriesItem(CreateTableVersionEntry entriesItem) {
    if (this.entries == null) {
      this.entries = new ArrayList<>();
    }
    this.entries.add(entriesItem);
    return this;
  }

  /**
   * List of table version entries to create atomically
   *
   * @return entries
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ENTRIES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<CreateTableVersionEntry> getEntries() {
    return entries;
  }

  @JsonProperty(JSON_PROPERTY_ENTRIES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEntries(@javax.annotation.Nonnull List<CreateTableVersionEntry> entries) {
    this.entries = entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCreateTableVersionsRequest batchCreateTableVersionsRequest =
        (BatchCreateTableVersionsRequest) o;
    return Objects.equals(this.identity, batchCreateTableVersionsRequest.identity)
        && Objects.equals(this.context, batchCreateTableVersionsRequest.context)
        && Objects.equals(this.entries, batchCreateTableVersionsRequest.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, entries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCreateTableVersionsRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    entries: ").append(toIndentedString(entries)).append("\n");
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
        try {
          joiner.add(
              String.format(
                  "%scontext%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getContext().get(_key),
                  URLEncoder.encode(String.valueOf(getContext().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `entries` to the URL query string
    if (getEntries() != null) {
      for (int i = 0; i < getEntries().size(); i++) {
        if (getEntries().get(i) != null) {
          joiner.add(
              getEntries()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%sentries%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}
