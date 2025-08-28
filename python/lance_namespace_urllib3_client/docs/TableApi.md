# lance_namespace_urllib3_client.TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_table_add_columns**](TableApi.md#alter_table_add_columns) | **POST** /v1/table/{id}/add_columns | Add new columns to table schema
[**alter_table_alter_columns**](TableApi.md#alter_table_alter_columns) | **POST** /v1/table/{id}/alter_columns | Modify existing columns
[**alter_table_drop_columns**](TableApi.md#alter_table_drop_columns) | **POST** /v1/table/{id}/drop_columns | Remove columns from table
[**analyze_table_query_plan**](TableApi.md#analyze_table_query_plan) | **POST** /v1/table/{id}/analyze_plan | Analyze query execution plan
[**count_table_rows**](TableApi.md#count_table_rows) | **POST** /v1/table/{id}/count_rows | Count rows in a table
[**create_empty_table**](TableApi.md#create_empty_table) | **POST** /v1/table/{id}/create-empty | Create an empty table
[**create_table**](TableApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**create_table_index**](TableApi.md#create_table_index) | **POST** /v1/table/{id}/create_index | Create an index on a table
[**create_table_tag**](TableApi.md#create_table_tag) | **POST** /v1/table/{id}/tags/create | Create a new tag
[**delete_from_table**](TableApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**delete_table_tag**](TableApi.md#delete_table_tag) | **POST** /v1/table/{id}/tags/delete | Delete a tag
[**deregister_table**](TableApi.md#deregister_table) | **POST** /v1/table/{id}/deregister | Deregister a table
[**describe_table**](TableApi.md#describe_table) | **POST** /v1/table/{id}/describe | Describe information of a table
[**describe_table_index_stats**](TableApi.md#describe_table_index_stats) | **POST** /v1/table/{id}/index/{index_name}/stats | Get table index statistics
[**drop_table**](TableApi.md#drop_table) | **POST** /v1/table/{id}/drop | Drop a table
[**drop_table_index**](TableApi.md#drop_table_index) | **POST** /v1/table/{id}/index/{index_name}/drop | Drop a specific index
[**explain_table_query_plan**](TableApi.md#explain_table_query_plan) | **POST** /v1/table/{id}/explain_plan | Get query execution plan explanation
[**get_table_stats**](TableApi.md#get_table_stats) | **POST** /v1/table/{id}/stats | Get table statistics
[**get_table_tag_version**](TableApi.md#get_table_tag_version) | **POST** /v1/table/{id}/tags/version | Get version for a specific tag
[**insert_into_table**](TableApi.md#insert_into_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**list_table_indices**](TableApi.md#list_table_indices) | **POST** /v1/table/{id}/index/list | List indexes on a table
[**list_table_tags**](TableApi.md#list_table_tags) | **GET** /v1/table/{id}/tags/list | List all tags for a table
[**list_table_versions**](TableApi.md#list_table_versions) | **POST** /v1/table/{id}/version/list | List all versions of a table
[**list_tables**](TableApi.md#list_tables) | **GET** /v1/namespace/{id}/table/list | List tables in a namespace
[**merge_insert_into_table**](TableApi.md#merge_insert_into_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](TableApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**register_table**](TableApi.md#register_table) | **POST** /v1/table/{id}/register | Register a table to a namespace
[**restore_table**](TableApi.md#restore_table) | **POST** /v1/table/{id}/restore | Restore table to a specific version
[**table_exists**](TableApi.md#table_exists) | **POST** /v1/table/{id}/exists | Check if a table exists
[**update_table**](TableApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table
[**update_table_tag**](TableApi.md#update_table_tag) | **POST** /v1/table/{id}/tags/update | Update a tag to point to a different version


# **alter_table_add_columns**
> AlterTableAddColumnsResponse alter_table_add_columns(id, alter_table_add_columns_request, delimiter=delimiter)

Add new columns to table schema

Add new columns to table `id` using SQL expressions or default values.


### Example


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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    alter_table_add_columns_request = lance_namespace_urllib3_client.AlterTableAddColumnsRequest() # AlterTableAddColumnsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Add new columns to table schema
        api_response = api_instance.alter_table_add_columns(id, alter_table_add_columns_request, delimiter=delimiter)
        print("The response of TableApi->alter_table_add_columns:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->alter_table_add_columns: %s\n" % e)
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

No authorization required

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

# **alter_table_alter_columns**
> AlterTableAlterColumnsResponse alter_table_alter_columns(id, alter_table_alter_columns_request, delimiter=delimiter)

Modify existing columns

Modify existing columns in table `id`, such as renaming or changing data types.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.alter_table_alter_columns_request import AlterTableAlterColumnsRequest
from lance_namespace_urllib3_client.models.alter_table_alter_columns_response import AlterTableAlterColumnsResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    alter_table_alter_columns_request = lance_namespace_urllib3_client.AlterTableAlterColumnsRequest() # AlterTableAlterColumnsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Modify existing columns
        api_response = api_instance.alter_table_alter_columns(id, alter_table_alter_columns_request, delimiter=delimiter)
        print("The response of TableApi->alter_table_alter_columns:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->alter_table_alter_columns: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **alter_table_alter_columns_request** | [**AlterTableAlterColumnsRequest**](AlterTableAlterColumnsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**AlterTableAlterColumnsResponse**](AlterTableAlterColumnsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Alter columns operation result |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **alter_table_drop_columns**
> AlterTableDropColumnsResponse alter_table_drop_columns(id, alter_table_drop_columns_request, delimiter=delimiter)

Remove columns from table

Remove specified columns from table `id`.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.alter_table_drop_columns_request import AlterTableDropColumnsRequest
from lance_namespace_urllib3_client.models.alter_table_drop_columns_response import AlterTableDropColumnsResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    alter_table_drop_columns_request = lance_namespace_urllib3_client.AlterTableDropColumnsRequest() # AlterTableDropColumnsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Remove columns from table
        api_response = api_instance.alter_table_drop_columns(id, alter_table_drop_columns_request, delimiter=delimiter)
        print("The response of TableApi->alter_table_drop_columns:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->alter_table_drop_columns: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **alter_table_drop_columns_request** | [**AlterTableDropColumnsRequest**](AlterTableDropColumnsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**AlterTableDropColumnsResponse**](AlterTableDropColumnsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Drop columns operation result |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **analyze_table_query_plan**
> AnalyzeTableQueryPlanResponse analyze_table_query_plan(id, analyze_table_query_plan_request, delimiter=delimiter)

Analyze query execution plan

Analyze the query execution plan for a query against table `id`.
Returns detailed statistics and analysis of the query execution plan.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.analyze_table_query_plan_request import AnalyzeTableQueryPlanRequest
from lance_namespace_urllib3_client.models.analyze_table_query_plan_response import AnalyzeTableQueryPlanResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    analyze_table_query_plan_request = lance_namespace_urllib3_client.AnalyzeTableQueryPlanRequest() # AnalyzeTableQueryPlanRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Analyze query execution plan
        api_response = api_instance.analyze_table_query_plan(id, analyze_table_query_plan_request, delimiter=delimiter)
        print("The response of TableApi->analyze_table_query_plan:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->analyze_table_query_plan: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **analyze_table_query_plan_request** | [**AnalyzeTableQueryPlanRequest**](AnalyzeTableQueryPlanRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**AnalyzeTableQueryPlanResponse**](AnalyzeTableQueryPlanResponse.md)

### Authorization

No authorization required

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


### Example


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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    count_table_rows_request = lance_namespace_urllib3_client.CountTableRowsRequest() # CountTableRowsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Count rows in a table
        api_response = api_instance.count_table_rows(id, count_table_rows_request, delimiter=delimiter)
        print("The response of TableApi->count_table_rows:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->count_table_rows: %s\n" % e)
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

No authorization required

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

# **create_empty_table**
> CreateEmptyTableResponse create_empty_table(id, create_empty_table_request, delimiter=delimiter)

Create an empty table

Create an empty table with the given name without touching storage.
This is a metadata-only operation that records the table existence and sets up aspects like access control.

For DirectoryNamespace implementation, this creates a `.lance-reserved` file in the table directory
to mark the table's existence without creating actual Lance data files.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_empty_table_request import CreateEmptyTableRequest
from lance_namespace_urllib3_client.models.create_empty_table_response import CreateEmptyTableResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    create_empty_table_request = lance_namespace_urllib3_client.CreateEmptyTableRequest() # CreateEmptyTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Create an empty table
        api_response = api_instance.create_empty_table(id, create_empty_table_request, delimiter=delimiter)
        print("The response of TableApi->create_empty_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->create_empty_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **create_empty_table_request** | [**CreateEmptyTableRequest**](CreateEmptyTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**CreateEmptyTableResponse**](CreateEmptyTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table properties result when creating an empty table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_table**
> CreateTableResponse create_table(id, body, delimiter=delimiter, mode=mode, x_lance_table_location=x_lance_table_location, x_lance_table_properties=x_lance_table_properties)

Create a table with the given name

Create table `id` in the namespace with the given data in Arrow IPC stream.

The schema of the Arrow IPC stream is used as the table schema.    
If the stream is empty, the API creates a new empty table.

REST NAMESPACE ONLY
REST namespace uses Arrow IPC stream as the request body.
It passes in the `CreateTableRequest` information in the following way:
- `id`: pass through path parameter of the same name
- `mode`: pass through query parameter of the same name
- `location`: pass through header `x-lance-table-location`
- `properties`: pass through header `x-lance-table-properties`


### Example


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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC data
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    mode = 'mode_example' # str |  (optional)
    x_lance_table_location = 'x_lance_table_location_example' # str | URI pointing to root location to create the table at (optional)
    x_lance_table_properties = 'x_lance_table_properties_example' # str | JSON-encoded string map (e.g. { \"owner\": \"jack\" })  (optional)

    try:
        # Create a table with the given name
        api_response = api_instance.create_table(id, body, delimiter=delimiter, mode=mode, x_lance_table_location=x_lance_table_location, x_lance_table_properties=x_lance_table_properties)
        print("The response of TableApi->create_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->create_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC data | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **mode** | **str**|  | [optional] 
 **x_lance_table_location** | **str**| URI pointing to root location to create the table at | [optional] 
 **x_lance_table_properties** | **str**| JSON-encoded string map (e.g. { \&quot;owner\&quot;: \&quot;jack\&quot; })  | [optional] 

### Return type

[**CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

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

# **create_table_index**
> CreateTableIndexResponse create_table_index(id, create_table_index_request, delimiter=delimiter)

Create an index on a table

Create an index on a table column for faster search operations.
Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE, BITMAP, FTS, etc.).
Index creation is handled asynchronously. 
Use the `ListTableIndices` and `DescribeTableIndexStats` operations to monitor index creation progress.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_table_index_request import CreateTableIndexRequest
from lance_namespace_urllib3_client.models.create_table_index_response import CreateTableIndexResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    create_table_index_request = lance_namespace_urllib3_client.CreateTableIndexRequest() # CreateTableIndexRequest | Index creation request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Create an index on a table
        api_response = api_instance.create_table_index(id, create_table_index_request, delimiter=delimiter)
        print("The response of TableApi->create_table_index:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->create_table_index: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **create_table_index_request** | [**CreateTableIndexRequest**](CreateTableIndexRequest.md)| Index creation request | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**CreateTableIndexResponse**](CreateTableIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Index created successfully |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_table_tag**
> create_table_tag(id, create_table_tag_request, delimiter=delimiter)

Create a new tag

Create a new tag for table `id` that points to a specific version.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_table_tag_request import CreateTableTagRequest
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    create_table_tag_request = lance_namespace_urllib3_client.CreateTableTagRequest() # CreateTableTagRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Create a new tag
        api_instance.create_table_tag(id, create_table_tag_request, delimiter=delimiter)
    except Exception as e:
        print("Exception when calling TableApi->create_table_tag: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **create_table_tag_request** | [**CreateTableTagRequest**](CreateTableTagRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Success, no content |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **delete_from_table**
> DeleteFromTableResponse delete_from_table(id, delete_from_table_request, delimiter=delimiter)

Delete rows from a table

Delete rows from table `id`.


### Example


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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    delete_from_table_request = lance_namespace_urllib3_client.DeleteFromTableRequest() # DeleteFromTableRequest | Delete request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Delete rows from a table
        api_response = api_instance.delete_from_table(id, delete_from_table_request, delimiter=delimiter)
        print("The response of TableApi->delete_from_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->delete_from_table: %s\n" % e)
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

No authorization required

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

# **delete_table_tag**
> delete_table_tag(id, delete_table_tag_request, delimiter=delimiter)

Delete a tag

Delete an existing tag from table `id`.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.delete_table_tag_request import DeleteTableTagRequest
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    delete_table_tag_request = lance_namespace_urllib3_client.DeleteTableTagRequest() # DeleteTableTagRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Delete a tag
        api_instance.delete_table_tag(id, delete_table_tag_request, delimiter=delimiter)
    except Exception as e:
        print("Exception when calling TableApi->delete_table_tag: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **delete_table_tag_request** | [**DeleteTableTagRequest**](DeleteTableTagRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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

# **deregister_table**
> DeregisterTableResponse deregister_table(id, deregister_table_request, delimiter=delimiter)

Deregister a table

Deregister table `id` from its namespace.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.deregister_table_request import DeregisterTableRequest
from lance_namespace_urllib3_client.models.deregister_table_response import DeregisterTableResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    deregister_table_request = lance_namespace_urllib3_client.DeregisterTableRequest() # DeregisterTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Deregister a table
        api_response = api_instance.deregister_table(id, deregister_table_request, delimiter=delimiter)
        print("The response of TableApi->deregister_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->deregister_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **deregister_table_request** | [**DeregisterTableRequest**](DeregisterTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DeregisterTableResponse**](DeregisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Response of DeregisterTable |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **describe_table**
> DescribeTableResponse describe_table(id, describe_table_request, delimiter=delimiter)

Describe information of a table

Describe the detailed information for table `id`.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.describe_table_request import DescribeTableRequest
from lance_namespace_urllib3_client.models.describe_table_response import DescribeTableResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    describe_table_request = lance_namespace_urllib3_client.DescribeTableRequest() # DescribeTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Describe information of a table
        api_response = api_instance.describe_table(id, describe_table_request, delimiter=delimiter)
        print("The response of TableApi->describe_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->describe_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **describe_table_request** | [**DescribeTableRequest**](DescribeTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DescribeTableResponse**](DescribeTableResponse.md)

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
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **describe_table_index_stats**
> DescribeTableIndexStatsResponse describe_table_index_stats(id, index_name, describe_table_index_stats_request, delimiter=delimiter)

Get table index statistics

Get statistics for a specific index on a table. Returns information about
the index type, distance type (for vector indices), and row counts.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.describe_table_index_stats_request import DescribeTableIndexStatsRequest
from lance_namespace_urllib3_client.models.describe_table_index_stats_response import DescribeTableIndexStatsResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    index_name = 'index_name_example' # str | Name of the index to get stats for
    describe_table_index_stats_request = lance_namespace_urllib3_client.DescribeTableIndexStatsRequest() # DescribeTableIndexStatsRequest | Index stats request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Get table index statistics
        api_response = api_instance.describe_table_index_stats(id, index_name, describe_table_index_stats_request, delimiter=delimiter)
        print("The response of TableApi->describe_table_index_stats:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->describe_table_index_stats: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **index_name** | **str**| Name of the index to get stats for | 
 **describe_table_index_stats_request** | [**DescribeTableIndexStatsRequest**](DescribeTableIndexStatsRequest.md)| Index stats request | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DescribeTableIndexStatsResponse**](DescribeTableIndexStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Index statistics |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **drop_table**
> DropTableResponse drop_table(id, drop_table_request, delimiter=delimiter)

Drop a table

Drop table `id` and delete its data.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.drop_table_request import DropTableRequest
from lance_namespace_urllib3_client.models.drop_table_response import DropTableResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    drop_table_request = lance_namespace_urllib3_client.DropTableRequest() # DropTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Drop a table
        api_response = api_instance.drop_table(id, drop_table_request, delimiter=delimiter)
        print("The response of TableApi->drop_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->drop_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **drop_table_request** | [**DropTableRequest**](DropTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DropTableResponse**](DropTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Response of DropTable |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **drop_table_index**
> DropTableIndexResponse drop_table_index(id, index_name, drop_table_index_request, delimiter=delimiter)

Drop a specific index

Drop the specified index from table `id`.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.drop_table_index_request import DropTableIndexRequest
from lance_namespace_urllib3_client.models.drop_table_index_response import DropTableIndexResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    index_name = 'index_name_example' # str | Name of the index to drop
    drop_table_index_request = lance_namespace_urllib3_client.DropTableIndexRequest() # DropTableIndexRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Drop a specific index
        api_response = api_instance.drop_table_index(id, index_name, drop_table_index_request, delimiter=delimiter)
        print("The response of TableApi->drop_table_index:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->drop_table_index: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **index_name** | **str**| Name of the index to drop | 
 **drop_table_index_request** | [**DropTableIndexRequest**](DropTableIndexRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**DropTableIndexResponse**](DropTableIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Index drop operation result |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **explain_table_query_plan**
> ExplainTableQueryPlanResponse explain_table_query_plan(id, explain_table_query_plan_request, delimiter=delimiter)

Get query execution plan explanation

Get the query execution plan for a query against table `id`.
Returns a human-readable explanation of how the query will be executed.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.explain_table_query_plan_request import ExplainTableQueryPlanRequest
from lance_namespace_urllib3_client.models.explain_table_query_plan_response import ExplainTableQueryPlanResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    explain_table_query_plan_request = lance_namespace_urllib3_client.ExplainTableQueryPlanRequest() # ExplainTableQueryPlanRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Get query execution plan explanation
        api_response = api_instance.explain_table_query_plan(id, explain_table_query_plan_request, delimiter=delimiter)
        print("The response of TableApi->explain_table_query_plan:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->explain_table_query_plan: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **explain_table_query_plan_request** | [**ExplainTableQueryPlanRequest**](ExplainTableQueryPlanRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**ExplainTableQueryPlanResponse**](ExplainTableQueryPlanResponse.md)

### Authorization

No authorization required

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

# **get_table_stats**
> GetTableStatsResponse get_table_stats(id, get_table_stats_request, delimiter=delimiter)

Get table statistics

Get statistics for table `id`, including row counts, data sizes, and column statistics.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.get_table_stats_request import GetTableStatsRequest
from lance_namespace_urllib3_client.models.get_table_stats_response import GetTableStatsResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    get_table_stats_request = lance_namespace_urllib3_client.GetTableStatsRequest() # GetTableStatsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Get table statistics
        api_response = api_instance.get_table_stats(id, get_table_stats_request, delimiter=delimiter)
        print("The response of TableApi->get_table_stats:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->get_table_stats: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **get_table_stats_request** | [**GetTableStatsRequest**](GetTableStatsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**GetTableStatsResponse**](GetTableStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table statistics |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **get_table_tag_version**
> GetTableTagVersionResponse get_table_tag_version(id, get_table_tag_version_request, delimiter=delimiter)

Get version for a specific tag

Get the version number that a specific tag points to for table `id`.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.get_table_tag_version_request import GetTableTagVersionRequest
from lance_namespace_urllib3_client.models.get_table_tag_version_response import GetTableTagVersionResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    get_table_tag_version_request = lance_namespace_urllib3_client.GetTableTagVersionRequest() # GetTableTagVersionRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Get version for a specific tag
        api_response = api_instance.get_table_tag_version(id, get_table_tag_version_request, delimiter=delimiter)
        print("The response of TableApi->get_table_tag_version:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->get_table_tag_version: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **get_table_tag_version_request** | [**GetTableTagVersionRequest**](GetTableTagVersionRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**GetTableTagVersionResponse**](GetTableTagVersionResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Tag version information |  -  |
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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC stream containing the records to insert
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    mode = append # str | How the insert should behave: - append (default): insert data to the existing table - overwrite: remove all data in the table and then insert data to it  (optional) (default to append)

    try:
        # Insert records into a table
        api_response = api_instance.insert_into_table(id, body, delimiter=delimiter, mode=mode)
        print("The response of TableApi->insert_into_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->insert_into_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC stream containing the records to insert | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **mode** | **str**| How the insert should behave: - append (default): insert data to the existing table - overwrite: remove all data in the table and then insert data to it  | [optional] [default to append]

### Return type

[**InsertIntoTableResponse**](InsertIntoTableResponse.md)

### Authorization

No authorization required

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

# **list_table_indices**
> ListTableIndicesResponse list_table_indices(id, list_table_indices_request, delimiter=delimiter)

List indexes on a table

List all indices created on a table. Returns information about each index
including name, columns, status, and UUID.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.list_table_indices_request import ListTableIndicesRequest
from lance_namespace_urllib3_client.models.list_table_indices_response import ListTableIndicesResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    list_table_indices_request = lance_namespace_urllib3_client.ListTableIndicesRequest() # ListTableIndicesRequest | Index list request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # List indexes on a table
        api_response = api_instance.list_table_indices(id, list_table_indices_request, delimiter=delimiter)
        print("The response of TableApi->list_table_indices:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->list_table_indices: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **list_table_indices_request** | [**ListTableIndicesRequest**](ListTableIndicesRequest.md)| Index list request | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**ListTableIndicesResponse**](ListTableIndicesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | List of indices on the table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list_table_tags**
> ListTableTagsResponse list_table_tags(id, delimiter=delimiter, page_token=page_token, limit=limit)

List all tags for a table

List all tags that have been created for table `id`.
Returns a map of tag names to their corresponding version numbers and metadata.

REST NAMESPACE ONLY
REST namespace uses GET to perform this operation without a request body.
It passes in the `ListTableTagsRequest` information in the following way:
- `id`: pass through path parameter of the same name
- `page_token`: pass through query parameter of the same name
- `limit`: pass through query parameter of the same name


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.list_table_tags_response import ListTableTagsResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    page_token = 'page_token_example' # str |  (optional)
    limit = 56 # int |  (optional)

    try:
        # List all tags for a table
        api_response = api_instance.list_table_tags(id, delimiter=delimiter, page_token=page_token, limit=limit)
        print("The response of TableApi->list_table_tags:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->list_table_tags: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **page_token** | **str**|  | [optional] 
 **limit** | **int**|  | [optional] 

### Return type

[**ListTableTagsResponse**](ListTableTagsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | List of table tags |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list_table_versions**
> ListTableVersionsResponse list_table_versions(id, list_table_versions_request, delimiter=delimiter)

List all versions of a table

List all versions (commits) of table `id` with their metadata.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.list_table_versions_request import ListTableVersionsRequest
from lance_namespace_urllib3_client.models.list_table_versions_response import ListTableVersionsResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    list_table_versions_request = lance_namespace_urllib3_client.ListTableVersionsRequest() # ListTableVersionsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # List all versions of a table
        api_response = api_instance.list_table_versions(id, list_table_versions_request, delimiter=delimiter)
        print("The response of TableApi->list_table_versions:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->list_table_versions: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **list_table_versions_request** | [**ListTableVersionsRequest**](ListTableVersionsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**ListTableVersionsResponse**](ListTableVersionsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | List of table versions |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **list_tables**
> ListTablesResponse list_tables(id, delimiter=delimiter, page_token=page_token, limit=limit)

List tables in a namespace

List all child table names of the parent namespace `id`.

REST NAMESPACE ONLY
REST namespace uses GET to perform this operation without a request body.
It passes in the `ListTablesRequest` information in the following way:
- `id`: pass through path parameter of the same name
- `page_token`: pass through query parameter of the same name
- `limit`: pass through query parameter of the same name


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.list_tables_response import ListTablesResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    page_token = 'page_token_example' # str |  (optional)
    limit = 56 # int |  (optional)

    try:
        # List tables in a namespace
        api_response = api_instance.list_tables(id, delimiter=delimiter, page_token=page_token, limit=limit)
        print("The response of TableApi->list_tables:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->list_tables: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 
 **page_token** | **str**|  | [optional] 
 **limit** | **int**|  | [optional] 

### Return type

[**ListTablesResponse**](ListTablesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | A list of tables |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **merge_insert_into_table**
> MergeInsertIntoTableResponse merge_insert_into_table(id, on, body, delimiter=delimiter, when_matched_update_all=when_matched_update_all, when_matched_update_all_filt=when_matched_update_all_filt, when_not_matched_insert_all=when_not_matched_insert_all, when_not_matched_by_source_delete=when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt=when_not_matched_by_source_delete_filt)

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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    on = 'on_example' # str | Column name to use for matching rows (required)
    body = None # bytearray | Arrow IPC stream containing the records to merge
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)
    when_matched_update_all = False # bool | Update all columns when rows match (optional) (default to False)
    when_matched_update_all_filt = 'when_matched_update_all_filt_example' # str | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true (optional)
    when_not_matched_insert_all = False # bool | Insert all columns when rows don't match (optional) (default to False)
    when_not_matched_by_source_delete = False # bool | Delete all rows from target table that don't match a row in the source table (optional) (default to False)
    when_not_matched_by_source_delete_filt = 'when_not_matched_by_source_delete_filt_example' # str | Delete rows from the target table if there is no match AND the SQL expression evaluates to true (optional)

    try:
        # Merge insert (upsert) records into a table
        api_response = api_instance.merge_insert_into_table(id, on, body, delimiter=delimiter, when_matched_update_all=when_matched_update_all, when_matched_update_all_filt=when_matched_update_all_filt, when_not_matched_insert_all=when_not_matched_insert_all, when_not_matched_by_source_delete=when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt=when_not_matched_by_source_delete_filt)
        print("The response of TableApi->merge_insert_into_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->merge_insert_into_table: %s\n" % e)
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

### Return type

[**MergeInsertIntoTableResponse**](MergeInsertIntoTableResponse.md)

### Authorization

No authorization required

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


### Example


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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    query_table_request = lance_namespace_urllib3_client.QueryTableRequest() # QueryTableRequest | Query request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Query a table
        api_response = api_instance.query_table(id, query_table_request, delimiter=delimiter)
        print("The response of TableApi->query_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->query_table: %s\n" % e)
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

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/vnd.apache.arrow.file, application/vnd.apache.arrow.stream, application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Query results in Arrow IPC file or stream format |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **register_table**
> RegisterTableResponse register_table(id, register_table_request, delimiter=delimiter)

Register a table to a namespace

Register an existing table at a given storage location as `id`.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.register_table_request import RegisterTableRequest
from lance_namespace_urllib3_client.models.register_table_response import RegisterTableResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    register_table_request = lance_namespace_urllib3_client.RegisterTableRequest() # RegisterTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Register a table to a namespace
        api_response = api_instance.register_table(id, register_table_request, delimiter=delimiter)
        print("The response of TableApi->register_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->register_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **register_table_request** | [**RegisterTableRequest**](RegisterTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**RegisterTableResponse**](RegisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table properties result when registering a table |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **restore_table**
> RestoreTableResponse restore_table(id, restore_table_request, delimiter=delimiter)

Restore table to a specific version

Restore table `id` to a specific version.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.restore_table_request import RestoreTableRequest
from lance_namespace_urllib3_client.models.restore_table_response import RestoreTableResponse
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    restore_table_request = lance_namespace_urllib3_client.RestoreTableRequest() # RestoreTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Restore table to a specific version
        api_response = api_instance.restore_table(id, restore_table_request, delimiter=delimiter)
        print("The response of TableApi->restore_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->restore_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **restore_table_request** | [**RestoreTableRequest**](RestoreTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

[**RestoreTableResponse**](RestoreTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Table restore operation result |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **table_exists**
> table_exists(id, table_exists_request, delimiter=delimiter)

Check if a table exists

Check if table `id` exists.

This operation should behave exactly like DescribeTable, 
except it does not contain a response body.

For DirectoryNamespace implementation, a table exists if either:
- The table has Lance data versions (regular table created with CreateTable)
- A `.lance-reserved` file exists in the table directory (empty table created with CreateEmptyTable)


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.table_exists_request import TableExistsRequest
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    table_exists_request = lance_namespace_urllib3_client.TableExistsRequest() # TableExistsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Check if a table exists
        api_instance.table_exists(id, table_exists_request, delimiter=delimiter)
    except Exception as e:
        print("Exception when calling TableApi->table_exists: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **table_exists_request** | [**TableExistsRequest**](TableExistsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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

# **update_table**
> UpdateTableResponse update_table(id, update_table_request, delimiter=delimiter)

Update rows in a table

Update existing rows in table `id`.


### Example


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


# Enter a context with an instance of the API client
with lance_namespace_urllib3_client.ApiClient(configuration) as api_client:
    # Create an instance of the API class
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    update_table_request = lance_namespace_urllib3_client.UpdateTableRequest() # UpdateTableRequest | Update request
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Update rows in a table
        api_response = api_instance.update_table(id, update_table_request, delimiter=delimiter)
        print("The response of TableApi->update_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->update_table: %s\n" % e)
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

No authorization required

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

# **update_table_tag**
> update_table_tag(id, update_table_tag_request, delimiter=delimiter)

Update a tag to point to a different version

Update an existing tag for table `id` to point to a different version.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.update_table_tag_request import UpdateTableTagRequest
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
    api_instance = lance_namespace_urllib3_client.TableApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
    update_table_tag_request = lance_namespace_urllib3_client.UpdateTableTagRequest() # UpdateTableTagRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  (optional)

    try:
        # Update a tag to point to a different version
        api_instance.update_table_tag(id, update_table_tag_request, delimiter=delimiter)
    except Exception as e:
        print("Exception when calling TableApi->update_table_tag: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **update_table_tag_request** | [**UpdateTableTagRequest**](UpdateTableTagRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] 

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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

