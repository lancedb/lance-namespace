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

/**
 * A range of versions to delete (start inclusive, end exclusive). Special values: -
 * &#x60;start_version: 0&#x60; with &#x60;end_version: -1&#x60; means ALL versions
 */
@Schema(
    name = "VersionRange",
    description =
        "A range of versions to delete (start inclusive, end exclusive). Special values: - `start_version: 0` with `end_version: -1` means ALL versions ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class VersionRange {

  private Long startVersion;

  private Long endVersion;

  public VersionRange() {
    super();
  }

  /** Constructor with only required parameters */
  public VersionRange(Long startVersion, Long endVersion) {
    this.startVersion = startVersion;
    this.endVersion = endVersion;
  }

  public VersionRange startVersion(Long startVersion) {
    this.startVersion = startVersion;
    return this;
  }

  /**
   * Start version of the range (inclusive). Use 0 to start from the first version.
   *
   * @return startVersion
   */
  @NotNull
  @Schema(
      name = "start_version",
      description =
          "Start version of the range (inclusive). Use 0 to start from the first version. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("start_version")
  public Long getStartVersion() {
    return startVersion;
  }

  public void setStartVersion(Long startVersion) {
    this.startVersion = startVersion;
  }

  public VersionRange endVersion(Long endVersion) {
    this.endVersion = endVersion;
    return this;
  }

  /**
   * End version of the range (exclusive). Use -1 to indicate all versions up to and including the
   * latest.
   *
   * @return endVersion
   */
  @NotNull
  @Schema(
      name = "end_version",
      description =
          "End version of the range (exclusive). Use -1 to indicate all versions up to and including the latest. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("end_version")
  public Long getEndVersion() {
    return endVersion;
  }

  public void setEndVersion(Long endVersion) {
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
}
