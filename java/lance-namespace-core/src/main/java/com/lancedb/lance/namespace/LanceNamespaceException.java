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
package com.lancedb.lance.namespace;

import com.lancedb.lance.namespace.client.apache.ApiException;
import com.lancedb.lance.namespace.model.ErrorResponse;
import com.lancedb.lance.namespace.util.JsonUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LanceNamespaceException extends RuntimeException {
  private static final Logger LOG = LoggerFactory.getLogger(LanceNamespaceException.class);

  private final int UNKNOWN_ERROR_CODE = -1;

  private final int code;
  private final Optional<ErrorResponse> errorResponse;

  public LanceNamespaceException(int code, String responseBody) {
    super(responseBody);
    this.code = code;
    this.errorResponse = Optional.empty();
  }

  public LanceNamespaceException(ApiException e) {
    super(e.getResponseBody() == null ? e.getMessage() : e.getResponseBody(), e);

    Optional<ErrorResponse> res = Optional.empty();
    this.code = e.getCode();
    if (e.getResponseBody() != null) {
      try {
        res = Optional.of(JsonUtil.mapper().readValue(e.getResponseBody(), ErrorResponse.class));
      } catch (JsonProcessingException ex) {
        // ignore
      }
    }

    this.errorResponse = res;
  }

  public LanceNamespaceException(ErrorResponse errorResponse) {
    super(errorResponse.toString());
    this.code = errorResponse.getCode() == null ? UNKNOWN_ERROR_CODE : errorResponse.getCode();
    this.errorResponse = Optional.of(errorResponse);
  }

  public int getCode() {
    return code;
  }

  public Optional<ErrorResponse> getErrorResponse() {
    return errorResponse;
  }

  public static LanceNamespaceException badRequest(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(400);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException unauthorized(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(401);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException forbidden(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(403);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException notFound(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(404);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException unsupportedOperation(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(406);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException conflict(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(409);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException serviceUnavailable(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(504);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }

  public static LanceNamespaceException serverError(
      String error, String type, String instance, String detail) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setError(error);
    errorResponse.type(type);
    errorResponse.setCode(500);
    errorResponse.setDetail(detail);
    errorResponse.setInstance(instance);
    return new LanceNamespaceException(errorResponse);
  }
}
