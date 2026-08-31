// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

#include <lance_namespace/lance_namespace_impl.h>

#include <cstdlib>
#include <cstring>
#include <mutex>
#include <string>
#include <unordered_map>

/* ─── Internal struct (vtable + impl data) ─────────────────────────────────── */

struct lance_namespace_s {
  const lance_namespace_vtable_t *vtable;
  void *data;
};

/* ─── Thread-local error state ──────────────────────────────────────────────── */

namespace {

struct ThreadError {
  int code = LANCE_NS_OK;
  std::string message;
};

thread_local ThreadError tl_error;

void set_error(int code, std::string msg) noexcept {
  tl_error.code = code;
  tl_error.message = std::move(msg);
}

void clear_error() noexcept {
  tl_error.code = LANCE_NS_OK;
  tl_error.message.clear();
}

/* Check handle is non-null and vtable function @p fn is present. */
template <typename Fn>
bool check(lance_namespace_t *ns, Fn fn, const char *op_name) noexcept {
  if (!ns) {
    set_error(LANCE_NS_ERR_INVALID_INPUT, "namespace handle is NULL");
    return false;
  }
  if (!fn) {
    set_error(LANCE_NS_ERR_UNSUPPORTED, std::string(op_name) + " is not supported");
    return false;
  }
  return true;
}

}  // namespace

/* ─── Registry ───────────────────────────────────────────────────────────────── */

namespace {

struct Registry {
  std::mutex mtx;
  std::unordered_map<std::string, lance_namespace_factory_fn> factories;

  static Registry &instance() {
    static Registry reg;
    return reg;
  }

  lance_namespace_factory_fn find(const std::string &type) {
    std::lock_guard<std::mutex> lk(mtx);
    auto it = factories.find(type);
    return it != factories.end() ? it->second : nullptr;
  }

  void add(const std::string &type, lance_namespace_factory_fn fn) {
    std::lock_guard<std::mutex> lk(mtx);
    factories[type] = fn;
  }
};

}  // namespace

/* ─── Public C API ───────────────────────────────────────────────────────────── */

