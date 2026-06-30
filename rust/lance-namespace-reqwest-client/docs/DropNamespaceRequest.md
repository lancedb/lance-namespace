# DropNamespaceRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Fail (default): the server must return 400 indicating the namespace to drop does not exist. - Skip: the server must return 204 indicating the drop operation has succeeded.  | [optional]
**behavior** | Option<**String**> | The behavior for dropping a namespace. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Restrict (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - Cascade: all tables and child namespaces in the namespace are dropped before the namespace is dropped.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


