

# BatchCommitTablesRequest

Request to atomically commit a batch of table operations. This replaces `BatchCreateTableVersionsRequest` with a more general interface that supports mixed operations (DeclareTable, CreateTableVersion, DeleteTableVersions, DeregisterTable) within a single atomic transaction at the metadata layer.  All operations are committed atomically: either all succeed or none are applied. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**context** | **Map&lt;String, String&gt;** | Arbitrary context for a request as key-value pairs. How to use the context is custom to the specific implementation.  REST NAMESPACE ONLY Context entries are passed via HTTP headers using the naming convention &#x60;x-lance-ctx-&lt;key&gt;: &lt;value&gt;&#x60;. For example, a context entry &#x60;{\&quot;trace_id\&quot;: \&quot;abc123\&quot;}&#x60; would be sent as the header &#x60;x-lance-ctx-trace_id: abc123&#x60;.  |  [optional] |
|**operations** | [**List&lt;CommitTableOperation&gt;**](CommitTableOperation.md) | List of operations to commit atomically. Supported operation types: DeclareTable, CreateTableVersion, DeleteTableVersions, DeregisterTable.  |  |



