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
package com.lancedb.lance.namespace.dir;

import com.lancedb.lance.Dataset;
import com.lancedb.lance.WriteParams;
import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.CreateTableRequest;
import com.lancedb.lance.namespace.model.CreateTableResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.ValidationUtil;

import com.google.common.collect.ImmutableMap;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.opendal.Entry;
import org.apache.opendal.Metadata;
import org.apache.opendal.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DirectoryNamespace implements LanceNamespace, Closeable {
  private static final Logger LOG = LoggerFactory.getLogger(DirectoryNamespace.class);

  private DirectoryNamespaceConfig config;
  private Operator operator;
  private BufferAllocator allocator;
  private String namespacePath;

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.config = new DirectoryNamespaceConfig(configProperties);
    this.allocator = allocator;
    this.operator = initializeOperator(this.config.getRoot());
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables");
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables");
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables");
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables");
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables");
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    String tableName = tableNameFromId(request.getId());
    ValidationUtil.checkNotNull(request.getSchema(), "Schema is required in CreateTableRequest");
    Schema schema = JsonArrowSchemaConverter.convertToArrowSchema(request.getSchema());

    WriteParams writeParams =
        new WriteParams.Builder()
            .withMode(WriteParams.WriteMode.CREATE)
            .withStorageOptions(config.getStorageOptions())
            .build();

    String tablePath = getTablePath(tableName);
    ValidationUtil.checkArgument(
        request.getLocation() == null || request.getLocation().equals(tablePath),
        "Cannot create table {} at location {}, must be at location {}",
        tableName,
        request.getLocation(),
        tablePath);

    // Create the Lance dataset
    Dataset.create(allocator, tablePath, schema, writeParams);
    CreateTableResponse response = new CreateTableResponse();
    response.setLocation(tablePath);
    response.setVersion(1L);
    return response;
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    String tableName = tableNameFromId(request.getId());
    String tablePath = getTablePath(tableName);

    LOG.debug("Dropping table {} at path {}", tableName, tablePath);

    try {
      // Use Lance Dataset.drop to remove the dataset
      Dataset.drop(tablePath, config.getStorageOptions());

      DropTableResponse response = new DropTableResponse();
      return response;
    } catch (Exception e) {
      throw new LanceNamespaceException("Failed to drop table: " + tableName, e);
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    Set<String> tables = new HashSet<>();
    List<Entry> entries = operator.list("");

    for (Entry entry : entries) {
      Metadata metadata = operator.stat(entry.getPath());
      if (metadata.getMode() == Metadata.EntryMode.DIR) {
        String tableName = entry.getPath();
        if (tableName.endsWith("/")) {
          tableName = tableName.substring(0, tableName.length() - 1);
        }

        // Check if it's a Lance dataset by looking for _versions directory
        try {
          String versionsPath = getTableVersionsPath(tableName);
          Metadata versionsMetadata = operator.stat(versionsPath);
          if (versionsMetadata.getMode() == Metadata.EntryMode.DIR) {
            tables.add(tableName);
          }
        } catch (Exception e) {
          // If _versions doesn't exist or error accessing it, skip this directory
          LOG.debug("Directory {} does not contain _versions, skipping", tableName);
        }
      }
    }

    ListTablesResponse response = new ListTablesResponse();
    response.setTables(tables);
    return response;
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    String tableName = tableNameFromId(request.getId());
    String tablePath = getTablePath(tableName);

    LOG.debug("Describing table {} at path {}", tableName, tablePath);

    // Check if table exists by verifying _versions directory
    try {
      String versionsPath = getTableVersionsPath(tableName);
      Metadata versionsMetadata = operator.stat(versionsPath);
      if (versionsMetadata.getMode() != Metadata.EntryMode.DIR) {
        throw new LanceNamespaceException("Table does not exist: " + tableName);
      }
    } catch (Exception e) {
      throw new LanceNamespaceException("Table does not exist: " + tableName, e);
    }

    DescribeTableResponse response = new DescribeTableResponse();
    response.setLocation(tablePath);
    return response;
  }

  private Operator initializeOperator(String root) {
    String[] schemeSplit = root.split("://", -1);

    // local file system path
    if (schemeSplit.length < 2) {
      return Operator.of("fs", ImmutableMap.of("root", root));
    }

    String scheme = normalizeScheme(schemeSplit[0]);
    String[] authoritySplit = schemeSplit[1].split("/", 2);
    String authority = authoritySplit[0];
    String path = authoritySplit.length > 1 ? authoritySplit[1] : "";

    switch (scheme) {
      case "s3":
      case "gcs":
        return Operator.of(scheme, ImmutableMap.of("root", path, "bucket", authority));
      case "azblob":
        return Operator.of(scheme, ImmutableMap.of("root", path, "CONTAINER", authority));
      default:
        return Operator.of(scheme, ImmutableMap.of("root", schemeSplit[1]));
    }
  }

  private String normalizeScheme(String scheme) {
    switch (scheme.toLowerCase()) {
      case "s3a":
      case "s3n":
        return "s3";
      case "abfs":
        return "azblob";
      case "file":
        return "fs";
      default:
        return scheme.toLowerCase();
    }
  }

  private String tableNameFromId(List<String> id) {
    ValidationUtil.checkArgument(
        id.size() == 1, "Directory namespace table ID must have only 1 level, but got %s", id);
    return id.get(0);
  }

  private String getTablePath(String tableName) {
    return String.format("%s/%s", config.getRoot(), tableName);
  }

  private String getTableVersionsPath(String tableName) {
    return String.format("%s/_versions/", tableName);
  }

  @Override
  public void close() throws IOException {
    operator.close();
  }
}
