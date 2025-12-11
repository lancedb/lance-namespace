# CreateTableTagResponse

Response for create tag operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_tag_response import CreateTableTagResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableTagResponse from a JSON string
create_table_tag_response_instance = CreateTableTagResponse.from_json(json)
# print the JSON string representation of the object
print(CreateTableTagResponse.to_json())

# convert the object into a dict
create_table_tag_response_dict = create_table_tag_response_instance.to_dict()
# create an instance of CreateTableTagResponse from a dict
create_table_tag_response_from_dict = CreateTableTagResponse.from_dict(create_table_tag_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


