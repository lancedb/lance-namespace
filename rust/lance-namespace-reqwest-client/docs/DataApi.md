# \DataApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**delete_from_table**](DataApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**insert_into_table**](DataApi.md#insert_into_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**merge_insert_into_table**](DataApi.md#merge_insert_into_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](DataApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**update_table**](DataApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table



## delete_from_table

> models::DeleteFromTableResponse delete_from_table(id, delete_from_table_request)
Delete rows from a table

Delete rows from a table based on a SQL predicate. Returns the number of rows that were deleted. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**delete_from_table_request** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md) | Delete request | [required] |

### Return type

[**models::DeleteFromTableResponse**](DeleteFromTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## insert_into_table

> models::InsertIntoTableResponse insert_into_table(id, body, mode)
Insert records into a table

Insert new records into an existing table using Arrow IPC format. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **Vec<u8>** | Arrow IPC data | [required] |
**mode** | Option<**String**> | Insert mode: \"append\" (default) or \"overwrite\" |  |[default to append]

### Return type

[**models::InsertIntoTableResponse**](InsertIntoTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## merge_insert_into_table

> models::MergeInsertIntoTableResponse merge_insert_into_table(id, on, body, when_matched_update_all, when_not_matched_insert_all)
Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on a table. This operation updates existing rows based on a matching column and inserts new rows that don't match. Returns the number of rows inserted and updated. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**on** | **String** | Column name to use for matching rows (required) | [required] |
**body** | **Vec<u8>** | Arrow IPC data containing the records to merge | [required] |
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match |  |[default to false]
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match |  |[default to false]

### Return type

[**models::MergeInsertIntoTableResponse**](MergeInsertIntoTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## query_table

> Vec<u8> query_table(id, query_table_request)
Query a table

Query a table with vector search and optional filtering. Returns results in Arrow IPC stream format. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**query_table_request** | [**QueryTableRequest**](QueryTableRequest.md) | Query request | [required] |

### Return type

[**Vec<u8>**](Vec<u8>.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/vnd.apache.arrow.stream, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## update_table

> models::UpdateTableResponse update_table(id, update_table_request)
Update rows in a table

Update existing rows in a table using SQL expressions. Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**update_table_request** | [**UpdateTableRequest**](UpdateTableRequest.md) | Update request | [required] |

### Return type

[**models::UpdateTableResponse**](UpdateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

