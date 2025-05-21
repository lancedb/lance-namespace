# lance_namespace_urllib3_client.TransactionApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_transaction**](TransactionApi.md#alter_transaction) | **POST** /AlterTransaction | Alter information of a transaction.
[**get_transaction**](TransactionApi.md#get_transaction) | **POST** /GetTransaction | Get information about a transaction


# **alter_transaction**
> AlterTransactionResponse alter_transaction(alter_transaction_request)

Alter information of a transaction.

### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.alter_transaction_request import AlterTransactionRequest
from lance_namespace_urllib3_client.models.alter_transaction_response import AlterTransactionResponse
from lance_namespace_urllib3_client.rest import ApiException
from pprint import pprint

# Defining the host is optional and defaults to http://localhost:2333
# See configuration.py for a list of all supported configuration parameters.
configuration = lance_namespace_urllib3_client.Configuration(
    host = "http://localhost:2333"
)


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TransactionApi(api_client)
    alter_transaction_request = lance_namespace_urllib3_client.AlterTransactionRequest() # AlterTransactionRequest | 

    try:
        # Alter information of a transaction.
        api_response = api_instance.alter_transaction(alter_transaction_request)
        print("The response of TransactionApi->alter_transaction:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TransactionApi->alter_transaction: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **alter_transaction_request** | [**AlterTransactionRequest**](AlterTransactionRequest.md)|  | 

### Return type

[**AlterTransactionResponse**](AlterTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Response of AlterTransaction |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_transaction**
> GetTransactionResponse get_transaction(get_transaction_request)

Get information about a transaction

Return a detailed information for a given transaction

### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.get_transaction_request import GetTransactionRequest
from lance_namespace_urllib3_client.models.get_transaction_response import GetTransactionResponse
from lance_namespace_urllib3_client.rest import ApiException
from pprint import pprint

# Defining the host is optional and defaults to http://localhost:2333
# See configuration.py for a list of all supported configuration parameters.
configuration = lance_namespace_urllib3_client.Configuration(
    host = "http://localhost:2333"
)


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TransactionApi(api_client)
    get_transaction_request = lance_namespace_urllib3_client.GetTransactionRequest() # GetTransactionRequest | 

    try:
        # Get information about a transaction
        api_response = api_instance.get_transaction(get_transaction_request)
        print("The response of TransactionApi->get_transaction:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TransactionApi->get_transaction: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **get_transaction_request** | [**GetTransactionRequest**](GetTransactionRequest.md)|  | 

### Return type

[**GetTransactionResponse**](GetTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Response of GetTransaction |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

