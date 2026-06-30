// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * @file lance_namespace_impl.h
 * @brief C vtable interface for implementing a custom Lance Namespace backend.
 *
 * This is the *implementors* header.  Public consumers of the library only
 * need lance_namespace.h; this header is for authors building a custom
 * namespace backend that is registered with lance_namespace_register_vtable().
 *
 * Why a C vtable instead of C++ virtual dispatch?
 *   - The vtable is a plain C struct, so it crosses DLL/shared-library
 *     boundaries without depending on a particular C++ ABI (Itanium vs MSVC).
 *   - Any language that can call C (Python via ctypes, Rust via bindgen, etc.)
 *     can implement or consume a vtable-based backend.
 *   - New operations can be added at the end without breaking existing
 *     compiled implementations (forward-compatible layout).
 *
 * Usage (C):
 * @code
 *   #include <lance_namespace/lance_namespace_impl.h>
 *   #include <stdlib.h>
 *   #include <string.h>
 *
 *   typedef struct { char id[64]; } MyImpl;
 *
 *   static void my_destroy(void *impl) { free(impl); }
 *   static const char *my_id(const void *impl) {
 *     return ((const MyImpl *)impl)->id;
 *   }
 *   static char *my_list_namespaces(void *impl, const char *req) {
 *     (void)impl; (void)req;
 *     return strdup("{\"namespaces\":[]}");
 *   }
 *
 *   static const lance_namespace_vtable_t MY_VTABLE = {
 *     .abi_version       = LANCE_NAMESPACE_ABI_VERSION,
 *     .destroy           = my_destroy,
 *     .namespace_id      = my_id,
 *     .list_namespaces   = my_list_namespaces,
 *     // ... remaining fields default to NULL (=> UNSUPPORTED)
 *   };
 *
 *   lance_namespace_t *my_factory(const lance_namespace_properties_t *props) {
 *     MyImpl *impl = calloc(1, sizeof(MyImpl));
 *     snprintf(impl->id, sizeof(impl->id), "MyNS");
 *     return lance_namespace_create_from_vtable(&MY_VTABLE, impl);
 *   }
 *   // Register: lance_namespace_register("my-ns", my_factory);
 * @endcode
 *
 * Usage (C++):
 * @code
 *   #include <lance_namespace/lance_namespace_impl.h>
 *   struct MyImpl { std::string id; };
 *   // Fill vtable with lambdas cast to function pointers,
 *   // or use static member functions.
 * @endcode
 */

#ifndef LANCE_NAMESPACE_IMPL_H
#define LANCE_NAMESPACE_IMPL_H

#include <lance_namespace/lance_namespace.h>

