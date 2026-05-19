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

/** TagContents */
@JsonPropertyOrder({
  TagContents.JSON_PROPERTY_BRANCH,
  TagContents.JSON_PROPERTY_VERSION,
  TagContents.JSON_PROPERTY_MANIFEST_SIZE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class TagContents {
  public static final String JSON_PROPERTY_BRANCH = "branch";
  @javax.annotation.Nullable private String branch;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nonnull private Long version;

  public static final String JSON_PROPERTY_MANIFEST_SIZE = "manifestSize";
  @javax.annotation.Nonnull private Long manifestSize;

  public TagContents() {}

  public TagContents branch(@javax.annotation.Nullable String branch) {

    this.branch = branch;
    return this;
  }

  /**
   * Branch name that the tag was created on (if any)
   *
   * @return branch
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BRANCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getBranch() {
    return branch;
  }

  @JsonProperty(JSON_PROPERTY_BRANCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBranch(@javax.annotation.Nullable String branch) {
    this.branch = branch;
  }

  public TagContents version(@javax.annotation.Nonnull Long version) {

    this.version = version;
    return this;
  }

  /**
   * Version number that the tag points to minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(@javax.annotation.Nonnull Long version) {
    this.version = version;
  }

  public TagContents manifestSize(@javax.annotation.Nonnull Long manifestSize) {

    this.manifestSize = manifestSize;
    return this;
  }

  /**
   * Size of the manifest file in bytes minimum: 0
   *
   * @return manifestSize
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MANIFEST_SIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getManifestSize() {
    return manifestSize;
  }

  @JsonProperty(JSON_PROPERTY_MANIFEST_SIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setManifestSize(@javax.annotation.Nonnull Long manifestSize) {
    this.manifestSize = manifestSize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TagContents tagContents = (TagContents) o;
    return Objects.equals(this.branch, tagContents.branch)
        && Objects.equals(this.version, tagContents.version)
        && Objects.equals(this.manifestSize, tagContents.manifestSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(branch, version, manifestSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TagContents {\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    manifestSize: ").append(toIndentedString(manifestSize)).append("\n");
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

    // add `branch` to the URL query string
    if (getBranch() != null) {
      try {
        joiner.add(
            String.format(
                "%sbranch%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getBranch()), "UTF-8").replaceAll("\\+", "%20")));
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

    // add `manifestSize` to the URL query string
    if (getManifestSize() != null) {
      try {
        joiner.add(
            String.format(
                "%smanifestSize%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getManifestSize()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
