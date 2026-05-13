

# MergeInsertIntoTableRequest

Request for merging or inserting records into a table, excluding the Arrow IPC stream. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**on** | **String** | Column name to use for matching rows (required) |  [optional] |
|**whenMatchedUpdateAll** | **Boolean** | Update all columns when rows match |  [optional] |
|**whenMatchedUpdateAllFilt** | **String** | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true |  [optional] |
|**whenNotMatchedInsertAll** | **Boolean** | Insert all columns when rows don&#39;t match |  [optional] |
|**whenNotMatchedBySourceDelete** | **Boolean** | Delete all rows from target table that don&#39;t match a row in the source table |  [optional] |
|**whenNotMatchedBySourceDeleteFilt** | **String** | Delete rows from the target table if there is no match AND the SQL expression evaluates to true |  [optional] |
|**timeout** | **String** | Timeout for the operation (e.g., \&quot;30s\&quot;, \&quot;5m\&quot;) |  [optional] |
|**useIndex** | **Boolean** | Whether to use index for matching rows |  [optional] |



