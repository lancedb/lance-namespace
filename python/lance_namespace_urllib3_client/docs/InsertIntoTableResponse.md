# InsertIntoTableResponse

Response from inserting records into a table

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.insert_into_table_response import InsertIntoTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of InsertIntoTableResponse from a JSON string
insert_into_table_response_instance = InsertIntoTableResponse.from_json(json)
# print the JSON string representation of the object
print(InsertIntoTableResponse.to_json())

# convert the object into a dict
insert_into_table_response_dict = insert_into_table_response_instance.to_dict()
# create an instance of InsertIntoTableResponse from a dict
insert_into_table_response_from_dict = InsertIntoTableResponse.from_dict(insert_into_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


