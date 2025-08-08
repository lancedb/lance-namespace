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
package com.lancedb.lance.namespace.unity;

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.util.HttpClient;

public class UnityToLanceErrorConverter {

  public static LanceNamespaceException convert(HttpClient.HttpException e, String operation) {
    return convertHttpError(e.getCode(), e.getMessage(), e.getResponseBody(), operation);
  }

  private static LanceNamespaceException convertHttpError(
      int code, String message, String responseBody, String operation) {

    // Map Unity Catalog error codes to Lance namespace exceptions
    switch (code) {
      case 400:
        return LanceNamespaceException.badRequest(
            String.format("%s failed: %s", operation, message),
            "INVALID_ARGUMENT",
            operation,
            responseBody);
      case 401:
        return LanceNamespaceException.unauthorized(
            String.format("%s failed: Unauthorized", operation),
            "PERMISSION_DENIED",
            operation,
            message);
      case 403:
        return LanceNamespaceException.forbidden(
            String.format("%s failed: Forbidden", operation),
            "PERMISSION_DENIED",
            operation,
            message);
      case 404:
        return LanceNamespaceException.notFound(
            String.format("%s failed: Not found", operation), "NOT_FOUND", operation, message);
      case 409:
        return LanceNamespaceException.conflict(
            String.format("%s failed: Already exists", operation),
            "ALREADY_EXISTS",
            operation,
            message);
      case 500:
      case 502:
      case 503:
      case 504:
        return LanceNamespaceException.serviceUnavailable(
            String.format("%s failed: Service unavailable", operation),
            "UNAVAILABLE",
            operation,
            message);
      default:
        return LanceNamespaceException.serverError(
            String.format("%s failed with code %d", operation, code),
            "UNKNOWN",
            operation,
            message);
    }
  }

  public static LanceNamespaceException wrapException(Exception e, String operation) {
    if (e instanceof LanceNamespaceException) {
      return (LanceNamespaceException) e;
    } else if (e instanceof HttpClient.HttpException) {
      return convert((HttpClient.HttpException) e, operation);
    } else {
      return LanceNamespaceException.serverError(
          String.format("%s failed: %s", operation, e.getMessage()),
          "UNKNOWN",
          operation,
          e.getMessage());
    }
  }
}