#ifdef __cplusplus
extern "C" {
#endif

/** Current ABI version.  Increment when adding fields to the vtable. */
#define LANCE_NAMESPACE_ABI_VERSION 1u

/**
 * Virtual function table for a Lance Namespace implementation.
 *
 * All function pointers may be NULL, in which case the corresponding
 * public API call returns LANCE_NS_ERR_UNSUPPORTED.
 *
 * Conventions for operation functions:
 *   - @p impl  The implementation-specific data pointer.
 *   - @p req   Request body as UTF-8 JSON (may be NULL for empty requests).
 *   - Returns an owned, malloc-allocated JSON response string on success;
 *     NULL on error. The caller frees via lance_namespace_free_string().
 *   - On error, set the thread-local error state using the helpers in
 *     lance_namespace_impl_set_error() below.
 */
typedef struct lance_namespace_vtable_s {
  /** Must equal LANCE_NAMESPACE_ABI_VERSION. */
  uint32_t abi_version;

  /* ── Lifecycle ── */

  /** Release all resources associated with @p impl. */
  void (*destroy)(void *impl);

  /* ── Identity ── */

  /**
   * Return a human-readable identifier for this instance.
   * The returned pointer must remain valid for the lifetime of @p impl.
   */
  const char *(*namespace_id)(const void *impl);

  /* ── Namespace operations ── */

  char *(*list_namespaces)(void *impl, const char *req);
  char *(*describe_namespace)(void *impl, const char *req);
  char *(*create_namespace)(void *impl, const char *req);
  char *(*drop_namespace)(void *impl, const char *req);
  /** Returns 0 if exists, -1 on error / not found. */
  int (*namespace_exists)(void *impl, const char *req);

  /* ── Table operations ── */

  char *(*list_tables)(void *impl, const char *req);
  char *(*list_all_tables)(void *impl, const char *req);
  char *(*describe_table)(void *impl, const char *req);
  char *(*register_table)(void *impl, const char *req);
  /** Returns 0 if exists, -1 on error / not found. */
  int (*table_exists)(void *impl, const char *req);
  char *(*drop_table)(void *impl, const char *req);
  char *(*deregister_table)(void *impl, const char *req);
  char *(*rename_table)(void *impl, const char *req);
  char *(*restore_table)(void *impl, const char *req);

  /* ── Table data ── */

  /** Returns row count (>= 0), or -1 on error. */
  int64_t (*count_table_rows)(void *impl, const char *req);
  char *(*create_table)(void *impl, const char *req);
  char *(*declare_table)(void *impl, const char *req);
  char *(*create_empty_table)(void *impl, const char *req);
  char *(*insert_into_table)(void *impl, const char *req);
  char *(*merge_insert_into_table)(void *impl, const char *req);
  char *(*update_table)(void *impl, const char *req);
  char *(*delete_from_table)(void *impl, const char *req);
  /**
   * Returns an owned lance_namespace_bytes_t* (Arrow IPC), or NULL on error.
   * Caller frees with lance_namespace_free_bytes().
   */
  lance_namespace_bytes_t *(*query_table)(void *impl, const char *req);

  /* ── Table schema / metadata ── */

  char *(*get_table_stats)(void *impl, const char *req);
  char *(*update_table_schema_metadata)(void *impl, const char *req);
  char *(*update_field_metadata)(void *impl, const char *req);
  char *(*explain_table_query_plan)(void *impl, const char *req);
  char *(*analyze_table_query_plan)(void *impl, const char *req);

  /* ── Table alter ── */

  char *(*alter_table_add_columns)(void *impl, const char *req);
  char *(*alter_table_alter_columns)(void *impl, const char *req);
  char *(*alter_table_backfill_columns)(void *impl, const char *req);
  char *(*alter_table_drop_columns)(void *impl, const char *req);

  /* ── Table index ── */

  char *(*create_table_index)(void *impl, const char *req);
  char *(*create_table_scalar_index)(void *impl, const char *req);
  char *(*list_table_indices)(void *impl, const char *req);
  char *(*describe_table_index_stats)(void *impl, const char *req);
  char *(*drop_table_index)(void *impl, const char *req);

  /* ── Table versions ── */

  char *(*list_table_versions)(void *impl, const char *req);
  char *(*create_table_version)(void *impl, const char *req);
  char *(*describe_table_version)(void *impl, const char *req);
  char *(*batch_delete_table_versions)(void *impl, const char *req);
  char *(*batch_create_table_versions)(void *impl, const char *req);
  char *(*batch_commit_tables)(void *impl, const char *req);

  /* ── Materialized views ── */

  char *(*create_materialized_view)(void *impl, const char *req);
  char *(*refresh_materialized_view)(void *impl, const char *req);

  /* ── Tags ── */

  char *(*list_table_tags)(void *impl, const char *req);
  char *(*get_table_tag_version)(void *impl, const char *req);
  char *(*create_table_tag)(void *impl, const char *req);
  char *(*delete_table_tag)(void *impl, const char *req);
  char *(*update_table_tag)(void *impl, const char *req);

  /* ── Branches ── */

  char *(*create_table_branch)(void *impl, const char *req);
  char *(*list_table_branches)(void *impl, const char *req);
  char *(*delete_table_branch)(void *impl, const char *req);

  /* ── Transactions ── */

  char *(*describe_transaction)(void *impl, const char *req);
  char *(*alter_transaction)(void *impl, const char *req);

  /** Reserved for future ABI-compatible additions.  Must be zeroed. */
  void *reserved[8];
} lance_namespace_vtable_t;

/**
 * Create a lance_namespace_t from a vtable and implementation-specific data.
 *
 * @param vtable  Pointer to a static vtable struct.  Must remain valid for
 *                the lifetime of the returned handle.
 * @param data    Implementation-specific data passed as the first argument to
 *                every vtable function.  May be NULL.
 * @return        New handle owned by the caller, or NULL on allocation failure.
 */
LANCE_NAMESPACE_API lance_namespace_t *lance_namespace_create_from_vtable(
    const lance_namespace_vtable_t *vtable, void *data);

/**
 * Helper: set the thread-local error state.
 * Call this inside a vtable function before returning NULL / -1.
 */
LANCE_NAMESPACE_API void lance_namespace_impl_set_error(int code, const char *message);

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif /* LANCE_NAMESPACE_IMPL_H */
