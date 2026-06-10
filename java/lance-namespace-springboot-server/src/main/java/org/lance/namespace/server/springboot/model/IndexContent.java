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
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
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

  @Valid private List<@Size(min = 1) String> columns = new ArrayList<>();

  private String status;

  private String indexType;

  private String typeUrl;

  private Long numIndexedRows;

  private Long numUnindexedRows;

  private Long sizeBytes;

  private Integer numSegments;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  private Integer indexVersion;

  private String indexDetails;

  public IndexContent() {
    super();
  }

  /** Constructor with only required parameters */
  public IndexContent(
      String indexName, String indexUuid, List<@Size(min = 1) String> columns, String status) {
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

  public IndexContent columns(List<@Size(min = 1) String> columns) {
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
   * Canonical Lance field paths covered by this index. Nested fields use dot-separated segments;
   * segments containing literal dots are backtick-quoted, and backticks inside quoted segments are
   * doubled.
   *
   * @return columns
   */
  @NotNull
  @Schema(
      name = "columns",
      description =
          "Canonical Lance field paths covered by this index. Nested fields use dot-separated segments; segments containing literal dots are backtick-quoted, and backticks inside quoted segments are doubled.",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("columns")
  public List<@Size(min = 1) String> getColumns() {
    return columns;
  }

  public void setColumns(List<@Size(min = 1) String> columns) {
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

  public IndexContent indexType(String indexType) {
    this.indexType = indexType;
    return this;
  }

  /**
   * Friendly index type, e.g. IVF_PQ, BTREE. Unknown if no plugin recognizes the index.
   *
   * @return indexType
   */
  @Schema(
      name = "index_type",
      description =
          "Friendly index type, e.g. IVF_PQ, BTREE. Unknown if no plugin recognizes the index.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("index_type")
  public String getIndexType() {
    return indexType;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  public IndexContent typeUrl(String typeUrl) {
    this.typeUrl = typeUrl;
    return this;
  }

  /**
   * Protobuf type URL, a precise type identifier for the index.
   *
   * @return typeUrl
   */
  @Schema(
      name = "type_url",
      description = "Protobuf type URL, a precise type identifier for the index.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type_url")
  public String getTypeUrl() {
    return typeUrl;
  }

  public void setTypeUrl(String typeUrl) {
    this.typeUrl = typeUrl;
  }

  public IndexContent numIndexedRows(Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
    return this;
  }

  /**
   * Number of live rows covered by the index. This does not count rows that are in the index but
   * have since been deleted. minimum: 0
   *
   * @return numIndexedRows
   */
  @Min(0L)
  @Schema(
      name = "num_indexed_rows",
      description =
          "Number of live rows covered by the index. This does not count rows that are in the index but have since been deleted.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_indexed_rows")
  public Long getNumIndexedRows() {
    return numIndexedRows;
  }

  public void setNumIndexedRows(Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
  }

  public IndexContent numUnindexedRows(Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
    return this;
  }

  /**
   * Number of rows that are not indexed. minimum: 0
   *
   * @return numUnindexedRows
   */
  @Min(0L)
  @Schema(
      name = "num_unindexed_rows",
      description = "Number of rows that are not indexed.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_unindexed_rows")
  public Long getNumUnindexedRows() {
    return numUnindexedRows;
  }

  public void setNumUnindexedRows(Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
  }

  public IndexContent sizeBytes(Long sizeBytes) {
    this.sizeBytes = sizeBytes;
    return this;
  }

  /**
   * Total index size in bytes across all segments. Null for indices predating file-size tracking.
   * minimum: 0
   *
   * @return sizeBytes
   */
  @Min(0L)
  @Schema(
      name = "size_bytes",
      description =
          "Total index size in bytes across all segments. Null for indices predating file-size tracking.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size_bytes")
  public Long getSizeBytes() {
    return sizeBytes;
  }

  public void setSizeBytes(Long sizeBytes) {
    this.sizeBytes = sizeBytes;
  }

  public IndexContent numSegments(Integer numSegments) {
    this.numSegments = numSegments;
    return this;
  }

  /**
   * Number of index deltas/segments. minimum: 0
   *
   * @return numSegments
   */
  @Min(0)
  @Schema(
      name = "num_segments",
      description = "Number of index deltas/segments.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_segments")
  public Integer getNumSegments() {
    return numSegments;
  }

  public void setNumSegments(Integer numSegments) {
    this.numSegments = numSegments;
  }

  public IndexContent createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Creation time for indexes. Null for legacy indices.
   *
   * @return createdAt
   */
  @Valid
  @Schema(
      name = "created_at",
      description = "Creation time for indexes. Null for legacy indices.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("created_at")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public IndexContent indexVersion(Integer indexVersion) {
    this.indexVersion = indexVersion;
    return this;
  }

  /**
   * On-disk index format version. minimum: 0
   *
   * @return indexVersion
   */
  @Min(0)
  @Schema(
      name = "index_version",
      description = "On-disk index format version.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("index_version")
  public Integer getIndexVersion() {
    return indexVersion;
  }

  public void setIndexVersion(Integer indexVersion) {
    this.indexVersion = indexVersion;
  }

  public IndexContent indexDetails(String indexDetails) {
    this.indexDetails = indexDetails;
    return this;
  }

  /**
   * Opaque, type-specific JSON with additional index details. For vector indices this carries
   * metric/distance type, partitioning, and HNSW/PQ/SQ/RQ parameters.
   *
   * @return indexDetails
   */
  @Schema(
      name = "index_details",
      description =
          "Opaque, type-specific JSON with additional index details. For vector indices this carries metric/distance type, partitioning, and HNSW/PQ/SQ/RQ parameters.",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("index_details")
  public String getIndexDetails() {
    return indexDetails;
  }

  public void setIndexDetails(String indexDetails) {
    this.indexDetails = indexDetails;
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
        && Objects.equals(this.status, indexContent.status)
        && Objects.equals(this.indexType, indexContent.indexType)
        && Objects.equals(this.typeUrl, indexContent.typeUrl)
        && Objects.equals(this.numIndexedRows, indexContent.numIndexedRows)
        && Objects.equals(this.numUnindexedRows, indexContent.numUnindexedRows)
        && Objects.equals(this.sizeBytes, indexContent.sizeBytes)
        && Objects.equals(this.numSegments, indexContent.numSegments)
        && Objects.equals(this.createdAt, indexContent.createdAt)
        && Objects.equals(this.indexVersion, indexContent.indexVersion)
        && Objects.equals(this.indexDetails, indexContent.indexDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        indexName,
        indexUuid,
        columns,
        status,
        indexType,
        typeUrl,
        numIndexedRows,
        numUnindexedRows,
        sizeBytes,
        numSegments,
        createdAt,
        indexVersion,
        indexDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndexContent {\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    indexUuid: ").append(toIndentedString(indexUuid)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    indexType: ").append(toIndentedString(indexType)).append("\n");
    sb.append("    typeUrl: ").append(toIndentedString(typeUrl)).append("\n");
    sb.append("    numIndexedRows: ").append(toIndentedString(numIndexedRows)).append("\n");
    sb.append("    numUnindexedRows: ").append(toIndentedString(numUnindexedRows)).append("\n");
    sb.append("    sizeBytes: ").append(toIndentedString(sizeBytes)).append("\n");
    sb.append("    numSegments: ").append(toIndentedString(numSegments)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    indexVersion: ").append(toIndentedString(indexVersion)).append("\n");
    sb.append("    indexDetails: ").append(toIndentedString(indexDetails)).append("\n");
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
