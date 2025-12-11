# InsertIntoTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Create: create new table, fail if table already exists - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it  | [optional][default to append]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


