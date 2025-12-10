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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** RenameTableRequest */
@JsonPropertyOrder({
  RenameTableRequest.JSON_PROPERTY_ID,
  RenameTableRequest.JSON_PROPERTY_NEW_TABLE_NAME,
  RenameTableRequest.JSON_PROPERTY_NEW_NAMESPACE_ID
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class RenameTableRequest {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_NEW_TABLE_NAME = "new_table_name";
  @javax.annotation.Nonnull private String newTableName;

  public static final String JSON_PROPERTY_NEW_NAMESPACE_ID = "new_namespace_id";
  @javax.annotation.Nullable private List<String> newNamespaceId = new ArrayList<>();

  public RenameTableRequest() {}

  public RenameTableRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public RenameTableRequest addIdItem(String idItem) {
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

  public RenameTableRequest newTableName(@javax.annotation.Nonnull String newTableName) {

    this.newTableName = newTableName;
    return this;
  }

  /**
   * New name for the table
   *
   * @return newTableName
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NEW_TABLE_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getNewTableName() {
    return newTableName;
  }

  @JsonProperty(JSON_PROPERTY_NEW_TABLE_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNewTableName(@javax.annotation.Nonnull String newTableName) {
    this.newTableName = newTableName;
  }

  public RenameTableRequest newNamespaceId(@javax.annotation.Nullable List<String> newNamespaceId) {

    this.newNamespaceId = newNamespaceId;
    return this;
  }

  public RenameTableRequest addNewNamespaceIdItem(String newNamespaceIdItem) {
    if (this.newNamespaceId == null) {
      this.newNamespaceId = new ArrayList<>();
    }
    this.newNamespaceId.add(newNamespaceIdItem);
    return this;
  }

  /**
   * New namespace identifier to move the table to (optional, if not specified the table stays in
   * the same namespace)
   *
   * @return newNamespaceId
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NEW_NAMESPACE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getNewNamespaceId() {
    return newNamespaceId;
  }

  @JsonProperty(JSON_PROPERTY_NEW_NAMESPACE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNewNamespaceId(@javax.annotation.Nullable List<String> newNamespaceId) {
    this.newNamespaceId = newNamespaceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RenameTableRequest renameTableRequest = (RenameTableRequest) o;
    return Objects.equals(this.id, renameTableRequest.id)
        && Objects.equals(this.newTableName, renameTableRequest.newTableName)
        && Objects.equals(this.newNamespaceId, renameTableRequest.newNamespaceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, newTableName, newNamespaceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RenameTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    newTableName: ").append(toIndentedString(newTableName)).append("\n");
    sb.append("    newNamespaceId: ").append(toIndentedString(newNamespaceId)).append("\n");
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

    // add `new_table_name` to the URL query string
    if (getNewTableName() != null) {
      try {
        joiner.add(
            String.format(
                "%snew_table_name%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNewTableName()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `new_namespace_id` to the URL query string
    if (getNewNamespaceId() != null) {
      for (int i = 0; i < getNewNamespaceId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%snew_namespace_id%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getNewNamespaceId().get(i)), "UTF-8")
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
