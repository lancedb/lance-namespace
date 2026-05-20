# \MaterializedViewApi

All URIs are relative to *http://localhost:2333*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create_materialized_view**](MaterializedViewApi.md#create_materialized_view) | **POST** /v1/materialized_view/{id}/create | Create a materialized view
[**refresh_materialized_view**](MaterializedViewApi.md#refresh_materialized_view) | **POST** /v1/materialized_view/{id}/refresh | Trigger an async materialized view refresh



## create_materialized_view

> models::CreateMaterializedViewResponse create_materialized_view(id, create_materialized_view_request, delimiter)
Create a materialized view

Create a materialized view at identifier `id`. The view may be query-backed, UDTF-backed, or chunker-backed, controlled by the `kind` discriminator. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**create_materialized_view_request** | [**CreateMaterializedViewRequest**](CreateMaterializedViewRequest.md) |  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |

### Return type

[**models::CreateMaterializedViewResponse**](CreateMaterializedViewResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


## refresh_materialized_view

> models::RefreshMaterializedViewResponse refresh_materialized_view(id, delimiter, refresh_materialized_view_request)
Trigger an async materialized view refresh

Trigger an asynchronous refresh job for materialized view `id`. Returns a job ID for tracking. 

### Parameters


Name | Type | Description  | Required | Notes
------------- | ------------- | ------------- | ------------- | -------------
**id** | **String** | `string identifier` of an object in a namespace, following the Lance Namespace spec. When the value is equal to the delimiter, it represents the root namespace. For example, `v1/namespace/$/list` performs a `ListNamespace` on the root namespace.  | [required] |
**delimiter** | Option<**String**> | An optional delimiter of the `string identifier`, following the Lance Namespace spec. When not specified, the `$` delimiter must be used.  |  |
**refresh_materialized_view_request** | Option<[**RefreshMaterializedViewRequest**](RefreshMaterializedViewRequest.md)> |  |  |

### Return type

[**models::RefreshMaterializedViewResponse**](RefreshMaterializedViewResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2), [ApiKeyAuth](../README.md#ApiKeyAuth), [BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

