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

import com.lancedb.lance.namespace.util.OpenDalUtil;
import com.lancedb.lance.namespace.util.PropertyUtil;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class UnityNamespaceConfig implements Serializable {

  /** Unity Catalog server endpoint */
  public static final String ENDPOINT = "endpoint";

  /** Bearer token for authentication */
  public static final String TOKEN = "token";

  /** Storage root location for Lance tables */
  public static final String ROOT = "root";

  public static final String ROOT_DEFAULT = System.getProperty("user.dir");

  /** Additional storage configurations to access table */
  public static final String STORAGE_OPTIONS_PREFIX = "storage.";

  private final String endpoint;
  private final String token;
  private final String root;
  private final Map<String, String> storageOptions;

  public UnityNamespaceConfig(Map<String, String> properties) {
    this.endpoint =
        Optional.ofNullable(properties.get(ENDPOINT))
            .orElseThrow(() -> new IllegalArgumentException("Unity Catalog endpoint is required"));
    this.token = properties.get(TOKEN);
    this.root =
        OpenDalUtil.stripTrailingSlash(
            PropertyUtil.propertyAsString(properties, ROOT, ROOT_DEFAULT));
    this.storageOptions = PropertyUtil.propertiesWithPrefix(properties, STORAGE_OPTIONS_PREFIX);
  }

  public String getEndpoint() {
    return endpoint;
  }

  public Optional<String> getToken() {
    return Optional.ofNullable(token);
  }

  public String getRoot() {
    return root;
  }

  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }
}
