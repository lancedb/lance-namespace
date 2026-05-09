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
package org.lance.namespace.pact.consumer;

import org.lance.namespace.client.apache.ApiClient;
import org.lance.namespace.client.apache.ApiException;
import org.lance.namespace.client.apache.api.NamespaceApi;
import org.lance.namespace.client.apache.api.TableApi;
import org.lance.namespace.model.CreateNamespaceRequest;
import org.lance.namespace.model.CreateNamespaceResponse;
import org.lance.namespace.model.DeregisterTableRequest;
import org.lance.namespace.model.DeregisterTableResponse;
import org.lance.namespace.model.DescribeNamespaceRequest;
import org.lance.namespace.model.DescribeNamespaceResponse;
import org.lance.namespace.model.DescribeTableRequest;
import org.lance.namespace.model.DescribeTableResponse;
import org.lance.namespace.model.DropNamespaceRequest;
import org.lance.namespace.model.DropNamespaceResponse;
import org.lance.namespace.model.DropTableResponse;
import org.lance.namespace.model.ListNamespacesResponse;
import org.lance.namespace.model.ListTablesResponse;
import org.lance.namespace.model.NamespaceExistsRequest;
import org.lance.namespace.model.RegisterTableRequest;
import org.lance.namespace.model.RegisterTableResponse;
import org.lance.namespace.model.TableExistsRequest;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslJsonRootValue;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pact consumer tests for the Lance Namespace Apache HTTP client.
 *
 * <p>Covers all 22 interactions from contract-pack/interactions.json (schema v1.1). Consumer:
 * lance-namespace-java-apache Provider: lance-namespace-server
 *
 * <p>Note: Request body is omitted from pact definitions so the mock server accepts any body. The
 * generated client serializes default fields (context, id, properties) which is normal — pact is
 * used to verify the server contract, not the exact request wire format.
 */
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "lance-namespace-server", pactVersion = PactSpecVersion.V4)
public class NamespaceApiPactTest {

  private static Map<String, Object> acceptJson() {
    Map<String, Object> h = new HashMap<String, Object>();
    h.put("Accept", "application/json");
    return h;
  }

  private static Map<String, Object> jsonHeaders() {
    Map<String, Object> h = new HashMap<String, Object>();
    h.put("Accept", "application/json");
    h.put("Content-Type", "application/json");
    return h;
  }

  private static Map<String, Object> contentTypeJson() {
    Map<String, Object> h = new HashMap<String, Object>();
    h.put("Content-Type", "application/json");
    return h;
  }

