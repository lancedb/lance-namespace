# lance_namespace_urllib3_client.DataApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**count_table_rows**](DataApi.md#count_table_rows) | **POST** /v1/table/{id}/count_rows | Count rows in a table
[**delete_from_table**](DataApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**insert_into_table**](DataApi.md#insert_into_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**merge_insert_into_table**](DataApi.md#merge_insert_into_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](DataApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**update_table**](DataApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table


# **count_table_rows**
> int count_table_rows(id, count_table_rows_request, delimiter=delimiter)

Count rows in a table

Count the number of rows in a table.


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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    count_table_rows_request = lance_namespace_urllib3_client.CountTableRowsRequest() # CountTableRowsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

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
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **count_table_rows_request** | [**CountTableRowsRequest**](CountTableRowsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

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

# **delete_from_table**
> DeleteFromTableResponse delete_from_table(id, delete_from_table_request)

Delete rows from a table

Delete rows from a table based on a SQL predicate.
Returns the number of rows that were deleted.


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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    delete_from_table_request = lance_namespace_urllib3_client.DeleteFromTableRequest() # DeleteFromTableRequest | Delete request

    try:
        # Delete rows from a table
        api_response = api_instance.delete_from_table(id, delete_from_table_request)
        print("The response of DataApi->delete_from_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->delete_from_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **delete_from_table_request** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md)| Delete request | 

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

# **insert_into_table**
> InsertIntoTableResponse insert_into_table(id, body, mode=mode)

Insert records into a table

Insert new records into an existing table using Arrow IPC format.
Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers).


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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC data
    mode = append # str | Insert mode: \"append\" (default) or \"overwrite\" (optional) (default to append)

    try:
        # Insert records into a table
        api_response = api_instance.insert_into_table(id, body, mode=mode)
        print("The response of DataApi->insert_into_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->insert_into_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC data | 
 **mode** | **str**| Insert mode: \&quot;append\&quot; (default) or \&quot;overwrite\&quot; | [optional] [default to append]

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

# **merge_insert_into_table**
> MergeInsertIntoTableResponse merge_insert_into_table(id, on, body, when_matched_update_all=when_matched_update_all, when_matched_update_all_filt=when_matched_update_all_filt, when_not_matched_insert_all=when_not_matched_insert_all, when_not_matched_by_source_delete=when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt=when_not_matched_by_source_delete_filt)

Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on a table. This operation updates existing rows
based on a matching column and inserts new rows that don't match.
Returns the number of rows inserted and updated.


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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    on = 'on_example' # str | Column name to use for matching rows (required)
    body = None # bytearray | Arrow IPC data containing the records to merge
    when_matched_update_all = False # bool | Update all columns when rows match (optional) (default to False)
    when_matched_update_all_filt = 'when_matched_update_all_filt_example' # str | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true (optional)
    when_not_matched_insert_all = False # bool | Insert all columns when rows don't match (optional) (default to False)
    when_not_matched_by_source_delete = False # bool | Delete all rows from target table that don't match a row in the source table (optional) (default to False)
    when_not_matched_by_source_delete_filt = 'when_not_matched_by_source_delete_filt_example' # str | Delete rows from the target table if there is no match AND the SQL expression evaluates to true (optional)

    try:
        # Merge insert (upsert) records into a table
        api_response = api_instance.merge_insert_into_table(id, on, body, when_matched_update_all=when_matched_update_all, when_matched_update_all_filt=when_matched_update_all_filt, when_not_matched_insert_all=when_not_matched_insert_all, when_not_matched_by_source_delete=when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt=when_not_matched_by_source_delete_filt)
        print("The response of DataApi->merge_insert_into_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->merge_insert_into_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **on** | **str**| Column name to use for matching rows (required) | 
 **body** | **bytearray**| Arrow IPC data containing the records to merge | 
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
> bytearray query_table(id, query_table_request)

Query a table

Query a table with vector search, full text search and optional SQL filtering.
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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    query_table_request = lance_namespace_urllib3_client.QueryTableRequest() # QueryTableRequest | Query request

    try:
        # Query a table
        api_response = api_instance.query_table(id, query_table_request)
        print("The response of DataApi->query_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->query_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **query_table_request** | [**QueryTableRequest**](QueryTableRequest.md)| Query request | 

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

# **update_table**
> UpdateTableResponse update_table(id, update_table_request)

Update rows in a table

Update existing rows in a table using SQL expressions.
Each update consists of a column name and an SQL expression that will be
evaluated against the current row's value. Optionally, a predicate can be
provided to filter which rows to update.


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
    api_instance = lance_namespace_urllib3_client.DataApi(api_client)
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    update_table_request = lance_namespace_urllib3_client.UpdateTableRequest() # UpdateTableRequest | Update request

    try:
        # Update rows in a table
        api_response = api_instance.update_table(id, update_table_request)
        print("The response of DataApi->update_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling DataApi->update_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **update_table_request** | [**UpdateTableRequest**](UpdateTableRequest.md)| Update request | 

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

