

# UpdateTableRequest

Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**predicate** | **String** | Optional SQL predicate to filter rows for update |  [optional] |
|**updates** | **List&lt;List&lt;String&gt;&gt;** | List of column updates as [column_name, expression] pairs |  |



