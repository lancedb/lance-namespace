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
 * DeleteFromTableResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class DeleteFromTableResponse {

  private String transactionId;

  private Long version;

  public DeleteFromTableResponse transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier
   * @return transactionId
   */
  
  @Schema(name = "transaction_id", description = "Optional transaction identifier", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("transaction_id")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public DeleteFromTableResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation
   * minimum: 0
   * @return version
   */
  @Min(0L) 
  @Schema(name = "version", description = "The commit version associated with the operation", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteFromTableResponse deleteFromTableResponse = (DeleteFromTableResponse) o;
    return Objects.equals(this.transactionId, deleteFromTableResponse.transactionId) &&
        Objects.equals(this.version, deleteFromTableResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteFromTableResponse {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

