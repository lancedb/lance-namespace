# DeleteFromTableRequest

Delete data from table based on a SQL predicate. Returns the number of rows that were deleted. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** | The namespace identifier | [optional] 
**predicate** | **str** | SQL predicate to filter rows for deletion | 

## Example

```python
from lance_namespace_urllib3_client.models.delete_from_table_request import DeleteFromTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DeleteFromTableRequest from a JSON string
delete_from_table_request_instance = DeleteFromTableRequest.from_json(json)
# print the JSON string representation of the object
print(DeleteFromTableRequest.to_json())

# convert the object into a dict
delete_from_table_request_dict = delete_from_table_request_instance.to_dict()
# create an instance of DeleteFromTableRequest from a dict
delete_from_table_request_from_dict = DeleteFromTableRequest.from_dict(delete_from_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


