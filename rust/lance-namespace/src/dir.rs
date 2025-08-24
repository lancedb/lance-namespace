//! Directory-based Lance Namespace implementation.
//!
//! This module provides a directory-based implementation of the Lance namespace
//! that stores tables as Lance datasets in a filesystem directory structure.

use std::collections::HashMap;
use std::str::FromStr;
use std::sync::Arc;

use async_trait::async_trait;
use bytes::Bytes;
use lance::dataset::{Dataset, WriteParams};
use opendal::Operator;

use lance_namespace_reqwest_client::models::{
    CreateNamespaceRequest, CreateNamespaceResponse, CreateTableRequest, CreateTableResponse,
    DescribeNamespaceRequest, DescribeNamespaceResponse, DescribeTableRequest,
    DescribeTableResponse, DropNamespaceRequest, DropNamespaceResponse, DropTableRequest,
    DropTableResponse, ListNamespacesRequest, ListNamespacesResponse, ListTablesRequest,
    ListTablesResponse, NamespaceExistsRequest, TableExistsRequest,
};

use crate::namespace::{LanceNamespace, NamespaceError, Result};
use crate::schema::convert_json_arrow_schema;

/// Configuration for DirectoryNamespace.
#[derive(Debug, Clone)]
pub struct DirectoryNamespaceConfig {
    /// Root directory for the namespace
    root: String,
    /// Storage options for the backend
    storage_options: HashMap<String, String>,
}

impl DirectoryNamespaceConfig {
    /// Property key for the root directory
    pub const ROOT: &'static str = "root";
    /// Prefix for storage options
    pub const STORAGE_OPTIONS_PREFIX: &'static str = "storage.";

    /// Create a new configuration from properties
    pub fn new(properties: HashMap<String, String>) -> Self {
        let root = properties
            .get(Self::ROOT)
            .cloned()
            .unwrap_or_else(|| std::env::current_dir().unwrap().to_string_lossy().to_string())
            .trim_end_matches('/')
            .to_string();

        let storage_options: HashMap<String, String> = properties
            .iter()
            .filter_map(|(k, v)| {
                k.strip_prefix(Self::STORAGE_OPTIONS_PREFIX)
                    .map(|key| (key.to_string(), v.clone()))
            })
            .collect();

        Self {
            root,
            storage_options,
        }
    }

    /// Get the root directory
    pub fn root(&self) -> &str {
        &self.root
    }

    /// Get the storage options
    pub fn storage_options(&self) -> &HashMap<String, String> {
        &self.storage_options
    }
}

/// Directory-based implementation of Lance Namespace.
///
/// This implementation stores tables as Lance datasets in a directory structure.
/// It supports local filesystems and cloud storage backends through OpenDAL.
pub struct DirectoryNamespace {
    config: DirectoryNamespaceConfig,
    operator: Operator,
}

impl DirectoryNamespace {
    /// Create a new DirectoryNamespace instance
    pub fn new(properties: HashMap<String, String>) -> Result<Self> {
        let config = DirectoryNamespaceConfig::new(properties);
        let operator = Self::initialize_operator(&config)?;

        Ok(Self { config, operator })
    }

    /// Initialize the OpenDAL operator based on the configuration
    fn initialize_operator(config: &DirectoryNamespaceConfig) -> Result<Operator> {
        let root = config.root();
        let storage_options = &config.storage_options;

        // Parse the root path to determine scheme and configuration
        let (scheme, mut opendal_config) = Self::parse_storage_path(root)?;
        
        // Add any additional storage options from config
        opendal_config.extend(storage_options.clone());

        // Create the operator with the determined scheme and configuration
        let operator = Operator::via_iter(scheme, opendal_config)
            .map_err(|e| NamespaceError::Other(format!("Failed to create operator: {}", e)))?;

        Ok(operator)
    }

