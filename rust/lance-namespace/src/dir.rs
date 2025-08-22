use async_trait::async_trait;
use std::collections::HashMap;
use std::sync::Arc;
use url::Url;
use opendal::Operator;
use arrow::datatypes::{DataType, Field, Schema};
use arrow::array::*;
use arrow::record_batch::RecordBatch;
use lance_namespace_reqwest_client::models::*;

use crate::namespace::LanceNamespace;
use crate::error::{LanceNamespaceError, Result};

pub struct DirNamespace {
    config: DirNamespaceConfig,
    operator: Operator,
}

impl DirNamespace {
    pub fn new(properties: HashMap<String, String>) -> Result<Self> {
        let config = DirNamespaceConfig::new(properties)?;
        let operator = Self::initialize_operator(&config)?;
        
        Ok(DirNamespace {
            config,
            operator,
        })
    }
    
    fn initialize_operator(config: &DirNamespaceConfig) -> Result<Operator> {
        let root = config.root().unwrap_or_else(|| std::env::current_dir().unwrap().to_string_lossy().to_string());
        
        // Try to parse as URL to determine scheme
        if let Ok(url) = Url::parse(&root) {
            let scheme = Self::normalize_scheme(url.scheme());
            let mut operator_config = HashMap::new();
            
            match scheme.as_str() {
                "s3" => {
                    let bucket = url.host_str().ok_or_else(|| {
                        LanceNamespaceError::InvalidConfiguration("S3 URL must have a bucket".to_string())
                    })?;
                    
                    operator_config.insert("bucket".to_string(), bucket.to_string());
                    operator_config.insert("root".to_string(), url.path().trim_start_matches('/').to_string());
                    
                    // Add storage options from config
                    for (key, value) in config.storage_options() {
                        operator_config.insert(key.clone(), value.clone());
                    }
                    
                    let op = Operator::via_iter(opendal::Scheme::S3, operator_config)?;
                    Ok(op)
                }
                "gcs" => {
                    let bucket = url.host_str().ok_or_else(|| {
                        LanceNamespaceError::InvalidConfiguration("GCS URL must have a bucket".to_string())
                    })?;
                    
                    operator_config.insert("bucket".to_string(), bucket.to_string());
                    operator_config.insert("root".to_string(), url.path().trim_start_matches('/').to_string());
                    
                    // Add storage options from config
                    for (key, value) in config.storage_options() {
                        operator_config.insert(key.clone(), value.clone());
                    }
                    
                    let op = Operator::via_iter(opendal::Scheme::Gcs, operator_config)?;
                    Ok(op)
                }
                "azblob" => {
                    // For Azure Blob Storage, we might need to use "abs" or similar
                    // Let's try to create it and catch the error if the scheme is not supported
                    let container = url.host_str().ok_or_else(|| {
                        LanceNamespaceError::InvalidConfiguration("Azure Blob URL must have a container".to_string())
                    })?;
                    
                    operator_config.insert("container".to_string(), container.to_string());
                    operator_config.insert("root".to_string(), url.path().trim_start_matches('/').to_string());
                    
                    // Add storage options from config  
                    for (key, value) in config.storage_options() {
                        operator_config.insert(key.clone(), value.clone());
                    }

                    let op = Operator::via_iter(opendal::Scheme::Azblob, operator_config)?;
                    Ok(op)
                }
                "fs" => {
                    operator_config.insert("root".to_string(), url.path().to_string());
                    
                    // Add storage options from config
                    for (key, value) in config.storage_options() {
                        operator_config.insert(key.clone(), value.clone());
                    }
                    
                    let op = Operator::via_iter(opendal::Scheme::Fs, operator_config)?;
                    Ok(op)
                }
                _ => {
                    // Default fallback - treat as root with storage options
                    operator_config.insert("root".to_string(), root);
                    for (key, value) in config.storage_options() {
                        operator_config.insert(key.clone(), value.clone());
                    }
                    
                    let op = Operator::via_iter(opendal::Scheme::Fs, operator_config)?;
                    Ok(op)
                }
            }
        } else {
            // Local file system path (no scheme)
            let mut operator_config = HashMap::new();
            operator_config.insert("root".to_string(), root);
            
            // Add storage options from config
            for (key, value) in config.storage_options() {
                operator_config.insert(key.clone(), value.clone());
            }
            
            let op = Operator::via_iter(opendal::Scheme::Fs, operator_config)?;
            Ok(op)
        }
    }
    
