# DescribeTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**table** | **str** | Table name. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**namespace** | **List[str]** | The namespace identifier as a list of parts. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**version** | **int** | Table version number. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**location** | **str** | Table storage location (e.g., S3/GCS path).  | [optional] 
**table_uri** | **str** | Table URI. Unlike location, this field must be a complete and valid URI. Only returned when &#x60;with_table_uri&#x60; is true.  | [optional] 
**var_schema** | [**JsonArrowSchema**](JsonArrowSchema.md) | Table schema in JSON Arrow format. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**storage_options** | **Dict[str, str]** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access. When &#x60;vend_credentials&#x60; is true, this field may include vended credentials. If the vended credentials are temporary, the &#x60;expires_at_millis&#x60; key should be included to indicate the millisecond timestamp when the credentials expire.  | [optional] 
**stats** | [**TableBasicStats**](TableBasicStats.md) | Table statistics. Only populated when &#x60;load_detailed_metadata&#x60; is true.  | [optional] 
**metadata** | **Dict[str, str]** | Optional table metadata as key-value pairs.  | [optional] 

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


