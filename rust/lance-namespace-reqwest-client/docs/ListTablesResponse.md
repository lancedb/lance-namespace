# ListTablesResponse

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**tables** | **Vec<String>** |  | 
**next_page_token** | Option<**String**> | An opaque token that allows pagination for list APIs (e.g. ListNamespaces). For an initial client request for a list API, if the server cannot return all items in one response, or if there are more items than the `pageSize` specified in the client request, the server must return a `nextPageToken` in the response indicating there are more results available. After the initial request, the value of `nextPageToken` from each response must be used by the client as the `pageToken` parameter value for the next request. Clients must interpret either `null`, missing value or empty string value of `nextPageToken` from a server response as the end of the listing results. | [optional]

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


