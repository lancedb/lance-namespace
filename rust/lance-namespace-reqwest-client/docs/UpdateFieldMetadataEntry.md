# UpdateFieldMetadataEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**path** | **String** | Lance field path whose metadata to update. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**metadata** | **std::collections::HashMap<String, String>** | Metadata key-value pairs to apply to the field. A null value deletes that key.  | 
**replace** | Option<**bool**> | If true, replace the field's existing metadata entirely; otherwise merge into it (optional, defaults to false).  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


