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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** DropNamespaceResponse */
@JsonPropertyOrder({
  DropNamespaceResponse.JSON_PROPERTY_NAME,
  DropNamespaceResponse.JSON_PROPERTY_PARENT,
  DropNamespaceResponse.JSON_PROPERTY_PROPERTIES
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DropNamespaceResponse {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nullable private String name;

  public static final String JSON_PROPERTY_PARENT = "parent";
  @javax.annotation.Nullable private List<String> parent = new ArrayList<>();

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public DropNamespaceResponse() {}

  public DropNamespaceResponse name(@javax.annotation.Nullable String name) {

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

  public DropNamespaceResponse parent(@javax.annotation.Nullable List<String> parent) {

    this.parent = parent;
    return this;
  }

  public DropNamespaceResponse addParentItem(String parentItem) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PARENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getParent() {
    return parent;
  }

  @JsonProperty(JSON_PROPERTY_PARENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setParent(@javax.annotation.Nullable List<String> parent) {
    this.parent = parent;
  }

  public DropNamespaceResponse properties(
      @javax.annotation.Nullable Map<String, String> properties) {

    this.properties = properties;
    return this;
  }

  public DropNamespaceResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   *
   * @return properties
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getProperties() {
    return properties;
  }

  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(@javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DropNamespaceResponse dropNamespaceResponse = (DropNamespaceResponse) o;
    return Objects.equals(this.name, dropNamespaceResponse.name)
        && Objects.equals(this.parent, dropNamespaceResponse.parent)
        && Objects.equals(this.properties, dropNamespaceResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DropNamespaceResponse {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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

    // add `parent` to the URL query string
    if (getParent() != null) {
      for (int i = 0; i < getParent().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sparent%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getParent().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sproperties%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getProperties().get(_key),
                  URLEncoder.encode(String.valueOf(getProperties().get(_key)), "UTF-8")
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
