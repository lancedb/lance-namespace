# \TransactionApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_transaction**](TransactionApi.md#alter_transaction) | **POST** /v1/transactions/{txn} | Alter information of a transaction.
[**get_transaction**](TransactionApi.md#get_transaction) | **GET** /v1/transactions/{txn} | Get information about a transaction



## alter_transaction

> alter_transaction(txn, alter_transaction_request)
Alter information of a transaction.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**txn** | **String** | The ID of the transaction. | [required] |
**alter_transaction_request** | [**AlterTransactionRequest**](AlterTransactionRequest.md) |  | [required] |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_transaction

> models::GetTransactionResponse get_transaction(txn)
Get information about a transaction

Return a detailed information for a given transaction

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**txn** | **String** | The ID of the transaction. | [required] |

### Return type

[**models::GetTransactionResponse**](GetTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

