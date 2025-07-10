# DescribeTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**namespace** | **List[str]** |  | 
**location** | **str** |  | 
**properties** | **Dict[str, str]** |  | [optional] 
**var_schema** | **object** |  | 
**stats** | [**TableBasicStats**](TableBasicStats.md) |  | 
**table** | **str** |  | 
**table_uri** | **str** | Table URI, optional | [optional] 
**version** | **int** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.describe_table_response import DescribeTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeTableResponse from a JSON string
describe_table_response_instance = DescribeTableResponse.from_json(json)
# print the JSON string representation of the object
print(DescribeTableResponse.to_json())

# convert the object into a dict
describe_table_response_dict = describe_table_response_instance.to_dict()
# create an instance of DescribeTableResponse from a dict
describe_table_response_from_dict = DescribeTableResponse.from_dict(describe_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


