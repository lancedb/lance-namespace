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

import static com.lancedb.lance.namespace.hive.ErrorType.TableNotFound;

public class HiveNamespace implements LanceNamespace, Configurable<Configuration> {
  private static final Logger LOG = LoggerFactory.getLogger(HiveNamespace.class);

  private HiveClientPool clientPool;
  private Configuration hadoopConf;
  private HiveAdapter adapter;
  private BufferAllocator allocator;

  public HiveNamespace() {}

  @Override
  public void initialize(Map<String, String> configProperties, BufferAllocator allocator) {
    this.allocator = allocator;
    if (hadoopConf == null) {
      LOG.warn("Hadoop configuration not set, using the default configuration.");
      hadoopConf = new Configuration();
    }

    HiveNamespaceConfig config = new HiveNamespaceConfig(configProperties);
    this.clientPool = new HiveClientPool(config.getClientPoolSize(), hadoopConf);

    this.adapter = HiveAdapter.create(clientPool, hadoopConf, allocator);
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    List<String> nss = adapter.listNamespaces(nsId);

    Collections.sort(nss);
    PageUtil.Page page =
        PageUtil.splitPage(
            nss, request.getPageToken(), PageUtil.normalizePageSize(request.getLimit()));

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

    adapter.createNamespace(id, mode, properties);

    CreateNamespaceResponse response = new CreateNamespaceResponse();
    response.setProperties(properties);
    return response;
  }

  @Override
  public DescribeTableResponse describeTable(DescribeTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    Optional<String> location = adapter.describeTable(tableId);

    if (!location.isPresent()) {
      throw LanceNamespaceException.notFound(
          String.format("Table does not exist: %s", tableId),
          TableNotFound.getType(),
          tableId.toString(),
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

    adapter.createTable(
        tableId, schema, request.getLocation(), request.getProperties(), requestData);

    CreateTableResponse response = new CreateTableResponse();
    response.setLocation(request.getLocation());
    response.setVersion(1L);
    return response;
  }

  @Override
  public DropTableResponse dropTable(DropTableRequest request) {
    ObjectIdentifier tableId = ObjectIdentifier.of(request.getId());
    String location = adapter.dropTable(tableId);

    DropTableResponse response = new DropTableResponse();
    response.setLocation(location);
    response.setId(request.getId());
    return response;
  }

  @Override
  public void setConf(Configuration conf) {
    this.hadoopConf = conf;
  }
}
