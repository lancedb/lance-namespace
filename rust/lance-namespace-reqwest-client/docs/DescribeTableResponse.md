# DescribeTableResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | Option<**i64**> |  | [optional]
**location** | Option<**String**> |  | [optional]
**schema** | Option<[**models::JsonArrowSchema**](JsonArrowSchema.md)> |  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> |  | [optional]
**storage_options** | Option<**std::collections::HashMap<String, String>**> | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


