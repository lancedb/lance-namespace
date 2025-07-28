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

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestException {

  @Test
  public void testDefaultApiException() {
    ApiException apiError = new ApiException();
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(0, nsError.getCode());
  }

  @Test
  public void testApiExceptionFromIOError() {
    ApiException apiError = new ApiException(new IOException("connect timeout"));
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(0, nsError.getCode());
  }

  @Test
  public void testApiExceptionFromMessage() {
    ApiException apiError = new ApiException("connect timeout");
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(0, nsError.getCode());
    assertEquals("connect timeout", nsError.getMessage());
  }

  @Test
  public void testApiExceptionWithInvalidJsonResponse() {
    String jsonResponse = "{,,}"; // bad json
    ApiException apiError =
        new ApiException(
            "message", new IOException("connect timeout"), 123, Maps.newHashMap(), jsonResponse);
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(123, nsError.getCode());
    assertEquals("{,,}", nsError.getMessage());
  }

  @Test
  public void testApiExceptionWithValidJsonResponse() {
    String jsonResponse =
        "{"
            + "\"type\":\"/errors/not-found-error\","
            + "\"error\":\"Not found Error\","
            + "\"code\":\"404\","
            + "\"instance\":\"/v1/namespaces\","
            + "\"detail\":\"Namespace not found\""
            + "}";
    ApiException apiError =
        new ApiException(
            "message", new IOException("connect timeout"), 123, Maps.newHashMap(), jsonResponse);
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    ErrorResponse errorResponse = nsError.getErrorResponse().get();
    assertEquals("Not found Error", errorResponse.getError());
    assertEquals("/errors/not-found-error", errorResponse.getType());
    assertEquals(404, errorResponse.getCode());
    assertEquals("/v1/namespaces", errorResponse.getInstance());
    assertEquals("Namespace not found", errorResponse.getDetail());
  }

  @Test
  public void testApiExceptionWithUnknownFields() {
    String jsonResponse =
        "{"
            + "\"type\":\"/errors/validation-error\","
            + "\"error\":\"Validation Error\","
            + "\"code\":\"400\","
            + "\"instance\":\"/v1/tables\","
            + "\"detail\":\"Invalid table name\","
            + "\"unknownField\":\"should_be_ignored\","
            + "\"futureExtension\":123,"
            + "\"nestedUnknown\":{\"some\":\"data\"},"
            + "\"arrayUnknown\":[\"item1\",\"item2\"]"
            + "}";
    ApiException apiError =
        new ApiException(
            "message", new IOException("validation error"), 400, Maps.newHashMap(), jsonResponse);
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    ErrorResponse errorResponse = nsError.getErrorResponse().get();
    assertEquals("Validation Error", errorResponse.getError());
    assertEquals("/errors/validation-error", errorResponse.getType());
    assertEquals(400, errorResponse.getCode());
    assertEquals("/v1/tables", errorResponse.getInstance());
    assertEquals("Invalid table name", errorResponse.getDetail());
  }
}
