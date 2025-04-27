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
import com.lancedb.lance.catalog.client.apache.model.CreateCatalogRequest;
import com.lancedb.lance.catalog.client.apache.model.CreateCatalogResponse;
import com.lancedb.lance.catalog.client.apache.model.GetCatalogResponse;
import com.lancedb.lance.catalog.client.apache.model.ListCatalogsResponse;

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
public class CatalogApi extends BaseApi {

  public CatalogApi() {
    super(Configuration.getDefaultApiClient());
  }

  public CatalogApi(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Check if a catalog exists Check if a catalog exists. The response does not contain a body.
   *
   * @param catalog An identifier of the catalog. (required)
   * @param catalogDelimiter The delimiter used by the catalog identifier string (optional, default
   *     to .)
   * @throws ApiException if fails to make API call
   */
  public void catalogExists(String catalog, String catalogDelimiter) throws ApiException {
    this.catalogExists(catalog, catalogDelimiter, Collections.emptyMap());
  }

  /**
   * Check if a catalog exists Check if a catalog exists. The response does not contain a body.
   *
   * @param catalog An identifier of the catalog. (required)
   * @param catalogDelimiter The delimiter used by the catalog identifier string (optional, default
   *     to .)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void catalogExists(
      String catalog, String catalogDelimiter, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalog' is set
    if (catalog == null) {
      throw new ApiException(
          400, "Missing the required parameter 'catalog' when calling catalogExists");
    }

    // create path and map variables
    String localVarPath =
        "/v1/catalogs/{catalog}"
            .replaceAll(
                "\\{" + "catalog" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(catalog)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("catalogDelimiter", catalogDelimiter));

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

  /**
   * Create a new catalog. A catalog can manage either a collection of child catalogs, or a
   * collection of tables. There are three modes when trying to create a catalog to differentiate
   * the behavior when a catalog of the same name already exists: * CREATE: the operation fails with
   * 400. * EXIST_OK: the operation succeeds and the existing catalog is kept. * OVERWRITE: the
   * existing catalog is dropped and a new empty catalog with this name is created.
   *
   * @param createCatalogRequest (required)
   * @param parentCatalog An identifier of the parent catalog. (optional)
   * @param parentCatalogDelimiter The delimiter used by the parent catalog identifier (optional,
   *     default to .)
   * @return CreateCatalogResponse
   * @throws ApiException if fails to make API call
   */
  public CreateCatalogResponse createCatalog(
      CreateCatalogRequest createCatalogRequest,
      String parentCatalog,
      String parentCatalogDelimiter)
      throws ApiException {
    return this.createCatalog(
        createCatalogRequest, parentCatalog, parentCatalogDelimiter, Collections.emptyMap());
  }

  /**
   * Create a new catalog. A catalog can manage either a collection of child catalogs, or a
   * collection of tables. There are three modes when trying to create a catalog to differentiate
   * the behavior when a catalog of the same name already exists: * CREATE: the operation fails with
   * 400. * EXIST_OK: the operation succeeds and the existing catalog is kept. * OVERWRITE: the
   * existing catalog is dropped and a new empty catalog with this name is created.
   *
   * @param createCatalogRequest (required)
   * @param parentCatalog An identifier of the parent catalog. (optional)
   * @param parentCatalogDelimiter The delimiter used by the parent catalog identifier (optional,
   *     default to .)
   * @param additionalHeaders additionalHeaders for this call
   * @return CreateCatalogResponse
   * @throws ApiException if fails to make API call
   */
  public CreateCatalogResponse createCatalog(
      CreateCatalogRequest createCatalogRequest,
      String parentCatalog,
      String parentCatalogDelimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = createCatalogRequest;

    // verify the required parameter 'createCatalogRequest' is set
    if (createCatalogRequest == null) {
      throw new ApiException(
          400, "Missing the required parameter 'createCatalogRequest' when calling createCatalog");
    }

    // create path and map variables
    String localVarPath = "/v1/catalogs";

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("parentCatalog", parentCatalog));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("parentCatalogDelimiter", parentCatalogDelimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<CreateCatalogResponse> localVarReturnType =
        new TypeReference<CreateCatalogResponse>() {};
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
   * Drop a catalog. The catalog must be empty.
   *
   * @param catalog An identifier of the catalog. (required)
   * @param catalogDelimiter The delimiter used by the catalog identifier string (optional, default
   *     to .)
   * @throws ApiException if fails to make API call
   */
  public void dropCatalog(String catalog, String catalogDelimiter) throws ApiException {
    this.dropCatalog(catalog, catalogDelimiter, Collections.emptyMap());
  }

  /**
   * Drop a catalog. The catalog must be empty.
   *
   * @param catalog An identifier of the catalog. (required)
   * @param catalogDelimiter The delimiter used by the catalog identifier string (optional, default
   *     to .)
   * @param additionalHeaders additionalHeaders for this call
   * @throws ApiException if fails to make API call
   */
  public void dropCatalog(
      String catalog, String catalogDelimiter, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalog' is set
    if (catalog == null) {
      throw new ApiException(
          400, "Missing the required parameter 'catalog' when calling dropCatalog");
    }

    // create path and map variables
    String localVarPath =
        "/v1/catalogs/{catalog}"
            .replaceAll(
                "\\{" + "catalog" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(catalog)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("catalogDelimiter", catalogDelimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    apiClient.invokeAPI(
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
        null);
  }

  /**
   * Get information about a catalog Return a detailed information for a given catalog
   *
   * @param catalog An identifier of the catalog. (required)
   * @param catalogDelimiter The delimiter used by the catalog identifier string (optional, default
   *     to .)
   * @return GetCatalogResponse
   * @throws ApiException if fails to make API call
   */
  public GetCatalogResponse getCatalog(String catalog, String catalogDelimiter)
      throws ApiException {
    return this.getCatalog(catalog, catalogDelimiter, Collections.emptyMap());
  }

  /**
   * Get information about a catalog Return a detailed information for a given catalog
   *
   * @param catalog An identifier of the catalog. (required)
   * @param catalogDelimiter The delimiter used by the catalog identifier string (optional, default
   *     to .)
   * @param additionalHeaders additionalHeaders for this call
   * @return GetCatalogResponse
   * @throws ApiException if fails to make API call
   */
  public GetCatalogResponse getCatalog(
      String catalog, String catalogDelimiter, Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalog' is set
    if (catalog == null) {
      throw new ApiException(
          400, "Missing the required parameter 'catalog' when calling getCatalog");
    }

    // create path and map variables
    String localVarPath =
        "/v1/catalogs/{catalog}"
            .replaceAll(
                "\\{" + "catalog" + "\\}",
                apiClient.escapeString(apiClient.parameterToString(catalog)));

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("catalogDelimiter", catalogDelimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<GetCatalogResponse> localVarReturnType =
        new TypeReference<GetCatalogResponse>() {};
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
   * List all direct child catalogs of the root catalog.
   *
   * @param pageToken (optional)
   * @param pageSize An inclusive upper bound of the number of results that a client will receive.
   *     (optional)
   * @param parentCatalog An identifier of the parent catalog. (optional)
   * @param parentCatalogDelimiter The delimiter used by the parent catalog identifier (optional,
   *     default to .)
   * @return ListCatalogsResponse
   * @throws ApiException if fails to make API call
   */
  public ListCatalogsResponse listCatalogs(
      String pageToken, Integer pageSize, String parentCatalog, String parentCatalogDelimiter)
      throws ApiException {
    return this.listCatalogs(
        pageToken, pageSize, parentCatalog, parentCatalogDelimiter, Collections.emptyMap());
  }

  /**
   * List all direct child catalogs of the root catalog.
   *
   * @param pageToken (optional)
   * @param pageSize An inclusive upper bound of the number of results that a client will receive.
   *     (optional)
   * @param parentCatalog An identifier of the parent catalog. (optional)
   * @param parentCatalogDelimiter The delimiter used by the parent catalog identifier (optional,
   *     default to .)
   * @param additionalHeaders additionalHeaders for this call
   * @return ListCatalogsResponse
   * @throws ApiException if fails to make API call
   */
  public ListCatalogsResponse listCatalogs(
      String pageToken,
      Integer pageSize,
      String parentCatalog,
      String parentCatalogDelimiter,
      Map<String, String> additionalHeaders)
      throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/catalogs";

    StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPair("pageToken", pageToken));
    localVarQueryParams.addAll(apiClient.parameterToPair("pageSize", pageSize));
    localVarQueryParams.addAll(apiClient.parameterToPair("parentCatalog", parentCatalog));
    localVarQueryParams.addAll(
        apiClient.parameterToPair("parentCatalogDelimiter", parentCatalogDelimiter));

    localVarHeaderParams.putAll(additionalHeaders);

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {};

    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {};

    TypeReference<ListCatalogsResponse> localVarReturnType =
        new TypeReference<ListCatalogsResponse>() {};
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
