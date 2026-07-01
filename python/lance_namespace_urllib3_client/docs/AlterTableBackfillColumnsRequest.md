# AlterTableBackfillColumnsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**context** | **Dict[str, str]** | Arbitrary context as key-value pairs. How to use the context is custom to the specific implementation.  On a request, it carries caller-provided context to the implementation. On a response, it carries implementation-provided context back to the caller.  REST NAMESPACE ONLY Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP   request header with the prefix stripped. For example, the entry   &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the request header   &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response header is returned as an entry whose key is the   header name prefixed with &#x60;header.&#x60;. For example, the response header   &#x60;x-request-id: abc123&#x60; is returned as the entry &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.  | [optional] 
**id** | **List[str]** | Table identifier path (namespace + table name) | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**column** | **str** | Lance field path to backfill. Nested fields use dot-separated segments; use backtick-quoted segments for literal dots and double backticks inside quoted segments. Use canonical full paths for display and errors; leaf names alone only identify top-level fields; invalid or unresolved paths should return InvalidInput or TableColumnNotFound. | 
**where** | **str** | Optional WHERE clause filter | [optional] 
**concurrency** | **int** | Optional concurrency override | [optional] 
**intra_applier_concurrency** | **int** | Optional intra-applier concurrency override | [optional] 
**min_checkpoint_size** | **int** | Optional minimum checkpoint size | [optional] 
**max_checkpoint_size** | **int** | Optional maximum checkpoint size | [optional] 
**batch_checkpoint_flush_interval_seconds** | **float** | Optional batch checkpoint flush interval in seconds | [optional] 
**read_version** | **int** | Optional table version to read from | [optional] 
**task_size** | **int** | Optional task size | [optional] 
**num_frags** | **int** | Optional number of fragments | [optional] 
**checkpoint_size** | **int** | Optional checkpoint size | [optional] 
**commit_granularity** | **int** | Optional commit granularity | [optional] 
**cluster** | **str** | Optional cluster name | [optional] 
**manifest** | **str** | Optional manifest name | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.alter_table_backfill_columns_request import AlterTableBackfillColumnsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of AlterTableBackfillColumnsRequest from a JSON string
alter_table_backfill_columns_request_instance = AlterTableBackfillColumnsRequest.from_json(json)
# print the JSON string representation of the object
print(AlterTableBackfillColumnsRequest.to_json())

# convert the object into a dict
alter_table_backfill_columns_request_dict = alter_table_backfill_columns_request_instance.to_dict()
# create an instance of AlterTableBackfillColumnsRequest from a dict
alter_table_backfill_columns_request_from_dict = AlterTableBackfillColumnsRequest.from_dict(alter_table_backfill_columns_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


