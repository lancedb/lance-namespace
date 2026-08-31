// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * @file lance_namespace.h
 * @brief C API/ABI for the Lance Namespace interface.
 *
 * All operations use JSON-serialised request/response bodies (UTF-8,
 * null-terminated) so the ABI remains stable as the schema evolves.
 * The sole exception is query_table(), which returns raw Arrow IPC bytes
 * via lance_namespace_bytes_t.
 *
 * Memory ownership:
 *   - Strings returned by this API are heap-allocated by the library.
 *     Free them with lance_namespace_free_string().
 *   - Bytes objects returned by query_table are freed with
 *     lance_namespace_free_bytes().
 *   - The string returned by lance_namespace_id() is owned by the handle;
 *     do NOT free it.
 *
 * Error handling:
 *   - Functions that can fail return NULL / -1 on error.
 *   - After a failure, call lance_namespace_last_error_code() and
 *     lance_namespace_last_error_message() (thread-local state).
 */

#ifndef LANCE_NAMESPACE_H
#define LANCE_NAMESPACE_H

#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

/* ─── Visibility ─────────────────────────────────────────────────────────── */

#if defined(_WIN32) || defined(__CYGWIN__)
#  ifdef LANCE_NAMESPACE_BUILD_SHARED
#    define LANCE_NAMESPACE_API __declspec(dllexport)
#  elif defined(LANCE_NAMESPACE_SHARED)
#    define LANCE_NAMESPACE_API __declspec(dllimport)
#  else
#    define LANCE_NAMESPACE_API
#  endif
#else
#  if defined(__GNUC__) || defined(__clang__)
#    define LANCE_NAMESPACE_API __attribute__((visibility("default")))
#  else
#    define LANCE_NAMESPACE_API
#  endif
#endif

/* ─── Error codes ────────────────────────────────────────────────────────── */

/**
 * Error codes for Lance Namespace operations.
 * Values match the Python sdk ErrorCode enum exactly.
 * LANCE_NS_OK (-1) means no error.
 */
typedef enum lance_namespace_error_code {
  LANCE_NS_OK = -1,
  LANCE_NS_ERR_UNSUPPORTED = 0,
  LANCE_NS_ERR_NAMESPACE_NOT_FOUND = 1,
  LANCE_NS_ERR_NAMESPACE_ALREADY_EXISTS = 2,
  LANCE_NS_ERR_NAMESPACE_NOT_EMPTY = 3,
  LANCE_NS_ERR_TABLE_NOT_FOUND = 4,
  LANCE_NS_ERR_TABLE_ALREADY_EXISTS = 5,
  LANCE_NS_ERR_TABLE_INDEX_NOT_FOUND = 6,
  LANCE_NS_ERR_TABLE_INDEX_ALREADY_EXISTS = 7,
  LANCE_NS_ERR_TABLE_TAG_NOT_FOUND = 8,
  LANCE_NS_ERR_TABLE_TAG_ALREADY_EXISTS = 9,
  LANCE_NS_ERR_TRANSACTION_NOT_FOUND = 10,
  LANCE_NS_ERR_TABLE_VERSION_NOT_FOUND = 11,
  LANCE_NS_ERR_TABLE_COLUMN_NOT_FOUND = 12,
  LANCE_NS_ERR_INVALID_INPUT = 13,
  LANCE_NS_ERR_CONCURRENT_MODIFICATION = 14,
  LANCE_NS_ERR_PERMISSION_DENIED = 15,
  LANCE_NS_ERR_UNAUTHENTICATED = 16,
  LANCE_NS_ERR_SERVICE_UNAVAILABLE = 17,
  LANCE_NS_ERR_INTERNAL = 18,
  LANCE_NS_ERR_INVALID_TABLE_STATE = 19,
  LANCE_NS_ERR_TABLE_SCHEMA_VALIDATION = 20,
  LANCE_NS_ERR_THROTTLING = 21,
  LANCE_NS_ERR_TABLE_BRANCH_NOT_FOUND = 22,
  LANCE_NS_ERR_TABLE_BRANCH_ALREADY_EXISTS = 23,
} lance_namespace_error_code_t;

/* ─── Common types ───────────────────────────────────────────────────────── */

