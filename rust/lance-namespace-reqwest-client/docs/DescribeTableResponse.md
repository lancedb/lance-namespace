# DescribeTableResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**table** | Option<**String**> | Table name. Only populated when `load_detailed_metadata` is true.  | [optional]
**namespace** | Option<**Vec<String>**> | The namespace identifier as a list of parts. Only populated when `load_detailed_metadata` is true.  | [optional]
**version** | Option<**i64**> | Table version number. Only populated when `load_detailed_metadata` is true.  | [optional]
**location** | **String** | Table storage location (e.g., S3/GCS path). This is the only required field and is always returned.  | 
**table_uri** | Option<**String**> | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when `with_table_uri` is true.  | [optional]
**schema** | Option<[**models::JsonArrowSchema**](JsonArrowSchema.md)> | Table schema in JSON Arrow format. Only populated when `load_detailed_metadata` is true.  | [optional]
**storage_options** | Option<**std::collections::HashMap<String, String>**> | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional]
**stats** | Option<[**models::TableBasicStats**](TableBasicStats.md)> | Table statistics. Only populated when `load_detailed_metadata` is true.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


