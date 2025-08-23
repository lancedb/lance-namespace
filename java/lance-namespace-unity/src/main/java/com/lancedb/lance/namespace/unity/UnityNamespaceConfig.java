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

import com.lancedb.lance.namespace.util.PropertyUtil;

import java.util.Map;

/** Configuration for Unity Catalog namespace. */
public class UnityNamespaceConfig {
  private static final String ENDPOINT = "endpoint";
  private static final String API_PATH = "api_path";
  private static final String AUTH_TOKEN = "auth_token";
  private static final String CATALOG = "catalog";
  private static final String CONNECT_TIMEOUT = "connect_timeout";
  private static final String READ_TIMEOUT = "read_timeout";
  private static final String MAX_RETRIES = "max_retries";
  private static final String ROOT = "root";

  private static final String DEFAULT_API_PATH = "/api/2.1/unity-catalog";
  private static final int DEFAULT_CONNECT_TIMEOUT = 10;
  private static final int DEFAULT_READ_TIMEOUT = 60;
  private static final int DEFAULT_MAX_RETRIES = 3;

  private final Map<String, String> properties;
  private final String endpoint;
  private final String apiPath;
  private final String authToken;
  private final String catalog;
  private final int connectTimeout;
  private final int readTimeout;
  private final int maxRetries;
  private final String root;
  private final Map<String, String> storageProperties;

  public UnityNamespaceConfig(Map<String, String> properties) {
    this.properties = properties;
    this.endpoint = PropertyUtil.propertyAsString(properties, ENDPOINT, null);
    if (endpoint == null) {
      throw new IllegalArgumentException("Unity endpoint is required");
    }
    this.apiPath = PropertyUtil.propertyAsString(properties, API_PATH, DEFAULT_API_PATH);
    this.authToken = PropertyUtil.propertyAsString(properties, AUTH_TOKEN, null);
    this.catalog = PropertyUtil.propertyAsString(properties, CATALOG, null);
    if (catalog == null) {
      throw new IllegalArgumentException("Unity catalog is required");
    }
    this.connectTimeout =
        PropertyUtil.propertyAsInt(properties, CONNECT_TIMEOUT, DEFAULT_CONNECT_TIMEOUT);
    this.readTimeout = PropertyUtil.propertyAsInt(properties, READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
    this.maxRetries = PropertyUtil.propertyAsInt(properties, MAX_RETRIES, DEFAULT_MAX_RETRIES);
    this.root = PropertyUtil.propertyAsString(properties, ROOT, System.getProperty("user.dir"));
    this.storageProperties = PropertyUtil.propertiesWithPrefix(properties, "storage.");
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getApiPath() {
    return apiPath;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getCatalog() {
    return catalog;
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

  public String getRoot() {
    return root;
  }

  public Map<String, String> getStorageProperties() {
    return storageProperties;
  }

  public String getFullApiUrl() {
    String baseUrl =
        endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
    String path = apiPath.startsWith("/") ? apiPath : "/" + apiPath;
    return baseUrl + path;
  }
}
