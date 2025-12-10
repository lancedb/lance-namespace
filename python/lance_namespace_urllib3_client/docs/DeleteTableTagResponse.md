# DeleteTableTagResponse

Response for delete tag operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.delete_table_tag_response import DeleteTableTagResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DeleteTableTagResponse from a JSON string
delete_table_tag_response_instance = DeleteTableTagResponse.from_json(json)
# print the JSON string representation of the object
print(DeleteTableTagResponse.to_json())

# convert the object into a dict
delete_table_tag_response_dict = delete_table_tag_response_instance.to_dict()
# create an instance of DeleteTableTagResponse from a dict
delete_table_tag_response_from_dict = DeleteTableTagResponse.from_dict(delete_table_tag_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


