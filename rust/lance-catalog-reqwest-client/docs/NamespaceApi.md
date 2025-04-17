# \NamespaceApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_namespace**](NamespaceApi.md#create_namespace) | **POST** /v1/namespaces | Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. 
[**drop_namespace**](NamespaceApi.md#drop_namespace) | **DELETE** /v1/namespaces/{ns} | Drop a namespace from the catalog. Namespace must be empty.
[**get_namespace**](NamespaceApi.md#get_namespace) | **GET** /v1/namespaces/{ns} | Get information about a namespace
[**list_namespaces**](NamespaceApi.md#list_namespaces) | **GET** /v1/namespaces | List all namespaces in the catalog. 
[**namespace_exists**](NamespaceApi.md#namespace_exists) | **HEAD** /v1/namespaces/{ns} | Check if a namespace exists



## create_namespace

> models::CreateNamespaceResponse create_namespace(create_namespace_request)
Create a new namespace. A catalog can manage one or more namespaces. A namespace is used to manage one or more tables. There are three modes when trying to create a namespace:   * CREATE: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation fails with 400.   * EXIST_OK: Create the namespace if it does not exist. If a namespace of the same name already exists, the operation succeeds and the existing namespace is kept.   * OVERWRITE: Create the namespace if it does not exist. If a namespace of the same name already exists, the existing namespace is dropped and a new namespace with this name with no table is created. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**create_namespace_request** | [**CreateNamespaceRequest**](CreateNamespaceRequest.md) |  | [required] |

### Return type

[**models::CreateNamespaceResponse**](CreateNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## drop_namespace

> drop_namespace(ns)
Drop a namespace from the catalog. Namespace must be empty.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_namespace

> models::GetNamespaceResponse get_namespace(ns)
Get information about a namespace

Return a detailed information for a given namespace

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |

### Return type

[**models::GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_namespaces

> models::ListNamespacesResponse list_namespaces()
List all namespaces in the catalog. 

### Parameters

This endpoint does not need any parameter.

### Return type

[**models::ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## namespace_exists

> namespace_exists(ns)
Check if a namespace exists

Check if a namespace exists. The response does not contain a body.

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**ns** | **String** | The name of the namespace. | [required] |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

