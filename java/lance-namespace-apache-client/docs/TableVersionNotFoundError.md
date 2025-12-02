

# TableVersionNotFoundError

The requested table version does not exist

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
| NUMBER_404 | 404 |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| LANCE_NAMESPACE_203 | &quot;lance-namespace:203&quot; |



