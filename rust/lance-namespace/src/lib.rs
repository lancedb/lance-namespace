//! Lance Namespace Rust Client
//!
//! A Rust client for the Lance Namespace API that provides a unified interface
//! for managing namespaces and tables across different backend implementations.

pub mod connect;
pub mod dir;
pub mod namespace;
pub mod rest;
pub mod schema;

// Re-export the trait and connect function at the crate root
pub use connect::{connect, ConnectError};
pub use dir::DirectoryNamespace;
pub use namespace::{LanceNamespace, NamespaceError, Result};

// Re-export reqwest client for convenience
pub use lance_namespace_reqwest_client as reqwest_client;

// Re-export commonly used models from the reqwest client
pub mod models {
    pub use lance_namespace_reqwest_client::models::*;
}

// Re-export APIs from the reqwest client
pub mod apis {
    pub use lance_namespace_reqwest_client::apis::*;
}
