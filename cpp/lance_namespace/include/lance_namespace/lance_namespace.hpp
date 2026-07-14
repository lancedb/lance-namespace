// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * @file lance_namespace.hpp
 * @brief C++20 RAII wrapper for the Lance Namespace C API.
 *
 * All operations are exposed as strongly-typed methods that accept and return
 * generated model objects (consistent with the Python SDK's LanceNamespace
 * interface). query_table() returns std::vector<uint8_t> (raw Arrow IPC bytes).
 *
 * @code
 *   #include <lance_namespace/lance_namespace.hpp>
 *
 *   lance::namespace_::Properties props{{"uri", "http://localhost:2333"}};
 *   auto ns = lance::namespace_::connect("rest", props);
 *
 *   lance_namespace::models::ListTablesRequest req;
 *   req.setId({"myns"});
 *   auto resp = ns.list_tables(req);  // resp is ListTablesResponse
 * @endcode
 *
 * Generate the REST client once with:
 *   make gen-rest-client   (from the cpp/ directory)
 */

#ifndef LANCE_NAMESPACE_HPP
#define LANCE_NAMESPACE_HPP

#include <lance_namespace/lance_namespace.h>
#include <lance_namespace/models.hpp>

#include <cpprest/details/basic_types.h>
#include <cpprest/json.h>
#include <cstdint>
#include <stdexcept>
#include <string>
#include <unordered_map>
#include <utility>
#include <vector>

namespace lance {
namespace namespace_ {

/* ─── Error ──────────────────────────────────────────────────────────────── */

/** Base exception for all Lance Namespace errors. */
class Error : public std::runtime_error {
 public:
  explicit Error(std::string msg, int code = LANCE_NS_ERR_INTERNAL)
      : std::runtime_error(std::move(msg)), code_(code) {}

  /** Numeric error code (matches lance_namespace_error_code_t). */
  [[nodiscard]] int code() const noexcept { return code_; }

 private:
  int code_;
};

[[noreturn]] inline void throw_last_error() {
  int code = lance_namespace_last_error_code();
  const char *msg = lance_namespace_last_error_message();
  throw Error(msg ? msg : "(no message)", code);
}

/* ─── Properties ─────────────────────────────────────────────────────────── */

/** Build a lance_namespace_properties_t from an initializer-list or map. */
class Properties {
 public:
  Properties() = default;

  Properties(std::initializer_list<std::pair<std::string, std::string>> init) {
    keys_.reserve(init.size());
    values_.reserve(init.size());
    for (auto &[k, v] : init) {
      keys_.push_back(k);
      values_.push_back(v);
    }
    rebuild();
  }

  explicit Properties(const std::unordered_map<std::string, std::string> &map) {
    keys_.reserve(map.size());
    values_.reserve(map.size());
    for (auto &[k, v] : map) {
      keys_.push_back(k);
      values_.push_back(v);
    }
    rebuild();
  }

  void set(std::string key, std::string value) {
    keys_.push_back(std::move(key));
    values_.push_back(std::move(value));
    rebuild();
  }

  [[nodiscard]] const lance_namespace_properties_t *c_props() const noexcept { return &props_; }

 private:
  void rebuild() {
    items_.clear();
    items_.reserve(keys_.size());
    for (size_t i = 0; i < keys_.size(); ++i)
      items_.push_back({keys_[i].c_str(), values_[i].c_str()});
    props_ = {items_.data(), items_.size()};
  }

  std::vector<std::string> keys_;
  std::vector<std::string> values_;
  std::vector<lance_namespace_property_t> items_;
  lance_namespace_properties_t props_{nullptr, 0};
};

/* ─── Namespace handle ───────────────────────────────────────────────────── */

/**
 * RAII owner of a lance_namespace_t*.  Movable, not copyable.
 *
 * All methods throw lance::namespace_::Error on failure.
 * Request / response payloads are UTF-8 JSON strings.
 */
class Namespace {
 public:
  Namespace() = default;
  explicit Namespace(lance_namespace_t *raw) : raw_(raw) {
    if (!raw_)
      throw_last_error();
  }

