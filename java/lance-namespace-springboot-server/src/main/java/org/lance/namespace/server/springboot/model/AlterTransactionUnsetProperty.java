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
 * AlterTransactionUnsetProperty
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class AlterTransactionUnsetProperty {

  private String key;

  private String mode;

  public AlterTransactionUnsetProperty key(String key) {
    this.key = key;
    return this;
  }

  /**
   * Get key
   * @return key
   */
  
  @Schema(name = "key", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("key")
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public AlterTransactionUnsetProperty mode(String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * The behavior if the property key to unset does not exist. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Skip (default): skip the property to unset - Fail: fail the entire operation 
   * @return mode
   */
  
  @Schema(name = "mode", description = "The behavior if the property key to unset does not exist. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Skip (default): skip the property to unset - Fail: fail the entire operation ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTransactionUnsetProperty alterTransactionUnsetProperty = (AlterTransactionUnsetProperty) o;
    return Objects.equals(this.key, alterTransactionUnsetProperty.key) &&
        Objects.equals(this.mode, alterTransactionUnsetProperty.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionUnsetProperty {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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

