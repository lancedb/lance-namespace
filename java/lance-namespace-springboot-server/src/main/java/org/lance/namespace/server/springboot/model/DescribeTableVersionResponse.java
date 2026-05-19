package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.lance.namespace.server.springboot.model.TableVersion;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Response containing the table version information
 */

@Schema(name = "DescribeTableVersionResponse", description = "Response containing the table version information")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class DescribeTableVersionResponse {

  private TableVersion version;

  public DescribeTableVersionResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DescribeTableVersionResponse(TableVersion version) {
    this.version = version;
  }

  public DescribeTableVersionResponse version(TableVersion version) {
    this.version = version;
    return this;
  }

  /**
   * The table version information
   * @return version
   */
  @NotNull @Valid 
  @Schema(name = "version", description = "The table version information", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public TableVersion getVersion() {
    return version;
  }

  public void setVersion(TableVersion version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableVersionResponse describeTableVersionResponse = (DescribeTableVersionResponse) o;
    return Objects.equals(this.version, describeTableVersionResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableVersionResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

