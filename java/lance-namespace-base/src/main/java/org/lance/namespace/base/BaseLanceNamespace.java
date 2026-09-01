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
package org.lance.namespace.base;

import org.lance.Dataset;
import org.lance.ReadOptions;
import org.lance.Tag;
import org.lance.Version;
import org.lance.WriteParams;
import org.lance.index.DistanceType;
import org.lance.index.Index;
import org.lance.index.IndexDescription;
import org.lance.index.IndexOptions;
import org.lance.index.IndexParams;
import org.lance.index.IndexType;
import org.lance.index.scalar.ScalarIndexParams;
import org.lance.ipc.FullTextQuery;
import org.lance.ipc.LanceScanner;
import org.lance.ipc.Query;
import org.lance.ipc.ScanOptions;
import org.lance.merge.MergeInsertParams;
import org.lance.namespace.LanceNamespace;
import org.lance.namespace.errors.InternalException;
import org.lance.namespace.errors.InvalidInputException;
import org.lance.namespace.errors.LanceNamespaceException;
import org.lance.namespace.errors.NamespaceAlreadyExistsException;
import org.lance.namespace.errors.NamespaceNotEmptyException;
import org.lance.namespace.errors.NamespaceNotFoundException;
import org.lance.namespace.errors.TableAlreadyExistsException;
import org.lance.namespace.errors.TableIndexNotFoundException;
import org.lance.namespace.errors.TableNotFoundException;
import org.lance.namespace.errors.TableTagNotFoundException;
import org.lance.namespace.errors.TableVersionNotFoundException;
import org.lance.namespace.model.*;
import org.lance.schema.ColumnAlteration;
import org.lance.schema.LanceField;
import org.lance.schema.LanceSchema;
import org.lance.schema.SqlExpressions;

import org.apache.arrow.c.ArrowArrayStream;
import org.apache.arrow.c.Data;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.ipc.ArrowReader;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.Schema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Base implementation of {@link LanceNamespace} using the Lance Java SDK.
 *
 * <p>This class provides a filesystem-directory-based namespace implementation that delegates table
 * operations to the Lance Java SDK ({@link Dataset}, {@link LanceScanner}, etc.) rather than JNI
 * namespace calls.
 *
 * <p>Namespace hierarchy maps to filesystem directories: namespace path {@code ["a", "b"]} resolves
 * to {@code {root}/a/b/}. Tables within a namespace resolve to {@code {root}/a/b/tableName.lance}.
 *
 * <p>Configuration properties:
 *
 * <ul>
 *   <li>{@code root} (required): Root directory path
 *   <li>{@code storage.*} (optional): Storage options passed to Dataset operations
 * </ul>
 */
public class BaseLanceNamespace implements LanceNamespace, Closeable {

  private static final String LANCE_EXTENSION = ".lance";
  private static final String STORAGE_PREFIX = "storage.";

  private BufferAllocator allocator;
  private String root;
  private Map<String, String> storageOptions;

