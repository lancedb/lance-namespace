# UpdateTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**predicate** | Option<**String**> | Optional SQL predicate to filter rows for update. Field references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. | [optional]
**updates** | [**Vec<Vec<String>>**](Vec.md) | List of field updates as [field_path, expression] pairs. Field paths and expression references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. | 
**properties** | Option<**std::collections::HashMap<String, String>**> | Properties stored on the table, if supported by the implementation.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


