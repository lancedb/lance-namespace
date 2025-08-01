/*
 * Lance Namespace Specification
 *
 * This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts:  The `components/schemas`, `components/responses`, `components/examples`, `tags` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details.  The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 * Generated by: https://openapi-generator.tech
 */

use crate::models;
use serde::{Deserialize, Serialize};

/// MergeInsertIntoTableRequest : Request for merging or inserting records into a table, excluding the Arrow IPC stream. 
#[derive(Clone, Default, Debug, PartialEq, Serialize, Deserialize)]
pub struct MergeInsertIntoTableRequest {
    #[serde(rename = "id", skip_serializing_if = "Option::is_none")]
    pub id: Option<Vec<String>>,
    /// Column name to use for matching rows (required)
    #[serde(rename = "on", skip_serializing_if = "Option::is_none")]
    pub on: Option<String>,
    /// Update all columns when rows match
    #[serde(rename = "when_matched_update_all", skip_serializing_if = "Option::is_none")]
    pub when_matched_update_all: Option<bool>,
    /// The row is updated (similar to UpdateAll) only for rows where the SQL expression evaluates to true
    #[serde(rename = "when_matched_update_all_filt", skip_serializing_if = "Option::is_none")]
    pub when_matched_update_all_filt: Option<String>,
    /// Insert all columns when rows don't match
    #[serde(rename = "when_not_matched_insert_all", skip_serializing_if = "Option::is_none")]
    pub when_not_matched_insert_all: Option<bool>,
    /// Delete all rows from target table that don't match a row in the source table
    #[serde(rename = "when_not_matched_by_source_delete", skip_serializing_if = "Option::is_none")]
    pub when_not_matched_by_source_delete: Option<bool>,
    /// Delete rows from the target table if there is no match AND the SQL expression evaluates to true
    #[serde(rename = "when_not_matched_by_source_delete_filt", skip_serializing_if = "Option::is_none")]
    pub when_not_matched_by_source_delete_filt: Option<String>,
}

impl MergeInsertIntoTableRequest {
    /// Request for merging or inserting records into a table, excluding the Arrow IPC stream. 
    pub fn new() -> MergeInsertIntoTableRequest {
        MergeInsertIntoTableRequest {
            id: None,
            on: None,
            when_matched_update_all: None,
            when_matched_update_all_filt: None,
            when_not_matched_insert_all: None,
            when_not_matched_by_source_delete: None,
            when_not_matched_by_source_delete_filt: None,
        }
    }
}

