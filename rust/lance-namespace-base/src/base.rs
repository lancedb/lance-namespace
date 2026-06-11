// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! Base Lance Namespace implementation using the Lance SDK directly.

use arrow::record_batch::RecordBatchIterator;
use arrow_ipc::reader::StreamReader;
use async_trait::async_trait;
use bytes::Bytes;
use futures::TryStreamExt;
use lance::dataset::builder::DatasetBuilder;
use lance::dataset::transaction::{Operation, Transaction};
use lance::dataset::{Dataset, WriteMode, WriteParams};
use lance::index::{DatasetIndexExt, IndexParams, vector::VectorIndexParams};
use lance::session::Session;
use lance_index::scalar::{BuiltinIndexType, InvertedIndexParams, ScalarIndexParams};
use lance_index::vector::{
    bq::RQBuildParams, hnsw::builder::HnswBuildParams, ivf::IvfBuildParams, pq::PQBuildParams,
    sq::builder::SQBuildParams,
};
use lance_index::{IndexType, is_system_index};
use lance_io::object_store::{ObjectStore, ObjectStoreParams, ObjectStoreRegistry};
use lance_linalg::distance::MetricType;
use lance_table::io::commit::ManifestNamingScheme;
use object_store::path::Path;
use object_store::{
    Error as ObjectStoreError, ObjectStore as OSObjectStore, ObjectStoreExt, PutMode, PutOptions,
};
use std::collections::HashMap;
use std::io::Cursor;
use std::sync::Arc;

use lance_namespace::models::{
    AlterTableAddColumnsRequest, AlterTableAddColumnsResponse, AlterTableAlterColumnsRequest,
    AlterTableAlterColumnsResponse, AlterTableDropColumnsRequest, AlterTableDropColumnsResponse,
    AlterTransactionRequest, AlterTransactionResponse, AnalyzeTableQueryPlanRequest,
    BatchDeleteTableVersionsRequest, BatchDeleteTableVersionsResponse, CountTableRowsRequest,
    CreateNamespaceRequest, CreateNamespaceResponse, CreateTableIndexRequest,
    CreateTableIndexResponse, CreateTableRequest, CreateTableResponse,
    CreateTableScalarIndexResponse, CreateTableTagRequest, CreateTableTagResponse,
    CreateTableVersionRequest, CreateTableVersionResponse, DeclareTableRequest,
    DeclareTableResponse, DeleteFromTableRequest, DeleteFromTableResponse, DeleteTableTagRequest,
    DeleteTableTagResponse, DeregisterTableRequest, DeregisterTableResponse,
    DescribeNamespaceRequest, DescribeNamespaceResponse, DescribeTableIndexStatsRequest,
    DescribeTableIndexStatsResponse, DescribeTableRequest, DescribeTableResponse,
    DescribeTableVersionRequest, DescribeTableVersionResponse, DescribeTransactionRequest,
    DescribeTransactionResponse, DropNamespaceRequest, DropNamespaceResponse,
    DropTableIndexRequest, DropTableIndexResponse, DropTableRequest, DropTableResponse,
    ExplainTableQueryPlanRequest, GetTableStatsRequest, GetTableStatsResponse,
    GetTableTagVersionRequest, GetTableTagVersionResponse, IndexContent, InsertIntoTableRequest,
    InsertIntoTableResponse, ListNamespacesRequest, ListNamespacesResponse,
    ListTableIndicesRequest, ListTableIndicesResponse, ListTableTagsRequest, ListTableTagsResponse,
    ListTableVersionsRequest, ListTableVersionsResponse, ListTablesRequest, ListTablesResponse,
    MergeInsertIntoTableRequest, MergeInsertIntoTableResponse, NamespaceExistsRequest,
    QueryTableRequest, RegisterTableRequest, RegisterTableResponse, RenameTableRequest,
    RenameTableResponse, RestoreTableRequest, RestoreTableResponse, TableExistsRequest,
    TableVersion, TagContents, UpdateTableRequest, UpdateTableResponse,
    UpdateTableSchemaMetadataRequest, UpdateTableSchemaMetadataResponse, UpdateTableTagRequest,
    UpdateTableTagResponse,
};

use lance_core::Result;
use lance_namespace::LanceNamespace;
use lance_namespace::error::NamespaceError;
use lance_namespace::schema::arrow_schema_to_json;

// ── Index param helper types ────────────────────────────────────────────────

enum BaseIndexParams {
    Scalar {
        index_type: IndexType,
        params: ScalarIndexParams,
    },
    Inverted(InvertedIndexParams),
    Vector {
        index_type: IndexType,
        params: VectorIndexParams,
    },
}

impl BaseIndexParams {
    fn index_type(&self) -> IndexType {
        match self {
            Self::Scalar { index_type, .. } | Self::Vector { index_type, .. } => *index_type,
            Self::Inverted(_) => IndexType::Inverted,
        }
    }

    fn params(&self) -> &dyn IndexParams {
        match self {
            Self::Scalar { params, .. } => params,
            Self::Inverted(params) => params,
            Self::Vector { params, .. } => params,
        }
    }
}

// ── Builder ─────────────────────────────────────────────────────────────────

/// Builder for creating a [`BaseLanceNamespace`].
#[derive(Clone)]
pub struct BaseLanceNamespaceBuilder {
    root: String,
    storage_options: Option<HashMap<String, String>>,
    session: Option<Arc<Session>>,
}

impl std::fmt::Debug for BaseLanceNamespaceBuilder {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        f.debug_struct("BaseLanceNamespaceBuilder")
            .field("root", &self.root)
            .field("storage_options", &self.storage_options)
            .finish()
    }
}

impl BaseLanceNamespaceBuilder {
    /// Create a new builder with the specified root path.
    pub fn new(root: impl Into<String>) -> Self {
        Self {
            root: root.into().trim_end_matches('/').to_string(),
            storage_options: None,
            session: None,
        }
    }

    /// Add a storage option.
    pub fn storage_option(mut self, key: impl Into<String>, value: impl Into<String>) -> Self {
        self.storage_options
            .get_or_insert_with(HashMap::new)
            .insert(key.into(), value.into());
        self
    }

    /// Add multiple storage options.
    pub fn storage_options(mut self, options: HashMap<String, String>) -> Self {
        self.storage_options
            .get_or_insert_with(HashMap::new)
            .extend(options);
        self
    }

    /// Set the Lance session to use.
    pub fn session(mut self, session: Arc<Session>) -> Self {
        self.session = Some(session);
        self
    }

    /// Create a builder from a properties map.
    ///
    /// Expects `root` (required) and optional `storage.*` prefixed options.
    pub fn from_properties(
        properties: HashMap<String, String>,
        session: Option<Arc<Session>>,
    ) -> Result<Self> {
        let root = properties.get("root").cloned().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Missing required property 'root' for base namespace".to_string(),
            })
        })?;

        let storage_options: HashMap<String, String> = properties
            .iter()
            .filter_map(|(k, v)| {
                k.strip_prefix("storage.")
                    .map(|key| (key.to_string(), v.clone()))
            })
            .collect();

        let storage_options = if storage_options.is_empty() {
            None
        } else {
            Some(storage_options)
        };

        Ok(Self {
            root: root.trim_end_matches('/').to_string(),
            storage_options,
            session,
        })
    }

    /// Build the [`BaseLanceNamespace`].
    pub async fn build(self) -> Result<BaseLanceNamespace> {
        let (object_store, base_path) =
            Self::initialize_object_store(&self.root, &self.storage_options, &self.session).await?;

        Ok(BaseLanceNamespace {
            root: self.root,
            storage_options: self.storage_options,
            session: self.session,
            object_store,
            base_path,
        })
    }

    async fn initialize_object_store(
        root: &str,
        storage_options: &Option<HashMap<String, String>>,
        session: &Option<Arc<Session>>,
    ) -> Result<(Arc<ObjectStore>, Path)> {
        let accessor = storage_options.clone().map(|opts| {
            Arc::new(lance_io::object_store::StorageOptionsAccessor::with_static_options(opts))
        });
        let params = ObjectStoreParams {
            storage_options_accessor: accessor,
            ..Default::default()
        };

        let registry = if let Some(session) = session {
            session.store_registry()
        } else {
            Arc::new(ObjectStoreRegistry::default())
        };

        let (object_store, base_path) = ObjectStore::from_uri_and_params(registry, root, &params)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create object store: {}", e),
                })
            })?;

        Ok((object_store, base_path))
    }
}

// ── Main struct ─────────────────────────────────────────────────────────────

/// Base implementation of [`LanceNamespace`] using the Lance SDK directly.
///
/// Stores namespaces as directories and tables as `{name}.lance` subdirectories.
/// Supports hierarchical namespaces (e.g. `["org","team"]` → `{root}/org/team/`).
pub struct BaseLanceNamespace {
    root: String,
    storage_options: Option<HashMap<String, String>>,
    session: Option<Arc<Session>>,
    object_store: Arc<ObjectStore>,
    base_path: Path,
}

impl std::fmt::Debug for BaseLanceNamespace {
    fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
        write!(f, "{}", self.namespace_id())
    }
}

// ── Private helpers ─────────────────────────────────────────────────────────

/// Describes the version ranges to delete for a single table.
struct TableDeleteEntry {
    table_id: Option<Vec<String>>,
    ranges: Vec<(i64, i64)>,
}

impl BaseLanceNamespace {
    // ── Path resolution (hierarchical namespace support) ─────────────────

    /// Resolve a namespace ID to an object_store `Path`.
    /// `["a","b"]` → `{base_path}/a/b`
    fn resolve_namespace_path(&self, id: &Option<Vec<String>>) -> Path {
        let mut path = self.base_path.clone();
        if let Some(parts) = id {
            for part in parts {
                path = path.join(part.as_str());
            }
        }
        path
    }

    /// Resolve a namespace ID to a URI string.
    /// `["a","b"]` → `{root}/a/b`
    fn resolve_namespace_uri(&self, id: &Option<Vec<String>>) -> String {
        let mut uri = self.root.clone();
        if let Some(parts) = id {
            for part in parts {
                uri = format!("{}/{}", uri, part);
            }
        }
        uri
    }

