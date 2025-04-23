# WriterVersion


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**library** | **str** | The name of the library that created this file. | [optional] 
**version** | **str** | The version of the library that created this file.  Because we cannot assume that the library is semantically versioned, this is a string.  However, if it is semantically versioned, it should be a valid semver string without any &#39;v&#39; prefix.  For example: &#x60;2.0.0&#x60;, &#x60;2.0.0-rc.1&#x60;.  | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.writer_version import WriterVersion

# TODO update the JSON string below
json = "{}"
# create an instance of WriterVersion from a JSON string
writer_version_instance = WriterVersion.from_json(json)
# print the JSON string representation of the object
print(WriterVersion.to_json())

# convert the object into a dict
writer_version_dict = writer_version_instance.to_dict()
# create an instance of WriterVersion from a dict
writer_version_from_dict = WriterVersion.from_dict(writer_version_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


