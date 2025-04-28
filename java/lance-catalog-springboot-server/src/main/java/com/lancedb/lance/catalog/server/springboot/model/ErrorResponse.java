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
package com.lancedb.lance.catalog.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807) */
@Schema(
    name = "ErrorResponse",
    description =
        "JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807)")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.13.0")
public class ErrorResponse {

  private String type;

  private String title;

  private Integer status;

  private String detail;

  private String instance;

  public ErrorResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public ErrorResponse(String type) {
    this.type = type;
  }

  public ErrorResponse type(String type) {
    this.type = type;
    return this;
  }

  /**
   * a URI identifier that categorizes the error
   *
   * @return type
   */
  @NotNull
  @Schema(
      name = "type",
      example = "/errors/incorrect-user-pass",
      description = "a URI identifier that categorizes the error",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ErrorResponse title(String title) {
    this.title = title;
    return this;
  }

  /**
   * a brief, human-readable message about the error
   *
   * @return title
   */
  @Schema(
      name = "title",
      example = "Incorrect username or password",
      description = "a brief, human-readable message about the error",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ErrorResponse status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * HTTP response code, (if present) it must match the actual HTTP code returned by the service
   * minimum: 400 maximum: 600
   *
   * @return status
   */
  @Min(400)
  @Max(600)
  @Schema(
      name = "status",
      example = "404",
      description =
          "HTTP response code, (if present) it must match the actual HTTP code returned by the service",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public ErrorResponse detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * a human-readable explanation of the error
   *
   * @return detail
   */
  @Schema(
      name = "detail",
      example = "Authentication failed due to incorrect username or password",
      description = "a human-readable explanation of the error",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("detail")
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public ErrorResponse instance(String instance) {
    this.instance = instance;
    return this;
  }

  /**
   * a URI that identifies the specific occurrence of the error
   *
   * @return instance
   */
  @Schema(
      name = "instance",
      example = "/login/log/abc123",
      description = "a URI that identifies the specific occurrence of the error",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("instance")
  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.type, errorResponse.type)
        && Objects.equals(this.title, errorResponse.title)
        && Objects.equals(this.status, errorResponse.status)
        && Objects.equals(this.detail, errorResponse.detail)
        && Objects.equals(this.instance, errorResponse.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, title, status, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
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
