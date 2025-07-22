# \MetadataApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_transaction**](MetadataApi.md#alter_transaction) | **POST** /v1/transaction/{id}/alter | Alter information of a transaction.
[**create_namespace**](MetadataApi.md#create_namespace) | **POST** /v1/namespace/{id}/create | Create a new namespace
[**create_table**](MetadataApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**create_table_index**](MetadataApi.md#create_table_index) | **POST** /v1/table/{id}/create_index | Create an index on a table
[**deregister_table**](MetadataApi.md#deregister_table) | **POST** /v1/table/{id}/deregister | Deregister a table from its namespace
[**describe_namespace**](MetadataApi.md#describe_namespace) | **POST** /v1/namespace/{id}/describe | Describe information about a namespace
[**describe_table**](MetadataApi.md#describe_table) | **POST** /v1/table/{id}/describe | Describe a table from the namespace
[**describe_table_index_stats**](MetadataApi.md#describe_table_index_stats) | **POST** /v1/table/{id}/index/{index_name}/stats | Get table index statistics
[**describe_transaction**](MetadataApi.md#describe_transaction) | **POST** /v1/transaction/{id}/describe | Describe information about a transaction
[**drop_namespace**](MetadataApi.md#drop_namespace) | **POST** /v1/namespace/{id}/drop | Drop a namespace
[**drop_table**](MetadataApi.md#drop_table) | **POST** /v1/table/{id}/drop | Drop a table from its namespace
[**list_namespaces**](MetadataApi.md#list_namespaces) | **POST** /v1/namespace/{id}/list | List namespaces
[**list_table_indices**](MetadataApi.md#list_table_indices) | **POST** /v1/table/{id}/index/list | List indexes on a table
[**list_tables**](MetadataApi.md#list_tables) | **POST** /v1/namespace/{id}/table/list | List tables in a namespace
[**namespace_exists**](MetadataApi.md#namespace_exists) | **POST** /v1/namespace/{id}/exists | Check if a namespace exists
[**register_table**](MetadataApi.md#register_table) | **POST** /v1/table/{id}/register | Register a table to a namespace
[**table_exists**](MetadataApi.md#table_exists) | **POST** /v1/table/{id}/exists | Check if a table exists



## alter_transaction

> models::AlterTransactionResponse alter_transaction(id, alter_transaction_request, delimiter)
Alter information of a transaction.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**alter_transaction_request** | [**AlterTransactionRequest**](AlterTransactionRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::AlterTransactionResponse**](AlterTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_namespace

> models::CreateNamespaceResponse create_namespace(id, create_namespace_request, delimiter)
Create a new namespace

Create a new namespace.  A namespace can manage either a collection of child namespaces, or a collection of tables.  The namespace in the API route should be the parent namespace to create the new namespace.  There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing namespace is kept.   * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_namespace_request** | [**CreateNamespaceRequest**](CreateNamespaceRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::CreateNamespaceResponse**](CreateNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_table

> models::CreateTableResponse create_table(id, x_lance_table_location, body, x_lance_table_properties)
Create a table with the given name

Create a new table in the namespace with the given data in Arrow IPC stream.  The schema of the Arrow IPC stream is used as the table schema.     If the stream is empty, the API creates a new empty table. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**x_lance_table_location** | **String** | URI pointing to root location to create the table at | [required] |
**body** | **Vec<u8>** | Arrow IPC data | [required] |
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

> models::CreateTableIndexResponse create_table_index(id, create_table_index_request)
Create an index on a table

Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously.  Use the `ListTableIndices` and `DescribeTableIndexStats` operations to monitor index creation progress. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_table_index_request** | [**CreateTableIndexRequest**](CreateTableIndexRequest.md) | Index creation request | [required] |

### Return type

[**models::CreateTableIndexResponse**](CreateTableIndexResponse.md)

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


## describe_namespace

> models::DescribeNamespaceResponse describe_namespace(id, describe_namespace_request, delimiter)
Describe information about a namespace

Return the detailed information for a given namespace 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**describe_namespace_request** | [**DescribeNamespaceRequest**](DescribeNamespaceRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DescribeNamespaceResponse**](DescribeNamespaceResponse.md)

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


## describe_table_index_stats

> models::DescribeTableIndexStatsResponse describe_table_index_stats(id, index_name, describe_table_index_stats_request)
Get table index statistics

Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**index_name** | **String** | Name of the index to get stats for | [required] |
**describe_table_index_stats_request** | [**DescribeTableIndexStatsRequest**](DescribeTableIndexStatsRequest.md) | Index stats request | [required] |

### Return type

[**models::DescribeTableIndexStatsResponse**](DescribeTableIndexStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## describe_transaction

> models::DescribeTransactionResponse describe_transaction(id, describe_transaction_request, delimiter)
Describe information about a transaction

Return a detailed information for a given transaction

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**describe_transaction_request** | [**DescribeTransactionRequest**](DescribeTransactionRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DescribeTransactionResponse**](DescribeTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## drop_namespace

> models::DropNamespaceResponse drop_namespace(id, drop_namespace_request, delimiter)
Drop a namespace

Drop a namespace. The namespace must be empty. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**drop_namespace_request** | [**DropNamespaceRequest**](DropNamespaceRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::DropNamespaceResponse**](DropNamespaceResponse.md)

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


## list_namespaces

> models::ListNamespacesResponse list_namespaces(id, list_namespaces_request, delimiter)
List namespaces

List all child namespace names of the root namespace or a given parent namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**list_namespaces_request** | [**ListNamespacesRequest**](ListNamespacesRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_table_indices

> models::ListTableIndicesResponse list_table_indices(id, list_table_indices_request)
List indexes on a table

List all indices created on a table. Returns information about each index including name, columns, status, and UUID. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**list_table_indices_request** | [**ListTableIndicesRequest**](ListTableIndicesRequest.md) | Index list request | [required] |

### Return type

[**models::ListTableIndicesResponse**](ListTableIndicesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_tables

> models::ListTablesResponse list_tables(id, list_tables_request, delimiter)
List tables in a namespace

List all child table names of the root namespace or a given parent namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**list_tables_request** | [**ListTablesRequest**](ListTablesRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::ListTablesResponse**](ListTablesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## namespace_exists

> namespace_exists(id, namespace_exists_request, delimiter)
Check if a namespace exists

Check if a namespace exists.  This API should behave exactly like the DescribeNamespace API, except it does not contain a body. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**namespace_exists_request** | [**NamespaceExistsRequest**](NamespaceExistsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

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

> table_exists(id, table_exists_request, delimiter)
Check if a table exists

Check if a table exists.  This API should behave exactly like the DescribeTable API, except it does not contain a body. 

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

