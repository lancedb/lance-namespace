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
import org.lance.namespace.model.CreateTableBranchRequest;
import org.lance.namespace.model.CreateTableBranchResponse;
import org.lance.namespace.model.DeleteTableBranchRequest;
import org.lance.namespace.model.DeleteTableBranchResponse;
import org.lance.namespace.model.ListTableBranchesResponse;

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
public class BranchApi extends BaseApi {

  public BranchApi() {
    super(Configuration.getDefaultApiClient());
  }

  public BranchApi(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create a new branch Create a new branch for table &#x60;id&#x60; starting from a source ref
   * (another branch and/or version), defaulting to the latest version of the main branch.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableBranchRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return CreateTableBranchResponse
   * @throws ApiException if fails to make API call
   */
  public CreateTableBranchResponse createTableBranch(
      String id, CreateTableBranchRequest createTableBranchRequest, String delimiter)
      throws ApiException {
    return this.createTableBranch(id, createTableBranchRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Create a new branch Create a new branch for table &#x60;id&#x60; starting from a source ref
   * (another branch and/or version), defaulting to the latest version of the main branch.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param createTableBranchRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateTableBranchResponse
   * @throws ApiException if fails to make API call
   */
  public CreateTableBranchResponse createTableBranch(
      String id,
      CreateTableBranchRequest createTableBranchRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createTableBranchRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling createTableBranch");
    }

    // verify the required parameter 'createTableBranchRequest' is set
    if (createTableBranchRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'createTableBranchRequest' when calling createTableBranch");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/branches/create"
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

    TypeReference<CreateTableBranchResponse> localVarReturnType =
        new TypeReference<CreateTableBranchResponse>() {};
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
   * Delete a branch Delete an existing branch from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteTableBranchRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @return DeleteTableBranchResponse
   * @throws ApiException if fails to make API call
   */
  public DeleteTableBranchResponse deleteTableBranch(
      String id, DeleteTableBranchRequest deleteTableBranchRequest, String delimiter)
      throws ApiException {
    return this.deleteTableBranch(id, deleteTableBranchRequest, delimiter, Collections.emptyMap());
  }

  /**
   * Delete a branch Delete an existing branch from table &#x60;id&#x60;.
   *
   * @param id &#x60;string identifier&#x60; of an object in a namespace, following the Lance
   *     Namespace spec. When the value is equal to the delimiter, it represents the root namespace.
   *     For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the
   *     root namespace. (required)
   * @param deleteTableBranchRequest (required)
   * @param delimiter An optional delimiter of the &#x60;string identifier&#x60;, following the
   *     Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.
   *     (optional)
   * @param additionalHeaders additionalHeaders for this call
   * @return DeleteTableBranchResponse
   * @throws ApiException if fails to make API call
   */
  public DeleteTableBranchResponse deleteTableBranch(
      String id,
      DeleteTableBranchRequest deleteTableBranchRequest,
      String delimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = deleteTableBranchRequest;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(
          400, "Missing the required parameter 'id' when calling deleteTableBranch");
    }

    // verify the required parameter 'deleteTableBranchRequest' is set
    if (deleteTableBranchRequest == null) {
      throw new ApiException(
          400,
          "Missing the required parameter 'deleteTableBranchRequest' when calling deleteTableBranch");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/branches/delete"
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

    TypeReference<DeleteTableBranchResponse> localVarReturnType =
        new TypeReference<DeleteTableBranchResponse>() {};
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
   * List all branches for a table List all branches that have been created for table
   * &#x60;id&#x60;. Returns a map of branch names to their contents. REST NAMESPACE ONLY REST
   * namespace does not use a request body for this operation. The
   * &#x60;ListTableBranchesRequest&#x60; information is passed in the following way: -
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
   * @return ListTableBranchesResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableBranchesResponse listTableBranches(
      String id, String delimiter, String pageToken, Integer limit) throws ApiException {
    return this.listTableBranches(id, delimiter, pageToken, limit, Collections.emptyMap());
  }

  /**
   * List all branches for a table List all branches that have been created for table
   * &#x60;id&#x60;. Returns a map of branch names to their contents. REST NAMESPACE ONLY REST
   * namespace does not use a request body for this operation. The
   * &#x60;ListTableBranchesRequest&#x60; information is passed in the following way: -
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
   * @param additionalHeaders additionalHeaders for this call
   * @return ListTableBranchesResponse
   * @throws ApiException if fails to make API call
   */
  public ListTableBranchesResponse listTableBranches(
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
          400, "Missing the required parameter 'id' when calling listTableBranches");
    }

    // create path and map variables
    String localVarPath =
        "/v1/table/{id}/branches/list"
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

    String[] localVarAuthNames = new String[] {"OAuth2", "ApiKeyAuth", "BearerAuth"};

    TypeReference<ListTableBranchesResponse> localVarReturnType =
        new TypeReference<ListTableBranchesResponse>() {};
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

    final String[] localVarContentTypes = {};

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
