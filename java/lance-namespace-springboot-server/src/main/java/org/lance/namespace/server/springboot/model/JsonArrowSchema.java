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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

/** JSON representation of a Apache Arrow schema. */
@Schema(name = "JsonArrowSchema", description = "JSON representation of a Apache Arrow schema. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class JsonArrowSchema {

  @Valid private List<@Valid JsonArrowField> fields = new ArrayList<>();

  @Valid private Map<String, String> metadata = new HashMap<>();

  public JsonArrowSchema() {
    super();
  }

  /** Constructor with only required parameters */
  public JsonArrowSchema(List<@Valid JsonArrowField> fields) {
    this.fields = fields;
  }

  public JsonArrowSchema fields(List<@Valid JsonArrowField> fields) {
    this.fields = fields;
    return this;
  }

  public JsonArrowSchema addFieldsItem(JsonArrowField fieldsItem) {
    if (this.fields == null) {
      this.fields = new ArrayList<>();
    }
    this.fields.add(fieldsItem);
    return this;
  }

  /**
   * Get fields
   *
   * @return fields
   */
  @NotNull
  @Valid
  @Schema(name = "fields", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fields")
  public List<@Valid JsonArrowField> getFields() {
    return fields;
  }

  public void setFields(List<@Valid JsonArrowField> fields) {
    this.fields = fields;
  }

  public JsonArrowSchema metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public JsonArrowSchema putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Get metadata
   *
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonArrowSchema jsonArrowSchema = (JsonArrowSchema) o;
    return Objects.equals(this.fields, jsonArrowSchema.fields)
        && Objects.equals(this.metadata, jsonArrowSchema.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fields, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JsonArrowSchema {\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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
