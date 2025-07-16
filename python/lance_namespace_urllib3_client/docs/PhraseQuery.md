# PhraseQuery


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column** | **str** |  | [optional] 
**slop** | **int** |  | [optional] 
**terms** | **str** |  | 

## Example

```python
from lance_namespace_urllib3_client.models.phrase_query import PhraseQuery

# TODO update the JSON string below
json = "{}"
# create an instance of PhraseQuery from a JSON string
phrase_query_instance = PhraseQuery.from_json(json)
# print the JSON string representation of the object
print(PhraseQuery.to_json())

# convert the object into a dict
phrase_query_dict = phrase_query_instance.to_dict()
# create an instance of PhraseQuery from a dict
phrase_query_from_dict = PhraseQuery.from_dict(phrase_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


