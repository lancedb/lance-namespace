# CreateNamespaceRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing namespace is kept.   * Overwrite: the existing namespace is dropped and a new empty namespace with this name is created.  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


