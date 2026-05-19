package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AlterTableBackfillColumnsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AlterTableBackfillColumnsResponse {

  private String jobId;

  public AlterTableBackfillColumnsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AlterTableBackfillColumnsResponse(String jobId) {
    this.jobId = jobId;
  }

  public AlterTableBackfillColumnsResponse jobId(String jobId) {
    this.jobId = jobId;
    return this;
  }

  /**
   * The job ID for tracking the backfill job
   * @return jobId
   */
  @NotNull 
  @Schema(name = "job_id", description = "The job ID for tracking the backfill job", requiredMode = Schema.RequiredMode.REQUIRED)
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
    AlterTableBackfillColumnsResponse alterTableBackfillColumnsResponse = (AlterTableBackfillColumnsResponse) o;
    return Objects.equals(this.jobId, alterTableBackfillColumnsResponse.jobId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jobId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableBackfillColumnsResponse {\n");
    sb.append("    jobId: ").append(toIndentedString(jobId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

