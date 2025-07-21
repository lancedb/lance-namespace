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
package com.lancedb.lance.namespace.glue;

import com.lancedb.lance.namespace.GlueProperties;
import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.glue.GlueClient;
import software.amazon.awssdk.services.glue.model.Database;
import software.amazon.awssdk.services.glue.model.GetDatabasesRequest;
import software.amazon.awssdk.services.glue.model.GetDatabasesResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestLanceGlueNamespace {

  @Mock private GlueClient glue;

  private LanceGlueNamespace glueNamespace;

  @BeforeEach
  public void before() {
    this.glueNamespace = new LanceGlueNamespace();
    GlueProperties glueProperties = new GlueProperties();
    glueNamespace.initialize("glue", glueProperties, glue);
  }

  @Test
  public void testBasicListNamespaces() {
    when(glue.getDatabases(any(GetDatabasesRequest.class)))
        .thenReturn(
            GetDatabasesResponse.builder()
                .databaseList(
                    Database.builder().name("db1").build(), Database.builder().name("db2").build())
                .build());

    ListNamespacesRequest request = new ListNamespacesRequest();
    ListNamespacesResponse response = glueNamespace.listNamespaces(request);

    assertNotNull(response.getNamespaces());
    assertEquals(2, response.getNamespaces().size());
    assertEquals(Sets.newHashSet("db1", "db2"), response.getNamespaces());
    assertNull(response.getNextPageToken());
  }

  @Test
  void testListingAllPagination() {
    GetDatabasesResponse respOne =
        GetDatabasesResponse.builder()
            .databaseList(
                Database.builder().name("db1").build(), Database.builder().name("db2").build())
            .nextToken("tkn1")
            .build();

    GetDatabasesResponse respTwo =
        GetDatabasesResponse.builder()
            .databaseList(Database.builder().name("db3").build())
            .nextToken(null)
            .build();

    when(glue.getDatabases(any(GetDatabasesRequest.class))).thenReturn(respOne, respTwo);

    ListNamespacesResponse resp = glueNamespace.listNamespaces(new ListNamespacesRequest());
    assertEquals(Sets.newHashSet("db1", "db2", "db3"), resp.getNamespaces());
    assertNull(resp.getNextPageToken());
  }

  @Test
  void testEmptyListNamespaces() {
    when(glue.getDatabases(any(GetDatabasesRequest.class)))
        .thenReturn(GetDatabasesResponse.builder().build());

    ListNamespacesRequest request = new ListNamespacesRequest();
    ListNamespacesResponse response = glueNamespace.listNamespaces(request);

    assertNotNull(response.getNamespaces());
    assertEquals(0, response.getNamespaces().size());
    assertNull(response.getNextPageToken());
  }

  @Test
  void testNestedParentThrows() {
    ListNamespacesRequest req = new ListNamespacesRequest().parent(Lists.newArrayList("a", "b"));
    assertThrows(RuntimeException.class, () -> glueNamespace.listNamespaces(req));
  }
}
