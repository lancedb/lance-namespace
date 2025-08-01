

# RegisterTableRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**location** | **String** |  |  |
|**mode** | [**ModeEnum**](#ModeEnum) | There are two modes when trying to register a table, to differentiate the behavior when a table of the same name already exists:   * CREATE (default): the operation fails with 409.   * OVERWRITE: the existing table registration is replaced with the new registration.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;CREATE&quot; |
| OVERWRITE | &quot;OVERWRITE&quot; |



