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

import {
  Configuration,
  FetchError,
  NamespaceApi,
  RequiredError,
  ResponseError,
  TableApi,
  TransactionApi,
} from "@lance/namespace-fetch-client";
import type * as Models from "@lance/namespace-fetch-client";

import {
  fromErrorCode,
  fromErrorResponse,
  InternalError,
  InvalidInputError,
  LanceNamespace,
  LanceNamespaceError,
  ServiceUnavailableError,
} from "@lance/lance-namespace";

interface HasIdentityAndContext {
  identity?: {
    api_key?: string;
    auth_token?: string;
  };
  context?: Record<string, string>;
}

export interface RestNamespaceProperties {
  uri: string;
  delimiter?: string;
  [key: string]: string | undefined;
}

export class RestNamespace extends LanceNamespace {
  private readonly properties: Record<string, string>;
  private readonly delimiter: string;
  private readonly namespaceApi: NamespaceApi;
  private readonly tableApi: TableApi;
  private readonly transactionApi: TransactionApi;

  public constructor(properties: Record<string, string>) {
    super();

    const uri = properties.uri;
    if (uri === undefined || uri.trim() === "") {
      throw new InvalidInputError("Missing required rest namespace property: uri");
    }

    this.properties = properties;
    this.delimiter = properties.delimiter ?? "$";

    const headers: Record<string, string> = {};
    for (const [key, value] of Object.entries(properties)) {
      if (value !== undefined && key.startsWith("headers.")) {
        headers[key.slice("headers.".length)] = value;
      }
    }

    const config = new Configuration({
      basePath: uri.replace(/\/+$/, ""),
      headers,
    });

    this.namespaceApi = new NamespaceApi(config);
    this.tableApi = new TableApi(config);
    this.transactionApi = new TransactionApi(config);
  }

  public namespaceId(): string {
    return `RestNamespace { uri: '${this.properties.uri}', delimiter: '${this.delimiter}' }`;
  }

