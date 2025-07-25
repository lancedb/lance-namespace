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
import com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ErrorResponse;
import com.lancedb.lance.namespace.server.springboot.model.JsonArrowField;
import com.lancedb.lance.namespace.server.springboot.model.JsonArrowSchema;
import com.lancedb.lance.namespace.server.springboot.model.JsonDataType;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListTablesResponse;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TransactionStatus;

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
    serverField.setType(convertJsonDataType(clientField.getType()));
    serverField.setMetadata(clientField.getMetadata());
    return serverField;
  }

  private static JsonDataType convertJsonDataType(
      com.lancedb.lance.namespace.model.JsonDataType clientType) {
    if (clientType == null) {
      return null;
    }

    JsonDataType serverType = new JsonDataType();
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
    converted.setTitle(errorResponse.getTitle());
    converted.setDetail(errorResponse.getDetail());
    converted.setStatus(errorResponse.getStatus());
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
}
