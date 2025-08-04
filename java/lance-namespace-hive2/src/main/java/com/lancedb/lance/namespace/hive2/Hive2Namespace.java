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
package com.lancedb.lance.namespace.hive2;

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
import com.lancedb.lance.namespace.model.DescribeTableRequest;
import com.lancedb.lance.namespace.model.DescribeTableResponse;
import com.lancedb.lance.namespace.model.DropTableRequest;
import com.lancedb.lance.namespace.model.DropTableResponse;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
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
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.lancedb.lance.namespace.hive2.Hive2ErrorType.DatabaseAlreadyExist;
import static com.lancedb.lance.namespace.hive2.Hive2ErrorType.HiveMetaStoreError;
import static com.lancedb.lance.namespace.hive2.Hive2ErrorType.TableAlreadyExists;
import static com.lancedb.lance.namespace.hive2.Hive2ErrorType.TableNotFound;

public class Hive2Namespace implements LanceNamespace, Configurable<Configuration> {
  private static final Logger LOG = LoggerFactory.getLogger(Hive2Namespace.class);

  protected Hive2ClientPool clientPool;
  protected Configuration hadoopConf;
  protected BufferAllocator allocator;

  public Hive2Namespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    if (hadoopConf == null) {
      LOG.warn("Hadoop configuration not set, using the default configuration.");
      hadoopConf = new Configuration();
    }

    int poolSize =
        configProperties.containsKey(Hive2NamespaceConfig.CLIENT_POOL_SIZE)
            ? Integer.parseInt(configProperties.get(Hive2NamespaceConfig.CLIENT_POOL_SIZE))
            : Hive2NamespaceConfig.CLIENT_POOL_SIZE_DEFAULT;
    this.clientPool = new Hive2ClientPool(poolSize, hadoopConf);
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(nsId.levels() <= 1, "Expect a 1-level namespace but get %s", nsId);

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
        !id.isRoot() && id.levels() <= 1, "Expect a 1-level namespace but get %s", id);

    doCreateNamespace(id, mode, properties);

    CreateNamespaceResponse response = new CreateNamespaceResponse();
    response.setProperties(properties);
    return response;
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        tableId.levels() == 2, "Expect 2-level table identifier but get %s", tableId);

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
        tableId.levels() == 2, "Expect 2-level table identifier but get %s", tableId);

    doCreateTable(tableId, schema, request.getLocation(), request.getProperties(), requestData);

    CreateTableResponse response = new CreateTableResponse();
    response.setLocation(request.getLocation());
    response.setVersion(1L);
    return response;
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        tableId.levels() == 2, "Expect 2-level table identifier but get %s", tableId);

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
        return clientPool.run(IMetaStoreClient::getAllDatabases);
      } else {
        return Lists.newArrayList();
      }
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to list namespaces: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }

  protected void doCreateNamespace(
      ObjectIdentifier id, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties) {

    try {
      String db = id.levelAtListPos(0).toLowerCase();
      createDatabase(db, mode, properties);
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to create namespace: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }

  private void createDatabase(
      String dbName, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties)
      throws TException, InterruptedException {
    Database oldDb = Hive2Util.getDatabaseOrNull(clientPool, dbName);
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
    Hive2Util.setDatabaseProperties(database, warehouseLocation, dbName, properties);

    clientPool.run(
        client -> {
          client.createDatabase(database);
          return null;
        });
  }

  protected Optional<String> doDescribeTable(ObjectIdentifier id) {
    String db = id.levelAtListPos(0).toLowerCase();
    String table = id.levelAtListPos(1).toLowerCase();

    Optional<Table> hmsTable = Hive2Util.getTable(clientPool, db, table);
    if (!hmsTable.isPresent()) {
      return Optional.empty();
    }

    Hive2Util.validateLanceTable(hmsTable.get());
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

    String db = id.levelAtListPos(0).toLowerCase();
    String tableName = id.levelAtListPos(1).toLowerCase();

    try {
      Optional<Table> existing = Hive2Util.getTable(clientPool, db, tableName);
      if (existing.isPresent()) {
        throw LanceNamespaceException.conflict(
            String.format("Table %s.%s already exists", db, tableName),
            TableAlreadyExists.getType(),
            String.format("%s.%s", db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      Table table = new Table();
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

      Map<String, String> params = Hive2Util.createLanceTableParams(properties);
      table.setParameters(params);

      clientPool.run(
          client -> {
            client.createTable(table);
            return null;
          });

      if (data != null && data.length > 0) {
        WriteParams writeParams =
            new WriteParams.Builder().withMode(WriteParams.WriteMode.CREATE).build();
        Dataset.create(allocator, location, schema, writeParams);
      }
    } catch (TException | InterruptedException | RuntimeException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to create table: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }

  protected String doDropTable(ObjectIdentifier id) {
    String db = id.levelAtListPos(0).toLowerCase();
    String tableName = id.levelAtListPos(1).toLowerCase();

    try {
      Optional<Table> hmsTable = Hive2Util.getTable(clientPool, db, tableName);
      if (!hmsTable.isPresent()) {
        throw LanceNamespaceException.notFound(
            String.format("Table %s.%s does not exist", db, tableName),
            TableNotFound.getType(),
            String.format("%s.%s", db, tableName),
            CommonUtil.formatCurrentStackTrace());
      }

      Hive2Util.validateLanceTable(hmsTable.get());
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
      String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
      throw LanceNamespaceException.serviceUnavailable(
          "Failed to drop table: " + errorMessage,
          HiveMetaStoreError.getType(),
          "",
          CommonUtil.formatCurrentStackTrace());
    }
  }
}
