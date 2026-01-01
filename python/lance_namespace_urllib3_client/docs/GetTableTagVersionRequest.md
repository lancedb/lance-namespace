# GetTableTagVersionRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**tag** | **str** | Name of the tag to get version for | 

## Example

```python
from lance_namespace_urllib3_client.models.get_table_tag_version_request import GetTableTagVersionRequest

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableTagVersionRequest from a JSON string
get_table_tag_version_request_instance = GetTableTagVersionRequest.from_json(json)
# print the JSON string representation of the object
print(GetTableTagVersionRequest.to_json())

# convert the object into a dict
get_table_tag_version_request_dict = get_table_tag_version_request_instance.to_dict()
# create an instance of GetTableTagVersionRequest from a dict
get_table_tag_version_request_from_dict = GetTableTagVersionRequest.from_dict(get_table_tag_version_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


