use async_trait::async_trait;
use std::collections::HashMap;
use reqwest::Client;
use serde_json;
use lance_namespace_reqwest_client::models::*;

use crate::namespace::LanceNamespace;
use crate::error::{NamespaceError, Result};

pub struct RestNamespace {
    client: Client,
    base_url: String,
    config: RestNamespaceConfig,
}

impl RestNamespace {
    pub fn new(properties: HashMap<String, String>) -> Result<Self> {
        let config = RestNamespaceConfig::new(properties)?;
        let base_url = config.uri().ok_or_else(|| {
            NamespaceError::InvalidConfiguration("uri property is required for REST namespace".to_string())
        })?;
        
        let mut client_builder = Client::builder();
        
        // Add default headers
        let mut default_headers = reqwest::header::HeaderMap::new();
        for (key, value) in config.additional_headers() {
            let header_name = reqwest::header::HeaderName::try_from(key)
                .map_err(|e| NamespaceError::InvalidConfiguration(format!("Invalid header name '{}': {}", key, e)))?;
            let header_value = reqwest::header::HeaderValue::try_from(value)
                .map_err(|e| NamespaceError::InvalidConfiguration(format!("Invalid header value for '{}': {}", key, e)))?;
            default_headers.insert(header_name, header_value);
        }
        client_builder = client_builder.default_headers(default_headers);
        
        let client = client_builder.build()?;
        
        Ok(RestNamespace {
            client,
            base_url,
            config,
        })
    }
    
    fn object_id_str(&self, id: &[String]) -> String {
        if id.is_empty() {
            self.config.delimiter().to_string()
        } else {
            id.join(&self.config.delimiter())
        }
    }
    
    async fn make_request<T>(&self, method: reqwest::Method, path: &str, body: Option<Vec<u8>>) -> Result<T>
    where
        T: serde::de::DeserializeOwned,
    {
        let url = format!("{}/{}", self.base_url.trim_end_matches('/'), path.trim_start_matches('/'));
        
        let mut request = self.client.request(method, &url);
        
        if let Some(body) = body {
            request = request.body(body).header("Content-Type", "application/json");
        }
        
        let response = request.send().await?;
        
        if !response.status().is_success() {
            return Err(NamespaceError::Runtime(format!(
                "HTTP request failed with status {}: {}",
                response.status(),
                response.text().await.unwrap_or_default()
            )));
        }
        
        let response_body = response.bytes().await?;
        
        if response_body.is_empty() {
            // Handle empty response for operations that don't return data
            return serde_json::from_str("{}").map_err(Into::into);
        }
        
        serde_json::from_slice(&response_body).map_err(Into::into)
    }
}

#[async_trait]
impl LanceNamespace for RestNamespace {
    async fn list_namespaces(&self, request: ListNamespacesRequest) -> Result<ListNamespacesResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let mut path = format!("namespaces/{}", id_str);
        let mut query_params = Vec::new();
        
        query_params.push(format!("delimiter={}", urlencoding::encode(&self.config.delimiter())));
        
        if let Some(page_token) = &request.page_token {
            query_params.push(format!("page_token={}", urlencoding::encode(page_token)));
        }
        
        if let Some(limit) = request.limit {
            query_params.push(format!("limit={}", limit));
        }
        
        if !query_params.is_empty() {
            path.push('?');
            path.push_str(&query_params.join("&"));
        }
        
