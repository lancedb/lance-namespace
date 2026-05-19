package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.lance.namespace.server.springboot.model.TableVersion;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response for batch creating table versions. Contains the created versions for each table in the same order as the request. 
 */

@Schema(name = "BatchCreateTableVersionsResponse", description = "Response for batch creating table versions. Contains the created versions for each table in the same order as the request. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class BatchCreateTableVersionsResponse {

  private String transactionId;

  @Valid
  private List<@Valid TableVersion> versions = new ArrayList<>();

  public BatchCreateTableVersionsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BatchCreateTableVersionsResponse(List<@Valid TableVersion> versions) {
    this.versions = versions;
  }

  public BatchCreateTableVersionsResponse transactionId(String transactionId) {
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

  public BatchCreateTableVersionsResponse versions(List<@Valid TableVersion> versions) {
    this.versions = versions;
    return this;
  }

  public BatchCreateTableVersionsResponse addVersionsItem(TableVersion versionsItem) {
    if (this.versions == null) {
      this.versions = new ArrayList<>();
    }
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * List of created table versions in the same order as the request entries
   * @return versions
   */
  @NotNull @Valid 
  @Schema(name = "versions", description = "List of created table versions in the same order as the request entries", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("versions")
  public List<@Valid TableVersion> getVersions() {
    return versions;
  }

  public void setVersions(List<@Valid TableVersion> versions) {
    this.versions = versions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCreateTableVersionsResponse batchCreateTableVersionsResponse = (BatchCreateTableVersionsResponse) o;
    return Objects.equals(this.transactionId, batchCreateTableVersionsResponse.transactionId) &&
        Objects.equals(this.versions, batchCreateTableVersionsResponse.versions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, versions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCreateTableVersionsResponse {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
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

