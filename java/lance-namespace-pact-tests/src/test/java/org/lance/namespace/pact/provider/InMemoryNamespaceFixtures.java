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
package org.lance.namespace.pact.provider;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory store that the Pact Provider State hooks use to pre-populate the server with well-known
 * test data before each interaction is verified.
 *
 * <p>Design:
 *
 * <ul>
 *   <li>Thread-safe via {@link ConcurrentHashMap}: each @State hook runs in the test thread.
 *   <li>Immutable snapshots returned by getters (defensive copies) to prevent test pollution.
 *   <li>{@link #reset()} clears all state — must be called at the start of every @State hook.
 *   <li>Table locations stored as {@code namespaceId -> (tableName -> location)}.
 * </ul>
 */
@Component
public class InMemoryNamespaceFixtures {

  /** namespace-id → set-of-table-names. */
  private final Map<String, Set<String>> namespaces = new ConcurrentHashMap<>();

  /** parent-namespace-id → set-of-child-namespace-names. */
  private final Map<String, Set<String>> childNamespaces = new ConcurrentHashMap<>();

  /**
   * namespace-id → (table-name → location). Location is used by DescribeTable and RegisterTable
   * responses.
   */
  private final Map<String, Map<String, String>> tableLocations = new ConcurrentHashMap<>();

  /** Flag to simulate 401: when true, the security filter should reject all requests. */
  private volatile boolean authInvalid = false;

  // ─────────────────────────────────────────────────────────────────────────
  // Lifecycle
  // ─────────────────────────────────────────────────────────────────────────

  /** Wipes all stored namespaces, tables, child namespace mappings, and table locations. */
  public void reset() {
    namespaces.clear();
    childNamespaces.clear();
    tableLocations.clear();
    authInvalid = false;
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Namespace operations
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Creates a namespace with no tables.
   *
   * @param namespaceId the namespace identifier (e.g. {@code "ns_existing"})
   */
  public void createNamespace(String namespaceId) {
    namespaces.putIfAbsent(namespaceId, new LinkedHashSet<>());
  }

  /**
   * Creates a table inside the given namespace (creates namespace too if absent).
   *
   * @param namespaceId the parent namespace identifier
   * @param tableName the table name to add
   */
  public void createTable(String namespaceId, String tableName) {
    namespaces.computeIfAbsent(namespaceId, k -> new LinkedHashSet<>()).add(tableName);
  }

  /**
   * Registers a table with a storage location (creates namespace if absent).
   *
   * @param namespaceId the parent namespace identifier
   * @param tableName the table name to add
   * @param location the storage location (e.g. {@code "s3://example/ns/table"})
   */
  public void registerTableLocation(String namespaceId, String tableName, String location) {
    namespaces.computeIfAbsent(namespaceId, k -> new LinkedHashSet<>()).add(tableName);
    tableLocations
        .computeIfAbsent(namespaceId, k -> new ConcurrentHashMap<>())
        .put(tableName, location);
  }

  /**
   * Registers {@code childName} as a child namespace of {@code parentId}.
   *
   * @param parentId the parent namespace identifier (e.g. {@code "ns_existing"})
   * @param childName the child namespace name (e.g. {@code "child_a"})
   */
  public void createChildNamespace(String parentId, String childName) {
    namespaces.putIfAbsent(parentId, new LinkedHashSet<>());
    childNamespaces.computeIfAbsent(parentId, k -> new LinkedHashSet<>()).add(childName);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Queries — Namespaces
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Returns whether the given namespace exists.
   *
   * @param namespaceId the namespace to check
   * @return {@code true} if the namespace has been created
   */
  public boolean namespaceExists(String namespaceId) {
    return namespaces.containsKey(namespaceId);
  }

  /**
   * Returns an immutable snapshot of child namespace names under the given parent.
   *
   * @param parentId the parent namespace identifier; use {@code "$"} for root
   * @return unmodifiable set of child namespace names, empty if none
   */
  public Set<String> listChildNamespaces(String parentId) {
    if ("$".equals(parentId) || parentId == null) {
      return Collections.unmodifiableSet(new LinkedHashSet<>(namespaces.keySet()));
    }
    Set<String> children = childNamespaces.get(parentId);
    if (children == null) {
      return Collections.emptySet();
    }
    return Collections.unmodifiableSet(new LinkedHashSet<>(children));
  }

  /**
   * Returns an immutable snapshot of table names in the given namespace.
   *
   * @param namespaceId the namespace to query
   * @return unmodifiable set of table names; empty if namespace has no tables
   * @throws IllegalStateException if the namespace does not exist
   */
  public Set<String> listTables(String namespaceId) {
    Set<String> tables = namespaces.get(namespaceId);
    if (tables == null) {
      throw new IllegalStateException("Namespace not found in fixtures: " + namespaceId);
    }
    return Collections.unmodifiableSet(new LinkedHashSet<>(tables));
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Queries — Tables
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Returns whether the given table exists in the given namespace.
   *
   * @param namespaceId the namespace identifier
   * @param tableName the table name
   * @return {@code true} if the table has been created or registered
   */
  public boolean tableExists(String namespaceId, String tableName) {
    Set<String> tables = namespaces.get(namespaceId);
    return tables != null && tables.contains(tableName);
  }

  /**
   * Returns the storage location for the given table, if known.
   *
   * @param namespaceId the namespace identifier
   * @param tableName the table name
   * @return an {@link Optional} containing the location, or empty if not registered
   */
  public Optional<String> getTableLocation(String namespaceId, String tableName) {
    Map<String, String> locations = tableLocations.get(namespaceId);
    if (locations == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(locations.get(tableName));
  }

  /**
   * Returns all namespaces and their table counts (diagnostic / state inspection).
   *
   * @return unmodifiable map copy
   */
  public Map<String, Integer> namespaceTableCounts() {
    Map<String, Integer> result = new LinkedHashMap<>();
    namespaces.forEach((ns, tables) -> result.put(ns, tables.size()));
    return Collections.unmodifiableMap(result);
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Auth helpers
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Marks all subsequent requests as unauthenticated (causes 401 responses). Corresponds to §7
   * state: {@code auth token is invalid}.
   */
  public void invalidateAuth() {
    authInvalid = true;
  }

  /** Returns whether auth should be treated as invalid. */
  public boolean isAuthInvalid() {
    return authInvalid;
  }
}