  public async createNamespace(
    request: Models.CreateNamespaceRequest,
  ): Promise<Models.CreateNamespaceResponse> {
    const id = this.requireNamespaceIdentifier(request.id);
    const normalized = this.canonicalizeId(request, id, "createNamespace");

    return this.withErrorMapping(() =>
      this.namespaceApi.createNamespace(
        {
          id: this.toRouteIdentifier(id, true),
          delimiter: this.delimiter,
          createNamespaceRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async listNamespaces(
    request: Models.ListNamespacesRequest,
  ): Promise<Models.ListNamespacesResponse> {
    const id = this.optionalNamespaceIdentifier(request.id);

    return this.withErrorMapping(() =>
      this.namespaceApi.listNamespaces(
        {
          id: this.toRouteIdentifier(id, true),
          delimiter: this.delimiter,
          pageToken: request.page_token,
          limit: request.limit,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async describeNamespace(
    request: Models.DescribeNamespaceRequest,
  ): Promise<Models.DescribeNamespaceResponse> {
    const id = this.requireNamespaceIdentifier(request.id);
    const normalized = this.canonicalizeId(request, id, "describeNamespace");

    return this.withErrorMapping(() =>
      this.namespaceApi.describeNamespace(
        {
          id: this.toRouteIdentifier(id, true),
          delimiter: this.delimiter,
          describeNamespaceRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async dropNamespace(
    request: Models.DropNamespaceRequest,
  ): Promise<Models.DropNamespaceResponse> {
    const id = this.requireNamespaceIdentifier(request.id);
    const normalized = this.normalizeDropNamespaceRequest(
      this.canonicalizeId(request, id, "dropNamespace"),
    );

    return this.withErrorMapping(() =>
      this.namespaceApi.dropNamespace(
        {
          id: this.toRouteIdentifier(id, true),
          delimiter: this.delimiter,
          dropNamespaceRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async namespaceExists(request: Models.NamespaceExistsRequest): Promise<void> {
    const id = this.requireNamespaceIdentifier(request.id);
    const normalized = this.canonicalizeId(request, id, "namespaceExists");

    await this.withErrorMapping(() =>
      this.namespaceApi.namespaceExists(
        {
          id: this.toRouteIdentifier(id, true),
          delimiter: this.delimiter,
          namespaceExistsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async listTables(
    request: Models.ListTablesRequest,
  ): Promise<Models.ListTablesResponse> {
    const id = this.optionalNamespaceIdentifier(request.id);

    return this.withErrorMapping(() =>
      this.namespaceApi.listTables(
        {
          id: this.toRouteIdentifier(id, true),
          delimiter: this.delimiter,
          pageToken: request.page_token,
          limit: request.limit,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async listAllTables(
    request: Models.ListTablesRequest,
  ): Promise<Models.ListTablesResponse> {
    return this.withErrorMapping(() =>
      this.tableApi.listAllTables(
        {
          delimiter: this.delimiter,
          pageToken: request.page_token,
          limit: request.limit,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async describeTable(
    request: Models.DescribeTableRequest,
  ): Promise<Models.DescribeTableResponse> {
    const id = this.requireTableIdentifier(request.id, "describeTable");
    const normalized = this.canonicalizeId(request, id, "describeTable");

    return this.withErrorMapping(() =>
      this.tableApi.describeTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          withTableUri: request.with_table_uri,
          loadDetailedMetadata: request.load_detailed_metadata ?? false,
          describeTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async registerTable(
    request: Models.RegisterTableRequest,
  ): Promise<Models.RegisterTableResponse> {
    const id = this.requireTableIdentifier(request.id, "registerTable");
    const normalized = this.canonicalizeId(request, id, "registerTable");

    return this.withErrorMapping(() =>
      this.tableApi.registerTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          registerTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async tableExists(request: Models.TableExistsRequest): Promise<void> {
    const id = this.requireTableIdentifier(request.id, "tableExists");
    const normalized = this.canonicalizeId(request, id, "tableExists");

    await this.withErrorMapping(() =>
      this.tableApi.tableExists(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          tableExistsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async dropTable(
    request: Models.DropTableRequest,
  ): Promise<Models.DropTableResponse> {
    const id = this.requireTableIdentifier(request.id, "dropTable");

    return this.withErrorMapping(() =>
      this.tableApi.dropTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async deregisterTable(
    request: Models.DeregisterTableRequest,
  ): Promise<Models.DeregisterTableResponse> {
    const id = this.requireTableIdentifier(request.id, "deregisterTable");
    const normalized = this.canonicalizeId(request, id, "deregisterTable");

    return this.withErrorMapping(() =>
      this.tableApi.deregisterTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          deregisterTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async createTable(
    request: Models.CreateTableRequest,
    requestData: Uint8Array,
  ): Promise<Models.CreateTableResponse> {
    const id = this.requireTableIdentifier(request.id, "createTable");
    const normalized = this.canonicalizeId(request, id, "createTable");

    const extraHeaders: Record<string, string> = {};
    if (request.properties !== undefined) {
      extraHeaders["x-lance-table-properties"] = JSON.stringify(request.properties);
    }

    return this.withErrorMapping(() =>
      this.tableApi.createTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          mode: normalized.mode,
          body: this.toBlob(requestData),
        },
        this.requestInitOverride(normalized, extraHeaders),
      ),
    );
  }

  public async declareTable(
    request: Models.DeclareTableRequest,
  ): Promise<Models.DeclareTableResponse> {
    const id = this.requireTableIdentifier(request.id, "declareTable");
    const normalized = this.canonicalizeId(request, id, "declareTable");

    return this.withErrorMapping(() =>
      this.tableApi.declareTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          declareTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async createEmptyTable(
    request: Models.CreateEmptyTableRequest,
  ): Promise<Models.CreateEmptyTableResponse> {
    const id = this.requireTableIdentifier(request.id, "createEmptyTable");
    const normalized = this.canonicalizeId(request, id, "createEmptyTable");

    return this.withErrorMapping(() =>
      this.tableApi.createEmptyTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          createEmptyTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async insertIntoTable(
    request: Models.InsertIntoTableRequest,
    requestData: Uint8Array,
  ): Promise<Models.InsertIntoTableResponse> {
    const id = this.requireTableIdentifier(request.id, "insertIntoTable");
    const normalized = this.canonicalizeId(request, id, "insertIntoTable");

    return this.withErrorMapping(() =>
      this.tableApi.insertIntoTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          mode: normalized.mode,
          body: this.toBlob(requestData),
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async mergeInsertIntoTable(
    request: Models.MergeInsertIntoTableRequest,
    requestData: Uint8Array,
  ): Promise<Models.MergeInsertIntoTableResponse> {
    const id = this.requireTableIdentifier(request.id, "mergeInsertIntoTable");
    const normalized = this.canonicalizeId(request, id, "mergeInsertIntoTable");
    const on = normalized.on;
    if (on === undefined || on.length === 0) {
      throw new InvalidInputError("MergeInsertIntoTable request requires non-empty `on`");
    }

    return this.withErrorMapping(() =>
      this.tableApi.mergeInsertIntoTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          on,
          whenMatchedUpdateAll: normalized.when_matched_update_all,
          whenMatchedUpdateAllFilt: normalized.when_matched_update_all_filt,
          whenNotMatchedInsertAll: normalized.when_not_matched_insert_all,
          whenNotMatchedBySourceDelete: normalized.when_not_matched_by_source_delete,
          whenNotMatchedBySourceDeleteFilt:
            normalized.when_not_matched_by_source_delete_filt,
          timeout: normalized.timeout,
          useIndex: normalized.use_index,
          body: this.toBlob(requestData),
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async updateTable(
    request: Models.UpdateTableRequest,
  ): Promise<Models.UpdateTableResponse> {
    const id = this.requireTableIdentifier(request.id, "updateTable");
    const normalized = this.canonicalizeId(request, id, "updateTable");

    return this.withErrorMapping(() =>
      this.tableApi.updateTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          updateTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async deleteFromTable(
    request: Models.DeleteFromTableRequest,
  ): Promise<Models.DeleteFromTableResponse> {
    const id = this.requireTableIdentifier(request.id, "deleteFromTable");
    const normalized = this.canonicalizeId(request, id, "deleteFromTable");

    return this.withErrorMapping(() =>
      this.tableApi.deleteFromTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          deleteFromTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async queryTable(request: Models.QueryTableRequest): Promise<Uint8Array> {
    const id = this.requireTableIdentifier(request.id, "queryTable");
    const normalized = this.canonicalizeId(request, id, "queryTable");

    const blob = await this.withErrorMapping(() =>
      this.tableApi.queryTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          queryTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );

    return this.toUint8Array(blob);
  }

  public async countTableRows(request: Models.CountTableRowsRequest): Promise<number> {
    const id = this.requireTableIdentifier(request.id, "countTableRows");
    const normalized = this.canonicalizeId(request, id, "countTableRows");

    return this.withErrorMapping(() =>
      this.tableApi.countTableRows(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          countTableRowsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async createTableIndex(
    request: Models.CreateTableIndexRequest,
  ): Promise<Models.CreateTableIndexResponse> {
    const id = this.requireTableIdentifier(request.id, "createTableIndex");
    const normalized = this.canonicalizeId(request, id, "createTableIndex");

    return this.withErrorMapping(() =>
      this.tableApi.createTableIndex(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          createTableIndexRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async createTableScalarIndex(
    request: Models.CreateTableIndexRequest,
  ): Promise<Models.CreateTableScalarIndexResponse> {
    const id = this.requireTableIdentifier(request.id, "createTableScalarIndex");
    const normalized = this.canonicalizeId(request, id, "createTableScalarIndex");

    return this.withErrorMapping(() =>
      this.tableApi.createTableScalarIndex(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          createTableIndexRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async listTableIndices(
    request: Models.ListTableIndicesRequest,
  ): Promise<Models.ListTableIndicesResponse> {
    const id = this.requireTableIdentifier(request.id, "listTableIndices");
    const normalized = this.canonicalizeId(request, id, "listTableIndices");

    return this.withErrorMapping(() =>
      this.tableApi.listTableIndices(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          listTableIndicesRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async describeTableIndexStats(
    request: Models.DescribeTableIndexStatsRequest,
  ): Promise<Models.DescribeTableIndexStatsResponse> {
    const id = this.requireTableIdentifier(request.id, "describeTableIndexStats");
    const indexName = this.requireIndexName(
      request.index_name,
      "describeTableIndexStats",
    );
    const normalized = this.canonicalizeIndexName(
      this.canonicalizeId(request, id, "describeTableIndexStats"),
      indexName,
      "describeTableIndexStats",
    );

    return this.withErrorMapping(() =>
      this.tableApi.describeTableIndexStats(
        {
          id: this.toRouteIdentifier(id),
          indexName,
          delimiter: this.delimiter,
          describeTableIndexStatsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async dropTableIndex(
    request: Models.DropTableIndexRequest,
  ): Promise<Models.DropTableIndexResponse> {
    const id = this.requireTableIdentifier(request.id, "dropTableIndex");
    const indexName = this.requireIndexName(request.index_name, "dropTableIndex");

    return this.withErrorMapping(() =>
      this.tableApi.dropTableIndex(
        {
          id: this.toRouteIdentifier(id),
          indexName,
          delimiter: this.delimiter,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async restoreTable(
    request: Models.RestoreTableRequest,
  ): Promise<Models.RestoreTableResponse> {
    const id = this.requireTableIdentifier(request.id, "restoreTable");
    const normalized = this.canonicalizeId(request, id, "restoreTable");

    return this.withErrorMapping(() =>
      this.tableApi.restoreTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          restoreTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async renameTable(
    request: Models.RenameTableRequest,
  ): Promise<Models.RenameTableResponse> {
    const id = this.requireTableIdentifier(request.id, "renameTable");
    const normalized = this.canonicalizeId(request, id, "renameTable");

    return this.withErrorMapping(() =>
      this.tableApi.renameTable(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          renameTableRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async listTableVersions(
    request: Models.ListTableVersionsRequest,
  ): Promise<Models.ListTableVersionsResponse> {
    const id = this.requireTableIdentifier(request.id, "listTableVersions");

    return this.withErrorMapping(() =>
      this.tableApi.listTableVersions(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          pageToken: request.page_token,
          limit: request.limit,
          descending: request.descending,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async createTableVersion(
    request: Models.CreateTableVersionRequest,
  ): Promise<Models.CreateTableVersionResponse> {
    const id = this.requireTableIdentifier(request.id, "createTableVersion");
    const normalized = this.canonicalizeId(request, id, "createTableVersion");

    return this.withErrorMapping(() =>
      this.tableApi.createTableVersion(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          createTableVersionRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async describeTableVersion(
    request: Models.DescribeTableVersionRequest,
  ): Promise<Models.DescribeTableVersionResponse> {
    const id = this.requireTableIdentifier(request.id, "describeTableVersion");
    const normalized = this.canonicalizeId(request, id, "describeTableVersion");

    return this.withErrorMapping(() =>
      this.tableApi.describeTableVersion(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          describeTableVersionRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async batchDeleteTableVersions(
    request: Models.BatchDeleteTableVersionsRequest,
  ): Promise<Models.BatchDeleteTableVersionsResponse> {
    const id = this.requireTableIdentifier(request.id, "batchDeleteTableVersions");
    const normalized = this.canonicalizeId(
      request,
      id,
      "batchDeleteTableVersions",
    );

    return this.withErrorMapping(() =>
      this.tableApi.batchDeleteTableVersions(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          batchDeleteTableVersionsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async batchCreateTableVersions(
    request: Models.BatchCreateTableVersionsRequest,
  ): Promise<Models.BatchCreateTableVersionsResponse> {
    return this.withErrorMapping(() =>
      this.tableApi.batchCreateTableVersions(
        {
          delimiter: this.delimiter,
          batchCreateTableVersionsRequest: request,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async updateTableSchemaMetadata(
    request: Models.UpdateTableSchemaMetadataRequest,
  ): Promise<Models.UpdateTableSchemaMetadataResponse> {
    const id = this.requireTableIdentifier(request.id, "updateTableSchemaMetadata");
    const normalized = this.canonicalizeId(
      request,
      id,
      "updateTableSchemaMetadata",
    );

    const metadataMap = await this.withErrorMapping(() =>
      this.tableApi.updateTableSchemaMetadata(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          requestBody: normalized.metadata ?? {},
        },
        this.requestInitOverride(normalized),
      ),
    );

    return { metadata: metadataMap };
  }

  public async getTableStats(
    request: Models.GetTableStatsRequest,
  ): Promise<Models.GetTableStatsResponse> {
    const id = this.requireTableIdentifier(request.id, "getTableStats");
    const normalized = this.canonicalizeId(request, id, "getTableStats");

    return this.withErrorMapping(() =>
      this.tableApi.getTableStats(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          getTableStatsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async explainTableQueryPlan(
    request: Models.ExplainTableQueryPlanRequest,
  ): Promise<string> {
    const id = this.requireTableIdentifier(request.id, "explainTableQueryPlan");
    const normalized = this.canonicalizeId(request, id, "explainTableQueryPlan");

    return this.withErrorMapping(() =>
      this.tableApi.explainTableQueryPlan(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          explainTableQueryPlanRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async analyzeTableQueryPlan(
    request: Models.AnalyzeTableQueryPlanRequest,
  ): Promise<string> {
    const id = this.requireTableIdentifier(request.id, "analyzeTableQueryPlan");
    const normalized = this.canonicalizeId(request, id, "analyzeTableQueryPlan");

    return this.withErrorMapping(() =>
      this.tableApi.analyzeTableQueryPlan(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          analyzeTableQueryPlanRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async alterTableAddColumns(
    request: Models.AlterTableAddColumnsRequest,
  ): Promise<Models.AlterTableAddColumnsResponse> {
    const id = this.requireTableIdentifier(request.id, "alterTableAddColumns");
    const normalized = this.canonicalizeId(request, id, "alterTableAddColumns");

    return this.withErrorMapping(() =>
      this.tableApi.alterTableAddColumns(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          alterTableAddColumnsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async alterTableAlterColumns(
    request: Models.AlterTableAlterColumnsRequest,
  ): Promise<Models.AlterTableAlterColumnsResponse> {
    const id = this.requireTableIdentifier(request.id, "alterTableAlterColumns");
    const normalized = this.canonicalizeId(request, id, "alterTableAlterColumns");

    return this.withErrorMapping(() =>
      this.tableApi.alterTableAlterColumns(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          alterTableAlterColumnsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async alterTableDropColumns(
    request: Models.AlterTableDropColumnsRequest,
  ): Promise<Models.AlterTableDropColumnsResponse> {
    const id = this.requireTableIdentifier(request.id, "alterTableDropColumns");
    const normalized = this.canonicalizeId(request, id, "alterTableDropColumns");

    return this.withErrorMapping(() =>
      this.tableApi.alterTableDropColumns(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          alterTableDropColumnsRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async listTableTags(
    request: Models.ListTableTagsRequest,
  ): Promise<Models.ListTableTagsResponse> {
    const id = this.requireTableIdentifier(request.id, "listTableTags");

    return this.withErrorMapping(() =>
      this.tableApi.listTableTags(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          pageToken: request.page_token,
          limit: request.limit,
        },
        this.requestInitOverride(request),
      ),
    );
  }

  public async getTableTagVersion(
    request: Models.GetTableTagVersionRequest,
  ): Promise<Models.GetTableTagVersionResponse> {
    const id = this.requireTableIdentifier(request.id, "getTableTagVersion");
    const normalized = this.canonicalizeId(request, id, "getTableTagVersion");

    return this.withErrorMapping(() =>
      this.tableApi.getTableTagVersion(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          getTableTagVersionRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async createTableTag(
    request: Models.CreateTableTagRequest,
  ): Promise<Models.CreateTableTagResponse> {
    const id = this.requireTableIdentifier(request.id, "createTableTag");
    const normalized = this.canonicalizeId(request, id, "createTableTag");

    return this.withErrorMapping(() =>
      this.tableApi.createTableTag(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          createTableTagRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async deleteTableTag(
    request: Models.DeleteTableTagRequest,
  ): Promise<Models.DeleteTableTagResponse> {
    const id = this.requireTableIdentifier(request.id, "deleteTableTag");
    const normalized = this.canonicalizeId(request, id, "deleteTableTag");

    return this.withErrorMapping(() =>
      this.tableApi.deleteTableTag(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          deleteTableTagRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async updateTableTag(
    request: Models.UpdateTableTagRequest,
  ): Promise<Models.UpdateTableTagResponse> {
    const id = this.requireTableIdentifier(request.id, "updateTableTag");
    const normalized = this.canonicalizeId(request, id, "updateTableTag");

    return this.withErrorMapping(() =>
      this.tableApi.updateTableTag(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          updateTableTagRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async describeTransaction(
    request: Models.DescribeTransactionRequest,
  ): Promise<Models.DescribeTransactionResponse> {
    const id = this.requireTransactionIdentifier(request.id, "describeTransaction");
    const normalized = this.canonicalizeId(request, id, "describeTransaction");

    return this.withErrorMapping(() =>
      this.transactionApi.describeTransaction(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          describeTransactionRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  public async alterTransaction(
    request: Models.AlterTransactionRequest,
  ): Promise<Models.AlterTransactionResponse> {
    const id = this.requireTransactionIdentifier(request.id, "alterTransaction");
    const normalized = this.canonicalizeId(request, id, "alterTransaction");

    return this.withErrorMapping(() =>
      this.transactionApi.alterTransaction(
        {
          id: this.toRouteIdentifier(id),
          delimiter: this.delimiter,
          alterTransactionRequest: normalized,
        },
        this.requestInitOverride(normalized),
      ),
    );
  }

  private async withErrorMapping<T>(fn: () => Promise<T>): Promise<T> {
    try {
      return await fn();
    } catch (error) {
      throw await this.mapError(error);
    }
  }

  private async mapError(error: unknown): Promise<LanceNamespaceError> {
    if (error instanceof LanceNamespaceError) {
      return error;
    }

    if (error instanceof RequiredError) {
      return new InvalidInputError(error.message, { cause: error });
    }

    if (error instanceof FetchError) {
      return new ServiceUnavailableError(error.message, { cause: error });
    }

    if (error instanceof ResponseError) {
      let payload: unknown;
      try {
        payload = await error.response.clone().json();
      } catch {
        payload = undefined;
      }

      if (
        payload !== undefined &&
        payload !== null &&
        typeof payload === "object" &&
        "code" in payload
      ) {
        return fromErrorResponse(
          payload as {
            code?: number;
            error?: string;
            detail?: string;
            instance?: string;
          },
          `HTTP ${error.response.status}`,
          {
            cause: error,
            status: error.response.status,
          },
        );
      }

      const responseText = await error.response.clone().text();
      const message = responseText.length > 0 ? responseText : error.message;
      return fromErrorCode(18, message, {
        cause: error,
        status: error.response.status,
      });
    }

    const message =
      error instanceof Error ? error.message : "Unknown error from rest namespace";
    return new InternalError(message, { cause: error });
  }

  private requestInitOverride(
    request: HasIdentityAndContext,
    additionalHeaders: Record<string, string> = {},
  ): (ctx: { init: RequestInit }) => Promise<RequestInit> {
    return async ({ init }) => {
      const headers = new Headers(init.headers ?? {});

      for (const [key, value] of Object.entries(additionalHeaders)) {
        headers.set(key, value);
      }

      if (request.identity?.api_key !== undefined) {
        headers.set("x-api-key", request.identity.api_key);
      }

      if (request.identity?.auth_token !== undefined) {
        const authToken = request.identity.auth_token;
        const headerValue = authToken.startsWith("Bearer ")
          ? authToken
          : `Bearer ${authToken}`;
        headers.set("Authorization", headerValue);
      }

      if (request.context !== undefined) {
        for (const [key, value] of Object.entries(request.context)) {
          headers.set(`x-lance-ctx-${key}`, value);
        }
      }

      return { ...init, headers };
    };
  }

  private toBlob(data: Uint8Array): Blob {
    const copied = Uint8Array.from(data);
    return new Blob([copied], { type: "application/vnd.apache.arrow.stream" });
  }

  private async toUint8Array(blob: Blob): Promise<Uint8Array> {
    const arrayBuffer = await blob.arrayBuffer();
    return new Uint8Array(arrayBuffer);
  }

  private canonicalizeId<T extends { id?: string[] }>(
    request: T,
    pathId: string[],
    operation: string,
  ): T {
    if (request.id !== undefined && !this.idsEqual(pathId, request.id)) {
      throw new InvalidInputError(
        `Request id does not match route id for ${operation}`,
      );
    }

    return {
      ...request,
      id: [...pathId],
    };
  }

  private canonicalizeIndexName<T extends { index_name?: string }>(
    request: T,
    indexName: string,
    operation: string,
  ): T {
    if (
      request.index_name !== undefined &&
      request.index_name.trim() !== indexName
    ) {
      throw new InvalidInputError(
        `Request index_name does not match route index_name for ${operation}`,
      );
    }

    return {
      ...request,
      index_name: indexName,
    };
  }

  private normalizeDropNamespaceRequest(
    request: Models.DropNamespaceRequest,
  ): Models.DropNamespaceRequest {
    const behavior = request.behavior;
    if (behavior !== undefined && !this.isRestrictBehavior(behavior)) {
      throw new InvalidInputError(
        "DropNamespace only supports behavior=Restrict in this implementation",
      );
    }

    return {
      ...request,
      behavior: behavior ?? "Restrict",
    };
  }

  private isRestrictBehavior(value: string): boolean {
    const normalized = value.trim().toLowerCase();
    return normalized === "restrict";
  }

  private optionalNamespaceIdentifier(id?: string[]): string[] {
    if (id === undefined || id.length === 0) {
      return [];
    }
    return [...id];
  }

  private requireNamespaceIdentifier(id?: string[]): string[] {
    if (id === undefined) {
      throw new InvalidInputError("Request id is required");
    }
    return [...id];
  }

  private requireTableIdentifier(id: string[] | undefined, op: string): string[] {
    if (id === undefined || id.length === 0) {
      throw new InvalidInputError(`Table id is required for ${op}`);
    }
    return [...id];
  }

  private requireTransactionIdentifier(
    id: string[] | undefined,
    op: string,
  ): string[] {
    if (id === undefined || id.length === 0) {
      throw new InvalidInputError(`Transaction id is required for ${op}`);
    }
    return [...id];
  }

  private requireIndexName(value: string | undefined, op: string): string {
    if (value === undefined || value.trim().length === 0) {
      throw new InvalidInputError(`index_name is required for ${op}`);
    }
    return value.trim();
  }

  private toRouteIdentifier(id: string[], allowRoot = false): string {
    if (id.length === 0) {
      if (!allowRoot) {
        throw new InvalidInputError("Root identifier is not allowed here");
      }
      return this.delimiter;
    }
    return id.join(this.delimiter);
  }

  private idsEqual(left: string[], right: string[]): boolean {
    if (left.length !== right.length) {
      return false;
    }

    for (let index = 0; index < left.length; index += 1) {
      if (left[index] !== right[index]) {
        return false;
      }
    }

    return true;
  }
}
