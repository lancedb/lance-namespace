# NamespaceApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNamespace**](NamespaceApi.md#createNamespace) | **POST** /v1/namespaces | Create a new namespace |
| [**dropNamespace**](NamespaceApi.md#dropNamespace) | **DELETE** /v1/namespaces/{namespace} | Drop a namespace |
| [**getNamespace**](NamespaceApi.md#getNamespace) | **GET** /v1/namespaces/{namespace} | Get information about a namespace |
| [**listNamespaces**](NamespaceApi.md#listNamespaces) | **GET** /v1/namespaces | List namespaces |
| [**namespaceExists**](NamespaceApi.md#namespaceExists) | **HEAD** /v1/namespaces/{namespace} | Check if a namespace exists |



## createNamespace

> GetNamespaceResponse createNamespace(createNamespaceRequest)

Create a new namespace

Create a new namespace. A namespace can manage either a collection of child namespaces, or a collection of tables. There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing namespace is kept.   * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created. 

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
            GetNamespaceResponse result = apiInstance.createNamespace(createNamespaceRequest);
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

[**GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Returns a namespace, as well as any properties stored on the namespace if namespace properties are supported by the server. |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## dropNamespace

> dropNamespace(namespace, delimiter)

Drop a namespace

Drop a namespace. The namespace must be empty. 

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
        String namespace = "namespace_example"; // String | A string identifier of the namespace.
        String delimiter = "delimiter_example"; // String | The delimiter for the identifier used in the context
        try {
            apiInstance.dropNamespace(namespace, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling NamespaceApi#dropNamespace");
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
| **namespace** | **String**| A string identifier of the namespace. | |
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
| **204** | Success, no content |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## getNamespace

> GetNamespaceResponse getNamespace(namespace, delimiter)

Get information about a namespace

Return the detailed information for a given namespace 

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
        String namespace = "namespace_example"; // String | A string identifier of the namespace.
        String delimiter = "delimiter_example"; // String | The delimiter for the identifier used in the context
        try {
            GetNamespaceResponse result = apiInstance.getNamespace(namespace, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling NamespaceApi#getNamespace");
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
| **namespace** | **String**| A string identifier of the namespace. | |
| **delimiter** | **String**| The delimiter for the identifier used in the context | [optional] |

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
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listNamespaces

> ListNamespacesResponse listNamespaces(pageToken, pageSize, parent, delimiter)

List namespaces

List all child namespace names of the root namespace or a given parent namespace. 

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
        String pageToken = "pageToken_example"; // String | 
        Integer pageSize = 56; // Integer | An inclusive upper bound of the number of results that a client will receive.
        String parent = "parent_example"; // String | A string identifier of the parent namespace.
        String delimiter = "delimiter_example"; // String | The delimiter for the identifier used in the context
        try {
            ListNamespacesResponse result = apiInstance.listNamespaces(pageToken, pageSize, parent, delimiter);
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


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **pageToken** | **String**|  | [optional] |
| **pageSize** | **Integer**| An inclusive upper bound of the number of results that a client will receive. | [optional] |
| **parent** | **String**| A string identifier of the parent namespace. | [optional] |
| **delimiter** | **String**| The delimiter for the identifier used in the context | [optional] |

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
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## namespaceExists

> namespaceExists(namespace, delimiter)

Check if a namespace exists

Check if a namespace exists. This API should behave exactly like the GetNamespace API, except it does not contain a body. 

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
        String namespace = "namespace_example"; // String | A string identifier of the namespace.
        String delimiter = "delimiter_example"; // String | The delimiter for the identifier used in the context
        try {
            apiInstance.namespaceExists(namespace, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling NamespaceApi#namespaceExists");
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
| **namespace** | **String**| A string identifier of the namespace. | |
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

