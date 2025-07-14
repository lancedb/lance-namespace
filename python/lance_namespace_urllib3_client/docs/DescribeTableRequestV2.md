# DescribeTableRequestV2


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | 
**namespace** | **List[str]** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.describe_table_request_v2 import DescribeTableRequestV2

# TODO update the JSON string below
json = "{}"
# create an instance of DescribeTableRequestV2 from a JSON string
describe_table_request_v2_instance = DescribeTableRequestV2.from_json(json)
# print the JSON string representation of the object
print(DescribeTableRequestV2.to_json())

# convert the object into a dict
describe_table_request_v2_dict = describe_table_request_v2_instance.to_dict()
# create an instance of DescribeTableRequestV2 from a dict
describe_table_request_v2_from_dict = DescribeTableRequestV2.from_dict(describe_table_request_v2_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


