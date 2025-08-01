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
pub struct DropNamespaceRequest {
    #[serde(rename = "id", skip_serializing_if = "Option::is_none")]
    pub id: Option<Vec<String>>,
    /// The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. - FAIL (default): the server must return 400 indicating the namespace to drop does not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded. 
    #[serde(rename = "mode", skip_serializing_if = "Option::is_none")]
    pub mode: Option<Mode>,
    /// The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - CASCADE: all tables and child namespaces in the namespace are dropped before the namespace is dropped. 
    #[serde(rename = "behavior", skip_serializing_if = "Option::is_none")]
    pub behavior: Option<Behavior>,
}

impl DropNamespaceRequest {
    pub fn new() -> DropNamespaceRequest {
        DropNamespaceRequest {
            id: None,
            mode: None,
            behavior: None,
        }
    }
}
/// The mode for dropping a namespace, deciding the server behavior when the namespace to drop is not found. - FAIL (default): the server must return 400 indicating the namespace to drop does not exist. - SKIP: the server must return 204 indicating the drop operation has succeeded. 
#[derive(Clone, Copy, Debug, Eq, PartialEq, Ord, PartialOrd, Hash, Serialize, Deserialize)]
pub enum Mode {
    #[serde(rename = "SKIP")]
    Skip,
    #[serde(rename = "FAIL")]
    Fail,
}

impl Default for Mode {
    fn default() -> Mode {
        Self::Skip
    }
}
/// The behavior for dropping a namespace. - RESTRICT (default): the namespace should not contain any table or child namespace when drop is initiated.     If tables are found, the server should return error and not drop the namespace. - CASCADE: all tables and child namespaces in the namespace are dropped before the namespace is dropped. 
#[derive(Clone, Copy, Debug, Eq, PartialEq, Ord, PartialOrd, Hash, Serialize, Deserialize)]
pub enum Behavior {
    #[serde(rename = "RESTRICT")]
    Restrict,
    #[serde(rename = "CASCADE")]
    Cascade,
}

impl Default for Behavior {
    fn default() -> Behavior {
        Self::Restrict
    }
}

