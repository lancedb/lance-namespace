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

/** AddVirtualColumnEntry */
@JsonPropertyOrder({
  AddVirtualColumnEntry.JSON_PROPERTY_INPUT_COLUMNS,
  AddVirtualColumnEntry.JSON_PROPERTY_DATA_TYPE,
  AddVirtualColumnEntry.JSON_PROPERTY_IMAGE,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF_NAME,
  AddVirtualColumnEntry.JSON_PROPERTY_UDF_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AddVirtualColumnEntry {
  public static final String JSON_PROPERTY_INPUT_COLUMNS = "input_columns";
  @javax.annotation.Nonnull private List<String> inputColumns = new ArrayList<>();

  public static final String JSON_PROPERTY_DATA_TYPE = "data_type";
  @javax.annotation.Nonnull private Object dataType;

  public static final String JSON_PROPERTY_IMAGE = "image";
  @javax.annotation.Nonnull private String image;

  public static final String JSON_PROPERTY_UDF = "udf";
  @javax.annotation.Nonnull private String udf;

  public static final String JSON_PROPERTY_UDF_NAME = "udf_name";
  @javax.annotation.Nonnull private String udfName;

  public static final String JSON_PROPERTY_UDF_VERSION = "udf_version";
  @javax.annotation.Nonnull private String udfVersion;

  public AddVirtualColumnEntry() {}

  public AddVirtualColumnEntry inputColumns(@javax.annotation.Nonnull List<String> inputColumns) {

    this.inputColumns = inputColumns;
    return this;
  }

  public AddVirtualColumnEntry addInputColumnsItem(String inputColumnsItem) {
    if (this.inputColumns == null) {
      this.inputColumns = new ArrayList<>();
    }
    this.inputColumns.add(inputColumnsItem);
    return this;
  }

  /**
   * List of input column names for the virtual column
   *
   * @return inputColumns
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<String> getInputColumns() {
    return inputColumns;
  }

  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setInputColumns(@javax.annotation.Nonnull List<String> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public AddVirtualColumnEntry dataType(@javax.annotation.Nonnull Object dataType) {

    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the virtual column using JSON representation
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

  public AddVirtualColumnEntry image(@javax.annotation.Nonnull String image) {

    this.image = image;
    return this;
  }

  /**
   * Docker image to use for the UDF
   *
   * @return image
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_IMAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getImage() {
    return image;
  }

  @JsonProperty(JSON_PROPERTY_IMAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setImage(@javax.annotation.Nonnull String image) {
    this.image = image;
  }

  public AddVirtualColumnEntry udf(@javax.annotation.Nonnull String udf) {

    this.udf = udf;
    return this;
  }

  /**
   * Base64 encoded pickled UDF
   *
   * @return udf
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDF)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdf() {
    return udf;
  }

  @JsonProperty(JSON_PROPERTY_UDF)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdf(@javax.annotation.Nonnull String udf) {
    this.udf = udf;
  }

  public AddVirtualColumnEntry udfName(@javax.annotation.Nonnull String udfName) {

    this.udfName = udfName;
    return this;
  }

  /**
   * Name of the UDF
   *
   * @return udfName
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDF_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdfName() {
    return udfName;
  }

  @JsonProperty(JSON_PROPERTY_UDF_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdfName(@javax.annotation.Nonnull String udfName) {
    this.udfName = udfName;
  }

  public AddVirtualColumnEntry udfVersion(@javax.annotation.Nonnull String udfVersion) {

    this.udfVersion = udfVersion;
    return this;
  }

  /**
   * Version of the UDF
   *
   * @return udfVersion
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_UDF_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getUdfVersion() {
    return udfVersion;
  }

  @JsonProperty(JSON_PROPERTY_UDF_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUdfVersion(@javax.annotation.Nonnull String udfVersion) {
    this.udfVersion = udfVersion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddVirtualColumnEntry addVirtualColumnEntry = (AddVirtualColumnEntry) o;
    return Objects.equals(this.inputColumns, addVirtualColumnEntry.inputColumns)
        && Objects.equals(this.dataType, addVirtualColumnEntry.dataType)
        && Objects.equals(this.image, addVirtualColumnEntry.image)
        && Objects.equals(this.udf, addVirtualColumnEntry.udf)
        && Objects.equals(this.udfName, addVirtualColumnEntry.udfName)
        && Objects.equals(this.udfVersion, addVirtualColumnEntry.udfVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputColumns, dataType, image, udf, udfName, udfVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddVirtualColumnEntry {\n");
    sb.append("    inputColumns: ").append(toIndentedString(inputColumns)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    udf: ").append(toIndentedString(udf)).append("\n");
    sb.append("    udfName: ").append(toIndentedString(udfName)).append("\n");
    sb.append("    udfVersion: ").append(toIndentedString(udfVersion)).append("\n");
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

    // add `input_columns` to the URL query string
    if (getInputColumns() != null) {
      for (int i = 0; i < getInputColumns().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sinput_columns%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getInputColumns().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
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

    // add `image` to the URL query string
    if (getImage() != null) {
      try {
        joiner.add(
            String.format(
                "%simage%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getImage()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udf` to the URL query string
    if (getUdf() != null) {
      try {
        joiner.add(
            String.format(
                "%sudf%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdf()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udf_name` to the URL query string
    if (getUdfName() != null) {
      try {
        joiner.add(
            String.format(
                "%sudf_name%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdfName()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `udf_version` to the URL query string
    if (getUdfVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sudf_version%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getUdfVersion()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
