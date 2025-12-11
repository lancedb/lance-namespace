# DescribeTransactionResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**status** | **String** | The status of a transaction. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Queued: the transaction is queued and not yet started - Running: the transaction is currently running - Succeeded: the transaction has completed successfully - Failed: the transaction has failed - Canceled: the transaction was canceled  | 
**properties** | Option<**std::collections::HashMap<String, String>**> |  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


