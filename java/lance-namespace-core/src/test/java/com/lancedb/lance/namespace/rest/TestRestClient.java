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
package com.lancedb.lance.namespace.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests for RestClient. */
public class TestRestClient {
  private HttpServer server;
  private RestClient client;
  private int port;

  @BeforeEach
  public void setUp() throws IOException {
    // Start a simple HTTP server for testing
    server = HttpServer.create(new InetSocketAddress(0), 0);
    port = server.getAddress().getPort();
    server.setExecutor(null);
    server.start();

    client = RestClient.builder().baseUrl("http://localhost:" + port).build();
  }

  @AfterEach
  public void tearDown() throws IOException {
    if (client != null) {
      client.close();
    }
    if (server != null) {
      server.stop(0);
    }
  }

  @Test
  public void testGet() throws IOException {
    TestResponse expectedResponse = new TestResponse("test", 123);

    server.createContext(
        "/test",
        exchange -> {
          if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
          }

          String json = new ObjectMapper().writeValueAsString(expectedResponse);
          byte[] response = json.getBytes(StandardCharsets.UTF_8);
          exchange.getResponseHeaders().add("Content-Type", "application/json");
          exchange.sendResponseHeaders(200, response.length);
          try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
          }
        });

    TestResponse response = client.get("/test", TestResponse.class);
    assertNotNull(response);
    assertEquals(expectedResponse.getName(), response.getName());
    assertEquals(expectedResponse.getValue(), response.getValue());
  }

  @Test
  public void testGetWithQueryParams() throws IOException {
    server.createContext(
        "/search",
        exchange -> {
          String query = exchange.getRequestURI().getQuery();
          assertEquals("q=test&limit=10", query);

          exchange.sendResponseHeaders(204, -1);
        });

    Map<String, String> params = new HashMap<>();
    params.put("q", "test");
    params.put("limit", "10");

    client.get("/search", params, Void.class);
  }

  @Test
  public void testPost() throws IOException {
    TestRequest request = new TestRequest("create", "data");
    TestResponse expectedResponse = new TestResponse("created", 1);

    server.createContext(
        "/create",
        exchange -> {
          if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
          }

          // Read request body
          java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
          int bytesRead;
          byte[] data = new byte[1024];
          while ((bytesRead = exchange.getRequestBody().read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
          }
          byte[] requestBytes = buffer.toByteArray();
          TestRequest receivedRequest =
              new ObjectMapper().readValue(requestBytes, TestRequest.class);
          assertEquals(request.getAction(), receivedRequest.getAction());
          assertEquals(request.getData(), receivedRequest.getData());

          // Send response
          String json = new ObjectMapper().writeValueAsString(expectedResponse);
          byte[] response = json.getBytes(StandardCharsets.UTF_8);
          exchange.getResponseHeaders().add("Content-Type", "application/json");
          exchange.sendResponseHeaders(200, response.length);
          try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
          }
        });

    TestResponse response = client.post("/create", request, TestResponse.class);
    assertNotNull(response);
    assertEquals(expectedResponse.getName(), response.getName());
    assertEquals(expectedResponse.getValue(), response.getValue());
  }

  @Test
  public void testDelete() throws IOException {
    server.createContext(
        "/delete/123",
        exchange -> {
          if (!"DELETE".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
          }
          exchange.sendResponseHeaders(204, -1);
        });

    client.delete("/delete/123");
  }

  @Test
  public void testErrorHandling() {
    server.createContext(
        "/error",
        exchange -> {
          String errorJson = "{\"error\": \"Bad Request\"}";
          byte[] response = errorJson.getBytes(StandardCharsets.UTF_8);
          exchange.getResponseHeaders().add("Content-Type", "application/json");
          exchange.sendResponseHeaders(400, response.length);
          try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
          }
        });

    RestClient.RestClientException exception =
        assertThrows(
            RestClient.RestClientException.class, () -> client.get("/error", TestResponse.class));

    assertEquals(400, exception.getStatusCode());
    assertTrue(exception.getResponseBody().contains("Bad Request"));
  }

  @Test
  public void testRetry() throws IOException {
    AtomicInteger attempts = new AtomicInteger(0);

    server.createContext(
        "/retry",
        exchange -> {
          int attempt = attempts.incrementAndGet();
          if (attempt < 3) {
            // Return 503 for first two attempts
            exchange.sendResponseHeaders(503, -1);
          } else {
            // Success on third attempt
            String json = "{\"name\": \"success\", \"value\": 42}";
            byte[] response = json.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = exchange.getResponseBody()) {
              os.write(response);
            }
          }
        });

    RestClient retryClient =
        RestClient.builder()
            .baseUrl("http://localhost:" + port)
            .maxRetries(3)
            .retryDelayMs(100)
            .build();

    try {
      TestResponse response = retryClient.get("/retry", TestResponse.class);
      assertNotNull(response);
      assertEquals("success", response.getName());
      assertEquals(42, response.getValue());
      assertEquals(3, attempts.get());
    } finally {
      retryClient.close();
    }
  }

  @Test
  public void testHeaders() throws IOException {
    Map<String, String> headers = new HashMap<>();
    headers.put("Authorization", "Bearer token123");
    headers.put("X-Custom-Header", "custom-value");

    RestClient clientWithHeaders =
        RestClient.builder().baseUrl("http://localhost:" + port).defaultHeaders(headers).build();

    try {
      server.createContext(
          "/headers",
          exchange -> {
            assertEquals("Bearer token123", exchange.getRequestHeaders().getFirst("Authorization"));
            assertEquals("custom-value", exchange.getRequestHeaders().getFirst("X-Custom-Header"));
            exchange.sendResponseHeaders(204, -1);
          });

      clientWithHeaders.get("/headers", Void.class);
    } finally {
      clientWithHeaders.close();
    }
  }

  @Test
  public void testNullResponse() throws IOException {
    server.createContext(
        "/empty",
        exchange -> {
          exchange.sendResponseHeaders(204, -1);
        });

    TestResponse response = client.get("/empty", TestResponse.class);
    assertNull(response);
  }

  @Test
  public void testPutAndPatch() throws IOException {
    TestRequest request = new TestRequest("update", "newdata");

    server.createContext(
        "/put",
        exchange -> {
          assertEquals("PUT", exchange.getRequestMethod());
          exchange.sendResponseHeaders(204, -1);
        });

    server.createContext(
        "/patch",
        exchange -> {
          assertEquals("PATCH", exchange.getRequestMethod());
          exchange.sendResponseHeaders(204, -1);
        });

    client.put("/put", request, Void.class);
    client.patch("/patch", request, Void.class);
  }

  // Test model classes
  static class TestRequest {
    private String action;
    private String data;

    public TestRequest() {}

    public TestRequest(String action, String data) {
      this.action = action;
      this.data = data;
    }

    public String getAction() {
      return action;
    }

    public void setAction(String action) {
      this.action = action;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }
  }

  static class TestResponse {
    private String name;
    private int value;

    public TestResponse() {}

    public TestResponse(String name, int value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }
  }
}
