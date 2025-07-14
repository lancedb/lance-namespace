# lance_namespace_urllib3_client.TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**count_rows**](TableApi.md#count_rows) | **POST** /v1/table/{id}/count_rows | Count rows in a table
[**create_index**](TableApi.md#create_index) | **POST** /v1/table/{id}/create_index | Create an index on a table
[**create_scalar_index**](TableApi.md#create_scalar_index) | **POST** /v1/table/{id}/create_scalar_index | Create a scalar index on a table
[**create_table**](TableApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**delete_from_table**](TableApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**deregister_table**](TableApi.md#deregister_table) | **POST** /v1/table/{id}/deregister | Deregister a table from its namespace
[**describe_table**](TableApi.md#describe_table) | **POST** /v1/table/{id}/describe | Describe a table from the namespace
[**drop_table**](TableApi.md#drop_table) | **POST** /v1/table/{id}/drop | Drop a table from its namespace
[**get_index_stats**](TableApi.md#get_index_stats) | **POST** /v1/table/{id}/index/{index_name}/stats | Get index statistics
[**insert_table**](TableApi.md#insert_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**list_indices**](TableApi.md#list_indices) | **POST** /v1/table/{id}/index/list | List indices on a table
[**list_tables**](TableApi.md#list_tables) | **POST** /v1/table/{id}/list | List tables
[**merge_insert_table**](TableApi.md#merge_insert_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](TableApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**register_table**](TableApi.md#register_table) | **POST** /v1/table/{id}/register | Register a table to a namespace
[**table_exists**](TableApi.md#table_exists) | **POST** /v1/table/{id}/exists | Check if a table exists
[**update_table**](TableApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table


# **count_rows**
> int count_rows(id, count_rows_request, delimiter=delimiter)

Count rows in a table

Count the number of rows in a table. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers).


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.count_rows_request import CountRowsRequest
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    count_rows_request = lance_namespace_urllib3_client.CountRowsRequest() # CountRowsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

    try:
        # Count rows in a table
        api_response = api_instance.count_rows(id, count_rows_request, delimiter=delimiter)
        print("The response of TableApi->count_rows:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->count_rows: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **count_rows_request** | [**CountRowsRequest**](CountRowsRequest.md)|  | 
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

# **create_index**
> CreateIndexResponse create_index(id, create_index_request)

Create an index on a table

Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ) and scalar indexes.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_index_request import CreateIndexRequest
from lance_namespace_urllib3_client.models.create_index_response import CreateIndexResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    create_index_request = lance_namespace_urllib3_client.CreateIndexRequest() # CreateIndexRequest | Index creation request

    try:
        # Create an index on a table
        api_response = api_instance.create_index(id, create_index_request)
        print("The response of TableApi->create_index:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->create_index: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **create_index_request** | [**CreateIndexRequest**](CreateIndexRequest.md)| Index creation request | 

### Return type

[**CreateIndexResponse**](CreateIndexResponse.md)

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

# **create_scalar_index**
> CreateIndexResponse create_scalar_index(id, create_index_request)

Create a scalar index on a table

Create a scalar index on a table column for faster search operations. Supports scalar indexes (BTREE, BITMAP, LABEL_LIST).


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.create_index_request import CreateIndexRequest
from lance_namespace_urllib3_client.models.create_index_response import CreateIndexResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    create_index_request = lance_namespace_urllib3_client.CreateIndexRequest() # CreateIndexRequest | Scalar index creation request

    try:
        # Create a scalar index on a table
        api_response = api_instance.create_scalar_index(id, create_index_request)
        print("The response of TableApi->create_scalar_index:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->create_scalar_index: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **create_index_request** | [**CreateIndexRequest**](CreateIndexRequest.md)| Scalar index creation request | 

### Return type

[**CreateIndexResponse**](CreateIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Scalar index created successfully |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**404** | A server-side problem that means can not find the specified resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **create_table**
> CreateTableResponse create_table(id, body)

Create a table with the given name

Create a new table in the namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers).


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC data

    try:
        # Create a table with the given name
        api_response = api_instance.create_table(id, body)
        print("The response of TableApi->create_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->create_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC data | 

### Return type

[**CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-arrow-ipc
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
> DeleteFromTableResponse delete_from_table(id, delete_from_table_request)

Delete rows from a table

Delete rows from a table based on a SQL predicate. Returns the number of rows that were deleted.


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    delete_from_table_request = lance_namespace_urllib3_client.DeleteFromTableRequest() # DeleteFromTableRequest | Delete request

    try:
        # Delete rows from a table
        api_response = api_instance.delete_from_table(id, delete_from_table_request)
        print("The response of TableApi->delete_from_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->delete_from_table: %s\n" % e)
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

# **deregister_table**
> DeregisterTableResponse deregister_table(id, deregister_table_request, delimiter=delimiter)

Deregister a table from its namespace

Deregister a table from its namespace. The table content remains available in the storage.


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    deregister_table_request = lance_namespace_urllib3_client.DeregisterTableRequest() # DeregisterTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

    try:
        # Deregister a table from its namespace
        api_response = api_instance.deregister_table(id, deregister_table_request, delimiter=delimiter)
        print("The response of TableApi->deregister_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->deregister_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **deregister_table_request** | [**DeregisterTableRequest**](DeregisterTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

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

Describe a table from the namespace

Get a table's detailed information under a specified namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers).


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    describe_table_request = lance_namespace_urllib3_client.DescribeTableRequest() # DescribeTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

    try:
        # Describe a table from the namespace
        api_response = api_instance.describe_table(id, describe_table_request, delimiter=delimiter)
        print("The response of TableApi->describe_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->describe_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **describe_table_request** | [**DescribeTableRequest**](DescribeTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

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

# **drop_table**
> DropTableResponse drop_table(id, drop_table_request, delimiter=delimiter)

Drop a table from its namespace

Drop a table from its namespace and delete its data. If the table and its data can be immediately deleted, return information of the deleted table. Otherwise, return a transaction ID that client can use to track deletion progress.


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    drop_table_request = lance_namespace_urllib3_client.DropTableRequest() # DropTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

    try:
        # Drop a table from its namespace
        api_response = api_instance.drop_table(id, drop_table_request, delimiter=delimiter)
        print("The response of TableApi->drop_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->drop_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **drop_table_request** | [**DropTableRequest**](DropTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

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

# **get_index_stats**
> IndexStatsResponse get_index_stats(id, index_name, index_stats_request)

Get index statistics

Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.index_stats_request import IndexStatsRequest
from lance_namespace_urllib3_client.models.index_stats_response import IndexStatsResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    index_name = 'index_name_example' # str | Name of the index to get stats for
    index_stats_request = lance_namespace_urllib3_client.IndexStatsRequest() # IndexStatsRequest | Index stats request

    try:
        # Get index statistics
        api_response = api_instance.get_index_stats(id, index_name, index_stats_request)
        print("The response of TableApi->get_index_stats:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->get_index_stats: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **index_name** | **str**| Name of the index to get stats for | 
 **index_stats_request** | [**IndexStatsRequest**](IndexStatsRequest.md)| Index stats request | 

### Return type

[**IndexStatsResponse**](IndexStatsResponse.md)

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

# **insert_table**
> InsertTableResponse insert_table(id, body, mode=mode)

Insert records into a table

Insert new records into an existing table using Arrow IPC format. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers).


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.insert_table_response import InsertTableResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    body = None # bytearray | Arrow IPC data
    mode = append # str | Insert mode: \"append\" (default) or \"overwrite\" (optional) (default to append)

    try:
        # Insert records into a table
        api_response = api_instance.insert_table(id, body, mode=mode)
        print("The response of TableApi->insert_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->insert_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **body** | **bytearray**| Arrow IPC data | 
 **mode** | **str**| Insert mode: \&quot;append\&quot; (default) or \&quot;overwrite\&quot; | [optional] [default to append]

### Return type

[**InsertTableResponse**](InsertTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-arrow-ipc
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

# **list_indices**
> IndexListResponse list_indices(id, index_list_request)

List indices on a table

List all indices created on a table. Returns information about each index including name, columns, status, and UUID.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.index_list_request import IndexListRequest
from lance_namespace_urllib3_client.models.index_list_response import IndexListResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    index_list_request = lance_namespace_urllib3_client.IndexListRequest() # IndexListRequest | Index list request

    try:
        # List indices on a table
        api_response = api_instance.list_indices(id, index_list_request)
        print("The response of TableApi->list_indices:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->list_indices: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **index_list_request** | [**IndexListRequest**](IndexListRequest.md)| Index list request | 

### Return type

[**IndexListResponse**](IndexListResponse.md)

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

# **list_tables**
> ListTablesResponse list_tables(id, list_tables_request, delimiter=delimiter)

List tables

List all child table names of the root namespace or a given parent namespace.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.list_tables_request import ListTablesRequest
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    list_tables_request = lance_namespace_urllib3_client.ListTablesRequest() # ListTablesRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

    try:
        # List tables
        api_response = api_instance.list_tables(id, list_tables_request, delimiter=delimiter)
        print("The response of TableApi->list_tables:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->list_tables: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **list_tables_request** | [**ListTablesRequest**](ListTablesRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

### Return type

[**ListTablesResponse**](ListTablesResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | A list of tables |  -  |
**400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
**401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
**403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **merge_insert_table**
> MergeInsertTableResponse merge_insert_table(id, on, body, when_matched_update_all=when_matched_update_all, when_not_matched_insert_all=when_not_matched_insert_all)

Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on a table. This operation updates existing rows based on a matching column and inserts new rows that don't match. Returns the number of rows inserted and updated.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.merge_insert_table_response import MergeInsertTableResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    on = 'on_example' # str | Column name to use for matching rows (required)
    body = None # bytearray | Arrow IPC data containing the records to merge
    when_matched_update_all = False # bool | Update all columns when rows match (optional) (default to False)
    when_not_matched_insert_all = False # bool | Insert all columns when rows don't match (optional) (default to False)

    try:
        # Merge insert (upsert) records into a table
        api_response = api_instance.merge_insert_table(id, on, body, when_matched_update_all=when_matched_update_all, when_not_matched_insert_all=when_not_matched_insert_all)
        print("The response of TableApi->merge_insert_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->merge_insert_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **on** | **str**| Column name to use for matching rows (required) | 
 **body** | **bytearray**| Arrow IPC data containing the records to merge | 
 **when_matched_update_all** | **bool**| Update all columns when rows match | [optional] [default to False]
 **when_not_matched_insert_all** | **bool**| Insert all columns when rows don&#39;t match | [optional] [default to False]

### Return type

[**MergeInsertTableResponse**](MergeInsertTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-arrow-ipc
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
> bytearray query_table(id, query_request)

Query a table

Query a table with vector search and optional filtering. Returns results in Arrow IPC stream format.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.query_request import QueryRequest
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    query_request = lance_namespace_urllib3_client.QueryRequest() # QueryRequest | Query request

    try:
        # Query a table
        api_response = api_instance.query_table(id, query_request)
        print("The response of TableApi->query_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->query_table: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **query_request** | [**QueryRequest**](QueryRequest.md)| Query request | 

### Return type

**bytearray**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/vnd.apache.arrow.stream, application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Query results in Arrow IPC stream format |  -  |
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

Register an existing table at a given storage location to a namespace.


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    register_table_request = lance_namespace_urllib3_client.RegisterTableRequest() # RegisterTableRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

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
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **register_table_request** | [**RegisterTableRequest**](RegisterTableRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

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
**406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
**409** | The request conflicts with the current state of the target resource. |  -  |
**503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
**5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **table_exists**
> TableExistsResponse table_exists(id, table_exists_request, delimiter=delimiter)

Check if a table exists

Check if a table exists.
This API should behave exactly like the GetTable API, except it does not contain a body.


### Example


```python
import lance_namespace_urllib3_client
from lance_namespace_urllib3_client.models.table_exists_request import TableExistsRequest
from lance_namespace_urllib3_client.models.table_exists_response import TableExistsResponse
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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    table_exists_request = lance_namespace_urllib3_client.TableExistsRequest() # TableExistsRequest | 
    delimiter = 'delimiter_example' # str | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  (optional)

    try:
        # Check if a table exists
        api_response = api_instance.table_exists(id, table_exists_request, delimiter=delimiter)
        print("The response of TableApi->table_exists:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->table_exists: %s\n" % e)
```



### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | 
 **table_exists_request** | [**TableExistsRequest**](TableExistsRequest.md)|  | 
 **delimiter** | **str**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] 

### Return type

[**TableExistsResponse**](TableExistsResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details

| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Result of checking if a table exists |  -  |
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

Update existing rows in a table using SQL expressions. Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update.


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
    id = 'id_example' # str | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
    update_table_request = lance_namespace_urllib3_client.UpdateTableRequest() # UpdateTableRequest | Update request

    try:
        # Update rows in a table
        api_response = api_instance.update_table(id, update_table_request)
        print("The response of TableApi->update_table:\n")
        pprint(api_response)
    except Exception as e:
        print("Exception when calling TableApi->update_table: %s\n" % e)
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

