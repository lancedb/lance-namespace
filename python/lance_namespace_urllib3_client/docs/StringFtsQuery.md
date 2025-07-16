# StringFtsQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**columns** | **List[str]** |  | [optional] 
**query** | **str** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.string_fts_query import StringFtsQuery

# TODO update the JSON string below
json = "{}"
# create an instance of StringFtsQuery from a JSON string
string_fts_query_instance = StringFtsQuery.from_json(json)
# print the JSON string representation of the object
print(StringFtsQuery.to_json())

# convert the object into a dict
string_fts_query_dict = string_fts_query_instance.to_dict()
# create an instance of StringFtsQuery from a dict
string_fts_query_from_dict = StringFtsQuery.from_dict(string_fts_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


