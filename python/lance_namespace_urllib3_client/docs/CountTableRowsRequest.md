# CountTableRowsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**identity** | [**Identity**](Identity.md) |  | [optional] 
**id** | **List[str]** |  | [optional] 
**version** | **int** | Version of the table to describe. If not specified, server should resolve it to the latest version.  | [optional] 
**predicate** | **str** | Optional SQL predicate to filter rows for counting  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.count_table_rows_request import CountTableRowsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CountTableRowsRequest from a JSON string
count_table_rows_request_instance = CountTableRowsRequest.from_json(json)
# print the JSON string representation of the object
print(CountTableRowsRequest.to_json())

# convert the object into a dict
count_table_rows_request_dict = count_table_rows_request_instance.to_dict()
# create an instance of CountTableRowsRequest from a dict
count_table_rows_request_from_dict = CountTableRowsRequest.from_dict(count_table_rows_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


