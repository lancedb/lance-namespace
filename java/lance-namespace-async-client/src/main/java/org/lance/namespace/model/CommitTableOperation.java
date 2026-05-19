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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * A single operation within a batch commit. Provide exactly one of the operation fields to specify
 * the operation kind.
 */
@JsonPropertyOrder({
  CommitTableOperation.JSON_PROPERTY_DECLARE_TABLE,
  CommitTableOperation.JSON_PROPERTY_CREATE_TABLE_VERSION,
  CommitTableOperation.JSON_PROPERTY_DELETE_TABLE_VERSIONS,
  CommitTableOperation.JSON_PROPERTY_DEREGISTER_TABLE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CommitTableOperation {
  public static final String JSON_PROPERTY_DECLARE_TABLE = "declare_table";
  @javax.annotation.Nullable private DeclareTableRequest declareTable;

  public static final String JSON_PROPERTY_CREATE_TABLE_VERSION = "create_table_version";
  @javax.annotation.Nullable private CreateTableVersionRequest createTableVersion;

  public static final String JSON_PROPERTY_DELETE_TABLE_VERSIONS = "delete_table_versions";
  @javax.annotation.Nullable private BatchDeleteTableVersionsRequest deleteTableVersions;

  public static final String JSON_PROPERTY_DEREGISTER_TABLE = "deregister_table";
  @javax.annotation.Nullable private DeregisterTableRequest deregisterTable;

  public CommitTableOperation() {}

  public CommitTableOperation declareTable(
      @javax.annotation.Nullable DeclareTableRequest declareTable) {
    this.declareTable = declareTable;
    return this;
  }

  /**
   * Declare (reserve) a new table in the namespace
   *
   * @return declareTable
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DECLARE_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public DeclareTableRequest getDeclareTable() {
    return declareTable;
  }

  @JsonProperty(JSON_PROPERTY_DECLARE_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeclareTable(@javax.annotation.Nullable DeclareTableRequest declareTable) {
    this.declareTable = declareTable;
  }

  public CommitTableOperation createTableVersion(
      @javax.annotation.Nullable CreateTableVersionRequest createTableVersion) {
    this.createTableVersion = createTableVersion;
    return this;
  }

  /**
   * Create a new version entry for a table
   *
   * @return createTableVersion
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CREATE_TABLE_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public CreateTableVersionRequest getCreateTableVersion() {
    return createTableVersion;
  }

  @JsonProperty(JSON_PROPERTY_CREATE_TABLE_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreateTableVersion(
      @javax.annotation.Nullable CreateTableVersionRequest createTableVersion) {
    this.createTableVersion = createTableVersion;
  }

  public CommitTableOperation deleteTableVersions(
      @javax.annotation.Nullable BatchDeleteTableVersionsRequest deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
    return this;
  }

  /**
   * Delete version ranges from a table
   *
   * @return deleteTableVersions
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DELETE_TABLE_VERSIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public BatchDeleteTableVersionsRequest getDeleteTableVersions() {
    return deleteTableVersions;
  }

  @JsonProperty(JSON_PROPERTY_DELETE_TABLE_VERSIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeleteTableVersions(
      @javax.annotation.Nullable BatchDeleteTableVersionsRequest deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
  }

  public CommitTableOperation deregisterTable(
      @javax.annotation.Nullable DeregisterTableRequest deregisterTable) {
    this.deregisterTable = deregisterTable;
    return this;
  }

  /**
   * Deregister (soft-delete) a table
   *
   * @return deregisterTable
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DEREGISTER_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public DeregisterTableRequest getDeregisterTable() {
    return deregisterTable;
  }

  @JsonProperty(JSON_PROPERTY_DEREGISTER_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeregisterTable(
      @javax.annotation.Nullable DeregisterTableRequest deregisterTable) {
    this.deregisterTable = deregisterTable;
  }

  /** Return true if this CommitTableOperation object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitTableOperation commitTableOperation = (CommitTableOperation) o;
    return Objects.equals(this.declareTable, commitTableOperation.declareTable)
        && Objects.equals(this.createTableVersion, commitTableOperation.createTableVersion)
        && Objects.equals(this.deleteTableVersions, commitTableOperation.deleteTableVersions)
        && Objects.equals(this.deregisterTable, commitTableOperation.deregisterTable);
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
    sb.append("    deleteTableVersions: ")
        .append(toIndentedString(deleteTableVersions))
        .append("\n");
    sb.append("    deregisterTable: ").append(toIndentedString(deregisterTable)).append("\n");
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `declare_table` to the URL query string
    if (getDeclareTable() != null) {
      joiner.add(getDeclareTable().toUrlQueryString(prefix + "declare_table" + suffix));
    }

    // add `create_table_version` to the URL query string
    if (getCreateTableVersion() != null) {
      joiner.add(
          getCreateTableVersion().toUrlQueryString(prefix + "create_table_version" + suffix));
    }

    // add `delete_table_versions` to the URL query string
    if (getDeleteTableVersions() != null) {
      joiner.add(
          getDeleteTableVersions().toUrlQueryString(prefix + "delete_table_versions" + suffix));
    }

    // add `deregister_table` to the URL query string
    if (getDeregisterTable() != null) {
      joiner.add(getDeregisterTable().toUrlQueryString(prefix + "deregister_table" + suffix));
    }

    return joiner.toString();
  }
}
