# DeleteTableTagRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**tag** | **str** | Name of the tag to delete | 

## Example

```python
from lance_namespace_urllib3_client.models.delete_table_tag_request import DeleteTableTagRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DeleteTableTagRequest from a JSON string
delete_table_tag_request_instance = DeleteTableTagRequest.from_json(json)
# print the JSON string representation of the object
print(DeleteTableTagRequest.to_json())

# convert the object into a dict
delete_table_tag_request_dict = delete_table_tag_request_instance.to_dict()
# create an instance of DeleteTableTagRequest from a dict
delete_table_tag_request_from_dict = DeleteTableTagRequest.from_dict(delete_table_tag_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