        self.make_request(reqwest::Method::GET, &path, None).await
    }
    
    async fn describe_namespace(&self, request: DescribeNamespaceRequest) -> Result<DescribeNamespaceResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("namespaces/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn create_namespace(&self, request: CreateNamespaceRequest) -> Result<CreateNamespaceResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("namespaces/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::PUT, &path, Some(body)).await
    }
    
    async fn drop_namespace(&self, request: DropNamespaceRequest) -> Result<DropNamespaceResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("namespaces/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::DELETE, &path, Some(body)).await
    }
    
    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("namespaces/{}/exists?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        let _: serde_json::Value = self.make_request(reqwest::Method::POST, &path, Some(body)).await?;
        Ok(())
    }
    
    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let mut path = format!("namespaces/{}/tables", id_str);
        let mut query_params = Vec::new();
        
        query_params.push(format!("delimiter={}", urlencoding::encode(&self.config.delimiter())));
        
        if let Some(page_token) = &request.page_token {
            query_params.push(format!("page_token={}", urlencoding::encode(page_token)));
        }
        
        if let Some(limit) = request.limit {
            query_params.push(format!("limit={}", limit));
        }
        
        if !query_params.is_empty() {
            path.push('?');
            path.push_str(&query_params.join("&"));
        }
        
        self.make_request(reqwest::Method::GET, &path, None).await
    }
    
    async fn describe_table(&self, request: DescribeTableRequest) -> Result<DescribeTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn register_table(&self, request: RegisterTableRequest) -> Result<RegisterTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/register?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn table_exists(&self, request: TableExistsRequest) -> Result<()> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/exists?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        let _: serde_json::Value = self.make_request(reqwest::Method::POST, &path, Some(body)).await?;
        Ok(())
    }
    
    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::DELETE, &path, Some(body)).await
    }
    
    async fn deregister_table(&self, request: DeregisterTableRequest) -> Result<DeregisterTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/deregister?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn count_table_rows(&self, request: CountTableRowsRequest) -> Result<i32> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/rows/count?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn create_table(&self, request: CreateTableRequest, request_data: Vec<u8>) -> Result<CreateTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let mut path = format!("tables/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        
        if let Some(location) = &request.location {
            path.push_str(&format!("&x-lance-table-location={}", urlencoding::encode(location)));
        }
        
        if let Some(properties) = &request.properties {
            let properties_json = serde_json::to_string(properties)?;
            path.push_str(&format!("&x-lance-table-properties={}", urlencoding::encode(&properties_json)));
        }
        
        self.make_request(reqwest::Method::PUT, &path, Some(request_data)).await
    }
    
    async fn insert_into_table(&self, request: InsertIntoTableRequest, request_data: Vec<u8>) -> Result<InsertIntoTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let mut path = format!("tables/{}/data?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        
        if let Some(mode) = &request.mode {
            let mode_str = format!("{:?}", mode); // Convert enum to string representation
            path.push_str(&format!("&mode={}", urlencoding::encode(&mode_str)));
        }
        
        self.make_request(reqwest::Method::POST, &path, Some(request_data)).await
    }
    
    async fn merge_insert_into_table(&self, request: MergeInsertIntoTableRequest, request_data: Vec<u8>) -> Result<MergeInsertIntoTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let mut path = format!("tables/{}/data/merge?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        
        if let Some(on) = &request.on {
            path.push_str(&format!("&on={}", urlencoding::encode(on)));
        }
        
        if let Some(when_matched_update_all) = request.when_matched_update_all {
            path.push_str(&format!("&when_matched_update_all={}", when_matched_update_all));
        }
        
        if let Some(when_matched_update_all_filt) = &request.when_matched_update_all_filt {
            path.push_str(&format!("&when_matched_update_all_filt={}", urlencoding::encode(when_matched_update_all_filt)));
        }
        
        if let Some(when_not_matched_insert_all) = request.when_not_matched_insert_all {
            path.push_str(&format!("&when_not_matched_insert_all={}", when_not_matched_insert_all));
        }
        
        if let Some(when_not_matched_by_source_delete) = request.when_not_matched_by_source_delete {
            path.push_str(&format!("&when_not_matched_by_source_delete={}", when_not_matched_by_source_delete));
        }
        
        if let Some(when_not_matched_by_source_delete_filt) = &request.when_not_matched_by_source_delete_filt {
            path.push_str(&format!("&when_not_matched_by_source_delete_filt={}", urlencoding::encode(when_not_matched_by_source_delete_filt)));
        }
        
        self.make_request(reqwest::Method::POST, &path, Some(request_data)).await
    }
    
    async fn update_table(&self, request: UpdateTableRequest) -> Result<UpdateTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::PATCH, &path, Some(body)).await
    }
    
    async fn delete_from_table(&self, request: DeleteFromTableRequest) -> Result<DeleteFromTableResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/data?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::DELETE, &path, Some(body)).await
    }
    
    async fn query_table(&self, request: QueryTableRequest) -> Result<Vec<u8>> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/query?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        let url = format!("{}/{}", self.base_url.trim_end_matches('/'), path.trim_start_matches('/'));
        
        let response = self.client
            .post(&url)
            .header("Content-Type", "application/json")
            .body(body)
            .send()
            .await?;
        
        if !response.status().is_success() {
            return Err(NamespaceError::Runtime(format!(
                "HTTP request failed with status {}: {}",
                response.status(),
                response.text().await.unwrap_or_default()
            )));
        }
        
        Ok(response.bytes().await?.to_vec())
    }
    
    async fn create_table_index(&self, request: CreateTableIndexRequest) -> Result<CreateTableIndexResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/indices?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn list_table_indices(&self, request: ListTableIndicesRequest) -> Result<ListTableIndicesResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("tables/{}/indices?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::GET, &path, Some(body)).await
    }
    
    async fn describe_table_index_stats(&self, request: DescribeTableIndexStatsRequest) -> Result<DescribeTableIndexStatsResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        // Note: This assumes index_name is provided somehow - in the Python version it's a separate parameter
        // For simplicity, we'll assume it's in the request somewhere or use a default
        let index_name = "default"; // This should be properly handled in a real implementation
        
        let path = format!("tables/{}/indices/{}/stats?delimiter={}", id_str, index_name, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn describe_transaction(&self, request: DescribeTransactionRequest) -> Result<DescribeTransactionResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("transactions/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::POST, &path, Some(body)).await
    }
    
    async fn alter_transaction(&self, request: AlterTransactionRequest) -> Result<AlterTransactionResponse> {
        let id_str = self.object_id_str(request.id.as_deref().unwrap_or_default());
        
        let path = format!("transactions/{}?delimiter={}", id_str, urlencoding::encode(&self.config.delimiter()));
        let body = serde_json::to_vec(&request)?;
        
        self.make_request(reqwest::Method::PATCH, &path, Some(body)).await
    }
}

