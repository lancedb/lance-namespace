//! Lance Namespace Rust Client
//!
//! A Rust client for the Lance Namespace API that provides a unified interface
//! for managing namespaces and tables across different backend implementations.

pub mod namespace;
pub mod connect;

// Re-export the trait and connect function at the crate root
pub use namespace::LanceNamespace;
pub use connect::{connect, ConnectError};

// Re-export all models from the reqwest client
pub use lance_namespace_reqwest_client::models::{
    ListNamespacesRequest,
    ListNamespacesResponse,
    DescribeNamespaceRequest,
    DescribeNamespaceResponse,
    CreateNamespaceRequest,
    CreateNamespaceResponse,
    DropNamespaceRequest,
    DropNamespaceResponse,
    NamespaceExistsRequest,
    ListTablesRequest,
    ListTablesResponse,
    DescribeTableRequest,
    DescribeTableResponse,
    RegisterTableRequest,
    RegisterTableResponse,
    TableExistsRequest,
    DropTableRequest,
    DropTableResponse,
    DeregisterTableRequest,
    DeregisterTableResponse,
    CountTableRowsRequest,
    CreateTableRequest,
    CreateTableResponse,
    InsertIntoTableRequest,
    InsertIntoTableResponse,
    MergeInsertIntoTableRequest,
    MergeInsertIntoTableResponse,
    UpdateTableRequest,
    UpdateTableResponse,
    DeleteFromTableRequest,
    DeleteFromTableResponse,
    QueryTableRequest,
    CreateTableIndexRequest,
    CreateTableIndexResponse,
    ListTableIndicesRequest,
    ListTableIndicesResponse,
    DescribeTableIndexStatsRequest,
    DescribeTableIndexStatsResponse,
    DescribeTransactionRequest,
    DescribeTransactionResponse,
    AlterTransactionRequest,
    AlterTransactionResponse,
};