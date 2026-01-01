# UpdateTableTagRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**tag** | **str** | Name of the tag to update | 
**version** | **int** | New version number for the tag to point to | 

## Example

```python
from lance_namespace_urllib3_client.models.update_table_tag_request import UpdateTableTagRequest

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTableTagRequest from a JSON string
update_table_tag_request_instance = UpdateTableTagRequest.from_json(json)
# print the JSON string representation of the object
print(UpdateTableTagRequest.to_json())

# convert the object into a dict
update_table_tag_request_dict = update_table_tag_request_instance.to_dict()
# create an instance of UpdateTableTagRequest from a dict
update_table_tag_request_from_dict = UpdateTableTagRequest.from_dict(update_table_tag_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


