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

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHive3NamespaceConfig {

  @TempDir Path tempDir;

  @Test
  public void testRootConfigurationDefault() {
    Map<String, String> properties = ImmutableMap.of();
    Hive3NamespaceConfig config = new Hive3NamespaceConfig(properties);

    assertEquals(System.getProperty("user.dir"), config.getRoot());
  }

  @Test
  public void testRootConfigurationCustom() {
    String customRoot = tempDir.toString();
    Map<String, String> properties = ImmutableMap.of("root", customRoot);
    Hive3NamespaceConfig config = new Hive3NamespaceConfig(properties);

    assertEquals(customRoot, config.getRoot());
  }

  @Test
  public void testRootConfigurationTrailingSlashStripped() {
    String customRoot = tempDir.toString() + "/";
    Map<String, String> properties = ImmutableMap.of("root", customRoot);
    Hive3NamespaceConfig config = new Hive3NamespaceConfig(properties);

    assertEquals(tempDir.toString(), config.getRoot());
  }

  @Test
  public void testOtherConfigurationValues() {
    Map<String, String> properties =
        ImmutableMap.of(
            "root", "/custom/root",
            "client.pool-size", "5",
            "storage.s3.region", "us-west-2");
    Hive3NamespaceConfig config = new Hive3NamespaceConfig(properties);

    assertEquals("/custom/root", config.getRoot());
    assertEquals(5, config.getClientPoolSize());
    assertEquals("us-west-2", config.getStorageOptions().get("s3.region"));
  }
}
