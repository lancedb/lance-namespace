// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! `ContractCaller` —— SUT abstraction.
//!
//! Today there is exactly one implementation, [`InProcessDirectoryCaller`],
//! which forwards every method to a concrete
//! [`lance_namespace::LanceNamespace`] (a `DirectoryNamespace` constructed
//! over a fresh `tempfile::TempDir`).  When the next-stage HTTP reference
//! server lands (design §13), an `HttpReferenceCaller` will join here and
//! the only file that changes is this one — generated tests speak through
//! the trait and remain untouched.
//!
//! The caller surface intentionally mirrors `LanceNamespace` 1:1 for the
//! operations we actually exercise.  Operations gated behind a capability
//! flag (e.g. `supports_table_tags`) are simply omitted until the
//! corresponding pilot phase ships them; the tests for them get filtered
//! out by `Capabilities::skip_if_missing`.

use std::sync::Arc;

use async_trait::async_trait;
use bytes::Bytes;
use lance_core::Result;
use lance_namespace::LanceNamespace;
use lance_namespace::models::{
    AlterTableAddColumnsRequest, AlterTableAddColumnsResponse, AlterTableAlterColumnsRequest,
    AlterTableAlterColumnsResponse, AlterTableDropColumnsRequest, AlterTableDropColumnsResponse,
    AlterTransactionRequest, AlterTransactionResponse, AnalyzeTableQueryPlanRequest,
    BatchDeleteTableVersionsRequest, BatchDeleteTableVersionsResponse, CountTableRowsRequest,
    CreateNamespaceRequest, CreateNamespaceResponse, CreateTableIndexRequest,
    CreateTableIndexResponse, CreateTableRequest, CreateTableResponse,
    CreateTableScalarIndexResponse, CreateTableTagRequest, CreateTableTagResponse,
    CreateTableVersionRequest, CreateTableVersionResponse, DeleteFromTableRequest,
    DeleteFromTableResponse, DeleteTableTagRequest, DeleteTableTagResponse, DeregisterTableRequest,
    DeregisterTableResponse, DescribeNamespaceRequest, DescribeNamespaceResponse,
    DescribeTableIndexStatsRequest, DescribeTableIndexStatsResponse, DescribeTableRequest,
    DescribeTableResponse, DescribeTableVersionRequest, DescribeTableVersionResponse,
    DescribeTransactionRequest, DescribeTransactionResponse, DropNamespaceRequest,
    DropNamespaceResponse, DropTableIndexRequest, DropTableIndexResponse, DropTableRequest,
    DropTableResponse, ExplainTableQueryPlanRequest, GetTableStatsRequest, GetTableStatsResponse,
    GetTableTagVersionRequest, GetTableTagVersionResponse, InsertIntoTableRequest,
    InsertIntoTableResponse, ListNamespacesRequest, ListNamespacesResponse,
    ListTableIndicesRequest, ListTableIndicesResponse, ListTableTagsRequest, ListTableTagsResponse,
    ListTableVersionsRequest, ListTableVersionsResponse, ListTablesRequest, ListTablesResponse,
    MergeInsertIntoTableRequest, MergeInsertIntoTableResponse, NamespaceExistsRequest,
    QueryTableRequest, RegisterTableRequest, RegisterTableResponse, RenameTableRequest,
    RenameTableResponse, RestoreTableRequest, RestoreTableResponse, TableExistsRequest,
    UpdateTableRequest, UpdateTableResponse, UpdateTableSchemaMetadataRequest,
    UpdateTableSchemaMetadataResponse, UpdateTableTagRequest, UpdateTableTagResponse,
};
use lance_namespace_impls::DirectoryNamespaceBuilder;

/// SUT abstraction.  Each method matches the `LanceNamespace` trait
/// signature so that future HTTP / gRPC implementations stay drop-in.
#[async_trait]
pub trait ContractCaller: Send + Sync + std::fmt::Debug {
    async fn create_namespace(
        &self,
        request: CreateNamespaceRequest,
    ) -> Result<CreateNamespaceResponse>;

