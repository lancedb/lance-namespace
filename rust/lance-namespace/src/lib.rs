pub mod namespace;
pub mod dir;
pub mod rest;
pub mod error;

pub use namespace::LanceNamespace;
pub use dir::DirNamespace;
pub use rest::RestNamespace;
pub use error::{NamespaceError, Result};

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
        _ => Err(NamespaceError::UnsupportedImplementation(impl_name.to_string())),
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
        
        if let Err(NamespaceError::UnsupportedImplementation(impl_name)) = result {
            assert_eq!(impl_name, "unsupported");
        } else {
            panic!("Expected UnsupportedImplementation error");
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
        assert!(matches!(result, Err(NamespaceError::UnsupportedImplementation(_))));
        
        let properties2 = HashMap::new();
        let result = connect("REST", properties2);
        assert!(result.is_err());
        assert!(matches!(result, Err(NamespaceError::UnsupportedImplementation(_))));
    }
}