# GetTableTagVersionResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**version** | **int** | version number that the tag points to | 

## Example

```python
from lance_namespace_urllib3_client.models.get_table_tag_version_response import GetTableTagVersionResponse

# TODO update the JSON string below
json = "{}"
# create an instance of GetTableTagVersionResponse from a JSON string
get_table_tag_version_response_instance = GetTableTagVersionResponse.from_json(json)
# print the JSON string representation of the object
print(GetTableTagVersionResponse.to_json())

# convert the object into a dict
get_table_tag_version_response_dict = get_table_tag_version_response_instance.to_dict()
# create an instance of GetTableTagVersionResponse from a dict
get_table_tag_version_response_from_dict = GetTableTagVersionResponse.from_dict(get_table_tag_version_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


