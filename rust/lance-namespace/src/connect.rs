//! Connect functionality for Lance Namespace implementations.

use std::collections::HashMap;
use std::sync::Arc;
use thiserror::Error;
use anyhow::Result;

use crate::namespace::LanceNamespace;

/// Error type for connection-related operations
#[derive(Debug, Error)]
pub enum ConnectError {
    #[error("Unknown implementation: {0}")]
    UnknownImpl(String),
    
    #[error("Failed to load implementation: {0}")]
    LoadError(String),
    
    #[error("Invalid implementation: {0}")]
    InvalidImpl(String),
    
    #[error("Missing required property: {0}")]
    MissingProperty(String),
    
    #[error("Other error: {0}")]
    Other(String),
}

/// Native implementation aliases
fn get_native_impls() -> HashMap<&'static str, &'static str> {
    let impls = HashMap::new();
    // Add native implementations here as they are created
    // For example:
    // impls.insert("rest", "lance_namespace::impls::rest::RestNamespace");
    // impls.insert("dir", "lance_namespace::impls::dir::DirectoryNamespace");
    impls
}

/// Connect to a Lance namespace implementation.
///
/// This function creates a connection to a Lance namespace backend based on
/// the specified implementation type and configuration properties.
///
/// # Arguments
///
/// * `impl_name` - Implementation alias or full type path. Built-in aliases include:
///   - "rest": REST API implementation (when available)
///   - "dir": Directory-based implementation (when available)
///   - Or a full type path like "my_crate::MyNamespaceImpl"
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
/// // Connect using a built-in alias (when available)
/// let mut props = HashMap::new();
/// props.insert("url".to_string(), "http://localhost:8080".to_string());
/// let namespace = connect("rest", props).await?;
///
/// // Connect using a full type path
/// let mut props = HashMap::new();
/// props.insert("path".to_string(), "/data/lance".to_string());
/// let namespace = connect("my_crate::CustomNamespace", props).await?;
/// # Ok(())
/// # }
/// ```
pub async fn connect(
    impl_name: &str,
    _properties: HashMap<String, String>,
) -> Result<Arc<dyn LanceNamespace>, ConnectError> {
    // Check if it's a native implementation alias
    let native_impls = get_native_impls();
    let impl_path = native_impls.get(impl_name).unwrap_or(&impl_name);
    
    // For now, we return an error since we don't have any implementations yet
    // In the future, this would:
    // 1. Dynamically load the implementation type
    // 2. Instantiate it with the provided properties
    // 3. Return it as a trait object
    
    Err(ConnectError::UnknownImpl(format!(
        "Implementation '{}' is not yet available. This will be implemented when concrete namespace implementations are added.",
        impl_path
    )))
}

/// Builder for creating namespace connections with a fluent API.
///
/// # Examples
///
/// ```no_run
/// use lance_namespace::connect::ConnectionBuilder;
///
/// # async fn example() -> Result<(), Box<dyn std::error::Error>> {
/// let namespace = ConnectionBuilder::new("rest")
///     .property("url", "http://localhost:8080")
///     .property("auth_token", "secret-token")
///     .connect()
///     .await?;
/// # Ok(())
/// # }
/// ```
pub struct ConnectionBuilder {
    impl_name: String,
    properties: HashMap<String, String>,
}

impl ConnectionBuilder {
    /// Create a new connection builder for the specified implementation.
    pub fn new(impl_name: impl Into<String>) -> Self {
        Self {
            impl_name: impl_name.into(),
            properties: HashMap::new(),
        }
    }
    
    /// Add a configuration property.
    pub fn property(mut self, key: impl Into<String>, value: impl Into<String>) -> Self {
        self.properties.insert(key.into(), value.into());
        self
    }
    
    /// Add multiple configuration properties.
    pub fn properties(mut self, props: HashMap<String, String>) -> Self {
        self.properties.extend(props);
        self
    }
    
    /// Connect to the namespace implementation.
    pub async fn connect(self) -> Result<Arc<dyn LanceNamespace>, ConnectError> {
        connect(&self.impl_name, self.properties).await
    }
}