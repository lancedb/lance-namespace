# MultiMatchQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**match_queries** | [**List[MatchQuery]**](MatchQuery.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.multi_match_query import MultiMatchQuery

# TODO update the JSON string below
json = "{}"
# create an instance of MultiMatchQuery from a JSON string
multi_match_query_instance = MultiMatchQuery.from_json(json)
# print the JSON string representation of the object
print(MultiMatchQuery.to_json())

# convert the object into a dict
multi_match_query_dict = multi_match_query_instance.to_dict()
# create an instance of MultiMatchQuery from a dict
multi_match_query_from_dict = MultiMatchQuery.from_dict(multi_match_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


