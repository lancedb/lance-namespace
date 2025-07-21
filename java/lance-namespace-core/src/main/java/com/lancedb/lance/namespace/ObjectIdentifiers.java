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
import com.lancedb.lance.namespace.model.CountTableRowsRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateTableIndexRequest;
import com.lancedb.lance.namespace.model.DeleteFromTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeTableIndexStatsRequest;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableRequestV2;
import com.lancedb.lance.namespace.model.DescribeTransactionRequest;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListTableIndicesRequest;
import com.lancedb.lance.namespace.model.MergeInsertIntoTableRequest;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.QueryTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.model.UpdateTableRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectIdentifiers {

  private ObjectIdentifiers() {}

  public static String stringFrom(CreateNamespaceRequest request, String delimiter) {
    if (request.getParent() == null || request.getParent().isEmpty()) {
      return delimiter;
    }

    StringBuilder builder = new StringBuilder();
    for (String part : request.getParent()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.delete(builder.length() - delimiter.length(), builder.length());
    return builder.toString();
  }

  public static List<String> listFrom(CreateNamespaceRequest request) {
    if (request.getParent() == null) {
      return Collections.emptyList();
    }
    return Collections.unmodifiableList(request.getParent());
  }

  public static String stringFrom(DescribeNamespaceRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getParent() != null) {
      for (String part : request.getParent()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DescribeNamespaceRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getParent() != null) {
      identifier.addAll(request.getParent());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DropNamespaceRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getParent() != null) {
      for (String part : request.getParent()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DropNamespaceRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getParent() != null) {
      identifier.addAll(request.getParent());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(NamespaceExistsRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getParent() != null) {
      for (String part : request.getParent()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(NamespaceExistsRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getParent() != null) {
      identifier.addAll(request.getParent());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DescribeTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    for (String part : request.getNamespace()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DescribeTableRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.addAll(request.getNamespace());
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DescribeTableRequestV2 request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    for (String part : request.getNamespace()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DescribeTableRequestV2 request) {
    List<String> identifier = new ArrayList<>();
    identifier.addAll(request.getNamespace());
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(CountTableRowsRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(CountTableRowsRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(QueryTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(QueryTableRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(CreateTableIndexRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(CreateTableIndexRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(RegisterTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    for (String part : request.getNamespace()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(RegisterTableRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.addAll(request.getNamespace());
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DropTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    for (String part : request.getNamespace()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DropTableRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.addAll(request.getNamespace());
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DeregisterTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    for (String part : request.getNamespace()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DeregisterTableRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.addAll(request.getNamespace());
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(TableExistsRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    for (String part : request.getNamespace()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(TableExistsRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.addAll(request.getNamespace());
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(ListNamespacesRequest request, String delimiter) {
    if (request.getParent() == null || request.getParent().isEmpty()) {
      return delimiter;
    }

    StringBuilder builder = new StringBuilder();
    for (String part : request.getParent()) {
      builder.append(part);
      builder.append(delimiter);
    }
    builder.delete(builder.length() - delimiter.length(), builder.length());
    return builder.toString();
  }

  public static List<String> listFrom(ListNamespacesRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getParent() != null) {
      identifier.addAll(request.getParent());
    }
    return identifier;
  }

  public static String stringFrom(ListTableIndicesRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(ListTableIndicesRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DescribeTransactionRequest request) {
    return request.getId();
  }

  public static List<String> listFrom(DescribeTransactionRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.add(request.getId());
    return identifier;
  }

  public static String stringFrom(AlterTransactionRequest request) {
    return request.getId();
  }

  public static List<String> listFrom(AlterTransactionRequest request) {
    List<String> identifier = new ArrayList<>();
    identifier.add(request.getId());
    return identifier;
  }

  public static String stringFrom(DescribeTableIndexStatsRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DescribeTableIndexStatsRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(UpdateTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(UpdateTableRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(DeleteFromTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(DeleteFromTableRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }

  public static String stringFrom(MergeInsertIntoTableRequest request, String delimiter) {
    StringBuilder builder = new StringBuilder();
    if (request.getNamespace() != null) {
      for (String part : request.getNamespace()) {
        builder.append(part);
        builder.append(delimiter);
      }
    }
    builder.append(request.getName());
    return builder.toString();
  }

  public static List<String> listFrom(MergeInsertIntoTableRequest request) {
    List<String> identifier = new ArrayList<>();
    if (request.getNamespace() != null) {
      identifier.addAll(request.getNamespace());
    }
    identifier.add(request.getName());
    return identifier;
  }
}
