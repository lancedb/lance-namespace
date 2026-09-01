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
package org.lance.namespace.base;

import org.lance.namespace.errors.ErrorCode;
import org.lance.namespace.errors.InvalidInputException;
import org.lance.namespace.errors.LanceNamespaceException;
import org.lance.namespace.errors.UnsupportedOperationException;
import org.lance.namespace.model.*;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.memory.RootAllocator;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.ArrowType;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.FieldType;
import org.apache.arrow.vector.types.pojo.Schema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for BaseLanceNamespace implementation. */
public class BaseLanceNamespaceTest {
  @TempDir Path tempDir;

  private BufferAllocator allocator;
  private BaseLanceNamespace namespace;

  @BeforeEach
  void setUp() {
    allocator = new RootAllocator(Long.MAX_VALUE);
    namespace = new BaseLanceNamespace();

    Map<String, String> config = new HashMap<>();
    config.put("root", tempDir.toString());
    namespace.initialize(config, allocator);
  }

  @AfterEach
  void tearDown() {
    if (namespace != null) {
      namespace.close();
    }
    if (allocator != null) {
      allocator.close();
    }
  }

  private byte[] createArrowData(int[] ids, String[] names, int[] ages) throws Exception {
    Schema schema =
        new Schema(
            Arrays.asList(
                new Field("id", FieldType.nullable(new ArrowType.Int(32, true)), null),
                new Field("name", FieldType.nullable(new ArrowType.Utf8()), null),
                new Field("age", FieldType.nullable(new ArrowType.Int(32, true)), null)));

    try (VectorSchemaRoot root = VectorSchemaRoot.create(schema, allocator)) {
      IntVector idVector = (IntVector) root.getVector("id");
      VarCharVector nameVector = (VarCharVector) root.getVector("name");
      IntVector ageVector = (IntVector) root.getVector("age");

      idVector.allocateNew(ids.length);
      nameVector.allocateNew(ids.length);
      ageVector.allocateNew(ids.length);

      for (int i = 0; i < ids.length; i++) {
        idVector.set(i, ids[i]);
        nameVector.set(i, names[i].getBytes());
        ageVector.set(i, ages[i]);
      }

      idVector.setValueCount(ids.length);
      nameVector.setValueCount(ids.length);
      ageVector.setValueCount(ids.length);
      root.setRowCount(ids.length);

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      try (ArrowStreamWriter writer = new ArrowStreamWriter(root, null, out)) {
        writer.writeBatch();
      }
      return out.toByteArray();
    }
  }

  private byte[] createTestTableData() throws Exception {
    return createArrowData(
        new int[] {1, 2, 3}, new String[] {"Alice", "Bob", "Charlie"}, new int[] {30, 25, 35});
  }

  private byte[] createAdditionalData() throws Exception {
    return createArrowData(new int[] {4, 5}, new String[] {"Dave", "Eve"}, new int[] {40, 28});
  }

  private void createNamespace(String... parts) {
    namespace.createNamespace(new CreateNamespaceRequest().id(Arrays.asList(parts)));
  }

  private void createTestTable(String... idParts) throws Exception {
    byte[] data = createTestTableData();
    namespace.createTable(new CreateTableRequest().id(Arrays.asList(idParts)), data);
  }

  private String getTablePath(String... idParts) {
    StringBuilder sb = new StringBuilder(tempDir.toString());
    for (String part : idParts) {
      sb.append("/").append(part);
    }
    sb.append(".lance");
    return sb.toString();
  }

  private int countRowsInIpc(byte[] ipcData) throws Exception {
    int totalRows = 0;
    try (ArrowStreamReader reader =
        new ArrowStreamReader(new ByteArrayInputStream(ipcData), allocator)) {
      while (reader.loadNextBatch()) {
        totalRows += reader.getVectorSchemaRoot().getRowCount();
      }
    }
    return totalRows;
  }

  // ========== Namespace Operations ==========

  @Test
  void testNamespaceId() {
    String namespaceId = namespace.namespaceId();
    assertNotNull(namespaceId);
    assertTrue(namespaceId.contains("BaseLanceNamespace"));
  }

  @Test
  void testInitializeWithoutRoot() {
    BaseLanceNamespace ns = new BaseLanceNamespace();
    assertThrows(InvalidInputException.class, () -> ns.initialize(new HashMap<>(), allocator));
  }

  @Test
  void testInitializeWithEmptyRoot() {
    BaseLanceNamespace ns = new BaseLanceNamespace();
    Map<String, String> config = new HashMap<>();
    config.put("root", "");
    assertThrows(InvalidInputException.class, () -> ns.initialize(config, allocator));
  }

  @Test
  void testInitializeExtractsStorageOptions() {
    BaseLanceNamespace ns = new BaseLanceNamespace();
    Map<String, String> config = new HashMap<>();
    config.put("root", tempDir.toString());
    config.put("storage.region", "us-east-1");
    config.put("storage.endpoint", "http://localhost:9000");
    config.put("other.key", "value");
    ns.initialize(config, allocator);
    // Namespace should be functional (storage options are used internally)
    assertNotNull(ns.namespaceId());
    ns.close();
  }

  @Test
  void testCreateAndListNamespaces() {
    createNamespace("workspace");

    ListNamespacesResponse listResp = namespace.listNamespaces(new ListNamespacesRequest());
    assertNotNull(listResp);
    assertNotNull(listResp.getNamespaces());
    assertEquals(1, listResp.getNamespaces().size());
    assertTrue(listResp.getNamespaces().contains("workspace"));
  }

  @Test
  void testCreateNamespaceAlreadyExists() {
    createNamespace("workspace");

    LanceNamespaceException ex =
        assertThrows(LanceNamespaceException.class, () -> createNamespace("workspace"));
    assertEquals(ErrorCode.NAMESPACE_ALREADY_EXISTS, ex.getErrorCode());
  }

