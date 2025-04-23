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

/** CreateTableRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableRequest {

  private String name;

  /** Gets or Sets mode */
  public enum ModeEnum {
    CREATE("CREATE"),

    EXIST_OK("EXIST_OK"),

    OVERWRITE("OVERWRITE");

    private String value;

    ModeEnum(String value) {
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
    public static ModeEnum fromValue(String value) {
      for (ModeEnum b : ModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ModeEnum mode = ModeEnum.CREATE;

  /** Gets or Sets type */
  public enum TypeEnum {
    STORAGE_MANAGED("STORAGE_MANAGED"),

    CATALOG_MANAGED("CATALOG_MANAGED");

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

  private TypeEnum type = TypeEnum.STORAGE_MANAGED;

  private String location;

  private Schema schema;

  private WriterVersion writerVersion;

  @Valid private Map<String, String> config = new HashMap<>();

  public CreateTableRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateTableRequest(String name, Schema schema) {
    this.name = name;
    this.schema = schema;
  }

  public CreateTableRequest name(String name) {
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

  public CreateTableRequest mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Get mode
   *
   * @return mode
   */
  @Schema(name = "mode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  public CreateTableRequest type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   *
   * @return type
   */
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public CreateTableRequest location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location
   */
  @Schema(name = "location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public CreateTableRequest schema(Schema schema) {
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   *
   * @return schema
   */
  @NotNull
  @Valid
  @Schema(name = "schema", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("schema")
  public Schema getSchema() {
    return schema;
  }

  public void setSchema(Schema schema) {
    this.schema = schema;
  }

  public CreateTableRequest writerVersion(WriterVersion writerVersion) {
    this.writerVersion = writerVersion;
    return this;
  }

  /**
   * Get writerVersion
   *
   * @return writerVersion
   */
  @Valid
  @Schema(name = "writerVersion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("writerVersion")
  public WriterVersion getWriterVersion() {
    return writerVersion;
  }

  public void setWriterVersion(WriterVersion writerVersion) {
    this.writerVersion = writerVersion;
  }

  public CreateTableRequest config(Map<String, String> config) {
    this.config = config;
    return this;
  }

  public CreateTableRequest putConfigItem(String key, String configItem) {
    if (this.config == null) {
      this.config = new HashMap<>();
    }
    this.config.put(key, configItem);
    return this;
  }

  /**
   * optional configurations for the table. Keys with the prefix \"lance.\" are reserved for the
   * Lance library. Other libraries may wish to similarly prefix their configuration keys
   * appropriately.
   *
   * @return config
   */
  @Schema(
      name = "config",
      description =
          "optional configurations for the table. Keys with the prefix \"lance.\" are reserved for the Lance library.  Other libraries may wish to similarly prefix their configuration keys appropriately. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("config")
  public Map<String, String> getConfig() {
    return config;
  }

  public void setConfig(Map<String, String> config) {
    this.config = config;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTableRequest createTableRequest = (CreateTableRequest) o;
    return Objects.equals(this.name, createTableRequest.name)
        && Objects.equals(this.mode, createTableRequest.mode)
        && Objects.equals(this.type, createTableRequest.type)
        && Objects.equals(this.location, createTableRequest.location)
        && Objects.equals(this.schema, createTableRequest.schema)
        && Objects.equals(this.writerVersion, createTableRequest.writerVersion)
        && Objects.equals(this.config, createTableRequest.config);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, mode, type, location, schema, writerVersion, config);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableRequest {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    writerVersion: ").append(toIndentedString(writerVersion)).append("\n");
    sb.append("    config: ").append(toIndentedString(config)).append("\n");
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
