

# CreateTableVersionRequest

Request to create a new table version entry. This supports `put_if_not_exists` semantics, where the operation fails if the version already exists. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** | The table identifier |  [optional] |
|**version** | **Long** | Version number to create |  |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**manifestPath** | **String** | Path to the manifest file for this version |  |
|**manifestSize** | **Long** | Size of the manifest file in bytes |  [optional] |
|**eTag** | **String** | Optional ETag for the manifest file |  [optional] |
|**metadata** | **Map&lt;String, String&gt;** | Optional metadata for the version |  [optional] |
|**namingScheme** | **String** | The naming scheme used for manifest files in the &#x60;_versions/&#x60; directory.  Known values: - &#x60;V1&#x60;: &#x60;_versions/{version}.manifest&#x60; - Simple version-based naming - &#x60;V2&#x60;: &#x60;_versions/{inverted_version}.manifest&#x60; - Zero-padded, reversed version number   (uses &#x60;u64::MAX - version&#x60;) for O(1) lookup of latest version on object stores  V2 is preferred for new tables as it enables efficient latest-version discovery without needing to list all versions.  |  [optional] |



