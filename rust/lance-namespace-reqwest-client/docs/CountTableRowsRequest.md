# CountTableRowsRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | Option<[**models::Identity**](Identity.md)> |  | [optional]
**id** | Option<**Vec<String>**> |  | [optional]
**version** | Option<**i64**> | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional]
**predicate** | Option<**String**> | Optional SQL predicate to filter rows for counting  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


