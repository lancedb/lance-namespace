

# ListTablesResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**tables** | **Set&lt;String&gt;** | The list of names of all the tables under the connected namespace implementation. This should recursively list all the tables in all child namespaces. Each string in the list is the full identifier in string form.  |  |
|**pageToken** | **String** | An opaque token that allows pagination for list operations (e.g. ListNamespaces).  For an initial request of a list operation,  if the implementation cannot return all items in one response, or if there are more items than the page limit specified in the request, the implementation must return a page token in the response, indicating there are more results available.  After the initial request,  the value of the page token from each response must be used as the page token value for the next request.  Caller must interpret either &#x60;null&#x60;,  missing value or empty string value of the page token from the implementation&#39;s response as the end of the listing results.  |  [optional] |



