# DescribeTableIndexStatsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Optional table version to get stats for | [optional] 
**index_name** | **str** | Name of the index | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.describe_table_index_stats_request import DescribeTableIndexStatsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeTableIndexStatsRequest from a JSON string
describe_table_index_stats_request_instance = DescribeTableIndexStatsRequest.from_json(json)
# print the JSON string representation of the object
print(DescribeTableIndexStatsRequest.to_json())

# convert the object into a dict
describe_table_index_stats_request_dict = describe_table_index_stats_request_instance.to_dict()
# create an instance of DescribeTableIndexStatsRequest from a dict
describe_table_index_stats_request_from_dict = DescribeTableIndexStatsRequest.from_dict(describe_table_index_stats_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


