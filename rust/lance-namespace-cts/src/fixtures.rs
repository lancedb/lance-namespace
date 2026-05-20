// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

//! `Fixtures` —— per-test bookkeeping.
//!
//! Each generated test owns one `Fixtures` instance which:
//!   * Holds the active `ContractCaller`.
//!   * Mints unique-but-readable strings for `{{ns_*}}` template
//!     variables (UUID-suffixed; collisions across tests are
//!     impossible).
//!   * Records every namespace it materialises so `tear_down` can drop
//!     them in reverse-creation order via real API calls — never
//!     touching the on-disk SUT directly.
//!
//! Even though `InProcessDirectoryCaller` is built per-test with a
//! fresh `TempDir` (so disk-level isolation is automatic), we still
//! perform the `tear_down` reverse drop because:
//!   * It exercises the destructive path of the SUT (catching e.g.
//!     `DropNamespace` regressions that the case body itself wouldn't
//!     hit).
//!   * The same `Fixtures` will be used unchanged when the next-stage
//!     `HttpReferenceCaller` lands and the SUT is *shared* across
//!     tests — at which point reverse-drop is mandatory for isolation.

use std::sync::Arc;

use bytes::Bytes;
use lance_namespace::models::{
    CreateNamespaceRequest, CreateTableRequest, DropNamespaceRequest, DropTableRequest,
    NamespaceExistsRequest, TableExistsRequest,
};
use uuid::Uuid;

use crate::caller::ContractCaller;

/// Per-test bookkeeping.  Construct one with `Fixtures::new(caller)` and
/// drop it (or call `tear_down().await`) at the end of the test.
pub struct Fixtures {
    caller: Arc<dyn ContractCaller>,
    /// Namespaces created via this fixture, in creation order.  Drained
    /// in reverse during `tear_down`.
    created_namespaces: Vec<Vec<String>>,
    /// Tables created via this fixture, in creation order.  Drained
    /// before namespaces during `tear_down`.
    created_tables: Vec<Vec<String>>,
}

impl Fixtures {
    pub fn new(caller: Arc<dyn ContractCaller>) -> Self {
        Self {
            caller,
            created_namespaces: Vec::new(),
            created_tables: Vec::new(),
        }
    }

    /// Borrow the live caller for ad-hoc operations inside a test body.
    pub fn api(&self) -> &Arc<dyn ContractCaller> {
        &self.caller
    }

    /// Return a unique short string suitable for use as a single
    /// namespace path component.  The `cts_` prefix makes the
    /// originating fixture obvious in logs and on disk.
    pub fn unique_name(&self) -> String {
        // `simple()` strips the dashes so the name stays a valid path
        // component on every supported file system.
        format!("cts_{}", Uuid::new_v4().simple())
    }

    /// Convenience for the common single-component case: returns a
    /// `Vec<String>` of length 1 ready to plug into a request `id`.
    pub fn unique_namespace(&self) -> String {
        self.unique_name()
    }

    // ─── Arrow IPC body helpers ──────────────────────────
    //
    // The `LanceNamespace` trait surfaces a few operations whose request
    // bodies are *not* JSON but Arrow IPC streams:
    //
    //   * `create_table(_, request_data: Bytes)`
    //   * `insert_into_table(_, request_data: Bytes)`
    //   * `merge_insert_into_table(_, request_data: Bytes)`
    //   * `query_table(_) -> Result<Bytes>` (response side)
    //
    // The SUT decodes these via `arrow::ipc::reader::StreamReader`, so
    // the helpers below produce that exact wire format. We deliberately
    // keep the schema small and uniform (single nullable `int32` column
    // named `id`) so cases stay focused on the namespace contract,
    // not on Arrow type plumbing.

    /// Schema used by every `arrow_ipc_*` helper:
    /// `Schema { fields: [Field { name: "id", DataType::Int32, nullable: true }] }`.
    pub fn ipc_schema() -> Arc<arrow::datatypes::Schema> {
        Arc::new(arrow::datatypes::Schema::new(vec![
            arrow::datatypes::Field::new("id", arrow::datatypes::DataType::Int32, true),
        ]))
    }

    /// Encode an Arrow IPC stream containing zero rows under
    /// [`Self::ipc_schema`]. Suitable for `CreateTable` happy paths
    /// where the test only cares about the table existing afterward.
    pub fn arrow_ipc_empty() -> Bytes {
        Self::arrow_ipc_int32_rows(&[])
    }

    /// Encode an Arrow IPC stream containing a single batch of `int32`
    /// values under [`Self::ipc_schema`].
    pub fn arrow_ipc_int32_rows(values: &[i32]) -> Bytes {
        use arrow::array::{ArrayRef, Int32Array};
        use arrow::ipc::writer::StreamWriter;
        use arrow::record_batch::RecordBatch;

        let schema = Self::ipc_schema();
        let array: ArrayRef = Arc::new(Int32Array::from(values.to_vec()));
        let batch = RecordBatch::try_new(schema.clone(), vec![array])
            .expect("RecordBatch::try_new for ipc_schema int32 array");

        let mut buf: Vec<u8> = Vec::new();
        {
            let mut writer =
                StreamWriter::try_new(&mut buf, schema.as_ref()).expect("StreamWriter::try_new");
            writer
                .write(&batch)
                .expect("StreamWriter::write empty/int32 batch");
            writer.finish().expect("StreamWriter::finish");
        }
        Bytes::from(buf)
    }

