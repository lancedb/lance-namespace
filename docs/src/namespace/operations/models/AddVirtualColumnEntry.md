

# AddVirtualColumnEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**inputColumns** | **List&lt;String&gt;** | List of input column names for the virtual column |  |
|**dataType** | **Object** | Data type of the virtual column using JSON representation |  |
|**image** | **String** | Docker image to use for the UDF |  |
|**udf** | **String** | Base64 encoded pickled UDF |  |
|**udfName** | **String** | Name of the UDF |  |
|**udfVersion** | **String** | Version of the UDF |  |
|**udfBackend** | **String** | UDF backend type (e.g. DockerUDFSpecV1) |  [optional] |
|**autoBackfill** | **Boolean** | Whether to automatically backfill the column after creation |  [optional] |
|**manifest** | **String** | JSON-serialized manifest for the UDF environment |  [optional] |
|**manifestChecksum** | **String** | SHA-256 checksum of the manifest content |  [optional] |
|**fieldMetadata** | **Map&lt;String, String&gt;** | User-supplied field metadata (string key-value pairs) |  [optional] |



