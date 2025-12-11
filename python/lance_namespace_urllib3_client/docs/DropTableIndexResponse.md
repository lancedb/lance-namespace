# DropTableIndexResponse

Response for drop index operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.drop_table_index_response import DropTableIndexResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DropTableIndexResponse from a JSON string
drop_table_index_response_instance = DropTableIndexResponse.from_json(json)
# print the JSON string representation of the object
print(DropTableIndexResponse.to_json())

# convert the object into a dict
drop_table_index_response_dict = drop_table_index_response_instance.to_dict()
# create an instance of DropTableIndexResponse from a dict
drop_table_index_response_from_dict = DropTableIndexResponse.from_dict(drop_table_index_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


