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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** AddVirtualColumnOutputEntry */
@JsonPropertyOrder({
  AddVirtualColumnOutputEntry.JSON_PROPERTY_COLUMN,
  AddVirtualColumnOutputEntry.JSON_PROPERTY_STRUCT_FIELD,
  AddVirtualColumnOutputEntry.JSON_PROPERTY_DATA_TYPE,
  AddVirtualColumnOutputEntry.JSON_PROPERTY_NULLABLE,
  AddVirtualColumnOutputEntry.JSON_PROPERTY_METADATA
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AddVirtualColumnOutputEntry {
  public static final String JSON_PROPERTY_COLUMN = "column";
  @javax.annotation.Nonnull private String column;

  public static final String JSON_PROPERTY_STRUCT_FIELD = "struct_field";
  @javax.annotation.Nonnull private String structField;

  public static final String JSON_PROPERTY_DATA_TYPE = "data_type";
  @javax.annotation.Nonnull private Object dataType;

  public static final String JSON_PROPERTY_NULLABLE = "nullable";
  @javax.annotation.Nonnull private Boolean nullable;

  public static final String JSON_PROPERTY_METADATA = "metadata";
  @javax.annotation.Nullable private Map<String, String> metadata = new HashMap<>();

  public AddVirtualColumnOutputEntry() {}

  public AddVirtualColumnOutputEntry column(@javax.annotation.Nonnull String column) {

    this.column = column;
    return this;
  }

  /**
   * Physical output column name
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

  public AddVirtualColumnOutputEntry structField(@javax.annotation.Nonnull String structField) {

    this.structField = structField;
    return this;
  }

  /**
   * Field name in the UDF output struct
   *
   * @return structField
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STRUCT_FIELD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getStructField() {
    return structField;
  }

  @JsonProperty(JSON_PROPERTY_STRUCT_FIELD)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStructField(@javax.annotation.Nonnull String structField) {
    this.structField = structField;
  }

  public AddVirtualColumnOutputEntry dataType(@javax.annotation.Nonnull Object dataType) {

    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the output column using JSON representation
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

  public AddVirtualColumnOutputEntry nullable(@javax.annotation.Nonnull Boolean nullable) {

    this.nullable = nullable;
    return this;
  }

  /**
   * Whether the output column is nullable
   *
   * @return nullable
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Boolean getNullable() {
    return nullable;
  }

  @JsonProperty(JSON_PROPERTY_NULLABLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNullable(@javax.annotation.Nonnull Boolean nullable) {
    this.nullable = nullable;
  }

  public AddVirtualColumnOutputEntry metadata(
      @javax.annotation.Nullable Map<String, String> metadata) {

    this.metadata = metadata;
    return this;
  }

  public AddVirtualColumnOutputEntry putMetadataItem(String key, String metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

  /**
   * User-supplied output field metadata (string key-value pairs)
   *
   * @return metadata
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getMetadata() {
    return metadata;
  }

  @JsonProperty(JSON_PROPERTY_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMetadata(@javax.annotation.Nullable Map<String, String> metadata) {
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddVirtualColumnOutputEntry addVirtualColumnOutputEntry = (AddVirtualColumnOutputEntry) o;
    return Objects.equals(this.column, addVirtualColumnOutputEntry.column)
        && Objects.equals(this.structField, addVirtualColumnOutputEntry.structField)
        && Objects.equals(this.dataType, addVirtualColumnOutputEntry.dataType)
        && Objects.equals(this.nullable, addVirtualColumnOutputEntry.nullable)
        && Objects.equals(this.metadata, addVirtualColumnOutputEntry.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, structField, dataType, nullable, metadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddVirtualColumnOutputEntry {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    structField: ").append(toIndentedString(structField)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    nullable: ").append(toIndentedString(nullable)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
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

    // add `struct_field` to the URL query string
    if (getStructField() != null) {
      try {
        joiner.add(
            String.format(
                "%sstruct_field%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getStructField()), "UTF-8")
                    .replaceAll("\\+", "%20")));
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

    // add `metadata` to the URL query string
    if (getMetadata() != null) {
      for (String _key : getMetadata().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%smetadata%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getMetadata().get(_key),
                  URLEncoder.encode(String.valueOf(getMetadata().get(_key)), "UTF-8")
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
