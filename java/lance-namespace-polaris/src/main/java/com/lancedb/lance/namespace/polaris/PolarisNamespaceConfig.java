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

import java.util.Map;

/** Configuration for Polaris namespace implementation. */
public class PolarisNamespaceConfig {
  public static final String POLARIS_ENDPOINT = "endpoint";
  public static final String POLARIS_CATALOG = "catalog";
  public static final String POLARIS_AUTH_TOKEN = "auth.token";
  public static final String POLARIS_CONNECT_TIMEOUT = "connect.timeout";
  public static final String POLARIS_READ_TIMEOUT = "read.timeout";
  public static final String POLARIS_MAX_RETRIES = "max.retries";

  private static final int DEFAULT_CONNECT_TIMEOUT = 10000; // 10 seconds
  private static final int DEFAULT_READ_TIMEOUT = 30000; // 30 seconds
  private static final int DEFAULT_MAX_RETRIES = 3;

  private final String endpoint;
  private final String catalog;
  private final String authToken;
  private final int connectTimeout;
  private final int readTimeout;
  private final int maxRetries;

  public PolarisNamespaceConfig(Map<String, String> properties) {
    this.endpoint = getRequiredProperty(properties, POLARIS_ENDPOINT);
    this.catalog = getRequiredProperty(properties, POLARIS_CATALOG);
    this.authToken = properties.get(POLARIS_AUTH_TOKEN);
    this.connectTimeout =
        Integer.parseInt(
            properties.getOrDefault(
                POLARIS_CONNECT_TIMEOUT, String.valueOf(DEFAULT_CONNECT_TIMEOUT)));
    this.readTimeout =
        Integer.parseInt(
            properties.getOrDefault(POLARIS_READ_TIMEOUT, String.valueOf(DEFAULT_READ_TIMEOUT)));
    this.maxRetries =
        Integer.parseInt(
            properties.getOrDefault(POLARIS_MAX_RETRIES, String.valueOf(DEFAULT_MAX_RETRIES)));

    validateConfig();
  }

  private String getRequiredProperty(Map<String, String> properties, String key) {
    String value = properties.get(key);
    if (value == null || value.trim().isEmpty()) {
      throw LanceNamespaceException.badRequest(
          "Missing required configuration",
          "ConfigurationError",
          key,
          String.format("Required configuration property '%s' is not set", key));
    }
    return value.trim();
  }

  private void validateConfig() {
    if (!endpoint.startsWith("http://") && !endpoint.startsWith("https://")) {
      throw LanceNamespaceException.badRequest(
          "Invalid endpoint format",
          "ConfigurationError",
          POLARIS_ENDPOINT,
          "Polaris endpoint must start with http:// or https://: " + endpoint);
    }

    if (connectTimeout <= 0) {
      throw LanceNamespaceException.badRequest(
          "Invalid timeout value",
          "ConfigurationError",
          POLARIS_CONNECT_TIMEOUT,
          "Connect timeout must be positive: " + connectTimeout);
    }

    if (readTimeout <= 0) {
      throw LanceNamespaceException.badRequest(
          "Invalid timeout value",
          "ConfigurationError",
          POLARIS_READ_TIMEOUT,
          "Read timeout must be positive: " + readTimeout);
    }

    if (maxRetries < 0) {
      throw LanceNamespaceException.badRequest(
          "Invalid retry value",
          "ConfigurationError",
          POLARIS_MAX_RETRIES,
          "Max retries cannot be negative: " + maxRetries);
    }
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getCatalog() {
    return catalog;
  }

  public String getAuthToken() {
    return authToken;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public int getMaxRetries() {
    return maxRetries;
  }

  /**
   * Get the full API URL for Polaris generic table operations. Format:
   * {endpoint}/polaris/v1/{catalog}
   */
  public String getFullApiUrl() {
    String baseUrl =
        endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
    return String.format("%s/polaris/v1/%s", baseUrl, catalog);
  }
}
