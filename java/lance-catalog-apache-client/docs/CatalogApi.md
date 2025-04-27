# CatalogApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**catalogExists**](CatalogApi.md#catalogExists) | **HEAD** /v1/catalogs/{catalog} | Check if a catalog exists |
| [**createCatalog**](CatalogApi.md#createCatalog) | **POST** /v1/catalogs | Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created.  |
| [**dropCatalog**](CatalogApi.md#dropCatalog) | **DELETE** /v1/catalogs/{catalog} | Drop a catalog. The catalog must be empty. |
| [**getCatalog**](CatalogApi.md#getCatalog) | **GET** /v1/catalogs/{catalog} | Get information about a catalog |
| [**listCatalogs**](CatalogApi.md#listCatalogs) | **GET** /v1/catalogs | List all direct child catalogs of the root catalog.  |



## catalogExists

> catalogExists(catalog, catalogDelimiter)

Check if a catalog exists

Check if a catalog exists. The response does not contain a body.

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        String catalog = "catalog_example"; // String | An identifier of the catalog.
        String catalogDelimiter = "."; // String | The delimiter used by the catalog identifier string
        try {
            apiInstance.catalogExists(catalog, catalogDelimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#catalogExists");
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
| **catalog** | **String**| An identifier of the catalog. | |
| **catalogDelimiter** | **String**| The delimiter used by the catalog identifier string | [optional] [default to .] |

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


## createCatalog

> CreateCatalogResponse createCatalog(createCatalogRequest, parentCatalog, parentCatalogDelimiter)

Create a new catalog. A catalog can manage either a collection of child catalogs, or a collection of tables. There are three modes when trying to create a catalog to differentiate the behavior when a catalog of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing catalog is kept.   * OVERWRITE: the existing catalog is dropped and a new empty catalog with this name is created. 

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        CreateCatalogRequest createCatalogRequest = new CreateCatalogRequest(); // CreateCatalogRequest | 
        String parentCatalog = "parentCatalog_example"; // String | An identifier of the parent catalog.
        String parentCatalogDelimiter = "."; // String | The delimiter used by the parent catalog identifier
        try {
            CreateCatalogResponse result = apiInstance.createCatalog(createCatalogRequest, parentCatalog, parentCatalogDelimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#createCatalog");
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
| **createCatalogRequest** | [**CreateCatalogRequest**](CreateCatalogRequest.md)|  | |
| **parentCatalog** | **String**| An identifier of the parent catalog. | [optional] |
| **parentCatalogDelimiter** | **String**| The delimiter used by the parent catalog identifier | [optional] [default to .] |

### Return type

[**CreateCatalogResponse**](CreateCatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Represents a successful call to create a catalog. Returns the catalog created, as well as any properties that were stored for the catalog, including those the server might have added. Implementations are not required to support catalog properties. |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## dropCatalog

> dropCatalog(catalog, catalogDelimiter)

Drop a catalog. The catalog must be empty.

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        String catalog = "catalog_example"; // String | An identifier of the catalog.
        String catalogDelimiter = "."; // String | The delimiter used by the catalog identifier string
        try {
            apiInstance.dropCatalog(catalog, catalogDelimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#dropCatalog");
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
| **catalog** | **String**| An identifier of the catalog. | |
| **catalogDelimiter** | **String**| The delimiter used by the catalog identifier string | [optional] [default to .] |

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


## getCatalog

> GetCatalogResponse getCatalog(catalog, catalogDelimiter)

Get information about a catalog

Return a detailed information for a given catalog

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        String catalog = "catalog_example"; // String | An identifier of the catalog.
        String catalogDelimiter = "."; // String | The delimiter used by the catalog identifier string
        try {
            GetCatalogResponse result = apiInstance.getCatalog(catalog, catalogDelimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#getCatalog");
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
| **catalog** | **String**| An identifier of the catalog. | |
| **catalogDelimiter** | **String**| The delimiter used by the catalog identifier string | [optional] [default to .] |

### Return type

[**GetCatalogResponse**](GetCatalogResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Returns a catalog, as well as any properties stored on the catalog if catalog properties are supported by the server. |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listCatalogs

> ListCatalogsResponse listCatalogs(pageToken, pageSize, parentCatalog, parentCatalogDelimiter)

List all direct child catalogs of the root catalog. 

### Example

```java
// Import classes:
import com.lancedb.lance.catalog.client.apache.ApiClient;
import com.lancedb.lance.catalog.client.apache.ApiException;
import com.lancedb.lance.catalog.client.apache.Configuration;
import com.lancedb.lance.catalog.client.apache.models.*;
import com.lancedb.lance.catalog.client.apache.api.CatalogApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        CatalogApi apiInstance = new CatalogApi(defaultClient);
        String pageToken = "pageToken_example"; // String | 
        Integer pageSize = 56; // Integer | An inclusive upper bound of the number of results that a client will receive.
        String parentCatalog = "parentCatalog_example"; // String | An identifier of the parent catalog.
        String parentCatalogDelimiter = "."; // String | The delimiter used by the parent catalog identifier
        try {
            ListCatalogsResponse result = apiInstance.listCatalogs(pageToken, pageSize, parentCatalog, parentCatalogDelimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CatalogApi#listCatalogs");
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
| **parentCatalog** | **String**| An identifier of the parent catalog. | [optional] |
| **parentCatalogDelimiter** | **String**| The delimiter used by the parent catalog identifier | [optional] [default to .] |

### Return type

[**ListCatalogsResponse**](ListCatalogsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of catalogs |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

