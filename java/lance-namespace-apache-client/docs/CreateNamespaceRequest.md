

# CreateNamespaceRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing namespace is kept.   * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;CREATE&quot; |
| EXIST_OK | &quot;EXIST_OK&quot; |
| OVERWRITE | &quot;OVERWRITE&quot; |



