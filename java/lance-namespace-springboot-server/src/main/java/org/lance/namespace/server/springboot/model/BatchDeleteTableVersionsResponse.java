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
 * Response for deleting table version records
 */

@Schema(name = "BatchDeleteTableVersionsResponse", description = "Response for deleting table version records")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class BatchDeleteTableVersionsResponse {

  private Long deletedCount;

  private String transactionId;

  public BatchDeleteTableVersionsResponse deletedCount(Long deletedCount) {
    this.deletedCount = deletedCount;
    return this;
  }

  /**
   * Number of version records deleted
   * minimum: 0
   * @return deletedCount
   */
  @Min(0L) 
  @Schema(name = "deleted_count", description = "Number of version records deleted", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deleted_count")
  public Long getDeletedCount() {
    return deletedCount;
  }

  public void setDeletedCount(Long deletedCount) {
    this.deletedCount = deletedCount;
  }

  public BatchDeleteTableVersionsResponse transactionId(String transactionId) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchDeleteTableVersionsResponse batchDeleteTableVersionsResponse = (BatchDeleteTableVersionsResponse) o;
    return Objects.equals(this.deletedCount, batchDeleteTableVersionsResponse.deletedCount) &&
        Objects.equals(this.transactionId, batchDeleteTableVersionsResponse.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deletedCount, transactionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchDeleteTableVersionsResponse {\n");
    sb.append("    deletedCount: ").append(toIndentedString(deletedCount)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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