    /// Resolve a table ID to a full URI string.
    /// `["ns","table"]` → `{root}/ns/table.lance`
    fn resolve_table_uri(&self, id: &Option<Vec<String>>) -> Result<String> {
        let id = id.as_ref().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Table ID is required".to_string(),
            })
        })?;
        if id.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Table ID cannot be empty".to_string(),
            }
            .into());
        }
        let mut uri = self.root.clone();
        for part in id {
            uri = format!("{}/{}", uri, part);
        }
        Ok(format!("{}.lance", uri))
    }

    /// Resolve a table ID to an object_store `Path`.
    fn resolve_table_path(&self, id: &Option<Vec<String>>) -> Result<Path> {
        let id = id.as_ref().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Table ID is required".to_string(),
            })
        })?;
        if id.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Table ID cannot be empty".to_string(),
            }
            .into());
        }
        let mut path = self.base_path.clone();
        for (i, part) in id.iter().enumerate() {
            if i == id.len() - 1 {
                path = path.join(format!("{}.lance", part).as_str());
            } else {
                path = path.join(part.as_str());
            }
        }
        Ok(path)
    }

    /// Extract the table name (last element) from a table ID.
    fn table_name_from_id(id: &Option<Vec<String>>) -> Result<String> {
        let id = id.as_ref().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Table ID is required".to_string(),
            })
        })?;
        if id.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Table ID cannot be empty".to_string(),
            }
            .into());
        }
        Ok(id.last().unwrap().clone())
    }

    /// Extract the namespace path (all but last element) from a table ID.
    fn namespace_path_from_table_id(id: &Option<Vec<String>>) -> Option<Vec<String>> {
        id.as_ref().map(|parts| {
            if parts.len() > 1 {
                parts[..parts.len() - 1].to_vec()
            } else {
                vec![]
            }
        })
    }

    /// Ensure a namespace directory exists.
    async fn ensure_namespace_exists(&self, path: &Path, id: &Option<Vec<String>>) -> Result<()> {
        // Root namespace always exists
        if id.as_ref().is_none_or(|parts| parts.is_empty()) {
            return Ok(());
        }
        // Check by looking for the .lance-namespace marker file
        let marker_path = path.clone().join(".lance-namespace");
        match self.object_store.inner.head(&marker_path).await {
            Ok(_) => Ok(()),
            Err(_) => {
                let name = id
                    .as_ref()
                    .map(|parts| parts.join("/"))
                    .unwrap_or_else(|| "<root>".to_string());
                Err(NamespaceError::NamespaceNotFound { message: name }.into())
            }
        }
    }

    /// Ensure a table's `.lance` directory exists.
    async fn ensure_table_exists(&self, id: &Option<Vec<String>>) -> Result<()> {
        let table_path = self.resolve_table_path(id)?;
        match self.object_store.read_dir(table_path).await {
            Ok(entries) if !entries.is_empty() => Ok(()),
            _ => {
                let name = Self::table_name_from_id(id)?;
                Err(NamespaceError::TableNotFound { message: name }.into())
            }
        }
    }

    /// Ensure the parent namespace of a table exists.
    async fn ensure_parent_namespace_exists(&self, id: &Option<Vec<String>>) -> Result<()> {
        let ns_id = Self::namespace_path_from_table_id(id);
        if ns_id.as_ref().is_some_and(|parts| !parts.is_empty()) {
            let ns_path = self.resolve_namespace_path(&ns_id);
            self.ensure_namespace_exists(&ns_path, &ns_id).await?;
        }
        Ok(())
    }

    // ── Dataset operations ──────────────────────────────────────────────

    async fn load_dataset(
        &self,
        table_uri: &str,
        version: Option<i64>,
        operation: &str,
    ) -> Result<Dataset> {
        if let Some(version) = version
            && version < 0
        {
            return Err(NamespaceError::InvalidInput {
                message: format!(
                    "Table version for {} must be non-negative, got {}",
                    operation, version
                ),
            }
            .into());
        }

        let mut builder = DatasetBuilder::from_uri(table_uri);
        if let Some(opts) = &self.storage_options {
            builder = builder.with_storage_options(opts.clone());
        }
        if let Some(sess) = &self.session {
            builder = builder.with_session(sess.clone());
        }

        let dataset = builder.load().await.map_err(|e| {
            lance_core::Error::from(NamespaceError::TableNotFound {
                message: format!(
                    "Failed to open table at '{}' for {}: {}",
                    table_uri, operation, e
                ),
            })
        })?;

        if let Some(version) = version {
            return dataset.checkout_version(version as u64).await.map_err(|e| {
                lance_core::Error::from(NamespaceError::TableVersionNotFound {
                    message: format!(
                        "Failed to checkout version {} for table at '{}' during {}: {}",
                        version, table_uri, operation, e
                    ),
                })
            });
        }

        Ok(dataset)
    }

    // ── Arrow IPC helpers ───────────────────────────────────────────────

    fn decode_ipc_stream(
        data: &Bytes,
    ) -> Result<(
        Arc<arrow_schema::Schema>,
        Vec<arrow::record_batch::RecordBatch>,
    )> {
        let cursor = Cursor::new(data.as_ref());
        let stream_reader = StreamReader::try_new(cursor, None).map_err(|e| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: format!("Invalid Arrow IPC stream: {}", e),
            })
        })?;
        let schema = stream_reader.schema();
        let mut batches = Vec::new();
        for batch_result in stream_reader {
            batches.push(batch_result.map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Failed to read batch from IPC stream: {}", e),
                })
            })?);
        }
        Ok((schema, batches))
    }

    /// Convert a full URI to an `object_store::Path` relative to the bucket/root.
    ///
    /// For cloud URIs (`s3://`, `gs://`, `az://`) the scheme and bucket name are
    /// stripped because the [`ObjectStore`] instance is already bound to a specific
    /// bucket.
    ///
    /// **Limitation:** only the schemes listed below are handled. Other Azure
    /// schemes (`wasb://`, `abfss://`) and custom schemes are passed through
    /// unchanged and may produce incorrect paths on cloud storage.
    fn uri_to_object_store_path(uri: &str) -> Path {
        let path_str = if let Some(rest) = uri.strip_prefix("file://") {
            rest
        } else if let Some(rest) = uri.strip_prefix("s3://") {
            rest.split_once('/').map(|(_, p)| p).unwrap_or(rest)
        } else if let Some(rest) = uri.strip_prefix("gs://") {
            rest.split_once('/').map(|(_, p)| p).unwrap_or(rest)
        } else if let Some(rest) = uri.strip_prefix("az://") {
            rest.split_once('/').map(|(_, p)| p).unwrap_or(rest)
        } else {
            uri
        };
        Path::from(path_str)
    }

    // ── Index building ──────────────────────────────────────────────────

    fn parse_index_type(index_type: &str) -> Result<IndexType> {
        match index_type.trim().to_ascii_uppercase().as_str() {
            "SCALAR" | "BTREE" => Ok(IndexType::BTree),
            "BITMAP" => Ok(IndexType::Bitmap),
            "LABEL_LIST" | "LABELLIST" => Ok(IndexType::LabelList),
            "INVERTED" | "FTS" => Ok(IndexType::Inverted),
            "NGRAM" => Ok(IndexType::NGram),
            "ZONEMAP" | "ZONE_MAP" => Ok(IndexType::ZoneMap),
            "BLOOMFILTER" | "BLOOM_FILTER" => Ok(IndexType::BloomFilter),
            "RTREE" | "R_TREE" => Ok(IndexType::RTree),
            "VECTOR" | "IVF_PQ" => Ok(IndexType::IvfPq),
            "IVF_FLAT" => Ok(IndexType::IvfFlat),
            "IVF_SQ" => Ok(IndexType::IvfSq),
            "IVF_RQ" => Ok(IndexType::IvfRq),
            "IVF_HNSW_FLAT" => Ok(IndexType::IvfHnswFlat),
            "IVF_HNSW_SQ" => Ok(IndexType::IvfHnswSq),
            "IVF_HNSW_PQ" => Ok(IndexType::IvfHnswPq),
            other => Err(NamespaceError::InvalidInput {
                message: format!("Unsupported index_type '{}'", other),
            }
            .into()),
        }
    }

    fn parse_metric_type(distance_type: Option<&str>) -> Result<MetricType> {
        let distance_type = distance_type.unwrap_or("l2");
        MetricType::try_from(distance_type).map_err(|e| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: format!("Unsupported distance_type '{}': {}", distance_type, e),
            })
        })
    }

    fn build_index_params(request: &CreateTableIndexRequest) -> Result<BaseIndexParams> {
        let index_type = Self::parse_index_type(&request.index_type)?;
        Ok(match index_type {
            IndexType::BTree => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::BTree),
            },
            IndexType::Bitmap => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::Bitmap),
            },
            IndexType::LabelList => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::LabelList),
            },
            IndexType::NGram => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::NGram),
            },
            IndexType::ZoneMap => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::ZoneMap),
            },
            IndexType::BloomFilter => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::BloomFilter),
            },
            IndexType::RTree => BaseIndexParams::Scalar {
                index_type,
                params: ScalarIndexParams::for_builtin(BuiltinIndexType::RTree),
            },
            IndexType::Inverted => {
                let mut params = InvertedIndexParams::default();
                if let Some(with_position) = request.with_position {
                    params = params.with_position(with_position);
                }
                if let Some(base_tokenizer) = &request.base_tokenizer {
                    params = params.base_tokenizer(base_tokenizer.clone());
                }
                if let Some(language) = &request.language {
                    params = params.language(language)?;
                }
                if let Some(max_token_length) = request.max_token_length {
                    if max_token_length < 0 {
                        return Err(NamespaceError::InvalidInput {
                            message: format!(
                                "FTS max_token_length must be non-negative, got {}",
                                max_token_length
                            ),
                        }
                        .into());
                    }
                    params = params.max_token_length(Some(max_token_length as usize));
                }
                if let Some(lower_case) = request.lower_case {
                    params = params.lower_case(lower_case);
                }
                if let Some(stem) = request.stem {
                    params = params.stem(stem);
                }
                if let Some(remove_stop_words) = request.remove_stop_words {
                    params = params.remove_stop_words(remove_stop_words);
                }
                if let Some(ascii_folding) = request.ascii_folding {
                    params = params.ascii_folding(ascii_folding);
                }
                BaseIndexParams::Inverted(params)
            }
            IndexType::IvfFlat => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::with_ivf_flat_params(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                ),
            },
            IndexType::IvfPq => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::with_ivf_pq_params(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                    PQBuildParams::default(),
                ),
            },
            IndexType::IvfSq => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::with_ivf_sq_params(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                    SQBuildParams::default(),
                ),
            },
            IndexType::IvfRq => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::with_ivf_rq_params(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                    RQBuildParams::default(),
                ),
            },
            IndexType::IvfHnswFlat => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::ivf_hnsw(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                    HnswBuildParams::default(),
                ),
            },
            IndexType::IvfHnswSq => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::with_ivf_hnsw_sq_params(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                    HnswBuildParams::default(),
                    SQBuildParams::default(),
                ),
            },
            IndexType::IvfHnswPq => BaseIndexParams::Vector {
                index_type,
                params: VectorIndexParams::with_ivf_hnsw_pq_params(
                    Self::parse_metric_type(request.distance_type.as_deref())?,
                    IvfBuildParams::default(),
                    HnswBuildParams::default(),
                    PQBuildParams::default(),
                ),
            },
            other => {
                return Err(NamespaceError::InvalidInput {
                    message: format!("Unsupported index type: {}", other),
                }
                .into());
            }
        })
    }

    // ── Pagination helpers ──────────────────────────────────────────────

    fn apply_pagination(
        names: &mut Vec<String>,
        page_token: Option<String>,
        limit: Option<i32>,
    ) -> Option<String> {
        names.sort();

        if let Some(start_after) = page_token {
            if let Some(index) = names
                .iter()
                .position(|name| name.as_str() > start_after.as_str())
            {
                names.drain(0..index);
            } else {
                names.clear();
            }
        }

        let mut next_page_token = None;
        if let Some(limit) = limit
            && limit >= 0
        {
            let limit = limit as usize;
            if limit > 0 && names.len() > limit {
                next_page_token = Some(names[limit - 1].clone());
            }
            names.truncate(limit);
        }

        if names.is_empty() {
            None
        } else {
            next_page_token
        }
    }

    fn paginate_indices(
        indices: &mut Vec<IndexContent>,
        page_token: Option<String>,
        limit: Option<i32>,
    ) -> Option<String> {
        indices.sort_by(|a, b| a.index_name.cmp(&b.index_name));

        if let Some(start_after) = page_token {
            if let Some(index) = indices
                .iter()
                .position(|idx| idx.index_name.as_str() > start_after.as_str())
            {
                indices.drain(0..index);
            } else {
                indices.clear();
            }
        }

        let mut next_page_token = None;
        if let Some(limit) = limit
            && limit >= 0
        {
            let limit = limit as usize;
            if limit > 0 && indices.len() > limit {
                next_page_token = Some(indices[limit - 1].index_name.clone());
            }
            indices.truncate(limit);
        }
        if indices.is_empty() {
            None
        } else {
            next_page_token
        }
    }

    // ── Transaction helpers ─────────────────────────────────────────────

    fn transaction_operation_name(transaction: &Transaction) -> String {
        match &transaction.operation {
            Operation::CreateIndex {
                new_indices,
                removed_indices,
            } if new_indices.is_empty() && !removed_indices.is_empty() => "DropIndex".to_string(),
            _ => transaction.operation.to_string(),
        }
    }

    fn transaction_response(
        version: u64,
        transaction: &Transaction,
    ) -> DescribeTransactionResponse {
        let mut properties = transaction
            .transaction_properties
            .as_ref()
            .map(|properties| (**properties).clone())
            .unwrap_or_default();
        properties.insert("uuid".to_string(), transaction.uuid.clone());
        properties.insert("version".to_string(), version.to_string());
        properties.insert(
            "read_version".to_string(),
            transaction.read_version.to_string(),
        );
        properties.insert(
            "operation".to_string(),
            Self::transaction_operation_name(transaction),
        );
        if let Some(tag) = &transaction.tag {
            properties.insert("tag".to_string(), tag.clone());
        }

        DescribeTransactionResponse {
            status: "SUCCEEDED".to_string(),
            properties: Some(properties),
        }
    }

    fn describe_table_index_stats_response(
        stats: &serde_json::Value,
    ) -> DescribeTableIndexStatsResponse {
        let get_i64 = |key: &str| {
            stats.get(key).and_then(|value| {
                value
                    .as_i64()
                    .or_else(|| value.as_u64().and_then(|v| i64::try_from(v).ok()))
            })
        };

        DescribeTableIndexStatsResponse {
            distance_type: stats
                .get("distance_type")
                .and_then(|value| value.as_str())
                .map(str::to_string),
            index_type: stats
                .get("index_type")
                .and_then(|value| value.as_str())
                .map(str::to_string),
            num_indexed_rows: get_i64("num_indexed_rows"),
            num_unindexed_rows: get_i64("num_unindexed_rows"),
            num_indices: get_i64("num_indices").and_then(|value| i32::try_from(value).ok()),
        }
    }

    async fn find_transaction(&self, dataset: &Dataset, id: &str) -> Result<(u64, Transaction)> {
        if let Ok(version) = id.parse::<u64>() {
            let transaction = dataset
                .read_transaction_by_version(version)
                .await
                .map_err(|e| {
                    lance_core::Error::from(NamespaceError::Internal {
                        message: format!(
                            "Failed to read transaction for version {}: {}",
                            version, e
                        ),
                    })
                })?
                .ok_or_else(|| {
                    lance_core::Error::from(NamespaceError::TransactionNotFound {
                        message: format!("version {}", version),
                    })
                })?;
            return Ok((version, transaction));
        }

        let versions = dataset.versions().await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!(
                    "Failed to list table versions while resolving transaction '{}': {}",
                    id, e
                ),
            })
        })?;

        for version in versions.into_iter().rev() {
            if let Some(transaction) = dataset
                .read_transaction_by_version(version.version)
                .await
                .map_err(|e| {
                    lance_core::Error::from(NamespaceError::Internal {
                        message: format!(
                            "Failed to read transaction for version {} while resolving '{}': {}",
                            version.version, id, e
                        ),
                    })
                })?
                && transaction.uuid == id
            {
                return Ok((version.version, transaction));
            }
        }

        Err(NamespaceError::TransactionNotFound {
            message: id.to_string(),
        }
        .into())
    }

    // ── Version file helpers ────────────────────────────────────────────

    async fn delete_physical_version_files(
        &self,
        table_entries: &[TableDeleteEntry],
        best_effort: bool,
    ) -> Result<i64> {
        let mut deleted_count = 0i64;
        for te in table_entries {
            let table_uri = self.resolve_table_uri(&te.table_id)?;
            let table_path = Self::uri_to_object_store_path(&table_uri);
            let versions_dir_path = table_path.clone().join("_versions");

            for (start, end) in &te.ranges {
                for version in *start..=*end {
                    let version_path = versions_dir_path
                        .clone()
                        .join(format!("{}.manifest", version as u64));
                    match self.object_store.inner.delete(&version_path).await {
                        Ok(_) => {
                            deleted_count += 1;
                        }
                        Err(object_store::Error::NotFound { .. }) => {}
                        Err(e) => {
                            if best_effort {
                                log::warn!(
                                    "Failed to delete manifest file for version {} of table {:?}: {:?}",
                                    version,
                                    te.table_id,
                                    e
                                );
                            } else {
                                return Err(NamespaceError::Internal {
                                    message: format!(
                                        "Failed to delete version {} for table at '{}': {}",
                                        version, table_uri, e
                                    ),
                                }
                                .into());
                            }
                        }
                    }
                }
            }
        }
        Ok(deleted_count)
    }

    // ── Scanner configuration ──────────────────────────────────────────

    fn configure_scanner(
        scanner: &mut lance::dataset::scanner::Scanner,
        query: &QueryTableRequest,
    ) -> Result<()> {
        if let Some(ref filter) = query.filter
            && !filter.is_empty()
        {
            scanner.filter(filter).map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Invalid filter expression: {}", e),
                })
            })?;
        }
        if let Some(ref columns) = query.columns
            && let Some(ref col_names) = columns.column_names
        {
            let col_refs: Vec<&str> = col_names.iter().map(|s| s.as_str()).collect();
            scanner.project(&col_refs).map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Invalid column projection: {}", e),
                })
            })?;
        }
        if query.k > 0 {
            let _ = scanner.limit(Some(query.k as i64), query.offset.map(|o| o as i64));
        } else if query.offset.is_some() {
            let _ = scanner.limit(None, query.offset.map(|o| o as i64));
        }
        Ok(())
    }

    // ── Directory helpers ───────────────────────────────────────────────

    /// List tables in a namespace directory by scanning for `*.lance` subdirectories.
    async fn list_directory_tables(&self, ns_path: &Path) -> Result<Vec<String>> {
        let mut tables = Vec::new();
        let entries = self
            .object_store
            .read_dir(ns_path.clone())
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to list directory: {}", e),
                })
            })?;

        for entry in entries {
            let path = entry.trim_end_matches('/');
            if let Some(table_name) = path.strip_suffix(".lance") {
                tables.push(table_name.to_string());
            }
        }

        Ok(tables)
    }

    /// Recursively collect all tables from root, with namespace path prefixes.
    ///
    /// Note: this recurses into **all** non-`.lance` subdirectories, not only
    /// those marked with `.lance-namespace`.  Stray directories that are not
    /// namespaces will be traversed but will not produce false table entries
    /// because only entries ending with `.lance` are collected.
    async fn collect_tables_recursive(&self, dir: &Path, prefix: &str, tables: &mut Vec<String>) {
        let entries = match self.object_store.read_dir(dir.clone()).await {
            Ok(entries) => entries,
            Err(_) => return,
        };

        for entry in entries {
            let name = entry.trim_end_matches('/');
            let child_path = dir.clone().join(name);
            if let Some(table_name) = name.strip_suffix(".lance") {
                let full_name = if prefix.is_empty() {
                    table_name.to_string()
                } else {
                    format!("{}/{}", prefix, table_name)
                };
                tables.push(full_name);
            } else {
                let new_prefix = if prefix.is_empty() {
                    name.to_string()
                } else {
                    format!("{}/{}", prefix, name)
                };
                Box::pin(self.collect_tables_recursive(&child_path, &new_prefix, tables)).await;
            }
        }
    }
}

