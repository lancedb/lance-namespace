

# CreateTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**version** | **Long** |  |  [optional] |
|**location** | **String** |  |  [optional] |
|**schema** | [**JsonArrowSchema**](JsonArrowSchema.md) |  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  |  [optional] |



