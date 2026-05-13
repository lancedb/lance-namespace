

# CommitTableOperation

A single operation within a batch commit. Provide exactly one of the operation fields to specify the operation kind. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**declareTable** | [**DeclareTableRequest**](DeclareTableRequest.md) | Declare (reserve) a new table in the namespace |  [optional] |
|**createTableVersion** | [**CreateTableVersionRequest**](CreateTableVersionRequest.md) | Create a new version entry for a table |  [optional] |
|**deleteTableVersions** | [**BatchDeleteTableVersionsRequest**](BatchDeleteTableVersionsRequest.md) | Delete version ranges from a table |  [optional] |
|**deregisterTable** | [**DeregisterTableRequest**](DeregisterTableRequest.md) | Deregister (soft-delete) a table |  [optional] |



