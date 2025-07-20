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
package com.lancedb.lance.namespace.test;

import com.lancedb.lance.namespace.LanceRestNamespace;
import com.lancedb.lance.namespace.client.apache.ApiClient;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Base test class for Lance Namespace tests. Provides common setup and configuration for all test
 * classes.
 */
public abstract class BaseNamespaceTest {

  // Configuration from environment variables
  protected static String DATABASE;
  protected static String API_KEY;
  protected static String HOST_OVERRIDE;
  protected static String REGION;

  protected LanceRestNamespace namespace;
  protected BufferAllocator allocator;

  @BeforeAll
  public static void setUpClass() {
    // Get configuration from environment variables
    DATABASE = System.getenv("LANCEDB_DB");
    API_KEY = System.getenv("LANCEDB_API_KEY");
    HOST_OVERRIDE = System.getenv("LANCEDB_HOST_OVERRIDE");
    REGION = System.getenv("LANCEDB_REGION");

    // Default values if not set
    if (REGION == null) {
      REGION = "us-east-1";
    }

    // TODO(claude) only API_KEY is required. DATABASE can be a random database name
    if (DATABASE != null && API_KEY != null) {
      System.out.println("Using configuration:");
      System.out.println("  Database: " + DATABASE);
      System.out.println("  Region: " + REGION);
      System.out.println("  Host Override: " + (HOST_OVERRIDE != null ? HOST_OVERRIDE : "none"));
    }
  }

  @BeforeEach
  public void setUp() {
    // Only initialize if required environment variables are set
    if (DATABASE != null && API_KEY != null) {
      namespace = initializeClient();
      allocator = new RootAllocator();
    }
  }

  @AfterEach
  public void tearDown() {
    if (allocator != null) {
      allocator.close();
    }
  }

  /**
   * Skip test if environment variables are not set. Call this at the beginning of each test method.
   */
  protected void skipIfNotConfigured() {
    assumeTrue(
        DATABASE != null && API_KEY != null,
        "Skipping test: LANCEDB_DB and LANCEDB_API_KEY environment variables must be set");
  }

  /**
   * Initialize the Lance REST client.
   *
   * @return Configured LanceRestNamespace instance
   */
  private LanceRestNamespace initializeClient() {
    Map<String, String> config = new HashMap<>();
    config.put("headers.x-lancedb-database", DATABASE);
    config.put("headers.x-api-key", API_KEY);

    if (HOST_OVERRIDE != null) {
      config.put("host_override", HOST_OVERRIDE);
    }
    if (REGION != null) {
      config.put("region", REGION);
    }

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
