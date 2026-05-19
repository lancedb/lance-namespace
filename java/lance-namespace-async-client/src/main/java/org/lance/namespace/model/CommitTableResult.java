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
 * Result of a single operation within a batch commit. Each result corresponds to one operation in
 * the request, in the same order. Exactly one of the result fields will be set.
 */
@JsonPropertyOrder({
  CommitTableResult.JSON_PROPERTY_DECLARE_TABLE,
  CommitTableResult.JSON_PROPERTY_CREATE_TABLE_VERSION,
  CommitTableResult.JSON_PROPERTY_DELETE_TABLE_VERSIONS,
  CommitTableResult.JSON_PROPERTY_DEREGISTER_TABLE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CommitTableResult {
  public static final String JSON_PROPERTY_DECLARE_TABLE = "declare_table";
  @javax.annotation.Nullable private DeclareTableResponse declareTable;

  public static final String JSON_PROPERTY_CREATE_TABLE_VERSION = "create_table_version";
  @javax.annotation.Nullable private CreateTableVersionResponse createTableVersion;

  public static final String JSON_PROPERTY_DELETE_TABLE_VERSIONS = "delete_table_versions";
  @javax.annotation.Nullable private BatchDeleteTableVersionsResponse deleteTableVersions;

  public static final String JSON_PROPERTY_DEREGISTER_TABLE = "deregister_table";
  @javax.annotation.Nullable private DeregisterTableResponse deregisterTable;

  public CommitTableResult() {}

  public CommitTableResult declareTable(
      @javax.annotation.Nullable DeclareTableResponse declareTable) {
    this.declareTable = declareTable;
    return this;
  }

  /**
   * Result of a DeclareTable operation
   *
   * @return declareTable
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DECLARE_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public DeclareTableResponse getDeclareTable() {
    return declareTable;
  }

  @JsonProperty(JSON_PROPERTY_DECLARE_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeclareTable(@javax.annotation.Nullable DeclareTableResponse declareTable) {
    this.declareTable = declareTable;
  }

  public CommitTableResult createTableVersion(
      @javax.annotation.Nullable CreateTableVersionResponse createTableVersion) {
    this.createTableVersion = createTableVersion;
    return this;
  }

  /**
   * Result of a CreateTableVersion operation
   *
   * @return createTableVersion
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CREATE_TABLE_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public CreateTableVersionResponse getCreateTableVersion() {
    return createTableVersion;
  }

  @JsonProperty(JSON_PROPERTY_CREATE_TABLE_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreateTableVersion(
      @javax.annotation.Nullable CreateTableVersionResponse createTableVersion) {
    this.createTableVersion = createTableVersion;
  }

  public CommitTableResult deleteTableVersions(
      @javax.annotation.Nullable BatchDeleteTableVersionsResponse deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
    return this;
  }

  /**
   * Result of a DeleteTableVersions operation
   *
   * @return deleteTableVersions
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DELETE_TABLE_VERSIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public BatchDeleteTableVersionsResponse getDeleteTableVersions() {
    return deleteTableVersions;
  }

  @JsonProperty(JSON_PROPERTY_DELETE_TABLE_VERSIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeleteTableVersions(
      @javax.annotation.Nullable BatchDeleteTableVersionsResponse deleteTableVersions) {
    this.deleteTableVersions = deleteTableVersions;
  }

  public CommitTableResult deregisterTable(
      @javax.annotation.Nullable DeregisterTableResponse deregisterTable) {
    this.deregisterTable = deregisterTable;
    return this;
  }

  /**
   * Result of a DeregisterTable operation
   *
   * @return deregisterTable
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DEREGISTER_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public DeregisterTableResponse getDeregisterTable() {
    return deregisterTable;
  }

  @JsonProperty(JSON_PROPERTY_DEREGISTER_TABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeregisterTable(
      @javax.annotation.Nullable DeregisterTableResponse deregisterTable) {
    this.deregisterTable = deregisterTable;
  }

  /** Return true if this CommitTableResult object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommitTableResult commitTableResult = (CommitTableResult) o;
    return Objects.equals(this.declareTable, commitTableResult.declareTable)
        && Objects.equals(this.createTableVersion, commitTableResult.createTableVersion)
        && Objects.equals(this.deleteTableVersions, commitTableResult.deleteTableVersions)
        && Objects.equals(this.deregisterTable, commitTableResult.deregisterTable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(declareTable, createTableVersion, deleteTableVersions, deregisterTable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommitTableResult {\n");
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
