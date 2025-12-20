# ErrorResponse

Common JSON error response model

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | **str** | A brief, human-readable message about the error. | [optional] 
**code** | **int** | Lance Namespace error code identifying the error type.  Error codes:   0 - Unsupported: Operation not supported by this backend   1 - NamespaceNotFound: The specified namespace does not exist   2 - NamespaceAlreadyExists: A namespace with this name already exists   3 - NamespaceNotEmpty: Namespace contains tables or child namespaces   4 - TableNotFound: The specified table does not exist   5 - TableAlreadyExists: A table with this name already exists   6 - TableIndexNotFound: The specified table index does not exist   7 - TableIndexAlreadyExists: A table index with this name already exists   8 - TableTagNotFound: The specified table tag does not exist   9 - TableTagAlreadyExists: A table tag with this name already exists   10 - TransactionNotFound: The specified transaction does not exist   11 - TableVersionNotFound: The specified table version does not exist   12 - TableColumnNotFound: The specified table column does not exist   13 - InvalidInput: Malformed request or invalid parameters   14 - ConcurrentModification: Optimistic concurrency conflict   15 - PermissionDenied: User lacks permission for this operation   16 - Unauthenticated: Authentication credentials are missing or invalid   17 - ServiceUnavailable: Service is temporarily unavailable   18 - Internal: Unexpected server/implementation error   19 - InvalidTableState: Table is in an invalid state for the operation   20 - TableSchemaValidationError: Table schema validation failed  | 
**detail** | **str** | An optional human-readable explanation of the error. This can be used to record additional information such as stack trace.  | [optional] 
**instance** | **str** | A string that identifies the specific occurrence of the error. This can be a URI, a request or response ID, or anything that the implementation can recognize to trace specific occurrence of the error.  | [optional] 

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