extern "C" {

/* ── Memory ── */

void lance_namespace_free_string(char *s) { std::free(s); }

void lance_namespace_free_bytes(lance_namespace_bytes_t *b) {
  if (b) {
    std::free(b->data);
    std::free(b);
  }
}

/* ── Error state ── */

int lance_namespace_last_error_code(void) { return tl_error.code; }

const char *lance_namespace_last_error_message(void) { return tl_error.message.c_str(); }

/* ── Implementor helper ── */

void lance_namespace_impl_set_error(int code, const char *message) {
  set_error(code, message ? message : "");
}

/* ── Create from vtable ── */

lance_namespace_t *lance_namespace_create_from_vtable(const lance_namespace_vtable_t *vtable,
                                                       void *data) {
  if (!vtable) {
    set_error(LANCE_NS_ERR_INVALID_INPUT, "vtable must not be NULL");
    return nullptr;
  }
  auto *ns = new (std::nothrow) lance_namespace_s{vtable, data};
  if (!ns) {
    set_error(LANCE_NS_ERR_INTERNAL, "out of memory");
    return nullptr;
  }
  return ns;
}

/* ── Lifecycle ── */

lance_namespace_t *lance_namespace_connect(const char *impl_type,
                                            const lance_namespace_properties_t *properties) {
  clear_error();
  if (!impl_type) {
    set_error(LANCE_NS_ERR_INVALID_INPUT, "impl_type must not be NULL");
    return nullptr;
  }
  auto factory = Registry::instance().find(impl_type);
  if (!factory) {
    set_error(LANCE_NS_ERR_UNSUPPORTED,
              std::string("Unknown namespace implementation type: ") + impl_type);
    return nullptr;
  }
  return factory(properties);
}

void lance_namespace_destroy(lance_namespace_t *ns) {
  if (!ns) return;
  if (ns->vtable && ns->vtable->destroy) {
    ns->vtable->destroy(ns->data);
  }
  delete ns;
}

/* ── Identity ── */

const char *lance_namespace_id(lance_namespace_t *ns) {
  clear_error();
  if (!check(ns, ns ? ns->vtable->namespace_id : nullptr, "namespace_id")) return nullptr;
  return ns->vtable->namespace_id(ns->data);
}

/* ── Register factory ── */

void lance_namespace_register(const char *impl_type, lance_namespace_factory_fn factory) {
  if (impl_type && factory) Registry::instance().add(impl_type, factory);
}

/* ─── Dispatch macros ────────────────────────────────────────────────────────── *
 * DISPATCH_JSON: operation returns char* (JSON response or NULL on error).       *
 * DISPATCH_INT:  operation returns int (0 = success, -1 = error).                *
 * DISPATCH_I64:  operation returns int64_t (>= 0 = value, -1 = error).           *
 * DISPATCH_BYTES:operation returns lance_namespace_bytes_t* or NULL.             *
 * ─────────────────────────────────────────────────────────────────────────────── */

#define DISPATCH_JSON(op)                                                         \
  char *lance_namespace_##op(lance_namespace_t *ns, const char *req) {           \
    clear_error();                                                                 \
    if (!check(ns, ns ? ns->vtable->op : nullptr, #op)) return nullptr;          \
    return ns->vtable->op(ns->data, req);                                         \
  }

#define DISPATCH_INT(op)                                                          \
  int lance_namespace_##op(lance_namespace_t *ns, const char *req) {             \
    clear_error();                                                                 \
    if (!check(ns, ns ? ns->vtable->op : nullptr, #op)) return -1;               \
    return ns->vtable->op(ns->data, req);                                         \
  }

#define DISPATCH_I64(op)                                                          \
  int64_t lance_namespace_##op(lance_namespace_t *ns, const char *req) {         \
    clear_error();                                                                 \
    if (!check(ns, ns ? ns->vtable->op : nullptr, #op)) return -1;               \
    return ns->vtable->op(ns->data, req);                                         \
  }

#define DISPATCH_BYTES(op)                                                                \
  lance_namespace_bytes_t *lance_namespace_##op(lance_namespace_t *ns, const char *req) { \
    clear_error();                                                                         \
    if (!check(ns, ns ? ns->vtable->op : nullptr, #op)) return nullptr;                  \
    return ns->vtable->op(ns->data, req);                                                 \
  }

/* ── Namespace operations ── */
DISPATCH_JSON(list_namespaces)
DISPATCH_JSON(describe_namespace)
DISPATCH_JSON(create_namespace)
DISPATCH_JSON(drop_namespace)
DISPATCH_INT(namespace_exists)

/* ── Table operations ── */
DISPATCH_JSON(list_tables)
DISPATCH_JSON(list_all_tables)
DISPATCH_JSON(describe_table)
DISPATCH_JSON(register_table)
DISPATCH_INT(table_exists)
DISPATCH_JSON(drop_table)
DISPATCH_JSON(deregister_table)
DISPATCH_JSON(rename_table)
DISPATCH_JSON(restore_table)

/* ── Table data ── */
DISPATCH_I64(count_table_rows)
DISPATCH_JSON(create_table)
DISPATCH_JSON(declare_table)
DISPATCH_JSON(create_empty_table)
DISPATCH_JSON(insert_into_table)
DISPATCH_JSON(merge_insert_into_table)
DISPATCH_JSON(update_table)
DISPATCH_JSON(delete_from_table)
DISPATCH_BYTES(query_table)

/* ── Table schema / metadata ── */
DISPATCH_JSON(get_table_stats)
DISPATCH_JSON(update_table_schema_metadata)
DISPATCH_JSON(update_field_metadata)
DISPATCH_JSON(explain_table_query_plan)
DISPATCH_JSON(analyze_table_query_plan)

/* ── Table alter ── */
DISPATCH_JSON(alter_table_add_columns)
DISPATCH_JSON(alter_table_alter_columns)
DISPATCH_JSON(alter_table_backfill_columns)
DISPATCH_JSON(alter_table_drop_columns)

/* ── Table index ── */
DISPATCH_JSON(create_table_index)
DISPATCH_JSON(create_table_scalar_index)
DISPATCH_JSON(list_table_indices)
DISPATCH_JSON(describe_table_index_stats)
DISPATCH_JSON(drop_table_index)

/* ── Table versions ── */
DISPATCH_JSON(list_table_versions)
DISPATCH_JSON(create_table_version)
DISPATCH_JSON(describe_table_version)
DISPATCH_JSON(batch_delete_table_versions)
DISPATCH_JSON(batch_create_table_versions)
DISPATCH_JSON(batch_commit_tables)

/* ── Materialized views ── */
DISPATCH_JSON(create_materialized_view)
DISPATCH_JSON(refresh_materialized_view)

/* ── Tags ── */
DISPATCH_JSON(list_table_tags)
DISPATCH_JSON(get_table_tag_version)
DISPATCH_JSON(create_table_tag)
DISPATCH_JSON(delete_table_tag)
DISPATCH_JSON(update_table_tag)

/* ── Branches ── */
DISPATCH_JSON(create_table_branch)
DISPATCH_JSON(list_table_branches)
DISPATCH_JSON(delete_table_branch)

/* ── Transactions ── */
DISPATCH_JSON(describe_transaction)
DISPATCH_JSON(alter_transaction)

#undef DISPATCH_JSON
#undef DISPATCH_INT
#undef DISPATCH_I64
#undef DISPATCH_BYTES

}  // extern "C"
