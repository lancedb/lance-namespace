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
   * <ul>
   *   <li>Local filesystem: /my/dir, ./my/dir, file:///my/dir</li>
   *   <li>S3: s3://bucket/prefix, s3a://bucket/prefix, s3n://bucket/prefix</li>
   *   <li>Google Cloud Storage: gcs://bucket/prefix</li>
   *   <li>Azure Blob Storage: abfs://container/prefix</li>
   * </ul>
   */
  public static final String ROOT = "root";

  /**
   * Prefix for OpenDAL configuration properties.
   * 
   * <p>All properties starting with "opendal." will be passed to OpenDAL after removing the prefix.
   * This allows users to configure any OpenDAL-specific settings by referencing the OpenDAL documentation.
   * 
   * <p>Examples:
   * <ul>
   *   <li>opendal.region = us-west-2 (for S3)</li>
   *   <li>opendal.endpoint = https://custom-endpoint.com (for S3)</li>
   *   <li>opendal.access_key_id = AKIA... (for S3)</li>
   *   <li>opendal.secret_access_key = ... (for S3)</li>
   *   <li>opendal.account_name = myaccount (for Azure)</li>
   *   <li>opendal.credential_path = /path/to/gcs-key.json (for GCS)</li>
   * </ul>
   */
  public static final String OPENDAL_PREFIX = "opendal.";

  private final String root;
  private final Map<String, String> opendalConfig;

  public DirectoryNamespaceConfig() {
    this.root = null;
    this.opendalConfig = PropertyUtil.propertiesWithPrefix(null, OPENDAL_PREFIX);
  }

  public DirectoryNamespaceConfig(Map<String, String> properties) {
    this.root = properties.get(ROOT);
    this.opendalConfig = PropertyUtil.propertiesWithPrefix(properties, OPENDAL_PREFIX);
  }

  public String getRoot() {
    return root;
  }

  public Map<String, String> getOpendalConfig() {
    return opendalConfig;
  }
}