# NamespaceApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNamespace**](NamespaceApi.md#createNamespace) | **POST** /v1/namespaces | Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created.  |
| [**listNamespaces**](NamespaceApi.md#listNamespaces) | **GET** /v1/namespaces | List all namespaces in the catalog.  |
| [**loadNamespaceMetadata**](NamespaceApi.md#loadNamespaceMetadata) | **GET** /v1/namespaces/{ns} | Load the metadata properties for a namespace |



## createNamespace

> CreateNamespaceResponse createNamespace(createNamespaceRequest)

Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. 

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        CreateNamespaceRequest createNamespaceRequest = new CreateNamespaceRequest(); // CreateNamespaceRequest | 
        try {
            CreateNamespaceResponse result = apiInstance.createNamespace(createNamespaceRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling NamespaceApi#createNamespace");
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
| **createNamespaceRequest** | [**CreateNamespaceRequest**](CreateNamespaceRequest.md)|  | |

### Return type

[**CreateNamespaceResponse**](CreateNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Represents a successful call to create a namespace. Returns the namespace created, as well as any properties that were stored for the namespace, including those the server might have added. Implementations are not required to support namespace properties. |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listNamespaces

> ListNamespacesResponse listNamespaces()

List all namespaces in the catalog. 

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        try {
            ListNamespacesResponse result = apiInstance.listNamespaces();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling NamespaceApi#listNamespaces");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of namespaces |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## loadNamespaceMetadata

> GetNamespaceResponse loadNamespaceMetadata(ns)

Load the metadata properties for a namespace

Return all stored metadata properties for a given namespace

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        String ns = "ns_example"; // String | The name of the namespace.
        try {
            GetNamespaceResponse result = apiInstance.loadNamespaceMetadata(ns);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling NamespaceApi#loadNamespaceMetadata");
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

### Return type

[**GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server. |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

