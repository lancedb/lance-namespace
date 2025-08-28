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

import com.lancedb.lance.Dataset;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.JsonArrowDataType;
import com.lancedb.lance.namespace.model.JsonArrowField;
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.AlreadyExistsException;
import software.amazon.awssdk.services.glue.model.CreateDatabaseRequest;
import software.amazon.awssdk.services.glue.model.CreateDatabaseResponse;
import software.amazon.awssdk.services.glue.model.Database;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseRequest;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseResponse;
import software.amazon.awssdk.services.glue.model.DeleteTableRequest;
import software.amazon.awssdk.services.glue.model.DeleteTableResponse;
import software.amazon.awssdk.services.glue.model.EntityNotFoundException;
import software.amazon.awssdk.services.glue.model.GetDatabaseRequest;
import software.amazon.awssdk.services.glue.model.GetDatabaseResponse;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;
import software.amazon.awssdk.services.glue.model.GetTableRequest;
import software.amazon.awssdk.services.glue.model.GetTableResponse;
import software.amazon.awssdk.services.glue.model.GetTableVersionRequest;
import software.amazon.awssdk.services.glue.model.GetTableVersionResponse;
import software.amazon.awssdk.services.glue.model.GetTablesRequest;
import software.amazon.awssdk.services.glue.model.GetTablesResponse;
import software.amazon.awssdk.services.glue.model.StorageDescriptor;
import software.amazon.awssdk.services.glue.model.Table;
import software.amazon.awssdk.services.glue.model.TableVersion;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lancedb.lance.namespace.glue.GlueNamespace.LANCE_TABLE_TYPE_VALUE;
import static com.lancedb.lance.namespace.glue.GlueNamespace.TABLE_TYPE_PROP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestGlueNamespace {

  @Mock private GlueClient glue;

  private GlueNamespace glueNamespace;
  private static BufferAllocator allocator;
  @TempDir Path tempDir;

  @BeforeAll
  public static void setUpAll() {
    allocator = new RootAllocator(Long.MAX_VALUE);
  }

  @AfterAll
  public static void tearDownAll() {
    if (allocator != null) {
      allocator.close();
    }
  }

  @BeforeEach
  public void before() {
    this.glueNamespace = new GlueNamespace();
    GlueNamespaceConfig glueProperties = new GlueNamespaceConfig();
    glueNamespace.initialize(glueProperties, glue, allocator);
  }

  @Test
  public void testBasicListNamespaces() {
    when(glue.getDatabases(any(GetDatabasesRequest.class)))
        .thenReturn(
            GetDatabasesResponse.builder()
                .databaseList(
                    Database.builder().name("db1").build(), Database.builder().name("db2").build())
                .build());

    ListNamespacesRequest request = new ListNamespacesRequest();
    ListNamespacesResponse response = glueNamespace.listNamespaces(request);

    assertNotNull(response.getNamespaces());
    assertEquals(2, response.getNamespaces().size());
    assertEquals(Sets.newHashSet("db1", "db2"), response.getNamespaces());
    assertNull(response.getPageToken());
  }

  @Test
  void testListNamespacesPagination() {
    GetDatabasesResponse respOne =
        GetDatabasesResponse.builder()
            .databaseList(
                Database.builder().name("db1").build(), Database.builder().name("db2").build())
            .nextToken("tkn1")
            .build();

    GetDatabasesResponse respTwo =
        GetDatabasesResponse.builder()
            .databaseList(Database.builder().name("db3").build())
            .nextToken(null)
            .build();

    when(glue.getDatabases(any(GetDatabasesRequest.class))).thenReturn(respOne, respTwo);

    ListNamespacesResponse resp = glueNamespace.listNamespaces(new ListNamespacesRequest());
    assertEquals(Sets.newHashSet("db1", "db2", "db3"), resp.getNamespaces());
    assertNull(resp.getPageToken());
  }

  @Test
  void testEmptyListNamespaces() {
    when(glue.getDatabases(any(GetDatabasesRequest.class)))
        .thenReturn(GetDatabasesResponse.builder().build());

    ListNamespacesRequest request = new ListNamespacesRequest();
    ListNamespacesResponse response = glueNamespace.listNamespaces(request);

    assertNotNull(response.getNamespaces());
    assertEquals(0, response.getNamespaces().size());
    assertNull(response.getPageToken());
  }

  @Test
  void testNestedParentThrows() {
    ListNamespacesRequest req = new ListNamespacesRequest().id(ImmutableList.of("parent", "test"));
    assertThrows(LanceNamespaceException.class, () -> glueNamespace.listNamespaces(req));
  }

  @Test
  public void testBasicDescribeNamespaces() {
    Map<String, String> parameters =
        ImmutableMap.of("key", "value", "description", "test", "location", "s3://bucket/db1");
    Database database =
        Database.builder()
            .name("db1")
            .description("test")
            .locationUri("s3://bucket/db1")
            .parameters(parameters)
            .build();

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(database).build());

    DescribeNamespaceRequest request = new DescribeNamespaceRequest().id(ImmutableList.of("db1"));
    DescribeNamespaceResponse response = glueNamespace.describeNamespace(request);

    assertEquals(response.getProperties(), parameters);
  }

  @Test
  public void testCreateNamespaceWithCreateMode() {
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of("test"))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE)
            .properties(ImmutableMap.of("location", "s3://bucket/test"));

    // Mock namespace doesn't exist
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());

    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenReturn(CreateDatabaseResponse.builder().build());

    CreateNamespaceResponse response = glueNamespace.createNamespace(request);

    assertEquals(ImmutableMap.of("location", "s3://bucket/test"), response.getProperties());
  }

  @Test
  public void testCreateNamespaceWithCreateModeAlreadyExists() {
    String namespaceName = "existing";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE);

    // Mock namespace exists
    Database existingDatabase = Database.builder().name(namespaceName).build();

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(existingDatabase).build());

    // Mock create already exists
    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenThrow(AlreadyExistsException.builder().message("Database already exists").build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.createNamespace(request));
  }

  @Test
  public void testCreateNamespaceWithExistOkModeNamespaceExists() {
    String namespaceName = "existing";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.EXIST_OK);

    Database existingDatabase =
        Database.builder()
            .name(namespaceName)
            .description("test description")
            .locationUri("s3://bucket/existing")
            .parameters(ImmutableMap.of("key", "val"))
            .build();

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(existingDatabase).build());

    CreateNamespaceResponse response = glueNamespace.createNamespace(request);

    Map<String, String> expectedProperties =
        ImmutableMap.of(
            "key", "val", "description", "test description", "location", "s3://bucket/existing");
    assertEquals(expectedProperties, response.getProperties());
  }

  @Test
  public void testCreateNamespaceWithExistOkModeNamespaceDoesNotExist() {
    String namespaceName = "ns1";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.EXIST_OK)
            .properties(ImmutableMap.of("key", "val"));

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());
    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenReturn(CreateDatabaseResponse.builder().build());

    CreateNamespaceResponse response = glueNamespace.createNamespace(request);

    assertEquals(ImmutableMap.of("key", "val"), response.getProperties());
    verify(glue).createDatabase(any(CreateDatabaseRequest.class));
  }

  @Test
  public void testCreateNamespaceWithOverwriteMode() {
    String namespaceName = "overwrite";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.OVERWRITE);

    // Mock namespace exists
    Database existingDatabase = Database.builder().name(namespaceName).build();
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(existingDatabase).build());

    // Mock successful drop
    when(glue.deleteDatabase(any(DeleteDatabaseRequest.class)))
        .thenReturn(DeleteDatabaseResponse.builder().build());

    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenReturn(CreateDatabaseResponse.builder().build());

    glueNamespace.createNamespace(request);

    verify(glue).deleteDatabase(any(DeleteDatabaseRequest.class));
    verify(glue).createDatabase(any(CreateDatabaseRequest.class));
  }

  @Test
  public void testCreateNamespaceWithOverwriteModeNamespaceDoesNotExist() {
    String namespaceName = "overwrite";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.OVERWRITE);

    // Mock namespace doesn't exist
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());
    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenReturn(CreateDatabaseResponse.builder().build());

    glueNamespace.createNamespace(request);

    verify(glue, never()).deleteDatabase(any(DeleteDatabaseRequest.class));
    verify(glue).createDatabase(any(CreateDatabaseRequest.class));
  }

  @Test
  public void testBasicCreateNamespaceWithLocationAndDescription() {
    String namespaceName = "ns1";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE)
            .properties(
                ImmutableMap.of(
                    "location",
                    "s3://bucket/test",
                    "description",
                    "Test description",
                    "key",
                    "val"));

    // Mock namespace doesn't exist
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());

    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenReturn(CreateDatabaseResponse.builder().build());
    CreateNamespaceResponse response = glueNamespace.createNamespace(request);

    Map<String, String> expectedProperties =
        ImmutableMap.of(
            "location", "s3://bucket/test", "description", "Test description", "key", "val");
    assertEquals(expectedProperties, response.getProperties());
    verify(glue).createDatabase(any(CreateDatabaseRequest.class));
  }

  @Test
  public void testCreateNamespaceWithNullName() {
    CreateNamespaceRequest request =
        new CreateNamespaceRequest().mode(CreateNamespaceRequest.ModeEnum.CREATE);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.createNamespace(request));
  }

  @Test
  public void testCreateNamespaceWithEmptyName() {
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(""))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.createNamespace(request));
  }

  @Test
  public void testCreateNamespaceWithNestedParent() {
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of("parent", "ns1"))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.createNamespace(request));
  }

  @Test
  public void testDropNamespaceWithFailModeExists() {
    String namespaceName = "ns1";
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(DropNamespaceRequest.ModeEnum.FAIL);

    // Mock database exists
    Database database = Database.builder().name(namespaceName).build();
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(database).build());

    // Mock empty table list
    when(glue.getTables(any(GetTablesRequest.class)))
        .thenReturn(GetTablesResponse.builder().build());

    when(glue.deleteDatabase(any(DeleteDatabaseRequest.class)))
        .thenReturn(DeleteDatabaseResponse.builder().build());

    glueNamespace.dropNamespace(request);

    verify(glue).getDatabase(any(GetDatabaseRequest.class));
    verify(glue).getTables(any(GetTablesRequest.class));
    verify(glue).deleteDatabase(any(DeleteDatabaseRequest.class));
  }

  @Test
  public void testDropNamespaceWithFailModeDoesNotExist() {
    String namespaceName = "nonexistent";
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(DropNamespaceRequest.ModeEnum.FAIL);

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.dropNamespace(request));
  }

  @Test
  public void testDropNamespaceWithSkipModeDoesNotExist() {
    String namespaceName = "nonexistent";
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(DropNamespaceRequest.ModeEnum.SKIP);

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());

    glueNamespace.dropNamespace(request);

    verify(glue).getDatabase(any(GetDatabaseRequest.class));
  }

  @Test
  public void testDropNamespaceWithRestrictBehaviorHasTables() {
    String namespaceName = "ns1";
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(DropNamespaceRequest.ModeEnum.FAIL)
            .behavior(DropNamespaceRequest.BehaviorEnum.RESTRICT);

    Database database = Database.builder().name(namespaceName).build();
    Table table = Table.builder().name("table").build();

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(database).build());

    when(glue.getTables(any(GetTablesRequest.class)))
        .thenReturn(GetTablesResponse.builder().tableList(table).build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.dropNamespace(request));
  }

  @Test
  public void testDropNamespaceWithCascadeBehaviorHasTables() {
    String namespaceName = "ns1";
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(DropNamespaceRequest.ModeEnum.FAIL)
            .behavior(DropNamespaceRequest.BehaviorEnum.CASCADE);

    Database database = Database.builder().name(namespaceName).build();
    Table table1 = Table.builder().name("table1").build();
    Table table2 = Table.builder().name("table2").build();

    // Mock database call
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(database).build());

    // Mock get tables for cascade
    when(glue.getTables(any(GetTablesRequest.class)))
        .thenReturn(GetTablesResponse.builder().tableList(table1, table2).build());

    when(glue.deleteDatabase(any(DeleteDatabaseRequest.class)))
        .thenReturn(DeleteDatabaseResponse.builder().build());

    glueNamespace.dropNamespace(request);

    verify(glue).getTables(any(GetTablesRequest.class));
    verify(glue, times(2)).deleteTable(any(DeleteTableRequest.class));
    verify(glue).deleteDatabase(any(DeleteDatabaseRequest.class));
  }

  @Test
  public void testDeleteAllTablesDropsLanceAndNonLance() throws Exception {
    String namespace = "ns1";
    Path nsDir = tempDir.resolve(namespace);
    Path lanceTable = nsDir.resolve("tbl1");

    // First create a lance table
    com.lancedb.lance.namespace.model.CreateTableRequest createReq =
        new com.lancedb.lance.namespace.model.CreateTableRequest()
            .id(ImmutableList.of(namespace, "tbl1"))
            .location(lanceTable.toString());
    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());
    glueNamespace.createTable(createReq, createTestArrowData());

    // Create a mocked directory of another table with data
    Path nonLanceTable = nsDir.resolve("tbl2");
    Files.createDirectories(nonLanceTable);
    Files.write(nonLanceTable.resolve("foo.metadata"), "bar".getBytes());
    assertTrue(Files.exists(nonLanceTable.resolve("foo.metadata")));
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(
            GetDatabaseResponse.builder()
                .database(Database.builder().name(namespace).build())
                .build());

    // Mock delete glue calls
    Table t1 =
        Table.builder()
            .databaseName(namespace)
            .name("tbl1")
            .storageDescriptor(StorageDescriptor.builder().location(lanceTable.toString()).build())
            .parameters(ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE))
            .build();
    Table t2 =
        Table.builder()
            .databaseName(namespace)
            .name("tbl2")
            .storageDescriptor(
                StorageDescriptor.builder().location(nonLanceTable.toString()).build())
            .build();

    when(glue.getTables(any(GetTablesRequest.class)))
        .thenReturn(GetTablesResponse.builder().tableList(t1, t2).build());
    when(glue.deleteTable(any(DeleteTableRequest.class)))
        .thenReturn(DeleteTableResponse.builder().build());
    when(glue.deleteDatabase(any(DeleteDatabaseRequest.class)))
        .thenReturn(DeleteDatabaseResponse.builder().build());

    // Drop with cascade
    DropNamespaceRequest drop =
        new DropNamespaceRequest()
            .id(ImmutableList.of(namespace))
            .mode(DropNamespaceRequest.ModeEnum.FAIL)
            .behavior(DropNamespaceRequest.BehaviorEnum.CASCADE);
    glueNamespace.dropNamespace(drop);

    assertFalse(Files.exists(lanceTable), "Lance dataset directory should have been deleted");
    assertFalse(Files.exists(nonLanceTable), "Non-Lance directory should have been deleted");
  }

  @Test
  public void testDropNamespaceWithNullName() {
    DropNamespaceRequest request =
        new DropNamespaceRequest().mode(DropNamespaceRequest.ModeEnum.FAIL);
    assertThrows(LanceNamespaceException.class, () -> glueNamespace.dropNamespace(request));
  }

  @Test
  public void testDropNamespaceWithEmptyName() {
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of(""))
            .mode(DropNamespaceRequest.ModeEnum.FAIL);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.dropNamespace(request));
  }

  @Test
  public void testDropNamespaceWithNestedParent() {
    DropNamespaceRequest request =
        new DropNamespaceRequest()
            .id(ImmutableList.of("parent", "ns1"))
            .mode(DropNamespaceRequest.ModeEnum.FAIL);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.dropNamespace(request));
  }

  @Test
  public void testNamespaceExistsTrue() {
    String namespaceName = "existing";
    NamespaceExistsRequest request =
        new NamespaceExistsRequest().id(ImmutableList.of(namespaceName));

    Database database = Database.builder().name(namespaceName).build();
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(database).build());

    // Should not throw any exception for existing namespace
    glueNamespace.namespaceExists(request);

    verify(glue).getDatabase(any(GetDatabaseRequest.class));
  }

  @Test
  public void testNamespaceExistsFalse() {
    String namespaceName = "nonexistent";
    NamespaceExistsRequest request =
        new NamespaceExistsRequest().id(ImmutableList.of(namespaceName));

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Entity Not Found").build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.namespaceExists(request));
    verify(glue).getDatabase(any(GetDatabaseRequest.class));
  }

  @Test
  public void testNamespaceExistsWithNullName() {
    NamespaceExistsRequest request = new NamespaceExistsRequest();

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.namespaceExists(request));
  }

  @Test
  public void testNamespaceExistsWithEmptyName() {
    NamespaceExistsRequest request = new NamespaceExistsRequest().id(ImmutableList.of(""));

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.namespaceExists(request));
  }

  @Test
  public void testNamespaceExistsWithNestedParent() {
    NamespaceExistsRequest request =
        new NamespaceExistsRequest().id(ImmutableList.of("parent", "test"));

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.namespaceExists(request));
  }

  @Test
  public void testBasicListTables() {
    Map<String, String> parameters = ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE);

    when(glue.getTables(any(GetTablesRequest.class)))
        .thenReturn(
            GetTablesResponse.builder()
                .tableList(
                    Table.builder().name("t1").parameters(parameters).build(),
                    Table.builder().name("t2").parameters(parameters).build())
                .nextToken(null)
                .build());

    ListTablesRequest req = new ListTablesRequest().id(ImmutableList.of("ns1"));
    ListTablesResponse resp = glueNamespace.listTables(req);

    assertEquals(Sets.newHashSet("t1", "t2"), resp.getTables());
    assertNull(resp.getPageToken());
  }

  @Test
  void testListTablesPagination() {
    Map<String, String> parameters = ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE);
    GetTablesResponse respOne =
        GetTablesResponse.builder()
            .tableList(
                Table.builder().name("tbl1").parameters(parameters).build(),
                Table.builder().name("tbl2").parameters(parameters).build())
            .nextToken("tkn1")
            .build();

    GetTablesResponse respTwo =
        GetTablesResponse.builder()
            .tableList(Table.builder().name("tbl3").parameters(parameters).build())
            .nextToken(null)
            .build();

    when(glue.getTables(any(GetTablesRequest.class))).thenReturn(respOne, respTwo);

    ListTablesResponse resp =
        glueNamespace.listTables(new ListTablesRequest().id(ImmutableList.of("ns1")));
    assertEquals(Sets.newHashSet("tbl1", "tbl2", "tbl3"), resp.getTables());
    assertNull(resp.getPageToken());
  }

  @Test
  public void testListTablesEmpty() {
    when(glue.getTables(any(GetTablesRequest.class)))
        .thenReturn(GetTablesResponse.builder().build());

    ListTablesResponse resp =
        glueNamespace.listTables(new ListTablesRequest().id(ImmutableList.of("ns1")));
    assertNotNull(resp.getTables());
    assertEquals(0, resp.getTables().size());
    assertNull(resp.getPageToken());
  }

  @Test
  public void testListTablesWithNullId() {
    assertThrows(
        LanceNamespaceException.class, () -> glueNamespace.listTables(new ListTablesRequest()));
  }

  @Test
  public void testListTablesWithNestedId() {
    assertThrows(
        LanceNamespaceException.class,
        () -> glueNamespace.listTables(new ListTablesRequest().id(ImmutableList.of("ns", "tbl"))));
  }

  @Test
  public void testListTablesNamespaceNotFound() {
    when(glue.getTables(any(GetTablesRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Entity Not Found").build());

    assertThrows(
        LanceNamespaceException.class,
        () ->
            glueNamespace.listTables(new ListTablesRequest().id(ImmutableList.of("nonexistent"))));
  }

  @Test
  public void testDescribeTableBasic() {
    Table tbl =
        Table.builder()
            .name("tbl")
            .storageDescriptor(StorageDescriptor.builder().location("s3://bucket/tbl").build())
            .parameters(ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE))
            .build();
    when(glue.getTable(any(GetTableRequest.class)))
        .thenReturn(GetTableResponse.builder().table(tbl).build());
    DescribeTableResponse resp =
        glueNamespace.describeTable(new DescribeTableRequest().id(ImmutableList.of("ns1", "tbl")));
    assertEquals("s3://bucket/tbl", resp.getLocation());
  }

  @Test
  public void testDescribeTableNonLanceTable() {
    Table tbl =
        Table.builder()
            .name("tbl")
            .storageDescriptor(StorageDescriptor.builder().location("s3://bucket/tbl").build())
            .build();

    when(glue.getTable(any(GetTableRequest.class)))
        .thenReturn(GetTableResponse.builder().table(tbl).build());

    assertThrows(
        LanceNamespaceException.class,
        () ->
            glueNamespace.describeTable(
                new DescribeTableRequest().id(ImmutableList.of("ns", "tbl"))));
  }

  @Test
  public void testDescribeTableNotFound() {
    when(glue.getTable(any(GetTableRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Entity Not Found").build());

    assertThrows(
        LanceNamespaceException.class,
        () ->
            glueNamespace.describeTable(
                new DescribeTableRequest().id(ImmutableList.of("ns1", "tbl"))));
  }

  @Test
  public void testDescribeTableWithInvalidId() {
    assertThrows(
        LanceNamespaceException.class,
        () -> glueNamespace.describeTable(new DescribeTableRequest().id(ImmutableList.of("ns1"))));
  }

  @Test
  public void testBasicRegisterTable() {
    RegisterTableRequest req =
        new RegisterTableRequest()
            .id(ImmutableList.of("ns1", "tbl"))
            .location("s3://bucket/tbl")
            .properties(ImmutableMap.of("key", "val"));

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());

    RegisterTableResponse resp = glueNamespace.registerTable(req);
    assertEquals("s3://bucket/tbl", resp.getLocation());
    assertEquals(ImmutableMap.of("key", "val"), resp.getProperties());
  }

  @Test
  public void testRegisterTableAlreadyExists() {
    RegisterTableRequest req =
        new RegisterTableRequest().id(ImmutableList.of("ns1", "tbl")).location("s3://bucket/tbl");
    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenThrow(AlreadyExistsException.builder().message("Table Already Exists").build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.registerTable(req));
  }

  @Test
  public void testRegisterTableWithOverwrite() {
    // First create a table
    RegisterTableRequest req =
        new RegisterTableRequest().id(ImmutableList.of("ns", "tbl")).location("s3://bucket/tbl");

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());

    glueNamespace.registerTable(req);

    // Now overwrite
    req.setMode(RegisterTableRequest.ModeEnum.OVERWRITE);
    glueNamespace.registerTable(req);
  }

  @Test
  public void testRegisterTableNamespaceNotFound() {
    RegisterTableRequest req =
        new RegisterTableRequest().id(ImmutableList.of("ns1", "tbl")).location("s3://bucket/tbl");
    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Database Not Found").build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.registerTable(req));
  }

  @Test
  public void testRegisterTableMissingLocation() {
    RegisterTableRequest req =
        new RegisterTableRequest().id(ImmutableList.of("ns1", "tbl")).location("");
    assertThrows(LanceNamespaceException.class, () -> glueNamespace.registerTable(req));
  }

  @Test
  public void testBasicDeregisterTable() {
    List<String> id = ImmutableList.of("ns1", "tbl");
    Table tbl =
        Table.builder()
            .name("tbl")
            .storageDescriptor(StorageDescriptor.builder().location("s3://bucket/tbl").build())
            .parameters(ImmutableMap.of("key", "val", TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE))
            .build();
    when(glue.getTable(any(GetTableRequest.class)))
        .thenReturn(GetTableResponse.builder().table(tbl).build());
    when(glue.deleteTable(any(DeleteTableRequest.class)))
        .thenReturn(DeleteTableResponse.builder().build());

    DeregisterTableResponse resp =
        glueNamespace.deregisterTable(new DeregisterTableRequest().id(id));

    assertEquals(id, resp.getId());
    assertEquals("s3://bucket/tbl", resp.getLocation());
    assertEquals(ImmutableMap.of("key", "val", "table_type", "lance"), resp.getProperties());
  }

  @Test
  public void testDeregisterTableRejectsNonLanceTable() {
    Table tbl =
        Table.builder()
            .name("tbl")
            .storageDescriptor(StorageDescriptor.builder().location("s3://bucket/tbl").build())
            .build();

    when(glue.getTable(any(GetTableRequest.class)))
        .thenReturn(GetTableResponse.builder().table(tbl).build());

    assertThrows(
        LanceNamespaceException.class,
        () ->
            glueNamespace.deregisterTable(
                new DeregisterTableRequest().id(ImmutableList.of("ns", "tbl"))));
  }

  @Test
  public void testDeregisterTableNotFound() {
    when(glue.getTable(any(GetTableRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Entity Not Found").build());

    assertThrows(
        LanceNamespaceException.class,
        () ->
            glueNamespace.deregisterTable(
                new DeregisterTableRequest().id(ImmutableList.of("ns1", "tbl"))));
  }

  @Test
  public void testTableExistsNoVersion() {
    ImmutableMap<String, String> parameters =
        ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE);

    TableExistsRequest req = new TableExistsRequest().id(ImmutableList.of("ns1", "tbl"));

    when(glue.getTable(any(GetTableRequest.class)))
        .thenReturn(
            GetTableResponse.builder()
                .table(Table.builder().name("tbl").parameters(parameters).build())
                .build());

    glueNamespace.tableExists(req);
  }

  @Test
  public void testTableExistsWithVersion() {
    TableExistsRequest req =
        new TableExistsRequest().id(ImmutableList.of("ns1", "tbl")).version(42L);

    TableVersion tableVersion =
        TableVersion.builder()
            .table(
                Table.builder()
                    .parameters(ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE))
                    .build())
            .build();

    when(glue.getTableVersion(any(GetTableVersionRequest.class)))
        .thenReturn(GetTableVersionResponse.builder().tableVersion(tableVersion).build());

    glueNamespace.tableExists(req);
  }

  @Test
  public void testTableExistsNotFound() {
    TableExistsRequest req = new TableExistsRequest().id(ImmutableList.of("ns1", "tbl"));
    when(glue.getTable(any(GetTableRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Entity Not Found").build());

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.tableExists(req));
  }

  @Test
  public void testTableExistsInvalidId() {
    TableExistsRequest req = new TableExistsRequest();

    req.addIdItem("ns1");
    req.addIdItem(null);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.tableExists(req));
  }

  @Test
  public void testBasicCreateTable() {
    String location = tempDir.resolve("ns1/tbl").toString();
    CreateTableRequest request =
        new CreateTableRequest().id(ImmutableList.of("ns1", "tbl")).location(location);

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());

    CreateTableResponse response = glueNamespace.createTable(request, createTestArrowData());
    assertNotNull(response);
    assertNotNull(response.getLocation());
    assertTrue(response.getLocation().contains("tbl"));
    assertEquals(Long.valueOf(1), response.getVersion());

    // Verify Lance dataset was created (check for _versions directory)
    File tableDir = new File(location);
    assertTrue(tableDir.exists());
    assertTrue(tableDir.isDirectory());

    File versionsDir = new File(location, "_versions");
    assertTrue(versionsDir.exists());
    assertTrue(versionsDir.isDirectory());

    // Verify dataset can be loaded and has expected schema
    try (Dataset dataset = Dataset.open(response.getLocation(), allocator)) {
      assertNotNull(dataset);
      assertNotNull(dataset.getSchema());
      assertEquals(2, dataset.getSchema().getFields().size());
      assertEquals("id", dataset.getSchema().getFields().get(0).getName());
      assertEquals("name", dataset.getSchema().getFields().get(1).getName());
    } catch (Exception e) {
      throw new RuntimeException("Failed to verify created dataset", e);
    }
  }

  @Test
  public void testCreateTableDerivesLocationFromNamespaceUri() throws Exception {
    com.lancedb.lance.namespace.model.CreateTableRequest req =
        new com.lancedb.lance.namespace.model.CreateTableRequest()
            .id(ImmutableList.of("ns1", "tbl"));

    Database db =
        Database.builder()
            .name("ns1")
            .locationUri(tempDir.resolve("ns1").toUri().toString())
            .build();

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(db).build());

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());

    com.lancedb.lance.namespace.model.CreateTableResponse resp =
        glueNamespace.createTable(req, createTestArrowData());

    String expectedPrefix = tempDir.resolve("ns1").resolve("tbl").toString();
    assertTrue(resp.getLocation().contains(expectedPrefix));

    File dir = new File(new URI(resp.getLocation()));
    assertTrue(dir.exists(), "Derived table directory must exist");
    assertTrue(new File(dir, "_versions").isDirectory());
  }

  @Test
  public void testCreateTableDerivesLocationFromDefaultRootWhenNoNamespaceUri() {
    // Initialize with a custom root directory using tempDir
    GlueNamespaceConfig config =
        new GlueNamespaceConfig(ImmutableMap.of("root", tempDir.toString()));
    glueNamespace.initialize(config, glue, allocator);

    com.lancedb.lance.namespace.model.CreateTableRequest req =
        new com.lancedb.lance.namespace.model.CreateTableRequest()
            .id(ImmutableList.of("ns1", "tbl"));

    Database db = Database.builder().name("ns1").build();

    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenReturn(GetDatabaseResponse.builder().database(db).build());

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());

    // Now the implementation uses the configured root when namespace URI is missing
    com.lancedb.lance.namespace.model.CreateTableResponse resp =
        glueNamespace.createTable(req, createTestArrowData());

    // The location should be derived from the configured root
    assertNotNull(resp.getLocation());
    String expectedLocationPattern = String.format("%s/ns1/tbl.lance", tempDir.toString());
    assertEquals(expectedLocationPattern, resp.getLocation());

    // Verify the dataset was created
    Path tableDir = tempDir.resolve("ns1").resolve("tbl.lance");
    assertTrue(Files.exists(tableDir));
    assertTrue(Files.exists(tableDir.resolve("_versions")));
  }

  @Test
  public void testCreateTableConflictCleansUpDataset() {
    String namespace = "ns";
    String tbl = "tbl";
    Path loc = tempDir.resolve(namespace).resolve(tbl);

    com.lancedb.lance.namespace.model.CreateTableRequest req =
        new com.lancedb.lance.namespace.model.CreateTableRequest()
            .id(ImmutableList.of(namespace, tbl))
            .location(loc.toString());

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenThrow(AlreadyExistsException.builder().message("Table already exists").build());

    assertThrows(
        LanceNamespaceException.class, () -> glueNamespace.createTable(req, createTestArrowData()));
    assertFalse(Files.exists(loc), "Dataset should be removed on exception");
  }

  @Test
  public void testCreateTableMissingSchema() {
    com.lancedb.lance.namespace.model.CreateTableRequest req =
        new com.lancedb.lance.namespace.model.CreateTableRequest()
            .id(ImmutableList.of("ns", "tbl"))
            .location(tempDir.toString());

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class, () -> glueNamespace.createTable(req, new byte[0]));
    assertTrue(ex.getMessage().contains("Arrow IPC"));
  }

  @Test
  public void testDropTableExplicitLocationOnDisk() {
    // First create table
    Path location = tempDir.resolve("ns1/tbl");
    List<String> tableId = ImmutableList.of("ns1", "tbl");
    com.lancedb.lance.namespace.model.CreateTableRequest request =
        new com.lancedb.lance.namespace.model.CreateTableRequest()
            .id(tableId)
            .location(location.toString());

    when(glue.createTable(any(software.amazon.awssdk.services.glue.model.CreateTableRequest.class)))
        .thenReturn(
            software.amazon.awssdk.services.glue.model.CreateTableResponse.builder().build());

    glueNamespace.createTable(request, createTestArrowData());

    // Verify it exists
    File tableDir = new File(location.toString());
    assertTrue(tableDir.exists());
    File versionsDir = new File(tableDir, "_versions");
    assertTrue(versionsDir.exists());

    // Drop the table
    Table tbl =
        Table.builder()
            .databaseName(tableId.get(0))
            .name(tableId.get(1))
            .storageDescriptor(StorageDescriptor.builder().location(location.toString()).build())
            .parameters(ImmutableMap.of(TABLE_TYPE_PROP, LANCE_TABLE_TYPE_VALUE))
            .build();
    when(glue.getTable(any(GetTableRequest.class)))
        .thenReturn(GetTableResponse.builder().table(tbl).build());

    DropTableRequest dropRequest = new DropTableRequest();
    dropRequest.setId(tableId);
    DropTableResponse response = glueNamespace.dropTable(dropRequest);

    assertNotNull(response);
    assertFalse(tableDir.exists());
  }

  @Test
  public void testDropTableTableNotFound() {
    when(glue.getTable(any(GetTableRequest.class)))
        .thenThrow(EntityNotFoundException.builder().message("Entity Not found").build());
    DropTableRequest req = new DropTableRequest().id(ImmutableList.of("ns1", "tbl"));
    LanceNamespaceException e =
        assertThrows(LanceNamespaceException.class, () -> glueNamespace.dropTable(req));

    assertTrue(e.getMessage().contains("Glue table not found: ns1.tbl"));
  }

  private JsonArrowSchema createTestSchema() {
    // Create a simple schema with id (int32) and name (string) fields
    JsonArrowDataType intType = new JsonArrowDataType();
    intType.setType("int32");

    JsonArrowDataType stringType = new JsonArrowDataType();
    stringType.setType("utf8");

    JsonArrowField idField = new JsonArrowField();
    idField.setName("id");
    idField.setType(intType);
    idField.setNullable(false);

    JsonArrowField nameField = new JsonArrowField();
    nameField.setName("name");
    nameField.setType(stringType);
    nameField.setNullable(true);

    List<JsonArrowField> fields = new ArrayList<>();
    fields.add(idField);
    fields.add(nameField);

    JsonArrowSchema schema = new JsonArrowSchema();
    schema.setFields(fields);
    return schema;
  }

  private byte[] createTestArrowData() {
    // Create a proper Arrow IPC stream with test schema
    try {
      return com.lancedb.lance.namespace.util.ArrowIpcUtil.createEmptyArrowIpcStream(
          createTestSchema());
    } catch (Exception e) {
      throw new RuntimeException("Failed to create test Arrow data", e);
    }
  }
}
