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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** BranchContents */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class BranchContents {

  private String parentBranch;

  private Long parentVersion;

  private Long createAt;

  private Long manifestSize;

  @Valid private Map<String, String> metadata = new HashMap<>();

  public BranchContents() {
    super();
  }

  /** Constructor with only required parameters */
  public BranchContents(Long parentVersion, Long createAt, Long manifestSize) {
    this.parentVersion = parentVersion;
    this.createAt = createAt;
    this.manifestSize = manifestSize;
  }

  public BranchContents parentBranch(String parentBranch) {
    this.parentBranch = parentBranch;
    return this;
  }

  /**
   * Name of the branch this branch was created from. Absent when the branch was created from the
   * main branch.
   *
   * @return parentBranch
   */
  @Schema(
      name = "parentBranch",
      description =
          "Name of the branch this branch was created from. Absent when the branch was created from the main branch. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parentBranch")
  public String getParentBranch() {
    return parentBranch;
  }

  public void setParentBranch(String parentBranch) {
    this.parentBranch = parentBranch;
  }

  public BranchContents parentVersion(Long parentVersion) {
    this.parentVersion = parentVersion;
    return this;
  }

  /**
   * Version of the parent (branch or main) this branch was created from minimum: 0
   *
   * @return parentVersion
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "parentVersion",
      description = "Version of the parent (branch or main) this branch was created from",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("parentVersion")
  public Long getParentVersion() {
    return parentVersion;
  }

  public void setParentVersion(Long parentVersion) {
    this.parentVersion = parentVersion;
  }

  public BranchContents createAt(Long createAt) {
    this.createAt = createAt;
    return this;
  }

  /**
   * Unix timestamp (in seconds) when the branch was created minimum: 0
   *
   * @return createAt
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "createAt",
      description = "Unix timestamp (in seconds) when the branch was created",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createAt")
  public Long getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Long createAt) {
    this.createAt = createAt;
  }

  public BranchContents manifestSize(Long manifestSize) {
    this.manifestSize = manifestSize;
    return this;
  }

  /**
   * Size of the branch's manifest file in bytes minimum: 0
   *
   * @return manifestSize
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "manifestSize",
      description = "Size of the branch's manifest file in bytes",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("manifestSize")
  public Long getManifestSize() {
    return manifestSize;
  }

  public void setManifestSize(Long manifestSize) {
    this.manifestSize = manifestSize;
  }

  public BranchContents metadata(Map<String, String> metadata) {
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
  @Schema(
      name = "metadata",
      description = "Key-value metadata associated with the branch",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
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
}
