# DescribeTableIndexStats404Response


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | Brief error message | 
**code** | **int** | HTTP status code | 
**type** | **str** | Error type identifier | 
**detail** | **str** | Detailed error explanation | [optional] 
**instance** | **str** | Specific occurrence identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.describe_table_index_stats404_response import DescribeTableIndexStats404Response

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeTableIndexStats404Response from a JSON string
describe_table_index_stats404_response_instance = DescribeTableIndexStats404Response.from_json(json)
# print the JSON string representation of the object
print(DescribeTableIndexStats404Response.to_json())

# convert the object into a dict
describe_table_index_stats404_response_dict = describe_table_index_stats404_response_instance.to_dict()
# create an instance of DescribeTableIndexStats404Response from a dict
describe_table_index_stats404_response_from_dict = DescribeTableIndexStats404Response.from_dict(describe_table_index_stats404_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


