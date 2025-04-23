# \TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_table**](TableApi.md#create_table) | **POST** /v1/namespaces/{ns}/tables | Create a table in the catalog
[**get_table**](TableApi.md#get_table) | **GET** /v1/namespaces/{ns}/tables/{table} | Get a table from the catalog
[**register_table**](TableApi.md#register_table) | **POST** /v1/namespaces/{ns}/register | Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 
[**table_exists**](TableApi.md#table_exists) | **HEAD** /v1/namespaces/{ns}/tables/{table} | Check if a table exists



## create_table

> models::GetTableResponse create_table(ns, create_table_request)
Create a table in the catalog

Create a new Lance table in the catalog. There are three modes when trying to create a table: * CREATE: Create the table if it does not exist. If a table of the same name already exists, the operation fails with 400. * EXIST_OK: Create the table if it does not exist. If a table of the same name already exists, the operation succeeds and the existing table is kept. * OVERWRITE: Create the table if it does not exist. If a table of the same name already exists, the existing table and all data is dropped and a new table with this name with no data is created. The server might create the table using a library and writer version that is different from the one in the user environment. The server is responsible for rejecting the request if the table created by the server cannot be properly used by the client library and writer version. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |
**create_table_request** | [**CreateTableRequest**](CreateTableRequest.md) |  | [required] |

### Return type

[**models::GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_table

> models::GetTableResponse get_table(ns, table)
Get a table from the catalog

Get a table's detailed information under a specified namespace from the catalog.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |
**table** | **String** | A table name. | [required] |

### Return type

[**models::GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## register_table

> models::GetTableResponse register_table(ns, register_table_request)
Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |
**register_table_request** | [**RegisterTableRequest**](RegisterTableRequest.md) |  | [required] |

### Return type

[**models::GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## table_exists

> table_exists(ns, table)
Check if a table exists

Check if a table exists within a given namespace.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |
**table** | **String** | A table name. | [required] |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

