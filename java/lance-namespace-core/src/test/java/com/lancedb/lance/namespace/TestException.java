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
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestException {

  @Test
  public void testParseApiException() {
    // Case 1: default ApiException
    ApiException apiError = new ApiException();
    LanceNamespaceException nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(0, nsError.getCode());
    assertNull(nsError.getResponseBody());

    // Case 2: ApiException from io error
    apiError = new ApiException(new IOException("connect timeout"));
    nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(0, nsError.getCode());
    assertNull(nsError.getResponseBody());

    // Case 3: ApiException from message
    apiError = new ApiException("connect timeout");
    nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(0, nsError.getCode());
    assertNull(nsError.getResponseBody());

    // Case 4: ApiException with response body in wrong format
    String jsonResponse = "{,,}"; // bad json
    apiError =
        new ApiException(
            "message", new IOException("connect timeout"), 123, Maps.newHashMap(), jsonResponse);
    nsError = new LanceNamespaceException(apiError);
    assertFalse(nsError.getErrorResponse().isPresent());
    assertEquals(123, nsError.getCode());
    assertEquals(jsonResponse, nsError.getResponseBody());

    // Case 5: ApiException with response body in correct format
    jsonResponse =
        "{"
            + "\"type\":\"/errors/not-found-error\","
            + "\"title\":\"Not found Error\","
            + "\"status\":\"404\","
            + "\"instance\":\"/v1/namespaces\","
            + "\"detail\":\"Namespace not found\""
            + "}";
    apiError =
        new ApiException(
            "message", new IOException("connect timeout"), 123, Maps.newHashMap(), jsonResponse);
    nsError = new LanceNamespaceException(apiError);
    ErrorResponse errorResponse = nsError.getErrorResponse().get();
    assertEquals("/errors/not-found-error", errorResponse.getType());
    assertEquals("Not found Error", errorResponse.getTitle());
    assertEquals(404, errorResponse.getStatus());
    assertEquals("/v1/namespaces", errorResponse.getInstance());
    assertEquals("Namespace not found", errorResponse.getDetail());
  }
}
