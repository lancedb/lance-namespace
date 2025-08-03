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
package com.lancedb.lance.namespace.hive.base;

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

import com.google.common.collect.Sets;
import org.apache.arrow.memory.BufferAllocator;
import org.apache.arrow.vector.types.pojo.Schema;
import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lancedb.lance.namespace.hive.base.ErrorType.TableNotFound;

public abstract class BaseHiveNamespace implements LanceNamespace, Configurable<Configuration> {
  private static final Logger LOG = LoggerFactory.getLogger(BaseHiveNamespace.class);

  protected HiveClientPool clientPool;
  protected Configuration hadoopConf;
  protected BufferAllocator allocator;

  public BaseHiveNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    if (hadoopConf == null) {
      LOG.warn("Hadoop configuration not set, using the default configuration.");
      hadoopConf = new Configuration();
    }

    HiveNamespaceConfig config = new HiveNamespaceConfig(configProperties);
    this.clientPool = new HiveClientPool(config.getClientPoolSize(), hadoopConf);
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        nsId.levels() <= getMaxNamespaceLevel(),
        "Expect a %d-level namespace but get %s",
        getMaxNamespaceLevel(),
        nsId);

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
        !id.isRoot() && id.levels() <= getMaxNamespaceLevel(),
        "Expect a %d-level namespace but get %s",
        getMaxNamespaceLevel(),
        id);

    doCreateNamespace(id, mode, properties);

    CreateNamespaceResponse response = new CreateNamespaceResponse();
    response.setProperties(properties);
    return response;
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());

    ValidationUtil.checkArgument(
        tableId.levels() == getExpectedTableLevel(),
        "Expect %d-level table identifier but get %s",
        getExpectedTableLevel(),
        tableId);

    Optional<String> location = doDescribeTable(tableId);

    if (!location.isPresent()) {
      throw LanceNamespaceException.notFound(
          String.format("Table does not exist: %s", tableId.idStringStyle()),
          TableNotFound.getType(),
          tableId.idStringStyle(),
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
        tableId.levels() == getExpectedTableLevel(),
        "Expect %d-level table identifier but get %s",
        getExpectedTableLevel(),
        tableId);

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
        tableId.levels() == getExpectedTableLevel(),
        "Expect %d-level table identifier but get %s",
        getExpectedTableLevel(),
        tableId);

    String location = doDropTable(tableId);

    DropTableResponse response = new DropTableResponse();
    response.setLocation(location);
    response.setId(request.getId());
    return response;
  }

  @Override
  public void setConf(Configuration conf) {
    this.hadoopConf = conf;
  }

  protected abstract int getMaxNamespaceLevel();

  protected abstract int getExpectedTableLevel();

  protected abstract List<String> doListNamespaces(ObjectIdentifier parent);

  protected abstract void doCreateNamespace(
      ObjectIdentifier id, CreateNamespaceRequest.ModeEnum mode, Map<String, String> properties);

  protected abstract Optional<String> doDescribeTable(ObjectIdentifier id);

  protected abstract void doCreateTable(
      ObjectIdentifier id,
      Schema schema,
      String location,
      Map<String, String> properties,
      byte[] data);

  protected abstract String doDropTable(ObjectIdentifier id);
}
