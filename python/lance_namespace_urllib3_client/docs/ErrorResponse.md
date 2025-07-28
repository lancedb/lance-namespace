# ErrorResponse

Common JSON error response model

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | a brief, human-readable message about the error | [optional] 
**code** | **int** | HTTP style response code, where 4XX represents client side errors  and 5XX represents server side errors.  For implementations that uses HTTP (e.g. REST namespace), this field can be optional in favor of the HTTP response status code. In case both values exist and do not match, the HTTP response status code should be used.  | [optional] 
**type** | **str** | An optional type identifier string for the error. This allows the implementation to specify their internal error type, which could be more detailed than the HTTP standard status code.  | [optional] 
**detail** | **str** | an optional human-readable explanation of the error. This can be used to record information such as stack trace.  | [optional] 
**instance** | **str** | a string that identifies the specific occurrence of the error. This can be a URI, a request or response ID,  or anything that the implementation can recognize to trace specific occurrence of the error.  | [optional] 

## Example

```python
from lance_namespace_urllib3_client.models.error_response import ErrorResponse

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


