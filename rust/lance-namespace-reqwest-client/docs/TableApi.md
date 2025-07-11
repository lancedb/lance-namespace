# \TableApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_table**](TableApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**deregister_table**](TableApi.md#deregister_table) | **POST** /v1/table/{id}/deregister | Deregister a table from its namespace
[**describe_table**](TableApi.md#describe_table) | **POST** /v1/table/{id}/describe | Describe a table from the namespace
[**drop_table**](TableApi.md#drop_table) | **POST** /v1/table/{id}/drop | Drop a table from its namespace
[**register_table**](TableApi.md#register_table) | **POST** /v1/table/{id}/register | Register a table to a namespace
[**table_exists**](TableApi.md#table_exists) | **POST** /v1/table/{id}/exists | Check if a table exists



## create_table

> models::CreateTableResponse create_table(id, body, delimiter)
Create a table with the given name

Create a new table in the namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **std::path::PathBuf** | Arrow IPC data | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used.  |  |

### Return type

[**models::CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
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

