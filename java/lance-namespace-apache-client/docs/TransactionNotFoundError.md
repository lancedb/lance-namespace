

# TransactionNotFoundError

The requested transaction does not exist

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
| LANCE_NAMESPACE_501 | &quot;lance-namespace:501&quot; |



