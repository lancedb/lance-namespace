# AlterColumnsEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | Column path to alter | 
**data_type** | [**serde_json::Value**](.md) | New data type for the column using JSON representation (optional) | 
**rename** | Option<**String**> | New name for the column (optional) | [optional]
**nullable** | Option<**bool**> | Whether the column should be nullable (optional) | [optional]
**virtual_column** | Option<[**models::AlterVirtualColumnEntry**](AlterVirtualColumnEntry.md)> | Virtual column alterations (optional) | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


