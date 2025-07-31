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

import com.lancedb.lance.namespace.model.ListNamespacesRequest;
import com.lancedb.lance.namespace.model.ListNamespacesResponse;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * An iterable that provides paginated access to namespace names from a LanceNamespace. This class
 * handles pagination automatically by making successive requests using page tokens until all
 * namespace names have been retrieved.
 */
public class ListNamespacesIterable implements Iterable<String> {

  private final LanceNamespace namespace;
  private final ListNamespacesRequest initialRequest;

  /**
   * Creates a new ListNamespacesIterable.
   *
   * @param namespace the LanceNamespace to list namespaces from
   * @param initialRequest the initial ListNamespacesRequest containing the parent namespace ID and
   *     optional limit
   */
  public ListNamespacesIterable(LanceNamespace namespace, ListNamespacesRequest initialRequest) {
    this.namespace = namespace;
    this.initialRequest = initialRequest;
  }

  @Override
  public Iterator<String> iterator() {
    return new ListNamespacesIterator();
  }

  private class ListNamespacesIterator implements Iterator<String> {
    private Iterator<String> currentPageIterator;
    private String nextPageToken;
    private boolean hasMorePages = true;
    private boolean initialized = false;

    @Override
    public boolean hasNext() {
      ensureInitialized();

      // If current page has more items, return true
      if (currentPageIterator != null && currentPageIterator.hasNext()) {
        return true;
      }

      // If no more pages available, return false
      if (!hasMorePages) {
        return false;
      }

      // Try to load the next page
      loadNextPage();

      // Return true if the newly loaded page has items
      return currentPageIterator != null && currentPageIterator.hasNext();
    }

    @Override
    public String next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more namespace names available");
      }

      return currentPageIterator.next();
    }

    private void ensureInitialized() {
      if (!initialized) {
        loadNextPage();
        initialized = true;
      }
    }

    private void loadNextPage() {
      try {
        // Create request for the current page
        ListNamespacesRequest request =
            new ListNamespacesRequest()
                .id(initialRequest.getId())
                .limit(initialRequest.getLimit())
                .pageToken(nextPageToken);

        // Make the request
        ListNamespacesResponse response = namespace.listNamespaces(request);

        // Extract namespace names
        Set<String> namespaces = response.getNamespaces();
        currentPageIterator = namespaces != null ? namespaces.iterator() : null;

        // Update pagination state
        nextPageToken = response.getPageToken();
        hasMorePages = nextPageToken != null && !nextPageToken.isEmpty();

      } catch (Exception e) {
        hasMorePages = false;
        currentPageIterator = null;
        throw new RuntimeException("Failed to load namespace names", e);
      }
    }
  }
}
