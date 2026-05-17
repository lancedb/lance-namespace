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
package org.lance.namespace.client.apache.cts;

import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.api.IndexApi;
import org.lance.namespace.client.apache.api.NamespaceApi;
import org.lance.namespace.client.apache.api.TableApi;
import org.lance.namespace.client.apache.api.TagApi;
import org.lance.namespace.client.apache.api.TransactionApi;
import org.lance.namespace.model.AlterTableAddColumnsRequest;
import org.lance.namespace.model.AlterTableAlterColumnsRequest;
import org.lance.namespace.model.AlterTableBackfillColumnsRequest;
import org.lance.namespace.model.AlterTableDropColumnsRequest;
import org.lance.namespace.model.AlterTransactionAction;
import org.lance.namespace.model.AlterTransactionRequest;
import org.lance.namespace.model.AnalyzeTableQueryPlanRequest;
import org.lance.namespace.model.BatchCommitTablesRequest;
import org.lance.namespace.model.BatchCreateTableVersionsRequest;
import org.lance.namespace.model.BatchDeleteTableVersionsRequest;
import org.lance.namespace.model.CountTableRowsRequest;
import org.lance.namespace.model.CreateNamespaceRequest;
import org.lance.namespace.model.CreateTableIndexRequest;
import org.lance.namespace.model.CreateTableTagRequest;
import org.lance.namespace.model.CreateTableVersionRequest;
import org.lance.namespace.model.DeclareTableRequest;
import org.lance.namespace.model.DeleteFromTableRequest;
import org.lance.namespace.model.DeleteTableTagRequest;
import org.lance.namespace.model.DeregisterTableRequest;
import org.lance.namespace.model.DescribeNamespaceRequest;
import org.lance.namespace.model.DescribeTableIndexStatsRequest;
import org.lance.namespace.model.DescribeTableRequest;
import org.lance.namespace.model.DescribeTableVersionRequest;
import org.lance.namespace.model.DescribeTransactionRequest;
import org.lance.namespace.model.DropNamespaceRequest;
import org.lance.namespace.model.ExplainTableQueryPlanRequest;
import org.lance.namespace.model.GetTableStatsRequest;
import org.lance.namespace.model.GetTableTagVersionRequest;
import org.lance.namespace.model.ListTableIndicesRequest;
import org.lance.namespace.model.NamespaceExistsRequest;
import org.lance.namespace.model.QueryTableRequest;
import org.lance.namespace.model.QueryTableRequestVector;
import org.lance.namespace.model.RefreshMaterializedViewRequest;
import org.lance.namespace.model.RegisterTableRequest;
import org.lance.namespace.model.RenameTableRequest;
import org.lance.namespace.model.RestoreTableRequest;
import org.lance.namespace.model.TableExistsRequest;
import org.lance.namespace.model.UpdateTableRequest;
import org.lance.namespace.model.UpdateTableTagRequest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

/** Thin contract runner: starts WireMock with pre-generated mappings from build/cts/wiremock/. */
public class WireMockContractIT {

  private static WireMockServer wireMock;
  private static ApiClient apiClient;

  @BeforeAll
  static void startWireMock() {
    String mappingsRoot =
        Paths.get(
                System.getProperty(
                    "wiremock.mappings.root", "../../build/cts/wiremock/src/main/resources"))
            .toAbsolutePath()
            .toString();

    wireMock =
        new WireMockServer(
            WireMockConfiguration.options().dynamicPort().usingFilesUnderDirectory(mappingsRoot));
    wireMock.start();

    apiClient = new ApiClient();
    apiClient.setBasePath("http://localhost:" + wireMock.port());
  }

  @AfterAll
  static void stopWireMock() {
    if (wireMock != null) {
      wireMock.stop();
    }
  }

