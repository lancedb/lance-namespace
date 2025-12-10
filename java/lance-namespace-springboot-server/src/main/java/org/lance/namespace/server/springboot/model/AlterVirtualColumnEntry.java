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

/** AlterVirtualColumnEntry */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterVirtualColumnEntry {

  @Valid private List<String> inputColumns = new ArrayList<>();

  private String image;

  private String udf;

  private String udfName;

  private String udfVersion;

  public AlterVirtualColumnEntry inputColumns(List<String> inputColumns) {
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
  @Schema(
      name = "input_columns",
      description = "List of input column names for the virtual column (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("input_columns")
  public List<String> getInputColumns() {
    return inputColumns;
  }

  public void setInputColumns(List<String> inputColumns) {
    this.inputColumns = inputColumns;
  }

  public AlterVirtualColumnEntry image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Docker image to use for the UDF (optional)
   *
   * @return image
   */
  @Schema(
      name = "image",
      description = "Docker image to use for the UDF (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("image")
  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public AlterVirtualColumnEntry udf(String udf) {
    this.udf = udf;
    return this;
  }

  /**
   * Base64 encoded pickled UDF (optional)
   *
   * @return udf
   */
  @Schema(
      name = "udf",
      description = "Base64 encoded pickled UDF (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("udf")
  public String getUdf() {
    return udf;
  }

  public void setUdf(String udf) {
    this.udf = udf;
  }

  public AlterVirtualColumnEntry udfName(String udfName) {
    this.udfName = udfName;
    return this;
  }

  /**
   * Name of the UDF (optional)
   *
   * @return udfName
   */
  @Schema(
      name = "udf_name",
      description = "Name of the UDF (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("udf_name")
  public String getUdfName() {
    return udfName;
  }

  public void setUdfName(String udfName) {
    this.udfName = udfName;
  }

  public AlterVirtualColumnEntry udfVersion(String udfVersion) {
    this.udfVersion = udfVersion;
    return this;
  }

  /**
   * Version of the UDF (optional)
   *
   * @return udfVersion
   */
  @Schema(
      name = "udf_version",
      description = "Version of the UDF (optional)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
}
