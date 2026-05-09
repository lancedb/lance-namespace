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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pact Provider State controller — exposes fixture-setup helpers as named methods.
 *
 * <p>For the MockMvc-based PoC, the actual {@link au.com.dius.pact.provider.junitsupport.State}
 * annotations live in {@link PactProviderTest} (which can {@code @Autowired} {@link
 * InMemoryNamespaceFixtures} directly). This class centralises the state-setup logic so that a
 * future HTTP-target variant can call these methods from a provider-state endpoint.
 *
 * <p>State strings are copied verbatim from {@code contract-pack/provider-states.lock.json} — do
 * NOT rename them.
 *
 * <p>Active only when the {@code pact} Spring profile is enabled.
 */
@Profile("pact")
@RestController
public class PactStateController {

  private final InMemoryNamespaceFixtures fixtures;

  @Autowired
  public PactStateController(InMemoryNamespaceFixtures fixtures) {
    this.fixtures = fixtures;
  }

  /**
   * Sets up provider state: {@code "namespace 'ns_existing' has 3 tables"}.
   *
   * <p>Creates namespace {@code ns_existing} and populates it with three child namespaces {@code
   * child_a}, {@code child_b}, {@code child_c} as specified in {@code provider-states.lock.json}
   * fixture.
   */
  public void setupNsExistingWith3Tables() {
    fixtures.reset();
    fixtures.createNamespace("ns_existing");
    fixtures.createChildNamespace("ns_existing", "child_a");
    fixtures.createChildNamespace("ns_existing", "child_b");
    fixtures.createChildNamespace("ns_existing", "child_c");
  }

  /**
   * Sets up provider state: {@code "namespace 'ns_missing' does not exist"}.
   *
   * <p>Clears all state to ensure {@code ns_missing} is absent from the in-memory store. Per {@code
   * provider-states.lock.json}: teardownAction is "no-op".
   */
  public void setupNsMissingAbsent() {
    fixtures.reset();
  }
}
