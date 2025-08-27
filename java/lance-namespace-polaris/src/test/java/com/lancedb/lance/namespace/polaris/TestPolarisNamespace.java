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
package com.lancedb.lance.namespace.polaris;

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.rest.RestClient;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestPolarisNamespace {

  @Mock private RestClient restClient;

  private PolarisNamespace namespace;
  private BufferAllocator allocator;
  private Map<String, String> config;

  @BeforeEach
  public void setUp() throws Exception {
    allocator = new RootAllocator();
    namespace = new PolarisNamespace();

    config = new HashMap<>();
    config.put("endpoint", "http://localhost:8182");
    config.put("auth.token", "test-token");

    // Initialize namespace with config
    namespace.initialize(config, allocator);

    // Replace the RestClient with mock using reflection
    Field restClientField = PolarisNamespace.class.getDeclaredField("restClient");
    restClientField.setAccessible(true);
    restClientField.set(namespace, restClient);
  }

  @AfterEach
  public void tearDown() {
    if (allocator != null) {
      allocator.close();
    }
  }

  @Test
  public void testInitialize() {
    // Test successful initialization
    PolarisNamespace ns = new PolarisNamespace();
    ns.initialize(config, allocator);
    // Should not throw
  }

  @Test
  public void testInitializeMissingEndpoint() {
    Map<String, String> badConfig = new HashMap<>();
    badConfig.put("polaris.catalog", "test_catalog");

    PolarisNamespace ns = new PolarisNamespace();
    assertThatThrownBy(() -> ns.initialize(badConfig, allocator))
        .isInstanceOf(LanceNamespaceException.class)
        .hasMessageContaining("Required configuration property 'endpoint' is not set");
  }

  @Test
  public void testCreateNamespace() throws IOException {
    PolarisModels.NamespaceResponse mockResponse = new PolarisModels.NamespaceResponse();
    mockResponse.setNamespace(Arrays.asList("test_catalog", "schema1"));
    mockResponse.setProperties(Collections.singletonMap("key", "value"));

    when(restClient.post(
            eq("/namespaces"),
            any(PolarisModels.CreateNamespaceRequest.class),
            eq(PolarisModels.NamespaceResponse.class)))
        .thenReturn(mockResponse);

    CreateNamespaceRequest request = new CreateNamespaceRequest();
    request.setId(Arrays.asList("test_catalog", "schema1"));
    request.setProperties(Collections.singletonMap("key", "value"));

    CreateNamespaceResponse response = namespace.createNamespace(request);

    // Response doesn't have getId() method, just verify properties
    assertThat(response.getProperties()).containsEntry("key", "value");
  }

  @Test
  public void testDescribeNamespace() throws IOException {
    PolarisModels.NamespaceResponse mockResponse = new PolarisModels.NamespaceResponse();
    mockResponse.setNamespace(Arrays.asList("test_catalog", "schema1"));
    mockResponse.setProperties(Collections.singletonMap("description", "test schema"));

    when(restClient.get(
            eq("/namespaces/test_catalog.schema1"), eq(PolarisModels.NamespaceResponse.class)))
        .thenReturn(mockResponse);

    DescribeNamespaceRequest request = new DescribeNamespaceRequest();
    request.setId(Arrays.asList("test_catalog", "schema1"));

    DescribeNamespaceResponse response = namespace.describeNamespace(request);

    // Response doesn't have getId() method, just verify properties
    assertThat(response.getProperties()).containsEntry("description", "test schema");
  }

  @Test
  public void testListNamespaces() throws IOException {
    PolarisModels.ListNamespacesResponse mockResponse = new PolarisModels.ListNamespacesResponse();
    PolarisModels.ListNamespacesResponse.Namespace ns1 =
        new PolarisModels.ListNamespacesResponse.Namespace();
    ns1.setNamespace(Arrays.asList("test_catalog", "schema1"));
    PolarisModels.ListNamespacesResponse.Namespace ns2 =
        new PolarisModels.ListNamespacesResponse.Namespace();
    ns2.setNamespace(Arrays.asList("test_catalog", "schema2"));
    mockResponse.setNamespaces(Arrays.asList(ns1, ns2));

    when(restClient.get(eq("/namespaces"), eq(PolarisModels.ListNamespacesResponse.class)))
        .thenReturn(mockResponse);

    ListNamespacesRequest request = new ListNamespacesRequest();

    ListNamespacesResponse response = namespace.listNamespaces(request);

    assertThat(response.getNamespaces()).hasSize(2);
    assertThat(response.getNamespaces()).contains("test_catalog.schema1", "test_catalog.schema2");
  }

  @Test
  public void testDropNamespace() throws IOException {
    DropNamespaceRequest request = new DropNamespaceRequest();
    request.setId(Arrays.asList("test_catalog", "schema1"));

    DropNamespaceResponse response = namespace.dropNamespace(request);

    verify(restClient).delete("/namespaces/test_catalog.schema1");
    // Response doesn't have getId() method, just verify the delete was called
  }

  @Test
  public void testNamespaceExists() throws IOException {
    when(restClient.get(
            eq("/namespaces/test_catalog.schema1"), eq(PolarisModels.NamespaceResponse.class)))
        .thenReturn(new PolarisModels.NamespaceResponse());

    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setId(Arrays.asList("test_catalog", "schema1"));

    // namespaceExists returns void - it throws exception if not exists
    namespace.namespaceExists(request); // Should not throw
  }

  @Test
  public void testNamespaceNotExists() throws IOException {
    when(restClient.get(
            eq("/namespaces/test_catalog.schema1"), eq(PolarisModels.NamespaceResponse.class)))
        .thenThrow(new IOException("404 Not Found"));

    NamespaceExistsRequest request = new NamespaceExistsRequest();
    request.setId(Arrays.asList("test_catalog", "schema1"));

    // namespaceExists should throw when namespace doesn't exist
    assertThatThrownBy(() -> namespace.namespaceExists(request))
        .isInstanceOf(LanceNamespaceException.class);
  }

  @Test
  public void testCreateTable() throws IOException {
    PolarisModels.GenericTable mockTable = new PolarisModels.GenericTable();
    mockTable.setName("test_table");
    mockTable.setFormat("lance");
    mockTable.setBaseLocation("s3://bucket/path/to/table");
    mockTable.setDoc("Test table"); // Should be returned in doc field
    Map<String, String> props = new HashMap<>();
    props.put("table_type", "lance");
    mockTable.setProperties(props);

    PolarisModels.LoadGenericTableResponse mockResponse =
        new PolarisModels.LoadGenericTableResponse();
    mockResponse.setTable(mockTable);

    when(restClient.post(
            eq("/namespaces/test_catalog.schema1/generic-tables"),
            any(PolarisModels.CreateGenericTableRequest.class),
            eq(PolarisModels.LoadGenericTableResponse.class)))
        .thenReturn(mockResponse);

    CreateTableRequest request = new CreateTableRequest();
    request.setId(Arrays.asList("test_catalog", "schema1", "test_table"));
    request.setLocation("s3://bucket/path/to/table");
    request.setProperties(Collections.singletonMap("comment", "Test table"));

    CreateTableResponse response = namespace.createTable(request, new byte[0]);

    assertThat(response.getLocation()).isEqualTo("s3://bucket/path/to/table");
    assertThat(response.getProperties()).containsEntry("table_type", "lance");
    assertThat(response.getProperties()).containsEntry("comment", "Test table");
  }

  @Test
  public void testDescribeTable() throws IOException {
    PolarisModels.GenericTable mockTable = new PolarisModels.GenericTable();
    mockTable.setName("test_table");
    mockTable.setFormat("lance");
    mockTable.setBaseLocation("s3://bucket/path/to/table");
    mockTable.setDoc("Test table");
    Map<String, String> props = new HashMap<>();
    props.put("table_type", "lance");
    mockTable.setProperties(props);

    PolarisModels.LoadGenericTableResponse mockResponse =
        new PolarisModels.LoadGenericTableResponse();
    mockResponse.setTable(mockTable);

    when(restClient.get(
            eq("/namespaces/test_catalog.schema1/generic-tables/test_table"),
            eq(PolarisModels.LoadGenericTableResponse.class)))
        .thenReturn(mockResponse);

    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Arrays.asList("test_catalog", "schema1", "test_table"));

    DescribeTableResponse response = namespace.describeTable(request);

    assertThat(response.getLocation()).isEqualTo("s3://bucket/path/to/table");
    assertThat(response.getProperties()).containsEntry("comment", "Test table");
    assertThat(response.getProperties()).containsEntry("table_type", "lance");
  }

  @Test
  public void testDescribeTableNotLanceFormat() throws IOException {
    PolarisModels.GenericTable mockTable = new PolarisModels.GenericTable();
    mockTable.setName("test_table");
    mockTable.setFormat("iceberg"); // Not a Lance table
    mockTable.setBaseLocation("s3://bucket/path/to/table");

    PolarisModels.LoadGenericTableResponse mockResponse =
        new PolarisModels.LoadGenericTableResponse();
    mockResponse.setTable(mockTable);

    when(restClient.get(
            eq("/namespaces/test_catalog.schema1/generic-tables/test_table"),
            eq(PolarisModels.LoadGenericTableResponse.class)))
        .thenReturn(mockResponse);

    DescribeTableRequest request = new DescribeTableRequest();
    request.setId(Arrays.asList("test_catalog", "schema1", "test_table"));

    assertThatThrownBy(() -> namespace.describeTable(request))
        .isInstanceOf(LanceNamespaceException.class)
        .hasMessageContaining("is not a Lance table");
  }

  @Test
  public void testListTables() throws IOException {
    PolarisModels.TableIdentifier id1 = new PolarisModels.TableIdentifier();
    id1.setNamespace("test_catalog.schema1");
    id1.setName("table1");

    PolarisModels.TableIdentifier id2 = new PolarisModels.TableIdentifier();
    id2.setNamespace("test_catalog.schema1");
    id2.setName("table2");

    PolarisModels.ListGenericTablesResponse mockResponse =
        new PolarisModels.ListGenericTablesResponse();
    mockResponse.setIdentifiers(Arrays.asList(id1, id2));

    when(restClient.get(
            eq("/namespaces/test_catalog.schema1/generic-tables"),
            eq(PolarisModels.ListGenericTablesResponse.class)))
        .thenReturn(mockResponse);

    ListTablesRequest request = new ListTablesRequest();
    request.setId(Arrays.asList("test_catalog", "schema1"));

    ListTablesResponse response = namespace.listTables(request);

    assertThat(response.getTables()).hasSize(2);
    assertThat(response.getTables()).contains("table1", "table2");
  }

  @Test
  public void testDropTable() throws IOException {
    DropTableRequest request = new DropTableRequest();
    request.setId(Arrays.asList("test_catalog", "schema1", "test_table"));

    DropTableResponse response = namespace.dropTable(request);

    verify(restClient).delete("/namespaces/test_catalog.schema1/generic-tables/test_table");
    // Response doesn't have getId() method, just verify the delete was called
  }
}
