# DescribeTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**version** | Option<**i64**> | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional]
**with_table_uri** | Option<**bool**> | Whether to include the table URI in the response. Default is false.  | [optional][default to false]
**load_detailed_metadata** | Option<**bool**> | Whether to load detailed metadata that requires opening the dataset. When false (default), only `location` is required in the response. When true, the response includes additional metadata such as `version`, `schema`, and `stats` which require reading the dataset.  | [optional][default to false]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


