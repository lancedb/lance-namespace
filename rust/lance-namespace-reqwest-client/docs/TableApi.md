# \TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**get_table**](TableApi.md#get_table) | **POST** /GetTable | Get a table from the namespace
[**register_table**](TableApi.md#register_table) | **POST** /RegisterTable | Register a table to a namespace
[**table_exists**](TableApi.md#table_exists) | **POST** /TableExists | Check if a table exists



## get_table

> models::GetTableResponse get_table(get_table_request)
Get a table from the namespace

Get a table's detailed information under a specified namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**get_table_request** | [**GetTableRequest**](GetTableRequest.md) |  | [required] |

### Return type

[**models::GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## register_table

> models::RegisterTableResponse register_table(register_table_request)
Register a table to a namespace

Register an existing table at a given storage location to a namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**register_table_request** | [**RegisterTableRequest**](RegisterTableRequest.md) |  | [required] |

### Return type

[**models::RegisterTableResponse**](RegisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## table_exists

> serde_json::Value table_exists(table_exists_request)
Check if a table exists

Check if a table exists. This API should behave exactly like the GetTable API, except it does not contain a body. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**table_exists_request** | [**TableExistsRequest**](TableExistsRequest.md) |  | [required] |

### Return type

[**serde_json::Value**](serde_json::Value.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

