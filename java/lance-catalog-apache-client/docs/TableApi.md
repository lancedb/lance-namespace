# TableApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getTable**](TableApi.md#getTable) | **GET** /v1/namespaces/{ns}/tables/{table} | Get a table from the catalog |
| [**registerTable**](TableApi.md#registerTable) | **POST** /v1/namespaces/{ns}/register | Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace.  |



## getTable

> GetTableResponse getTable(ns, table)

Get a table from the catalog

Get a table&#39;s detailed information under a specified namespace from the catalog.

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String ns = "ns_example"; // String | The name of the namespace.
        String table = "table_example"; // String | A table name.
        try {
            GetTableResponse result = apiInstance.getTable(ns, table);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#getTable");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ns** | **String**| The name of the namespace. | |
| **table** | **String**| A table name. | |

### Return type

[**GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table properties result when loading a table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## registerTable

> GetTableResponse registerTable(ns, registerTableRequest)

Register a new table in the given namespace. A table represents a lance dataset.  In Lance catalog, a table must be hosted in a namespace. 

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String ns = "ns_example"; // String | The name of the namespace.
        RegisterTableRequest registerTableRequest = new RegisterTableRequest(); // RegisterTableRequest | 
        try {
            GetTableResponse result = apiInstance.registerTable(ns, registerTableRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#registerTable");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **ns** | **String**| The name of the namespace. | |
| **registerTableRequest** | [**RegisterTableRequest**](RegisterTableRequest.md)|  | |

### Return type

[**GetTableResponse**](GetTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table properties result when loading a table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

