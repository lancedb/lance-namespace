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
package com.lancedb.lance.namespace.client.apache.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateNamespaceRequest */
@JsonPropertyOrder({
  CreateNamespaceRequest.JSON_PROPERTY_NAME,
  CreateNamespaceRequest.JSON_PROPERTY_PARENT,
  CreateNamespaceRequest.JSON_PROPERTY_MODE,
  CreateNamespaceRequest.JSON_PROPERTY_OPTIONS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateNamespaceRequest {
  public static final String JSON_PROPERTY_NAME = "name";
  @javax.annotation.Nonnull private String name;

  public static final String JSON_PROPERTY_PARENT = "parent";
  @javax.annotation.Nullable private List<String> parent = new ArrayList<>();

  /** Gets or Sets mode */
  public enum ModeEnum {
    CREATE(String.valueOf("CREATE")),

    EXIST_OK(String.valueOf("EXIST_OK")),

    OVERWRITE(String.valueOf("OVERWRITE"));

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
  @javax.annotation.Nonnull private ModeEnum mode;

  public static final String JSON_PROPERTY_OPTIONS = "options";
  @javax.annotation.Nullable private Map<String, String> options = new HashMap<>();

  public CreateNamespaceRequest() {}

  public CreateNamespaceRequest name(@javax.annotation.Nonnull String name) {

    this.name = name;
    return this;
  }

  /**
   * Get name
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

  public CreateNamespaceRequest parent(@javax.annotation.Nullable List<String> parent) {

    this.parent = parent;
    return this;
  }

  public CreateNamespaceRequest addParentItem(String parentItem) {
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

  public CreateNamespaceRequest mode(@javax.annotation.Nonnull ModeEnum mode) {

    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   *
   * @return mode
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public ModeEnum getMode() {
    return mode;
  }

  @JsonProperty(JSON_PROPERTY_MODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMode(@javax.annotation.Nonnull ModeEnum mode) {
    this.mode = mode;
  }

  public CreateNamespaceRequest options(@javax.annotation.Nullable Map<String, String> options) {

    this.options = options;
    return this;
  }

  public CreateNamespaceRequest putOptionsItem(String key, String optionsItem) {
    if (this.options == null) {
      this.options = new HashMap<>();
    }
    this.options.put(key, optionsItem);
    return this;
  }

  /**
   * Get options
   *
   * @return options
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getOptions() {
    return options;
  }

  @JsonProperty(JSON_PROPERTY_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOptions(@javax.annotation.Nullable Map<String, String> options) {
    this.options = options;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateNamespaceRequest createNamespaceRequest = (CreateNamespaceRequest) o;
    return Objects.equals(this.name, createNamespaceRequest.name)
        && Objects.equals(this.parent, createNamespaceRequest.parent)
        && Objects.equals(this.mode, createNamespaceRequest.mode)
        && Objects.equals(this.options, createNamespaceRequest.options);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parent, mode, options);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateNamespaceRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
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

    // add `options` to the URL query string
    if (getOptions() != null) {
      for (String _key : getOptions().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%soptions%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getOptions().get(_key),
                  URLEncoder.encode(String.valueOf(getOptions().get(_key)), "UTF-8")
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
