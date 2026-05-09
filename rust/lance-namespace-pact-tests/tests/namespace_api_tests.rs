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

//! Pact consumer tests for the Namespace API.
//!
//! Consumer: `lance-namespace-rust-reqwest`
//! Provider: `lance-namespace-server`
//!
//! Covers all 5 MVP Namespace operations:
//! - ListNamespaces  (GET  /v1/namespace/{id}/list)
//! - DescribeNamespace (POST /v1/namespace/{id}/describe)
//! - CreateNamespace (POST /v1/namespace/{id}/create)
//! - DropNamespace   (POST /v1/namespace/{id}/drop)
//! - NamespaceExists  (POST /v1/namespace/{id}/exists)
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

// ─────────────────────────────────────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────────────────────────────────────

/// Build a type-matched ErrorResponse body matcher.
/// Fields: error (string), code (integer), type (string), detail (string).
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

// ─────────────────────────────────────────────────────────────────────────────
// ListNamespaces — GET /v1/namespace/{id}/list
// ─────────────────────────────────────────────────────────────────────────────

#[tokio::test]
async fn test_list_namespaces_returns_items() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "List child namespaces under ns_existing returns 3 items",
            "",
            |mut i| {
                i.given("namespace 'ns_existing' has 3 tables");
                i.request.method("GET");
                i.request.path("/v1/namespace/ns_existing/list");
                i.request.header("Accept", "application/json");
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({
                    "namespaces": each_like!(like!("child_a"), min = 1)
                }));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .get(format!("{}/v1/namespace/ns_existing/list", base_url))
        .header("Accept", "application/json")
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
    let body: Value = resp.json().await.expect("body parse failed");
    let namespaces = body["namespaces"]
        .as_array()
        .expect("namespaces must be array");
    assert!(!namespaces.is_empty());
    for ns in namespaces {
        assert!(ns.is_string(), "each namespace must be a string");
    }
}

#[tokio::test]
async fn test_list_namespaces_returns_404() {
    let error_body = error_response_matcher(
        "NAMESPACE_NOT_FOUND",
        404,
        "org.lance.namespace.NamespaceNotFoundException",
        "Namespace ns_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "List child namespaces under non-existent namespace returns 404",
            "",
            |mut i| {
                i.given("namespace 'ns_missing' does not exist");
                i.request.method("GET");
                i.request.path("/v1/namespace/ns_missing/list");
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
        .get(format!("{}/v1/namespace/ns_missing/list", base_url))
        .header("Accept", "application/json")
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
    assert!(body["code"].is_number());
    assert!(body["type"].is_string());
    assert!(body["detail"].is_string());
}

// ─────────────────────────────────────────────────────────────────────────────
// DescribeNamespace — POST /v1/namespace/{id}/describe
// ─────────────────────────────────────────────────────────────────────────────

#[tokio::test]
async fn test_describe_namespace_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Describe namespace ns_existing returns properties",
            "",
            |mut i| {
                i.given("namespace 'ns_existing' has 3 tables");
                i.request.method("POST");
                i.request.path("/v1/namespace/ns_existing/describe");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({}));
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({
                    "properties": like!({})
                }));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!("{}/v1/namespace/ns_existing/describe", base_url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body.get("properties").is_some());
}

