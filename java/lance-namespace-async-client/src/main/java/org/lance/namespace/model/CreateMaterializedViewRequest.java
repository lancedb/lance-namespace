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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/** CreateMaterializedViewRequest */
@JsonPropertyOrder({
  CreateMaterializedViewRequest.JSON_PROPERTY_IDENTITY,
  CreateMaterializedViewRequest.JSON_PROPERTY_ID,
  CreateMaterializedViewRequest.JSON_PROPERTY_KIND,
  CreateMaterializedViewRequest.JSON_PROPERTY_SOURCE_QUERY,
  CreateMaterializedViewRequest.JSON_PROPERTY_OUTPUT_SCHEMA,
  CreateMaterializedViewRequest.JSON_PROPERTY_UDTF_SPEC,
  CreateMaterializedViewRequest.JSON_PROPERTY_WITH_NO_DATA,
  CreateMaterializedViewRequest.JSON_PROPERTY_AUTO_REFRESH
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.12.0")
public class CreateMaterializedViewRequest {
  public static final String JSON_PROPERTY_IDENTITY = "identity";
  @javax.annotation.Nullable private Identity identity;

  public static final String JSON_PROPERTY_ID = "id";
  @javax.annotation.Nullable private List<String> id = new ArrayList<>();

  /**
   * The materialized view kind. - &#x60;query&#x60; — plain query-backed view (no UDTF), 1:1 rows.
   * - &#x60;udtf&#x60; — batch UDTF-backed view (N:M rows, full refresh). - &#x60;chunker&#x60;,
   * aka &#39;scalar_udtf&#39; — chunker view (1:N row expansion, incremental refresh).
   */
  public enum KindEnum {
    QUERY(String.valueOf("query")),

    UDTF(String.valueOf("udtf")),

    CHUNKER(String.valueOf("chunker"));

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static KindEnum fromValue(String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_KIND = "kind";
  @javax.annotation.Nonnull private KindEnum kind;

  public static final String JSON_PROPERTY_SOURCE_QUERY = "source_query";
  @javax.annotation.Nonnull private String sourceQuery;

  public static final String JSON_PROPERTY_OUTPUT_SCHEMA = "output_schema";
  @javax.annotation.Nonnull private String outputSchema;

  public static final String JSON_PROPERTY_UDTF_SPEC = "udtf_spec";
  private JsonNullable<MaterializedViewUdtfEntry> udtfSpec =
      JsonNullable.<MaterializedViewUdtfEntry>undefined();

  public static final String JSON_PROPERTY_WITH_NO_DATA = "with_no_data";
  @javax.annotation.Nullable private Boolean withNoData = true;

  public static final String JSON_PROPERTY_AUTO_REFRESH = "auto_refresh";
  private JsonNullable<Boolean> autoRefresh = JsonNullable.<Boolean>of(false);

  public CreateMaterializedViewRequest() {}

  public CreateMaterializedViewRequest identity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Identity getIdentity() {
    return identity;
  }

  @JsonProperty(JSON_PROPERTY_IDENTITY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentity(@javax.annotation.Nullable Identity identity) {
    this.identity = identity;
  }

  public CreateMaterializedViewRequest id(@javax.annotation.Nullable List<String> id) {
    this.id = id;
    return this;
  }

  public CreateMaterializedViewRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * View identifier path (namespace + view name)
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

  public CreateMaterializedViewRequest kind(@javax.annotation.Nonnull KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * The materialized view kind. - &#x60;query&#x60; — plain query-backed view (no UDTF), 1:1 rows.
   * - &#x60;udtf&#x60; — batch UDTF-backed view (N:M rows, full refresh). - &#x60;chunker&#x60;,
   * aka &#39;scalar_udtf&#39; — chunker view (1:N row expansion, incremental refresh).
   *
   * @return kind
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_KIND)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public KindEnum getKind() {
    return kind;
  }

  @JsonProperty(JSON_PROPERTY_KIND)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setKind(@javax.annotation.Nonnull KindEnum kind) {
    this.kind = kind;
  }

  public CreateMaterializedViewRequest sourceQuery(@javax.annotation.Nonnull String sourceQuery) {
    this.sourceQuery = sourceQuery;
    return this;
  }

  /**
   * Opaque serialized representation of the source query that defines the view&#39;s input. The
   * format is defined by the client; the namespace server stores it without interpreting it.
   *
   * @return sourceQuery
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SOURCE_QUERY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getSourceQuery() {
    return sourceQuery;
  }

  @JsonProperty(JSON_PROPERTY_SOURCE_QUERY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSourceQuery(@javax.annotation.Nonnull String sourceQuery) {
    this.sourceQuery = sourceQuery;
  }

  public CreateMaterializedViewRequest outputSchema(@javax.annotation.Nonnull String outputSchema) {
    this.outputSchema = outputSchema;
    return this;
  }

  /**
   * Base64-encoded Arrow schema of the view output
   *
   * @return outputSchema
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OUTPUT_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getOutputSchema() {
    return outputSchema;
  }

  @JsonProperty(JSON_PROPERTY_OUTPUT_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOutputSchema(@javax.annotation.Nonnull String outputSchema) {
    this.outputSchema = outputSchema;
  }

  public CreateMaterializedViewRequest udtfSpec(
      @javax.annotation.Nullable MaterializedViewUdtfEntry udtfSpec) {
    this.udtfSpec = JsonNullable.<MaterializedViewUdtfEntry>of(udtfSpec);
    return this;
  }

  /**
   * Get udtfSpec
   *
   * @return udtfSpec
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public MaterializedViewUdtfEntry getUdtfSpec() {
    return udtfSpec.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_UDTF_SPEC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<MaterializedViewUdtfEntry> getUdtfSpec_JsonNullable() {
    return udtfSpec;
  }

  @JsonProperty(JSON_PROPERTY_UDTF_SPEC)
  public void setUdtfSpec_JsonNullable(JsonNullable<MaterializedViewUdtfEntry> udtfSpec) {
    this.udtfSpec = udtfSpec;
  }

  public void setUdtfSpec(@javax.annotation.Nullable MaterializedViewUdtfEntry udtfSpec) {
    this.udtfSpec = JsonNullable.<MaterializedViewUdtfEntry>of(udtfSpec);
  }

  public CreateMaterializedViewRequest withNoData(@javax.annotation.Nullable Boolean withNoData) {
    this.withNoData = withNoData;
    return this;
  }

  /**
   * If false, the server kicks off an initial refresh immediately after creating the view and the
   * response includes a job ID.
   *
   * @return withNoData
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_WITH_NO_DATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public Boolean getWithNoData() {
    return withNoData;
  }

  @JsonProperty(JSON_PROPERTY_WITH_NO_DATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWithNoData(@javax.annotation.Nullable Boolean withNoData) {
    this.withNoData = withNoData;
  }

  public CreateMaterializedViewRequest autoRefresh(@javax.annotation.Nullable Boolean autoRefresh) {
    this.autoRefresh = JsonNullable.<Boolean>of(autoRefresh);
    return this;
  }

  /**
   * If true, the view is automatically refreshed when source-table data changes past the
   * deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on
   * the deployment, not per-view.
   *
   * @return autoRefresh
   */
  @javax.annotation.Nullable
  @JsonIgnore
  public Boolean getAutoRefresh() {
    return autoRefresh.orElse(null);
  }

  @JsonProperty(JSON_PROPERTY_AUTO_REFRESH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public JsonNullable<Boolean> getAutoRefresh_JsonNullable() {
    return autoRefresh;
  }

  @JsonProperty(JSON_PROPERTY_AUTO_REFRESH)
  public void setAutoRefresh_JsonNullable(JsonNullable<Boolean> autoRefresh) {
    this.autoRefresh = autoRefresh;
  }

  public void setAutoRefresh(@javax.annotation.Nullable Boolean autoRefresh) {
    this.autoRefresh = JsonNullable.<Boolean>of(autoRefresh);
  }

  /** Return true if this CreateMaterializedViewRequest object is equal to o. */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateMaterializedViewRequest createMaterializedViewRequest = (CreateMaterializedViewRequest) o;
    return Objects.equals(this.identity, createMaterializedViewRequest.identity)
        && Objects.equals(this.id, createMaterializedViewRequest.id)
        && Objects.equals(this.kind, createMaterializedViewRequest.kind)
        && Objects.equals(this.sourceQuery, createMaterializedViewRequest.sourceQuery)
        && Objects.equals(this.outputSchema, createMaterializedViewRequest.outputSchema)
        && equalsNullable(this.udtfSpec, createMaterializedViewRequest.udtfSpec)
        && Objects.equals(this.withNoData, createMaterializedViewRequest.withNoData)
        && equalsNullable(this.autoRefresh, createMaterializedViewRequest.autoRefresh);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b
        || (a != null
            && b != null
            && a.isPresent()
            && b.isPresent()
            && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity,
        id,
        kind,
        sourceQuery,
        outputSchema,
        hashCodeNullable(udtfSpec),
        withNoData,
        hashCodeNullable(autoRefresh));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[] {a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateMaterializedViewRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    sourceQuery: ").append(toIndentedString(sourceQuery)).append("\n");
    sb.append("    outputSchema: ").append(toIndentedString(outputSchema)).append("\n");
    sb.append("    udtfSpec: ").append(toIndentedString(udtfSpec)).append("\n");
    sb.append("    withNoData: ").append(toIndentedString(withNoData)).append("\n");
    sb.append("    autoRefresh: ").append(toIndentedString(autoRefresh)).append("\n");
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

    // add `identity` to the URL query string
    if (getIdentity() != null) {
      joiner.add(getIdentity().toUrlQueryString(prefix + "identity" + suffix));
    }

    // add `id` to the URL query string
    if (getId() != null) {
      for (int i = 0; i < getId().size(); i++) {
        joiner.add(
            String.format(
                "%sid%s%s=%s",
                prefix,
                suffix,
                "".equals(suffix)
                    ? ""
                    : String.format("%s%d%s", containerPrefix, i, containerSuffix),
                ApiClient.urlEncode(ApiClient.valueToString(getId().get(i)))));
      }
    }

    // add `kind` to the URL query string
    if (getKind() != null) {
      joiner.add(
          String.format(
              "%skind%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getKind()))));
    }

    // add `source_query` to the URL query string
    if (getSourceQuery() != null) {
      joiner.add(
          String.format(
              "%ssource_query%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getSourceQuery()))));
    }

    // add `output_schema` to the URL query string
    if (getOutputSchema() != null) {
      joiner.add(
          String.format(
              "%soutput_schema%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getOutputSchema()))));
    }

    // add `udtf_spec` to the URL query string
    if (getUdtfSpec() != null) {
      joiner.add(getUdtfSpec().toUrlQueryString(prefix + "udtf_spec" + suffix));
    }

    // add `with_no_data` to the URL query string
    if (getWithNoData() != null) {
      joiner.add(
          String.format(
              "%swith_no_data%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getWithNoData()))));
    }

    // add `auto_refresh` to the URL query string
    if (getAutoRefresh() != null) {
      joiner.add(
          String.format(
              "%sauto_refresh%s=%s",
              prefix, suffix, ApiClient.urlEncode(ApiClient.valueToString(getAutoRefresh()))));
    }

    return joiner.toString();
  }
}
