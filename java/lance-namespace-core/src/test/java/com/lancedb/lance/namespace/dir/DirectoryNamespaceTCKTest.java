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

import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.NamespaceCapabilities;
import com.lancedb.lance.namespace.NamespaceTCK;

import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TCK test implementation for DirectoryNamespace.
 *
 * <p>DirectoryNamespace is a simple file-based implementation that: - Does not support namespace
 * operations - Supports tables directly in the root directory - Only supports single-level table
 * identifiers
 */
public class DirectoryNamespaceTCKTest extends NamespaceTCK {

  @TempDir private Path tempDir;

  private DirectoryNamespace directoryNamespace;

  @Override
  protected LanceNamespace createNamespace() throws Exception {
    directoryNamespace = new DirectoryNamespace();
    Map<String, String> properties = new HashMap<>();
    properties.put("root", tempDir.toString());
    directoryNamespace.initialize(properties, allocator);
    return directoryNamespace;
  }

  @Override
  protected void cleanupNamespace() throws Exception {
    // DirectoryNamespace doesn't need explicit cleanup
    // TempDir will be cleaned up automatically by JUnit
  }

  @Override
  protected String getTempDirectory() {
    return tempDir.toString();
  }

  @Override
  protected NamespaceCapabilities getCapabilities() {
    return NamespaceCapabilities.builder()
        .supportsNamespaceOperations(false) // No namespace operations
        .supportsTablesInRootNamespace(true) // Tables directly in root
        .supportsMultiLevelTableIds(false) // Single-level table IDs only
        .supportsCascadeDelete(false) // No cascade support
        .supportsRestrictDelete(false) // No restrict support
        .supportsTableVersioning(true) // Lance supports versioning
        .supportsPagination(false) // No pagination support
        .supportsRegisterDeregister(false) // No register/deregister
        .build();
  }

  @Override
  protected List<String> getTestNamespaceId() {
    // DirectoryNamespace doesn't support namespaces
    return new ArrayList<>();
  }

  @Override
  protected List<String> getTestTableId(String tableName) {
    // DirectoryNamespace only supports single-level table identifiers
    List<String> tableId = new ArrayList<>();
    tableId.add(tableName);
    return tableId;
  }
}
