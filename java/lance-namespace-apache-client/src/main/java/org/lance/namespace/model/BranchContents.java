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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** BranchContents */
@JsonPropertyOrder({
  BranchContents.JSON_PROPERTY_PARENT_BRANCH,
  BranchContents.JSON_PROPERTY_PARENT_VERSION,
  BranchContents.JSON_PROPERTY_CREATE_AT,
  BranchContents.JSON_PROPERTY_MANIFEST_SIZE,
  BranchContents.JSON_PROPERTY_METADATA
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BranchContents {
  public static final String JSON_PROPERTY_PARENT_BRANCH = "parentBranch";
  @javax.annotation.Nullable private String parentBranch;

  public static final String JSON_PROPERTY_PARENT_VERSION = "parentVersion";
  @javax.annotation.Nonnull private Long parentVersion;

  public static final String JSON_PROPERTY_CREATE_AT = "createAt";
  @javax.annotation.Nonnull private Long createAt;

  public static final String JSON_PROPERTY_MANIFEST_SIZE = "manifestSize";
  @javax.annotation.Nonnull private Long manifestSize;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nullable private Map<String, String> metadata = new HashMap<>();

  public BranchContents() {}

  public BranchContents parentBranch(@javax.annotation.Nullable String parentBranch) {

    this.parentBranch = parentBranch;
    return this;
  }

  /**
   * Name of the branch this branch was created from. Absent when the branch was created from the
   * main branch.
   *
   * @return parentBranch
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PARENT_BRANCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getParentBranch() {
    return parentBranch;
  }

  @JsonProperty(JSON_PROPERTY_PARENT_BRANCH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setParentBranch(@javax.annotation.Nullable String parentBranch) {
    this.parentBranch = parentBranch;
  }

  public BranchContents parentVersion(@javax.annotation.Nonnull Long parentVersion) {

    this.parentVersion = parentVersion;
    return this;
  }

  /**
   * Version of the parent (branch or main) this branch was created from minimum: 0
   *
   * @return parentVersion
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PARENT_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getParentVersion() {
    return parentVersion;
  }

  @JsonProperty(JSON_PROPERTY_PARENT_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setParentVersion(@javax.annotation.Nonnull Long parentVersion) {
    this.parentVersion = parentVersion;
  }

  public BranchContents createAt(@javax.annotation.Nonnull Long createAt) {

    this.createAt = createAt;
    return this;
  }

  /**
   * Unix timestamp (in seconds) when the branch was created minimum: 0
   *
   * @return createAt
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CREATE_AT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Long getCreateAt() {
    return createAt;
  }

  @JsonProperty(JSON_PROPERTY_CREATE_AT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCreateAt(@javax.annotation.Nonnull Long createAt) {
    this.createAt = createAt;
  }

  public BranchContents manifestSize(@javax.annotation.Nonnull Long manifestSize) {

    this.manifestSize = manifestSize;
    return this;
  }

  /**
   * Size of the branch&#39;s manifest file in bytes minimum: 0
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

  public BranchContents metadata(@javax.annotation.Nullable Map<String, String> metadata) {

    this.metadata = metadata;
    return this;
  }

  public BranchContents putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Key-value metadata associated with the branch
   *
   * @return metadata
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getMetadata() {
    return metadata;
  }

  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetadata(@javax.annotation.Nullable Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BranchContents branchContents = (BranchContents) o;
    return Objects.equals(this.parentBranch, branchContents.parentBranch)
        && Objects.equals(this.parentVersion, branchContents.parentVersion)
        && Objects.equals(this.createAt, branchContents.createAt)
        && Objects.equals(this.manifestSize, branchContents.manifestSize)
        && Objects.equals(this.metadata, branchContents.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parentBranch, parentVersion, createAt, manifestSize, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BranchContents {\n");
    sb.append("    parentBranch: ").append(toIndentedString(parentBranch)).append("\n");
    sb.append("    parentVersion: ").append(toIndentedString(parentVersion)).append("\n");
    sb.append("    createAt: ").append(toIndentedString(createAt)).append("\n");
    sb.append("    manifestSize: ").append(toIndentedString(manifestSize)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

    // add `parentBranch` to the URL query string
    if (getParentBranch() != null) {
      try {
        joiner.add(
            String.format(
                "%sparentBranch%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getParentBranch()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `parentVersion` to the URL query string
    if (getParentVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sparentVersion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getParentVersion()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `createAt` to the URL query string
    if (getCreateAt() != null) {
      try {
        joiner.add(
            String.format(
                "%screateAt%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getCreateAt()), "UTF-8")
                    .replaceAll("\\+", "%20")));
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

    // add `metadata` to the URL query string
    if (getMetadata() != null) {
      for (String _key : getMetadata().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%smetadata%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getMetadata().get(_key),
                  URLEncoder.encode(String.valueOf(getMetadata().get(_key)), "UTF-8")
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