    async fn describe_namespace(
        &self,
        request: DescribeNamespaceRequest,
    ) -> Result<DescribeNamespaceResponse>;

    async fn drop_namespace(&self, request: DropNamespaceRequest) -> Result<DropNamespaceResponse>;

    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()>;

    async fn list_namespaces(
        &self,
        request: ListNamespacesRequest,
    ) -> Result<ListNamespacesResponse>;

    // ─── Table metadata operations ────────────────────────────
    //
    // These mirror the `LanceNamespace` trait 1:1.  Operations whose
    // happy path requires writing a Lance dataset (CreateTable / Insert /
    // …) are deferred to the write-path block below, where the harness
    // gains an Arrow IPC body helper.

    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse>;

    async fn describe_table(&self, request: DescribeTableRequest) -> Result<DescribeTableResponse>;

    async fn table_exists(&self, request: TableExistsRequest) -> Result<()>;

    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse>;

    async fn register_table(&self, request: RegisterTableRequest) -> Result<RegisterTableResponse>;

    async fn deregister_table(
        &self,
        request: DeregisterTableRequest,
    ) -> Result<DeregisterTableResponse>;

    async fn rename_table(&self, request: RenameTableRequest) -> Result<RenameTableResponse>;

    // ─── Table write-path operations ───────────────────────────────
    //
    // CreateTable / InsertIntoTable take an Arrow IPC stream (`Bytes`)
    // alongside the JSON request. The other entries below are
    // metadata-only but their happy paths require a real on-disk
    // dataset, which `Fixtures::create_table_empty` produces by
    // calling `create_table` with an empty IPC stream.

    async fn create_table(
        &self,
        request: CreateTableRequest,
        request_data: Bytes,
    ) -> Result<CreateTableResponse>;

    async fn insert_into_table(
        &self,
        request: InsertIntoTableRequest,
        request_data: Bytes,
    ) -> Result<InsertIntoTableResponse>;

    async fn count_table_rows(&self, request: CountTableRowsRequest) -> Result<i64>;

    async fn restore_table(&self, request: RestoreTableRequest) -> Result<RestoreTableResponse>;

    async fn update_table_schema_metadata(
        &self,
        request: UpdateTableSchemaMetadataRequest,
    ) -> Result<UpdateTableSchemaMetadataResponse>;

    async fn get_table_stats(&self, request: GetTableStatsRequest)
    -> Result<GetTableStatsResponse>;

    async fn alter_table_add_columns(
        &self,
        request: AlterTableAddColumnsRequest,
    ) -> Result<AlterTableAddColumnsResponse>;

    async fn alter_table_alter_columns(
        &self,
        request: AlterTableAlterColumnsRequest,
    ) -> Result<AlterTableAlterColumnsResponse>;

    async fn alter_table_drop_columns(
        &self,
        request: AlterTableDropColumnsRequest,
    ) -> Result<AlterTableDropColumnsResponse>;

    // ─── Index family ──────────────────────────────────────────────

    async fn create_table_index(
        &self,
        request: CreateTableIndexRequest,
    ) -> Result<CreateTableIndexResponse>;

    async fn create_table_scalar_index(
        &self,
        request: CreateTableIndexRequest,
    ) -> Result<CreateTableScalarIndexResponse>;

    async fn list_table_indices(
        &self,
        request: ListTableIndicesRequest,
    ) -> Result<ListTableIndicesResponse>;

    async fn describe_table_index_stats(
        &self,
        request: DescribeTableIndexStatsRequest,
    ) -> Result<DescribeTableIndexStatsResponse>;

    async fn drop_table_index(
        &self,
        request: DropTableIndexRequest,
    ) -> Result<DropTableIndexResponse>;

    // ─── Tag family ────────────────────────────────────────────────

    async fn list_table_tags(&self, request: ListTableTagsRequest)
    -> Result<ListTableTagsResponse>;

    async fn get_table_tag_version(
        &self,
        request: GetTableTagVersionRequest,
    ) -> Result<GetTableTagVersionResponse>;

    async fn create_table_tag(
        &self,
        request: CreateTableTagRequest,
    ) -> Result<CreateTableTagResponse>;

