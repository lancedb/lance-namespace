# IndexListRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** | The table name | 
**namespace** | **List[str]** | The namespace identifier | 
**version** | **int** | Optional table version to list indexes from | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.index_list_request import IndexListRequest

# TODO update the JSON string below
json = "{}"
# create an instance of IndexListRequest from a JSON string
index_list_request_instance = IndexListRequest.from_json(json)
# print the JSON string representation of the object
print(IndexListRequest.to_json())

# convert the object into a dict
index_list_request_dict = index_list_request_instance.to_dict()
# create an instance of IndexListRequest from a dict
index_list_request_from_dict = IndexListRequest.from_dict(index_list_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


