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
import com.fasterxml.jackson.annotation.JsonTypeName;
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

/** Optional columns to return. Provide either column_names or column_aliases, not both. */
@Schema(
    name = "QueryTableRequest_columns",
    description =
        "Optional columns to return. Provide either column_names or column_aliases, not both. ")
@JsonTypeName("QueryTableRequest_columns")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableRequestColumns {

  @Valid private List<String> columnNames = new ArrayList<>();

  @Valid private Map<String, String> columnAliases = new HashMap<>();

  public QueryTableRequestColumns columnNames(List<String> columnNames) {
    this.columnNames = columnNames;
    return this;
  }

  public QueryTableRequestColumns addColumnNamesItem(String columnNamesItem) {
    if (this.columnNames == null) {
      this.columnNames = new ArrayList<>();
    }
    this.columnNames.add(columnNamesItem);
    return this;
  }

  /**
   * List of column names to return
   *
   * @return columnNames
   */
  @Schema(
      name = "column_names",
      description = "List of column names to return",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("column_names")
  public List<String> getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(List<String> columnNames) {
    this.columnNames = columnNames;
  }

  public QueryTableRequestColumns columnAliases(Map<String, String> columnAliases) {
    this.columnAliases = columnAliases;
    return this;
  }

  public QueryTableRequestColumns putColumnAliasesItem(String key, String columnAliasesItem) {
    if (this.columnAliases == null) {
      this.columnAliases = new HashMap<>();
    }
    this.columnAliases.put(key, columnAliasesItem);
    return this;
  }

  /**
   * Object mapping output aliases to source column names
   *
   * @return columnAliases
   */
  @Schema(
      name = "column_aliases",
      description = "Object mapping output aliases to source column names",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("column_aliases")
  public Map<String, String> getColumnAliases() {
    return columnAliases;
  }

  public void setColumnAliases(Map<String, String> columnAliases) {
    this.columnAliases = columnAliases;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryTableRequestColumns queryTableRequestColumns = (QueryTableRequestColumns) o;
    return Objects.equals(this.columnNames, queryTableRequestColumns.columnNames)
        && Objects.equals(this.columnAliases, queryTableRequestColumns.columnAliases);
  }

  @Override
  public int hashCode() {
    return Objects.hash(columnNames, columnAliases);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryTableRequestColumns {\n");
    sb.append("    columnNames: ").append(toIndentedString(columnNames)).append("\n");
    sb.append("    columnAliases: ").append(toIndentedString(columnAliases)).append("\n");
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