    async fn delete_table_tag(
        &self,
        request: DeleteTableTagRequest,
    ) -> Result<DeleteTableTagResponse>;

    async fn update_table_tag(
        &self,
        request: UpdateTableTagRequest,
    ) -> Result<UpdateTableTagResponse>;

    // ─── Version family ────────────────────────────────────────────

    async fn list_table_versions(
        &self,
        request: ListTableVersionsRequest,
    ) -> Result<ListTableVersionsResponse>;

    async fn describe_table_version(
        &self,
        request: DescribeTableVersionRequest,
    ) -> Result<DescribeTableVersionResponse>;

    async fn create_table_version(
        &self,
        request: CreateTableVersionRequest,
    ) -> Result<CreateTableVersionResponse>;

    async fn batch_delete_table_versions(
        &self,
        request: BatchDeleteTableVersionsRequest,
    ) -> Result<BatchDeleteTableVersionsResponse>;

    // ─── Transaction family ────────────────────────────────────────

    async fn describe_transaction(
        &self,
        request: DescribeTransactionRequest,
    ) -> Result<DescribeTransactionResponse>;

    async fn alter_transaction(
        &self,
        request: AlterTransactionRequest,
    ) -> Result<AlterTransactionResponse>;

    // ─── Data family ───────────────────────────────────────────────

    async fn merge_insert_into_table(
        &self,
        request: MergeInsertIntoTableRequest,
        request_data: Bytes,
    ) -> Result<MergeInsertIntoTableResponse>;

    async fn update_table(&self, request: UpdateTableRequest) -> Result<UpdateTableResponse>;

    async fn delete_from_table(
        &self,
        request: DeleteFromTableRequest,
    ) -> Result<DeleteFromTableResponse>;

    async fn query_table(&self, request: QueryTableRequest) -> Result<Bytes>;

    async fn explain_table_query_plan(
        &self,
        request: ExplainTableQueryPlanRequest,
    ) -> Result<String>;

    async fn analyze_table_query_plan(
        &self,
        request: AnalyzeTableQueryPlanRequest,
    ) -> Result<String>;
}

/// `ContractCaller` impl that forwards directly to a concrete
/// `LanceNamespace`.  Constructed with `fresh_directory_per_test`
/// semantics (the owning `TempDir` lives inside `_tempdir` so the
/// directory is wiped on `Drop`).
#[derive(Debug)]
pub struct InProcessDirectoryCaller {
    ns: Arc<dyn LanceNamespace>,
    /// Owns the on-disk root.  Held only for its `Drop` impl; never read.
    _tempdir: Arc<tempfile::TempDir>,
}

impl InProcessDirectoryCaller {
    /// Build a brand-new `DirectoryNamespace` rooted at a fresh
    /// `TempDir`.  `unwrap`s are intentional — these are setup-time
    /// failures that should fail the whole test binary, not the case
    /// under test.
    pub async fn fresh() -> Self {
        let tempdir = tempfile::TempDir::new().expect("create tempdir for SUT");
        let root = tempdir
            .path()
            .to_str()
            .expect("tempdir path is valid UTF-8")
            .to_owned();
        let ns = DirectoryNamespaceBuilder::new(&root)
            .build()
            .await
            .expect("build DirectoryNamespace");
        Self {
            ns: Arc::new(ns),
            _tempdir: Arc::new(tempdir),
        }
    }
}

#[async_trait]
impl ContractCaller for InProcessDirectoryCaller {
    async fn create_namespace(
        &self,
        request: CreateNamespaceRequest,
    ) -> Result<CreateNamespaceResponse> {
        self.ns.create_namespace(request).await
    }

    async fn describe_namespace(
        &self,
        request: DescribeNamespaceRequest,
    ) -> Result<DescribeNamespaceResponse> {
        self.ns.describe_namespace(request).await
    }

    async fn drop_namespace(&self, request: DropNamespaceRequest) -> Result<DropNamespaceResponse> {
        self.ns.drop_namespace(request).await
    }

    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()> {
        self.ns.namespace_exists(request).await
    }

