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
import com.lancedb.lance.namespace.client.apache.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.GetNamespaceRequest;
import com.lancedb.lance.namespace.client.apache.model.GetNamespaceResponse;
import com.lancedb.lance.namespace.client.apache.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.client.apache.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.client.apache.model.NamespaceExistsRequest;

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
   * Create a new namespace Create a new namespace. A namespace can manage either a collection of
   * child namespaces, or a collection of tables. There are three modes when trying to create a
   * namespace, to differentiate the behavior when a namespace of the same name already exists: *
   * CREATE: the operation fails with 400. * EXIST_OK: the operation succeeds and the existing
   * namespace is kept. * OVERWRITE: the existing namespace is dropped and a new empty namespace
   * with this name is created.
   *
   * @param createNamespaceRequest (required)
   * @return GetNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public GetNamespaceResponse createNamespace(CreateNamespaceRequest createNamespaceRequest)
      throws ApiException {
    return this.createNamespace(createNamespaceRequest, Collections.emptyMap());
  }

  /**
   * Create a new namespace Create a new namespace. A namespace can manage either a collection of
   * child namespaces, or a collection of tables. There are three modes when trying to create a
   * namespace, to differentiate the behavior when a namespace of the same name already exists: *
   * CREATE: the operation fails with 400. * EXIST_OK: the operation succeeds and the existing
   * namespace is kept. * OVERWRITE: the existing namespace is dropped and a new empty namespace
   * with this name is created.
   *
   * @param createNamespaceRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public GetNamespaceResponse createNamespace(
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
    String localVarPath = "/CreateNamespace";

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

    TypeReference<GetNamespaceResponse> localVarReturnType =
        new TypeReference<GetNamespaceResponse>() {};
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
   * Drop a namespace Drop a namespace. The namespace must be empty.
   *
   * @param dropNamespaceRequest (required)
   * @return Object
   * @throws ApiException if fails to make API call
   */
  public Object dropNamespace(DropNamespaceRequest dropNamespaceRequest) throws ApiException {
    return this.dropNamespace(dropNamespaceRequest, Collections.emptyMap());
  }

  /**
   * Drop a namespace Drop a namespace. The namespace must be empty.
   *
   * @param dropNamespaceRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return Object
   * @throws ApiException if fails to make API call
   */
  public Object dropNamespace(
      DropNamespaceRequest dropNamespaceRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = dropNamespaceRequest;

    // verify the required parameter 'dropNamespaceRequest' is set
    if (dropNamespaceRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'dropNamespaceRequest' when calling dropNamespace");
    }

    // create path and map variables
    String localVarPath = "/DropNamespace";

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

    TypeReference<Object> localVarReturnType = new TypeReference<Object>() {};
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
   * Get information about a namespace Return the detailed information for a given namespace
   *
   * @param getNamespaceRequest (required)
   * @return GetNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public GetNamespaceResponse getNamespace(GetNamespaceRequest getNamespaceRequest)
      throws ApiException {
    return this.getNamespace(getNamespaceRequest, Collections.emptyMap());
  }

  /**
   * Get information about a namespace Return the detailed information for a given namespace
   *
   * @param getNamespaceRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetNamespaceResponse
   * @throws ApiException if fails to make API call
   */
  public GetNamespaceResponse getNamespace(
      GetNamespaceRequest getNamespaceRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = getNamespaceRequest;

    // verify the required parameter 'getNamespaceRequest' is set
    if (getNamespaceRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'getNamespaceRequest' when calling getNamespace");
    }

    // create path and map variables
    String localVarPath = "/GetNamespace";

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

    TypeReference<GetNamespaceResponse> localVarReturnType =
        new TypeReference<GetNamespaceResponse>() {};
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
   * List namespaces List all child namespace names of the root namespace or a given parent
   * namespace.
   *
   * @param listNamespacesRequest (required)
   * @return ListNamespacesResponse
   * @throws ApiException if fails to make API call
   */
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest listNamespacesRequest)
      throws ApiException {
    return this.listNamespaces(listNamespacesRequest, Collections.emptyMap());
  }

  /**
   * List namespaces List all child namespace names of the root namespace or a given parent
   * namespace.
   *
   * @param listNamespacesRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListNamespacesResponse
   * @throws ApiException if fails to make API call
   */
  public ListNamespacesResponse listNamespaces(
      ListNamespacesRequest listNamespacesRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = listNamespacesRequest;

    // verify the required parameter 'listNamespacesRequest' is set
    if (listNamespacesRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'listNamespacesRequest' when calling listNamespaces");
    }

    // create path and map variables
    String localVarPath = "/ListNamespaces";

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

    TypeReference<ListNamespacesResponse> localVarReturnType =
        new TypeReference<ListNamespacesResponse>() {};
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
   * Check if a namespace exists Check if a namespace exists.
   *
   * @param namespaceExistsRequest (required)
   * @return Object
   * @throws ApiException if fails to make API call
   */
  public Object namespaceExists(NamespaceExistsRequest namespaceExistsRequest) throws ApiException {
    return this.namespaceExists(namespaceExistsRequest, Collections.emptyMap());
  }

  /**
   * Check if a namespace exists Check if a namespace exists.
   *
   * @param namespaceExistsRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return Object
   * @throws ApiException if fails to make API call
   */
  public Object namespaceExists(
      NamespaceExistsRequest namespaceExistsRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = namespaceExistsRequest;

    // verify the required parameter 'namespaceExistsRequest' is set
    if (namespaceExistsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'namespaceExistsRequest' when calling namespaceExists");
    }

    // create path and map variables
    String localVarPath = "/NamespaceExists";

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

    TypeReference<Object> localVarReturnType = new TypeReference<Object>() {};
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
