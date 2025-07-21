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
import com.lancedb.lance.namespace.server.springboot.model.DescribeTableResponseV2;
import com.lancedb.lance.namespace.server.springboot.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.server.springboot.model.DropTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsResponse;
import com.lancedb.lance.namespace.server.springboot.model.RegisterTableResponse;
import com.lancedb.lance.namespace.server.springboot.model.TableExistsResponse;
import com.lancedb.lance.namespace.server.springboot.model.TransactionStatus;

public class ClientToServerResponse {

  public static DescribeNamespaceResponse describeNamespace(
      com.lancedb.lance.namespace.model.DescribeNamespaceResponse response) {
    DescribeNamespaceResponse converted = new DescribeNamespaceResponse();
    converted.setParent(response.getParent());
    converted.setProperties(response.getProperties());
    converted.setName(response.getName());
    return converted;
  }

  public static ListNamespacesResponse listNamespaces(
      com.lancedb.lance.namespace.model.ListNamespacesResponse response) {
    ListNamespacesResponse converted = new ListNamespacesResponse();
    converted.setNamespaces(response.getNamespaces());
    return converted;
  }

  public static CreateNamespaceResponse createNamespace(
      com.lancedb.lance.namespace.model.CreateNamespaceResponse response) {
    CreateNamespaceResponse converted = new CreateNamespaceResponse();
    converted.setParent(response.getParent());
    converted.setProperties(response.getProperties());
    converted.setName(response.getName());
    return converted;
  }

  public static DropNamespaceResponse dropNamespace(
      com.lancedb.lance.namespace.model.DropNamespaceResponse response) {
    DropNamespaceResponse converted = new DropNamespaceResponse();
    converted.setParent(response.getParent());
    converted.setProperties(response.getProperties());
    converted.setName(response.getName());
    converted.setTransactionId(response.getTransactionId());
    return converted;
  }

  public static NamespaceExistsResponse namespaceExists(
      com.lancedb.lance.namespace.model.NamespaceExistsResponse response) {
    NamespaceExistsResponse converted = new NamespaceExistsResponse();
    converted.setExists(response.getExists());
    return converted;
  }

  public static DescribeTableResponseV2 describeTableV2(
      com.lancedb.lance.namespace.model.DescribeTableResponseV2 response) {
    DescribeTableResponseV2 converted = new DescribeTableResponseV2();
    converted.setNamespace(response.getNamespace());
    converted.setName(response.getName());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static RegisterTableResponse registerTable(
      com.lancedb.lance.namespace.model.RegisterTableResponse response) {
    RegisterTableResponse converted = new RegisterTableResponse();
    converted.setNamespace(response.getNamespace());
    converted.setName(response.getName());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static TableExistsResponse tableExists(
      com.lancedb.lance.namespace.model.TableExistsResponse response) {
    TableExistsResponse converted = new TableExistsResponse();
    converted.setExists(response.getExists());
    return converted;
  }

  public static DropTableResponse dropTable(
      com.lancedb.lance.namespace.model.DropTableResponse response) {
    DropTableResponse converted = new DropTableResponse();
    converted.setName(response.getName());
    converted.setNamespace(response.getNamespace());
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    converted.setTransactionId(response.getTransactionId());
    return converted;
  }

  public static DeregisterTableResponse deregisterTable(
      com.lancedb.lance.namespace.model.DeregisterTableResponse response) {
    DeregisterTableResponse converted = new DeregisterTableResponse();
    converted.setName(response.getName());
    converted.setNamespace(response.getNamespace());
    converted.setLocation(response.getLocation());
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static DescribeTransactionResponse describeTransaction(
      com.lancedb.lance.namespace.model.DescribeTransactionResponse response) {
    DescribeTransactionResponse converted = new DescribeTransactionResponse();
    converted.setId(response.getId());
    converted.setStatus(TransactionStatus.valueOf(response.getStatus().name()));
    converted.setProperties(response.getProperties());
    return converted;
  }

  public static AlterTransactionResponse alterTransaction(
      com.lancedb.lance.namespace.model.AlterTransactionResponse response) {
    AlterTransactionResponse converted = new AlterTransactionResponse();
    converted.setId(response.getId());
    converted.setStatus(TransactionStatus.valueOf(response.getStatus().name()));
    return converted;
  }
}
