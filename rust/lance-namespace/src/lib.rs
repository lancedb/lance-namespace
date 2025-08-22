pub mod namespace;
pub mod dir;
pub mod rest;
pub mod error;

pub use namespace::LanceNamespace;
pub use dir::DirNamespace;
pub use rest::RestNamespace;
pub use error::{LanceNamespaceError, Result};

use std::collections::HashMap;

pub fn connect(impl_name: &str, properties: HashMap<String, String>) -> Result<Box<dyn LanceNamespace>> {
    match impl_name {
        "dir" => {
            let dir_namespace = DirNamespace::new(properties)?;
            Ok(Box::new(dir_namespace))
        }
        "rest" => {
            let rest_namespace = RestNamespace::new(properties)?;
            Ok(Box::new(rest_namespace))
        }
        _ => Err(LanceNamespaceError::UnknownImplementation(impl_name.to_string())),
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use tempfile::TempDir;
    use lance_namespace_reqwest_client::models::ListTablesRequest;

    #[tokio::test]
    async fn test_connect_dir_namespace() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path);
        
        let namespace = connect("dir", properties).unwrap();
        
        // Test that we can call a method on the trait object
        let request = ListTablesRequest {
            id: None,
            page_token: None,
            limit: None,
        };
        
        let result = namespace.list_tables(request).await;
        assert!(result.is_ok());
    }

    #[test]
    fn test_connect_rest_namespace() {
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        
        let namespace = connect("rest", properties);
        assert!(namespace.is_ok());
        
        // Just verify we can create the namespace
        // Actual HTTP operations would require a test server
    }

    #[test]
    fn test_connect_unsupported_implementation() {
        let properties = HashMap::new();
        
        let result = connect("unsupported", properties);
        assert!(result.is_err());
        
        if let Err(LanceNamespaceError::UnknownImplementation(impl_name)) = result {
            assert_eq!(impl_name, "unsupported");
        } else {
            panic!("Expected UnknownImplementation error");
        }
    }

    #[test]
    fn test_connect_dir_missing_properties() {
        // Even without root property, dir namespace should work (uses current dir)
        let properties = HashMap::new();
        
        let result = connect("dir", properties);
        assert!(result.is_ok());
    }

    #[test]
    fn test_connect_rest_missing_uri() {
        // REST namespace requires URI
        let properties = HashMap::new();
        
        let result = connect("rest", properties);
        assert!(result.is_err());
        // Just check that it's an error - don't unwrap_err without Debug trait
        if let Err(e) = result {
            assert!(e.to_string().contains("uri property is required"));
        }
    }

    #[test]
    fn test_connect_case_sensitive() {
        // Implementation names are case-sensitive
        let properties1 = HashMap::new();
        let result = connect("DIR", properties1);
        assert!(result.is_err());
        assert!(matches!(result, Err(LanceNamespaceError::UnknownImplementation(_))));
        
        let properties2 = HashMap::new();
        let result = connect("REST", properties2);
        assert!(result.is_err());
        assert!(matches!(result, Err(LanceNamespaceError::UnknownImplementation(_))));
    }

    // Comprehensive tests for DirNamespace operations
    
    #[tokio::test]
    async fn test_dir_namespace_create_namespace() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), temp_dir.path().to_string_lossy().to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::CreateNamespaceRequest {
            id: None, // Root namespace
            mode: None,
            properties: Some(HashMap::new()),
        };
        
        // Root namespace already exists, should fail with AlreadyExists error
        let result = namespace.create_namespace(request).await;
        assert!(result.is_err());
        assert!(matches!(result.unwrap_err(), LanceNamespaceError::AlreadyExists(_)));
    }

    #[tokio::test]
    async fn test_dir_namespace_create_non_root_namespace() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), temp_dir.path().to_string_lossy().to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::CreateNamespaceRequest {
            id: Some(vec!["test".to_string()]), // Non-root namespace
            mode: None,
            properties: Some(HashMap::new()),
        };
        
        // Non-root namespace creation not supported
        let result = namespace.create_namespace(request).await;
        assert!(result.is_err());
        assert!(matches!(result.unwrap_err(), LanceNamespaceError::NotSupported(_)));
    }

    #[tokio::test]
    async fn test_dir_namespace_create_root_namespace_empty_list() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), temp_dir.path().to_string_lossy().to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::CreateNamespaceRequest {
            id: Some(vec![]), // Root namespace as empty list
            mode: None,
            properties: Some(HashMap::new()),
        };
        
        // Root namespace already exists, should fail with AlreadyExists error
        let result = namespace.create_namespace(request).await;
        assert!(result.is_err());
        assert!(matches!(result.unwrap_err(), LanceNamespaceError::AlreadyExists(_)));
    }

    #[tokio::test]
    async fn test_dir_namespace_list_namespaces() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), temp_dir.path().to_string_lossy().to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::ListNamespacesRequest {
            id: None, // Root namespace
            page_token: None,
            limit: None,
        };
        
        let result = namespace.list_namespaces(request).await;
        assert!(result.is_ok());
        let response = result.unwrap();
        assert!(response.namespaces.is_empty()); // Directory namespace is flat
    }

    #[tokio::test]
    async fn test_dir_namespace_describe_namespace() {
        let temp_dir = TempDir::new().unwrap();
        let root_path = temp_dir.path().to_string_lossy().to_string();
        
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root_path.clone());
        properties.insert("storage.test_option".to_string(), "test_value".to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::DescribeNamespaceRequest {
            id: Some(vec![]), // Root namespace
        };
        
        let result = namespace.describe_namespace(request).await;
        assert!(result.is_ok());
        
        let response = result.unwrap();
        let props = response.properties.unwrap();
        
        // Should contain root path and storage options
        assert_eq!(props.get("root"), Some(&root_path));
        assert_eq!(props.get("test_option"), Some(&"test_value".to_string())); // storage. prefix is stripped
    }

    #[tokio::test]
    async fn test_dir_namespace_namespace_exists() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), temp_dir.path().to_string_lossy().to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::NamespaceExistsRequest {
            id: Some(vec![]), // Root namespace
        };
        
        let result = namespace.namespace_exists(request).await;
        assert!(result.is_ok()); // Root namespace always exists
    }

    #[tokio::test]
    async fn test_dir_namespace_drop_namespace() {
        let temp_dir = TempDir::new().unwrap();
        let mut properties = HashMap::new();
        properties.insert("root".to_string(), temp_dir.path().to_string_lossy().to_string());
        
        let namespace = connect("dir", properties).unwrap();
        
        let request = lance_namespace_reqwest_client::models::DropNamespaceRequest {
            id: Some(vec![]), // Root namespace
            mode: None,
            behavior: None,
        };
        
        let result = namespace.drop_namespace(request).await;
        assert!(result.is_err()); // Cannot drop root namespace
    }

    // Rest Namespace tests (basic connectivity tests)
    
    #[tokio::test]
    async fn test_rest_namespace_operations() {
        // Test basic REST namespace operations (without actual server)
        let mut properties = HashMap::new();
        properties.insert("uri".to_string(), "http://localhost:8080".to_string());
        
        let namespace = connect("rest", properties).unwrap();
        
        // These will fail because there's no server, but they test the request construction
        let list_namespaces_request = lance_namespace_reqwest_client::models::ListNamespacesRequest {
            id: None,
            page_token: None,
            limit: None,
        };
        
        let result = namespace.list_namespaces(list_namespaces_request).await;
        assert!(result.is_err()); // Expected to fail without server
        
        let list_tables_request = ListTablesRequest {
            id: None,
            page_token: None,
            limit: None,
        };
        
        let result = namespace.list_tables(list_tables_request).await;
        assert!(result.is_err()); // Expected to fail without server
    }
}