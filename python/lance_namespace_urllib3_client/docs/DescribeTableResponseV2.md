# DescribeTableResponseV2


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**namespace** | **List[str]** |  | 
**location** | **str** |  | 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.describe_table_response_v2 import DescribeTableResponseV2

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeTableResponseV2 from a JSON string
describe_table_response_v2_instance = DescribeTableResponseV2.from_json(json)
# print the JSON string representation of the object
print(DescribeTableResponseV2.to_json())

# convert the object into a dict
describe_table_response_v2_dict = describe_table_response_v2_instance.to_dict()
# create an instance of DescribeTableResponseV2 from a dict
describe_table_response_v2_from_dict = DescribeTableResponseV2.from_dict(describe_table_response_v2_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


