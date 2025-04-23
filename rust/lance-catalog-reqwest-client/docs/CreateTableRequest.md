# CreateTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**mode** | Option<**String**> |  | [optional][default to Create]
**location** | Option<**String**> |  | [optional]
**schema** | [**models::Schema**](Schema.md) |  | 
**writer_version** | Option<[**models::WriterVersion**](WriterVersion.md)> |  | [optional]
**config** | Option<**std::collections::HashMap<String, String>**> | optional configurations for the table. Keys with the prefix \"lance.\" are reserved for the Lance library.  Other libraries may wish to similarly prefix their configuration keys appropriately.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


