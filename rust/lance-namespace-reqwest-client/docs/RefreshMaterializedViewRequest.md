# RefreshMaterializedViewRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> | Table identifier path (namespace + table name) | [optional]
**src_version** | Option<**i32**> | Optional source version to refresh from | [optional]
**max_rows_per_fragment** | Option<**i32**> | Optional maximum rows per fragment | [optional]
**concurrency** | Option<**i32**> | Optional concurrency override | [optional]
**intra_applier_concurrency** | Option<**i32**> | Optional intra-applier concurrency override | [optional]
**source_task_size** | Option<**i32**> | Optional number of source row ids per work item during expansion. Bounds per-actor memory for chunker materialized views.  | [optional]
**cluster** | Option<**String**> | Optional cluster name (operational override) | [optional]
**output_limit** | Option<**i32**> | Post-trim cap on view row count after expansion. Valid only for chunker materialized views; returns 400 if set on other kinds.  | [optional]
**manifest** | Option<**String**> | Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only; does not mutate the view's snapshotted manifest. When omitted, the manifest stored in the view's metadata is used.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


