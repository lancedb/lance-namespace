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
package org.lance.namespace.client.apache.api;

import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.BaseApi;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.Pair;
import org.lance.namespace.model.CreateMaterializedViewRequest;
import org.lance.namespace.model.CreateMaterializedViewResponse;
import org.lance.namespace.model.RefreshMaterializedViewRequest;
import org.lance.namespace.model.RefreshMaterializedViewResponse;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MaterializedViewApi extends BaseApi {

  public MaterializedViewApi() {
    super(Configuration.getDefaultApiClient());
  }

  public MaterializedViewApi(ApiClient apiClient) {
    super(apiClient);
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
   * @return CreateMaterializedViewResponse
   * @throws ApiException if fails to make API call
   */
  public CreateMaterializedViewResponse createMaterializedView(
      String id, CreateMaterializedViewRequest createMaterializedViewRequest, String delimiter)
      throws ApiException {
    return this.createMaterializedView(
        id, createMaterializedViewRequest, delimiter, Collections.emptyMap());
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
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateMaterializedViewResponse
   * @throws ApiException if fails to make API call
   */
  public CreateMaterializedViewResponse createMaterializedView(
      String id,
      CreateMaterializedViewRequest createMaterializedViewRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createMaterializedViewRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/materialized_view/{id}/create"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {"OAuth2", "ApiKeyAuth", "BearerAuth"};

    TypeReference<CreateMaterializedViewResponse> localVarReturnType =
        new TypeReference<CreateMaterializedViewResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarCookieParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
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
   * @return RefreshMaterializedViewResponse
   * @throws ApiException if fails to make API call
   */
  public RefreshMaterializedViewResponse refreshMaterializedView(
      String id, String delimiter, RefreshMaterializedViewRequest refreshMaterializedViewRequest)
      throws ApiException {
    return this.refreshMaterializedView(
        id, delimiter, refreshMaterializedViewRequest, Collections.emptyMap());
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
   * @param additionalHeaders additionalHeaders for this call
   * @return RefreshMaterializedViewResponse
   * @throws ApiException if fails to make API call
   */
  public RefreshMaterializedViewResponse refreshMaterializedView(
      String id,
      String delimiter,
      RefreshMaterializedViewRequest refreshMaterializedViewRequest,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = refreshMaterializedViewRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling refreshMaterializedView");
    }

    // create path and map variables
    String localVarPath =
        "/v1/materialized_view/{id}/refresh"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {"OAuth2", "ApiKeyAuth", "BearerAuth"};

    TypeReference<RefreshMaterializedViewResponse> localVarReturnType =
        new TypeReference<RefreshMaterializedViewResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarCookieParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        localVarReturnType);
  }

  @Override
  public <T> T invokeAPI(
      String url,
      String method,
      Object request,
      TypeReference<T> returnType,
      Map<String, String> additionalHeaders)
      throws ApiException {
    String localVarPath = url.replace(apiClient.getBaseURL(), "");
    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {"OAuth2", "ApiKeyAuth", "BearerAuth"};

    return apiClient.invokeAPI(
        localVarPath,
        method,
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        request,
        localVarHeaderParams,
        localVarCookieParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
        returnType);
  }
}
