# CreateNamespace400Response


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | Brief error message | 
**code** | **int** | HTTP status code | 
**type** | **str** | Error type identifier | 
**detail** | **str** | Detailed error explanation | [optional] 
**instance** | **str** | Specific occurrence identifier | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.create_namespace400_response import CreateNamespace400Response

# TODO update the JSON string below
json = "{}"
# create an instance of CreateNamespace400Response from a JSON string
create_namespace400_response_instance = CreateNamespace400Response.from_json(json)
# print the JSON string representation of the object
print(CreateNamespace400Response.to_json())

# convert the object into a dict
create_namespace400_response_dict = create_namespace400_response_instance.to_dict()
# create an instance of CreateNamespace400Response from a dict
create_namespace400_response_from_dict = CreateNamespace400Response.from_dict(create_namespace400_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


