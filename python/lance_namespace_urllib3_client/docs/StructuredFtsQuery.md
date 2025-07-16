# StructuredFtsQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**query** | [**FtsQuery**](FtsQuery.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.structured_fts_query import StructuredFtsQuery

# TODO update the JSON string below
json = "{}"
# create an instance of StructuredFtsQuery from a JSON string
structured_fts_query_instance = StructuredFtsQuery.from_json(json)
# print the JSON string representation of the object
print(StructuredFtsQuery.to_json())

# convert the object into a dict
structured_fts_query_dict = structured_fts_query_instance.to_dict()
# create an instance of StructuredFtsQuery from a dict
structured_fts_query_from_dict = StructuredFtsQuery.from_dict(structured_fts_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


