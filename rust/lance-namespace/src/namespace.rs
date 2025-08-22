use async_trait::async_trait;
use lance_namespace_reqwest_client::models::*;
use crate::error::Result;

#[async_trait]
pub trait LanceNamespace: Send + Sync {
    async fn list_namespaces(&self, request: ListNamespacesRequest) -> Result<ListNamespacesResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("list_namespaces".to_string()))
    }
    
    async fn describe_namespace(&self, request: DescribeNamespaceRequest) -> Result<DescribeNamespaceResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("describe_namespace".to_string()))
    }
    
    async fn create_namespace(&self, request: CreateNamespaceRequest) -> Result<CreateNamespaceResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("create_namespace".to_string()))
    }
    
    async fn drop_namespace(&self, request: DropNamespaceRequest) -> Result<DropNamespaceResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("drop_namespace".to_string()))
    }
    
    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()> {
        Err(crate::error::LanceNamespaceError::NotSupported("namespace_exists".to_string()))
    }
    
    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("list_tables".to_string()))
    }
    
    async fn describe_table(&self, request: DescribeTableRequest) -> Result<DescribeTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("describe_table".to_string()))
    }
    
    async fn register_table(&self, request: RegisterTableRequest) -> Result<RegisterTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("register_table".to_string()))
    }
    
    async fn table_exists(&self, request: TableExistsRequest) -> Result<()> {
        Err(crate::error::LanceNamespaceError::NotSupported("table_exists".to_string()))
    }
    
    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("drop_table".to_string()))
    }
    
    async fn deregister_table(&self, request: DeregisterTableRequest) -> Result<DeregisterTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("deregister_table".to_string()))
    }
    
    async fn count_table_rows(&self, request: CountTableRowsRequest) -> Result<i32> {
        Err(crate::error::LanceNamespaceError::NotSupported("count_table_rows".to_string()))
    }
    
    async fn create_table(&self, request: CreateTableRequest, request_data: Vec<u8>) -> Result<CreateTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("create_table".to_string()))
    }
    
    async fn insert_into_table(&self, request: InsertIntoTableRequest, request_data: Vec<u8>) -> Result<InsertIntoTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("insert_into_table".to_string()))
    }
    
    async fn merge_insert_into_table(&self, request: MergeInsertIntoTableRequest, request_data: Vec<u8>) -> Result<MergeInsertIntoTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("merge_insert_into_table".to_string()))
    }
    
    async fn update_table(&self, request: UpdateTableRequest) -> Result<UpdateTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("update_table".to_string()))
    }
    
    async fn delete_from_table(&self, request: DeleteFromTableRequest) -> Result<DeleteFromTableResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("delete_from_table".to_string()))
    }
    
    async fn query_table(&self, request: QueryTableRequest) -> Result<Vec<u8>> {
        Err(crate::error::LanceNamespaceError::NotSupported("query_table".to_string()))
    }
    
    async fn create_table_index(&self, request: CreateTableIndexRequest) -> Result<CreateTableIndexResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("create_table_index".to_string()))
    }
    
    async fn list_table_indices(&self, request: ListTableIndicesRequest) -> Result<ListTableIndicesResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("list_table_indices".to_string()))
    }
    
    async fn describe_table_index_stats(&self, request: DescribeTableIndexStatsRequest) -> Result<DescribeTableIndexStatsResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("describe_table_index_stats".to_string()))
    }
    
    async fn describe_transaction(&self, request: DescribeTransactionRequest) -> Result<DescribeTransactionResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("describe_transaction".to_string()))
    }
    
    async fn alter_transaction(&self, request: AlterTransactionRequest) -> Result<AlterTransactionResponse> {
        Err(crate::error::LanceNamespaceError::NotSupported("alter_transaction".to_string()))
    }
}