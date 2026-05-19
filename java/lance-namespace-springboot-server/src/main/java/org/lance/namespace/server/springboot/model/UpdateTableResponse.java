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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** UpdateTableResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class UpdateTableResponse {

  private String transactionId;

  private Long updatedRows;

  private Long version;

  @Valid private Map<String, String> properties = new HashMap<>();

  public UpdateTableResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public UpdateTableResponse(Long updatedRows, Long version) {
    this.updatedRows = updatedRows;
    this.version = version;
  }

  public UpdateTableResponse transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier
   *
   * @return transactionId
   */
  @Schema(
      name = "transaction_id",
      description = "Optional transaction identifier",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("transaction_id")
  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public UpdateTableResponse updatedRows(Long updatedRows) {
    this.updatedRows = updatedRows;
    return this;
  }

  /**
   * Number of rows updated minimum: 0
   *
   * @return updatedRows
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "updated_rows",
      description = "Number of rows updated",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updated_rows")
  public Long getUpdatedRows() {
    return updatedRows;
  }

  public void setUpdatedRows(Long updatedRows) {
    this.updatedRows = updatedRows;
  }

  public UpdateTableResponse version(Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
   *
   * @return version
   */
  @NotNull
  @Min(0L)
  @Schema(
      name = "version",
      description = "The commit version associated with the operation",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("version")
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public UpdateTableResponse properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public UpdateTableResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * If the implementation does not support table properties, it should return null for this field.
   * Otherwise, it should return the properties.
   *
   * @return properties
   */
  @Schema(
      name = "properties",
      description =
          "If the implementation does not support table properties, it should return null for this field. Otherwise, it should return the properties. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateTableResponse updateTableResponse = (UpdateTableResponse) o;
    return Objects.equals(this.transactionId, updateTableResponse.transactionId)
        && Objects.equals(this.updatedRows, updateTableResponse.updatedRows)
        && Objects.equals(this.version, updateTableResponse.version)
        && Objects.equals(this.properties, updateTableResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, updatedRows, version, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTableResponse {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    updatedRows: ").append(toIndentedString(updatedRows)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
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
