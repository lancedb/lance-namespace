

# ListTablesRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**parent** | **List&lt;String&gt;** |  |  [optional] |
|**pageToken** | **String** | An opaque token that allows pagination for list APIs (e.g. ListNamespaces). For an initial client request for a list API, if the server cannot return all items in one response, or if there are more items than the &#x60;pageSize&#x60; specified in the client request, the server must return a &#x60;nextPageToken&#x60; in the response indicating there are more results available. After the initial request, the value of &#x60;nextPageToken&#x60; from each response must be used by the client as the &#x60;pageToken&#x60; parameter value for the next request. Clients must interpret either &#x60;null&#x60;, missing value or empty string value of &#x60;nextPageToken&#x60; from a server response as the end of the listing results. |  [optional] |
|**pageSize** | **Integer** | An inclusive upper bound of the number of results that a client will receive. |  [optional] |



