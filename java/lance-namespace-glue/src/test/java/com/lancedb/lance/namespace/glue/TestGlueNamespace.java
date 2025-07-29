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

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.CreateDatabaseRequest;
import software.amazon.awssdk.services.glue.model.CreateDatabaseResponse;
import software.amazon.awssdk.services.glue.model.Database;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseRequest;
import software.amazon.awssdk.services.glue.model.DeleteDatabaseResponse;
import software.amazon.awssdk.services.glue.model.DeleteTableRequest;
import software.amazon.awssdk.services.glue.model.EntityNotFoundException;
import software.amazon.awssdk.services.glue.model.GetDatabaseRequest;
import software.amazon.awssdk.services.glue.model.GetDatabaseResponse;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;
import software.amazon.awssdk.services.glue.model.GetTablesRequest;
import software.amazon.awssdk.services.glue.model.GetTablesResponse;
import software.amazon.awssdk.services.glue.model.Table;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestGlueNamespace {

  @Mock private GlueClient glue;

  private GlueNamespace glueNamespace;

  @BeforeEach
  public void before() {
    this.glueNamespace = new GlueNamespace();
    GlueNamespaceConfig glueProperties = new GlueNamespaceConfig();
    glueNamespace.initialize(glueProperties, glue);
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
  void testListingAllPagination() {
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
        ImmutableMap.of(
            "key",
            "value",
            GlueConstants.PARAM_DESCRIPTION,
            "test",
            GlueConstants.PARAM_LOCATION,
            "s3://bucket/db1");
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
            .properties(ImmutableMap.of(GlueConstants.PARAM_LOCATION, "s3://bucket/test"));

    // Mock namespace doesn't exist
    when(glue.getDatabase(any(GetDatabaseRequest.class)))
        .thenThrow(EntityNotFoundException.builder().build());

    when(glue.createDatabase(any(CreateDatabaseRequest.class)))
        .thenReturn(CreateDatabaseResponse.builder().build());

    CreateNamespaceResponse response = glueNamespace.createNamespace(request);

    assertEquals(
        ImmutableMap.of(GlueConstants.PARAM_LOCATION, "s3://bucket/test"),
        response.getProperties());
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
            "key",
            "val",
            GlueConstants.PARAM_DESCRIPTION,
            "test description",
            GlueConstants.PARAM_LOCATION,
            "s3://bucket/existing");
    assertEquals(expectedProperties, response.getProperties());
  }

  @Test
  public void testCreateNamespaceWithExistOkModeNamespaceDoesNotExist() {
    String namespaceName = "new";
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
    String namespaceName = "test";
    CreateNamespaceRequest request =
        new CreateNamespaceRequest()
            .id(ImmutableList.of(namespaceName))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE)
            .properties(
                ImmutableMap.of(
                    GlueConstants.PARAM_LOCATION,
                    "s3://bucket/test",
                    GlueConstants.PARAM_DESCRIPTION,
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
            GlueConstants.PARAM_LOCATION,
            "s3://bucket/test",
            GlueConstants.PARAM_DESCRIPTION,
            "Test description",
            "key",
            "val");
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
            .id(ImmutableList.of("parent", "test"))
            .mode(CreateNamespaceRequest.ModeEnum.CREATE);

    assertThrows(LanceNamespaceException.class, () -> glueNamespace.createNamespace(request));
  }

  @Test
  public void testDropNamespaceWithFailModeExists() {
    String namespaceName = "test";
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
    String namespaceName = "test";
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
    String namespaceName = "test";
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
            .id(ImmutableList.of("parent", "test"))
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
        .thenThrow(EntityNotFoundException.builder().build());

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
}
