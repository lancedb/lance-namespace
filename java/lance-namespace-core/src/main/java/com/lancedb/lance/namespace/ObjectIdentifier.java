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

import com.lancedb.lance.namespace.util.ValidationUtil;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ObjectIdentifier {

  private static final String DELIMITER_DEFAULT = ".";

  private final String[] levels;

  private ObjectIdentifier(String[] levels) {
    this.levels = levels;
  }

  public static ObjectIdentifier of(List<String> levels) {
    ValidationUtil.checkNotNull(levels, "Identifier must not be null");
    levels.stream()
        .forEach(
            level ->
                ValidationUtil.checkNotNullOrEmptyString(
                    level, "Invalid namespace containing empty string levels %s", levels));
    return new ObjectIdentifier(levels.toArray(new String[0]));
  }

  public static ObjectIdentifier of(String id) {
    return of(id, DELIMITER_DEFAULT);
  }

  public static ObjectIdentifier of(String id, String delimiter) {
    ValidationUtil.checkNotNull(id, "Identifier must not be null");
    ValidationUtil.checkNotNull(delimiter, "Identifier must not be null");
    if (id.equals(delimiter)) {
      return new ObjectIdentifier(new String[0]);
    }
    // Split by the delimiter literally(i.e., escape any regex special characters)
    return new ObjectIdentifier(id.split(Pattern.quote(delimiter)));
  }

  public String levelAtListPos(int pos) {
    return levels[pos];
  }

  public String stringStyleId() {
    return stringStyleId(DELIMITER_DEFAULT);
  }

  public String stringStyleId(String delimiter) {
    if (levels.length == 0) {
      return delimiter;
    }
    return String.join(delimiter, levels);
  }

  public List<String> listStyleId() {
    return ImmutableList.copyOf(levels);
  }

  public List<String> parent() {
    ValidationUtil.checkArgument(levels.length > 0, "Root namespace does not have a parent");
    if (levels.length == 1) {
      return ImmutableList.of();
    }
    return ImmutableList.copyOf(Arrays.copyOfRange(levels, 0, levels.length - 1));
  }

  public int levels() {
    return levels.length;
  }

  public boolean isRoot() {
    return levels.length == 0;
  }

  @Override
  public String toString() {
    return "[root -> " + String.join(" -> ", levels) + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ObjectIdentifier that = (ObjectIdentifier) o;
    return Objects.deepEquals(levels, that.levels);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(levels);
  }
}
