

# DescribeTableResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**table** | **String** | Table name. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**namespace** | **List&lt;String&gt;** | The namespace identifier as a list of parts. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**version** | **Long** | Table version number. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**location** | **String** | Table storage location (e.g., S3/GCS path).  |  [optional] |
|**tableUri** | **String** | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when &#x60;with_table_uri&#x60; is true.  |  [optional] |
|**schema** | [**JsonArrowSchema**](JsonArrowSchema.md) | Table schema in JSON Arrow format. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access. When &#x60;vend_credentials&#x60; is true, this field may include vended credentials. If the vended credentials are temporary, the &#x60;expires_at_millis&#x60; key should be included to indicate the millisecond timestamp when the credentials expire.  |  [optional] |
|**stats** | [**TableBasicStats**](TableBasicStats.md) | Table statistics. Only populated when &#x60;load_detailed_metadata&#x60; is true.  |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | Optional table metadata as key-value pairs.  |  [optional] |



