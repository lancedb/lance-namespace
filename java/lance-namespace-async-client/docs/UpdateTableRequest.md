

# UpdateTableRequest

Each update consists of a field path and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**branch** | **String** | Branch to target. When not specified, the main branch is used.  |  [optional] |
|**predicate** | **String** | Optional SQL predicate to filter rows for update. Field references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. |  [optional] |
|**updates** | **List&lt;List&lt;String&gt;&gt;** | List of field updates as [field_path, expression] pairs. Field paths and expression references must use Lance field path syntax: nested fields use dot-separated segments, literal dots require backtick-quoted segments, and backticks inside quoted segments are doubled. |  |
|**properties** | **Map&lt;String, String&gt;** | Properties stored on the table, if supported by the implementation.  |  [optional] |



