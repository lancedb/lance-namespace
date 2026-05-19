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
package org.lance.namespace.server.springboot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** CreateMaterializedViewRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class CreateMaterializedViewRequest {

  private Identity identity;

  @Valid private List<String> id = new ArrayList<>();

  /**
   * The materialized view kind. - `query` — plain query-backed view (no UDTF), 1:1 rows. - `udtf` —
   * batch UDTF-backed view (N:M rows, full refresh). - `chunker`, aka 'scalar_udtf' — chunker view
   * (1:N row expansion, incremental refresh).
   */
  public enum KindEnum {
    QUERY("query"),

    UDTF("udtf"),

    CHUNKER("chunker");

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

  private KindEnum kind;

  private String sourceQuery;

  private String outputSchema;

  private MaterializedViewUdtfEntry udtfSpec = null;

  private Boolean withNoData = true;

  private @Nullable Boolean autoRefresh = null;

  public CreateMaterializedViewRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public CreateMaterializedViewRequest(KindEnum kind, String sourceQuery, String outputSchema) {
    this.kind = kind;
    this.sourceQuery = sourceQuery;
    this.outputSchema = outputSchema;
  }

  public CreateMaterializedViewRequest identity(Identity identity) {
    this.identity = identity;
    return this;
  }

  /**
   * Get identity
   *
   * @return identity
   */
  @Valid
  @Schema(name = "identity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("identity")
  public Identity getIdentity() {
    return identity;
  }

  public void setIdentity(Identity identity) {
    this.identity = identity;
  }

  public CreateMaterializedViewRequest id(List<String> id) {
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
  @Schema(
      name = "id",
      description = "View identifier path (namespace + view name)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public CreateMaterializedViewRequest kind(KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * The materialized view kind. - `query` — plain query-backed view (no UDTF), 1:1 rows. - `udtf` —
   * batch UDTF-backed view (N:M rows, full refresh). - `chunker`, aka 'scalar_udtf' — chunker view
   * (1:N row expansion, incremental refresh).
   *
   * @return kind
   */
  @NotNull
  @Schema(
      name = "kind",
      description =
          "The materialized view kind. - `query` — plain query-backed view (no UDTF), 1:1 rows. - `udtf` — batch UDTF-backed view (N:M rows, full refresh). - `chunker`, aka 'scalar_udtf' — chunker view (1:N row expansion, incremental refresh). ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("kind")
  public KindEnum getKind() {
    return kind;
  }

  public void setKind(KindEnum kind) {
    this.kind = kind;
  }

  public CreateMaterializedViewRequest sourceQuery(String sourceQuery) {
    this.sourceQuery = sourceQuery;
    return this;
  }

  /**
   * Opaque serialized representation of the source query that defines the view's input. The format
   * is defined by the client; the namespace server stores it without interpreting it.
   *
   * @return sourceQuery
   */
  @NotNull
  @Schema(
      name = "source_query",
      description =
          "Opaque serialized representation of the source query that defines the view's input. The format is defined by the client; the namespace server stores it without interpreting it. ",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("source_query")
  public String getSourceQuery() {
    return sourceQuery;
  }

  public void setSourceQuery(String sourceQuery) {
    this.sourceQuery = sourceQuery;
  }

  public CreateMaterializedViewRequest outputSchema(String outputSchema) {
    this.outputSchema = outputSchema;
    return this;
  }

  /**
   * Base64-encoded Arrow schema of the view output
   *
   * @return outputSchema
   */
  @NotNull
  @Schema(
      name = "output_schema",
      description = "Base64-encoded Arrow schema of the view output",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("output_schema")
  public String getOutputSchema() {
    return outputSchema;
  }

  public void setOutputSchema(String outputSchema) {
    this.outputSchema = outputSchema;
  }

  public CreateMaterializedViewRequest udtfSpec(MaterializedViewUdtfEntry udtfSpec) {
    this.udtfSpec = udtfSpec;
    return this;
  }

  /**
   * Get udtfSpec
   *
   * @return udtfSpec
   */
  @Valid
  @Schema(name = "udtf_spec", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("udtf_spec")
  public MaterializedViewUdtfEntry getUdtfSpec() {
    return udtfSpec;
  }

  public void setUdtfSpec(MaterializedViewUdtfEntry udtfSpec) {
    this.udtfSpec = udtfSpec;
  }

  public CreateMaterializedViewRequest withNoData(Boolean withNoData) {
    this.withNoData = withNoData;
    return this;
  }

  /**
   * If false, the server kicks off an initial refresh immediately after creating the view and the
   * response includes a job ID.
   *
   * @return withNoData
   */
  @Schema(
      name = "with_no_data",
      description =
          "If false, the server kicks off an initial refresh immediately after creating the view and the response includes a job ID. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("with_no_data")
  public Boolean getWithNoData() {
    return withNoData;
  }

  public void setWithNoData(Boolean withNoData) {
    this.withNoData = withNoData;
  }

  public CreateMaterializedViewRequest autoRefresh(Boolean autoRefresh) {
    this.autoRefresh = autoRefresh;
    return this;
  }

  /**
   * If true, the view is automatically refreshed when source-table data changes past the
   * deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on
   * the deployment, not per-view.
   *
   * @return autoRefresh
   */
  @Schema(
      name = "auto_refresh",
      description =
          "If true, the view is automatically refreshed when source-table data changes past the deployment-level threshold. Boolean opt-in only; the threshold and cooldown are configured on the deployment, not per-view. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("auto_refresh")
  public Boolean getAutoRefresh() {
    return autoRefresh;
  }

  public void setAutoRefresh(Boolean autoRefresh) {
    this.autoRefresh = autoRefresh;
  }

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
        && Objects.equals(this.udtfSpec, createMaterializedViewRequest.udtfSpec)
        && Objects.equals(this.withNoData, createMaterializedViewRequest.withNoData)
        && Objects.equals(this.autoRefresh, createMaterializedViewRequest.autoRefresh);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity, id, kind, sourceQuery, outputSchema, udtfSpec, withNoData, autoRefresh);
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
}