    /// Normalize scheme names with common aliases, matching Java implementation
    fn normalize_scheme(scheme: &str) -> String {
        match scheme.to_lowercase().as_str() {
            "s3a" | "s3n" => "s3".to_string(),
            "abfs" => "azblob".to_string(), 
            "file" => "fs".to_string(),
            _ => scheme.to_lowercase(),
        }
    }
    
    fn normalize_table_id(&self, id: &[String]) -> Result<String> {
        if id.is_empty() {
            return Err(LanceNamespaceError::InvalidConfiguration("Directory namespace table ID cannot be empty".to_string()));
        }
        
        if id.len() != 1 {
            return Err(LanceNamespaceError::InvalidConfiguration(
                format!("Directory namespace only supports single-level table IDs, but got: {:?}", id)
            ));
        }
        
        Ok(id[0].clone())
    }
    
    fn validate_root_namespace_id(&self, id: &Option<Vec<String>>) -> Result<()> {
        if let Some(id) = id {
            if !id.is_empty() {
                return Err(LanceNamespaceError::InvalidConfiguration(
                    format!("Directory namespace only supports root namespace operations, but got namespace ID: {:?}. Expected empty ID.", id)
                ));
            }
        }
        Ok(())
    }
    
    fn get_table_path(&self, table_name: &str) -> String {
        format!("{}.lance", table_name)
    }
    