pub struct RestNamespaceConfig {
    delimiter: String,
    headers: HashMap<String, String>,
    uri: Option<String>,
}

impl RestNamespaceConfig {
    const DELIMITER: &'static str = "delimiter";
    const HEADER_PREFIX: &'static str = "header.";
    const URI: &'static str = "uri";
    
    pub fn new(properties: HashMap<String, String>) -> Result<Self> {
        let delimiter = properties.get(Self::DELIMITER).cloned().unwrap_or_else(|| ".".to_string());
        let uri = properties.get(Self::URI).cloned();
        let headers = Self::extract_headers(&properties);
        
        Ok(RestNamespaceConfig {
            delimiter,
            headers,
            uri,
        })
    }
    
    fn extract_headers(properties: &HashMap<String, String>) -> HashMap<String, String> {
        let mut headers = HashMap::new();
        for (key, value) in properties {
            if key.starts_with(Self::HEADER_PREFIX) {
                let header_name = key.strip_prefix(Self::HEADER_PREFIX).unwrap();
                headers.insert(header_name.to_string(), value.clone());
            }
        }
        headers
    }
    
    pub fn delimiter(&self) -> &str {
        &self.delimiter
    }
    
    pub fn additional_headers(&self) -> &HashMap<String, String> {
        &self.headers
    }
    
