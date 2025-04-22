# lance_catalog_urllib3_client.TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**get_table**](TableApi.md#get_table) | **GET** /v1/namespaces/{ns}/tables/{table} | Get a table from the catalog
[**register_table**](TableApi.md#register_table) | **POST** /v1/namespaces/{ns}/register | Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 
[**table_exists**](TableApi.md#table_exists) | **HEAD** /v1/namespaces/{ns}/tables/{table} | Check if a table exists


# **get_table**
> GetTableResponse get_table(ns, table)

Get a table from the catalog

Get a table's detailed information under a specified namespace from the catalog.

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.get_table_response import GetTableResponse
from lance_catalog_urllib3_client.rest import ApiException
from pprint import pprint

# Defining the host is optional and defaults to http://localhost:2333
# See configuration.py for a list of all supported configuration parameters.
configuration = lance_catalog_urllib3_client.Configuration(
    host = "http://localhost:2333"
)


# Enter a context with an instance of the API client
with lance_catalog_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_catalog_urllib3_client.TableApi(api_client)
    ns = 'ns_example' # str | The name of the namespace.
    table = 'table_example' # str | A table name.

    try:
        # Get a table from the catalog
        api_response = api_instance.get_table(ns, table)
        print("The response of TableApi->get_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->get_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ns** | **str**| The name of the namespace. | 
 **table** | **str**| A table name. | 

### Return type

[**GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table properties result when loading a table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **register_table**
> GetTableResponse register_table(ns, register_table_request)

Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.get_table_response import GetTableResponse
from lance_catalog_urllib3_client.models.register_table_request import RegisterTableRequest
from lance_catalog_urllib3_client.rest import ApiException
from pprint import pprint

# Defining the host is optional and defaults to http://localhost:2333
# See configuration.py for a list of all supported configuration parameters.
configuration = lance_catalog_urllib3_client.Configuration(
    host = "http://localhost:2333"
)


# Enter a context with an instance of the API client
with lance_catalog_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_catalog_urllib3_client.TableApi(api_client)
    ns = 'ns_example' # str | The name of the namespace.
    register_table_request = lance_catalog_urllib3_client.RegisterTableRequest() # RegisterTableRequest | 

    try:
        # Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 
        api_response = api_instance.register_table(ns, register_table_request)
        print("The response of TableApi->register_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->register_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ns** | **str**| The name of the namespace. | 
 **register_table_request** | [**RegisterTableRequest**](RegisterTableRequest.md)|  | 

### Return type

[**GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table properties result when loading a table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **table_exists**
> table_exists(ns, table)

Check if a table exists

Check if a table exists within a given namespace.

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.rest import ApiException
from pprint import pprint

# Defining the host is optional and defaults to http://localhost:2333
# See configuration.py for a list of all supported configuration parameters.
configuration = lance_catalog_urllib3_client.Configuration(
    host = "http://localhost:2333"
)


# Enter a context with an instance of the API client
with lance_catalog_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_catalog_urllib3_client.TableApi(api_client)
    ns = 'ns_example' # str | The name of the namespace.
    table = 'table_example' # str | A table name.

    try:
        # Check if a table exists
        api_instance.table_exists(ns, table)
    except Exception as e:
        print("Exception when calling TableApi->table_exists: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ns** | **str**| The name of the namespace. | 
 **table** | **str**| A table name. | 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**204** | Success, no content |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

