/*
 * Lance REST Namespace Specification
 *
 * This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts: The `components/schemas`, `components/responses`, `components/examples` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details. The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 
 *
 * The version of the OpenAPI document: 0.0.1
 * 
 * Generated by: https://openapi-generator.tech
 */

use crate::models;
use serde::{Deserialize, Serialize};

#[derive(Clone, Debug, PartialEq, Serialize, Deserialize)]
#[serde(untagged)]
pub enum FtsQuery {
    FtsQueryOneOf(Box<models::FtsQueryOneOf>),
    FtsQueryOneOf1(Box<models::FtsQueryOneOf1>),
    FtsQueryOneOf2(Box<models::FtsQueryOneOf2>),
    FtsQueryOneOf3(Box<models::FtsQueryOneOf3>),
    FtsQueryOneOf4(Box<models::FtsQueryOneOf4>),
}

impl Default for FtsQuery {
    fn default() -> Self {
        Self::FtsQueryOneOf(Default::default())
    }
}