  ~Namespace() { reset(); }
  Namespace(const Namespace &) = delete;
  Namespace &operator=(const Namespace &) = delete;
  Namespace(Namespace &&o) noexcept : raw_(std::exchange(o.raw_, nullptr)) {}
  Namespace &operator=(Namespace &&o) noexcept {
    if (this != &o) {
      reset();
      raw_ = std::exchange(o.raw_, nullptr);
    }
    return *this;
  }

  [[nodiscard]] explicit operator bool() const noexcept { return raw_ != nullptr; }
  [[nodiscard]] lance_namespace_t *get() const noexcept { return raw_; }

  void reset() noexcept {
    if (raw_) {
      lance_namespace_destroy(raw_);
      raw_ = nullptr;
    }
  }

  /* ── Identity ── */

  [[nodiscard]] std::string id() const { return call_str(lance_namespace_id(raw_), false); }

  /* ── Namespace operations ── */

  lance_namespace::models::ListNamespacesResponse list_namespaces(
      const lance_namespace::models::ListNamespacesRequest &req) const {
    return call_typed<lance_namespace::models::ListNamespacesResponse>(
        lance_namespace_list_namespaces(raw_, c(req)));
  }
  lance_namespace::models::DescribeNamespaceResponse describe_namespace(
      const lance_namespace::models::DescribeNamespaceRequest &req) const {
    return call_typed<lance_namespace::models::DescribeNamespaceResponse>(
        lance_namespace_describe_namespace(raw_, c(req)));
  }
  lance_namespace::models::CreateNamespaceResponse create_namespace(
      const lance_namespace::models::CreateNamespaceRequest &req) const {
    return call_typed<lance_namespace::models::CreateNamespaceResponse>(
        lance_namespace_create_namespace(raw_, c(req)));
  }
  lance_namespace::models::DropNamespaceResponse drop_namespace(
      const lance_namespace::models::DropNamespaceRequest &req) const {
    return call_typed<lance_namespace::models::DropNamespaceResponse>(
        lance_namespace_drop_namespace(raw_, c(req)));
  }
  /**
   * Check whether a namespace exists.
   * Throws Error with NAMESPACE_NOT_FOUND if the namespace does not exist.
   */
  void namespace_exists(const lance_namespace::models::NamespaceExistsRequest &req) const {
    int r = lance_namespace_namespace_exists(raw_, c(req));
    if (r < 0)
      throw_last_error();
  }

  /* ── Table operations ── */

  lance_namespace::models::ListTablesResponse list_tables(
      const lance_namespace::models::ListTablesRequest &req) const {
    return call_typed<lance_namespace::models::ListTablesResponse>(
        lance_namespace_list_tables(raw_, c(req)));
  }
  lance_namespace::models::ListTablesResponse list_all_tables(
      const lance_namespace::models::ListTablesRequest &req) const {
    return call_typed<lance_namespace::models::ListTablesResponse>(
        lance_namespace_list_all_tables(raw_, c(req)));
  }
  lance_namespace::models::DescribeTableResponse describe_table(
      const lance_namespace::models::DescribeTableRequest &req) const {
    return call_typed<lance_namespace::models::DescribeTableResponse>(
        lance_namespace_describe_table(raw_, c(req)));
  }
  lance_namespace::models::RegisterTableResponse register_table(
      const lance_namespace::models::RegisterTableRequest &req) const {
    return call_typed<lance_namespace::models::RegisterTableResponse>(
        lance_namespace_register_table(raw_, c(req)));
  }
  /**
   * Check whether a table exists.
   * Throws Error with TABLE_NOT_FOUND if the table does not exist.
   */
  void table_exists(const lance_namespace::models::TableExistsRequest &req) const {
    int r = lance_namespace_table_exists(raw_, c(req));
    if (r < 0)
      throw_last_error();
  }
  lance_namespace::models::DropTableResponse drop_table(
      const lance_namespace::models::DropTableRequest &req) const {
    return call_typed<lance_namespace::models::DropTableResponse>(
        lance_namespace_drop_table(raw_, c(req)));
  }
  lance_namespace::models::DeregisterTableResponse deregister_table(
      const lance_namespace::models::DeregisterTableRequest &req) const {
    return call_typed<lance_namespace::models::DeregisterTableResponse>(
        lance_namespace_deregister_table(raw_, c(req)));
  }
  lance_namespace::models::RenameTableResponse rename_table(
      const lance_namespace::models::RenameTableRequest &req) const {
    return call_typed<lance_namespace::models::RenameTableResponse>(
        lance_namespace_rename_table(raw_, c(req)));
  }
  lance_namespace::models::RestoreTableResponse restore_table(
      const lance_namespace::models::RestoreTableRequest &req) const {
    return call_typed<lance_namespace::models::RestoreTableResponse>(
        lance_namespace_restore_table(raw_, c(req)));
  }

