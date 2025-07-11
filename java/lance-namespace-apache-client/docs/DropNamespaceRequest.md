

# DropNamespaceRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** |  |  |
|**parent** | **List&lt;String&gt;** |  |  [optional] |
|**mode** | [**ModeEnum**](#ModeEnum) | The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. - FAIL (default): the server must return 400 indicating the namespace to drop does not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded.  |  [optional] |
|**behavior** | [**BehaviorEnum**](#BehaviorEnum) | The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - CASCADE: all tables and child namespaces in the namespace are dropped before the namespace is dropped.  |  [optional] |



## Enum: ModeEnum

| Name | Value |
|---- | -----|
| SKIP | &quot;SKIP&quot; |
| FAIL | &quot;FAIL&quot; |



## Enum: BehaviorEnum

| Name | Value |
|---- | -----|
| RESTRICT | &quot;RESTRICT&quot; |
| CASCADE | &quot;CASCADE&quot; |



