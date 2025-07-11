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

/** UpdateTableRequest */
@JsonPropertyOrder({
  UpdateTableRequest.JSON_PROPERTY_NAME,
  UpdateTableRequest.JSON_PROPERTY_NAMESPACE,
  UpdateTableRequest.JSON_PROPERTY_PREDICATE,
  UpdateTableRequest.JSON_PROPERTY_UPDATES
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateTableRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nonnull private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_PREDICATE = "predicate";
  @javax.annotation.Nullable private String predicate;

  public static final String JSON_PROPERTY_UPDATES = "updates";
  @javax.annotation.Nonnull private List<List<String>> updates = new ArrayList<>();

  public UpdateTableRequest() {}

  public UpdateTableRequest name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * The table name
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

  public UpdateTableRequest namespace(@javax.annotation.Nonnull List<String> namespace) {

    this.namespace = namespace;
    return this;
  }

  public UpdateTableRequest addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * The namespace identifier
   *
   * @return namespace
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getNamespace() {
    return namespace;
  }

  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNamespace(@javax.annotation.Nonnull List<String> namespace) {
    this.namespace = namespace;
  }

  public UpdateTableRequest predicate(@javax.annotation.Nullable String predicate) {

    this.predicate = predicate;
    return this;
  }

  /**
   * Optional SQL predicate to filter rows for update
   *
   * @return predicate
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PREDICATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getPredicate() {
    return predicate;
  }

  @JsonProperty(JSON_PROPERTY_PREDICATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPredicate(@javax.annotation.Nullable String predicate) {
    this.predicate = predicate;
  }

  public UpdateTableRequest updates(@javax.annotation.Nonnull List<List<String>> updates) {

    this.updates = updates;
    return this;
  }

  public UpdateTableRequest addUpdatesItem(List<String> updatesItem) {
    if (this.updates == null) {
      this.updates = new ArrayList<>();
    }
    this.updates.add(updatesItem);
    return this;
  }

  /**
   * List of column updates as [column_name, expression] pairs
   *
   * @return updates
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UPDATES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<List<String>> getUpdates() {
    return updates;
  }

  @JsonProperty(JSON_PROPERTY_UPDATES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUpdates(@javax.annotation.Nonnull List<List<String>> updates) {
    this.updates = updates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTableRequest updateTableRequest = (UpdateTableRequest) o;
    return Objects.equals(this.name, updateTableRequest.name)
        && Objects.equals(this.namespace, updateTableRequest.namespace)
        && Objects.equals(this.predicate, updateTableRequest.predicate)
        && Objects.equals(this.updates, updateTableRequest.updates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, predicate, updates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    predicate: ").append(toIndentedString(predicate)).append("\n");
    sb.append("    updates: ").append(toIndentedString(updates)).append("\n");
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

    // add `name` to the URL query string
    if (getName() != null) {
      try {
        joiner.add(
            String.format(
                "%sname%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getName()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `namespace` to the URL query string
    if (getNamespace() != null) {
      for (int i = 0; i < getNamespace().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%snamespace%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getNamespace().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `predicate` to the URL query string
    if (getPredicate() != null) {
      try {
        joiner.add(
            String.format(
                "%spredicate%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPredicate()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `updates` to the URL query string
    if (getUpdates() != null) {
      for (int i = 0; i < getUpdates().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%supdates%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getUpdates().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    return joiner.toString();
  }
}
