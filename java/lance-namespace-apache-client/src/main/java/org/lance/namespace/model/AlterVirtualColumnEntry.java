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

/** AlterVirtualColumnEntry */
@JsonPropertyOrder({
  AlterVirtualColumnEntry.JSON_PROPERTY_INPUT_COLUMNS,
  AlterVirtualColumnEntry.JSON_PROPERTY_IMAGE,
  AlterVirtualColumnEntry.JSON_PROPERTY_UDF,
  AlterVirtualColumnEntry.JSON_PROPERTY_UDF_NAME,
  AlterVirtualColumnEntry.JSON_PROPERTY_UDF_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class AlterVirtualColumnEntry {
  public static final String JSON_PROPERTY_INPUT_COLUMNS = "input_columns";
  @javax.annotation.Nullable private List<String> inputColumns = new ArrayList<>();

  public static final String JSON_PROPERTY_IMAGE = "image";
  @javax.annotation.Nullable private String image;

  public static final String JSON_PROPERTY_UDF = "udf";
  @javax.annotation.Nullable private String udf;

  public static final String JSON_PROPERTY_UDF_NAME = "udf_name";
  @javax.annotation.Nullable private String udfName;

  public static final String JSON_PROPERTY_UDF_VERSION = "udf_version";
  @javax.annotation.Nullable private String udfVersion;

  public AlterVirtualColumnEntry() {}

  public AlterVirtualColumnEntry inputColumns(
      @javax.annotation.Nullable List<String> inputColumns) {

    this.inputColumns = inputColumns;
    return this;
  }

  public AlterVirtualColumnEntry addInputColumnsItem(String inputColumnsItem) {
    if (this.inputColumns == null) {
      this.inputColumns = new ArrayList<>();
    }
    this.inputColumns.add(inputColumnsItem);
    return this;
  }

  /**
   * List of input column names for the virtual column (optional)
   *
   * @return inputColumns
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getInputColumns() {
    return inputColumns;
  }

  @JsonProperty(JSON_PROPERTY_INPUT_COLUMNS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInputColumns(@javax.annotation.Nullable List<String> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public AlterVirtualColumnEntry image(@javax.annotation.Nullable String image) {

    this.image = image;
    return this;
  }

  /**
   * Docker image to use for the UDF (optional)
   *
   * @return image
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IMAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getImage() {
    return image;
  }

  @JsonProperty(JSON_PROPERTY_IMAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setImage(@javax.annotation.Nullable String image) {
    this.image = image;
  }

  public AlterVirtualColumnEntry udf(@javax.annotation.Nullable String udf) {

    this.udf = udf;
    return this;
  }

  /**
   * Base64 encoded pickled UDF (optional)
   *
   * @return udf
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_UDF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getUdf() {
    return udf;
  }

  @JsonProperty(JSON_PROPERTY_UDF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUdf(@javax.annotation.Nullable String udf) {
    this.udf = udf;
  }

  public AlterVirtualColumnEntry udfName(@javax.annotation.Nullable String udfName) {

    this.udfName = udfName;
    return this;
  }

  /**
   * Name of the UDF (optional)
   *
   * @return udfName
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_UDF_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getUdfName() {
    return udfName;
  }

  @JsonProperty(JSON_PROPERTY_UDF_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUdfName(@javax.annotation.Nullable String udfName) {
    this.udfName = udfName;
  }

  public AlterVirtualColumnEntry udfVersion(@javax.annotation.Nullable String udfVersion) {

    this.udfVersion = udfVersion;
    return this;
  }

  /**
   * Version of the UDF (optional)
   *
   * @return udfVersion
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_UDF_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getUdfVersion() {
    return udfVersion;
  }

  @JsonProperty(JSON_PROPERTY_UDF_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUdfVersion(@javax.annotation.Nullable String udfVersion) {
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
    AlterVirtualColumnEntry alterVirtualColumnEntry = (AlterVirtualColumnEntry) o;
    return Objects.equals(this.inputColumns, alterVirtualColumnEntry.inputColumns)
        && Objects.equals(this.image, alterVirtualColumnEntry.image)
        && Objects.equals(this.udf, alterVirtualColumnEntry.udf)
        && Objects.equals(this.udfName, alterVirtualColumnEntry.udfName)
        && Objects.equals(this.udfVersion, alterVirtualColumnEntry.udfVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputColumns, image, udf, udfName, udfVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterVirtualColumnEntry {\n");
    sb.append("    inputColumns: ").append(toIndentedString(inputColumns)).append("\n");
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
