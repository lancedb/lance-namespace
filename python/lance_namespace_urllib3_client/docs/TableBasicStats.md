# TableBasicStats


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**num_deleted_rows** | **int** | Number of deleted rows in the table | 
**num_fragments** | **int** | Number of fragments in the table | 

## Example

```python
from lance_namespace_urllib3_client.models.table_basic_stats import TableBasicStats

# TODO update the JSON string below
json = "{}"
# create an instance of TableBasicStats from a JSON string
table_basic_stats_instance = TableBasicStats.from_json(json)
# print the JSON string representation of the object
print(TableBasicStats.to_json())

# convert the object into a dict
table_basic_stats_dict = table_basic_stats_instance.to_dict()
# create an instance of TableBasicStats from a dict
table_basic_stats_from_dict = TableBasicStats.from_dict(table_basic_stats_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


