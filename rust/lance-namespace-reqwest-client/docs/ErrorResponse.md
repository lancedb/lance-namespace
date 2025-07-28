# ErrorResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**error** | Option<**String**> | a brief, human-readable message about the error | [optional]
**code** | Option<**i32**> | HTTP style response code, where 4XX represents client side errors  and 5XX represents server side errors.  For implementations that uses HTTP (e.g. REST namespace), this field can be optional in favor of the HTTP response status code. In case both values exist and do not match, the HTTP response status code should be used.  | [optional]
**r#type** | Option<**String**> | An optional type identifier string for the error. This allows the implementation to specify their internal error type, which could be more detailed than the HTTP standard status code.  | [optional]
**detail** | Option<**String**> | an optional human-readable explanation of the error. This can be used to record information such as stack trace.  | [optional]
**instance** | Option<**String**> | a string that identifies the specific occurrence of the error. This can be a URI, a request or response ID,  or anything that the implementation can recognize to trace specific occurrence of the error.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


