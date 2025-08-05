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
package com.lancedb.lance.namespace.glue;

import com.lancedb.lance.namespace.util.OpenDalUtil;
import com.lancedb.lance.namespace.util.PropertyUtil;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glue.GlueClientBuilder;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

public class GlueNamespaceConfig implements Serializable {

  /**
   * The Catalog ID of the Glue catalog to be used for all operations. If not specified, Glue
   * defaults to the AWS account ID of the caller.
   *
   * <p>For more details, see
   * https://docs.aws.amazon.com/glue/latest/dg/aws-glue-api-catalog-databases.html
   */
  public static final String CATALOG_ID = "catalog_id";

  /**
   * Configures a specific Glue service endpoint for the GlueCatalog.
   *
   * <p>This allows GlueCatalog to connect to any Glue API compatible metastore with a custom
   * endpoint.
   */
  public static final String ENDPOINT = "endpoint";

  /** Configure the AWS region for all Glue operations */
  public static final String REGION = "region";

  /**
   * AWS access key ID for static credentials.
   *
   * <p>If set along with {@link #SECRET_ACCESS_KEY}, the client uses these credentials instead of
   * the default AWS credential provider chain. If {@link #SESSION_TOKEN} is also provided, session
   * credentials will be used.
   */
  public static final String ACCESS_KEY_ID = "access_key_id";

  /**
   * AWS secret access key for static credentials.
   *
   * <p>Used together with {@link #ACCESS_KEY_ID}. If {@link #SESSION_TOKEN} is provided, session
   * credentials are used; otherwise, basic credentials are used.
   */
  public static final String SECRET_ACCESS_KEY = "secret_access_key";

  /**
   * AWS session token for temporary credentials.
   *
   * <p>When set, the glue client uses session credentials rather than the default credential
   * provider chain.
   */
  public static final String SESSION_TOKEN = "session_token";

  /** Additional storage configurations to access table */
  public static final String STORAGE_OPTIONS_PREFIX = "storage.";

  /** Storage root location of the lakehouse on Glue catalog */
  public static final String ROOT = "root";

  public static final String ROOT_DEFAULT = System.getProperty("user.dir");

  private final String endpoint;
  private final String region;
  private final String catalogId;
  private final String accessKeyId;
  private final String secretAccessKey;
  private final String sessionToken;
  private final Map<String, String> storageOptions;
  private final String root;

  public GlueNamespaceConfig() {
    this.catalogId = null;
    this.endpoint = null;
    this.region = null;
    this.accessKeyId = null;
    this.secretAccessKey = null;
    this.sessionToken = null;
    this.storageOptions = ImmutableMap.of();
    this.root = ROOT_DEFAULT;
  }

  public GlueNamespaceConfig(Map<String, String> properties) {
    this.endpoint = properties.get(ENDPOINT);
    this.region = properties.get(REGION);
    this.catalogId = properties.get(CATALOG_ID);
    this.accessKeyId = properties.get(ACCESS_KEY_ID);
    this.secretAccessKey = properties.get(SECRET_ACCESS_KEY);
    this.sessionToken = properties.get(SESSION_TOKEN);
    this.storageOptions = PropertyUtil.propertiesWithPrefix(properties, STORAGE_OPTIONS_PREFIX);
    this.root =
        OpenDalUtil.stripTrailingSlash(
            PropertyUtil.propertyAsString(properties, ROOT, ROOT_DEFAULT));
  }

  public String catalogId() {
    return catalogId;
  }

  private AwsCredentialsProvider credentialsProvider() {
    if (!Strings.isNullOrEmpty(accessKeyId) && !Strings.isNullOrEmpty(secretAccessKey)) {
      return StaticCredentialsProvider.create(
          Strings.isNullOrEmpty(sessionToken)
              ? AwsBasicCredentials.create(accessKeyId, secretAccessKey)
              : AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken));
    }
    return DefaultCredentialsProvider.builder().build();
  }

  public void configureClientBuilder(GlueClientBuilder builder) {
    if (endpoint != null) {
      builder.endpointOverride(URI.create(endpoint));
    }
    if (!Strings.isNullOrEmpty(region)) {
      builder.region(Region.of(region));
    }
    builder.credentialsProvider(credentialsProvider());
  }

  public Map<String, String> getStorageOptions() {
    return storageOptions;
  }

  public String getRoot() {
    return root;
  }
}
