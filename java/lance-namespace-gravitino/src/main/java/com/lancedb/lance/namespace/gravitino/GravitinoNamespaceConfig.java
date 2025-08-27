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
package com.lancedb.lance.namespace.gravitino;

import com.lancedb.lance.namespace.util.PropertyUtil;
import java.util.Map;

/** Configuration for Gravitino namespace. */
public class GravitinoNamespaceConfig {
  
  // Configuration keys
  public static final String GRAVITINO_ENDPOINT = "endpoint";
  public static final String GRAVITINO_METALAKE = "metalake";
  public static final String GRAVITINO_CATALOG = "catalog";
  public static final String GRAVITINO_AUTH_TOKEN = "auth_token";
  public static final String GRAVITINO_CONNECT_TIMEOUT = "connect_timeout";
  public static final String GRAVITINO_READ_TIMEOUT = "read_timeout";
  public static final String GRAVITINO_MAX_RETRIES = "max_retries";
  
  // Default values
  private static final String DEFAULT_ENDPOINT = "http://localhost:8090";
  private static final String DEFAULT_API_VERSION = "v1";
  private static final int DEFAULT_CONNECT_TIMEOUT = 10; // seconds
  private static final int DEFAULT_READ_TIMEOUT = 60; // seconds
  private static final int DEFAULT_MAX_RETRIES = 3;
  
  private final String endpoint;
  private final String metalake;
  private final String catalog;
  private final String authToken;
  private final int connectTimeout;
  private final int readTimeout;
  private final int maxRetries;
  
  public GravitinoNamespaceConfig(Map<String, String> properties) {
    this.endpoint = PropertyUtil.propertyAsString(
        properties, GRAVITINO_ENDPOINT, DEFAULT_ENDPOINT);
    
    this.metalake = PropertyUtil.propertyAsString(
        properties, GRAVITINO_METALAKE, null);
    if (metalake == null || metalake.isEmpty()) {
      throw new IllegalArgumentException(
          "Gravitino metalake must be specified with property: " + GRAVITINO_METALAKE);
    }
    
    this.catalog = PropertyUtil.propertyAsString(
        properties, GRAVITINO_CATALOG, null);
    if (catalog == null || catalog.isEmpty()) {
      throw new IllegalArgumentException(
          "Gravitino catalog must be specified with property: " + GRAVITINO_CATALOG);
    }
    
    this.authToken = PropertyUtil.propertyAsString(
        properties, GRAVITINO_AUTH_TOKEN, null);
    
    this.connectTimeout = PropertyUtil.propertyAsInt(
        properties, GRAVITINO_CONNECT_TIMEOUT, DEFAULT_CONNECT_TIMEOUT);
    
    this.readTimeout = PropertyUtil.propertyAsInt(
        properties, GRAVITINO_READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
    
    this.maxRetries = PropertyUtil.propertyAsInt(
        properties, GRAVITINO_MAX_RETRIES, DEFAULT_MAX_RETRIES);
  }
  
  public String getEndpoint() {
    return endpoint;
  }
  
  public String getMetalake() {
    return metalake;
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
   * Constructs the full API URL for Gravitino REST API.
   * @return The full API URL
   */
  public String getFullApiUrl() {
    String baseUrl = endpoint.endsWith("/") ? endpoint.substring(0, endpoint.length() - 1) : endpoint;
    return String.format("%s/api/%s", baseUrl, DEFAULT_API_VERSION);
  }
  
  /**
   * Constructs the base path for catalog operations.
   * @return The catalog base path
   */
  public String getCatalogBasePath() {
    return String.format("/metalakes/%s/catalogs/%s", metalake, catalog);
  }
  
  /**
   * Constructs the path for schema operations.
   * @return The schema operations path
   */
  public String getSchemasPath() {
    return getCatalogBasePath() + "/schemas";
  }
  
  /**
   * Constructs the path for a specific schema.
   * @param schema The schema name
   * @return The schema path
   */
  public String getSchemaPath(String schema) {
    return getSchemasPath() + "/" + schema;
  }
  
  /**
   * Constructs the path for table operations within a schema.
   * @param schema The schema name
   * @return The tables path
   */
  public String getTablesPath(String schema) {
    return getSchemaPath(schema) + "/tables";
  }
  
  /**
   * Constructs the path for a specific table.
   * @param schema The schema name
   * @param table The table name
   * @return The table path
   */
  public String getTablePath(String schema, String table) {
    return getTablesPath(schema) + "/" + table;
  }
}