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

/**
 * Defines the capabilities of a namespace implementation for testing purposes. This class is used
 * by the NamespaceTCK to determine which tests should be run for a given namespace implementation.
 */
public class NamespaceCapabilities {

  // Namespace hierarchy support
  private boolean supportsNamespaceOperations; // Can create/drop namespaces at all
  private boolean supportsNestedNamespaces; // Can create namespaces within namespaces (Hive3:
  // catalog->database)
  private boolean
      supportsRootNamespaceOnly; // Only supports namespace ops at root (Glue: only databases)

  // Table support
  private boolean
      supportsTablesInRootNamespace; // Can create tables directly in root (DirectoryNamespace)
  private boolean supportsMultiLevelTableIds; // Supports multi-part table identifiers

  // Delete behaviors
  private boolean supportsCascadeDelete; // Supports CASCADE behavior for drops
  private boolean supportsRestrictDelete; // Supports RESTRICT behavior for drops

  // Additional features
  private boolean supportsTableVersioning; // Supports table version tracking
  private boolean supportsPagination; // Supports pagination in list operations
  private boolean supportsRegisterDeregister; // Supports register/deregister operations

  // Private constructor to force use of builder
  private NamespaceCapabilities() {}

  // Getters
  public boolean supportsNamespaceOperations() {
    return supportsNamespaceOperations;
  }

  public boolean supportsNestedNamespaces() {
    return supportsNestedNamespaces;
  }

  public boolean supportsRootNamespaceOnly() {
    return supportsRootNamespaceOnly;
  }

  public boolean supportsTablesInRootNamespace() {
    return supportsTablesInRootNamespace;
  }

  public boolean supportsMultiLevelTableIds() {
    return supportsMultiLevelTableIds;
  }

  public boolean supportsCascadeDelete() {
    return supportsCascadeDelete;
  }

  public boolean supportsRestrictDelete() {
    return supportsRestrictDelete;
  }

  public boolean supportsTableVersioning() {
    return supportsTableVersioning;
  }

  public boolean supportsPagination() {
    return supportsPagination;
  }

  public boolean supportsRegisterDeregister() {
    return supportsRegisterDeregister;
  }

  // Builder pattern for easy construction
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final NamespaceCapabilities capabilities = new NamespaceCapabilities();

    public Builder supportsNamespaceOperations(boolean value) {
      capabilities.supportsNamespaceOperations = value;
      return this;
    }

    public Builder supportsNestedNamespaces(boolean value) {
      capabilities.supportsNestedNamespaces = value;
      return this;
    }

    public Builder supportsRootNamespaceOnly(boolean value) {
      capabilities.supportsRootNamespaceOnly = value;
      return this;
    }

    public Builder supportsTablesInRootNamespace(boolean value) {
      capabilities.supportsTablesInRootNamespace = value;
      return this;
    }

    public Builder supportsMultiLevelTableIds(boolean value) {
      capabilities.supportsMultiLevelTableIds = value;
      return this;
    }

    public Builder supportsCascadeDelete(boolean value) {
      capabilities.supportsCascadeDelete = value;
      return this;
    }

    public Builder supportsRestrictDelete(boolean value) {
      capabilities.supportsRestrictDelete = value;
      return this;
    }

    public Builder supportsTableVersioning(boolean value) {
      capabilities.supportsTableVersioning = value;
      return this;
    }

    public Builder supportsPagination(boolean value) {
      capabilities.supportsPagination = value;
      return this;
    }

    public Builder supportsRegisterDeregister(boolean value) {
      capabilities.supportsRegisterDeregister = value;
      return this;
    }

    public NamespaceCapabilities build() {
      // Validate consistency of capabilities
      if (capabilities.supportsNestedNamespaces && capabilities.supportsRootNamespaceOnly) {
        throw new IllegalStateException(
            "Cannot support both nested namespaces and root-only namespace operations");
      }

      if (!capabilities.supportsNamespaceOperations
          && (capabilities.supportsNestedNamespaces || capabilities.supportsRootNamespaceOnly)) {
        throw new IllegalStateException(
            "Cannot specify namespace hierarchy support without namespace operations support");
      }

      return capabilities;
    }
  }
}
