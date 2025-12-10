# RestoreTableResponse

Response for restore table operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.restore_table_response import RestoreTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of RestoreTableResponse from a JSON string
restore_table_response_instance = RestoreTableResponse.from_json(json)
# print the JSON string representation of the object
print(RestoreTableResponse.to_json())

# convert the object into a dict
restore_table_response_dict = restore_table_response_instance.to_dict()
# create an instance of RestoreTableResponse from a dict
restore_table_response_from_dict = RestoreTableResponse.from_dict(restore_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


