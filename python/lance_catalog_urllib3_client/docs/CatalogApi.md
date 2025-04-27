# lance_catalog_urllib3_client.CatalogApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**catalog_exists**](CatalogApi.md#catalog_exists) | **HEAD** /v1/catalogs/{catalog} | Check if a catalog exists
[**create_catalog**](CatalogApi.md#create_catalog) | **POST** /v1/catalogs | Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created. 
[**drop_catalog**](CatalogApi.md#drop_catalog) | **DELETE** /v1/catalogs/{catalog} | Drop a catalog. The catalog must be empty.
[**get_catalog**](CatalogApi.md#get_catalog) | **GET** /v1/catalogs/{catalog} | Get information about a catalog
[**list_catalogs**](CatalogApi.md#list_catalogs) | **GET** /v1/catalogs | List all direct child catalogs of the root catalog. 


# **catalog_exists**
> catalog_exists(catalog, catalog_delimiter=catalog_delimiter)

Check if a catalog exists

Check if a catalog exists. The response does not contain a body.

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
    api_instance = lance_catalog_urllib3_client.CatalogApi(api_client)
    catalog = 'catalog_example' # str | An identifier of the catalog.
    catalog_delimiter = '.' # str | The delimiter used by the catalog identifier (optional) (default to '.')

    try:
        # Check if a catalog exists
        api_instance.catalog_exists(catalog, catalog_delimiter=catalog_delimiter)
    except Exception as e:
        print("Exception when calling CatalogApi->catalog_exists: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **catalog** | **str**| An identifier of the catalog. | 
 **catalog_delimiter** | **str**| The delimiter used by the catalog identifier | [optional] [default to &#39;.&#39;]

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
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_catalog**
> CreateCatalogResponse create_catalog(create_catalog_request, parent_catalog=parent_catalog, parent_catalog_delimiter=parent_catalog_delimiter)

Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created. 

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.create_catalog_request import CreateCatalogRequest
from lance_catalog_urllib3_client.models.create_catalog_response import CreateCatalogResponse
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
    api_instance = lance_catalog_urllib3_client.CatalogApi(api_client)
    create_catalog_request = lance_catalog_urllib3_client.CreateCatalogRequest() # CreateCatalogRequest | 
    parent_catalog = 'parent_catalog_example' # str | An identifier of the parent catalog. (optional)
    parent_catalog_delimiter = '.' # str | The delimiter used by the parent catalog identifier (optional) (default to '.')

    try:
        # Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created. 
        api_response = api_instance.create_catalog(create_catalog_request, parent_catalog=parent_catalog, parent_catalog_delimiter=parent_catalog_delimiter)
        print("The response of CatalogApi->create_catalog:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling CatalogApi->create_catalog: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **create_catalog_request** | [**CreateCatalogRequest**](CreateCatalogRequest.md)|  | 
 **parent_catalog** | **str**| An identifier of the parent catalog. | [optional] 
 **parent_catalog_delimiter** | **str**| The delimiter used by the parent catalog identifier | [optional] [default to &#39;.&#39;]

### Return type

[**CreateCatalogResponse**](CreateCatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Represents a successful call to create a catalog. Returns the catalog created, as well as any properties that were stored for the catalog, including those the server might have added. Implementations are not required to support catalog properties. |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **drop_catalog**
> drop_catalog(catalog, catalog_delimiter=catalog_delimiter)

Drop a catalog. The catalog must be empty.

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
    api_instance = lance_catalog_urllib3_client.CatalogApi(api_client)
    catalog = 'catalog_example' # str | An identifier of the catalog.
    catalog_delimiter = '.' # str | The delimiter used by the catalog identifier (optional) (default to '.')

    try:
        # Drop a catalog. The catalog must be empty.
        api_instance.drop_catalog(catalog, catalog_delimiter=catalog_delimiter)
    except Exception as e:
        print("Exception when calling CatalogApi->drop_catalog: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **catalog** | **str**| An identifier of the catalog. | 
 **catalog_delimiter** | **str**| The delimiter used by the catalog identifier | [optional] [default to &#39;.&#39;]

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
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_catalog**
> GetCatalogResponse get_catalog(catalog, catalog_delimiter=catalog_delimiter)

Get information about a catalog

Return a detailed information for a given catalog

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.get_catalog_response import GetCatalogResponse
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
    api_instance = lance_catalog_urllib3_client.CatalogApi(api_client)
    catalog = 'catalog_example' # str | An identifier of the catalog.
    catalog_delimiter = '.' # str | The delimiter used by the catalog identifier (optional) (default to '.')

    try:
        # Get information about a catalog
        api_response = api_instance.get_catalog(catalog, catalog_delimiter=catalog_delimiter)
        print("The response of CatalogApi->get_catalog:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling CatalogApi->get_catalog: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **catalog** | **str**| An identifier of the catalog. | 
 **catalog_delimiter** | **str**| The delimiter used by the catalog identifier | [optional] [default to &#39;.&#39;]

### Return type

[**GetCatalogResponse**](GetCatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Returns a catalog, as well as any properties stored on the catalog if catalog properties are supported by the server. |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list_catalogs**
> ListCatalogsResponse list_catalogs(page_token=page_token, page_size=page_size, parent_catalog=parent_catalog, parent_catalog_delimiter=parent_catalog_delimiter)

List all direct child catalogs of the root catalog. 

### Example


```python
import lance_catalog_urllib3_client
from lance_catalog_urllib3_client.models.list_catalogs_response import ListCatalogsResponse
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
    api_instance = lance_catalog_urllib3_client.CatalogApi(api_client)
    page_token = 'page_token_example' # str |  (optional)
    page_size = 56 # int | An inclusive upper bound of the number of results that a client will receive. (optional)
    parent_catalog = 'parent_catalog_example' # str | An identifier of the parent catalog. (optional)
    parent_catalog_delimiter = '.' # str | The delimiter used by the parent catalog identifier (optional) (default to '.')

    try:
        # List all direct child catalogs of the root catalog. 
        api_response = api_instance.list_catalogs(page_token=page_token, page_size=page_size, parent_catalog=parent_catalog, parent_catalog_delimiter=parent_catalog_delimiter)
        print("The response of CatalogApi->list_catalogs:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling CatalogApi->list_catalogs: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page_token** | **str**|  | [optional] 
 **page_size** | **int**| An inclusive upper bound of the number of results that a client will receive. | [optional] 
 **parent_catalog** | **str**| An identifier of the parent catalog. | [optional] 
 **parent_catalog_delimiter** | **str**| The delimiter used by the parent catalog identifier | [optional] [default to &#39;.&#39;]

### Return type

[**ListCatalogsResponse**](ListCatalogsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | A list of catalogs |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

