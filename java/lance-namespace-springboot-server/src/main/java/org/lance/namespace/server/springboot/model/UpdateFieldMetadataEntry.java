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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** UpdateFieldMetadataEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateFieldMetadataEntry {

  private String path;

  @Valid private Map<String, String> metadata = new HashMap<>();

  private Boolean replace = null;

  public UpdateFieldMetadataEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateFieldMetadataEntry(String path, Map<String, String> metadata) {
    this.path = path;
    this.metadata = metadata;
  }

  public UpdateFieldMetadataEntry path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Field (column) path whose metadata to update
   *
   * @return path
   */
  @NotNull
  @Schema(
      name = "path",
      description = "Field (column) path whose metadata to update",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public UpdateFieldMetadataEntry metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public UpdateFieldMetadataEntry putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Metadata key-value pairs to apply to the field. A null value deletes that key.
   *
   * @return metadata
   */
  @NotNull
  @Schema(
      name = "metadata",
      description =
          "Metadata key-value pairs to apply to the field. A null value deletes that key. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("metadata")
  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }

  public UpdateFieldMetadataEntry replace(Boolean replace) {
    this.replace = replace;
    return this;
  }

  /**
   * If true, replace the field's existing metadata entirely; otherwise merge into it (optional,
   * defaults to false).
   *
   * @return replace
   */
  @Schema(
      name = "replace",
      description =
          "If true, replace the field's existing metadata entirely; otherwise merge into it (optional, defaults to false). ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("replace")
  public Boolean getReplace() {
    return replace;
  }

  public void setReplace(Boolean replace) {
    this.replace = replace;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateFieldMetadataEntry updateFieldMetadataEntry = (UpdateFieldMetadataEntry) o;
    return Objects.equals(this.path, updateFieldMetadataEntry.path)
        && Objects.equals(this.metadata, updateFieldMetadataEntry.metadata)
        && Objects.equals(this.replace, updateFieldMetadataEntry.replace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, metadata, replace);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateFieldMetadataEntry {\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    replace: ").append(toIndentedString(replace)).append("\n");
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