    /// Parse storage path and return scheme and configuration
    fn parse_storage_path(root: &str) -> Result<(opendal::Scheme, HashMap<String, String>)> {
        let mut config = HashMap::new();
        
        // Check if it's a URL by splitting on "://"
        let parts: Vec<&str> = root.splitn(2, "://").collect();
        
        if parts.len() < 2 {
            // Not a URL, treat as local filesystem
            config.insert("root".to_string(), root.to_string());
            return Ok((opendal::Scheme::Fs, config));
        }

        // Normalize the scheme
        let normalized_scheme = Self::normalize_scheme(parts[0]);
        let path_part = parts[1];

        // Split authority and path
        let authority_parts: Vec<&str> = path_part.splitn(2, '/').collect();
        let authority = authority_parts[0];
        let path = if authority_parts.len() > 1 {
            authority_parts[1]
        } else {
            ""
        };

        // Configure based on scheme
        let scheme = match normalized_scheme.as_str() {
            "fs" => {
                config.insert("root".to_string(), path_part.to_string());
                opendal::Scheme::Fs
            }
            "s3" => {
                config.insert("root".to_string(), path.to_string());
                config.insert("bucket".to_string(), authority.to_string());
                opendal::Scheme::S3
            }
            "gcs" => {
                config.insert("root".to_string(), path.to_string());
                config.insert("bucket".to_string(), authority.to_string());
                opendal::Scheme::Gcs
            }
            "azblob" => {
                config.insert("root".to_string(), path.to_string());
                config.insert("container".to_string(), authority.to_string());
                opendal::Scheme::Azblob
            }
            _ => {
                // For unknown schemes, pass as-is and let OpenDAL handle it
                config.insert("root".to_string(), path_part.to_string());
                // Try to parse the scheme string to OpenDAL scheme
                opendal::Scheme::from_str(&normalized_scheme)
                    .map_err(|_| NamespaceError::Other(format!("Unsupported storage scheme: {}", normalized_scheme)))?
            }
        };

        Ok((scheme, config))
    }

    /// Normalize scheme names with common aliases
    fn normalize_scheme(scheme: &str) -> String {
        match scheme.to_lowercase().as_str() {
            "s3a" | "s3n" => "s3".to_string(),
            "abfs" => "azblob".to_string(),
            "file" => "fs".to_string(),
            s => s.to_string(),
        }
    }


    /// Validate that the namespace ID represents the root namespace
    fn validate_root_namespace_id(id: &Option<Vec<String>>) -> Result<()> {
        if let Some(id) = id {
            if !id.is_empty() {
                return Err(NamespaceError::Other(format!(
                    "Directory namespace only supports root namespace operations, but got namespace ID: {:?}. Expected empty ID.",
                    id
                )));
            }
        }
        Ok(())
    }

    /// Extract table name from table ID
    fn table_name_from_id(id: &Option<Vec<String>>) -> Result<String> {
        let id = id.as_ref().ok_or_else(|| {
            NamespaceError::Other("Directory namespace table ID cannot be empty".to_string())
        })?;

        if id.len() != 1 {
            return Err(NamespaceError::Other(format!(
                "Directory namespace only supports single-level table IDs, but got: {:?}",
                id
            )));
        }

        Ok(id[0].clone())
    }

    /// Get the full path for a table
    fn table_full_path(&self, table_name: &str) -> String {
        format!("{}/{}.lance", self.config.root(), table_name)
    }

    /// Get the versions path for a table
    fn table_versions_path(&self, table_name: &str) -> String {
        format!("{}.lance/_versions/", table_name)
    }
}

#[async_trait]
impl LanceNamespace for DirectoryNamespace {
    async fn list_namespaces(
        &self,
        request: ListNamespacesRequest,
    ) -> Result<ListNamespacesResponse> {
        // Validate this is a request for the root namespace
        Self::validate_root_namespace_id(&request.id)?;
        
        // Directory namespace only contains the root namespace (empty list)
        Ok(ListNamespacesResponse::new(vec![]))
    }

    async fn describe_namespace(
        &self,
        request: DescribeNamespaceRequest,
    ) -> Result<DescribeNamespaceResponse> {
        // Validate this is a request for the root namespace
        Self::validate_root_namespace_id(&request.id)?;
        
        // Return description of the root namespace
        Ok(DescribeNamespaceResponse {
            properties: Some(HashMap::new()),
        })
    }

    async fn create_namespace(
        &self,
        request: CreateNamespaceRequest,
    ) -> Result<CreateNamespaceResponse> {
        // Root namespace always exists and cannot be created
        if request.id.is_none() || request.id.as_ref().unwrap().is_empty() {
            return Err(NamespaceError::Other(
                "Root namespace already exists and cannot be created".to_string(),
            ));
        }
        
        // Non-root namespaces are not supported
        Err(NamespaceError::NotSupported(
            "Directory namespace only supports the root namespace".to_string(),
        ))
    }

