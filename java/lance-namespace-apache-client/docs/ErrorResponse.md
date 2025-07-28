

# ErrorResponse

Common JSON error response model

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**error** | **String** | a brief, human-readable message about the error |  [optional] |
|**code** | **Integer** | HTTP style response code, where 4XX represents client side errors  and 5XX represents server side errors.  For implementations that uses HTTP (e.g. REST namespace), this field can be optional in favor of the HTTP response status code. In case both values exist and do not match, the HTTP response status code should be used.  |  [optional] |
|**type** | **String** | An optional type identifier string for the error. This allows the implementation to specify their internal error type, which could be more detailed than the HTTP standard status code.  |  [optional] |
|**detail** | **String** | an optional human-readable explanation of the error. This can be used to record information such as stack trace.  |  [optional] |
|**instance** | **String** | a string that identifies the specific occurrence of the error. This can be a URI, a request or response ID,  or anything that the implementation can recognize to trace specific occurrence of the error.  |  [optional] |