// ── LanceNamespace trait implementation ──────────────────────────────────────

#[async_trait]
impl LanceNamespace for BaseLanceNamespace {
    // ── Namespace Operations ────────────────────────────────────────────

    async fn list_namespaces(
        &self,
        request: ListNamespacesRequest,
    ) -> Result<ListNamespacesResponse> {
        let ns_path = self.resolve_namespace_path(&request.id);
        self.ensure_namespace_exists(&ns_path, &request.id).await?;

        let entries = self
            .object_store
            .read_dir(ns_path.clone())
            .await
            .unwrap_or_default();

        let mut namespaces = Vec::new();
        for entry in entries {
            let name = entry.trim_end_matches('/').to_string();
            if name.ends_with(".lance") || name == ".lance-namespace" {
                continue;
            }
            // Check if this subdirectory has a .lance-namespace marker
            let child_marker = ns_path.clone().join(name.as_str()).join(".lance-namespace");
            if self.object_store.inner.head(&child_marker).await.is_ok() {
                namespaces.push(name);
            }
        }
        namespaces.sort();

        Ok(ListNamespacesResponse::new(namespaces))
    }

    async fn describe_namespace(
        &self,
        request: DescribeNamespaceRequest,
    ) -> Result<DescribeNamespaceResponse> {
        let ns_path = self.resolve_namespace_path(&request.id);
        self.ensure_namespace_exists(&ns_path, &request.id).await?;
        #[allow(clippy::needless_update)]
        Ok(DescribeNamespaceResponse {
            properties: Some(HashMap::new()),
            ..Default::default()
        })
    }

