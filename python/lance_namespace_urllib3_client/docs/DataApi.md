# lance_namespace_urllib3_client.DataApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_table_add_columns**](DataApi.md#alter_table_add_columns) | **POST** /v1/table/{id}/add_columns | Add new columns to table schema
[**analyze_table_query_plan**](DataApi.md#analyze_table_query_plan) | **POST** /v1/table/{id}/analyze_plan | Analyze query execution plan
[**count_table_rows**](DataApi.md#count_table_rows) | **POST** /v1/table/{id}/count_rows | Count rows in a table
[**create_table**](DataApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**delete_from_table**](DataApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**explain_table_query_plan**](DataApi.md#explain_table_query_plan) | **POST** /v1/table/{id}/explain_plan | Get query execution plan explanation
[**insert_into_table**](DataApi.md#insert_into_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**merge_insert_into_table**](DataApi.md#merge_insert_into_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](DataApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**update_table**](DataApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table


# **alter_table_add_columns**
> AlterTableAddColumnsResponse alter_table_add_columns(id, alter_table_add_columns_request, delimiter=delimiter)

Add new columns to table schema

Add new columns to table `id` using SQL expressions or default values.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.alter_table_add_columns_request import AlterTableAddColumnsRequest
from lance_namespace_urllib3_client.models.alter_table_add_columns_response import AlterTableAddColumnsResponse
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    alter_table_add_columns_request = lance_namespace_urllib3_client.AlterTableAddColumnsRequest() # AlterTableAddColumnsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Add new columns to table schema
        api_response = api_instance.alter_table_add_columns(id, alter_table_add_columns_request, delimiter=delimiter)
        print("The response of DataApi->alter_table_add_columns:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->alter_table_add_columns: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **alter_table_add_columns_request** | [**AlterTableAddColumnsRequest**](AlterTableAddColumnsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**AlterTableAddColumnsResponse**](AlterTableAddColumnsResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Add columns operation result |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **analyze_table_query_plan**
> str analyze_table_query_plan(id, analyze_table_query_plan_request, delimiter=delimiter)

Analyze query execution plan

Analyze the query execution plan for a query against table `id`.
Returns detailed statistics and analysis of the query execution plan.

REST NAMESPACE ONLY
REST namespace returns the response as a plain string
instead of the `AnalyzeTableQueryPlanResponse` JSON object.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.analyze_table_query_plan_request import AnalyzeTableQueryPlanRequest
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    analyze_table_query_plan_request = lance_namespace_urllib3_client.AnalyzeTableQueryPlanRequest() # AnalyzeTableQueryPlanRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Analyze query execution plan
        api_response = api_instance.analyze_table_query_plan(id, analyze_table_query_plan_request, delimiter=delimiter)
        print("The response of DataApi->analyze_table_query_plan:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->analyze_table_query_plan: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **analyze_table_query_plan_request** | [**AnalyzeTableQueryPlanRequest**](AnalyzeTableQueryPlanRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

**str**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Query execution plan analysis |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **count_table_rows**
> int count_table_rows(id, count_table_rows_request, delimiter=delimiter)

Count rows in a table

Count the number of rows in table `id`

REST NAMESPACE ONLY
REST namespace returns the response as a plain integer
instead of the `CountTableRowsResponse` JSON object.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.count_table_rows_request import CountTableRowsRequest
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    count_table_rows_request = lance_namespace_urllib3_client.CountTableRowsRequest() # CountTableRowsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Count rows in a table
        api_response = api_instance.count_table_rows(id, count_table_rows_request, delimiter=delimiter)
        print("The response of DataApi->count_table_rows:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->count_table_rows: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **count_table_rows_request** | [**CountTableRowsRequest**](CountTableRowsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

**int**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Result of counting rows in a table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_table**
> CreateTableResponse create_table(id, body, delimiter=delimiter, mode=mode)

Create a table with the given name

Create table `id` in the namespace with the given data in Arrow IPC stream.

The schema of the Arrow IPC stream is used as the table schema.
If the stream is empty, the API creates a new empty table.

REST NAMESPACE ONLY
REST namespace uses Arrow IPC stream as the request body.
It passes in the `CreateTableRequest` information in the following way:
- `id`: pass through path parameter of the same name
- `mode`: pass through query parameter of the same name


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_table_response import CreateTableResponse
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC data
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    mode = 'mode_example' # str |  (optional)

    try:
        # Create a table with the given name
        api_response = api_instance.create_table(id, body, delimiter=delimiter, mode=mode)
        print("The response of DataApi->create_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->create_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC data | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **mode** | **str**|  | [optional] 

### Return type

[**CreateTableResponse**](CreateTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/vnd.apache.arrow.stream
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table properties result when creating a table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_from_table**
> DeleteFromTableResponse delete_from_table(id, delete_from_table_request, delimiter=delimiter)

Delete rows from a table

Delete rows from table `id`.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.delete_from_table_request import DeleteFromTableRequest
from lance_namespace_urllib3_client.models.delete_from_table_response import DeleteFromTableResponse
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    delete_from_table_request = lance_namespace_urllib3_client.DeleteFromTableRequest() # DeleteFromTableRequest | Delete request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Delete rows from a table
        api_response = api_instance.delete_from_table(id, delete_from_table_request, delimiter=delimiter)
        print("The response of DataApi->delete_from_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->delete_from_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **delete_from_table_request** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md)| Delete request | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DeleteFromTableResponse**](DeleteFromTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Delete successful |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **explain_table_query_plan**
> str explain_table_query_plan(id, explain_table_query_plan_request, delimiter=delimiter)

Get query execution plan explanation

Get the query execution plan for a query against table `id`.
Returns a human-readable explanation of how the query will be executed.

REST NAMESPACE ONLY
REST namespace returns the response as a plain string
instead of the `ExplainTableQueryPlanResponse` JSON object.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.explain_table_query_plan_request import ExplainTableQueryPlanRequest
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    explain_table_query_plan_request = lance_namespace_urllib3_client.ExplainTableQueryPlanRequest() # ExplainTableQueryPlanRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Get query execution plan explanation
        api_response = api_instance.explain_table_query_plan(id, explain_table_query_plan_request, delimiter=delimiter)
        print("The response of DataApi->explain_table_query_plan:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->explain_table_query_plan: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **explain_table_query_plan_request** | [**ExplainTableQueryPlanRequest**](ExplainTableQueryPlanRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

**str**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Query execution plan explanation |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **insert_into_table**
> InsertIntoTableResponse insert_into_table(id, body, delimiter=delimiter, mode=mode)

Insert records into a table

Insert new records into table `id`.

REST NAMESPACE ONLY
REST namespace uses Arrow IPC stream as the request body.
It passes in the `InsertIntoTableRequest` information in the following way:
- `id`: pass through path parameter of the same name
- `mode`: pass through query parameter of the same name


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.insert_into_table_response import InsertIntoTableResponse
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC stream containing the records to insert
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    mode = 'append' # str | How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it  (optional) (default to 'append')

    try:
        # Insert records into a table
        api_response = api_instance.insert_into_table(id, body, delimiter=delimiter, mode=mode)
        print("The response of DataApi->insert_into_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->insert_into_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC stream containing the records to insert | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **mode** | **str**| How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it  | [optional] [default to &#39;append&#39;]

### Return type

[**InsertIntoTableResponse**](InsertIntoTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/vnd.apache.arrow.stream
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Result of inserting records into a table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **merge_insert_into_table**
> MergeInsertIntoTableResponse merge_insert_into_table(id, on, body, delimiter=delimiter, when_matched_update_all=when_matched_update_all, when_matched_update_all_filt=when_matched_update_all_filt, when_not_matched_insert_all=when_not_matched_insert_all, when_not_matched_by_source_delete=when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt=when_not_matched_by_source_delete_filt, timeout=timeout, use_index=use_index)

Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on table `id`.
This operation updates existing rows
based on a matching column and inserts new rows that don't match.
It returns the number of rows inserted and updated.

REST NAMESPACE ONLY
REST namespace uses Arrow IPC stream as the request body.
It passes in the `MergeInsertIntoTableRequest` information in the following way:
- `id`: pass through path parameter of the same name
- `on`: pass through query parameter of the same name
- `when_matched_update_all`: pass through query parameter of the same name
- `when_matched_update_all_filt`: pass through query parameter of the same name
- `when_not_matched_insert_all`: pass through query parameter of the same name
- `when_not_matched_by_source_delete`: pass through query parameter of the same name
- `when_not_matched_by_source_delete_filt`: pass through query parameter of the same name


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.merge_insert_into_table_response import MergeInsertIntoTableResponse
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    on = 'on_example' # str | Column name to use for matching rows (required)
    body = None # bytearray | Arrow IPC stream containing the records to merge
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    when_matched_update_all = False # bool | Update all columns when rows match (optional) (default to False)
    when_matched_update_all_filt = 'when_matched_update_all_filt_example' # str | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true (optional)
    when_not_matched_insert_all = False # bool | Insert all columns when rows don't match (optional) (default to False)
    when_not_matched_by_source_delete = False # bool | Delete all rows from target table that don't match a row in the source table (optional) (default to False)
    when_not_matched_by_source_delete_filt = 'when_not_matched_by_source_delete_filt_example' # str | Delete rows from the target table if there is no match AND the SQL expression evaluates to true (optional)
    timeout = 'timeout_example' # str | Timeout for the operation (e.g., \"30s\", \"5m\") (optional)
    use_index = False # bool | Whether to use index for matching rows (optional) (default to False)

    try:
        # Merge insert (upsert) records into a table
        api_response = api_instance.merge_insert_into_table(id, on, body, delimiter=delimiter, when_matched_update_all=when_matched_update_all, when_matched_update_all_filt=when_matched_update_all_filt, when_not_matched_insert_all=when_not_matched_insert_all, when_not_matched_by_source_delete=when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt=when_not_matched_by_source_delete_filt, timeout=timeout, use_index=use_index)
        print("The response of DataApi->merge_insert_into_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->merge_insert_into_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **on** | **str**| Column name to use for matching rows (required) | 
 **body** | **bytearray**| Arrow IPC stream containing the records to merge | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **when_matched_update_all** | **bool**| Update all columns when rows match | [optional] [default to False]
 **when_matched_update_all_filt** | **str**| The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true | [optional] 
 **when_not_matched_insert_all** | **bool**| Insert all columns when rows don&#39;t match | [optional] [default to False]
 **when_not_matched_by_source_delete** | **bool**| Delete all rows from target table that don&#39;t match a row in the source table | [optional] [default to False]
 **when_not_matched_by_source_delete_filt** | **str**| Delete rows from the target table if there is no match AND the SQL expression evaluates to true | [optional] 
 **timeout** | **str**| Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) | [optional] 
 **use_index** | **bool**| Whether to use index for matching rows | [optional] [default to False]

### Return type

[**MergeInsertIntoTableResponse**](MergeInsertIntoTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/vnd.apache.arrow.stream
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Result of merge insert operation |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **query_table**
> bytearray query_table(id, query_table_request, delimiter=delimiter)

Query a table

Query table `id` with vector search, full text search and optional SQL filtering.
Returns results in Arrow IPC file or stream format.

REST NAMESPACE ONLY
REST namespace returns the response as Arrow IPC file binary data
instead of the `QueryTableResponse` JSON object.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.query_table_request import QueryTableRequest
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    query_table_request = lance_namespace_urllib3_client.QueryTableRequest() # QueryTableRequest | Query request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Query a table
        api_response = api_instance.query_table(id, query_table_request, delimiter=delimiter)
        print("The response of DataApi->query_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->query_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **query_table_request** | [**QueryTableRequest**](QueryTableRequest.md)| Query request | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

**bytearray**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/vnd.apache.arrow.file, application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Query results in Arrow IPC file format |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update_table**
> UpdateTableResponse update_table(id, update_table_request, delimiter=delimiter)

Update rows in a table

Update existing rows in table `id`.


### Example

* OAuth Authentication (OAuth2):
* Api Key Authentication (ApiKeyAuth):
* Bearer Authentication (BearerAuth):

```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.update_table_request import UpdateTableRequest
from lance_namespace_urllib3_client.models.update_table_response import UpdateTableResponse
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    update_table_request = lance_namespace_urllib3_client.UpdateTableRequest() # UpdateTableRequest | Update request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Update rows in a table
        api_response = api_instance.update_table(id, update_table_request, delimiter=delimiter)
        print("The response of DataApi->update_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->update_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **update_table_request** | [**UpdateTableRequest**](UpdateTableRequest.md)| Update request | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**UpdateTableResponse**](UpdateTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Update successful |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

