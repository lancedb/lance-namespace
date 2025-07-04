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

import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PropertyUtil {

  private PropertyUtil() {}

  public static boolean propertyAsBoolean(
      Map<String, String> properties, String property, boolean defaultValue) {
    String value = properties.get(property);
    if (value != null) {
      return Boolean.parseBoolean(value);
    }
    return defaultValue;
  }

  public static Boolean propertyAsNullableBoolean(Map<String, String> properties, String property) {
    String value = properties.get(property);
    if (value != null) {
      return Boolean.parseBoolean(value);
    }
    return null;
  }

  public static double propertyAsDouble(
      Map<String, String> properties, String property, double defaultValue) {
    String value = properties.get(property);
    if (value != null) {
      return Double.parseDouble(value);
    }
    return defaultValue;
  }

  public static int propertyAsInt(
      Map<String, String> properties, String property, int defaultValue) {
    String value = properties.get(property);
    if (value != null) {
      return Integer.parseInt(value);
    }
    return defaultValue;
  }

  public static Integer propertyAsNullableInt(Map<String, String> properties, String property) {
    String value = properties.get(property);
    if (value != null) {
      return Integer.parseInt(value);
    }
    return null;
  }

  public static long propertyAsLong(
      Map<String, String> properties, String property, long defaultValue) {
    String value = properties.get(property);
    if (value != null) {
      return Long.parseLong(value);
    }
    return defaultValue;
  }

  public static Long propertyAsNullableLong(Map<String, String> properties, String property) {
    String value = properties.get(property);
    if (value != null) {
      return Long.parseLong(value);
    }
    return null;
  }

  public static String propertyAsString(
      Map<String, String> properties, String property, String defaultValue) {
    String value = properties.get(property);
    if (value != null) {
      return value;
    }
    return defaultValue;
  }

  public static String propertyAsString(Map<String, String> properties, String property) {
    String value = properties.get(property);
    ValidationUtil.checkNotNull(value, "Property %s must be set", property);
    return value;
  }

  public static String propertyAsNullableString(Map<String, String> properties, String property) {
    return properties.get(property);
  }

  /**
   * Returns subset of provided map with keys matching the provided prefix. Matching is
   * case-sensitive and the matching prefix is removed from the keys in returned map.
   *
   * @param properties input map
   * @param prefix prefix to choose keys from input map
   * @return subset of input map with keys starting with provided prefix and prefix trimmed out
   */
  public static Map<String, String> propertiesWithPrefix(
      Map<String, String> properties, String prefix) {
    if (properties == null || properties.isEmpty()) {
      return Collections.emptyMap();
    }

    ValidationUtil.checkArgument(prefix != null, "Invalid prefix: null");

    return properties.entrySet().stream()
        .filter(e -> e.getKey().startsWith(prefix))
        .collect(Collectors.toMap(e -> e.getKey().replaceFirst(prefix, ""), Map.Entry::getValue));
  }

  /**
   * Filter the properties map by the provided key predicate.
   *
   * @param properties input map
   * @param keyPredicate predicate to choose keys from input map
   * @return subset of input map with keys satisfying the predicate
   */
  public static Map<String, String> filterProperties(
      Map<String, String> properties, Predicate<String> keyPredicate) {
    if (properties == null || properties.isEmpty()) {
      return Collections.emptyMap();
    }

    ValidationUtil.checkArgument(keyPredicate != null, "Invalid key pattern: null");

    return properties.entrySet().stream()
        .filter(e -> keyPredicate.test(e.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
