package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.lance.namespace.server.springboot.model.FragmentStats;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GetTableStatsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class GetTableStatsResponse {

  private Long totalBytes;

  private Long numRows;

  private Long numIndices;

  private FragmentStats fragmentStats;

  public GetTableStatsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GetTableStatsResponse(Long totalBytes, Long numRows, Long numIndices, FragmentStats fragmentStats) {
    this.totalBytes = totalBytes;
    this.numRows = numRows;
    this.numIndices = numIndices;
    this.fragmentStats = fragmentStats;
  }

  public GetTableStatsResponse totalBytes(Long totalBytes) {
    this.totalBytes = totalBytes;
    return this;
  }

  /**
   * The total number of bytes in the table
   * minimum: 0
   * @return totalBytes
   */
  @NotNull @Min(0L) 
  @Schema(name = "total_bytes", description = "The total number of bytes in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("total_bytes")
  public Long getTotalBytes() {
    return totalBytes;
  }

  public void setTotalBytes(Long totalBytes) {
    this.totalBytes = totalBytes;
  }

  public GetTableStatsResponse numRows(Long numRows) {
    this.numRows = numRows;
    return this;
  }

  /**
   * The number of rows in the table
   * minimum: 0
   * @return numRows
   */
  @NotNull @Min(0L) 
  @Schema(name = "num_rows", description = "The number of rows in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_rows")
  public Long getNumRows() {
    return numRows;
  }

  public void setNumRows(Long numRows) {
    this.numRows = numRows;
  }

  public GetTableStatsResponse numIndices(Long numIndices) {
    this.numIndices = numIndices;
    return this;
  }

  /**
   * The number of indices in the table
   * minimum: 0
   * @return numIndices
   */
  @NotNull @Min(0L) 
  @Schema(name = "num_indices", description = "The number of indices in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_indices")
  public Long getNumIndices() {
    return numIndices;
  }

  public void setNumIndices(Long numIndices) {
    this.numIndices = numIndices;
  }

  public GetTableStatsResponse fragmentStats(FragmentStats fragmentStats) {
    this.fragmentStats = fragmentStats;
    return this;
  }

  /**
   * Statistics on table fragments
   * @return fragmentStats
   */
  @NotNull @Valid 
  @Schema(name = "fragment_stats", description = "Statistics on table fragments", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fragment_stats")
  public FragmentStats getFragmentStats() {
    return fragmentStats;
  }

  public void setFragmentStats(FragmentStats fragmentStats) {
    this.fragmentStats = fragmentStats;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetTableStatsResponse getTableStatsResponse = (GetTableStatsResponse) o;
    return Objects.equals(this.totalBytes, getTableStatsResponse.totalBytes) &&
        Objects.equals(this.numRows, getTableStatsResponse.numRows) &&
        Objects.equals(this.numIndices, getTableStatsResponse.numIndices) &&
        Objects.equals(this.fragmentStats, getTableStatsResponse.fragmentStats);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalBytes, numRows, numIndices, fragmentStats);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTableStatsResponse {\n");
    sb.append("    totalBytes: ").append(toIndentedString(totalBytes)).append("\n");
    sb.append("    numRows: ").append(toIndentedString(numRows)).append("\n");
    sb.append("    numIndices: ").append(toIndentedString(numIndices)).append("\n");
    sb.append("    fragmentStats: ").append(toIndentedString(fragmentStats)).append("\n");
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

