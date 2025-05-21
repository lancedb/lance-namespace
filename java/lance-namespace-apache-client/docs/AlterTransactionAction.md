

# AlterTransactionAction


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | **String** |  |  |
|**status** | **TransactionStatus** |  |  [optional] |
|**key** | **String** |  |  [optional] |
|**value** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | The behavior if the property key to unset does not exist. - SKIP (default): skip the property to unset - FAIL: fail the entire operation  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| SKIP | &quot;SKIP&quot; |
| FAIL | &quot;FAIL&quot; |



