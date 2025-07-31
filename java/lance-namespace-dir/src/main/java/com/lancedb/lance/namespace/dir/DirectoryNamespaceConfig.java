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
package com.lancedb.lance.namespace.dir;

import com.lancedb.lance.namespace.util.PropertyUtil;

import java.io.Serializable;
import java.util.Map;

public class DirectoryNamespaceConfig implements Serializable {

  /**
   * The root directory of the namespace. This is the root directory where tables are stored.
   *
   * <p>Supports various URI formats:
   *
   * <ul>
   *   <li>Local filesystem: /my/dir, ./my/dir, file:///my/dir
   *   <li>S3: s3://bucket/prefix, s3a://bucket/prefix, s3n://bucket/prefix
   *   <li>Google Cloud Storage: gcs://bucket/prefix
   *   <li>Azure Blob Storage: abfs://container/prefix
   * </ul>
   */
  public static final String ROOT = "root";

  public static final String STORAGE_OPTIONS_PREFIX = "storage.";

  private final String root;
  private final Map<String, String> storageOptions;

  public DirectoryNamespaceConfig() {
    this.root = null;
    this.storageOptions = PropertyUtil.propertiesWithPrefix(null, STORAGE_OPTIONS_PREFIX);
  }

  public DirectoryNamespaceConfig(Map<String, String> properties) {
    this.root = PropertyUtil.propertyAsString(properties, ROOT, System.getProperty("user.dir"));
    this.storageOptions = PropertyUtil.propertiesWithPrefix(properties, STORAGE_OPTIONS_PREFIX);
  }

  public String getRoot() {
    return root;
  }

  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }
}
