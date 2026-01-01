

# RegisterTableRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**identity** | [**Identity**](Identity.md) |  |  [optional] |
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**location** | **String** |  |  |
|**mode** | **String** | There are two modes when trying to register a table, to differentiate the behavior when a table of the same name already exists. Case insensitive, supports both PascalCase and snake_case. Valid values are:   * Create (default): the operation fails with 409.   * Overwrite: the existing table registration is replaced with the new registration.  |  [optional] |
|**properties** | **Map&lt;String, String&gt;** |  |  [optional] |