  /* ── Table data ── */

  int64_t count_table_rows(const lance_namespace::models::CountTableRowsRequest &req) const {
    int64_t r = lance_namespace_count_table_rows(raw_, c(req));
    if (r < 0)
      throw_last_error();
    return r;
  }
  lance_namespace::models::CreateTableResponse create_table(
      const lance_namespace::models::CreateTableRequest &req) const {
    return call_typed<lance_namespace::models::CreateTableResponse>(
        lance_namespace_create_table(raw_, c(req)));
  }
  lance_namespace::models::DeclareTableResponse declare_table(
      const lance_namespace::models::DeclareTableRequest &req) const {
    return call_typed<lance_namespace::models::DeclareTableResponse>(
        lance_namespace_declare_table(raw_, c(req)));
  }
  lance_namespace::models::InsertIntoTableResponse insert_into_table(
      const lance_namespace::models::InsertIntoTableRequest &req) const {
    return call_typed<lance_namespace::models::InsertIntoTableResponse>(
        lance_namespace_insert_into_table(raw_, c(req)));
  }
  lance_namespace::models::MergeInsertIntoTableResponse merge_insert_into_table(
      const lance_namespace::models::MergeInsertIntoTableRequest &req) const {
    return call_typed<lance_namespace::models::MergeInsertIntoTableResponse>(
        lance_namespace_merge_insert_into_table(raw_, c(req)));
  }
  lance_namespace::models::UpdateTableResponse update_table(
      const lance_namespace::models::UpdateTableRequest &req) const {
    return call_typed<lance_namespace::models::UpdateTableResponse>(
        lance_namespace_update_table(raw_, c(req)));
  }
  lance_namespace::models::DeleteFromTableResponse delete_from_table(
      const lance_namespace::models::DeleteFromTableRequest &req) const {
    return call_typed<lance_namespace::models::DeleteFromTableResponse>(
        lance_namespace_delete_from_table(raw_, c(req)));
  }
  /** Returns raw Arrow IPC bytes. */
  std::vector<uint8_t> query_table(const lance_namespace::models::QueryTableRequest &req) const {
    lance_namespace_bytes_t *b = lance_namespace_query_table(raw_, c(req));
    if (!b)
      throw_last_error();
    std::vector<uint8_t> v(b->data, b->data + b->length);
    lance_namespace_free_bytes(b);
    return v;
  }

  /* ── Table schema / metadata ── */

