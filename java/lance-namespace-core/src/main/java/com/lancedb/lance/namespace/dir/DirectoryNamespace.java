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
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.OpenDalUtil;
import com.lancedb.lance.namespace.util.ValidationUtil;

import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.opendal.Entry;
import org.apache.opendal.ListOptions;
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

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.config = new DirectoryNamespaceConfig(configProperties);
    this.allocator = allocator;
    this.operator =
        OpenDalUtil.initializeOperator(this.config.getRoot(), this.config.getStorageOptions());
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

    String tablePath = tableFullPath(tableName);
    ValidationUtil.checkArgument(
        request.getLocation() == null
            || OpenDalUtil.stripTrailingSlash(request.getLocation()).equals(tablePath),
        "Cannot create table %s at location %s, must be at location %s",
        tableName,
        request.getLocation(),
        tablePath);

    // Create the Lance dataset
    Dataset.create(allocator, tablePath, schema, writeParams);
    CreateTableResponse response = new CreateTableResponse();
    response.setLocation(tablePath);
    response.setVersion(1L);
    response.setStorageOptions(config.getStorageOptions());
    return response;
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    String tableName = tableNameFromId(request.getId());
    String tablePath = tableFullPath(tableName);

    LOG.debug("Dropping table {} at path {}", tableName, tablePath);

    try {
      Dataset.drop(tablePath, config.getStorageOptions());
      DropTableResponse response = new DropTableResponse();
      response.setLocation(tablePath);
      response.setId(request.getId());
      return response;
    } catch (Exception e) {
      throw LanceNamespaceException.serverError(
          "Failed to drop table: " + tableName,
          "TABLE_DROP_ERROR",
          tableName,
          "An error occurred while attempting to drop the table: " + e.getMessage());
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    validateRootNamespaceId(request.getId());

    Set<String> tables = new HashSet<>();
    List<Entry> entries = operator.list("", ListOptions.builder().recursive(false).build());

    for (Entry entry : entries) {

      String path = OpenDalUtil.stripTrailingSlash(entry.getPath());
      if (!path.contains(".lance")) {
        continue;
      }

      String tableName = path.substring(0, path.length() - 6);
      try {
        String versionsPath = tableVersionsPath(tableName);
        List<Entry> versionEntries =
            operator.list(versionsPath, ListOptions.builder().limit(1).build());
        if (!versionEntries.isEmpty()) {
          tables.add(tableName);
        }
      } catch (Exception e) {
        LOG.debug("Invalid Lance table directory {}, skipping", path);
      }
    }

    ListTablesResponse response = new ListTablesResponse();
    response.setTables(tables);
    return response;
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    String tableName = tableNameFromId(request.getId());

    LOG.debug("Checking if table {} exists", tableName);

    String versionsPath = tableVersionsPath(tableName);
    List<Entry> versionEntries =
        operator.list(versionsPath, ListOptions.builder().limit(1).build());
    if (versionEntries.isEmpty()) {
      throw LanceNamespaceException.notFound(
          "Table does not exist: " + tableName,
          "TABLE_NOT_FOUND",
          tableName,
          "The requested table was not found in the namespace");
    }
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    String tableName = tableNameFromId(request.getId());
    String tablePath = tableFullPath(tableName);

    LOG.debug("Describing table {} at path {}", tableName, tablePath);

    try {
      String versionsPath = tableVersionsPath(tableName);
      List<Entry> versionEntries =
          operator.list(versionsPath, ListOptions.builder().limit(1).build());
      if (versionEntries.isEmpty()) {
        throw LanceNamespaceException.notFound(
            "Table does not exist: " + tableName,
            "TABLE_NOT_FOUND",
            tableName,
            "The requested table was not found in the namespace");
      }
    } catch (Exception e) {
      throw LanceNamespaceException.notFound(
          "Table does not exist: " + tableName,
          "TABLE_NOT_FOUND",
          tableName,
          "The requested table was not found in the namespace: " + e.getMessage());
    }

    DescribeTableResponse response = new DescribeTableResponse();
    response.setLocation(tablePath);
    response.setStorageOptions(config.getStorageOptions());
    return response;
  }

  private void validateRootNamespaceId(List<String> id) {
    ValidationUtil.checkArgument(
        id == null || id.isEmpty(),
        String.format(
            "Directory namespace only supports root namespace operations, "
                + "but got namespace ID: %s. Expected empty ID.",
            id));
  }

  private String tableNameFromId(List<String> id) {
    ValidationUtil.checkArgument(
        id != null && !id.isEmpty(), "Directory namespace table ID cannot be empty");

    ValidationUtil.checkArgument(
        id.size() == 1,
        "Directory namespace only supports single-level table IDs, but got: %s",
        id);

    return id.get(0);
  }

  private String tableFullPath(String tableName) {
    return String.format("%s/%s.lance", config.getRoot(), tableName);
  }

  private String tableVersionsPath(String tableName) {
    return String.format("%s.lance/_versions/", tableName);
  }

  @Override
  public void close() throws IOException {
    operator.close();
  }
}
