# IndexStatsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**distance_type** | **str** | Distance type for vector indexes | [optional] 
**index_type** | **str** | Type of the index | [optional] 
**num_indexed_rows** | **int** | Number of indexed rows | [optional] 
**num_unindexed_rows** | **int** | Number of unindexed rows | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.index_stats_response import IndexStatsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of IndexStatsResponse from a JSON string
index_stats_response_instance = IndexStatsResponse.from_json(json)
# print the JSON string representation of the object
print(IndexStatsResponse.to_json())

# convert the object into a dict
index_stats_response_dict = index_stats_response_instance.to_dict()
# create an instance of IndexStatsResponse from a dict
index_stats_response_from_dict = IndexStatsResponse.from_dict(index_stats_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


