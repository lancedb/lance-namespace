# TableApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getTable**](TableApi.md#getTable) | **GET** /v1/tables/{table} | Get a table from the namespace |
| [**registerTable**](TableApi.md#registerTable) | **POST** /v1/table/register | Register a table to a namespace |
| [**tableExists**](TableApi.md#tableExists) | **HEAD** /v1/tables/{table} | Check if a table exists |



## getTable

> GetTableResponse getTable(table, delimiter)

Get a table from the namespace

Get a table&#39;s detailed information under a specified namespace. 

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
        String table = "table_example"; // String | A string identifier of the table
        String delimiter = "delimiter_example"; // String | The delimiter for the identifier used in the context
        try {
            GetTableResponse result = apiInstance.getTable(table, delimiter);
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
| **table** | **String**| A string identifier of the table | |
| **delimiter** | **String**| The delimiter for the identifier used in the context | [optional] |

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

> GetTableResponse registerTable(registerTableRequest)

Register a table to a namespace

Register an existing table at a given storage location to a namespace. 

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
        RegisterTableRequest registerTableRequest = new RegisterTableRequest(); // RegisterTableRequest | 
        try {
            GetTableResponse result = apiInstance.registerTable(registerTableRequest);
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


## tableExists

> tableExists(table, delimiter)

Check if a table exists

Check if a table exists. This API should behave exactly like the GetTable API, except it does not contain a body. 

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
        String table = "table_example"; // String | A string identifier of the table
        String delimiter = "delimiter_example"; // String | The delimiter for the identifier used in the context
        try {
            apiInstance.tableExists(table, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#tableExists");
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
| **table** | **String**| A string identifier of the table | |
| **delimiter** | **String**| The delimiter for the identifier used in the context | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Success, no content |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

