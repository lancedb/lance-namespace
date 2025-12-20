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

/** Common JSON error response model */
@Schema(name = "ErrorResponse", description = "Common JSON error response model")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ErrorResponse {

  private String error;

  private Integer code;

  private String detail;

  private String instance;

  public ErrorResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public ErrorResponse(Integer code) {
    this.code = code;
  }

  public ErrorResponse error(String error) {
    this.error = error;
    return this;
  }

  /**
   * A brief, human-readable message about the error.
   *
   * @return error
   */
  @Schema(
      name = "error",
      example = "Table 'users' not found in namespace 'production'",
      description = "A brief, human-readable message about the error.",
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
   * Lance Namespace error code identifying the error type. Error codes: 0 - Unsupported: Operation
   * not supported by this backend 1 - NamespaceNotFound: The specified namespace does not exist 2 -
   * NamespaceAlreadyExists: A namespace with this name already exists 3 - NamespaceNotEmpty:
   * Namespace contains tables or child namespaces 4 - TableNotFound: The specified table does not
   * exist 5 - TableAlreadyExists: A table with this name already exists 6 - TableIndexNotFound: The
   * specified table index does not exist 7 - TableIndexAlreadyExists: A table index with this name
   * already exists 8 - TableTagNotFound: The specified table tag does not exist 9 -
   * TableTagAlreadyExists: A table tag with this name already exists 10 - TransactionNotFound: The
   * specified transaction does not exist 11 - TableVersionNotFound: The specified table version
   * does not exist 12 - TableColumnNotFound: The specified table column does not exist 13 -
   * InvalidInput: Malformed request or invalid parameters 14 - ConcurrentModification: Optimistic
   * concurrency conflict 15 - PermissionDenied: User lacks permission for this operation 16 -
   * Unauthenticated: Authentication credentials are missing or invalid 17 - ServiceUnavailable:
   * Service is temporarily unavailable 18 - Internal: Unexpected server/implementation error 19 -
   * InvalidTableState: Table is in an invalid state for the operation 20 -
   * TableSchemaValidationError: Table schema validation failed minimum: 0
   *
   * @return code
   */
  @NotNull
  @Min(0)
  @Schema(
      name = "code",
      example = "4",
      description =
          "Lance Namespace error code identifying the error type.  Error codes:   0 - Unsupported: Operation not supported by this backend   1 - NamespaceNotFound: The specified namespace does not exist   2 - NamespaceAlreadyExists: A namespace with this name already exists   3 - NamespaceNotEmpty: Namespace contains tables or child namespaces   4 - TableNotFound: The specified table does not exist   5 - TableAlreadyExists: A table with this name already exists   6 - TableIndexNotFound: The specified table index does not exist   7 - TableIndexAlreadyExists: A table index with this name already exists   8 - TableTagNotFound: The specified table tag does not exist   9 - TableTagAlreadyExists: A table tag with this name already exists   10 - TransactionNotFound: The specified transaction does not exist   11 - TableVersionNotFound: The specified table version does not exist   12 - TableColumnNotFound: The specified table column does not exist   13 - InvalidInput: Malformed request or invalid parameters   14 - ConcurrentModification: Optimistic concurrency conflict   15 - PermissionDenied: User lacks permission for this operation   16 - Unauthenticated: Authentication credentials are missing or invalid   17 - ServiceUnavailable: Service is temporarily unavailable   18 - Internal: Unexpected server/implementation error   19 - InvalidTableState: Table is in an invalid state for the operation   20 - TableSchemaValidationError: Table schema validation failed ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ErrorResponse detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * An optional human-readable explanation of the error. This can be used to record additional
   * information such as stack trace.
   *
   * @return detail
   */
  @Schema(
      name = "detail",
      example = "The table may have been dropped or renamed",
      description =
          "An optional human-readable explanation of the error. This can be used to record additional information such as stack trace. ",
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
   * A string that identifies the specific occurrence of the error. This can be a URI, a request or
   * response ID, or anything that the implementation can recognize to trace specific occurrence of
   * the error.
   *
   * @return instance
   */
  @Schema(
      name = "instance",
      example = "/v1/table/production$users/describe",
      description =
          "A string that identifies the specific occurrence of the error. This can be a URI, a request or response ID, or anything that the implementation can recognize to trace specific occurrence of the error. ",
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
        && Objects.equals(this.detail, errorResponse.detail)
        && Objects.equals(this.instance, errorResponse.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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
