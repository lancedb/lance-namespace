# ListTablesRequest

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | Option<**Vec<String>**> |  | [optional]
**page_token** | Option<**String**> | An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation,  if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request,  the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either `null`,  missing value or empty string value of the page token from the implementation's response as the end of the listing results.  | [optional]
**limit** | Option<**i32**> | An inclusive upper bound of the  number of results that a caller will receive.  | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


