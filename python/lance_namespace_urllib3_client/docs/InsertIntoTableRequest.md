# InsertIntoTableRequest

Request for inserting records into a table, excluding the Arrow IPC stream. Note that this is only used for non-REST implementations. For REST, pass in the information in the following way: - `name`: pass as a part of the path parameter `id` - `namespace`: pass as a part of the path parameter `namespace` - `mode`: pass through query parameter of the same name 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | [optional] 
**namespace** | **List[str]** |  | [optional] 
**mode** | **str** |  | [optional] [default to 'append']

## Example

```python
from lance_namespace_urllib3_client.models.insert_into_table_request import InsertIntoTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of InsertIntoTableRequest from a JSON string
insert_into_table_request_instance = InsertIntoTableRequest.from_json(json)
# print the JSON string representation of the object
print(InsertIntoTableRequest.to_json())

# convert the object into a dict
insert_into_table_request_dict = insert_into_table_request_instance.to_dict()
# create an instance of InsertIntoTableRequest from a dict
insert_into_table_request_from_dict = InsertIntoTableRequest.from_dict(insert_into_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


