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
import java.util.List;
import java.util.Objects;

/** UpdateFieldMetadataRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateFieldMetadataRequest {

  private Identity identity;

  @Valid private List<String> id = new ArrayList<>();

  private String branch;

  @Valid private List<@Valid UpdateFieldMetadataEntry> updates = new ArrayList<>();

  public UpdateFieldMetadataRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateFieldMetadataRequest(List<@Valid UpdateFieldMetadataEntry> updates) {
    this.updates = updates;
  }

  public UpdateFieldMetadataRequest identity(Identity identity) {
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

  public UpdateFieldMetadataRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public UpdateFieldMetadataRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Table identifier path (namespace + table name)
   *
   * @return id
   */
  @Schema(
      name = "id",
      description = "Table identifier path (namespace + table name)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public UpdateFieldMetadataRequest branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Branch to target. When not specified, the main branch is used.
   *
   * @return branch
   */
  @Schema(
      name = "branch",
      description = "Branch to target. When not specified, the main branch is used. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branch")
  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public UpdateFieldMetadataRequest updates(List<@Valid UpdateFieldMetadataEntry> updates) {
    this.updates = updates;
    return this;
  }

  public UpdateFieldMetadataRequest addUpdatesItem(UpdateFieldMetadataEntry updatesItem) {
    if (this.updates == null) {
      this.updates = new ArrayList<>();
    }
    this.updates.add(updatesItem);
    return this;
  }

  /**
   * List of per-field metadata updates to apply
   *
   * @return updates
   */
  @NotNull
  @Valid
  @Schema(
      name = "updates",
      description = "List of per-field metadata updates to apply",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updates")
  public List<@Valid UpdateFieldMetadataEntry> getUpdates() {
    return updates;
  }

  public void setUpdates(List<@Valid UpdateFieldMetadataEntry> updates) {
    this.updates = updates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateFieldMetadataRequest updateFieldMetadataRequest = (UpdateFieldMetadataRequest) o;
    return Objects.equals(this.identity, updateFieldMetadataRequest.identity)
        && Objects.equals(this.id, updateFieldMetadataRequest.id)
        && Objects.equals(this.branch, updateFieldMetadataRequest.branch)
        && Objects.equals(this.updates, updateFieldMetadataRequest.updates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(identity, id, branch, updates);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateFieldMetadataRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    updates: ").append(toIndentedString(updates)).append("\n");
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
