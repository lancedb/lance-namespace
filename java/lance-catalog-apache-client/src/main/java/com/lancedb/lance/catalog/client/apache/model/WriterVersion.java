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
package com.lancedb.lance.catalog.client.apache.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.StringJoiner;

/** WriterVersion */
@JsonPropertyOrder({WriterVersion.JSON_PROPERTY_LIBRARY, WriterVersion.JSON_PROPERTY_VERSION})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class WriterVersion {
  public static final String JSON_PROPERTY_LIBRARY = "library";
  @javax.annotation.Nullable private String library;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private String version;

  public WriterVersion() {}

  public WriterVersion library(@javax.annotation.Nullable String library) {

    this.library = library;
    return this;
  }

  /**
   * The name of the library that created this file.
   *
   * @return library
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LIBRARY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getLibrary() {
    return library;
  }

  @JsonProperty(JSON_PROPERTY_LIBRARY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLibrary(@javax.annotation.Nullable String library) {
    this.library = library;
  }

  public WriterVersion version(@javax.annotation.Nullable String version) {

    this.version = version;
    return this;
  }

  /**
   * The version of the library that created this file. Because we cannot assume that the library is
   * semantically versioned, this is a string. However, if it is semantically versioned, it should
   * be a valid semver string without any &#39;v&#39; prefix. For example: &#x60;2.0.0&#x60;,
   * &#x60;2.0.0-rc.1&#x60;.
   *
   * @return version
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(@javax.annotation.Nullable String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WriterVersion writerVersion = (WriterVersion) o;
    return Objects.equals(this.library, writerVersion.library)
        && Objects.equals(this.version, writerVersion.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(library, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WriterVersion {\n");
    sb.append("    library: ").append(toIndentedString(library)).append("\n");
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

    // add `library` to the URL query string
    if (getLibrary() != null) {
      try {
        joiner.add(
            String.format(
                "%slibrary%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLibrary()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
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
