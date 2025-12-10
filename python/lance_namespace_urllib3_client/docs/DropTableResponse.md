# DropTableResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**transaction_id** | **str** | Optional transaction identifier | [optional] 
**id** | **List[str]** |  | [optional] 
**location** | **str** |  | [optional] 
**properties** | **Dict[str, str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.drop_table_response import DropTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of DropTableResponse from a JSON string
drop_table_response_instance = DropTableResponse.from_json(json)
# print the JSON string representation of the object
print(DropTableResponse.to_json())

# convert the object into a dict
drop_table_response_dict = drop_table_response_instance.to_dict()
# create an instance of DropTableResponse from a dict
drop_table_response_from_dict = DropTableResponse.from_dict(drop_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


