# AddColumnsEntry

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | Name of the new column | 
**expression** | Option<**String**> | SQL expression for the column (optional if virtual_column or computed is specified). Evaluated once over existing rows; nothing is stored, so rows appended later read null. | [optional]
**computed** | Option<**String**> | SQL expression declaring a maintained computed column (optional if expression or virtual_column is specified). The column is added all-null with the expression persisted as its binding in field metadata; its type and input columns are inferred from the expression. Rows are filled by backfill, never at declaration. | [optional]
**virtual_column** | Option<[**models::AddVirtualColumnEntry**](AddVirtualColumnEntry.md)> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


