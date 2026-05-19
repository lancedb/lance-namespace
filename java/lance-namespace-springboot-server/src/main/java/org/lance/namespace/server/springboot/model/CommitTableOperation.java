package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.lance.namespace.server.springboot.model.BatchDeleteTableVersionsRequest;
import org.lance.namespace.server.springboot.model.CreateTableVersionRequest;
import org.lance.namespace.server.springboot.model.DeclareTableRequest;
import org.lance.namespace.server.springboot.model.DeregisterTableRequest;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * A single operation within a batch commit. Provide exactly one of the operation fields to specify the operation kind. 
 */

@Schema(name = "CommitTableOperation", description = "A single operation within a batch commit. Provide exactly one of the operation fields to specify the operation kind. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class CommitTableOperation {

  private DeclareTableRequest declareTable;

  private CreateTableVersionRequest createTableVersion;

  private BatchDeleteTableVersionsRequest deleteTableVersions;

  private DeregisterTableRequest deregisterTable;

  public CommitTableOperation declareTable(DeclareTableRequest declareTable) {
    this.declareTable = declareTable;
    return this;
  }

  /**
   * Declare (reserve) a new table in the namespace
   * @return declareTable
   */
  @Valid 
  @Schema(name = "declare_table", description = "Declare (reserve) a new table in the namespace", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("declare_table")
  public DeclareTableRequest getDeclareTable() {
    return declareTable;
  }

  public void setDeclareTable(DeclareTableRequest declareTable) {
    this.declareTable = declareTable;
  }

  public CommitTableOperation createTableVersion(CreateTableVersionRequest createTableVersion) {
    this.createTableVersion = createTableVersion;
    return this;
  }

  /**
   * Create a new version entry for a table
   * @return createTableVersion
   */
  @Valid 
  @Schema(name = "create_table_version", description = "Create a new version entry for a table", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("create_table_version")
  public CreateTableVersionRequest getCreateTableVersion() {
    return createTableVersion;
  }

  public void setCreateTableVersion(CreateTableVersionRequest createTableVersion) {
    this.createTableVersion = createTableVersion;
  }

  public CommitTableOperation deleteTableVersions(BatchDeleteTableVersionsRequest deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
    return this;
  }

  /**
   * Delete version ranges from a table
   * @return deleteTableVersions
   */
  @Valid 
  @Schema(name = "delete_table_versions", description = "Delete version ranges from a table", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("delete_table_versions")
  public BatchDeleteTableVersionsRequest getDeleteTableVersions() {
    return deleteTableVersions;
  }

  public void setDeleteTableVersions(BatchDeleteTableVersionsRequest deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
  }

  public CommitTableOperation deregisterTable(DeregisterTableRequest deregisterTable) {
    this.deregisterTable = deregisterTable;
    return this;
  }

  /**
   * Deregister (soft-delete) a table
   * @return deregisterTable
   */
  @Valid 
  @Schema(name = "deregister_table", description = "Deregister (soft-delete) a table", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("deregister_table")
  public DeregisterTableRequest getDeregisterTable() {
    return deregisterTable;
  }

  public void setDeregisterTable(DeregisterTableRequest deregisterTable) {
    this.deregisterTable = deregisterTable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitTableOperation commitTableOperation = (CommitTableOperation) o;
    return Objects.equals(this.declareTable, commitTableOperation.declareTable) &&
        Objects.equals(this.createTableVersion, commitTableOperation.createTableVersion) &&
        Objects.equals(this.deleteTableVersions, commitTableOperation.deleteTableVersions) &&
        Objects.equals(this.deregisterTable, commitTableOperation.deregisterTable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(declareTable, createTableVersion, deleteTableVersions, deregisterTable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitTableOperation {\n");
    sb.append("    declareTable: ").append(toIndentedString(declareTable)).append("\n");
    sb.append("    createTableVersion: ").append(toIndentedString(createTableVersion)).append("\n");
    sb.append("    deleteTableVersions: ").append(toIndentedString(deleteTableVersions)).append("\n");
    sb.append("    deregisterTable: ").append(toIndentedString(deregisterTable)).append("\n");
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

