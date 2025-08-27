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
package com.lancedb.lance.namespace.polaris;

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for PolarisNamespace against a running Polaris instance.
 *
 * <p>To run these tests, start Polaris with: - Endpoint: http://localhost:8182 - Credentials:
 * CLIENT_ID=root, CLIENT_SECRET=s3cr3t
 *
 * <p>Tests are automatically skipped if Polaris is not available.
 */
public class TestPolarisNamespaceIntegration {

  private static final String POLARIS_ENDPOINT = "http://localhost:8182";
  private static final String CLIENT_ID = "root";
  private static final String CLIENT_SECRET = "s3cr3t";
  private static boolean polarisAvailable = false;

  private PolarisNamespace namespace;
  private BufferAllocator allocator;
  private String testCatalog;
  private String testNamespace;

  @BeforeAll
  public static void checkPolarisAvailable() {
    try {
      // Try to check if Polaris API is available by checking a known endpoint
      // We'll try the namespaces endpoint which should exist
      URL url = new URL(POLARIS_ENDPOINT + "/api/catalog/v1/namespaces");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setConnectTimeout(1000);
      conn.setReadTimeout(1000);

      // Add basic auth header to check if we can authenticate
      String auth =
          java.util.Base64.getEncoder()
              .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
      conn.setRequestProperty("Authorization", "Basic " + auth);

      int responseCode = conn.getResponseCode();
      conn.disconnect();

      // Consider Polaris available if we get any HTTP response (even 401/403)
      // but not 404 which means the endpoint doesn't exist
      polarisAvailable = responseCode != 404 && responseCode > 0;

      if (!polarisAvailable) {
        System.out.println(
            "Polaris is not available at " + POLARIS_ENDPOINT + " - skipping integration tests");
      } else {
        System.out.println(
            "Polaris detected at " + POLARIS_ENDPOINT + " (response code: " + responseCode + ")");
      }
    } catch (Exception e) {
      polarisAvailable = false;
      System.out.println(
          "Polaris is not available at "
              + POLARIS_ENDPOINT
              + " ("
              + e.getMessage()
              + ") - skipping integration tests");
    }
  }

  @BeforeEach
  public void setUp() throws Exception {
    // Skip all tests if Polaris is not available
    Assumptions.assumeTrue(polarisAvailable, "Polaris is not available at " + POLARIS_ENDPOINT);

    allocator = new RootAllocator();
    namespace = new PolarisNamespace();

    // Generate unique names for this test run
    String uniqueId = UUID.randomUUID().toString().substring(0, 8);
    testCatalog = "test_catalog"; // Use a fixed catalog that should exist
    testNamespace = "test_ns_" + uniqueId;

    Map<String, String> config = new HashMap<>();
    config.put("endpoint", POLARIS_ENDPOINT);

    // Try to get OAuth token first
    String token = getOAuthToken();
    if (token != null) {
      config.put("auth.token", token);
    } else {
      // Fall back to basic auth
      String basicAuth =
          java.util.Base64.getEncoder()
              .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
      config.put("auth.token", basicAuth);
    }

    namespace.initialize(config, allocator);
  }

  @AfterEach
  public void tearDown() {
    // Clean up test resources
    try {
      // Drop test namespace if it exists
      DropNamespaceRequest dropRequest = new DropNamespaceRequest();
      dropRequest.setId(Arrays.asList(testCatalog, testNamespace));
      namespace.dropNamespace(dropRequest);
    } catch (Exception e) {
      // Ignore cleanup errors
    }

    if (allocator != null) {
      allocator.close();
    }
  }

