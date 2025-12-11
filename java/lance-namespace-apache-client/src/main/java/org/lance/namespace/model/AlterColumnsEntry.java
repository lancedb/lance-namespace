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
import java.util.Objects;
import java.util.StringJoiner;

/** AlterColumnsEntry */
@JsonPropertyOrder({
  AlterColumnsEntry.JSON_PROPERTY_PATH,
  AlterColumnsEntry.JSON_PROPERTY_DATA_TYPE,
  AlterColumnsEntry.JSON_PROPERTY_RENAME,
  AlterColumnsEntry.JSON_PROPERTY_NULLABLE,
  AlterColumnsEntry.JSON_PROPERTY_VIRTUAL_COLUMN
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterColumnsEntry {
  public static final String JSON_PROPERTY_PATH = "path";
  @javax.annotation.Nonnull private String path;

  public static final String JSON_PROPERTY_DATA_TYPE = "data_type";
  @javax.annotation.Nonnull private Object dataType;

  public static final String JSON_PROPERTY_RENAME = "rename";
  @javax.annotation.Nullable private String rename;

  public static final String JSON_PROPERTY_NULLABLE = "nullable";
  @javax.annotation.Nullable private Boolean nullable;

  public static final String JSON_PROPERTY_VIRTUAL_COLUMN = "virtual_column";
  @javax.annotation.Nullable private AlterVirtualColumnEntry virtualColumn;

  public AlterColumnsEntry() {}

  public AlterColumnsEntry path(@javax.annotation.Nonnull String path) {

    this.path = path;
    return this;
  }

  /**
   * Column path to alter
   *
   * @return path
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PATH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getPath() {
    return path;
  }

  @JsonProperty(JSON_PROPERTY_PATH)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPath(@javax.annotation.Nonnull String path) {
    this.path = path;
  }

  public AlterColumnsEntry dataType(@javax.annotation.Nonnull Object dataType) {

    this.dataType = dataType;
    return this;
  }

  /**
   * New data type for the column using JSON representation (optional)
   *
   * @return dataType
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_DATA_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Object getDataType() {
    return dataType;
  }

  @JsonProperty(JSON_PROPERTY_DATA_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDataType(@javax.annotation.Nonnull Object dataType) {
    this.dataType = dataType;
  }

  public AlterColumnsEntry rename(@javax.annotation.Nullable String rename) {

    this.rename = rename;
    return this;
  }

  /**
   * New name for the column (optional)
   *
   * @return rename
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_RENAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getRename() {
    return rename;
  }

  @JsonProperty(JSON_PROPERTY_RENAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRename(@javax.annotation.Nullable String rename) {
    this.rename = rename;
  }

  public AlterColumnsEntry nullable(@javax.annotation.Nullable Boolean nullable) {

    this.nullable = nullable;
    return this;
  }

  /**
   * Whether the column should be nullable (optional)
   *
   * @return nullable
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getNullable() {
    return nullable;
  }

  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNullable(@javax.annotation.Nullable Boolean nullable) {
    this.nullable = nullable;
  }

  public AlterColumnsEntry virtualColumn(
      @javax.annotation.Nullable AlterVirtualColumnEntry virtualColumn) {

    this.virtualColumn = virtualColumn;
    return this;
  }

  /**
   * Virtual column alterations (optional)
   *
   * @return virtualColumn
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VIRTUAL_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public AlterVirtualColumnEntry getVirtualColumn() {
    return virtualColumn;
  }

  @JsonProperty(JSON_PROPERTY_VIRTUAL_COLUMN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVirtualColumn(@javax.annotation.Nullable AlterVirtualColumnEntry virtualColumn) {
    this.virtualColumn = virtualColumn;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AlterColumnsEntry alterColumnsEntry = (AlterColumnsEntry) o;
    return Objects.equals(this.path, alterColumnsEntry.path)
        && Objects.equals(this.dataType, alterColumnsEntry.dataType)
        && Objects.equals(this.rename, alterColumnsEntry.rename)
        && Objects.equals(this.nullable, alterColumnsEntry.nullable)
        && Objects.equals(this.virtualColumn, alterColumnsEntry.virtualColumn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, dataType, rename, nullable, virtualColumn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterColumnsEntry {\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    rename: ").append(toIndentedString(rename)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    virtualColumn: ").append(toIndentedString(virtualColumn)).append("\n");
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

    // add `path` to the URL query string
    if (getPath() != null) {
      try {
        joiner.add(
            String.format(
                "%spath%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getPath()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `data_type` to the URL query string
    if (getDataType() != null) {
      try {
        joiner.add(
            String.format(
                "%sdata_type%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getDataType()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `rename` to the URL query string
    if (getRename() != null) {
      try {
        joiner.add(
            String.format(
                "%srename%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getRename()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `nullable` to the URL query string
    if (getNullable() != null) {
      try {
        joiner.add(
            String.format(
                "%snullable%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getNullable()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `virtual_column` to the URL query string
    if (getVirtualColumn() != null) {
      joiner.add(getVirtualColumn().toUrlQueryString(prefix + "virtual_column" + suffix));
    }

    return joiner.toString();
  }
}
