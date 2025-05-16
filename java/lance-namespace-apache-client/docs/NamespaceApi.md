# NamespaceApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createNamespace**](NamespaceApi.md#createNamespace) | **POST** /CreateNamespace | Create a new namespace |
| [**dropNamespace**](NamespaceApi.md#dropNamespace) | **POST** /DropNamespace | Drop a namespace |
| [**getNamespace**](NamespaceApi.md#getNamespace) | **POST** /GetNamespace | Get information about a namespace |
| [**listNamespaces**](NamespaceApi.md#listNamespaces) | **POST** /ListNamespaces | List namespaces |
| [**namespaceExists**](NamespaceApi.md#namespaceExists) | **POST** /NamespaceExists | Check if a namespace exists |



## createNamespace

> GetNamespaceResponse createNamespace(createNamespaceRequest)

Create a new namespace

Create a new namespace. A namespace can manage either a collection of child namespaces, or a collection of tables. There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing namespace is kept.   * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;

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

> Object dropNamespace(dropNamespaceRequest)

Drop a namespace

Drop a namespace. The namespace must be empty. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        DropNamespaceRequest dropNamespaceRequest = new DropNamespaceRequest(); // DropNamespaceRequest | 
        try {
            Object result = apiInstance.dropNamespace(dropNamespaceRequest);
            System.out.println(result);
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
| **dropNamespaceRequest** | [**DropNamespaceRequest**](DropNamespaceRequest.md)|  | |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Result of dropping a namespace |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## getNamespace

> GetNamespaceResponse getNamespace(getNamespaceRequest)

Get information about a namespace

Return the detailed information for a given namespace 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        GetNamespaceRequest getNamespaceRequest = new GetNamespaceRequest(); // GetNamespaceRequest | 
        try {
            GetNamespaceResponse result = apiInstance.getNamespace(getNamespaceRequest);
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
| **getNamespaceRequest** | [**GetNamespaceRequest**](GetNamespaceRequest.md)|  | |

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
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listNamespaces

> ListNamespacesResponse listNamespaces(listNamespacesRequest)

List namespaces

List all child namespace names of the root namespace or a given parent namespace. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        ListNamespacesRequest listNamespacesRequest = new ListNamespacesRequest(); // ListNamespacesRequest | 
        try {
            ListNamespacesResponse result = apiInstance.listNamespaces(listNamespacesRequest);
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
| **listNamespacesRequest** | [**ListNamespacesRequest**](ListNamespacesRequest.md)|  | |

### Return type

[**ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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

> Object namespaceExists(namespaceExistsRequest)

Check if a namespace exists

Check if a namespace exists. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.NamespaceApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        NamespaceApi apiInstance = new NamespaceApi(defaultClient);
        NamespaceExistsRequest namespaceExistsRequest = new NamespaceExistsRequest(); // NamespaceExistsRequest | 
        try {
            Object result = apiInstance.namespaceExists(namespaceExistsRequest);
            System.out.println(result);
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
| **namespaceExistsRequest** | [**NamespaceExistsRequest**](NamespaceExistsRequest.md)|  | |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Result of checking if a namespace exists |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

