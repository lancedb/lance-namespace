

# CreateTableRequest

Request for creating a table, excluding the Arrow IPC stream. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**location** | **String** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists:   * create: the operation fails with 409.   * exist_ok: the operation succeeds and the existing table is kept.   * overwrite: the existing table is dropped and a new table with this name is created.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;create&quot; |
| EXIST_OK | &quot;exist_ok&quot; |
| OVERWRITE | &quot;overwrite&quot; |



