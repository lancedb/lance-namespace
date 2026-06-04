# \DataApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**alter_table_add_columns**](DataApi.md#alter_table_add_columns) | **POST** /v1/table/{id}/add_columns | Add new columns to table schema
[**alter_table_backfill_columns**](DataApi.md#alter_table_backfill_columns) | **POST** /v1/table/{id}/backfill_column | Trigger an async column backfill job
[**analyze_table_query_plan**](DataApi.md#analyze_table_query_plan) | **POST** /v1/table/{id}/analyze_plan | Analyze query execution plan
[**count_table_rows**](DataApi.md#count_table_rows) | **POST** /v1/table/{id}/count_rows | Count rows in a table
[**create_materialized_view**](DataApi.md#create_materialized_view) | **POST** /v1/materialized_view/{id}/create | Create a materialized view
[**create_table**](DataApi.md#create_table) | **POST** /v1/table/{id}/create | Create a table with the given name
[**delete_from_table**](DataApi.md#delete_from_table) | **POST** /v1/table/{id}/delete | Delete rows from a table
[**explain_table_query_plan**](DataApi.md#explain_table_query_plan) | **POST** /v1/table/{id}/explain_plan | Get query execution plan explanation
[**insert_into_table**](DataApi.md#insert_into_table) | **POST** /v1/table/{id}/insert | Insert records into a table
[**merge_insert_into_table**](DataApi.md#merge_insert_into_table) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table
[**query_table**](DataApi.md#query_table) | **POST** /v1/table/{id}/query | Query a table
[**refresh_materialized_view**](DataApi.md#refresh_materialized_view) | **POST** /v1/materialized_view/{id}/refresh | Trigger an async materialized view refresh
[**update_table**](DataApi.md#update_table) | **POST** /v1/table/{id}/update | Update rows in a table



## alter_table_add_columns

> models::AlterTableAddColumnsResponse alter_table_add_columns(id, alter_table_add_columns_request, delimiter)
Add new columns to table schema

Add new columns to table `id` using SQL expressions or default values. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**alter_table_add_columns_request** | [**AlterTableAddColumnsRequest**](AlterTableAddColumnsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::AlterTableAddColumnsResponse**](AlterTableAddColumnsResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## alter_table_backfill_columns

> models::AlterTableBackfillColumnsResponse alter_table_backfill_columns(id, alter_table_backfill_columns_request, delimiter)
Trigger an async column backfill job

Trigger an asynchronous backfill job for a computed column on table `id`. The column must be a virtual (UDF-backed) column. Returns a job ID for tracking. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**alter_table_backfill_columns_request** | [**AlterTableBackfillColumnsRequest**](AlterTableBackfillColumnsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::AlterTableBackfillColumnsResponse**](AlterTableBackfillColumnsResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## analyze_table_query_plan

> String analyze_table_query_plan(id, analyze_table_query_plan_request, delimiter)
Analyze query execution plan

Analyze the query execution plan for a query against table `id`. Returns detailed statistics and analysis of the query execution plan.  REST NAMESPACE ONLY REST namespace returns the response as a plain string instead of the `AnalyzeTableQueryPlanResponse` JSON object. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**analyze_table_query_plan_request** | [**AnalyzeTableQueryPlanRequest**](AnalyzeTableQueryPlanRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

**String**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## count_table_rows

> i64 count_table_rows(id, count_table_rows_request, delimiter)
Count rows in a table

Count the number of rows in table `id`  REST NAMESPACE ONLY REST namespace returns the response as a plain integer instead of the `CountTableRowsResponse` JSON object. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**count_table_rows_request** | [**CountTableRowsRequest**](CountTableRowsRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

**i64**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_materialized_view

> models::CreateMaterializedViewResponse create_materialized_view(id, create_materialized_view_request, delimiter)
Create a materialized view

Create a materialized view at identifier `id`. The view may be query-backed, UDTF-backed, or chunker-backed, controlled by the `kind` discriminator. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_materialized_view_request** | [**CreateMaterializedViewRequest**](CreateMaterializedViewRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::CreateMaterializedViewResponse**](CreateMaterializedViewResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## create_table

> models::CreateTableResponse create_table(id, body, delimiter, mode, properties, storage_options)
Create a table with the given name

Create table `id` in the namespace with the given data in Arrow IPC stream.  The schema of the Arrow IPC stream is used as the table schema. If the stream is empty, the API creates a new empty table.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `CreateTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `mode`: pass through query parameter of the same name - `properties`: serialize as a single JSON-encoded query parameter such as   `properties={\"user\":\"alice\",\"team\":\"eng\"}`; these are business logic properties   managed by the namespace implementation outside Lance context - `storage_options`: serialize as a single JSON-encoded query parameter such as   `storage_options={\"aws_region\":\"us-east-1\",\"timeout\":\"30s\"}`; these configure   write-time overrides for data and metadata written during table creation 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **Vec<u8>** | Arrow IPC data | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |
**mode** | Option<**String**> |  |  |
**properties** | Option<**String**> | Business logic properties managed by the namespace implementation outside Lance context. The map is translated to a single JSON-encoded query parameter such as `properties={\"user\":\"alice\",\"team\":\"eng\"}`.  |  |
**storage_options** | Option<**String**> | Storage options that configure overrides for writing table data and metadata during table creation. These are passed to Lance for the write path. The map is translated to a single JSON-encoded query parameter such as `storage_options={\"aws_region\":\"us-east-1\",\"timeout\":\"30s\"}`.  |  |

### Return type

[**models::CreateTableResponse**](CreateTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## delete_from_table

> models::DeleteFromTableResponse delete_from_table(id, delete_from_table_request, delimiter)
Delete rows from a table

Delete rows from table `id`. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**delete_from_table_request** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md) | Delete request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::DeleteFromTableResponse**](DeleteFromTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## explain_table_query_plan

> String explain_table_query_plan(id, explain_table_query_plan_request, delimiter)
Get query execution plan explanation

Get the query execution plan for a query against table `id`. Returns a human-readable explanation of how the query will be executed.  REST NAMESPACE ONLY REST namespace returns the response as a plain string instead of the `ExplainTableQueryPlanResponse` JSON object. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**explain_table_query_plan_request** | [**ExplainTableQueryPlanRequest**](ExplainTableQueryPlanRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

**String**

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## insert_into_table

> models::InsertIntoTableResponse insert_into_table(id, body, delimiter, branch, mode)
Insert records into a table

Insert new records into table `id`.  For tables that have been declared but not yet created on storage (is_only_declared=true), this operation will create the table with the provided data.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `InsertIntoTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `mode`: pass through query parameter of the same name 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**body** | **Vec<u8>** | Arrow IPC stream containing the records to insert | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |
**branch** | Option<**String**> | Optional branch to target. When not specified, the main branch is used. Used by branch-scoped operations that cannot carry a `branch` field in their request body (Arrow IPC stream and bodyless operations). Operations with a JSON request body carry `branch` as a body field instead.  |  |
**mode** | Option<**String**> | How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it  |  |[default to append]

### Return type

[**models::InsertIntoTableResponse**](InsertIntoTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## merge_insert_into_table

> models::MergeInsertIntoTableResponse merge_insert_into_table(id, on, body, delimiter, branch, when_matched_update_all, when_matched_update_all_filt, when_not_matched_insert_all, when_not_matched_by_source_delete, when_not_matched_by_source_delete_filt, timeout, use_index)
Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on table `id`. This operation updates existing rows based on a matching column and inserts new rows that don't match. It returns the number of rows inserted and updated.  For tables that have been declared but not yet created on storage (is_only_declared=true), this operation will create the table with the provided data (since there are no existing rows to merge with).  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the `MergeInsertIntoTableRequest` information in the following way: - `id`: pass through path parameter of the same name - `on`: pass through query parameter of the same name - `when_matched_update_all`: pass through query parameter of the same name - `when_matched_update_all_filt`: pass through query parameter of the same name - `when_not_matched_insert_all`: pass through query parameter of the same name - `when_not_matched_by_source_delete`: pass through query parameter of the same name - `when_not_matched_by_source_delete_filt`: pass through query parameter of the same name 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**on** | **String** | Column name to use for matching rows (required) | [required] |
**body** | **Vec<u8>** | Arrow IPC stream containing the records to merge | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |
**branch** | Option<**String**> | Optional branch to target. When not specified, the main branch is used. Used by branch-scoped operations that cannot carry a `branch` field in their request body (Arrow IPC stream and bodyless operations). Operations with a JSON request body carry `branch` as a body field instead.  |  |
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match |  |[default to false]
**when_matched_update_all_filt** | Option<**String**> | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true |  |
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match |  |[default to false]
**when_not_matched_by_source_delete** | Option<**bool**> | Delete all rows from target table that don't match a row in the source table |  |[default to false]
**when_not_matched_by_source_delete_filt** | Option<**String**> | Delete rows from the target table if there is no match AND the SQL expression evaluates to true |  |
**timeout** | Option<**String**> | Timeout for the operation (e.g., \"30s\", \"5m\") |  |
**use_index** | Option<**bool**> | Whether to use index for matching rows |  |[default to false]

### Return type

[**models::MergeInsertIntoTableResponse**](MergeInsertIntoTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## query_table

> Vec<u8> query_table(id, query_table_request, delimiter)
Query a table

Query table `id` with vector search, full text search and optional SQL filtering. Returns results in Arrow IPC file or stream format.  REST NAMESPACE ONLY REST namespace returns the response as Arrow IPC file binary data instead of the `QueryTableResponse` JSON object. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**query_table_request** | [**QueryTableRequest**](QueryTableRequest.md) | Query request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**Vec<u8>**](Vec<u8>.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/vnd.apache.arrow.file, application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## refresh_materialized_view

> models::RefreshMaterializedViewResponse refresh_materialized_view(id, delimiter, refresh_materialized_view_request)
Trigger an async materialized view refresh

Trigger an asynchronous refresh job for materialized view `id`. Returns a job ID for tracking. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |
**refresh_materialized_view_request** | Option<[**RefreshMaterializedViewRequest**](RefreshMaterializedViewRequest.md)> |  |  |

### Return type

[**models::RefreshMaterializedViewResponse**](RefreshMaterializedViewResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

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
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**update_table_request** | [**UpdateTableRequest**](UpdateTableRequest.md) | Update request | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::UpdateTableResponse**](UpdateTableResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

