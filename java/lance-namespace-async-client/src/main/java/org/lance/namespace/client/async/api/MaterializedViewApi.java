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
package org.lance.namespace.client.async.api;

import org.lance.namespace.client.async.ApiClient;
import org.lance.namespace.client.async.ApiException;
import org.lance.namespace.client.async.ApiResponse;
import org.lance.namespace.client.async.Configuration;
import org.lance.namespace.client.async.Pair;
import org.lance.namespace.model.CreateMaterializedViewRequest;
import org.lance.namespace.model.CreateMaterializedViewResponse;
import org.lance.namespace.model.RefreshMaterializedViewRequest;
import org.lance.namespace.model.RefreshMaterializedViewResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MaterializedViewApi {
  private final HttpClient memberVarHttpClient;
  private final ObjectMapper memberVarObjectMapper;
  private final String memberVarBaseUri;
  private final Consumer<HttpRequest.Builder> memberVarInterceptor;
  private final Duration memberVarReadTimeout;
  private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;
  private final Consumer<HttpResponse<String>> memberVarAsyncResponseInterceptor;

  public MaterializedViewApi() {
    this(Configuration.getDefaultApiClient());
  }

  public MaterializedViewApi(ApiClient apiClient) {
    memberVarHttpClient = apiClient.getHttpClient();
    memberVarObjectMapper = apiClient.getObjectMapper();
    memberVarBaseUri = apiClient.getBaseUri();
    memberVarInterceptor = apiClient.getRequestInterceptor();
    memberVarReadTimeout = apiClient.getReadTimeout();
    memberVarResponseInterceptor = apiClient.getResponseInterceptor();
    memberVarAsyncResponseInterceptor = apiClient.getAsyncResponseInterceptor();
  }

  private ApiException getApiException(String operationId, HttpResponse<String> response) {
    String message = formatExceptionMessage(operationId, response.statusCode(), response.body());
    return new ApiException(response.statusCode(), message, response.headers(), response.body());
  }

  private String formatExceptionMessage(String operationId, int statusCode, String body) {
    if (body == null || body.isEmpty()) {
      body = "[no body]";
    }
    return operationId + " call failed with: " + statusCode + " - " + body;
  }

  /**
   * Create a materialized view Create a materialized view at identifier &#x60;id&#x60;. The view
   * may be query-backed, UDTF-backed, or chunker-backed, controlled by the &#x60;kind&#x60;
   * discriminator.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createMaterializedViewRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;CreateMaterializedViewResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<CreateMaterializedViewResponse> createMaterializedView(
      String id, CreateMaterializedViewRequest createMaterializedViewRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          createMaterializedViewRequestBuilder(id, createMaterializedViewRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("createMaterializedView", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody,
                              new TypeReference<CreateMaterializedViewResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Create a materialized view Create a materialized view at identifier &#x60;id&#x60;. The view
   * may be query-backed, UDTF-backed, or chunker-backed, controlled by the &#x60;kind&#x60;
   * discriminator.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createMaterializedViewRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;CreateMaterializedViewResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<CreateMaterializedViewResponse>>
      createMaterializedViewWithHttpInfo(
          String id, CreateMaterializedViewRequest createMaterializedViewRequest, String delimiter)
          throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          createMaterializedViewRequestBuilder(id, createMaterializedViewRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("createMaterializedView", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<CreateMaterializedViewResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody,
                                  new TypeReference<CreateMaterializedViewResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder createMaterializedViewRequestBuilder(
      String id, CreateMaterializedViewRequest createMaterializedViewRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling createMaterializedView");
    }
    // verify the required parameter 'createMaterializedViewRequest' is set
    if (createMaterializedViewRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'createMaterializedViewRequest' when calling createMaterializedView");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/materialized_view/{id}/create".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));

    if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      if (localVarQueryStringJoiner.length() != 0) {
        queryJoiner.add(localVarQueryStringJoiner.toString());
      }
      localVarRequestBuilder.uri(
          URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Content-Type", "application/json");
    localVarRequestBuilder.header("Accept", "application/json");

    try {
      byte[] localVarPostBody =
          memberVarObjectMapper.writeValueAsBytes(createMaterializedViewRequest);
      localVarRequestBuilder.method(
          "POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
    } catch (IOException e) {
      throw new ApiException(e);
    }
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }

  /**
   * Trigger an async materialized view refresh Trigger an asynchronous refresh job for materialized
   * view &#x60;id&#x60;. Returns a job ID for tracking.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param refreshMaterializedViewRequest (optional)
   * @return CompletableFuture&lt;RefreshMaterializedViewResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<RefreshMaterializedViewResponse> refreshMaterializedView(
      String id, String delimiter, RefreshMaterializedViewRequest refreshMaterializedViewRequest)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          refreshMaterializedViewRequestBuilder(id, delimiter, refreshMaterializedViewRequest);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("refreshMaterializedView", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody,
                              new TypeReference<RefreshMaterializedViewResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Trigger an async materialized view refresh Trigger an asynchronous refresh job for materialized
   * view &#x60;id&#x60;. Returns a job ID for tracking.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param refreshMaterializedViewRequest (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;RefreshMaterializedViewResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<RefreshMaterializedViewResponse>>
      refreshMaterializedViewWithHttpInfo(
          String id,
          String delimiter,
          RefreshMaterializedViewRequest refreshMaterializedViewRequest)
          throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          refreshMaterializedViewRequestBuilder(id, delimiter, refreshMaterializedViewRequest);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("refreshMaterializedView", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<RefreshMaterializedViewResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody,
                                  new TypeReference<RefreshMaterializedViewResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder refreshMaterializedViewRequestBuilder(
      String id, String delimiter, RefreshMaterializedViewRequest refreshMaterializedViewRequest)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling refreshMaterializedView");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/materialized_view/{id}/refresh".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));

    if (!localVarQueryParams.isEmpty() || localVarQueryStringJoiner.length() != 0) {
      StringJoiner queryJoiner = new StringJoiner("&");
      localVarQueryParams.forEach(p -> queryJoiner.add(p.getName() + '=' + p.getValue()));
      if (localVarQueryStringJoiner.length() != 0) {
        queryJoiner.add(localVarQueryStringJoiner.toString());
      }
      localVarRequestBuilder.uri(
          URI.create(memberVarBaseUri + localVarPath + '?' + queryJoiner.toString()));
    } else {
      localVarRequestBuilder.uri(URI.create(memberVarBaseUri + localVarPath));
    }

    localVarRequestBuilder.header("Content-Type", "application/json");
    localVarRequestBuilder.header("Accept", "application/json");

    try {
      byte[] localVarPostBody =
          memberVarObjectMapper.writeValueAsBytes(refreshMaterializedViewRequest);
      localVarRequestBuilder.method(
          "POST", HttpRequest.BodyPublishers.ofByteArray(localVarPostBody));
    } catch (IOException e) {
      throw new ApiException(e);
    }
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }
}
