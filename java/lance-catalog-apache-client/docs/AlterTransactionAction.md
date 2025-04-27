

# AlterTransactionAction


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) |  |  |
|**status** | **TransactionStatus** |  |  [optional] |
|**key** | **String** |  |  [optional] |
|**value** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | the behavior if the property key to unset does not exist |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| SET_STATUS | &quot;SetStatus&quot; |
| SET_PROPERTY | &quot;SetProperty&quot; |
| UNSET_PROPERTY | &quot;UnsetProperty&quot; |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| SKIP | &quot;SKIP&quot; |
| FAIL | &quot;FAIL&quot; |



