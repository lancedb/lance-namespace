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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** RefreshMaterializedViewRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class RefreshMaterializedViewRequest {

  private Identity identity;

  @Valid private List<String> id = new ArrayList<>();

  private Integer srcVersion = null;

  private Integer maxRowsPerFragment = null;

  private Integer concurrency = null;

  private Integer intraApplierConcurrency = null;

  private String cluster = null;

  private Integer outputLimit = null;

  private String manifest = null;

  public RefreshMaterializedViewRequest identity(Identity identity) {
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

  public RefreshMaterializedViewRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public RefreshMaterializedViewRequest addIdItem(String idItem) {
    if (this.id == null) {
      this.id = new ArrayList<>();
    }
    this.id.add(idItem);
    return this;
  }

  /**
   * Table identifier path (namespace + table name)
   *
   * @return id
   */
  @Schema(
      name = "id",
      description = "Table identifier path (namespace + table name)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public List<String> getId() {
    return id;
  }

  public void setId(List<String> id) {
    this.id = id;
  }

  public RefreshMaterializedViewRequest srcVersion(Integer srcVersion) {
    this.srcVersion = srcVersion;
    return this;
  }

  /**
   * Optional source version to refresh from
   *
   * @return srcVersion
   */
  @Schema(
      name = "src_version",
      description = "Optional source version to refresh from",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("src_version")
  public Integer getSrcVersion() {
    return srcVersion;
  }

  public void setSrcVersion(Integer srcVersion) {
    this.srcVersion = srcVersion;
  }

  public RefreshMaterializedViewRequest maxRowsPerFragment(Integer maxRowsPerFragment) {
    this.maxRowsPerFragment = maxRowsPerFragment;
    return this;
  }

  /**
   * Optional maximum rows per fragment
   *
   * @return maxRowsPerFragment
   */
  @Schema(
      name = "max_rows_per_fragment",
      description = "Optional maximum rows per fragment",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_rows_per_fragment")
  public Integer getMaxRowsPerFragment() {
    return maxRowsPerFragment;
  }

  public void setMaxRowsPerFragment(Integer maxRowsPerFragment) {
    this.maxRowsPerFragment = maxRowsPerFragment;
  }

  public RefreshMaterializedViewRequest concurrency(Integer concurrency) {
    this.concurrency = concurrency;
    return this;
  }

  /**
   * Optional concurrency override
   *
   * @return concurrency
   */
  @Schema(
      name = "concurrency",
      description = "Optional concurrency override",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("concurrency")
  public Integer getConcurrency() {
    return concurrency;
  }

  public void setConcurrency(Integer concurrency) {
    this.concurrency = concurrency;
  }

  public RefreshMaterializedViewRequest intraApplierConcurrency(Integer intraApplierConcurrency) {
    this.intraApplierConcurrency = intraApplierConcurrency;
    return this;
  }

  /**
   * Optional intra-applier concurrency override
   *
   * @return intraApplierConcurrency
   */
  @Schema(
      name = "intra_applier_concurrency",
      description = "Optional intra-applier concurrency override",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("intra_applier_concurrency")
  public Integer getIntraApplierConcurrency() {
    return intraApplierConcurrency;
  }

  public void setIntraApplierConcurrency(Integer intraApplierConcurrency) {
    this.intraApplierConcurrency = intraApplierConcurrency;
  }

  public RefreshMaterializedViewRequest cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Optional cluster name (operational override)
   *
   * @return cluster
   */
  @Schema(
      name = "cluster",
      description = "Optional cluster name (operational override)",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cluster")
  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public RefreshMaterializedViewRequest outputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
    return this;
  }

  /**
   * Post-trim cap on view row count after expansion. Valid only for chunker materialized views;
   * returns 400 if set on other kinds.
   *
   * @return outputLimit
   */
  @Schema(
      name = "output_limit",
      description =
          "Post-trim cap on view row count after expansion. Valid only for chunker materialized views; returns 400 if set on other kinds. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("output_limit")
  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  public RefreshMaterializedViewRequest manifest(String manifest) {
    this.manifest = manifest;
    return this;
  }

  /**
   * Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only;
   * does not mutate the view's snapshotted manifest. When omitted, the manifest stored in the
   * view's metadata is used.
   *
   * @return manifest
   */
  @Schema(
      name = "manifest",
      description =
          "Optional inline JSON-serialized GenevaManifest. Operational override for this refresh only; does not mutate the view's snapshotted manifest. When omitted, the manifest stored in the view's metadata is used. ",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("manifest")
  public String getManifest() {
    return manifest;
  }

  public void setManifest(String manifest) {
    this.manifest = manifest;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefreshMaterializedViewRequest refreshMaterializedViewRequest =
        (RefreshMaterializedViewRequest) o;
    return Objects.equals(this.identity, refreshMaterializedViewRequest.identity)
        && Objects.equals(this.id, refreshMaterializedViewRequest.id)
        && Objects.equals(this.srcVersion, refreshMaterializedViewRequest.srcVersion)
        && Objects.equals(
            this.maxRowsPerFragment, refreshMaterializedViewRequest.maxRowsPerFragment)
        && Objects.equals(this.concurrency, refreshMaterializedViewRequest.concurrency)
        && Objects.equals(
            this.intraApplierConcurrency, refreshMaterializedViewRequest.intraApplierConcurrency)
        && Objects.equals(this.cluster, refreshMaterializedViewRequest.cluster)
        && Objects.equals(this.outputLimit, refreshMaterializedViewRequest.outputLimit)
        && Objects.equals(this.manifest, refreshMaterializedViewRequest.manifest);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity,
        id,
        srcVersion,
        maxRowsPerFragment,
        concurrency,
        intraApplierConcurrency,
        cluster,
        outputLimit,
        manifest);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RefreshMaterializedViewRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    srcVersion: ").append(toIndentedString(srcVersion)).append("\n");
    sb.append("    maxRowsPerFragment: ").append(toIndentedString(maxRowsPerFragment)).append("\n");
    sb.append("    concurrency: ").append(toIndentedString(concurrency)).append("\n");
    sb.append("    intraApplierConcurrency: ")
        .append(toIndentedString(intraApplierConcurrency))
        .append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
    sb.append("    outputLimit: ").append(toIndentedString(outputLimit)).append("\n");
    sb.append("    manifest: ").append(toIndentedString(manifest)).append("\n");
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
