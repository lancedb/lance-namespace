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
package com.lancedb.lance.namespace.hive3;

import com.lancedb.lance.Dataset;
import com.lancedb.lance.WriteParams;
import com.lancedb.lance.namespace.Configurable;
import com.lancedb.lance.namespace.LanceNamespace;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.ObjectIdentifier;
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
import com.lancedb.lance.namespace.util.CommonUtil;
import com.lancedb.lance.namespace.util.JsonArrowSchemaConverter;
import com.lancedb.lance.namespace.util.PageUtil;
import com.lancedb.lance.namespace.util.ValidationUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Catalog;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lancedb.lance.namespace.hive3.Hive3ErrorType.DatabaseAlreadyExist;
import static com.lancedb.lance.namespace.hive3.Hive3ErrorType.HiveMetaStoreError;
import static com.lancedb.lance.namespace.hive3.Hive3ErrorType.TableAlreadyExists;
import static com.lancedb.lance.namespace.hive3.Hive3ErrorType.TableNotFound;

public class Hive3Namespace implements LanceNamespace, Configurable<Configuration> {
  private static final Logger LOG = LoggerFactory.getLogger(Hive3Namespace.class);

  private Hive3ClientPool clientPool;
  private Configuration hadoopConf;
  private BufferAllocator allocator;
  private Hive3NamespaceConfig config;

  public Hive3Namespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    if (hadoopConf == null) {
      LOG.warn("Hadoop configuration not set, using the default configuration.");
      hadoopConf = new Configuration();
    }

    this.config = new Hive3NamespaceConfig(configProperties);
    this.clientPool = new Hive3ClientPool(config.getClientPoolSize(), hadoopConf);
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(nsId.levels() <= 2, "Expect a 2-level namespace but get %s", nsId);

    List<String> namespaces = doListNamespaces(nsId);

    Collections.sort(namespaces);
    PageUtil.Page page =
        PageUtil.splitPage(
            namespaces, request.getPageToken(), PageUtil.normalizePageSize(request.getLimit()));

