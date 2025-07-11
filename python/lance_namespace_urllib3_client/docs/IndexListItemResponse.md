# IndexListItemResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**index_name** | **str** | Name of the index | 
**index_uuid** | **str** | Unique identifier for the index | 
**columns** | **List[str]** | Columns covered by this index | 
**status** | **str** | Current status of the index | 

## Example

```python
from lance_namespace_urllib3_client.models.index_list_item_response import IndexListItemResponse

# TODO update the JSON string below
json = "{}"
# create an instance of IndexListItemResponse from a JSON string
index_list_item_response_instance = IndexListItemResponse.from_json(json)
# print the JSON string representation of the object
print(IndexListItemResponse.to_json())

# convert the object into a dict
index_list_item_response_dict = index_list_item_response_instance.to_dict()
# create an instance of IndexListItemResponse from a dict
index_list_item_response_from_dict = IndexListItemResponse.from_dict(index_list_item_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


