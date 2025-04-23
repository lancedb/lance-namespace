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
package com.lancedb.lance.catalog.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Field */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class Field {

  /** Gets or Sets type */
  public enum TypeEnum {
    PARENT("PARENT"),

    REPEATED("REPEATED"),

    LEAF("LEAF");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private String name;

  private Integer id = null;

  private Integer parentId = null;

  private String logicalType;

  private Boolean nullable;

  @Valid private Map<String, String> metadata = new HashMap<>();

  public Field() {
    super();
  }

  /** Constructor with only required parameters */
  public Field(TypeEnum type, String name, Integer id, String logicalType) {
    this.type = type;
    this.name = name;
    this.id = id;
    this.logicalType = logicalType;
  }

  public Field type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @NotNull
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Field name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   *
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

  public Field id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @NotNull
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Field parentId(Integer parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * Get parentId
   *
   * @return parentId
   */
  @Schema(name = "parentId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("parentId")
  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Field logicalType(String logicalType) {
    this.logicalType = logicalType;
    return this;
  }

  /**
   * Logical types, currently support parameterized Arrow Type. PARENT types will always have
   * logical type \"struct\". Logical type \"map\" is represented as a struct with a single child
   * field \"entries\". \"entries\" is a struct with fields \"key\" and \"value\". REPEATED types
   * may have logical types: * \"list\" * \"large_list\" * \"list.struct\" * \"large_list.struct\"
   * The final two are used if the list values are structs, and therefore the field is both
   * implicitly REPEATED and PARENT. LEAF types may have logical types: * \"null\" * \"bool\" *
   * \"int8\" / \"uint8\" * \"int16\" / \"uint16\" * \"int32\" / \"uint32\" * \"int64\" / \"uint64\"
   * * \"halffloat\" / \"float\" / \"double\" * \"string\" / \"large_string\" * \"binary\" /
   * \"large_binary\" * \"date32:day\" * \"date64:ms\" * \"decimal:128:{precision}:{scale}\" /
   * \"decimal:256:{precision}:{scale}\" * \"time:{unit}\" / \"timestamp:{unit}\" /
   * \"duration:{unit}\", where unit is \"s\", \"ms\", \"us\", \"ns\" *
   * \"dict:{value_type}:{index_type}:false\"
   *
   * @return logicalType
   */
  @NotNull
  @Schema(
      name = "logicalType",
      description =
          "Logical types, currently support parameterized Arrow Type. PARENT types will always have logical type \"struct\". Logical type \"map\" is represented as a struct with a single child field \"entries\". \"entries\" is a struct with fields \"key\" and \"value\". REPEATED types may have logical types: * \"list\" * \"large_list\" * \"list.struct\" * \"large_list.struct\" The final two are used if the list values are structs, and therefore the field is both implicitly REPEATED and PARENT. LEAF types may have logical types: * \"null\" * \"bool\" * \"int8\" / \"uint8\" * \"int16\" / \"uint16\" * \"int32\" / \"uint32\" * \"int64\" / \"uint64\" * \"halffloat\" / \"float\" / \"double\" * \"string\" / \"large_string\" * \"binary\" / \"large_binary\" * \"date32:day\" * \"date64:ms\" * \"decimal:128:{precision}:{scale}\" / \"decimal:256:{precision}:{scale}\" * \"time:{unit}\" / \"timestamp:{unit}\" / \"duration:{unit}\", where unit is \"s\", \"ms\", \"us\", \"ns\" * \"dict:{value_type}:{index_type}:false\" ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("logicalType")
  public String getLogicalType() {
    return logicalType;
  }

  public void setLogicalType(String logicalType) {
    this.logicalType = logicalType;
  }

  public Field nullable(Boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  /**
   * Get nullable
   *
   * @return nullable
   */
  @Schema(name = "nullable", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nullable")
  public Boolean getNullable() {
    return nullable;
  }

  public void setNullable(Boolean nullable) {
    this.nullable = nullable;
  }

  public Field metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public Field putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * optional field metadata (e.g. extension type name/parameters)
   *
   * @return metadata
   */
  @Schema(
      name = "metadata",
      description = "optional field metadata (e.g. extension type name/parameters)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Field field = (Field) o;
    return Objects.equals(this.type, field.type)
        && Objects.equals(this.name, field.name)
        && Objects.equals(this.id, field.id)
        && Objects.equals(this.parentId, field.parentId)
        && Objects.equals(this.logicalType, field.logicalType)
        && Objects.equals(this.nullable, field.nullable)
        && Objects.equals(this.metadata, field.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, id, parentId, logicalType, nullable, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Field {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    logicalType: ").append(toIndentedString(logicalType)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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
}