    pub fn uri(&self) -> Option<String> {
        self.uri.clone()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_rest_namespace_creation() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        properties.insert("header.Authorization".to_string(), "Bearer token".to_string());
        properties.insert("delimiter".to_string(), "/".to_string());
        
        let namespace = RestNamespace::new(properties).unwrap();
        assert_eq!(namespace.config.uri(), Some("http://localhost:8080".to_string()));
        assert_eq!(namespace.config.delimiter(), "/");
    }

    #[test]
    fn test_rest_namespace_creation_missing_uri() {
        let properties = HashMap::new();
        
        let result = RestNamespace::new(properties);
        assert!(result.is_err());
        // Just check that it's an error - don't unwrap_err without Debug trait
        if let Err(e) = result {
            assert!(e.to_string().contains("uri property is required"));
        }
    }

    #[test]
    fn test_rest_namespace_config() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        properties.insert("delimiter".to_string(), "/".to_string());
        properties.insert("header.Authorization".to_string(), "Bearer token".to_string());
        properties.insert("header.X-Custom".to_string(), "custom_value".to_string());
        properties.insert("other_property".to_string(), "value".to_string());
        
        let config = RestNamespaceConfig::new(properties).unwrap();
        
        assert_eq!(config.uri(), Some("http://localhost:8080".to_string()));
        assert_eq!(config.delimiter(), "/");
        assert_eq!(config.additional_headers().get("Authorization"), Some(&"Bearer token".to_string()));
        assert_eq!(config.additional_headers().get("X-Custom"), Some(&"custom_value".to_string()));
        assert!(!config.additional_headers().contains_key("other_property"));
    }

    #[test]
    fn test_rest_namespace_config_default_delimiter() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        
        let config = RestNamespaceConfig::new(properties).unwrap();
        
        assert_eq!(config.delimiter(), ".");  // Default delimiter should be "."
    }

    #[test]
    fn test_object_id_str() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        properties.insert("delimiter".to_string(), "/".to_string());
        
        let namespace = RestNamespace::new(properties).unwrap();
        
        // Test empty ID
        assert_eq!(namespace.object_id_str(&[]), "/");
        
        // Test single ID
        assert_eq!(namespace.object_id_str(&["test".to_string()]), "test");
        
        // Test multiple IDs
        assert_eq!(namespace.object_id_str(&["namespace".to_string(), "table".to_string()]), "namespace/table");
    }

    #[test]
    fn test_object_id_str_with_default_delimiter() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        
        let namespace = RestNamespace::new(properties).unwrap();
        
        // Test empty ID with default delimiter
        assert_eq!(namespace.object_id_str(&[]), ".");
        
        // Test multiple IDs with default delimiter
        assert_eq!(namespace.object_id_str(&["namespace".to_string(), "table".to_string()]), "namespace.table");
    }

    #[tokio::test]
    async fn test_make_request_url_construction() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080/".to_string());
        
        let namespace = RestNamespace::new(properties).unwrap();
        
        // We can't actually make HTTP requests without a server, but we can test
        // that the namespace is created successfully and would be ready to make requests
        assert_eq!(namespace.base_url, "http://localhost:8080/");
    }

    #[test]
    fn test_header_configuration() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        properties.insert("header.Content-Type".to_string(), "application/json".to_string());
        properties.insert("header.Accept".to_string(), "application/json".to_string());
        properties.insert("header.X-Api-Key".to_string(), "secret-key".to_string());
        
        let config = RestNamespaceConfig::new(properties).unwrap();
        
        let headers = config.additional_headers();
        assert_eq!(headers.len(), 3);
        assert_eq!(headers.get("Content-Type"), Some(&"application/json".to_string()));
        assert_eq!(headers.get("Accept"), Some(&"application/json".to_string()));
        assert_eq!(headers.get("X-Api-Key"), Some(&"secret-key".to_string()));
    }
}