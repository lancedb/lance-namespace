/*
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
 */
package org.lance.namespace.errors;

/**
 * Lance Namespace error codes.
 *
 * <p>These codes are globally unique across all Lance Namespace implementations.
 */
public enum ErrorCode {
  UNSUPPORTED(0, "Operation not supported"),
  NAMESPACE_NOT_FOUND(1, "Namespace not found"),
  NAMESPACE_ALREADY_EXISTS(2, "Namespace already exists"),
  NAMESPACE_NOT_EMPTY(3, "Namespace not empty"),
  TABLE_NOT_FOUND(4, "Table not found"),
  TABLE_ALREADY_EXISTS(5, "Table already exists"),
  TABLE_INDEX_NOT_FOUND(6, "Table index not found"),
  TABLE_INDEX_ALREADY_EXISTS(7, "Table index already exists"),
  TABLE_TAG_NOT_FOUND(8, "Table tag not found"),
  TABLE_TAG_ALREADY_EXISTS(9, "Table tag already exists"),
  TRANSACTION_NOT_FOUND(10, "Transaction not found"),
  TABLE_VERSION_NOT_FOUND(11, "Table version not found"),
  TABLE_COLUMN_NOT_FOUND(12, "Table column not found"),
  INVALID_INPUT(13, "Invalid input"),
  CONCURRENT_MODIFICATION(14, "Concurrent modification"),
  PERMISSION_DENIED(15, "Permission denied"),
  UNAUTHENTICATED(16, "Unauthenticated"),
  SERVICE_UNAVAILABLE(17, "Service unavailable"),
  INTERNAL(18, "Internal error"),
  INVALID_TABLE_STATE(19, "Invalid table state"),
  TABLE_SCHEMA_VALIDATION_ERROR(20, "Table schema validation error");

  private final int code;
  private final String description;

  ErrorCode(int code, String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Returns the numeric error code.
   *
   * @return the numeric error code
   */
  public int getCode() {
    return code;
  }

  /**
   * Returns a human-readable description of the error.
   *
   * @return the error description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the ErrorCode for a given numeric code.
   *
   * @param code the numeric error code
   * @return the corresponding ErrorCode, or INTERNAL if not found
   */
  public static ErrorCode fromCode(int code) {
    for (ErrorCode ec : values()) {
      if (ec.code == code) {
        return ec;
      }
    }
    return INTERNAL;
  }
}
