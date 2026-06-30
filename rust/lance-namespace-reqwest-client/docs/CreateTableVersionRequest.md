# CreateTableVersionRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the `header.` prefix: - On a request, any entry whose key starts with `header.` is sent as an HTTP   request header with the prefix stripped. For example, the entry   `{\"header.Authorization\": \"Bearer abc\"}` is sent as the request header   `Authorization: Bearer abc`. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with `header.`. For example, the response header   `x-request-id: abc123` is returned as the entry `{\"header.x-request-id\": \"abc123\"}`.  | [optional]
**id** | Option<**Vec<String>**> | The table identifier | [optional]
**version** | **i64** | Version number to create | 
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**manifest_path** | **String** | Path to the manifest file for this version | 
**manifest_size** | Option<**i64**> | Size of the manifest file in bytes | [optional]
**e_tag** | Option<**String**> | Optional ETag for the manifest file | [optional]
**metadata** | Option<**std::collections::HashMap<String, String>**> | Optional metadata for the version | [optional]
**naming_scheme** | Option<**String**> | The naming scheme used for manifest files in the `_versions/` directory.  Known values: - `V1`: `_versions/{version}.manifest` - Simple version-based naming - `V2`: `_versions/{inverted_version}.manifest` - Zero-padded, reversed version number   (uses `u64::MAX - version`) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


