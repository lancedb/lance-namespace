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

/** AlterTransactionSetStatus */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionSetStatus {

  private String status;

  public AlterTransactionSetStatus status(String status) {
    this.status = status;
    return this;
  }

  /**
   * The status of a transaction. Case insensitive. Valid values are: - QUEUED: the transaction is
   * queued and not yet started - RUNNING: the transaction is currently running - SUCCEEDED: the
   * transaction has completed successfully - FAILED: the transaction has failed - CANCELED: the
   * transaction was canceled
   *
   * @return status
   */
  @Schema(
      name = "status",
      description =
          "The status of a transaction. Case insensitive. Valid values are: - QUEUED: the transaction is queued and not yet started - RUNNING: the transaction is currently running - SUCCEEDED: the transaction has completed successfully - FAILED: the transaction has failed - CANCELED: the transaction was canceled ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionSetStatus alterTransactionSetStatus = (AlterTransactionSetStatus) o;
    return Objects.equals(this.status, alterTransactionSetStatus.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionSetStatus {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
