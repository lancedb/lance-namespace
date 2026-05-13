

# TableVersion


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**version** | **Long** | Version number |  |
|**manifestPath** | **String** | Path to the manifest file for this version. |  |
|**manifestSize** | **Long** | Size of the manifest file in bytes |  [optional] |
|**eTag** | **String** | Optional ETag for optimistic concurrency control. Useful for S3 and similar object stores.  |  [optional] |
|**timestampMillis** | **Long** | Timestamp when the version was created, in milliseconds since epoch (Unix time) |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | Optional key-value pairs of metadata |  [optional] |



