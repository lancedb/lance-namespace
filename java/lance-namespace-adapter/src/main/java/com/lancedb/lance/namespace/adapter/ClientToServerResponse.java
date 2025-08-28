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
package com.lancedb.lance.namespace.adapter;

import com.lancedb.lance.namespace.server.springboot.model.AlterTransactionResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateEmptyTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableIndexResponse;
import com.lancedb.lance.namespace.server.springboot.model.CreateTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeleteFromTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableIndexStatsResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ErrorResponse;
import com.lancedb.lance.namespace.server.springboot.model.IndexContent;
import com.lancedb.lance.namespace.server.springboot.model.InsertIntoTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.JsonArrowDataType;
import com.lancedb.lance.namespace.server.springboot.model.JsonArrowField;
import com.lancedb.lance.namespace.server.springboot.model.JsonArrowSchema;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListTableIndicesResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListTablesResponse;
import com.lancedb.lance.namespace.server.springboot.model.MergeInsertIntoTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TransactionStatus;
import com.lancedb.lance.namespace.server.springboot.model.UpdateTableResponse;

import java.util.stream.Collectors;

public class ClientToServerResponse {

  public static DescribeNamespaceResponse describeNamespace(
      com.lancedb.lance.namespace.model.DescribeNamespaceResponse response) {
    DescribeNamespaceResponse converted = new DescribeNamespaceResponse();
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static ListNamespacesResponse listNamespaces(
      com.lancedb.lance.namespace.model.ListNamespacesResponse response) {
    ListNamespacesResponse converted = new ListNamespacesResponse();
    converted.setNamespaces(response.getNamespaces());
    converted.setPageToken(response.getPageToken());
    return converted;
  }

  public static ListTablesResponse listTables(
      com.lancedb.lance.namespace.model.ListTablesResponse response) {
    ListTablesResponse converted = new ListTablesResponse();
    converted.setTables(response.getTables());
    converted.setPageToken(response.getPageToken());
    return converted;
  }

  public static CreateNamespaceResponse createNamespace(
      com.lancedb.lance.namespace.model.CreateNamespaceResponse response) {
    CreateNamespaceResponse converted = new CreateNamespaceResponse();
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static DropNamespaceResponse dropNamespace(
      com.lancedb.lance.namespace.model.DropNamespaceResponse response) {
    DropNamespaceResponse converted = new DropNamespaceResponse();
    converted.setProperties(response.getProperties());
    converted.setTransactionId(response.getTransactionId());
    return converted;
  }

  public static DescribeTableResponse describeTable(
      com.lancedb.lance.namespace.model.DescribeTableResponse response) {
    DescribeTableResponse converted = new DescribeTableResponse();
    converted.setVersion(response.getVersion());
    converted.setLocation(response.getLocation());
    converted.setSchema(convertJsonSchema(response.getSchema()));
    converted.setProperties(response.getProperties());
    converted.setStorageOptions(response.getStorageOptions());
    return converted;
  }

  public static RegisterTableResponse registerTable(
      com.lancedb.lance.namespace.model.RegisterTableResponse response) {
    RegisterTableResponse converted = new RegisterTableResponse();
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    return converted;
  }

  private static JsonArrowSchema convertJsonSchema(
      com.lancedb.lance.namespace.model.JsonArrowSchema clientSchema) {
    if (clientSchema == null) {
      return null;
    }

    JsonArrowSchema serverSchema = new JsonArrowSchema();
    if (clientSchema.getFields() != null) {
      serverSchema.setFields(
          clientSchema.getFields().stream()
              .map(ClientToServerResponse::convertJsonField)
              .collect(Collectors.toList()));
    }
    serverSchema.setMetadata(clientSchema.getMetadata());
    return serverSchema;
  }

  private static JsonArrowField convertJsonField(
      com.lancedb.lance.namespace.model.JsonArrowField clientField) {
    if (clientField == null) {
      return null;
    }

    JsonArrowField serverField = new JsonArrowField();
    serverField.setName(clientField.getName());
    serverField.setNullable(clientField.getNullable());
    serverField.setType(convertJsonArrowDataType(clientField.getType()));
    serverField.setMetadata(clientField.getMetadata());
    return serverField;
  }

  private static JsonArrowDataType convertJsonArrowDataType(
      com.lancedb.lance.namespace.model.JsonArrowDataType clientType) {
    if (clientType == null) {
      return null;
    }

    JsonArrowDataType serverType = new JsonArrowDataType();
    serverType.setType(clientType.getType());
    if (clientType.getFields() != null) {
      serverType.setFields(
          clientType.getFields().stream()
              .map(ClientToServerResponse::convertJsonField)
              .collect(Collectors.toList()));
    }
    serverType.setLength(clientType.getLength());
    return serverType;
  }

  public static DropTableResponse dropTable(
      com.lancedb.lance.namespace.model.DropTableResponse response) {
    DropTableResponse converted = new DropTableResponse();
    converted.setId(response.getId());
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    converted.setTransactionId(response.getTransactionId());
    return converted;
  }

  public static DeregisterTableResponse deregisterTable(
      com.lancedb.lance.namespace.model.DeregisterTableResponse response) {
    DeregisterTableResponse converted = new DeregisterTableResponse();
    converted.setId(response.getId());
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static ErrorResponse errorResponse(
      com.lancedb.lance.namespace.model.ErrorResponse errorResponse) {
    ErrorResponse converted = new ErrorResponse();
    converted.setType(errorResponse.getType());
    converted.setError(errorResponse.getError());
    converted.setDetail(errorResponse.getDetail());
    converted.setCode(errorResponse.getCode());
    converted.setInstance(errorResponse.getInstance());
    return converted;
  }

  public static DescribeTransactionResponse describeTransaction(
      com.lancedb.lance.namespace.model.DescribeTransactionResponse response) {
    DescribeTransactionResponse converted = new DescribeTransactionResponse();
    converted.setStatus(TransactionStatus.valueOf(response.getStatus().name()));
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static AlterTransactionResponse alterTransaction(
      com.lancedb.lance.namespace.model.AlterTransactionResponse response) {
    AlterTransactionResponse converted = new AlterTransactionResponse();
    converted.setStatus(TransactionStatus.valueOf(response.getStatus().name()));
    return converted;
  }

  public static CreateTableResponse createTable(
      com.lancedb.lance.namespace.model.CreateTableResponse response) {
    CreateTableResponse converted = new CreateTableResponse();
    converted.setLocation(response.getLocation());
    converted.setVersion(response.getVersion());
    converted.setSchema(convertJsonSchema(response.getSchema()));
    converted.setProperties(response.getProperties());
    converted.setStorageOptions(response.getStorageOptions());
    return converted;
  }

  public static CreateEmptyTableResponse createEmptyTable(
      com.lancedb.lance.namespace.model.CreateEmptyTableResponse response) {
    CreateEmptyTableResponse converted = new CreateEmptyTableResponse();
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    converted.setStorageOptions(response.getStorageOptions());
    return converted;
  }

  public static InsertIntoTableResponse insertIntoTable(
      com.lancedb.lance.namespace.model.InsertIntoTableResponse response) {
    InsertIntoTableResponse converted = new InsertIntoTableResponse();
    converted.setVersion(response.getVersion());
    return converted;
  }

  public static MergeInsertIntoTableResponse mergeInsertIntoTable(
      com.lancedb.lance.namespace.model.MergeInsertIntoTableResponse response) {
    MergeInsertIntoTableResponse converted = new MergeInsertIntoTableResponse();
    converted.setNumUpdatedRows(response.getNumUpdatedRows());
    converted.setNumInsertedRows(response.getNumInsertedRows());
    converted.setNumDeletedRows(response.getNumDeletedRows());
    converted.setVersion(response.getVersion());
    return converted;
  }

  public static UpdateTableResponse updateTable(
      com.lancedb.lance.namespace.model.UpdateTableResponse response) {
    UpdateTableResponse converted = new UpdateTableResponse();
    converted.setUpdatedRows(response.getUpdatedRows());
    converted.setVersion(response.getVersion());
    return converted;
  }

  public static DeleteFromTableResponse deleteFromTable(
      com.lancedb.lance.namespace.model.DeleteFromTableResponse response) {
    DeleteFromTableResponse converted = new DeleteFromTableResponse();
    converted.setVersion(response.getVersion());
    return converted;
  }

  public static CreateTableIndexResponse createTableIndex(
      com.lancedb.lance.namespace.model.CreateTableIndexResponse response) {
    CreateTableIndexResponse converted = new CreateTableIndexResponse();
    converted.setId(response.getId());
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static ListTableIndicesResponse listTableIndices(
      com.lancedb.lance.namespace.model.ListTableIndicesResponse response) {
    ListTableIndicesResponse converted = new ListTableIndicesResponse();
    converted.setIndexes(
        response.getIndexes().stream()
            .map(ClientToServerResponse::convertIndexListItem)
            .collect(Collectors.toList()));
    return converted;
  }

  private static IndexContent convertIndexListItem(
      com.lancedb.lance.namespace.model.IndexContent item) {
    IndexContent converted = new IndexContent();
    converted.setIndexName(item.getIndexName());
    converted.setIndexUuid(item.getIndexUuid());
    converted.setColumns(item.getColumns());
    converted.setStatus(item.getStatus());
    return converted;
  }

  public static DescribeTableIndexStatsResponse describeTableIndexStats(
      com.lancedb.lance.namespace.model.DescribeTableIndexStatsResponse response) {
    DescribeTableIndexStatsResponse converted = new DescribeTableIndexStatsResponse();
    converted.setDistanceType(response.getDistanceType());
    converted.setIndexType(response.getIndexType());
    converted.setNumIndexedRows(response.getNumIndexedRows());
    converted.setNumUnindexedRows(response.getNumUnindexedRows());
    return converted;
  }
}
