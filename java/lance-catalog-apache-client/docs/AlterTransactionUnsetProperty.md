

# AlterTransactionUnsetProperty


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | [**TypeEnum**](#TypeEnum) |  |  |
|**key** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | the behavior if the property key to unset does not exist |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| UNSET_PROPERTY | &quot;UnsetProperty&quot; |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| SKIP | &quot;SKIP&quot; |
| FAIL | &quot;FAIL&quot; |



