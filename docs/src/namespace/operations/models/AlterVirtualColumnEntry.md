

# AlterVirtualColumnEntry


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**inputColumns** | **List&lt;String&gt;** | List of input column names for the virtual column (optional) |  [optional] |
|**image** | **String** | Docker image to use for the UDF (optional) |  [optional] |
|**udf** | **String** | Base64 encoded pickled UDF (optional) |  [optional] |
|**udfName** | **String** | Name of the UDF (optional) |  [optional] |
|**udfVersion** | **String** | Version of the UDF (optional) |  [optional] |
|**udfBackend** | **String** | UDF backend type (e.g. DockerUDFSpecV1) (optional) |  [optional] |
|**autoBackfill** | **Boolean** | Whether to automatically backfill the column (optional) |  [optional] |
|**manifest** | **String** | JSON-serialized manifest for the UDF environment (optional) |  [optional] |
|**manifestChecksum** | **String** | SHA-256 checksum of the manifest content (optional) |  [optional] |
|**fieldMetadata** | **Map&lt;String, String&gt;** | User-supplied field metadata (optional) |  [optional] |



