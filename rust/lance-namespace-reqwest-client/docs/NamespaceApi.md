# \NamespaceApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_namespace**](NamespaceApi.md#create_namespace) | **POST** /CreateNamespace | Create a new namespace
[**drop_namespace**](NamespaceApi.md#drop_namespace) | **POST** /DropNamespace | Drop a namespace
[**get_namespace**](NamespaceApi.md#get_namespace) | **POST** /GetNamespace | Get information about a namespace
[**list_namespaces**](NamespaceApi.md#list_namespaces) | **POST** /ListNamespaces | List namespaces
[**namespace_exists**](NamespaceApi.md#namespace_exists) | **POST** /NamespaceExists | Check if a namespace exists



## create_namespace

> models::GetNamespaceResponse create_namespace(create_namespace_request)
Create a new namespace

Create a new namespace. A namespace can manage either a collection of child namespaces, or a collection of tables. There are three modes when trying to create a namespace, to differentiate the behavior when a namespace of the same name already exists:   * CREATE: the operation fails with 400.   * EXIST_OK: the operation succeeds and the existing namespace is kept.   * OVERWRITE: the existing namespace is dropped and a new empty namespace with this name is created. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**create_namespace_request** | [**CreateNamespaceRequest**](CreateNamespaceRequest.md) |  | [required] |

### Return type

[**models::GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## drop_namespace

> serde_json::Value drop_namespace(drop_namespace_request)
Drop a namespace

Drop a namespace. The namespace must be empty. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**drop_namespace_request** | [**DropNamespaceRequest**](DropNamespaceRequest.md) |  | [required] |

### Return type

[**serde_json::Value**](serde_json::Value.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_namespace

> models::GetNamespaceResponse get_namespace(get_namespace_request)
Get information about a namespace

Return the detailed information for a given namespace 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**get_namespace_request** | [**GetNamespaceRequest**](GetNamespaceRequest.md) |  | [required] |

### Return type

[**models::GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_namespaces

> models::ListNamespacesResponse list_namespaces(list_namespaces_request)
List namespaces

List all child namespace names of the root namespace or a given parent namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**list_namespaces_request** | [**ListNamespacesRequest**](ListNamespacesRequest.md) |  | [required] |

### Return type

[**models::ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## namespace_exists

> models::NamespaceExistsResponse namespace_exists(namespace_exists_request)
Check if a namespace exists

Check if a namespace exists. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**namespace_exists_request** | [**NamespaceExistsRequest**](NamespaceExistsRequest.md) |  | [required] |

### Return type

[**models::NamespaceExistsResponse**](NamespaceExistsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

