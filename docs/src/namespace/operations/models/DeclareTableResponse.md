

# DeclareTableResponse

Response for declaring a table. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**transactionId** | **String** | Optional transaction identifier |  [optional] |
|**location** | **String** |  |  [optional] |
|**storageOptions** | **Map&lt;String, String&gt;** | Configuration options to be used to access storage. The available options depend on the type of storage in use. These will be passed directly to Lance to initialize storage access.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** | If the implementation does not support table properties, it should return null for this field. Otherwise it should return the properties.  |  [optional] |
|**managedVersioning** | **Boolean** | When true, the caller should use namespace table version operations (CreateTableVersion, BatchCreateTableVersions, DescribeTableVersion, ListTableVersions, BatchDeleteTableVersions) to manage table versions instead of relying on Lance&#39;s native version management.  |  [optional] |



