# \TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**register_table**](TableApi.md#register_table) | **POST** /v1/namespaces/{ns}/register | Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 



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

