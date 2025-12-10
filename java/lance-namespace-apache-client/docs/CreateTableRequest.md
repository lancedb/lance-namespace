

# CreateTableRequest

Request for creating a table, excluding the Arrow IPC stream. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | There are three modes when trying to create a table, to differentiate the behavior when a table of the same name already exists:   * Create: the operation fails with 409.   * ExistOk: the operation succeeds and the existing table is kept.   * Overwrite: the existing table is dropped and a new table with this name is created.  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;Create&quot; |
| EXIST_OK | &quot;ExistOk&quot; |
| OVERWRITE | &quot;Overwrite&quot; |



