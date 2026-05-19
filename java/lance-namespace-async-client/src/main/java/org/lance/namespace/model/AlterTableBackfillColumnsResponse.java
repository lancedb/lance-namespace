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

import org.lance.namespace.client.async.ApiClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.StringJoiner;

/** AlterTableBackfillColumnsResponse */
@JsonPropertyOrder({AlterTableBackfillColumnsResponse.JSON_PROPERTY_JOB_ID})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableBackfillColumnsResponse {
  public static final String JSON_PROPERTY_JOB_ID = "job_id";
  @javax.annotation.Nonnull private String jobId;

  public AlterTableBackfillColumnsResponse() {}

  public AlterTableBackfillColumnsResponse jobId(@javax.annotation.Nonnull String jobId) {
    this.jobId = jobId;
    return this;
  }

  /**
   * The job ID for tracking the backfill job
   *
   * @return jobId
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_JOB_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getJobId() {
    return jobId;
  }

  @JsonProperty(JSON_PROPERTY_JOB_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setJobId(@javax.annotation.Nonnull String jobId) {
    this.jobId = jobId;
  }

  /** Return true if this AlterTableBackfillColumnsResponse object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableBackfillColumnsResponse alterTableBackfillColumnsResponse =
        (AlterTableBackfillColumnsResponse) o;
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

    // add `job_id` to the URL query string
    if (getJobId() != null) {
      joiner.add(
          String.format(
              "%sjob_id%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getJobId()))));
    }

    return joiner.toString();
  }
}
