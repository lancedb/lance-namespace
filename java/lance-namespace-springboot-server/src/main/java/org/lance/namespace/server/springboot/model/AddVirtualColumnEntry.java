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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** AddVirtualColumnEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AddVirtualColumnEntry {

  @Valid private List<String> inputColumns = new ArrayList<>();

  private Object dataType;

  private String image;

  private String udf;

  private String udfName;

  private String udfVersion;

  public AddVirtualColumnEntry() {
    super();
  }

  /** Constructor with only required parameters */
  public AddVirtualColumnEntry(
      List<String> inputColumns,
      Object dataType,
      String image,
      String udf,
      String udfName,
      String udfVersion) {
    this.inputColumns = inputColumns;
    this.dataType = dataType;
    this.image = image;
    this.udf = udf;
    this.udfName = udfName;
    this.udfVersion = udfVersion;
  }

  public AddVirtualColumnEntry inputColumns(List<String> inputColumns) {
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
  @NotNull
  @Schema(
      name = "input_columns",
      description = "List of input column names for the virtual column",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("input_columns")
  public List<String> getInputColumns() {
    return inputColumns;
  }

  public void setInputColumns(List<String> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public AddVirtualColumnEntry dataType(Object dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the virtual column using JSON representation
   *
   * @return dataType
   */
  @NotNull
  @Schema(
      name = "data_type",
      description = "Data type of the virtual column using JSON representation",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("data_type")
  public Object getDataType() {
    return dataType;
  }

  public void setDataType(Object dataType) {
    this.dataType = dataType;
  }

  public AddVirtualColumnEntry image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Docker image to use for the UDF
   *
   * @return image
   */
  @NotNull
  @Schema(
      name = "image",
      description = "Docker image to use for the UDF",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("image")
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public AddVirtualColumnEntry udf(String udf) {
    this.udf = udf;
    return this;
  }

  /**
   * Base64 encoded pickled UDF
   *
   * @return udf
   */
  @NotNull
  @Schema(
      name = "udf",
      description = "Base64 encoded pickled UDF",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udf")
  public String getUdf() {
    return udf;
  }

  public void setUdf(String udf) {
    this.udf = udf;
  }

  public AddVirtualColumnEntry udfName(String udfName) {
    this.udfName = udfName;
    return this;
  }

  /**
   * Name of the UDF
   *
   * @return udfName
   */
  @NotNull
  @Schema(
      name = "udf_name",
      description = "Name of the UDF",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udf_name")
  public String getUdfName() {
    return udfName;
  }

  public void setUdfName(String udfName) {
    this.udfName = udfName;
  }

  public AddVirtualColumnEntry udfVersion(String udfVersion) {
    this.udfVersion = udfVersion;
    return this;
  }

  /**
   * Version of the UDF
   *
   * @return udfVersion
   */
  @NotNull
  @Schema(
      name = "udf_version",
      description = "Version of the UDF",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("udf_version")
  public String getUdfVersion() {
    return udfVersion;
  }

  public void setUdfVersion(String udfVersion) {
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
}
