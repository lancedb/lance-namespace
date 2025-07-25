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
package com.lancedb.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** AlterTransactionResponse */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTransactionResponse {

  private TransactionStatus status;

  @Valid private Map<String, String> properties = new HashMap<>();

  public AlterTransactionResponse() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTransactionResponse(TransactionStatus status) {
    this.status = status;
  }

  public AlterTransactionResponse status(TransactionStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status
   */
  @NotNull
  @Valid
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  public AlterTransactionResponse properties(Map<String, String> properties) {
    this.properties = properties;
    return this;
  }

  public AlterTransactionResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Get properties
   *
   * @return properties
   */
  @Schema(name = "properties", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    AlterTransactionResponse alterTransactionResponse = (AlterTransactionResponse) o;
    return Objects.equals(this.status, alterTransactionResponse.status)
        && Objects.equals(this.properties, alterTransactionResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTransactionResponse {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
