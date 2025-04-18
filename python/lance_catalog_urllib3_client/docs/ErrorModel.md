# ErrorModel

JSON error model(based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807)) payload returned in a response with further details on the error

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **str** | a URI identifier that categorizes the error | 
**title** | **str** | a brief, human-readable message about the error | [optional] 
**status** | **int** | HTTP response code, (if present) it must match the actual HTTP code returned by the service | [optional] 
**detail** | **str** | a human-readable explanation of the error | [optional] 
**instance** | **str** | a URI that identifies the specific occurrence of the error | [optional] 

## Example

```python
from lance_catalog_urllib3_client.models.error_model import ErrorModel

# TODO update the JSON string below
json = "{}"
# create an instance of ErrorModel from a JSON string
error_model_instance = ErrorModel.from_json(json)
# print the JSON string representation of the object
print(ErrorModel.to_json())

# convert the object into a dict
error_model_dict = error_model_instance.to_dict()
# create an instance of ErrorModel from a dict
error_model_from_dict = ErrorModel.from_dict(error_model_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


