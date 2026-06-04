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
import org.lance.namespace.model.AlterTableAddColumnsRequest;
import org.lance.namespace.model.AlterTableAddColumnsResponse;
import org.lance.namespace.model.AlterTableBackfillColumnsRequest;
import org.lance.namespace.model.AlterTableBackfillColumnsResponse;
import org.lance.namespace.model.AnalyzeTableQueryPlanRequest;
import org.lance.namespace.model.CountTableRowsRequest;
import org.lance.namespace.model.CreateMaterializedViewRequest;
import org.lance.namespace.model.CreateMaterializedViewResponse;
import org.lance.namespace.model.CreateTableResponse;
import org.lance.namespace.model.DeleteFromTableRequest;
import org.lance.namespace.model.DeleteFromTableResponse;
import org.lance.namespace.model.ExplainTableQueryPlanRequest;
import org.lance.namespace.model.InsertIntoTableResponse;
import org.lance.namespace.model.MergeInsertIntoTableResponse;
import org.lance.namespace.model.QueryTableRequest;
import org.lance.namespace.model.RefreshMaterializedViewRequest;
import org.lance.namespace.model.RefreshMaterializedViewResponse;
import org.lance.namespace.model.UpdateTableRequest;
import org.lance.namespace.model.UpdateTableResponse;

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
public class DataApi {
  private final HttpClient memberVarHttpClient;
  private final ObjectMapper memberVarObjectMapper;
  private final String memberVarBaseUri;
  private final Consumer<HttpRequest.Builder> memberVarInterceptor;
  private final Duration memberVarReadTimeout;
  private final Consumer<HttpResponse<InputStream>> memberVarResponseInterceptor;
  private final Consumer<HttpResponse<String>> memberVarAsyncResponseInterceptor;

  public DataApi() {
    this(Configuration.getDefaultApiClient());
  }

