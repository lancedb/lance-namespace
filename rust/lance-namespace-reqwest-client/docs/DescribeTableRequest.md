# DescribeTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**version** | Option<**i64**> | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional]
**with_table_uri** | Option<**bool**> | Whether to include the table URI in the response. Default is false.  | [optional][default to false]
**load_detailed_metadata** | Option<**bool**> | Whether to load detailed metadata that requires opening the dataset. When true, the response must include all detailed metadata such as `version`, `schema`, and `stats` which require reading the dataset. When not set, the implementation can decide whether to return detailed metadata and which parts of detailed metadata to return.  | [optional]
**vend_credentials** | Option<**bool**> | Whether to include vended credentials in the response `storage_options`. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


