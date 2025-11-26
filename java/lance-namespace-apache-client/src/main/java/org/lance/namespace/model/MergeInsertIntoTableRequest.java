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

/** Request for merging or inserting records into a table, excluding the Arrow IPC stream. */
@JsonPropertyOrder({
  MergeInsertIntoTableRequest.JSON_PROPERTY_ID,
  MergeInsertIntoTableRequest.JSON_PROPERTY_ON,
  MergeInsertIntoTableRequest.JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL,
  MergeInsertIntoTableRequest.JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL_FILT,
  MergeInsertIntoTableRequest.JSON_PROPERTY_WHEN_NOT_MATCHED_INSERT_ALL,
  MergeInsertIntoTableRequest.JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE,
  MergeInsertIntoTableRequest.JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE_FILT
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MergeInsertIntoTableRequest {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_ON = "on";
  @javax.annotation.Nullable private String on;

  public static final String JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL = "when_matched_update_all";
  @javax.annotation.Nullable private Boolean whenMatchedUpdateAll = false;

  public static final String JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL_FILT =
      "when_matched_update_all_filt";
  @javax.annotation.Nullable private String whenMatchedUpdateAllFilt;

  public static final String JSON_PROPERTY_WHEN_NOT_MATCHED_INSERT_ALL =
      "when_not_matched_insert_all";
  @javax.annotation.Nullable private Boolean whenNotMatchedInsertAll = false;

  public static final String JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE =
      "when_not_matched_by_source_delete";
  @javax.annotation.Nullable private Boolean whenNotMatchedBySourceDelete = false;

  public static final String JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE_FILT =
      "when_not_matched_by_source_delete_filt";
  @javax.annotation.Nullable private String whenNotMatchedBySourceDeleteFilt;

  public MergeInsertIntoTableRequest() {}

  public MergeInsertIntoTableRequest id(@javax.annotation.Nullable List<String> id) {

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

  public MergeInsertIntoTableRequest on(@javax.annotation.Nullable String on) {

    this.on = on;
    return this;
  }

  /**
   * Column name to use for matching rows (required)
   *
   * @return on
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ON)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getOn() {
    return on;
  }

  @JsonProperty(JSON_PROPERTY_ON)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOn(@javax.annotation.Nullable String on) {
    this.on = on;
  }

  public MergeInsertIntoTableRequest whenMatchedUpdateAll(
      @javax.annotation.Nullable Boolean whenMatchedUpdateAll) {

    this.whenMatchedUpdateAll = whenMatchedUpdateAll;
    return this;
  }

  /**
   * Update all columns when rows match
   *
   * @return whenMatchedUpdateAll
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWhenMatchedUpdateAll() {
    return whenMatchedUpdateAll;
  }

  @JsonProperty(JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWhenMatchedUpdateAll(@javax.annotation.Nullable Boolean whenMatchedUpdateAll) {
    this.whenMatchedUpdateAll = whenMatchedUpdateAll;
  }

  public MergeInsertIntoTableRequest whenMatchedUpdateAllFilt(
      @javax.annotation.Nullable String whenMatchedUpdateAllFilt) {

    this.whenMatchedUpdateAllFilt = whenMatchedUpdateAllFilt;
    return this;
  }

  /**
   * The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to
   * true
   *
   * @return whenMatchedUpdateAllFilt
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL_FILT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getWhenMatchedUpdateAllFilt() {
    return whenMatchedUpdateAllFilt;
  }

  @JsonProperty(JSON_PROPERTY_WHEN_MATCHED_UPDATE_ALL_FILT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWhenMatchedUpdateAllFilt(
      @javax.annotation.Nullable String whenMatchedUpdateAllFilt) {
    this.whenMatchedUpdateAllFilt = whenMatchedUpdateAllFilt;
  }

  public MergeInsertIntoTableRequest whenNotMatchedInsertAll(
      @javax.annotation.Nullable Boolean whenNotMatchedInsertAll) {

    this.whenNotMatchedInsertAll = whenNotMatchedInsertAll;
    return this;
  }

  /**
   * Insert all columns when rows don&#39;t match
   *
   * @return whenNotMatchedInsertAll
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WHEN_NOT_MATCHED_INSERT_ALL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWhenNotMatchedInsertAll() {
    return whenNotMatchedInsertAll;
  }

  @JsonProperty(JSON_PROPERTY_WHEN_NOT_MATCHED_INSERT_ALL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWhenNotMatchedInsertAll(
      @javax.annotation.Nullable Boolean whenNotMatchedInsertAll) {
    this.whenNotMatchedInsertAll = whenNotMatchedInsertAll;
  }

  public MergeInsertIntoTableRequest whenNotMatchedBySourceDelete(
      @javax.annotation.Nullable Boolean whenNotMatchedBySourceDelete) {

    this.whenNotMatchedBySourceDelete = whenNotMatchedBySourceDelete;
    return this;
  }

  /**
   * Delete all rows from target table that don&#39;t match a row in the source table
   *
   * @return whenNotMatchedBySourceDelete
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWhenNotMatchedBySourceDelete() {
    return whenNotMatchedBySourceDelete;
  }

  @JsonProperty(JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWhenNotMatchedBySourceDelete(
      @javax.annotation.Nullable Boolean whenNotMatchedBySourceDelete) {
    this.whenNotMatchedBySourceDelete = whenNotMatchedBySourceDelete;
  }

  public MergeInsertIntoTableRequest whenNotMatchedBySourceDeleteFilt(
      @javax.annotation.Nullable String whenNotMatchedBySourceDeleteFilt) {

    this.whenNotMatchedBySourceDeleteFilt = whenNotMatchedBySourceDeleteFilt;
    return this;
  }

  /**
   * Delete rows from the target table if there is no match AND the SQL expression evaluates to true
   *
   * @return whenNotMatchedBySourceDeleteFilt
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE_FILT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getWhenNotMatchedBySourceDeleteFilt() {
    return whenNotMatchedBySourceDeleteFilt;
  }

  @JsonProperty(JSON_PROPERTY_WHEN_NOT_MATCHED_BY_SOURCE_DELETE_FILT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWhenNotMatchedBySourceDeleteFilt(
      @javax.annotation.Nullable String whenNotMatchedBySourceDeleteFilt) {
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

    // add `on` to the URL query string
    if (getOn() != null) {
      try {
        joiner.add(
            String.format(
                "%son%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getOn()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `when_matched_update_all` to the URL query string
    if (getWhenMatchedUpdateAll() != null) {
      try {
        joiner.add(
            String.format(
                "%swhen_matched_update_all%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWhenMatchedUpdateAll()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `when_matched_update_all_filt` to the URL query string
    if (getWhenMatchedUpdateAllFilt() != null) {
      try {
        joiner.add(
            String.format(
                "%swhen_matched_update_all_filt%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWhenMatchedUpdateAllFilt()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `when_not_matched_insert_all` to the URL query string
    if (getWhenNotMatchedInsertAll() != null) {
      try {
        joiner.add(
            String.format(
                "%swhen_not_matched_insert_all%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWhenNotMatchedInsertAll()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `when_not_matched_by_source_delete` to the URL query string
    if (getWhenNotMatchedBySourceDelete() != null) {
      try {
        joiner.add(
            String.format(
                "%swhen_not_matched_by_source_delete%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWhenNotMatchedBySourceDelete()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `when_not_matched_by_source_delete_filt` to the URL query string
    if (getWhenNotMatchedBySourceDeleteFilt() != null) {
      try {
        joiner.add(
            String.format(
                "%swhen_not_matched_by_source_delete_filt%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWhenNotMatchedBySourceDeleteFilt()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
