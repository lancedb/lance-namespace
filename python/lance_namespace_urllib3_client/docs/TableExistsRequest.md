# TableExistsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version of the table to check existence. If not specified, server should resolve it to the latest version.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.table_exists_request import TableExistsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of TableExistsRequest from a JSON string
table_exists_request_instance = TableExistsRequest.from_json(json)
# print the JSON string representation of the object
print(TableExistsRequest.to_json())

# convert the object into a dict
table_exists_request_dict = table_exists_request_instance.to_dict()
# create an instance of TableExistsRequest from a dict
table_exists_request_from_dict = TableExistsRequest.from_dict(table_exists_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


