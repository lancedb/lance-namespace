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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** DescribeTableResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableResponse {

  private Long version;

  private String location;

  private JsonSchema schema;

  @Valid private Map<String, String> properties = new HashMap<>();

  @Valid private Map<String, String> storageOptions = new HashMap<>();

  public DescribeTableResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public DescribeTableResponse(String location, JsonSchema schema) {
    this.location = location;
    this.schema = schema;
  }

  public DescribeTableResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * Get version minimum: 0
   *
   * @return version
   */
  @Min(0L)
  @Schema(name = "version", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public DescribeTableResponse location(String location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location
   */
  @NotNull
  @Schema(name = "location", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("location")
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public DescribeTableResponse schema(JsonSchema schema) {
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
  public JsonSchema getSchema() {
    return schema;
  }

  public void setSchema(JsonSchema schema) {
    this.schema = schema;
  }

  public DescribeTableResponse properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public DescribeTableResponse putPropertiesItem(String key, String propertiesItem) {
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

  public DescribeTableResponse storageOptions(Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
    return this;
  }

  public DescribeTableResponse putStorageOptionsItem(String key, String storageOptionsItem) {
    if (this.storageOptions == null) {
      this.storageOptions = new HashMap<>();
    }
    this.storageOptions.put(key, storageOptionsItem);
    return this;
  }

  /**
   * Get storageOptions
   *
   * @return storageOptions
   */
  @Schema(name = "storage_options", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("storage_options")
  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  public void setStorageOptions(Map<String, String> storageOptions) {
    this.storageOptions = storageOptions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableResponse describeTableResponse = (DescribeTableResponse) o;
    return Objects.equals(this.version, describeTableResponse.version)
        && Objects.equals(this.location, describeTableResponse.location)
        && Objects.equals(this.schema, describeTableResponse.schema)
        && Objects.equals(this.properties, describeTableResponse.properties)
        && Objects.equals(this.storageOptions, describeTableResponse.storageOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, location, schema, properties, storageOptions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    storageOptions: ").append(toIndentedString(storageOptions)).append("\n");
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
