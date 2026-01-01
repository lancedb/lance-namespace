# UpdateTableRequest

Each update consists of a column name and an SQL expression that will be evaluated against the current row's value. Optionally, a predicate can be provided to filter which rows to update. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**predicate** | **str** | Optional SQL predicate to filter rows for update | [optional] 
**updates** | **List[List[str]]** | List of column updates as [column_name, expression] pairs | 

## Example

```python
from lance_namespace_urllib3_client.models.update_table_request import UpdateTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of UpdateTableRequest from a JSON string
update_table_request_instance = UpdateTableRequest.from_json(json)
# print the JSON string representation of the object
print(UpdateTableRequest.to_json())

# convert the object into a dict
update_table_request_dict = update_table_request_instance.to_dict()
# create an instance of UpdateTableRequest from a dict
update_table_request_from_dict = UpdateTableRequest.from_dict(update_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


