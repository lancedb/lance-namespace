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
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableResponse;

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
public class DataApi extends BaseApi {

  public DataApi() {
    super(Configuration.getDefaultApiClient());
  }

  public DataApi(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Delete rows from a table Delete rows from a table based on a SQL predicate. Returns the number
   * of rows that were deleted.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteFromTableRequest Delete request (required)
   * @return DeleteFromTableResponse
   * @throws ApiException if fails to make API call
   */
  public DeleteFromTableResponse deleteFromTable(
      String id, DeleteFromTableRequest deleteFromTableRequest) throws ApiException {
    return this.deleteFromTable(id, deleteFromTableRequest, Collections.emptyMap());
  }

  /**
   * Delete rows from a table Delete rows from a table based on a SQL predicate. Returns the number
   * of rows that were deleted.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteFromTableRequest Delete request (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return DeleteFromTableResponse
   * @throws ApiException if fails to make API call
   */
  public DeleteFromTableResponse deleteFromTable(
      String id,
      DeleteFromTableRequest deleteFromTableRequest,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = deleteFromTableRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/delete"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

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

    TypeReference<DeleteFromTableResponse> localVarReturnType =
        new TypeReference<DeleteFromTableResponse>() {};
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
   * Insert records into a table Insert new records into an existing table using Arrow IPC format.
   * Supports both lance-namespace format (with namespace in body) and LanceDB format (with database
   * in headers).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @param mode Insert mode: \&quot;append\&quot; (default) or \&quot;overwrite\&quot; (optional,
   *     default to append)
   * @return InsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public InsertIntoTableResponse insertIntoTable(String id, byte[] body, String mode)
      throws ApiException {
    return this.insertIntoTable(id, body, mode, Collections.emptyMap());
  }

  /**
   * Insert records into a table Insert new records into an existing table using Arrow IPC format.
   * Supports both lance-namespace format (with namespace in body) and LanceDB format (with database
   * in headers).
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC data (required)
   * @param mode Insert mode: \&quot;append\&quot; (default) or \&quot;overwrite\&quot; (optional,
   *     default to append)
   * @param additionalHeaders additionalHeaders for this call
   * @return InsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public InsertIntoTableResponse insertIntoTable(
      String id, byte[] body, String mode, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = body;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/insert"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("mode", mode));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/x-arrow-ipc"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<InsertIntoTableResponse> localVarReturnType =
        new TypeReference<InsertIntoTableResponse>() {};
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
   * Merge insert (upsert) records into a table Performs a merge insert (upsert) operation on a
   * table. This operation updates existing rows based on a matching column and inserts new rows
   * that don&#39;t match. Returns the number of rows inserted and updated.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param on Column name to use for matching rows (required) (required)
   * @param body Arrow IPC data containing the records to merge (required)
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @return MergeInsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      String id,
      String on,
      byte[] body,
      Boolean whenMatchedUpdateAll,
      Boolean whenNotMatchedInsertAll)
      throws ApiException {
    return this.mergeInsertIntoTable(
        id, on, body, whenMatchedUpdateAll, whenNotMatchedInsertAll, Collections.emptyMap());
  }

  /**
   * Merge insert (upsert) records into a table Performs a merge insert (upsert) operation on a
   * table. This operation updates existing rows based on a matching column and inserts new rows
   * that don&#39;t match. Returns the number of rows inserted and updated.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param on Column name to use for matching rows (required) (required)
   * @param body Arrow IPC data containing the records to merge (required)
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @param additionalHeaders additionalHeaders for this call
   * @return MergeInsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      String id,
      String on,
      byte[] body,
      Boolean whenMatchedUpdateAll,
      Boolean whenNotMatchedInsertAll,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = body;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/merge_insert"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("on", on));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("when_matched_update_all", whenMatchedUpdateAll));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("when_not_matched_insert_all", whenNotMatchedInsertAll));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/x-arrow-ipc"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<MergeInsertIntoTableResponse> localVarReturnType =
        new TypeReference<MergeInsertIntoTableResponse>() {};
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
   * Query a table Query a table with vector search and optional filtering. Returns results in Arrow
   * IPC stream format.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @return byte[]
   * @throws ApiException if fails to make API call
   */
  public byte[] queryTable(String id, QueryTableRequest queryTableRequest) throws ApiException {
    return this.queryTable(id, queryTableRequest, Collections.emptyMap());
  }

  /**
   * Query a table Query a table with vector search and optional filtering. Returns results in Arrow
   * IPC stream format.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return byte[]
   * @throws ApiException if fails to make API call
   */
  public byte[] queryTable(
      String id, QueryTableRequest queryTableRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = queryTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling queryTable");
    }

    // verify the required parameter 'queryTableRequest' is set
    if (queryTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'queryTableRequest' when calling queryTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/query"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/vnd.apache.arrow.stream", "application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<byte[]> localVarReturnType = new TypeReference<byte[]>() {};
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
   * Update rows in a table Update existing rows in a table using SQL expressions. Each update
   * consists of a column name and an SQL expression that will be evaluated against the current
   * row&#39;s value. Optionally, a predicate can be provided to filter which rows to update.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableRequest Update request (required)
   * @return UpdateTableResponse
   * @throws ApiException if fails to make API call
   */
  public UpdateTableResponse updateTable(String id, UpdateTableRequest updateTableRequest)
      throws ApiException {
    return this.updateTable(id, updateTableRequest, Collections.emptyMap());
  }

  /**
   * Update rows in a table Update existing rows in a table using SQL expressions. Each update
   * consists of a column name and an SQL expression that will be evaluated against the current
   * row&#39;s value. Optionally, a predicate can be provided to filter which rows to update.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableRequest Update request (required)
   * @param additionalHeaders additionalHeaders for this call
   * @return UpdateTableResponse
   * @throws ApiException if fails to make API call
   */
  public UpdateTableResponse updateTable(
      String id, UpdateTableRequest updateTableRequest, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = updateTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateTable");
    }

    // verify the required parameter 'updateTableRequest' is set
    if (updateTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'updateTableRequest' when calling updateTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/update"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)));

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

    TypeReference<UpdateTableResponse> localVarReturnType =
        new TypeReference<UpdateTableResponse>() {};
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
