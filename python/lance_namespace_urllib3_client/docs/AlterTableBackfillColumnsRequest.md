# AlterTableBackfillColumnsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** | Table identifier path (namespace + table name) | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**column** | **str** | Lance field path.  A field path identifies a field in a Lance schema. Nested fields are addressed by joining path segments with &#x60;.&#x60;. A &#x60;.&#x60; that is not inside backticks is always a path separator, so a field name that contains a literal &#x60;.&#x60; must be written as a backtick-quoted segment, for example &#x60;parent.&#x60;child.with.dot&#x60;&#x60;. Backticks inside a quoted segment are escaped by doubling them.  The canonical display form is the full path from the table schema root to the field, with any segment containing characters other than alphanumeric characters or &#x60;_&#x60; quoted with backticks, for example &#x60;metadata.status&#x60;, &#x60;MetaData.userId&#x60;, and &#x60;meta-data&#x60;.&#x60;user-id&#x60;. Index listings and error messages should use this canonical form.  A leaf field name by itself only identifies a top-level field. Nested fields must be referenced by their full path, which keeps schemas with the same leaf name under different parents unambiguous. If a path cannot be parsed or resolved against the table schema, the implementation should reject the request with InvalidInput or TableColumnNotFound.  | 
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


