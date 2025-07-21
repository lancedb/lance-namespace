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

import com.google.common.base.Strings;
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

public class GlueNamespaceProperties implements Serializable {

  /**
   * The Catalog ID of the Glue catalog to be used for all operations. If not specified, Glue
   * defaults to the AWS account ID of the caller.
   *
   * <p>For more details, see
   * https://docs.aws.amazon.com/glue/latest/dg/aws-glue-api-catalog-databases.html
   */
  public static final String CATALOG_ID = "glue.id";

  /**
   * Configures a specific Glue service endpoint for the GlueCatalog.
   *
   * <p>This allows GlueCatalog to connect to any Glue API compatible metastore with a custom
   * endpoint.
   */
  public static final String ENDPOINT = "glue.endpoint";

  /** Configure the AWS region for all Glue operations */
  public static final String REGION = "glue.region";

  /**
   * AWS access key ID for static credentials.
   *
   * <p>If set along with {@link #SECRET_ACCESS_KEY}, the client uses these credentials instead of
   * the default AWS credential provider chain. If {@link #SESSION_TOKEN} is also provided, session
   * credentials will be used.
   */
  public static final String ACCESS_KEY_ID = "glue.access-key-id";

  /**
   * AWS secret access key for static credentials.
   *
   * <p>Used together with {@link #ACCESS_KEY_ID}. If {@link #SESSION_TOKEN} is provided, session
   * credentials are used; otherwise, basic credentials are used.
   */
  public static final String SECRET_ACCESS_KEY = "glue.secret-access-key";

  /**
   * AWS session token for temporary credentials.
   *
   * <p>When set, the glue client uses session credentials rather than the default credential
   * provider chain.
   */
  public static final String SESSION_TOKEN = "glue.session-token";

  private final String glueEndpoint;
  private final String glueRegion;
  private final String glueCatalogId;
  private final String glueAccessKeyId;
  private final String glueSecretAccessKey;
  private final String glueSessionToken;

  public GlueNamespaceProperties() {
    this.glueCatalogId = null;
    this.glueEndpoint = null;
    this.glueRegion = null;
    this.glueAccessKeyId = null;
    this.glueSecretAccessKey = null;
    this.glueSessionToken = null;
  }

  public GlueNamespaceProperties(Map<String, String> properties) {
    this.glueEndpoint = properties.get(ENDPOINT);
    this.glueRegion = properties.get(REGION);
    this.glueCatalogId = properties.get(CATALOG_ID);
    this.glueAccessKeyId = properties.get(ACCESS_KEY_ID);
    this.glueSecretAccessKey = properties.get(SECRET_ACCESS_KEY);
    this.glueSessionToken = properties.get(SESSION_TOKEN);
  }

  public String glueCatalogId() {
    return glueCatalogId;
  }

  private AwsCredentialsProvider credentialsProvider() {
    if (!Strings.isNullOrEmpty(glueAccessKeyId) && !Strings.isNullOrEmpty(glueSecretAccessKey)) {
      return StaticCredentialsProvider.create(
          Strings.isNullOrEmpty(glueSessionToken)
              ? AwsBasicCredentials.create(glueAccessKeyId, glueSecretAccessKey)
              : AwsSessionCredentials.create(
                  glueAccessKeyId, glueSecretAccessKey, glueSessionToken));
    }
    return DefaultCredentialsProvider.builder().build();
  }

  public void configureClientBuilder(GlueClientBuilder builder) {
    if (glueEndpoint != null) {
      builder.endpointOverride(URI.create(glueEndpoint));
    }
    if (!Strings.isNullOrEmpty(glueRegion)) {
      builder.region(Region.of(glueRegion));
    }
    builder.credentialsProvider(credentialsProvider());
  }
}
