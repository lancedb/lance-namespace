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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** AlterTableAddColumnsRequest */
@JsonPropertyOrder({
  AlterTableAddColumnsRequest.JSON_PROPERTY_ID,
  AlterTableAddColumnsRequest.JSON_PROPERTY_NEW_COLUMNS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableAddColumnsRequest {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_NEW_COLUMNS = "new_columns";
  @javax.annotation.Nonnull private List<NewColumnTransform> newColumns = new ArrayList<>();

  public AlterTableAddColumnsRequest() {}

  public AlterTableAddColumnsRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public AlterTableAddColumnsRequest addIdItem(String idItem) {
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
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public AlterTableAddColumnsRequest newColumns(
      @javax.annotation.Nonnull List<NewColumnTransform> newColumns) {

    this.newColumns = newColumns;
    return this;
  }

  public AlterTableAddColumnsRequest addNewColumnsItem(NewColumnTransform newColumnsItem) {
    if (this.newColumns == null) {
      this.newColumns = new ArrayList<>();
    }
    this.newColumns.add(newColumnsItem);
    return this;
  }

  /**
   * List of new columns to add
   *
   * @return newColumns
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NEW_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<NewColumnTransform> getNewColumns() {
    return newColumns;
  }

  @JsonProperty(JSON_PROPERTY_NEW_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNewColumns(@javax.annotation.Nonnull List<NewColumnTransform> newColumns) {
    this.newColumns = newColumns;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterTableAddColumnsRequest alterTableAddColumnsRequest = (AlterTableAddColumnsRequest) o;
    return Objects.equals(this.id, alterTableAddColumnsRequest.id)
        && Objects.equals(this.newColumns, alterTableAddColumnsRequest.newColumns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, newColumns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableAddColumnsRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    newColumns: ").append(toIndentedString(newColumns)).append("\n");
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

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `new_columns` to the URL query string
    if (getNewColumns() != null) {
      for (int i = 0; i < getNewColumns().size(); i++) {
        if (getNewColumns().get(i) != null) {
          joiner.add(
              getNewColumns()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%snew_columns%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}
