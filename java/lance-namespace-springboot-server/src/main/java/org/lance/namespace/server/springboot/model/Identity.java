package org.lance.namespace.server.springboot.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Identity information of a request. 
 */

@Schema(name = "Identity", description = "Identity information of a request. ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.12.0")
public class Identity {

  private String apiKey;

  private String authToken;

  public Identity apiKey(String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  /**
   * API key for authentication.  REST NAMESPACE ONLY This is passed via the `x-api-key` header. 
   * @return apiKey
   */
  
  @Schema(name = "api_key", description = "API key for authentication.  REST NAMESPACE ONLY This is passed via the `x-api-key` header. ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
   * Bearer token for authentication.  REST NAMESPACE ONLY This is passed via the `Authorization` header with the Bearer scheme (e.g., `Bearer <token>`). 
   * @return authToken
   */
  
  @Schema(name = "auth_token", description = "Bearer token for authentication.  REST NAMESPACE ONLY This is passed via the `Authorization` header with the Bearer scheme (e.g., `Bearer <token>`). ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    return Objects.equals(this.apiKey, identity.apiKey) &&
        Objects.equals(this.authToken, identity.authToken);
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
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

