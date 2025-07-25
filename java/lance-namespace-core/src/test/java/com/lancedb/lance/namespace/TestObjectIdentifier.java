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

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestObjectIdentifier {
  @Test
  public void testObjectIdentifier() {
    // Case 1: invalid namespace
    List<String> ns = Lists.newArrayList();
    ns.add(null);
    assertThrows(IllegalArgumentException.class, () -> ObjectIdentifier.of(ns));

    ns.clear();
    ns.add("");
    assertThrows(IllegalArgumentException.class, () -> ObjectIdentifier.of(ns));

    assertThrows(
        IllegalArgumentException.class, () -> ObjectIdentifier.of(Lists.newArrayList("a", "")));

    // Case 2: empty namespace
    ObjectIdentifier oid = ObjectIdentifier.of(Lists.newArrayList());
    assertEquals(1, oid.levels());

    oid = ObjectIdentifier.of(Lists.newArrayList("a", "b"));
    assertEquals(3, oid.levels());
    assertEquals("a", oid.level(0));
    assertEquals("b", oid.level(1));

    // Case 3: parse name
    oid = ObjectIdentifier.of(Lists.newArrayList());
    assertEquals(".", oid.strId());

    oid = ObjectIdentifier.of(Lists.newArrayList("a"));
    assertEquals("a", oid.strId());

    oid = ObjectIdentifier.of(Lists.newArrayList("a", "b"));
    assertEquals("a.b", oid.strId());

    // Case 4: parse parent
    oid = ObjectIdentifier.of(Lists.newArrayList());
    assertThrows(IllegalArgumentException.class, oid::parent);

    oid = ObjectIdentifier.of(Lists.newArrayList("a"));
    assertEquals(Lists.newArrayList(), oid.parent());

    oid = ObjectIdentifier.of(Lists.newArrayList("a", "b"));
    assertEquals(Lists.newArrayList("a"), oid.parent());

    oid = ObjectIdentifier.of(Lists.newArrayList("a", "b", "c"));
    assertEquals(Lists.newArrayList("a", "b"), oid.parent());
  }
}
