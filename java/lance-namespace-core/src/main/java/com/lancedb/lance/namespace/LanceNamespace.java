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
package com.lancedb.lance.namespace;

import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.AlterTransactionResponse;
import com.lancedb.lance.namespace.model.CountRowsRequest;
import com.lancedb.lance.namespace.model.CreateIndexRequest;
import com.lancedb.lance.namespace.model.CreateIndexResponse;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DescribeTransactionResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.IndexListRequest;
import com.lancedb.lance.namespace.model.IndexListResponse;
import com.lancedb.lance.namespace.model.InsertTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsResponse;
import com.lancedb.lance.namespace.model.QueryRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsResponse;

/** TODO: add documentation */
public interface LanceNamespace {

  ListNamespacesResponse listNamespaces(ListNamespacesRequest request);

  DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request);

  CreateNamespaceResponse createNamespace(CreateNamespaceRequest request);

  DropNamespaceResponse dropNamespace(DropNamespaceRequest request);

  NamespaceExistsResponse namespaceExists(NamespaceExistsRequest request);

  DescribeTableResponse describeTable(DescribeTableRequest request);

  Long countRows(CountRowsRequest request);

  CreateTableResponse createTable(String tableName, byte[] arrowIpcData);

  InsertTableResponse insertTable(String tableName, byte[] arrowIpcData, String mode);

  byte[] queryTable(QueryRequest request);

  CreateIndexResponse createIndex(CreateIndexRequest request);

  CreateIndexResponse createScalarIndex(CreateIndexRequest request);

  IndexListResponse listIndices(IndexListRequest request);

  RegisterTableResponse registerTable(RegisterTableRequest request);

  TableExistsResponse tableExists(TableExistsRequest request);

  DropTableResponse dropTable(DropTableRequest request);

  DeregisterTableResponse deregisterTable(DeregisterTableRequest request);

  DescribeTransactionResponse describeTransaction(DescribeTransactionRequest request);

  AlterTransactionResponse alterTransaction(AlterTransactionRequest request);
}
