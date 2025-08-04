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

import com.lancedb.lance.namespace.LanceNamespaceException;
import com.lancedb.lance.namespace.util.CommonUtil;

import com.google.common.collect.Maps;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.NoSuchObjectException;
import org.apache.hadoop.hive.metastore.api.PrincipalType;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.lancedb.lance.namespace.hive2.Hive2ErrorType.HiveMetaStoreError;
import static com.lancedb.lance.namespace.hive2.Hive2ErrorType.InvalidLanceTable;

public class Hive2Util {

  public static Database getDatabaseOrNull(Hive2ClientPool clientPool, String db) {
    try {
      return clientPool.run(client -> client.getDatabase(db));
    } catch (NoSuchObjectException e) {
      return null;
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }

  public static void setDatabaseProperties(
      Database database,
      Supplier<String> warehouseLocation,
      String dbName,
      Map<String, String> properties) {
    Map<String, String> parameters = Maps.newHashMap();
    properties.forEach(
        (k, v) -> {
          if (k.equals(Hive2NamespaceConfig.DATABASE_DESCRIPTION)) {
            database.setDescription(v);
          } else if (k.equals(Hive2NamespaceConfig.DATABASE_LOCATION_URI)) {
            database.setLocationUri(v);
          } else if (v != null) {
            parameters.put(k, v);
          }
        });

    if (!database.isSetLocationUri()) {
      String location = databaseLocation(warehouseLocation.get(), dbName);
      database.setLocationUri(location);
      properties.put(Hive2NamespaceConfig.DATABASE_LOCATION_URI, location);
    }
    if (!database.isSetOwnerName()) {
      String user = hadoopUser();
      database.setOwnerName(user);
    }
    if (!database.isSetOwnerType()) {
      database.setOwnerType(PrincipalType.USER);
    }

    database.setParameters(parameters);
  }

  public static String databaseLocation(String warehouse, String dbName) {
    return String.format("%s/%s", CommonUtil.makeQualified(warehouse), dbName);
  }

  public static String hadoopUser() {
    try {
      return UserGroupInformation.getCurrentUser().getUserName();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Optional<Table> getTable(Hive2ClientPool clientPool, String db, String table) {
    try {
      return Optional.of(clientPool.run(client -> client.getTable(db, table)));
    } catch (NoSuchObjectException e) {
      return Optional.empty();
    } catch (TException | InterruptedException e) {
      if (e instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      throw LanceNamespaceException.serviceUnavailable(
          e.getMessage(), HiveMetaStoreError.getType(), "", CommonUtil.formatCurrentStackTrace());
    }
  }

  public static void validateLanceTable(Table table) {
    Map<String, String> params = table.getParameters();
    if (params == null || !"lance".equalsIgnoreCase(params.get("table_type"))) {
      throw LanceNamespaceException.badRequest(
          String.format(
              "Table %s.%s is not a Lance table", table.getDbName(), table.getTableName()),
          InvalidLanceTable.getType(),
          String.format("%s.%s", table.getDbName(), table.getTableName()),
          CommonUtil.formatCurrentStackTrace());
    }
  }

  public static Map<String, String> createLanceTableParams(Map<String, String> properties) {
    Map<String, String> params = new HashMap<>();
    if (properties != null) {
      params.putAll(properties);
    }
    params.put("table_type", "lance");
    params.putIfAbsent("managed_by", "storage");
    return params;
  }
}