/** A single string key-value property. */
typedef struct lance_namespace_property {
  const char *key;
  const char *value;
} lance_namespace_property_t;

/** An immutable list of string key-value properties. */
typedef struct lance_namespace_properties {
  const lance_namespace_property_t *items;
  size_t count;
} lance_namespace_properties_t;

/**
 * An owned byte buffer (for binary responses such as Arrow IPC).
 * Free with lance_namespace_free_bytes().
 */
typedef struct lance_namespace_bytes {
  uint8_t *data;
  size_t length;
} lance_namespace_bytes_t;

/* ─── Opaque handle ──────────────────────────────────────────────────────── */

/** Opaque handle for a Lance namespace instance. */
typedef struct lance_namespace_s lance_namespace_t;

/* ─── Memory ─────────────────────────────────────────────────────────────── */

/** Free a string returned by any API function. Safe to call with NULL. */
LANCE_NAMESPACE_API void lance_namespace_free_string(char *s);

/** Free a bytes object returned by lance_namespace_query_table(). */
LANCE_NAMESPACE_API void lance_namespace_free_bytes(lance_namespace_bytes_t *b);

/* ─── Error state (thread-local) ─────────────────────────────────────────── */

/** Error code of the most recent failed call on this thread, or LANCE_NS_OK. */
LANCE_NAMESPACE_API int lance_namespace_last_error_code(void);

/** Error message of the most recent failed call on this thread. Never NULL. */
LANCE_NAMESPACE_API const char *lance_namespace_last_error_message(void);

/* ─── Lifecycle ──────────────────────────────────────────────────────────── */

/**
 * Connect to a Lance namespace backend.
 *
 * @param impl_type  "dir", "rest", or a custom type registered with
 *                   lance_namespace_register().
 * @param properties Key-value configuration for the implementation, or NULL.
 * @return           New handle owned by the caller, or NULL on error.
 */
LANCE_NAMESPACE_API lance_namespace_t *lance_namespace_connect(
    const char *impl_type, const lance_namespace_properties_t *properties);

/** Destroy a handle. Safe to call with NULL. */
LANCE_NAMESPACE_API void lance_namespace_destroy(lance_namespace_t *ns);

/* ─── Identity ───────────────────────────────────────────────────────────── */

/**
 * Human-readable identifier for this namespace instance.
 * The returned string is owned by @p ns — do NOT free it.
 */
LANCE_NAMESPACE_API const char *lance_namespace_id(lance_namespace_t *ns);

/* ─── Factory registration ───────────────────────────────────────────────── */

/**
 * Register a custom namespace factory.
 * See lance_namespace_impl.h for the vtable-based approach which is
 * preferred for non-trivial implementations.
 */
typedef lance_namespace_t *(*lance_namespace_factory_fn)(
    const lance_namespace_properties_t *properties);

LANCE_NAMESPACE_API void lance_namespace_register(const char *impl_type,
                                                   lance_namespace_factory_fn factory);

/* ─── Namespace operations ───────────────────────────────────────────────── */

