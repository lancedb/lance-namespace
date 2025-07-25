

# DescribeTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**version** | **Long** |  |  |
|**location** | **String** |  |  [optional] |
|**schema** | [**JsonArrowSchema**](JsonArrowSchema.md) |  |  |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  |  [optional] |



