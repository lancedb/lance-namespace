

# UpdateTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**transactionId** | **String** | Optional transaction identifier |  [optional] |
|**updatedRows** | **Long** | Number of rows updated |  |
|**version** | **Long** | The commit version associated with the operation |  |
|**properties** | **Map&lt;String, String&gt;** | If the implementation does not support table properties, it should return null for this field. Otherwise, it should return the properties.  |  [optional] |



