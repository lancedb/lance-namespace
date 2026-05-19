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
 * AlterTransactionSetStatus
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AlterTransactionSetStatus {

  private String status;

  public AlterTransactionSetStatus status(String status) {
    this.status = status;
    return this;
  }

  /**
   * The status of a transaction. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Queued: the transaction is queued and not yet started - Running: the transaction is currently running - Succeeded: the transaction has completed successfully - Failed: the transaction has failed - Canceled: the transaction was canceled 
   * @return status
   */
  
  @Schema(name = "status", description = "The status of a transaction. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Queued: the transaction is queued and not yet started - Running: the transaction is currently running - Succeeded: the transaction has completed successfully - Failed: the transaction has failed - Canceled: the transaction was canceled ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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

