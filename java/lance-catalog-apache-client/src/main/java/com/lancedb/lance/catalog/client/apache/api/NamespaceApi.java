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
package com.lancedb.lance.catalog.client.apache.api;

import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.BaseApi;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.Pair;
import com.lancedb.lance.catalog.client.apache.model.CreateNamespaceRequest;
import com.lancedb.lance.catalog.client.apache.model.CreateNamespaceResponse;
import com.lancedb.lance.catalog.client.apache.model.GetNamespaceResponse;
import com.lancedb.lance.catalog.client.apache.model.GetTransactionResponse;
import com.lancedb.lance.catalog.client.apache.model.ListNamespacesResponse;

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
   * Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to
   * manage one or more tables. There are three modes when trying to create a namespace: * CREATE:
   * Create the namespace if it does not exist. If a namespace of the same name already exists, the
   * operation fails with 400. * EXIST_OK: Create the namespace if it does not exist. If a namespace
   * of the same name already exists, the operation succeeds and the existing namespace is kept. *
   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already
   * exists, the existing namespace is dropped and a new namespace with this name with no table is
   * created.
   *
   * @param createNamespaceRequest (required)
   * @return CreateNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest createNamespaceRequest)
      throws ApiException {
    return this.createNamespace(createNamespaceRequest, Collections.emptyMap());
  }

  /**
   * Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to
   * manage one or more tables. There are three modes when trying to create a namespace: * CREATE:
   * Create the namespace if it does not exist. If a namespace of the same name already exists, the
   * operation fails with 400. * EXIST_OK: Create the namespace if it does not exist. If a namespace
   * of the same name already exists, the operation succeeds and the existing namespace is kept. *
   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already
   * exists, the existing namespace is dropped and a new namespace with this name with no table is
   * created.
   *
   * @param createNamespaceRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public CreateNamespaceResponse createNamespace(
      CreateNamespaceRequest createNamespaceRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createNamespaceRequest;

    // verify the required parameter 'createNamespaceRequest' is set
    if (createNamespaceRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'createNamespaceRequest' when calling createNamespace");
    }

    // create path and map variables
    String localVarPath = "/v1/namespaces";

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
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
   * Drop a namespace from the catalog. Namespace must be empty. Drop a namespace from the catalog.
   * If the operation is completed immediately, the server should respond with 204. If the operation
   * is long running, the server should respond with 202 to provide a transaction that the client
   * can use to track namespace drop progress.
   *
   * @param ns The name of the namespace. (required)
   * @param mode The mode for dropping a namespace, deciding the server behavior when the namespace
   *     to drop is not found. FAIL (default): the server must return 400 indicating the namespace
   *     to drop does not exist. SKIP: the server must return 204 indicating the drop operation has
   *     succeeded. (optional)
   * @param behavior The behavior for dropping a namespace. RESTRICT (default): the namespace should
   *     not contain any table when drop is initiated. If tables are found, the server should return
   *     error and not drop the namespace. CASCADE: all tables in the namespace are dropped before
   *     the namespace is dropped. (optional)
   * @return GetTransactionResponse
   * @throws ApiException if fails to make API call
   */
  public GetTransactionResponse dropNamespace(String ns, String mode, String behavior)
      throws ApiException {
    return this.dropNamespace(ns, mode, behavior, Collections.emptyMap());
  }

  /**
   * Drop a namespace from the catalog. Namespace must be empty. Drop a namespace from the catalog.
   * If the operation is completed immediately, the server should respond with 204. If the operation
   * is long running, the server should respond with 202 to provide a transaction that the client
   * can use to track namespace drop progress.
   *
   * @param ns The name of the namespace. (required)
   * @param mode The mode for dropping a namespace, deciding the server behavior when the namespace
   *     to drop is not found. FAIL (default): the server must return 400 indicating the namespace
   *     to drop does not exist. SKIP: the server must return 204 indicating the drop operation has
   *     succeeded. (optional)
   * @param behavior The behavior for dropping a namespace. RESTRICT (default): the namespace should
   *     not contain any table when drop is initiated. If tables are found, the server should return
   *     error and not drop the namespace. CASCADE: all tables in the namespace are dropped before
   *     the namespace is dropped. (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetTransactionResponse
   * @throws ApiException if fails to make API call
   */
  public GetTransactionResponse dropNamespace(
      String ns, String mode, String behavior, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'ns' is set
    if (ns == null) {
      throw new ApiException(400, "Missing the required parameter 'ns' when calling dropNamespace");
    }

    // create path and map variables
    String localVarPath =
        "/v1/namespaces/{ns}"
            .replaceAll(
                "\\{" + "ns" + "\\}", apiClient.escapeString(apiClient.parameterToString(ns)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("mode", mode));
    localVarQueryParams.addAll(apiClient.parameterToPair("behavior", behavior));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<GetTransactionResponse> localVarReturnType =
        new TypeReference<GetTransactionResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "DELETE",
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
   * Get information about a namespace Return a detailed information for a given namespace
   *
   * @param ns The name of the namespace. (required)
   * @return GetNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public GetNamespaceResponse getNamespace(String ns) throws ApiException {
    return this.getNamespace(ns, Collections.emptyMap());
  }

  /**
   * Get information about a namespace Return a detailed information for a given namespace
   *
   * @param ns The name of the namespace. (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public GetNamespaceResponse getNamespace(String ns, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'ns' is set
    if (ns == null) {
      throw new ApiException(400, "Missing the required parameter 'ns' when calling getNamespace");
    }

    // create path and map variables
    String localVarPath =
        "/v1/namespaces/{ns}"
            .replaceAll(
                "\\{" + "ns" + "\\}", apiClient.escapeString(apiClient.parameterToString(ns)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<GetNamespaceResponse> localVarReturnType =
        new TypeReference<GetNamespaceResponse>() {};
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
   * List all namespaces in the catalog.
   *
   * @param pageToken (optional)
   * @param pageSize An inclusive upper bound of the number of results that a client will receive.
   *     (optional)
   * @return ListNamespacesResponse
   * @throws ApiException if fails to make API call
   */
  public ListNamespacesResponse listNamespaces(String pageToken, Integer pageSize)
      throws ApiException {
    return this.listNamespaces(pageToken, pageSize, Collections.emptyMap());
  }

  /**
   * List all namespaces in the catalog.
   *
   * @param pageToken (optional)
   * @param pageSize An inclusive upper bound of the number of results that a client will receive.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListNamespacesResponse
   * @throws ApiException if fails to make API call
   */
  public ListNamespacesResponse listNamespaces(
      String pageToken, Integer pageSize, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/namespaces";

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("pageToken", pageToken));
    localVarQueryParams.addAll(apiClient.parameterToPair("pageSize", pageSize));

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
   * Check if a namespace exists Check if a namespace exists. The response does not contain a body.
   *
   * @param ns The name of the namespace. (required)
   * @throws ApiException if fails to make API call
   */
  public void namespaceExists(String ns) throws ApiException {
    this.namespaceExists(ns, Collections.emptyMap());
  }

  /**
   * Check if a namespace exists Check if a namespace exists. The response does not contain a body.
   *
   * @param ns The name of the namespace. (required)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void namespaceExists(String ns, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'ns' is set
    if (ns == null) {
      throw new ApiException(
          400, "Missing the required parameter 'ns' when calling namespaceExists");
    }

    // create path and map variables
    String localVarPath =
        "/v1/namespaces/{ns}"
            .replaceAll(
                "\\{" + "ns" + "\\}", apiClient.escapeString(apiClient.parameterToString(ns)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    apiClient.invokeAPI(
        localVarPath,
        "HEAD",
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

    final String[] localVarContentTypes = {};

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
