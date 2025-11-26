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

/** DropTableIndexRequest */
@JsonPropertyOrder({
  DropTableIndexRequest.JSON_PROPERTY_ID,
  DropTableIndexRequest.JSON_PROPERTY_INDEX_NAME
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DropTableIndexRequest {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nonnull private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_INDEX_NAME = "index_name";
  @javax.annotation.Nonnull private String indexName;

  public DropTableIndexRequest() {}

  public DropTableIndexRequest id(@javax.annotation.Nonnull List<String> id) {

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
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(@javax.annotation.Nonnull List<String> id) {
    this.id = id;
  }

  public DropTableIndexRequest indexName(@javax.annotation.Nonnull String indexName) {

    this.indexName = indexName;
    return this;
  }

  /**
   * Name of the index to drop
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

    return joiner.toString();
  }
}
