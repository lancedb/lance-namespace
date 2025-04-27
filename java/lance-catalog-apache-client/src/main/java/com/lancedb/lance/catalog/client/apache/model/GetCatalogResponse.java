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
package com.lancedb.lance.catalog.client.apache.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** GetCatalogResponse */
@JsonPropertyOrder({
  GetCatalogResponse.JSON_PROPERTY_CATALOG,
  GetCatalogResponse.JSON_PROPERTY_PROPERTIES
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class GetCatalogResponse {
  public static final String JSON_PROPERTY_CATALOG = "catalog";
  @javax.annotation.Nonnull private String catalog;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  @javax.annotation.Nullable private Map<String, String> properties = new HashMap<>();

  public GetCatalogResponse() {}

  public GetCatalogResponse catalog(@javax.annotation.Nonnull String catalog) {

    this.catalog = catalog;
    return this;
  }

  /**
   * Get catalog
   *
   * @return catalog
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CATALOG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getCatalog() {
    return catalog;
  }

  @JsonProperty(JSON_PROPERTY_CATALOG)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCatalog(@javax.annotation.Nonnull String catalog) {
    this.catalog = catalog;
  }

  public GetCatalogResponse properties(@javax.annotation.Nullable Map<String, String> properties) {

    this.properties = properties;
    return this;
  }

  public GetCatalogResponse putPropertiesItem(String key, String propertiesItem) {
    if (this.properties == null) {
      this.properties = new HashMap<>();
    }
    this.properties.put(key, propertiesItem);
    return this;
  }

  /**
   * Properties stored on the catalog, if supported by the server. If the server does not support
   * catalog properties, it should return null for this field. If catalog properties are supported,
   * but none are set, it should return an empty object.
   *
   * @return properties
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getProperties() {
    return properties;
  }

  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(@javax.annotation.Nullable Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetCatalogResponse getCatalogResponse = (GetCatalogResponse) o;
    return Objects.equals(this.catalog, getCatalogResponse.catalog)
        && Objects.equals(this.properties, getCatalogResponse.properties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(catalog, properties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetCatalogResponse {\n");
    sb.append("    catalog: ").append(toIndentedString(catalog)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `catalog` to the URL query string
    if (getCatalog() != null) {
      try {
        joiner.add(
            String.format(
                "%scatalog%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getCatalog()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `properties` to the URL query string
    if (getProperties() != null) {
      for (String _key : getProperties().keySet()) {
        try {
          joiner.add(
              String.format(
                  "%sproperties%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                  getProperties().get(_key),
                  URLEncoder.encode(String.valueOf(getProperties().get(_key)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    return joiner.toString();
  }
}
