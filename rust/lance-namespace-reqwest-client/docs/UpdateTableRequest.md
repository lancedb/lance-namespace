# UpdateTableRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**predicate** | Option<**String**> | Optional SQL predicate to filter rows for update | [optional]
**updates** | [**Vec<Vec<String>>**](Vec.md) | List of column updates as [column_name, expression] pairs | 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


