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

/** GetTableTagVersionResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class GetTableTagVersionResponse {

  private Long version;

  private String branch;

  public GetTableTagVersionResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public GetTableTagVersionResponse(Long version) {
    this.version = version;
  }

  public GetTableTagVersionResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * version number that the tag points to minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "version",
      description = "version number that the tag points to",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public GetTableTagVersionResponse branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Branch the tag's version lives on. Absent when the tag points to the main branch.
   *
   * @return branch
   */
  @Schema(
      name = "branch",
      description =
          "Branch the tag's version lives on. Absent when the tag points to the main branch. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branch")
  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetTableTagVersionResponse getTableTagVersionResponse = (GetTableTagVersionResponse) o;
    return Objects.equals(this.version, getTableTagVersionResponse.version)
        && Objects.equals(this.branch, getTableTagVersionResponse.branch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, branch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTableTagVersionResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
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
