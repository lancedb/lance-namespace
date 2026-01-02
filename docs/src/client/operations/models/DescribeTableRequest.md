

# DescribeTableRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**version** | **Long** | Version of the table to describe. If not specified, server should resolve it to the latest version.  |  [optional] |
|**withTableUri** | **Boolean** | Whether to include the table URI in the response. Default is false.  |  [optional] |
|**loadDetailedMetadata** | **Boolean** | Whether to load detailed metadata that requires opening the dataset. When true, the response must include all detailed metadata such as &#x60;version&#x60;, &#x60;schema&#x60;, and &#x60;stats&#x60; which require reading the dataset. When not set, the implementation can decide whether to return detailed metadata and which parts of detailed metadata to return.  |  [optional] |
|**vendCredentials** | **Boolean** | Whether to include vended credentials in the response &#x60;storage_options&#x60;. When true, the implementation should provide vended credentials for accessing storage. When not set, the implementation can decide whether to return vended credentials.  |  [optional] |



