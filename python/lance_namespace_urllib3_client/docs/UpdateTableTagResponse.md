# UpdateTableTagResponse

Response for update tag operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.update_table_tag_response import UpdateTableTagResponse

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTableTagResponse from a JSON string
update_table_tag_response_instance = UpdateTableTagResponse.from_json(json)
# print the JSON string representation of the object
print(UpdateTableTagResponse.to_json())

# convert the object into a dict
update_table_tag_response_dict = update_table_tag_response_instance.to_dict()
# create an instance of UpdateTableTagResponse from a dict
update_table_tag_response_from_dict = UpdateTableTagResponse.from_dict(update_table_tag_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


