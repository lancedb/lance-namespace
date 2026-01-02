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

/** AlterTableAlterColumnsRequest */
@JsonPropertyOrder({
  AlterTableAlterColumnsRequest.JSON_PROPERTY_IDENTITY,
  AlterTableAlterColumnsRequest.JSON_PROPERTY_CONTEXT,
  AlterTableAlterColumnsRequest.JSON_PROPERTY_ID,
  AlterTableAlterColumnsRequest.JSON_PROPERTY_ALTERATIONS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableAlterColumnsRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_ALTERATIONS = "alterations";
  @javax.annotation.Nonnull private List<AlterColumnsEntry> alterations = new ArrayList<>();

  public AlterTableAlterColumnsRequest() {}

  public AlterTableAlterColumnsRequest identity(@javax.annotation.Nullable Identity identity) {

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

  public AlterTableAlterColumnsRequest context(
      @javax.annotation.Nullable Map<String, String> context) {

    this.context = context;
    return this;
  }

  public AlterTableAlterColumnsRequest putContextItem(String key, String contextItem) {
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

  public AlterTableAlterColumnsRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public AlterTableAlterColumnsRequest addIdItem(String idItem) {
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

  public AlterTableAlterColumnsRequest alterations(
      @javax.annotation.Nonnull List<AlterColumnsEntry> alterations) {

    this.alterations = alterations;
    return this;
  }

  public AlterTableAlterColumnsRequest addAlterationsItem(AlterColumnsEntry alterationsItem) {
    if (this.alterations == null) {
      this.alterations = new ArrayList<>();
    }
    this.alterations.add(alterationsItem);
    return this;
  }

  /**
   * List of column alterations to perform
   *
   * @return alterations
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ALTERATIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<AlterColumnsEntry> getAlterations() {
    return alterations;
  }

  @JsonProperty(JSON_PROPERTY_ALTERATIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAlterations(@javax.annotation.Nonnull List<AlterColumnsEntry> alterations) {
    this.alterations = alterations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableAlterColumnsRequest alterTableAlterColumnsRequest = (AlterTableAlterColumnsRequest) o;
    return Objects.equals(this.identity, alterTableAlterColumnsRequest.identity)
        && Objects.equals(this.context, alterTableAlterColumnsRequest.context)
        && Objects.equals(this.id, alterTableAlterColumnsRequest.id)
        && Objects.equals(this.alterations, alterTableAlterColumnsRequest.alterations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, context, id, alterations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableAlterColumnsRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    alterations: ").append(toIndentedString(alterations)).append("\n");
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

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `alterations` to the URL query string
    if (getAlterations() != null) {
      for (int i = 0; i < getAlterations().size(); i++) {
        if (getAlterations().get(i) != null) {
          joiner.add(
              getAlterations()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%salterations%s%s",
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
