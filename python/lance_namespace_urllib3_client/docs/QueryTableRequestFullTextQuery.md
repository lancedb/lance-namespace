# QueryTableRequestFullTextQuery

Optional full-text search query. Provide either string_query or structured_query, not both.

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**string_query** | [**StringFtsQuery**](StringFtsQuery.md) |  | [optional] 
**structured_query** | [**StructuredFtsQuery**](StructuredFtsQuery.md) |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.query_table_request_full_text_query import QueryTableRequestFullTextQuery

# TODO update the JSON string below
json = "{}"
# create an instance of QueryTableRequestFullTextQuery from a JSON string
query_table_request_full_text_query_instance = QueryTableRequestFullTextQuery.from_json(json)
# print the JSON string representation of the object
print(QueryTableRequestFullTextQuery.to_json())

# convert the object into a dict
query_table_request_full_text_query_dict = query_table_request_full_text_query_instance.to_dict()
# create an instance of QueryTableRequestFullTextQuery from a dict
query_table_request_full_text_query_from_dict = QueryTableRequestFullTextQuery.from_dict(query_table_request_full_text_query_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


