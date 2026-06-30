// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright The Lance Authors

/**
 * @file models.hpp
 * @brief Re-exports all generated REST client model types into
 *        lance_namespace::models.
 *
 * Include this header to get typed request/response objects:
 * @code
 *   #include <lance_namespace/models.hpp>
 *
 *   lance_namespace::models::CreateNamespaceRequest req;
 *   req.setNamespace({"myns"});
 * @endcode
 *
 * The underlying types come from the generated REST client
 * (cpp/lance_namespace_rest_client/).  Run `make gen-rest-client`
 * (from cpp/) to (re)generate them.
 *
 * When the OpenAPI spec adds or removes models, update the #include list
 * and using declarations below to match.
 */

#pragma once

// ─── Generated model headers ─────────────────────────────────────────────────

#include <model/AlterTableAddColumnsRequest.h>
#include <model/AlterTableAddColumnsResponse.h>
#include <model/AlterTableAlterColumnsRequest.h>
#include <model/AlterTableAlterColumnsResponse.h>
#include <model/AlterTableBackfillColumnsRequest.h>
#include <model/AlterTableBackfillColumnsResponse.h>
#include <model/AlterTableDropColumnsRequest.h>
#include <model/AlterTableDropColumnsResponse.h>
#include <model/AlterTransactionRequest.h>
#include <model/AlterTransactionResponse.h>
#include <model/AnalyzeTableQueryPlanRequest.h>
#include <model/BatchCommitTablesRequest.h>
#include <model/BatchCommitTablesResponse.h>
#include <model/BatchCreateTableVersionsRequest.h>
#include <model/BatchCreateTableVersionsResponse.h>
#include <model/BatchDeleteTableVersionsRequest.h>
#include <model/BatchDeleteTableVersionsResponse.h>
#include <model/CommitTableOperation.h>
#include <model/CommitTableResult.h>
#include <model/CountTableRowsRequest.h>
#include <model/CreateMaterializedViewRequest.h>
#include <model/CreateMaterializedViewResponse.h>
#include <model/CreateNamespaceRequest.h>
#include <model/CreateNamespaceResponse.h>
#include <model/CreateTableBranchRequest.h>
#include <model/CreateTableBranchResponse.h>
#include <model/CreateTableIndexRequest.h>
#include <model/CreateTableIndexResponse.h>
#include <model/CreateTableRequest.h>
#include <model/CreateTableResponse.h>
#include <model/CreateTableScalarIndexResponse.h>
#include <model/CreateTableTagRequest.h>
#include <model/CreateTableTagResponse.h>
#include <model/CreateTableVersionEntry.h>
#include <model/CreateTableVersionRequest.h>
#include <model/CreateTableVersionResponse.h>
#include <model/DeclareTableRequest.h>
#include <model/DeclareTableResponse.h>
#include <model/DeleteFromTableRequest.h>
#include <model/DeleteFromTableResponse.h>
#include <model/DeleteTableBranchRequest.h>
#include <model/DeleteTableBranchResponse.h>
#include <model/DeleteTableTagRequest.h>
#include <model/DeleteTableTagResponse.h>
#include <model/DeregisterTableRequest.h>
#include <model/DeregisterTableResponse.h>
#include <model/DescribeNamespaceRequest.h>
#include <model/DescribeNamespaceResponse.h>
#include <model/DescribeTableIndexStatsRequest.h>
#include <model/DescribeTableIndexStatsResponse.h>
#include <model/DescribeTableRequest.h>
#include <model/DescribeTableResponse.h>
#include <model/DescribeTableVersionRequest.h>
#include <model/DescribeTableVersionResponse.h>
#include <model/DescribeTransactionRequest.h>
#include <model/DescribeTransactionResponse.h>
#include <model/DropNamespaceRequest.h>
#include <model/DropNamespaceResponse.h>
#include <model/DropTableIndexRequest.h>
#include <model/DropTableIndexResponse.h>
#include <model/DropTableRequest.h>
#include <model/DropTableResponse.h>
#include <model/ExplainTableQueryPlanRequest.h>
#include <model/GetTableStatsRequest.h>
#include <model/GetTableStatsResponse.h>
#include <model/GetTableTagVersionRequest.h>
#include <model/GetTableTagVersionResponse.h>
#include <model/InsertIntoTableRequest.h>
#include <model/InsertIntoTableResponse.h>
#include <model/ListNamespacesRequest.h>
#include <model/ListNamespacesResponse.h>
#include <model/ListTableBranchesRequest.h>
#include <model/ListTableBranchesResponse.h>
#include <model/ListTableIndicesRequest.h>
#include <model/ListTableIndicesResponse.h>
#include <model/ListTableTagsRequest.h>
#include <model/ListTableTagsResponse.h>
#include <model/ListTableVersionsRequest.h>
#include <model/ListTableVersionsResponse.h>
#include <model/ListTablesRequest.h>
#include <model/ListTablesResponse.h>
#include <model/MaterializedViewUdtfEntry.h>
#include <model/MergeInsertIntoTableRequest.h>
#include <model/MergeInsertIntoTableResponse.h>
#include <model/NamespaceExistsRequest.h>
#include <model/QueryTableRequest.h>
#include <model/RefreshMaterializedViewRequest.h>
#include <model/RefreshMaterializedViewResponse.h>
#include <model/RegisterTableRequest.h>
#include <model/RegisterTableResponse.h>
#include <model/RenameTableRequest.h>
#include <model/RenameTableResponse.h>
#include <model/RestoreTableRequest.h>
#include <model/RestoreTableResponse.h>
#include <model/TableExistsRequest.h>
#include <model/TableVersion.h>
#include <model/UpdateFieldMetadataRequest.h>
#include <model/UpdateFieldMetadataResponse.h>
#include <model/UpdateTableRequest.h>
#include <model/UpdateTableResponse.h>
#include <model/UpdateTableSchemaMetadataRequest.h>
#include <model/UpdateTableSchemaMetadataResponse.h>
#include <model/UpdateTableTagRequest.h>
#include <model/UpdateTableTagResponse.h>

