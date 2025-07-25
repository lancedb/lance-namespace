# CreateNamespaceRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing namespace is kept.   * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created.  | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


