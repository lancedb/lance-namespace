# GetTableStatsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.get_table_stats_request import GetTableStatsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableStatsRequest from a JSON string
get_table_stats_request_instance = GetTableStatsRequest.from_json(json)
# print the JSON string representation of the object
print(GetTableStatsRequest.to_json())

# convert the object into a dict
get_table_stats_request_dict = get_table_stats_request_instance.to_dict()
# create an instance of GetTableStatsRequest from a dict
get_table_stats_request_from_dict = GetTableStatsRequest.from_dict(get_table_stats_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


