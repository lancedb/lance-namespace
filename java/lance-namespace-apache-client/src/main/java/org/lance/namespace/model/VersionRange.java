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
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A range of versions to delete (start inclusive, end exclusive). Special values: -
 * &#x60;start_version: 0&#x60; with &#x60;end_version: -1&#x60; means ALL versions
 */
@JsonPropertyOrder({
  VersionRange.JSON_PROPERTY_START_VERSION,
  VersionRange.JSON_PROPERTY_END_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class VersionRange {
  public static final String JSON_PROPERTY_START_VERSION = "start_version";
  @javax.annotation.Nonnull private Long startVersion;

  public static final String JSON_PROPERTY_END_VERSION = "end_version";
  @javax.annotation.Nonnull private Long endVersion;

  public VersionRange() {}

  public VersionRange startVersion(@javax.annotation.Nonnull Long startVersion) {

    this.startVersion = startVersion;
    return this;
  }

  /**
   * Start version of the range (inclusive). Use 0 to start from the first version.
   *
   * @return startVersion
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_START_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getStartVersion() {
    return startVersion;
  }

  @JsonProperty(JSON_PROPERTY_START_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStartVersion(@javax.annotation.Nonnull Long startVersion) {
    this.startVersion = startVersion;
  }

  public VersionRange endVersion(@javax.annotation.Nonnull Long endVersion) {

    this.endVersion = endVersion;
    return this;
  }

  /**
   * End version of the range (exclusive). Use -1 to indicate all versions up to and including the
   * latest.
   *
   * @return endVersion
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_END_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getEndVersion() {
    return endVersion;
  }

  @JsonProperty(JSON_PROPERTY_END_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEndVersion(@javax.annotation.Nonnull Long endVersion) {
    this.endVersion = endVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VersionRange versionRange = (VersionRange) o;
    return Objects.equals(this.startVersion, versionRange.startVersion)
        && Objects.equals(this.endVersion, versionRange.endVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startVersion, endVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersionRange {\n");
    sb.append("    startVersion: ").append(toIndentedString(startVersion)).append("\n");
    sb.append("    endVersion: ").append(toIndentedString(endVersion)).append("\n");
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

    // add `start_version` to the URL query string
    if (getStartVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sstart_version%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getStartVersion()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `end_version` to the URL query string
    if (getEndVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%send_version%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getEndVersion()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