    async fn create_namespace(
        &self,
        request: CreateNamespaceRequest,
    ) -> Result<CreateNamespaceResponse> {
        let id = &request.id;
        if id.is_none() || id.as_ref().unwrap().is_empty() {
            return Err(NamespaceError::NamespaceAlreadyExists {
                message: "root namespace".to_string(),
            }
            .into());
        }

        let ns_path = self.resolve_namespace_path(id);
        let ns_uri = self.resolve_namespace_uri(id);

        // Check if already exists by looking for marker file
        let marker_path = ns_path.clone().join(".lance-namespace");
        if self.object_store.inner.head(&marker_path).await.is_ok() {
            return Err(NamespaceError::NamespaceAlreadyExists {
                message: id.as_ref().unwrap().join("/"),
            }
            .into());
        }

        // Create directory by writing a placeholder file and then deleting it,
        // since object stores don't have explicit directory creation.
        // For local filesystem, we can just create the directory marker.
        let marker_path = ns_path.clone().join(".lance-namespace");
        self.object_store
            .inner
            .put(&marker_path, bytes::Bytes::new().into())
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create namespace at '{}': {}", ns_uri, e),
                })
            })?;

        Ok(CreateNamespaceResponse {
            properties: Some(HashMap::new()),
            ..Default::default()
        })
    }

    async fn drop_namespace(&self, request: DropNamespaceRequest) -> Result<DropNamespaceResponse> {
        let id = &request.id;
        if id.is_none() || id.as_ref().unwrap().is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Root namespace cannot be dropped".to_string(),
            }
            .into());
        }

        let ns_path = self.resolve_namespace_path(id);
        self.ensure_namespace_exists(&ns_path, id).await?;

        // Check if namespace is empty (only .lance-namespace marker allowed)
        let entries = self
            .object_store
            .read_dir(ns_path.clone())
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to check namespace: {}", e),
                })
            })?;

        let non_marker_entries: Vec<_> = entries
            .iter()
            .filter(|e| !e.ends_with(".lance-namespace"))
            .collect();

        if !non_marker_entries.is_empty() {
            return Err(NamespaceError::NamespaceNotEmpty {
                message: id.as_ref().unwrap().join("/"),
            }
            .into());
        }

        // Delete the namespace directory
        self.object_store
            .remove_dir_all(ns_path)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to drop namespace: {}", e),
                })
            })?;

        Ok(DropNamespaceResponse {
            ..Default::default()
        })
    }

    async fn namespace_exists(&self, request: NamespaceExistsRequest) -> Result<()> {
        let ns_path = self.resolve_namespace_path(&request.id);
        self.ensure_namespace_exists(&ns_path, &request.id).await
    }

    // ── Table Operations ────────────────────────────────────────────────

    async fn list_tables(&self, request: ListTablesRequest) -> Result<ListTablesResponse> {
        let ns_path = self.resolve_namespace_path(&request.id);
        self.ensure_namespace_exists(&ns_path, &request.id).await?;

        let mut tables = self.list_directory_tables(&ns_path).await?;
        let page_token = Self::apply_pagination(&mut tables, request.page_token, request.limit);

        Ok(ListTablesResponse { tables, page_token })
    }

    async fn describe_table(&self, request: DescribeTableRequest) -> Result<DescribeTableResponse> {
        self.ensure_table_exists(&request.id).await?;

        let table_name = Self::table_name_from_id(&request.id)?;
        let table_uri = self.resolve_table_uri(&request.id)?;
        let ns_path = Self::namespace_path_from_table_id(&request.id);

        let load_detailed_metadata = request.load_detailed_metadata.unwrap_or(false);

        if !load_detailed_metadata {
            return Ok(DescribeTableResponse {
                table: Some(table_name),
                namespace: ns_path,
                location: Some(table_uri.clone()),
                table_uri: Some(table_uri),
                ..Default::default()
            });
        }

        let mut builder = DatasetBuilder::from_uri(&table_uri);
        if let Some(opts) = &self.storage_options {
            builder = builder.with_storage_options(opts.clone());
        }
        if let Some(sess) = &self.session {
            builder = builder.with_session(sess.clone());
        }
        match builder.load().await {
            Ok(mut dataset) => {
                if let Some(requested_version) = request.version {
                    dataset = dataset.checkout_version(requested_version as u64).await?;
                }

                let version_info = dataset.version();
                let lance_schema = dataset.schema();
                let arrow_schema: arrow_schema::Schema = lance_schema.into();
                let json_schema = arrow_schema_to_json(&arrow_schema)?;

                let metadata: HashMap<String, String> = version_info.metadata.into_iter().collect();

                Ok(DescribeTableResponse {
                    table: Some(table_name),
                    namespace: ns_path,
                    version: Some(version_info.version as i64),
                    location: Some(table_uri.clone()),
                    table_uri: Some(table_uri),
                    schema: Some(Box::new(json_schema)),
                    metadata: Some(metadata),
                    ..Default::default()
                })
            }
            Err(err) => Err(NamespaceError::Internal {
                message: format!(
                    "Table directory exists but cannot load dataset {}: {:?}",
                    table_name, err
                ),
            }
            .into()),
        }
    }

    async fn table_exists(&self, request: TableExistsRequest) -> Result<()> {
        self.ensure_table_exists(&request.id).await
    }

    async fn create_table(
        &self,
        request: CreateTableRequest,
        request_data: Bytes,
    ) -> Result<CreateTableResponse> {
        self.ensure_parent_namespace_exists(&request.id).await?;

        let table_uri = self.resolve_table_uri(&request.id)?;
        if request_data.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Request data (Arrow IPC stream) is required for create_table".to_string(),
            }
            .into());
        }

        // Check if table already exists
        if self.ensure_table_exists(&request.id).await.is_ok() {
            let table_name = Self::table_name_from_id(&request.id)?;
            return Err(NamespaceError::TableAlreadyExists {
                message: table_name,
            }
            .into());
        }

        let (arrow_schema, batches) = Self::decode_ipc_stream(&request_data)?;

        let reader = if batches.is_empty() {
            let batch = arrow::record_batch::RecordBatch::new_empty(arrow_schema.clone());
            RecordBatchIterator::new(vec![Ok(batch)], arrow_schema.clone())
        } else {
            let batch_results: Vec<_> = batches.into_iter().map(Ok).collect();
            RecordBatchIterator::new(batch_results, arrow_schema)
        };

        let store_params = self.storage_options.as_ref().map(|opts| ObjectStoreParams {
            storage_options_accessor: Some(Arc::new(
                lance_io::object_store::StorageOptionsAccessor::with_static_options(opts.clone()),
            )),
            ..Default::default()
        });

        let write_params = WriteParams {
            mode: WriteMode::Create,
            store_params,
            ..Default::default()
        };

        Dataset::write(reader, &table_uri, Some(write_params))
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create Lance dataset: {}", e),
                })
            })?;

        Ok(CreateTableResponse {
            version: Some(1),
            location: Some(table_uri),
            ..Default::default()
        })
    }

    async fn declare_table(&self, request: DeclareTableRequest) -> Result<DeclareTableResponse> {
        self.ensure_parent_namespace_exists(&request.id).await?;

        let table_uri = self.resolve_table_uri(&request.id)?;
        let table_name = Self::table_name_from_id(&request.id)?;

        // Check if table already exists
        if self.ensure_table_exists(&request.id).await.is_ok() {
            return Err(NamespaceError::TableAlreadyExists {
                message: table_name,
            }
            .into());
        }

        // Create a marker file to declare the table
        let table_path = self.resolve_table_path(&request.id)?;
        let reserved_path = table_path.clone().join(".lance-reserved");
        self.object_store
            .inner
            .put(&reserved_path, bytes::Bytes::new().into())
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to declare table {}: {}", table_name, e),
                })
            })?;

        Ok(DeclareTableResponse {
            location: Some(table_uri),
            ..Default::default()
        })
    }

    async fn register_table(
        &self,
        _request: RegisterTableRequest,
    ) -> Result<RegisterTableResponse> {
        Err(NamespaceError::Unsupported {
            message: "register_table is not supported in base namespace".to_string(),
        }
        .into())
    }

    async fn deregister_table(
        &self,
        request: DeregisterTableRequest,
    ) -> Result<DeregisterTableResponse> {
        // In base namespace, deregister = drop (like the Java impl)
        let table_uri = self.resolve_table_uri(&request.id)?;
        self.ensure_table_exists(&request.id).await?;

        let table_path = self.resolve_table_path(&request.id)?;
        self.object_store
            .remove_dir_all(table_path)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to deregister table: {}", e),
                })
            })?;

        Ok(DeregisterTableResponse {
            id: request.id,
            location: Some(table_uri),
            ..Default::default()
        })
    }

    async fn drop_table(&self, request: DropTableRequest) -> Result<DropTableResponse> {
        self.ensure_table_exists(&request.id).await?;

        let table_uri = self.resolve_table_uri(&request.id)?;
        let table_path = self.resolve_table_path(&request.id)?;

        self.object_store
            .remove_dir_all(table_path)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to drop table: {}", e),
                })
            })?;

        Ok(DropTableResponse {
            id: request.id,
            location: Some(table_uri),
            ..Default::default()
        })
    }

    async fn rename_table(&self, request: RenameTableRequest) -> Result<RenameTableResponse> {
        self.ensure_table_exists(&request.id).await?;

        let new_name = &request.new_table_name;
        if new_name.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "New table name is required".to_string(),
            }
            .into());
        }

        // Build new table ID (same namespace, different name)
        let ns_parts = Self::namespace_path_from_table_id(&request.id);
        let mut new_id = ns_parts.unwrap_or_default();
        new_id.push(new_name.to_string());
        let new_id = Some(new_id);

        // Check new table doesn't exist
        if self.ensure_table_exists(&new_id).await.is_ok() {
            return Err(NamespaceError::TableAlreadyExists {
                message: new_name.to_string(),
            }
            .into());
        }

        let old_path = self.resolve_table_path(&request.id)?;
        let new_path = self.resolve_table_path(&new_id)?;

        // Recursively copy all files to the new location, then remove the old directory.
        // ObjectStore::rename only handles single objects, which is insufficient for
        // Lance table directories on cloud storage.
        let entries: Vec<object_store::ObjectMeta> = self
            .object_store
            .inner
            .list(Some(&old_path))
            .try_collect()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to list table files for rename: {}", e),
                })
            })?;

        let old_prefix = old_path.as_ref();
        let new_prefix = new_path.as_ref();

        for meta in &entries {
            let location_str = meta.location.as_ref();
            let relative = location_str
                .strip_prefix(old_prefix)
                .unwrap_or(location_str)
                .trim_start_matches('/');
            let dest = if relative.is_empty() {
                new_path.clone()
            } else {
                Path::from(format!("{}/{}", new_prefix, relative))
            };
            self.object_store
                .inner
                .copy(&meta.location, &dest)
                .await
                .map_err(|e| {
                    lance_core::Error::from(NamespaceError::Internal {
                        message: format!("Failed to copy file during rename: {}", e),
                    })
                })?;
        }

        self.object_store
            .remove_dir_all(old_path)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to remove old table directory during rename: {}", e),
                })
            })?;

        Ok(RenameTableResponse {
            ..Default::default()
        })
    }

    async fn restore_table(&self, request: RestoreTableRequest) -> Result<RestoreTableResponse> {
        self.ensure_table_exists(&request.id).await?;

        let version = request.version;

        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, Some(version), "restore_table")
            .await?;
        dataset.restore().await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to restore table to version {}: {}", version, e),
            })
        })?;

        Ok(RestoreTableResponse {
            ..Default::default()
        })
    }

    async fn list_all_tables(&self, _request: ListTablesRequest) -> Result<ListTablesResponse> {
        let mut tables = Vec::new();
        self.collect_tables_recursive(&self.base_path, "", &mut tables)
            .await;
        tables.sort();
        Ok(ListTablesResponse::new(tables))
    }

    // ── Data Operations ─────────────────────────────────────────────────

    async fn count_table_rows(&self, request: CountTableRowsRequest) -> Result<i64> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "count_table_rows")
            .await?;

        let count = if let Some(ref predicate) = request.predicate {
            if predicate.is_empty() {
                dataset.count_rows(None).await
            } else {
                dataset.count_rows(Some(predicate.clone())).await
            }
        } else {
            dataset.count_rows(None).await
        }
        .map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to count rows: {}", e),
            })
        })?;

        Ok(count as i64)
    }

    async fn insert_into_table(
        &self,
        request: InsertIntoTableRequest,
        request_data: Bytes,
    ) -> Result<InsertIntoTableResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;

        if request_data.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Request data (Arrow IPC stream) is required for insert_into_table"
                    .to_string(),
            }
            .into());
        }

        let (arrow_schema, batches) = Self::decode_ipc_stream(&request_data)?;
        let batch_results: Vec<_> = batches.into_iter().map(Ok).collect();
        let reader = RecordBatchIterator::new(batch_results, arrow_schema);

        let store_params = self.storage_options.as_ref().map(|opts| ObjectStoreParams {
            storage_options_accessor: Some(Arc::new(
                lance_io::object_store::StorageOptionsAccessor::with_static_options(opts.clone()),
            )),
            ..Default::default()
        });

        let write_params = WriteParams {
            mode: WriteMode::Append,
            store_params,
            ..Default::default()
        };

        Dataset::write(reader, &table_uri, Some(write_params))
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to insert into table: {}", e),
                })
            })?;

        Ok(InsertIntoTableResponse {
            ..Default::default()
        })
    }

    async fn merge_insert_into_table(
        &self,
        request: MergeInsertIntoTableRequest,
        request_data: Bytes,
    ) -> Result<MergeInsertIntoTableResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "merge_insert_into_table")
            .await?;

        let on = request.on.as_deref().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "'on' column(s) required for merge insert".to_string(),
            })
        })?;

        let on_columns: Vec<String> = on.split(',').map(|s| s.trim().to_string()).collect();
        let dataset_arc = Arc::new(dataset);
        let mut builder = lance::dataset::MergeInsertBuilder::try_new(dataset_arc, on_columns)
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Failed to create merge insert builder: {}", e),
                })
            })?;

        if request.when_matched_update_all == Some(true) {
            if let Some(ref filt) = request.when_matched_update_all_filt {
                builder.when_matched(lance::dataset::WhenMatched::UpdateIf(filt.clone()));
            } else {
                builder.when_matched(lance::dataset::WhenMatched::UpdateAll);
            }
        }

        if request.when_not_matched_insert_all == Some(true) {
            builder.when_not_matched(lance::dataset::WhenNotMatched::InsertAll);
        }

        if request.when_not_matched_by_source_delete == Some(true) {
            builder.when_not_matched_by_source(lance::dataset::WhenNotMatchedBySource::Delete);
        }

        let (arrow_schema, batches) = Self::decode_ipc_stream(&request_data)?;
        let batch_results: Vec<_> = batches.into_iter().map(Ok).collect();
        let reader = RecordBatchIterator::new(batch_results, arrow_schema);

        let job = builder.try_build().map_err(|e| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: format!("Failed to build merge insert job: {}", e),
            })
        })?;

        job.execute_reader(reader).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to merge insert: {}", e),
            })
        })?;

        Ok(MergeInsertIntoTableResponse {
            ..Default::default()
        })
    }

    async fn update_table(&self, request: UpdateTableRequest) -> Result<UpdateTableResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self.load_dataset(&table_uri, None, "update_table").await?;

        let dataset_arc = Arc::new(dataset);
        let mut op = lance::dataset::UpdateBuilder::new(dataset_arc);

        if let Some(ref predicate) = request.predicate
            && !predicate.is_empty()
        {
            op = op.update_where(predicate).map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Invalid update predicate: {}", e),
                })
            })?;
        }

        for update_pair in &request.updates {
            if update_pair.len() >= 2 {
                op = op.set(&update_pair[0], &update_pair[1]).map_err(|e| {
                    lance_core::Error::from(NamespaceError::InvalidInput {
                        message: format!("Invalid update expression: {}", e),
                    })
                })?;
            }
        }

        let job = op.build().map_err(|e| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: format!("Failed to build update job: {}", e),
            })
        })?;

        job.execute().await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to update table: {}", e),
            })
        })?;

        Ok(UpdateTableResponse {
            ..Default::default()
        })
    }

    async fn delete_from_table(
        &self,
        request: DeleteFromTableRequest,
    ) -> Result<DeleteFromTableResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "delete_from_table")
            .await?;

        let predicate = &request.predicate;
        if predicate.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "Filter predicate is required for delete".to_string(),
            }
            .into());
        }

        dataset.delete(predicate).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to delete from table: {}", e),
            })
        })?;

        Ok(DeleteFromTableResponse {
            ..Default::default()
        })
    }

    async fn query_table(&self, request: QueryTableRequest) -> Result<Bytes> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, request.version, "query_table")
            .await?;

        let mut scanner = dataset.scan();
        Self::configure_scanner(&mut scanner, &request)?;

        let batches: Vec<arrow::record_batch::RecordBatch> = scanner
            .try_into_stream()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create scan stream: {}", e),
                })
            })?
            .try_collect()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to collect scan results: {}", e),
                })
            })?;

        // Serialize to Arrow IPC
        let schema = if let Some(first) = batches.first() {
            first.schema()
        } else {
            let lance_schema = dataset.schema();
            Arc::new(lance_schema.into())
        };

        let mut buffer = Vec::new();
        {
            let mut writer = arrow_ipc::writer::StreamWriter::try_new(&mut buffer, &schema)
                .map_err(|e| {
                    lance_core::Error::from(NamespaceError::Internal {
                        message: format!("Failed to create IPC writer: {}", e),
                    })
                })?;
            for batch in &batches {
                writer.write(batch).map_err(|e| {
                    lance_core::Error::from(NamespaceError::Internal {
                        message: format!("Failed to write IPC batch: {}", e),
                    })
                })?;
            }
            writer.finish().map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to finish IPC stream: {}", e),
                })
            })?;
        }

        Ok(Bytes::from(buffer))
    }

    // ── Index Operations ────────────────────────────────────────────────

    async fn create_table_index(
        &self,
        request: CreateTableIndexRequest,
    ) -> Result<CreateTableIndexResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "create_table_index")
            .await?;
        let index_request = Self::build_index_params(&request)?;

        dataset
            .create_index(
                &[request.column.as_str()],
                index_request.index_type(),
                request.name.clone(),
                index_request.params(),
                false,
            )
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create index: {}", e),
                })
            })?;

        let transaction_id = dataset
            .read_transaction()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to read transaction after index creation: {}", e),
                })
            })?
            .map(|t| t.uuid);

        Ok(CreateTableIndexResponse { transaction_id })
    }

    async fn create_table_scalar_index(
        &self,
        request: CreateTableIndexRequest,
    ) -> Result<CreateTableScalarIndexResponse> {
        let index_type = Self::parse_index_type(&request.index_type)?;
        if !index_type.is_scalar() {
            return Err(NamespaceError::InvalidInput {
                message: format!(
                    "create_table_scalar_index only supports scalar index types, got {}",
                    request.index_type
                ),
            }
            .into());
        }

        let response = self.create_table_index(request).await?;
        Ok(CreateTableScalarIndexResponse {
            transaction_id: response.transaction_id,
        })
    }

    async fn list_table_indices(
        &self,
        request: ListTableIndicesRequest,
    ) -> Result<ListTableIndicesResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, request.version, "list_table_indices")
            .await?;

        let mut indices = dataset
            .describe_indices(None)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to describe table indices: {}", e),
                })
            })?
            .into_iter()
            .filter(|description| {
                description
                    .metadata()
                    .first()
                    .map(|metadata| !is_system_index(metadata))
                    .unwrap_or(false)
            })
            .map(|description| {
                let columns = description
                    .field_ids()
                    .iter()
                    .map(|field_id| {
                        dataset
                            .schema()
                            .field_path(i32::try_from(*field_id).map_err(|e| {
                                lance_core::Error::from(NamespaceError::Internal {
                                    message: format!(
                                        "Field id {} does not fit in i32: {}",
                                        field_id, e
                                    ),
                                })
                            })?)
                            .map_err(|e| {
                                lance_core::Error::from(NamespaceError::Internal {
                                    message: format!(
                                        "Failed to resolve field path for field_id {}: {}",
                                        field_id, e
                                    ),
                                })
                            })
                    })
                    .collect::<Result<Vec<_>>>()?;

                Ok(IndexContent {
                    index_name: description.name().to_string(),
                    index_uuid: description.metadata()[0].uuid.to_string(),
                    columns,
                    status: "SUCCEEDED".to_string(),
                    ..Default::default()
                })
            })
            .collect::<Result<Vec<_>>>()?;

        let page_token = Self::paginate_indices(&mut indices, request.page_token, request.limit);
        Ok(ListTableIndicesResponse {
            indexes: indices,
            page_token,
        })
    }

    async fn describe_table_index_stats(
        &self,
        request: DescribeTableIndexStatsRequest,
    ) -> Result<DescribeTableIndexStatsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, request.version, "describe_table_index_stats")
            .await?;
        let index_name = request.index_name.as_deref().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Index name is required".to_string(),
            })
        })?;

        let metadatas = dataset
            .load_indices_by_name(index_name)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to load index '{}' metadata: {}", index_name, e),
                })
            })?;
        if metadatas.first().is_some_and(is_system_index) {
            return Err(NamespaceError::Unsupported {
                message: format!("System index '{}' is not exposed by this API", index_name),
            }
            .into());
        }

        let stats = <Dataset as DatasetIndexExt>::index_statistics(&dataset, index_name)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to describe index statistics: {}", e),
                })
            })?;
        let stats: serde_json::Value = serde_json::from_str(&stats).map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to parse index statistics: {}", e),
            })
        })?;

        Ok(Self::describe_table_index_stats_response(&stats))
    }

    async fn drop_table_index(
        &self,
        request: DropTableIndexRequest,
    ) -> Result<DropTableIndexResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let index_name = request.index_name.as_deref().ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Index name is required for drop_table_index".to_string(),
            })
        })?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "drop_table_index")
            .await?;

        let metadatas = dataset
            .load_indices_by_name(index_name)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!(
                        "Failed to load index '{}' before dropping: {}",
                        index_name, e
                    ),
                })
            })?;
        if metadatas.first().is_some_and(is_system_index) {
            return Err(NamespaceError::Unsupported {
                message: format!(
                    "System index '{}' cannot be dropped via this API",
                    index_name
                ),
            }
            .into());
        }

        dataset.drop_index(index_name).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to drop index '{}': {}", index_name, e),
            })
        })?;

        let transaction_id = dataset
            .read_transaction()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to read transaction after drop index: {}", e),
                })
            })?
            .map(|t| t.uuid);

        Ok(DropTableIndexResponse { transaction_id })
    }

    // ── Version Operations ──────────────────────────────────────────────

    async fn list_table_versions(
        &self,
        request: ListTableVersionsRequest,
    ) -> Result<ListTableVersionsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let table_path = Self::uri_to_object_store_path(&table_uri);
        let versions_dir = table_path.clone().join("_versions");

        let manifest_metas: Vec<_> = self
            .object_store
            .read_dir_all(&versions_dir, None)
            .try_collect()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to list manifest files: {}", e),
                })
            })?;

        let mut table_versions: Vec<TableVersion> = manifest_metas
            .into_iter()
            .filter_map(|meta| {
                let filename = meta.location.filename()?;
                let version_str = filename.strip_suffix(".manifest")?;
                if version_str.starts_with('d') {
                    return None;
                }
                let file_version: u64 = version_str.parse().ok()?;
                // Lance V2 naming scheme stores manifests as
                // `(u64::MAX - version).manifest` so that lexicographic listing
                // returns newest versions first.  Detect this by checking
                // whether the numeric value exceeds half of u64 range.
                let actual_version = if file_version > u64::MAX / 2 {
                    u64::MAX - file_version
                } else {
                    file_version
                };

                Some(TableVersion {
                    version: actual_version as i64,
                    manifest_path: meta.location.to_string(),
                    manifest_size: Some(meta.size as i64),
                    e_tag: meta.e_tag,
                    timestamp_millis: Some(meta.last_modified.timestamp_millis()),
                    metadata: None,
                })
            })
            .collect();

        let want_descending = request.descending == Some(true);
        if want_descending {
            table_versions.sort_by(|a, b| b.version.cmp(&a.version));
        } else {
            table_versions.sort_by(|a, b| a.version.cmp(&b.version));
        }

        if let Some(limit) = request.limit {
            table_versions.truncate(limit as usize);
        }

        Ok(ListTableVersionsResponse {
            versions: table_versions,
            page_token: None,
        })
    }

    async fn describe_table_version(
        &self,
        request: DescribeTableVersionRequest,
    ) -> Result<DescribeTableVersionResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;

        let mut builder = DatasetBuilder::from_uri(&table_uri);
        if let Some(opts) = &self.storage_options {
            builder = builder.with_storage_options(opts.clone());
        }
        if let Some(sess) = &self.session {
            builder = builder.with_session(sess.clone());
        }
        let mut dataset = builder.load().await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to open table at '{}': {}", table_uri, e),
            })
        })?;

        if let Some(version) = request.version {
            dataset = dataset
                .checkout_version(version as u64)
                .await
                .map_err(|e| {
                    lance_core::Error::from(NamespaceError::TableVersionNotFound {
                        message: format!("Failed to checkout version {}: {}", version, e),
                    })
                })?;
        }

        let version_info = dataset.version();
        let manifest_location = dataset.manifest_location();
        let metadata: HashMap<String, String> = version_info.metadata.into_iter().collect();

        let table_version = TableVersion {
            version: version_info.version as i64,
            manifest_path: manifest_location.path.to_string(),
            manifest_size: manifest_location.size.map(|s| s as i64),
            e_tag: manifest_location.e_tag.clone(),
            timestamp_millis: Some(version_info.timestamp.timestamp_millis()),
            metadata: if metadata.is_empty() {
                None
            } else {
                Some(metadata)
            },
        };

        Ok(DescribeTableVersionResponse {
            version: Box::new(table_version),
        })
    }

    async fn create_table_version(
        &self,
        request: CreateTableVersionRequest,
    ) -> Result<CreateTableVersionResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let staging_manifest_path = &request.manifest_path;
        let version = request.version as u64;
        // manifest_path and version are required (i64 and String, not Optional)
        let table_path = Self::uri_to_object_store_path(&table_uri);

        let naming_scheme = match request.naming_scheme.as_deref() {
            Some("V1") => ManifestNamingScheme::V1,
            _ => ManifestNamingScheme::V2,
        };

        let final_path = naming_scheme.manifest_path(&table_path, version);
        let staging_path = Self::uri_to_object_store_path(staging_manifest_path);

        let manifest_data = self
            .object_store
            .inner
            .get(&staging_path)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to read staging manifest: {}", e),
                })
            })?
            .bytes()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to read staging manifest bytes: {}", e),
                })
            })?;

        let manifest_size = manifest_data.len() as i64;

        let put_result = self
            .object_store
            .inner
            .put_opts(
                &final_path,
                manifest_data.into(),
                PutOptions {
                    mode: PutMode::Create,
                    ..Default::default()
                },
            )
            .await
            .map_err(|e| match e {
                ObjectStoreError::AlreadyExists { .. } | ObjectStoreError::Precondition { .. } => {
                    lance_core::Error::from(NamespaceError::ConcurrentModification {
                        message: format!("Version {} already exists", version),
                    })
                }
                _ => lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create version {}: {}", version, e),
                }),
            })?;

        // Best-effort cleanup of staging manifest
        if let Err(e) = self.object_store.inner.delete(&staging_path).await {
            log::warn!("Failed to delete staging manifest: {:?}", e);
        }

        Ok(CreateTableVersionResponse {
            transaction_id: None,
            version: Some(Box::new(TableVersion {
                version: version as i64,
                manifest_path: final_path.to_string(),
                manifest_size: Some(manifest_size),
                e_tag: put_result.e_tag,
                timestamp_millis: None,
                metadata: None,
            })),
        })
    }

    async fn batch_delete_table_versions(
        &self,
        request: BatchDeleteTableVersionsRequest,
    ) -> Result<BatchDeleteTableVersionsResponse> {
        let ranges: Vec<(i64, i64)> = request
            .ranges
            .iter()
            .map(|r| {
                let start = r.start_version;
                let end = if r.end_version > 0 {
                    r.end_version
                } else {
                    start
                };
                (start, end)
            })
            .collect();

        let table_entries = vec![TableDeleteEntry {
            table_id: request.id.clone(),
            ranges,
        }];

        let total_deleted_count = self
            .delete_physical_version_files(&table_entries, false)
            .await?;

        Ok(BatchDeleteTableVersionsResponse {
            deleted_count: Some(total_deleted_count),
            transaction_id: None,
        })
    }

    // ── Schema Operations ───────────────────────────────────────────────

    async fn alter_table_add_columns(
        &self,
        request: AlterTableAddColumnsRequest,
    ) -> Result<AlterTableAddColumnsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "alter_table_add_columns")
            .await?;

        let transforms = &request.new_columns;

        if transforms.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "At least one column transform is required".to_string(),
            }
            .into());
        }

        let sql_pairs: Vec<(String, String)> = transforms
            .iter()
            .map(|t| {
                (
                    t.name.clone(),
                    t.expression.clone().flatten().unwrap_or_default(),
                )
            })
            .collect();

        dataset
            .add_columns(
                lance::dataset::NewColumnTransform::SqlExpressions(sql_pairs),
                None,
                None,
            )
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to add columns: {}", e),
                })
            })?;

        Ok(AlterTableAddColumnsResponse {
            ..Default::default()
        })
    }

    async fn alter_table_alter_columns(
        &self,
        request: AlterTableAlterColumnsRequest,
    ) -> Result<AlterTableAlterColumnsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "alter_table_alter_columns")
            .await?;

        let entries = &request.alterations;

        if entries.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "At least one column alteration is required".to_string(),
            }
            .into());
        }

        let alterations: Vec<lance::dataset::ColumnAlteration> = entries
            .iter()
            .map(|entry| {
                let mut alt = lance::dataset::ColumnAlteration::new(entry.path.clone());
                if let Some(Some(rename)) = &entry.rename {
                    alt = alt.rename(rename.to_string());
                }
                if let Some(Some(nullable)) = entry.nullable {
                    alt = alt.set_nullable(nullable);
                }
                alt
            })
            .collect();

        dataset.alter_columns(&alterations).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to alter columns: {}", e),
            })
        })?;

        Ok(AlterTableAlterColumnsResponse {
            ..Default::default()
        })
    }

    async fn alter_table_drop_columns(
        &self,
        request: AlterTableDropColumnsRequest,
    ) -> Result<AlterTableDropColumnsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "alter_table_drop_columns")
            .await?;

        let columns = &request.columns;
        if columns.is_empty() {
            return Err(NamespaceError::InvalidInput {
                message: "At least one column name is required".to_string(),
            }
            .into());
        }

        let col_refs: Vec<&str> = columns.iter().map(|s| s.as_str()).collect();
        dataset.drop_columns(&col_refs).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to drop columns: {}", e),
            })
        })?;

        Ok(AlterTableDropColumnsResponse {
            ..Default::default()
        })
    }

    async fn update_table_schema_metadata(
        &self,
        request: UpdateTableSchemaMetadataRequest,
    ) -> Result<UpdateTableSchemaMetadataResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let mut dataset = self
            .load_dataset(&table_uri, None, "update_table_schema_metadata")
            .await?;

        let entries: Vec<(&str, &str)> = request
            .metadata
            .as_ref()
            .map(|m| m.iter().map(|(k, v)| (k.as_str(), v.as_str())).collect())
            .unwrap_or_default();

        let updated = dataset.update_schema_metadata(entries).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to update schema metadata: {}", e),
            })
        })?;

        let transaction_id = dataset
            .read_transaction()
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!(
                        "Failed to read transaction after schema metadata update: {}",
                        e
                    ),
                })
            })?
            .map(|t| t.uuid);

        Ok(UpdateTableSchemaMetadataResponse {
            metadata: Some(updated),
            transaction_id,
        })
    }

    async fn get_table_stats(
        &self,
        request: GetTableStatsRequest,
    ) -> Result<GetTableStatsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "get_table_stats")
            .await?;

        let row_count = dataset.count_rows(None).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to count rows for stats: {}", e),
            })
        })?;

        let fragments = dataset.get_fragments();
        let mut fragment_lengths = Vec::with_capacity(fragments.len());
        for f in &fragments {
            let count = f.count_rows(None).await.unwrap_or(0) as i64;
            fragment_lengths.push(count);
        }
        let num_fragments = fragment_lengths.len() as i64;
        let num_small_fragments = fragment_lengths.iter().filter(|&&l| l < 1024).count() as i64;

        let (min, max, mean, p25, p50, p75, p99) = if fragment_lengths.is_empty() {
            (0, 0, 0, 0, 0, 0, 0)
        } else {
            let mut sorted = fragment_lengths.clone();
            sorted.sort();
            let len = sorted.len();
            let sum: i64 = sorted.iter().sum();
            let percentile = |p: usize| sorted[(len - 1) * p / 100];
            (
                sorted[0],
                sorted[len - 1],
                sum / len as i64,
                percentile(25),
                percentile(50),
                percentile(75),
                percentile(99),
            )
        };

        let num_indices = dataset
            .describe_indices(None)
            .await
            .map(|v| v.len() as i64)
            .unwrap_or(0);

        Ok(GetTableStatsResponse::new(
            0, // total_bytes — not easily available without scanning all files
            row_count as i64,
            num_indices,
            lance_namespace::models::FragmentStats::new(
                num_fragments,
                num_small_fragments,
                lance_namespace::models::FragmentSummary::new(min, max, mean, p25, p50, p75, p99),
            ),
        ))
    }

    // ── Transaction Operations ──────────────────────────────────────────

    async fn describe_transaction(
        &self,
        request: DescribeTransactionRequest,
    ) -> Result<DescribeTransactionResponse> {
        let mut request_id = request.id.ok_or_else(|| {
            lance_core::Error::from(NamespaceError::InvalidInput {
                message: "Transaction id must include table id and transaction identifier"
                    .to_string(),
            })
        })?;
        if request_id.len() < 2 {
            return Err(NamespaceError::InvalidInput {
                message: format!(
                    "Transaction request id must include table id and transaction identifier, got {:?}",
                    request_id
                ),
            }
            .into());
        }

        let id = request_id.pop().expect("request_id len checked above");
        let table_id = Some(request_id);
        let table_uri = self.resolve_table_uri(&table_id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "describe_transaction")
            .await?;
        let (version, transaction) = self.find_transaction(&dataset, &id).await?;

        Ok(Self::transaction_response(version, &transaction))
    }

    async fn alter_transaction(
        &self,
        _request: AlterTransactionRequest,
    ) -> Result<AlterTransactionResponse> {
        Err(NamespaceError::Unsupported {
            message: "alter_transaction is not supported in base namespace".to_string(),
        }
        .into())
    }

    // ── Query Plan Operations ───────────────────────────────────────────

    async fn explain_table_query_plan(
        &self,
        request: ExplainTableQueryPlanRequest,
    ) -> Result<String> {
        let query = &request.query;
        let table_uri = self.resolve_table_uri(&query.id)?;
        let dataset = self
            .load_dataset(&table_uri, query.version, "explain_table_query_plan")
            .await?;

        let mut scanner = dataset.scan();
        Self::configure_scanner(&mut scanner, query)?;

        let verbose = request.verbose.unwrap_or(false);
        scanner.explain_plan(verbose).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to explain query plan: {}", e),
            })
        })
    }

    async fn analyze_table_query_plan(
        &self,
        request: AnalyzeTableQueryPlanRequest,
    ) -> Result<String> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, request.version, "analyze_table_query_plan")
            .await?;

        let mut scanner = dataset.scan();
        if let Some(ref filter) = request.filter
            && !filter.is_empty()
        {
            scanner.filter(filter).map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Invalid filter expression: {}", e),
                })
            })?;
        }
        if let Some(ref columns) = request.columns
            && let Some(ref col_names) = columns.column_names
        {
            let col_refs: Vec<&str> = col_names.iter().map(|s| s.as_str()).collect();
            scanner.project(&col_refs).map_err(|e| {
                lance_core::Error::from(NamespaceError::InvalidInput {
                    message: format!("Invalid column projection: {}", e),
                })
            })?;
        }
        if request.k > 0 {
            let _ = scanner.limit(Some(request.k as i64), request.offset.map(|o| o as i64));
        }

        // analyze = explain with verbose
        scanner.explain_plan(true).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to analyze query plan: {}", e),
            })
        })
    }

    // ── Tag Operations ──────────────────────────────────────────────────

    async fn create_table_tag(
        &self,
        request: CreateTableTagRequest,
    ) -> Result<CreateTableTagResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "create_table_tag")
            .await?;

        dataset
            .tags()
            .create(&request.tag, request.version as u64)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to create tag '{}': {}", request.tag, e),
                })
            })?;

        Ok(CreateTableTagResponse {
            transaction_id: None,
        })
    }

    async fn delete_table_tag(
        &self,
        request: DeleteTableTagRequest,
    ) -> Result<DeleteTableTagResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "delete_table_tag")
            .await?;

        dataset.tags().delete(&request.tag).await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to delete tag '{}': {}", request.tag, e),
            })
        })?;

        Ok(DeleteTableTagResponse {
            transaction_id: None,
        })
    }

    async fn update_table_tag(
        &self,
        request: UpdateTableTagRequest,
    ) -> Result<UpdateTableTagResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "update_table_tag")
            .await?;

        dataset
            .tags()
            .update(&request.tag, request.version as u64)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to update tag '{}': {}", request.tag, e),
                })
            })?;

        Ok(UpdateTableTagResponse {
            transaction_id: None,
        })
    }

    async fn list_table_tags(
        &self,
        request: ListTableTagsRequest,
    ) -> Result<ListTableTagsResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "list_table_tags")
            .await?;

        let sdk_tags = dataset.tags().list().await.map_err(|e| {
            lance_core::Error::from(NamespaceError::Internal {
                message: format!("Failed to list tags: {}", e),
            })
        })?;

        let mut tags: HashMap<String, TagContents> = sdk_tags
            .into_iter()
            .map(|(name, contents)| {
                (
                    name,
                    TagContents {
                        branch: contents.branch,
                        version: contents.version as i64,
                        manifest_size: contents.manifest_size as i64,
                    },
                )
            })
            .collect();

        // Apply pagination
        let mut tag_names: Vec<String> = tags.keys().cloned().collect();
        tag_names.sort();

        if let Some(ref start_after) = request.page_token {
            tag_names.retain(|name| name.as_str() > start_after.as_str());
        }

        let mut page_token = None;
        if let Some(limit) = request.limit {
            let limit = limit as usize;
            if limit > 0 && tag_names.len() > limit {
                page_token = Some(tag_names[limit - 1].clone());
                tag_names.truncate(limit);
            }
        }

        let filtered_tags: HashMap<String, TagContents> = tag_names
            .into_iter()
            .filter_map(|name| tags.remove(&name).map(|contents| (name, contents)))
            .collect();

        Ok(ListTableTagsResponse {
            tags: filtered_tags,
            page_token,
        })
    }

    async fn get_table_tag_version(
        &self,
        request: GetTableTagVersionRequest,
    ) -> Result<GetTableTagVersionResponse> {
        let table_uri = self.resolve_table_uri(&request.id)?;
        let dataset = self
            .load_dataset(&table_uri, None, "get_table_tag_version")
            .await?;

        let version = dataset
            .tags()
            .get_version(&request.tag)
            .await
            .map_err(|e| {
                lance_core::Error::from(NamespaceError::Internal {
                    message: format!("Failed to get version for tag '{}': {}", request.tag, e),
                })
            })?;

        Ok(GetTableTagVersionResponse {
            version: version as i64,
            ..Default::default()
        })
    }

    // ── Utility ─────────────────────────────────────────────────────────

    fn namespace_id(&self) -> String {
        format!("BaseLanceNamespace{{root={}}}", self.root)
    }
}

