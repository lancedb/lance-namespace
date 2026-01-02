

# RegisterTableRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**location** | **String** |  |  |
|**mode** | **String** | There are two modes when trying to register a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create (default): the operation fails with 409.   * Overwrite: the existing table registration is replaced with the new registration.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



