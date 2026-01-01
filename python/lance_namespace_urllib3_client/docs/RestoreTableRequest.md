# RestoreTableRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version to restore to | 

## Example

```python
from lance_namespace_urllib3_client.models.restore_table_request import RestoreTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of RestoreTableRequest from a JSON string
restore_table_request_instance = RestoreTableRequest.from_json(json)
# print the JSON string representation of the object
print(RestoreTableRequest.to_json())

# convert the object into a dict
restore_table_request_dict = restore_table_request_instance.to_dict()
# create an instance of RestoreTableRequest from a dict
restore_table_request_from_dict = RestoreTableRequest.from_dict(restore_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