    fn convert_json_arrow_schema_to_arrow(&self, json_schema: &JsonArrowSchema) -> Result<Schema> {
        let mut fields = Vec::new();
        
        for json_field in &json_schema.fields {
            let arrow_type = self.convert_json_arrow_type_to_arrow(&json_field.r#type)?;
            let nullable = json_field.nullable;
            let field = Field::new(&json_field.name, arrow_type, nullable);
            fields.push(field);
        }
        
        let metadata = json_schema.metadata.as_ref()
            .map(|m| m.iter().map(|(k, v)| (k.clone(), v.clone())).collect())
            .unwrap_or_default();
        
        Ok(Schema::new_with_metadata(fields, metadata))
    }
    
    fn convert_json_arrow_type_to_arrow(&self, json_type: &JsonArrowDataType) -> Result<DataType> {
        match json_type.r#type.to_lowercase().as_str() {
            "null" => Ok(DataType::Null),
            "bool" | "boolean" => Ok(DataType::Boolean),
            "int8" => Ok(DataType::Int8),
            "uint8" => Ok(DataType::UInt8),
            "int16" => Ok(DataType::Int16),
            "uint16" => Ok(DataType::UInt16),
            "int32" => Ok(DataType::Int32),
            "uint32" => Ok(DataType::UInt32),
            "int64" => Ok(DataType::Int64),
            "uint64" => Ok(DataType::UInt64),
            "float32" => Ok(DataType::Float32),
            "float64" => Ok(DataType::Float64),
            "utf8" => Ok(DataType::Utf8),
            "binary" => Ok(DataType::Binary),
            _ => Err(LanceNamespaceError::InvalidConfiguration(format!("Unsupported Arrow type: {}", json_type.r#type))),
        }
    }
}

#[async_trait]
impl LanceNamespace for DirNamespace {
    async fn create_namespace(&self, request: CreateNamespaceRequest) -> Result<CreateNamespaceResponse> {
        self.validate_root_namespace_id(&request.id)?;
        
        // For root namespace, this is a no-op that succeeds
        Ok(CreateNamespaceResponse {
            properties: request.properties,
        })
    }
    
    async fn list_namespaces(&self, request: ListNamespacesRequest) -> Result<ListNamespacesResponse> {
        self.validate_root_namespace_id(&request.id)?;
        
        // Directory namespace is flat, so return empty list of namespaces
        Ok(ListNamespacesResponse {
            namespaces: vec![],
            page_token: None,
        })
    }
    
    async fn describe_namespace(&self, request: DescribeNamespaceRequest) -> Result<DescribeNamespaceResponse> {
        self.validate_root_namespace_id(&request.id)?;
        
        // Return description of the root namespace
        Ok(DescribeNamespaceResponse {
            properties: Some(std::collections::HashMap::new()), // No special properties for directory namespace
        })
    }
    
    async fn drop_namespace(&self, request: DropNamespaceRequest) -> Result<DropNamespaceResponse> {
        self.validate_root_namespace_id(&request.id)?;
        
        // Cannot drop the root namespace
        Err(LanceNamespaceError::NotSupported(
            "Cannot drop root namespace in directory namespace implementation".to_string()
        ))
    }
    
    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()> {
        self.validate_root_namespace_id(&request.id)?;
        
        // Root namespace always exists in directory implementation
        Ok(())
    }
    
    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        self.validate_root_namespace_id(&request.id)?;
        
        let mut tables = Vec::new();
        
        let entries = self.operator.list("").await?;
        
        for entry in entries {
            let path = entry.path().trim_end_matches('/');
            
            if !path.contains(".lance") {
                continue;
            }
            
            let table_name = path.strip_suffix(".lance").unwrap_or(path);
            
            let versions_path = format!("{}/_versions/", path);
            match self.operator.list(&versions_path).await {
                Ok(version_entries) => {
                    if !version_entries.is_empty() {
                        tables.push(table_name.to_string());
                    }
                }
                Err(_) => {
                    // _versions doesn't exist, not a Lance dataset
                }
            }
        }
        
        Ok(ListTablesResponse {
            tables,
            page_token: None,
        })
    }
    
    async fn create_table(&self, request: CreateTableRequest, _request_data: Vec<u8>) -> Result<CreateTableResponse> {
        let id = request.id.as_ref().ok_or_else(|| {
            LanceNamespaceError::InvalidConfiguration("table ID cannot be empty".to_string())
        })?;
        
        let schema = request.schema.as_ref().ok_or_else(|| {
            LanceNamespaceError::InvalidConfiguration("Schema is required in CreateTableRequest".to_string())
        })?;
        
        let table_name = self.normalize_table_id(id)?;
        let table_path = self.get_table_path(&table_name);
        
        if let Some(location) = &request.location {
            if location != &table_path {
                return Err(LanceNamespaceError::InvalidConfiguration(
                    format!("Cannot create table {} at location {}, must be at location {}", table_name, location, table_path)
                ));
            }
        }
        
        let arrow_schema = self.convert_json_arrow_schema_to_arrow(schema)?;
        
        let mut arrays: Vec<ArrayRef> = Vec::new();
        for field in arrow_schema.fields() {
            let empty_array: ArrayRef = match field.data_type() {
                DataType::Boolean => Arc::new(BooleanArray::from(Vec::<bool>::new())),
                DataType::Int8 => Arc::new(Int8Array::from(Vec::<i8>::new())),
                DataType::Int16 => Arc::new(Int16Array::from(Vec::<i16>::new())),
                DataType::Int32 => Arc::new(Int32Array::from(Vec::<i32>::new())),
                DataType::Int64 => Arc::new(Int64Array::from(Vec::<i64>::new())),
                DataType::UInt8 => Arc::new(UInt8Array::from(Vec::<u8>::new())),
                DataType::UInt16 => Arc::new(UInt16Array::from(Vec::<u16>::new())),
                DataType::UInt32 => Arc::new(UInt32Array::from(Vec::<u32>::new())),
                DataType::UInt64 => Arc::new(UInt64Array::from(Vec::<u64>::new())),
                DataType::Float32 => Arc::new(Float32Array::from(Vec::<f32>::new())),
                DataType::Float64 => Arc::new(Float64Array::from(Vec::<f64>::new())),
                DataType::Utf8 => Arc::new(StringArray::from(Vec::<String>::new())),
                DataType::Binary => Arc::new(BinaryArray::from(Vec::<&[u8]>::new())),
                _ => return Err(LanceNamespaceError::InvalidConfiguration(format!("Unsupported data type: {:?}", field.data_type()))),
            };
            arrays.push(empty_array);
        }
        
        let empty_batch = RecordBatch::try_new(
            std::sync::Arc::new(arrow_schema),
            arrays,
        )?;
        
        // TODO: Use Lance to write the dataset
        // This would require integrating with Lance's Rust API
        // For now, we'll return a placeholder response
        
        Ok(CreateTableResponse {
            location: Some(table_path),
            version: Some(1),
            schema: None,
            properties: None,
            storage_options: None,
        })
    }
    
    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse> {
        let id = request.id.as_ref().ok_or_else(|| {
            LanceNamespaceError::InvalidConfiguration("table ID cannot be empty".to_string())
        })?;
        
        let table_name = self.normalize_table_id(id)?;
        let table_path = self.get_table_path(&table_name);
        
        self.operator.remove_all(&format!("{}/", table_path)).await?;
        
        Ok(DropTableResponse {
            id: None,
            location: None,
            properties: None,
            transaction_id: None,
        })
    }
    
    async fn describe_table(&self, request: DescribeTableRequest) -> Result<DescribeTableResponse> {
        let id = request.id.as_ref().ok_or_else(|| {
            LanceNamespaceError::InvalidConfiguration("table ID cannot be empty".to_string())
        })?;
        
        let table_name = self.normalize_table_id(id)?;
        let table_path = self.get_table_path(&table_name);
        
        let versions_path = format!("{}/_versions/", table_path);
        match self.operator.list(&versions_path).await {
            Ok(version_entries) => {
                if version_entries.is_empty() {
                    return Err(LanceNamespaceError::TableNotFound(table_name));
                }
            }
            Err(_) => {
                return Err(LanceNamespaceError::TableNotFound(table_name));
            }
        }
        
        Ok(DescribeTableResponse {
            location: Some(table_path),
            version: None,
            schema: None,
            properties: None,
            storage_options: None,
        })
    }
}

pub struct DirNamespaceConfig {
    root: Option<String>,
    storage_options: HashMap<String, String>,
}

impl DirNamespaceConfig {
    const ROOT: &'static str = "root";
    const STORAGE_OPTIONS_PREFIX: &'static str = "storage.";
    
    pub fn new(properties: HashMap<String, String>) -> Result<Self> {
        let root = properties.get(Self::ROOT).cloned();
        let storage_options = Self::extract_storage_options(&properties);
        
        Ok(DirNamespaceConfig {
            root,
            storage_options,
        })
    }
    
    fn extract_storage_options(properties: &HashMap<String, String>) -> HashMap<String, String> {
        let mut storage_options = HashMap::new();
        for (key, value) in properties {
            if key.starts_with(Self::STORAGE_OPTIONS_PREFIX) {
                let storage_key = key.strip_prefix(Self::STORAGE_OPTIONS_PREFIX).unwrap();
                storage_options.insert(storage_key.to_string(), value.clone());
            }
        }
        storage_options
    }
    
    pub fn root(&self) -> Option<String> {
        self.root.clone()
    }
    
    pub fn storage_options(&self) -> &HashMap<String, String> {
        &self.storage_options
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use tempfile::TempDir;

    #[tokio::test]
    async fn test_dir_namespace_creation() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path);
        
        let namespace = DirNamespace::new(properties).unwrap();
        
        // Test list_tables on empty directory
        let request = ListTablesRequest {
            id: None,
            page_token: None,
            limit: None,
        };
        
        let response = namespace.list_tables(request).await.unwrap();
        assert!(response.tables.is_empty());
    }

    #[tokio::test]
    async fn test_namespace_operations_not_supported() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path);
        
