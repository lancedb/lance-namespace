# DataApi

All URIs are relative to *http://localhost:2333*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**alterTableAddColumns**](DataApi.md#alterTableAddColumns) | **POST** /v1/table/{id}/add_columns | Add new columns to table schema |
| [**analyzeTableQueryPlan**](DataApi.md#analyzeTableQueryPlan) | **POST** /v1/table/{id}/analyze_plan | Analyze query execution plan |
| [**countTableRows**](DataApi.md#countTableRows) | **POST** /v1/table/{id}/count_rows | Count rows in a table |
| [**createTable**](DataApi.md#createTable) | **POST** /v1/table/{id}/create | Create a table with the given name |
| [**deleteFromTable**](DataApi.md#deleteFromTable) | **POST** /v1/table/{id}/delete | Delete rows from a table |
| [**explainTableQueryPlan**](DataApi.md#explainTableQueryPlan) | **POST** /v1/table/{id}/explain_plan | Get query execution plan explanation |
| [**insertIntoTable**](DataApi.md#insertIntoTable) | **POST** /v1/table/{id}/insert | Insert records into a table |
| [**mergeInsertIntoTable**](DataApi.md#mergeInsertIntoTable) | **POST** /v1/table/{id}/merge_insert | Merge insert (upsert) records into a table |
| [**queryTable**](DataApi.md#queryTable) | **POST** /v1/table/{id}/query | Query a table |
| [**updateTable**](DataApi.md#updateTable) | **POST** /v1/table/{id}/update | Update rows in a table |



## alterTableAddColumns

> AlterTableAddColumnsResponse alterTableAddColumns(id, alterTableAddColumnsRequest, delimiter)

Add new columns to table schema

Add new columns to table &#x60;id&#x60; using SQL expressions or default values. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        AlterTableAddColumnsRequest alterTableAddColumnsRequest = new AlterTableAddColumnsRequest(); // AlterTableAddColumnsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            AlterTableAddColumnsResponse result = apiInstance.alterTableAddColumns(id, alterTableAddColumnsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#alterTableAddColumns");
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
| **alterTableAddColumnsRequest** | [**AlterTableAddColumnsRequest**](AlterTableAddColumnsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**AlterTableAddColumnsResponse**](AlterTableAddColumnsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Add columns operation result |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## analyzeTableQueryPlan

> AnalyzeTableQueryPlanResponse analyzeTableQueryPlan(id, analyzeTableQueryPlanRequest, delimiter)

Analyze query execution plan

Analyze the query execution plan for a query against table &#x60;id&#x60;. Returns detailed statistics and analysis of the query execution plan. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        AnalyzeTableQueryPlanRequest analyzeTableQueryPlanRequest = new AnalyzeTableQueryPlanRequest(); // AnalyzeTableQueryPlanRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            AnalyzeTableQueryPlanResponse result = apiInstance.analyzeTableQueryPlan(id, analyzeTableQueryPlanRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#analyzeTableQueryPlan");
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
| **analyzeTableQueryPlanRequest** | [**AnalyzeTableQueryPlanRequest**](AnalyzeTableQueryPlanRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**AnalyzeTableQueryPlanResponse**](AnalyzeTableQueryPlanResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Query execution plan analysis |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## countTableRows

> Long countTableRows(id, countTableRowsRequest, delimiter)

Count rows in a table

Count the number of rows in table &#x60;id&#x60; 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        CountTableRowsRequest countTableRowsRequest = new CountTableRowsRequest(); // CountTableRowsRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            Long result = apiInstance.countTableRows(id, countTableRowsRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#countTableRows");
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
| **countTableRowsRequest** | [**CountTableRowsRequest**](CountTableRowsRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

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


## createTable

> CreateTableResponse createTable(id, body, delimiter, mode, xLanceTableLocation, xLanceTableProperties)

Create a table with the given name

Create table &#x60;id&#x60; in the namespace with the given data in Arrow IPC stream.  The schema of the Arrow IPC stream is used as the table schema.     If the stream is empty, the API creates a new empty table.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the &#x60;CreateTableRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;mode&#x60;: pass through query parameter of the same name - &#x60;location&#x60;: pass through header &#x60;x-lance-table-location&#x60; - &#x60;properties&#x60;: pass through header &#x60;x-lance-table-properties&#x60; 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        byte[] body = null; // byte[] | Arrow IPC data
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        String mode = "mode_example"; // String | 
        String xLanceTableLocation = "xLanceTableLocation_example"; // String | URI pointing to root location to create the table at
        String xLanceTableProperties = "xLanceTableProperties_example"; // String | JSON-encoded string map (e.g. { \"owner\": \"jack\" }) 
        try {
            CreateTableResponse result = apiInstance.createTable(id, body, delimiter, mode, xLanceTableLocation, xLanceTableProperties);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#createTable");
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
| **body** | **byte[]**| Arrow IPC data | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |
| **mode** | **String**|  | [optional] |
| **xLanceTableLocation** | **String**| URI pointing to root location to create the table at | [optional] |
| **xLanceTableProperties** | **String**| JSON-encoded string map (e.g. { \&quot;owner\&quot;: \&quot;jack\&quot; })  | [optional] |

### Return type

[**CreateTableResponse**](CreateTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
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

> DeleteFromTableResponse deleteFromTable(id, deleteFromTableRequest, delimiter)

Delete rows from a table

Delete rows from table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        DeleteFromTableRequest deleteFromTableRequest = new DeleteFromTableRequest(); // DeleteFromTableRequest | Delete request
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            DeleteFromTableResponse result = apiInstance.deleteFromTable(id, deleteFromTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#deleteFromTable");
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
| **deleteFromTableRequest** | [**DeleteFromTableRequest**](DeleteFromTableRequest.md)| Delete request | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

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


## explainTableQueryPlan

> ExplainTableQueryPlanResponse explainTableQueryPlan(id, explainTableQueryPlanRequest, delimiter)

Get query execution plan explanation

Get the query execution plan for a query against table &#x60;id&#x60;. Returns a human-readable explanation of how the query will be executed. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        ExplainTableQueryPlanRequest explainTableQueryPlanRequest = new ExplainTableQueryPlanRequest(); // ExplainTableQueryPlanRequest | 
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            ExplainTableQueryPlanResponse result = apiInstance.explainTableQueryPlan(id, explainTableQueryPlanRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#explainTableQueryPlan");
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
| **explainTableQueryPlanRequest** | [**ExplainTableQueryPlanRequest**](ExplainTableQueryPlanRequest.md)|  | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

[**ExplainTableQueryPlanResponse**](ExplainTableQueryPlanResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Query execution plan explanation |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## insertIntoTable

> InsertIntoTableResponse insertIntoTable(id, body, delimiter, mode)

Insert records into a table

Insert new records into table &#x60;id&#x60;.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the &#x60;InsertIntoTableRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;mode&#x60;: pass through query parameter of the same name 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        byte[] body = null; // byte[] | Arrow IPC stream containing the records to insert
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        String mode = "append"; // String | How the insert should behave: - append (default): insert data to the existing table - overwrite: remove all data in the table and then insert data to it 
        try {
            InsertIntoTableResponse result = apiInstance.insertIntoTable(id, body, delimiter, mode);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#insertIntoTable");
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
| **body** | **byte[]**| Arrow IPC stream containing the records to insert | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |
| **mode** | **String**| How the insert should behave: - append (default): insert data to the existing table - overwrite: remove all data in the table and then insert data to it  | [optional] [default to append] [enum: append, overwrite] |

### Return type

[**InsertIntoTableResponse**](InsertIntoTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
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


## mergeInsertIntoTable

> MergeInsertIntoTableResponse mergeInsertIntoTable(id, on, body, delimiter, whenMatchedUpdateAll, whenMatchedUpdateAllFilt, whenNotMatchedInsertAll, whenNotMatchedBySourceDelete, whenNotMatchedBySourceDeleteFilt)

Merge insert (upsert) records into a table

Performs a merge insert (upsert) operation on table &#x60;id&#x60;. This operation updates existing rows based on a matching column and inserts new rows that don&#39;t match. It returns the number of rows inserted and updated.  REST NAMESPACE ONLY REST namespace uses Arrow IPC stream as the request body. It passes in the &#x60;MergeInsertIntoTableRequest&#x60; information in the following way: - &#x60;id&#x60;: pass through path parameter of the same name - &#x60;on&#x60;: pass through query parameter of the same name - &#x60;when_matched_update_all&#x60;: pass through query parameter of the same name - &#x60;when_matched_update_all_filt&#x60;: pass through query parameter of the same name - &#x60;when_not_matched_insert_all&#x60;: pass through query parameter of the same name - &#x60;when_not_matched_by_source_delete&#x60;: pass through query parameter of the same name - &#x60;when_not_matched_by_source_delete_filt&#x60;: pass through query parameter of the same name 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        String on = "on_example"; // String | Column name to use for matching rows (required)
        byte[] body = null; // byte[] | Arrow IPC stream containing the records to merge
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        Boolean whenMatchedUpdateAll = false; // Boolean | Update all columns when rows match
        String whenMatchedUpdateAllFilt = "whenMatchedUpdateAllFilt_example"; // String | The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true
        Boolean whenNotMatchedInsertAll = false; // Boolean | Insert all columns when rows don't match
        Boolean whenNotMatchedBySourceDelete = false; // Boolean | Delete all rows from target table that don't match a row in the source table
        String whenNotMatchedBySourceDeleteFilt = "whenNotMatchedBySourceDeleteFilt_example"; // String | Delete rows from the target table if there is no match AND the SQL expression evaluates to true
        try {
            MergeInsertIntoTableResponse result = apiInstance.mergeInsertIntoTable(id, on, body, delimiter, whenMatchedUpdateAll, whenMatchedUpdateAllFilt, whenNotMatchedInsertAll, whenNotMatchedBySourceDelete, whenNotMatchedBySourceDeleteFilt);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#mergeInsertIntoTable");
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
| **on** | **String**| Column name to use for matching rows (required) | |
| **body** | **byte[]**| Arrow IPC stream containing the records to merge | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |
| **whenMatchedUpdateAll** | **Boolean**| Update all columns when rows match | [optional] [default to false] |
| **whenMatchedUpdateAllFilt** | **String**| The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true | [optional] |
| **whenNotMatchedInsertAll** | **Boolean**| Insert all columns when rows don&#39;t match | [optional] [default to false] |
| **whenNotMatchedBySourceDelete** | **Boolean**| Delete all rows from target table that don&#39;t match a row in the source table | [optional] [default to false] |
| **whenNotMatchedBySourceDeleteFilt** | **String**| Delete rows from the target table if there is no match AND the SQL expression evaluates to true | [optional] |

### Return type

[**MergeInsertIntoTableResponse**](MergeInsertIntoTableResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/vnd.apache.arrow.stream
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

> byte[] queryTable(id, queryTableRequest, delimiter)

Query a table

Query table &#x60;id&#x60; with vector search, full text search and optional SQL filtering. Returns results in Arrow IPC file or stream format. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        QueryTableRequest queryTableRequest = new QueryTableRequest(); // QueryTableRequest | Query request
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            byte[] result = apiInstance.queryTable(id, queryTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#queryTable");
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
| **queryTableRequest** | [**QueryTableRequest**](QueryTableRequest.md)| Query request | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

### Return type

**byte[]**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/vnd.apache.arrow.file, application/vnd.apache.arrow.stream, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Query results in Arrow IPC file or stream format |  -  |
| **400** | Indicates a bad request error. It could be caused by an unexpected request body format or other forms of request validation failure, such as invalid json. Usually serves application/json content, although in some cases simple text/plain content might be returned by the server&#39;s middleware. |  -  |
| **401** | Unauthorized. The request lacks valid authentication credentials for the operation. |  -  |
| **403** | Forbidden. Authenticated user does not have the necessary permissions. |  -  |
| **404** | A server-side problem that means can not find the specified resource. |  -  |
| **503** | The service is not ready to handle the request. The client should wait and retry. The service may additionally send a Retry-After header to indicate when to retry. |  -  |
| **5XX** | A server-side problem that might not be addressable from the client side. Used for server 5xx errors without more specific documentation in individual routes. |  -  |


## updateTable

> UpdateTableResponse updateTable(id, updateTableRequest, delimiter)

Update rows in a table

Update existing rows in table &#x60;id&#x60;. 

### Example

```java
// Import classes:
import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.Configuration;
import org.lance.namespace.client.apache.models.*;
import org.lance.namespace.client.apache.api.DataApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:2333");

        DataApi apiInstance = new DataApi(defaultClient);
        String id = "id_example"; // String | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace. 
        UpdateTableRequest updateTableRequest = new UpdateTableRequest(); // UpdateTableRequest | Update request
        String delimiter = "delimiter_example"; // String | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used. 
        try {
            UpdateTableResponse result = apiInstance.updateTable(id, updateTableRequest, delimiter);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DataApi#updateTable");
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
| **updateTableRequest** | [**UpdateTableRequest**](UpdateTableRequest.md)| Update request | |
| **delimiter** | **String**| An optional delimiter of the &#x60;string identifier&#x60;, following the Lance Namespace spec. When not specified, the &#x60;$&#x60; delimiter must be used.  | [optional] |

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

