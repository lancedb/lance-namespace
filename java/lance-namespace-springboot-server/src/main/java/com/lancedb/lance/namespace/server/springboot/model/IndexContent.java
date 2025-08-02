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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** IndexContent */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class IndexContent {

  private String indexName;

  private String indexUuid;

  @Valid private List<String> columns = new ArrayList<>();

  private String status;

  public IndexContent() {
    super();
  }

  /** Constructor with only required parameters */
  public IndexContent(String indexName, String indexUuid, List<String> columns, String status) {
    this.indexName = indexName;
    this.indexUuid = indexUuid;
    this.columns = columns;
    this.status = status;
  }

  public IndexContent indexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  /**
   * Name of the index
   *
   * @return indexName
   */
  @NotNull
  @Schema(
      name = "index_name",
      description = "Name of the index",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("index_name")
  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public IndexContent indexUuid(String indexUuid) {
    this.indexUuid = indexUuid;
    return this;
  }

  /**
   * Unique identifier for the index
   *
   * @return indexUuid
   */
  @NotNull
  @Schema(
      name = "index_uuid",
      description = "Unique identifier for the index",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("index_uuid")
  public String getIndexUuid() {
    return indexUuid;
  }

  public void setIndexUuid(String indexUuid) {
    this.indexUuid = indexUuid;
  }

  public IndexContent columns(List<String> columns) {
    this.columns = columns;
    return this;
  }

  public IndexContent addColumnsItem(String columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<>();
    }
    this.columns.add(columnsItem);
    return this;
  }

  /**
   * Columns covered by this index
   *
   * @return columns
   */
  @NotNull
  @Schema(
      name = "columns",
      description = "Columns covered by this index",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("columns")
  public List<String> getColumns() {
    return columns;
  }

  public void setColumns(List<String> columns) {
    this.columns = columns;
  }

  public IndexContent status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Current status of the index
   *
   * @return status
   */
  @NotNull
  @Schema(
      name = "status",
      description = "Current status of the index",
      requiredMode = Schema.RequiredMode.REQUIRED)
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
    IndexContent indexContent = (IndexContent) o;
    return Objects.equals(this.indexName, indexContent.indexName)
        && Objects.equals(this.indexUuid, indexContent.indexUuid)
        && Objects.equals(this.columns, indexContent.columns)
        && Objects.equals(this.status, indexContent.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexName, indexUuid, columns, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndexContent {\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    indexUuid: ").append(toIndentedString(indexUuid)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
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
