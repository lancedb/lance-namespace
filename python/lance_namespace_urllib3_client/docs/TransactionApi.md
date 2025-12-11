# lance_namespace_urllib3_client.TransactionApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_transaction**](TransactionApi.md#alter_transaction) | **POST** /v1/transaction/{id}/alter | Alter information of a transaction.
[**describe_transaction**](TransactionApi.md#describe_transaction) | **POST** /v1/transaction/{id}/describe | Describe information about a transaction


# **alter_transaction**
> AlterTransactionResponse alter_transaction(id, alter_transaction_request, delimiter=delimiter)

Alter information of a transaction.

Alter a transaction with a list of actions such as setting status or properties.
The server should either succeed and apply all actions, or fail and apply no action.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

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

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

configuration.access_token = os.environ["ACCESS_TOKEN"]

# Configure API key authorization: ApiKeyAuth
configuration.api_key['ApiKeyAuth'] = os.environ["API_KEY"]

# Uncomment below to setup prefix (e.g. Bearer) for API key, if needed
# configuration.api_key_prefix['ApiKeyAuth'] = 'Bearer'

# Configure Bearer authorization: BearerAuth
configuration = lance_namespace_urllib3_client.Configuration(
    access_token = os.environ["BEARER_TOKEN"]
)

# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TransactionApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    alter_transaction_request = lance_namespace_urllib3_client.AlterTransactionRequest() # AlterTransactionRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Alter information of a transaction.
        api_response = api_instance.alter_transaction(id, alter_transaction_request, delimiter=delimiter)
        print("The response of TransactionApi->alter_transaction:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TransactionApi->alter_transaction: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **alter_transaction_request** | [**AlterTransactionRequest**](AlterTransactionRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**AlterTransactionResponse**](AlterTransactionResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

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

# **describe_transaction**
> DescribeTransactionResponse describe_transaction(id, describe_transaction_request, delimiter=delimiter)

Describe information about a transaction

Return a detailed information for a given transaction


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.describe_transaction_request import DescribeTransactionRequest
from lance_namespace_urllib3_client.models.describe_transaction_response import DescribeTransactionResponse
from lance_namespace_urllib3_client.rest import ApiException
from pprint import pprint

# Defining the host is optional and defaults to http://localhost:2333
# See configuration.py for a list of all supported configuration parameters.
configuration = lance_namespace_urllib3_client.Configuration(
    host = "http://localhost:2333"
)

# The client must configure the authentication and authorization parameters
# in accordance with the API server security policy.
# Examples for each auth method are provided below, use the example that
# satisfies your auth use case.

configuration.access_token = os.environ["ACCESS_TOKEN"]

# Configure API key authorization: ApiKeyAuth
configuration.api_key['ApiKeyAuth'] = os.environ["API_KEY"]

# Uncomment below to setup prefix (e.g. Bearer) for API key, if needed
# configuration.api_key_prefix['ApiKeyAuth'] = 'Bearer'

# Configure Bearer authorization: BearerAuth
configuration = lance_namespace_urllib3_client.Configuration(
    access_token = os.environ["BEARER_TOKEN"]
)

# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TransactionApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    describe_transaction_request = lance_namespace_urllib3_client.DescribeTransactionRequest() # DescribeTransactionRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Describe information about a transaction
        api_response = api_instance.describe_transaction(id, describe_transaction_request, delimiter=delimiter)
        print("The response of TransactionApi->describe_transaction:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TransactionApi->describe_transaction: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **describe_transaction_request** | [**DescribeTransactionRequest**](DescribeTransactionRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DescribeTransactionResponse**](DescribeTransactionResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Response of DescribeTransaction |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

