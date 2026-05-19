package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UpdateTableSchemaMetadataResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class UpdateTableSchemaMetadataResponse {

  @Valid
  private Map<String, String> metadata = new HashMap<>();

  private String transactionId;

  public UpdateTableSchemaMetadataResponse metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public UpdateTableSchemaMetadataResponse putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * The updated schema metadata
   * @return metadata
   */
  
  @Schema(name = "metadata", description = "The updated schema metadata", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public UpdateTableSchemaMetadataResponse transactionId(String transactionId) {
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
    UpdateTableSchemaMetadataResponse updateTableSchemaMetadataResponse = (UpdateTableSchemaMetadataResponse) o;
    return Objects.equals(this.metadata, updateTableSchemaMetadataResponse.metadata) &&
        Objects.equals(this.transactionId, updateTableSchemaMetadataResponse.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, transactionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTableSchemaMetadataResponse {\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

