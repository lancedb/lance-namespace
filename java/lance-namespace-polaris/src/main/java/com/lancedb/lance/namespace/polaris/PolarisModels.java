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
package com.lancedb.lance.namespace.polaris;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Map;

/** Data transfer objects for Polaris Generic Table API. */
public class PolarisModels {

  /** Request to create a generic table. */
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class CreateGenericTableRequest {
    private String name;
    private String format;
    private String baseLocation;
    private String doc;
    private Map<String, String> properties;

    public CreateGenericTableRequest() {}

    public CreateGenericTableRequest(
        String name,
        String format,
        String baseLocation,
        String doc,
        Map<String, String> properties) {
      this.name = name;
      this.format = format;
      this.baseLocation = baseLocation;
      this.doc = doc;
      this.properties = properties;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
      this.format = format;
    }

    public String getBaseLocation() {
      return baseLocation;
    }

    public void setBaseLocation(String baseLocation) {
      this.baseLocation = baseLocation;
    }

    public String getDoc() {
      return doc;
    }

    public void setDoc(String doc) {
      this.doc = doc;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }
  }

  /** Generic table information. */
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class GenericTable {
    private String name;
    private String format;
    private String baseLocation;
    private String doc;
    private Map<String, String> properties;

    public GenericTable() {}

    public GenericTable(
        String name,
        String format,
        String baseLocation,
        String doc,
        Map<String, String> properties) {
      this.name = name;
      this.format = format;
      this.baseLocation = baseLocation;
      this.doc = doc;
      this.properties = properties;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
      this.format = format;
    }

    public String getBaseLocation() {
      return baseLocation;
    }

    public void setBaseLocation(String baseLocation) {
      this.baseLocation = baseLocation;
    }

    public String getDoc() {
      return doc;
    }

    public void setDoc(String doc) {
      this.doc = doc;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }
  }

  /** Response when loading or creating a generic table. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class LoadGenericTableResponse {
    private GenericTable table;

    public LoadGenericTableResponse() {}

    public LoadGenericTableResponse(GenericTable table) {
      this.table = table;
    }

    public GenericTable getTable() {
      return table;
    }

    public void setTable(GenericTable table) {
      this.table = table;
    }
  }

  /** Table identifier. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TableIdentifier {
    private String namespace;
    private String name;

    public TableIdentifier() {}

    public TableIdentifier(String namespace, String name) {
      this.namespace = namespace;
      this.name = name;
    }

    public String getNamespace() {
      return namespace;
    }

    public void setNamespace(String namespace) {
      this.namespace = namespace;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  /** Response for listing generic tables. */
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ListGenericTablesResponse {
    @JsonProperty("next-page-token")
    private String nextPageToken;

    private List<TableIdentifier> identifiers;

    public ListGenericTablesResponse() {}

    public ListGenericTablesResponse(String nextPageToken, List<TableIdentifier> identifiers) {
      this.nextPageToken = nextPageToken;
      this.identifiers = identifiers;
    }

    public String getNextPageToken() {
      return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
      this.nextPageToken = nextPageToken;
    }

    public List<TableIdentifier> getIdentifiers() {
      return identifiers;
    }

    public void setIdentifiers(List<TableIdentifier> identifiers) {
      this.identifiers = identifiers;
    }
  }

  /** Error response from Polaris API. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class IcebergErrorResponse {
    private Error error;

    public IcebergErrorResponse() {}

    public IcebergErrorResponse(Error error) {
      this.error = error;
    }

    public Error getError() {
      return error;
    }

    public void setError(Error error) {
      this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {
      private String message;
      private String type;
      private int code;
      private String stack;

      public Error() {}

      public String getMessage() {
        return message;
      }

      public void setMessage(String message) {
        this.message = message;
      }

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      public int getCode() {
        return code;
      }

      public void setCode(int code) {
        this.code = code;
      }

      public String getStack() {
        return stack;
      }

      public void setStack(String stack) {
        this.stack = stack;
      }
    }
  }

  /** Namespace properties response. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class NamespaceResponse {
    private List<String> namespace;
    private Map<String, String> properties;

    public NamespaceResponse() {}

    public NamespaceResponse(List<String> namespace, Map<String, String> properties) {
      this.namespace = namespace;
      this.properties = properties;
    }

    public List<String> getNamespace() {
      return namespace;
    }

    public void setNamespace(List<String> namespace) {
      this.namespace = namespace;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }
  }

  /** List namespaces response. */
  @JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ListNamespacesResponse {
    @JsonProperty("next-page-token")
    private String nextPageToken;

    private List<Namespace> namespaces;

    public ListNamespacesResponse() {}

    public ListNamespacesResponse(String nextPageToken, List<Namespace> namespaces) {
      this.nextPageToken = nextPageToken;
      this.namespaces = namespaces;
    }

    public String getNextPageToken() {
      return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
      this.nextPageToken = nextPageToken;
    }

    public List<Namespace> getNamespaces() {
      return namespaces;
    }

    public void setNamespaces(List<Namespace> namespaces) {
      this.namespaces = namespaces;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Namespace {
      private List<String> namespace;

      public Namespace() {}

      public Namespace(List<String> namespace) {
        this.namespace = namespace;
      }

      public List<String> getNamespace() {
        return namespace;
      }

      public void setNamespace(List<String> namespace) {
        this.namespace = namespace;
      }
    }
  }

  /** Create namespace request. */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class CreateNamespaceRequest {
    private List<String> namespace;
    private Map<String, String> properties;

    public CreateNamespaceRequest() {}

    public CreateNamespaceRequest(List<String> namespace, Map<String, String> properties) {
      this.namespace = namespace;
      this.properties = properties;
    }

    public List<String> getNamespace() {
      return namespace;
    }

    public void setNamespace(List<String> namespace) {
      this.namespace = namespace;
    }

    public Map<String, String> getProperties() {
      return properties;
    }

    public void setProperties(Map<String, String> properties) {
      this.properties = properties;
    }
  }
}