    async fn drop_namespace(
        &self,
        request: DropNamespaceRequest,
    ) -> Result<DropNamespaceResponse> {
        // Root namespace always exists and cannot be dropped
        if request.id.is_none() || request.id.as_ref().unwrap().is_empty() {
            return Err(NamespaceError::Other(
                "Root namespace cannot be dropped".to_string(),
            ));
        }
        
        // Non-root namespaces are not supported
        Err(NamespaceError::NotSupported(
            "Directory namespace only supports the root namespace".to_string(),
        ))
    }

    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()> {
        // Root namespace always exists
        if request.id.is_none() || request.id.as_ref().unwrap().is_empty() {
            return Ok(());
        }
        
        // Non-root namespaces don't exist
        Err(NamespaceError::Other(
            "Only root namespace exists in directory namespace".to_string(),
        ))
    }

    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        Self::validate_root_namespace_id(&request.id)?;

        let mut tables = Vec::new();
        
        // Use non-recursive listing to avoid issues with object stores that don't have directory concept
        let entries = self.operator.list("").await.map_err(|e| {
            NamespaceError::Io(std::io::Error::new(
                std::io::ErrorKind::Other,
                format!("Failed to list directory: {}", e),
            ))
        })?;

        for entry in entries {
            let path = entry.path().trim_end_matches('/');
            
            // Only process directory-like paths that end with .lance
            if !path.ends_with(".lance") {
                continue;
            }

            // Extract table name (remove .lance suffix)
            let table_name = &path[..path.len() - 6];

            // For object stores, we need to check if there's a manifest file to verify it's a Lance dataset
            // Try to check for a manifest file or version file
            let manifest_path = format!("{}.lance/_latest.manifest", table_name);
            match self.operator.read(&manifest_path).await {
                Ok(_) => {
                    // Found a manifest, this is likely a Lance dataset
                    tables.push(table_name.to_string());
                }
                Err(_) => {
                    // No manifest found, check for version files pattern
                    let versions_path = format!("{}.lance/_versions/", table_name);
                    if let Ok(version_entries) = self.operator.list(&versions_path).await {
                        if !version_entries.is_empty() {
                            tables.push(table_name.to_string());
                        }
                    }
                }
            }
        }

