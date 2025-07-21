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

import org.apache.hadoop.hive.metastore.IMetaStoreClient;

import java.lang.reflect.Method;
import java.util.Optional;

public enum HiveVersion {
  V2,
  V3;

  private static Optional<HiveVersion> version = Optional.empty();

  public static HiveVersion version() {
    if (version.isPresent()) {
      return version.get();
    }
    synchronized (HiveVersion.class) {
      if (version.isPresent()) {
        return version.get();
      }

      for (Method method : IMetaStoreClient.class.getMethods()) {
        if (method.getName().equals("getCatalogs")) {
          version = Optional.of(HiveVersion.V3);
          break;
        }
      }

      if (!version.isPresent()) {
        version = Optional.of(HiveVersion.V2);
      }

      return version.get();
    }
  }
}
