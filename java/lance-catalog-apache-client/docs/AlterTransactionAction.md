

# AlterTransactionAction


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | **String** |  |  |
|**status** | **TransactionStatus** |  |  [optional] |
|**key** | **String** |  |  [optional] |
|**value** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | the behavior if the property key to unset does not exist |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| SKIP | &quot;SKIP&quot; |
| FAIL | &quot;FAIL&quot; |



