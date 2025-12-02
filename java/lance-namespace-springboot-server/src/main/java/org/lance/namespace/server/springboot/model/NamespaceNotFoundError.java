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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** The requested namespace does not exist */
@Schema(name = "NamespaceNotFoundError", description = "The requested namespace does not exist")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class NamespaceNotFoundError {

  private String error;

  /** HTTP status code */
  public enum CodeEnum {
    NUMBER_404(404);

    private Integer value;

    CodeEnum(Integer value) {
      this.value = value;
    }

    @JsonValue
    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static CodeEnum fromValue(Integer value) {
      for (CodeEnum b : CodeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private CodeEnum code;

  /** Error type identifier */
  public enum TypeEnum {
    LANCE_NAMESPACE_101("lance-namespace:101");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  private String detail;

  private String instance;

  public NamespaceNotFoundError() {
    super();
  }

  /** Constructor with only required parameters */
  public NamespaceNotFoundError(String error, CodeEnum code, TypeEnum type) {
    this.error = error;
    this.code = code;
    this.type = type;
  }

  public NamespaceNotFoundError error(String error) {
    this.error = error;
    return this;
  }

  /**
   * Brief error message
   *
   * @return error
   */
  @NotNull
  @Schema(
      name = "error",
      example = "Namespace not found",
      description = "Brief error message",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("error")
  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public NamespaceNotFoundError code(CodeEnum code) {
    this.code = code;
    return this;
  }

  /**
   * HTTP status code
   *
   * @return code
   */
  @NotNull
  @Schema(
      name = "code",
      description = "HTTP status code",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public CodeEnum getCode() {
    return code;
  }

  public void setCode(CodeEnum code) {
    this.code = code;
  }

  public NamespaceNotFoundError type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Error type identifier
   *
   * @return type
   */
  @NotNull
  @Schema(
      name = "type",
      description = "Error type identifier",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public NamespaceNotFoundError detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Detailed error explanation
   *
   * @return detail
   */
  @Schema(
      name = "detail",
      description = "Detailed error explanation",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("detail")
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public NamespaceNotFoundError instance(String instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Specific occurrence identifier
   *
   * @return instance
   */
  @Schema(
      name = "instance",
      description = "Specific occurrence identifier",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("instance")
  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NamespaceNotFoundError namespaceNotFoundError = (NamespaceNotFoundError) o;
    return Objects.equals(this.error, namespaceNotFoundError.error)
        && Objects.equals(this.code, namespaceNotFoundError.code)
        && Objects.equals(this.type, namespaceNotFoundError.type)
        && Objects.equals(this.detail, namespaceNotFoundError.detail)
        && Objects.equals(this.instance, namespaceNotFoundError.instance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, code, type, detail, instance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NamespaceNotFoundError {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    detail: ").append(toIndentedString(detail)).append("\n");
    sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
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
