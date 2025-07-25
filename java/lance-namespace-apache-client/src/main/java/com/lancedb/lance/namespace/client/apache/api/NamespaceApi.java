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
package com.lancedb.lance.namespace.client.apache.api;

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.BaseApi;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.Pair;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;

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
public class NamespaceApi extends BaseApi {

  public NamespaceApi() {
    super(Configuration.getDefaultApiClient());
  }

  public NamespaceApi(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create a new namespace Create new namespace &#x60;id&#x60;. During the creation process, the
   * implementation may modify user-provided &#x60;properties&#x60;, such as adding additional
   * properties like &#x60;created_at&#x60; to user-provided properties, omitting any specific
   * property, or performing actions based on any property value.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return CreateNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public CreateNamespaceResponse createNamespace(
      String id, CreateNamespaceRequest createNamespaceRequest, String delimiter)
      throws ApiException {
    return this.createNamespace(id, createNamespaceRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Create a new namespace Create new namespace &#x60;id&#x60;. During the creation process, the
   * implementation may modify user-provided &#x60;properties&#x60;, such as adding additional
   * properties like &#x60;created_at&#x60; to user-provided properties, omitting any specific
   * property, or performing actions based on any property value.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public CreateNamespaceResponse createNamespace(
      String id,
      CreateNamespaceRequest createNamespaceRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createNamespaceRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/namespace/{id}/create"
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

    String[] localVarAuthNames = new String[] {};

    TypeReference<CreateNamespaceResponse> localVarReturnType =
        new TypeReference<CreateNamespaceResponse>() {};
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
   * Describe a namespace Describe the detailed information for namespace &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return DescribeNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public DescribeNamespaceResponse describeNamespace(
      String id, DescribeNamespaceRequest describeNamespaceRequest, String delimiter)
      throws ApiException {
    return this.describeNamespace(id, describeNamespaceRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Describe a namespace Describe the detailed information for namespace &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DescribeNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public DescribeNamespaceResponse describeNamespace(
      String id,
      DescribeNamespaceRequest describeNamespaceRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = describeNamespaceRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/namespace/{id}/describe"
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

    String[] localVarAuthNames = new String[] {};

    TypeReference<DescribeNamespaceResponse> localVarReturnType =
        new TypeReference<DescribeNamespaceResponse>() {};
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
   * Drop a namespace Drop namespace &#x60;id&#x60; from its parent namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @return DropNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public DropNamespaceResponse dropNamespace(
      String id, DropNamespaceRequest dropNamespaceRequest, String delimiter) throws ApiException {
    return this.dropNamespace(id, dropNamespaceRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Drop a namespace Drop namespace &#x60;id&#x60; from its parent namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropNamespaceRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DropNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public DropNamespaceResponse dropNamespace(
      String id,
      DropNamespaceRequest dropNamespaceRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = dropNamespaceRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling dropNamespace");
    }

    // verify the required parameter 'dropNamespaceRequest' is set
    if (dropNamespaceRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'dropNamespaceRequest' when calling dropNamespace");
    }

    // create path and map variables
    String localVarPath =
        "/v1/namespace/{id}/drop"
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

    String[] localVarAuthNames = new String[] {};

    TypeReference<DropNamespaceResponse> localVarReturnType =
        new TypeReference<DropNamespaceResponse>() {};
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
   * List namespaces List all child namespace names of the parent namespace &#x60;id&#x60;. REST
   * NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It
   * passes in the &#x60;ListNamespacesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @return ListNamespacesResponse
   * @throws ApiException if fails to make API call
   */
  public ListNamespacesResponse listNamespaces(
      String id, String delimiter, String pageToken, Integer limit) throws ApiException {
    return this.listNamespaces(id, delimiter, pageToken, limit, Collections.emptyMap());
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
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListNamespacesResponse
   * @throws ApiException if fails to make API call
   */
  public ListNamespacesResponse listNamespaces(
      String id,
      String delimiter,
      String pageToken,
      Integer limit,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling listNamespaces");
    }

    // create path and map variables
    String localVarPath =
        "/v1/namespace/{id}/list"
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
    localVarQueryParams.addAll(apiClient.parameterToPair("page_token", pageToken));
    localVarQueryParams.addAll(apiClient.parameterToPair("limit", limit));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<ListNamespacesResponse> localVarReturnType =
        new TypeReference<ListNamespacesResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
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
   * List tables in a namespace List all child table names of the parent namespace &#x60;id&#x60;.
   * REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body.
   * It passes in the &#x60;ListTablesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @return ListTablesResponse
   * @throws ApiException if fails to make API call
   */
  public ListTablesResponse listTables(String id, String delimiter, String pageToken, Integer limit)
      throws ApiException {
    return this.listTables(id, delimiter, pageToken, limit, Collections.emptyMap());
  }

  /**
   * List tables in a namespace List all child table names of the parent namespace &#x60;id&#x60;.
   * REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body.
   * It passes in the &#x60;ListTablesRequest&#x60; information in the following way: -
   * &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass
   * through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of
   * the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListTablesResponse
   * @throws ApiException if fails to make API call
   */
  public ListTablesResponse listTables(
      String id,
      String delimiter,
      String pageToken,
      Integer limit,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling listTables");
    }

    // create path and map variables
    String localVarPath =
        "/v1/namespace/{id}/table/list"
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
    localVarQueryParams.addAll(apiClient.parameterToPair("page_token", pageToken));
    localVarQueryParams.addAll(apiClient.parameterToPair("limit", limit));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<ListTablesResponse> localVarReturnType =
        new TypeReference<ListTablesResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "GET",
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
   * Check if a namespace exists Check if namespace &#x60;id&#x60; exists. This operation must
   * behave exactly like the DescribeNamespace API, except it does not contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param namespaceExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @throws ApiException if fails to make API call
   */
  public void namespaceExists(
      String id, NamespaceExistsRequest namespaceExistsRequest, String delimiter)
      throws ApiException {
    this.namespaceExists(id, namespaceExistsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Check if a namespace exists Check if namespace &#x60;id&#x60; exists. This operation must
   * behave exactly like the DescribeNamespace API, except it does not contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param namespaceExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void namespaceExists(
      String id,
      NamespaceExistsRequest namespaceExistsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = namespaceExistsRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/namespace/{id}/exists"
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

    String[] localVarAuthNames = new String[] {};

    apiClient.invokeAPI(
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
        null);
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

    String[] localVarAuthNames = new String[] {};

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
