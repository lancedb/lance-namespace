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
package org.lance.namespace.errors;

import javax.annotation.Nullable;

/** Thrown when a table index with the specified name already exists. */
public class TableIndexAlreadyExistsException extends LanceNamespaceException {
  public TableIndexAlreadyExistsException(String message) {
    super(ErrorCode.TABLE_INDEX_ALREADY_EXISTS, message);
  }

  public TableIndexAlreadyExistsException(String message, Throwable cause) {
    super(ErrorCode.TABLE_INDEX_ALREADY_EXISTS, message, cause);
  }

  public TableIndexAlreadyExistsException(
      String message, @Nullable String detail, @Nullable String instance) {
    super(ErrorCode.TABLE_INDEX_ALREADY_EXISTS, message, detail, instance);
  }
}
