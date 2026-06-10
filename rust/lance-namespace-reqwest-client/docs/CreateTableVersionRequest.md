# CreateTableVersionRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> | The table identifier | [optional]
**version** | **i64** | Version number to create | 
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**manifest_path** | **String** | Path to the manifest file for this version | 
**manifest_size** | Option<**i64**> | Size of the manifest file in bytes | [optional]
**e_tag** | Option<**String**> | Optional ETag for the manifest file | [optional]
**metadata** | Option<**std::collections::HashMap<String, String>**> | Optional metadata for the version | [optional]
**naming_scheme** | Option<**String**> | The naming scheme used for manifest files in the `_versions/` directory.  Known values: - `V1`: `_versions/{version}.manifest` - Simple version-based naming - `V2`: `_versions/{inverted_version}.manifest` - Zero-padded, reversed version number   (uses `u64::MAX - version`) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


