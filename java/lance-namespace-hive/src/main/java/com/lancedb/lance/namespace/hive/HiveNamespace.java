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
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HiveNamespace implements LanceNamespace, Configurable<Configuration> {
  private static final Logger LOG = LoggerFactory.getLogger(HiveNamespace.class);

  private HiveClientPool clientPool;
  private Configuration hadoopConf;

  public HiveNamespace() {}

  @Override
  public void initialize(Map<String, String> properties) {
    if (hadoopConf == null) {
      LOG.warn("Hadoop configuration not set, using the default configuration.");
      hadoopConf = new Configuration();
    }

    HiveNamespaceConfig config = new HiveNamespaceConfig(properties);
    this.clientPool = new HiveClientPool(config.getClientPoolSize(), hadoopConf);
  }

  @Override
  public ListNamespacesResponse listNamespaces(ListNamespacesRequest request) {
    ObjectIdentifier nsId = ObjectIdentifier.of(request.getId());

    List<String> nss = null;
    switch (HiveVersion.version()) {
      case V2:
        nss = listNamespacesV2(nsId);
        break;
      case V3:
        nss = listNamespacesV3(nsId);
        break;
    }

    Collections.sort(nss);
    PageUtil.Page page =
        PageUtil.splitPage(
            nss, request.getPageToken(), PageUtil.normalizePageSize(request.getLimit()));

    ListNamespacesResponse response = new ListNamespacesResponse();
    response.setNamespaces(Sets.newHashSet(page.items()));
    response.setPageToken(page.nextPageToken());
    return response;
  }

  private List<String> listNamespacesV2(ObjectIdentifier nsId) {
    ValidationUtil.checkArgument(nsId.levels() <= 2, "Expect a 2-level namespace but get %s", nsId);

    try {
      if (nsId.isRoot()) {
        return clientPool.run(IMetaStoreClient::getAllDatabases);
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
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setConf(Configuration conf) {
    this.hadoopConf = conf;
  }
}
