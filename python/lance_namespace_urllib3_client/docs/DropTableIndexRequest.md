# DropTableIndexRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**index_name** | **str** | Name of the index to drop | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.drop_table_index_request import DropTableIndexRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DropTableIndexRequest from a JSON string
drop_table_index_request_instance = DropTableIndexRequest.from_json(json)
# print the JSON string representation of the object
print(DropTableIndexRequest.to_json())

# convert the object into a dict
drop_table_index_request_dict = drop_table_index_request_instance.to_dict()
# create an instance of DropTableIndexRequest from a dict
drop_table_index_request_from_dict = DropTableIndexRequest.from_dict(drop_table_index_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