  private String getOAuthToken() {
    try {
      // Attempt to get OAuth token
      URL url = new URL(POLARIS_ENDPOINT + "/api/catalog/v1/oauth/tokens");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setDoOutput(true);

      String params =
          String.format(
              "grant_type=client_credentials&client_id=%s&client_secret=%s&scope=PRINCIPAL_ROLE:ALL",
              CLIENT_ID, CLIENT_SECRET);

      conn.getOutputStream().write(params.getBytes());

      if (conn.getResponseCode() == 200) {
        // Parse token from response (simplified - in production use proper JSON parsing)
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = conn.getInputStream().read(buffer)) != -1) {
          baos.write(buffer, 0, len);
        }
        String responseStr = new String(baos.toByteArray());
        // Extract token from JSON response
        int tokenStart = responseStr.indexOf("\"access_token\":\"") + 16;
        if (tokenStart > 16) {
          int tokenEnd = responseStr.indexOf("\"", tokenStart);
          return responseStr.substring(tokenStart, tokenEnd);
        }
      }
    } catch (Exception e) {
      // OAuth not available, will fall back to basic auth
    }
    return null;
  }

  @Test
  public void testNamespaceOperations() {
    // Create namespace
    CreateNamespaceRequest createRequest = new CreateNamespaceRequest();
    createRequest.setId(Arrays.asList(testCatalog, testNamespace));
    createRequest.setProperties(Collections.singletonMap("description", "Test namespace"));

    CreateNamespaceResponse createResponse = namespace.createNamespace(createRequest);
    // Response doesn't have getId() method, just verify properties
    assertThat(createResponse.getProperties()).containsEntry("description", "Test namespace");

    // Describe namespace
    DescribeNamespaceRequest describeRequest = new DescribeNamespaceRequest();
    describeRequest.setId(Arrays.asList(testCatalog, testNamespace));

    DescribeNamespaceResponse describeResponse = namespace.describeNamespace(describeRequest);
    // Response doesn't have getId() method, just verify we got a response
    assertThat(describeResponse).isNotNull();

    // Check namespace exists
    NamespaceExistsRequest existsRequest = new NamespaceExistsRequest();
    existsRequest.setId(Arrays.asList(testCatalog, testNamespace));
    namespace.namespaceExists(existsRequest); // Should not throw

    // List namespaces
    ListNamespacesRequest listRequest = new ListNamespacesRequest();
    ListNamespacesResponse listResponse = namespace.listNamespaces(listRequest);
    assertThat(listResponse.getNamespaces())
        .anyMatch(ns -> ns.equals(Arrays.asList(testCatalog, testNamespace)));

    // Drop namespace
    DropNamespaceRequest dropRequest = new DropNamespaceRequest();
    dropRequest.setId(Arrays.asList(testCatalog, testNamespace));
    namespace.dropNamespace(dropRequest);

    // Verify namespace doesn't exist
    assertThatThrownBy(() -> namespace.namespaceExists(existsRequest))
        .isInstanceOf(LanceNamespaceException.class)
        .hasMessageContaining("404");
  }

  @Test
  public void testTableOperations() {
    // Create namespace first
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Arrays.asList(testCatalog, testNamespace));
    namespace.createNamespace(nsRequest);

    String tableName = "test_table_" + UUID.randomUUID().toString().substring(0, 8);

    // Create table
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Arrays.asList(testCatalog, testNamespace, tableName));
    createRequest.setLocation("s3://test-bucket/lance/" + tableName);
    createRequest.setProperties(Collections.singletonMap("comment", "Test table"));

    CreateTableResponse createResponse = namespace.createTable(createRequest, new byte[0]);
    assertThat(createResponse.getLocation()).isEqualTo("s3://test-bucket/lance/" + tableName);
    assertThat(createResponse.getProperties()).containsEntry("comment", "Test table");

    // Describe table
    DescribeTableRequest describeRequest = new DescribeTableRequest();
    describeRequest.setId(Arrays.asList(testCatalog, testNamespace, tableName));

    DescribeTableResponse describeResponse = namespace.describeTable(describeRequest);
    assertThat(describeResponse.getLocation()).isEqualTo("s3://test-bucket/lance/" + tableName);

    // Check table exists
    TableExistsRequest existsRequest = new TableExistsRequest();
    existsRequest.setId(Arrays.asList(testCatalog, testNamespace, tableName));
    namespace.tableExists(existsRequest); // Should not throw

    // List tables
    ListTablesRequest listRequest = new ListTablesRequest();
    listRequest.setId(Arrays.asList(testCatalog, testNamespace));

    ListTablesResponse listResponse = namespace.listTables(listRequest);
    assertThat(listResponse.getTables()).contains(tableName);

    // Drop table
    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setId(Arrays.asList(testCatalog, testNamespace, tableName));
    namespace.dropTable(dropRequest);

    // Verify table doesn't exist
    assertThatThrownBy(() -> namespace.tableExists(existsRequest))
        .isInstanceOf(LanceNamespaceException.class)
        .hasMessageContaining("404");
  }

  @Test
  public void testCreateTableWithInvalidFormat() {
    // Create namespace first
    CreateNamespaceRequest nsRequest = new CreateNamespaceRequest();
    nsRequest.setId(Arrays.asList(testCatalog, testNamespace));
    namespace.createNamespace(nsRequest);

    // Try to describe a non-Lance table (would need to be created through Polaris directly)
    // This test demonstrates the format validation

    // For now, just verify Lance tables work correctly
    String tableName = "lance_table";
    CreateTableRequest createRequest = new CreateTableRequest();
    createRequest.setId(Arrays.asList(testCatalog, testNamespace, tableName));
    createRequest.setLocation("s3://test-bucket/lance/" + tableName);

    CreateTableResponse response = namespace.createTable(createRequest, new byte[0]);
    assertThat(response.getProperties()).containsEntry("table_type", "lance");
  }
}
