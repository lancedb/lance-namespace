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

import com.google.common.base.Preconditions;
import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.model.DeregisterTableRequest;
import com.lancedb.lance.namespace.model.DeregisterTableResponse;
import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;
import com.lancedb.lance.namespace.model.RegisterTableRequest;
import com.lancedb.lance.namespace.model.RegisterTableResponse;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.model.CreateNamespaceResponse;
import com.lancedb.lance.namespace.model.DescribeNamespaceRequest;
import com.lancedb.lance.namespace.model.DescribeNamespaceResponse;
import com.lancedb.lance.namespace.model.DropNamespaceRequest;
import com.lancedb.lance.namespace.model.DropNamespaceResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.model.NamespaceExistsRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.opendal.Entry;
import org.apache.opendal.Metadata;
import org.apache.opendal.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryNamespace implements LanceNamespace {
  private static final Logger LOG = LoggerFactory.getLogger(DirectoryNamespace.class);

  private DirectoryNamespaceConfig config;
  private Operator operator;

  @Override
  public void initialize(Map<String, String> configProperties) {
    this.config = new DirectoryNamespaceConfig(configProperties);
    String root = this.config.getRoot();
    
    // Use current directory if root is not specified
    if (root == null) {
      root = System.getProperty("user.dir");
    }

    String namespacePath = parsePath(root);
    this.operator = initializeOperator(namespacePath, this.config.getOpendalConfig());
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables and does not support creating namespaces");
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables and does not support listing namespaces");
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables and does not support describing namespaces");
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables and does not support dropping namespaces");
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    throw new UnsupportedOperationException(
        "Directory namespace only contains a flat list of tables and does not support namespace existence checks");
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    String tableName = request.getId().get(0);
    Preconditions.checkNotNull(tableName, "table name is required");

    String tablePath = getTablePath(tableName);
    LOG.debug("Registering table {} at path {}", tableName, tablePath);

    try {
      operator.createDir(tablePath);
      RegisterTableResponse response = new RegisterTableResponse();
      response.setTable(tableName);
      response.setTableUri(tablePath);
      return response;
    } catch (Exception e) {
      throw new LanceNamespaceException("Failed to register table: " + tableName, e);
    }
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    String tableName = request.getTable();
    Preconditions.checkNotNull(tableName, "table name is required");

    String tablePath = getTablePath(tableName);
    LOG.debug("Deregistering table {} at path {}", tableName, tablePath);

    try {
      operator.removeAll(tablePath);
      DeregisterTableResponse response = new DeregisterTableResponse();
      response.setTable(tableName);
      return response;
    } catch (Exception e) {
      throw new LanceNamespaceException("Failed to deregister table: " + tableName, e);
    }
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    LOG.debug("Listing tables in namespace {}", namespacePath);

    try {
      List<String> tables = new ArrayList<>();
      List<Entry> entries = operator.list("");

      for (Entry entry : entries) {
        Metadata metadata = operator.stat(entry.getPath());
        if (metadata.getMode() == Metadata.EntryMode.DIR) {
          String tableName = entry.getPath();
          if (tableName.endsWith("/")) {
            tableName = tableName.substring(0, tableName.length() - 1);
          }
          tables.add(tableName);
        }
      }

      ListTablesResponse response = new ListTablesResponse();
      response.setTables(tables);
      return response;
    } catch (Exception e) {
      throw new LanceNamespaceException("Failed to list tables", e);
    }
  }

  private String parsePath(String path) {
    try {
      URI uri = new URI(path);
      if (uri.getScheme() != null) {
        return path;
      }
    } catch (URISyntaxException e) {
      // Not a URI, treat as file path
    }

    // Handle absolute and relative POSIX paths
    if (path.startsWith("/")) {
      return "file://" + path;
    } else {
      String currentDir = System.getProperty("user.dir");
      return "file://" + Paths.get(currentDir, path).toAbsolutePath().normalize();
    }
  }

  private Operator initializeOperator(String path, Map<String, String> opendalConfig) {
    URI uri = new URI(path);
    String scheme = normalizeScheme(uri.getScheme());

    Map<String, String> config = new HashMap<>(opendalConfig);

    // Set basic config based on scheme
    if ("fs".equals(scheme)) {
      config.put("root", uri.getPath());
    } else if (uri.getHost() != null) {
      // For cloud storage, set bucket/container and root
      if ("s3".equals(scheme)) {
        config.put("bucket", uri.getHost());
      } else if ("gcs".equals(scheme)) {
        config.put("bucket", uri.getHost());
      } else if ("azblob".equals(scheme)) {
        config.put("container", uri.getHost());
      } else {
        // For other schemes, try to set a generic "bucket" config
        config.put("bucket", uri.getHost());
      }

      if (uri.getPath() != null && !uri.getPath().isEmpty()) {
        config.put("root", uri.getPath());
      }
    }

    // Create operator with blocking layer for synchronous operations
    return Operator.of(scheme, config);
  }

  private String normalizeScheme(String scheme) {
    if (scheme == null) {
      return "fs";
    }
    
    // Handle scheme aliases
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

  private String getTablePath(String tableName) {
    return tableName + "/";
  }
}