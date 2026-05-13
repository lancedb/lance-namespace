

# CreateTableVersionRequest

Request to create a new table version entry. This supports `put_if_not_exists` semantics, where the operation fails if the version already exists. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** | The table identifier |  [optional] |
|**version** | **Long** | Version number to create |  |
|**manifestPath** | **String** | Path to the manifest file for this version |  |
|**manifestSize** | **Long** | Size of the manifest file in bytes |  [optional] |
|**eTag** | **String** | Optional ETag for the manifest file |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | Optional metadata for the version |  [optional] |
|**namingScheme** | **String** | The naming scheme used for manifest files in the &#x60;_versions/&#x60; directory.  Known values: - &#x60;V1&#x60;: &#x60;_versions/{version}.manifest&#x60; - Simple version-based naming - &#x60;V2&#x60;: &#x60;_versions/{inverted_version}.manifest&#x60; - Zero-padded, reversed version number   (uses &#x60;u64::MAX - version&#x60;) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions.  |  [optional] |



