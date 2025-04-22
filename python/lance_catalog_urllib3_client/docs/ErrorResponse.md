# ErrorResponse

JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807)

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
from lance_catalog_urllib3_client.models.error_response import ErrorResponse

# TODO update the JSON string below
json = "{}"
# create an instance of ErrorResponse from a JSON string
error_response_instance = ErrorResponse.from_json(json)
# print the JSON string representation of the object
print(ErrorResponse.to_json())

# convert the object into a dict
error_response_dict = error_response_instance.to_dict()
# create an instance of ErrorResponse from a dict
error_response_from_dict = ErrorResponse.from_dict(error_response_dict)
```
[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