// ── Tests ───────────────────────────────────────────────────────────────────

#[cfg(test)]
mod tests {
    use super::*;
    use arrow::array::{Int32Array, StringArray};
    use arrow::datatypes::{DataType, Field, Schema as ArrowSchema};
    use arrow_ipc::reader::StreamReader;
    use std::io::Cursor;

    fn create_ipc_data_from_batches(
        schema: Arc<ArrowSchema>,
        batches: Vec<arrow::record_batch::RecordBatch>,
    ) -> Vec<u8> {
        let mut buffer = Vec::new();
        {
            let mut writer =
                arrow_ipc::writer::StreamWriter::try_new(&mut buffer, &schema).unwrap();
            for batch in &batches {
                writer.write(batch).unwrap();
            }
            writer.finish().unwrap();
        }
        buffer
    }

    fn create_test_table_data() -> Vec<u8> {
        let schema = Arc::new(ArrowSchema::new(vec![
            Field::new("id", DataType::Int32, false),
            Field::new("name", DataType::Utf8, true),
            Field::new("age", DataType::Int32, true),
        ]));
        let batch = arrow::record_batch::RecordBatch::try_new(
            schema.clone(),
            vec![
                Arc::new(Int32Array::from(vec![1, 2, 3])),
                Arc::new(StringArray::from(vec!["Alice", "Bob", "Charlie"])),
                Arc::new(Int32Array::from(vec![30, 25, 35])),
            ],
        )
        .unwrap();
        create_ipc_data_from_batches(schema, vec![batch])
    }

