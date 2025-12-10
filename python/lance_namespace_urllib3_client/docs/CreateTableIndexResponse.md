# CreateTableIndexResponse

Response for create index operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_table_index_response import CreateTableIndexResponse

# TODO update the JSON string below
json = "{}"
# create an instance of CreateTableIndexResponse from a JSON string
create_table_index_response_instance = CreateTableIndexResponse.from_json(json)
# print the JSON string representation of the object
print(CreateTableIndexResponse.to_json())

# convert the object into a dict
create_table_index_response_dict = create_table_index_response_instance.to_dict()
# create an instance of CreateTableIndexResponse from a dict
create_table_index_response_from_dict = CreateTableIndexResponse.from_dict(create_table_index_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


