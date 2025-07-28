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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** Common JSON error response model */
@Schema(name = "ErrorResponse", description = "Common JSON error response model")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ErrorResponse {

  private String error;

  private Integer code;

  private String type;

  private String detail;

  private String instance;

  public ErrorResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * a brief, human-readable message about the error
   *
   * @return error
   */
  @Schema(
      name = "error",
      example = "Incorrect username or password",
      description = "a brief, human-readable message about the error",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public ErrorResponse code(Integer code) {
    this.code = code;
    return this;
  }

  /**
   * HTTP style response code, where 4XX represents client side errors and 5XX represents server
   * side errors. For implementations that uses HTTP (e.g. REST namespace), this field can be
   * optional in favor of the HTTP response status code. In case both values exist and do not match,
   * the HTTP response status code should be used. minimum: 400 maximum: 600
   *
   * @return code
   */
  @Min(400)
  @Max(600)
  @Schema(
      name = "code",
      example = "404",
      description =
          "HTTP style response code, where 4XX represents client side errors  and 5XX represents server side errors.  For implementations that uses HTTP (e.g. REST namespace), this field can be optional in favor of the HTTP response status code. In case both values exist and do not match, the HTTP response status code should be used. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ErrorResponse type(String type) {
    this.type = type;
    return this;
  }

  /**
   * An optional type identifier string for the error. This allows the implementation to specify
   * their internal error type, which could be more detailed than the HTTP standard status code.
   *
   * @return type
   */
  @Schema(
      name = "type",
      example = "/errors/incorrect-user-pass",
      description =
          "An optional type identifier string for the error. This allows the implementation to specify their internal error type, which could be more detailed than the HTTP standard status code. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ErrorResponse detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * an optional human-readable explanation of the error. This can be used to record information
   * such as stack trace.
   *
   * @return detail
   */
  @Schema(
      name = "detail",
      example = "Authentication failed due to incorrect username or password",
      description =
          "an optional human-readable explanation of the error. This can be used to record information such as stack trace. ",
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
   * a string that identifies the specific occurrence of the error. This can be a URI, a request or
   * response ID, or anything that the implementation can recognize to trace specific occurrence of
   * the error.
   *
   * @return instance
   */
  @Schema(
      name = "instance",
      example = "/login/log/abc123",
      description =
          "a string that identifies the specific occurrence of the error. This can be a URI, a request or response ID,  or anything that the implementation can recognize to trace specific occurrence of the error. ",
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
    return Objects.equals(this.error, errorResponse.error)
        && Objects.equals(this.code, errorResponse.code)
        && Objects.equals(this.type, errorResponse.type)
        && Objects.equals(this.detail, errorResponse.detail)
        && Objects.equals(this.instance, errorResponse.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code, type, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