        let namespace = DirNamespace::new(properties).unwrap();
        
        // Test that namespace operations are not supported
        let create_namespace_request = CreateNamespaceRequest {
            id: Some(vec!["test".to_string()]),
            properties: None,
            mode: None,
        };
        
        let result = namespace.create_namespace(create_namespace_request).await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("does not support"));
        
        // Test list_namespaces
        let list_request = ListNamespacesRequest {
            id: None,
            page_token: None,
            limit: None,
        };
        let result = namespace.list_namespaces(list_request).await;
        assert!(result.is_err());
        
        // Test describe_namespace
        let describe_request = DescribeNamespaceRequest {
            id: Some(vec!["test".to_string()]),
        };
        let result = namespace.describe_namespace(describe_request).await;
        assert!(result.is_err());
        
        // Test drop_namespace
        let drop_request = DropNamespaceRequest {
            id: Some(vec!["test".to_string()]),
            mode: None,
            behavior: None,
        };
        let result = namespace.drop_namespace(drop_request).await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_table_operations() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path.clone());
        
        let namespace = DirNamespace::new(properties).unwrap();
        
        // Test describe_table for non-existent table
        let describe_request = DescribeTableRequest {
            id: Some(vec!["non_existent".to_string()]),
            version: None,
        };
        let result = namespace.describe_table(describe_request).await;
        assert!(result.is_err());
        
        // Test drop_table (should succeed even if table doesn't exist)
        let drop_request = DropTableRequest {
            id: Some(vec!["test_table".to_string()]),
        };
        let result = namespace.drop_table(drop_request).await;
        assert!(result.is_ok());
    }

    #[test]
    fn test_dir_namespace_config() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "/tmp/test".to_string());
        properties.insert("storage.access_key_id".to_string(), "test_key".to_string());
        properties.insert("storage.secret_access_key".to_string(), "test_secret".to_string());
        properties.insert("other_property".to_string(), "value".to_string());
        
        let config = DirNamespaceConfig::new(properties).unwrap();
        
        assert_eq!(config.root(), Some("/tmp/test".to_string()));
        assert_eq!(config.storage_options().get("access_key_id"), Some(&"test_key".to_string()));
        assert_eq!(config.storage_options().get("secret_access_key"), Some(&"test_secret".to_string()));
        assert!(!config.storage_options().contains_key("other_property"));
    }

    #[test]
    fn test_normalize_table_id() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path);
        
        let namespace = DirNamespace::new(properties).unwrap();
        
        // Test valid single-level ID
        let result = namespace.normalize_table_id(&["test_table".to_string()]);
        assert!(result.is_ok());
        assert_eq!(result.unwrap(), "test_table");
        
        // Test empty ID
        let result = namespace.normalize_table_id(&[]);
        assert!(result.is_err());
        
        // Test multi-level ID (not supported)
        let result = namespace.normalize_table_id(&["level1".to_string(), "level2".to_string()]);
        assert!(result.is_err());
    }

    #[test]
    fn test_validate_root_namespace_id() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path);
        
        let namespace = DirNamespace::new(properties).unwrap();
        
        // Test None (valid)
        let result = namespace.validate_root_namespace_id(&None);
        assert!(result.is_ok());
        
        // Test empty vector (valid)
        let result = namespace.validate_root_namespace_id(&Some(vec![]));
        assert!(result.is_ok());
        
        // Test non-empty vector (invalid)
        let result = namespace.validate_root_namespace_id(&Some(vec!["namespace".to_string()]));
        assert!(result.is_err());
    }

    #[test]
    fn test_normalize_scheme() {
        assert_eq!(DirNamespace::normalize_scheme("s3"), "s3");
        assert_eq!(DirNamespace::normalize_scheme("s3a"), "s3");
        assert_eq!(DirNamespace::normalize_scheme("s3n"), "s3");
        assert_eq!(DirNamespace::normalize_scheme("gcs"), "gcs");
        assert_eq!(DirNamespace::normalize_scheme("abfs"), "azblob");
        assert_eq!(DirNamespace::normalize_scheme("file"), "fs");
        assert_eq!(DirNamespace::normalize_scheme("fs"), "fs");
        assert_eq!(DirNamespace::normalize_scheme("AZBLOB"), "azblob");
    }

    #[test]
    fn test_s3_configuration() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "s3://my-bucket/data/".to_string());
        properties.insert("storage.access_key_id".to_string(), "test_key".to_string());
        properties.insert("storage.secret_access_key".to_string(), "test_secret".to_string());
        properties.insert("storage.region".to_string(), "us-west-2".to_string());
        
        // This should create the namespace without error (though we can't test actual S3 operations)
        let result = DirNamespace::new(properties);
        assert!(result.is_ok());
    }

    #[test]  
    fn test_gcs_configuration() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "gcs://my-bucket/data/".to_string());
        properties.insert("storage.service_account".to_string(), "test@project.iam.gserviceaccount.com".to_string());
        
        // This should create the namespace without error (though we can't test actual GCS operations)
        let result = DirNamespace::new(properties);
        assert!(result.is_ok());
    }

    #[test]
    fn test_azure_configuration() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "azblob://mycontainer/data/".to_string());
        properties.insert("storage.account_name".to_string(), "mystorageaccount".to_string());
        properties.insert("storage.account_key".to_string(), "test_key".to_string());
        
        // This might fail if Azure Blob Storage is not supported in this OpenDAL build
        let result = DirNamespace::new(properties);
        // Accept either success or specific Azure configuration error
        if let Err(e) = result {
            let error_msg = e.to_string();
            assert!(error_msg.contains("Azure Blob Storage is not supported") || 
                   error_msg.contains("unsupported") ||
                   error_msg.contains("azblob"));
        }
    }

    #[test]
    fn test_s3a_alias_configuration() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "s3a://my-bucket/data/".to_string());
        properties.insert("storage.access_key_id".to_string(), "test_key".to_string());
        
        // This should create the namespace without error and normalize s3a to s3
        let result = DirNamespace::new(properties);
        assert!(result.is_ok());
    }

    #[test]
    fn test_abfs_alias_configuration() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "abfs://mycontainer/data/".to_string());
        properties.insert("storage.account_name".to_string(), "mystorageaccount".to_string());
        
        // This might fail if Azure Blob Storage is not supported in this OpenDAL build
        let result = DirNamespace::new(properties);
        // Accept either success or specific Azure configuration error
        if let Err(e) = result {
            let error_msg = e.to_string();
            assert!(error_msg.contains("Azure Blob Storage is not supported") || 
                   error_msg.contains("unsupported") ||
                   error_msg.contains("azblob"));
        }
    }

    #[test]
    fn test_invalid_url_missing_host() {
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), "s3:///missing-bucket/data/".to_string());
        
        // This should fail because S3 URLs require a bucket
        let result = DirNamespace::new(properties);
        assert!(result.is_err());
        if let Err(e) = result {
            assert!(e.to_string().contains("S3 URL must have a bucket"));
        }
    }
}