#[tokio::test]
async fn test_describe_namespace_returns_404() {
    let error_body = error_response_matcher(
        "NAMESPACE_NOT_FOUND",
        404,
        "org.lance.namespace.NamespaceNotFoundException",
        "Namespace ns_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Describe non-existent namespace returns 404",
            "",
            |mut i| {
                i.given("namespace 'ns_missing' does not exist");
                i.request.method("POST");
                i.request.path("/v1/namespace/ns_missing/describe");
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
        .post(format!("{}/v1/namespace/ns_missing/describe", base_url))
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

// ─────────────────────────────────────────────────────────────────────────────
// CreateNamespace — POST /v1/namespace/{id}/create
// ─────────────────────────────────────────────────────────────────────────────

#[tokio::test]
async fn test_create_namespace_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Create namespace ns_new returns created namespace",
            "",
            |mut i| {
                i.given("namespace 'ns_new' does not exist");
                i.request.method("POST");
                i.request.path("/v1/namespace/ns_new/create");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({"mode": "Create"}));
                i.response.status(200);
                i.response.content_type("application/json");
                i.response.json_body(json_pattern!({
                    "properties": like!({})
                }));
                i
            },
        )
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!("{}/v1/namespace/ns_new/create", base_url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({"mode": "Create"}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
}

#[tokio::test]
async fn test_create_namespace_returns_409() {
    let error_body = error_response_matcher(
        "NAMESPACE_ALREADY_EXISTS",
        409,
        "org.lance.namespace.NamespaceAlreadyExistsException",
        "Namespace ns_existing already exists",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Create already-existing namespace returns 409 conflict",
            "",
            |mut i| {
                i.given("namespace 'ns_existing' has 3 tables");
                i.request.method("POST");
                i.request.path("/v1/namespace/ns_existing/create");
                i.request.header("Content-Type", "application/json");
                i.request.header("Accept", "application/json");
                i.request.json_body(json_pattern!({"mode": "Create"}));
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
        .post(format!("{}/v1/namespace/ns_existing/create", base_url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({"mode": "Create"}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 409);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

// ─────────────────────────────────────────────────────────────────────────────
// DropNamespace — POST /v1/namespace/{id}/drop
// ─────────────────────────────────────────────────────────────────────────────

#[tokio::test]
async fn test_drop_namespace_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction("Drop empty namespace ns_empty returns 200", "", |mut i| {
            i.given("namespace 'ns_empty' exists and is empty");
            i.request.method("POST");
            i.request.path("/v1/namespace/ns_empty/drop");
            i.request.header("Content-Type", "application/json");
            i.request.header("Accept", "application/json");
            i.request
                .json_body(json_pattern!({"mode": "Fail", "behavior": "Restrict"}));
            i.response.status(200);
            i.response.content_type("application/json");
            i.response.json_body(json_pattern!({}));
            i
        })
        .start_mock_server(None, None);

    let base_url_raw = mock_server.url().to_string();
    let base_url = base_url_raw.trim_end_matches('/');

    let client = reqwest::Client::new();
    let resp = client
        .post(format!("{}/v1/namespace/ns_empty/drop", base_url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({"mode": "Fail", "behavior": "Restrict"}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
}

#[tokio::test]
async fn test_drop_namespace_returns_404() {
    let error_body = error_response_matcher(
        "NAMESPACE_NOT_FOUND",
        404,
        "org.lance.namespace.NamespaceNotFoundException",
        "Namespace ns_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction("Drop non-existent namespace returns 404", "", |mut i| {
            i.given("namespace 'ns_missing' does not exist");
            i.request.method("POST");
            i.request.path("/v1/namespace/ns_missing/drop");
            i.request.header("Content-Type", "application/json");
            i.request.header("Accept", "application/json");
            i.request
                .json_body(json_pattern!({"mode": "Fail", "behavior": "Restrict"}));
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
        .post(format!("{}/v1/namespace/ns_missing/drop", base_url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({"mode": "Fail", "behavior": "Restrict"}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 404);
    let body: Value = resp.json().await.expect("body parse failed");
    assert!(body["error"].is_string());
}

// ─────────────────────────────────────────────────────────────────────────────
// NamespaceExists — POST /v1/namespace/{id}/exists
// ─────────────────────────────────────────────────────────────────────────────

#[tokio::test]
async fn test_namespace_exists_returns_200() {
    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction(
            "Check existence of ns_existing returns 200 no content",
            "",
            |mut i| {
                i.given("namespace 'ns_existing' has 3 tables");
                i.request.method("POST");
                i.request.path("/v1/namespace/ns_existing/exists");
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
        .post(format!("{}/v1/namespace/ns_existing/exists", base_url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .json(&json!({}))
        .send()
        .await
        .expect("request failed");

    assert_eq!(resp.status().as_u16(), 200);
}

#[tokio::test]
async fn test_namespace_exists_returns_404() {
    let error_body = error_response_matcher(
        "NAMESPACE_NOT_FOUND",
        404,
        "org.lance.namespace.NamespaceNotFoundException",
        "Namespace ns_missing does not exist",
    );

    let mock_server = PactBuilder::new(CONSUMER, PROVIDER)
        .interaction("Check existence of ns_missing returns 404", "", |mut i| {
            i.given("namespace 'ns_missing' does not exist");
            i.request.method("POST");
            i.request.path("/v1/namespace/ns_missing/exists");
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
        .post(format!("{}/v1/namespace/ns_missing/exists", base_url))
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
