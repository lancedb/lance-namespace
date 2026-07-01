# CreateMaterializedViewRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> | View identifier path (namespace + view name) | [optional]
**kind** | **String** | The materialized view kind. - `query` — plain query-backed view (no UDTF), 1:1 rows. - `udtf` — batch UDTF-backed view (N:M rows, full refresh). - `chunker`, aka 'scalar_udtf' — chunker view (1:N row expansion, incremental refresh).  | 
**source_query** | **String** | Opaque serialized representation of the source query that defines the view's input. The format is defined by the client; the namespace server stores it without interpreting it.  | 
**output_schema** | **String** | Base64-encoded Arrow schema of the view output | 
**udtf_spec** | Option<[**models::MaterializedViewUdtfEntry**](MaterializedViewUdtfEntry.md)> |  | [optional]
**with_no_data** | Option<**bool**> | If false, the server kicks off an initial refresh immediately after creating the view and the response includes a job ID.  | [optional][default to true]
**auto_refresh** | Option<**bool**> | If true, the view is automatically refreshed when source-table data changes past the deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on the deployment, not per-view.  | [optional][default to false]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


