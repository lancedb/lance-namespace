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
package com.lancedb.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** StringFtsQuery */
@JsonPropertyOrder({StringFtsQuery.JSON_PROPERTY_COLUMNS, StringFtsQuery.JSON_PROPERTY_QUERY})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class StringFtsQuery {
  public static final String JSON_PROPERTY_COLUMNS = "columns";

  @javax.annotation.Nullable
  private JsonNullable<List<String>> columns = JsonNullable.<List<String>>undefined();

  public static final String JSON_PROPERTY_QUERY = "query";
  @javax.annotation.Nonnull private String query;

  public StringFtsQuery() {}

  public StringFtsQuery columns(@javax.annotation.Nullable List<String> columns) {
    this.columns = JsonNullable.<List<String>>of(columns);

    return this;
  }

  public StringFtsQuery addColumnsItem(String columnsItem) {
    if (this.columns == null || !this.columns.isPresent()) {
      this.columns = JsonNullable.<List<String>>of(new ArrayList<>());
    }
    try {
      this.columns.get().add(columnsItem);
    } catch (java.util.NoSuchElementException e) {
      // this can never happen, as we make sure above that the value is present
    }
    return this;
  }

  /**
   * Get columns
   *
   * @return columns
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public List<String> getColumns() {
    return columns.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<List<String>> getColumns_JsonNullable() {
    return columns;
  }

  @JsonProperty(JSON_PROPERTY_COLUMNS)
  public void setColumns_JsonNullable(JsonNullable<List<String>> columns) {
    this.columns = columns;
  }

  public void setColumns(@javax.annotation.Nullable List<String> columns) {
    this.columns = JsonNullable.<List<String>>of(columns);
  }

  public StringFtsQuery query(@javax.annotation.Nonnull String query) {

    this.query = query;
    return this;
  }

  /**
   * Get query
   *
   * @return query
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_QUERY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getQuery() {
    return query;
  }

  @JsonProperty(JSON_PROPERTY_QUERY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setQuery(@javax.annotation.Nonnull String query) {
    this.query = query;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StringFtsQuery stringFtsQuery = (StringFtsQuery) o;
    return equalsNullable(this.columns, stringFtsQuery.columns)
        && Objects.equals(this.query, stringFtsQuery.query);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(hashCodeNullable(columns), query);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StringFtsQuery {\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
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

    // add `columns` to the URL query string
    if (getColumns() != null) {
      for (int i = 0; i < getColumns().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%scolumns%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getColumns().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `query` to the URL query string
    if (getQuery() != null) {
      try {
        joiner.add(
            String.format(
                "%squery%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getQuery()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
