# \BranchApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_table_branch**](BranchApi.md#create_table_branch) | **POST** /v1/table/{id}/branches/create | Create a new branch
[**delete_table_branch**](BranchApi.md#delete_table_branch) | **POST** /v1/table/{id}/branches/delete | Delete a branch
[**list_table_branches**](BranchApi.md#list_table_branches) | **POST** /v1/table/{id}/branches/list | List all branches for a table



## create_table_branch

> models::CreateTableBranchResponse create_table_branch(id, create_table_branch_request, delimiter)
Create a new branch

Create a new branch for table `id` starting from a source ref (another branch and/or version), defaulting to the latest version of the main branch. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_table_branch_request** | [**CreateTableBranchRequest**](CreateTableBranchRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::CreateTableBranchResponse**](CreateTableBranchResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## delete_table_branch

> models::DeleteTableBranchResponse delete_table_branch(id, delete_table_branch_request, delimiter)
Delete a branch

Delete an existing branch from table `id`. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**delete_table_branch_request** | [**DeleteTableBranchRequest**](DeleteTableBranchRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::DeleteTableBranchResponse**](DeleteTableBranchResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## list_table_branches

> models::ListTableBranchesResponse list_table_branches(id, delimiter, page_token, limit)
List all branches for a table

List all branches that have been created for table `id`. Returns a map of branch names to their contents.  REST NAMESPACE ONLY REST namespace does not use a request body for this operation. The `ListTableBranchesRequest` information is passed in the following way: - `id`: pass through path parameter of the same name - `page_token`: pass through query parameter of the same name - `limit`: pass through query parameter of the same name 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |
**page_token** | Option<**String**> | Pagination token from a previous request |  |
**limit** | Option<**i32**> | Maximum number of items to return |  |

### Return type

[**models::ListTableBranchesResponse**](ListTableBranchesResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

