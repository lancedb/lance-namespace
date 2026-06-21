/**
 * @file lance_namespace.h
 * @brief C API for Lance Namespace core interface.
 *
 * This header provides the C API for creating, connecting to, and managing
 * Lance Namespace implementations. It defines:
 *
 * - Status codes (lance_namespace_status_t)
 * - Connection options (lance_namespace_options_t, lance_namespace_property_t)
 * - Namespace handles (lance_namespace_handle_t)
 * - Plugin registration (lance_namespace_register_impl, lance_namespace_connect)
 *
 * For namespace operation functions (list_tables, create_table, etc.),
 * see operations.h.
 *
 * For C-compatible request/response model types, see models.h.
 *
 * Example usage:
 * @code
 *   // Register a custom implementation
 *   lance_namespace_register_impl("my-impl", my_create_fn);
 *
 *   // Connect to the namespace
 *   lance_namespace_handle_t *ns = NULL;
 *   lance_namespace_error_t error = {0};
 *   lance_namespace_property_t props[] = {{"uri", "http://localhost:8080"}};
 *   lance_namespace_options_t opts = {props, 1};
 *   lance_namespace_connect("my-impl", &opts, &ns, &error);
 *
 *   // Use the namespace...
 *   printf("ID: %s\n", lance_namespace_namespace_id(ns));
 *
 *   // Clean up
 *   lance_namespace_handle_release(ns);
 *   lance_namespace_error_free(&error);
 * @endcode
 */

#ifndef LANCE_NAMESPACE_LANCE_NAMESPACE_H
#define LANCE_NAMESPACE_LANCE_NAMESPACE_H

#include <stddef.h>

#include "version.h"

#if defined(_WIN32) || defined(__CYGWIN__)
#if defined(LANCE_NAMESPACE_BUILDING)
#define LANCE_NAMESPACE_API __declspec(dllexport)
#else
#define LANCE_NAMESPACE_API __declspec(dllimport)
#endif
#else
#define LANCE_NAMESPACE_API
#endif

#ifdef __cplusplus
extern "C" {
#endif

typedef enum lance_namespace_status {
    LANCE_NAMESPACE_STATUS_OK = 0,
    LANCE_NAMESPACE_STATUS_INVALID_ARGUMENT = 1,
    LANCE_NAMESPACE_STATUS_NOT_FOUND = 2,
    LANCE_NAMESPACE_STATUS_ALREADY_EXISTS = 3,
    LANCE_NAMESPACE_STATUS_UNSUPPORTED = 4,
    LANCE_NAMESPACE_STATUS_INTERNAL = 5,
} lance_namespace_status_t;

typedef struct lance_namespace_property {
    const char *key;
    const char *value;
} lance_namespace_property_t;

typedef struct lance_namespace_options {
    const lance_namespace_property_t *properties;
    size_t property_count;
} lance_namespace_options_t;

typedef struct lance_namespace_header {
    const char *name;
    const char *value;
} lance_namespace_header_t;

typedef struct lance_namespace_headers {
    const lance_namespace_header_t *items;
    size_t count;
} lance_namespace_headers_t;

typedef struct lance_namespace_handle lance_namespace_handle_t;
typedef struct lance_namespace_vtable lance_namespace_vtable_t;

typedef lance_namespace_status_t (*lance_namespace_create_fn)(
    const lance_namespace_options_t *options,
    lance_namespace_handle_t **out_namespace,
    lance_namespace_error_t *error);

struct lance_namespace_vtable {
    const char *(*namespace_id)(const lance_namespace_handle_t *self);
    void (*destroy)(void *instance);
};

LANCE_NAMESPACE_API const char *lance_namespace_version_string(void);
LANCE_NAMESPACE_API const char *lance_namespace_status_to_string(
    lance_namespace_status_t status);
LANCE_NAMESPACE_API const char *lance_namespace_option_get(
    const lance_namespace_options_t *options,
    const char *key);
LANCE_NAMESPACE_API const char *lance_namespace_header_get(
    const lance_namespace_headers_t *headers,
    const char *name);

LANCE_NAMESPACE_API lance_namespace_handle_t *lance_namespace_handle_create(
    const lance_namespace_vtable_t *vtable,
    void *instance);
LANCE_NAMESPACE_API void *lance_namespace_handle_instance(
    const lance_namespace_handle_t *handle);
LANCE_NAMESPACE_API void lance_namespace_handle_release(
    lance_namespace_handle_t *handle);
LANCE_NAMESPACE_API const char *lance_namespace_namespace_id(
    const lance_namespace_handle_t *handle);

LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_register_impl(
    const char *name,
    lance_namespace_create_fn create_fn);
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_unregister_impl(
    const char *name);
LANCE_NAMESPACE_API lance_namespace_status_t lance_namespace_connect(
    const char *impl,
    const lance_namespace_options_t *options,
    lance_namespace_handle_t **out_namespace,
    lance_namespace_error_t *error);
LANCE_NAMESPACE_API void lance_namespace_error_free(lance_namespace_error_t *error);

#ifdef __cplusplus
}
#endif

#endif  // LANCE_NAMESPACE_LANCE_NAMESPACE_H
