/*
 * Lance Namespace Specification
 *
 * This OpenAPI specification is a part of the Lance namespace specification. It contains 2 parts:  The `components/schemas`, `components/responses`, `components/examples`, `tags` sections define the request and response shape for each operation in a Lance Namespace across all implementations. See https://lancedb.github.io/lance-namespace/spec/operations for more details.  The `servers`, `security`, `paths`, `components/parameters` sections are for the  Lance REST Namespace implementation, which defines a complete REST server that can work with Lance datasets. See https://lancedb.github.io/lance-namespace/spec/impls/rest for more details. 
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 * Generated by: https://openapi-generator.tech
 */


use reqwest;
use serde::{Deserialize, Serialize, de::Error as _};
use crate::{apis::ResponseContent, models};
use super::{Error, configuration, ContentType};


/// struct for typed errors of method [`create_table_index`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum CreateTableIndexError {
    Status400(models::ErrorResponse),
    Status401(models::ErrorResponse),
    Status403(models::ErrorResponse),
    Status404(models::ErrorResponse),
    Status503(models::ErrorResponse),
    Status5XX(models::ErrorResponse),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`describe_table_index_stats`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum DescribeTableIndexStatsError {
    Status400(models::ErrorResponse),
    Status401(models::ErrorResponse),
    Status403(models::ErrorResponse),
    Status404(models::ErrorResponse),
    Status503(models::ErrorResponse),
    Status5XX(models::ErrorResponse),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`drop_table_index`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum DropTableIndexError {
    Status400(models::ErrorResponse),
    Status401(models::ErrorResponse),
    Status403(models::ErrorResponse),
    Status404(models::ErrorResponse),
    Status503(models::ErrorResponse),
    Status5XX(models::ErrorResponse),
    UnknownValue(serde_json::Value),
}

/// struct for typed errors of method [`list_table_indices`]
#[derive(Debug, Clone, Serialize, Deserialize)]
#[serde(untagged)]
pub enum ListTableIndicesError {
    Status400(models::ErrorResponse),
    Status401(models::ErrorResponse),
    Status403(models::ErrorResponse),
    Status404(models::ErrorResponse),
    Status503(models::ErrorResponse),
    Status5XX(models::ErrorResponse),
    UnknownValue(serde_json::Value),
}


/// Create an index on a table column for faster search operations. Supports vector indexes (IVF_FLAT, IVF_HNSW_SQ, IVF_PQ, etc.) and scalar indexes (BTREE, BITMAP, FTS, etc.). Index creation is handled asynchronously.  Use the `ListTableIndices` and `DescribeTableIndexStats` operations to monitor index creation progress. 
pub async fn create_table_index(configuration: &configuration::Configuration, id: &str, create_table_index_request: models::CreateTableIndexRequest, delimiter: Option<&str>) -> Result<models::CreateTableIndexResponse, Error<CreateTableIndexError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_id = id;
    let p_create_table_index_request = create_table_index_request;
    let p_delimiter = delimiter;

    let uri_str = format!("{}/v1/table/{id}/create_index", configuration.base_path, id=crate::apis::urlencode(p_id));
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref param_value) = p_delimiter {
        req_builder = req_builder.query(&[("delimiter", &param_value.to_string())]);
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    req_builder = req_builder.json(&p_create_table_index_request);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req).await?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text().await?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::CreateTableIndexResponse`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::CreateTableIndexResponse`")))),
        }
    } else {
        let content = resp.text().await?;
        let entity: Option<CreateTableIndexError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Get statistics for a specific index on a table. Returns information about the index type, distance type (for vector indices), and row counts. 
pub async fn describe_table_index_stats(configuration: &configuration::Configuration, id: &str, index_name: &str, describe_table_index_stats_request: models::DescribeTableIndexStatsRequest, delimiter: Option<&str>) -> Result<models::DescribeTableIndexStatsResponse, Error<DescribeTableIndexStatsError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_id = id;
    let p_index_name = index_name;
    let p_describe_table_index_stats_request = describe_table_index_stats_request;
    let p_delimiter = delimiter;

    let uri_str = format!("{}/v1/table/{id}/index/{index_name}/stats", configuration.base_path, id=crate::apis::urlencode(p_id), index_name=crate::apis::urlencode(p_index_name));
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref param_value) = p_delimiter {
        req_builder = req_builder.query(&[("delimiter", &param_value.to_string())]);
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    req_builder = req_builder.json(&p_describe_table_index_stats_request);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req).await?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text().await?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::DescribeTableIndexStatsResponse`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::DescribeTableIndexStatsResponse`")))),
        }
    } else {
        let content = resp.text().await?;
        let entity: Option<DescribeTableIndexStatsError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// Drop the specified index from table `id`. 
pub async fn drop_table_index(configuration: &configuration::Configuration, id: &str, index_name: &str, drop_table_index_request: models::DropTableIndexRequest, delimiter: Option<&str>) -> Result<models::DropTableIndexResponse, Error<DropTableIndexError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_id = id;
    let p_index_name = index_name;
    let p_drop_table_index_request = drop_table_index_request;
    let p_delimiter = delimiter;

    let uri_str = format!("{}/v1/table/{id}/index/{index_name}/drop", configuration.base_path, id=crate::apis::urlencode(p_id), index_name=crate::apis::urlencode(p_index_name));
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref param_value) = p_delimiter {
        req_builder = req_builder.query(&[("delimiter", &param_value.to_string())]);
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    req_builder = req_builder.json(&p_drop_table_index_request);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req).await?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text().await?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::DropTableIndexResponse`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::DropTableIndexResponse`")))),
        }
    } else {
        let content = resp.text().await?;
        let entity: Option<DropTableIndexError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

/// List all indices created on a table. Returns information about each index including name, columns, status, and UUID. 
pub async fn list_table_indices(configuration: &configuration::Configuration, id: &str, list_table_indices_request: models::ListTableIndicesRequest, delimiter: Option<&str>) -> Result<models::ListTableIndicesResponse, Error<ListTableIndicesError>> {
    // add a prefix to parameters to efficiently prevent name collisions
    let p_id = id;
    let p_list_table_indices_request = list_table_indices_request;
    let p_delimiter = delimiter;

    let uri_str = format!("{}/v1/table/{id}/index/list", configuration.base_path, id=crate::apis::urlencode(p_id));
    let mut req_builder = configuration.client.request(reqwest::Method::POST, &uri_str);

    if let Some(ref param_value) = p_delimiter {
        req_builder = req_builder.query(&[("delimiter", &param_value.to_string())]);
    }
    if let Some(ref user_agent) = configuration.user_agent {
        req_builder = req_builder.header(reqwest::header::USER_AGENT, user_agent.clone());
    }
    req_builder = req_builder.json(&p_list_table_indices_request);

    let req = req_builder.build()?;
    let resp = configuration.client.execute(req).await?;

    let status = resp.status();
    let content_type = resp
        .headers()
        .get("content-type")
        .and_then(|v| v.to_str().ok())
        .unwrap_or("application/octet-stream");
    let content_type = super::ContentType::from(content_type);

    if !status.is_client_error() && !status.is_server_error() {
        let content = resp.text().await?;
        match content_type {
            ContentType::Json => serde_json::from_str(&content).map_err(Error::from),
            ContentType::Text => return Err(Error::from(serde_json::Error::custom("Received `text/plain` content type response that cannot be converted to `models::ListTableIndicesResponse`"))),
            ContentType::Unsupported(unknown_type) => return Err(Error::from(serde_json::Error::custom(format!("Received `{unknown_type}` content type response that cannot be converted to `models::ListTableIndicesResponse`")))),
        }
    } else {
        let content = resp.text().await?;
        let entity: Option<ListTableIndicesError> = serde_json::from_str(&content).ok();
        Err(Error::ResponseError(ResponseContent { status, content, entity }))
    }
}

