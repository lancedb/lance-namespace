# DescribeTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**context** | Option<**std::collections::HashMap<String, String>**> | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention `x-lance-ctx-<key>: <value>`. For example, a context entry `{\"trace_id\": \"abc123\"}` would be sent as the header `x-lance-ctx-trace_id: abc123`.  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**version** | Option<**i64**> | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional]
**with_table_uri** | Option<**bool**> | Whether to include the table URI in the response. Default is false.  | [optional][default to false]
**load_detailed_metadata** | Option<**bool**> | Whether to load detailed metadata that requires opening the dataset. When true, the response must include all detailed metadata such as `version`, `schema`, and `stats` which require reading the dataset. When not set, the implementation can decide whether to return detailed metadata and which parts of detailed metadata to return.  | [optional]
**vend_credentials** | Option<**bool**> | Whether to include vended credentials in the response `storage_options`. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


