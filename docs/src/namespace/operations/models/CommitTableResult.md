

# CommitTableResult

Result of a single operation within a batch commit. Each result corresponds to one operation in the request, in the same order. Exactly one of the result fields will be set. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**declareTable** | [**DeclareTableResponse**](DeclareTableResponse.md) | Result of a DeclareTable operation |  [optional] |
|**createTableVersion** | [**CreateTableVersionResponse**](CreateTableVersionResponse.md) | Result of a CreateTableVersion operation |  [optional] |
|**deleteTableVersions** | [**BatchDeleteTableVersionsResponse**](BatchDeleteTableVersionsResponse.md) | Result of a DeleteTableVersions operation |  [optional] |
|**deregisterTable** | [**DeregisterTableResponse**](DeregisterTableResponse.md) | Result of a DeregisterTable operation |  [optional] |



