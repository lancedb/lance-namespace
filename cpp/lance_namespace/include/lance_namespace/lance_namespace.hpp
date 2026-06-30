// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * @file lance_namespace.hpp
 * @brief C++20 RAII wrapper for the Lance Namespace C API.
 *
 * All 55 namespace/table operations are exposed as methods that accept
 * and return std::string (UTF-8 JSON).  query_table() returns
 * std::vector<uint8_t> (raw Arrow IPC bytes).
 *
 * REST client model types live in a separate header in this library.
 * Include it manually when you need typed request/response objects:
 *
 * @code
 *   #include <lance_namespace/lance_namespace.hpp>
 *   #include <lance_namespace/models.hpp>  // optional, for typed models
 *
 *   // Typed model objects:
 *   lance_namespace::models::CreateNamespaceRequest req;
 *   req.setNamespace({"myns"});
 *
 *   lance::namespace_::Properties props{{"uri", "http://localhost:2333"}};
 *   auto ns = lance::namespace_::connect("rest", props);
 *   std::string resp = ns.list_namespaces(R"({"parent":[]})");
 * @endcode
 *
 * Generate the REST client once with:
 *   make gen-rest-client   (from the cpp/ directory)
 */

#ifndef LANCE_NAMESPACE_HPP
#define LANCE_NAMESPACE_HPP

#include <lance_namespace/lance_namespace.h>

