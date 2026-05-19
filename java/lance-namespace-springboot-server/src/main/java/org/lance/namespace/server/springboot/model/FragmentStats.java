package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.lance.namespace.server.springboot.model.FragmentSummary;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * FragmentStats
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class FragmentStats {

  private Long numFragments;

  private Long numSmallFragments;

  private FragmentSummary lengths;

  public FragmentStats() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FragmentStats(Long numFragments, Long numSmallFragments, FragmentSummary lengths) {
    this.numFragments = numFragments;
    this.numSmallFragments = numSmallFragments;
    this.lengths = lengths;
  }

  public FragmentStats numFragments(Long numFragments) {
    this.numFragments = numFragments;
    return this;
  }

  /**
   * The number of fragments in the table
   * minimum: 0
   * @return numFragments
   */
  @NotNull @Min(0L) 
  @Schema(name = "num_fragments", description = "The number of fragments in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_fragments")
  public Long getNumFragments() {
    return numFragments;
  }

  public void setNumFragments(Long numFragments) {
    this.numFragments = numFragments;
  }

  public FragmentStats numSmallFragments(Long numSmallFragments) {
    this.numSmallFragments = numSmallFragments;
    return this;
  }

  /**
   * The number of uncompacted fragments in the table
   * minimum: 0
   * @return numSmallFragments
   */
  @NotNull @Min(0L) 
  @Schema(name = "num_small_fragments", description = "The number of uncompacted fragments in the table", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("num_small_fragments")
  public Long getNumSmallFragments() {
    return numSmallFragments;
  }

  public void setNumSmallFragments(Long numSmallFragments) {
    this.numSmallFragments = numSmallFragments;
  }

  public FragmentStats lengths(FragmentSummary lengths) {
    this.lengths = lengths;
    return this;
  }

  /**
   * Statistics on the number of rows in the table fragments
   * @return lengths
   */
  @NotNull @Valid 
  @Schema(name = "lengths", description = "Statistics on the number of rows in the table fragments", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lengths")
  public FragmentSummary getLengths() {
    return lengths;
  }

  public void setLengths(FragmentSummary lengths) {
    this.lengths = lengths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FragmentStats fragmentStats = (FragmentStats) o;
    return Objects.equals(this.numFragments, fragmentStats.numFragments) &&
        Objects.equals(this.numSmallFragments, fragmentStats.numSmallFragments) &&
        Objects.equals(this.lengths, fragmentStats.lengths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numFragments, numSmallFragments, lengths);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FragmentStats {\n");
    sb.append("    numFragments: ").append(toIndentedString(numFragments)).append("\n");
    sb.append("    numSmallFragments: ").append(toIndentedString(numSmallFragments)).append("\n");
    sb.append("    lengths: ").append(toIndentedString(lengths)).append("\n");
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

