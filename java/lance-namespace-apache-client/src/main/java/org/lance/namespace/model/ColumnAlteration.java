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

/** ColumnAlteration */
@JsonPropertyOrder({
  ColumnAlteration.JSON_PROPERTY_COLUMN,
  ColumnAlteration.JSON_PROPERTY_RENAME,
  ColumnAlteration.JSON_PROPERTY_CAST_TO
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class ColumnAlteration {
  public static final String JSON_PROPERTY_COLUMN = "column";
  @javax.annotation.Nonnull private String column;

  public static final String JSON_PROPERTY_RENAME = "rename";
  @javax.annotation.Nullable private String rename;

  public static final String JSON_PROPERTY_CAST_TO = "cast_to";
  @javax.annotation.Nullable private String castTo;

  public ColumnAlteration() {}

  public ColumnAlteration column(@javax.annotation.Nonnull String column) {

    this.column = column;
    return this;
  }

  /**
   * Name of the column to alter
   *
   * @return column
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getColumn() {
    return column;
  }

  @JsonProperty(JSON_PROPERTY_COLUMN)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setColumn(@javax.annotation.Nonnull String column) {
    this.column = column;
  }

  public ColumnAlteration rename(@javax.annotation.Nullable String rename) {

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

  public ColumnAlteration castTo(@javax.annotation.Nullable String castTo) {

    this.castTo = castTo;
    return this;
  }

  /**
   * New data type to cast the column to (optional)
   *
   * @return castTo
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CAST_TO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getCastTo() {
    return castTo;
  }

  @JsonProperty(JSON_PROPERTY_CAST_TO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCastTo(@javax.annotation.Nullable String castTo) {
    this.castTo = castTo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnAlteration columnAlteration = (ColumnAlteration) o;
    return Objects.equals(this.column, columnAlteration.column)
        && Objects.equals(this.rename, columnAlteration.rename)
        && Objects.equals(this.castTo, columnAlteration.castTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, rename, castTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ColumnAlteration {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    rename: ").append(toIndentedString(rename)).append("\n");
    sb.append("    castTo: ").append(toIndentedString(castTo)).append("\n");
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

    // add `column` to the URL query string
    if (getColumn() != null) {
      try {
        joiner.add(
            String.format(
                "%scolumn%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getColumn()), "UTF-8").replaceAll("\\+", "%20")));
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

    // add `cast_to` to the URL query string
    if (getCastTo() != null) {
      try {
        joiner.add(
            String.format(
                "%scast_to%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getCastTo()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
