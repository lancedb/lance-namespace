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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateMaterializedViewResponse */
@JsonPropertyOrder({
  CreateMaterializedViewResponse.JSON_PROPERTY_VERSION,
  CreateMaterializedViewResponse.JSON_PROPERTY_JOB_ID
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateMaterializedViewResponse {
  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nonnull private Long version;

  public static final String JSON_PROPERTY_JOB_ID = "job_id";
  private JsonNullable<String> jobId = JsonNullable.<String>undefined();

  public CreateMaterializedViewResponse() {}

  public CreateMaterializedViewResponse version(@javax.annotation.Nonnull Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version that created the materialized view minimum: 0
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

  public CreateMaterializedViewResponse jobId(@javax.annotation.Nullable String jobId) {
    this.jobId = JsonNullable.<String>of(jobId);
    return this;
  }

  /**
   * Refresh job ID, populated only when &#x60;with_no_data&#x60; was false.
   *
   * @return jobId
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public String getJobId() {
    return jobId.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_JOB_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<String> getJobId_JsonNullable() {
    return jobId;
  }

  @JsonProperty(JSON_PROPERTY_JOB_ID)
  public void setJobId_JsonNullable(JsonNullable<String> jobId) {
    this.jobId = jobId;
  }

  public void setJobId(@javax.annotation.Nullable String jobId) {
    this.jobId = JsonNullable.<String>of(jobId);
  }

  /** Return true if this CreateMaterializedViewResponse object is equal to o. */
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
        && equalsNullable(this.jobId, createMaterializedViewResponse.jobId);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, hashCodeNullable(jobId));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
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

    // add `version` to the URL query string
    if (getVersion() != null) {
      joiner.add(
          String.format(
              "%sversion%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getVersion()))));
    }

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
