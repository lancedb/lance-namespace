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
 * TableBasicStats
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class TableBasicStats {

  private Integer numDeletedRows;

  private Integer numFragments;

  public TableBasicStats() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TableBasicStats(Integer numDeletedRows, Integer numFragments) {
    this.numDeletedRows = numDeletedRows;
    this.numFragments = numFragments;
  }

  public TableBasicStats numDeletedRows(Integer numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
    return this;
  }

  /**
   * Number of deleted rows in the table
   * minimum: 0
   * @return numDeletedRows
   */
  @NotNull @Min(0) 
  @Schema(name = "num_deleted_rows", description = "Number of deleted rows in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_deleted_rows")
  public Integer getNumDeletedRows() {
    return numDeletedRows;
  }

  public void setNumDeletedRows(Integer numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
  }

  public TableBasicStats numFragments(Integer numFragments) {
    this.numFragments = numFragments;
    return this;
  }

  /**
   * Number of fragments in the table
   * minimum: 0
   * @return numFragments
   */
  @NotNull @Min(0) 
  @Schema(name = "num_fragments", description = "Number of fragments in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_fragments")
  public Integer getNumFragments() {
    return numFragments;
  }

  public void setNumFragments(Integer numFragments) {
    this.numFragments = numFragments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TableBasicStats tableBasicStats = (TableBasicStats) o;
    return Objects.equals(this.numDeletedRows, tableBasicStats.numDeletedRows) &&
        Objects.equals(this.numFragments, tableBasicStats.numFragments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numDeletedRows, numFragments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TableBasicStats {\n");
    sb.append("    numDeletedRows: ").append(toIndentedString(numDeletedRows)).append("\n");
    sb.append("    numFragments: ").append(toIndentedString(numFragments)).append("\n");
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

