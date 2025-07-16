# FtsQueryInput


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**columns** | **List[str]** |  | [optional] 
**query** | [**FtsQuery**](FtsQuery.md) |  | 

## Example

```python
from lance_namespace_urllib3_client.models.fts_query_input import FtsQueryInput

# TODO update the JSON string below
json = "{}"
# create an instance of FtsQueryInput from a JSON string
fts_query_input_instance = FtsQueryInput.from_json(json)
# print the JSON string representation of the object
print(FtsQueryInput.to_json())

# convert the object into a dict
fts_query_input_dict = fts_query_input_instance.to_dict()
# create an instance of FtsQueryInput from a dict
fts_query_input_from_dict = FtsQueryInput.from_dict(fts_query_input_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


