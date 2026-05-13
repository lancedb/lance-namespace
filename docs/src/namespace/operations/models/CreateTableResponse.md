

# CreateTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**transactionId** | **String** | Optional transaction identifier |  [optional] |
|**location** | **String** |  |  [optional] |
|**version** | **Long** |  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** | Business logic properties stored and managed by the namespace implementation outside Lance context. If the implementation does not support table properties, it should return null for this field.  |  [optional] |



