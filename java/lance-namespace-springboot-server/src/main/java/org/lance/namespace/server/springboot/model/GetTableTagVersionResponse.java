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
 * GetTableTagVersionResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class GetTableTagVersionResponse {

  private Long version;

  public GetTableTagVersionResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GetTableTagVersionResponse(Long version) {
    this.version = version;
  }

  public GetTableTagVersionResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * version number that the tag points to
   * minimum: 0
   * @return version
   */
  @NotNull @Min(0L) 
  @Schema(name = "version", description = "version number that the tag points to", requiredMode = Schema.RequiredMode.REQUIRED)
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
    GetTableTagVersionResponse getTableTagVersionResponse = (GetTableTagVersionResponse) o;
    return Objects.equals(this.version, getTableTagVersionResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetTableTagVersionResponse {\n");
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

