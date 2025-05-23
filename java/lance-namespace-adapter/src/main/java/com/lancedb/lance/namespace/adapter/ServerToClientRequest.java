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

import com.lancedb.lance.namespace.model.AlterTransactionAction;
import com.lancedb.lance.namespace.model.AlterTransactionRequest;
import com.lancedb.lance.namespace.model.AlterTransactionSetProperty;
import com.lancedb.lance.namespace.model.AlterTransactionSetStatus;
import com.lancedb.lance.namespace.model.AlterTransactionUnsetProperty;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.GetNamespaceRequest;
import com.lancedb.lance.namespace.model.GetTableRequest;
import com.lancedb.lance.namespace.model.GetTransactionRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.SetPropertyMode;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.TransactionStatus;
import com.lancedb.lance.namespace.model.UnsetPropertyMode;

public class ServerToClientRequest {

  public static CreateNamespaceRequest createNamespace(
      com.lancedb.lance.namespace.server.springboot.model.CreateNamespaceRequest request) {
    CreateNamespaceRequest converted = new CreateNamespaceRequest();
    converted.setMode(CreateNamespaceRequest.ModeEnum.valueOf(request.getMode().name()));
    converted.setParent(request.getParent());
    converted.setOptions(request.getOptions());
    converted.setName(request.getName());
    return converted;
  }

  public static DropNamespaceRequest dropNamespace(
      com.lancedb.lance.namespace.server.springboot.model.DropNamespaceRequest request) {
    DropNamespaceRequest converted = new DropNamespaceRequest();
    converted.setName(request.getName());
    converted.setParent(request.getParent());
    converted.setMode(DropNamespaceRequest.ModeEnum.valueOf(request.getMode().name()));
    converted.setBehavior(DropNamespaceRequest.BehaviorEnum.valueOf(request.getBehavior().name()));
    return converted;
  }

  public static GetNamespaceRequest getNamespace(
      com.lancedb.lance.namespace.server.springboot.model.GetNamespaceRequest request) {
    GetNamespaceRequest converted = new GetNamespaceRequest();
    converted.setName(request.getName());
    return converted;
  }

  public static ListNamespacesRequest listNamespaces(
      com.lancedb.lance.namespace.server.springboot.model.ListNamespacesRequest request) {
    ListNamespacesRequest converted = new ListNamespacesRequest();
    converted.setParent(request.getParent());
    return converted;
  }

  public static NamespaceExistsRequest namespaceExists(
      com.lancedb.lance.namespace.server.springboot.model.NamespaceExistsRequest request) {
    NamespaceExistsRequest converted = new NamespaceExistsRequest();
    converted.setName(request.getName());
    return converted;
  }

  public static GetTableRequest getTable(
      com.lancedb.lance.namespace.server.springboot.model.GetTableRequest request) {
    GetTableRequest converted = new GetTableRequest();
    converted.setNamespace(request.getNamespace());
    converted.setName(request.getName());
    return converted;
  }

  public static RegisterTableRequest registerTable(
      com.lancedb.lance.namespace.server.springboot.model.RegisterTableRequest request) {
    RegisterTableRequest converted = new RegisterTableRequest();
    converted.setNamespace(request.getNamespace());
    converted.setName(request.getName());
    converted.setLocation(request.getLocation());
    return converted;
  }

  public static TableExistsRequest tableExists(
      com.lancedb.lance.namespace.server.springboot.model.TableExistsRequest request) {
    TableExistsRequest converted = new TableExistsRequest();
    converted.setNamespace(request.getNamespace());
    converted.setName(request.getName());
    return converted;
  }

  public static GetTransactionRequest getTransaction(
      com.lancedb.lance.namespace.server.springboot.model.GetTransactionRequest request) {
    GetTransactionRequest converted = new GetTransactionRequest();
    converted.setId(request.getId());
    return converted;
  }

  public static AlterTransactionRequest alterTransaction(
      com.lancedb.lance.namespace.server.springboot.model.AlterTransactionRequest request) {
    AlterTransactionRequest converted = new AlterTransactionRequest();
    converted.setActions(
        request.getActions().stream()
            .map(ServerToClientRequest::transactionAction)
            .collect(java.util.stream.Collectors.toList()));
    return converted;
  }

  public static AlterTransactionAction transactionAction(
      com.lancedb.lance.namespace.server.springboot.model.AlterTransactionAction action) {

    AlterTransactionAction converted = new AlterTransactionAction();

    if (action.getSetStatusAction() != null) {
      AlterTransactionSetStatus setStatus = new AlterTransactionSetStatus();
      setStatus.setStatus(
          TransactionStatus.valueOf(action.getSetStatusAction().getStatus().name()));
      converted.setSetStatusAction(setStatus);
      return converted;
    }

    if (action.getSetPropertyAction() != null) {
      AlterTransactionSetProperty setProperty = new AlterTransactionSetProperty();
      setProperty.setKey(action.getSetPropertyAction().getKey());
      setProperty.setValue(action.getSetPropertyAction().getValue());
      setProperty.setMode(SetPropertyMode.valueOf(action.getSetPropertyAction().getMode().name()));
      converted.setSetPropertyAction(setProperty);
      return converted;
    }

    if (action.getUnsetPropertyAction() != null) {
      AlterTransactionUnsetProperty unsetProperty = new AlterTransactionUnsetProperty();
      unsetProperty.setKey(action.getUnsetPropertyAction().getKey());
      unsetProperty.setMode(
          UnsetPropertyMode.valueOf(action.getUnsetPropertyAction().getMode().name()));
      converted.setUnsetPropertyAction(unsetProperty);
      return converted;
    }

    throw new IllegalArgumentException("Unexpected action: " + action);
  }
}
