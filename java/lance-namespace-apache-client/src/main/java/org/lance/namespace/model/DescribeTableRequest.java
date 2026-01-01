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
package org.lance.namespace.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** DescribeTableRequest */
@JsonPropertyOrder({
  DescribeTableRequest.JSON_PROPERTY_ID,
  DescribeTableRequest.JSON_PROPERTY_VERSION,
  DescribeTableRequest.JSON_PROPERTY_WITH_TABLE_URI,
  DescribeTableRequest.JSON_PROPERTY_LOAD_DETAILED_METADATA,
  DescribeTableRequest.JSON_PROPERTY_VEND_CREDENTIALS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class DescribeTableRequest {
  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

  public static final String JSON_PROPERTY_WITH_TABLE_URI = "with_table_uri";
  @javax.annotation.Nullable private Boolean withTableUri = false;

  public static final String JSON_PROPERTY_LOAD_DETAILED_METADATA = "load_detailed_metadata";
  @javax.annotation.Nullable private Boolean loadDetailedMetadata;

  public static final String JSON_PROPERTY_VEND_CREDENTIALS = "vend_credentials";
  @javax.annotation.Nullable private Boolean vendCredentials;

  public DescribeTableRequest() {}

  public DescribeTableRequest id(@javax.annotation.Nullable List<String> id) {

    this.id = id;
    return this;
  }

  public DescribeTableRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public List<String> getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(@javax.annotation.Nullable List<String> id) {
    this.id = id;
  }

  public DescribeTableRequest version(@javax.annotation.Nullable Long version) {

    this.version = version;
    return this;
  }

  /**
   * Version of the table to describe. If not specified, server should resolve it to the latest
   * version. minimum: 0
   *
   * @return version
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getVersion() {
    return version;
  }

  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(@javax.annotation.Nullable Long version) {
    this.version = version;
  }

  public DescribeTableRequest withTableUri(@javax.annotation.Nullable Boolean withTableUri) {

    this.withTableUri = withTableUri;
    return this;
  }

  /**
   * Whether to include the table URI in the response. Default is false.
   *
   * @return withTableUri
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WITH_TABLE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWithTableUri() {
    return withTableUri;
  }

  @JsonProperty(JSON_PROPERTY_WITH_TABLE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWithTableUri(@javax.annotation.Nullable Boolean withTableUri) {
    this.withTableUri = withTableUri;
  }

  public DescribeTableRequest loadDetailedMetadata(
      @javax.annotation.Nullable Boolean loadDetailedMetadata) {

    this.loadDetailedMetadata = loadDetailedMetadata;
    return this;
  }

  /**
   * Whether to load detailed metadata that requires opening the dataset. When true, the response
   * must include all detailed metadata such as &#x60;version&#x60;, &#x60;schema&#x60;, and
   * &#x60;stats&#x60; which require reading the dataset. When not set, the implementation can
   * decide whether to return detailed metadata and which parts of detailed metadata to return.
   *
   * @return loadDetailedMetadata
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOAD_DETAILED_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getLoadDetailedMetadata() {
    return loadDetailedMetadata;
  }

  @JsonProperty(JSON_PROPERTY_LOAD_DETAILED_METADATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLoadDetailedMetadata(@javax.annotation.Nullable Boolean loadDetailedMetadata) {
    this.loadDetailedMetadata = loadDetailedMetadata;
  }

  public DescribeTableRequest vendCredentials(@javax.annotation.Nullable Boolean vendCredentials) {

    this.vendCredentials = vendCredentials;
    return this;
  }

  /**
   * Whether to include vended credentials in the response &#x60;storage_options&#x60;. When true,
   * the implementation should provide vended credentials for accessing storage. When not set, the
   * implementation can decide whether to return vended credentials.
   *
   * @return vendCredentials
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VEND_CREDENTIALS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getVendCredentials() {
    return vendCredentials;
  }

  @JsonProperty(JSON_PROPERTY_VEND_CREDENTIALS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVendCredentials(@javax.annotation.Nullable Boolean vendCredentials) {
    this.vendCredentials = vendCredentials;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DescribeTableRequest describeTableRequest = (DescribeTableRequest) o;
    return Objects.equals(this.id, describeTableRequest.id)
        && Objects.equals(this.version, describeTableRequest.version)
        && Objects.equals(this.withTableUri, describeTableRequest.withTableUri)
        && Objects.equals(this.loadDetailedMetadata, describeTableRequest.loadDetailedMetadata)
        && Objects.equals(this.vendCredentials, describeTableRequest.vendCredentials);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, withTableUri, loadDetailedMetadata, vendCredentials);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DescribeTableRequest {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    withTableUri: ").append(toIndentedString(withTableUri)).append("\n");
    sb.append("    loadDetailedMetadata: ")
        .append(toIndentedString(loadDetailedMetadata))
        .append("\n");
    sb.append("    vendCredentials: ").append(toIndentedString(vendCredentials)).append("\n");
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

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        try {
          joiner.add(
              String.format(
                  "%sid%s%s=%s",
                  prefix,
                  suffix,
                  "".equals(suffix)
                      ? ""
                      : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                  URLEncoder.encode(String.valueOf(getId().get(i)), "UTF-8")
                      .replaceAll("\\+", "%20")));
        } catch (UnsupportedEncodingException e) {
          // Should never happen, UTF-8 is always supported
          throw new RuntimeException(e);
        }
      }
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      try {
        joiner.add(
            String.format(
                "%sversion%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVersion()), "UTF-8").replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `with_table_uri` to the URL query string
    if (getWithTableUri() != null) {
      try {
        joiner.add(
            String.format(
                "%swith_table_uri%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getWithTableUri()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `load_detailed_metadata` to the URL query string
    if (getLoadDetailedMetadata() != null) {
      try {
        joiner.add(
            String.format(
                "%sload_detailed_metadata%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getLoadDetailedMetadata()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `vend_credentials` to the URL query string
    if (getVendCredentials() != null) {
      try {
        joiner.add(
            String.format(
                "%svend_credentials%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getVendCredentials()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    return joiner.toString();
  }
}
