

# DescribeTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**table** | **String** | Table name. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**namespace** | **List&lt;String&gt;** | The namespace identifier as a list of parts. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**version** | **Long** | Table version number. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**location** | **String** | Table storage location (e.g., S3/GCS path). This is the only required field and is always returned.  |  |
|**tableUri** | **String** | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when &#x60;with_table_uri&#x60; is true.  |  [optional] |
|**schema** | [**JsonArrowSchema**](JsonArrowSchema.md) | Table schema in JSON Arrow format. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  |  [optional] |
|**stats** | [**TableBasicStats**](TableBasicStats.md) | Table statistics. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |



