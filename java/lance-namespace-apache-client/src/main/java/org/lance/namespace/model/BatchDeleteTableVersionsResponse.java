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
import java.util.Objects;
import java.util.StringJoiner;

/** Response for deleting table version records */
@JsonPropertyOrder({
  BatchDeleteTableVersionsResponse.JSON_PROPERTY_DELETED_COUNT,
  BatchDeleteTableVersionsResponse.JSON_PROPERTY_TRANSACTION_ID
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class BatchDeleteTableVersionsResponse {
  public static final String JSON_PROPERTY_DELETED_COUNT = "deleted_count";
  @javax.annotation.Nullable private Long deletedCount;

  public static final String JSON_PROPERTY_TRANSACTION_ID = "transaction_id";
  @javax.annotation.Nullable private String transactionId;

  public BatchDeleteTableVersionsResponse() {}

  public BatchDeleteTableVersionsResponse deletedCount(
      @javax.annotation.Nullable Long deletedCount) {

    this.deletedCount = deletedCount;
    return this;
  }

  /**
   * Number of version records deleted minimum: 0
   *
   * @return deletedCount
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DELETED_COUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getDeletedCount() {
    return deletedCount;
  }

  @JsonProperty(JSON_PROPERTY_DELETED_COUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeletedCount(@javax.annotation.Nullable Long deletedCount) {
    this.deletedCount = deletedCount;
  }

  public BatchDeleteTableVersionsResponse transactionId(
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BatchDeleteTableVersionsResponse batchDeleteTableVersionsResponse =
        (BatchDeleteTableVersionsResponse) o;
    return Objects.equals(this.deletedCount, batchDeleteTableVersionsResponse.deletedCount)
        && Objects.equals(this.transactionId, batchDeleteTableVersionsResponse.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deletedCount, transactionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BatchDeleteTableVersionsResponse {\n");
    sb.append("    deletedCount: ").append(toIndentedString(deletedCount)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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

    // add `deleted_count` to the URL query string
    if (getDeletedCount() != null) {
      try {
        joiner.add(
            String.format(
                "%sdeleted_count%s=%s",
                prefix,
                suffix,
                URLEncoder.encode(String.valueOf(getDeletedCount()), "UTF-8")
                    .replaceAll("\\+", "%20")));
      } catch (UnsupportedEncodingException e) {
        // Should never happen, UTF-8 is always supported
        throw new RuntimeException(e);
      }
    }

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

    return joiner.toString();
  }
}
