# AlterTableBackfillColumnsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** | Table identifier path (namespace + table name) | [optional] 
**branch** | **str** | Branch to target. When not specified, the main branch is used.  | [optional] 
**column** | **str** | Column name to backfill | 
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


