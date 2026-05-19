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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** Optional columns to return. Provide either column_names or column_aliases, not both. */
@JsonPropertyOrder({
  QueryTableRequestColumns.JSON_PROPERTY_COLUMN_NAMES,
  QueryTableRequestColumns.JSON_PROPERTY_COLUMN_ALIASES
})
@JsonTypeName("QueryTableRequest_columns")
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class QueryTableRequestColumns {
  public static final String JSON_PROPERTY_COLUMN_NAMES = "column_names";
  @javax.annotation.Nullable private List<String> columnNames = new ArrayList<>();

  public static final String JSON_PROPERTY_COLUMN_ALIASES = "column_aliases";
  @javax.annotation.Nullable private Map<String, String> columnAliases = new HashMap<>();

  public QueryTableRequestColumns() {}

  public QueryTableRequestColumns columnNames(@javax.annotation.Nullable List<String> columnNames) {

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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COLUMN_NAMES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getColumnNames() {
    return columnNames;
  }

  @JsonProperty(JSON_PROPERTY_COLUMN_NAMES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setColumnNames(@javax.annotation.Nullable List<String> columnNames) {
    this.columnNames = columnNames;
  }

  public QueryTableRequestColumns columnAliases(
      @javax.annotation.Nullable Map<String, String> columnAliases) {

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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_COLUMN_ALIASES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getColumnAliases() {
    return columnAliases;
  }

  @JsonProperty(JSON_PROPERTY_COLUMN_ALIASES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setColumnAliases(@javax.annotation.Nullable Map<String, String> columnAliases) {
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

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `column_names` to the URL query string
    if (getColumnNames() != null) {
      for (int i = 0; i < getColumnNames().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%scolumn_names%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getColumnNames().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `column_aliases` to the URL query string
    if (getColumnAliases() != null) {
      for (String _key : getColumnAliases().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%scolumn_aliases%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getColumnAliases().get(_key),
                  URLEncoder.encode(String.valueOf(getColumnAliases().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    return joiner.toString();
  }
}
