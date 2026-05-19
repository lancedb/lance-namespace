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

/**
 * Response for batch creating table versions. Contains the created versions for each table in the
 * same order as the request.
 */
@JsonPropertyOrder({
  BatchCreateTableVersionsResponse.JSON_PROPERTY_TRANSACTION_ID,
  BatchCreateTableVersionsResponse.JSON_PROPERTY_VERSIONS
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BatchCreateTableVersionsResponse {
  public static final String JSON_PROPERTY_TRANSACTION_ID = "transaction_id";
  @javax.annotation.Nullable private String transactionId;

  public static final String JSON_PROPERTY_VERSIONS = "versions";
  @javax.annotation.Nonnull private List<TableVersion> versions = new ArrayList<>();

  public BatchCreateTableVersionsResponse() {}

  public BatchCreateTableVersionsResponse transactionId(
      @javax.annotation.Nullable String transactionId) {

    this.transactionId = transactionId;
    return this;
  }

  /**
   * Optional transaction identifier
   *
   * @return transactionId
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TRANSACTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getTransactionId() {
    return transactionId;
  }

  @JsonProperty(JSON_PROPERTY_TRANSACTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTransactionId(@javax.annotation.Nullable String transactionId) {
    this.transactionId = transactionId;
  }

  public BatchCreateTableVersionsResponse versions(
      @javax.annotation.Nonnull List<TableVersion> versions) {

    this.versions = versions;
    return this;
  }

  public BatchCreateTableVersionsResponse addVersionsItem(TableVersion versionsItem) {
    if (this.versions == null) {
      this.versions = new ArrayList<>();
    }
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * List of created table versions in the same order as the request entries
   *
   * @return versions
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<TableVersion> getVersions() {
    return versions;
  }

  @JsonProperty(JSON_PROPERTY_VERSIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersions(@javax.annotation.Nonnull List<TableVersion> versions) {
    this.versions = versions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchCreateTableVersionsResponse batchCreateTableVersionsResponse =
        (BatchCreateTableVersionsResponse) o;
    return Objects.equals(this.transactionId, batchCreateTableVersionsResponse.transactionId)
        && Objects.equals(this.versions, batchCreateTableVersionsResponse.versions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, versions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchCreateTableVersionsResponse {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
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

    // add `transaction_id` to the URL query string
    if (getTransactionId() != null) {
      try {
        joiner.add(
            String.format(
                "%stransaction_id%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getTransactionId()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

    // add `versions` to the URL query string
    if (getVersions() != null) {
      for (int i = 0; i < getVersions().size(); i++) {
        if (getVersions().get(i) != null) {
          joiner.add(
              getVersions()
                  .get(i)
                  .toUrlQueryString(
                      String.format(
                          "%sversions%s%s",
                          prefix,
                          suffix,
                          "".equals(suffix)
                              ? ""
                              : String.format("%s%d%s", containerPrefix, i, containerSuffix))));
        }
      }
    }

    return joiner.toString();
  }
}
