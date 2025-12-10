

# DescribeTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**table** | **String** | Table name |  [optional] |
|**namespace** | **List&lt;String&gt;** | The namespace identifier as a list of parts |  [optional] |
|**version** | **Long** |  |  [optional] |
|**location** | **String** | Table storage location (e.g., S3/GCS path) |  [optional] |
|**tableUri** | **String** | Table URI (deprecated, use &#x60;location&#x60; instead) |  [optional] |
|**schema** | [**JsonArrowSchema**](JsonArrowSchema.md) |  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  |  [optional] |
|**stats** | [**TableBasicStats**](TableBasicStats.md) | Table statistics |  [optional] |



