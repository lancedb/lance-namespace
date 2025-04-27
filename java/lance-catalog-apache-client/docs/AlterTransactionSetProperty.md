

# AlterTransactionSetProperty


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) |  |  |
|**key** | **String** |  |  [optional] |
|**value** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | the behavior if the property key already exists |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| SET_PROPERTY | &quot;SetProperty&quot; |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| OVERWRITE | &quot;OVERWRITE&quot; |
| FAIL | &quot;FAIL&quot; |
| SKIP | &quot;SKIP&quot; |



