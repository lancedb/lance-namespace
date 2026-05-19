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
 * Well-known partition transform
 */

@Schema(name = "PartitionTransform", description = "Well-known partition transform")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class PartitionTransform {

  private String type;

  private Integer numBuckets;

  private Integer width;

  public PartitionTransform() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PartitionTransform(String type) {
    this.type = type;
  }

  public PartitionTransform type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Transform type (identity, year, month, day, hour, bucket, multi_bucket, truncate)
   * @return type
   */
  @NotNull 
  @Schema(name = "type", description = "Transform type (identity, year, month, day, hour, bucket, multi_bucket, truncate)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public PartitionTransform numBuckets(Integer numBuckets) {
    this.numBuckets = numBuckets;
    return this;
  }

  /**
   * Number of buckets for bucket transforms
   * @return numBuckets
   */
  
  @Schema(name = "num_buckets", description = "Number of buckets for bucket transforms", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_buckets")
  public Integer getNumBuckets() {
    return numBuckets;
  }

  public void setNumBuckets(Integer numBuckets) {
    this.numBuckets = numBuckets;
  }

  public PartitionTransform width(Integer width) {
    this.width = width;
    return this;
  }

  /**
   * Truncation width for truncate transforms
   * @return width
   */
  
  @Schema(name = "width", description = "Truncation width for truncate transforms", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("width")
  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartitionTransform partitionTransform = (PartitionTransform) o;
    return Objects.equals(this.type, partitionTransform.type) &&
        Objects.equals(this.numBuckets, partitionTransform.numBuckets) &&
        Objects.equals(this.width, partitionTransform.width);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, numBuckets, width);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartitionTransform {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    numBuckets: ").append(toIndentedString(numBuckets)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
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

