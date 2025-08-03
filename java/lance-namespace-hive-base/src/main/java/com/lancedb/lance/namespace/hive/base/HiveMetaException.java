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
package com.lancedb.lance.namespace.hive.base;

import com.google.errorprone.annotations.FormatMethod;
import org.apache.hadoop.hive.metastore.api.MetaException;

// Copied from apache iceberg.
// https://github.com/apache/iceberg/blob/main/hive-metastore/src/main/java/org/apache/iceberg/hive/RuntimeMetaException.java
public class HiveMetaException extends RuntimeException {
  public HiveMetaException(MetaException cause) {
    super(cause);
  }

  @FormatMethod
  public HiveMetaException(MetaException cause, String message, Object... args) {
    super(String.format(message, args), cause);
  }

  @FormatMethod
  public HiveMetaException(Throwable throwable, String message, Object... args) {
    super(String.format(message, args), throwable);
  }
}
