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
 * AlterTableAddColumnsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AlterTableAddColumnsResponse {

  private Long version;

  public AlterTableAddColumnsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AlterTableAddColumnsResponse(Long version) {
    this.version = version;
  }

  public AlterTableAddColumnsResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation
   * minimum: 0
   * @return version
   */
  @NotNull @Min(0L) 
  @Schema(name = "version", description = "The commit version associated with the operation", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
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
    AlterTableAddColumnsResponse alterTableAddColumnsResponse = (AlterTableAddColumnsResponse) o;
    return Objects.equals(this.version, alterTableAddColumnsResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableAddColumnsResponse {\n");
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

