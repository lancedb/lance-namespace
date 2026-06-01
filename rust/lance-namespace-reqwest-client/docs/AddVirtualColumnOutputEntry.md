# AddVirtualColumnOutputEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column** | **String** | Physical output column name | 
**struct_field** | **String** | Field name in the UDF output struct | 
**data_type** | [**serde_json::Value**](.md) | Data type of the output column using JSON representation | 
**nullable** | **bool** | Whether the output column is nullable | 
**metadata** | Option<**std::collections::HashMap<String, String>**> | User-supplied output field metadata (string key-value pairs) | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


