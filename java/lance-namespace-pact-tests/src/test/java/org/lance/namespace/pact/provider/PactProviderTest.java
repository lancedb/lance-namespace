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
package org.lance.namespace.pact.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Pact Provider verification test for {@code lance-namespace-server}.
 *
 * <p>Phase 2 mode: loads pacts from the local {@code contract-pack/sample-pacts/} directory via
 * {@link PactFolder}. The path is relative to the module root ({@code
 * java/lance-namespace-pact-tests/}), so {@code ../../contract-pack/sample-pacts} resolves to
 * {@code contract-pack/sample-pacts/} at the repository root.
 *
 * <p>No real HTTP port is opened; all requests are dispatched through {@link MockMvc}.
 *
 * <p>Covers all 12 MVP API provider states (8 states, 22 interactions). Provider state strings MUST
 * match contract-pack/provider-states.lock.json verbatim.
 *
 * <p>To switch to Pact Broker mode, set the environment variables {@code PACT_BROKER_URL} and
 * {@code PACT_BROKER_TOKEN}, replace {@code @PactFolder} with {@code @PactBroker} (uncommenting the
 * block below), and activate the {@code pact-broker} Spring profile.
 */
@Provider("lance-namespace-server")
// ── Phase 2: offline pact folder ──────────────────────────────────────────────────────────────
// Relative to module root: java/lance-namespace-pact-tests/
//   → ../../contract-pack/sample-pacts  → {repo-root}/contract-pack/sample-pacts/
@PactFolder("../../contract-pack/sample-pacts")
// ── Broker variant (uncomment + supply env vars PACT_BROKER_URL / PACT_BROKER_TOKEN) ─────────
// @PactBroker(
//     url = "${PACT_BROKER_URL}",
//     authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
@SpringBootTest(
    classes = PactTestApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("pact")
public class PactProviderTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private InMemoryNamespaceFixtures fixtures;

  @BeforeEach
  void configurePactTarget(PactVerificationContext context) {
    context.setTarget(new MockMvcTestTarget(mockMvc));
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void verifyPact(PactVerificationContext context) {
    context.verifyInteraction();
  }

  // ─────────────────────────────────────────────────────────────────────────
  // Provider State hooks — state strings copied verbatim from
  // contract-pack/provider-states.lock.json (do NOT rename)
  // ─────────────────────────────────────────────────────────────────────────

  /**
   * Provider state: {@code "namespace 'ns_existing' has 3 tables"}
   *
   * <p>Populates {@code ns_existing} with child namespaces {@code child_a}, {@code child_b}, {@code
   * child_c} per {@code provider-states.lock.json} fixture section.
   */
  @State("namespace 'ns_existing' has 3 tables")
  public void setupNsExistingWith3Tables() {
    fixtures.reset();
    fixtures.createNamespace("ns_existing");
    fixtures.createChildNamespace("ns_existing", "child_a");
    fixtures.createChildNamespace("ns_existing", "child_b");
    fixtures.createChildNamespace("ns_existing", "child_c");
  }

  /**
   * Provider state: {@code "namespace 'ns_missing' does not exist"}
   *
   * <p>Clears all state so {@code ns_missing} is absent, causing the controller to return 404.
   */
  @State("namespace 'ns_missing' does not exist")
  public void setupNsMissingAbsent() {
    fixtures.reset();
    // teardownAction per provider-states.lock.json: no-op — reset() already ensures ns_missing
    // absent
  }

  /**
   * Provider state: {@code "namespace 'ns_empty' exists and is empty"}
   *
   * <p>Creates {@code ns_empty} with no children; used for DropNamespace success path.
   */
  @State("namespace 'ns_empty' exists and is empty")
  public void setupNsEmptyExists() {
    fixtures.reset();
    fixtures.createNamespace("ns_empty");
  }

  /**
   * Provider state: {@code "namespace 'ns_new' does not exist"}
   *
   * <p>Ensures {@code ns_new} is absent; used for CreateNamespace success path.
   */
  @State("namespace 'ns_new' does not exist")
  public void setupNsNewAbsent() {
    fixtures.reset();
    // ns_new must not exist — reset() ensures this
  }

  /**
   * Provider state: {@code "namespace 'ns_with_tables' has 2 tables"}
   *
   * <p>Creates {@code ns_with_tables} with tables {@code table_alpha} and {@code table_beta}; used
   * for ListTables success path.
   */
  @State("namespace 'ns_with_tables' has 2 tables")
  public void setupNsWithTables() {
    fixtures.reset();
    fixtures.createNamespace("ns_with_tables");
    fixtures.createTable("ns_with_tables", "table_alpha");
    fixtures.createTable("ns_with_tables", "table_beta");
  }

  /**
   * Provider state: {@code "table 'ns_with_tables.table_alpha' exists"}
   *
   * <p>Creates namespace {@code ns_with_tables} and registers table {@code table_alpha} at a known
   * location; used for table read/write operations.
   */
  @State("table 'ns_with_tables.table_alpha' exists")
  public void setupTableAlphaExists() {
    fixtures.reset();
    fixtures.createNamespace("ns_with_tables");
    fixtures.createTable("ns_with_tables", "table_alpha");
    fixtures.registerTableLocation(
        "ns_with_tables", "table_alpha", "s3://example/ns_with_tables/table_alpha");
  }

  /**
   * Provider state: {@code "table 'ns_existing.table_missing' does not exist"}
   *
   * <p>Creates namespace {@code ns_existing} but does NOT register table {@code table_missing};
   * used for table 404 error paths.
   */
  @State("table 'ns_existing.table_missing' does not exist")
  public void setupTableMissingAbsent() {
    fixtures.reset();
    fixtures.createNamespace("ns_existing");
    // table_missing must not exist in ns_existing
  }

  /**
   * Provider state: {@code "table 'ns_existing.table_new' does not exist"}
   *
   * <p>Creates namespace {@code ns_existing} with no tables; used for RegisterTable success path.
   */
  @State("table 'ns_existing.table_new' does not exist")
  public void setupTableNewAbsent() {
    fixtures.reset();
    fixtures.createNamespace("ns_existing");
    // table_new must not be registered yet
  }
}
