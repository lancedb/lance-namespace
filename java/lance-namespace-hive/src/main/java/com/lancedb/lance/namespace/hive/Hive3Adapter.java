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
package com.lancedb.lance.namespace.hive;

import com.lancedb.lance.Dataset;
import com.lancedb.lance.WriteParams;
import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.CreateNamespaceRequest;
import com.lancedb.lance.namespace.util.CommonUtil;
import com.lancedb.lance.namespace.util.HiveUtil;
import com.lancedb.lance.namespace.util.ValidationUtil;

import com.google.common.collect.Lists;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Catalog;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lancedb.lance.namespace.hive.ErrorType.CatalogAlreadyExist;
import static com.lancedb.lance.namespace.hive.ErrorType.DatabaseAlreadyExist;
import static com.lancedb.lance.namespace.hive.ErrorType.HiveMetaStoreError;
import static com.lancedb.lance.namespace.hive.ErrorType.TableAlreadyExists;
import static com.lancedb.lance.namespace.hive.ErrorType.TableNotFound;

public class Hive3Adapter implements HiveAdapter {

  private final HiveClientPool clientPool;

  private final BufferAllocator allocator;

  public Hive3Adapter(
      HiveClientPool clientPool, Configuration hadoopConf, BufferAllocator allocator) {
    this.clientPool = clientPool;
    this.allocator = allocator;
  }

  @Override
  public List<String> listNamespaces(ObjectIdentifier parent) {
    ValidationUtil.checkArgument(
        parent.levels() <= 3, "Expect a 3-level namespace but get %s", parent);

    try {
      if (parent.isRoot()) {
        return clientPool.run(IMetaStoreClient::getCatalogs);
      } else if (parent.levels() == 2) {
        return clientPool.run(client -> client.getAllDatabases(parent.level(0)));
      } else {
        return Lists.newArrayList();
      }
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }

  @Override
  public void createNamespace(
      ObjectIdentifier id, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties) {
    ValidationUtil.checkArgument(
        !id.isRoot() && id.levels() <= 3, "Expect a 3-level namespace but get %s", id);

    try {
      if (id.levels() == 2) {
        String name = id.level(0).toLowerCase();
        createCatalog(name, mode, properties);
      } else {
        String catalog = id.level(0).toLowerCase();
        String db = id.level(1).toLowerCase();
        createDatabase(catalog, db, mode, properties);
      }
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }

  private void createCatalog(
      String catalogName, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties)
      throws TException, InterruptedException {
    ValidationUtil.checkNotNullOrEmptyString(
        properties.get(HiveNamespaceConfig.CATALOG_LOCATION_URI),
        "Expect %s to be set",
        HiveNamespaceConfig.CATALOG_LOCATION_URI);

    Catalog oldCatalog = HiveUtil.getCatalogOrNull(clientPool, catalogName);

    if (oldCatalog != null) {
      switch (mode) {
        case CREATE:
          throw LanceNamespaceException.conflict(
              String.format("Catalog %s already exist", catalogName),
              CatalogAlreadyExist.getType(),
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

    // Create catalog
    Catalog catalog = new Catalog();

    catalog.setName(catalogName);
    catalog.setLocationUri(properties.get(HiveNamespaceConfig.CATALOG_LOCATION_URI));

    String description = properties.get(HiveNamespaceConfig.CATALOG_DESCRIPTION);
    if (description != null) {
      catalog.setDescription(description);
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
    Catalog catalog = HiveUtil.getCatalogOrThrowNotFoundException(clientPool, catalogName);

    Database oldDb = HiveUtil.getDatabaseOrNull(clientPool, catalogName, dbName);
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

    // Create database
    Database database = new Database();
    database.setCatalogName(catalogName);
    database.setName(dbName);
    HiveUtil.setDatabaseProperties(database, () -> catalog.getLocationUri(), dbName, properties);

    clientPool.run(
        client -> {
          client.createDatabase(database);
          return null;
        });
  }

  @Override
  public Optional<String> describeTable(ObjectIdentifier id) {
    ValidationUtil.checkArgument(id.levels() == 4, "Expect catalog.db.table format but get %s", id);
    String catalog = id.level(0).toLowerCase();
    String db = id.level(1).toLowerCase();
    String table = id.level(2).toLowerCase();

    Optional<Table> hmsTable = HiveUtil.getTable(clientPool, catalog, db, table);
    if (!hmsTable.isPresent()) {
      return Optional.empty();
    }

    HiveUtil.validateLanceTable(hmsTable.get());
    return Optional.of(hmsTable.get().getSd().getLocation());
  }

  @Override
  public void createTable(
      ObjectIdentifier id,
      Schema schema,
      String location,
      Map<String, String> properties,
      byte[] data) {
    ValidationUtil.checkArgument(id.levels() == 4, "Expect catalog.db.table format but get %s", id);

    // Check for unsupported managed_by=impl
    if (properties != null && "impl".equalsIgnoreCase(properties.get("managed_by"))) {
      throw new UnsupportedOperationException("managed_by=impl is not supported yet");
    }

    String catalog = id.level(0).toLowerCase();
    String db = id.level(1).toLowerCase();
    String tableName = id.level(2).toLowerCase();

    try {
      // Check if table already exists
      Optional<Table> existing = HiveUtil.getTable(clientPool, catalog, db, tableName);
      if (existing.isPresent()) {
        throw LanceNamespaceException.conflict(
            String.format("Table %s.%s.%s already exists", catalog, db, tableName),
            TableAlreadyExists.getType(),
            String.format("%s.%s.%s", catalog, db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      // Create HMS table
      Table table = new Table();
      table.setCatName(catalog);
      table.setDbName(db);
      table.setTableName(tableName);
      table.setTableType("EXTERNAL_TABLE");

      StorageDescriptor sd = new StorageDescriptor();
      sd.setLocation(location);
      table.setSd(sd);

      // Set Lance parameters
      Map<String, String> params = HiveUtil.createLanceTableParams(properties);
      table.setParameters(params);

      clientPool.run(
          client -> {
            client.createTable(table);
            return null;
          });

      // Create Lance dataset if data provided
      if (data != null && data.length > 0) {
        WriteParams writeParams =
            new WriteParams.Builder().withMode(WriteParams.WriteMode.CREATE).build();
        Dataset.create(allocator, location, schema, writeParams);
      }
    } catch (TException | InterruptedException | RuntimeException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }

  @Override
  public String dropTable(ObjectIdentifier id) {
    ValidationUtil.checkArgument(id.levels() == 4, "Expect catalog.db.table format but get %s", id);
    String catalog = id.level(0).toLowerCase();
    String db = id.level(1).toLowerCase();
    String tableName = id.level(2).toLowerCase();

    try {
      Optional<Table> hmsTable = HiveUtil.getTable(clientPool, catalog, db, tableName);
      if (!hmsTable.isPresent()) {
        throw LanceNamespaceException.notFound(
            String.format("Table %s.%s.%s does not exist", catalog, db, tableName),
            TableNotFound.getType(),
            String.format("%s.%s.%s", catalog, db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      HiveUtil.validateLanceTable(hmsTable.get());
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
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }
}
