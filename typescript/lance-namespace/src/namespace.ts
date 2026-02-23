// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import type * as Models from "@lance/namespace-fetch-client";

import { UnsupportedOperationError } from "./errors";

export abstract class LanceNamespace {
  public abstract namespaceId(): string;

  protected unsupported(name: string): never {
    throw new UnsupportedOperationError(`Not supported: ${name}`);
  }

  public async listNamespaces(
    _request: Models.ListNamespacesRequest,
  ): Promise<Models.ListNamespacesResponse> {
    return this.unsupported("listNamespaces");
  }

  public async describeNamespace(
    _request: Models.DescribeNamespaceRequest,
  ): Promise<Models.DescribeNamespaceResponse> {
    return this.unsupported("describeNamespace");
  }

  public async createNamespace(
    _request: Models.CreateNamespaceRequest,
  ): Promise<Models.CreateNamespaceResponse> {
    return this.unsupported("createNamespace");
  }

  public async dropNamespace(
    _request: Models.DropNamespaceRequest,
  ): Promise<Models.DropNamespaceResponse> {
    return this.unsupported("dropNamespace");
  }

  public async namespaceExists(
    _request: Models.NamespaceExistsRequest,
  ): Promise<void> {
    return this.unsupported("namespaceExists");
  }

  public async listTables(
    _request: Models.ListTablesRequest,
  ): Promise<Models.ListTablesResponse> {
    return this.unsupported("listTables");
  }

  public async listAllTables(
    _request: Models.ListTablesRequest,
  ): Promise<Models.ListTablesResponse> {
    return this.unsupported("listAllTables");
  }

  public async describeTable(
    _request: Models.DescribeTableRequest,
  ): Promise<Models.DescribeTableResponse> {
    return this.unsupported("describeTable");
  }

  public async registerTable(
    _request: Models.RegisterTableRequest,
  ): Promise<Models.RegisterTableResponse> {
    return this.unsupported("registerTable");
  }

  public async tableExists(_request: Models.TableExistsRequest): Promise<void> {
    return this.unsupported("tableExists");
  }

  public async dropTable(
    _request: Models.DropTableRequest,
  ): Promise<Models.DropTableResponse> {
    return this.unsupported("dropTable");
  }

  public async deregisterTable(
    _request: Models.DeregisterTableRequest,
  ): Promise<Models.DeregisterTableResponse> {
    return this.unsupported("deregisterTable");
  }

  public async createTable(
    _request: Models.CreateTableRequest,
    _requestData: Uint8Array,
  ): Promise<Models.CreateTableResponse> {
    return this.unsupported("createTable");
  }

  public async declareTable(
    _request: Models.DeclareTableRequest,
  ): Promise<Models.DeclareTableResponse> {
    return this.unsupported("declareTable");
  }

  public async createEmptyTable(
    _request: Models.CreateEmptyTableRequest,
  ): Promise<Models.CreateEmptyTableResponse> {
    return this.unsupported("createEmptyTable");
  }

  public async insertIntoTable(
    _request: Models.InsertIntoTableRequest,
    _requestData: Uint8Array,
  ): Promise<Models.InsertIntoTableResponse> {
    return this.unsupported("insertIntoTable");
  }

  public async mergeInsertIntoTable(
    _request: Models.MergeInsertIntoTableRequest,
    _requestData: Uint8Array,
  ): Promise<Models.MergeInsertIntoTableResponse> {
    return this.unsupported("mergeInsertIntoTable");
  }

  public async updateTable(
    _request: Models.UpdateTableRequest,
  ): Promise<Models.UpdateTableResponse> {
    return this.unsupported("updateTable");
  }

  public async deleteFromTable(
    _request: Models.DeleteFromTableRequest,
  ): Promise<Models.DeleteFromTableResponse> {
    return this.unsupported("deleteFromTable");
  }

  public async queryTable(
    _request: Models.QueryTableRequest,
  ): Promise<Uint8Array> {
    return this.unsupported("queryTable");
  }

  public async countTableRows(
    _request: Models.CountTableRowsRequest,
  ): Promise<number> {
    return this.unsupported("countTableRows");
  }

  public async createTableIndex(
    _request: Models.CreateTableIndexRequest,
  ): Promise<Models.CreateTableIndexResponse> {
    return this.unsupported("createTableIndex");
  }

  public async createTableScalarIndex(
    _request: Models.CreateTableIndexRequest,
  ): Promise<Models.CreateTableScalarIndexResponse> {
    return this.unsupported("createTableScalarIndex");
  }

  public async listTableIndices(
    _request: Models.ListTableIndicesRequest,
  ): Promise<Models.ListTableIndicesResponse> {
    return this.unsupported("listTableIndices");
  }