  lance_namespace::models::GetTableStatsResponse get_table_stats(
      const lance_namespace::models::GetTableStatsRequest &req) const {
    return call_typed<lance_namespace::models::GetTableStatsResponse>(
        lance_namespace_get_table_stats(raw_, c(req)));
  }
  lance_namespace::models::UpdateTableSchemaMetadataResponse update_table_schema_metadata(
      const lance_namespace::models::UpdateTableSchemaMetadataRequest &req) const {
    return call_typed<lance_namespace::models::UpdateTableSchemaMetadataResponse>(
        lance_namespace_update_table_schema_metadata(raw_, c(req)));
  }
  lance_namespace::models::UpdateFieldMetadataResponse update_field_metadata(
      const lance_namespace::models::UpdateFieldMetadataRequest &req) const {
    return call_typed<lance_namespace::models::UpdateFieldMetadataResponse>(
        lance_namespace_update_field_metadata(raw_, c(req)));
  }
  lance_namespace::models::ExplainTableQueryPlanResponse explain_table_query_plan(
      const lance_namespace::models::ExplainTableQueryPlanRequest &req) const {
    return call_typed<lance_namespace::models::ExplainTableQueryPlanResponse>(
        lance_namespace_explain_table_query_plan(raw_, c(req)));
  }
  lance_namespace::models::AnalyzeTableQueryPlanResponse analyze_table_query_plan(
      const lance_namespace::models::AnalyzeTableQueryPlanRequest &req) const {
    return call_typed<lance_namespace::models::AnalyzeTableQueryPlanResponse>(
        lance_namespace_analyze_table_query_plan(raw_, c(req)));
  }

  /* ── Table alter ── */

  lance_namespace::models::AlterTableAddColumnsResponse alter_table_add_columns(
      const lance_namespace::models::AlterTableAddColumnsRequest &req) const {
    return call_typed<lance_namespace::models::AlterTableAddColumnsResponse>(
        lance_namespace_alter_table_add_columns(raw_, c(req)));
  }
  lance_namespace::models::AlterTableAlterColumnsResponse alter_table_alter_columns(
      const lance_namespace::models::AlterTableAlterColumnsRequest &req) const {
    return call_typed<lance_namespace::models::AlterTableAlterColumnsResponse>(
        lance_namespace_alter_table_alter_columns(raw_, c(req)));
  }
  lance_namespace::models::AlterTableBackfillColumnsResponse alter_table_backfill_columns(
      const lance_namespace::models::AlterTableBackfillColumnsRequest &req) const {
    return call_typed<lance_namespace::models::AlterTableBackfillColumnsResponse>(
        lance_namespace_alter_table_backfill_columns(raw_, c(req)));
  }
  lance_namespace::models::AlterTableDropColumnsResponse alter_table_drop_columns(
      const lance_namespace::models::AlterTableDropColumnsRequest &req) const {
    return call_typed<lance_namespace::models::AlterTableDropColumnsResponse>(
        lance_namespace_alter_table_drop_columns(raw_, c(req)));
  }

  /* ── Table index ── */

  lance_namespace::models::CreateTableIndexResponse create_table_index(
      const lance_namespace::models::CreateTableIndexRequest &req) const {
    return call_typed<lance_namespace::models::CreateTableIndexResponse>(
        lance_namespace_create_table_index(raw_, c(req)));
  }
  lance_namespace::models::CreateTableScalarIndexResponse create_table_scalar_index(
      const lance_namespace::models::CreateTableIndexRequest &req) const {
    return call_typed<lance_namespace::models::CreateTableScalarIndexResponse>(
        lance_namespace_create_table_scalar_index(raw_, c(req)));
  }
  lance_namespace::models::ListTableIndicesResponse list_table_indices(
      const lance_namespace::models::ListTableIndicesRequest &req) const {
    return call_typed<lance_namespace::models::ListTableIndicesResponse>(
        lance_namespace_list_table_indices(raw_, c(req)));
  }
  lance_namespace::models::DescribeTableIndexStatsResponse describe_table_index_stats(
      const lance_namespace::models::DescribeTableIndexStatsRequest &req) const {
    return call_typed<lance_namespace::models::DescribeTableIndexStatsResponse>(
        lance_namespace_describe_table_index_stats(raw_, c(req)));
  }
  lance_namespace::models::DropTableIndexResponse drop_table_index(
      const lance_namespace::models::DropTableIndexRequest &req) const {
    return call_typed<lance_namespace::models::DropTableIndexResponse>(
        lance_namespace_drop_table_index(raw_, c(req)));
  }