#include <cstdint>
#include <stdexcept>
#include <string>
#include <string_view>
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
    if (!raw_) throw_last_error();
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

  std::string list_namespaces(std::string_view req) const {
    return call_json(lance_namespace_list_namespaces(raw_, c(req)));
  }
  std::string describe_namespace(std::string_view req) const {
    return call_json(lance_namespace_describe_namespace(raw_, c(req)));
  }
  std::string create_namespace(std::string_view req) const {
    return call_json(lance_namespace_create_namespace(raw_, c(req)));
  }
  std::string drop_namespace(std::string_view req) const {
    return call_json(lance_namespace_drop_namespace(raw_, c(req)));
  }
  /** Returns true if namespace exists; throws Error with NAMESPACE_NOT_FOUND if not. */
  bool namespace_exists(std::string_view req) const {
    int r = lance_namespace_namespace_exists(raw_, c(req));
    if (r < 0) throw_last_error();
    return r == 0;
  }

  /* ── Table operations ── */

  std::string list_tables(std::string_view req) const {
    return call_json(lance_namespace_list_tables(raw_, c(req)));
  }
  std::string list_all_tables(std::string_view req) const {
    return call_json(lance_namespace_list_all_tables(raw_, c(req)));
  }
  std::string describe_table(std::string_view req) const {
    return call_json(lance_namespace_describe_table(raw_, c(req)));
  }
  std::string register_table(std::string_view req) const {
    return call_json(lance_namespace_register_table(raw_, c(req)));
  }
  /** Returns true if table exists; throws Error with TABLE_NOT_FOUND if not. */
  bool table_exists(std::string_view req) const {
    int r = lance_namespace_table_exists(raw_, c(req));
    if (r < 0) throw_last_error();
    return r == 0;
  }
  std::string drop_table(std::string_view req) const {
    return call_json(lance_namespace_drop_table(raw_, c(req)));
  }
  std::string deregister_table(std::string_view req) const {
    return call_json(lance_namespace_deregister_table(raw_, c(req)));
  }
  std::string rename_table(std::string_view req) const {
    return call_json(lance_namespace_rename_table(raw_, c(req)));
  }
  std::string restore_table(std::string_view req) const {
    return call_json(lance_namespace_restore_table(raw_, c(req)));
  }

  /* ── Table data ── */

  int64_t count_table_rows(std::string_view req) const {
    int64_t r = lance_namespace_count_table_rows(raw_, c(req));
    if (r < 0) throw_last_error();
    return r;
  }
  std::string create_table(std::string_view req) const {
    return call_json(lance_namespace_create_table(raw_, c(req)));
  }
  std::string declare_table(std::string_view req) const {
    return call_json(lance_namespace_declare_table(raw_, c(req)));
  }
  std::string create_empty_table(std::string_view req) const {
    return call_json(lance_namespace_create_empty_table(raw_, c(req)));
  }
  std::string insert_into_table(std::string_view req) const {
    return call_json(lance_namespace_insert_into_table(raw_, c(req)));
  }
  std::string merge_insert_into_table(std::string_view req) const {
    return call_json(lance_namespace_merge_insert_into_table(raw_, c(req)));
  }
  std::string update_table(std::string_view req) const {
    return call_json(lance_namespace_update_table(raw_, c(req)));
  }
  std::string delete_from_table(std::string_view req) const {
    return call_json(lance_namespace_delete_from_table(raw_, c(req)));
  }
  /** Returns Arrow IPC bytes. */
  std::vector<uint8_t> query_table(std::string_view req) const {
    lance_namespace_bytes_t *b = lance_namespace_query_table(raw_, c(req));
    if (!b) throw_last_error();
    std::vector<uint8_t> v(b->data, b->data + b->length);
    lance_namespace_free_bytes(b);
    return v;
  }

  /* ── Table schema / metadata ── */

  std::string get_table_stats(std::string_view req) const {
    return call_json(lance_namespace_get_table_stats(raw_, c(req)));
  }
  std::string update_table_schema_metadata(std::string_view req) const {
    return call_json(lance_namespace_update_table_schema_metadata(raw_, c(req)));
  }
  std::string update_field_metadata(std::string_view req) const {
    return call_json(lance_namespace_update_field_metadata(raw_, c(req)));
  }
  std::string explain_table_query_plan(std::string_view req) const {
    return call_json(lance_namespace_explain_table_query_plan(raw_, c(req)));
  }
  std::string analyze_table_query_plan(std::string_view req) const {
    return call_json(lance_namespace_analyze_table_query_plan(raw_, c(req)));
  }

  /* ── Table alter ── */

  std::string alter_table_add_columns(std::string_view req) const {
    return call_json(lance_namespace_alter_table_add_columns(raw_, c(req)));
  }
  std::string alter_table_alter_columns(std::string_view req) const {
    return call_json(lance_namespace_alter_table_alter_columns(raw_, c(req)));
  }
  std::string alter_table_backfill_columns(std::string_view req) const {
    return call_json(lance_namespace_alter_table_backfill_columns(raw_, c(req)));
  }
  std::string alter_table_drop_columns(std::string_view req) const {
    return call_json(lance_namespace_alter_table_drop_columns(raw_, c(req)));
  }

  /* ── Table index ── */

  std::string create_table_index(std::string_view req) const {
    return call_json(lance_namespace_create_table_index(raw_, c(req)));
  }
  std::string create_table_scalar_index(std::string_view req) const {
    return call_json(lance_namespace_create_table_scalar_index(raw_, c(req)));
  }
  std::string list_table_indices(std::string_view req) const {
    return call_json(lance_namespace_list_table_indices(raw_, c(req)));
  }
  std::string describe_table_index_stats(std::string_view req) const {
    return call_json(lance_namespace_describe_table_index_stats(raw_, c(req)));
  }
  std::string drop_table_index(std::string_view req) const {
    return call_json(lance_namespace_drop_table_index(raw_, c(req)));
  }

  /* ── Table versions ── */

  std::string list_table_versions(std::string_view req) const {
    return call_json(lance_namespace_list_table_versions(raw_, c(req)));
  }
  std::string create_table_version(std::string_view req) const {
    return call_json(lance_namespace_create_table_version(raw_, c(req)));
  }
  std::string describe_table_version(std::string_view req) const {
    return call_json(lance_namespace_describe_table_version(raw_, c(req)));
  }
  std::string batch_delete_table_versions(std::string_view req) const {
    return call_json(lance_namespace_batch_delete_table_versions(raw_, c(req)));
  }
  std::string batch_create_table_versions(std::string_view req) const {
    return call_json(lance_namespace_batch_create_table_versions(raw_, c(req)));
  }
  std::string batch_commit_tables(std::string_view req) const {
    return call_json(lance_namespace_batch_commit_tables(raw_, c(req)));
  }

  /* ── Materialized views ── */

  std::string create_materialized_view(std::string_view req) const {
    return call_json(lance_namespace_create_materialized_view(raw_, c(req)));
  }
  std::string refresh_materialized_view(std::string_view req) const {
    return call_json(lance_namespace_refresh_materialized_view(raw_, c(req)));
  }

  /* ── Tags ── */

  std::string list_table_tags(std::string_view req) const {
    return call_json(lance_namespace_list_table_tags(raw_, c(req)));
  }
  std::string get_table_tag_version(std::string_view req) const {
    return call_json(lance_namespace_get_table_tag_version(raw_, c(req)));
  }
  std::string create_table_tag(std::string_view req) const {
    return call_json(lance_namespace_create_table_tag(raw_, c(req)));
  }
  std::string delete_table_tag(std::string_view req) const {
    return call_json(lance_namespace_delete_table_tag(raw_, c(req)));
  }
  std::string update_table_tag(std::string_view req) const {
    return call_json(lance_namespace_update_table_tag(raw_, c(req)));
  }

  /* ── Branches ── */

  std::string create_table_branch(std::string_view req) const {
    return call_json(lance_namespace_create_table_branch(raw_, c(req)));
  }
  std::string list_table_branches(std::string_view req) const {
    return call_json(lance_namespace_list_table_branches(raw_, c(req)));
  }
  std::string delete_table_branch(std::string_view req) const {
    return call_json(lance_namespace_delete_table_branch(raw_, c(req)));
  }

  /* ── Transactions ── */

  std::string describe_transaction(std::string_view req) const {
    return call_json(lance_namespace_describe_transaction(raw_, c(req)));
  }
  std::string alter_transaction(std::string_view req) const {
    return call_json(lance_namespace_alter_transaction(raw_, c(req)));
  }

 private:
  lance_namespace_t *raw_{nullptr};

  static const char *c(std::string_view sv) noexcept {
    // string_view may not be null-terminated; callers pass string literals or
    // std::string data so this is safe in practice for the tests/examples.
    // For safety we rely on callers passing null-terminated strings.
    return sv.data();
  }

  // Adopt an owned char* response; frees it and returns std::string.
  static std::string call_json(char *raw) {
    if (!raw) throw_last_error();
    std::string s(raw);
    lance_namespace_free_string(raw);
    return s;
  }

  // For lance_namespace_id: pointer owned by handle, do not free.
  static std::string call_str(const char *raw, bool /*owned*/) {
    if (!raw) throw_last_error();
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

