# DropNamespaceRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**parent** | Option<**Vec<String>**> |  | [optional]
**mode** | Option<**String**> | The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. - FAIL (default): the server must return 400 indicating the namespace to drop does not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded.  | [optional]
**behavior** | Option<**String**> | The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - CASCADE: all tables and child namespaces in the namespace are dropped before the namespace is dropped.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


