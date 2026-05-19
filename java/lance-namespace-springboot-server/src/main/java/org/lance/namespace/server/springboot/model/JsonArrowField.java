package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.HashMap;
import java.util.Map;
import org.lance.namespace.server.springboot.model.JsonArrowDataType;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * JSON representation of an Apache Arrow field. 
 */

@Schema(name = "JsonArrowField", description = "JSON representation of an Apache Arrow field. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class JsonArrowField {

  @Valid
  private Map<String, String> metadata = new HashMap<>();

  private String name;

  private Boolean nullable;

  private JsonArrowDataType type;

  public JsonArrowField() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public JsonArrowField(String name, Boolean nullable, JsonArrowDataType type) {
    this.name = name;
    this.nullable = nullable;
    this.type = type;
  }

  public JsonArrowField metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public JsonArrowField putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Get metadata
   * @return metadata
   */
  
  @Schema(name = "metadata", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public JsonArrowField name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull 
  @Schema(name = "name", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public JsonArrowField nullable(Boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  /**
   * Get nullable
   * @return nullable
   */
  @NotNull 
  @Schema(name = "nullable", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nullable")
  public Boolean getNullable() {
    return nullable;
  }

  public void setNullable(Boolean nullable) {
    this.nullable = nullable;
  }

  public JsonArrowField type(JsonArrowDataType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull @Valid 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public JsonArrowDataType getType() {
    return type;
  }

  public void setType(JsonArrowDataType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonArrowField jsonArrowField = (JsonArrowField) o;
    return Objects.equals(this.metadata, jsonArrowField.metadata) &&
        Objects.equals(this.name, jsonArrowField.name) &&
        Objects.equals(this.nullable, jsonArrowField.nullable) &&
        Objects.equals(this.type, jsonArrowField.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, name, nullable, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonArrowField {\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

