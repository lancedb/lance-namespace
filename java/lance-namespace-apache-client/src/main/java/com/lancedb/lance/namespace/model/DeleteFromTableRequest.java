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

/** DeleteFromTableRequest */
@JsonPropertyOrder({
  DeleteFromTableRequest.JSON_PROPERTY_NAME,
  DeleteFromTableRequest.JSON_PROPERTY_NAMESPACE,
  DeleteFromTableRequest.JSON_PROPERTY_PREDICATE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DeleteFromTableRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nonnull private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_PREDICATE = "predicate";
  @javax.annotation.Nonnull private String predicate;

  public DeleteFromTableRequest() {}

  public DeleteFromTableRequest name(@javax.annotation.Nonnull String name) {

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

  public DeleteFromTableRequest namespace(@javax.annotation.Nonnull List<String> namespace) {

    this.namespace = namespace;
    return this;
  }

  public DeleteFromTableRequest addNamespaceItem(String namespaceItem) {
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

  public DeleteFromTableRequest predicate(@javax.annotation.Nonnull String predicate) {

    this.predicate = predicate;
    return this;
  }

  /**
   * SQL predicate to filter rows for deletion
   *
   * @return predicate
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PREDICATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getPredicate() {
    return predicate;
  }

  @JsonProperty(JSON_PROPERTY_PREDICATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPredicate(@javax.annotation.Nonnull String predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteFromTableRequest deleteFromTableRequest = (DeleteFromTableRequest) o;
    return Objects.equals(this.name, deleteFromTableRequest.name)
        && Objects.equals(this.namespace, deleteFromTableRequest.namespace)
        && Objects.equals(this.predicate, deleteFromTableRequest.predicate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, predicate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteFromTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    predicate: ").append(toIndentedString(predicate)).append("\n");
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

    return joiner.toString();
  }
}
