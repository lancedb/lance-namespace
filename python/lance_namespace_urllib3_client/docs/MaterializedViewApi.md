# lance_namespace_urllib3_client.MaterializedViewApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_materialized_view**](MaterializedViewApi.md#create_materialized_view) | **POST** /v1/materialized_view/{id}/create | Create a materialized view
[**refresh_materialized_view**](MaterializedViewApi.md#refresh_materialized_view) | **POST** /v1/materialized_view/{id}/refresh | Trigger an async materialized view refresh


# **create_materialized_view**
> CreateMaterializedViewResponse create_materialized_view(id, create_materialized_view_request, delimiter=delimiter)

Create a materialized view

Create a materialized view at identifier `id`. The view may be
query-backed, UDTF-backed, or chunker-backed, controlled by the
`kind` discriminator.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_materialized_view_request import CreateMaterializedViewRequest
from lance_namespace_urllib3_client.models.create_materialized_view_response import CreateMaterializedViewResponse
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
    api_instance = lance_namespace_urllib3_client.MaterializedViewApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    create_materialized_view_request = lance_namespace_urllib3_client.CreateMaterializedViewRequest() # CreateMaterializedViewRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Create a materialized view
        api_response = api_instance.create_materialized_view(id, create_materialized_view_request, delimiter=delimiter)
        print("The response of MaterializedViewApi->create_materialized_view:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling MaterializedViewApi->create_materialized_view: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **create_materialized_view_request** | [**CreateMaterializedViewRequest**](CreateMaterializedViewRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**CreateMaterializedViewResponse**](CreateMaterializedViewResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Materialized view created |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **refresh_materialized_view**
> RefreshMaterializedViewResponse refresh_materialized_view(id, delimiter=delimiter, refresh_materialized_view_request=refresh_materialized_view_request)

Trigger an async materialized view refresh

Trigger an asynchronous refresh job for materialized view `id`.
Returns a job ID for tracking.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.refresh_materialized_view_request import RefreshMaterializedViewRequest
from lance_namespace_urllib3_client.models.refresh_materialized_view_response import RefreshMaterializedViewResponse
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
    api_instance = lance_namespace_urllib3_client.MaterializedViewApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    refresh_materialized_view_request = lance_namespace_urllib3_client.RefreshMaterializedViewRequest() # RefreshMaterializedViewRequest |  (optional)

    try:
        # Trigger an async materialized view refresh
        api_response = api_instance.refresh_materialized_view(id, delimiter=delimiter, refresh_materialized_view_request=refresh_materialized_view_request)
        print("The response of MaterializedViewApi->refresh_materialized_view:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling MaterializedViewApi->refresh_materialized_view: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **refresh_materialized_view_request** | [**RefreshMaterializedViewRequest**](RefreshMaterializedViewRequest.md)|  | [optional] 

### Return type

[**RefreshMaterializedViewResponse**](RefreshMaterializedViewResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**202** | Refresh job accepted |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

