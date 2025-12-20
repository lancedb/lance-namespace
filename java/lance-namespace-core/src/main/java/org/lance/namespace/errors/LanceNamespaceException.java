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

import javax.annotation.Nullable;

/**
 * Base exception for all Lance Namespace errors.
 *
 * <p>All Lance Namespace operations may throw exceptions that extend this class. Each exception has
 * an associated {@link ErrorCode} that uniquely identifies the error type.
 */
public class LanceNamespaceException extends RuntimeException {
  private final ErrorCode errorCode;
  @Nullable private final String detail;
  @Nullable private final String instance;

  /**
   * Constructs a new LanceNamespaceException with the specified error code and message.
   *
   * @param errorCode the error code identifying the error type
   * @param message the error message
   */
  public LanceNamespaceException(ErrorCode errorCode, String message) {
    this(errorCode, message, null, null, null);
  }

  /**
   * Constructs a new LanceNamespaceException with the specified error code, message, and cause.
   *
   * @param errorCode the error code identifying the error type
   * @param message the error message
   * @param cause the cause of this exception
   */
  public LanceNamespaceException(ErrorCode errorCode, String message, Throwable cause) {
    this(errorCode, message, null, null, cause);
  }

  /**
   * Constructs a new LanceNamespaceException with full details.
   *
   * @param errorCode the error code identifying the error type
   * @param message the error message
   * @param detail optional detailed explanation
   * @param instance optional identifier for this specific error occurrence
   */
  public LanceNamespaceException(
      ErrorCode errorCode, String message, @Nullable String detail, @Nullable String instance) {
    this(errorCode, message, detail, instance, null);
  }

  /**
   * Constructs a new LanceNamespaceException with full details and a cause.
   *
   * @param errorCode the error code identifying the error type
   * @param message the error message
   * @param detail optional detailed explanation
   * @param instance optional identifier for this specific error occurrence
   * @param cause the cause of this exception
   */
  public LanceNamespaceException(
      ErrorCode errorCode,
      String message,
      @Nullable String detail,
      @Nullable String instance,
      @Nullable Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
    this.detail = detail;
    this.instance = instance;
  }

  /**
   * Returns the error code identifying the error type.
   *
   * @return the error code
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  /**
   * Returns the numeric error code.
   *
   * @return the numeric error code
   */
  public int getCode() {
    return errorCode.getCode();
  }

  /**
   * Returns the optional detailed explanation of the error.
   *
   * @return the detail string, or null if not set
   */
  @Nullable
  public String getDetail() {
    return detail;
  }

  /**
   * Returns the optional identifier for this specific error occurrence.
   *
   * @return the instance string, or null if not set
   */
  @Nullable
  public String getInstance() {
    return instance;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getClass().getSimpleName())
        .append("{code=")
        .append(errorCode.getCode())
        .append(", message='")
        .append(getMessage())
        .append("'");
    if (detail != null) {
      sb.append(", detail='").append(detail).append("'");
    }
    if (instance != null) {
      sb.append(", instance='").append(instance).append("'");
    }
    sb.append("}");
    return sb.toString();
  }
}
