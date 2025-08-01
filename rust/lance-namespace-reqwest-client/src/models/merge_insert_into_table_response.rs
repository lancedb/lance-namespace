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

/// MergeInsertIntoTableResponse : Response from merge insert operation
#[derive(Clone, Default, Debug, PartialEq, Serialize, Deserialize)]
pub struct MergeInsertIntoTableResponse {
    /// Number of rows updated
    #[serde(rename = "num_updated_rows", skip_serializing_if = "Option::is_none")]
    pub num_updated_rows: Option<i64>,
    /// Number of rows inserted
    #[serde(rename = "num_inserted_rows", skip_serializing_if = "Option::is_none")]
    pub num_inserted_rows: Option<i64>,
    /// Number of rows deleted (typically 0 for merge insert)
    #[serde(rename = "num_deleted_rows", skip_serializing_if = "Option::is_none")]
    pub num_deleted_rows: Option<i64>,
    /// The commit version associated with the operation
    #[serde(rename = "version", skip_serializing_if = "Option::is_none")]
    pub version: Option<i64>,
}

impl MergeInsertIntoTableResponse {
    /// Response from merge insert operation
    pub fn new() -> MergeInsertIntoTableResponse {
        MergeInsertIntoTableResponse {
            num_updated_rows: None,
            num_inserted_rows: None,
            num_deleted_rows: None,
            version: None,
        }
    }
}

