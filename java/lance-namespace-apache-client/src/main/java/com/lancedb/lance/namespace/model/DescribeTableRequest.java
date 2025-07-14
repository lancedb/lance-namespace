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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** DescribeTableRequest */
@JsonPropertyOrder({
  DescribeTableRequest.JSON_PROPERTY_NAME,
  DescribeTableRequest.JSON_PROPERTY_NAMESPACE,
  DescribeTableRequest.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nullable private String name;

  public static final String JSON_PROPERTY_NAMESPACE = "namespace";
  @javax.annotation.Nullable private List<String> namespace = new ArrayList<>();

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private JsonNullable<Long> version = JsonNullable.<Long>undefined();

  public DescribeTableRequest() {}

  public DescribeTableRequest name(@javax.annotation.Nullable String name) {

    this.name = name;
    return this;
  }

  /**
   * Get name
   *
   * @return name
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getName() {
    return name;
  }

  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(@javax.annotation.Nullable String name) {
    this.name = name;
  }

  public DescribeTableRequest namespace(@javax.annotation.Nullable List<String> namespace) {

    this.namespace = namespace;
    return this;
  }

  public DescribeTableRequest addNamespaceItem(String namespaceItem) {
    if (this.namespace == null) {
      this.namespace = new ArrayList<>();
    }
    this.namespace.add(namespaceItem);
    return this;
  }

  /**
   * Get namespace
   *
   * @return namespace
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getNamespace() {
    return namespace;
  }

  @JsonProperty(JSON_PROPERTY_NAMESPACE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNamespace(@javax.annotation.Nullable List<String> namespace) {
    this.namespace = namespace;
  }

  public DescribeTableRequest version(@javax.annotation.Nullable Long version) {
    this.version = JsonNullable.<Long>of(version);

    return this;
  }

  /**
   * Get version minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Long getVersion() {
    return version.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Long> getVersion_JsonNullable() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  public void setVersion_JsonNullable(JsonNullable<Long> version) {
    this.version = version;
  }

  public void setVersion(@javax.annotation.Nullable Long version) {
    this.version = JsonNullable.<Long>of(version);
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
    return Objects.equals(this.name, describeTableRequest.name)
        && Objects.equals(this.namespace, describeTableRequest.namespace)
        && equalsNullable(this.version, describeTableRequest.version);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, namespace, hashCodeNullable(version));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

    // add `version` to the URL query string
    if (getVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sversion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVersion()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
