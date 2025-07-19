# BoostQuery

Boost query that scores documents matching positive query higher and negative query lower

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**positive** | [**FtsQuery**](FtsQuery.md) |  | 
**negative** | [**FtsQuery**](FtsQuery.md) |  | 
**negative_boost** | **float** | Boost factor for negative query (default: 0.5) | [optional] [default to 0.5]

## Example

```python
from lance_namespace_urllib3_client.models.boost_query import BoostQuery

# TODO update the JSON string below
json = "{}"
# create an instance of BoostQuery from a JSON string
boost_query_instance = BoostQuery.from_json(json)
# print the JSON string representation of the object
print(BoostQuery.to_json())

# convert the object into a dict
boost_query_dict = boost_query_instance.to_dict()
# create an instance of BoostQuery from a dict
boost_query_from_dict = BoostQuery.from_dict(boost_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


