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
import com.lancedb.lance.namespace.ObjectIdentifier;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;
import com.lancedb.lance.namespace.util.PageUtil;
import com.lancedb.lance.namespace.util.ValidationUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.hadoop.conf.Configuration;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.lancedb.lance.namespace.hive.HiveNamespaceProperties.HIVE_CLIENT_POOL_SIZE;
import static com.lancedb.lance.namespace.hive.HiveNamespaceProperties.HIVE_CLIENT_POOL_SIZE_DEFAULT;

public class LanceHiveNamespace implements LanceNamespace, Configurable<Configuration> {
  private static final Logger LOG = LoggerFactory.getLogger(LanceHiveNamespace.class);

  private HiveClientPool clientPool;
  private String name;
  private Map<String, String> properties;
  private Configuration hadoopConf;

  public LanceHiveNamespace() {}

  @Override
  public void initialize(String name, Map<String, String> properties) {
    if (hadoopConf == null) {
      LOG.warn("Hadoop configuration not set, using the default configuration.");
      hadoopConf = new Configuration();
    }

    this.name = name;
    this.properties = properties;

    int poolSize =
        Integer.parseInt(
            properties.getOrDefault(HIVE_CLIENT_POOL_SIZE, HIVE_CLIENT_POOL_SIZE_DEFAULT));

    this.clientPool = new HiveClientPool(poolSize, hadoopConf);
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier parent = ObjectIdentifier.of(request.getParent());

    List<String> nss = null;
    switch (HiveVersion.version()) {
      case V2:
        nss = listNamespacesV2(parent);
        break;
      case V3:
        nss = listNamespacesV3(parent);
        break;
    }

    Collections.sort(nss);
    PageUtil.Page page =
        PageUtil.splitPage(
            nss, request.getPageToken(), PageUtil.normalizePageSize(request.getPageSize()));

    ListNamespacesResponse response = new ListNamespacesResponse();
    response.setNamespaces(Sets.newHashSet(page.items()));
    response.setNextPageToken(page.nextPageToken());
    return response;
  }

  private List<String> listNamespacesV2(ObjectIdentifier parent) {
    ValidationUtil.checkArgument(
        parent.size() <= 2, "Expect a 2-level namespace but get %s", parent);

    try {
      if (parent.empty()) {
        return clientPool.run(client -> client.getAllDatabases());
      } else {
        return Lists.newArrayList();
      }
    } catch (TException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

  private List<String> listNamespacesV3(ObjectIdentifier parent) {
    ValidationUtil.checkArgument(
        parent.size() <= 3, "Expect a 3-level namespace but get %s", parent);

    try {
      if (parent.empty()) {
        return clientPool.run(client -> client.getCatalogs());
      } else if (parent.size() == 1) {
        return clientPool.run(client -> client.getAllDatabases(parent.level(0)));
      } else {
        return Lists.newArrayList();
      }
    } catch (TException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setConf(Configuration conf) {
    this.hadoopConf = conf;
  }
}
