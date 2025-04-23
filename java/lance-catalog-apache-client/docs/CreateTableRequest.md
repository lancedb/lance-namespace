

# CreateTableRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  |
|**mode** | [**ModeEnum**](#ModeEnum) |  |  [optional] |
|**location** | **String** |  |  [optional] |
|**schema** | [**Schema**](Schema.md) |  |  |
|**writerVersion** | [**WriterVersion**](WriterVersion.md) |  |  [optional] |
|**config** | **Map&lt;String, String&gt;** | optional configurations for the table. Keys with the prefix \&quot;lance.\&quot; are reserved for the Lance library.  Other libraries may wish to similarly prefix their configuration keys appropriately.  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;CREATE&quot; |
| EXIST_OK | &quot;EXIST_OK&quot; |
| OVERWRITE | &quot;OVERWRITE&quot; |



