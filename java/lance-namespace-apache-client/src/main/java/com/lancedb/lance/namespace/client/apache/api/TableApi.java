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
import com.lancedb.lance.namespace.model.AlterTableAddColumnsRequest;
import com.lancedb.lance.namespace.model.AlterTableAddColumnsResponse;
import com.lancedb.lance.namespace.model.AlterTableAlterColumnsRequest;
import com.lancedb.lance.namespace.model.AlterTableAlterColumnsResponse;
import com.lancedb.lance.namespace.model.AlterTableDropColumnsRequest;
import com.lancedb.lance.namespace.model.AlterTableDropColumnsResponse;
import com.lancedb.lance.namespace.model.AnalyzeTableQueryPlanRequest;
import com.lancedb.lance.namespace.model.AnalyzeTableQueryPlanResponse;
import com.lancedb.lance.namespace.model.CountTableRowsRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.CreateTableTagRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.model.DeleteTableTagRequest;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropTableIndexRequest;
import com.lancedb.lance.namespace.model.DropTableIndexResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.ExplainTableQueryPlanRequest;
import com.lancedb.lance.namespace.model.ExplainTableQueryPlanResponse;
import com.lancedb.lance.namespace.model.GetTableStatsRequest;
import com.lancedb.lance.namespace.model.GetTableStatsResponse;
import com.lancedb.lance.namespace.model.GetTableTagVersionRequest;
import com.lancedb.lance.namespace.model.GetTableTagVersionResponse;
import com.lancedb.lance.namespace.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesResponse;
import com.lancedb.lance.namespace.model.ListTableTagsResponse;
import com.lancedb.lance.namespace.model.ListTableVersionsRequest;
import com.lancedb.lance.namespace.model.ListTableVersionsResponse;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.RestoreTableRequest;
import com.lancedb.lance.namespace.model.RestoreTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.UpdateTableRequest;
import com.lancedb.lance.namespace.model.UpdateTableResponse;
import com.lancedb.lance.namespace.model.UpdateTableTagRequest;

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
   * @return AlterTableAddColumnsResponse
   * @throws ApiException if fails to make API call
   */
  public AlterTableAddColumnsResponse alterTableAddColumns(
      String id, AlterTableAddColumnsRequest alterTableAddColumnsRequest, String delimiter)
      throws ApiException {
    return this.alterTableAddColumns(
        id, alterTableAddColumnsRequest, delimiter, Collections.emptyMap());
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
   * @param additionalHeaders additionalHeaders for this call
   * @return AlterTableAddColumnsResponse
   * @throws ApiException if fails to make API call
   */
  public AlterTableAddColumnsResponse alterTableAddColumns(
      String id,
      AlterTableAddColumnsRequest alterTableAddColumnsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = alterTableAddColumnsRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/add_columns"
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

    TypeReference<AlterTableAddColumnsResponse> localVarReturnType =
        new TypeReference<AlterTableAddColumnsResponse>() {};
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
   * Modify existing columns Modify existing columns in table &#x60;id&#x60;, such as renaming or
   * changing data types.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableAlterColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return AlterTableAlterColumnsResponse
   * @throws ApiException if fails to make API call
   */
  public AlterTableAlterColumnsResponse alterTableAlterColumns(
      String id, AlterTableAlterColumnsRequest alterTableAlterColumnsRequest, String delimiter)
      throws ApiException {
    return this.alterTableAlterColumns(
        id, alterTableAlterColumnsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Modify existing columns Modify existing columns in table &#x60;id&#x60;, such as renaming or
   * changing data types.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableAlterColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return AlterTableAlterColumnsResponse
   * @throws ApiException if fails to make API call
   */
  public AlterTableAlterColumnsResponse alterTableAlterColumns(
      String id,
      AlterTableAlterColumnsRequest alterTableAlterColumnsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = alterTableAlterColumnsRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling alterTableAlterColumns");
    }

    // verify the required parameter 'alterTableAlterColumnsRequest' is set
    if (alterTableAlterColumnsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'alterTableAlterColumnsRequest' when calling alterTableAlterColumns");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/alter_columns"
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

    TypeReference<AlterTableAlterColumnsResponse> localVarReturnType =
        new TypeReference<AlterTableAlterColumnsResponse>() {};
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
   * Remove columns from table Remove specified columns from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableDropColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return AlterTableDropColumnsResponse
   * @throws ApiException if fails to make API call
   */
  public AlterTableDropColumnsResponse alterTableDropColumns(
      String id, AlterTableDropColumnsRequest alterTableDropColumnsRequest, String delimiter)
      throws ApiException {
    return this.alterTableDropColumns(
        id, alterTableDropColumnsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Remove columns from table Remove specified columns from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param alterTableDropColumnsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return AlterTableDropColumnsResponse
   * @throws ApiException if fails to make API call
   */
  public AlterTableDropColumnsResponse alterTableDropColumns(
      String id,
      AlterTableDropColumnsRequest alterTableDropColumnsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = alterTableDropColumnsRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling alterTableDropColumns");
    }

    // verify the required parameter 'alterTableDropColumnsRequest' is set
    if (alterTableDropColumnsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'alterTableDropColumnsRequest' when calling alterTableDropColumns");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/drop_columns"
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

    TypeReference<AlterTableDropColumnsResponse> localVarReturnType =
        new TypeReference<AlterTableDropColumnsResponse>() {};
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
   * Analyze query execution plan Analyze the query execution plan for a query against table
   * &#x60;id&#x60;. Returns detailed statistics and analysis of the query execution plan.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param analyzeTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return AnalyzeTableQueryPlanResponse
   * @throws ApiException if fails to make API call
   */
  public AnalyzeTableQueryPlanResponse analyzeTableQueryPlan(
      String id, AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest, String delimiter)
      throws ApiException {
    return this.analyzeTableQueryPlan(
        id, analyzeTableQueryPlanRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Analyze query execution plan Analyze the query execution plan for a query against table
   * &#x60;id&#x60;. Returns detailed statistics and analysis of the query execution plan.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param analyzeTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return AnalyzeTableQueryPlanResponse
   * @throws ApiException if fails to make API call
   */
  public AnalyzeTableQueryPlanResponse analyzeTableQueryPlan(
      String id,
      AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = analyzeTableQueryPlanRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/analyze_plan"
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

    TypeReference<AnalyzeTableQueryPlanResponse> localVarReturnType =
        new TypeReference<AnalyzeTableQueryPlanResponse>() {};
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
   * Count rows in a table Count the number of rows in table &#x60;id&#x60;
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param countTableRowsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return Long
   * @throws ApiException if fails to make API call
   */
  public Long countTableRows(
      String id, CountTableRowsRequest countTableRowsRequest, String delimiter)
      throws ApiException {
    return this.countTableRows(id, countTableRowsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Count rows in a table Count the number of rows in table &#x60;id&#x60;
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param countTableRowsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return Long
   * @throws ApiException if fails to make API call
   */
  public Long countTableRows(
      String id,
      CountTableRowsRequest countTableRowsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = countTableRowsRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/count_rows"
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

    TypeReference<Long> localVarReturnType = new TypeReference<Long>() {};
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
   * Create a table with the given name Create table &#x60;id&#x60; in the namespace with the given
   * data in Arrow IPC stream. The schema of the Arrow IPC stream is used as the table schema. If
   * the stream is empty, the API creates a new empty table. REST NAMESPACE ONLY REST namespace uses
   * Arrow IPC stream as the request body. It passes in the &#x60;CreateTableRequest&#x60;
   * information in the following way: - &#x60;id&#x60;: pass through path parameter of the same
   * name - &#x60;mode&#x60;: pass through query parameter of the same name - &#x60;location&#x60;:
   * pass through header &#x60;x-lance-table-location&#x60; - &#x60;properties&#x60;: pass through
   * header &#x60;x-lance-table-properties&#x60;
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
   * @param xLanceTableLocation URI pointing to root location to create the table at (optional)
   * @param xLanceTableProperties JSON-encoded string map (e.g. { \&quot;owner\&quot;:
   *     \&quot;jack\&quot; }) (optional)
   * @return CreateTableResponse
   * @throws ApiException if fails to make API call
   */
  public CreateTableResponse createTable(
      String id,
      byte[] body,
      String delimiter,
      String mode,
      String xLanceTableLocation,
      String xLanceTableProperties)
      throws ApiException {
    return this.createTable(
        id,
        body,
        delimiter,
        mode,
        xLanceTableLocation,
        xLanceTableProperties,
        Collections.emptyMap());
  }

  /**
   * Create a table with the given name Create table &#x60;id&#x60; in the namespace with the given
   * data in Arrow IPC stream. The schema of the Arrow IPC stream is used as the table schema. If
   * the stream is empty, the API creates a new empty table. REST NAMESPACE ONLY REST namespace uses
   * Arrow IPC stream as the request body. It passes in the &#x60;CreateTableRequest&#x60;
   * information in the following way: - &#x60;id&#x60;: pass through path parameter of the same
   * name - &#x60;mode&#x60;: pass through query parameter of the same name - &#x60;location&#x60;:
   * pass through header &#x60;x-lance-table-location&#x60; - &#x60;properties&#x60;: pass through
   * header &#x60;x-lance-table-properties&#x60;
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
   * @param xLanceTableLocation URI pointing to root location to create the table at (optional)
   * @param xLanceTableProperties JSON-encoded string map (e.g. { \&quot;owner\&quot;:
   *     \&quot;jack\&quot; }) (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateTableResponse
   * @throws ApiException if fails to make API call
   */
  public CreateTableResponse createTable(
      String id,
      byte[] body,
      String delimiter,
      String mode,
      String xLanceTableLocation,
      String xLanceTableProperties,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling createTable");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/create"
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
    localVarQueryParams.addAll(apiClient.parameterToPair("mode", mode));
    if (xLanceTableLocation != null)
      localVarHeaderParams.put(
          "x-lance-table-location", apiClient.parameterToString(xLanceTableLocation));
    if (xLanceTableProperties != null)
      localVarHeaderParams.put(
          "x-lance-table-properties", apiClient.parameterToString(xLanceTableProperties));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/vnd.apache.arrow.stream"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<CreateTableResponse> localVarReturnType =
        new TypeReference<CreateTableResponse>() {};
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
   * Create an index on a table Create an index on a table column for faster search operations.
   * Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE,
   * BITMAP, FTS, etc.). Index creation is handled asynchronously. Use the
   * &#x60;ListTableIndices&#x60; and &#x60;DescribeTableIndexStats&#x60; operations to monitor
   * index creation progress.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableIndexRequest Index creation request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CreateTableIndexResponse
   * @throws ApiException if fails to make API call
   */
  public CreateTableIndexResponse createTableIndex(
      String id, CreateTableIndexRequest createTableIndexRequest, String delimiter)
      throws ApiException {
    return this.createTableIndex(id, createTableIndexRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Create an index on a table Create an index on a table column for faster search operations.
   * Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE,
   * BITMAP, FTS, etc.). Index creation is handled asynchronously. Use the
   * &#x60;ListTableIndices&#x60; and &#x60;DescribeTableIndexStats&#x60; operations to monitor
   * index creation progress.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableIndexRequest Index creation request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateTableIndexResponse
   * @throws ApiException if fails to make API call
   */
  public CreateTableIndexResponse createTableIndex(
      String id,
      CreateTableIndexRequest createTableIndexRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createTableIndexRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling createTableIndex");
    }

    // verify the required parameter 'createTableIndexRequest' is set
    if (createTableIndexRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'createTableIndexRequest' when calling createTableIndex");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/create_index"
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

    TypeReference<CreateTableIndexResponse> localVarReturnType =
        new TypeReference<CreateTableIndexResponse>() {};
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
   * Create a new tag Create a new tag for table &#x60;id&#x60; that points to a specific version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @throws ApiException if fails to make API call
   */
  public void createTableTag(
      String id, CreateTableTagRequest createTableTagRequest, String delimiter)
      throws ApiException {
    this.createTableTag(id, createTableTagRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Create a new tag Create a new tag for table &#x60;id&#x60; that points to a specific version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void createTableTag(
      String id,
      CreateTableTagRequest createTableTagRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createTableTagRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling createTableTag");
    }

    // verify the required parameter 'createTableTagRequest' is set
    if (createTableTagRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'createTableTagRequest' when calling createTableTag");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/tags/create"
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
   * @return DeleteFromTableResponse
   * @throws ApiException if fails to make API call
   */
  public DeleteFromTableResponse deleteFromTable(
      String id, DeleteFromTableRequest deleteFromTableRequest, String delimiter)
      throws ApiException {
    return this.deleteFromTable(id, deleteFromTableRequest, delimiter, Collections.emptyMap());
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
   * @param additionalHeaders additionalHeaders for this call
   * @return DeleteFromTableResponse
   * @throws ApiException if fails to make API call
   */
  public DeleteFromTableResponse deleteFromTable(
      String id,
      DeleteFromTableRequest deleteFromTableRequest,
      String delimiter,
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

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));

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
   * Delete a tag Delete an existing tag from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @throws ApiException if fails to make API call
   */
  public void deleteTableTag(
      String id, DeleteTableTagRequest deleteTableTagRequest, String delimiter)
      throws ApiException {
    this.deleteTableTag(id, deleteTableTagRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Delete a tag Delete an existing tag from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void deleteTableTag(
      String id,
      DeleteTableTagRequest deleteTableTagRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = deleteTableTagRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling deleteTableTag");
    }

    // verify the required parameter 'deleteTableTagRequest' is set
    if (deleteTableTagRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'deleteTableTagRequest' when calling deleteTableTag");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/tags/delete"
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

  /**
   * Deregister a table Deregister table &#x60;id&#x60; from its namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deregisterTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return DeregisterTableResponse
   * @throws ApiException if fails to make API call
   */
  public DeregisterTableResponse deregisterTable(
      String id, DeregisterTableRequest deregisterTableRequest, String delimiter)
      throws ApiException {
    return this.deregisterTable(id, deregisterTableRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Deregister a table Deregister table &#x60;id&#x60; from its namespace.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deregisterTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DeregisterTableResponse
   * @throws ApiException if fails to make API call
   */
  public DeregisterTableResponse deregisterTable(
      String id,
      DeregisterTableRequest deregisterTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = deregisterTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling deregisterTable");
    }

    // verify the required parameter 'deregisterTableRequest' is set
    if (deregisterTableRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'deregisterTableRequest' when calling deregisterTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/deregister"
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

    TypeReference<DeregisterTableResponse> localVarReturnType =
        new TypeReference<DeregisterTableResponse>() {};
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
   * Describe information of a table Describe the detailed information for table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return DescribeTableResponse
   * @throws ApiException if fails to make API call
   */
  public DescribeTableResponse describeTable(
      String id, DescribeTableRequest describeTableRequest, String delimiter) throws ApiException {
    return this.describeTable(id, describeTableRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Describe information of a table Describe the detailed information for table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param describeTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DescribeTableResponse
   * @throws ApiException if fails to make API call
   */
  public DescribeTableResponse describeTable(
      String id,
      DescribeTableRequest describeTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = describeTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling describeTable");
    }

    // verify the required parameter 'describeTableRequest' is set
    if (describeTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'describeTableRequest' when calling describeTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/describe"
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

    TypeReference<DescribeTableResponse> localVarReturnType =
        new TypeReference<DescribeTableResponse>() {};
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
   * Get table index statistics Get statistics for a specific index on a table. Returns information
   * about the index type, distance type (for vector indices), and row counts.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to get stats for (required)
   * @param describeTableIndexStatsRequest Index stats request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return DescribeTableIndexStatsResponse
   * @throws ApiException if fails to make API call
   */
  public DescribeTableIndexStatsResponse describeTableIndexStats(
      String id,
      String indexName,
      DescribeTableIndexStatsRequest describeTableIndexStatsRequest,
      String delimiter)
      throws ApiException {
    return this.describeTableIndexStats(
        id, indexName, describeTableIndexStatsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Get table index statistics Get statistics for a specific index on a table. Returns information
   * about the index type, distance type (for vector indices), and row counts.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to get stats for (required)
   * @param describeTableIndexStatsRequest Index stats request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DescribeTableIndexStatsResponse
   * @throws ApiException if fails to make API call
   */
  public DescribeTableIndexStatsResponse describeTableIndexStats(
      String id,
      String indexName,
      DescribeTableIndexStatsRequest describeTableIndexStatsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = describeTableIndexStatsRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling describeTableIndexStats");
    }

    // verify the required parameter 'indexName' is set
    if (indexName == null) {
      throw new ApiException(
          400, "Missing the required parameter 'indexName' when calling describeTableIndexStats");
    }

    // verify the required parameter 'describeTableIndexStatsRequest' is set
    if (describeTableIndexStatsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'describeTableIndexStatsRequest' when calling describeTableIndexStats");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/index/{index_name}/stats"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)))
            .replaceAll(
                "\\{" + "index_name" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(indexName)));

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

    TypeReference<DescribeTableIndexStatsResponse> localVarReturnType =
        new TypeReference<DescribeTableIndexStatsResponse>() {};
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
   * Drop a table Drop table &#x60;id&#x60; and delete its data.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return DropTableResponse
   * @throws ApiException if fails to make API call
   */
  public DropTableResponse dropTable(String id, DropTableRequest dropTableRequest, String delimiter)
      throws ApiException {
    return this.dropTable(id, dropTableRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Drop a table Drop table &#x60;id&#x60; and delete its data.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param dropTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DropTableResponse
   * @throws ApiException if fails to make API call
   */
  public DropTableResponse dropTable(
      String id,
      DropTableRequest dropTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = dropTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling dropTable");
    }

    // verify the required parameter 'dropTableRequest' is set
    if (dropTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'dropTableRequest' when calling dropTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/drop"
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

    TypeReference<DropTableResponse> localVarReturnType = new TypeReference<DropTableResponse>() {};
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
   * Drop a specific index Drop the specified index from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to drop (required)
   * @param dropTableIndexRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return DropTableIndexResponse
   * @throws ApiException if fails to make API call
   */
  public DropTableIndexResponse dropTableIndex(
      String id, String indexName, DropTableIndexRequest dropTableIndexRequest, String delimiter)
      throws ApiException {
    return this.dropTableIndex(
        id, indexName, dropTableIndexRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Drop a specific index Drop the specified index from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param indexName Name of the index to drop (required)
   * @param dropTableIndexRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DropTableIndexResponse
   * @throws ApiException if fails to make API call
   */
  public DropTableIndexResponse dropTableIndex(
      String id,
      String indexName,
      DropTableIndexRequest dropTableIndexRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = dropTableIndexRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling dropTableIndex");
    }

    // verify the required parameter 'indexName' is set
    if (indexName == null) {
      throw new ApiException(
          400, "Missing the required parameter 'indexName' when calling dropTableIndex");
    }

    // verify the required parameter 'dropTableIndexRequest' is set
    if (dropTableIndexRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'dropTableIndexRequest' when calling dropTableIndex");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/index/{index_name}/drop"
            .replaceAll(
                "\\{" + "id" + "\\}", apiClient.escapeString(apiClient.parameterToString(id)))
            .replaceAll(
                "\\{" + "index_name" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(indexName)));

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

    TypeReference<DropTableIndexResponse> localVarReturnType =
        new TypeReference<DropTableIndexResponse>() {};
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
   * Get query execution plan explanation Get the query execution plan for a query against table
   * &#x60;id&#x60;. Returns a human-readable explanation of how the query will be executed.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param explainTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return ExplainTableQueryPlanResponse
   * @throws ApiException if fails to make API call
   */
  public ExplainTableQueryPlanResponse explainTableQueryPlan(
      String id, ExplainTableQueryPlanRequest explainTableQueryPlanRequest, String delimiter)
      throws ApiException {
    return this.explainTableQueryPlan(
        id, explainTableQueryPlanRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Get query execution plan explanation Get the query execution plan for a query against table
   * &#x60;id&#x60;. Returns a human-readable explanation of how the query will be executed.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param explainTableQueryPlanRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ExplainTableQueryPlanResponse
   * @throws ApiException if fails to make API call
   */
  public ExplainTableQueryPlanResponse explainTableQueryPlan(
      String id,
      ExplainTableQueryPlanRequest explainTableQueryPlanRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = explainTableQueryPlanRequest;

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

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/explain_plan"
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

    TypeReference<ExplainTableQueryPlanResponse> localVarReturnType =
        new TypeReference<ExplainTableQueryPlanResponse>() {};
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
   * Get table statistics Get statistics for table &#x60;id&#x60;, including row counts, data sizes,
   * and column statistics.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param getTableStatsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return GetTableStatsResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableStatsResponse getTableStats(
      String id, GetTableStatsRequest getTableStatsRequest, String delimiter) throws ApiException {
    return this.getTableStats(id, getTableStatsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Get table statistics Get statistics for table &#x60;id&#x60;, including row counts, data sizes,
   * and column statistics.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param getTableStatsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetTableStatsResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableStatsResponse getTableStats(
      String id,
      GetTableStatsRequest getTableStatsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = getTableStatsRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getTableStats");
    }

    // verify the required parameter 'getTableStatsRequest' is set
    if (getTableStatsRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'getTableStatsRequest' when calling getTableStats");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/stats"
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

    TypeReference<GetTableStatsResponse> localVarReturnType =
        new TypeReference<GetTableStatsResponse>() {};
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
   * Get version for a specific tag Get the version number that a specific tag points to for table
   * &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param getTableTagVersionRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return GetTableTagVersionResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableTagVersionResponse getTableTagVersion(
      String id, GetTableTagVersionRequest getTableTagVersionRequest, String delimiter)
      throws ApiException {
    return this.getTableTagVersion(
        id, getTableTagVersionRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Get version for a specific tag Get the version number that a specific tag points to for table
   * &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param getTableTagVersionRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetTableTagVersionResponse
   * @throws ApiException if fails to make API call
   */
  public GetTableTagVersionResponse getTableTagVersion(
      String id,
      GetTableTagVersionRequest getTableTagVersionRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = getTableTagVersionRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling getTableTagVersion");
    }

    // verify the required parameter 'getTableTagVersionRequest' is set
    if (getTableTagVersionRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'getTableTagVersionRequest' when calling getTableTagVersion");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/tags/version"
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

    TypeReference<GetTableTagVersionResponse> localVarReturnType =
        new TypeReference<GetTableTagVersionResponse>() {};
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
   * Insert records into a table Insert new records into table &#x60;id&#x60;. REST NAMESPACE ONLY
   * REST namespace uses Arrow IPC stream as the request body. It passes in the
   * &#x60;InsertIntoTableRequest&#x60; information in the following way: - &#x60;id&#x60;: pass
   * through path parameter of the same name - &#x60;mode&#x60;: pass through query parameter of the
   * same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC stream containing the records to insert (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param mode How the insert should behave: - append (default): insert data to the existing table
   *     - overwrite: remove all data in the table and then insert data to it (optional, default to
   *     append)
   * @return InsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public InsertIntoTableResponse insertIntoTable(
      String id, byte[] body, String delimiter, String mode) throws ApiException {
    return this.insertIntoTable(id, body, delimiter, mode, Collections.emptyMap());
  }

  /**
   * Insert records into a table Insert new records into table &#x60;id&#x60;. REST NAMESPACE ONLY
   * REST namespace uses Arrow IPC stream as the request body. It passes in the
   * &#x60;InsertIntoTableRequest&#x60; information in the following way: - &#x60;id&#x60;: pass
   * through path parameter of the same name - &#x60;mode&#x60;: pass through query parameter of the
   * same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param body Arrow IPC stream containing the records to insert (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param mode How the insert should behave: - append (default): insert data to the existing table
   *     - overwrite: remove all data in the table and then insert data to it (optional, default to
   *     append)
   * @param additionalHeaders additionalHeaders for this call
   * @return InsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public InsertIntoTableResponse insertIntoTable(
      String id, byte[] body, String delimiter, String mode, Map<String, String> additionalHeaders)
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

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));
    localVarQueryParams.addAll(apiClient.parameterToPair("mode", mode));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/vnd.apache.arrow.stream"};
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
   * List indexes on a table List all indices created on a table. Returns information about each
   * index including name, columns, status, and UUID.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param listTableIndicesRequest Index list request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return ListTableIndicesResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableIndicesResponse listTableIndices(
      String id, ListTableIndicesRequest listTableIndicesRequest, String delimiter)
      throws ApiException {
    return this.listTableIndices(id, listTableIndicesRequest, delimiter, Collections.emptyMap());
  }

  /**
   * List indexes on a table List all indices created on a table. Returns information about each
   * index including name, columns, status, and UUID.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param listTableIndicesRequest Index list request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListTableIndicesResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableIndicesResponse listTableIndices(
      String id,
      ListTableIndicesRequest listTableIndicesRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = listTableIndicesRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling listTableIndices");
    }

    // verify the required parameter 'listTableIndicesRequest' is set
    if (listTableIndicesRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'listTableIndicesRequest' when calling listTableIndices");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/index/list"
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

    TypeReference<ListTableIndicesResponse> localVarReturnType =
        new TypeReference<ListTableIndicesResponse>() {};
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
   * List all tags for a table List all tags that have been created for table &#x60;id&#x60;.
   * Returns a map of tag names to their corresponding version numbers and metadata. REST NAMESPACE
   * ONLY REST namespace uses GET to perform this operation without a request body. It passes in the
   * &#x60;ListTableTagsRequest&#x60; information in the following way: - &#x60;id&#x60;: pass
   * through path parameter of the same name - &#x60;page_token&#x60;: pass through query parameter
   * of the same name - &#x60;limit&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @return ListTableTagsResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableTagsResponse listTableTags(
      String id, String delimiter, String pageToken, Integer limit) throws ApiException {
    return this.listTableTags(id, delimiter, pageToken, limit, Collections.emptyMap());
  }

  /**
   * List all tags for a table List all tags that have been created for table &#x60;id&#x60;.
   * Returns a map of tag names to their corresponding version numbers and metadata. REST NAMESPACE
   * ONLY REST namespace uses GET to perform this operation without a request body. It passes in the
   * &#x60;ListTableTagsRequest&#x60; information in the following way: - &#x60;id&#x60;: pass
   * through path parameter of the same name - &#x60;page_token&#x60;: pass through query parameter
   * of the same name - &#x60;limit&#x60;: pass through query parameter of the same name
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param pageToken (optional)
   * @param limit (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListTableTagsResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableTagsResponse listTableTags(
      String id,
      String delimiter,
      String pageToken,
      Integer limit,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling listTableTags");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/tags/list"
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

    TypeReference<ListTableTagsResponse> localVarReturnType =
        new TypeReference<ListTableTagsResponse>() {};
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
   * List all versions of a table List all versions (commits) of table &#x60;id&#x60; with their
   * metadata.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param listTableVersionsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return ListTableVersionsResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableVersionsResponse listTableVersions(
      String id, ListTableVersionsRequest listTableVersionsRequest, String delimiter)
      throws ApiException {
    return this.listTableVersions(id, listTableVersionsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * List all versions of a table List all versions (commits) of table &#x60;id&#x60; with their
   * metadata.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param listTableVersionsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListTableVersionsResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableVersionsResponse listTableVersions(
      String id,
      ListTableVersionsRequest listTableVersionsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = listTableVersionsRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling listTableVersions");
    }

    // verify the required parameter 'listTableVersionsRequest' is set
    if (listTableVersionsRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'listTableVersionsRequest' when calling listTableVersions");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/version/list"
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

    TypeReference<ListTableVersionsResponse> localVarReturnType =
        new TypeReference<ListTableVersionsResponse>() {};
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
   * List tables in a namespace List all child table names of the parent namespace &#x60;id&#x60;.
   * REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body.
   * It passes in the &#x60;ListTablesRequest&#x60; information in the following way: -
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
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
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
   * Merge insert (upsert) records into a table Performs a merge insert (upsert) operation on table
   * &#x60;id&#x60;. This operation updates existing rows based on a matching column and inserts new
   * rows that don&#39;t match. It returns the number of rows inserted and updated. REST NAMESPACE
   * ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the
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
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenMatchedUpdateAllFilt The row is updated (similar to UpdateAll) only for rows where
   *     the SQL expression evaluates to true (optional)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @param whenNotMatchedBySourceDelete Delete all rows from target table that don&#39;t match a
   *     row in the source table (optional, default to false)
   * @param whenNotMatchedBySourceDeleteFilt Delete rows from the target table if there is no match
   *     AND the SQL expression evaluates to true (optional)
   * @return MergeInsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      String id,
      String on,
      byte[] body,
      String delimiter,
      Boolean whenMatchedUpdateAll,
      String whenMatchedUpdateAllFilt,
      Boolean whenNotMatchedInsertAll,
      Boolean whenNotMatchedBySourceDelete,
      String whenNotMatchedBySourceDeleteFilt)
      throws ApiException {
    return this.mergeInsertIntoTable(
        id,
        on,
        body,
        delimiter,
        whenMatchedUpdateAll,
        whenMatchedUpdateAllFilt,
        whenNotMatchedInsertAll,
        whenNotMatchedBySourceDelete,
        whenNotMatchedBySourceDeleteFilt,
        Collections.emptyMap());
  }

  /**
   * Merge insert (upsert) records into a table Performs a merge insert (upsert) operation on table
   * &#x60;id&#x60;. This operation updates existing rows based on a matching column and inserts new
   * rows that don&#39;t match. It returns the number of rows inserted and updated. REST NAMESPACE
   * ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the
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
   * @param whenMatchedUpdateAll Update all columns when rows match (optional, default to false)
   * @param whenMatchedUpdateAllFilt The row is updated (similar to UpdateAll) only for rows where
   *     the SQL expression evaluates to true (optional)
   * @param whenNotMatchedInsertAll Insert all columns when rows don&#39;t match (optional, default
   *     to false)
   * @param whenNotMatchedBySourceDelete Delete all rows from target table that don&#39;t match a
   *     row in the source table (optional, default to false)
   * @param whenNotMatchedBySourceDeleteFilt Delete rows from the target table if there is no match
   *     AND the SQL expression evaluates to true (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return MergeInsertIntoTableResponse
   * @throws ApiException if fails to make API call
   */
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      String id,
      String on,
      byte[] body,
      String delimiter,
      Boolean whenMatchedUpdateAll,
      String whenMatchedUpdateAllFilt,
      Boolean whenNotMatchedInsertAll,
      Boolean whenNotMatchedBySourceDelete,
      String whenNotMatchedBySourceDeleteFilt,
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

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));
    localVarQueryParams.addAll(apiClient.parameterToPair("on", on));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("when_matched_update_all", whenMatchedUpdateAll));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("when_matched_update_all_filt", whenMatchedUpdateAllFilt));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("when_not_matched_insert_all", whenNotMatchedInsertAll));
    localVarQueryParams.addAll(
        apiClient.parameterToPair(
            "when_not_matched_by_source_delete", whenNotMatchedBySourceDelete));
    localVarQueryParams.addAll(
        apiClient.parameterToPair(
            "when_not_matched_by_source_delete_filt", whenNotMatchedBySourceDeleteFilt));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/vnd.apache.arrow.stream"};
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
   * Query a table Query table &#x60;id&#x60; with vector search, full text search and optional SQL
   * filtering. Returns results in Arrow IPC file or stream format.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return byte[]
   * @throws ApiException if fails to make API call
   */
  public byte[] queryTable(String id, QueryTableRequest queryTableRequest, String delimiter)
      throws ApiException {
    return this.queryTable(id, queryTableRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Query a table Query table &#x60;id&#x60; with vector search, full text search and optional SQL
   * filtering. Returns results in Arrow IPC file or stream format.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param queryTableRequest Query request (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return byte[]
   * @throws ApiException if fails to make API call
   */
  public byte[] queryTable(
      String id,
      QueryTableRequest queryTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
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

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {
      "application/vnd.apache.arrow.file", "application/vnd.apache.arrow.stream", "application/json"
    };
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
   * Register a table to a namespace Register an existing table at a given storage location as
   * &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param registerTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return RegisterTableResponse
   * @throws ApiException if fails to make API call
   */
  public RegisterTableResponse registerTable(
      String id, RegisterTableRequest registerTableRequest, String delimiter) throws ApiException {
    return this.registerTable(id, registerTableRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Register a table to a namespace Register an existing table at a given storage location as
   * &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param registerTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return RegisterTableResponse
   * @throws ApiException if fails to make API call
   */
  public RegisterTableResponse registerTable(
      String id,
      RegisterTableRequest registerTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = registerTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling registerTable");
    }

    // verify the required parameter 'registerTableRequest' is set
    if (registerTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'registerTableRequest' when calling registerTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/register"
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

    TypeReference<RegisterTableResponse> localVarReturnType =
        new TypeReference<RegisterTableResponse>() {};
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
   * Restore table to a specific version Restore table &#x60;id&#x60; to a specific version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param restoreTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return RestoreTableResponse
   * @throws ApiException if fails to make API call
   */
  public RestoreTableResponse restoreTable(
      String id, RestoreTableRequest restoreTableRequest, String delimiter) throws ApiException {
    return this.restoreTable(id, restoreTableRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Restore table to a specific version Restore table &#x60;id&#x60; to a specific version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param restoreTableRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return RestoreTableResponse
   * @throws ApiException if fails to make API call
   */
  public RestoreTableResponse restoreTable(
      String id,
      RestoreTableRequest restoreTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = restoreTableRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling restoreTable");
    }

    // verify the required parameter 'restoreTableRequest' is set
    if (restoreTableRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'restoreTableRequest' when calling restoreTable");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/restore"
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

    TypeReference<RestoreTableResponse> localVarReturnType =
        new TypeReference<RestoreTableResponse>() {};
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
   * Check if a table exists Check if table &#x60;id&#x60; exists. This operation should behave
   * exactly like DescribeTable, except it does not contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param tableExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @throws ApiException if fails to make API call
   */
  public void tableExists(String id, TableExistsRequest tableExistsRequest, String delimiter)
      throws ApiException {
    this.tableExists(id, tableExistsRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Check if a table exists Check if table &#x60;id&#x60; exists. This operation should behave
   * exactly like DescribeTable, except it does not contain a response body.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param tableExistsRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void tableExists(
      String id,
      TableExistsRequest tableExistsRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = tableExistsRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling tableExists");
    }

    // verify the required parameter 'tableExistsRequest' is set
    if (tableExistsRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'tableExistsRequest' when calling tableExists");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/exists"
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
   * @return UpdateTableResponse
   * @throws ApiException if fails to make API call
   */
  public UpdateTableResponse updateTable(
      String id, UpdateTableRequest updateTableRequest, String delimiter) throws ApiException {
    return this.updateTable(id, updateTableRequest, delimiter, Collections.emptyMap());
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
   * @param additionalHeaders additionalHeaders for this call
   * @return UpdateTableResponse
   * @throws ApiException if fails to make API call
   */
  public UpdateTableResponse updateTable(
      String id,
      UpdateTableRequest updateTableRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
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

    localVarQueryParams.addAll(apiClient.parameterToPair("delimiter", delimiter));

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

  /**
   * Update a tag to point to a different version Update an existing tag for table &#x60;id&#x60; to
   * point to a different version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @throws ApiException if fails to make API call
   */
  public void updateTableTag(
      String id, UpdateTableTagRequest updateTableTagRequest, String delimiter)
      throws ApiException {
    this.updateTableTag(id, updateTableTagRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Update a tag to point to a different version Update an existing tag for table &#x60;id&#x60; to
   * point to a different version.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param updateTableTagRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void updateTableTag(
      String id,
      UpdateTableTagRequest updateTableTagRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = updateTableTagRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling updateTableTag");
    }

    // verify the required parameter 'updateTableTagRequest' is set
    if (updateTableTagRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'updateTableTagRequest' when calling updateTableTag");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/tags/update"
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
