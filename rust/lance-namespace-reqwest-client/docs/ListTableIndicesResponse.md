# ListTableIndicesResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** | The table name | 
**namespace** | **Vec<String>** | The namespace identifier | 
**location** | **String** | Table location (usually empty) | 
**properties** | Option<**std::collections::HashMap<String, String>**> | Additional properties (usually empty) | [optional]
**indexes** | [**Vec<models::IndexListItemResponse>**](IndexListItemResponse.md) | List of indexes on the table | 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


