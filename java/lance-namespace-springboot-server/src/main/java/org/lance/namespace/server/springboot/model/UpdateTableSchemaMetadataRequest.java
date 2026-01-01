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

/** UpdateTableSchemaMetadataRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateTableSchemaMetadataRequest {

  private Identity identity;

  @Valid private List<String> id = new ArrayList<>();

  @Valid private Map<String, String> metadata = new HashMap<>();

  public UpdateTableSchemaMetadataRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @Valid
  @Schema(name = "identity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identity")
  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public UpdateTableSchemaMetadataRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public UpdateTableSchemaMetadataRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * The table identifier
   *
   * @return id
   */
  @Schema(
      name = "id",
      description = "The table identifier",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public UpdateTableSchemaMetadataRequest metadata(Map<String, String> metadata) {
    this.metadata = metadata;
    return this;
  }

  public UpdateTableSchemaMetadataRequest putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * Schema metadata key-value pairs to set
   *
   * @return metadata
   */
  @Schema(
      name = "metadata",
      description = "Schema metadata key-value pairs to set",
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
    UpdateTableSchemaMetadataRequest updateTableSchemaMetadataRequest =
        (UpdateTableSchemaMetadataRequest) o;
    return Objects.equals(this.identity, updateTableSchemaMetadataRequest.identity)
        && Objects.equals(this.id, updateTableSchemaMetadataRequest.id)
        && Objects.equals(this.metadata, updateTableSchemaMetadataRequest.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, id, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTableSchemaMetadataRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
