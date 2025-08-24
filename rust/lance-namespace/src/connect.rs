//! Connect functionality for Lance Namespace implementations.

use std::collections::HashMap;
use std::sync::Arc;
use thiserror::Error;

use crate::dir::DirectoryNamespace;
use crate::namespace::LanceNamespace;
use crate::rest::RestNamespace;

/// Error type for connection-related operations
#[derive(Debug, Error)]
pub enum ConnectError {
    #[error("Unknown implementation: {0}")]
    UnknownImpl(String),

    #[error("Failed to construct implementation: {0}")]
    ConstructionError(String),

    #[error("Missing required property: {0}")]
    MissingProperty(String),

    #[error("Other error: {0}")]
    Other(String),
}

/// Connect to a Lance namespace implementation.
///
/// This function creates a connection to a Lance namespace backend based on
/// the specified implementation type and configuration properties.
///
/// # Arguments
///
/// * `impl_name` - Implementation identifier. Currently supported:
///   - "rest": REST API implementation (when available)
///   - "dir": Directory-based implementation (when available)
///
/// * `properties` - Configuration properties specific to the implementation.
///   Common properties might include:
///   - "url": Base URL for REST implementations
///   - "path": Directory path for file-based implementations
///   - "auth_token": Authentication token
///
/// # Returns
///
/// Returns a boxed trait object implementing the `LanceNamespace` trait.
///
/// # Examples
///
/// ```no_run
/// use lance_namespace::connect;
/// use std::collections::HashMap;
///
/// # async fn example() -> Result<(), Box<dyn std::error::Error>> {
/// let mut props = HashMap::new();
/// props.insert("url".to_string(), "http://localhost:8080".to_string());
/// let namespace = connect("rest", props).await?;
/// # Ok(())
/// # }
/// ```
pub async fn connect(
    impl_name: &str,
    properties: HashMap<String, String>,
) -> Result<Arc<dyn LanceNamespace>, ConnectError> {
    // Native implementations will be added here as they are created
    match impl_name {
        "rest" => {
            // Create REST implementation
            Ok(Arc::new(RestNamespace::new(properties)))
        }
        "dir" => {
            // Create directory implementation
            DirectoryNamespace::new(properties)
                .map(|ns| Arc::new(ns) as Arc<dyn LanceNamespace>)
                .map_err(|e| ConnectError::ConstructionError(e.to_string()))
        }
        _ => Err(ConnectError::UnknownImpl(format!(
            "Implementation '{}' is not yet available",
            impl_name
        ))),
    }
}
