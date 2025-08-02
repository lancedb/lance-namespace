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

/** IndexContent */
@JsonPropertyOrder({
  IndexContent.JSON_PROPERTY_INDEX_NAME,
  IndexContent.JSON_PROPERTY_INDEX_UUID,
  IndexContent.JSON_PROPERTY_COLUMNS,
  IndexContent.JSON_PROPERTY_STATUS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class IndexContent {
  public static final String JSON_PROPERTY_INDEX_NAME = "index_name";
  @javax.annotation.Nonnull private String indexName;

  public static final String JSON_PROPERTY_INDEX_UUID = "index_uuid";
  @javax.annotation.Nonnull private String indexUuid;

  public static final String JSON_PROPERTY_COLUMNS = "columns";
  @javax.annotation.Nonnull private List<String> columns = new ArrayList<>();

  public static final String JSON_PROPERTY_STATUS = "status";
  @javax.annotation.Nonnull private String status;

  public IndexContent() {}

  public IndexContent indexName(@javax.annotation.Nonnull String indexName) {

    this.indexName = indexName;
    return this;
  }

  /**
   * Name of the index
   *
   * @return indexName
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getIndexName() {
    return indexName;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndexName(@javax.annotation.Nonnull String indexName) {
    this.indexName = indexName;
  }

  public IndexContent indexUuid(@javax.annotation.Nonnull String indexUuid) {

    this.indexUuid = indexUuid;
    return this;
  }

  /**
   * Unique identifier for the index
   *
   * @return indexUuid
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INDEX_UUID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getIndexUuid() {
    return indexUuid;
  }

  @JsonProperty(JSON_PROPERTY_INDEX_UUID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setIndexUuid(@javax.annotation.Nonnull String indexUuid) {
    this.indexUuid = indexUuid;
  }

  public IndexContent columns(@javax.annotation.Nonnull List<String> columns) {

    this.columns = columns;
    return this;
  }

  public IndexContent addColumnsItem(String columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<>();
    }
    this.columns.add(columnsItem);
    return this;
  }

  /**
   * Columns covered by this index
   *
   * @return columns
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getColumns() {
    return columns;
  }

  @JsonProperty(JSON_PROPERTY_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setColumns(@javax.annotation.Nonnull List<String> columns) {
    this.columns = columns;
  }

  public IndexContent status(@javax.annotation.Nonnull String status) {

    this.status = status;
    return this;
  }

  /**
   * Current status of the index
   *
   * @return status
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getStatus() {
    return status;
  }

  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(@javax.annotation.Nonnull String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndexContent indexContent = (IndexContent) o;
    return Objects.equals(this.indexName, indexContent.indexName)
        && Objects.equals(this.indexUuid, indexContent.indexUuid)
        && Objects.equals(this.columns, indexContent.columns)
        && Objects.equals(this.status, indexContent.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexName, indexUuid, columns, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndexContent {\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    indexUuid: ").append(toIndentedString(indexUuid)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

    // add `index_name` to the URL query string
    if (getIndexName() != null) {
      try {
        joiner.add(
            String.format(
                "%sindex_name%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getIndexName()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `index_uuid` to the URL query string
    if (getIndexUuid() != null) {
      try {
        joiner.add(
            String.format(
                "%sindex_uuid%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getIndexUuid()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

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

    // add `status` to the URL query string
    if (getStatus() != null) {
      try {
        joiner.add(
            String.format(
                "%sstatus%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getStatus()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
