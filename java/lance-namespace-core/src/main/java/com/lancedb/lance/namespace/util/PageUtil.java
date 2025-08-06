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

import java.util.List;

public class PageUtil {
  private PageUtil() {}

  public static class Page {
    private List<String> items;
    private String nextPageToken;

    public Page(List<String> items, String nextPageToken) {
      this.items = items;
      this.nextPageToken = nextPageToken;
    }

    public List<String> items() {
      return items;
    }

    public String nextPageToken() {
      return nextPageToken;
    }
  }

  public static Page splitPage(List<String> items, String pageToken, int pageSize) {
    int left = 0;
    if (!ValidationUtil.isNullOrEmpty(pageToken)) {
      while (left < items.size()) {
        if (items.get(left).compareTo(pageToken) >= 0) {
          break;
        }
        left++;
      }
    }

    // Avoid integer overflow when pageSize is Integer.MAX_VALUE
    int right;
    if (pageSize == Integer.MAX_VALUE) {
      right = items.size();
    } else {
      right = Math.min(left + pageSize, items.size());
    }

    List<String> pageItems = items.subList(left, right);
    String nextPageToken = right == items.size() ? null : items.get(right);
    return new Page(pageItems, nextPageToken);
  }

  public static int normalizePageSize(Integer pageSize) {
    if (pageSize == null) {
      return Integer.MAX_VALUE;
    }

    ValidationUtil.checkArgument(pageSize > 0, "Page size must be greater than 0");
    return pageSize;
  }
}
