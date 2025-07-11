# CountRowsRequest


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **str** |  | [optional] 
**namespace** | **List[str]** |  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.count_rows_request import CountRowsRequest

# TODO update the JSON string below
json = "{}"
# create an instance of CountRowsRequest from a JSON string
count_rows_request_instance = CountRowsRequest.from_json(json)
# print the JSON string representation of the object
print(CountRowsRequest.to_json())

# convert the object into a dict
count_rows_request_dict = count_rows_request_instance.to_dict()
# create an instance of CountRowsRequest from a dict
count_rows_request_from_dict = CountRowsRequest.from_dict(count_rows_request_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


