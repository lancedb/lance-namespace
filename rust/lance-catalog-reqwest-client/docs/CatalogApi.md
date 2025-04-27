# \CatalogApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**catalog_exists**](CatalogApi.md#catalog_exists) | **HEAD** /v1/catalogs/{catalog} | Check if a catalog exists
[**create_catalog**](CatalogApi.md#create_catalog) | **POST** /v1/catalogs | Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created. 
[**drop_catalog**](CatalogApi.md#drop_catalog) | **DELETE** /v1/catalogs/{catalog} | Drop a catalog. The catalog must be empty.
[**get_catalog**](CatalogApi.md#get_catalog) | **GET** /v1/catalogs/{catalog} | Get information about a catalog
[**list_catalogs**](CatalogApi.md#list_catalogs) | **GET** /v1/catalogs | List all direct child catalogs of the root catalog. 



## catalog_exists

> catalog_exists(catalog, catalog_delimiter)
Check if a catalog exists

Check if a catalog exists. The response does not contain a body.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**catalog** | **String** | An identifier of the catalog. | [required] |
**catalog_delimiter** | Option<**String**> | The delimiter used by the catalog identifier string |  |[default to .]

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_catalog

> models::CreateCatalogResponse create_catalog(create_catalog_request, parent_catalog, parent_catalog_delimiter)
Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**create_catalog_request** | [**CreateCatalogRequest**](CreateCatalogRequest.md) |  | [required] |
**parent_catalog** | Option<**String**> | An identifier of the parent catalog. |  |
**parent_catalog_delimiter** | Option<**String**> | The delimiter used by the parent catalog identifier |  |[default to .]

### Return type

[**models::CreateCatalogResponse**](CreateCatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## drop_catalog

> drop_catalog(catalog, catalog_delimiter)
Drop a catalog. The catalog must be empty.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**catalog** | **String** | An identifier of the catalog. | [required] |
**catalog_delimiter** | Option<**String**> | The delimiter used by the catalog identifier string |  |[default to .]

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_catalog

> models::GetCatalogResponse get_catalog(catalog, catalog_delimiter)
Get information about a catalog

Return a detailed information for a given catalog

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**catalog** | **String** | An identifier of the catalog. | [required] |
**catalog_delimiter** | Option<**String**> | The delimiter used by the catalog identifier string |  |[default to .]

### Return type

[**models::GetCatalogResponse**](GetCatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_catalogs

> models::ListCatalogsResponse list_catalogs(page_token, page_size, parent_catalog, parent_catalog_delimiter)
List all direct child catalogs of the root catalog. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**page_token** | Option<**String**> |  |  |
**page_size** | Option<**i32**> | An inclusive upper bound of the number of results that a client will receive. |  |
**parent_catalog** | Option<**String**> | An identifier of the parent catalog. |  |
**parent_catalog_delimiter** | Option<**String**> | The delimiter used by the parent catalog identifier |  |[default to .]

### Return type

[**models::ListCatalogsResponse**](ListCatalogsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

