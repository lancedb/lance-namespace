# CreateTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing table is kept.   * Overwrite: the existing table is dropped and a new table with this name is created.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


