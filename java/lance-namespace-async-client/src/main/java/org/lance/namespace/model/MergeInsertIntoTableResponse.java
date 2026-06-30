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

import org.lance.namespace.client.async.ApiClient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/** Response from merge insert operation */
@JsonPropertyOrder({
  MergeInsertIntoTableResponse.JSON_PROPERTY_CONTEXT,
  MergeInsertIntoTableResponse.JSON_PROPERTY_TRANSACTION_ID,
  MergeInsertIntoTableResponse.JSON_PROPERTY_NUM_UPDATED_ROWS,
  MergeInsertIntoTableResponse.JSON_PROPERTY_NUM_INSERTED_ROWS,
  MergeInsertIntoTableResponse.JSON_PROPERTY_NUM_DELETED_ROWS,
  MergeInsertIntoTableResponse.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class MergeInsertIntoTableResponse {
  public static final String JSON_PROPERTY_CONTEXT = "context";
  @javax.annotation.Nullable private Map<String, String> context = new HashMap<>();

  public static final String JSON_PROPERTY_TRANSACTION_ID = "transaction_id";
  @javax.annotation.Nullable private String transactionId;

  public static final String JSON_PROPERTY_NUM_UPDATED_ROWS = "num_updated_rows";
  @javax.annotation.Nullable private Long numUpdatedRows;

  public static final String JSON_PROPERTY_NUM_INSERTED_ROWS = "num_inserted_rows";
  @javax.annotation.Nullable private Long numInsertedRows;

  public static final String JSON_PROPERTY_NUM_DELETED_ROWS = "num_deleted_rows";
  @javax.annotation.Nullable private Long numDeletedRows;

  public static final String JSON_PROPERTY_VERSION = "version";
  @javax.annotation.Nullable private Long version;

  public MergeInsertIntoTableResponse() {}

  public MergeInsertIntoTableResponse context(
      @javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
    return this;
  }

  public MergeInsertIntoTableResponse putContextItem(String key, String contextItem) {
    if (this.context == null) {
      this.context = new HashMap<>();
    }
    this.context.put(key, contextItem);
    return this;
  }

  /**
   * Arbitrary context as key-value pairs. How to use the context is custom to the specific
   * implementation. On a request, it carries caller-provided context to the implementation. On a
   * response, it carries implementation-provided context back to the caller. REST NAMESPACE ONLY
   * Context entries are mapped to and from HTTP headers using the &#x60;header.&#x60; prefix: - On
   * a request, any entry whose key starts with &#x60;header.&#x60; is sent as an HTTP request
   * header with the prefix stripped. For example, the entry
   * &#x60;{\&quot;header.Authorization\&quot;: \&quot;Bearer abc\&quot;}&#x60; is sent as the
   * request header &#x60;Authorization: Bearer abc&#x60;. - On a response, every HTTP response
   * header is returned as an entry whose key is the header name prefixed with &#x60;header.&#x60;.
   * For example, the response header &#x60;x-request-id: abc123&#x60; is returned as the entry
   * &#x60;{\&quot;header.x-request-id\&quot;: \&quot;abc123\&quot;}&#x60;.
   *
   * @return context
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Map<String, String> getContext() {
    return context;
  }

  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setContext(@javax.annotation.Nullable Map<String, String> context) {
    this.context = context;
  }

  public MergeInsertIntoTableResponse transactionId(
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

  public MergeInsertIntoTableResponse numUpdatedRows(
      @javax.annotation.Nullable Long numUpdatedRows) {
    this.numUpdatedRows = numUpdatedRows;
    return this;
  }

  /**
   * Number of rows updated minimum: 0
   *
   * @return numUpdatedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_UPDATED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumUpdatedRows() {
    return numUpdatedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_UPDATED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumUpdatedRows(@javax.annotation.Nullable Long numUpdatedRows) {
    this.numUpdatedRows = numUpdatedRows;
  }

  public MergeInsertIntoTableResponse numInsertedRows(
      @javax.annotation.Nullable Long numInsertedRows) {
    this.numInsertedRows = numInsertedRows;
    return this;
  }

  /**
   * Number of rows inserted minimum: 0
   *
   * @return numInsertedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_INSERTED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumInsertedRows() {
    return numInsertedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_INSERTED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumInsertedRows(@javax.annotation.Nullable Long numInsertedRows) {
    this.numInsertedRows = numInsertedRows;
  }

  public MergeInsertIntoTableResponse numDeletedRows(
      @javax.annotation.Nullable Long numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
    return this;
  }

  /**
   * Number of rows deleted (typically 0 for merge insert) minimum: 0
   *
   * @return numDeletedRows
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NUM_DELETED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Long getNumDeletedRows() {
    return numDeletedRows;
  }

  @JsonProperty(JSON_PROPERTY_NUM_DELETED_ROWS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setNumDeletedRows(@javax.annotation.Nullable Long numDeletedRows) {
    this.numDeletedRows = numDeletedRows;
  }

  public MergeInsertIntoTableResponse version(@javax.annotation.Nullable Long version) {
    this.version = version;
    return this;
  }

  /**
   * The commit version associated with the operation minimum: 0
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

  /** Return true if this MergeInsertIntoTableResponse object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MergeInsertIntoTableResponse mergeInsertIntoTableResponse = (MergeInsertIntoTableResponse) o;
    return Objects.equals(this.context, mergeInsertIntoTableResponse.context)
        && Objects.equals(this.transactionId, mergeInsertIntoTableResponse.transactionId)
        && Objects.equals(this.numUpdatedRows, mergeInsertIntoTableResponse.numUpdatedRows)
        && Objects.equals(this.numInsertedRows, mergeInsertIntoTableResponse.numInsertedRows)
        && Objects.equals(this.numDeletedRows, mergeInsertIntoTableResponse.numDeletedRows)
        && Objects.equals(this.version, mergeInsertIntoTableResponse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        context, transactionId, numUpdatedRows, numInsertedRows, numDeletedRows, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MergeInsertIntoTableResponse {\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    numUpdatedRows: ").append(toIndentedString(numUpdatedRows)).append("\n");
    sb.append("    numInsertedRows: ").append(toIndentedString(numInsertedRows)).append("\n");
    sb.append("    numDeletedRows: ").append(toIndentedString(numDeletedRows)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

    // add `context` to the URL query string
    if (getContext() != null) {
      for (String _key : getContext().keySet()) {
        joiner.add(
            String.format(
                "%scontext%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
                getContext().get(_key),
                ApiClient.urlEncode(ApiClient.valueToString(getContext().get(_key)))));
      }
    }

    // add `transaction_id` to the URL query string
    if (getTransactionId() != null) {
      joiner.add(
          String.format(
              "%stransaction_id%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getTransactionId()))));
    }

    // add `num_updated_rows` to the URL query string
    if (getNumUpdatedRows() != null) {
      joiner.add(
          String.format(
              "%snum_updated_rows%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumUpdatedRows()))));
    }

    // add `num_inserted_rows` to the URL query string
    if (getNumInsertedRows() != null) {
      joiner.add(
          String.format(
              "%snum_inserted_rows%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumInsertedRows()))));
    }

    // add `num_deleted_rows` to the URL query string
    if (getNumDeletedRows() != null) {
      joiner.add(
          String.format(
              "%snum_deleted_rows%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getNumDeletedRows()))));
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      joiner.add(
          String.format(
              "%sversion%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getVersion()))));
    }

    return joiner.toString();
  }
}
