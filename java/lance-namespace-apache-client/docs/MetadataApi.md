# MetadataApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**alterTableAlterColumns**](MetadataApi.md#alterTableAlterColumns) | **POST** /v1/table/{id}/alter_columns | Modify existing columns |
| [**alterTableDropColumns**](MetadataApi.md#alterTableDropColumns) | **POST** /v1/table/{id}/drop_columns | Remove columns from table |
| [**alterTransaction**](MetadataApi.md#alterTransaction) | **POST** /v1/transaction/{id}/alter | Alter information of a transaction. |
| [**createEmptyTable**](MetadataApi.md#createEmptyTable) | **POST** /v1/table/{id}/create-empty | Create an empty table |
| [**createNamespace**](MetadataApi.md#createNamespace) | **POST** /v1/namespace/{id}/create | Create a new namespace |
| [**createTableIndex**](MetadataApi.md#createTableIndex) | **POST** /v1/table/{id}/create_index | Create an index on a table |
| [**createTableTag**](MetadataApi.md#createTableTag) | **POST** /v1/table/{id}/tags/create | Create a new tag |
| [**deleteTableTag**](MetadataApi.md#deleteTableTag) | **POST** /v1/table/{id}/tags/delete | Delete a tag |
| [**deregisterTable**](MetadataApi.md#deregisterTable) | **POST** /v1/table/{id}/deregister | Deregister a table |
| [**describeNamespace**](MetadataApi.md#describeNamespace) | **POST** /v1/namespace/{id}/describe | Describe a namespace |
| [**describeTable**](MetadataApi.md#describeTable) | **POST** /v1/table/{id}/describe | Describe information of a table |
| [**describeTableIndexStats**](MetadataApi.md#describeTableIndexStats) | **POST** /v1/table/{id}/index/{index_name}/stats | Get table index statistics |
| [**describeTransaction**](MetadataApi.md#describeTransaction) | **POST** /v1/transaction/{id}/describe | Describe information about a transaction |
| [**dropNamespace**](MetadataApi.md#dropNamespace) | **POST** /v1/namespace/{id}/drop | Drop a namespace |
| [**dropTable**](MetadataApi.md#dropTable) | **POST** /v1/table/{id}/drop | Drop a table |
| [**dropTableIndex**](MetadataApi.md#dropTableIndex) | **POST** /v1/table/{id}/index/{index_name}/drop | Drop a specific index |
| [**getTableStats**](MetadataApi.md#getTableStats) | **POST** /v1/table/{id}/stats | Get table statistics |
| [**getTableTagVersion**](MetadataApi.md#getTableTagVersion) | **POST** /v1/table/{id}/tags/version | Get version for a specific tag |
| [**listNamespaces**](MetadataApi.md#listNamespaces) | **GET** /v1/namespace/{id}/list | List namespaces |
| [**listTableIndices**](MetadataApi.md#listTableIndices) | **POST** /v1/table/{id}/index/list | List indexes on a table |
| [**listTableTags**](MetadataApi.md#listTableTags) | **GET** /v1/table/{id}/tags/list | List all tags for a table |
| [**listTableVersions**](MetadataApi.md#listTableVersions) | **POST** /v1/table/{id}/version/list | List all versions of a table |
| [**listTables**](MetadataApi.md#listTables) | **GET** /v1/namespace/{id}/table/list | List tables in a namespace |
| [**namespaceExists**](MetadataApi.md#namespaceExists) | **POST** /v1/namespace/{id}/exists | Check if a namespace exists |
| [**registerTable**](MetadataApi.md#registerTable) | **POST** /v1/table/{id}/register | Register a table to a namespace |
| [**restoreTable**](MetadataApi.md#restoreTable) | **POST** /v1/table/{id}/restore | Restore table to a specific version |
| [**tableExists**](MetadataApi.md#tableExists) | **POST** /v1/table/{id}/exists | Check if a table exists |
| [**updateTableTag**](MetadataApi.md#updateTableTag) | **POST** /v1/table/{id}/tags/update | Update a tag to point to a different version |



## alterTableAlterColumns

> AlterTableAlterColumnsResponse alterTableAlterColumns(id, alterTableAlterColumnsRequest, delimiter)

Modify existing columns

Modify existing columns in table &#x60;id&#x60;, such as renaming or changing data types. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        AlterTableAlterColumnsRequest alterTableAlterColumnsRequest = new AlterTableAlterColumnsRequest(); // AlterTableAlterColumnsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            AlterTableAlterColumnsResponse result = apiInstance.alterTableAlterColumns(id, alterTableAlterColumnsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#alterTableAlterColumns");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **alterTableAlterColumnsRequest** | [**AlterTableAlterColumnsRequest**](AlterTableAlterColumnsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**AlterTableAlterColumnsResponse**](AlterTableAlterColumnsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Alter columns operation result |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## alterTableDropColumns

> AlterTableDropColumnsResponse alterTableDropColumns(id, alterTableDropColumnsRequest, delimiter)

Remove columns from table

Remove specified columns from table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        AlterTableDropColumnsRequest alterTableDropColumnsRequest = new AlterTableDropColumnsRequest(); // AlterTableDropColumnsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            AlterTableDropColumnsResponse result = apiInstance.alterTableDropColumns(id, alterTableDropColumnsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#alterTableDropColumns");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **alterTableDropColumnsRequest** | [**AlterTableDropColumnsRequest**](AlterTableDropColumnsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**AlterTableDropColumnsResponse**](AlterTableDropColumnsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Drop columns operation result |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## alterTransaction

> AlterTransactionResponse alterTransaction(id, alterTransactionRequest, delimiter)

Alter information of a transaction.

Alter a transaction with a list of actions such as setting status or properties. The server should either succeed and apply all actions, or fail and apply no action. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        AlterTransactionRequest alterTransactionRequest = new AlterTransactionRequest(); // AlterTransactionRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            AlterTransactionResponse result = apiInstance.alterTransaction(id, alterTransactionRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#alterTransaction");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **alterTransactionRequest** | [**AlterTransactionRequest**](AlterTransactionRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**AlterTransactionResponse**](AlterTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Response of AlterTransaction |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## createEmptyTable

> CreateEmptyTableResponse createEmptyTable(id, createEmptyTableRequest, delimiter)

Create an empty table

Create an empty table with the given name without touching storage. This is a metadata-only operation that records the table existence and sets up aspects like access control.  For DirectoryNamespace implementation, this creates a &#x60;.lance-reserved&#x60; file in the table directory to mark the table&#39;s existence without creating actual Lance data files. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        CreateEmptyTableRequest createEmptyTableRequest = new CreateEmptyTableRequest(); // CreateEmptyTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            CreateEmptyTableResponse result = apiInstance.createEmptyTable(id, createEmptyTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#createEmptyTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **createEmptyTableRequest** | [**CreateEmptyTableRequest**](CreateEmptyTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**CreateEmptyTableResponse**](CreateEmptyTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table properties result when creating an empty table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## createNamespace

> CreateNamespaceResponse createNamespace(id, createNamespaceRequest, delimiter)

Create a new namespace

Create new namespace &#x60;id&#x60;.  During the creation process, the implementation may modify user-provided &#x60;properties&#x60;,  such as adding additional properties like &#x60;created_at&#x60; to user-provided properties,  omitting any specific property, or performing actions based on any property value. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        CreateNamespaceRequest createNamespaceRequest = new CreateNamespaceRequest(); // CreateNamespaceRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            CreateNamespaceResponse result = apiInstance.createNamespace(id, createNamespaceRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#createNamespace");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **createNamespaceRequest** | [**CreateNamespaceRequest**](CreateNamespaceRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

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
| **200** | Result of creating a namespace |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## createTableIndex

> CreateTableIndexResponse createTableIndex(id, createTableIndexRequest, delimiter)

Create an index on a table

Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously.  Use the &#x60;ListTableIndices&#x60; and &#x60;DescribeTableIndexStats&#x60; operations to monitor index creation progress. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        CreateTableIndexRequest createTableIndexRequest = new CreateTableIndexRequest(); // CreateTableIndexRequest | Index creation request
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            CreateTableIndexResponse result = apiInstance.createTableIndex(id, createTableIndexRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#createTableIndex");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **createTableIndexRequest** | [**CreateTableIndexRequest**](CreateTableIndexRequest.md)| Index creation request | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**CreateTableIndexResponse**](CreateTableIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Index created successfully |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## createTableTag

> createTableTag(id, createTableTagRequest, delimiter)

Create a new tag

Create a new tag for table &#x60;id&#x60; that points to a specific version. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        CreateTableTagRequest createTableTagRequest = new CreateTableTagRequest(); // CreateTableTagRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            apiInstance.createTableTag(id, createTableTagRequest, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#createTableTag");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **createTableTagRequest** | [**CreateTableTagRequest**](CreateTableTagRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Success, no content |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## deleteTableTag

> deleteTableTag(id, deleteTableTagRequest, delimiter)

Delete a tag

Delete an existing tag from table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DeleteTableTagRequest deleteTableTagRequest = new DeleteTableTagRequest(); // DeleteTableTagRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            apiInstance.deleteTableTag(id, deleteTableTagRequest, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#deleteTableTag");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **deleteTableTagRequest** | [**DeleteTableTagRequest**](DeleteTableTagRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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


## deregisterTable

> DeregisterTableResponse deregisterTable(id, deregisterTableRequest, delimiter)

Deregister a table

Deregister table &#x60;id&#x60; from its namespace. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DeregisterTableRequest deregisterTableRequest = new DeregisterTableRequest(); // DeregisterTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DeregisterTableResponse result = apiInstance.deregisterTable(id, deregisterTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#deregisterTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **deregisterTableRequest** | [**DeregisterTableRequest**](DeregisterTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DeregisterTableResponse**](DeregisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Response of DeregisterTable |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## describeNamespace

> DescribeNamespaceResponse describeNamespace(id, describeNamespaceRequest, delimiter)

Describe a namespace

Describe the detailed information for namespace &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DescribeNamespaceRequest describeNamespaceRequest = new DescribeNamespaceRequest(); // DescribeNamespaceRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DescribeNamespaceResponse result = apiInstance.describeNamespace(id, describeNamespaceRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#describeNamespace");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **describeNamespaceRequest** | [**DescribeNamespaceRequest**](DescribeNamespaceRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DescribeNamespaceResponse**](DescribeNamespaceResponse.md)

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


## describeTable

> DescribeTableResponse describeTable(id, describeTableRequest, delimiter)

Describe information of a table

Describe the detailed information for table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DescribeTableRequest describeTableRequest = new DescribeTableRequest(); // DescribeTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DescribeTableResponse result = apiInstance.describeTable(id, describeTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#describeTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **describeTableRequest** | [**DescribeTableRequest**](DescribeTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DescribeTableResponse**](DescribeTableResponse.md)

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
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## describeTableIndexStats

> DescribeTableIndexStatsResponse describeTableIndexStats(id, indexName, describeTableIndexStatsRequest, delimiter)

Get table index statistics

Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        String indexName = "indexName_example"; // String | Name of the index to get stats for
        DescribeTableIndexStatsRequest describeTableIndexStatsRequest = new DescribeTableIndexStatsRequest(); // DescribeTableIndexStatsRequest | Index stats request
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DescribeTableIndexStatsResponse result = apiInstance.describeTableIndexStats(id, indexName, describeTableIndexStatsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#describeTableIndexStats");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **indexName** | **String**| Name of the index to get stats for | |
| **describeTableIndexStatsRequest** | [**DescribeTableIndexStatsRequest**](DescribeTableIndexStatsRequest.md)| Index stats request | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DescribeTableIndexStatsResponse**](DescribeTableIndexStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Index statistics |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## describeTransaction

> DescribeTransactionResponse describeTransaction(id, describeTransactionRequest, delimiter)

Describe information about a transaction

Return a detailed information for a given transaction 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DescribeTransactionRequest describeTransactionRequest = new DescribeTransactionRequest(); // DescribeTransactionRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DescribeTransactionResponse result = apiInstance.describeTransaction(id, describeTransactionRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#describeTransaction");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **describeTransactionRequest** | [**DescribeTransactionRequest**](DescribeTransactionRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DescribeTransactionResponse**](DescribeTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Response of GetTransaction |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## dropNamespace

> DropNamespaceResponse dropNamespace(id, dropNamespaceRequest, delimiter)

Drop a namespace

Drop namespace &#x60;id&#x60; from its parent namespace. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DropNamespaceRequest dropNamespaceRequest = new DropNamespaceRequest(); // DropNamespaceRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DropNamespaceResponse result = apiInstance.dropNamespace(id, dropNamespaceRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#dropNamespace");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **dropNamespaceRequest** | [**DropNamespaceRequest**](DropNamespaceRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DropNamespaceResponse**](DropNamespaceResponse.md)

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


## dropTable

> DropTableResponse dropTable(id, dropTableRequest, delimiter)

Drop a table

Drop table &#x60;id&#x60; and delete its data. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DropTableRequest dropTableRequest = new DropTableRequest(); // DropTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DropTableResponse result = apiInstance.dropTable(id, dropTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#dropTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **dropTableRequest** | [**DropTableRequest**](DropTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DropTableResponse**](DropTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Response of DropTable |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## dropTableIndex

> DropTableIndexResponse dropTableIndex(id, indexName, dropTableIndexRequest, delimiter)

Drop a specific index

Drop the specified index from table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        String indexName = "indexName_example"; // String | Name of the index to drop
        DropTableIndexRequest dropTableIndexRequest = new DropTableIndexRequest(); // DropTableIndexRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DropTableIndexResponse result = apiInstance.dropTableIndex(id, indexName, dropTableIndexRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#dropTableIndex");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **indexName** | **String**| Name of the index to drop | |
| **dropTableIndexRequest** | [**DropTableIndexRequest**](DropTableIndexRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**DropTableIndexResponse**](DropTableIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Index drop operation result |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## getTableStats

> GetTableStatsResponse getTableStats(id, getTableStatsRequest, delimiter)

Get table statistics

Get statistics for table &#x60;id&#x60;, including row counts, data sizes, and column statistics. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        GetTableStatsRequest getTableStatsRequest = new GetTableStatsRequest(); // GetTableStatsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            GetTableStatsResponse result = apiInstance.getTableStats(id, getTableStatsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#getTableStats");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **getTableStatsRequest** | [**GetTableStatsRequest**](GetTableStatsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**GetTableStatsResponse**](GetTableStatsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table statistics |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## getTableTagVersion

> GetTableTagVersionResponse getTableTagVersion(id, getTableTagVersionRequest, delimiter)

Get version for a specific tag

Get the version number that a specific tag points to for table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        GetTableTagVersionRequest getTableTagVersionRequest = new GetTableTagVersionRequest(); // GetTableTagVersionRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            GetTableTagVersionResponse result = apiInstance.getTableTagVersion(id, getTableTagVersionRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#getTableTagVersion");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **getTableTagVersionRequest** | [**GetTableTagVersionRequest**](GetTableTagVersionRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**GetTableTagVersionResponse**](GetTableTagVersionResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Tag version information |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listNamespaces

> ListNamespacesResponse listNamespaces(id, delimiter, pageToken, limit)

List namespaces

List all child namespace names of the parent namespace &#x60;id&#x60;.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the &#x60;ListNamespacesRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of the same name 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        String pageToken = "pageToken_example"; // String | 
        Integer limit = 56; // Integer | 
        try {
            ListNamespacesResponse result = apiInstance.listNamespaces(id, delimiter, pageToken, limit);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#listNamespaces");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |
| **pageToken** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] |

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
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listTableIndices

> ListTableIndicesResponse listTableIndices(id, listTableIndicesRequest, delimiter)

List indexes on a table

List all indices created on a table. Returns information about each index including name, columns, status, and UUID. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        ListTableIndicesRequest listTableIndicesRequest = new ListTableIndicesRequest(); // ListTableIndicesRequest | Index list request
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            ListTableIndicesResponse result = apiInstance.listTableIndices(id, listTableIndicesRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#listTableIndices");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **listTableIndicesRequest** | [**ListTableIndicesRequest**](ListTableIndicesRequest.md)| Index list request | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**ListTableIndicesResponse**](ListTableIndicesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of indices on the table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listTableTags

> ListTableTagsResponse listTableTags(id, delimiter, pageToken, limit)

List all tags for a table

List all tags that have been created for table &#x60;id&#x60;. Returns a map of tag names to their corresponding version numbers and metadata.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the &#x60;ListTableTagsRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of the same name 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        String pageToken = "pageToken_example"; // String | 
        Integer limit = 56; // Integer | 
        try {
            ListTableTagsResponse result = apiInstance.listTableTags(id, delimiter, pageToken, limit);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#listTableTags");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |
| **pageToken** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] |

### Return type

[**ListTableTagsResponse**](ListTableTagsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of table tags |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listTableVersions

> ListTableVersionsResponse listTableVersions(id, listTableVersionsRequest, delimiter)

List all versions of a table

List all versions (commits) of table &#x60;id&#x60; with their metadata. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        ListTableVersionsRequest listTableVersionsRequest = new ListTableVersionsRequest(); // ListTableVersionsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            ListTableVersionsResponse result = apiInstance.listTableVersions(id, listTableVersionsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#listTableVersions");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **listTableVersionsRequest** | [**ListTableVersionsRequest**](ListTableVersionsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**ListTableVersionsResponse**](ListTableVersionsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of table versions |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listTables

> ListTablesResponse listTables(id, delimiter, pageToken, limit)

List tables in a namespace

List all child table names of the parent namespace &#x60;id&#x60;.  REST NAMESPACE ONLY REST namespace uses GET to perform this operation without a request body. It passes in the &#x60;ListTablesRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;page_token&#x60;: pass through query parameter of the same name - &#x60;limit&#x60;: pass through query parameter of the same name 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        String pageToken = "pageToken_example"; // String | 
        Integer limit = 56; // Integer | 
        try {
            ListTablesResponse result = apiInstance.listTables(id, delimiter, pageToken, limit);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#listTables");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |
| **pageToken** | **String**|  | [optional] |
| **limit** | **Integer**|  | [optional] |

### Return type

[**ListTablesResponse**](ListTablesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | A list of tables |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## namespaceExists

> namespaceExists(id, namespaceExistsRequest, delimiter)

Check if a namespace exists

Check if namespace &#x60;id&#x60; exists.  This operation must behave exactly like the DescribeNamespace API,  except it does not contain a response body. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        NamespaceExistsRequest namespaceExistsRequest = new NamespaceExistsRequest(); // NamespaceExistsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            apiInstance.namespaceExists(id, namespaceExistsRequest, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#namespaceExists");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **namespaceExistsRequest** | [**NamespaceExistsRequest**](NamespaceExistsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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


## registerTable

> RegisterTableResponse registerTable(id, registerTableRequest, delimiter)

Register a table to a namespace

Register an existing table at a given storage location as &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        RegisterTableRequest registerTableRequest = new RegisterTableRequest(); // RegisterTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            RegisterTableResponse result = apiInstance.registerTable(id, registerTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#registerTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **registerTableRequest** | [**RegisterTableRequest**](RegisterTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**RegisterTableResponse**](RegisterTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table properties result when registering a table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **406** | Not Acceptable / Unsupported Operation. The server does not support this operation. |  -  |
| **409** | The request conflicts with the current state of the target resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## restoreTable

> RestoreTableResponse restoreTable(id, restoreTableRequest, delimiter)

Restore table to a specific version

Restore table &#x60;id&#x60; to a specific version. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        RestoreTableRequest restoreTableRequest = new RestoreTableRequest(); // RestoreTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            RestoreTableResponse result = apiInstance.restoreTable(id, restoreTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#restoreTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **restoreTableRequest** | [**RestoreTableRequest**](RestoreTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**RestoreTableResponse**](RestoreTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table restore operation result |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## tableExists

> tableExists(id, tableExistsRequest, delimiter)

Check if a table exists

Check if table &#x60;id&#x60; exists.  This operation should behave exactly like DescribeTable,  except it does not contain a response body.  For DirectoryNamespace implementation, a table exists if either: - The table has Lance data versions (regular table created with CreateTable) - A &#x60;.lance-reserved&#x60; file exists in the table directory (empty table created with CreateEmptyTable) 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        TableExistsRequest tableExistsRequest = new TableExistsRequest(); // TableExistsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            apiInstance.tableExists(id, tableExistsRequest, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#tableExists");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **tableExistsRequest** | [**TableExistsRequest**](TableExistsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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


## updateTableTag

> updateTableTag(id, updateTableTagRequest, delimiter)

Update a tag to point to a different version

Update an existing tag for table &#x60;id&#x60; to point to a different version. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.MetadataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        MetadataApi apiInstance = new MetadataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        UpdateTableTagRequest updateTableTagRequest = new UpdateTableTagRequest(); // UpdateTableTagRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            apiInstance.updateTableTag(id, updateTableTagRequest, delimiter);
        } catch (ApiException e) {
            System.err.println("Exception when calling MetadataApi#updateTableTag");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/$/list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **updateTableTagRequest** | [**UpdateTableTagRequest**](UpdateTableTagRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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

