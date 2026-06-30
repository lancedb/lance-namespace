# CreateNamespaceRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing namespace is kept.   * Overwrite: the existing namespace is dropped and a new empty namespace with this name is created.  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> | Properties stored on the namespace, if supported by the implementation.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


