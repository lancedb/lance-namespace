

# AlterTransactionSetProperty


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | **String** |  |  |
|**key** | **String** |  |  [optional] |
|**value** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | The behavior if the property key already exists. - OVERWRITE (default): overwrite the existing value with the provided value - FAIL: fail the entire operation - SKIP: keep the existing value and skip setting the provided value  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| OVERWRITE | &quot;OVERWRITE&quot; |
| FAIL | &quot;FAIL&quot; |
| SKIP | &quot;SKIP&quot; |



