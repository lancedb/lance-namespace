

# AlterTransactionAction

A single action that could be performed to alter a transaction. This action holds the model definition for all types of specific actions models, this is to minimize difference and compatibility issue across codegen in different languages. When used, only one of the actions should be non-null for each action. If you would like to perform multiple actions, set a list of actions in the AlterTransactionRequest. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**setStatusAction** | [**AlterTransactionSetStatus**](AlterTransactionSetStatus.md) |  |  [optional] |
|**setPropertyAction** | [**AlterTransactionSetProperty**](AlterTransactionSetProperty.md) |  |  [optional] |
|**unsetPropertyAction** | [**AlterTransactionUnsetProperty**](AlterTransactionUnsetProperty.md) |  |  [optional] |



