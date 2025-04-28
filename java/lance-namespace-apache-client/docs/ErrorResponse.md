

# ErrorResponse

JSON error response model based on [RFC-7807](https://datatracker.ietf.org/doc/html/rfc7807)

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**type** | **String** | a URI identifier that categorizes the error |  |
|**title** | **String** | a brief, human-readable message about the error |  [optional] |
|**status** | **Integer** | HTTP response code, (if present) it must match the actual HTTP code returned by the service |  [optional] |
|**detail** | **String** | a human-readable explanation of the error |  [optional] |
|**instance** | **String** | a URI that identifies the specific occurrence of the error |  [optional] |



