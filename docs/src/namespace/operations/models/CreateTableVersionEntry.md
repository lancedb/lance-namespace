

# CreateTableVersionEntry

An entry for creating a new table version in a batch operation. This supports `put_if_not_exists` semantics, where the operation fails if the version already exists. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** | The table identifier |  |
|**version** | **Long** | Version number to create |  |
|**manifestPath** | **String** | Path to the manifest file for this version |  |
|**manifestSize** | **Long** | Size of the manifest file in bytes |  [optional] |
|**eTag** | **String** | Optional ETag for the manifest file |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | Optional metadata for the version |  [optional] |
|**namingScheme** | **String** | The naming scheme used for manifest files in the &#x60;_versions/&#x60; directory.  Known values: - &#x60;V1&#x60;: &#x60;_versions/{version}.manifest&#x60; - Simple version-based naming - &#x60;V2&#x60;: &#x60;_versions/{inverted_version}.manifest&#x60; - Zero-padded, reversed version number   (uses &#x60;u64::MAX - version&#x60;) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions.  |  [optional] |