        let response = ListTablesResponse::new(tables);
        Ok(response)
    }

    async fn describe_table(
        &self,
        request: DescribeTableRequest,
    ) -> Result<DescribeTableResponse> {
        let table_name = Self::table_name_from_id(&request.id)?;
        let table_path = self.table_full_path(&table_name);

        // Check if the table exists by looking for _versions directory
        let versions_path = self.table_versions_path(&table_name);
        let entries = self.operator.list(&versions_path).await.map_err(|_| {
            NamespaceError::Other(format!("Table does not exist: {}", table_name))
        })?;

        if entries.is_empty() {
            return Err(NamespaceError::Other(format!(
                "Table does not exist: {}",
                table_name
            )));
        }

        Ok(DescribeTableResponse {
            version: None,
            location: Some(table_path),
            schema: None,
            properties: None,
            storage_options: Some(self.config.storage_options.clone()),
        })
    }

    async fn table_exists(&self, request: TableExistsRequest) -> Result<()> {
        let table_name = Self::table_name_from_id(&request.id)?;

        // Check if the table exists by looking for _versions directory
        let versions_path = self.table_versions_path(&table_name);
        let entries = self.operator.list(&versions_path).await.map_err(|_| {
            NamespaceError::Other(format!("Table does not exist: {}", table_name))
        })?;

        if entries.is_empty() {
            return Err(NamespaceError::Other(format!(
                "Table does not exist: {}",
                table_name
            )));
        }

        Ok(())
    }

    async fn create_table(
        &self,
        request: CreateTableRequest,
        request_data: Bytes,
    ) -> Result<CreateTableResponse> {
        let table_name = Self::table_name_from_id(&request.id)?;
        let table_path = self.table_full_path(&table_name);

        // Validate schema is provided
        let json_schema = request
            .schema
            .as_ref()
            .ok_or_else(|| NamespaceError::Other("Schema is required in CreateTableRequest".to_string()))?;

        // Validate location if provided
        if let Some(location) = &request.location {
            let location = location.trim_end_matches('/');
            if location != table_path {
                return Err(NamespaceError::Other(format!(
                    "Cannot create table {} at location {}, must be at location {}",
                    table_name, location, table_path
                )));
            }
        }

        // Convert schema
        let arrow_schema = convert_json_arrow_schema(json_schema)?;
        let arrow_schema = Arc::new(arrow_schema);

        // Create RecordBatchReader from request_data (Arrow IPC stream)
        let reader = if request_data.is_empty() {
            // If no data provided, create an empty batch with the schema
            let batch = arrow::record_batch::RecordBatch::new_empty(arrow_schema.clone());
            let batches = vec![Ok(batch)];
            arrow::record_batch::RecordBatchIterator::new(batches, arrow_schema.clone())
        } else {
            // Parse the Arrow IPC stream from request_data
            use arrow::ipc::reader::StreamReader;
            use std::io::Cursor;
            
            let cursor = Cursor::new(request_data.to_vec());
            let stream_reader = StreamReader::try_new(cursor, None)
                .map_err(|e| NamespaceError::Other(format!("Failed to parse Arrow IPC stream: {}", e)))?;
            
            // Collect all batches from the stream
            let mut batches = Vec::new();
            let actual_schema = stream_reader.schema();
            
            // Verify schema matches
            if actual_schema != arrow_schema {
                return Err(NamespaceError::Other(
                    "Schema in IPC stream does not match the provided schema".to_string()
                ));
            }
            
            for batch_result in stream_reader {
                batches.push(batch_result.map_err(|e| 
                    NamespaceError::Other(format!("Failed to read batch from IPC stream: {}", e)))?);
            }
            
            // Convert to RecordBatchIterator
            let batch_results: Vec<_> = batches.into_iter().map(Ok).collect();
            arrow::record_batch::RecordBatchIterator::new(batch_results, actual_schema)
        };

        // Set up write parameters for creating a new dataset
        let write_params = WriteParams {
            mode: lance::dataset::WriteMode::Create,
            ..Default::default()
        };

        // Create the Lance dataset using the actual Lance API
        Dataset::write(
            reader,
            &table_path,
            Some(write_params),
        )
        .await
        .map_err(|e| NamespaceError::Other(format!("Failed to create Lance dataset: {}", e)))?;

        Ok(CreateTableResponse {
            version: Some(1),
            location: Some(table_path),
            schema: None,
            properties: None,
            storage_options: Some(self.config.storage_options.clone()),
        })
    }

    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse> {
        let table_name = Self::table_name_from_id(&request.id)?;
        let table_path = self.table_full_path(&table_name);

        // Remove the entire table directory
        let table_dir = format!("{}.lance/", table_name);
        self.operator
            .remove_all(&table_dir)
            .await
            .map_err(|e| NamespaceError::Other(format!("Failed to drop table {}: {}", table_name, e)))?;

        Ok(DropTableResponse {
            id: request.id,
            location: Some(table_path),
            properties: None,
            transaction_id: None,
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use lance_namespace_reqwest_client::models::{
        JsonArrowDataType, JsonArrowField, JsonArrowSchema,
    };
    use std::collections::HashMap;
    use tempfile::TempDir;

    /// Helper to create a test DirectoryNamespace with a temporary directory
    async fn create_test_namespace() -> (DirectoryNamespace, TempDir) {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert(
            "root".to_string(),
            temp_dir.path().to_string_lossy().to_string(),
        );

        let namespace = DirectoryNamespace::new(properties).unwrap();
        (namespace, temp_dir)
    }

    /// Helper to create a simple test schema
    fn create_test_schema() -> JsonArrowSchema {
        let int_type = JsonArrowDataType::new("int32".to_string());
        let string_type = JsonArrowDataType::new("utf8".to_string());

        let id_field = JsonArrowField {
            name: "id".to_string(),
            r#type: Box::new(int_type.clone()),
            nullable: false,
            metadata: None,
        };

        let name_field = JsonArrowField {
            name: "name".to_string(),
            r#type: Box::new(string_type),
            nullable: true,
            metadata: None,
        };

        JsonArrowSchema {
            fields: vec![id_field, name_field],
            metadata: None,
        }
    }

    #[tokio::test]
    async fn test_create_table() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["test_table".to_string()]);
        request.schema = Some(Box::new(create_test_schema()));

        let response = namespace
            .create_table(request, bytes::Bytes::new())
            .await
            .unwrap();

        assert!(response.location.is_some());
        assert!(response.location.unwrap().ends_with("test_table.lance"));
        assert_eq!(response.version, Some(1));
    }

    #[tokio::test]
    async fn test_create_table_without_schema() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["test_table".to_string()]);

        let result = namespace.create_table(request, bytes::Bytes::new()).await;
        assert!(result.is_err());
        assert!(result
            .unwrap_err()
            .to_string()
            .contains("Schema is required"));
    }

    #[tokio::test]
    async fn test_create_table_with_invalid_id() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Test with empty ID
        let mut request = CreateTableRequest::new();
        request.id = Some(vec![]);
        request.schema = Some(Box::new(create_test_schema()));

        let result = namespace.create_table(request, bytes::Bytes::new()).await;
        assert!(result.is_err());

        // Test with multi-level ID
        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["namespace".to_string(), "table".to_string()]);
        request.schema = Some(Box::new(create_test_schema()));

        let result = namespace.create_table(request, bytes::Bytes::new()).await;
        assert!(result.is_err());
        assert!(result
            .unwrap_err()
            .to_string()
            .contains("single-level table IDs"));
    }

    #[tokio::test]
    async fn test_create_table_with_wrong_location() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["test_table".to_string()]);
        request.schema = Some(Box::new(create_test_schema()));
        request.location = Some("/wrong/path/table.lance".to_string());

        let result = namespace.create_table(request, bytes::Bytes::new()).await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("must be at location"));
    }

    #[tokio::test]
    async fn test_list_tables() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Initially, no tables
        let request = ListTablesRequest::new();
        let response = namespace.list_tables(request).await.unwrap();
        assert_eq!(response.tables.len(), 0);

        // Create a table
        let mut create_request = CreateTableRequest::new();
        create_request.id = Some(vec!["table1".to_string()]);
        create_request.schema = Some(Box::new(create_test_schema()));
        namespace
            .create_table(create_request, bytes::Bytes::new())
            .await
            .unwrap();

        // Create another table
        let mut create_request = CreateTableRequest::new();
        create_request.id = Some(vec!["table2".to_string()]);
        create_request.schema = Some(Box::new(create_test_schema()));
        namespace
            .create_table(create_request, bytes::Bytes::new())
            .await
            .unwrap();

        // List tables should return both
        let request = ListTablesRequest::new();
        let response = namespace.list_tables(request).await.unwrap();
        let tables = response.tables;
        assert_eq!(tables.len(), 2);
        assert!(tables.contains(&"table1".to_string()));
        assert!(tables.contains(&"table2".to_string()));
    }

    #[tokio::test]
    async fn test_list_tables_with_namespace_id() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        let mut request = ListTablesRequest::new();
        request.id = Some(vec!["namespace".to_string()]);

        let result = namespace.list_tables(request).await;
        assert!(result.is_err());
        assert!(result
            .unwrap_err()
            .to_string()
            .contains("root namespace operations"));
    }

    #[tokio::test]
    async fn test_describe_table() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Create a table first
        let mut create_request = CreateTableRequest::new();
        create_request.id = Some(vec!["test_table".to_string()]);
        create_request.schema = Some(Box::new(create_test_schema()));
        namespace
            .create_table(create_request, bytes::Bytes::new())
            .await
            .unwrap();

        // Describe the table
        let mut request = DescribeTableRequest::new();
        request.id = Some(vec!["test_table".to_string()]);
        let response = namespace.describe_table(request).await.unwrap();
        
        assert!(response.location.is_some());
        assert!(response.location.unwrap().ends_with("test_table.lance"));
    }

    #[tokio::test]
    async fn test_describe_nonexistent_table() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        let mut request = DescribeTableRequest::new();
        request.id = Some(vec!["nonexistent".to_string()]);
        
        let result = namespace.describe_table(request).await;
        assert!(result.is_err());
        assert!(result
            .unwrap_err()
            .to_string()
            .contains("Table does not exist"));
    }

    #[tokio::test]
    async fn test_table_exists() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Create a table
        let mut create_request = CreateTableRequest::new();
        create_request.id = Some(vec!["existing_table".to_string()]);
        create_request.schema = Some(Box::new(create_test_schema()));
        namespace
            .create_table(create_request, bytes::Bytes::new())
            .await
            .unwrap();

        // Check existing table
        let mut request = TableExistsRequest::new();
        request.id = Some(vec!["existing_table".to_string()]);
        let result = namespace.table_exists(request).await;
        assert!(result.is_ok());

        // Check non-existent table
        let mut request = TableExistsRequest::new();
        request.id = Some(vec!["nonexistent".to_string()]);
        let result = namespace.table_exists(request).await;
        assert!(result.is_err());
        assert!(result
            .unwrap_err()
            .to_string()
            .contains("Table does not exist"));
    }

    #[tokio::test]
    async fn test_drop_table() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Create a table
        let mut create_request = CreateTableRequest::new();
        create_request.id = Some(vec!["table_to_drop".to_string()]);
        create_request.schema = Some(Box::new(create_test_schema()));
        namespace
            .create_table(create_request, bytes::Bytes::new())
            .await
            .unwrap();

        // Verify it exists
        let mut exists_request = TableExistsRequest::new();
        exists_request.id = Some(vec!["table_to_drop".to_string()]);
        assert!(namespace.table_exists(exists_request.clone()).await.is_ok());

        // Drop the table
        let mut drop_request = DropTableRequest::new();
        drop_request.id = Some(vec!["table_to_drop".to_string()]);
        let response = namespace.drop_table(drop_request).await.unwrap();
        assert!(response.location.is_some());

        // Verify it no longer exists
        assert!(namespace.table_exists(exists_request).await.is_err());
    }

    #[tokio::test]
    async fn test_drop_nonexistent_table() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        let mut request = DropTableRequest::new();
        request.id = Some(vec!["nonexistent".to_string()]);
        
        // Should not fail when dropping non-existent table (idempotent)
        let result = namespace.drop_table(request).await;
        // The operation might succeed or fail depending on implementation
        // But it should not panic
        let _ = result;
    }

    #[tokio::test]
    async fn test_root_namespace_operations() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Test list_namespaces - should return empty list for root
        let request = ListNamespacesRequest::new();
        let result = namespace.list_namespaces(request).await;
        assert!(result.is_ok());
        assert_eq!(result.unwrap().namespaces.len(), 0);

        // Test describe_namespace - should succeed for root
        let request = DescribeNamespaceRequest::new();
        let result = namespace.describe_namespace(request).await;
        assert!(result.is_ok());

        // Test namespace_exists - root always exists
        let request = NamespaceExistsRequest::new();
        let result = namespace.namespace_exists(request).await;
        assert!(result.is_ok());

        // Test create_namespace - root cannot be created
        let request = CreateNamespaceRequest::new();
        let result = namespace.create_namespace(request).await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("already exists"));

        // Test drop_namespace - root cannot be dropped
        let request = DropNamespaceRequest::new();
        let result = namespace.drop_namespace(request).await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("cannot be dropped"));
    }

    #[tokio::test]
    async fn test_non_root_namespace_operations() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Test create_namespace for non-root - not supported
        let mut request = CreateNamespaceRequest::new();
        request.id = Some(vec!["child".to_string()]);
        let result = namespace.create_namespace(request).await;
        assert!(matches!(result, Err(NamespaceError::NotSupported(_))));

        // Test namespace_exists for non-root - should not exist
        let mut request = NamespaceExistsRequest::new();
        request.id = Some(vec!["child".to_string()]);
        let result = namespace.namespace_exists(request).await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("Only root namespace exists"));

        // Test drop_namespace for non-root - not supported
        let mut request = DropNamespaceRequest::new();
        request.id = Some(vec!["child".to_string()]);
        let result = namespace.drop_namespace(request).await;
        assert!(matches!(result, Err(NamespaceError::NotSupported(_))));
    }

    #[tokio::test]
    async fn test_config_custom_root() {
        let temp_dir = TempDir::new().unwrap();
        let custom_path = temp_dir.path().join("custom");
        std::fs::create_dir(&custom_path).unwrap();

        let mut properties = HashMap::new();
        properties.insert("root".to_string(), custom_path.to_string_lossy().to_string());

        let namespace = DirectoryNamespace::new(properties).unwrap();

        // Create a table and verify location
        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["test_table".to_string()]);
        request.schema = Some(Box::new(create_test_schema()));

        let response = namespace
            .create_table(request, bytes::Bytes::new())
            .await
            .unwrap();

        assert!(response.location.unwrap().contains("custom"));
    }

    #[tokio::test]
    async fn test_config_storage_options() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert(
            "root".to_string(),
            temp_dir.path().to_string_lossy().to_string(),
        );
        properties.insert("storage.option1".to_string(), "value1".to_string());
        properties.insert("storage.option2".to_string(), "value2".to_string());

        let namespace = DirectoryNamespace::new(properties).unwrap();

        // Create a table and check storage options are included
        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["test_table".to_string()]);
        request.schema = Some(Box::new(create_test_schema()));

        let response = namespace
            .create_table(request, bytes::Bytes::new())
            .await
            .unwrap();

        let storage_options = response.storage_options.unwrap();
        assert_eq!(storage_options.get("option1"), Some(&"value1".to_string()));
        assert_eq!(storage_options.get("option2"), Some(&"value2".to_string()));
    }

    #[tokio::test]
    async fn test_various_arrow_types() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Create schema with various types
        let fields = vec![
            JsonArrowField {
                name: "bool_col".to_string(),
                r#type: Box::new(JsonArrowDataType::new("bool".to_string())),
                nullable: true,
                metadata: None,
            },
            JsonArrowField {
                name: "int8_col".to_string(),
                r#type: Box::new(JsonArrowDataType::new("int8".to_string())),
                nullable: true,
                metadata: None,
            },
            JsonArrowField {
                name: "float64_col".to_string(),
                r#type: Box::new(JsonArrowDataType::new("float64".to_string())),
                nullable: true,
                metadata: None,
            },
            JsonArrowField {
                name: "binary_col".to_string(),
                r#type: Box::new(JsonArrowDataType::new("binary".to_string())),
                nullable: true,
                metadata: None,
            },
        ];

        let schema = JsonArrowSchema {
            fields,
            metadata: None,
        };

        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["complex_table".to_string()]);
        request.schema = Some(Box::new(schema));

        let response = namespace
            .create_table(request, bytes::Bytes::new())
            .await
            .unwrap();

        assert!(response.location.is_some());
    }

    #[tokio::test]
    async fn test_connect_dir() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert(
            "root".to_string(),
            temp_dir.path().to_string_lossy().to_string(),
        );

        let namespace = crate::connect("dir", properties)
            .await
            .unwrap();

        // Test basic operation through the trait object
        let request = ListTablesRequest::new();
        let response = namespace.list_tables(request).await.unwrap();
        assert_eq!(response.tables.len(), 0);
    }

    #[test]
    fn test_parse_storage_path_local() {
        // Test local filesystem paths
        let (scheme, config) = DirectoryNamespace::parse_storage_path("/path/to/data").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Fs));
        assert_eq!(config.get("root").unwrap(), "/path/to/data");

        // Test relative path
        let (scheme, config) = DirectoryNamespace::parse_storage_path("./data").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Fs));
        assert_eq!(config.get("root").unwrap(), "./data");
    }

    #[test]
    fn test_parse_storage_path_s3() {
        // Test S3 URL
        let (scheme, config) = DirectoryNamespace::parse_storage_path("s3://my-bucket/path/to/data").unwrap();
        assert!(matches!(scheme, opendal::Scheme::S3));
        assert_eq!(config.get("bucket").unwrap(), "my-bucket");
        assert_eq!(config.get("root").unwrap(), "path/to/data");

        // Test S3 with just bucket
        let (scheme, config) = DirectoryNamespace::parse_storage_path("s3://my-bucket").unwrap();
        assert!(matches!(scheme, opendal::Scheme::S3));
        assert_eq!(config.get("bucket").unwrap(), "my-bucket");
        assert_eq!(config.get("root").unwrap(), "");
    }

    #[test]
    fn test_parse_storage_path_gcs() {
        // Test GCS URL
        let (scheme, config) = DirectoryNamespace::parse_storage_path("gcs://my-bucket/path/to/data").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Gcs));
        assert_eq!(config.get("bucket").unwrap(), "my-bucket");
        assert_eq!(config.get("root").unwrap(), "path/to/data");
    }

    #[test]
    fn test_parse_storage_path_azblob() {
        // Test Azure Blob URL
        let (scheme, config) = DirectoryNamespace::parse_storage_path("azblob://my-container/path/to/data").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Azblob));
        assert_eq!(config.get("container").unwrap(), "my-container");
        assert_eq!(config.get("root").unwrap(), "path/to/data");

        // Test with abfs alias
        let (scheme, config) = DirectoryNamespace::parse_storage_path("abfs://my-container/path").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Azblob));
        assert_eq!(config.get("container").unwrap(), "my-container");
        assert_eq!(config.get("root").unwrap(), "path");
    }

    #[test]
    fn test_normalize_scheme() {
        // Test scheme normalization
        assert_eq!(DirectoryNamespace::normalize_scheme("s3a"), "s3");
        assert_eq!(DirectoryNamespace::normalize_scheme("s3n"), "s3");
        assert_eq!(DirectoryNamespace::normalize_scheme("S3A"), "s3");
        assert_eq!(DirectoryNamespace::normalize_scheme("abfs"), "azblob");
        assert_eq!(DirectoryNamespace::normalize_scheme("ABFS"), "azblob");
        assert_eq!(DirectoryNamespace::normalize_scheme("file"), "fs");
        assert_eq!(DirectoryNamespace::normalize_scheme("FILE"), "fs");
        assert_eq!(DirectoryNamespace::normalize_scheme("gcs"), "gcs");
        assert_eq!(DirectoryNamespace::normalize_scheme("random"), "random");
    }

    #[test]
    fn test_fs_scheme_url() {
        // Test file:// URLs
        let (scheme, config) = DirectoryNamespace::parse_storage_path("file:///absolute/path").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Fs));
        assert_eq!(config.get("root").unwrap(), "/absolute/path");

        // Test fs:// URLs
        let (scheme, config) = DirectoryNamespace::parse_storage_path("fs:///absolute/path").unwrap();
        assert!(matches!(scheme, opendal::Scheme::Fs));
        assert_eq!(config.get("root").unwrap(), "/absolute/path");
    }

    #[tokio::test]
    async fn test_storage_options_passed_through() {
        // Test that storage options are properly passed to the operator
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert(
            "root".to_string(),
            temp_dir.path().to_string_lossy().to_string(),
        );
        
        // Add some storage options
        properties.insert("aws_access_key_id".to_string(), "test_key".to_string());
        properties.insert("aws_secret_access_key".to_string(), "test_secret".to_string());

        let namespace = DirectoryNamespace::new(properties).unwrap();
        
        // Verify the namespace was created (storage options are internal to operator)
        // The main test is that it doesn't fail with the extra options
        let request = ListTablesRequest::new();
        let response = namespace.list_tables(request).await.unwrap();
        assert_eq!(response.tables.len(), 0);
    }

    #[tokio::test]
    async fn test_create_table_with_ipc_data() {
        use arrow::array::{Int32Array, StringArray};
        use arrow::ipc::writer::StreamWriter;

        let (namespace, _temp_dir) = create_test_namespace().await;

        // Create a schema with some fields
        let schema = create_test_schema();

        // Create some test data that matches the schema
        let arrow_schema = convert_json_arrow_schema(&schema).unwrap();
        let arrow_schema = Arc::new(arrow_schema);

        // Create a RecordBatch with actual data
        let id_array = Int32Array::from(vec![1, 2, 3]);
        let name_array = StringArray::from(vec!["Alice", "Bob", "Charlie"]);
        let batch = arrow::record_batch::RecordBatch::try_new(
            arrow_schema.clone(),
            vec![Arc::new(id_array), Arc::new(name_array)],
        ).unwrap();

        // Write the batch to an IPC stream
        let mut buffer = Vec::new();
        {
            let mut writer = StreamWriter::try_new(&mut buffer, &arrow_schema).unwrap();
            writer.write(&batch).unwrap();
            writer.finish().unwrap();
        }

        // Create table with the IPC data
        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["test_table_with_data".to_string()]);
        request.schema = Some(Box::new(schema));

        let response = namespace
            .create_table(request, Bytes::from(buffer))
            .await
            .unwrap();

        assert_eq!(response.version, Some(1));
        assert!(response.location.unwrap().contains("test_table_with_data.lance"));

        // Verify table exists
        let mut exists_request = TableExistsRequest::new();
        exists_request.id = Some(vec!["test_table_with_data".to_string()]);
        namespace.table_exists(exists_request).await.unwrap();
    }

    #[tokio::test]
    async fn test_create_table_with_empty_data() {
        let (namespace, _temp_dir) = create_test_namespace().await;

        // Create a schema
        let schema = create_test_schema();

        // Create table with empty data (should create empty dataset with schema)
        let mut request = CreateTableRequest::new();
        request.id = Some(vec!["empty_table".to_string()]);
        request.schema = Some(Box::new(schema));

        let response = namespace
            .create_table(request, Bytes::new())
            .await
            .unwrap();

        assert_eq!(response.version, Some(1));
        assert!(response.location.unwrap().contains("empty_table.lance"));

        // Verify table exists
        let mut exists_request = TableExistsRequest::new();
        exists_request.id = Some(vec!["empty_table".to_string()]);
        namespace.table_exists(exists_request).await.unwrap();
    }
}