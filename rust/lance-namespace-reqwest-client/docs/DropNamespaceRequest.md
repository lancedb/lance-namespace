# DropNamespaceRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Fail (default): the server must return 400 indicating the namespace to drop does not exist. - Skip: the server must return 204 indicating the drop operation has succeeded.  | [optional]
**behavior** | Option<**String**> | The behavior for dropping a namespace. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Restrict (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - Cascade: all tables and child namespaces in the namespace are dropped before the namespace is dropped.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