  public BaseLanceNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    this.root = configProperties.get("root");
    if (this.root == null || this.root.isEmpty()) {
      throw new InvalidInputException("Property 'root' is required");
    }
    this.storageOptions = new HashMap<>();
    for (Map.Entry<String, String> entry : configProperties.entrySet()) {
      if (entry.getKey().startsWith(STORAGE_PREFIX)) {
        storageOptions.put(entry.getKey().substring(STORAGE_PREFIX.length()), entry.getValue());
      }
    }
  }

  @Override
  public String namespaceId() {
    return "BaseLanceNamespace{root=" + root + "}";
  }

  @Override
  public void close() {
    // No native resources to release
  }

  // ========== Namespace Operations ==========

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    Path nsPath = resolveNamespacePath(request.getId());
    if (Files.exists(nsPath)) {
      throw new NamespaceAlreadyExistsException(
          "Namespace already exists: " + String.join("/", request.getId()));
    }
    try {
      Files.createDirectories(nsPath);
    } catch (IOException e) {
      throw new InternalException("Failed to create namespace: " + e.getMessage());
    }
    return new CreateNamespaceResponse().properties(new HashMap<>());
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    List<String> id = request.getId();
    Path nsPath = (id == null || id.isEmpty()) ? Paths.get(root) : resolveNamespacePath(id);
    ensureNamespaceExists(nsPath, id);

    List<String> namespaces = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(nsPath)) {
      for (Path entry : stream) {
        if (Files.isDirectory(entry) && !entry.getFileName().toString().endsWith(LANCE_EXTENSION)) {
          namespaces.add(entry.getFileName().toString());
        }
      }
    } catch (IOException e) {
      throw new InternalException("Failed to list namespaces: " + e.getMessage());
    }
    Collections.sort(namespaces);
    String[] nextToken = new String[1];
    namespaces = paginateList(namespaces, request.getLimit(), request.getPageToken(), nextToken);
    ListNamespacesResponse response =
        new ListNamespacesResponse().namespaces(new LinkedHashSet<>(namespaces));
    if (nextToken[0] != null) {
      response.setPageToken(nextToken[0]);
    }
    return response;
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    Path nsPath = resolveNamespacePath(request.getId());
    ensureNamespaceExists(nsPath, request.getId());
    return new DescribeNamespaceResponse().properties(new HashMap<>());
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    Path nsPath = resolveNamespacePath(request.getId());
    ensureNamespaceExists(nsPath, request.getId());

    try (DirectoryStream<Path> stream = Files.newDirectoryStream(nsPath)) {
      if (stream.iterator().hasNext()) {
        throw new NamespaceNotEmptyException(
            "Namespace is not empty: " + String.join("/", request.getId()));
      }
    } catch (LanceNamespaceException e) {
      throw e;
    } catch (IOException e) {
      throw new InternalException("Failed to check namespace: " + e.getMessage());
    }

    try {
      Files.delete(nsPath);
    } catch (IOException e) {
      throw new InternalException("Failed to drop namespace: " + e.getMessage());
    }
    return new DropNamespaceResponse();
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    Path nsPath = resolveNamespacePath(request.getId());
    ensureNamespaceExists(nsPath, request.getId());
  }

  // ========== Table Operations ==========

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    String tableUri = resolveTableUri(request.getId());
    if (Files.exists(Paths.get(tableUri))) {
      throw new TableAlreadyExistsException("Table already exists: " + tableName(request.getId()));
    }
    ensureParentNamespaceExists(request.getId());

    try (ArrowArrayStream stream = ipcBytesToStream(requestData);
        Dataset dataset =
            Dataset.write().allocator(allocator).stream(stream)
                .uri(tableUri)
                .storageOptions(storageOptions)
                .mode(WriteParams.WriteMode.CREATE)
                .execute()) {
      return new CreateTableResponse().location(tableUri).version(dataset.version());
    }
  }

  @Override
  public DeclareTableResponse declareTable(DeclareTableRequest request) {
    String tableUri = resolveTableUri(request.getId());
    if (Files.exists(Paths.get(tableUri))) {
      throw new TableAlreadyExistsException("Table already exists: " + tableName(request.getId()));
    }
    ensureParentNamespaceExists(request.getId());

    try {
      Files.createDirectories(Paths.get(tableUri));
    } catch (IOException e) {
      throw new InternalException("Failed to declare table: " + e.getMessage());
    }
    return new DeclareTableResponse().location(tableUri);
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    List<String> id = request.getId();
    Path nsPath = (id == null || id.isEmpty()) ? Paths.get(root) : resolveNamespacePath(id);
    ensureNamespaceExists(nsPath, id);

    List<String> tables = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(nsPath)) {
      for (Path entry : stream) {
        String name = entry.getFileName().toString();
        if (Files.isDirectory(entry) && name.endsWith(LANCE_EXTENSION)) {
          tables.add(name.substring(0, name.length() - LANCE_EXTENSION.length()));
        }
      }
    } catch (IOException e) {
      throw new InternalException("Failed to list tables: " + e.getMessage());
    }
    Collections.sort(tables);
    String[] nextToken = new String[1];
    tables = paginateList(tables, request.getLimit(), request.getPageToken(), nextToken);
    ListTablesResponse response = new ListTablesResponse().tables(new LinkedHashSet<>(tables));
    if (nextToken[0] != null) {
      response.setPageToken(nextToken[0]);
    }
    return response;
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    String tableUri = resolveTableUri(request.getId());
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      Schema schema = dataset.getSchema();
      return new DescribeTableResponse()
          .location(tableUri)
          .version(dataset.version())
          .schema(convertArrowSchemaToJson(schema));
    }
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    ensureTableExists(request.getId());
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    Path tablePath = Paths.get(resolveTableUri(request.getId()));
    ensureTableExists(request.getId());

    try {
      deleteRecursive(tablePath);
    } catch (IOException e) {
      throw new InternalException("Failed to drop table: " + e.getMessage());
    }
    return new DropTableResponse();
  }

  @Override
  public RegisterTableResponse registerTable(RegisterTableRequest request) {
    String location = request.getLocation();
    if (location == null || location.isEmpty()) {
      throw new InvalidInputException("Location is required for registerTable");
    }
    List<String> tableId = request.getId();
    String tableUri = resolveTableUri(tableId);
    if (Files.exists(Paths.get(tableUri))) {
      throw new TableAlreadyExistsException("Table already exists: " + tableName(tableId));
    }
    ensureParentNamespaceExists(tableId);

    // Verify the location is a valid Lance dataset by opening it
    try (Dataset dataset =
        Dataset.open(
            allocator,
            location,
            new ReadOptions.Builder().setStorageOptions(storageOptions).build())) {
      // Dataset is valid -- create a symlink to register it under this namespace
      Path target = Paths.get(location);
      Path link = Paths.get(tableUri);
      try {
        Files.createSymbolicLink(link, target);
      } catch (IOException e) {
        throw new InternalException("Failed to register table: " + e.getMessage());
      }
      return new RegisterTableResponse().location(tableUri);
    } catch (LanceNamespaceException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new InvalidInputException(
          "Location is not a valid Lance dataset: " + location + " (" + e.getMessage() + ")");
    }
  }

  @Override
  public DeregisterTableResponse deregisterTable(DeregisterTableRequest request) {
    // For filesystem-based namespace, deregister is the same as drop
    dropTable(new DropTableRequest().id(request.getId()));
    return new DeregisterTableResponse();
  }

  @Override
  public Long countTableRows(CountTableRowsRequest request) {
    ensureTableExists(request.getId());
    try (Dataset dataset = openDataset(request.getId())) {
      String predicate = request.getPredicate();
      if (predicate != null && !predicate.isEmpty()) {
        return dataset.countRows(predicate);
      }
      return dataset.countRows();
    }
  }

  // ========== Data Operations ==========

  @Override
  public InsertIntoTableResponse insertIntoTable(
      InsertIntoTableRequest request, byte[] requestData) {
    ensureTableExists(request.getId());
    String tableUri = resolveTableUri(request.getId());

    try (ArrowArrayStream stream = ipcBytesToStream(requestData);
        Dataset dataset =
            Dataset.write().allocator(allocator).stream(stream)
                .uri(tableUri)
                .storageOptions(storageOptions)
                .mode(WriteParams.WriteMode.APPEND)
                .execute()) {
      return new InsertIntoTableResponse();
    }
  }

  @Override
  public MergeInsertIntoTableResponse mergeInsertIntoTable(
      MergeInsertIntoTableRequest request, byte[] requestData) {
    ensureTableExists(request.getId());

    MergeInsertParams params = buildMergeInsertParams(request);

    try (Dataset dataset = openDataset(request.getId());
        ArrowArrayStream stream = ipcBytesToStream(requestData)) {
      dataset.mergeInsert(params, stream);
      return new MergeInsertIntoTableResponse().version(dataset.version());
    }
  }

  @Override
  public DeleteFromTableResponse deleteFromTable(DeleteFromTableRequest request) {
    ensureTableExists(request.getId());
    String predicate = request.getPredicate();
    if (predicate == null || predicate.isEmpty()) {
      throw new InvalidInputException("Filter predicate is required for delete");
    }

    try (Dataset dataset = openDataset(request.getId())) {
      dataset.delete(predicate);
      return new DeleteFromTableResponse();
    }
  }

  @Override
  public byte[] queryTable(QueryTableRequest request) {
    ensureTableExists(request.getId());

    Long version = request.getVersion();
    try (Dataset dataset =
        version != null ? openDataset(request.getId(), version) : openDataset(request.getId())) {

      ScanOptions scanOptions = buildScanOptions(request);
      LanceScanner scanner = dataset.newScan(scanOptions);
      try {
        return scannerToIpcBytes(scanner);
      } finally {
        closeQuietly(scanner);
      }
    }
  }

  // ========== Index Operations ==========

  @Override
  public CreateTableIndexResponse createTableIndex(CreateTableIndexRequest request) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      IndexOptions options = buildIndexOptions(request, false);
      Index index = dataset.createIndex(options);
      return new CreateTableIndexResponse();
    }
  }

  @Override
  public CreateTableScalarIndexResponse createTableScalarIndex(CreateTableIndexRequest request) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      IndexOptions options = buildIndexOptions(request, true);
      dataset.createIndex(options);
      return new CreateTableScalarIndexResponse();
    }
  }

  @Override
  public ListTableIndicesResponse listTableIndices(ListTableIndicesRequest request) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      Map<Integer, String> fieldIdToName = buildFieldIdToNameMap(dataset);
      List<Index> indices = dataset.getIndexes();
      List<IndexContent> indexContents = new ArrayList<>();
      for (Index index : indices) {
        IndexContent content = new IndexContent();
        content.setIndexName(index.name());
        content.setIndexUuid(index.uuid() != null ? index.uuid().toString() : "");
        content.setColumns(
            index.fields().stream()
                .map(fieldId -> fieldIdToName.getOrDefault(fieldId, String.valueOf(fieldId)))
                .collect(Collectors.toList()));
        content.setStatus("READY");
        indexContents.add(content);
      }
      Integer limit = request.getLimit();
      if (limit != null && limit > 0 && indexContents.size() > limit) {
        indexContents = indexContents.subList(0, limit);
      }
      return new ListTableIndicesResponse().indexes(indexContents);
    }
  }

  @Override
  public DescribeTableIndexStatsResponse describeTableIndexStats(
      DescribeTableIndexStatsRequest request, String indexName) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      List<IndexDescription> descriptions = dataset.describeIndices();
      for (IndexDescription desc : descriptions) {
        if (indexName.equals(desc.getName())) {
          long totalRows = dataset.countRows();
          long indexedRows = desc.getRowsIndexed();
          long unindexedRows = Math.max(0, totalRows - indexedRows);
          DescribeTableIndexStatsResponse response = new DescribeTableIndexStatsResponse();
          response.setIndexType(desc.getIndexType());
          response.setNumIndexedRows(indexedRows);
          response.setNumUnindexedRows(unindexedRows);
          response.setNumIndices(desc.getSegments().size());
          return response;
        }
      }
      throw new TableIndexNotFoundException("Index not found: " + indexName);
    }
  }

  @Override
  public DropTableIndexResponse dropTableIndex(DropTableIndexRequest request, String indexName) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      dataset.dropIndex(indexName);
      return new DropTableIndexResponse();
    }
  }

  // ========== Schema Operations ==========

  @Override
  public AlterTableAddColumnsResponse alterTableAddColumns(AlterTableAddColumnsRequest request) {
    ensureTableExists(request.getId());

    List<NewColumnTransform> transforms = request.getNewColumns();
    if (transforms == null || transforms.isEmpty()) {
      throw new InvalidInputException("At least one column transform is required");
    }

    SqlExpressions.Builder builder = new SqlExpressions.Builder();
    for (NewColumnTransform transform : transforms) {
      builder.withExpression(transform.getName(), transform.getExpression());
    }

    try (Dataset dataset = openDataset(request.getId())) {
      dataset.addColumns(builder.build(), Optional.empty());
      return new AlterTableAddColumnsResponse();
    }
  }

  @Override
  public AlterTableAlterColumnsResponse alterTableAlterColumns(
      AlterTableAlterColumnsRequest request) {
    ensureTableExists(request.getId());

    List<AlterColumnsEntry> entries = request.getAlterations();
    if (entries == null || entries.isEmpty()) {
      throw new InvalidInputException("At least one column alteration is required");
    }

    List<ColumnAlteration> alterations = new ArrayList<>();
    for (AlterColumnsEntry entry : entries) {
      ColumnAlteration.Builder builder = new ColumnAlteration.Builder(entry.getPath());
      if (entry.getRename() != null) {
        builder.rename(entry.getRename());
      }
      if (entry.getNullable() != null) {
        builder.nullable(entry.getNullable());
      }
      alterations.add(builder.build());
    }

    try (Dataset dataset = openDataset(request.getId())) {
      dataset.alterColumns(alterations);
      return new AlterTableAlterColumnsResponse();
    }
  }

  @Override
  public AlterTableDropColumnsResponse alterTableDropColumns(AlterTableDropColumnsRequest request) {
    ensureTableExists(request.getId());

    List<String> columns = request.getColumns();
    if (columns == null || columns.isEmpty()) {
      throw new InvalidInputException("At least one column name is required");
    }

    try (Dataset dataset = openDataset(request.getId())) {
      dataset.dropColumns(columns);
      return new AlterTableDropColumnsResponse();
    }
  }

  @Override
  public GetTableStatsResponse getTableStats(GetTableStatsRequest request) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      long rowCount = dataset.countRows();
      return new GetTableStatsResponse().numRows(rowCount);
    }
  }

  // ========== Version Operations ==========

  @Override
  public ListTableVersionsResponse listTableVersions(ListTableVersionsRequest request) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      List<Version> versions = dataset.listVersions();
      List<TableVersion> tableVersions = new ArrayList<>();
      for (Version version : versions) {
        tableVersions.add(toTableVersion(version));
      }
      Integer limit = request.getLimit();
      if (limit != null && limit > 0 && tableVersions.size() > limit) {
        tableVersions = tableVersions.subList(0, limit);
      }
      return new ListTableVersionsResponse().versions(tableVersions);
    }
  }

  @Override
  public DescribeTableVersionResponse describeTableVersion(DescribeTableVersionRequest request) {
    ensureTableExists(request.getId());

    Long versionNum = request.getVersion();
    if (versionNum == null) {
      throw new InvalidInputException("Version is required for describeTableVersion");
    }

    try (Dataset dataset = openDataset(request.getId())) {
      List<Version> versions = dataset.listVersions();
      for (Version version : versions) {
        if (version.getId() == versionNum.longValue()) {
          return new DescribeTableVersionResponse().version(toTableVersion(version));
        }
      }
      throw new TableVersionNotFoundException("Version not found: " + versionNum);
    }
  }

  @Override
  public RestoreTableResponse restoreTable(RestoreTableRequest request) {
    ensureTableExists(request.getId());

    Long version = request.getVersion();
    if (version == null) {
      throw new InvalidInputException("Version is required for restore");
    }

    try (Dataset dataset = openDataset(request.getId(), version)) {
      dataset.restore();
      return new RestoreTableResponse();
    }
  }

  @Override
  public RenameTableResponse renameTable(RenameTableRequest request) {
    ensureTableExists(request.getId());

    String newName = request.getNewTableName();
    if (newName == null || newName.isEmpty()) {
      throw new InvalidInputException("New table name is required");
    }

    Path sourcePath = Paths.get(resolveTableUri(request.getId()));
    List<String> namespacePath = namespacePath(request.getId());
    List<String> newId = new ArrayList<>(namespacePath);
    newId.add(newName);
    Path targetPath = Paths.get(resolveTableUri(newId));

    if (Files.exists(targetPath)) {
      throw new TableAlreadyExistsException("Table already exists: " + newName);
    }

    try {
      Files.move(sourcePath, targetPath);
    } catch (IOException e) {
      throw new InternalException("Failed to rename table: " + e.getMessage());
    }
    return new RenameTableResponse();
  }

  @Override
  public ListTablesResponse listAllTables(ListTablesRequest request) {
    List<String> tables = new ArrayList<>();
    collectTablesRecursive(Paths.get(root), "", tables);
    Collections.sort(tables);
    String[] nextToken = new String[1];
    tables = paginateList(tables, request.getLimit(), request.getPageToken(), nextToken);
    ListTablesResponse response = new ListTablesResponse().tables(new LinkedHashSet<>(tables));
    if (nextToken[0] != null) {
      response.setPageToken(nextToken[0]);
    }
    return response;
  }

  // ========== Tag Operations ==========

  @Override
  public CreateTableTagResponse createTableTag(CreateTableTagRequest request) {
    ensureTableExists(request.getId());

    String tagName = request.getTag();
    Long version = request.getVersion();
    if (tagName == null || version == null) {
      throw new InvalidInputException("Tag name and version are required");
    }

    try (Dataset dataset = openDataset(request.getId())) {
      dataset.tags().create(tagName, version);
      return new CreateTableTagResponse();
    }
  }

  @Override
  public ListTableTagsResponse listTableTags(ListTableTagsRequest request) {
    ensureTableExists(request.getId());

    try (Dataset dataset = openDataset(request.getId())) {
      List<Tag> tags = dataset.tags().list();
      Integer limit = request.getLimit();
      Map<String, TagContents> tagMap = new LinkedHashMap<>();
      int count = 0;
      for (Tag tag : tags) {
        if (limit != null && limit > 0 && count >= limit) {
          break;
        }
        TagContents tc = new TagContents();
        tc.setVersion(tag.getVersion());
        tc.setManifestSize((long) tag.getManifestSize());
        tag.getBranch().ifPresent(tc::setBranch);
        tagMap.put(tag.getName(), tc);
        count++;
      }
      return new ListTableTagsResponse().tags(tagMap);
    }
  }

  @Override
  public GetTableTagVersionResponse getTableTagVersion(GetTableTagVersionRequest request) {
    ensureTableExists(request.getId());

    String tagName = request.getTag();
    if (tagName == null) {
      throw new InvalidInputException("Tag name is required");
    }

    return withTagNotFoundHandling(
        tagName,
        () -> {
          try (Dataset dataset = openDataset(request.getId())) {
            long version = dataset.tags().getVersion(tagName);
            return new GetTableTagVersionResponse().version(version);
          }
        });
  }

  @Override
  public DeleteTableTagResponse deleteTableTag(DeleteTableTagRequest request) {
    ensureTableExists(request.getId());

    String tagName = request.getTag();
    if (tagName == null) {
      throw new InvalidInputException("Tag name is required");
    }

    return withTagNotFoundHandling(
        tagName,
        () -> {
          try (Dataset dataset = openDataset(request.getId())) {
            dataset.tags().delete(tagName);
            return new DeleteTableTagResponse();
          }
        });
  }

  @Override
  public UpdateTableTagResponse updateTableTag(UpdateTableTagRequest request) {
    ensureTableExists(request.getId());

    String tagName = request.getTag();
    Long version = request.getVersion();
    if (tagName == null || version == null) {
      throw new InvalidInputException("Tag name and version are required");
    }

    return withTagNotFoundHandling(
        tagName,
        () -> {
          try (Dataset dataset = openDataset(request.getId())) {
            dataset.tags().update(tagName, version);
            return new UpdateTableTagResponse();
          }
        });
  }

  // ========== Protected Helper Methods ==========

  protected Dataset openDataset(List<String> tableId) {
    String tableUri = resolveTableUri(tableId);
    ReadOptions options = new ReadOptions.Builder().setStorageOptions(storageOptions).build();
    return Dataset.open(allocator, tableUri, options);
  }

  protected Dataset openDataset(List<String> tableId, long version) {
    String tableUri = resolveTableUri(tableId);
    ReadOptions options =
        new ReadOptions.Builder().setStorageOptions(storageOptions).setVersion(version).build();
    return Dataset.open(allocator, tableUri, options);
  }

  protected String resolveTableUri(List<String> tableId) {
    if (tableId == null || tableId.isEmpty()) {
      throw new InvalidInputException("Table ID is required");
    }
    StringBuilder sb = new StringBuilder(root);
    for (String part : tableId) {
      sb.append("/").append(part);
    }
    sb.append(LANCE_EXTENSION);
    return sb.toString();
  }

  protected Path resolveNamespacePath(List<String> namespaceId) {
    if (namespaceId == null || namespaceId.isEmpty()) {
      return Paths.get(root);
    }
    StringBuilder sb = new StringBuilder(root);
    for (String part : namespaceId) {
      sb.append("/").append(part);
    }
    return Paths.get(sb.toString());
  }

  protected ArrowArrayStream ipcBytesToStream(byte[] data) {
    ArrowArrayStream stream = ArrowArrayStream.allocateNew(allocator);
    try {
      // exportArrayStream takes ownership of the reader -- it will be closed when the stream is
      // consumed. Do NOT close the reader here or wrap it in try-with-resources.
      ArrowStreamReader reader = new ArrowStreamReader(new ByteArrayInputStream(data), allocator);
      Data.exportArrayStream(allocator, reader, stream);
    } catch (Exception e) {
      stream.close();
      throw new InvalidInputException("Failed to parse Arrow IPC data: " + e.getMessage());
    }
    return stream;
  }

  protected byte[] scannerToIpcBytes(LanceScanner scanner) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try (ArrowReader reader = scanner.scanBatches()) {
        try (ArrowStreamWriter writer =
            new ArrowStreamWriter(reader.getVectorSchemaRoot(), null, baos)) {
          writer.start();
          while (reader.loadNextBatch()) {
            writer.writeBatch();
          }
        }
      }
      return baos.toByteArray();
    } catch (IOException e) {
      throw new InternalException("Failed to serialize scan results: " + e.getMessage());
    }
  }

  private <T> T withTagNotFoundHandling(String tagName, Supplier<T> action) {
    try {
      return action.get();
    } catch (LanceNamespaceException e) {
      throw e;
    } catch (RuntimeException e) {
      if (isTagNotFound(e)) {
        throw new TableTagNotFoundException("Tag not found: " + tagName);
      }
      throw e;
    }
  }

  // Heuristic: the Lance SDK does not expose typed exceptions for tag operations,
  // so we detect "tag not found" by inspecting the exception message. This is fragile
  // and may need updating if the SDK changes its error message format.
  private boolean isTagNotFound(RuntimeException e) {
    String msg = e.getMessage();
    if (msg == null) {
      return false;
    }
    String lower = msg.toLowerCase();
    return lower.contains("tag") && lower.contains("not found");
  }

  // ========== Private Helper Methods ==========

  /**
   * Apply limit-based pagination to a sorted list. Uses the last element as the next page token
   * when there are more results than the limit. When a pageToken is provided, skips all elements up
   * to and including that token value.
   *
   * <p>Returns a new list (does not mutate the input). The next page token (or null) is stored in
   * {@code nextTokenOut[0]}.
   */
  private List<String> paginateList(
      List<String> sorted, Integer limit, String pageToken, String[] nextTokenOut) {
    int startIdx = 0;

    // Skip past the page token if provided
    if (pageToken != null && !pageToken.isEmpty()) {
      int idx = Collections.binarySearch(sorted, pageToken);
      if (idx >= 0) {
        startIdx = idx + 1;
      } else {
        startIdx = -(idx + 1);
      }
    }

    int endIdx = sorted.size();
    nextTokenOut[0] = null;

    if (limit != null && limit > 0 && (endIdx - startIdx) > limit) {
      endIdx = startIdx + limit;
      nextTokenOut[0] = sorted.get(endIdx - 1);
    }

    return new ArrayList<>(sorted.subList(startIdx, endIdx));
  }

  private void closeQuietly(AutoCloseable closeable) {
    try {
      closeable.close();
    } catch (Exception ignored) {
      // best-effort close
    }
  }

  private TableVersion toTableVersion(Version version) {
    TableVersion tv = new TableVersion();
    tv.setVersion(version.getId());
    if (version.getDataTime() != null) {
      tv.setTimestampMillis(version.getDataTime().toInstant().toEpochMilli());
    }
    tv.setMetadata(version.getMetadata());
    return tv;
  }

  private Map<Integer, String> buildFieldIdToNameMap(Dataset dataset) {
    Map<Integer, String> map = new HashMap<>();
    LanceSchema lanceSchema = dataset.getLanceSchema();
    for (LanceField field : lanceSchema.fields()) {
      map.put(field.getId(), field.getName());
    }
    return map;
  }

  private List<String> namespacePath(List<String> id) {
    if (id.size() <= 1) {
      return Collections.emptyList();
    }
    return id.subList(0, id.size() - 1);
  }

  private String tableName(List<String> id) {
    return id.get(id.size() - 1);
  }

  private void ensureNamespaceExists(Path nsPath, List<String> id) {
    if (!Files.exists(nsPath) || !Files.isDirectory(nsPath)) {
      String name = (id == null || id.isEmpty()) ? "<root>" : String.join("/", id);
      throw new NamespaceNotFoundException("Namespace not found: " + name);
    }
  }

  private void ensureTableExists(List<String> tableId) {
    Path tablePath = Paths.get(resolveTableUri(tableId));
    if (!Files.exists(tablePath)) {
      throw new TableNotFoundException("Table not found: " + tableName(tableId));
    }
  }

  private DistanceType parseDistanceType(String value) {
    switch (value.toLowerCase()) {
      case "l2":
      case "euclidean":
        return DistanceType.L2;
      case "cosine":
        return DistanceType.Cosine;
      case "dot":
        return DistanceType.Dot;
      case "hamming":
        return DistanceType.Hamming;
      default:
        throw new InvalidInputException("Unknown distance type: " + value);
    }
  }

  private void ensureParentNamespaceExists(List<String> tableId) {
    List<String> nsPath = namespacePath(tableId);
    if (!nsPath.isEmpty()) {
      Path parentPath = resolveNamespacePath(nsPath);
      if (!Files.exists(parentPath)) {
        throw new NamespaceNotFoundException(
            "Parent namespace not found: " + String.join("/", nsPath));
      }
    }
  }

  // TODO: forward fastSearch, lowerBound, upperBound once the Lance Java SDK supports them.
  private ScanOptions buildScanOptions(QueryTableRequest request) {
    ScanOptions.Builder builder = new ScanOptions.Builder();

    QueryTableRequestColumns columns = request.getColumns();
    if (columns != null && columns.getColumnNames() != null) {
      builder.columns(columns.getColumnNames());
    }

    String filter = request.getFilter();
    if (filter != null && !filter.isEmpty()) {
      builder.filter(filter);
    }

    Integer k = request.getK();
    if (k != null && k > 0) {
      Query.Builder queryBuilder = new Query.Builder();
      queryBuilder.setK(k);

      QueryTableRequestVector vector = request.getVector();
      if (vector != null && vector.getSingleVector() != null) {
        List<Float> values = vector.getSingleVector();
        float[] keyArray = new float[values.size()];
        for (int i = 0; i < values.size(); i++) {
          keyArray[i] = values.get(i);
        }
        queryBuilder.setKey(keyArray);
      }

      String vectorColumn = request.getVectorColumn();
      if (vectorColumn != null) {
        queryBuilder.setColumn(vectorColumn);
      }

      Integer nprobes = request.getNprobes();
      if (nprobes != null) {
        queryBuilder.setNprobes(nprobes);
      }

      Integer ef = request.getEf();
      if (ef != null) {
        queryBuilder.setEf(ef);
      }

      Integer refineFactor = request.getRefineFactor();
      if (refineFactor != null) {
        queryBuilder.setRefineFactor(refineFactor);
      }

      String distanceType = request.getDistanceType();
      if (distanceType != null) {
        queryBuilder.setDistanceType(parseDistanceType(distanceType));
      }

      if (Boolean.TRUE.equals(request.getBypassVectorIndex())) {
        queryBuilder.setUseIndex(false);
      }

      builder.nearest(queryBuilder.build());
    }

    QueryTableRequestFullTextQuery ftsQuery = request.getFullTextQuery();
    if (ftsQuery != null && ftsQuery.getStringQuery() != null) {
      StringFtsQuery stringFts = ftsQuery.getStringQuery();
      String queryText = stringFts.getQuery();
      List<String> ftsColumns = stringFts.getColumns();
      if (queryText != null) {
        if (ftsColumns != null && ftsColumns.size() == 1) {
          builder.fullTextQuery(FullTextQuery.match(queryText, ftsColumns.get(0)));
        } else if (ftsColumns != null && ftsColumns.size() > 1) {
          builder.fullTextQuery(FullTextQuery.multiMatch(queryText, ftsColumns));
        }
      }
    }

    Integer offset = request.getOffset();
    if (offset != null) {
      builder.offset(offset.longValue());
    }

    Boolean prefilter = request.getPrefilter();
    if (prefilter != null) {
      builder.prefilter(prefilter);
    }

    Boolean withRowId = request.getWithRowId();
    if (withRowId != null) {
      builder.withRowId(withRowId);
    }

    return builder.build();
  }

  private MergeInsertParams buildMergeInsertParams(MergeInsertIntoTableRequest request) {
    String on = request.getOn();
    if (on == null || on.isEmpty()) {
      throw new InvalidInputException("'on' column(s) required for merge insert");
    }

    List<String> onColumns = new ArrayList<>();
    for (String col : on.split(",")) {
      onColumns.add(col.trim());
    }

    MergeInsertParams params = new MergeInsertParams(onColumns);

    if (Boolean.TRUE.equals(request.getWhenMatchedUpdateAll())) {
      String filt = request.getWhenMatchedUpdateAllFilt();
      if (filt != null && !filt.isEmpty()) {
        params.withMatchedUpdateIf(filt);
      } else {
        params.withMatchedUpdateAll();
      }
    }

    if (Boolean.TRUE.equals(request.getWhenNotMatchedInsertAll())) {
      params.withNotMatched(MergeInsertParams.WhenNotMatched.InsertAll);
    }

    if (Boolean.TRUE.equals(request.getWhenNotMatchedBySourceDelete())) {
      String filt = request.getWhenNotMatchedBySourceDeleteFilt();
      if (filt != null && !filt.isEmpty()) {
        params.withNotMatchedBySourceDeleteIf(filt);
      } else {
        params.withNotMatchedBySourceDelete();
      }
    }

    return params;
  }

  private IndexOptions buildIndexOptions(CreateTableIndexRequest request, boolean forceScalar) {
    List<String> columns = new ArrayList<>();
    if (request.getColumn() != null) {
      columns.add(request.getColumn());
    }

    String requestedType =
        request.getIndexType() != null
            ? request.getIndexType().toUpperCase()
            : (forceScalar ? "BTREE" : "IVF_PQ");

    IndexType indexType;
    IndexParams indexParams;
    switch (requestedType) {
      case "BTREE":
        indexType = IndexType.BTREE;
        indexParams =
            IndexParams.builder().setScalarIndexParams(ScalarIndexParams.create("btree")).build();
        break;
      case "BITMAP":
        indexType = IndexType.BITMAP;
        indexParams =
            IndexParams.builder().setScalarIndexParams(ScalarIndexParams.create("bitmap")).build();
        break;
      case "INVERTED":
      case "FTS":
        indexType = IndexType.INVERTED;
        indexParams =
            IndexParams.builder()
                .setScalarIndexParams(ScalarIndexParams.create("inverted"))
                .build();
        break;
      default:
        if (forceScalar) {
          indexType = IndexType.BTREE;
          indexParams =
              IndexParams.builder().setScalarIndexParams(ScalarIndexParams.create("btree")).build();
        } else {
          indexType = IndexType.IVF_PQ;
          indexParams = IndexParams.builder().build();
        }
        break;
    }

    IndexOptions.Builder builder = IndexOptions.builder(columns, indexType, indexParams);

    String indexName = request.getName();
    if (indexName != null) {
      builder.withIndexName(indexName);
    }

    builder.replace(true);

    return builder.build();
  }

  private JsonArrowSchema convertArrowSchemaToJson(Schema schema) {
    JsonArrowSchema jsonSchema = new JsonArrowSchema();
    List<JsonArrowField> fields = new ArrayList<>();
    for (org.apache.arrow.vector.types.pojo.Field field : schema.getFields()) {
      fields.add(convertArrowFieldToJson(field));
    }
    jsonSchema.setFields(fields);
    if (schema.getCustomMetadata() != null && !schema.getCustomMetadata().isEmpty()) {
      jsonSchema.setMetadata(schema.getCustomMetadata());
    }
    return jsonSchema;
  }

  private JsonArrowField convertArrowFieldToJson(org.apache.arrow.vector.types.pojo.Field field) {
    JsonArrowField jsonField = new JsonArrowField();
    jsonField.setName(field.getName());
    jsonField.setNullable(field.isNullable());
    JsonArrowDataType dataType = new JsonArrowDataType();
    dataType.setType(field.getType().toString());
    jsonField.setType(dataType);
    if (field.getMetadata() != null && !field.getMetadata().isEmpty()) {
      jsonField.setMetadata(field.getMetadata());
    }
    return jsonField;
  }

  private void deleteRecursive(Path path) throws IOException {
    if (Files.isDirectory(path)) {
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
        for (Path entry : stream) {
          deleteRecursive(entry);
        }
      }
    }
    Files.delete(path);
  }

  private void collectTablesRecursive(Path dir, String prefix, List<String> tables) {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
      for (Path entry : stream) {
        String name = entry.getFileName().toString();
        if (Files.isDirectory(entry)) {
          if (name.endsWith(LANCE_EXTENSION)) {
            String tableName = name.substring(0, name.length() - LANCE_EXTENSION.length());
            tables.add(prefix.isEmpty() ? tableName : prefix + "/" + tableName);
          } else {
            String newPrefix = prefix.isEmpty() ? name : prefix + "/" + name;
            collectTablesRecursive(entry, newPrefix, tables);
          }
        }
      }
    } catch (IOException e) {
      // Skip directories that can't be read
    }
  }
}
