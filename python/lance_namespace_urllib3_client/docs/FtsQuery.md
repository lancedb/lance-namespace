# FtsQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**match** | [**MatchQuery**](MatchQuery.md) |  | 
**phrase** | [**PhraseQuery**](PhraseQuery.md) |  | 
**boost** | [**BoostQuery**](BoostQuery.md) |  | 
**multi_match** | [**MultiMatchQuery**](MultiMatchQuery.md) |  | 
**boolean** | [**BooleanQuery**](BooleanQuery.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.fts_query import FtsQuery

# TODO update the JSON string below
json = "{}"
# create an instance of FtsQuery from a JSON string
fts_query_instance = FtsQuery.from_json(json)
# print the JSON string representation of the object
print(FtsQuery.to_json())

# convert the object into a dict
fts_query_dict = fts_query_instance.to_dict()
# create an instance of FtsQuery from a dict
fts_query_from_dict = FtsQuery.from_dict(fts_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


