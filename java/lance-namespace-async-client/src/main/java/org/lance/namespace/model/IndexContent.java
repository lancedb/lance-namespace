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

import org.lance.namespace.client.async.ApiClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** IndexContent */
@JsonPropertyOrder({
  IndexContent.JSON_PROPERTY_INDEX_NAME,
  IndexContent.JSON_PROPERTY_INDEX_UUID,
  IndexContent.JSON_PROPERTY_COLUMNS,
  IndexContent.JSON_PROPERTY_STATUS,
  IndexContent.JSON_PROPERTY_INDEX_TYPE,
  IndexContent.JSON_PROPERTY_TYPE_URL,
  IndexContent.JSON_PROPERTY_NUM_INDEXED_ROWS,
  IndexContent.JSON_PROPERTY_NUM_UNINDEXED_ROWS,
  IndexContent.JSON_PROPERTY_SIZE_BYTES,
  IndexContent.JSON_PROPERTY_NUM_SEGMENTS,
  IndexContent.JSON_PROPERTY_CREATED_AT,
  IndexContent.JSON_PROPERTY_INDEX_VERSION,
  IndexContent.JSON_PROPERTY_INDEX_DETAILS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class IndexContent {
  public static final String JSON_PROPERTY_INDEX_NAME = "index_name";
  @javax.annotation.Nonnull private String indexName;

  public static final String JSON_PROPERTY_INDEX_UUID = "index_uuid";
  @javax.annotation.Nonnull private String indexUuid;

  public static final String JSON_PROPERTY_COLUMNS = "columns";
  @javax.annotation.Nonnull private List<String> columns = new ArrayList<>();

  public static final String JSON_PROPERTY_STATUS = "status";
  @javax.annotation.Nonnull private String status;

  public static final String JSON_PROPERTY_INDEX_TYPE = "index_type";
  @javax.annotation.Nullable private String indexType;

  public static final String JSON_PROPERTY_TYPE_URL = "type_url";
  @javax.annotation.Nullable private String typeUrl;

  public static final String JSON_PROPERTY_NUM_INDEXED_ROWS = "num_indexed_rows";
  @javax.annotation.Nullable private Long numIndexedRows;

  public static final String JSON_PROPERTY_NUM_UNINDEXED_ROWS = "num_unindexed_rows";
  @javax.annotation.Nullable private Long numUnindexedRows;

  public static final String JSON_PROPERTY_SIZE_BYTES = "size_bytes";
  @javax.annotation.Nullable private Long sizeBytes;

  public static final String JSON_PROPERTY_NUM_SEGMENTS = "num_segments";
  @javax.annotation.Nullable private Integer numSegments;

  public static final String JSON_PROPERTY_CREATED_AT = "created_at";
  @javax.annotation.Nullable private OffsetDateTime createdAt;

  public static final String JSON_PROPERTY_INDEX_VERSION = "index_version";
  @javax.annotation.Nullable private Integer indexVersion;

  public static final String JSON_PROPERTY_INDEX_DETAILS = "index_details";
  @javax.annotation.Nullable private String indexDetails;

  public IndexContent() {}

  public IndexContent indexName(@javax.annotation.Nonnull String indexName) {
    this.indexName = indexName;
    return this;
  }

  /**
   * Name of the index
   *
   * @return indexName
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getIndexName() {
    return indexName;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndexName(@javax.annotation.Nonnull String indexName) {
    this.indexName = indexName;
  }

  public IndexContent indexUuid(@javax.annotation.Nonnull String indexUuid) {
    this.indexUuid = indexUuid;
    return this;
  }

  /**
   * Unique identifier for the index
   *
   * @return indexUuid
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX_UUID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getIndexUuid() {
    return indexUuid;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_UUID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndexUuid(@javax.annotation.Nonnull String indexUuid) {
    this.indexUuid = indexUuid;
  }

  public IndexContent columns(@javax.annotation.Nonnull List<String> columns) {
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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getColumns() {
    return columns;
  }

  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setColumns(@javax.annotation.Nonnull List<String> columns) {
    this.columns = columns;
  }

  public IndexContent status(@javax.annotation.Nonnull String status) {
    this.status = status;
    return this;
  }

  /**
   * Current status of the index
   *
   * @return status
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getStatus() {
    return status;
  }

  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(@javax.annotation.Nonnull String status) {
    this.status = status;
  }

  public IndexContent indexType(@javax.annotation.Nullable String indexType) {
    this.indexType = indexType;
    return this;
  }

  /**
   * Friendly index type, e.g. IVF_PQ, BTREE. Unknown if no plugin recognizes the index.
   *
   * @return indexType
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getIndexType() {
    return indexType;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIndexType(@javax.annotation.Nullable String indexType) {
    this.indexType = indexType;
  }

  public IndexContent typeUrl(@javax.annotation.Nullable String typeUrl) {
    this.typeUrl = typeUrl;
    return this;
  }

  /**
   * Protobuf type URL, a precise type identifier for the index.
   *
   * @return typeUrl
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TYPE_URL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getTypeUrl() {
    return typeUrl;
  }

  @JsonProperty(JSON_PROPERTY_TYPE_URL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTypeUrl(@javax.annotation.Nullable String typeUrl) {
    this.typeUrl = typeUrl;
  }

  public IndexContent numIndexedRows(@javax.annotation.Nullable Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
    return this;
  }

  /**
   * Number of live rows covered by the index. This does not count rows that are in the index but
   * have since been deleted. minimum: 0
   *
   * @return numIndexedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_INDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumIndexedRows() {
    return numIndexedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_INDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumIndexedRows(@javax.annotation.Nullable Long numIndexedRows) {
    this.numIndexedRows = numIndexedRows;
  }

  public IndexContent numUnindexedRows(@javax.annotation.Nullable Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
    return this;
  }

  /**
   * Number of rows that are not indexed. minimum: 0
   *
   * @return numUnindexedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_UNINDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumUnindexedRows() {
    return numUnindexedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_UNINDEXED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumUnindexedRows(@javax.annotation.Nullable Long numUnindexedRows) {
    this.numUnindexedRows = numUnindexedRows;
  }

  public IndexContent sizeBytes(@javax.annotation.Nullable Long sizeBytes) {
    this.sizeBytes = sizeBytes;
    return this;
  }

  /**
   * Total index size in bytes across all segments. Null for indices predating file-size tracking.
   * minimum: 0
   *
   * @return sizeBytes
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SIZE_BYTES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getSizeBytes() {
    return sizeBytes;
  }

  @JsonProperty(JSON_PROPERTY_SIZE_BYTES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSizeBytes(@javax.annotation.Nullable Long sizeBytes) {
    this.sizeBytes = sizeBytes;
  }

  public IndexContent numSegments(@javax.annotation.Nullable Integer numSegments) {
    this.numSegments = numSegments;
    return this;
  }

  /**
   * Number of index deltas/segments. minimum: 0
   *
   * @return numSegments
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_SEGMENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getNumSegments() {
    return numSegments;
  }

  @JsonProperty(JSON_PROPERTY_NUM_SEGMENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumSegments(@javax.annotation.Nullable Integer numSegments) {
    this.numSegments = numSegments;
  }

  public IndexContent createdAt(@javax.annotation.Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Creation time for indexes. Null for legacy indices.
   *
   * @return createdAt
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CREATED_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  @JsonProperty(JSON_PROPERTY_CREATED_AT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreatedAt(@javax.annotation.Nullable OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public IndexContent indexVersion(@javax.annotation.Nullable Integer indexVersion) {
    this.indexVersion = indexVersion;
    return this;
  }

  /**
   * On-disk index format version. minimum: 0
   *
   * @return indexVersion
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INDEX_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Integer getIndexVersion() {
    return indexVersion;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIndexVersion(@javax.annotation.Nullable Integer indexVersion) {
    this.indexVersion = indexVersion;
  }

  public IndexContent indexDetails(@javax.annotation.Nullable String indexDetails) {
    this.indexDetails = indexDetails;
    return this;
  }

  /**
   * Opaque, type-specific JSON with additional index details. For vector indices this carries
   * metric/distance type, partitioning, and HNSW/PQ/SQ/RQ parameters.
   *
   * @return indexDetails
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INDEX_DETAILS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getIndexDetails() {
    return indexDetails;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_DETAILS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIndexDetails(@javax.annotation.Nullable String indexDetails) {
    this.indexDetails = indexDetails;
  }

  /** Return true if this IndexContent object is equal to o. */
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

    // add `index_name` to the URL query string
    if (getIndexName() != null) {
      joiner.add(
          String.format(
              "%sindex_name%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getIndexName()))));
    }

    // add `index_uuid` to the URL query string
    if (getIndexUuid() != null) {
      joiner.add(
          String.format(
              "%sindex_uuid%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getIndexUuid()))));
    }

    // add `columns` to the URL query string
    if (getColumns() != null) {
      for (int i = 0; i < getColumns().size(); i++) {
        joiner.add(
            String.format(
                "%scolumns%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getColumns().get(i)))));
      }
    }

    // add `status` to the URL query string
    if (getStatus() != null) {
      joiner.add(
          String.format(
              "%sstatus%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getStatus()))));
    }

    // add `index_type` to the URL query string
    if (getIndexType() != null) {
      joiner.add(
          String.format(
              "%sindex_type%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getIndexType()))));
    }

    // add `type_url` to the URL query string
    if (getTypeUrl() != null) {
      joiner.add(
          String.format(
              "%stype_url%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTypeUrl()))));
    }

    // add `num_indexed_rows` to the URL query string
    if (getNumIndexedRows() != null) {
      joiner.add(
          String.format(
              "%snum_indexed_rows%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumIndexedRows()))));
    }

    // add `num_unindexed_rows` to the URL query string
    if (getNumUnindexedRows() != null) {
      joiner.add(
          String.format(
              "%snum_unindexed_rows%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumUnindexedRows()))));
    }

    // add `size_bytes` to the URL query string
    if (getSizeBytes() != null) {
      joiner.add(
          String.format(
              "%ssize_bytes%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getSizeBytes()))));
    }

    // add `num_segments` to the URL query string
    if (getNumSegments() != null) {
      joiner.add(
          String.format(
              "%snum_segments%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumSegments()))));
    }

    // add `created_at` to the URL query string
    if (getCreatedAt() != null) {
      joiner.add(
          String.format(
              "%screated_at%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getCreatedAt()))));
    }

    // add `index_version` to the URL query string
    if (getIndexVersion() != null) {
      joiner.add(
          String.format(
              "%sindex_version%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getIndexVersion()))));
    }

    // add `index_details` to the URL query string
    if (getIndexDetails() != null) {
      joiner.add(
          String.format(
              "%sindex_details%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getIndexDetails()))));
    }

    return joiner.toString();
  }
}
