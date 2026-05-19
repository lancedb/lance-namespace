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

/** CreateMaterializedViewResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateMaterializedViewResponse {

  private Long version;

  private String jobId = null;

  public CreateMaterializedViewResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateMaterializedViewResponse(Long version) {
    this.version = version;
  }

  public CreateMaterializedViewResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version that created the materialized view minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "version",
      description = "The commit version that created the materialized view",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public CreateMaterializedViewResponse jobId(String jobId) {
    this.jobId = jobId;
    return this;
  }

  /**
   * Refresh job ID, populated only when `with_no_data` was false.
   *
   * @return jobId
   */
  @Schema(
      name = "job_id",
      description = "Refresh job ID, populated only when `with_no_data` was false. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("job_id")
  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateMaterializedViewResponse createMaterializedViewResponse =
        (CreateMaterializedViewResponse) o;
    return Objects.equals(this.version, createMaterializedViewResponse.version)
        && Objects.equals(this.jobId, createMaterializedViewResponse.jobId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, jobId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateMaterializedViewResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    jobId: ").append(toIndentedString(jobId)).append("\n");
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
