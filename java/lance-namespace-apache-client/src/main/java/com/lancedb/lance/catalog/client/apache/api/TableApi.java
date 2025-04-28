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
import com.lancedb.lance.catalog.client.apache.model.GetTableResponse;
import com.lancedb.lance.catalog.client.apache.model.RegisterTableRequest;

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
public class TableApi extends BaseApi {

  public TableApi() {
    super(Configuration.getDefaultApiClient());
  }

  public TableApi(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Get a table from the namespace Get a table&#39;s detailed information under a specified
   * namespace.
   *
   * @param table A string identifier of the table (required)
   * @param delimiter The delimiter for the identifier used in the context (optional)
   * @return GetTableResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableResponse getTable(String table, String delimiter) throws ApiException {
    return this.getTable(table, delimiter, Collections.emptyMap());
  }

  /**
   * Get a table from the namespace Get a table&#39;s detailed information under a specified
   * namespace.
   *
   * @param table A string identifier of the table (required)
   * @param delimiter The delimiter for the identifier used in the context (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetTableResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableResponse getTable(
      String table, String delimiter, Map<String, String> additionalHeaders) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'table' is set
    if (table == null) {
      throw new ApiException(400, "Missing the required parameter 'table' when calling getTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/tables/{table}"
            .replaceAll(
                "\\{" + "table" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(table)));

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

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<GetTableResponse> localVarReturnType = new TypeReference<GetTableResponse>() {};
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
   * Register a table to a namespace Register an existing table at a given storage location to a
   * namespace.
   *
   * @param registerTableRequest (required)
   * @return GetTableResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableResponse registerTable(RegisterTableRequest registerTableRequest)
      throws ApiException {
    return this.registerTable(registerTableRequest, Collections.emptyMap());
  }

  /**
   * Register a table to a namespace Register an existing table at a given storage location to a
   * namespace.
   *
   * @param registerTableRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetTableResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableResponse registerTable(
      RegisterTableRequest registerTableRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = registerTableRequest;

    // verify the required parameter 'registerTableRequest' is set
    if (registerTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'registerTableRequest' when calling registerTable");
    }

    // create path and map variables
    String localVarPath = "/v1/table/register";

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

    TypeReference<GetTableResponse> localVarReturnType = new TypeReference<GetTableResponse>() {};
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
   * Check if a table exists Check if a table exists. This API should behave exactly like the
   * GetTable API, except it does not contain a body.
   *
   * @param table A string identifier of the table (required)
   * @param delimiter The delimiter for the identifier used in the context (optional)
   * @throws ApiException if fails to make API call
   */
  public void tableExists(String table, String delimiter) throws ApiException {
    this.tableExists(table, delimiter, Collections.emptyMap());
  }

  /**
   * Check if a table exists Check if a table exists. This API should behave exactly like the
   * GetTable API, except it does not contain a body.
   *
   * @param table A string identifier of the table (required)
   * @param delimiter The delimiter for the identifier used in the context (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void tableExists(String table, String delimiter, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'table' is set
    if (table == null) {
      throw new ApiException(
          400, "Missing the required parameter 'table' when calling tableExists");
    }

    // create path and map variables
    String localVarPath =
        "/v1/tables/{table}"
            .replaceAll(
                "\\{" + "table" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(table)));

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
