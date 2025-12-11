# CreateNamespaceResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | Option<**String**> | Optional transaction identifier | [optional]
**properties** | Option<**std::collections::HashMap<String, String>**> | Properties after the namespace is created.  If the server does not support namespace properties, it should return null for this field. If namespace properties are supported, but none are set, it should return an empty object.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


