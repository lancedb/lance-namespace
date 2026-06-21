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
 * @file errors.hpp
 * @brief C++ exception types for Lance Namespace operations.
 * 
 * Defines C++ exception types corresponding to the error codes in errors.h.
 */

#ifndef LANCE_NAMESPACE_ERRORS_HPP
#define LANCE_NAMESPACE_ERRORS_HPP

#include "errors.h"

#include <exception>
#include <string>
#include <utility>

namespace lance_namespace {

class LanceNamespaceError : public std::exception {
  public:
    explicit LanceNamespaceError(std::string message,
                                 lance_namespace_error_code_t code = LANCE_NAMESPACE_ERROR_INTERNAL)
        : message_(std::move(message)), code_(code) {}

    const char *what() const noexcept override { return message_.c_str(); }
    lance_namespace_error_code_t code() const noexcept { return code_; }

  private:
    std::string message_;
    lance_namespace_error_code_t code_;
};

class UnsupportedOperationError : public LanceNamespaceError {
  public:
    explicit UnsupportedOperationError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_UNSUPPORTED) {}
};

class NamespaceNotFoundError : public LanceNamespaceError {
  public:
    explicit NamespaceNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_FOUND) {}
};

class NamespaceAlreadyExistsError : public LanceNamespaceError {
  public:
    explicit NamespaceAlreadyExistsError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_NAMESPACE_ALREADY_EXISTS) {}
};

class NamespaceNotEmptyError : public LanceNamespaceError {
  public:
    explicit NamespaceNotEmptyError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_EMPTY) {}
};

class TableNotFoundError : public LanceNamespaceError {
  public:
    explicit TableNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_NOT_FOUND) {}
};

class TableAlreadyExistsError : public LanceNamespaceError {
  public:
    explicit TableAlreadyExistsError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_ALREADY_EXISTS) {}
};

class TableIndexNotFoundError : public LanceNamespaceError {
  public:
    explicit TableIndexNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_INDEX_NOT_FOUND) {}
};

class TableIndexAlreadyExistsError : public LanceNamespaceError {
  public:
    explicit TableIndexAlreadyExistsError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_INDEX_ALREADY_EXISTS) {}
};

class TableTagNotFoundError : public LanceNamespaceError {
  public:
    explicit TableTagNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_TAG_NOT_FOUND) {}
};

class TableTagAlreadyExistsError : public LanceNamespaceError {
  public:
    explicit TableTagAlreadyExistsError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_TAG_ALREADY_EXISTS) {}
};

class TransactionNotFoundError : public LanceNamespaceError {
  public:
    explicit TransactionNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TRANSACTION_NOT_FOUND) {}
};

class TableVersionNotFoundError : public LanceNamespaceError {
  public:
    explicit TableVersionNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_VERSION_NOT_FOUND) {}
};

class TableColumnNotFoundError : public LanceNamespaceError {
  public:
    explicit TableColumnNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_COLUMN_NOT_FOUND) {}
};

class InvalidInputError : public LanceNamespaceError {
  public:
    explicit InvalidInputError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_INVALID_INPUT) {}
};

class ConcurrentModificationError : public LanceNamespaceError {
  public:
    explicit ConcurrentModificationError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_CONCURRENT_MODIFICATION) {}
};

class PermissionDeniedError : public LanceNamespaceError {
  public:
    explicit PermissionDeniedError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_PERMISSION_DENIED) {}
};

class UnauthenticatedError : public LanceNamespaceError {
  public:
    explicit UnauthenticatedError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_UNAUTHENTICATED) {}
};

class ServiceUnavailableError : public LanceNamespaceError {
  public:
    explicit ServiceUnavailableError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_SERVICE_UNAVAILABLE) {}
};

class InternalError : public LanceNamespaceError {
  public:
    explicit InternalError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_INTERNAL) {}
};

class InvalidTableStateError : public LanceNamespaceError {
  public:
    explicit InvalidTableStateError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_INVALID_TABLE_STATE) {}
};

class TableSchemaValidationError : public LanceNamespaceError {
  public:
    explicit TableSchemaValidationError(std::string message)
        : LanceNamespaceError(std::move(message),
                              LANCE_NAMESPACE_ERROR_TABLE_SCHEMA_VALIDATION_ERROR) {}
};

class ThrottlingError : public LanceNamespaceError {
  public:
    explicit ThrottlingError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_THROTTLING) {}
};

class TableBranchNotFoundError : public LanceNamespaceError {
  public:
    explicit TableBranchNotFoundError(std::string message)
        : LanceNamespaceError(std::move(message), LANCE_NAMESPACE_ERROR_TABLE_BRANCH_NOT_FOUND) {}
};

class TableBranchAlreadyExistsError : public LanceNamespaceError {
  public:
    explicit TableBranchAlreadyExistsError(std::string message)
        : LanceNamespaceError(std::move(message),
                              LANCE_NAMESPACE_ERROR_TABLE_BRANCH_ALREADY_EXISTS) {}
};

inline std::unique_ptr<LanceNamespaceError> from_error_code(lance_namespace_error_code_t code, std::string message) {
    switch (code) {
        case LANCE_NAMESPACE_ERROR_UNSUPPORTED:
            return std::make_unique<UnsupportedOperationError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_FOUND:
            return std::make_unique<NamespaceNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_NAMESPACE_ALREADY_EXISTS:
            return std::make_unique<NamespaceAlreadyExistsError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_NAMESPACE_NOT_EMPTY:
            return std::make_unique<NamespaceNotEmptyError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_NOT_FOUND:
            return std::make_unique<TableNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_ALREADY_EXISTS:
            return std::make_unique<TableAlreadyExistsError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_INDEX_NOT_FOUND:
            return std::make_unique<TableIndexNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_INDEX_ALREADY_EXISTS:
            return std::make_unique<TableIndexAlreadyExistsError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_TAG_NOT_FOUND:
            return std::make_unique<TableTagNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_TAG_ALREADY_EXISTS:
            return std::make_unique<TableTagAlreadyExistsError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TRANSACTION_NOT_FOUND:
            return std::make_unique<TransactionNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_VERSION_NOT_FOUND:
            return std::make_unique<TableVersionNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_COLUMN_NOT_FOUND:
            return std::make_unique<TableColumnNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_INVALID_INPUT:
            return std::make_unique<InvalidInputError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_CONCURRENT_MODIFICATION:
            return std::make_unique<ConcurrentModificationError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_PERMISSION_DENIED:
            return std::make_unique<PermissionDeniedError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_UNAUTHENTICATED:
            return std::make_unique<UnauthenticatedError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_SERVICE_UNAVAILABLE:
            return std::make_unique<ServiceUnavailableError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_INVALID_TABLE_STATE:
            return std::make_unique<InvalidTableStateError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_SCHEMA_VALIDATION_ERROR:
            return std::make_unique<TableSchemaValidationError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_THROTTLING:
            return std::make_unique<ThrottlingError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_BRANCH_NOT_FOUND:
            return std::make_unique<TableBranchNotFoundError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_TABLE_BRANCH_ALREADY_EXISTS:
            return std::make_unique<TableBranchAlreadyExistsError>(std::move(message));
        case LANCE_NAMESPACE_ERROR_INTERNAL:
        default:
            return std::make_unique<InternalError>(std::move(message));
    }
}

}  // namespace lance_namespace

#endif  // LANCE_NAMESPACE_ERRORS_HPP
