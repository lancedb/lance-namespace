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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** DropNamespaceRequest */
@JsonPropertyOrder({
  DropNamespaceRequest.JSON_PROPERTY_NAME,
  DropNamespaceRequest.JSON_PROPERTY_PARENT,
  DropNamespaceRequest.JSON_PROPERTY_MODE,
  DropNamespaceRequest.JSON_PROPERTY_BEHAVIOR
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DropNamespaceRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nullable private String name;

  public static final String JSON_PROPERTY_PARENT = "parent";
  @javax.annotation.Nullable private List<String> parent = new ArrayList<>();

  /**
   * The mode for dropping a namespace, deciding the server behavior when the namespace to drop is
   * not found. - FAIL (default): the server must return 400 indicating the namespace to drop does
   * not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded.
   */
  public enum ModeEnum {
    SKIP(String.valueOf("SKIP")),

    FAIL(String.valueOf("FAIL"));

    private String value;

    ModeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ModeEnum fromValue(String value) {
      for (ModeEnum b : ModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_MODE = "mode";
  @javax.annotation.Nullable private ModeEnum mode;

  /**
   * The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain
   * any table or child namespace when drop is initiated. If tables are found, the server should
   * return error and not drop the namespace. - CASCADE: all tables and child namespaces in the
   * namespace are dropped before the namespace is dropped.
   */
  public enum BehaviorEnum {
    RESTRICT(String.valueOf("RESTRICT")),

    CASCADE(String.valueOf("CASCADE"));

    private String value;

    BehaviorEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static BehaviorEnum fromValue(String value) {
      for (BehaviorEnum b : BehaviorEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_BEHAVIOR = "behavior";
  @javax.annotation.Nullable private BehaviorEnum behavior;

  public DropNamespaceRequest() {}

  public DropNamespaceRequest name(@javax.annotation.Nullable String name) {

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

  public DropNamespaceRequest parent(@javax.annotation.Nullable List<String> parent) {

    this.parent = parent;
    return this;
  }

  public DropNamespaceRequest addParentItem(String parentItem) {
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

  public DropNamespaceRequest mode(@javax.annotation.Nullable ModeEnum mode) {

    this.mode = mode;
    return this;
  }

  /**
   * The mode for dropping a namespace, deciding the server behavior when the namespace to drop is
   * not found. - FAIL (default): the server must return 400 indicating the namespace to drop does
   * not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded.
   *
   * @return mode
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public ModeEnum getMode() {
    return mode;
  }

  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMode(@javax.annotation.Nullable ModeEnum mode) {
    this.mode = mode;
  }

  public DropNamespaceRequest behavior(@javax.annotation.Nullable BehaviorEnum behavior) {

    this.behavior = behavior;
    return this;
  }

  /**
   * The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain
   * any table or child namespace when drop is initiated. If tables are found, the server should
   * return error and not drop the namespace. - CASCADE: all tables and child namespaces in the
   * namespace are dropped before the namespace is dropped.
   *
   * @return behavior
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BEHAVIOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public BehaviorEnum getBehavior() {
    return behavior;
  }

  @JsonProperty(JSON_PROPERTY_BEHAVIOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBehavior(@javax.annotation.Nullable BehaviorEnum behavior) {
    this.behavior = behavior;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DropNamespaceRequest dropNamespaceRequest = (DropNamespaceRequest) o;
    return Objects.equals(this.name, dropNamespaceRequest.name)
        && Objects.equals(this.parent, dropNamespaceRequest.parent)
        && Objects.equals(this.mode, dropNamespaceRequest.mode)
        && Objects.equals(this.behavior, dropNamespaceRequest.behavior);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent, mode, behavior);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DropNamespaceRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    behavior: ").append(toIndentedString(behavior)).append("\n");
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

    // add `mode` to the URL query string
    if (getMode() != null) {
      try {
        joiner.add(
            String.format(
                "%smode%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getMode()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `behavior` to the URL query string
    if (getBehavior() != null) {
      try {
        joiner.add(
            String.format(
                "%sbehavior%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getBehavior()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
