# ListTableTagsResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**tags** | [**Dict[str, TagContents]**](TagContents.md) | Map of tag names to their contents | 

## Example

```python
from lance_namespace_urllib3_client.models.list_table_tags_response import ListTableTagsResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ListTableTagsResponse from a JSON string
list_table_tags_response_instance = ListTableTagsResponse.from_json(json)
# print the JSON string representation of the object
print(ListTableTagsResponse.to_json())

# convert the object into a dict
list_table_tags_response_dict = list_table_tags_response_instance.to_dict()
# create an instance of ListTableTagsResponse from a dict
list_table_tags_response_from_dict = ListTableTagsResponse.from_dict(list_table_tags_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


