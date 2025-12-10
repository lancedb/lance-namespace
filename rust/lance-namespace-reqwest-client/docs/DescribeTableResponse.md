# DescribeTableResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**table** | Option<**String**> | Table name | [optional]
**namespace** | Option<**Vec<String>**> | The namespace identifier as a list of parts | [optional]
**version** | Option<**i64**> |  | [optional]
**location** | Option<**String**> | Table storage location (e.g., S3/GCS path) | [optional]
**table_uri** | Option<**String**> | Table URI (deprecated, use `location` instead) | [optional]
**schema** | Option<[**models::JsonArrowSchema**](JsonArrowSchema.md)> |  | [optional]
**storage_options** | Option<**std::collections::HashMap<String, String>**> | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional]
**stats** | Option<[**models::TableBasicStats**](TableBasicStats.md)> | Table statistics | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


