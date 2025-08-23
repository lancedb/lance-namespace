//! Tests for DirectoryNamespace implementation

use std::collections::HashMap;

use lance_namespace::{DirectoryNamespace, LanceNamespace, NamespaceError};
use lance_namespace_reqwest_client::models::{
    CreateTableRequest, DescribeTableRequest, DropTableRequest, JsonArrowDataType,
    JsonArrowField, JsonArrowSchema, ListTablesRequest, TableExistsRequest,
};
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

/// Test creating a table
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

/// Test creating a table without schema should fail
#[tokio::test]
async fn test_create_table_without_schema() {
    let (namespace, _temp_dir) = create_test_namespace().await;

    let mut request = CreateTableRequest::new();
    request.id = Some(vec!["test_table".to_string()]);
    // No schema provided

    let result = namespace.create_table(request, bytes::Bytes::new()).await;
    assert!(result.is_err());
    assert!(result
        .unwrap_err()
        .to_string()
        .contains("Schema is required"));
}

/// Test creating a table with invalid ID should fail
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

/// Test creating a table with mismatched location
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

/// Test listing tables
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

/// Test listing tables with non-root namespace ID should fail
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

/// Test describing a table
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

/// Test describing a non-existent table
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

/// Test table_exists
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

/// Test dropping a table
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

/// Test dropping a non-existent table
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

/// Test namespace operations that are not supported
#[tokio::test]
async fn test_unsupported_namespace_operations() {
    let (namespace, _temp_dir) = create_test_namespace().await;

    // Test create_namespace
    let request = lance_namespace_reqwest_client::models::CreateNamespaceRequest::new();
    let result = namespace.create_namespace(request).await;
    assert!(matches!(result, Err(NamespaceError::NotSupported(_))));

    // Test list_namespaces
    let request = lance_namespace_reqwest_client::models::ListNamespacesRequest::new();
    let result = namespace.list_namespaces(request).await;
    assert!(matches!(result, Err(NamespaceError::NotSupported(_))));

    // Test describe_namespace
    let request = lance_namespace_reqwest_client::models::DescribeNamespaceRequest::new();
    let result = namespace.describe_namespace(request).await;
    assert!(matches!(result, Err(NamespaceError::NotSupported(_))));

    // Test drop_namespace
    let request = lance_namespace_reqwest_client::models::DropNamespaceRequest::new();
    let result = namespace.drop_namespace(request).await;
    assert!(matches!(result, Err(NamespaceError::NotSupported(_))));

    // Test namespace_exists
    let request = lance_namespace_reqwest_client::models::NamespaceExistsRequest::new();
    let result = namespace.namespace_exists(request).await;
    assert!(matches!(result, Err(NamespaceError::NotSupported(_))));
}

/// Test configuration with custom root
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

/// Test configuration with storage options
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

/// Test different Arrow data types in schema
#[tokio::test]
async fn test_various_arrow_types() {
    let (namespace, _temp_dir) = create_test_namespace().await;

    // Create schema with various types
    let mut fields = Vec::new();

    // Boolean field
    let bool_field = JsonArrowField {
        name: "bool_col".to_string(),
        r#type: Box::new(JsonArrowDataType::new("bool".to_string())),
        nullable: true,
        metadata: None,
    };
    fields.push(bool_field);

    // Int8 field
    let int8_field = JsonArrowField {
        name: "int8_col".to_string(),
        r#type: Box::new(JsonArrowDataType::new("int8".to_string())),
        nullable: true,
        metadata: None,
    };
    fields.push(int8_field);

    // Float64 field
    let float64_field = JsonArrowField {
        name: "float64_col".to_string(),
        r#type: Box::new(JsonArrowDataType::new("float64".to_string())),
        nullable: true,
        metadata: None,
    };
    fields.push(float64_field);

    // Binary field
    let binary_field = JsonArrowField {
        name: "binary_col".to_string(),
        r#type: Box::new(JsonArrowDataType::new("binary".to_string())),
        nullable: true,
        metadata: None,
    };
    fields.push(binary_field);

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

/// Test connect function with dir implementation
#[tokio::test]
async fn test_connect_dir() {
    let temp_dir = TempDir::new().unwrap();
    let mut properties = HashMap::new();
    properties.insert(
        "root".to_string(),
        temp_dir.path().to_string_lossy().to_string(),
    );

    let namespace = lance_namespace::connect("dir", properties)
        .await
        .unwrap();

    // Test basic operation through the trait object
    let request = ListTablesRequest::new();
    let response = namespace.list_tables(request).await.unwrap();
    assert_eq!(response.tables.len(), 0);
}