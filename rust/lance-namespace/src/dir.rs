//! Directory-based Lance Namespace implementation.

use std::collections::HashMap;
use std::sync::Arc;

use arrow::datatypes::{DataType, Field, Schema as ArrowSchema};
use async_trait::async_trait;
use bytes::Bytes;
use opendal::Operator;

use lance_namespace_reqwest_client::models::{
    CreateNamespaceRequest, CreateNamespaceResponse, CreateTableRequest, CreateTableResponse,
    DescribeNamespaceRequest, DescribeNamespaceResponse, DescribeTableRequest,
    DescribeTableResponse, DropNamespaceRequest, DropNamespaceResponse, DropTableRequest,
    DropTableResponse, JsonArrowDataType, JsonArrowField, JsonArrowSchema,
    ListNamespacesRequest, ListNamespacesResponse, ListTablesRequest, ListTablesResponse,
    NamespaceExistsRequest, TableExistsRequest,
};

use crate::namespace::{LanceNamespace, NamespaceError, Result};

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

        // For now, we'll only support local filesystem
        // More complex storage backends can be added later
        let mut map = HashMap::new();
        map.insert("root".to_string(), root.to_string());
        
        let operator = Operator::via_map(opendal::Scheme::Fs, map)
            .map_err(|e| NamespaceError::Other(format!("Failed to create operator: {}", e)))?;

        Ok(operator)
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

    /// Convert JsonArrowSchema to Arrow Schema
    fn convert_json_arrow_schema(json_schema: &JsonArrowSchema) -> Result<ArrowSchema> {
        let fields: Result<Vec<Field>> = json_schema
            .fields
            .iter()
            .map(|f| Self::convert_json_arrow_field(f))
            .collect();

        let metadata = json_schema
            .metadata
            .as_ref()
            .map(|m| m.clone())
            .unwrap_or_default();

        Ok(ArrowSchema::new_with_metadata(fields?, metadata))
    }

    /// Convert JsonArrowField to Arrow Field
    fn convert_json_arrow_field(json_field: &JsonArrowField) -> Result<Field> {
        let data_type = Self::convert_json_arrow_type(&json_field.r#type)?;
        let nullable = json_field.nullable;

        Ok(Field::new(&json_field.name, data_type, nullable))
    }

    /// Convert JsonArrowDataType to Arrow DataType
    fn convert_json_arrow_type(json_type: &JsonArrowDataType) -> Result<DataType> {
        let type_name = json_type.r#type.to_lowercase();

        match type_name.as_str() {
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
            _ => Err(NamespaceError::Other(format!(
                "Unsupported Arrow type: {}",
                type_name
            ))),
        }
    }
}

#[async_trait]
impl LanceNamespace for DirectoryNamespace {
    async fn list_namespaces(
        &self,
        _request: ListNamespacesRequest,
    ) -> Result<ListNamespacesResponse> {
        Err(NamespaceError::NotSupported(
            "Directory namespace only contains a flat list of tables".to_string(),
        ))
    }

    async fn describe_namespace(
        &self,
        _request: DescribeNamespaceRequest,
    ) -> Result<DescribeNamespaceResponse> {
        Err(NamespaceError::NotSupported(
            "Directory namespace only contains a flat list of tables".to_string(),
        ))
    }

    async fn create_namespace(
        &self,
        _request: CreateNamespaceRequest,
    ) -> Result<CreateNamespaceResponse> {
        Err(NamespaceError::NotSupported(
            "Directory namespace only contains a flat list of tables".to_string(),
        ))
    }

    async fn drop_namespace(
        &self,
        _request: DropNamespaceRequest,
    ) -> Result<DropNamespaceResponse> {
        Err(NamespaceError::NotSupported(
            "Directory namespace only contains a flat list of tables".to_string(),
        ))
    }

    async fn namespace_exists(&self, _request: NamespaceExistsRequest) -> Result<()> {
        Err(NamespaceError::NotSupported(
            "Directory namespace only contains a flat list of tables".to_string(),
        ))
    }

    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        Self::validate_root_namespace_id(&request.id)?;

        let mut tables = Vec::new();
        let entries = self.operator.list("").await.map_err(|e| {
            NamespaceError::Io(std::io::Error::new(
                std::io::ErrorKind::Other,
                format!("Failed to list directory: {}", e),
            ))
        })?;

        for entry in entries {
            let path = entry.path().trim_end_matches('/');
            
            // Only process paths that end with .lance
            if !path.ends_with(".lance") {
                continue;
            }

            // Extract table name (remove .lance suffix)
            let table_name = &path[..path.len() - 6];

            // Check if it's a valid Lance dataset by looking for _versions directory
            let versions_path = self.table_versions_path(table_name);
            match self.operator.list(&versions_path).await {
                Ok(version_entries) => {
                    // Check if there's at least one version
                    if !version_entries.is_empty() {
                        tables.push(table_name.to_string());
                    }
                }
                Err(_) => {
                    // Not a valid Lance dataset, skip
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
        _request_data: Bytes,
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
        let arrow_schema = Self::convert_json_arrow_schema(json_schema)?;
        let _arrow_schema = Arc::new(arrow_schema);

        // For now, we'll just create a placeholder directory structure
        // In a real implementation, this would use Lance dataset creation
        let table_dir = format!("{}.lance/", table_name);
        self.operator
            .create_dir(&table_dir)
            .await
            .map_err(|e| NamespaceError::Other(format!("Failed to create table directory: {}", e)))?;
        
        // Create _versions directory to simulate Lance structure
        let versions_dir = format!("{}_versions/", table_dir);
        self.operator
            .create_dir(&versions_dir)
            .await
            .map_err(|e| NamespaceError::Other(format!("Failed to create versions directory: {}", e)))?;
        
        // Create a placeholder version file
        let version_file = format!("{}1.manifest", versions_dir);
        self.operator
            .write(&version_file, vec![0u8])
            .await
            .map_err(|e| NamespaceError::Other(format!("Failed to create version file: {}", e)))?;

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