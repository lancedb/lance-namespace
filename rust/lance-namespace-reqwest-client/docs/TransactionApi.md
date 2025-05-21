# \TransactionApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_transaction**](TransactionApi.md#alter_transaction) | **POST** /AlterTransaction | Alter information of a transaction.
[**get_transaction**](TransactionApi.md#get_transaction) | **POST** /GetTransaction | Get information about a transaction



## alter_transaction

> models::AlterTransactionResponse alter_transaction(alter_transaction_request)
Alter information of a transaction.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**alter_transaction_request** | [**AlterTransactionRequest**](AlterTransactionRequest.md) |  | [required] |

### Return type

[**models::AlterTransactionResponse**](AlterTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_transaction

> models::GetTransactionResponse get_transaction(get_transaction_request)
Get information about a transaction

Return a detailed information for a given transaction

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**get_transaction_request** | [**GetTransactionRequest**](GetTransactionRequest.md) |  | [required] |

### Return type

[**models::GetTransactionResponse**](GetTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