  @Test
  void testDescribeNamespace() {
    createNamespace("workspace");

    DescribeNamespaceResponse descResp =
        namespace.describeNamespace(new DescribeNamespaceRequest().id(Arrays.asList("workspace")));
    assertNotNull(descResp);
    assertNotNull(descResp.getProperties());
  }

  @Test
  void testNamespaceExists() {
    createNamespace("workspace");

    assertDoesNotThrow(
        () ->
            namespace.namespaceExists(new NamespaceExistsRequest().id(Arrays.asList("workspace"))));

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.namespaceExists(
                    new NamespaceExistsRequest().id(Arrays.asList("nonexistent"))));
    assertEquals(ErrorCode.NAMESPACE_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testDropNamespace() {
    createNamespace("workspace");

    DropNamespaceResponse dropResp =
        namespace.dropNamespace(new DropNamespaceRequest().id(Arrays.asList("workspace")));
    assertNotNull(dropResp);

    assertThrows(
        LanceNamespaceException.class,
        () ->
            namespace.namespaceExists(new NamespaceExistsRequest().id(Arrays.asList("workspace"))));
  }

  @Test
  void testDropNamespaceNotEmpty() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.dropNamespace(new DropNamespaceRequest().id(Arrays.asList("workspace"))));
    assertEquals(ErrorCode.NAMESPACE_NOT_EMPTY, ex.getErrorCode());
  }

  @Test
  void testListNamespacesInNestedNamespace() {
    createNamespace("org");
    createNamespace("org", "team1");
    createNamespace("org", "team2");

    ListNamespacesResponse listResp =
        namespace.listNamespaces(new ListNamespacesRequest().id(Arrays.asList("org")));
    assertNotNull(listResp);
    assertEquals(2, listResp.getNamespaces().size());
    assertTrue(listResp.getNamespaces().contains("team1"));
    assertTrue(listResp.getNamespaces().contains("team2"));
  }

  @Test
  void testListNamespacesExcludesLanceTables() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "my_table");
    createNamespace("workspace", "child_ns");

    ListNamespacesResponse listResp =
        namespace.listNamespaces(new ListNamespacesRequest().id(Arrays.asList("workspace")));
    assertEquals(1, listResp.getNamespaces().size());
    assertTrue(listResp.getNamespaces().contains("child_ns"));
  }

  @Test
  void testDropNamespaceNotFound() {
    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.dropNamespace(
                    new DropNamespaceRequest().id(Arrays.asList("nonexistent"))));
    assertEquals(ErrorCode.NAMESPACE_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testNamespaceNotFound() {
    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.describeNamespace(
                    new DescribeNamespaceRequest().id(Arrays.asList("nonexistent"))));
    assertEquals(ErrorCode.NAMESPACE_NOT_FOUND, ex.getErrorCode());
  }

  // ========== Table Operations ==========

  @Test
  void testCreateTable() throws Exception {
    createNamespace("workspace");

    byte[] tableData = createTestTableData();
    CreateTableResponse createResp =
        namespace.createTable(
            new CreateTableRequest().id(Arrays.asList("workspace", "test_table")), tableData);

    assertNotNull(createResp);
    assertNotNull(createResp.getLocation());
    assertTrue(createResp.getLocation().contains("test_table"));
    assertEquals(Long.valueOf(1), createResp.getVersion());
  }

  @Test
  void testCreateTableAlreadyExists() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    byte[] tableData = createTestTableData();
    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.createTable(
                    new CreateTableRequest().id(Arrays.asList("workspace", "test_table")),
                    tableData));
    assertEquals(ErrorCode.TABLE_ALREADY_EXISTS, ex.getErrorCode());
  }

  @Test
  void testListTables() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "table1");
    createTestTable("workspace", "table2");

    ListTablesResponse listResp =
        namespace.listTables(new ListTablesRequest().id(Arrays.asList("workspace")));

    assertNotNull(listResp);
    assertNotNull(listResp.getTables());
    assertEquals(2, listResp.getTables().size());
    assertTrue(listResp.getTables().contains("table1"));
    assertTrue(listResp.getTables().contains("table2"));
  }

  @Test
  void testDescribeTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    DescribeTableResponse descResp =
        namespace.describeTable(
            new DescribeTableRequest().id(Arrays.asList("workspace", "test_table")));

    assertNotNull(descResp);
    assertNotNull(descResp.getLocation());
    assertTrue(descResp.getLocation().contains("test_table"));
    assertEquals(Long.valueOf(1), descResp.getVersion());
    assertNotNull(descResp.getSchema());
    assertEquals(3, descResp.getSchema().getFields().size());
    assertEquals(
        "id", descResp.getSchema().getFields().get(0).getName(), "First field should be 'id'");
  }

  @Test
  void testTableExists() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    assertDoesNotThrow(
        () ->
            namespace.tableExists(
                new TableExistsRequest().id(Arrays.asList("workspace", "test_table"))));

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.tableExists(
                    new TableExistsRequest().id(Arrays.asList("workspace", "nonexistent"))));
    assertEquals(ErrorCode.TABLE_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testDropTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    DropTableResponse dropResp =
        namespace.dropTable(new DropTableRequest().id(Arrays.asList("workspace", "test_table")));
    assertNotNull(dropResp);

    assertThrows(
        LanceNamespaceException.class,
        () ->
            namespace.tableExists(
                new TableExistsRequest().id(Arrays.asList("workspace", "test_table"))));
  }

  @Test
  void testTableNotFound() {
    createNamespace("workspace");

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.describeTable(
                    new DescribeTableRequest().id(Arrays.asList("workspace", "nonexistent"))));
    assertEquals(ErrorCode.TABLE_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testCreateTableInNonexistentNamespace() throws Exception {
    byte[] data = createTestTableData();
    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.createTable(
                    new CreateTableRequest().id(Arrays.asList("nonexistent", "table")), data));
    assertEquals(ErrorCode.NAMESPACE_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testDeclareTableAlreadyExists() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.declareTable(
                    new DeclareTableRequest().id(Arrays.asList("workspace", "test_table"))));
    assertEquals(ErrorCode.TABLE_ALREADY_EXISTS, ex.getErrorCode());
  }

  @Test
  void testCountTableRows() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    long count =
        namespace.countTableRows(
            new CountTableRowsRequest().id(Arrays.asList("workspace", "test_table")));
    assertEquals(3, count);
  }

  @Test
  void testCountTableRowsWithPredicate() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    long count =
        namespace.countTableRows(
            new CountTableRowsRequest()
                .id(Arrays.asList("workspace", "test_table"))
                .predicate("age > 28"));
    assertEquals(2, count); // Alice (30) and Charlie (35)
  }

  @Test
  void testDeclareTable() {
    createNamespace("workspace");

    DeclareTableResponse declResp =
        namespace.declareTable(
            new DeclareTableRequest().id(Arrays.asList("workspace", "declared_table")));
    assertNotNull(declResp);
    assertNotNull(declResp.getLocation());
    assertTrue(declResp.getLocation().contains("declared_table"));

    // Verify declared table is visible in table listing
    ListTablesResponse listResp =
        namespace.listTables(new ListTablesRequest().id(Arrays.asList("workspace")));
    assertTrue(listResp.getTables().contains("declared_table"));
  }

  // ========== Data Operations ==========

  @Test
  void testInsertIntoTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    byte[] additionalData = createAdditionalData();
    InsertIntoTableResponse insertResp =
        namespace.insertIntoTable(
            new InsertIntoTableRequest().id(Arrays.asList("workspace", "test_table")),
            additionalData);
    assertNotNull(insertResp);

    long count =
        namespace.countTableRows(
            new CountTableRowsRequest().id(Arrays.asList("workspace", "test_table")));
    assertEquals(5, count);
  }

  @Test
  void testQueryTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    byte[] result =
        namespace.queryTable(new QueryTableRequest().id(Arrays.asList("workspace", "test_table")));
    assertNotNull(result);
    assertTrue(result.length > 0);

    int rowCount = countRowsInIpc(result);
    assertEquals(3, rowCount);
  }

  @Test
  void testQueryTableWithFilter() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    QueryTableRequest queryReq =
        new QueryTableRequest().id(Arrays.asList("workspace", "test_table")).filter("age > 28");
    byte[] result = namespace.queryTable(queryReq);
    assertNotNull(result);

    int rowCount = countRowsInIpc(result);
    assertEquals(2, rowCount); // Alice (30) and Charlie (35)
  }

  @Test
  void testQueryTableWithProjection() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    QueryTableRequestColumns columns = new QueryTableRequestColumns();
    columns.setColumnNames(Arrays.asList("id", "name"));
    QueryTableRequest queryReq =
        new QueryTableRequest().id(Arrays.asList("workspace", "test_table")).columns(columns);
    byte[] result = namespace.queryTable(queryReq);
    assertNotNull(result);

    // Verify schema only has 2 columns
    try (ArrowStreamReader reader =
        new ArrowStreamReader(new ByteArrayInputStream(result), allocator)) {
      Schema schema = reader.getVectorSchemaRoot().getSchema();
      assertEquals(2, schema.getFields().size());
      assertEquals("id", schema.getFields().get(0).getName());
      assertEquals("name", schema.getFields().get(1).getName());
    }
  }

  @Test
  void testDeleteFromTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    namespace.deleteFromTable(
        new DeleteFromTableRequest()
            .id(Arrays.asList("workspace", "test_table"))
            .predicate("id = 2"));

    long count =
        namespace.countTableRows(
            new CountTableRowsRequest().id(Arrays.asList("workspace", "test_table")));
    assertEquals(2, count);
  }

  @Test
  void testDeleteFromTableWithoutPredicate() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    assertThrows(
        InvalidInputException.class,
        () ->
            namespace.deleteFromTable(
                new DeleteFromTableRequest().id(Arrays.asList("workspace", "test_table"))));
  }

  @Test
  void testDeleteFromTableWithEmptyPredicate() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    assertThrows(
        InvalidInputException.class,
        () ->
            namespace.deleteFromTable(
                new DeleteFromTableRequest()
                    .id(Arrays.asList("workspace", "test_table"))
                    .predicate("")));
  }

  @Test
  void testQueryTableWithVersion() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Insert more data (creates version 2)
    byte[] additionalData = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), additionalData);

    // Query at version 1 should return only 3 rows
    byte[] result = namespace.queryTable(new QueryTableRequest().id(tableId).version(1L));
    assertEquals(3, countRowsInIpc(result));

    // Query at latest (no version) should return 5 rows
    result = namespace.queryTable(new QueryTableRequest().id(tableId));
    assertEquals(5, countRowsInIpc(result));
  }

  @Test
  void testMergeInsertIntoTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    // Create merge data: update existing id=1, insert new id=4
    byte[] mergeData =
        createArrowData(
            new int[] {1, 4}, new String[] {"Alice Updated", "Dave"}, new int[] {31, 40});

    MergeInsertIntoTableRequest mergeReq = new MergeInsertIntoTableRequest();
    mergeReq.setId(Arrays.asList("workspace", "test_table"));
    mergeReq.setOn("id");
    mergeReq.setWhenMatchedUpdateAll(true);
    mergeReq.setWhenNotMatchedInsertAll(true);

    MergeInsertIntoTableResponse mergeResp = namespace.mergeInsertIntoTable(mergeReq, mergeData);
    assertNotNull(mergeResp);

    long count =
        namespace.countTableRows(
            new CountTableRowsRequest().id(Arrays.asList("workspace", "test_table")));
    assertEquals(
        4, count); // 3 original - 1 updated + 1 new = 4 (Alice updated, Bob, Charlie, Dave)
  }

  @Test
  void testMergeInsertWithDeleteNotMatchedBySource() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Merge: keep only id=1, delete anything not in source
    byte[] mergeData = createArrowData(new int[] {1}, new String[] {"Alice"}, new int[] {30});

    MergeInsertIntoTableRequest mergeReq = new MergeInsertIntoTableRequest();
    mergeReq.setId(tableId);
    mergeReq.setOn("id");
    mergeReq.setWhenMatchedUpdateAll(true);
    mergeReq.setWhenNotMatchedBySourceDelete(true);

    MergeInsertIntoTableResponse mergeResp = namespace.mergeInsertIntoTable(mergeReq, mergeData);
    assertNotNull(mergeResp);

    long count = namespace.countTableRows(new CountTableRowsRequest().id(tableId));
    assertEquals(1, count); // Only Alice remains
  }

  @Test
  void testMergeInsertWithoutOnColumn() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    byte[] mergeData = createArrowData(new int[] {1}, new String[] {"Alice"}, new int[] {30});

    MergeInsertIntoTableRequest mergeReq = new MergeInsertIntoTableRequest();
    mergeReq.setId(Arrays.asList("workspace", "test_table"));

    assertThrows(
        InvalidInputException.class, () -> namespace.mergeInsertIntoTable(mergeReq, mergeData));
  }

  // ========== Index Operations ==========

  @Test
  void testCreateAndListScalarIndex() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Create scalar index on 'id' column
    CreateTableIndexRequest indexReq = new CreateTableIndexRequest();
    indexReq.setId(tableId);
    indexReq.setColumn("id");
    indexReq.setIndexType("btree");

    CreateTableScalarIndexResponse indexResp = namespace.createTableScalarIndex(indexReq);
    assertNotNull(indexResp);

    // List indices
    ListTableIndicesResponse listResp =
        namespace.listTableIndices(new ListTableIndicesRequest().id(tableId));
    assertNotNull(listResp);
    assertNotNull(listResp.getIndexes());
    assertEquals(1, listResp.getIndexes().size());
    assertEquals(
        Arrays.asList("id"),
        listResp.getIndexes().get(0).getColumns(),
        "Index should reference the 'id' column by name");
  }

  @Test
  void testDropTableIndex() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Create index
    CreateTableIndexRequest indexReq = new CreateTableIndexRequest();
    indexReq.setId(tableId);
    indexReq.setColumn("id");
    indexReq.setIndexType("btree");
    namespace.createTableScalarIndex(indexReq);

    // List and find index name
    ListTableIndicesResponse listResp =
        namespace.listTableIndices(new ListTableIndicesRequest().id(tableId));
    String indexName = listResp.getIndexes().get(0).getIndexName();

    // Drop it
    DropTableIndexResponse dropResp =
        namespace.dropTableIndex(new DropTableIndexRequest().id(tableId), indexName);
    assertNotNull(dropResp);

    // Verify index is gone
    ListTableIndicesResponse afterDrop =
        namespace.listTableIndices(new ListTableIndicesRequest().id(tableId));
    assertEquals(0, afterDrop.getIndexes().size());
  }

  @Test
  void testDescribeTableIndexStats() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Create index
    CreateTableIndexRequest indexReq = new CreateTableIndexRequest();
    indexReq.setId(tableId);
    indexReq.setColumn("id");
    indexReq.setIndexType("btree");
    namespace.createTableScalarIndex(indexReq);

    // Get index name
    ListTableIndicesResponse listResp =
        namespace.listTableIndices(new ListTableIndicesRequest().id(tableId));
    String indexName = listResp.getIndexes().get(0).getIndexName();

    // Describe stats
    DescribeTableIndexStatsResponse statsResp =
        namespace.describeTableIndexStats(
            new DescribeTableIndexStatsRequest().id(tableId), indexName);
    assertNotNull(statsResp);
    assertNotNull(statsResp.getIndexType());
    assertNotNull(statsResp.getNumIndexedRows());
    assertNotNull(statsResp.getNumIndices());
  }

  @Test
  void testDescribeTableIndexStatsNotFound() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.describeTableIndexStats(
                    new DescribeTableIndexStatsRequest().id(tableId), "nonexistent_index"));
    assertEquals(ErrorCode.TABLE_INDEX_NOT_FOUND, ex.getErrorCode());
  }

  // ========== Schema Operations ==========

  @Test
  void testAlterTableAddColumns() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    NewColumnTransform newCol = new NewColumnTransform();
    newCol.setName("score");
    newCol.setExpression("0");

    AlterTableAddColumnsRequest addReq = new AlterTableAddColumnsRequest();
    addReq.setId(tableId);
    addReq.setNewColumns(Arrays.asList(newCol));

    AlterTableAddColumnsResponse addResp = namespace.alterTableAddColumns(addReq);
    assertNotNull(addResp);

    // Verify the column exists via describe
    DescribeTableResponse descResp =
        namespace.describeTable(new DescribeTableRequest().id(tableId));
    assertNotNull(descResp.getSchema());
    boolean hasScoreField =
        descResp.getSchema().getFields().stream().anyMatch(f -> "score".equals(f.getName()));
    assertTrue(hasScoreField, "Schema should contain 'score' column after add");
    assertEquals(
        4, descResp.getSchema().getFields().size(), "Schema should have 4 fields after add");
  }

  @Test
  void testAlterTableDropColumns() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    AlterTableDropColumnsRequest dropReq = new AlterTableDropColumnsRequest();
    dropReq.setId(tableId);
    dropReq.setColumns(Arrays.asList("age"));

    AlterTableDropColumnsResponse dropResp = namespace.alterTableDropColumns(dropReq);
    assertNotNull(dropResp);

    // Verify column removed
    DescribeTableResponse descResp =
        namespace.describeTable(new DescribeTableRequest().id(tableId));
    boolean hasAgeField =
        descResp.getSchema().getFields().stream().anyMatch(f -> "age".equals(f.getName()));
    assertFalse(hasAgeField, "Schema should not contain 'age' column after drop");
  }

  @Test
  void testAlterTableAlterColumns() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    AlterColumnsEntry entry = new AlterColumnsEntry();
    entry.setPath("name");
    entry.setRename("full_name");

    AlterTableAlterColumnsRequest alterReq = new AlterTableAlterColumnsRequest();
    alterReq.setId(tableId);
    alterReq.setAlterations(Arrays.asList(entry));

    AlterTableAlterColumnsResponse alterResp = namespace.alterTableAlterColumns(alterReq);
    assertNotNull(alterResp);

    // Verify rename
    DescribeTableResponse descResp =
        namespace.describeTable(new DescribeTableRequest().id(tableId));
    boolean hasFullName =
        descResp.getSchema().getFields().stream().anyMatch(f -> "full_name".equals(f.getName()));
    assertTrue(hasFullName, "Schema should contain 'full_name' after rename");
    boolean hasName =
        descResp.getSchema().getFields().stream().anyMatch(f -> "name".equals(f.getName()));
    assertFalse(hasName, "Schema should not contain 'name' after rename");
  }

  @Test
  void testAlterTableAddColumnsEmpty() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    AlterTableAddColumnsRequest addReq = new AlterTableAddColumnsRequest();
    addReq.setId(Arrays.asList("workspace", "test_table"));
    addReq.setNewColumns(Collections.emptyList());

    assertThrows(InvalidInputException.class, () -> namespace.alterTableAddColumns(addReq));
  }

  @Test
  void testAlterTableDropColumnsEmpty() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    AlterTableDropColumnsRequest dropReq = new AlterTableDropColumnsRequest();
    dropReq.setId(Arrays.asList("workspace", "test_table"));
    dropReq.setColumns(Collections.emptyList());

    assertThrows(InvalidInputException.class, () -> namespace.alterTableDropColumns(dropReq));
  }

  @Test
  void testAlterTableAlterColumnsEmpty() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    AlterTableAlterColumnsRequest alterReq = new AlterTableAlterColumnsRequest();
    alterReq.setId(Arrays.asList("workspace", "test_table"));
    alterReq.setAlterations(Collections.emptyList());

    assertThrows(InvalidInputException.class, () -> namespace.alterTableAlterColumns(alterReq));
  }

  @Test
  void testGetTableStats() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    GetTableStatsResponse statsResp =
        namespace.getTableStats(
            new GetTableStatsRequest().id(Arrays.asList("workspace", "test_table")));
    assertNotNull(statsResp);
    assertEquals(Long.valueOf(3), statsResp.getNumRows());
  }

  // ========== Version Operations ==========

  @Test
  void testListTableVersions() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Insert more data to create version 2
    byte[] additionalData = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), additionalData);

    ListTableVersionsResponse versionsResp =
        namespace.listTableVersions(new ListTableVersionsRequest().id(tableId));
    assertNotNull(versionsResp);
    assertNotNull(versionsResp.getVersions());
    assertEquals(2, versionsResp.getVersions().size());
  }

  @Test
  void testRestoreTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Insert more data (version 2)
    byte[] additionalData = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), additionalData);

    // Verify 5 rows at version 2
    long count = namespace.countTableRows(new CountTableRowsRequest().id(tableId));
    assertEquals(5, count);

    // Restore to version 1
    RestoreTableResponse restoreResp =
        namespace.restoreTable(new RestoreTableRequest().id(tableId).version(1L));
    assertNotNull(restoreResp);

    // Verify 3 rows after restore
    count = namespace.countTableRows(new CountTableRowsRequest().id(tableId));
    assertEquals(3, count);
  }

  @Test
  void testDescribeTableVersion() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    DescribeTableVersionResponse resp =
        namespace.describeTableVersion(new DescribeTableVersionRequest().id(tableId).version(1L));
    assertNotNull(resp);
    assertNotNull(resp.getVersion());
    assertEquals(Long.valueOf(1), resp.getVersion().getVersion());
  }

  @Test
  void testDescribeTableVersionNotFound() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () ->
                namespace.describeTableVersion(
                    new DescribeTableVersionRequest().id(tableId).version(999L)));
    assertEquals(ErrorCode.TABLE_VERSION_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testRestoreTableWithoutVersion() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    assertThrows(
        InvalidInputException.class,
        () ->
            namespace.restoreTable(
                new RestoreTableRequest().id(Arrays.asList("workspace", "test_table"))));
  }

  @Test
  void testRenameTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    RenameTableRequest renameReq = new RenameTableRequest();
    renameReq.setId(Arrays.asList("workspace", "test_table"));
    renameReq.setNewTableName("renamed_table");

    RenameTableResponse renameResp = namespace.renameTable(renameReq);
    assertNotNull(renameResp);

    // Old table should not exist
    assertThrows(
        LanceNamespaceException.class,
        () ->
            namespace.tableExists(
                new TableExistsRequest().id(Arrays.asList("workspace", "test_table"))));

    // New table should exist
    assertDoesNotThrow(
        () ->
            namespace.tableExists(
                new TableExistsRequest().id(Arrays.asList("workspace", "renamed_table"))));
  }

  @Test
  void testRenameTableToExistingName() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "table_a");
    createTestTable("workspace", "table_b");

    RenameTableRequest renameReq = new RenameTableRequest();
    renameReq.setId(Arrays.asList("workspace", "table_a"));
    renameReq.setNewTableName("table_b");

    LanceNamespaceException ex =
        assertThrows(LanceNamespaceException.class, () -> namespace.renameTable(renameReq));
    assertEquals(ErrorCode.TABLE_ALREADY_EXISTS, ex.getErrorCode());
  }

  @Test
  void testRenameTableWithoutNewName() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    RenameTableRequest renameReq = new RenameTableRequest();
    renameReq.setId(Arrays.asList("workspace", "test_table"));

    assertThrows(InvalidInputException.class, () -> namespace.renameTable(renameReq));
  }

  @Test
  void testListAllTables() throws Exception {
    createNamespace("ns1");
    createNamespace("ns2");
    createTestTable("ns1", "table_a");
    createTestTable("ns2", "table_b");

    ListTablesResponse allTablesResp = namespace.listAllTables(new ListTablesRequest());
    assertNotNull(allTablesResp);
    assertNotNull(allTablesResp.getTables());
    assertEquals(2, allTablesResp.getTables().size());
    // Verify qualified path format: "namespace/table"
    assertTrue(allTablesResp.getTables().contains("ns1/table_a"));
    assertTrue(allTablesResp.getTables().contains("ns2/table_b"));
  }

  @Test
  void testListAllTablesWithNestedNamespaces() throws Exception {
    createNamespace("org");
    createNamespace("org", "team");
    createTestTable("org", "team", "deep_table");
    createTestTable("root_table");

    ListTablesResponse allTablesResp = namespace.listAllTables(new ListTablesRequest());
    assertNotNull(allTablesResp);
    assertEquals(2, allTablesResp.getTables().size());
    assertTrue(allTablesResp.getTables().contains("org/team/deep_table"));
    assertTrue(allTablesResp.getTables().contains("root_table"));
  }

  // ========== Tag Operations ==========

  @Test
  void testCreateAndListTags() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Create a tag
    CreateTableTagRequest tagReq = new CreateTableTagRequest();
    tagReq.setId(tableId);
    tagReq.setTag("v1.0");
    tagReq.setVersion(1L);

    CreateTableTagResponse tagResp = namespace.createTableTag(tagReq);
    assertNotNull(tagResp);

    // List tags
    ListTableTagsResponse listResp =
        namespace.listTableTags(new ListTableTagsRequest().id(tableId));
    assertNotNull(listResp);
    assertNotNull(listResp.getTags());
    assertTrue(listResp.getTags().containsKey("v1.0"));
    assertEquals(Long.valueOf(1), listResp.getTags().get("v1.0").getVersion());
  }

  @Test
  void testGetTableTagVersion() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Create a tag
    CreateTableTagRequest tagReq = new CreateTableTagRequest();
    tagReq.setId(tableId);
    tagReq.setTag("v1.0");
    tagReq.setVersion(1L);
    namespace.createTableTag(tagReq);

    // Get tag version
    GetTableTagVersionRequest getReq = new GetTableTagVersionRequest();
    getReq.setId(tableId);
    getReq.setTag("v1.0");

    GetTableTagVersionResponse getResp = namespace.getTableTagVersion(getReq);
    assertNotNull(getResp);
    assertEquals(Long.valueOf(1), getResp.getVersion());
  }

  @Test
  void testDeleteTableTag() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Create and delete tag
    CreateTableTagRequest tagReq = new CreateTableTagRequest();
    tagReq.setId(tableId);
    tagReq.setTag("v1.0");
    tagReq.setVersion(1L);
    namespace.createTableTag(tagReq);

    DeleteTableTagRequest deleteReq = new DeleteTableTagRequest();
    deleteReq.setId(tableId);
    deleteReq.setTag("v1.0");

    DeleteTableTagResponse deleteResp = namespace.deleteTableTag(deleteReq);
    assertNotNull(deleteResp);

    // Verify tag is gone
    LanceNamespaceException ex =
        assertThrows(
            LanceNamespaceException.class,
            () -> {
              GetTableTagVersionRequest getReq = new GetTableTagVersionRequest();
              getReq.setId(tableId);
              getReq.setTag("v1.0");
              namespace.getTableTagVersion(getReq);
            });
    assertEquals(ErrorCode.TABLE_TAG_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testCreateTableTagMissingFields() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    CreateTableTagRequest tagReq = new CreateTableTagRequest();
    tagReq.setId(Arrays.asList("workspace", "test_table"));
    // Missing tag name and version
    assertThrows(InvalidInputException.class, () -> namespace.createTableTag(tagReq));
  }

  @Test
  void testGetTableTagVersionNotFound() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    GetTableTagVersionRequest getReq = new GetTableTagVersionRequest();
    getReq.setId(Arrays.asList("workspace", "test_table"));
    getReq.setTag("nonexistent");

    LanceNamespaceException ex =
        assertThrows(LanceNamespaceException.class, () -> namespace.getTableTagVersion(getReq));
    assertEquals(ErrorCode.TABLE_TAG_NOT_FOUND, ex.getErrorCode());
  }

  @Test
  void testUpdateTableTag() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Insert data to create version 2
    byte[] additionalData = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), additionalData);

    // Create tag at version 1
    CreateTableTagRequest tagReq = new CreateTableTagRequest();
    tagReq.setId(tableId);
    tagReq.setTag("latest");
    tagReq.setVersion(1L);
    namespace.createTableTag(tagReq);

    // Update tag to version 2
    UpdateTableTagRequest updateReq = new UpdateTableTagRequest();
    updateReq.setId(tableId);
    updateReq.setTag("latest");
    updateReq.setVersion(2L);

    UpdateTableTagResponse updateResp = namespace.updateTableTag(updateReq);
    assertNotNull(updateResp);

    // Verify tag now points to version 2
    GetTableTagVersionRequest getReq = new GetTableTagVersionRequest();
    getReq.setId(tableId);
    getReq.setTag("latest");
    GetTableTagVersionResponse getResp = namespace.getTableTagVersion(getReq);
    assertEquals(Long.valueOf(2), getResp.getVersion());
  }

  // ========== Nested Namespace Tests ==========

  @Test
  void testNestedNamespaces() throws Exception {
    createNamespace("org");
    createNamespace("org", "team");
    createTestTable("org", "team", "dataset");

    // Verify table accessible
    assertDoesNotThrow(
        () ->
            namespace.tableExists(
                new TableExistsRequest().id(Arrays.asList("org", "team", "dataset"))));

    long count =
        namespace.countTableRows(
            new CountTableRowsRequest().id(Arrays.asList("org", "team", "dataset")));
    assertEquals(3, count);
  }

  // ========== Table at Root Level ==========

  @Test
  void testTableAtRootLevel() throws Exception {
    byte[] data = createTestTableData();
    CreateTableResponse createResp =
        namespace.createTable(new CreateTableRequest().id(Arrays.asList("root_table")), data);
    assertNotNull(createResp);

    long count =
        namespace.countTableRows(new CountTableRowsRequest().id(Arrays.asList("root_table")));
    assertEquals(3, count);
  }

  // ========== Deregister Table ==========

  @Test
  void testDeregisterTable() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    DeregisterTableResponse deregResp =
        namespace.deregisterTable(
            new DeregisterTableRequest().id(Arrays.asList("workspace", "test_table")));
    assertNotNull(deregResp);

    // Table should be gone
    assertThrows(
        LanceNamespaceException.class,
        () ->
            namespace.tableExists(
                new TableExistsRequest().id(Arrays.asList("workspace", "test_table"))));
  }

  // ========== Register Table Tests ==========

  @Test
  void testRegisterTable() throws Exception {
    // Create a table at one location
    createTestTable("source_table");
    String sourceLocation = getTablePath("source_table");

    // Register it under a namespace
    createNamespace("workspace");
    RegisterTableRequest regReq = new RegisterTableRequest();
    regReq.setId(Arrays.asList("workspace", "registered_table"));
    regReq.setLocation(sourceLocation);

    RegisterTableResponse regResp = namespace.registerTable(regReq);
    assertNotNull(regResp);
    assertNotNull(regResp.getLocation());
  }

  @Test
  void testRegisterTableWithoutLocation() {
    createNamespace("workspace");

    RegisterTableRequest regReq = new RegisterTableRequest();
    regReq.setId(Arrays.asList("workspace", "table"));

    assertThrows(InvalidInputException.class, () -> namespace.registerTable(regReq));
  }

  @Test
  void testRegisterTableAlreadyExists() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "existing_table");
    createTestTable("source_table");
    String sourceLocation = getTablePath("source_table");

    RegisterTableRequest regReq = new RegisterTableRequest();
    regReq.setId(Arrays.asList("workspace", "existing_table"));
    regReq.setLocation(sourceLocation);

    LanceNamespaceException ex =
        assertThrows(LanceNamespaceException.class, () -> namespace.registerTable(regReq));
    assertEquals(ErrorCode.TABLE_ALREADY_EXISTS, ex.getErrorCode());
  }

  // ========== Insert and Query Roundtrip ==========

  @Test
  void testInsertQueryDeleteRoundtrip() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");

    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Verify initial data
    byte[] result = namespace.queryTable(new QueryTableRequest().id(tableId));
    assertEquals(3, countRowsInIpc(result));

    // Insert more data
    byte[] additionalData = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), additionalData);

    result = namespace.queryTable(new QueryTableRequest().id(tableId));
    assertEquals(5, countRowsInIpc(result));

    // Delete some rows
    namespace.deleteFromTable(new DeleteFromTableRequest().id(tableId).predicate("id >= 4"));

    result = namespace.queryTable(new QueryTableRequest().id(tableId));
    assertEquals(3, countRowsInIpc(result));
  }

  // ========== Unimplemented Methods ==========

  @Test
  void testUpdateTableNotSupported() {
    assertThrows(
        UnsupportedOperationException.class, () -> namespace.updateTable(new UpdateTableRequest()));
  }

  @Test
  void testCreateTableVersionNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.createTableVersion(new CreateTableVersionRequest()));
  }

  @Test
  void testBatchDeleteTableVersionsNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.batchDeleteTableVersions(new BatchDeleteTableVersionsRequest()));
  }

  @Test
  void testBatchCreateTableVersionsNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.batchCreateTableVersions(new BatchCreateTableVersionsRequest()));
  }

  @Test
  void testBatchCommitTablesNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.batchCommitTables(new BatchCommitTablesRequest()));
  }

  @Test
  void testUpdateTableSchemaMetadataNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.updateTableSchemaMetadata(new UpdateTableSchemaMetadataRequest()));
  }

  @Test
  void testExplainTableQueryPlanNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.explainTableQueryPlan(new ExplainTableQueryPlanRequest()));
  }

  @Test
  void testAnalyzeTableQueryPlanNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.analyzeTableQueryPlan(new AnalyzeTableQueryPlanRequest()));
  }

  @Test
  void testDescribeTransactionNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.describeTransaction(new DescribeTransactionRequest()));
  }

  @Test
  void testAlterTransactionNotSupported() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> namespace.alterTransaction(new AlterTransactionRequest()));
  }

  // ========== Pagination Tests ==========

  @Test
  void testListNamespacesWithLimit() {
    createNamespace("alpha");
    createNamespace("beta");
    createNamespace("gamma");

    ListNamespacesRequest request = new ListNamespacesRequest();
    request.setLimit(2);

    ListNamespacesResponse response = namespace.listNamespaces(request);
    assertEquals(2, response.getNamespaces().size());
    assertNotNull(response.getPageToken());
    assertTrue(response.getNamespaces().contains("alpha"));
    assertTrue(response.getNamespaces().contains("beta"));
  }

  @Test
  void testListNamespacesWithPageToken() {
    createNamespace("alpha");
    createNamespace("beta");
    createNamespace("gamma");

    ListNamespacesRequest request = new ListNamespacesRequest();
    request.setLimit(2);
    ListNamespacesResponse firstPage = namespace.listNamespaces(request);

    // Use pageToken to get next page
    ListNamespacesRequest nextRequest = new ListNamespacesRequest();
    nextRequest.setPageToken(firstPage.getPageToken());
    ListNamespacesResponse secondPage = namespace.listNamespaces(nextRequest);
    assertEquals(1, secondPage.getNamespaces().size());
    assertTrue(secondPage.getNamespaces().contains("gamma"));
  }

  @Test
  void testListTablesWithLimit() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "alpha_table");
    createTestTable("workspace", "beta_table");
    createTestTable("workspace", "gamma_table");

    ListTablesRequest request = new ListTablesRequest();
    request.setId(Collections.singletonList("workspace"));
    request.setLimit(2);

    ListTablesResponse response = namespace.listTables(request);
    assertEquals(2, response.getTables().size());
    assertNotNull(response.getPageToken());
  }

  @Test
  void testListTablesWithPageToken() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "alpha_table");
    createTestTable("workspace", "beta_table");
    createTestTable("workspace", "gamma_table");

    ListTablesRequest request = new ListTablesRequest();
    request.setId(Collections.singletonList("workspace"));
    request.setLimit(2);
    ListTablesResponse firstPage = namespace.listTables(request);
    assertNotNull(firstPage.getPageToken());

    ListTablesRequest nextRequest = new ListTablesRequest();
    nextRequest.setId(Collections.singletonList("workspace"));
    nextRequest.setPageToken(firstPage.getPageToken());
    ListTablesResponse secondPage = namespace.listTables(nextRequest);
    assertEquals(1, secondPage.getTables().size());
    assertTrue(secondPage.getTables().contains("gamma_table"));
  }

  @Test
  void testListTableVersionsWithLimit() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");
    List<String> tableId = Arrays.asList("workspace", "test_table");

    // Insert to create additional versions
    byte[] data = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), data);
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), data);

    ListTableVersionsRequest request = new ListTableVersionsRequest();
    request.setId(tableId);
    // Should have at least 3 versions (create + 2 inserts); limit to 2
    request.setLimit(2);

    ListTableVersionsResponse response = namespace.listTableVersions(request);
    assertEquals(2, response.getVersions().size());
  }

  @Test
  void testListTableTagsWithLimit() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");
    List<String> tableId = Arrays.asList("workspace", "test_table");

    namespace.createTableTag(new CreateTableTagRequest().id(tableId).tag("v1").version(1L));

    // Insert to create version 2
    byte[] data = createAdditionalData();
    namespace.insertIntoTable(new InsertIntoTableRequest().id(tableId), data);
    namespace.createTableTag(new CreateTableTagRequest().id(tableId).tag("v2").version(2L));

    ListTableTagsRequest request = new ListTableTagsRequest();
    request.setId(tableId);
    request.setLimit(1);

    ListTableTagsResponse response = namespace.listTableTags(request);
    assertEquals(1, response.getTags().size());
  }

  @Test
  void testListAllTablesWithLimit() throws Exception {
    createNamespace("ns_a");
    createNamespace("ns_b");
    createTestTable("ns_a", "table1");
    createTestTable("ns_a", "table2");
    createTestTable("ns_b", "table3");

    ListTablesRequest request = new ListTablesRequest();
    request.setLimit(2);

    ListTablesResponse response = namespace.listAllTables(request);
    assertEquals(2, response.getTables().size());
    assertNotNull(response.getPageToken());
  }

  @Test
  void testListAllTablesWithPageToken() throws Exception {
    createNamespace("ns_a");
    createNamespace("ns_b");
    createTestTable("ns_a", "table1");
    createTestTable("ns_a", "table2");
    createTestTable("ns_b", "table3");

    ListTablesRequest request = new ListTablesRequest();
    request.setLimit(2);
    ListTablesResponse firstPage = namespace.listAllTables(request);
    assertNotNull(firstPage.getPageToken());

    ListTablesRequest nextRequest = new ListTablesRequest();
    nextRequest.setPageToken(firstPage.getPageToken());
    ListTablesResponse secondPage = namespace.listAllTables(nextRequest);
    assertEquals(1, secondPage.getTables().size());
  }

  @Test
  void testPaginationWithNoLimit() throws Exception {
    createNamespace("alpha");
    createNamespace("beta");
    createNamespace("gamma");

    // No limit -- should return all results, no page token
    ListNamespacesResponse response = namespace.listNamespaces(new ListNamespacesRequest());
    assertEquals(3, response.getNamespaces().size());
    assertNull(response.getPageToken());
  }

  // ========== Query Parameter Tests ==========

  @Test
  void testQueryTableWithDistanceType() throws Exception {
    // distanceType is only meaningful with vector search (k > 0), but we verify
    // it doesn't throw when passed without a vector query (it's inside the k > 0 block)
    createNamespace("workspace");
    createTestTable("workspace", "test_table");
    List<String> tableId = Arrays.asList("workspace", "test_table");

    QueryTableRequest request = new QueryTableRequest();
    request.setId(tableId);
    request.setDistanceType("cosine");
    // No k set -- distanceType is ignored gracefully
    byte[] result = namespace.queryTable(request);
    assertEquals(3, countRowsInIpc(result));
  }

  @Test
  void testParseDistanceTypeInvalid() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");
    List<String> tableId = Arrays.asList("workspace", "test_table");

    QueryTableRequest request = new QueryTableRequest();
    request.setId(tableId);
    request.setK(5);
    request.setDistanceType("invalid_type");

    // Vector with single vector is needed for k > 0
    QueryTableRequestVector vector = new QueryTableRequestVector();
    vector.setSingleVector(Arrays.asList(1.0f, 2.0f, 3.0f));
    request.setVector(vector);

    LanceNamespaceException ex =
        assertThrows(LanceNamespaceException.class, () -> namespace.queryTable(request));
    assertEquals(ErrorCode.INVALID_INPUT, ex.getErrorCode());
  }

  @Test
  void testQueryTableWithBypassVectorIndex() throws Exception {
    createNamespace("workspace");
    createTestTable("workspace", "test_table");
    List<String> tableId = Arrays.asList("workspace", "test_table");

    // bypassVectorIndex without vector search -- should be ignored gracefully
    QueryTableRequest request = new QueryTableRequest();
    request.setId(tableId);
    request.setBypassVectorIndex(true);

    byte[] result = namespace.queryTable(request);
    assertEquals(3, countRowsInIpc(result));
  }
}
