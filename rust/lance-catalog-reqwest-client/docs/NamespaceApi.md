# \NamespaceApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_namespace**](NamespaceApi.md#create_namespace) | **POST** /v1/namespaces | Create a new namespace
[**drop_namespace**](NamespaceApi.md#drop_namespace) | **DELETE** /v1/namespaces/{namespace} | Drop a namespace
[**get_namespace**](NamespaceApi.md#get_namespace) | **GET** /v1/namespaces/{namespace} | Get information about a namespace
[**list_namespaces**](NamespaceApi.md#list_namespaces) | **GET** /v1/namespaces | List namespaces
[**namespace_exists**](NamespaceApi.md#namespace_exists) | **HEAD** /v1/namespaces/{namespace} | Check if a namespace exists



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

> drop_namespace(namespace, delimiter)
Drop a namespace

Drop a namespace. The namespace must be empty. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**namespace** | **String** | A string identifier of the namespace. | [required] |
**delimiter** | Option<**String**> | The delimiter for the identifier used in the context |  |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## get_namespace

> models::GetNamespaceResponse get_namespace(namespace, delimiter)
Get information about a namespace

Return the detailed information for a given namespace 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**namespace** | **String** | A string identifier of the namespace. | [required] |
**delimiter** | Option<**String**> | The delimiter for the identifier used in the context |  |

### Return type

[**models::GetNamespaceResponse**](GetNamespaceResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_namespaces

> models::ListNamespacesResponse list_namespaces(page_token, page_size, parent, delimiter)
List namespaces

List all child namespace names of the root namespace or a given parent namespace. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**page_token** | Option<**String**> |  |  |
**page_size** | Option<**i32**> | An inclusive upper bound of the number of results that a client will receive. |  |
**parent** | Option<**String**> | A string identifier of the parent namespace. |  |
**delimiter** | Option<**String**> | The delimiter for the identifier used in the context |  |

### Return type

[**models::ListNamespacesResponse**](ListNamespacesResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## namespace_exists

> namespace_exists(namespace, delimiter)
Check if a namespace exists

Check if a namespace exists. This API should behave exactly like the GetNamespace API, except it does not contain a body. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**namespace** | **String** | A string identifier of the namespace. | [required] |
**delimiter** | Option<**String**> | The delimiter for the identifier used in the context |  |

### Return type

 (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

