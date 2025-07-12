# MergeInsertTableResponse

Response from merge insert operation

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**num_updated_rows** | **int** | Number of rows updated | [optional] 
**num_inserted_rows** | **int** | Number of rows inserted | [optional] 
**num_deleted_rows** | **int** | Number of rows deleted (typically 0 for merge insert) | [optional] 
**version** | **int** | The commit version associated with the operation | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.merge_insert_table_response import MergeInsertTableResponse

# TODO update the JSON string below
json = "{}"
# create an instance of MergeInsertTableResponse from a JSON string
merge_insert_table_response_instance = MergeInsertTableResponse.from_json(json)
# print the JSON string representation of the object
print(MergeInsertTableResponse.to_json())

# convert the object into a dict
merge_insert_table_response_dict = merge_insert_table_response_instance.to_dict()
# create an instance of MergeInsertTableResponse from a dict
merge_insert_table_response_from_dict = MergeInsertTableResponse.from_dict(merge_insert_table_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


