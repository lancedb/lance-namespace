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

import com.google.common.base.Joiner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class CommonUtil {
  private CommonUtil() {}

  public static String formatCurrentStackTrace() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    return formatStackTrace(stackTrace);
  }

  public static String formatStackTrace(StackTraceElement[] stackTrace) {
    return Joiner.on("\n\t").join(Arrays.copyOfRange(stackTrace, 1, stackTrace.length));
  }

  /**
   * Parse absolute path string into qualified path. Qualified path format is
   * '{scheme}://{authority}/{path}'.
   */
  public static String makeQualified(String absolutePath) {
    try {
      // Handle absolute local file paths that start with /
      String uriString = absolutePath.startsWith("/") ? "file://" + absolutePath : absolutePath;

      URI uri = new URI(uriString);
      ValidationUtil.checkArgument(
          uri.isAbsolute(), "Couldn't parse %s because it is not absolute.", absolutePath);

      String scheme = uri.getScheme();
      String authority = uri.getAuthority() == null ? "" : uri.getAuthority();
      String path = uri.getPath();
      return String.format("%s://%s%s", scheme, authority, path);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(String.format("Invalid path %s", absolutePath), e);
    }
  }
}
