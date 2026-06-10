# UpdateFieldMetadataRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> | Table identifier path (namespace + table name) | [optional]
**branch** | Option<**String**> | Branch to target. When not specified, the main branch is used.  | [optional]
**updates** | [**Vec<models::UpdateFieldMetadataEntry>**](UpdateFieldMetadataEntry.md) | List of per-field metadata updates to apply | 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