    fn create_additional_data() -> Vec<u8> {
        let schema = Arc::new(ArrowSchema::new(vec![
            Field::new("id", DataType::Int32, false),
            Field::new("name", DataType::Utf8, true),
            Field::new("age", DataType::Int32, true),
        ]));
        let batch = arrow::record_batch::RecordBatch::try_new(
            schema.clone(),
            vec![
                Arc::new(Int32Array::from(vec![4, 5])),
                Arc::new(StringArray::from(vec!["Dave", "Eve"])),
                Arc::new(Int32Array::from(vec![40, 28])),
            ],
        )
        .unwrap();
        create_ipc_data_from_batches(schema, vec![batch])
    }

    async fn create_test_namespace() -> (BaseLanceNamespace, tempfile::TempDir) {
        let temp_dir = tempfile::TempDir::new().unwrap();
        let namespace = BaseLanceNamespaceBuilder::new(temp_dir.path().to_str().unwrap())
            .build()
            .await
            .unwrap();
        (namespace, temp_dir)
    }

    async fn create_namespace(ns: &BaseLanceNamespace, parts: &[&str]) {
        ns.create_namespace(CreateNamespaceRequest {
            id: Some(parts.iter().map(|s| s.to_string()).collect()),
            ..Default::default()
        })
        .await
        .unwrap();
    }

    async fn create_test_table(ns: &BaseLanceNamespace, id_parts: &[&str]) {
        let data = create_test_table_data();
        ns.create_table(
            CreateTableRequest {
                id: Some(id_parts.iter().map(|s| s.to_string()).collect()),
                ..Default::default()
            },
            Bytes::from(data),
        )
        .await
        .unwrap();
    }

    fn count_rows_in_ipc(data: &[u8]) -> usize {
        let cursor = Cursor::new(data);
        let reader = StreamReader::try_new(cursor, None).unwrap();
        let mut total = 0;
        for batch in reader {
            total += batch.unwrap().num_rows();
        }
        total
    }

    fn table_id(parts: &[&str]) -> Option<Vec<String>> {
        Some(parts.iter().map(|s| s.to_string()).collect())
    }

    // ── Namespace Operations ────────────────────────────────────────────

    #[tokio::test]
    async fn test_namespace_id() {
        let (ns, _dir) = create_test_namespace().await;
        assert!(ns.namespace_id().contains("BaseLanceNamespace"));
    }

    #[tokio::test]
    async fn test_create_and_list_namespaces() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        let resp = ns
            .list_namespaces(ListNamespacesRequest {
                id: None,
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.namespaces.contains(&"workspace".to_string()));
    }

    #[tokio::test]
    async fn test_create_namespace_already_exists() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        let result = ns
            .create_namespace(CreateNamespaceRequest {
                id: table_id(&["workspace"]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("already exists"));
    }

    #[tokio::test]
    async fn test_describe_namespace() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        let resp = ns
            .describe_namespace(DescribeNamespaceRequest {
                id: table_id(&["workspace"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.properties.is_some());
    }

    #[tokio::test]
    async fn test_namespace_exists() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        assert!(
            ns.namespace_exists(NamespaceExistsRequest {
                id: table_id(&["workspace"]),
                ..Default::default()
            })
            .await
            .is_ok()
        );

        assert!(
            ns.namespace_exists(NamespaceExistsRequest {
                id: table_id(&["nonexistent"]),
                ..Default::default()
            })
            .await
            .is_err()
        );
    }

    #[tokio::test]
    async fn test_drop_namespace() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        ns.drop_namespace(DropNamespaceRequest {
            id: table_id(&["workspace"]),
            ..Default::default()
        })
        .await
        .unwrap();

        assert!(
            ns.namespace_exists(NamespaceExistsRequest {
                id: table_id(&["workspace"]),
                ..Default::default()
            })
            .await
            .is_err()
        );
    }

    #[tokio::test]
    async fn test_drop_namespace_not_empty() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .drop_namespace(DropNamespaceRequest {
                id: table_id(&["workspace"]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("not empty"));
    }

    #[tokio::test]
    async fn test_nested_namespaces() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["org"]).await;
        create_namespace(&ns, &["org", "team"]).await;

        let resp = ns
            .list_namespaces(ListNamespacesRequest {
                id: table_id(&["org"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.namespaces.contains(&"team".to_string()));
    }

    // ── Table Operations ────────────────────────────────────────────────

    #[tokio::test]
    async fn test_create_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        let data = create_test_table_data();
        let resp = ns
            .create_table(
                CreateTableRequest {
                    id: table_id(&["workspace", "test_table"]),
                    ..Default::default()
                },
                Bytes::from(data),
            )
            .await
            .unwrap();

        assert!(resp.location.unwrap().contains("test_table"));
        assert_eq!(resp.version, Some(1));
    }

    #[tokio::test]
    async fn test_create_table_already_exists() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let data = create_test_table_data();
        let result = ns
            .create_table(
                CreateTableRequest {
                    id: table_id(&["workspace", "test_table"]),
                    ..Default::default()
                },
                Bytes::from(data),
            )
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("already exists"));
    }

