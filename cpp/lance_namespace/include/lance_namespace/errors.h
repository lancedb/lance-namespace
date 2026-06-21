/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

/**
 * @file errors.h
 * @brief C error codes for Lance Namespace operations.
 *
 * Defines all error codes that can be returned by Lance Namespace operations.
 * These correspond to the C++ exception types in errors.hpp. Also, a
 * error structure is provided to hold an error code and an optional message.
 *
 * Use lance_namespace_error_code_to_string() to get a human-readable name
 * for any error code.
 */

#ifndef LANCE_NAMESPACE_ERRORS_H
#define LANCE_NAMESPACE_ERRORS_H

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

/**
 * @brief Error codes for Lance Namespace operations.
 *
 * Every operation may return one of these codes to indicate success or
 * the specific category of failure. The codes are shared across the C API,
 * C++ exceptions (errors.hpp), and Python error types.
 */
typedef enum lance_namespace_error_code {
    /** The operation is not supported by this namespace implementation. */
    LANCE_NAMESPACE_ERROR_UNSUPPORTED = 0,
    /** The requested namespace does not exist. */
    LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_FOUND = 1,
    /** A namespace with the same name already exists. */
    LANCE_NAMESPACE_ERROR_NAMESPACE_ALREADY_EXISTS = 2,
    /** The namespace is not empty (contains tables or child namespaces). */
    LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_EMPTY = 3,
    /** The requested table does not exist. */
    LANCE_NAMESPACE_ERROR_TABLE_NOT_FOUND = 4,
    /** A table with the same name already exists. */
    LANCE_NAMESPACE_ERROR_TABLE_ALREADY_EXISTS = 5,
    /** The requested table index does not exist. */
    LANCE_NAMESPACE_ERROR_TABLE_INDEX_NOT_FOUND = 6,
    /** An index with the same name already exists on the table. */
    LANCE_NAMESPACE_ERROR_TABLE_INDEX_ALREADY_EXISTS = 7,
    /** The requested table tag does not exist. */
    LANCE_NAMESPACE_ERROR_TABLE_TAG_NOT_FOUND = 8,
    /** A tag with the same name already exists on the table. */
    LANCE_NAMESPACE_ERROR_TABLE_TAG_ALREADY_EXISTS = 9,
    /** The requested transaction does not exist. */
    LANCE_NAMESPACE_ERROR_TRANSACTION_NOT_FOUND = 10,
    /** The requested table version does not exist. */
    LANCE_NAMESPACE_ERROR_TABLE_VERSION_NOT_FOUND = 11,
    /** A referenced column does not exist in the table. */
    LANCE_NAMESPACE_ERROR_TABLE_COLUMN_NOT_FOUND = 12,
    /** The request contains invalid parameters. */
    LANCE_NAMESPACE_ERROR_INVALID_INPUT = 13,
    /** A concurrent modification conflict occurred. */
    LANCE_NAMESPACE_ERROR_CONCURRENT_MODIFICATION = 14,
    /** The user lacks permission for this operation. */
    LANCE_NAMESPACE_ERROR_PERMISSION_DENIED = 15,
    /** Authentication credentials are missing or invalid. */
    LANCE_NAMESPACE_ERROR_UNAUTHENTICATED = 16,
    /** The service is temporarily unavailable. */
    LANCE_NAMESPACE_ERROR_SERVICE_UNAVAILABLE = 17,
    /** An unexpected internal error occurred. */
    LANCE_NAMESPACE_ERROR_INTERNAL = 18,
    /** The table is in an invalid state for this operation. */
    LANCE_NAMESPACE_ERROR_INVALID_TABLE_STATE = 19,
    /** Schema validation failed. */
    LANCE_NAMESPACE_ERROR_TABLE_SCHEMA_VALIDATION_ERROR = 20,
    /** The request was throttled due to rate limiting. */
    LANCE_NAMESPACE_ERROR_THROTTLING = 21,
    /** The requested table branch does not exist. */
    LANCE_NAMESPACE_ERROR_TABLE_BRANCH_NOT_FOUND = 22,
    /** A branch with the same name already exists on the table. */
    LANCE_NAMESPACE_ERROR_TABLE_BRANCH_ALREADY_EXISTS = 23,
} lance_namespace_error_code_t;

/**
 * @brief Structure representing an error in Lance Namespace operations.
 *
 * This structure contains the error code and an optional message
 * providing additional details about the error.
 */
typedef struct lance_namespace_error {
    lance_namespace_status_t code;
    char *message;
} lance_namespace_error_t;

/**
 * Convert an error code to a human-readable string.
 *
 * Returns a static string such as "unsupported", "namespace_not_found",
 * "table_already_exists", etc. The returned pointer is valid for the
 * lifetime of the program and must not be freed.
 *
 * @param code  The error code to convert.
 * @return A static string representation of the error code.
 */
LANCE_NAMESPACE_API const char *lance_namespace_error_code_to_string(lance_namespace_error_code_t code);

#ifdef __cplusplus
}
#endif

#endif  // LANCE_NAMESPACE_ERRORS_H
