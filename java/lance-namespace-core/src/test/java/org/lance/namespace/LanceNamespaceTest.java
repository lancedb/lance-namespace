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
package org.lance.namespace;

import org.apache.arrow.memory.BufferAllocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for LanceNamespace interface and registry. */
public class LanceNamespaceTest {

  @BeforeEach
  void setUp() {
    // Clear registered implementations before each test
    LanceNamespace.REGISTERED_IMPLS.clear();
  }

  @AfterEach
  void tearDown() {
    // Clear registered implementations after each test
    LanceNamespace.REGISTERED_IMPLS.clear();
  }

  @Test
  void testNativeImplsDefined() {
    // Test that native implementations are defined
    assertTrue(LanceNamespace.NATIVE_IMPLS.containsKey("dir"));
    assertTrue(LanceNamespace.NATIVE_IMPLS.containsKey("rest"));
    assertEquals("org.lance.namespace.DirectoryNamespace", LanceNamespace.NATIVE_IMPLS.get("dir"));
    assertEquals("org.lance.namespace.RestNamespace", LanceNamespace.NATIVE_IMPLS.get("rest"));
  }

  @Test
  void testRegisterNamespaceImpl() {
    // Test registering a custom implementation
    LanceNamespace.registerNamespaceImpl("mock", "org.lance.namespace.MockNamespace");
    assertTrue(LanceNamespace.REGISTERED_IMPLS.containsKey("mock"));
    assertEquals("org.lance.namespace.MockNamespace", LanceNamespace.REGISTERED_IMPLS.get("mock"));
  }

  @Test
  void testUnregisterNamespaceImpl() {
    // Test unregistering an implementation
    LanceNamespace.registerNamespaceImpl("mock", "org.lance.namespace.MockNamespace");
    assertTrue(LanceNamespace.unregisterNamespaceImpl("mock"));
    assertFalse(LanceNamespace.REGISTERED_IMPLS.containsKey("mock"));

    // Test unregistering non-existent implementation
    assertFalse(LanceNamespace.unregisterNamespaceImpl("nonexistent"));
  }

  @Test
  void testIsRegistered() {
    // Test checking if implementation is registered
    assertTrue(LanceNamespace.isRegistered("dir")); // Native impl
    assertTrue(LanceNamespace.isRegistered("rest")); // Native impl
    assertFalse(LanceNamespace.isRegistered("mock"));

    // Register and check again
    LanceNamespace.registerNamespaceImpl("mock", "org.lance.namespace.MockNamespace");
    assertTrue(LanceNamespace.isRegistered("mock"));
  }

  @Test
  void testConnectWithFullClassPath() {
    // Test connecting using full class path (inner class uses $ separator)
    Map<String, String> properties = new HashMap<>();
    properties.put("id", "test");

    LanceNamespace ns =
        LanceNamespace.connect(
            "org.lance.namespace.LanceNamespaceTest$MockNamespace", properties, null);
    assertNotNull(ns);
    assertTrue(ns instanceof MockNamespace);
    assertTrue(ns.namespaceId().contains("test"));
  }

  @Test
  void testConnectWithRegisteredImpl() {
    // Test connecting using registered implementation alias
    LanceNamespace.registerNamespaceImpl(
        "mock", "org.lance.namespace.LanceNamespaceTest$MockNamespace");

    Map<String, String> properties = new HashMap<>();
    properties.put("id", "test-registered");

    LanceNamespace ns = LanceNamespace.connect("mock", properties, null);
    assertNotNull(ns);
    assertTrue(ns instanceof MockNamespace);
    assertTrue(ns.namespaceId().contains("test-registered"));
  }

  @Test
  void testConnectInvalidClassPath() {
    // Test that invalid class path throws IllegalArgumentException
    Map<String, String> properties = new HashMap<>();
    assertThrows(
        IllegalArgumentException.class,
        () -> LanceNamespace.connect("non.existent.Namespace", properties, null));
  }

  @Test
  void testConnectNonNamespaceClass() {
    // Test that non-LanceNamespace class throws IllegalArgumentException
    Map<String, String> properties = new HashMap<>();
    IllegalArgumentException ex =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                LanceNamespace.connect(
                    "org.lance.namespace.LanceNamespaceTest$NotANamespace", properties, null));
    assertTrue(ex.getMessage().contains("does not implement LanceNamespace"));
  }

  @Test
  void testDefaultMethodsThrowUnsupportedOperation() {
    // Test that default methods throw UnsupportedOperationException
    MockNamespace ns = new MockNamespace();
    ns.initialize(new HashMap<>(), null);

    assertThrows(
        org.lance.namespace.errors.UnsupportedOperationException.class,
        () -> ns.listNamespaces(new org.lance.namespace.model.ListNamespacesRequest()));

    assertThrows(
        org.lance.namespace.errors.UnsupportedOperationException.class,
        () -> ns.listTables(new org.lance.namespace.model.ListTablesRequest()));
  }

  /** Mock namespace implementation for testing. */
  public static class MockNamespace implements LanceNamespace {
    private String id = "default";

    public MockNamespace() {
      // No-arg constructor required for reflection-based instantiation
    }

    @Override
    public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
      if (configProperties.containsKey("id")) {
        this.id = configProperties.get("id");
      }
    }

    @Override
    public String namespaceId() {
      return "MockNamespace { id: '" + id + "' }";
    }
  }

  /** A class that doesn't implement LanceNamespace for testing rejection. */
  public static class NotANamespace {
    public NotANamespace() {}
  }
}
