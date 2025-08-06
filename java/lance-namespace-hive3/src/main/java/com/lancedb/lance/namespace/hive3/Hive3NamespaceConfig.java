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
package com.lancedb.lance.namespace.hive3;

import com.lancedb.lance.namespace.util.OpenDalUtil;
import com.lancedb.lance.namespace.util.PropertyUtil;

import java.util.Map;

public class Hive3NamespaceConfig {

  public static final String CATALOG_NAME = "hive";
  public static final String DATABASE_DESCRIPTION = "database.description";
  public static final String DATABASE_LOCATION_URI = "database.location-uri";
  public static final String DATABASE_OWNER = "database.owner";
  public static final String DATABASE_OWNER_TYPE = "database.owner-type";

  // Namespace config key
  public static final String CLIENT_POOL_SIZE = "client.pool-size";
  public static final int CLIENT_POOL_SIZE_DEFAULT = 3;

  public static final String STORAGE_OPTIONS_PREFIX = "storage.";

  /** Storage root location of the lakehouse on Hive catalog */
  public static final String ROOT = "root";

  public static final String ROOT_DEFAULT = System.getProperty("user.dir");

  private final int clientPoolSize;
  private final Map<String, String> storageOptions;
  private final String root;

  public Hive3NamespaceConfig(Map<String, String> properties) {
    this.clientPoolSize =
        PropertyUtil.propertyAsInt(properties, CLIENT_POOL_SIZE, CLIENT_POOL_SIZE_DEFAULT);
    this.storageOptions = PropertyUtil.propertiesWithPrefix(properties, STORAGE_OPTIONS_PREFIX);
    this.root =
        OpenDalUtil.stripTrailingSlash(
            PropertyUtil.propertyAsString(properties, ROOT, ROOT_DEFAULT));
  }

  public int getClientPoolSize() {
    return clientPoolSize;
  }

  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  public String getRoot() {
    return root;
  }
}