// ─── Re-export into lance_namespace::models ───────────────────────────────────

namespace lance_namespace {
namespace models {

using org::openapitools::client::model::AlterTableAddColumnsRequest;
using org::openapitools::client::model::AlterTableAddColumnsResponse;
using org::openapitools::client::model::AlterTableAlterColumnsRequest;
using org::openapitools::client::model::AlterTableAlterColumnsResponse;
using org::openapitools::client::model::AlterTableBackfillColumnsRequest;
using org::openapitools::client::model::AlterTableBackfillColumnsResponse;
using org::openapitools::client::model::AlterTableDropColumnsRequest;
using org::openapitools::client::model::AlterTableDropColumnsResponse;
using org::openapitools::client::model::AlterTransactionRequest;
using org::openapitools::client::model::AlterTransactionResponse;
using org::openapitools::client::model::AnalyzeTableQueryPlanRequest;
using org::openapitools::client::model::BatchCommitTablesRequest;
using org::openapitools::client::model::BatchCommitTablesResponse;
using org::openapitools::client::model::BatchCreateTableVersionsRequest;
using org::openapitools::client::model::BatchCreateTableVersionsResponse;
using org::openapitools::client::model::BatchDeleteTableVersionsRequest;
using org::openapitools::client::model::BatchDeleteTableVersionsResponse;
using org::openapitools::client::model::CommitTableOperation;
using org::openapitools::client::model::CommitTableResult;
using org::openapitools::client::model::CountTableRowsRequest;
using org::openapitools::client::model::CreateMaterializedViewRequest;
using org::openapitools::client::model::CreateMaterializedViewResponse;
using org::openapitools::client::model::CreateNamespaceRequest;
using org::openapitools::client::model::CreateNamespaceResponse;
using org::openapitools::client::model::CreateTableBranchRequest;
using org::openapitools::client::model::CreateTableBranchResponse;
using org::openapitools::client::model::CreateTableIndexRequest;
using org::openapitools::client::model::CreateTableIndexResponse;
using org::openapitools::client::model::CreateTableRequest;
using org::openapitools::client::model::CreateTableResponse;
using org::openapitools::client::model::CreateTableScalarIndexResponse;
using org::openapitools::client::model::CreateTableTagRequest;
using org::openapitools::client::model::CreateTableTagResponse;
using org::openapitools::client::model::CreateTableVersionEntry;
using org::openapitools::client::model::CreateTableVersionRequest;
using org::openapitools::client::model::CreateTableVersionResponse;
using org::openapitools::client::model::DeclareTableRequest;
using org::openapitools::client::model::DeclareTableResponse;
using org::openapitools::client::model::DeleteFromTableRequest;
using org::openapitools::client::model::DeleteFromTableResponse;
using org::openapitools::client::model::DeleteTableBranchRequest;
using org::openapitools::client::model::DeleteTableBranchResponse;
using org::openapitools::client::model::DeleteTableTagRequest;
using org::openapitools::client::model::DeleteTableTagResponse;
using org::openapitools::client::model::DeregisterTableRequest;
using org::openapitools::client::model::DeregisterTableResponse;
using org::openapitools::client::model::DescribeNamespaceRequest;
using org::openapitools::client::model::DescribeNamespaceResponse;
using org::openapitools::client::model::DescribeTableIndexStatsRequest;
using org::openapitools::client::model::DescribeTableIndexStatsResponse;
using org::openapitools::client::model::DescribeTableRequest;
using org::openapitools::client::model::DescribeTableResponse;
using org::openapitools::client::model::DescribeTableVersionRequest;
using org::openapitools::client::model::DescribeTableVersionResponse;
using org::openapitools::client::model::DescribeTransactionRequest;
using org::openapitools::client::model::DescribeTransactionResponse;
using org::openapitools::client::model::DropNamespaceRequest;
using org::openapitools::client::model::DropNamespaceResponse;
using org::openapitools::client::model::DropTableIndexRequest;
using org::openapitools::client::model::DropTableIndexResponse;
using org::openapitools::client::model::DropTableRequest;
using org::openapitools::client::model::DropTableResponse;
using org::openapitools::client::model::ExplainTableQueryPlanRequest;
using org::openapitools::client::model::GetTableStatsRequest;
using org::openapitools::client::model::GetTableStatsResponse;
using org::openapitools::client::model::GetTableTagVersionRequest;
using org::openapitools::client::model::GetTableTagVersionResponse;
using org::openapitools::client::model::InsertIntoTableRequest;
using org::openapitools::client::model::InsertIntoTableResponse;
using org::openapitools::client::model::ListNamespacesRequest;
using org::openapitools::client::model::ListNamespacesResponse;
using org::openapitools::client::model::ListTableBranchesRequest;
using org::openapitools::client::model::ListTableBranchesResponse;
using org::openapitools::client::model::ListTableIndicesRequest;
using org::openapitools::client::model::ListTableIndicesResponse;
using org::openapitools::client::model::ListTableTagsRequest;
using org::openapitools::client::model::ListTableTagsResponse;
using org::openapitools::client::model::ListTableVersionsRequest;
using org::openapitools::client::model::ListTableVersionsResponse;
using org::openapitools::client::model::ListTablesRequest;
using org::openapitools::client::model::ListTablesResponse;
using org::openapitools::client::model::MaterializedViewUdtfEntry;
using org::openapitools::client::model::MergeInsertIntoTableRequest;
using org::openapitools::client::model::MergeInsertIntoTableResponse;
using org::openapitools::client::model::NamespaceExistsRequest;
using org::openapitools::client::model::QueryTableRequest;
using org::openapitools::client::model::RefreshMaterializedViewRequest;
using org::openapitools::client::model::RefreshMaterializedViewResponse;
using org::openapitools::client::model::RegisterTableRequest;
using org::openapitools::client::model::RegisterTableResponse;
using org::openapitools::client::model::RenameTableRequest;
using org::openapitools::client::model::RenameTableResponse;
using org::openapitools::client::model::RestoreTableRequest;
using org::openapitools::client::model::RestoreTableResponse;
using org::openapitools::client::model::TableExistsRequest;
using org::openapitools::client::model::TableVersion;
using org::openapitools::client::model::UpdateFieldMetadataRequest;
using org::openapitools::client::model::UpdateFieldMetadataResponse;
using org::openapitools::client::model::UpdateTableRequest;
using org::openapitools::client::model::UpdateTableResponse;
using org::openapitools::client::model::UpdateTableSchemaMetadataRequest;
using org::openapitools::client::model::UpdateTableSchemaMetadataResponse;
using org::openapitools::client::model::UpdateTableTagRequest;
using org::openapitools::client::model::UpdateTableTagResponse;

}  // namespace models
}  // namespace lance_namespace
