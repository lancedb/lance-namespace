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

/** UpdateFieldMetadataResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateFieldMetadataResponse {

  private Long version;

  @Valid private Map<String, Map<String, String>> fields = new HashMap<>();

  public UpdateFieldMetadataResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateFieldMetadataResponse(Long version) {
    this.version = version;
  }

  public UpdateFieldMetadataResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "version",
      description = "The commit version associated with the operation",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public UpdateFieldMetadataResponse fields(Map<String, Map<String, String>> fields) {
    this.fields = fields;
    return this;
  }

  public UpdateFieldMetadataResponse putFieldsItem(String key, Map<String, String> fieldsItem) {
    if (this.fields == null) {
      this.fields = new HashMap<>();
    }
    this.fields.put(key, fieldsItem);
    return this;
  }

  /**
   * Resulting metadata for each updated field, keyed by field path.
   *
   * @return fields
   */
  @Valid
  @Schema(
      name = "fields",
      description = "Resulting metadata for each updated field, keyed by field path. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fields")
  public Map<String, Map<String, String>> getFields() {
    return fields;
  }

  public void setFields(Map<String, Map<String, String>> fields) {
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
    UpdateFieldMetadataResponse updateFieldMetadataResponse = (UpdateFieldMetadataResponse) o;
    return Objects.equals(this.version, updateFieldMetadataResponse.version)
        && Objects.equals(this.fields, updateFieldMetadataResponse.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, fields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateFieldMetadataResponse {\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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
