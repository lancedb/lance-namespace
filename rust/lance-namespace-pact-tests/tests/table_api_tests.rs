// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//! Pact consumer tests for the Table API.
//!
//! Consumer: `lance-namespace-rust-reqwest`
//! Provider: `lance-namespace-server`
//!
//! Covers all 7 MVP Table operations:
//! - ListTables      (GET  /v1/namespace/{id}/table/list)
//! - DescribeTable   (POST /v1/table/{id}/describe)
//! - TableExists     (POST /v1/table/{id}/exists)
//! - DropTable       (POST /v1/table/{id}/drop)
//! - RegisterTable   (POST /v1/table/{id}/register)
//! - DeregisterTable (POST /v1/table/{id}/deregister)
//!
//! Provider state strings MUST match contract-pack/provider-states.lock.json verbatim.

use pact_consumer::patterns::{JsonPattern, Pattern};
use pact_consumer::prelude::*;
use pact_models::matchingrules::{MatchingRule, MatchingRuleCategory, RuleLogic};
use pact_models::path_exp::DocPath;
use serde_json::{json, Value};

/// A JsonPattern that emits {"match": "integer"} — canonical `integer` matcher.
#[derive(Debug)]
struct IntegerLike(i64);
impl Pattern for IntegerLike {
    type Matches = serde_json::Value;
    fn to_example(&self) -> Self::Matches {
        json!(self.0)
    }
    fn to_example_bytes(&self) -> Vec<u8> {
        self.0.to_string().into_bytes()
    }
    fn extract_matching_rules(&self, path: DocPath, rules_out: &mut MatchingRuleCategory) {
        rules_out.add_rule(path, MatchingRule::Integer, RuleLogic::And);
    }
}
impl From<IntegerLike> for JsonPattern {
    fn from(v: IntegerLike) -> Self {
        JsonPattern::pattern(v)
    }
}

const CONSUMER: &str = "lance-namespace-rust-reqwest";
const PROVIDER: &str = "lance-namespace-server";

/// Build a type-matched ErrorResponse body matcher.
fn error_response_matcher(
    error: &str,
    code: u16,
    error_type: &str,
    detail: &str,
) -> pact_consumer::prelude::JsonPattern {
    json_pattern!({
        "error": like!(error),
        "code": IntegerLike(code as i64),
        "type": like!(error_type),
        "detail": like!(detail)
    })
}

#[tokio::test]
async fn test_list_tables_returns_items() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "List tables in ns_with_tables returns 2 tables",
            "",
            |mut i| {
                i.given("namespace 'ns_with_tables' has 2 tables");
                i.request.method("GET");
                i.request.path("/v1/namespace/ns_with_tables/table/list");
                i.request.header("Accept", "application/json");
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({
                    "tables": each_like!(like!("table_alpha"), min = 1)
                }));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .get(format!(
            "{}/v1/namespace/ns_with_tables/table/list",
            base_url
        ))
        .header("Accept", "application/json")
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
    let body: Value = resp.json().await.expect("body parse failed");
    let tables = body["tables"].as_array().expect("tables must be array");
    assert!(!tables.is_empty());
    for tbl in tables {
        assert!(tbl.is_string(), "each table must be a string");
    }
}

#[tokio::test]
async fn test_list_tables_returns_404() {
    let error_body = error_response_matcher(
        "NAMESPACE_NOT_FOUND",
        404,
        "org.lance.namespace.NamespaceNotFoundException",
        "Namespace ns_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "List tables in non-existent namespace returns 404",
            "",
            |mut i| {
                i.given("namespace 'ns_missing' does not exist");
                i.request.method("GET");
                i.request.path("/v1/namespace/ns_missing/table/list");
                i.request.header("Accept", "application/json");
                i.response.status(404);
                i.response.content_type("application/json");
                i.response.json_body(error_body);
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .get(format!("{}/v1/namespace/ns_missing/table/list", base_url))
        .header("Accept", "application/json")
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

#[tokio::test]
async fn test_describe_table_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Describe table_alpha in ns_with_tables returns table info",
            "",
            |mut i| {
                i.given("table 'ns_with_tables.table_alpha' exists");
                i.request.method("POST");
                i.request
                    .path("/v1/table/ns_with_tables.table_alpha/describe");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({}));
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({
                    "location": like!("s3://example/ns_with_tables/table_alpha")
                }));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_with_tables.table_alpha/describe",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["location"].is_string());
}

#[tokio::test]
async fn test_describe_table_returns_404() {
    let error_body = error_response_matcher(
        "TABLE_NOT_FOUND",
        404,
        "org.lance.namespace.TableNotFoundException",
        "Table ns_existing.table_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction("Describe non-existent table returns 404", "", |mut i| {
            i.given("table 'ns_existing.table_missing' does not exist");
            i.request.method("POST");
            i.request
                .path("/v1/table/ns_existing.table_missing/describe");
            i.request.header("Content-Type", "application/json");
            i.request.header("Accept", "application/json");
            i.request.json_body(json_pattern!({}));
            i.response.status(404);
            i.response.content_type("application/json");
            i.response.json_body(error_body);
            i
        })
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_existing.table_missing/describe",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

#[tokio::test]
async fn test_table_exists_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Check existence of table_alpha in ns_with_tables returns 200",
            "",
            |mut i| {
                i.given("table 'ns_with_tables.table_alpha' exists");
                i.request.method("POST");
                i.request
                    .path("/v1/table/ns_with_tables.table_alpha/exists");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({}));
                i.response.status(200);
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_with_tables.table_alpha/exists",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
}