  // =========================================================================
  // 1. listNamespaces – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact listNamespaces_returns_3_items(PactBuilder builder) {
    return builder
        .given("namespace 'ns_existing' has 3 tables")
        .expectsToReceiveHttpInteraction(
            "List child namespaces under ns_existing returns 3 items",
            i ->
                i.withRequest(
                        req ->
                            req.method("GET")
                                .path("/v1/namespace/ns_existing/list")
                                .headers(acceptJson()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(
                                    new PactDslJsonBody()
                                        .minArrayLike(
                                            "namespaces",
                                            1,
                                            PactDslJsonRootValue.stringType("child_a"),
                                            3))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "listNamespaces_returns_3_items")
  void test_listNamespaces_returns_3_items(MockServer mockServer) throws Exception {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ListNamespacesResponse response = api.listNamespaces("ns_existing", null, null, null);
    assertNotNull(response);
    assertFalse(response.getNamespaces().isEmpty());
  }

  // =========================================================================
  // 2. listNamespaces – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact listNamespaces_returns_404(PactBuilder builder) {
    return builder
        .given("namespace 'ns_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "List child namespaces under non-existent namespace returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("GET")
                                .path("/v1/namespace/ns_missing/list")
                                .headers(acceptJson()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "NAMESPACE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.NamespaceNotFoundException",
                                        "Namespace ns_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "listNamespaces_returns_404")
  void test_listNamespaces_returns_404(MockServer mockServer) {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(ApiException.class, () -> api.listNamespaces("ns_missing", null, null, null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 3. describeNamespace – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact describeNamespace_returns_properties(PactBuilder builder) {
    return builder
        .given("namespace 'ns_existing' has 3 tables")
        .expectsToReceiveHttpInteraction(
            "Describe namespace ns_existing returns properties",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_existing/describe")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(
                                    new PactDslJsonBody()
                                        .like("properties", new HashMap<String, Object>()))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "describeNamespace_returns_properties")
  void test_describeNamespace_returns_properties(MockServer mockServer) throws Exception {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    DescribeNamespaceResponse response =
        api.describeNamespace("ns_existing", new DescribeNamespaceRequest(), null);
    assertNotNull(response);
  }

  // =========================================================================
  // 4. describeNamespace – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact describeNamespace_returns_404(PactBuilder builder) {
    return builder
        .given("namespace 'ns_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Describe non-existent namespace returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_missing/describe")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "NAMESPACE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.NamespaceNotFoundException",
                                        "Namespace ns_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "describeNamespace_returns_404")
  void test_describeNamespace_returns_404(MockServer mockServer) {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () -> api.describeNamespace("ns_missing", new DescribeNamespaceRequest(), null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 5. createNamespace – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact createNamespace_returns_200(PactBuilder builder) {
    return builder
        .given("namespace 'ns_new' does not exist")
        .expectsToReceiveHttpInteraction(
            "Create namespace ns_new returns created namespace",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_new/create")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(
                                    new PactDslJsonBody()
                                        .like("properties", new HashMap<String, Object>()))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "createNamespace_returns_200")
  void test_createNamespace_returns_200(MockServer mockServer) throws Exception {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    CreateNamespaceResponse response =
        api.createNamespace("ns_new", new CreateNamespaceRequest().mode("Create"), null);
    assertNotNull(response);
  }

  // =========================================================================
  // 6. createNamespace – 409
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact createNamespace_returns_409(PactBuilder builder) {
    return builder
        .given("namespace 'ns_existing' has 3 tables")
        .expectsToReceiveHttpInteraction(
            "Create already-existing namespace returns 409 conflict",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_existing/create")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(409)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "NAMESPACE_ALREADY_EXISTS",
                                        409,
                                        "org.lance.namespace.NamespaceAlreadyExistsException",
                                        "Namespace ns_existing already exists"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "createNamespace_returns_409")
  void test_createNamespace_returns_409(MockServer mockServer) {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () ->
                api.createNamespace(
                    "ns_existing", new CreateNamespaceRequest().mode("Create"), null));
    assertEquals(409, ex.getCode());
  }

  // =========================================================================
  // 7. dropNamespace – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact dropNamespace_returns_200(PactBuilder builder) {
    return builder
        .given("namespace 'ns_empty' exists and is empty")
        .expectsToReceiveHttpInteraction(
            "Drop empty namespace ns_empty returns 200",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_empty/drop")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(new PactDslJsonBody().nullValue("properties"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "dropNamespace_returns_200")
  void test_dropNamespace_returns_200(MockServer mockServer) throws Exception {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    DropNamespaceResponse response =
        api.dropNamespace(
            "ns_empty", new DropNamespaceRequest().mode("Fail").behavior("Restrict"), null);
    assertNotNull(response);
  }

  // =========================================================================
  // 8. dropNamespace – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact dropNamespace_returns_404(PactBuilder builder) {
    return builder
        .given("namespace 'ns_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Drop non-existent namespace returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_missing/drop")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "NAMESPACE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.NamespaceNotFoundException",
                                        "Namespace ns_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "dropNamespace_returns_404")
  void test_dropNamespace_returns_404(MockServer mockServer) {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () ->
                api.dropNamespace(
                    "ns_missing",
                    new DropNamespaceRequest().mode("Fail").behavior("Restrict"),
                    null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 9. namespaceExists – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact namespaceExists_returns_200(PactBuilder builder) {
    return builder
        .given("namespace 'ns_existing' has 3 tables")
        .expectsToReceiveHttpInteraction(
            "Check existence of ns_existing returns 200 no content",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_existing/exists")
                                .headers(jsonHeaders()))
                    .willRespondWith(resp -> resp.status(200)))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "namespaceExists_returns_200")
  void test_namespaceExists_returns_200(MockServer mockServer) throws Exception {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    api.namespaceExists("ns_existing", new NamespaceExistsRequest(), null);
  }

  // =========================================================================
  // 10. namespaceExists – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact namespaceExists_returns_404(PactBuilder builder) {
    return builder
        .given("namespace 'ns_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Check existence of ns_missing returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/namespace/ns_missing/exists")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "NAMESPACE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.NamespaceNotFoundException",
                                        "Namespace ns_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "namespaceExists_returns_404")
  void test_namespaceExists_returns_404(MockServer mockServer) {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () -> api.namespaceExists("ns_missing", new NamespaceExistsRequest(), null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 11. listTables – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact listTables_returns_2_items(PactBuilder builder) {
    return builder
        .given("namespace 'ns_with_tables' has 2 tables")
        .expectsToReceiveHttpInteraction(
            "List tables in ns_with_tables returns 2 tables",
            i ->
                i.withRequest(
                        req ->
                            req.method("GET")
                                .path("/v1/namespace/ns_with_tables/table/list")
                                .headers(acceptJson()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(
                                    new PactDslJsonBody()
                                        .minArrayLike(
                                            "tables",
                                            1,
                                            PactDslJsonRootValue.stringType("table_alpha"),
                                            2))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "listTables_returns_2_items")
  void test_listTables_returns_2_items(MockServer mockServer) throws Exception {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ListTablesResponse response = api.listTables("ns_with_tables", null, null, null, null);
    assertNotNull(response);
    assertFalse(response.getTables().isEmpty());
  }

  // =========================================================================
  // 12. listTables – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact listTables_returns_404(PactBuilder builder) {
    return builder
        .given("namespace 'ns_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "List tables in non-existent namespace returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("GET")
                                .path("/v1/namespace/ns_missing/table/list")
                                .headers(acceptJson()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "NAMESPACE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.NamespaceNotFoundException",
                                        "Namespace ns_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "listTables_returns_404")
  void test_listTables_returns_404(MockServer mockServer) {
    NamespaceApi api = new NamespaceApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class, () -> api.listTables("ns_missing", null, null, null, null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 13. describeTable – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact describeTable_returns_200(PactBuilder builder) {
    return builder
        .given("table 'ns_with_tables.table_alpha' exists")
        .expectsToReceiveHttpInteraction(
            "Describe table_alpha in ns_with_tables returns table info",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_with_tables.table_alpha/describe")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(
                                    new PactDslJsonBody()
                                        .stringType(
                                            "location",
                                            "s3://example/ns_with_tables/table_alpha"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "describeTable_returns_200")
  void test_describeTable_returns_200(MockServer mockServer) throws Exception {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    DescribeTableResponse response =
        api.describeTable(
            "ns_with_tables.table_alpha", new DescribeTableRequest(), null, null, null, null);
    assertNotNull(response);
    assertNotNull(response.getLocation());
  }

  // =========================================================================
  // 14. describeTable – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact describeTable_returns_404(PactBuilder builder) {
    return builder
        .given("table 'ns_existing.table_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Describe non-existent table returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_existing.table_missing/describe")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "TABLE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.TableNotFoundException",
                                        "Table ns_existing.table_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "describeTable_returns_404")
  void test_describeTable_returns_404(MockServer mockServer) {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () ->
                api.describeTable(
                    "ns_existing.table_missing",
                    new DescribeTableRequest(),
                    null,
                    null,
                    null,
                    null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 15. tableExists – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact tableExists_returns_200(PactBuilder builder) {
    return builder
        .given("table 'ns_with_tables.table_alpha' exists")
        .expectsToReceiveHttpInteraction(
            "Check existence of table_alpha in ns_with_tables returns 200",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_with_tables.table_alpha/exists")
                                .headers(jsonHeaders()))
                    .willRespondWith(resp -> resp.status(200)))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "tableExists_returns_200")
  void test_tableExists_returns_200(MockServer mockServer) throws Exception {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    api.tableExists("ns_with_tables.table_alpha", new TableExistsRequest(), null);
  }

  // =========================================================================
  // 16. tableExists – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact tableExists_returns_404(PactBuilder builder) {
    return builder
        .given("table 'ns_existing.table_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Check existence of non-existent table returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_existing.table_missing/exists")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "TABLE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.TableNotFoundException",
                                        "Table ns_existing.table_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "tableExists_returns_404")
  void test_tableExists_returns_404(MockServer mockServer) {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () -> api.tableExists("ns_existing.table_missing", new TableExistsRequest(), null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 17. dropTable – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact dropTable_returns_200(PactBuilder builder) {
    return builder
        .given("table 'ns_with_tables.table_alpha' exists")
        .expectsToReceiveHttpInteraction(
            "Drop table_alpha in ns_with_tables returns 200",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_with_tables.table_alpha/drop")
                                .headers(acceptJson()))
                    .willRespondWith(
                        resp -> resp.status(200).headers(contentTypeJson()).body("{}")))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "dropTable_returns_200")
  void test_dropTable_returns_200(MockServer mockServer) throws Exception {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    DropTableResponse response = api.dropTable("ns_with_tables.table_alpha", null);
    assertNotNull(response);
  }

  // =========================================================================
  // 18. dropTable – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact dropTable_returns_404(PactBuilder builder) {
    return builder
        .given("table 'ns_existing.table_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Drop non-existent table returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_existing.table_missing/drop")
                                .headers(acceptJson()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "TABLE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.TableNotFoundException",
                                        "Table ns_existing.table_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "dropTable_returns_404")
  void test_dropTable_returns_404(MockServer mockServer) {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(ApiException.class, () -> api.dropTable("ns_existing.table_missing", null));
    assertEquals(404, ex.getCode());
  }

  // =========================================================================
  // 19. registerTable – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact registerTable_returns_200(PactBuilder builder) {
    return builder
        .given("namespace 'ns_existing' has 3 tables")
        .expectsToReceiveHttpInteraction(
            "Register new table in ns_existing returns 200",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_existing.table_new/register")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(200)
                                .headers(contentTypeJson())
                                .body(
                                    new PactDslJsonBody()
                                        .stringType(
                                            "location", "s3://example/ns_existing/table_new"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "registerTable_returns_200")
  void test_registerTable_returns_200(MockServer mockServer) throws Exception {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    RegisterTableResponse response =
        api.registerTable(
            "ns_existing.table_new",
            new RegisterTableRequest()
                .location("s3://example/ns_existing/table_new")
                .mode("Create"),
            null);
    assertNotNull(response);
    assertNotNull(response.getLocation());
  }

  // =========================================================================
  // 20. registerTable – 409
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact registerTable_returns_409(PactBuilder builder) {
    return builder
        .given("table 'ns_with_tables.table_alpha' exists")
        .expectsToReceiveHttpInteraction(
            "Register already-registered table returns 409",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_with_tables.table_alpha/register")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(409)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "TABLE_ALREADY_EXISTS",
                                        409,
                                        "org.lance.namespace.TableAlreadyExistsException",
                                        "Table ns_with_tables.table_alpha already exists"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "registerTable_returns_409")
  void test_registerTable_returns_409(MockServer mockServer) {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () ->
                api.registerTable(
                    "ns_with_tables.table_alpha",
                    new RegisterTableRequest()
                        .location("s3://example/ns_with_tables/table_alpha")
                        .mode("Create"),
                    null));
    assertEquals(409, ex.getCode());
  }

  // =========================================================================
  // 21. deregisterTable – 200
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact deregisterTable_returns_200(PactBuilder builder) {
    return builder
        .given("table 'ns_with_tables.table_alpha' exists")
        .expectsToReceiveHttpInteraction(
            "Deregister table_alpha from ns_with_tables returns 200",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_with_tables.table_alpha/deregister")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp -> resp.status(200).headers(contentTypeJson()).body("{}")))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "deregisterTable_returns_200")
  void test_deregisterTable_returns_200(MockServer mockServer) throws Exception {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    DeregisterTableResponse response =
        api.deregisterTable("ns_with_tables.table_alpha", new DeregisterTableRequest(), null);
    assertNotNull(response);
  }

  // =========================================================================
  // 22. deregisterTable – 404
  // =========================================================================

  @Pact(consumer = "lance-namespace-java-apache")
  public V4Pact deregisterTable_returns_404(PactBuilder builder) {
    return builder
        .given("table 'ns_existing.table_missing' does not exist")
        .expectsToReceiveHttpInteraction(
            "Deregister non-existent table returns 404",
            i ->
                i.withRequest(
                        req ->
                            req.method("POST")
                                .path("/v1/table/ns_existing.table_missing/deregister")
                                .headers(jsonHeaders()))
                    .willRespondWith(
                        resp ->
                            resp.status(404)
                                .headers(contentTypeJson())
                                .body(
                                    ErrorResponseDsl.body(
                                        "TABLE_NOT_FOUND",
                                        404,
                                        "org.lance.namespace.TableNotFoundException",
                                        "Table ns_existing.table_missing does not exist"))))
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "deregisterTable_returns_404")
  void test_deregisterTable_returns_404(MockServer mockServer) {
    TableApi api = new TableApi(new ApiClient().setBasePath(mockServer.getUrl()));
    ApiException ex =
        assertThrows(
            ApiException.class,
            () ->
                api.deregisterTable(
                    "ns_existing.table_missing", new DeregisterTableRequest(), null));
    assertEquals(404, ex.getCode());
  }
}
