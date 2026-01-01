# InsertIntoTableRequest

Request for inserting records into a table, excluding the Arrow IPC stream. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**mode** | **str** | How the insert should behave. Case insensitive, supports both PascalCase and snake_case. Valid values are: - Append (default): insert data to the existing table - Overwrite: remove all data in the table and then insert data to it  | [optional] [default to 'append']

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


