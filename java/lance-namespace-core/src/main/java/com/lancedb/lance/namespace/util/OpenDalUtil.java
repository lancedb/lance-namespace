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
package com.lancedb.lance.namespace.util;

import com.google.common.collect.ImmutableMap;
import org.apache.opendal.Operator;

/** Utility class for OpenDAL operations and configurations. */
public class OpenDalUtil {

  private OpenDalUtil() {
    // Utility class - no instantiation
  }

  /**
   * Initialize an OpenDAL operator based on the root path.
   *
   * @param root the root path/URI
   * @return configured OpenDAL operator
   */
  public static Operator initializeOperator(String root) {
    String[] schemeSplit = root.split("://", -1);

    // local file system path
    if (schemeSplit.length < 2) {
      return Operator.of("fs", ImmutableMap.of("root", root));
    }

    String scheme = normalizeScheme(schemeSplit[0]);
    String[] authoritySplit = schemeSplit[1].split("/", 2);
    String authority = authoritySplit[0];
    String path = authoritySplit.length > 1 ? authoritySplit[1] : "";

    switch (scheme) {
      case "s3":
      case "gcs":
        return Operator.of(scheme, ImmutableMap.of("root", path, "bucket", authority));
      case "azblob":
        return Operator.of(scheme, ImmutableMap.of("root", path, "CONTAINER", authority));
      default:
        return Operator.of(scheme, ImmutableMap.of("root", schemeSplit[1]));
    }
  }

  /**
   * Normalize scheme names with common aliases.
   *
   * @param scheme the original scheme
   * @return normalized scheme
   */
  public static String normalizeScheme(String scheme) {
    switch (scheme.toLowerCase()) {
      case "s3a":
      case "s3n":
        return "s3";
      case "abfs":
        return "azblob";
      case "file":
        return "fs";
      default:
        return scheme.toLowerCase();
    }
  }

  public static String stripTrailingSlash(String path) {
    if (path == null) {
      return null;
    }

    String result = path;
    while (result.endsWith("/")) {
      result = result.substring(0, result.length() - 1);
    }
    return result;
  }
}
