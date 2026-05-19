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
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.Objects;

/** Identity information of a request. */
@Schema(name = "Identity", description = "Identity information of a request. ")
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class Identity {

  private String apiKey;

  private String authToken;

  public Identity apiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /**
   * API key for authentication. REST NAMESPACE ONLY This is passed via the `x-api-key` header.
   *
   * @return apiKey
   */
  @Schema(
      name = "api_key",
      description =
          "API key for authentication.  REST NAMESPACE ONLY This is passed via the `x-api-key` header. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("api_key")
  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public Identity authToken(String authToken) {
    this.authToken = authToken;
    return this;
  }

  /**
   * Bearer token for authentication. REST NAMESPACE ONLY This is passed via the `Authorization`
   * header with the Bearer scheme (e.g., `Bearer <token>`).
   *
   * @return authToken
   */
  @Schema(
      name = "auth_token",
      description =
          "Bearer token for authentication.  REST NAMESPACE ONLY This is passed via the `Authorization` header with the Bearer scheme (e.g., `Bearer <token>`). ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("auth_token")
  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Identity identity = (Identity) o;
    return Objects.equals(this.apiKey, identity.apiKey)
        && Objects.equals(this.authToken, identity.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(apiKey, authToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Identity {\n");
    sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
    sb.append("    authToken: ").append(toIndentedString(authToken)).append("\n");
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
