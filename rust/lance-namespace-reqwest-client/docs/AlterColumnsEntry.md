# AlterColumnsEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | Lance field path to alter. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**data_type** | Option<[**serde_json::Value**](.md)> | New data type for the column using JSON representation (optional) | [optional]
**rename** | Option<**String**> | New name for the column (optional) | [optional]
**nullable** | Option<**bool**> | Whether the column should be nullable (optional) | [optional]
**virtual_column** | Option<[**models::AlterVirtualColumnEntry**](AlterVirtualColumnEntry.md)> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


