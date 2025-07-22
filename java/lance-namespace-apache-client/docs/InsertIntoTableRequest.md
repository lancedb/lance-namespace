

# InsertIntoTableRequest

Request for inserting records into a table, excluding the Arrow IPC stream. Note that this is only used for non-REST implementations. For REST, pass in the information in the following way: - `name`: pass as a part of the path parameter `id` - `namespace`: pass as a part of the path parameter `namespace` - `mode`: pass through query parameter of the same name 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  [optional] |
|**namespace** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) |  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| APPEND | &quot;append&quot; |
| OVERWRITE | &quot;overwrite&quot; |



