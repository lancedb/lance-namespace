# RegisterTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**location** | **String** |  | 
**mode** | Option<**String**> | There are two modes when trying to register a table, to differentiate the behavior when a table of the same name already exists. Case insensitive. Valid values are:   * CREATE (default): the operation fails with 409.   * OVERWRITE: the existing table registration is replaced with the new registration.  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