/** @return Allocated JSON response string, or NULL on error. */
LANCE_NAMESPACE_API char *lance_namespace_list_namespaces(lance_namespace_t *ns,
                                                           const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_describe_namespace(lance_namespace_t *ns,
                                                              const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_create_namespace(lance_namespace_t *ns,
                                                            const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_drop_namespace(lance_namespace_t *ns,
                                                          const char *request_json);

/**
 * Returns 0 if exists, -1 on error.
 * Sets LANCE_NS_ERR_NAMESPACE_NOT_FOUND when the namespace does not exist.
 */
LANCE_NAMESPACE_API int lance_namespace_namespace_exists(lance_namespace_t *ns,
                                                          const char *request_json);

/* ─── Table operations ───────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_list_tables(lance_namespace_t *ns,
                                                       const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_list_all_tables(lance_namespace_t *ns,
                                                           const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_describe_table(lance_namespace_t *ns,
                                                          const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_register_table(lance_namespace_t *ns,
                                                          const char *request_json);

/**
 * Returns 0 if exists, -1 on error.
 * Sets LANCE_NS_ERR_TABLE_NOT_FOUND when the table does not exist.
 */
LANCE_NAMESPACE_API int lance_namespace_table_exists(lance_namespace_t *ns,
                                                      const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_drop_table(lance_namespace_t *ns,
                                                      const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_deregister_table(lance_namespace_t *ns,
                                                            const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_rename_table(lance_namespace_t *ns,
                                                        const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_restore_table(lance_namespace_t *ns,
                                                         const char *request_json);

/* ─── Table data ─────────────────────────────────────────────────────────── */

/**
 * Returns row count (>= 0), or -1 on error.
 */
LANCE_NAMESPACE_API int64_t lance_namespace_count_table_rows(lance_namespace_t *ns,
                                                              const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_create_table(lance_namespace_t *ns,
                                                        const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_declare_table(lance_namespace_t *ns,
                                                         const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_create_empty_table(lance_namespace_t *ns,
                                                              const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_insert_into_table(lance_namespace_t *ns,
                                                             const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_merge_insert_into_table(lance_namespace_t *ns,
                                                                   const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_update_table(lance_namespace_t *ns,
                                                        const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_delete_from_table(lance_namespace_t *ns,
                                                             const char *request_json);

/**
 * Execute a table query.
 * @return  Owned lance_namespace_bytes_t containing Arrow IPC data, or NULL on error.
 *          Free with lance_namespace_free_bytes().
 */
LANCE_NAMESPACE_API lance_namespace_bytes_t *lance_namespace_query_table(
    lance_namespace_t *ns, const char *request_json);

/* ─── Table schema / metadata ────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_get_table_stats(lance_namespace_t *ns,
                                                           const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_update_table_schema_metadata(
    lance_namespace_t *ns, const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_update_field_metadata(lance_namespace_t *ns,
                                                                  const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_explain_table_query_plan(lance_namespace_t *ns,
                                                                    const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_analyze_table_query_plan(lance_namespace_t *ns,
                                                                    const char *request_json);

/* ─── Table alter ────────────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_alter_table_add_columns(lance_namespace_t *ns,
                                                                   const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_alter_table_alter_columns(lance_namespace_t *ns,
                                                                     const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_alter_table_backfill_columns(
    lance_namespace_t *ns, const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_alter_table_drop_columns(lance_namespace_t *ns,
                                                                    const char *request_json);

/* ─── Table index ────────────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_create_table_index(lance_namespace_t *ns,
                                                              const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_create_table_scalar_index(lance_namespace_t *ns,
                                                                     const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_list_table_indices(lance_namespace_t *ns,
                                                              const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_describe_table_index_stats(lance_namespace_t *ns,
                                                                      const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_drop_table_index(lance_namespace_t *ns,
                                                            const char *request_json);

/* ─── Table versions ─────────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_list_table_versions(lance_namespace_t *ns,
                                                               const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_create_table_version(lance_namespace_t *ns,
                                                                const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_describe_table_version(lance_namespace_t *ns,
                                                                  const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_batch_delete_table_versions(
    lance_namespace_t *ns, const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_batch_create_table_versions(
    lance_namespace_t *ns, const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_batch_commit_tables(lance_namespace_t *ns,
                                                               const char *request_json);

/* ─── Materialized views ─────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_create_materialized_view(lance_namespace_t *ns,
                                                                    const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_refresh_materialized_view(lance_namespace_t *ns,
                                                                     const char *request_json);

/* ─── Table tags ─────────────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_list_table_tags(lance_namespace_t *ns,
                                                           const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_get_table_tag_version(lance_namespace_t *ns,
                                                                  const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_create_table_tag(lance_namespace_t *ns,
                                                            const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_delete_table_tag(lance_namespace_t *ns,
                                                            const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_update_table_tag(lance_namespace_t *ns,
                                                            const char *request_json);

/* ─── Table branches ─────────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_create_table_branch(lance_namespace_t *ns,
                                                               const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_list_table_branches(lance_namespace_t *ns,
                                                               const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_delete_table_branch(lance_namespace_t *ns,
                                                               const char *request_json);

/* ─── Transactions ───────────────────────────────────────────────────────── */

LANCE_NAMESPACE_API char *lance_namespace_describe_transaction(lance_namespace_t *ns,
                                                                const char *request_json);

LANCE_NAMESPACE_API char *lance_namespace_alter_transaction(lance_namespace_t *ns,
                                                             const char *request_json);

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /* LANCE_NAMESPACE_H */
