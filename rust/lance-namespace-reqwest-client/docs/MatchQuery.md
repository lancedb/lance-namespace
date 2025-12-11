# MatchQuery

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**boost** | Option<**f32**> |  | [optional]
**column** | Option<**String**> |  | [optional]
**fuzziness** | Option<**i32**> |  | [optional]
**max_expansions** | Option<**i32**> | The maximum number of terms to expand for fuzzy matching. Default to 50. | [optional]
**operator** | Option<**String**> | The operator to use for combining terms. Case insensitive. Valid values are: - And: All terms must match. - Or: At least one term must match.  | [optional]
**prefix_length** | Option<**i32**> | The number of beginning characters being unchanged for fuzzy matching. Default to 0. | [optional]
**terms** | **String** |  | 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)


