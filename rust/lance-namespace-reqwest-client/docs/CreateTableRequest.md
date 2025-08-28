# CreateTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**location** | Option<**String**> |  | [optional]
**mode** | Option<**String**> | There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists:   * create: the operation fails with 409.   * exist_ok: the operation succeeds and the existing table is kept.   * overwrite: the existing table is dropped and a new table with this name is created.  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


