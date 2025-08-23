//! Lance Namespace Rust Client
//!
//! A Rust client for the Lance Namespace API that provides a unified interface
//! for managing namespaces and tables across different backend implementations.

pub mod connect;
pub mod dir;
pub mod namespace;
pub mod schema;

// Re-export the trait and connect function at the crate root
pub use connect::{connect, ConnectError};
pub use dir::DirectoryNamespace;
pub use namespace::{LanceNamespace, NamespaceError, Result};
