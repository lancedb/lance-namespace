# BooleanQuery

Boolean query with must, should, and must_not clauses

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**must** | [**List[FtsQuery]**](FtsQuery.md) | Queries that must match (AND) | 
**must_not** | [**List[FtsQuery]**](FtsQuery.md) | Queries that must not match (NOT) | 
**should** | [**List[FtsQuery]**](FtsQuery.md) | Queries that should match (OR) | 

## Example

```python
from lance_namespace_urllib3_client.models.boolean_query import BooleanQuery

# TODO update the JSON string below
json = "{}"
# create an instance of BooleanQuery from a JSON string
boolean_query_instance = BooleanQuery.from_json(json)
# print the JSON string representation of the object
print(BooleanQuery.to_json())

# convert the object into a dict
boolean_query_dict = boolean_query_instance.to_dict()
# create an instance of BooleanQuery from a dict
boolean_query_from_dict = BooleanQuery.from_dict(boolean_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


