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

#[derive(Clone, Default, Debug, PartialEq, Serialize, Deserialize)]
pub struct AlterTableDropColumnsRequest {
    #[serde(rename = "id", skip_serializing_if = "Option::is_none")]
    pub id: Option<Vec<String>>,
    /// Names of columns to drop
    #[serde(rename = "columns")]
    pub columns: Vec<String>,
}

impl AlterTableDropColumnsRequest {
    pub fn new(columns: Vec<String>) -> AlterTableDropColumnsRequest {
        AlterTableDropColumnsRequest {
            id: None,
            columns,
        }
    }
}

