# \TableApi

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
[**merge_insert_table**](TableApi.md#merge_insert_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](TableApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**register_table**](TableApi.md#register_table) | **POST** /v1/table/{id}/register | Register a table to a namespace
[**table_exists**](TableApi.md#table_exists) | **POST** /v1/table/{id}/exists | Check if a table exists
[**update_table**](TableApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table



## count_rows

> i64 count_rows(id, count_rows_request, delimiter)
Count rows in a table

Count the number of rows in a table. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**count_rows_request** | [**CountRowsRequest**](CountRowsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

**i64**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_index

> models::CreateIndexResponse create_index(id, create_index_request)
Create an index on a table

Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ) and scalar indexes. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_index_request** | [**CreateIndexRequest**](CreateIndexRequest.md) | Index creation request | [required] |

### Return type

[**models::CreateIndexResponse**](CreateIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_scalar_index

> models::CreateIndexResponse create_scalar_index(id, create_index_request)
Create a scalar index on a table

Create a scalar index on a table column for faster search operations. Supports scalar indexes (BTREE, BITMAP, LABEL_LIST). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_index_request** | [**CreateIndexRequest**](CreateIndexRequest.md) | Scalar index creation request | [required] |

### Return type

[**models::CreateIndexResponse**](CreateIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_table

> models::CreateTableResponse create_table(id, body)
Create a table with the given name

Create a new table in the namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **std::path::PathBuf** | Arrow IPC data | [required] |

### Return type

[**models::CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## delete_from_table

> models::DeleteFromTableResponse delete_from_table(id, delete_from_table_request)
Delete rows from a table

Delete rows from a table based on a SQL predicate. Returns the number of rows that were deleted. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**delete_from_table_request** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md) | Delete request | [required] |

### Return type

[**models::DeleteFromTableResponse**](DeleteFromTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## deregister_table

> models::DeregisterTableResponse deregister_table(id, deregister_table_request, delimiter)
Deregister a table from its namespace

Deregister a table from its namespace. The table content remains available in the storage. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**deregister_table_request** | [**DeregisterTableRequest**](DeregisterTableRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DeregisterTableResponse**](DeregisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## describe_table

> models::DescribeTableResponse describe_table(id, describe_table_request, delimiter)
Describe a table from the namespace

Get a table's detailed information under a specified namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**describe_table_request** | [**DescribeTableRequest**](DescribeTableRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DescribeTableResponse**](DescribeTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## drop_table

> models::DropTableResponse drop_table(id, drop_table_request, delimiter)
Drop a table from its namespace

Drop a table from its namespace and delete its data. If the table and its data can be immediately deleted, return information of the deleted table. Otherwise, return a transaction ID that client can use to track deletion progress. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**drop_table_request** | [**DropTableRequest**](DropTableRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DropTableResponse**](DropTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_index_stats

> models::IndexStatsResponse get_index_stats(id, index_name, index_stats_request)
Get index statistics

Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**index_name** | **String** | Name of the index to get stats for | [required] |
**index_stats_request** | [**IndexStatsRequest**](IndexStatsRequest.md) | Index stats request | [required] |

### Return type

[**models::IndexStatsResponse**](IndexStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## insert_table

> models::InsertTableResponse insert_table(id, body, mode)
Insert records into a table

Insert new records into an existing table using Arrow IPC format. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **std::path::PathBuf** | Arrow IPC data | [required] |
**mode** | Option<**String**> | Insert mode: \"append\" (default) or \"overwrite\" |  |[default to append]

### Return type

[**models::InsertTableResponse**](InsertTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_indices

> models::IndexListResponse list_indices(id, index_list_request)
List indices on a table

List all indices created on a table. Returns information about each index including name, columns, status, and UUID. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**index_list_request** | [**IndexListRequest**](IndexListRequest.md) | Index list request | [required] |

### Return type

[**models::IndexListResponse**](IndexListResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## merge_insert_table

> models::MergeInsertTableResponse merge_insert_table(id, on, body, when_matched_update_all, when_not_matched_insert_all)
Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on a table. This operation updates existing rows based on a matching column and inserts new rows that don't match. Returns the number of rows inserted and updated. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**on** | **String** | Column name to use for matching rows (required) | [required] |
**body** | **std::path::PathBuf** | Arrow IPC data containing the records to merge | [required] |
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match |  |[default to false]
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match |  |[default to false]

### Return type

[**models::MergeInsertTableResponse**](MergeInsertTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## query_table

> std::path::PathBuf query_table(id, query_request)
Query a table

Query a table with vector search and optional filtering. Returns results in Arrow IPC stream format. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**query_request** | [**QueryRequest**](QueryRequest.md) | Query request | [required] |

### Return type

[**std::path::PathBuf**](std::path::PathBuf.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/vnd.apache.arrow.stream, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## register_table

> models::RegisterTableResponse register_table(id, register_table_request, delimiter)
Register a table to a namespace

Register an existing table at a given storage location to a namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**register_table_request** | [**RegisterTableRequest**](RegisterTableRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::RegisterTableResponse**](RegisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## table_exists

> models::TableExistsResponse table_exists(id, table_exists_request, delimiter)
Check if a table exists

Check if a table exists. This API should behave exactly like the GetTable API, except it does not contain a body. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**table_exists_request** | [**TableExistsRequest**](TableExistsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::TableExistsResponse**](TableExistsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## update_table

> models::UpdateTableResponse update_table(id, update_table_request)
Update rows in a table

Update existing rows in a table using SQL expressions. Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**update_table_request** | [**UpdateTableRequest**](UpdateTableRequest.md) | Update request | [required] |

### Return type

[**models::UpdateTableResponse**](UpdateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

