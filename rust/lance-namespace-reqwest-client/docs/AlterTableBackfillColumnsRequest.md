# AlterTableBackfillColumnsRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> | Table identifier path (namespace + table name) | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**column** | **String** | Lance field path to backfill. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**r#where** | Option<**String**> | Optional WHERE clause filter | [optional]
**concurrency** | Option<**i32**> | Optional concurrency override | [optional]
**intra_applier_concurrency** | Option<**i32**> | Optional intra-applier concurrency override | [optional]
**min_checkpoint_size** | Option<**i32**> | Optional minimum checkpoint size | [optional]
**max_checkpoint_size** | Option<**i32**> | Optional maximum checkpoint size | [optional]
**batch_checkpoint_flush_interval_seconds** | Option<**f64**> | Optional batch checkpoint flush interval in seconds | [optional]
**read_version** | Option<**i32**> | Optional table version to read from | [optional]
**task_size** | Option<**i32**> | Optional task size | [optional]
**num_frags** | Option<**i32**> | Optional number of fragments | [optional]
**checkpoint_size** | Option<**i32**> | Optional checkpoint size | [optional]
**commit_granularity** | Option<**i32**> | Optional commit granularity | [optional]
**cluster** | Option<**String**> | Optional cluster name | [optional]
**manifest** | Option<**String**> | Optional manifest name | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


