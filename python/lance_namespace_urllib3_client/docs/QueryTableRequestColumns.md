# QueryTableRequestColumns

Optional columns to return. Provide either column_names or column_aliases, not both. 

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**column_names** | **List[str]** | List of column names to return | [optional] 
**column_aliases** | **Dict[str, str]** | Object mapping output aliases to source column names | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.query_table_request_columns import QueryTableRequestColumns

# TODO update the JSON string below
json = "{}"
# create an instance of QueryTableRequestColumns from a JSON string
query_table_request_columns_instance = QueryTableRequestColumns.from_json(json)
# print the JSON string representation of the object
print(QueryTableRequestColumns.to_json())

# convert the object into a dict
query_table_request_columns_dict = query_table_request_columns_instance.to_dict()
# create an instance of QueryTableRequestColumns from a dict
query_table_request_columns_from_dict = QueryTableRequestColumns.from_dict(query_table_request_columns_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


