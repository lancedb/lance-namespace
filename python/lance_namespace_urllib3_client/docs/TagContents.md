# TagContents


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**branch** | **str** | Branch name that the tag was created on (if any) | [optional] 
**version** | **int** | Version number that the tag points to | 
**manifest_size** | **int** | Size of the manifest file in bytes | 

## Example

```python
from lance_namespace_urllib3_client.models.tag_contents import TagContents

# TODO update the JSON string below
json = "{}"
# create an instance of TagContents from a JSON string
tag_contents_instance = TagContents.from_json(json)
# print the JSON string representation of the object
print(TagContents.to_json())

# convert the object into a dict
tag_contents_dict = tag_contents_instance.to_dict()
# create an instance of TagContents from a dict
tag_contents_from_dict = TagContents.from_dict(tag_contents_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


