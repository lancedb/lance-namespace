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
package com.lancedb.lance.namespace.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestOpenDalUtil {

  @Test
  public void testNormalizeScheme() {
    // Test s3 aliases
    assertEquals("s3", OpenDalUtil.normalizeScheme("s3"));
    assertEquals("s3", OpenDalUtil.normalizeScheme("s3a"));
    assertEquals("s3", OpenDalUtil.normalizeScheme("s3n"));
    assertEquals("s3", OpenDalUtil.normalizeScheme("S3A"));

    // Test Azure aliases
    assertEquals("azblob", OpenDalUtil.normalizeScheme("azblob"));
    assertEquals("azblob", OpenDalUtil.normalizeScheme("az"));
    assertEquals("azblob", OpenDalUtil.normalizeScheme("abfs"));
    assertEquals("azblob", OpenDalUtil.normalizeScheme("AZ"));
    assertEquals("azblob", OpenDalUtil.normalizeScheme("ABFS"));

    // Test filesystem
    assertEquals("fs", OpenDalUtil.normalizeScheme("file"));
    assertEquals("fs", OpenDalUtil.normalizeScheme("fs"));
    assertEquals("fs", OpenDalUtil.normalizeScheme("FILE"));

    // Test GCS (no aliases)
    assertEquals("gcs", OpenDalUtil.normalizeScheme("gcs"));
    assertEquals("gcs", OpenDalUtil.normalizeScheme("GCS"));

    // Test unknown scheme
    assertEquals("hdfs", OpenDalUtil.normalizeScheme("hdfs"));
    assertEquals("custom", OpenDalUtil.normalizeScheme("custom"));
  }

  @Test
  public void testDenormalizeScheme() {
    // Test Azure denormalization
    assertEquals("az", OpenDalUtil.denormalizeScheme("azblob"));
    assertEquals("az", OpenDalUtil.denormalizeScheme("AZBLOB"));

    // Test schemes that don't need denormalization
    assertEquals("s3", OpenDalUtil.denormalizeScheme("s3"));
    assertEquals("gcs", OpenDalUtil.denormalizeScheme("gcs"));
    assertEquals("fs", OpenDalUtil.denormalizeScheme("fs"));
    assertEquals("file", OpenDalUtil.denormalizeScheme("file"));
    assertEquals("hdfs", OpenDalUtil.denormalizeScheme("hdfs"));
  }

  @Test
  public void testDenormalizeUri() {
    // Test Azure URI denormalization
    assertEquals(
        "az://container/path/to/table.lance",
        OpenDalUtil.denormalizeUri("azblob://container/path/to/table.lance"));
    assertEquals(
        "az://mycontainer/data/mytable.lance",
        OpenDalUtil.denormalizeUri("azblob://mycontainer/data/mytable.lance"));

    // Test URIs that don't need denormalization
    assertEquals(
        "s3://bucket/path/to/table.lance",
        OpenDalUtil.denormalizeUri("s3://bucket/path/to/table.lance"));
    assertEquals(
        "gcs://bucket/path/to/table.lance",
        OpenDalUtil.denormalizeUri("gcs://bucket/path/to/table.lance"));
    assertEquals(
        "file:///local/path/to/table.lance",
        OpenDalUtil.denormalizeUri("file:///local/path/to/table.lance"));
    assertEquals(
        "/local/path/to/table.lance", OpenDalUtil.denormalizeUri("/local/path/to/table.lance"));

    // Test null and edge cases
    assertNull(OpenDalUtil.denormalizeUri(null));
    assertEquals("", OpenDalUtil.denormalizeUri(""));
    assertEquals("noscheme", OpenDalUtil.denormalizeUri("noscheme"));
  }

  @Test
  public void testStripTrailingSlash() {
    // Test with trailing slashes
    assertEquals("/path/to/dir", OpenDalUtil.stripTrailingSlash("/path/to/dir/"));
    assertEquals("/path/to/dir", OpenDalUtil.stripTrailingSlash("/path/to/dir//"));
    assertEquals("s3://bucket/path", OpenDalUtil.stripTrailingSlash("s3://bucket/path///"));

    // Test without trailing slashes
    assertEquals("/path/to/dir", OpenDalUtil.stripTrailingSlash("/path/to/dir"));
    assertEquals("s3://bucket/path", OpenDalUtil.stripTrailingSlash("s3://bucket/path"));

    // Test null and edge cases
    assertNull(OpenDalUtil.stripTrailingSlash(null));
    assertEquals("", OpenDalUtil.stripTrailingSlash("/"));
    assertEquals("", OpenDalUtil.stripTrailingSlash("///"));
  }
}
