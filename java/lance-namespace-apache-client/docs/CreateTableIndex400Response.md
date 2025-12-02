

# CreateTableIndex400Response


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**error** | **String** | Brief error message |  |
|**code** | [**CodeEnum**](#CodeEnum) | HTTP status code |  |
|**type** | [**TypeEnum**](#TypeEnum) | Error type identifier |  |
|**detail** | **String** | Detailed error explanation |  [optional] |
|**instance** | **String** | Specific occurrence identifier |  [optional] |



## Enum: CodeEnum

| Name | Value |
|---- | -----|
| NUMBER_400 | 400 |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LANCE_NAMESPACE_601 | &quot;lance-namespace:601&quot; |
| LANCE_NAMESPACE_204 | &quot;lance-namespace:204&quot; |
| LANCE_NAMESPACE_303 | &quot;lance-namespace:303&quot; |