  public DataApi(ApiClient apiClient) {
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
   * Add new columns to table schema Add new columns to table &#x60;id&#x60; using SQL expressions
   * or default values.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableAddColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;AlterTableAddColumnsResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<AlterTableAddColumnsResponse> alterTableAddColumns(
      String id, AlterTableAddColumnsRequest alterTableAddColumnsRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          alterTableAddColumnsRequestBuilder(id, alterTableAddColumnsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("alterTableAddColumns", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<AlterTableAddColumnsResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Add new columns to table schema Add new columns to table &#x60;id&#x60; using SQL expressions
   * or default values.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableAddColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;AlterTableAddColumnsResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<AlterTableAddColumnsResponse>>
      alterTableAddColumnsWithHttpInfo(
          String id, AlterTableAddColumnsRequest alterTableAddColumnsRequest, String delimiter)
          throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          alterTableAddColumnsRequestBuilder(id, alterTableAddColumnsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("alterTableAddColumns", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<AlterTableAddColumnsResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody,
                                  new TypeReference<AlterTableAddColumnsResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder alterTableAddColumnsRequestBuilder(
      String id, AlterTableAddColumnsRequest alterTableAddColumnsRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling alterTableAddColumns");
    }
    // verify the required parameter 'alterTableAddColumnsRequest' is set
    if (alterTableAddColumnsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'alterTableAddColumnsRequest' when calling alterTableAddColumns");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/add_columns".replace("{id}", ApiClient.urlEncode(id.toString()));

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
          memberVarObjectMapper.writeValueAsBytes(alterTableAddColumnsRequest);
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
   * Trigger an async column backfill job Trigger an asynchronous backfill job for a computed column
   * on table &#x60;id&#x60;. The column must be a virtual (UDF-backed) column. Returns a job ID for
   * tracking.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableBackfillColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;AlterTableBackfillColumnsResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<AlterTableBackfillColumnsResponse> alterTableBackfillColumns(
      String id,
      AlterTableBackfillColumnsRequest alterTableBackfillColumnsRequest,
      String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          alterTableBackfillColumnsRequestBuilder(id, alterTableBackfillColumnsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("alterTableBackfillColumns", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody,
                              new TypeReference<AlterTableBackfillColumnsResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Trigger an async column backfill job Trigger an asynchronous backfill job for a computed column
   * on table &#x60;id&#x60;. The column must be a virtual (UDF-backed) column. Returns a job ID for
   * tracking.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableBackfillColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;AlterTableBackfillColumnsResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<AlterTableBackfillColumnsResponse>>
      alterTableBackfillColumnsWithHttpInfo(
          String id,
          AlterTableBackfillColumnsRequest alterTableBackfillColumnsRequest,
          String delimiter)
          throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          alterTableBackfillColumnsRequestBuilder(id, alterTableBackfillColumnsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("alterTableBackfillColumns", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<AlterTableBackfillColumnsResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody,
                                  new TypeReference<AlterTableBackfillColumnsResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder alterTableBackfillColumnsRequestBuilder(
      String id,
      AlterTableBackfillColumnsRequest alterTableBackfillColumnsRequest,
      String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling alterTableBackfillColumns");
    }
    // verify the required parameter 'alterTableBackfillColumnsRequest' is set
    if (alterTableBackfillColumnsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'alterTableBackfillColumnsRequest' when calling alterTableBackfillColumns");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/backfill_column".replace("{id}", ApiClient.urlEncode(id.toString()));

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
          memberVarObjectMapper.writeValueAsBytes(alterTableBackfillColumnsRequest);
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
   * Analyze query execution plan Analyze the query execution plan for a query against table
   * &#x60;id&#x60;. Returns detailed statistics and analysis of the query execution plan. REST
   * NAMESPACE ONLY REST namespace returns the response as a plain string instead of the
   * &#x60;AnalyzeTableQueryPlanResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param analyzeTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;String&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<String> analyzeTableQueryPlan(
      String id, AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          analyzeTableQueryPlanRequestBuilder(id, analyzeTableQueryPlanRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("analyzeTableQueryPlan", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<String>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Analyze query execution plan Analyze the query execution plan for a query against table
   * &#x60;id&#x60;. Returns detailed statistics and analysis of the query execution plan. REST
   * NAMESPACE ONLY REST namespace returns the response as a plain string instead of the
   * &#x60;AnalyzeTableQueryPlanResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param analyzeTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;String&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<String>> analyzeTableQueryPlanWithHttpInfo(
      String id, AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          analyzeTableQueryPlanRequestBuilder(id, analyzeTableQueryPlanRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("analyzeTableQueryPlan", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<String>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<String>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder analyzeTableQueryPlanRequestBuilder(
      String id, AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling analyzeTableQueryPlan");
    }
    // verify the required parameter 'analyzeTableQueryPlanRequest' is set
    if (analyzeTableQueryPlanRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'analyzeTableQueryPlanRequest' when calling analyzeTableQueryPlan");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/analyze_plan".replace("{id}", ApiClient.urlEncode(id.toString()));

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
          memberVarObjectMapper.writeValueAsBytes(analyzeTableQueryPlanRequest);
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
   * Count rows in a table Count the number of rows in table &#x60;id&#x60; REST NAMESPACE ONLY REST
   * namespace returns the response as a plain integer instead of the
   * &#x60;CountTableRowsResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param countTableRowsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;Long&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<Long> countTableRows(
      String id, CountTableRowsRequest countTableRowsRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          countTableRowsRequestBuilder(id, countTableRowsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("countTableRows", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<Long>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Count rows in a table Count the number of rows in table &#x60;id&#x60; REST NAMESPACE ONLY REST
   * namespace returns the response as a plain integer instead of the
   * &#x60;CountTableRowsResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param countTableRowsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;Long&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<Long>> countTableRowsWithHttpInfo(
      String id, CountTableRowsRequest countTableRowsRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          countTableRowsRequestBuilder(id, countTableRowsRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("countTableRows", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<Long>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<Long>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder countTableRowsRequestBuilder(
      String id, CountTableRowsRequest countTableRowsRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling countTableRows");
    }
    // verify the required parameter 'countTableRowsRequest' is set
    if (countTableRowsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'countTableRowsRequest' when calling countTableRows");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/count_rows".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(countTableRowsRequest);
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
   * Create a table with the given name Create table &#x60;id&#x60; in the namespace with the given
   * data in Arrow IPC stream. The schema of the Arrow IPC stream is used as the table schema. If
   * the stream is empty, the API creates a new empty table. REST NAMESPACE ONLY REST namespace uses
   * Arrow IPC stream as the request body. It passes in the &#x60;CreateTableRequest&#x60;
   * information in the following way: - &#x60;id&#x60;: pass through path parameter of the same
   * name - &#x60;mode&#x60;: pass through query parameter of the same name -
   * &#x60;properties&#x60;: serialize as a single JSON-encoded query parameter such as
   * &#x60;properties&#x3D;{\&quot;user\&quot;:\&quot;alice\&quot;,\&quot;team\&quot;:\&quot;eng\&quot;}&#x60;;
   * these are business logic properties managed by the namespace implementation outside Lance
   * context - &#x60;storage_options&#x60;: serialize as a single JSON-encoded query parameter such
   * as
   * &#x60;storage_options&#x3D;{\&quot;aws_region\&quot;:\&quot;us-east-1\&quot;,\&quot;timeout\&quot;:\&quot;30s\&quot;}&#x60;;
   * these configure write-time overrides for data and metadata written during table creation
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param mode (optional)
   * @param properties Business logic properties managed by the namespace implementation outside
   *     Lance context. The map is translated to a single JSON-encoded query parameter such as
   *     &#x60;properties&#x3D;{\&quot;user\&quot;:\&quot;alice\&quot;,\&quot;team\&quot;:\&quot;eng\&quot;}&#x60;.
   *     (optional)
   * @param storageOptions Storage options that configure overrides for writing table data and
   *     metadata during table creation. These are passed to Lance for the write path. The map is
   *     translated to a single JSON-encoded query parameter such as
   *     &#x60;storage_options&#x3D;{\&quot;aws_region\&quot;:\&quot;us-east-1\&quot;,\&quot;timeout\&quot;:\&quot;30s\&quot;}&#x60;.
   *     (optional)
   * @return CompletableFuture&lt;CreateTableResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<CreateTableResponse> createTable(
      String id,
      byte[] body,
      String delimiter,
      String mode,
      String properties,
      String storageOptions)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          createTableRequestBuilder(id, body, delimiter, mode, properties, storageOptions);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("createTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<CreateTableResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Create a table with the given name Create table &#x60;id&#x60; in the namespace with the given
   * data in Arrow IPC stream. The schema of the Arrow IPC stream is used as the table schema. If
   * the stream is empty, the API creates a new empty table. REST NAMESPACE ONLY REST namespace uses
   * Arrow IPC stream as the request body. It passes in the &#x60;CreateTableRequest&#x60;
   * information in the following way: - &#x60;id&#x60;: pass through path parameter of the same
   * name - &#x60;mode&#x60;: pass through query parameter of the same name -
   * &#x60;properties&#x60;: serialize as a single JSON-encoded query parameter such as
   * &#x60;properties&#x3D;{\&quot;user\&quot;:\&quot;alice\&quot;,\&quot;team\&quot;:\&quot;eng\&quot;}&#x60;;
   * these are business logic properties managed by the namespace implementation outside Lance
   * context - &#x60;storage_options&#x60;: serialize as a single JSON-encoded query parameter such
   * as
   * &#x60;storage_options&#x3D;{\&quot;aws_region\&quot;:\&quot;us-east-1\&quot;,\&quot;timeout\&quot;:\&quot;30s\&quot;}&#x60;;
   * these configure write-time overrides for data and metadata written during table creation
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param mode (optional)
   * @param properties Business logic properties managed by the namespace implementation outside
   *     Lance context. The map is translated to a single JSON-encoded query parameter such as
   *     &#x60;properties&#x3D;{\&quot;user\&quot;:\&quot;alice\&quot;,\&quot;team\&quot;:\&quot;eng\&quot;}&#x60;.
   *     (optional)
   * @param storageOptions Storage options that configure overrides for writing table data and
   *     metadata during table creation. These are passed to Lance for the write path. The map is
   *     translated to a single JSON-encoded query parameter such as
   *     &#x60;storage_options&#x3D;{\&quot;aws_region\&quot;:\&quot;us-east-1\&quot;,\&quot;timeout\&quot;:\&quot;30s\&quot;}&#x60;.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;CreateTableResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<CreateTableResponse>> createTableWithHttpInfo(
      String id,
      byte[] body,
      String delimiter,
      String mode,
      String properties,
      String storageOptions)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          createTableRequestBuilder(id, body, delimiter, mode, properties, storageOptions);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("createTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<CreateTableResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<CreateTableResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder createTableRequestBuilder(
      String id,
      byte[] body,
      String delimiter,
      String mode,
      String properties,
      String storageOptions)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling createTable");
    }
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createTable");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/create".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));
    localVarQueryParameterBaseName = "mode";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("mode", mode));
    localVarQueryParameterBaseName = "properties";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("properties", properties));
    localVarQueryParameterBaseName = "storage_options";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("storage_options", storageOptions));

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

    localVarRequestBuilder.header("Content-Type", "application/vnd.apache.arrow.stream");
    localVarRequestBuilder.header("Accept", "application/json");

    try {
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(body);
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
   * Delete rows from a table Delete rows from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteFromTableRequest Delete request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;DeleteFromTableResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<DeleteFromTableResponse> deleteFromTable(
      String id, DeleteFromTableRequest deleteFromTableRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          deleteFromTableRequestBuilder(id, deleteFromTableRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("deleteFromTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<DeleteFromTableResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Delete rows from a table Delete rows from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteFromTableRequest Delete request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;DeleteFromTableResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<DeleteFromTableResponse>> deleteFromTableWithHttpInfo(
      String id, DeleteFromTableRequest deleteFromTableRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          deleteFromTableRequestBuilder(id, deleteFromTableRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("deleteFromTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<DeleteFromTableResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<DeleteFromTableResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder deleteFromTableRequestBuilder(
      String id, DeleteFromTableRequest deleteFromTableRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling deleteFromTable");
    }
    // verify the required parameter 'deleteFromTableRequest' is set
    if (deleteFromTableRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'deleteFromTableRequest' when calling deleteFromTable");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/delete".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(deleteFromTableRequest);
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
   * Get query execution plan explanation Get the query execution plan for a query against table
   * &#x60;id&#x60;. Returns a human-readable explanation of how the query will be executed. REST
   * NAMESPACE ONLY REST namespace returns the response as a plain string instead of the
   * &#x60;ExplainTableQueryPlanResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param explainTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;String&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<String> explainTableQueryPlan(
      String id, ExplainTableQueryPlanRequest explainTableQueryPlanRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          explainTableQueryPlanRequestBuilder(id, explainTableQueryPlanRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("explainTableQueryPlan", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<String>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Get query execution plan explanation Get the query execution plan for a query against table
   * &#x60;id&#x60;. Returns a human-readable explanation of how the query will be executed. REST
   * NAMESPACE ONLY REST namespace returns the response as a plain string instead of the
   * &#x60;ExplainTableQueryPlanResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param explainTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;String&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<String>> explainTableQueryPlanWithHttpInfo(
      String id, ExplainTableQueryPlanRequest explainTableQueryPlanRequest, String delimiter)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          explainTableQueryPlanRequestBuilder(id, explainTableQueryPlanRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("explainTableQueryPlan", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<String>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<String>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder explainTableQueryPlanRequestBuilder(
      String id, ExplainTableQueryPlanRequest explainTableQueryPlanRequest, String delimiter)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling explainTableQueryPlan");
    }
    // verify the required parameter 'explainTableQueryPlanRequest' is set
    if (explainTableQueryPlanRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'explainTableQueryPlanRequest' when calling explainTableQueryPlan");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/explain_plan".replace("{id}", ApiClient.urlEncode(id.toString()));

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
          memberVarObjectMapper.writeValueAsBytes(explainTableQueryPlanRequest);
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
   * Insert records into a table Insert new records into table &#x60;id&#x60;. For tables that have
   * been declared but not yet created on storage (is_only_declared&#x3D;true), this operation will
   * create the table with the provided data. REST NAMESPACE ONLY REST namespace uses Arrow IPC
   * stream as the request body. It passes in the &#x60;InsertIntoTableRequest&#x60; information in
   * the following way: - &#x60;id&#x60;: pass through path parameter of the same name -
   * &#x60;mode&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC stream containing the records to insert (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param branch Optional branch to target. When not specified, the main branch is used. Used by
   *     branch-scoped operations that cannot carry a &#x60;branch&#x60; field in their request body
   *     (Arrow IPC stream and bodyless operations). Operations with a JSON request body carry
   *     &#x60;branch&#x60; as a body field instead. (optional)
   * @param mode How the insert should behave. Case insensitive, supports both PascalCase and
   *     snake_case. Valid values are: - Append (default): insert data to the existing table -
   *     Overwrite: remove all data in the table and then insert data to it (optional, default to
   *     append)
   * @return CompletableFuture&lt;InsertIntoTableResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<InsertIntoTableResponse> insertIntoTable(
      String id, byte[] body, String delimiter, String branch, String mode) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          insertIntoTableRequestBuilder(id, body, delimiter, branch, mode);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("insertIntoTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<InsertIntoTableResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Insert records into a table Insert new records into table &#x60;id&#x60;. For tables that have
   * been declared but not yet created on storage (is_only_declared&#x3D;true), this operation will
   * create the table with the provided data. REST NAMESPACE ONLY REST namespace uses Arrow IPC
   * stream as the request body. It passes in the &#x60;InsertIntoTableRequest&#x60; information in
   * the following way: - &#x60;id&#x60;: pass through path parameter of the same name -
   * &#x60;mode&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC stream containing the records to insert (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param branch Optional branch to target. When not specified, the main branch is used. Used by
   *     branch-scoped operations that cannot carry a &#x60;branch&#x60; field in their request body
   *     (Arrow IPC stream and bodyless operations). Operations with a JSON request body carry
   *     &#x60;branch&#x60; as a body field instead. (optional)
   * @param mode How the insert should behave. Case insensitive, supports both PascalCase and
   *     snake_case. Valid values are: - Append (default): insert data to the existing table -
   *     Overwrite: remove all data in the table and then insert data to it (optional, default to
   *     append)
   * @return CompletableFuture&lt;ApiResponse&lt;InsertIntoTableResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<InsertIntoTableResponse>> insertIntoTableWithHttpInfo(
      String id, byte[] body, String delimiter, String branch, String mode) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          insertIntoTableRequestBuilder(id, body, delimiter, branch, mode);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("insertIntoTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<InsertIntoTableResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<InsertIntoTableResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder insertIntoTableRequestBuilder(
      String id, byte[] body, String delimiter, String branch, String mode) throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling insertIntoTable");
    }
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(
          400, "Missing the required parameter 'body' when calling insertIntoTable");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/insert".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));
    localVarQueryParameterBaseName = "branch";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("branch", branch));
    localVarQueryParameterBaseName = "mode";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("mode", mode));

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

    localVarRequestBuilder.header("Content-Type", "application/vnd.apache.arrow.stream");
    localVarRequestBuilder.header("Accept", "application/json");

    try {
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(body);
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
   * Merge insert (upsert) records into a table Performs a merge insert (upsert) operation on table
   * &#x60;id&#x60;. This operation updates existing rows based on a matching column and inserts new
   * rows that don&#39;t match. It returns the number of rows inserted and updated. For tables that
   * have been declared but not yet created on storage (is_only_declared&#x3D;true), this operation
   * will create the table with the provided data (since there are no existing rows to merge with).
   * REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the
   * &#x60;MergeInsertIntoTableRequest&#x60; information in the following way: - &#x60;id&#x60;:
   * pass through path parameter of the same name - &#x60;on&#x60;: pass through query parameter of
   * the same name - &#x60;when_matched_update_all&#x60;: pass through query parameter of the same
   * name - &#x60;when_matched_update_all_filt&#x60;: pass through query parameter of the same name
   * - &#x60;when_not_matched_insert_all&#x60;: pass through query parameter of the same name -
   * &#x60;when_not_matched_by_source_delete&#x60;: pass through query parameter of the same name -
   * &#x60;when_not_matched_by_source_delete_filt&#x60;: pass through query parameter of the same
   * name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param on Column name to use for matching rows (required) (required)
   * @param body Arrow IPC stream containing the records to merge (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param branch Optional branch to target. When not specified, the main branch is used. Used by
   *     branch-scoped operations that cannot carry a &#x60;branch&#x60; field in their request body
   *     (Arrow IPC stream and bodyless operations). Operations with a JSON request body carry
   *     &#x60;branch&#x60; as a body field instead. (optional)
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenMatchedUpdateAllFilt The row is updated (similar to UpdateAll) only for rows where
   *     the SQL expression evaluates to true (optional)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @param whenNotMatchedBySourceDelete Delete all rows from target table that don&#39;t match a
   *     row in the source table (optional, default to false)
   * @param whenNotMatchedBySourceDeleteFilt Delete rows from the target table if there is no match
   *     AND the SQL expression evaluates to true (optional)
   * @param timeout Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) (optional)
   * @param useIndex Whether to use index for matching rows (optional, default to false)
   * @return CompletableFuture&lt;MergeInsertIntoTableResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<MergeInsertIntoTableResponse> mergeInsertIntoTable(
      String id,
      String on,
      byte[] body,
      String delimiter,
      String branch,
      Boolean whenMatchedUpdateAll,
      String whenMatchedUpdateAllFilt,
      Boolean whenNotMatchedInsertAll,
      Boolean whenNotMatchedBySourceDelete,
      String whenNotMatchedBySourceDeleteFilt,
      String timeout,
      Boolean useIndex)
      throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          mergeInsertIntoTableRequestBuilder(
              id,
              on,
              body,
              delimiter,
              branch,
              whenMatchedUpdateAll,
              whenMatchedUpdateAllFilt,
              whenNotMatchedInsertAll,
              whenNotMatchedBySourceDelete,
              whenNotMatchedBySourceDeleteFilt,
              timeout,
              useIndex);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("mergeInsertIntoTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<MergeInsertIntoTableResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Merge insert (upsert) records into a table Performs a merge insert (upsert) operation on table
   * &#x60;id&#x60;. This operation updates existing rows based on a matching column and inserts new
   * rows that don&#39;t match. It returns the number of rows inserted and updated. For tables that
   * have been declared but not yet created on storage (is_only_declared&#x3D;true), this operation
   * will create the table with the provided data (since there are no existing rows to merge with).
   * REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the
   * &#x60;MergeInsertIntoTableRequest&#x60; information in the following way: - &#x60;id&#x60;:
   * pass through path parameter of the same name - &#x60;on&#x60;: pass through query parameter of
   * the same name - &#x60;when_matched_update_all&#x60;: pass through query parameter of the same
   * name - &#x60;when_matched_update_all_filt&#x60;: pass through query parameter of the same name
   * - &#x60;when_not_matched_insert_all&#x60;: pass through query parameter of the same name -
   * &#x60;when_not_matched_by_source_delete&#x60;: pass through query parameter of the same name -
   * &#x60;when_not_matched_by_source_delete_filt&#x60;: pass through query parameter of the same
   * name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param on Column name to use for matching rows (required) (required)
   * @param body Arrow IPC stream containing the records to merge (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param branch Optional branch to target. When not specified, the main branch is used. Used by
   *     branch-scoped operations that cannot carry a &#x60;branch&#x60; field in their request body
   *     (Arrow IPC stream and bodyless operations). Operations with a JSON request body carry
   *     &#x60;branch&#x60; as a body field instead. (optional)
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenMatchedUpdateAllFilt The row is updated (similar to UpdateAll) only for rows where
   *     the SQL expression evaluates to true (optional)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @param whenNotMatchedBySourceDelete Delete all rows from target table that don&#39;t match a
   *     row in the source table (optional, default to false)
   * @param whenNotMatchedBySourceDeleteFilt Delete rows from the target table if there is no match
   *     AND the SQL expression evaluates to true (optional)
   * @param timeout Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) (optional)
   * @param useIndex Whether to use index for matching rows (optional, default to false)
   * @return CompletableFuture&lt;ApiResponse&lt;MergeInsertIntoTableResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<MergeInsertIntoTableResponse>>
      mergeInsertIntoTableWithHttpInfo(
          String id,
          String on,
          byte[] body,
          String delimiter,
          String branch,
          Boolean whenMatchedUpdateAll,
          String whenMatchedUpdateAllFilt,
          Boolean whenNotMatchedInsertAll,
          Boolean whenNotMatchedBySourceDelete,
          String whenNotMatchedBySourceDeleteFilt,
          String timeout,
          Boolean useIndex)
          throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          mergeInsertIntoTableRequestBuilder(
              id,
              on,
              body,
              delimiter,
              branch,
              whenMatchedUpdateAll,
              whenMatchedUpdateAllFilt,
              whenNotMatchedInsertAll,
              whenNotMatchedBySourceDelete,
              whenNotMatchedBySourceDeleteFilt,
              timeout,
              useIndex);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("mergeInsertIntoTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<MergeInsertIntoTableResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody,
                                  new TypeReference<MergeInsertIntoTableResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder mergeInsertIntoTableRequestBuilder(
      String id,
      String on,
      byte[] body,
      String delimiter,
      String branch,
      Boolean whenMatchedUpdateAll,
      String whenMatchedUpdateAllFilt,
      Boolean whenNotMatchedInsertAll,
      Boolean whenNotMatchedBySourceDelete,
      String whenNotMatchedBySourceDeleteFilt,
      String timeout,
      Boolean useIndex)
      throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling mergeInsertIntoTable");
    }
    // verify the required parameter 'on' is set
    if (on == null) {
      throw new ApiException(
          400, "Missing the required parameter 'on' when calling mergeInsertIntoTable");
    }
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(
          400, "Missing the required parameter 'body' when calling mergeInsertIntoTable");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/merge_insert".replace("{id}", ApiClient.urlEncode(id.toString()));

    List<Pair> localVarQueryParams = new ArrayList<>();
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    localVarQueryParameterBaseName = "delimiter";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("delimiter", delimiter));
    localVarQueryParameterBaseName = "branch";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("branch", branch));
    localVarQueryParameterBaseName = "on";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("on", on));
    localVarQueryParameterBaseName = "when_matched_update_all";
    localVarQueryParams.addAll(
        ApiClient.parameterToPairs("when_matched_update_all", whenMatchedUpdateAll));
    localVarQueryParameterBaseName = "when_matched_update_all_filt";
    localVarQueryParams.addAll(
        ApiClient.parameterToPairs("when_matched_update_all_filt", whenMatchedUpdateAllFilt));
    localVarQueryParameterBaseName = "when_not_matched_insert_all";
    localVarQueryParams.addAll(
        ApiClient.parameterToPairs("when_not_matched_insert_all", whenNotMatchedInsertAll));
    localVarQueryParameterBaseName = "when_not_matched_by_source_delete";
    localVarQueryParams.addAll(
        ApiClient.parameterToPairs(
            "when_not_matched_by_source_delete", whenNotMatchedBySourceDelete));
    localVarQueryParameterBaseName = "when_not_matched_by_source_delete_filt";
    localVarQueryParams.addAll(
        ApiClient.parameterToPairs(
            "when_not_matched_by_source_delete_filt", whenNotMatchedBySourceDeleteFilt));
    localVarQueryParameterBaseName = "timeout";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("timeout", timeout));
    localVarQueryParameterBaseName = "use_index";
    localVarQueryParams.addAll(ApiClient.parameterToPairs("use_index", useIndex));

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

    localVarRequestBuilder.header("Content-Type", "application/vnd.apache.arrow.stream");
    localVarRequestBuilder.header("Accept", "application/json");

    try {
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(body);
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
   * Query a table Query table &#x60;id&#x60; with vector search, full text search and optional SQL
   * filtering. Returns results in Arrow IPC file or stream format. REST NAMESPACE ONLY REST
   * namespace returns the response as Arrow IPC file binary data instead of the
   * &#x60;QueryTableResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;byte[]&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<byte[]> queryTable(
      String id, QueryTableRequest queryTableRequest, String delimiter) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          queryTableRequestBuilder(id, queryTableRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofInputStream())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  try {
                    InputStream responseBody = localVarResponse.body();
                    String responseText = null;
                    try {
                      responseText =
                          responseBody == null ? null : new String(responseBody.readAllBytes());
                    } finally {
                      if (responseBody != null) {
                        responseBody.close();
                      }
                    }
                    String message =
                        formatExceptionMessage(
                            "queryTable", localVarResponse.statusCode(), responseText);
                    return CompletableFuture.failedFuture(
                        new ApiException(
                            localVarResponse.statusCode(),
                            message,
                            localVarResponse.headers(),
                            responseText));
                  } catch (IOException e) {
                    return CompletableFuture.failedFuture(new ApiException(e));
                  }
                }
                try {
                  InputStream responseBody = localVarResponse.body();
                  if (responseBody == null) {
                    return CompletableFuture.completedFuture(null);
                  }
                  try {
                    return CompletableFuture.completedFuture(responseBody.readAllBytes());
                  } finally {
                    responseBody.close();
                  }
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Query a table Query table &#x60;id&#x60; with vector search, full text search and optional SQL
   * filtering. Returns results in Arrow IPC file or stream format. REST NAMESPACE ONLY REST
   * namespace returns the response as Arrow IPC file binary data instead of the
   * &#x60;QueryTableResponse&#x60; JSON object.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;byte[]&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<byte[]>> queryTableWithHttpInfo(
      String id, QueryTableRequest queryTableRequest, String delimiter) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          queryTableRequestBuilder(id, queryTableRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofInputStream())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  try {
                    InputStream responseBody = localVarResponse.body();
                    String responseText = null;
                    try {
                      responseText =
                          responseBody == null ? null : new String(responseBody.readAllBytes());
                    } finally {
                      if (responseBody != null) {
                        responseBody.close();
                      }
                    }
                    String message =
                        formatExceptionMessage(
                            "queryTable", localVarResponse.statusCode(), responseText);
                    return CompletableFuture.failedFuture(
                        new ApiException(
                            localVarResponse.statusCode(),
                            message,
                            localVarResponse.headers(),
                            responseText));
                  } catch (IOException e) {
                    return CompletableFuture.failedFuture(new ApiException(e));
                  }
                }
                try {
                  InputStream responseBody = localVarResponse.body();
                  byte[] responseBytes = null;
                  try {
                    responseBytes = responseBody == null ? null : responseBody.readAllBytes();
                  } finally {
                    if (responseBody != null) {
                      responseBody.close();
                    }
                  }
                  return CompletableFuture.completedFuture(
                      new ApiResponse<byte[]>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBytes));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder queryTableRequestBuilder(
      String id, QueryTableRequest queryTableRequest, String delimiter) throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling queryTable");
    }
    // verify the required parameter 'queryTableRequest' is set
    if (queryTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'queryTableRequest' when calling queryTable");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/query".replace("{id}", ApiClient.urlEncode(id.toString()));

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
    localVarRequestBuilder.header("Accept", "application/vnd.apache.arrow.file, application/json");

    try {
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(queryTableRequest);
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

  /**
   * Update rows in a table Update existing rows in table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableRequest Update request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;UpdateTableResponse&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<UpdateTableResponse> updateTable(
      String id, UpdateTableRequest updateTableRequest, String delimiter) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          updateTableRequestBuilder(id, updateTableRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("updateTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      responseBody == null || responseBody.isBlank()
                          ? null
                          : memberVarObjectMapper.readValue(
                              responseBody, new TypeReference<UpdateTableResponse>() {}));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  /**
   * Update rows in a table Update existing rows in table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableRequest Update request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CompletableFuture&lt;ApiResponse&lt;UpdateTableResponse&gt;&gt;
   * @throws ApiException if fails to make API call
   */
  public CompletableFuture<ApiResponse<UpdateTableResponse>> updateTableWithHttpInfo(
      String id, UpdateTableRequest updateTableRequest, String delimiter) throws ApiException {
    try {
      HttpRequest.Builder localVarRequestBuilder =
          updateTableRequestBuilder(id, updateTableRequest, delimiter);
      return memberVarHttpClient
          .sendAsync(localVarRequestBuilder.build(), HttpResponse.BodyHandlers.ofString())
          .thenComposeAsync(
              localVarResponse -> {
                if (memberVarAsyncResponseInterceptor != null) {
                  memberVarAsyncResponseInterceptor.accept(localVarResponse);
                }
                if (localVarResponse.statusCode() / 100 != 2) {
                  return CompletableFuture.failedFuture(
                      getApiException("updateTable", localVarResponse));
                }
                try {
                  String responseBody = localVarResponse.body();
                  return CompletableFuture.completedFuture(
                      new ApiResponse<UpdateTableResponse>(
                          localVarResponse.statusCode(),
                          localVarResponse.headers().map(),
                          responseBody == null || responseBody.isBlank()
                              ? null
                              : memberVarObjectMapper.readValue(
                                  responseBody, new TypeReference<UpdateTableResponse>() {})));
                } catch (IOException e) {
                  return CompletableFuture.failedFuture(new ApiException(e));
                }
              });
    } catch (ApiException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private HttpRequest.Builder updateTableRequestBuilder(
      String id, UpdateTableRequest updateTableRequest, String delimiter) throws ApiException {
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateTable");
    }
    // verify the required parameter 'updateTableRequest' is set
    if (updateTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'updateTableRequest' when calling updateTable");
    }

    HttpRequest.Builder localVarRequestBuilder = HttpRequest.newBuilder();

    String localVarPath =
        "/v1/table/{id}/update".replace("{id}", ApiClient.urlEncode(id.toString()));

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
      byte[] localVarPostBody = memberVarObjectMapper.writeValueAsBytes(updateTableRequest);
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