    ListNamespacesResponse response = new ListNamespacesResponse();
    response.setNamespaces(Sets.newHashSet(page.items()));
    response.setPageToken(page.nextPageToken());
    return response;
  }

  @Override
  public CreateNamespaceResponse createNamespace(CreateNamespaceRequest request) {
    ObjectIdentifier id = ObjectIdentifier.of(request.getId());
    CreateNamespaceRequest.ModeEnum mode = request.getMode();
    Map<String, String> properties = request.getProperties();

    ValidationUtil.checkArgument(
        !id.isRoot() && id.levels() <= 2, "Expect a 3-level namespace but get %s", id);

    doCreateNamespace(id, mode, properties);

    CreateNamespaceResponse response = new CreateNamespaceResponse();
    response.setProperties(properties);
    return response;
  }

  @Override
  public DescribeNamespaceResponse describeNamespace(DescribeNamespaceRequest request) {
    ObjectIdentifier id = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        !id.isRoot() && id.levels() <= 2, "Expect a 2-level namespace but get %s", id);

    DescribeNamespaceResponse response = new DescribeNamespaceResponse();
    Map<String, String> properties = new HashMap<>();

    if (id.levels() == 1) {
      String catalog = id.levelAtListPos(0).toLowerCase();
      Catalog catalogObj = Hive3Util.getCatalogOrNull(clientPool, catalog);

      if (catalogObj == null) {
        throw LanceNamespaceException.notFound(
            String.format("Namespace does not exist: %s", id.stringStyleId()),
            HiveMetaStoreError.getType(),
            id.stringStyleId(),
            CommonUtil.formatCurrentStackTrace());
      }

      if (catalogObj.getDescription() != null) {
        properties.put("description", catalogObj.getDescription());
      }
      if (catalogObj.getLocationUri() != null) {
        properties.put("catalog.location.uri", catalogObj.getLocationUri());
      }
    } else {
      String catalog = id.levelAtListPos(0).toLowerCase();
      String db = id.levelAtListPos(1).toLowerCase();
      Database database = Hive3Util.getDatabaseOrNull(clientPool, catalog, db);

      if (database == null) {
        throw LanceNamespaceException.notFound(
            String.format("Namespace does not exist: %s", id.stringStyleId()),
            HiveMetaStoreError.getType(),
            id.stringStyleId(),
            CommonUtil.formatCurrentStackTrace());
      }

      if (database.getDescription() != null) {
        properties.put(Hive3NamespaceConfig.DATABASE_DESCRIPTION, database.getDescription());
      }
      if (database.getLocationUri() != null) {
        properties.put(Hive3NamespaceConfig.DATABASE_LOCATION_URI, database.getLocationUri());
      }
      if (database.getOwnerName() != null) {
        properties.put(Hive3NamespaceConfig.DATABASE_OWNER, database.getOwnerName());
      }
      if (database.getOwnerType() != null) {
        properties.put(Hive3NamespaceConfig.DATABASE_OWNER_TYPE, database.getOwnerType().name());
      }

      if (database.getParameters() != null) {
        properties.putAll(database.getParameters());
      }
    }

    response.setProperties(properties);
    return response;
  }

  @Override
  public void namespaceExists(NamespaceExistsRequest request) {
    ObjectIdentifier id = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        !id.isRoot() && id.levels() <= 2, "Expect a 2-level namespace but get %s", id);

    if (id.levels() == 1) {
      String catalog = id.levelAtListPos(0).toLowerCase();
      Catalog catalogObj = Hive3Util.getCatalogOrNull(clientPool, catalog);

      if (catalogObj == null) {
        throw LanceNamespaceException.notFound(
            String.format("Namespace does not exist: %s", id.stringStyleId()),
            HiveMetaStoreError.getType(),
            id.stringStyleId(),
            CommonUtil.formatCurrentStackTrace());
      }
    } else {
      String catalog = id.levelAtListPos(0).toLowerCase();
      String db = id.levelAtListPos(1).toLowerCase();
      Database database = Hive3Util.getDatabaseOrNull(clientPool, catalog, db);

      if (database == null) {
        throw LanceNamespaceException.notFound(
            String.format("Namespace does not exist: %s", id.stringStyleId()),
            HiveMetaStoreError.getType(),
            id.stringStyleId(),
            CommonUtil.formatCurrentStackTrace());
      }
    }
  }

  @Override
  public DropNamespaceResponse dropNamespace(DropNamespaceRequest request) {
    ObjectIdentifier id = ObjectIdentifier.of(request.getId());
    DropNamespaceRequest.ModeEnum mode = request.getMode();
    DropNamespaceRequest.BehaviorEnum behavior = request.getBehavior();

    ValidationUtil.checkArgument(
        !id.isRoot() && id.levels() <= 2, "Expect a 2-level namespace but get %s", id);

    if (mode == null) {
      mode = DropNamespaceRequest.ModeEnum.FAIL;
    }
    if (behavior == null) {
      behavior = DropNamespaceRequest.BehaviorEnum.RESTRICT;
    }

    Map<String, String> properties = doDropNamespace(id, mode, behavior);

    DropNamespaceResponse response = new DropNamespaceResponse();
    response.setProperties(properties);
    return response;
  }

  @Override
  public void tableExists(TableExistsRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect 3-level table identifier but get %s", tableId);

    String catalog = tableId.levelAtListPos(0).toLowerCase();
    String db = tableId.levelAtListPos(1).toLowerCase();
    String table = tableId.levelAtListPos(2).toLowerCase();

    Optional<Table> hmsTable = Hive3Util.getTable(clientPool, catalog, db, table);

    if (!hmsTable.isPresent()) {
      throw LanceNamespaceException.notFound(
          String.format("Table does not exist: %s", tableId.stringStyleId()),
          TableNotFound.getType(),
          tableId.stringStyleId(),
          CommonUtil.formatCurrentStackTrace());
    }

    Hive3Util.validateLanceTable(hmsTable.get());
  }

  @Override
  public ListTablesResponse listTables(ListTablesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        !nsId.isRoot() && nsId.levels() == 2, "Expect a 2-level namespace but get %s", nsId);

    String catalog = nsId.levelAtListPos(0).toLowerCase();
    String db = nsId.levelAtListPos(1).toLowerCase();
    List<String> tables = doListTables(catalog, db);

    Collections.sort(tables);
    PageUtil.Page page =
        PageUtil.splitPage(
            tables, request.getPageToken(), PageUtil.normalizePageSize(request.getLimit()));

    ListTablesResponse response = new ListTablesResponse();
    response.setTables(Sets.newHashSet(page.items()));
    response.setPageToken(page.nextPageToken());
    return response;
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect 3-level table identifier but get %s", tableId);

    Optional<String> location = doDescribeTable(tableId);

    if (!location.isPresent()) {
      throw LanceNamespaceException.notFound(
          String.format("Table does not exist: %s", tableId.stringStyleId()),
          TableNotFound.getType(),
          tableId.stringStyleId(),
          CommonUtil.formatCurrentStackTrace());
    }

    DescribeTableResponse response = new DescribeTableResponse();
    response.setLocation(location.get());
    return response;
  }

  @Override
  public CreateTableResponse createTable(CreateTableRequest request, byte[] requestData) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    Schema schema = JsonArrowSchemaConverter.convertToArrowSchema(request.getSchema());

    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect 3-level table identifier but get %s", tableId);

    String location = request.getLocation();
    if (location == null || location.isEmpty()) {
      location = getDefaultTableLocation(tableId.levelAtListPos(1), tableId.levelAtListPos(2));
    }

    doCreateTable(tableId, schema, location, request.getProperties(), requestData);

    CreateTableResponse response = new CreateTableResponse();
    response.setLocation(location);
    response.setVersion(1L);
    return response;
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        tableId.levels() == 3, "Expect 3-level table identifier but get %s", tableId);

    String location = doDropTable(tableId);
    // TODO: remove data

    DropTableResponse response = new DropTableResponse();
    response.setLocation(location);
    response.setId(request.getId());
    return response;
  }

  @Override
  public void setConf(Configuration conf) {
    this.hadoopConf = conf;
  }

  protected List<String> doListNamespaces(ObjectIdentifier parent) {
    try {
      if (parent.isRoot()) {
        return clientPool.run(IMetaStoreClient::getCatalogs);
      } else if (parent.levels() == 1) {
        return clientPool.run(client -> client.getAllDatabases(parent.levelAtListPos(0)));
      } else {
        return Lists.newArrayList();
      }
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed operation: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }

  protected void doCreateNamespace(
      ObjectIdentifier id, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties) {

    try {
      if (id.levels() == 1) {
        String name = id.levelAtListPos(0).toLowerCase();
        createCatalog(name, mode, properties);
      } else {
        String catalog = id.levelAtListPos(0).toLowerCase();
        String db = id.levelAtListPos(1).toLowerCase();
        createDatabase(catalog, db, mode, properties);
      }
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed operation: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }

  private void createCatalog(
      String catalogName, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties)
      throws TException, InterruptedException {

    Catalog existingCatalog = Hive3Util.getCatalogOrNull(clientPool, catalogName);
    if (existingCatalog != null) {
      switch (mode) {
        case CREATE:
          throw LanceNamespaceException.conflict(
              String.format("Catalog %s already exists", catalogName),
              DatabaseAlreadyExist.getType(),
              "",
              CommonUtil.formatCurrentStackTrace());
        case EXIST_OK:
          return;
        case OVERWRITE:
          clientPool.run(
              client -> {
                client.dropCatalog(catalogName);
                return null;
              });
      }
    }

    Catalog catalog = new Catalog();
    catalog.setName(catalogName);

    String locationUri = properties != null ? properties.get("catalog.location.uri") : null;
    if (locationUri == null) {
      locationUri =
          hadoopConf.get(HiveConf.ConfVars.METASTOREWAREHOUSE.varname) + "/" + catalogName;
    }
    catalog.setLocationUri(locationUri);

    String description = properties != null ? properties.get("description") : null;
    if (description != null) {
      catalog.setDescription(description);
    } else {
      catalog.setDescription("Lance catalog: " + catalogName);
    }

    clientPool.run(
        client -> {
          client.createCatalog(catalog);
          return null;
        });
  }

  private void createDatabase(
      String catalogName,
      String dbName,
      CreateNamespaceRequest.ModeEnum mode,
      Map<String, String> properties)
      throws TException, InterruptedException {
    Catalog catalog = Hive3Util.getCatalogOrThrowNotFoundException(clientPool, catalogName);

    Database oldDb = Hive3Util.getDatabaseOrNull(clientPool, catalogName, dbName);
    if (oldDb != null) {
      switch (mode) {
        case CREATE:
          throw LanceNamespaceException.conflict(
              String.format("Database %s.%s already exist", catalogName, dbName),
              DatabaseAlreadyExist.getType(),
              "",
              CommonUtil.formatCurrentStackTrace());
        case EXIST_OK:
          return;
        case OVERWRITE:
          clientPool.run(
              client -> {
                client.dropDatabase(catalogName, dbName, false, true, false);
                return null;
              });
      }
    }

    // If no location is specified in properties, use root config
    Map<String, String> dbProperties =
        new HashMap<>(properties != null ? properties : new HashMap<>());
    if (!dbProperties.containsKey(Hive3NamespaceConfig.DATABASE_LOCATION_URI)) {
      String dbLocation = String.format("%s/%s", config.getRoot(), dbName);
      dbProperties.put(Hive3NamespaceConfig.DATABASE_LOCATION_URI, dbLocation);
    }

    Database database = new Database();
    database.setCatalogName(catalogName);
    database.setName(dbName);
    Hive3Util.setDatabaseProperties(database, () -> catalog.getLocationUri(), dbName, dbProperties);

    clientPool.run(
        client -> {
          client.createDatabase(database);
          return null;
        });
  }

  protected Optional<String> doDescribeTable(ObjectIdentifier id) {
    String catalog = id.levelAtListPos(0).toLowerCase();
    String db = id.levelAtListPos(1).toLowerCase();
    String table = id.levelAtListPos(2).toLowerCase();

    Optional<Table> hmsTable = Hive3Util.getTable(clientPool, catalog, db, table);
    if (!hmsTable.isPresent()) {
      return Optional.empty();
    }

    Hive3Util.validateLanceTable(hmsTable.get());
    return Optional.of(hmsTable.get().getSd().getLocation());
  }

  protected void doCreateTable(
      ObjectIdentifier id,
      Schema schema,
      String location,
      Map<String, String> properties,
      byte[] data) {

    if (properties != null && "impl".equalsIgnoreCase(properties.get("managed_by"))) {
      throw new UnsupportedOperationException("managed_by=impl is not supported yet");
    }

    String catalog = id.levelAtListPos(0).toLowerCase();
    String db = id.levelAtListPos(1).toLowerCase();
    String tableName = id.levelAtListPos(2).toLowerCase();

    try {
      Optional<Table> existing = Hive3Util.getTable(clientPool, catalog, db, tableName);
      if (existing.isPresent()) {
        throw LanceNamespaceException.conflict(
            String.format("Table %s.%s.%s already exists", catalog, db, tableName),
            TableAlreadyExists.getType(),
            String.format("%s.%s.%s", catalog, db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      Table table = new Table();
      table.setCatName(catalog);
      table.setDbName(db);
      table.setTableName(tableName);
      table.setTableType("EXTERNAL_TABLE");
      table.setPartitionKeys(Lists.newArrayList());

      StorageDescriptor sd = new StorageDescriptor();
      sd.setLocation(location);
      sd.setCols(Lists.newArrayList());
      sd.setInputFormat("com.lancedb.lance.mapred.LanceInputFormat");
      sd.setOutputFormat("com.lancedb.lance.mapred.LanceOutputFormat");
      sd.setSerdeInfo(new org.apache.hadoop.hive.metastore.api.SerDeInfo());
      table.setSd(sd);

      Map<String, String> params = Hive3Util.createLanceTableParams(properties);
      table.setParameters(params);

      clientPool.run(
          client -> {
            client.createTable(table);
            return null;
          });
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serverError(
          "Fail to create table: " + e.getMessage(),
          HiveMetaStoreError.getType(),
          id.stringStyleId(),
          CommonUtil.formatCurrentStackTrace());
    }

    if (data != null && data.length > 0) {
      WriteParams writeParams =
          new WriteParams.Builder()
              .withMode(WriteParams.WriteMode.CREATE)
              .withStorageOptions(config.getStorageOptions())
              .build();
      Dataset.create(allocator, location, schema, writeParams);
    }
  }

  protected List<String> doListTables(String catalog, String db) {
    try {
      // First validate that catalog and database exist
      Catalog catalogObj = Hive3Util.getCatalogOrNull(clientPool, catalog);
      if (catalogObj == null) {
        throw LanceNamespaceException.notFound(
            String.format("Catalog %s doesn't exist", catalog),
            HiveMetaStoreError.getType(),
            catalog,
            CommonUtil.formatCurrentStackTrace());
      }

      Database database = Hive3Util.getDatabaseOrNull(clientPool, catalog, db);
      if (database == null) {
        throw LanceNamespaceException.notFound(
            String.format("Database %s.%s doesn't exist", catalog, db),
            HiveMetaStoreError.getType(),
            String.format("%s.%s", catalog, db),
            CommonUtil.formatCurrentStackTrace());
      }

      List<String> allTables = clientPool.run(client -> client.getAllTables(catalog, db));
      List<String> lanceTables = Lists.newArrayList();

      for (String tableName : allTables) {
        try {
          Optional<Table> table = Hive3Util.getTable(clientPool, catalog, db, tableName);
          if (table.isPresent()) {
            Map<String, String> params = table.get().getParameters();
            if (params != null && "lance".equalsIgnoreCase(params.get("table_type"))) {
              lanceTables.add(tableName);
            }
          }
        } catch (Exception e) {
          // Skip tables that can't be accessed or validated
          LOG.warn("Failed to validate table {}.{}.{}: {}", catalog, db, tableName, e.getMessage());
        }
      }

      return lanceTables;
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to list tables: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }

  protected String doDropTable(ObjectIdentifier id) {
    String catalog = id.levelAtListPos(0).toLowerCase();
    String db = id.levelAtListPos(1).toLowerCase();
    String tableName = id.levelAtListPos(2).toLowerCase();

    try {
      Optional<Table> hmsTable = Hive3Util.getTable(clientPool, catalog, db, tableName);
      if (!hmsTable.isPresent()) {
        throw LanceNamespaceException.notFound(
            String.format("Table %s.%s.%s does not exist", catalog, db, tableName),
            TableNotFound.getType(),
            id.stringStyleId(),
            CommonUtil.formatCurrentStackTrace());
      }

      Hive3Util.validateLanceTable(hmsTable.get());
      String location = hmsTable.get().getSd().getLocation();

      clientPool.run(
          client -> {
            client.dropTable(catalog, db, tableName, false, true);
            return null;
          });

      return location;
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to drop table: " + errorMessage,
          HiveMetaStoreError.getType(),
          id.stringStyleId(),
          CommonUtil.formatCurrentStackTrace());
    }
  }

  protected Map<String, String> doDropNamespace(
      ObjectIdentifier id,
      DropNamespaceRequest.ModeEnum mode,
      DropNamespaceRequest.BehaviorEnum behavior) {

    try {
      if (id.levels() == 1) {
        // Drop catalog
        return doDropCatalog(id.levelAtListPos(0).toLowerCase(), mode, behavior);
      } else {
        // Drop database
        return doDropDatabase(
            id.levelAtListPos(0).toLowerCase(), id.levelAtListPos(1).toLowerCase(), mode, behavior);
      }
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to drop namespace: " + errorMessage,
          HiveMetaStoreError.getType(),
          id.stringStyleId(),
          CommonUtil.formatCurrentStackTrace());
    }
  }

  private Map<String, String> doDropCatalog(
      String catalog,
      DropNamespaceRequest.ModeEnum mode,
      DropNamespaceRequest.BehaviorEnum behavior)
      throws TException, InterruptedException {
    Catalog catalogObj = Hive3Util.getCatalogOrNull(clientPool, catalog);
    if (catalogObj == null) {
      if (mode == DropNamespaceRequest.ModeEnum.SKIP) {
        return new HashMap<>();
      } else {
        throw LanceNamespaceException.notFound(
            String.format("Catalog %s doesn't exist", catalog),
            HiveMetaStoreError.getType(),
            catalog,
            CommonUtil.formatCurrentStackTrace());
      }
    }

    // Check for child databases
    List<String> databases = clientPool.run(client -> client.getAllDatabases(catalog));
    if (!databases.isEmpty()) {
      if (behavior == DropNamespaceRequest.BehaviorEnum.RESTRICT) {
        throw LanceNamespaceException.badRequest(
            String.format(
                "Catalog %s is not empty. Contains %d databases: %s",
                catalog, databases.size(), databases),
            HiveMetaStoreError.getType(),
            catalog,
            CommonUtil.formatCurrentStackTrace());
      } else if (behavior == DropNamespaceRequest.BehaviorEnum.CASCADE) {
        // Drop all databases first
        for (String dbName : databases) {
          try {
            doDropDatabase(
                catalog,
                dbName,
                DropNamespaceRequest.ModeEnum.FAIL,
                DropNamespaceRequest.BehaviorEnum.CASCADE);
            LOG.info("Dropped database {}.{} during CASCADE operation", catalog, dbName);
          } catch (Exception e) {
            LOG.warn("Failed to drop database {}.{}: {}", catalog, dbName, e.getMessage());
            throw LanceNamespaceException.serviceUnavailable(
                String.format(
                    "Failed to drop database %s.%s during CASCADE operation: %s",
                    catalog, dbName, e.getMessage()),
                HiveMetaStoreError.getType(),
                String.format("%s.%s", catalog, dbName),
                CommonUtil.formatCurrentStackTrace());
          }
        }
      }
    }

    // Collect catalog properties
    Map<String, String> properties = new HashMap<>();
    if (catalogObj.getDescription() != null) {
      properties.put("description", catalogObj.getDescription());
    }
    if (catalogObj.getLocationUri() != null) {
      properties.put("catalog.location.uri", catalogObj.getLocationUri());
    }

    // Drop the catalog
    clientPool.run(
        client -> {
          client.dropCatalog(catalog);
          return null;
        });

    LOG.info("Successfully dropped catalog: {}", catalog);
    return properties;
  }

  private Map<String, String> doDropDatabase(
      String catalog,
      String db,
      DropNamespaceRequest.ModeEnum mode,
      DropNamespaceRequest.BehaviorEnum behavior)
      throws TException, InterruptedException {
    Database database = Hive3Util.getDatabaseOrNull(clientPool, catalog, db);
    if (database == null) {
      if (mode == DropNamespaceRequest.ModeEnum.SKIP) {
        return new HashMap<>();
      } else {
        throw LanceNamespaceException.notFound(
            String.format("Database %s.%s doesn't exist", catalog, db),
            HiveMetaStoreError.getType(),
            String.format("%s.%s", catalog, db),
            CommonUtil.formatCurrentStackTrace());
      }
    }

    // Check if database contains tables
    List<String> tables = doListTables(catalog, db);
    if (!tables.isEmpty()) {
      if (behavior == DropNamespaceRequest.BehaviorEnum.RESTRICT) {
        throw LanceNamespaceException.badRequest(
            String.format(
                "Database %s.%s is not empty. Contains %d tables: %s",
                catalog, db, tables.size(), tables),
            HiveMetaStoreError.getType(),
            String.format("%s.%s", catalog, db),
            CommonUtil.formatCurrentStackTrace());
      } else if (behavior == DropNamespaceRequest.BehaviorEnum.CASCADE) {
        // Drop all tables first
        for (String tableName : tables) {
          try {
            ObjectIdentifier tableId =
                ObjectIdentifier.of(Lists.newArrayList(catalog, db, tableName));
            doDropTable(tableId);
            LOG.info("Dropped table {}.{}.{} during CASCADE operation", catalog, db, tableName);
          } catch (Exception e) {
            LOG.warn("Failed to drop table {}.{}.{}: {}", catalog, db, tableName, e.getMessage());
            throw LanceNamespaceException.serviceUnavailable(
                String.format(
                    "Failed to drop table %s.%s.%s during CASCADE operation: %s",
                    catalog, db, tableName, e.getMessage()),
                HiveMetaStoreError.getType(),
                String.format("%s.%s.%s", catalog, db, tableName),
                CommonUtil.formatCurrentStackTrace());
          }
        }
      }
    }

    // Collect database properties
    Map<String, String> properties = new HashMap<>();
    if (database.getDescription() != null) {
      properties.put(Hive3NamespaceConfig.DATABASE_DESCRIPTION, database.getDescription());
    }
    if (database.getLocationUri() != null) {
      properties.put(Hive3NamespaceConfig.DATABASE_LOCATION_URI, database.getLocationUri());
    }
    if (database.getOwnerName() != null) {
      properties.put(Hive3NamespaceConfig.DATABASE_OWNER, database.getOwnerName());
    }
    if (database.getOwnerType() != null) {
      properties.put(Hive3NamespaceConfig.DATABASE_OWNER_TYPE, database.getOwnerType().name());
    }
    if (database.getParameters() != null) {
      properties.putAll(database.getParameters());
    }

    // Drop the database
    clientPool.run(
        client -> {
          client.dropDatabase(catalog, db, false, true, false);
          return null;
        });

    LOG.info("Successfully dropped database: {}.{}", catalog, db);
    return properties;
  }

  private String getDefaultTableLocation(String namespaceName, String tableName) {
    try {
      // Try to get the database location first
      Database db = Hive3Util.getDatabaseOrNull(clientPool, namespaceName.toLowerCase());
      if (db != null && db.getLocationUri() != null && !db.getLocationUri().isEmpty()) {
        String dbLocation = db.getLocationUri();
        if (!dbLocation.endsWith("/")) {
          dbLocation += "/";
        }
        return dbLocation + tableName.toLowerCase() + ".lance";
      }
    } catch (Exception e) {
      // Fall back to using root config if database location fails
      LOG.warn("Failed to get database location for {}, using root config", namespaceName, e);
    }

    // Use the configured root as fallback
    return String.format(
        "%s/%s/%s.lance", config.getRoot(), namespaceName.toLowerCase(), tableName.toLowerCase());
  }
}