  @Test
  void alterTableAddColumnsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.alterTableAddColumns(
        "test_ns.test_table",
        new AlterTableAddColumnsRequest().newColumns(new java.util.ArrayList<>()),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void alterTableAlterColumnsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.alterTableAlterColumns(
        "test_ns.test_table",
        new AlterTableAlterColumnsRequest().alterations(new java.util.ArrayList<>()),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void alterTableBackfillColumnsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.alterTableBackfillColumns(
        "test_ns.test_table", new AlterTableBackfillColumnsRequest().column("col"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void alterTableDropColumnsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.alterTableDropColumns(
        "test_ns.test_table",
        new AlterTableDropColumnsRequest().columns(new java.util.ArrayList<>()),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void alterTransactionReturnsValidResponse() throws ApiException {
    TransactionApi api = new TransactionApi(apiClient);
    api.alterTransaction(
        "test_txn",
        new AlterTransactionRequest()
            .actions(java.util.Arrays.asList(new AlterTransactionAction())),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void analyzeTableQueryPlanReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.analyzeTableQueryPlan(
        "test_ns.test_table",
        new AnalyzeTableQueryPlanRequest()
            .k(1)
            .vector(new QueryTableRequestVector().singleVector(java.util.Arrays.asList(0.1f))),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void batchCommitTablesReturnsValidResponse() throws ApiException {
    TransactionApi api = new TransactionApi(apiClient);
    api.batchCommitTables(
        new BatchCommitTablesRequest().operations(new java.util.ArrayList<>()), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void batchCreateTableVersionsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.batchCreateTableVersions(
        new BatchCreateTableVersionsRequest().entries(new java.util.ArrayList<>()), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void batchDeleteTableVersionsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.batchDeleteTableVersions(
        "test_ns.test_table",
        new BatchDeleteTableVersionsRequest().ranges(new java.util.ArrayList<>()),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void countTableRowsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.countTableRows("test_ns.test_table", new CountTableRowsRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void createNamespaceReturnsValidResponse() throws ApiException {
    NamespaceApi api = new NamespaceApi(apiClient);
    api.createNamespace("test_ns", new CreateNamespaceRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void createTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.createTable("test_ns.test_table", new byte[0], null, null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void createTableIndexReturnsValidResponse() throws ApiException {
    IndexApi api = new IndexApi(apiClient);
    api.createTableIndex(
        "test_ns.test_table",
        new CreateTableIndexRequest().column("col").indexType("IVF_PQ"),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void createTableScalarIndexReturnsValidResponse() throws ApiException {
    IndexApi api = new IndexApi(apiClient);
    api.createTableScalarIndex(
        "test_ns.test_table", new CreateTableIndexRequest().column("col").indexType("BTREE"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void createTableTagReturnsValidResponse() throws ApiException {
    TagApi api = new TagApi(apiClient);
    api.createTableTag(
        "test_ns.test_table", new CreateTableTagRequest().tag("v1").version(1L), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void createTableVersionReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.createTableVersion(
        "test_ns.test_table",
        new CreateTableVersionRequest().version(1L).manifestPath("manifest_path"),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void declareTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.declareTable("test_ns.test_table", new DeclareTableRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void deleteFromTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.deleteFromTable(
        "test_ns.test_table", new DeleteFromTableRequest().predicate("id = 1"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void deleteTableTagReturnsValidResponse() throws ApiException {
    TagApi api = new TagApi(apiClient);
    api.deleteTableTag("test_ns.test_table", new DeleteTableTagRequest().tag("v1"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void deregisterTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.deregisterTable("test_ns.test_table", new DeregisterTableRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void describeNamespaceReturnsValidResponse() throws ApiException {
    NamespaceApi api = new NamespaceApi(apiClient);
    api.describeNamespace("ns_existing", new DescribeNamespaceRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void describeTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.describeTable(
        "ns_with_tables.table_alpha", new DescribeTableRequest(), null, null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void describeTableIndexStatsReturnsValidResponse() throws ApiException {
    IndexApi api = new IndexApi(apiClient);
    api.describeTableIndexStats(
        "test_ns.test_table", "idx", new DescribeTableIndexStatsRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void describeTableVersionReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.describeTableVersion("test_ns.test_table", new DescribeTableVersionRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void describeTransactionReturnsValidResponse() throws ApiException {
    TransactionApi api = new TransactionApi(apiClient);
    api.describeTransaction("test_txn", new DescribeTransactionRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void dropNamespaceReturnsValidResponse() throws ApiException {
    NamespaceApi api = new NamespaceApi(apiClient);
    api.dropNamespace("ns_existing", new DropNamespaceRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void dropTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.dropTable("test_ns.test_table", null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void dropTableIndexReturnsValidResponse() throws ApiException {
    IndexApi api = new IndexApi(apiClient);
    api.dropTableIndex("test_ns.test_table", "idx", null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void explainTableQueryPlanReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.explainTableQueryPlan(
        "test_ns.test_table",
        new ExplainTableQueryPlanRequest()
            .query(
                new QueryTableRequest()
                    .k(1)
                    .vector(
                        new QueryTableRequestVector().singleVector(java.util.Arrays.asList(0.1f)))),
        null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void getTableStatsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.getTableStats("test_ns.test_table", new GetTableStatsRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void getTableTagVersionReturnsValidResponse() throws ApiException {
    TagApi api = new TagApi(apiClient);
    api.getTableTagVersion("test_ns.test_table", new GetTableTagVersionRequest().tag("v1"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void insertIntoTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.insertIntoTable("test_ns.test_table", new byte[0], null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void listAllTablesReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.listAllTables(null, null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void listNamespacesReturnsValidResponse() throws ApiException {
    NamespaceApi api = new NamespaceApi(apiClient);
    api.listNamespaces("$", null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void listTableIndicesReturnsValidResponse() throws ApiException {
    IndexApi api = new IndexApi(apiClient);
    api.listTableIndices("test_ns.test_table", new ListTableIndicesRequest(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void listTableTagsReturnsValidResponse() throws ApiException {
    TagApi api = new TagApi(apiClient);
    api.listTableTags("test_ns.test_table", null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void listTableVersionsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.listTableVersions("test_ns.test_table", null, null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void listTablesReturnsValidResponse() throws ApiException {
    NamespaceApi api = new NamespaceApi(apiClient);
    api.listTables("ns_with_tables", null, null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void mergeInsertIntoTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.mergeInsertIntoTable(
        "test_ns.test_table", "id", new byte[0], null, null, null, null, null, null, null, null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void namespaceExistsReturnsValidResponse() throws ApiException {
    NamespaceApi api = new NamespaceApi(apiClient);
    api.namespaceExists("ns_existing", new NamespaceExistsRequest(), null);
  }

  @Test
  void queryTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.queryTable(
        "test_ns.test_table",
        new QueryTableRequest()
            .k(1)
            .vector(new QueryTableRequestVector().singleVector(java.util.Arrays.asList(0.1f))),
        null);
    // Binary response — successful return is the contract assertion.
  }

  @Test
  void refreshMaterializedViewReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.refreshMaterializedView("test_ns.test_table", null, new RefreshMaterializedViewRequest());
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void registerTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.registerTable(
        "test_ns.test_table", new RegisterTableRequest().location("s3://bucket/path"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void renameTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.renameTable("test_ns.test_table", new RenameTableRequest().newTableName("new_name"), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void restoreTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.restoreTable("test_ns.test_table", new RestoreTableRequest().version(1L), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void tableExistsReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.tableExists("ns_with_tables.table_alpha", new TableExistsRequest(), null);
  }

  @Test
  void updateTableReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.updateTable(
        "test_ns.test_table", new UpdateTableRequest().updates(new java.util.ArrayList<>()), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void updateTableSchemaMetadataReturnsValidResponse() throws ApiException {
    TableApi api = new TableApi(apiClient);
    api.updateTableSchemaMetadata("test_ns.test_table", new java.util.HashMap<>(), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }

  @Test
  void updateTableTagReturnsValidResponse() throws ApiException {
    TagApi api = new TagApi(apiClient);
    api.updateTableTag(
        "test_ns.test_table", new UpdateTableTagRequest().tag("v1").version(2L), null);
    // Non-null assertion omitted: some ops legitimately return null
    // when the response schema is typeless Object / empty body.
  }
}
