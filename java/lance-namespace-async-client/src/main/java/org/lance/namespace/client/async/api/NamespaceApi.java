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
import org.lance.namespace.model.CreateNamespaceRequest;
import org.lance.namespace.model.CreateNamespaceResponse;
import org.lance.namespace.model.DescribeNamespaceRequest;
import org.lance.namespace.model.DescribeNamespaceResponse;
import org.lance.namespace.model.DropNamespaceRequest;
import org.lance.namespace.model.DropNamespaceResponse;
import org.lance.namespace.model.ListNamespacesResponse;
import org.lance.namespace.model.ListTablesResponse;
import org.lance.namespace.model.NamespaceExistsRequest;

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
public class NamespaceApi {
  private final HttpClient memberVarHttpClient;
  private final ObjectMapper memberVarObjectMapper;
  private final String memberVarBaseUri;
  private final Consumer<HttpRequest.Builder> memberVarInterceptor;
  private final Duration memberVarReadTimeout;
  private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;
  private final Consumer<HttpResponse<String>> memberVarAsyncResponseInterceptor;

  public NamespaceApi() {
    this(Configuration.getDefaultApiClient());
  }

  public NamespaceApi(ApiClient apiClient) {
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
   * Create a new namespace Create new namespace &#x60;id&#x60;. During the creation process, the
   * implementation may modify user-provided &#x60;properties&#x60;, such as adding additional
   * properties like &#x60;created_at&#x60; to user-provided properties, omitting any specific
   * property, or performing actions based on any property value.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;CreateNamespaceResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<CreateNamespaceResponse> createNamespace(
      String id, CreateNamespaceRequest createNamespaceRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          createNamespaceRequestBuilder(id, createNamespaceRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("createNamespace", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<CreateNamespaceResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Create a new namespace Create new namespace &#x60;id&#x60;. During the creation process, the
   * implementation may modify user-provided &#x60;properties&#x60;, such as adding additional
   * properties like &#x60;created_at&#x60; to user-provided properties, omitting any specific
   * property, or performing actions based on any property value.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;CreateNamespaceResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<CreateNamespaceResponse>> createNamespaceWithHttpInfo(
      String id, CreateNamespaceRequest createNamespaceRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          createNamespaceRequestBuilder(id, createNamespaceRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("createNamespace", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<CreateNamespaceResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<CreateNamespaceResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder createNamespaceRequestBuilder(
      String id, CreateNamespaceRequest createNamespaceRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling createNamespace");
    }
    // verify the required parameter 'createNamespaceRequest' is set
    if (createNamespaceRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'createNamespaceRequest' when calling createNamespace");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/namespace/{id}/create".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(createNamespaceRequest);
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
   * Describe a namespace Describe the detailed information for namespace &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;DescribeNamespaceResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<DescribeNamespaceResponse> describeNamespace(
      String id, DescribeNamespaceRequest describeNamespaceRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          describeNamespaceRequestBuilder(id, describeNamespaceRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("describeNamespace", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<DescribeNamespaceResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Describe a namespace Describe the detailed information for namespace &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;DescribeNamespaceResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<DescribeNamespaceResponse>> describeNamespaceWithHttpInfo(
      String id, DescribeNamespaceRequest describeNamespaceRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          describeNamespaceRequestBuilder(id, describeNamespaceRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("describeNamespace", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<DescribeNamespaceResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody,
                                  new TypeReference<DescribeNamespaceResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder describeNamespaceRequestBuilder(
      String id, DescribeNamespaceRequest describeNamespaceRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling describeNamespace");
    }
    // verify the required parameter 'describeNamespaceRequest' is set
    if (describeNamespaceRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'describeNamespaceRequest' when calling describeNamespace");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/namespace/{id}/describe".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(describeNamespaceRequest);
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
   * Drop a namespace Drop namespace &#x60;id&#x60; from its parent namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;DropNamespaceResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<DropNamespaceResponse> dropNamespace(
      String id, DropNamespaceRequest dropNamespaceRequest, String delimiter) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          dropNamespaceRequestBuilder(id, dropNamespaceRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("dropNamespace", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<DropNamespaceResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Drop a namespace Drop namespace &#x60;id&#x60; from its parent namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;DropNamespaceResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<DropNamespaceResponse>> dropNamespaceWithHttpInfo(
      String id, DropNamespaceRequest dropNamespaceRequest, String delimiter) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          dropNamespaceRequestBuilder(id, dropNamespaceRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("dropNamespace", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<DropNamespaceResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<DropNamespaceResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder dropNamespaceRequestBuilder(
      String id, DropNamespaceRequest dropNamespaceRequest, String delimiter) throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling dropNamespace");
    }
    // verify the required parameter 'dropNamespaceRequest' is set
    if (dropNamespaceRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'dropNamespaceRequest' when calling dropNamespace");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/namespace/{id}/drop".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(dropNamespaceRequest);
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
   * List namespaces List all child namespace names of the parent namespace &#x60;id&#x60;. REST
   * NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It
   * passes in the &#x60;ListNamespacesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @return CompletableFuture&lt;ListNamespacesResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ListNamespacesResponse> listNamespaces(
      String id, String delimiter, String pageToken, Integer limit) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          listNamespacesRequestBuilder(id, delimiter, pageToken, limit);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("listNamespaces", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<ListNamespacesResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * List namespaces List all child namespace names of the parent namespace &#x60;id&#x60;. REST
   * NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It
   * passes in the &#x60;ListNamespacesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;ListNamespacesResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<ListNamespacesResponse>> listNamespacesWithHttpInfo(
      String id, String delimiter, String pageToken, Integer limit) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          listNamespacesRequestBuilder(id, delimiter, pageToken, limit);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("listNamespaces", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<ListNamespacesResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<ListNamespacesResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder listNamespacesRequestBuilder(
      String id, String delimiter, String pageToken, Integer limit) throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling listNamespaces");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/namespace/{id}/list".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));
    localVarQueryParameterBaseName = "page_token";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("page_token", pageToken));
    localVarQueryParameterBaseName = "limit";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("limit", limit));

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

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }

  /**
   * List tables in a namespace List all child table names of the parent namespace &#x60;id&#x60;.
   * REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body.
   * It passes in the &#x60;ListTablesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name - &#x60;include_declared&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @param includeDeclared When true (default), includes tables that have been declared in the
   *     namespace but not yet created on storage, in addition to tables that have been created.
   *     When false, only tables with storage components are returned. (optional, default to true)
   * @return CompletableFuture&lt;ListTablesResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ListTablesResponse> listTables(
      String id, String delimiter, String pageToken, Integer limit, Boolean includeDeclared)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          listTablesRequestBuilder(id, delimiter, pageToken, limit, includeDeclared);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("listTables", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<ListTablesResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * List tables in a namespace List all child table names of the parent namespace &#x60;id&#x60;.
   * REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body.
   * It passes in the &#x60;ListTablesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name - &#x60;include_declared&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken Pagination token from a previous request (optional)
   * @param limit Maximum number of items to return (optional)
   * @param includeDeclared When true (default), includes tables that have been declared in the
   *     namespace but not yet created on storage, in addition to tables that have been created.
   *     When false, only tables with storage components are returned. (optional, default to true)
   * @return CompletableFuture&lt;ApiResponse&lt;ListTablesResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<ListTablesResponse>> listTablesWithHttpInfo(
      String id, String delimiter, String pageToken, Integer limit, Boolean includeDeclared)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          listTablesRequestBuilder(id, delimiter, pageToken, limit, includeDeclared);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("listTables", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<ListTablesResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<ListTablesResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder listTablesRequestBuilder(
      String id, String delimiter, String pageToken, Integer limit, Boolean includeDeclared)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling listTables");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/namespace/{id}/table/list".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));
    localVarQueryParameterBaseName = "page_token";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("page_token", pageToken));
    localVarQueryParameterBaseName = "limit";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("limit", limit));
    localVarQueryParameterBaseName = "include_declared";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("include_declared", includeDeclared));

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

    localVarRequestBuilder.header("Accept", "application/json");

    localVarRequestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());
    if (memberVarReadTimeout != null) {
      localVarRequestBuilder.timeout(memberVarReadTimeout);
    }
    if (memberVarInterceptor != null) {
      memberVarInterceptor.accept(localVarRequestBuilder);
    }
    return localVarRequestBuilder;
  }

  /**
   * Check if a namespace exists Check if namespace &#x60;id&#x60; exists. This operation must
   * behave exactly like the DescribeNamespace API, except it does not contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param namespaceExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;Void&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<Void> namespaceExists(
      String id, NamespaceExistsRequest namespaceExistsRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          namespaceExistsRequestBuilder(id, namespaceExistsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("namespaceExists", localVarResponse));
                }
                return CompletableFuture.completedFuture(null);
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Check if a namespace exists Check if namespace &#x60;id&#x60; exists. This operation must
   * behave exactly like the DescribeNamespace API, except it does not contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param namespaceExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;Void&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<Void>> namespaceExistsWithHttpInfo(
      String id, NamespaceExistsRequest namespaceExistsRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          namespaceExistsRequestBuilder(id, namespaceExistsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("namespaceExists", localVarResponse));
                }
                return CompletableFuture.completedFuture(
                    new ApiResponse<Void>(
                        localVarResponse.statusCode(), localVarResponse.headers().map(), null));
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder namespaceExistsRequestBuilder(
      String id, NamespaceExistsRequest namespaceExistsRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling namespaceExists");
    }
    // verify the required parameter 'namespaceExistsRequest' is set
    if (namespaceExistsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'namespaceExistsRequest' when calling namespaceExists");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/namespace/{id}/exists".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(namespaceExistsRequest);
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