    /// Materialise a real on-disk Lance dataset via the public
    /// `CreateTable` API and remember it for `tear_down`. The table is
    /// created with the [`Self::ipc_schema`] empty stream so subsequent
    /// describe/exists/drop calls go through the same v1+manifest path
    /// `dir.rs` exercises in production.
    pub async fn create_table_empty(&mut self, id: Vec<String>) {
        let req = CreateTableRequest {
            id: Some(id.clone()),
            mode: Some("create".to_string()),
            ..Default::default()
        };
        self.caller
            .create_table(req, Self::arrow_ipc_empty())
            .await
            .unwrap_or_else(|e| panic!("fixture: create_table({id:?}) failed: {e}"));
        self.created_tables.push(id);
    }

    /// Materialise a namespace via the public API and remember it for
    /// later teardown.  Panics on failure (set-up bugs should not
    /// silently mask the case under test).
    pub async fn create_namespace(&mut self, id: Vec<String>) {
        let req = CreateNamespaceRequest {
            id: Some(id.clone()),
            mode: Some("create".to_string()),
            properties: None,
            ..Default::default()
        };
        self.caller
            .create_namespace(req)
            .await
            .unwrap_or_else(|e| panic!("fixture: create_namespace({id:?}) failed: {e}"));
        self.created_namespaces.push(id);
    }

    /// Drop every namespace created by this fixture, in reverse order.
    /// Tolerant of `NamespaceNotFound` because the case body might have
    /// already dropped it.
    pub async fn tear_down(&mut self) {
        // Tables first, namespaces second — the v1 directory layout
        // stores `<table>.lance/` *under* the namespace path, so the
        // reverse order matches what an HTTP reference SUT would see.
        while let Some(id) = self.created_tables.pop() {
            let req = DropTableRequest {
                id: Some(id.clone()),
                ..Default::default()
            };
            if let Err(err) = self.caller.drop_table(req).await {
                let code = crate::assertions::error_code_of(&err);
                // 4 = TableNotFound (already dropped by the case body),
                // 18 = Internal wrapping a NotFound from the object
                // store on the v1 path.  Both are tolerable here.
                if code != Some(4) && code != Some(18) {
                    eprintln!(
                        "fixture: tear_down: drop_table({id:?}) returned \
                         unexpected error (code = {code:?}): {err}"
                    );
                }
            }
        }
        while let Some(id) = self.created_namespaces.pop() {
            let req = DropNamespaceRequest {
                id: Some(id.clone()),
                ..Default::default()
            };
            // Best effort: a stale entry (already dropped by the case)
            // is fine; any *other* failure is a real bug we want to
            // surface but not in a way that hides the original case
            // failure.
            if let Err(err) = self.caller.drop_namespace(req).await {
                let code = crate::assertions::error_code_of(&err);
                if code != Some(1) {
                    eprintln!(
                        "fixture: tear_down: drop_namespace({id:?}) returned \
                         unexpected error (code = {code:?}): {err}"
                    );
                }
            }
        }
    }

    /// Sugar to assert that a namespace currently exists (via the public
    /// API).  Used inline in cases like `create_then_describe_succeeds`.
    pub async fn assert_namespace_exists(&self, id: Vec<String>) {
        let req = NamespaceExistsRequest {
            id: Some(id.clone()),
            ..Default::default()
        };
        if let Err(err) = self.caller.namespace_exists(req).await {
            panic!("fixture: expected namespace {id:?} to exist, got: {err}");
        }
    }

    /// Sugar to assert that a namespace is absent (via the public API).
    /// Will pass either when the impl returns `NamespaceNotFound (1)` or
    /// when it surfaces a not-supported flavour we can't distinguish
    /// from absence.
    pub async fn assert_namespace_absent(&self, id: Vec<String>) {
        let req = NamespaceExistsRequest {
            id: Some(id.clone()),
            ..Default::default()
        };
        match self.caller.namespace_exists(req).await {
            Ok(()) => panic!("fixture: expected namespace {id:?} to be absent"),
            Err(err) => {
                let code = crate::assertions::error_code_of(&err);
                if code != Some(1) {
                    panic!(
                        "fixture: expected NamespaceNotFound (1), got code \
                         {code:?}: {err}"
                    );
                }
            }
        }
    }

    /// Sugar to assert that a table currently exists (via the public
    /// `TableExists` API).
    pub async fn assert_table_exists(&self, id: Vec<String>) {
        let req = TableExistsRequest {
            id: Some(id.clone()),
            ..Default::default()
        };
        if let Err(err) = self.caller.table_exists(req).await {
            panic!("fixture: expected table {id:?} to exist, got: {err}");
        }
    }

    /// Sugar to assert that a table is absent. Accepts both
    /// `TableNotFound (4)` (canonical) and `Internal (18)` (the v1
    /// directory path's wrapping of object-store NotFound).
    pub async fn assert_table_absent(&self, id: Vec<String>) {
        let req = TableExistsRequest {
            id: Some(id.clone()),
            ..Default::default()
        };
        match self.caller.table_exists(req).await {
            Ok(()) => panic!("fixture: expected table {id:?} to be absent"),
            Err(err) => {
                let code = crate::assertions::error_code_of(&err);
                if code != Some(4) && code != Some(18) {
                    panic!(
                        "fixture: expected TableNotFound (4), got code \
                         {code:?}: {err}"
                    );
                }
            }
        }
    }
}
