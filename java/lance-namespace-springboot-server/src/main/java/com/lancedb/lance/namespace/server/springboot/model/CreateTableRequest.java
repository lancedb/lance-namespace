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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Request for creating a table, excluding the Arrow IPC stream. */
@Schema(
    name = "CreateTableRequest",
    description = "Request for creating a table, excluding the Arrow IPC stream. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateTableRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String location;

  /**
   * There are three modes when trying to create a table, to differentiate the behavior when a table
   * of the same name already exists: * create: the operation fails with 409. * exist_ok: the
   * operation succeeds and the existing table is kept. * overwrite: the existing table is dropped
   * and a new table with this name is created.
   */
  public enum ModeEnum {
    CREATE("create"),

    EXIST_OK("exist_ok"),

    OVERWRITE("overwrite");

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

  private ModeEnum mode;

  @Valid private Map<String, String> properties = new HashMap<>();

  public CreateTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public CreateTableRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
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

  public CreateTableRequest mode(ModeEnum mode) {
    this.mode = mode;
    return this;
  }

  /**
   * There are three modes when trying to create a table, to differentiate the behavior when a table
   * of the same name already exists: * create: the operation fails with 409. * exist_ok: the
   * operation succeeds and the existing table is kept. * overwrite: the existing table is dropped
   * and a new table with this name is created.
   *
   * @return mode
   */
  @Schema(
      name = "mode",
      description =
          "There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists:   * create: the operation fails with 409.   * exist_ok: the operation succeeds and the existing table is kept.   * overwrite: the existing table is dropped and a new table with this name is created. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mode")
  public ModeEnum getMode() {
    return mode;
  }

  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }

  public CreateTableRequest properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public CreateTableRequest putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   *
   * @return properties
   */
  @Schema(name = "properties", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
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
    return Objects.equals(this.id, createTableRequest.id)
        && Objects.equals(this.location, createTableRequest.location)
        && Objects.equals(this.mode, createTableRequest.mode)
        && Objects.equals(this.properties, createTableRequest.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, location, mode, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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
