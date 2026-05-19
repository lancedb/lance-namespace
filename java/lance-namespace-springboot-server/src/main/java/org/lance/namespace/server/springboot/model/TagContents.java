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
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** TagContents */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class TagContents {

  private String branch;

  private Long version;

  private Long manifestSize;

  public TagContents() {
    super();
  }

  /** Constructor with only required parameters */
  public TagContents(Long version, Long manifestSize) {
    this.version = version;
    this.manifestSize = manifestSize;
  }

  public TagContents branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Branch name that the tag was created on (if any)
   *
   * @return branch
   */
  @Schema(
      name = "branch",
      description = "Branch name that the tag was created on (if any)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branch")
  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public TagContents version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Version number that the tag points to minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "version",
      description = "Version number that the tag points to",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public TagContents manifestSize(Long manifestSize) {
    this.manifestSize = manifestSize;
    return this;
  }

  /**
   * Size of the manifest file in bytes minimum: 0
   *
   * @return manifestSize
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "manifestSize",
      description = "Size of the manifest file in bytes",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("manifestSize")
  public Long getManifestSize() {
    return manifestSize;
  }

  public void setManifestSize(Long manifestSize) {
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
}
