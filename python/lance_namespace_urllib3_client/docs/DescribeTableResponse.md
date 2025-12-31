# DescribeTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**table** | **str** | Table name. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**namespace** | **List[str]** | The namespace identifier as a list of parts. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**version** | **int** | Table version number. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**location** | **str** | Table storage location (e.g., S3/GCS path). This is the only required field and is always returned.  | 
**table_uri** | **str** | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when &#x60;with_table_uri&#x60; is true.  | [optional] 
**var_schema** | [**JsonArrowSchema**](JsonArrowSchema.md) | Table schema in JSON Arrow format. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**storage_options** | **Dict[str, str]** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  | [optional] 
**stats** | [**TableBasicStats**](TableBasicStats.md) | Table statistics. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 

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


