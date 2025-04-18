# lance_catalog_urllib3_client.NamespaceApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_namespace**](NamespaceApi.md#create_namespace) | **POST** /v1/namespaces | Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. 
[**drop_namespace**](NamespaceApi.md#drop_namespace) | **DELETE** /v1/namespaces/{ns} | Drop a namespace from the catalog. Namespace must be empty.
[**get_namespace**](NamespaceApi.md#get_namespace) | **GET** /v1/namespaces/{ns} | Get information about a namespace
[**list_namespaces**](NamespaceApi.md#list_namespaces) | **GET** /v1/namespaces | List all namespaces in the catalog. 
[**namespace_exists**](NamespaceApi.md#namespace_exists) | **HEAD** /v1/namespaces/{ns} | Check if a namespace exists


# **create_namespace**
> CreateNamespaceResponse create_namespace(create_namespace_request)

Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. 

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.create_namespace_request import CreateNamespaceRequest
from lance_catalog_urllib3_client.models.create_namespace_response import CreateNamespaceResponse
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
    api_instance = lance_catalog_urllib3_client.NamespaceApi(api_client)
    create_namespace_request = lance_catalog_urllib3_client.CreateNamespaceRequest() # CreateNamespaceRequest | 

    try:
        # Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. 
        api_response = api_instance.create_namespace(create_namespace_request)
        print("The response of NamespaceApi->create_namespace:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->create_namespace: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **create_namespace_request** | [**CreateNamespaceRequest**](CreateNamespaceRequest.md)|  | 

### Return type

[**CreateNamespaceResponse**](CreateNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Represents a successful call to create a namespace. Returns the namespace created, as well as any properties that were stored for the namespace, including those the server might have added. Implementations are not required to support namespace properties. |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **drop_namespace**
> drop_namespace(ns)

Drop a namespace from the catalog. Namespace must be empty.

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
    api_instance = lance_catalog_urllib3_client.NamespaceApi(api_client)
    ns = 'ns_example' # str | The name of the namespace.

    try:
        # Drop a namespace from the catalog. Namespace must be empty.
        api_instance.drop_namespace(ns)
    except Exception as e:
        print("Exception when calling NamespaceApi->drop_namespace: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ns** | **str**| The name of the namespace. | 

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
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_namespace**
> GetNamespaceResponse get_namespace(ns)

Get information about a namespace

Return a detailed information for a given namespace

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.get_namespace_response import GetNamespaceResponse
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
    api_instance = lance_catalog_urllib3_client.NamespaceApi(api_client)
    ns = 'ns_example' # str | The name of the namespace.

    try:
        # Get information about a namespace
        api_response = api_instance.get_namespace(ns)
        print("The response of NamespaceApi->get_namespace:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->get_namespace: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ns** | **str**| The name of the namespace. | 

### Return type

[**GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server. |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list_namespaces**
> ListNamespacesResponse list_namespaces()

List all namespaces in the catalog. 

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.list_namespaces_response import ListNamespacesResponse
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
    api_instance = lance_catalog_urllib3_client.NamespaceApi(api_client)

    try:
        # List all namespaces in the catalog. 
        api_response = api_instance.list_namespaces()
        print("The response of NamespaceApi->list_namespaces:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->list_namespaces: %s\n" % e)
```



### Parameters

This endpoint does not need any parameter.

### Return type

[**ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | A list of namespaces |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **namespace_exists**
> namespace_exists(ns)

Check if a namespace exists

Check if a namespace exists. The response does not contain a body.

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
    api_instance = lance_catalog_urllib3_client.NamespaceApi(api_client)
    ns = 'ns_example' # str | The name of the namespace.

    try:
        # Check if a namespace exists
        api_instance.namespace_exists(ns)
    except Exception as e:
        print("Exception when calling NamespaceApi->namespace_exists: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ns** | **str**| The name of the namespace. | 

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
**200** | Success, no content |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

