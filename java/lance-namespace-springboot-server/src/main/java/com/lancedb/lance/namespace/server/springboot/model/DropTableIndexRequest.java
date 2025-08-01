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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** DropTableIndexRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class DropTableIndexRequest {

  @Valid private List<String> id = new ArrayList<>();

  private String indexName;

  public DropTableIndexRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public DropTableIndexRequest(List<String> id, String indexName) {
    this.id = id;
    this.indexName = indexName;
  }

  public DropTableIndexRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public DropTableIndexRequest addIdItem(String idItem) {
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
  @NotNull
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public DropTableIndexRequest indexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  /**
   * Name of the index to drop
   *
   * @return indexName
   */
  @NotNull
  @Schema(
      name = "index_name",
      description = "Name of the index to drop",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("index_name")
  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DropTableIndexRequest dropTableIndexRequest = (DropTableIndexRequest) o;
    return Objects.equals(this.id, dropTableIndexRequest.id)
        && Objects.equals(this.indexName, dropTableIndexRequest.indexName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, indexName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DropTableIndexRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
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
