# \TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**get_table**](TableApi.md#get_table) | **GET** /v1/tables/{table} | Get a table from the namespace
[**register_table**](TableApi.md#register_table) | **POST** /v1/table/register | Register a table to a namespace
[**table_exists**](TableApi.md#table_exists) | **HEAD** /v1/tables/{table} | Check if a table exists



## get_table

> models::GetTableResponse get_table(table, delimiter)
Get a table from the namespace

Get a table's detailed information under a specified namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**table** | **String** | A string identifier of the table | [required] |
**delimiter** | Option<**String**> | The delimiter for the identifier used in the context |  |

### Return type

[**models::GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## register_table

> models::GetTableResponse register_table(register_table_request)
Register a table to a namespace

Register an existing table at a given storage location to a namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
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

> table_exists(table, delimiter)
Check if a table exists

Check if a table exists. This API should behave exactly like the GetTable API, except it does not contain a body. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**table** | **String** | A string identifier of the table | [required] |
**delimiter** | Option<**String**> | The delimiter for the identifier used in the context |  |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

