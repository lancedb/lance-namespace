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
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.lancedb.lance.namespace.hive.ErrorType.DatabaseAlreadyExist;
import static com.lancedb.lance.namespace.hive.ErrorType.HiveMetaStoreError;
import static com.lancedb.lance.namespace.hive.ErrorType.TableAlreadyExists;
import static com.lancedb.lance.namespace.hive.ErrorType.TableNotFound;

public class Hive2Adapter implements HiveAdapter {

  private final HiveClientPool clientPool;

  private final Configuration hadoopConf;

  private final BufferAllocator allocator;

  public Hive2Adapter(
      HiveClientPool clientPool, Configuration hadoopConf, BufferAllocator allocator) {
    this.clientPool = clientPool;
    this.hadoopConf = hadoopConf;
    this.allocator = allocator;
  }

  @Override
  public List<String> listNamespaces(ObjectIdentifier parent) {
    ValidationUtil.checkArgument(
        parent.levels() <= 2, "Expect a 2-level namespace but get %s", parent);

    try {
      if (parent.isRoot()) {
        return clientPool.run(IMetaStoreClient::getAllDatabases);
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
    ValidationUtil.checkArgument(id.levels() == 2, "Expect a 2-level namespace but get %s", id);

    try {
      String db = id.level(0).toLowerCase();
      createDatabase(db, mode, properties);
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }

  private void createDatabase(
      String dbName, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties)
      throws TException, InterruptedException {
    Database oldDb = HiveUtil.getDatabaseOrNull(clientPool, dbName);
    if (oldDb != null) {
      switch (mode) {
        case CREATE:
          throw LanceNamespaceException.conflict(
              String.format("Database %s already exist", dbName),
              DatabaseAlreadyExist.getType(),
              "",
              CommonUtil.formatCurrentStackTrace());
        case EXIST_OK:
          return;
        case OVERWRITE:
          clientPool.run(
              client -> {
                client.dropDatabase(dbName, false, true, false);
                return null;
              });
      }
    }

    // Create database
    Supplier<String> warehouseLocation =
        () ->
            ValidationUtil.checkNotNullOrEmptyString(
                hadoopConf.get(HiveConf.ConfVars.METASTOREWAREHOUSE.varname),
                String.format(
                    "Warehouse location is not set: %s=null",
                    HiveConf.ConfVars.METASTOREWAREHOUSE.varname));

    Database database = new Database();
    database.setName(dbName);
    HiveUtil.setDatabaseProperties(database, warehouseLocation, dbName, properties);

    clientPool.run(
        client -> {
          client.createDatabase(database);
          return null;
        });
  }

  @Override
  public Optional<String> describeTable(ObjectIdentifier id) {
    ValidationUtil.checkArgument(id.levels() == 3, "Expect db.table format but get %s", id);
    String db = id.level(0).toLowerCase();
    String table = id.level(1).toLowerCase();

    Optional<Table> hmsTable = HiveUtil.getTable(clientPool, db, table);
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
    ValidationUtil.checkArgument(id.levels() == 3, "Expect db.table format but get %s", id);

    // Check for unsupported managed_by=impl
    if (properties != null && "impl".equalsIgnoreCase(properties.get("managed_by"))) {
      throw new UnsupportedOperationException("managed_by=impl is not supported yet");
    }

    String db = id.level(0).toLowerCase();
    String tableName = id.level(1).toLowerCase();

    try {
      // Check if table already exists
      Optional<Table> existing = HiveUtil.getTable(clientPool, db, tableName);
      if (existing.isPresent()) {
        throw LanceNamespaceException.conflict(
            String.format("Table %s.%s already exists", db, tableName),
            TableAlreadyExists.getType(),
            String.format("%s.%s", db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      // Create HMS table
      Table table = new Table();
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
    ValidationUtil.checkArgument(id.levels() == 3, "Expect db.table format but get %s", id);
    String db = id.level(0).toLowerCase();
    String tableName = id.level(1).toLowerCase();

    try {
      Optional<Table> hmsTable = HiveUtil.getTable(clientPool, db, tableName);
      if (!hmsTable.isPresent()) {
        throw LanceNamespaceException.notFound(
            String.format("Table %s.%s does not exist", db, tableName),
            TableNotFound.getType(),
            String.format("%s.%s", db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      HiveUtil.validateLanceTable(hmsTable.get());
      String location = hmsTable.get().getSd().getLocation();

      clientPool.run(
          client -> {
            client.dropTable(db, tableName, false, true);
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