  /* ── Table versions ── */

  lance_namespace::models::ListTableVersionsResponse list_table_versions(
      const lance_namespace::models::ListTableVersionsRequest &req) const {
    return call_typed<lance_namespace::models::ListTableVersionsResponse>(
        lance_namespace_list_table_versions(raw_, c(req)));
  }
  lance_namespace::models::CreateTableVersionResponse create_table_version(
      const lance_namespace::models::CreateTableVersionRequest &req) const {
    return call_typed<lance_namespace::models::CreateTableVersionResponse>(
        lance_namespace_create_table_version(raw_, c(req)));
  }
  lance_namespace::models::DescribeTableVersionResponse describe_table_version(
      const lance_namespace::models::DescribeTableVersionRequest &req) const {
    return call_typed<lance_namespace::models::DescribeTableVersionResponse>(
        lance_namespace_describe_table_version(raw_, c(req)));
  }
  lance_namespace::models::BatchDeleteTableVersionsResponse batch_delete_table_versions(
      const lance_namespace::models::BatchDeleteTableVersionsRequest &req) const {
    return call_typed<lance_namespace::models::BatchDeleteTableVersionsResponse>(
        lance_namespace_batch_delete_table_versions(raw_, c(req)));
  }
  lance_namespace::models::BatchCreateTableVersionsResponse batch_create_table_versions(
      const lance_namespace::models::BatchCreateTableVersionsRequest &req) const {
    return call_typed<lance_namespace::models::BatchCreateTableVersionsResponse>(
        lance_namespace_batch_create_table_versions(raw_, c(req)));
  }
  lance_namespace::models::BatchCommitTablesResponse batch_commit_tables(
      const lance_namespace::models::BatchCommitTablesRequest &req) const {
    return call_typed<lance_namespace::models::BatchCommitTablesResponse>(
        lance_namespace_batch_commit_tables(raw_, c(req)));
  }

  /* ── Materialized views ── */

  lance_namespace::models::CreateMaterializedViewResponse create_materialized_view(
      const lance_namespace::models::CreateMaterializedViewRequest &req) const {
    return call_typed<lance_namespace::models::CreateMaterializedViewResponse>(
        lance_namespace_create_materialized_view(raw_, c(req)));
  }
  lance_namespace::models::RefreshMaterializedViewResponse refresh_materialized_view(
      const lance_namespace::models::RefreshMaterializedViewRequest &req) const {
    return call_typed<lance_namespace::models::RefreshMaterializedViewResponse>(
        lance_namespace_refresh_materialized_view(raw_, c(req)));
  }

  /* ── Tags ── */

  lance_namespace::models::ListTableTagsResponse list_table_tags(
      const lance_namespace::models::ListTableTagsRequest &req) const {
    return call_typed<lance_namespace::models::ListTableTagsResponse>(
        lance_namespace_list_table_tags(raw_, c(req)));
  }
  lance_namespace::models::GetTableTagVersionResponse get_table_tag_version(
      const lance_namespace::models::GetTableTagVersionRequest &req) const {
    return call_typed<lance_namespace::models::GetTableTagVersionResponse>(
        lance_namespace_get_table_tag_version(raw_, c(req)));
  }
  lance_namespace::models::CreateTableTagResponse create_table_tag(
      const lance_namespace::models::CreateTableTagRequest &req) const {
    return call_typed<lance_namespace::models::CreateTableTagResponse>(
        lance_namespace_create_table_tag(raw_, c(req)));
  }
  lance_namespace::models::DeleteTableTagResponse delete_table_tag(
      const lance_namespace::models::DeleteTableTagRequest &req) const {
    return call_typed<lance_namespace::models::DeleteTableTagResponse>(
        lance_namespace_delete_table_tag(raw_, c(req)));
  }
  lance_namespace::models::UpdateTableTagResponse update_table_tag(
      const lance_namespace::models::UpdateTableTagRequest &req) const {
    return call_typed<lance_namespace::models::UpdateTableTagResponse>(
        lance_namespace_update_table_tag(raw_, c(req)));
  }

