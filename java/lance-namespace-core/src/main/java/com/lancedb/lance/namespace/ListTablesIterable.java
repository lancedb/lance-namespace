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

import com.lancedb.lance.namespace.model.ListTablesRequest;
import com.lancedb.lance.namespace.model.ListTablesResponse;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * An iterable that provides paginated access to table names from a LanceNamespace.
 * This class handles pagination automatically by making successive requests using page tokens
 * until all table names have been retrieved.
 */
public class ListTablesIterable implements Iterable<String> {

  private final LanceNamespace namespace;
  private final ListTablesRequest initialRequest;

  /**
   * Creates a new ListTablesIterable.
   *
   * @param namespace the LanceNamespace to list tables from
   * @param initialRequest the initial ListTablesRequest containing the namespace ID and optional limit
   */
  public ListTablesIterable(LanceNamespace namespace, ListTablesRequest initialRequest) {
    this.namespace = namespace;
    this.initialRequest = initialRequest;
  }

  @Override
  public Iterator<String> iterator() {
    return new ListTablesIterator();
  }

  private class ListTablesIterator implements Iterator<String> {
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
        throw new NoSuchElementException("No more table names available");
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
        ListTablesRequest request = new ListTablesRequest()
            .id(initialRequest.getId())
            .limit(initialRequest.getLimit())
            .pageToken(nextPageToken);

        // Make the request
        ListTablesResponse response = namespace.listTables(request);
        
        // Extract table names
        Set<String> tables = response.getTables();
        currentPageIterator = tables != null ? tables.iterator() : null;
        
        // Update pagination state
        nextPageToken = response.getPageToken();
        hasMorePages = nextPageToken != null && !nextPageToken.isEmpty();
        
      } catch (Exception e) {
        hasMorePages = false;
        currentPageIterator = null;
        throw new RuntimeException("Failed to load table names", e);
      }
    }
  }
}