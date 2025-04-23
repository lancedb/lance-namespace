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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Schema */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class Schema {

  @Valid private Map<String, String> metadata = new HashMap<>();

  @Valid private Set<@Valid Field> fields = new LinkedHashSet<>();

  public Schema() {
    super();
  }

  /** Constructor with only required parameters */
  public Schema(Set<@Valid Field> fields) {
    this.fields = fields;
  }

  public Schema metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public Schema putMetadataItem(String key, String metadataItem) {
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

  public Schema fields(Set<@Valid Field> fields) {
    this.fields = fields;
    return this;
  }

  public Schema addFieldsItem(Field fieldsItem) {
    if (this.fields == null) {
      this.fields = new LinkedHashSet<>();
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
  public Set<@Valid Field> getFields() {
    return fields;
  }

  @JsonDeserialize(as = LinkedHashSet.class)
  public void setFields(Set<@Valid Field> fields) {
    this.fields = fields;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Schema schema = (Schema) o;
    return Objects.equals(this.metadata, schema.metadata)
        && Objects.equals(this.fields, schema.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metadata, fields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Schema {\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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
