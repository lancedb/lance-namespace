# lance_namespace_urllib3_client.NamespaceApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_namespace**](NamespaceApi.md#create_namespace) | **POST** /CreateNamespace | Create a new namespace
[**drop_namespace**](NamespaceApi.md#drop_namespace) | **POST** /DropNamespace | Drop a namespace
[**get_namespace**](NamespaceApi.md#get_namespace) | **POST** /GetNamespace | Get information about a namespace
[**list_namespaces**](NamespaceApi.md#list_namespaces) | **POST** /ListNamespaces | List namespaces
[**namespace_exists**](NamespaceApi.md#namespace_exists) | **POST** /NamespaceExists | Check if a namespace exists


# **create_namespace**
> GetNamespaceResponse create_namespace(create_namespace_request)

Create a new namespace

Create a new namespace.
A namespace can manage either a collection of child namespaces, or a collection of tables.
There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:
  * CREATE: the operation fails with 400.
  * EXIST_OK: the operation succeeds and the existing namespace is kept.
  * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_namespace_request import CreateNamespaceRequest
from lance_namespace_urllib3_client.models.get_namespace_response import GetNamespaceResponse
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
    api_instance = lance_namespace_urllib3_client.NamespaceApi(api_client)
    create_namespace_request = lance_namespace_urllib3_client.CreateNamespaceRequest() # CreateNamespaceRequest | 

    try:
        # Create a new namespace
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

[**GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server. |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **drop_namespace**
> object drop_namespace(drop_namespace_request)

Drop a namespace

Drop a namespace. The namespace must be empty.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.drop_namespace_request import DropNamespaceRequest
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
    api_instance = lance_namespace_urllib3_client.NamespaceApi(api_client)
    drop_namespace_request = lance_namespace_urllib3_client.DropNamespaceRequest() # DropNamespaceRequest | 

    try:
        # Drop a namespace
        api_response = api_instance.drop_namespace(drop_namespace_request)
        print("The response of NamespaceApi->drop_namespace:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->drop_namespace: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **drop_namespace_request** | [**DropNamespaceRequest**](DropNamespaceRequest.md)|  | 

### Return type

**object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Result of dropping a namespace |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_namespace**
> GetNamespaceResponse get_namespace(get_namespace_request)

Get information about a namespace

Return the detailed information for a given namespace


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.get_namespace_request import GetNamespaceRequest
from lance_namespace_urllib3_client.models.get_namespace_response import GetNamespaceResponse
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
    api_instance = lance_namespace_urllib3_client.NamespaceApi(api_client)
    get_namespace_request = lance_namespace_urllib3_client.GetNamespaceRequest() # GetNamespaceRequest | 

    try:
        # Get information about a namespace
        api_response = api_instance.get_namespace(get_namespace_request)
        print("The response of NamespaceApi->get_namespace:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->get_namespace: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **get_namespace_request** | [**GetNamespaceRequest**](GetNamespaceRequest.md)|  | 

### Return type

[**GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server. |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list_namespaces**
> ListNamespacesResponse list_namespaces(list_namespaces_request)

List namespaces

List all child namespace names of the root namespace or a given parent namespace.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.list_namespaces_request import ListNamespacesRequest
from lance_namespace_urllib3_client.models.list_namespaces_response import ListNamespacesResponse
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
    api_instance = lance_namespace_urllib3_client.NamespaceApi(api_client)
    list_namespaces_request = lance_namespace_urllib3_client.ListNamespacesRequest() # ListNamespacesRequest | 

    try:
        # List namespaces
        api_response = api_instance.list_namespaces(list_namespaces_request)
        print("The response of NamespaceApi->list_namespaces:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->list_namespaces: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **list_namespaces_request** | [**ListNamespacesRequest**](ListNamespacesRequest.md)|  | 

### Return type

[**ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | A list of namespaces |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **namespace_exists**
> object namespace_exists(namespace_exists_request)

Check if a namespace exists

Check if a namespace exists.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.namespace_exists_request import NamespaceExistsRequest
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
    api_instance = lance_namespace_urllib3_client.NamespaceApi(api_client)
    namespace_exists_request = lance_namespace_urllib3_client.NamespaceExistsRequest() # NamespaceExistsRequest | 

    try:
        # Check if a namespace exists
        api_response = api_instance.namespace_exists(namespace_exists_request)
        print("The response of NamespaceApi->namespace_exists:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling NamespaceApi->namespace_exists: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **namespace_exists_request** | [**NamespaceExistsRequest**](NamespaceExistsRequest.md)|  | 

### Return type

**object**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Result of checking if a namespace exists |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

