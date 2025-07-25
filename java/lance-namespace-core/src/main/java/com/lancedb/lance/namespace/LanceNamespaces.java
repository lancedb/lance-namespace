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
package com.lancedb.lance.namespace;

import com.lancedb.lance.namespace.util.DynConstructors;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class LanceNamespaces {

  public static final Map<String, String> NATIVE_IMPLEMENTATIONS =
      ImmutableMap.<String, String>builder()
          .put("hive", "com.lancedb.lance.namespace.hive.HiveNamespace")
          .put("glue", "com.lancedb.lance.namespace.glue.GlueNamespace")
          .build();

  private LanceNamespaces() {}

  /**
   * Create a Lance namespace implementation.
   *
   * @param impl implementation, either a short name for native impl, or a full Java class that
   *     implements {@link LanceNamespace}
   * @param properties input properties
   * @param conf additional system-specific configurations like Hadoop Configuration
   * @return a Lance namespace implementation
   */
  public static LanceNamespace create(String impl, Map<String, String> properties, Object conf) {
    String resolvedImpl = NATIVE_IMPLEMENTATIONS.getOrDefault(impl, impl);

    LanceNamespace ns;
    try {
      ns =
          (LanceNamespace)
              DynConstructors.builder(LanceNamespace.class)
                  .impl(resolvedImpl)
                  .buildChecked()
                  .newInstance();
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          String.format("Failed to construct namespace, impl: %s", impl), e);
    }

    if (ns instanceof Configurable && conf != null) {
      ((Configurable) ns).setConf(conf);
    }

    ns.initialize(properties);
    return ns;
  }
}
