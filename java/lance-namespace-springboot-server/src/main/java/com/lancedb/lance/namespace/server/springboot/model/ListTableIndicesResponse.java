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

/** ListTableIndicesResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class ListTableIndicesResponse {

  @Valid private List<@Valid IndexListItemResponse> indexes = new ArrayList<>();

  public ListTableIndicesResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public ListTableIndicesResponse(List<@Valid IndexListItemResponse> indexes) {
    this.indexes = indexes;
  }

  public ListTableIndicesResponse indexes(List<@Valid IndexListItemResponse> indexes) {
    this.indexes = indexes;
    return this;
  }

  public ListTableIndicesResponse addIndexesItem(IndexListItemResponse indexesItem) {
    if (this.indexes == null) {
      this.indexes = new ArrayList<>();
    }
    this.indexes.add(indexesItem);
    return this;
  }

  /**
   * List of indexes on the table
   *
   * @return indexes
   */
  @NotNull
  @Valid
  @Schema(
      name = "indexes",
      description = "List of indexes on the table",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("indexes")
  public List<@Valid IndexListItemResponse> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<@Valid IndexListItemResponse> indexes) {
    this.indexes = indexes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListTableIndicesResponse listTableIndicesResponse = (ListTableIndicesResponse) o;
    return Objects.equals(this.indexes, listTableIndicesResponse.indexes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListTableIndicesResponse {\n");
    sb.append("    indexes: ").append(toIndentedString(indexes)).append("\n");
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
