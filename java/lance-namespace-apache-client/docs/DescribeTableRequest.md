

# DescribeTableRequest


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **List&lt;String&gt;** |  |  [optional] |
|**version** | **Long** | Version of the table to describe. If not specified, server should resolve it to the latest version.  |  [optional] |
|**withTableUri** | **Boolean** | Whether to include the table URI in the response. Default is false.  |  [optional] |
|**loadDetailedMetadata** | **Boolean** | Whether to load detailed metadata that requires opening the dataset. When false (default), only &#x60;location&#x60; is required in the response. When true, the response includes additional metadata such as &#x60;version&#x60;, &#x60;schema&#x60;, and &#x60;stats&#x60; which require reading the dataset.  |  [optional] |



