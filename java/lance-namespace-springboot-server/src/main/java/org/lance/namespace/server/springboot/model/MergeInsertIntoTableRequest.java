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

/** Request for merging or inserting records into a table, excluding the Arrow IPC stream. */
@Schema(
    name = "MergeInsertIntoTableRequest",
    description =
        "Request for merging or inserting records into a table, excluding the Arrow IPC stream. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class MergeInsertIntoTableRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String on;

  private Boolean whenMatchedUpdateAll = false;

  private String whenMatchedUpdateAllFilt;

  private Boolean whenNotMatchedInsertAll = false;

  private Boolean whenNotMatchedBySourceDelete = false;

  private String whenNotMatchedBySourceDeleteFilt;

  public MergeInsertIntoTableRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public MergeInsertIntoTableRequest addIdItem(String idItem) {
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

  public MergeInsertIntoTableRequest on(String on) {
    this.on = on;
    return this;
  }

  /**
   * Column name to use for matching rows (required)
   *
   * @return on
   */
  @Schema(
      name = "on",
      description = "Column name to use for matching rows (required)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("on")
  public String getOn() {
    return on;
  }

  public void setOn(String on) {
    this.on = on;
  }

  public MergeInsertIntoTableRequest whenMatchedUpdateAll(Boolean whenMatchedUpdateAll) {
    this.whenMatchedUpdateAll = whenMatchedUpdateAll;
    return this;
  }

  /**
   * Update all columns when rows match
   *
   * @return whenMatchedUpdateAll
   */
  @Schema(
      name = "when_matched_update_all",
      description = "Update all columns when rows match",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("when_matched_update_all")
  public Boolean getWhenMatchedUpdateAll() {
    return whenMatchedUpdateAll;
  }

  public void setWhenMatchedUpdateAll(Boolean whenMatchedUpdateAll) {
    this.whenMatchedUpdateAll = whenMatchedUpdateAll;
  }

  public MergeInsertIntoTableRequest whenMatchedUpdateAllFilt(String whenMatchedUpdateAllFilt) {
    this.whenMatchedUpdateAllFilt = whenMatchedUpdateAllFilt;
    return this;
  }

  /**
   * The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to
   * true
   *
   * @return whenMatchedUpdateAllFilt
   */
  @Schema(
      name = "when_matched_update_all_filt",
      description =
          "The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("when_matched_update_all_filt")
  public String getWhenMatchedUpdateAllFilt() {
    return whenMatchedUpdateAllFilt;
  }

  public void setWhenMatchedUpdateAllFilt(String whenMatchedUpdateAllFilt) {
    this.whenMatchedUpdateAllFilt = whenMatchedUpdateAllFilt;
  }

  public MergeInsertIntoTableRequest whenNotMatchedInsertAll(Boolean whenNotMatchedInsertAll) {
    this.whenNotMatchedInsertAll = whenNotMatchedInsertAll;
    return this;
  }

  /**
   * Insert all columns when rows don't match
   *
   * @return whenNotMatchedInsertAll
   */
  @Schema(
      name = "when_not_matched_insert_all",
      description = "Insert all columns when rows don't match",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("when_not_matched_insert_all")
  public Boolean getWhenNotMatchedInsertAll() {
    return whenNotMatchedInsertAll;
  }

  public void setWhenNotMatchedInsertAll(Boolean whenNotMatchedInsertAll) {
    this.whenNotMatchedInsertAll = whenNotMatchedInsertAll;
  }

  public MergeInsertIntoTableRequest whenNotMatchedBySourceDelete(
      Boolean whenNotMatchedBySourceDelete) {
    this.whenNotMatchedBySourceDelete = whenNotMatchedBySourceDelete;
    return this;
  }

  /**
   * Delete all rows from target table that don't match a row in the source table
   *
   * @return whenNotMatchedBySourceDelete
   */
  @Schema(
      name = "when_not_matched_by_source_delete",
      description = "Delete all rows from target table that don't match a row in the source table",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("when_not_matched_by_source_delete")
  public Boolean getWhenNotMatchedBySourceDelete() {
    return whenNotMatchedBySourceDelete;
  }

  public void setWhenNotMatchedBySourceDelete(Boolean whenNotMatchedBySourceDelete) {
    this.whenNotMatchedBySourceDelete = whenNotMatchedBySourceDelete;
  }

  public MergeInsertIntoTableRequest whenNotMatchedBySourceDeleteFilt(
      String whenNotMatchedBySourceDeleteFilt) {
    this.whenNotMatchedBySourceDeleteFilt = whenNotMatchedBySourceDeleteFilt;
    return this;
  }

  /**
   * Delete rows from the target table if there is no match AND the SQL expression evaluates to true
   *
   * @return whenNotMatchedBySourceDeleteFilt
   */
  @Schema(
      name = "when_not_matched_by_source_delete_filt",
      description =
          "Delete rows from the target table if there is no match AND the SQL expression evaluates to true",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("when_not_matched_by_source_delete_filt")
  public String getWhenNotMatchedBySourceDeleteFilt() {
    return whenNotMatchedBySourceDeleteFilt;
  }

  public void setWhenNotMatchedBySourceDeleteFilt(String whenNotMatchedBySourceDeleteFilt) {
    this.whenNotMatchedBySourceDeleteFilt = whenNotMatchedBySourceDeleteFilt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MergeInsertIntoTableRequest mergeInsertIntoTableRequest = (MergeInsertIntoTableRequest) o;
    return Objects.equals(this.id, mergeInsertIntoTableRequest.id)
        && Objects.equals(this.on, mergeInsertIntoTableRequest.on)
        && Objects.equals(
            this.whenMatchedUpdateAll, mergeInsertIntoTableRequest.whenMatchedUpdateAll)
        && Objects.equals(
            this.whenMatchedUpdateAllFilt, mergeInsertIntoTableRequest.whenMatchedUpdateAllFilt)
        && Objects.equals(
            this.whenNotMatchedInsertAll, mergeInsertIntoTableRequest.whenNotMatchedInsertAll)
        && Objects.equals(
            this.whenNotMatchedBySourceDelete,
            mergeInsertIntoTableRequest.whenNotMatchedBySourceDelete)
        && Objects.equals(
            this.whenNotMatchedBySourceDeleteFilt,
            mergeInsertIntoTableRequest.whenNotMatchedBySourceDeleteFilt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        on,
        whenMatchedUpdateAll,
        whenMatchedUpdateAllFilt,
        whenNotMatchedInsertAll,
        whenNotMatchedBySourceDelete,
        whenNotMatchedBySourceDeleteFilt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MergeInsertIntoTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    on: ").append(toIndentedString(on)).append("\n");
    sb.append("    whenMatchedUpdateAll: ")
        .append(toIndentedString(whenMatchedUpdateAll))
        .append("\n");
    sb.append("    whenMatchedUpdateAllFilt: ")
        .append(toIndentedString(whenMatchedUpdateAllFilt))
        .append("\n");
    sb.append("    whenNotMatchedInsertAll: ")
        .append(toIndentedString(whenNotMatchedInsertAll))
        .append("\n");
    sb.append("    whenNotMatchedBySourceDelete: ")
        .append(toIndentedString(whenNotMatchedBySourceDelete))
        .append("\n");
    sb.append("    whenNotMatchedBySourceDeleteFilt: ")
        .append(toIndentedString(whenNotMatchedBySourceDeleteFilt))
        .append("\n");
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
