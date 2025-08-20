

# CreateNamespaceRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * create: the operation fails with 409.   * exist_ok: the operation succeeds and the existing namespace is kept.   * overwrite: the existing namespace is dropped and a new empty namespace with this name is created.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| CREATE | &quot;create&quot; |
| EXIST_OK | &quot;exist_ok&quot; |
| OVERWRITE | &quot;overwrite&quot; |