  /* ── Branches ── */

  lance_namespace::models::CreateTableBranchResponse create_table_branch(
      const lance_namespace::models::CreateTableBranchRequest &req) const {
    return call_typed<lance_namespace::models::CreateTableBranchResponse>(
        lance_namespace_create_table_branch(raw_, c(req)));
  }
  lance_namespace::models::ListTableBranchesResponse list_table_branches(
      const lance_namespace::models::ListTableBranchesRequest &req) const {
    return call_typed<lance_namespace::models::ListTableBranchesResponse>(
        lance_namespace_list_table_branches(raw_, c(req)));
  }
  lance_namespace::models::DeleteTableBranchResponse delete_table_branch(
      const lance_namespace::models::DeleteTableBranchRequest &req) const {
    return call_typed<lance_namespace::models::DeleteTableBranchResponse>(
        lance_namespace_delete_table_branch(raw_, c(req)));
  }

  /* ── Transactions ── */

  lance_namespace::models::DescribeTransactionResponse describe_transaction(
      const lance_namespace::models::DescribeTransactionRequest &req) const {
    return call_typed<lance_namespace::models::DescribeTransactionResponse>(
        lance_namespace_describe_transaction(raw_, c(req)));
  }
  lance_namespace::models::AlterTransactionResponse alter_transaction(
      const lance_namespace::models::AlterTransactionRequest &req) const {
    return call_typed<lance_namespace::models::AlterTransactionResponse>(
        lance_namespace_alter_transaction(raw_, c(req)));
  }

 private:
  lance_namespace_t *raw_{nullptr};

  // Serialize a model to a temporary UTF-8 JSON string and return its c_str().
  // The returned pointer is valid only for the lifetime of the std::string returned.
  static std::string serialize(const org::openapitools::client::model::ModelBase &model) {
    return utility::conversions::to_utf8string(model.toJson().serialize());
  }

  // Serialize req to JSON and return its c_str() for passing to the C API.
  // Uses a thread-local buffer so the pointer outlives the call_typed invocation.
  static const char *c(const org::openapitools::client::model::ModelBase &req) {
    thread_local std::string buf;
    buf = serialize(req);
    return buf.c_str();
  }

  // Adopt an owned char* JSON response; deserialize into T and free.
  template <typename T>
  static T call_typed(char *raw_json) {
    if (!raw_json)
      throw_last_error();
    std::string s(raw_json);
    lance_namespace_free_string(raw_json);
    T result;
    result.fromJson(web::json::value::parse(utility::conversions::to_string_t(s)));
    return result;
  }

  // For lance_namespace_id: pointer owned by handle, do not free.
  static std::string call_str(const char *raw, bool /*owned*/) {
    if (!raw)
      throw_last_error();
    return raw;
  }
};

/* ─── connect() / register_impl() ───────────────────────────────────────── */

/**
 * Connect to a Lance namespace backend.
 *
 * @param impl_type  "dir", "rest", or a custom registered type.
 * @param properties Configuration key-value pairs.
 * @throws Error on failure.
 */
inline Namespace connect(std::string_view impl_type, const Properties &properties = {}) {
  std::string t(impl_type);
  return Namespace(lance_namespace_connect(t.c_str(), properties.c_props()));
}

/** Register a custom namespace factory under @p impl_type. */
inline void register_impl(std::string_view impl_type, lance_namespace_factory_fn factory) {
  std::string t(impl_type);
  lance_namespace_register(t.c_str(), factory);
}

}  // namespace namespace_
}  // namespace lance

#endif /* LANCE_NAMESPACE_HPP */
