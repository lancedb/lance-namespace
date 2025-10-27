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
import com.lancedb.lance.namespace.model.CreateEmptyTableRequest;
import com.lancedb.lance.namespace.model.CreateEmptyTableResponse;
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
import com.lancedb.lance.namespace.model.JsonArrowSchema;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import com.lancedb.lance.namespace.model.TableExistsRequest;
import com.lancedb.lance.namespace.util.ArrowIpcUtil;
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
  public String namespaceId() {
    return String.format("DirectoryNamespace { root: \"%s\" }", this.config.getRoot());
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

    // Validate that requestData is a valid Arrow IPC stream
    ValidationUtil.checkNotNull(
        requestData, "Request data (Arrow IPC stream) is required for createTable");
    ValidationUtil.checkArgument(
        requestData.length > 0, "Request data (Arrow IPC stream) cannot be empty");

    // Extract schema from Arrow IPC stream
    JsonArrowSchema jsonSchema;
    try {
      jsonSchema = ArrowIpcUtil.extractSchemaFromIpc(requestData);
    } catch (IOException e) {
      throw LanceNamespaceException.badRequest(
          "Invalid Arrow IPC stream: " + e.getMessage(),
          "INVALID_ARROW_IPC",
          tableName,
          "Failed to extract schema from Arrow IPC stream");
    }
    Schema schema = JsonArrowSchemaConverter.convertToArrowSchema(jsonSchema);

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

    // Create the Lance dataset with data
    Dataset.create(allocator, tablePath, schema, writeParams);
    CreateTableResponse response = new CreateTableResponse();
    response.setLocation(OpenDalUtil.denormalizeUri(tablePath));
    response.setVersion(1L);
    response.setStorageOptions(config.getStorageOptions());
    return response;
  }

  @Override
  public CreateEmptyTableResponse createEmptyTable(CreateEmptyTableRequest request) {
    String tableName = tableNameFromId(request.getId());
    String tablePath = tableFullPath(tableName);

    ValidationUtil.checkArgument(
        request.getLocation() == null
            || OpenDalUtil.stripTrailingSlash(request.getLocation()).equals(tablePath),
        "Cannot create table %s at location %s, must be at location %s",
        tableName,
        request.getLocation(),
        tablePath);

    // Check if table already exists
    String versionsPath = tableVersionsPath(tableName);
    List<Entry> versionEntries =
        operator.list(versionsPath, ListOptions.builder().limit(1).build());
    if (!versionEntries.isEmpty()) {
      throw LanceNamespaceException.conflict(
          "Table already exists: " + tableName,
          "TABLE_ALREADY_EXISTS",
          tableName,
          "The table already exists in the namespace");
    }

    // Create .lance-reserved file to mark table existence - use relative path for operator
    String reservedFilePath = tableName + ".lance/.lance-reserved";
    try {
      operator.write(reservedFilePath, new byte[0]);
    } catch (Exception e) {
      throw LanceNamespaceException.serverError(
          "Failed to create empty table: " + tableName,
          "CREATE_EMPTY_TABLE_FAILED",
          tableName,
          "Failed to create .lance-reserved file");
    }

    CreateEmptyTableResponse response = new CreateEmptyTableResponse();
    response.setLocation(OpenDalUtil.denormalizeUri(tablePath));
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
      response.setLocation(OpenDalUtil.denormalizeUri(tablePath));
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

      // Check if it's a valid Lance dataset with versions
      boolean isTable = false;
      try {
        String versionsPath = tableVersionsPath(tableName);
        List<Entry> versionEntries =
            operator.list(versionsPath, ListOptions.builder().limit(1).build());
        if (!versionEntries.isEmpty()) {
          isTable = true;
        }
      } catch (Exception e) {
        // No versions directory, check for .lance-reserved file
      }

      // Check for .lance-reserved file (empty table)
      if (!isTable) {
        try {
          String reservedFilePath = tableName + ".lance/.lance-reserved";
          operator.stat(reservedFilePath);
          isTable = true;
        } catch (Exception e) {
          // No .lance-reserved file either
        }
      }

      if (isTable) {
        tables.add(tableName);
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

    // Check if table has versions (actual Lance table)
    String versionsPath = tableVersionsPath(tableName);
    List<Entry> versionEntries =
        operator.list(versionsPath, ListOptions.builder().limit(1).build());
    if (!versionEntries.isEmpty()) {
      return; // Table exists with data
    }

    // Check if .lance-reserved file exists (empty table created with createEmptyTable)
    String reservedFilePath = tableName + ".lance/.lance-reserved";
    try {
      operator.stat(reservedFilePath);
      return; // Table exists as empty table
    } catch (Exception e) {
      // File doesn't exist, continue to throw not found
    }

    throw LanceNamespaceException.notFound(
        "Table does not exist: " + tableName,
        "TABLE_NOT_FOUND",
        tableName,
        "The requested table was not found in the namespace");
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    String tableName = tableNameFromId(request.getId());
    String tablePath = tableFullPath(tableName);

    LOG.debug("Describing table {} at path {}", tableName, tablePath);

    // Check if table exists - either as Lance dataset or with .lance-reserved file
    boolean tableExists = false;

    // Check if table has versions (actual Lance table)
    try {
      String versionsPath = tableVersionsPath(tableName);
      List<Entry> versionEntries =
          operator.list(versionsPath, ListOptions.builder().limit(1).build());
      if (!versionEntries.isEmpty()) {
        tableExists = true;
      }
    } catch (Exception e) {
      // No versions directory, check for .lance-reserved file
    }

    // Check for .lance-reserved file (empty table)
    if (!tableExists) {
      try {
        String reservedFilePath = tableName + ".lance/.lance-reserved";
        operator.stat(reservedFilePath);
        tableExists = true;
      } catch (Exception e) {
        // No .lance-reserved file either
      }
    }

    if (!tableExists) {
      throw LanceNamespaceException.notFound(
          "Table does not exist: " + tableName,
          "TABLE_NOT_FOUND",
          tableName,
          "The requested table was not found in the namespace");
    }

    DescribeTableResponse response = new DescribeTableResponse();
    response.setLocation(OpenDalUtil.denormalizeUri(tablePath));
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
