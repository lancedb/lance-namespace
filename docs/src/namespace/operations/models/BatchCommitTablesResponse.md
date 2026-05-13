

# BatchCommitTablesResponse

Response for a batch commit of table operations. Contains the results of each operation in the same order as the request. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**transactionId** | **String** | Optional transaction identifier for the batch commit |  [optional] |
|**results** | [**List&lt;CommitTableResult&gt;**](CommitTableResult.md) | Results for each operation, in the same order as the request operations. Each result contains the outcome of the corresponding operation.  |  |