    #[tokio::test]
    async fn test_list_tables() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "table1"]).await;
        create_test_table(&ns, &["workspace", "table2"]).await;

        let resp = ns
            .list_tables(ListTablesRequest {
                id: table_id(&["workspace"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.tables.len(), 2);
        assert!(resp.tables.contains(&"table1".to_string()));
        assert!(resp.tables.contains(&"table2".to_string()));
    }

    #[tokio::test]
    async fn test_describe_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let resp = ns
            .describe_table(DescribeTableRequest {
                id: table_id(&["workspace", "test_table"]),
                load_detailed_metadata: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();

        assert!(resp.location.unwrap().contains("test_table"));
        assert_eq!(resp.version, Some(1));
        assert!(resp.schema.is_some());
        assert_eq!(resp.schema.unwrap().fields.len(), 3);
    }

    #[tokio::test]
    async fn test_table_exists() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        assert!(
            ns.table_exists(TableExistsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .is_ok()
        );

        assert!(
            ns.table_exists(TableExistsRequest {
                id: table_id(&["workspace", "nonexistent"]),
                ..Default::default()
            })
            .await
            .is_err()
        );
    }

    #[tokio::test]
    async fn test_drop_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        ns.drop_table(DropTableRequest {
            id: table_id(&["workspace", "test_table"]),
            ..Default::default()
        })
        .await
        .unwrap();

        assert!(
            ns.table_exists(TableExistsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .is_err()
        );
    }

    #[tokio::test]
    async fn test_count_table_rows() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 3);
    }

    #[tokio::test]
    async fn test_table_at_root_level() {
        let (ns, _dir) = create_test_namespace().await;

        let data = create_test_table_data();
        let resp = ns
            .create_table(
                CreateTableRequest {
                    id: table_id(&["root_table"]),
                    ..Default::default()
                },
                Bytes::from(data),
            )
            .await
            .unwrap();
        assert!(resp.location.is_some());

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["root_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 3);
    }

    // ── Data Operations ─────────────────────────────────────────────────

    #[tokio::test]
    async fn test_insert_into_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let additional = create_additional_data();
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            },
            Bytes::from(additional),
        )
        .await
        .unwrap();

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 5);
    }

    #[tokio::test]
    async fn test_query_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .query_table(QueryTableRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 3);
    }

    #[tokio::test]
    async fn test_query_table_with_filter() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .query_table(QueryTableRequest {
                id: table_id(&["workspace", "test_table"]),
                filter: Some("age > 28".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 2); // Alice (30) and Charlie (35)
    }

    #[tokio::test]
    async fn test_query_table_with_projection() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let mut columns = lance_namespace::models::QueryTableRequestColumns::new();
        columns.column_names = Some(vec!["id".to_string(), "name".to_string()]);

        let result = ns
            .query_table(QueryTableRequest {
                id: table_id(&["workspace", "test_table"]),
                columns: Some(Box::new(columns)),
                ..Default::default()
            })
            .await
            .unwrap();

        let cursor = Cursor::new(&result[..]);
        let reader = StreamReader::try_new(cursor, None).unwrap();
        let schema = reader.schema();
        assert_eq!(schema.fields().len(), 2);
        assert_eq!(schema.field(0).name(), "id");
        assert_eq!(schema.field(1).name(), "name");
    }

    #[tokio::test]
    async fn test_delete_from_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        ns.delete_from_table(DeleteFromTableRequest {
            id: table_id(&["workspace", "test_table"]),
            predicate: "id = 2".to_string(),
            ..Default::default()
        })
        .await
        .unwrap();

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 2);
    }

    #[tokio::test]
    async fn test_merge_insert_into_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        // Merge data: update id=1, insert id=4
        let schema = Arc::new(ArrowSchema::new(vec![
            Field::new("id", DataType::Int32, false),
            Field::new("name", DataType::Utf8, true),
            Field::new("age", DataType::Int32, true),
        ]));
        let batch = arrow::record_batch::RecordBatch::try_new(
            schema.clone(),
            vec![
                Arc::new(Int32Array::from(vec![1, 4])),
                Arc::new(StringArray::from(vec!["Alice Updated", "Dave"])),
                Arc::new(Int32Array::from(vec![31, 40])),
            ],
        )
        .unwrap();
        let merge_data = create_ipc_data_from_batches(schema, vec![batch]);

        ns.merge_insert_into_table(
            MergeInsertIntoTableRequest {
                id: table_id(&["workspace", "test_table"]),
                on: Some("id".to_string()),
                when_matched_update_all: Some(true),
                when_not_matched_insert_all: Some(true),
                ..Default::default()
            },
            Bytes::from(merge_data),
        )
        .await
        .unwrap();

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 4); // Alice updated, Bob, Charlie, Dave
    }

    #[tokio::test]
    async fn test_insert_query_delete_roundtrip() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Verify initial
        let result = ns
            .query_table(QueryTableRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 3);

        // Insert more
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        let result = ns
            .query_table(QueryTableRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 5);

        // Delete
        ns.delete_from_table(DeleteFromTableRequest {
            id: tid.clone(),
            predicate: "id >= 4".to_string(),
            ..Default::default()
        })
        .await
        .unwrap();

        let result = ns
            .query_table(QueryTableRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 3);
    }

    // ── Index Operations ────────────────────────────────────────────────

    #[tokio::test]
    async fn test_create_and_list_scalar_index() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_scalar_index(CreateTableIndexRequest {
            id: tid.clone(),
            column: "id".to_string(),
            index_type: "btree".to_string(),
            name: Some("id_idx".to_string()),
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .list_table_indices(ListTableIndicesRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.indexes.len(), 1);
        assert!(resp.indexes[0].columns.contains(&"id".to_string()));
    }

    #[tokio::test]
    async fn test_drop_table_index() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_scalar_index(CreateTableIndexRequest {
            id: tid.clone(),
            column: "id".to_string(),
            index_type: "btree".to_string(),
            name: Some("id_idx".to_string()),
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .list_table_indices(ListTableIndicesRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .unwrap();
        let index_name = resp.indexes[0].index_name.clone();

        ns.drop_table_index(DropTableIndexRequest {
            id: tid.clone(),
            index_name: Some(index_name),
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .list_table_indices(ListTableIndicesRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.indexes.len(), 0);
    }

    // ── Schema Operations ───────────────────────────────────────────────

    #[tokio::test]
    async fn test_alter_table_add_columns() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        let mut transform = lance_namespace::models::AddColumnsEntry::new("score".to_string());
        transform.expression = Some(Some("0".to_string()));

        ns.alter_table_add_columns(AlterTableAddColumnsRequest {
            id: tid.clone(),
            new_columns: vec![transform],
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .describe_table(DescribeTableRequest {
                id: tid,
                load_detailed_metadata: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();
        let schema = resp.schema.unwrap();
        assert_eq!(schema.fields.len(), 4);
        assert!(schema.fields.iter().any(|f| f.name == "score"));
    }

    #[tokio::test]
    async fn test_alter_table_drop_columns() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.alter_table_drop_columns(AlterTableDropColumnsRequest {
            id: tid.clone(),
            columns: vec!["age".to_string()],
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .describe_table(DescribeTableRequest {
                id: tid,
                load_detailed_metadata: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();
        let schema = resp.schema.unwrap();
        assert!(!schema.fields.iter().any(|f| f.name == "age"));
    }

    #[tokio::test]
    async fn test_alter_table_alter_columns() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        let mut entry = lance_namespace::models::AlterColumnsEntry::new("name".to_string());
        entry.rename = Some(Some("full_name".to_string()));

        ns.alter_table_alter_columns(AlterTableAlterColumnsRequest {
            id: tid.clone(),
            alterations: vec![entry],
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .describe_table(DescribeTableRequest {
                id: tid,
                load_detailed_metadata: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();
        let schema = resp.schema.unwrap();
        assert!(schema.fields.iter().any(|f| f.name == "full_name"));
        assert!(!schema.fields.iter().any(|f| f.name == "name"));
    }

    #[tokio::test]
    async fn test_get_table_stats() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let resp = ns
            .get_table_stats(GetTableStatsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.num_rows, 3);
    }

    // ── Version Operations ──────────────────────────────────────────────

    #[tokio::test]
    async fn test_list_table_versions() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Insert to create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        let resp = ns
            .list_table_versions(ListTableVersionsRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.versions.len() >= 2);
    }

    #[tokio::test]
    async fn test_restore_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Insert to create version 2 (5 rows total)
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 5);

        // Restore to version 1
        ns.restore_table(RestoreTableRequest {
            id: tid.clone(),
            version: 1,
            ..Default::default()
        })
        .await
        .unwrap();

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 3);
    }

    // ── Advanced ────────────────────────────────────────────────────────

    #[tokio::test]
    async fn test_table_in_child_namespace() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["org"]).await;
        create_namespace(&ns, &["org", "team"]).await;
        create_test_table(&ns, &["org", "team", "dataset"]).await;

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["org", "team", "dataset"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 3);
    }

    #[tokio::test]
    async fn test_rename_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        ns.rename_table(RenameTableRequest {
            id: table_id(&["workspace", "test_table"]),
            new_table_name: "renamed_table".to_string(),
            ..Default::default()
        })
        .await
        .unwrap();

        // Old name should not exist
        assert!(
            ns.table_exists(TableExistsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .is_err()
        );

        // New name should exist
        assert!(
            ns.table_exists(TableExistsRequest {
                id: table_id(&["workspace", "renamed_table"]),
                ..Default::default()
            })
            .await
            .is_ok()
        );
    }

    #[tokio::test]
    async fn test_list_all_tables() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["ns1"]).await;
        create_namespace(&ns, &["ns2"]).await;
        create_test_table(&ns, &["ns1", "table_a"]).await;
        create_test_table(&ns, &["ns2", "table_b"]).await;

        let resp = ns
            .list_all_tables(ListTablesRequest {
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.tables.len(), 2);
    }

    #[tokio::test]
    async fn test_deregister_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        ns.deregister_table(DeregisterTableRequest {
            id: table_id(&["workspace", "test_table"]),
            ..Default::default()
        })
        .await
        .unwrap();

        // Table should be gone
        assert!(
            ns.table_exists(TableExistsRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await
            .is_err()
        );
    }

    // ── Additional coverage ────────────────────────────────────────────

    #[tokio::test]
    async fn test_update_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.update_table(UpdateTableRequest {
            id: tid.clone(),
            updates: vec![vec!["age".to_string(), "age + 1".to_string()]],
            predicate: Some("id = 1".to_string()),
            ..Default::default()
        })
        .await
        .unwrap();

        let result = ns
            .query_table(QueryTableRequest {
                id: tid,
                filter: Some("id = 1".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        let cursor = Cursor::new(&result[..]);
        let reader = StreamReader::try_new(cursor, None).unwrap();
        for batch in reader {
            let batch = batch.unwrap();
            let ages = batch
                .column_by_name("age")
                .unwrap()
                .as_any()
                .downcast_ref::<Int32Array>()
                .unwrap();
            assert_eq!(ages.value(0), 31); // was 30, now 31
        }
    }

    #[tokio::test]
    async fn test_declare_table() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        let tid = table_id(&["workspace", "reserved_table"]);

        let resp = ns
            .declare_table(DeclareTableRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.location.is_some());

        // Table directory should exist (has .lance-reserved marker)
        assert!(
            ns.table_exists(TableExistsRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .is_ok()
        );

        // Declaring the same table again should fail
        let result = ns
            .declare_table(DeclareTableRequest {
                id: tid,
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_describe_table_version() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        let resp = ns
            .describe_table_version(DescribeTableVersionRequest {
                id: tid,
                version: Some(1),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.version.version, 1);
    }

    #[tokio::test]
    async fn test_count_table_rows_with_predicate() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "test_table"]),
                predicate: Some("age > 28".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 2); // Alice (30) and Charlie (35)
    }

    #[tokio::test]
    async fn test_rename_table_preserves_data() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        ns.rename_table(RenameTableRequest {
            id: table_id(&["workspace", "test_table"]),
            new_table_name: "renamed_table".to_string(),
            ..Default::default()
        })
        .await
        .unwrap();

        // Verify data is preserved after rename
        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "renamed_table"]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 3);
    }

    #[tokio::test]
    async fn test_list_all_tables_includes_namespace_prefix() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["ns1"]).await;
        create_namespace(&ns, &["ns2"]).await;
        create_test_table(&ns, &["ns1", "table_a"]).await;
        create_test_table(&ns, &["ns2", "table_b"]).await;

        let resp = ns
            .list_all_tables(ListTablesRequest {
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.tables.contains(&"ns1/table_a".to_string()));
        assert!(resp.tables.contains(&"ns2/table_b".to_string()));
    }

    #[tokio::test]
    async fn test_list_tables_pagination() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "alpha"]).await;
        create_test_table(&ns, &["workspace", "beta"]).await;
        create_test_table(&ns, &["workspace", "gamma"]).await;

        // Page 1: limit 2
        let resp = ns
            .list_tables(ListTablesRequest {
                id: table_id(&["workspace"]),
                limit: Some(2),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.tables.len(), 2);
        assert_eq!(resp.tables[0], "alpha");
        assert_eq!(resp.tables[1], "beta");
        assert!(resp.page_token.is_some());

        // Page 2: use page_token
        let resp2 = ns
            .list_tables(ListTablesRequest {
                id: table_id(&["workspace"]),
                page_token: resp.page_token,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp2.tables.len(), 1);
        assert_eq!(resp2.tables[0], "gamma");
    }

    // ── Error cases ────────────────────────────────────────────────────

    #[tokio::test]
    async fn test_create_table_empty_data() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;

        let result = ns
            .create_table(
                CreateTableRequest {
                    id: table_id(&["workspace", "test_table"]),
                    ..Default::default()
                },
                Bytes::new(),
            )
            .await;
        assert!(result.is_err());
        assert!(
            result
                .unwrap_err()
                .to_string()
                .contains("required for create_table")
        );
    }

    #[tokio::test]
    async fn test_insert_into_table_empty_data() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .insert_into_table(
                InsertIntoTableRequest {
                    id: table_id(&["workspace", "test_table"]),
                    ..Default::default()
                },
                Bytes::new(),
            )
            .await;
        assert!(result.is_err());
        assert!(
            result
                .unwrap_err()
                .to_string()
                .contains("required for insert_into_table")
        );
    }

    #[tokio::test]
    async fn test_delete_from_table_empty_predicate() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .delete_from_table(DeleteFromTableRequest {
                id: table_id(&["workspace", "test_table"]),
                predicate: "".to_string(),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(
            result
                .unwrap_err()
                .to_string()
                .contains("predicate is required")
        );
    }

    #[tokio::test]
    async fn test_register_table_unsupported() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .register_table(RegisterTableRequest {
                id: table_id(&["workspace", "test_table"]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("not supported"));
    }

    #[tokio::test]
    async fn test_drop_root_namespace_fails() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .drop_namespace(DropNamespaceRequest {
                id: Some(vec![]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_create_root_namespace_fails() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .create_namespace(CreateNamespaceRequest {
                id: Some(vec![]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("already exists"));
    }

    // ── Builder ────────────────────────────────────────────────────────

    #[tokio::test]
    async fn test_from_properties_builder() {
        let temp_dir = tempfile::TempDir::new().unwrap();
        let root = temp_dir.path().to_str().unwrap().to_string();

        let mut properties = HashMap::new();
        properties.insert("root".to_string(), root);
        properties.insert("storage.key1".to_string(), "value1".to_string());

        let ns = BaseLanceNamespaceBuilder::from_properties(properties, None)
            .unwrap()
            .build()
            .await
            .unwrap();
        assert!(ns.namespace_id().contains("BaseLanceNamespace"));
    }

    #[tokio::test]
    async fn test_from_properties_missing_root() {
        let properties = HashMap::new();
        let result = BaseLanceNamespaceBuilder::from_properties(properties, None);
        assert!(result.is_err());
    }

    // ── Unsupported operations ─────────────────────────────────────────

    #[tokio::test]
    async fn test_alter_transaction_unsupported() {
        let (ns, _dir) = create_test_namespace().await;
        let result = ns
            .alter_transaction(AlterTransactionRequest {
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("not supported"));
    }

    // ── Tag operations ────────────────────────────────────────────────

    #[tokio::test]
    async fn test_create_and_get_table_tag() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_tag(CreateTableTagRequest {
            id: tid.clone(),
            tag: "v1.0".to_string(),
            version: 1,
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .get_table_tag_version(GetTableTagVersionRequest {
                id: tid,
                tag: "v1.0".to_string(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.version, 1);
    }

    #[tokio::test]
    async fn test_list_table_tags() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_tag(CreateTableTagRequest {
            id: tid.clone(),
            tag: "v1.0".to_string(),
            version: 1,
            ..Default::default()
        })
        .await
        .unwrap();

        // Insert to create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        ns.create_table_tag(CreateTableTagRequest {
            id: tid.clone(),
            tag: "v2.0".to_string(),
            version: 2,
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .list_table_tags(ListTableTagsRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.tags.len(), 2);
        assert!(resp.tags.contains_key("v1.0"));
        assert!(resp.tags.contains_key("v2.0"));
        assert_eq!(resp.tags["v1.0"].version, 1);
        assert_eq!(resp.tags["v2.0"].version, 2);
    }

    #[tokio::test]
    async fn test_update_table_tag() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_tag(CreateTableTagRequest {
            id: tid.clone(),
            tag: "latest".to_string(),
            version: 1,
            ..Default::default()
        })
        .await
        .unwrap();

        // Insert to create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        ns.update_table_tag(UpdateTableTagRequest {
            id: tid.clone(),
            tag: "latest".to_string(),
            version: 2,
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .get_table_tag_version(GetTableTagVersionRequest {
                id: tid,
                tag: "latest".to_string(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.version, 2);
    }

    #[tokio::test]
    async fn test_delete_table_tag() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_tag(CreateTableTagRequest {
            id: tid.clone(),
            tag: "to-delete".to_string(),
            version: 1,
            ..Default::default()
        })
        .await
        .unwrap();

        ns.delete_table_tag(DeleteTableTagRequest {
            id: tid.clone(),
            tag: "to-delete".to_string(),
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .list_table_tags(ListTableTagsRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.tags.is_empty());
    }

    #[tokio::test]
    async fn test_list_table_tags_pagination() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        for i in 1..=3 {
            ns.create_table_tag(CreateTableTagRequest {
                id: tid.clone(),
                tag: format!("tag-{}", i),
                version: 1,
                ..Default::default()
            })
            .await
            .unwrap();
        }

        let resp = ns
            .list_table_tags(ListTableTagsRequest {
                id: tid.clone(),
                limit: Some(2),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.tags.len(), 2);
        assert!(resp.page_token.is_some());
    }

    // ── Query plan operations ──────────────────────────────────────────

    #[tokio::test]
    async fn test_explain_table_query_plan() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let query = QueryTableRequest {
            id: table_id(&["workspace", "test_table"]),
            filter: Some("age > 25".to_string()),
            ..Default::default()
        };

        let plan = ns
            .explain_table_query_plan(ExplainTableQueryPlanRequest {
                id: table_id(&["workspace", "test_table"]),
                query: Box::new(query),
                verbose: Some(false),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(!plan.is_empty());
    }

    #[tokio::test]
    async fn test_explain_table_query_plan_verbose() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let query = QueryTableRequest {
            id: table_id(&["workspace", "test_table"]),
            ..Default::default()
        };

        let plan = ns
            .explain_table_query_plan(ExplainTableQueryPlanRequest {
                id: table_id(&["workspace", "test_table"]),
                query: Box::new(query),
                verbose: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(!plan.is_empty());
    }

    #[tokio::test]
    async fn test_analyze_table_query_plan() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let plan = ns
            .analyze_table_query_plan(AnalyzeTableQueryPlanRequest {
                id: table_id(&["workspace", "test_table"]),
                filter: Some("age > 25".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(!plan.is_empty());
    }

    // ── Update table schema metadata ──────────────────────────────────

    #[tokio::test]
    async fn test_update_table_schema_metadata() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        let mut metadata = HashMap::new();
        metadata.insert("author".to_string(), "test".to_string());
        metadata.insert("version_label".to_string(), "initial".to_string());

        let resp = ns
            .update_table_schema_metadata(UpdateTableSchemaMetadataRequest {
                id: tid.clone(),
                metadata: Some(metadata),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.metadata.is_some());
        let updated = resp.metadata.unwrap();
        assert_eq!(updated.get("author").unwrap(), "test");
        assert_eq!(updated.get("version_label").unwrap(), "initial");
    }

    #[tokio::test]
    async fn test_update_table_schema_metadata_empty() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Empty metadata should succeed (no-op merge)
        let resp = ns
            .update_table_schema_metadata(UpdateTableSchemaMetadataRequest {
                id: tid,
                metadata: None,
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.metadata.is_some());
    }

    // ── Describe transaction ───────────────────────────────────────────

    #[tokio::test]
    async fn test_describe_transaction() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Insert to create a transaction at version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        // Describe by version number
        let resp = ns
            .describe_transaction(DescribeTransactionRequest {
                id: Some(vec![
                    "workspace".to_string(),
                    "test_table".to_string(),
                    "2".to_string(),
                ]),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.status, "SUCCEEDED");
        assert!(resp.properties.is_some());
        let props = resp.properties.unwrap();
        assert_eq!(props.get("version").unwrap(), "2");
    }

    #[tokio::test]
    async fn test_describe_transaction_not_found() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .describe_transaction(DescribeTransactionRequest {
                id: Some(vec![
                    "workspace".to_string(),
                    "test_table".to_string(),
                    "nonexistent-uuid".to_string(),
                ]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_describe_transaction_missing_id() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .describe_transaction(DescribeTransactionRequest {
                id: None,
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_describe_transaction_short_id() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .describe_transaction(DescribeTransactionRequest {
                id: Some(vec!["only_one_part".to_string()]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    // ── Describe table index stats ─────────────────────────────────────

    #[tokio::test]
    async fn test_describe_table_index_stats() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        ns.create_table_scalar_index(CreateTableIndexRequest {
            id: tid.clone(),
            column: "id".to_string(),
            index_type: "btree".to_string(),
            name: Some("id_stats_idx".to_string()),
            ..Default::default()
        })
        .await
        .unwrap();

        let resp = ns
            .describe_table_index_stats(DescribeTableIndexStatsRequest {
                id: tid,
                index_name: Some("id_stats_idx".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.num_indexed_rows.is_some());
    }

    #[tokio::test]
    async fn test_describe_table_index_stats_missing_name() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .describe_table_index_stats(DescribeTableIndexStatsRequest {
                id: table_id(&["workspace", "test_table"]),
                index_name: None,
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    // ── Query table with limit and offset ──────────────────────────────

    #[tokio::test]
    async fn test_query_table_with_limit() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .query_table(QueryTableRequest {
                id: table_id(&["workspace", "test_table"]),
                k: 2,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 2);
    }

    #[tokio::test]
    async fn test_query_table_with_offset() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .query_table(QueryTableRequest {
                id: table_id(&["workspace", "test_table"]),
                k: 10,
                offset: Some(1),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result), 2); // 3 total, skip 1
    }

    // ── List table versions descending / limit ─────────────────────────

    #[tokio::test]
    async fn test_list_table_versions_descending() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        let resp = ns
            .list_table_versions(ListTableVersionsRequest {
                id: tid.clone(),
                descending: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();

        assert!(resp.versions.len() >= 2);
        assert!(resp.versions[0].version >= resp.versions[1].version);
    }

    #[tokio::test]
    async fn test_list_table_versions_with_limit() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        let resp = ns
            .list_table_versions(ListTableVersionsRequest {
                id: tid,
                limit: Some(1),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.versions.len(), 1);
    }

    // ── Create table in nonexistent namespace ──────────────────────────

    #[tokio::test]
    async fn test_create_table_in_nonexistent_namespace() {
        let (ns, _dir) = create_test_namespace().await;

        let data = create_test_table_data();
        let result = ns
            .create_table(
                CreateTableRequest {
                    id: table_id(&["nonexistent_ns", "test_table"]),
                    ..Default::default()
                },
                Bytes::from(data),
            )
            .await;
        assert!(result.is_err());
    }

    // ── Count rows with empty predicate ────────────────────────────────

    #[tokio::test]
    async fn test_count_table_rows_empty_predicate() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        // Empty string predicate should count all rows (treated as no filter)
        let count = ns
            .count_table_rows(CountTableRowsRequest {
                id: table_id(&["workspace", "test_table"]),
                predicate: Some("".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count, 3);
    }

    // ── Table ID edge cases ────────────────────────────────────────────

    #[tokio::test]
    async fn test_table_operations_with_none_id() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .table_exists(TableExistsRequest {
                id: None,
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_table_operations_with_empty_id() {
        let (ns, _dir) = create_test_namespace().await;

        let result = ns
            .table_exists(TableExistsRequest {
                id: Some(vec![]),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    // ── Describe table without detailed metadata ───────────────────────

    #[tokio::test]
    async fn test_describe_table_without_detailed_metadata() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let resp = ns
            .describe_table(DescribeTableRequest {
                id: table_id(&["workspace", "test_table"]),
                load_detailed_metadata: Some(false),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.location.is_some());
        assert!(resp.schema.is_none()); // No schema when detailed=false
        assert!(resp.version.is_none()); // No version when detailed=false
    }

    // ── Rename table edge cases ────────────────────────────────────────

    #[tokio::test]
    async fn test_rename_table_empty_name() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .rename_table(RenameTableRequest {
                id: table_id(&["workspace", "test_table"]),
                new_table_name: "".to_string(),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    #[tokio::test]
    async fn test_rename_table_target_exists() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "table_a"]).await;
        create_test_table(&ns, &["workspace", "table_b"]).await;

        let result = ns
            .rename_table(RenameTableRequest {
                id: table_id(&["workspace", "table_a"]),
                new_table_name: "table_b".to_string(),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("already exists"));
    }

    // ── Scalar index type validation ───────────────────────────────────

    #[tokio::test]
    async fn test_create_scalar_index_rejects_vector_type() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .create_table_scalar_index(CreateTableIndexRequest {
                id: table_id(&["workspace", "test_table"]),
                column: "id".to_string(),
                index_type: "IVF_PQ".to_string(),
                name: Some("bad_idx".to_string()),
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(
            result
                .unwrap_err()
                .to_string()
                .contains("scalar index types")
        );
    }

    // ── Drop index missing name ────────────────────────────────────────

    #[tokio::test]
    async fn test_drop_table_index_missing_name() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .drop_table_index(DropTableIndexRequest {
                id: table_id(&["workspace", "test_table"]),
                index_name: None,
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
    }

    // ── Alter columns edge cases ───────────────────────────────────────

    #[tokio::test]
    async fn test_alter_table_add_columns_empty() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .alter_table_add_columns(AlterTableAddColumnsRequest {
                id: table_id(&["workspace", "test_table"]),
                new_columns: vec![],
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("At least one"));
    }

    #[tokio::test]
    async fn test_alter_table_drop_columns_empty() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .alter_table_drop_columns(AlterTableDropColumnsRequest {
                id: table_id(&["workspace", "test_table"]),
                columns: vec![],
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("At least one"));
    }

    #[tokio::test]
    async fn test_alter_table_alter_columns_empty() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let result = ns
            .alter_table_alter_columns(AlterTableAlterColumnsRequest {
                id: table_id(&["workspace", "test_table"]),
                alterations: vec![],
                ..Default::default()
            })
            .await;
        assert!(result.is_err());
        assert!(result.unwrap_err().to_string().contains("At least one"));
    }

    // ── Batch delete table versions ───────────────────────────────────

    #[tokio::test]
    async fn test_batch_delete_table_versions() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Create version 2 and 3
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        // Verify we have at least 3 versions
        let versions_before = ns
            .list_table_versions(ListTableVersionsRequest {
                id: tid.clone(),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(versions_before.versions.len() >= 3);

        // Delete version 1 only
        let resp = ns
            .batch_delete_table_versions(BatchDeleteTableVersionsRequest {
                id: tid.clone(),
                ranges: vec![lance_namespace::models::VersionRange::new(1, 1)],
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.deleted_count.is_some());
    }

    // ── Create table version ──────────────────────────────────────────

    #[tokio::test]
    async fn test_create_table_version() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Get the current manifest for version 1 to use as staging data
        let version_resp = ns
            .describe_table_version(DescribeTableVersionRequest {
                id: tid.clone(),
                version: Some(1),
                ..Default::default()
            })
            .await
            .unwrap();
        let manifest_path = version_resp.version.manifest_path.clone();

        // Create version 2 by copying manifest 1 as staging
        let resp = ns
            .create_table_version(CreateTableVersionRequest {
                id: tid.clone(),
                version: 2,
                manifest_path,
                naming_scheme: Some("V2".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(resp.version.is_some());
        let created = resp.version.unwrap();
        assert_eq!(created.version, 2);
        assert!(created.manifest_size.is_some());
    }

    // ── FTS / Inverted index creation ─────────────────────────────────

    #[tokio::test]
    async fn test_create_inverted_index() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        let resp = ns
            .create_table_index(CreateTableIndexRequest {
                id: tid.clone(),
                column: "name".to_string(),
                index_type: "INVERTED".to_string(),
                name: Some("name_fts_idx".to_string()),
                ..Default::default()
            })
            .await
            .unwrap();
        // Should have a transaction id from the index creation
        assert!(resp.transaction_id.is_some());

        // Verify the index appears in the list
        let indices = ns
            .list_table_indices(ListTableIndicesRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert!(
            indices
                .indexes
                .iter()
                .any(|i| i.columns.contains(&"name".to_string()))
        );
    }

    // ── Describe table with specific version ──────────────────────────

    #[tokio::test]
    async fn test_describe_table_at_specific_version() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Insert to create version 2 with 5 rows
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        // Describe at version 1
        let resp_v1 = ns
            .describe_table(DescribeTableRequest {
                id: tid.clone(),
                version: Some(1),
                load_detailed_metadata: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp_v1.version, Some(1));

        // Describe at latest (version 2)
        let resp_v2 = ns
            .describe_table(DescribeTableRequest {
                id: tid,
                load_detailed_metadata: Some(true),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp_v2.version, Some(2));
    }

    // ── Query table at specific version ───────────────────────────────

    #[tokio::test]
    async fn test_query_table_at_specific_version() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Insert to create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        // Query at version 1 — should return 3 rows
        let result_v1 = ns
            .query_table(QueryTableRequest {
                id: tid.clone(),
                version: Some(1),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result_v1), 3);

        // Query at latest (version 2) — should return 5 rows
        let result_v2 = ns
            .query_table(QueryTableRequest {
                id: tid,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(count_rows_in_ipc(&result_v2), 5);
    }

    // ── List table indices pagination ─────────────────────────────────

    #[tokio::test]
    async fn test_list_table_indices_pagination() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Create two scalar indices
        ns.create_table_scalar_index(CreateTableIndexRequest {
            id: tid.clone(),
            column: "id".to_string(),
            index_type: "btree".to_string(),
            name: Some("idx_id".to_string()),
            ..Default::default()
        })
        .await
        .unwrap();

        ns.create_table_scalar_index(CreateTableIndexRequest {
            id: tid.clone(),
            column: "name".to_string(),
            index_type: "btree".to_string(),
            name: Some("idx_name".to_string()),
            ..Default::default()
        })
        .await
        .unwrap();

        // Page 1: limit 1
        let resp = ns
            .list_table_indices(ListTableIndicesRequest {
                id: tid.clone(),
                limit: Some(1),
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.indexes.len(), 1);
        assert!(resp.page_token.is_some());

        // Page 2: use page token
        let resp2 = ns
            .list_table_indices(ListTableIndicesRequest {
                id: tid,
                page_token: resp.page_token,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp2.indexes.len(), 1);
    }

    // ── Describe table version without explicit version (latest) ──────

    #[tokio::test]
    async fn test_describe_table_version_latest() {
        let (ns, _dir) = create_test_namespace().await;
        create_namespace(&ns, &["workspace"]).await;
        create_test_table(&ns, &["workspace", "test_table"]).await;

        let tid = table_id(&["workspace", "test_table"]);

        // Insert to create version 2
        ns.insert_into_table(
            InsertIntoTableRequest {
                id: tid.clone(),
                ..Default::default()
            },
            Bytes::from(create_additional_data()),
        )
        .await
        .unwrap();

        // Describe without specifying version — should return latest (2)
        let resp = ns
            .describe_table_version(DescribeTableVersionRequest {
                id: tid,
                version: None,
                ..Default::default()
            })
            .await
            .unwrap();
        assert_eq!(resp.version.version, 2);
    }
}
