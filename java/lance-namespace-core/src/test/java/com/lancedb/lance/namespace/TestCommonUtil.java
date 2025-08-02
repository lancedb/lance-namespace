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

import com.lancedb.lance.namespace.util.CommonUtil;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCommonUtil {
  @Test
  public void testMakeQualified() {
    // Case 1: Un-absolute path
    assertThrows(IllegalArgumentException.class, () -> CommonUtil.makeQualified("./cat"));
    assertThrows(IllegalArgumentException.class, () -> CommonUtil.makeQualified("cat"));

    // Case 2: null and empty
    assertThrows(NullPointerException.class, () -> CommonUtil.makeQualified(null));
    assertThrows(IllegalArgumentException.class, () -> CommonUtil.makeQualified(""));

    // Case 3: absolute path
    assertEquals("s3://bucket/cat", CommonUtil.makeQualified("s3://bucket/cat"));
    assertEquals("s3:///cat", CommonUtil.makeQualified("s3:///cat"));
    assertEquals("s3://bucket/cat", CommonUtil.makeQualified("s3://bucket/cat?param=foo"));
    assertEquals("s3://bucket/cat", CommonUtil.makeQualified("s3://bucket/cat#frag=0"));

    // Case 4: absolute local file path
    assertEquals("file:///cat", CommonUtil.makeQualified("/cat"));
    assertEquals("file:///var/folders/test", CommonUtil.makeQualified("/var/folders/test"));
  }
}
