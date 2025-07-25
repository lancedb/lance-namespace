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

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.glue.GlueClientBuilder;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;

public class TestGlueNamespaceConfig {

  @Test
  public void testDefaultCredentials() {
    GlueNamespaceConfig properties = new GlueNamespaceConfig();
    GlueClientBuilder mockBuilder = mockGlueClientBuilder();

    properties.configureClientBuilder(mockBuilder);

    ArgumentCaptor<AwsCredentialsProvider> credsCaptor =
        ArgumentCaptor.forClass(AwsCredentialsProvider.class);
    verify(mockBuilder).credentialsProvider(credsCaptor.capture());
    assertInstanceOf(DefaultCredentialsProvider.class, credsCaptor.getValue());
  }

  @Test
  public void testPropertiesFromMap() {
    Map<String, String> props =
        Map.of(
            GlueNamespaceConfig.CATALOG_ID, "1234567890",
            GlueNamespaceConfig.REGION, "us-west-2",
            GlueNamespaceConfig.ENDPOINT, "https://glue.us-west-2.api.aws");
    GlueNamespaceConfig properties = new GlueNamespaceConfig(props);
    assertEquals("1234567890", properties.glueCatalogId());
  }

  @Test
  public void testBasicCredentials() {
    Map<String, String> props =
        Map.of(
            GlueNamespaceConfig.ACCESS_KEY_ID, "mykey",
            GlueNamespaceConfig.SECRET_ACCESS_KEY, "secret");

    GlueNamespaceConfig properties = new GlueNamespaceConfig(props);
    GlueClientBuilder mockBuilder = mockGlueClientBuilder();
    properties.configureClientBuilder(mockBuilder);

    ArgumentCaptor<AwsCredentialsProvider> credsCaptor =
        ArgumentCaptor.forClass(AwsCredentialsProvider.class);
    verify(mockBuilder).credentialsProvider(credsCaptor.capture());

    AwsCredentialsProvider provider = credsCaptor.getValue();
    assertInstanceOf(AwsBasicCredentials.class, provider.resolveCredentials());
    AwsBasicCredentials creds = (AwsBasicCredentials) provider.resolveCredentials();
    assertEquals("mykey", creds.accessKeyId());
    assertEquals("secret", creds.secretAccessKey());
  }

  @Test
  public void testSessionCredentials() {
    Map<String, String> props =
        Map.of(
            GlueNamespaceConfig.ACCESS_KEY_ID, "mykey",
            GlueNamespaceConfig.SECRET_ACCESS_KEY, "secret",
            GlueNamespaceConfig.SESSION_TOKEN, "token");

    GlueNamespaceConfig properties = new GlueNamespaceConfig(props);
    GlueClientBuilder mockBuilder = mockGlueClientBuilder();
    properties.configureClientBuilder(mockBuilder);

    ArgumentCaptor<AwsCredentialsProvider> credsCaptor =
        ArgumentCaptor.forClass(AwsCredentialsProvider.class);
    verify(mockBuilder).credentialsProvider(credsCaptor.capture());

    AwsCredentialsProvider provider = credsCaptor.getValue();
    assertInstanceOf(AwsSessionCredentials.class, provider.resolveCredentials());
    AwsSessionCredentials creds = (AwsSessionCredentials) provider.resolveCredentials();
    assertEquals("mykey", creds.accessKeyId());
    assertEquals("secret", creds.secretAccessKey());
    assertEquals("token", creds.sessionToken());
  }

  @Test
  public void testConfigureGlueClientBuilder() {
    Map<String, String> props =
        Map.of(
            GlueNamespaceConfig.CATALOG_ID, "1234567890",
            GlueNamespaceConfig.REGION, "us-west-2",
            GlueNamespaceConfig.ENDPOINT, "https://glue.us-west-2.api.aws",
            GlueNamespaceConfig.ACCESS_KEY_ID, "mykey",
            GlueNamespaceConfig.SECRET_ACCESS_KEY, "secret");

    GlueNamespaceConfig properties = new GlueNamespaceConfig(props);
    GlueClientBuilder mockBuilder = mockGlueClientBuilder();
    properties.configureClientBuilder(mockBuilder);

    ArgumentCaptor<Region> regionCaptor = ArgumentCaptor.forClass(Region.class);
    verify(mockBuilder).region(regionCaptor.capture());
    assertEquals("us-west-2", regionCaptor.getValue().toString());

    ArgumentCaptor<URI> endpointCaptor = ArgumentCaptor.forClass(URI.class);
    verify(mockBuilder).endpointOverride(endpointCaptor.capture());
    assertEquals("https://glue.us-west-2.api.aws", endpointCaptor.getValue().toString());
  }

  private GlueClientBuilder mockGlueClientBuilder() {
    GlueClientBuilder mockBuilder = Mockito.mock(GlueClientBuilder.class);
    Mockito.when(mockBuilder.region(Mockito.any(Region.class))).thenReturn(mockBuilder);
    Mockito.when(mockBuilder.endpointOverride(Mockito.any(URI.class))).thenReturn(mockBuilder);
    Mockito.when(mockBuilder.credentialsProvider(Mockito.any(AwsCredentialsProvider.class)))
        .thenReturn(mockBuilder);
    return mockBuilder;
  }
}
