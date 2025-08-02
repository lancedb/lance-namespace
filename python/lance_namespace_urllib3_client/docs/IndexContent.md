# IndexContent


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**index_name** | **str** | Name of the index | 
**index_uuid** | **str** | Unique identifier for the index | 
**columns** | **List[str]** | Columns covered by this index | 
**status** | **str** | Current status of the index | 

## Example

```python
from lance_namespace_urllib3_client.models.index_content import IndexContent

# TODO update the JSON string below
json = "{}"
# create an instance of IndexContent from a JSON string
index_content_instance = IndexContent.from_json(json)
# print the JSON string representation of the object
print(IndexContent.to_json())

# convert the object into a dict
index_content_dict = index_content_instance.to_dict()
# create an instance of IndexContent from a dict
index_content_from_dict = IndexContent.from_dict(index_content_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


