# GetTableStatsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**num_rows** | **int** | Total number of rows in the table | 
**size_bytes** | **int** | Total size of the table in bytes | 
**num_fragments** | **int** | Number of data fragments | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.get_table_stats_response import GetTableStatsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableStatsResponse from a JSON string
get_table_stats_response_instance = GetTableStatsResponse.from_json(json)
# print the JSON string representation of the object
print(GetTableStatsResponse.to_json())

# convert the object into a dict
get_table_stats_response_dict = get_table_stats_response_instance.to_dict()
# create an instance of GetTableStatsResponse from a dict
get_table_stats_response_from_dict = GetTableStatsResponse.from_dict(get_table_stats_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