    async fn list_namespaces(
        &self,
        request: ListNamespacesRequest,
    ) -> Result<ListNamespacesResponse> {
        self.ns.list_namespaces(request).await
    }

    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        self.ns.list_tables(request).await
    }

    async fn describe_table(&self, request: DescribeTableRequest) -> Result<DescribeTableResponse> {
        self.ns.describe_table(request).await
    }

    async fn table_exists(&self, request: TableExistsRequest) -> Result<()> {
        self.ns.table_exists(request).await
    }

    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse> {
        self.ns.drop_table(request).await
    }

    async fn register_table(&self, request: RegisterTableRequest) -> Result<RegisterTableResponse> {
        self.ns.register_table(request).await
    }

    async fn deregister_table(
        &self,
        request: DeregisterTableRequest,
    ) -> Result<DeregisterTableResponse> {
        self.ns.deregister_table(request).await
    }

    async fn rename_table(&self, request: RenameTableRequest) -> Result<RenameTableResponse> {
        self.ns.rename_table(request).await
    }

    async fn create_table(
        &self,
        request: CreateTableRequest,
        request_data: Bytes,
    ) -> Result<CreateTableResponse> {
        self.ns.create_table(request, request_data).await
    }

    async fn insert_into_table(
        &self,
        request: InsertIntoTableRequest,
        request_data: Bytes,
    ) -> Result<InsertIntoTableResponse> {
        self.ns.insert_into_table(request, request_data).await
    }

    async fn count_table_rows(&self, request: CountTableRowsRequest) -> Result<i64> {
        self.ns.count_table_rows(request).await
    }

    async fn restore_table(&self, request: RestoreTableRequest) -> Result<RestoreTableResponse> {
        self.ns.restore_table(request).await
    }

    async fn update_table_schema_metadata(
        &self,
        request: UpdateTableSchemaMetadataRequest,
    ) -> Result<UpdateTableSchemaMetadataResponse> {
        self.ns.update_table_schema_metadata(request).await
    }

    async fn get_table_stats(
        &self,
        request: GetTableStatsRequest,
    ) -> Result<GetTableStatsResponse> {
        self.ns.get_table_stats(request).await
    }

    async fn alter_table_add_columns(
        &self,
        request: AlterTableAddColumnsRequest,
    ) -> Result<AlterTableAddColumnsResponse> {
        self.ns.alter_table_add_columns(request).await
    }

    async fn alter_table_alter_columns(
        &self,
        request: AlterTableAlterColumnsRequest,
    ) -> Result<AlterTableAlterColumnsResponse> {
        self.ns.alter_table_alter_columns(request).await
    }

    async fn alter_table_drop_columns(
        &self,
        request: AlterTableDropColumnsRequest,
    ) -> Result<AlterTableDropColumnsResponse> {
        self.ns.alter_table_drop_columns(request).await
    }

    // ─── Index family ──────────────────────────────────────────────

    async fn create_table_index(
        &self,
        request: CreateTableIndexRequest,
    ) -> Result<CreateTableIndexResponse> {
        self.ns.create_table_index(request).await
    }

    async fn create_table_scalar_index(
        &self,
        request: CreateTableIndexRequest,
    ) -> Result<CreateTableScalarIndexResponse> {
        self.ns.create_table_scalar_index(request).await
    }

    async fn list_table_indices(
        &self,
        request: ListTableIndicesRequest,
    ) -> Result<ListTableIndicesResponse> {
        self.ns.list_table_indices(request).await
    }

    async fn describe_table_index_stats(
        &self,
        request: DescribeTableIndexStatsRequest,
    ) -> Result<DescribeTableIndexStatsResponse> {
        self.ns.describe_table_index_stats(request).await
    }

    async fn drop_table_index(
        &self,
        request: DropTableIndexRequest,
    ) -> Result<DropTableIndexResponse> {
        self.ns.drop_table_index(request).await
    }

    // ─── Tag family ────────────────────────────────────────────────

    async fn list_table_tags(
        &self,
        request: ListTableTagsRequest,
    ) -> Result<ListTableTagsResponse> {
        self.ns.list_table_tags(request).await
    }

    async fn get_table_tag_version(
        &self,
        request: GetTableTagVersionRequest,
    ) -> Result<GetTableTagVersionResponse> {
        self.ns.get_table_tag_version(request).await
    }

    async fn create_table_tag(
        &self,
        request: CreateTableTagRequest,
    ) -> Result<CreateTableTagResponse> {
        self.ns.create_table_tag(request).await
    }

    async fn delete_table_tag(
        &self,
        request: DeleteTableTagRequest,
    ) -> Result<DeleteTableTagResponse> {
        self.ns.delete_table_tag(request).await
    }

    async fn update_table_tag(
        &self,
        request: UpdateTableTagRequest,
    ) -> Result<UpdateTableTagResponse> {
        self.ns.update_table_tag(request).await
    }

    // ─── Version family ────────────────────────────────────────────

    async fn list_table_versions(
        &self,
        request: ListTableVersionsRequest,
    ) -> Result<ListTableVersionsResponse> {
        self.ns.list_table_versions(request).await
    }

    async fn describe_table_version(
        &self,
        request: DescribeTableVersionRequest,
    ) -> Result<DescribeTableVersionResponse> {
        self.ns.describe_table_version(request).await
    }

    async fn create_table_version(
        &self,
        request: CreateTableVersionRequest,
    ) -> Result<CreateTableVersionResponse> {
        self.ns.create_table_version(request).await
    }

    async fn batch_delete_table_versions(
        &self,
        request: BatchDeleteTableVersionsRequest,
    ) -> Result<BatchDeleteTableVersionsResponse> {
        self.ns.batch_delete_table_versions(request).await
    }

    // ─── Transaction family ────────────────────────────────────────

    async fn describe_transaction(
        &self,
        request: DescribeTransactionRequest,
    ) -> Result<DescribeTransactionResponse> {
        self.ns.describe_transaction(request).await
    }

    async fn alter_transaction(
        &self,
        request: AlterTransactionRequest,
    ) -> Result<AlterTransactionResponse> {
        self.ns.alter_transaction(request).await
    }

    // ─── Data family ───────────────────────────────────────────────

    async fn merge_insert_into_table(
        &self,
        request: MergeInsertIntoTableRequest,
        request_data: Bytes,
    ) -> Result<MergeInsertIntoTableResponse> {
        self.ns.merge_insert_into_table(request, request_data).await
    }

    async fn update_table(&self, request: UpdateTableRequest) -> Result<UpdateTableResponse> {
        self.ns.update_table(request).await
    }

    async fn delete_from_table(
        &self,
        request: DeleteFromTableRequest,
    ) -> Result<DeleteFromTableResponse> {
        self.ns.delete_from_table(request).await
    }

    async fn query_table(&self, request: QueryTableRequest) -> Result<Bytes> {
        self.ns.query_table(request).await
    }

    async fn explain_table_query_plan(
        &self,
        request: ExplainTableQueryPlanRequest,
    ) -> Result<String> {
        self.ns.explain_table_query_plan(request).await
    }

    async fn analyze_table_query_plan(
        &self,
        request: AnalyzeTableQueryPlanRequest,
    ) -> Result<String> {
        self.ns.analyze_table_query_plan(request).await
    }
}

/// Factory used by every generated test.  Resolves which `ContractCaller`
/// to instantiate from environment variables; today the only knob is
/// `LANCE_CTS_TARGET_URL` (when set we bail out — the HTTP caller is
/// next-stage work and we'd rather panic loudly than silently fall back).
pub struct ContractCallerFactory;

impl ContractCallerFactory {
    pub async fn build() -> Arc<dyn ContractCaller> {
        if std::env::var("LANCE_CTS_TARGET_URL").is_ok() {
            panic!(
                "LANCE_CTS_TARGET_URL is set but HttpReferenceCaller is not \
                 implemented yet (see design §13). Unset the env var to use \
                 the in-process DirectoryNamespace caller."
            );
        }
        Arc::new(InProcessDirectoryCaller::fresh().await)
    }
}
