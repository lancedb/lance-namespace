# MergeInsertIntoTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**on** | Option<**String**> | Lance field path to use for matching rows. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | [optional]
**when_matched_update_all** | Option<**bool**> | Update all columns when rows match | [optional][default to false]
**when_matched_update_all_filt** | Option<**String**> | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true. Field references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. | [optional]
**when_not_matched_insert_all** | Option<**bool**> | Insert all columns when rows don't match | [optional][default to false]
**when_not_matched_by_source_delete** | Option<**bool**> | Delete all rows from target table that don't match a row in the source table | [optional][default to false]
**when_not_matched_by_source_delete_filt** | Option<**String**> | Delete rows from the target table if there is no match AND the SQL expression evaluates to true. Field references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. | [optional]
**timeout** | Option<**String**> | Timeout for the operation (e.g., \"30s\", \"5m\") | [optional]
**use_index** | Option<**bool**> | Whether to use index for matching rows | [optional][default to false]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


