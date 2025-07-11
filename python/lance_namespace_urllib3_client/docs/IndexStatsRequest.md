# IndexStatsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 
**version** | **int** | Optional table version to get stats for | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.index_stats_request import IndexStatsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of IndexStatsRequest from a JSON string
index_stats_request_instance = IndexStatsRequest.from_json(json)
# print the JSON string representation of the object
print(IndexStatsRequest.to_json())

# convert the object into a dict
index_stats_request_dict = index_stats_request_instance.to_dict()
# create an instance of IndexStatsRequest from a dict
index_stats_request_from_dict = IndexStatsRequest.from_dict(index_stats_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


