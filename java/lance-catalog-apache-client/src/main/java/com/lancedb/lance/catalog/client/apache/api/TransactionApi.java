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
import com.lancedb.lance.catalog.client.apache.model.AlterTransactionRequest;
import com.lancedb.lance.catalog.client.apache.model.GetTransactionResponse;

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
public class TransactionApi extends BaseApi {

  public TransactionApi() {
    super(Configuration.getDefaultApiClient());
  }

  public TransactionApi(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Alter information of a transaction.
   *
   * @param txn The ID of the transaction. (required)
   * @param alterTransactionRequest (required)
   * @throws ApiException if fails to make API call
   */
  public void alterTransaction(String txn, AlterTransactionRequest alterTransactionRequest)
      throws ApiException {
    this.alterTransaction(txn, alterTransactionRequest, Collections.emptyMap());
  }

  /**
   * Alter information of a transaction.
   *
   * @param txn The ID of the transaction. (required)
   * @param alterTransactionRequest (required)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void alterTransaction(
      String txn,
      AlterTransactionRequest alterTransactionRequest,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = alterTransactionRequest;

    // verify the required parameter 'txn' is set
    if (txn == null) {
      throw new ApiException(
          400, "Missing the required parameter 'txn' when calling alterTransaction");
    }

    // verify the required parameter 'alterTransactionRequest' is set
    if (alterTransactionRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'alterTransactionRequest' when calling alterTransaction");
    }

    // create path and map variables
    String localVarPath =
        "/v1/transactions/{txn}"
            .replaceAll(
                "\\{" + "txn" + "\\}", apiClient.escapeString(apiClient.parameterToString(txn)));

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

  /**
   * Get information about a transaction Return a detailed information for a given transaction
   *
   * @param txn The ID of the transaction. (required)
   * @return GetTransactionResponse
   * @throws ApiException if fails to make API call
   */
  public GetTransactionResponse getTransaction(String txn) throws ApiException {
    return this.getTransaction(txn, Collections.emptyMap());
  }

  /**
   * Get information about a transaction Return a detailed information for a given transaction
   *
   * @param txn The ID of the transaction. (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetTransactionResponse
   * @throws ApiException if fails to make API call
   */
  public GetTransactionResponse getTransaction(String txn, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'txn' is set
    if (txn == null) {
      throw new ApiException(
          400, "Missing the required parameter 'txn' when calling getTransaction");
    }

    // create path and map variables
    String localVarPath =
        "/v1/transactions/{txn}"
            .replaceAll(
                "\\{" + "txn" + "\\}", apiClient.escapeString(apiClient.parameterToString(txn)));

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

    TypeReference<GetTransactionResponse> localVarReturnType =
        new TypeReference<GetTransactionResponse>() {};
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
