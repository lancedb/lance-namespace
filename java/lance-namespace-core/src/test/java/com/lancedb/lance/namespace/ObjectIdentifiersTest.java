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

import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObjectIdentifiersTest {

  @Test
  void testStringFromCreateNamespaceRequest() {
    // Test with parent namespace
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    assertEquals("parent1.parent2", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("parent1$parent2", ObjectIdentifiers.stringFrom(request, "$"));

    // Test without parent (root namespace)
    CreateNamespaceRequest rootRequest = new CreateNamespaceRequest();
    rootRequest.setName("rootNamespace");

    assertEquals(".", ObjectIdentifiers.stringFrom(rootRequest, "."));

    // Test with null parent
    CreateNamespaceRequest nullParentRequest = new CreateNamespaceRequest();
    nullParentRequest.setParent(null);
    nullParentRequest.setName(".");

    assertEquals(".", ObjectIdentifiers.stringFrom(nullParentRequest, "."));
  }

  @Test
  void testListFromCreateNamespaceRequest() {
    // Test with parent namespace
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    List<String> expected = Arrays.asList("parent1", "parent2");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));

    // Test without parent (root namespace)
    CreateNamespaceRequest rootRequest = new CreateNamespaceRequest();
    rootRequest.setName("rootNamespace");

    List<String> expectedRoot = Collections.emptyList();
    assertEquals(expectedRoot, ObjectIdentifiers.listFrom(rootRequest));

    // Test with null parent
    CreateNamespaceRequest nullParentRequest = new CreateNamespaceRequest();
    nullParentRequest.setParent(null);
    nullParentRequest.setName("nullParentNamespace");

    List<String> expectedNullParent = Collections.emptyList();
    assertEquals(expectedNullParent, ObjectIdentifiers.listFrom(nullParentRequest));
  }

  @Test
  void testStringFromDescribeNamespaceRequest() {
    DescribeNamespaceRequest request = new DescribeNamespaceRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    assertEquals("parent1.parent2.testNamespace", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("parent1$parent2$testNamespace", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromDescribeNamespaceRequest() {
    DescribeNamespaceRequest request = new DescribeNamespaceRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    List<String> expected = Arrays.asList("parent1", "parent2", "testNamespace");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromDropNamespaceRequest() {
    DropNamespaceRequest request = new DropNamespaceRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    assertEquals("parent1.parent2.testNamespace", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("parent1$parent2$testNamespace", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromDropNamespaceRequest() {
    DropNamespaceRequest request = new DropNamespaceRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    List<String> expected = Arrays.asList("parent1", "parent2", "testNamespace");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromNamespaceExistsRequest() {
    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    assertEquals("parent1.parent2.testNamespace", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("parent1$parent2$testNamespace", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromNamespaceExistsRequest() {
    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));
    request.setName("testNamespace");

    List<String> expected = Arrays.asList("parent1", "parent2", "testNamespace");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromDescribeTableRequest() {
    DescribeTableRequest request = new DescribeTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    assertEquals("namespace1.namespace2.testTable", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("namespace1$namespace2$testTable", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromDescribeTableRequest() {
    DescribeTableRequest request = new DescribeTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    List<String> expected = Arrays.asList("namespace1", "namespace2", "testTable");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromRegisterTableRequest() {
    RegisterTableRequest request = new RegisterTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    assertEquals("namespace1.namespace2.testTable", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("namespace1$namespace2$testTable", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromRegisterTableRequest() {
    RegisterTableRequest request = new RegisterTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    List<String> expected = Arrays.asList("namespace1", "namespace2", "testTable");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromDropTableRequest() {
    DropTableRequest request = new DropTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    assertEquals("namespace1.namespace2.testTable", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("namespace1$namespace2$testTable", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromDropTableRequest() {
    DropTableRequest request = new DropTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    List<String> expected = Arrays.asList("namespace1", "namespace2", "testTable");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromDeregisterTableRequest() {
    DeregisterTableRequest request = new DeregisterTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    assertEquals("namespace1.namespace2.testTable", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("namespace1$namespace2$testTable", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromDeregisterTableRequest() {
    DeregisterTableRequest request = new DeregisterTableRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    List<String> expected = Arrays.asList("namespace1", "namespace2", "testTable");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromTableExistsRequest() {
    TableExistsRequest request = new TableExistsRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    assertEquals("namespace1.namespace2.testTable", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("namespace1$namespace2$testTable", ObjectIdentifiers.stringFrom(request, "$"));
  }

  @Test
  void testListFromTableExistsRequest() {
    TableExistsRequest request = new TableExistsRequest();
    request.setNamespace(Arrays.asList("namespace1", "namespace2"));
    request.setName("testTable");

    List<String> expected = Arrays.asList("namespace1", "namespace2", "testTable");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromListNamespacesRequest() {
    // Test with parent namespace
    ListNamespacesRequest request = new ListNamespacesRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));

    assertEquals("parent1.parent2", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals("parent1$parent2", ObjectIdentifiers.stringFrom(request, "$"));

    // Test without parent (root namespace)
    ListNamespacesRequest rootRequest = new ListNamespacesRequest();
    rootRequest.setParent(Collections.emptyList());

    assertEquals(".", ObjectIdentifiers.stringFrom(rootRequest, "."));

    // Test with null parent
    ListNamespacesRequest nullParentRequest = new ListNamespacesRequest();
    nullParentRequest.setParent(null);

    assertEquals(".", ObjectIdentifiers.stringFrom(nullParentRequest, "."));
  }

  @Test
  void testListFromListNamespacesRequest() {
    // Test with parent namespace
    ListNamespacesRequest request = new ListNamespacesRequest();
    request.setParent(Arrays.asList("parent1", "parent2"));

    List<String> expected = Arrays.asList("parent1", "parent2");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));

    // Test without parent (root namespace)
    ListNamespacesRequest rootRequest = new ListNamespacesRequest();
    rootRequest.setParent(Collections.emptyList());

    List<String> expectedRoot = Collections.emptyList();
    assertEquals(expectedRoot, ObjectIdentifiers.listFrom(rootRequest));

    // Test with null parent
    ListNamespacesRequest nullParentRequest = new ListNamespacesRequest();
    nullParentRequest.setParent(null);

    List<String> expectedNullParent = Collections.emptyList();
    assertEquals(expectedNullParent, ObjectIdentifiers.listFrom(nullParentRequest));
  }

  @Test
  void testStringFromDescribeTransactionRequest() {
    DescribeTransactionRequest request = new DescribeTransactionRequest();
    request.setId("transaction-123");

    assertEquals("transaction-123", ObjectIdentifiers.stringFrom(request));
  }

  @Test
  void testListFromDescribeTransactionRequest() {
    DescribeTransactionRequest request = new DescribeTransactionRequest();
    request.setId("transaction-123");

    List<String> expected = Collections.singletonList("transaction-123");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testStringFromAlterTransactionRequest() {
    AlterTransactionRequest request = new AlterTransactionRequest();
    request.setId("transaction-456");

    assertEquals("transaction-456", ObjectIdentifiers.stringFrom(request));
  }

  @Test
  void testListFromAlterTransactionRequest() {
    AlterTransactionRequest request = new AlterTransactionRequest();
    request.setId("transaction-456");

    List<String> expected = Collections.singletonList("transaction-456");
    assertEquals(expected, ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testEdgeCases() {
    // Test with special characters in names
    CreateNamespaceRequest specialRequest = new CreateNamespaceRequest();
    specialRequest.setParent(Arrays.asList("parent-1", "parent.2"));
    specialRequest.setName("test.namespace");
    assertEquals("parent-1$parent.2", ObjectIdentifiers.stringFrom(specialRequest, "$"));
  }

  @Test
  void testNullHandling() {
    // Test that methods handle null gracefully
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setParent(null);
    request.setName("test");

    assertEquals(".", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals(Collections.emptyList(), ObjectIdentifiers.listFrom(request));
  }

  @Test
  void testEmptyListHandling() {
    // Test with empty lists
    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setParent(Collections.emptyList());
    request.setName("test");

    assertEquals(".", ObjectIdentifiers.stringFrom(request, "."));
    assertEquals(Collections.emptyList(), ObjectIdentifiers.listFrom(request));
  }
}
