# TableApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countRows**](TableApi.md#countRows) | **POST** /v1/table/{id}/count_rows | Count rows in a table |
| [**createIndex**](TableApi.md#createIndex) | **POST** /v1/table/{id}/create_index | Create an index on a table |
| [**createScalarIndex**](TableApi.md#createScalarIndex) | **POST** /v1/table/{id}/create_scalar_index | Create a scalar index on a table |
| [**createTable**](TableApi.md#createTable) | **POST** /v1/table/{id}/create | Create a table with the given name |
| [**deleteFromTable**](TableApi.md#deleteFromTable) | **POST** /v1/table/{id}/delete | Delete rows from a table |
| [**deregisterTable**](TableApi.md#deregisterTable) | **POST** /v1/table/{id}/deregister | Deregister a table from its namespace |
| [**describeTable**](TableApi.md#describeTable) | **POST** /v1/table/{id}/describe | Describe a table from the namespace |
| [**describeTableV2**](TableApi.md#describeTableV2) | **POST** /v2/table/{id}/describe | Describe a table from the namespace |
| [**dropTable**](TableApi.md#dropTable) | **POST** /v1/table/{id}/drop | Drop a table from its namespace |
| [**getIndexStats**](TableApi.md#getIndexStats) | **POST** /v1/table/{id}/index/{index_name}/stats | Get index statistics |
| [**insertTable**](TableApi.md#insertTable) | **POST** /v1/table/{id}/insert | Insert records into a table |
| [**listIndices**](TableApi.md#listIndices) | **POST** /v1/table/{id}/index/list | List indices on a table |
| [**listTables**](TableApi.md#listTables) | **POST** /v1/table/{id}/list | List tables |
| [**mergeInsertTable**](TableApi.md#mergeInsertTable) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table |
| [**queryTable**](TableApi.md#queryTable) | **POST** /v1/table/{id}/query | Query a table |
| [**registerTable**](TableApi.md#registerTable) | **POST** /v1/table/{id}/register | Register a table to a namespace |
| [**tableExists**](TableApi.md#tableExists) | **POST** /v1/table/{id}/exists | Check if a table exists |
| [**updateTable**](TableApi.md#updateTable) | **POST** /v1/table/{id}/update | Update rows in a table |



## countRows

> Long countRows(id, countRowsRequest, delimiter)

Count rows in a table

Count the number of rows in a table. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        CountRowsRequest countRowsRequest = new CountRowsRequest(); // CountRowsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            Long result = apiInstance.countRows(id, countRowsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#countRows");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **countRowsRequest** | [**CountRowsRequest**](CountRowsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

### Return type

**Long**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Result of counting rows in a table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## createIndex

> CreateIndexResponse createIndex(id, createIndexRequest)

Create an index on a table

Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ) and scalar indexes. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(); // CreateIndexRequest | Index creation request
        try {
            CreateIndexResponse result = apiInstance.createIndex(id, createIndexRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#createIndex");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **createIndexRequest** | [**CreateIndexRequest**](CreateIndexRequest.md)| Index creation request | |

### Return type

[**CreateIndexResponse**](CreateIndexResponse.md)

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


## createScalarIndex

> CreateIndexResponse createScalarIndex(id, createIndexRequest)

Create a scalar index on a table

Create a scalar index on a table column for faster search operations. Supports scalar indexes (BTREE, BITMAP, LABEL_LIST). 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(); // CreateIndexRequest | Scalar index creation request
        try {
            CreateIndexResponse result = apiInstance.createScalarIndex(id, createIndexRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#createScalarIndex");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **createIndexRequest** | [**CreateIndexRequest**](CreateIndexRequest.md)| Scalar index creation request | |

### Return type

[**CreateIndexResponse**](CreateIndexResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Scalar index created successfully |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## createTable

> CreateTableResponse createTable(id, body)

Create a table with the given name

Create a new table in the namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        byte[] body = null; // byte[] | Arrow IPC data
        try {
            CreateTableResponse result = apiInstance.createTable(id, body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#createTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **body** | **byte[]**| Arrow IPC data | |

### Return type

[**CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Table properties result when creating a table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## deleteFromTable

> DeleteFromTableResponse deleteFromTable(id, deleteFromTableRequest)

Delete rows from a table

Delete rows from a table based on a SQL predicate. Returns the number of rows that were deleted. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        DeleteFromTableRequest deleteFromTableRequest = new DeleteFromTableRequest(); // DeleteFromTableRequest | Delete request
        try {
            DeleteFromTableResponse result = apiInstance.deleteFromTable(id, deleteFromTableRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#deleteFromTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **deleteFromTableRequest** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md)| Delete request | |

### Return type

[**DeleteFromTableResponse**](DeleteFromTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Delete successful |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## deregisterTable

> DeregisterTableResponse deregisterTable(id, deregisterTableRequest, delimiter)

Deregister a table from its namespace

Deregister a table from its namespace. The table content remains available in the storage. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        DeregisterTableRequest deregisterTableRequest = new DeregisterTableRequest(); // DeregisterTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            DeregisterTableResponse result = apiInstance.deregisterTable(id, deregisterTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#deregisterTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **deregisterTableRequest** | [**DeregisterTableRequest**](DeregisterTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

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


## describeTable

> DescribeTableResponse describeTable(id, describeTableRequest, delimiter)

Describe a table from the namespace

Get a table&#39;s detailed information under a specified namespace. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        DescribeTableRequest describeTableRequest = new DescribeTableRequest(); // DescribeTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            DescribeTableResponse result = apiInstance.describeTable(id, describeTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#describeTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **describeTableRequest** | [**DescribeTableRequest**](DescribeTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

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


## describeTableV2

> DescribeTableResponseV2 describeTableV2(id, describeTableRequestV2, delimiter)

Describe a table from the namespace

Get a table&#39;s detailed information under a specified namespace. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        DescribeTableRequestV2 describeTableRequestV2 = new DescribeTableRequestV2(); // DescribeTableRequestV2 | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            DescribeTableResponseV2 result = apiInstance.describeTableV2(id, describeTableRequestV2, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#describeTableV2");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **describeTableRequestV2** | [**DescribeTableRequestV2**](DescribeTableRequestV2.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

### Return type

[**DescribeTableResponseV2**](DescribeTableResponseV2.md)

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


## dropTable

> DropTableResponse dropTable(id, dropTableRequest, delimiter)

Drop a table from its namespace

Drop a table from its namespace and delete its data. If the table and its data can be immediately deleted, return information of the deleted table. Otherwise, return a transaction ID that client can use to track deletion progress. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        DropTableRequest dropTableRequest = new DropTableRequest(); // DropTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            DropTableResponse result = apiInstance.dropTable(id, dropTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#dropTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **dropTableRequest** | [**DropTableRequest**](DropTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

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


## getIndexStats

> IndexStatsResponse getIndexStats(id, indexName, indexStatsRequest)

Get index statistics

Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        String indexName = "indexName_example"; // String | Name of the index to get stats for
        IndexStatsRequest indexStatsRequest = new IndexStatsRequest(); // IndexStatsRequest | Index stats request
        try {
            IndexStatsResponse result = apiInstance.getIndexStats(id, indexName, indexStatsRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#getIndexStats");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **indexName** | **String**| Name of the index to get stats for | |
| **indexStatsRequest** | [**IndexStatsRequest**](IndexStatsRequest.md)| Index stats request | |

### Return type

[**IndexStatsResponse**](IndexStatsResponse.md)

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


## insertTable

> InsertTableResponse insertTable(id, body, mode)

Insert records into a table

Insert new records into an existing table using Arrow IPC format. Supports both lance-namespace format (with namespace in body) and LanceDB format (with database in headers). 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        byte[] body = null; // byte[] | Arrow IPC data
        String mode = "append"; // String | Insert mode: \"append\" (default) or \"overwrite\"
        try {
            InsertTableResponse result = apiInstance.insertTable(id, body, mode);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#insertTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **body** | **byte[]**| Arrow IPC data | |
| **mode** | **String**| Insert mode: \&quot;append\&quot; (default) or \&quot;overwrite\&quot; | [optional] [default to append] [enum: append, overwrite] |

### Return type

[**InsertTableResponse**](InsertTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Result of inserting records into a table |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## listIndices

> IndexListResponse listIndices(id, indexListRequest)

List indices on a table

List all indices created on a table. Returns information about each index including name, columns, status, and UUID. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        IndexListRequest indexListRequest = new IndexListRequest(); // IndexListRequest | Index list request
        try {
            IndexListResponse result = apiInstance.listIndices(id, indexListRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#listIndices");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **indexListRequest** | [**IndexListRequest**](IndexListRequest.md)| Index list request | |

### Return type

[**IndexListResponse**](IndexListResponse.md)

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


## listTables

> ListTablesResponse listTables(id, listTablesRequest, delimiter)

List tables

List all child table names of the root namespace or a given parent namespace. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        ListTablesRequest listTablesRequest = new ListTablesRequest(); // ListTablesRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            ListTablesResponse result = apiInstance.listTables(id, listTablesRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#listTables");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **listTablesRequest** | [**ListTablesRequest**](ListTablesRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

### Return type

[**ListTablesResponse**](ListTablesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
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


## mergeInsertTable

> MergeInsertTableResponse mergeInsertTable(id, on, body, whenMatchedUpdateAll, whenNotMatchedInsertAll)

Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on a table. This operation updates existing rows based on a matching column and inserts new rows that don&#39;t match. Returns the number of rows inserted and updated. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        String on = "on_example"; // String | Column name to use for matching rows (required)
        byte[] body = null; // byte[] | Arrow IPC data containing the records to merge
        Boolean whenMatchedUpdateAll = false; // Boolean | Update all columns when rows match
        Boolean whenNotMatchedInsertAll = false; // Boolean | Insert all columns when rows don't match
        try {
            MergeInsertTableResponse result = apiInstance.mergeInsertTable(id, on, body, whenMatchedUpdateAll, whenNotMatchedInsertAll);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#mergeInsertTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **on** | **String**| Column name to use for matching rows (required) | |
| **body** | **byte[]**| Arrow IPC data containing the records to merge | |
| **whenMatchedUpdateAll** | **Boolean**| Update all columns when rows match | [optional] [default to false] |
| **whenNotMatchedInsertAll** | **Boolean**| Insert all columns when rows don&#39;t match | [optional] [default to false] |

### Return type

[**MergeInsertTableResponse**](MergeInsertTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/x-arrow-ipc
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Result of merge insert operation |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## queryTable

> byte[] queryTable(id, queryRequest)

Query a table

Query a table with vector search and optional filtering. Returns results in Arrow IPC stream format. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        QueryRequest queryRequest = new QueryRequest(); // QueryRequest | Query request
        try {
            byte[] result = apiInstance.queryTable(id, queryRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#queryTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **queryRequest** | [**QueryRequest**](QueryRequest.md)| Query request | |

### Return type

**byte[]**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/vnd.apache.arrow.stream, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Query results in Arrow IPC stream format |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## registerTable

> RegisterTableResponse registerTable(id, registerTableRequest, delimiter)

Register a table to a namespace

Register an existing table at a given storage location to a namespace. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        RegisterTableRequest registerTableRequest = new RegisterTableRequest(); // RegisterTableRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            RegisterTableResponse result = apiInstance.registerTable(id, registerTableRequest, delimiter);
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **registerTableRequest** | [**RegisterTableRequest**](RegisterTableRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

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


## tableExists

> TableExistsResponse tableExists(id, tableExistsRequest, delimiter)

Check if a table exists

Check if a table exists. This API should behave exactly like the GetTable API, except it does not contain a body. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        TableExistsRequest tableExistsRequest = new TableExistsRequest(); // TableExistsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `.` delimiter must be used. 
        try {
            TableExistsResponse result = apiInstance.tableExists(id, tableExistsRequest, delimiter);
            System.out.println(result);
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **tableExistsRequest** | [**TableExistsRequest**](TableExistsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;.&#x60; delimiter must be used.  | [optional] |

### Return type

[**TableExistsResponse**](TableExistsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Result of checking if a table exists |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## updateTable

> UpdateTableResponse updateTable(id, updateTableRequest)

Update rows in a table

Update existing rows in a table using SQL expressions. Each update consists of a column name and an SQL expression that will be evaluated against the current row&#39;s value. Optionally, a predicate can be provided to filter which rows to update. 

### Example

```java
// Import classes:
import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.client.apache.Configuration;
import com.lancedb.lance.namespace.client.apache.models.*;
import com.lancedb.lance.namespace.client.apache.api.TableApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        TableApi apiInstance = new TableApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/./list` performs a `ListNamespace` on the root namespace. 
        UpdateTableRequest updateTableRequest = new UpdateTableRequest(); // UpdateTableRequest | Update request
        try {
            UpdateTableResponse result = apiInstance.updateTable(id, updateTableRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling TableApi#updateTable");
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
| **id** | **String**| &#x60;string identifier&#x60; of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, &#x60;v1/namespace/./list&#x60; performs a &#x60;ListNamespace&#x60; on the root namespace.  | |
| **updateTableRequest** | [**UpdateTableRequest**](UpdateTableRequest.md)| Update request | |

### Return type

[**UpdateTableResponse**](UpdateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Update successful |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |

