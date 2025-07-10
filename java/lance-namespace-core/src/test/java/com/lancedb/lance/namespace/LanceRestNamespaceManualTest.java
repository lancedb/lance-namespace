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

import com.lancedb.lance.namespace.client.apache.ApiClient;
import com.lancedb.lance.namespace.model.*;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Test for LanceRestNamespace. Update the placeholder values before running. */
public class LanceRestNamespaceManualTest {

  // Update these values before running
  private static final String DATABASE = "albert1-d5ac3e";
  private static final String API_KEY =
      "sk_33T2Z6OF55FPNP7PLR3SFTIKEQNCTVL6MI7CGFH3XYPXL6W3TKEQ====";
  private static final String HOST_OVERRIDE = null; // or "https://your-enterprise-server.com"
  private static final String REGION = "us-east-1";

  // Test data
  private static final String TEST_TABLE_NAME = "lancedb-canary-table1";

  private LanceRestNamespace namespace;

  @BeforeEach
  public void setUp() {
    namespace = initializeClient();
  }

  @Test
  public void testDescribeTable() {
    System.out.println("=== Test: Describe Table ===");

    DescribeTableRequest request = new DescribeTableRequest();
    // Table name goes in URL path, not request body
    // Database comes from headers automatically
    // Optional LanceDB fields:
    request.setName(TEST_TABLE_NAME);
    request.setVersion(null); // Latest version
    request.setWithTableUri(true); // Include table URI

    DescribeTableResponse response = namespace.describeTable(request);
    System.out.println("Table Description: " + response);

    assertNotNull(response, "Response should not be null");
    System.out.println("Test passed!");
  }

  private LanceRestNamespace initializeClient() {
    Map<String, String> config = new HashMap<>();
    config.put("headers.x-lancedb-database", DATABASE);
    config.put("headers.x-api-key", API_KEY);

    if (HOST_OVERRIDE != null) {
      config.put("host_override", HOST_OVERRIDE);
    }
    config.put("region", REGION);

    ApiClient apiClient = new ApiClient();

    // Set base URL based on configuration
    String baseUrl;
    if (HOST_OVERRIDE != null) {
      baseUrl = HOST_OVERRIDE;
    } else {
      baseUrl = String.format("https://%s.%s.api.lancedb.com", DATABASE, REGION);
    }
    apiClient.setBasePath(baseUrl);

    System.out.println("Initialized client with base URL: " + baseUrl);

    return new LanceRestNamespace(apiClient, config);
  }
}
