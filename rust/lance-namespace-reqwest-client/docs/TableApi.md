# \TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**count_table_rows**](TableApi.md#count_table_rows) | **POST** /v1/table/{id}/count_rows | Count rows in a table
[**create_table**](TableApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**create_table_index**](TableApi.md#create_table_index) | **POST** /v1/table/{id}/create_index | Create an index on a table
[**delete_from_table**](TableApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**deregister_table**](TableApi.md#deregister_table) | **POST** /v1/table/{id}/deregister | Deregister a table
[**describe_table**](TableApi.md#describe_table) | **POST** /v1/table/{id}/describe | Describe information of a table
[**describe_table_index_stats**](TableApi.md#describe_table_index_stats) | **POST** /v1/table/{id}/index/{index_name}/stats | Get table index statistics
[**drop_table**](TableApi.md#drop_table) | **POST** /v1/table/{id}/drop | Drop a table
[**insert_into_table**](TableApi.md#insert_into_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**list_table_indices**](TableApi.md#list_table_indices) | **POST** /v1/table/{id}/index/list | List indexes on a table
[**list_tables**](TableApi.md#list_tables) | **GET** /v1/namespace/{id}/table/list | List tables in a namespace
[**merge_insert_into_table**](TableApi.md#merge_insert_into_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](TableApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**register_table**](TableApi.md#register_table) | **POST** /v1/table/{id}/register | Register a table to a namespace
[**table_exists**](TableApi.md#table_exists) | **POST** /v1/table/{id}/exists | Check if a table exists
[**update_table**](TableApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table



## count_table_rows

> i64 count_table_rows(id, count_table_rows_request, delimiter)
Count rows in a table

Count the number of rows in table `id` 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**count_table_rows_request** | [**CountTableRowsRequest**](CountTableRowsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

**i64**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_table

> models::CreateTableResponse create_table(id, body, delimiter, x_lance_table_location, x_lance_table_properties)
Create a table with the given name

Create table `id` in the namespace with the given data in Arrow IPC stream.  The schema of the Arrow IPC stream is used as the table schema.     If the stream is empty, the API creates a new empty table.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `CreateTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `location`: pass through header `x-lance-table-location` - `properties`: pass through header `x-lance-table-properties` 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **Vec<u8>** | Arrow IPC data | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |
**x_lance_table_location** | Option<**String**> | URI pointing to root location to create the table at |  |
**x_lance_table_properties** | Option<**String**> | JSON-encoded string map (e.g. { \"owner\": \"jack\" })  |  |

### Return type

[**models::CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_table_index

> models::CreateTableIndexResponse create_table_index(id, create_table_index_request, delimiter)
Create an index on a table

Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously.  Use the `ListTableIndices` and `DescribeTableIndexStats` operations to monitor index creation progress. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_table_index_request** | [**CreateTableIndexRequest**](CreateTableIndexRequest.md) | Index creation request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::CreateTableIndexResponse**](CreateTableIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## delete_from_table

> models::DeleteFromTableResponse delete_from_table(id, delete_from_table_request, delimiter)
Delete rows from a table

Delete rows from table `id`. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**delete_from_table_request** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md) | Delete request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

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
Deregister a table

Deregister table `id` from its namespace. 

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
Describe information of a table

Describe the detailed information for table `id`. 

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


## describe_table_index_stats

> models::DescribeTableIndexStatsResponse describe_table_index_stats(id, index_name, describe_table_index_stats_request, delimiter)
Get table index statistics

Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**index_name** | **String** | Name of the index to get stats for | [required] |
**describe_table_index_stats_request** | [**DescribeTableIndexStatsRequest**](DescribeTableIndexStatsRequest.md) | Index stats request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DescribeTableIndexStatsResponse**](DescribeTableIndexStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## drop_table

> models::DropTableResponse drop_table(id, drop_table_request, delimiter)
Drop a table

Drop table `id` and delete its data. 

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


## insert_into_table

> models::InsertIntoTableResponse insert_into_table(id, body, delimiter, mode)
Insert records into a table

Insert new records into table `id`.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `InsertIntoTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `mode`: pass through query parameter of the same name 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **Vec<u8>** | Arrow IPC stream containing the records to insert | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |
**mode** | Option<**String**> | How the insert should behave: - append (default): insert data to the existing table - overwrite: remove all data in the table and then insert data to it  |  |[default to append]

### Return type

[**models::InsertIntoTableResponse**](InsertIntoTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_table_indices

> models::ListTableIndicesResponse list_table_indices(id, list_table_indices_request, delimiter)
List indexes on a table

List all indices created on a table. Returns information about each index including name, columns, status, and UUID. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**list_table_indices_request** | [**ListTableIndicesRequest**](ListTableIndicesRequest.md) | Index list request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::ListTableIndicesResponse**](ListTableIndicesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_tables

> models::ListTablesResponse list_tables(id, delimiter, page_token, limit)
List tables in a namespace

List all child table names of the parent namespace `id`.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the `ListTablesRequest` information in the following way: - `id`: pass through path parameter of the same name - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |
**page_token** | Option<**String**> |  |  |
**limit** | Option<**i32**> |  |  |

### Return type

[**models::ListTablesResponse**](ListTablesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## merge_insert_into_table

> models::MergeInsertIntoTableResponse merge_insert_into_table(id, on, body, delimiter, when_matched_update_all, when_matched_update_all_filt, when_not_matched_insert_all, when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt)
Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on table `id`. This operation updates existing rows based on a matching column and inserts new rows that don't match. It returns the number of rows inserted and updated.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `MergeInsertIntoTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `on`: pass through query parameter of the same name - `when_matched_update_all`: pass through query parameter of the same name - `when_matched_update_all_filt`: pass through query parameter of the same name - `when_not_matched_insert_all`: pass through query parameter of the same name - `when_not_matched_by_source_delete`: pass through query parameter of the same name - `when_not_matched_by_source_delete_filt`: pass through query parameter of the same name 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**on** | **String** | Column name to use for matching rows (required) | [required] |
**body** | **Vec<u8>** | Arrow IPC stream containing the records to merge | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match |  |[default to false]
**when_matched_update_all_filt** | Option<**String**> | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true |  |
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match |  |[default to false]
**when_not_matched_by_source_delete** | Option<**bool**> | Delete all rows from target table that don't match a row in the source table |  |[default to false]
**when_not_matched_by_source_delete_filt** | Option<**String**> | Delete rows from the target table if there is no match AND the SQL expression evaluates to true |  |

### Return type

[**models::MergeInsertIntoTableResponse**](MergeInsertIntoTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## query_table

> Vec<u8> query_table(id, query_table_request, delimiter)
Query a table

Query table `id` with vector search, full text search and optional SQL filtering. Returns results in Arrow IPC file or stream format. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**query_table_request** | [**QueryTableRequest**](QueryTableRequest.md) | Query request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**Vec<u8>**](Vec<u8>.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/vnd.apache.arrow.file, application/vnd.apache.arrow.stream, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## register_table

> models::RegisterTableResponse register_table(id, register_table_request, delimiter)
Register a table to a namespace

Register an existing table at a given storage location as `id`. 

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

> table_exists(id, table_exists_request, delimiter)
Check if a table exists

Check if table `id` exists.  This operation should behave exactly like DescribeTable,  except it does not contain a response body. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**table_exists_request** | [**TableExistsRequest**](TableExistsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## update_table

> models::UpdateTableResponse update_table(id, update_table_request, delimiter)
Update rows in a table

Update existing rows in table `id`. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**update_table_request** | [**UpdateTableRequest**](UpdateTableRequest.md) | Update request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::UpdateTableResponse**](UpdateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