  public async describeTableIndexStats(
    _request: Models.DescribeTableIndexStatsRequest,
  ): Promise<Models.DescribeTableIndexStatsResponse> {
    return this.unsupported("describeTableIndexStats");
  }

  public async dropTableIndex(
    _request: Models.DropTableIndexRequest,
  ): Promise<Models.DropTableIndexResponse> {
    return this.unsupported("dropTableIndex");
  }

  public async restoreTable(
    _request: Models.RestoreTableRequest,
  ): Promise<Models.RestoreTableResponse> {
    return this.unsupported("restoreTable");
  }

  public async renameTable(
    _request: Models.RenameTableRequest,
  ): Promise<Models.RenameTableResponse> {
    return this.unsupported("renameTable");
  }

  public async listTableVersions(
    _request: Models.ListTableVersionsRequest,
  ): Promise<Models.ListTableVersionsResponse> {
    return this.unsupported("listTableVersions");
  }

  public async createTableVersion(
    _request: Models.CreateTableVersionRequest,
  ): Promise<Models.CreateTableVersionResponse> {
    return this.unsupported("createTableVersion");
  }

  public async describeTableVersion(
    _request: Models.DescribeTableVersionRequest,
  ): Promise<Models.DescribeTableVersionResponse> {
    return this.unsupported("describeTableVersion");
  }

  public async batchDeleteTableVersions(
    _request: Models.BatchDeleteTableVersionsRequest,
  ): Promise<Models.BatchDeleteTableVersionsResponse> {
    return this.unsupported("batchDeleteTableVersions");
  }

  public async batchCreateTableVersions(
    _request: Models.BatchCreateTableVersionsRequest,
  ): Promise<Models.BatchCreateTableVersionsResponse> {
    return this.unsupported("batchCreateTableVersions");
  }

  public async updateTableSchemaMetadata(
    _request: Models.UpdateTableSchemaMetadataRequest,
  ): Promise<Models.UpdateTableSchemaMetadataResponse> {
    return this.unsupported("updateTableSchemaMetadata");
  }

  public async getTableStats(
    _request: Models.GetTableStatsRequest,
  ): Promise<Models.GetTableStatsResponse> {
    return this.unsupported("getTableStats");
  }

  public async explainTableQueryPlan(
    _request: Models.ExplainTableQueryPlanRequest,
  ): Promise<string> {
    return this.unsupported("explainTableQueryPlan");
  }

  public async analyzeTableQueryPlan(
    _request: Models.AnalyzeTableQueryPlanRequest,
  ): Promise<string> {
    return this.unsupported("analyzeTableQueryPlan");
  }

  public async alterTableAddColumns(
    _request: Models.AlterTableAddColumnsRequest,
  ): Promise<Models.AlterTableAddColumnsResponse> {
    return this.unsupported("alterTableAddColumns");
  }

  public async alterTableAlterColumns(
    _request: Models.AlterTableAlterColumnsRequest,
  ): Promise<Models.AlterTableAlterColumnsResponse> {
    return this.unsupported("alterTableAlterColumns");
  }

  public async alterTableDropColumns(
    _request: Models.AlterTableDropColumnsRequest,
  ): Promise<Models.AlterTableDropColumnsResponse> {
    return this.unsupported("alterTableDropColumns");
  }

  public async listTableTags(
    _request: Models.ListTableTagsRequest,
  ): Promise<Models.ListTableTagsResponse> {
    return this.unsupported("listTableTags");
  }

  public async getTableTagVersion(
    _request: Models.GetTableTagVersionRequest,
  ): Promise<Models.GetTableTagVersionResponse> {
    return this.unsupported("getTableTagVersion");
  }

  public async createTableTag(
    _request: Models.CreateTableTagRequest,
  ): Promise<Models.CreateTableTagResponse> {
    return this.unsupported("createTableTag");
  }

  public async deleteTableTag(
    _request: Models.DeleteTableTagRequest,
  ): Promise<Models.DeleteTableTagResponse> {
    return this.unsupported("deleteTableTag");
  }

  public async updateTableTag(
    _request: Models.UpdateTableTagRequest,
  ): Promise<Models.UpdateTableTagResponse> {
    return this.unsupported("updateTableTag");
  }

  public async describeTransaction(
    _request: Models.DescribeTransactionRequest,
  ): Promise<Models.DescribeTransactionResponse> {
    return this.unsupported("describeTransaction");
  }

  public async alterTransaction(
    _request: Models.AlterTransactionRequest,
  ): Promise<Models.AlterTransactionResponse> {
    return this.unsupported("alterTransaction");
  }
}