#[tokio::test]
async fn test_table_exists_returns_404() {
    let error_body = error_response_matcher(
        "TABLE_NOT_FOUND",
        404,
        "org.lance.namespace.TableNotFoundException",
        "Table ns_existing.table_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Check existence of non-existent table returns 404",
            "",
            |mut i| {
                i.given("table 'ns_existing.table_missing' does not exist");
                i.request.method("POST");
                i.request.path("/v1/table/ns_existing.table_missing/exists");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({}));
                i.response.status(404);
                i.response.content_type("application/json");
                i.response.json_body(error_body);
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_existing.table_missing/exists",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

#[tokio::test]
async fn test_drop_table_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Drop table_alpha in ns_with_tables returns 200",
            "",
            |mut i| {
                i.given("table 'ns_with_tables.table_alpha' exists");
                i.request.method("POST");
                i.request.path("/v1/table/ns_with_tables.table_alpha/drop");
                i.request.header("Accept", "application/json");
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({}));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_with_tables.table_alpha/drop",
            base_url
        ))
        .header("Accept", "application/json")
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
}

#[tokio::test]
async fn test_drop_table_returns_404() {
    let error_body = error_response_matcher(
        "TABLE_NOT_FOUND",
        404,
        "org.lance.namespace.TableNotFoundException",
        "Table ns_existing.table_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction("Drop non-existent table returns 404", "", |mut i| {
            i.given("table 'ns_existing.table_missing' does not exist");
            i.request.method("POST");
            i.request.path("/v1/table/ns_existing.table_missing/drop");
            i.request.header("Accept", "application/json");
            i.response.status(404);
            i.response.content_type("application/json");
            i.response.json_body(error_body);
            i
        })
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_existing.table_missing/drop",
            base_url
        ))
        .header("Accept", "application/json")
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

#[tokio::test]
async fn test_register_table_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Register new table in ns_existing returns 200",
            "",
            |mut i| {
                i.given("namespace 'ns_existing' has 3 tables");
                i.request.method("POST");
                i.request.path("/v1/table/ns_existing.table_new/register");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({
                    "location": "s3://example/ns_existing/table_new",
                    "mode": "Create"
                }));
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({
                    "location": like!("s3://example/ns_existing/table_new")
                }));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_existing.table_new/register",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({
            "location": "s3://example/ns_existing/table_new",
            "mode": "Create"
        }))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["location"].is_string());
}

#[tokio::test]
async fn test_register_table_returns_409() {
    let error_body = error_response_matcher(
        "TABLE_ALREADY_EXISTS",
        409,
        "org.lance.namespace.TableAlreadyExistsException",
        "Table ns_with_tables.table_alpha already exists",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Register already-registered table returns 409",
            "",
            |mut i| {
                i.given("table 'ns_with_tables.table_alpha' exists");
                i.request.method("POST");
                i.request
                    .path("/v1/table/ns_with_tables.table_alpha/register");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({
                    "location": "s3://example/ns_with_tables/table_alpha",
                    "mode": "Create"
                }));
                i.response.status(409);
                i.response.content_type("application/json");
                i.response.json_body(error_body);
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_with_tables.table_alpha/register",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({
            "location": "s3://example/ns_with_tables/table_alpha",
            "mode": "Create"
        }))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 409);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

#[tokio::test]
async fn test_deregister_table_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Deregister table_alpha from ns_with_tables returns 200",
            "",
            |mut i| {
                i.given("table 'ns_with_tables.table_alpha' exists");
                i.request.method("POST");
                i.request
                    .path("/v1/table/ns_with_tables.table_alpha/deregister");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({}));
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({}));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_with_tables.table_alpha/deregister",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
}

#[tokio::test]
async fn test_deregister_table_returns_404() {
    let error_body = error_response_matcher(
        "TABLE_NOT_FOUND",
        404,
        "org.lance.namespace.TableNotFoundException",
        "Table ns_existing.table_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction("Deregister non-existent table returns 404", "", |mut i| {
            i.given("table 'ns_existing.table_missing' does not exist");
            i.request.method("POST");
            i.request
                .path("/v1/table/ns_existing.table_missing/deregister");
            i.request.header("Content-Type", "application/json");
            i.request.header("Accept", "application/json");
            i.request.json_body(json_pattern!({}));
            i.response.status(404);
            i.response.content_type("application/json");
            i.response.json_body(error_body);
            i
        })
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!(
            "{}/v1/table/ns_existing.table_missing/deregister",
            base_url
        ))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}
