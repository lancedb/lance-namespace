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
package com.lancedb.lance.namespace.hive3;

import com.google.errorprone.annotations.FormatMethod;
import org.apache.hadoop.hive.metastore.api.MetaException;

// Adapted from apache iceberg for Hive 3.x
public class Hive3MetaException extends RuntimeException {
  public Hive3MetaException(MetaException cause) {
    super(cause);
  }

  @FormatMethod
  public Hive3MetaException(MetaException cause, String message, Object... args) {
    super(String.format(message, args), cause);
  }

  @FormatMethod
  public Hive3MetaException(Throwable throwable, String message, Object... args) {
    super(String.format(message, args), throwable);
  }
}