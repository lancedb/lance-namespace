# DeclareTableRequest

Request for declaring a table. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**location** | **str** | Optional storage location for the table. If not provided, the namespace implementation should determine the table location.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.declare_table_request import DeclareTableRequest

# TODO update the JSON string below
json = "{}"
# create an instance of DeclareTableRequest from a JSON string
declare_table_request_instance = DeclareTableRequest.from_json(json)
# print the JSON string representation of the object
print(DeclareTableRequest.to_json())

# convert the object into a dict
declare_table_request_dict = declare_table_request_instance.to_dict()
# create an instance of DeclareTableRequest from a dict
declare_table_request_from_dict = DeclareTableRequest.from_dict(declare_table_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


