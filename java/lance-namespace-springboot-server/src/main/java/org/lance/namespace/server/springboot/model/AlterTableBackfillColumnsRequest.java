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

import java.math.BigDecimal;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** AlterTableBackfillColumnsRequest */
@Generated(
    value = "org.openapitools.codegen.languages.SpringCodegen",
    comments = "Generator version: 7.12.0")
public class AlterTableBackfillColumnsRequest {

  private Identity identity;

  @Valid private List<String> id = new ArrayList<>();

  private String column;

  private String where = null;

  private Integer concurrency = null;

  private Integer intraApplierConcurrency = null;

  private Integer minCheckpointSize = null;

  private Integer maxCheckpointSize = null;

  private BigDecimal batchCheckpointFlushIntervalSeconds = null;

  private Integer readVersion = null;

  private Integer taskSize = null;

  private Integer numFrags = null;

  private Integer checkpointSize = null;

  private Integer commitGranularity = null;

  private String cluster = null;

  private String manifest = null;

  public AlterTableBackfillColumnsRequest() {
    super();
  }

  /** Constructor with only required parameters */
  public AlterTableBackfillColumnsRequest(String column) {
    this.column = column;
  }

  public AlterTableBackfillColumnsRequest identity(Identity identity) {
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

  public AlterTableBackfillColumnsRequest id(List<String> id) {
    this.id = id;
    return this;
  }

  public AlterTableBackfillColumnsRequest addIdItem(String idItem) {
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

  public AlterTableBackfillColumnsRequest column(String column) {
    this.column = column;
    return this;
  }

  /**
   * Column name to backfill
   *
   * @return column
   */
  @NotNull
  @Schema(
      name = "column",
      description = "Column name to backfill",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("column")
  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public AlterTableBackfillColumnsRequest where(String where) {
    this.where = where;
    return this;
  }

  /**
   * Optional WHERE clause filter
   *
   * @return where
   */
  @Schema(
      name = "where",
      description = "Optional WHERE clause filter",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("where")
  public String getWhere() {
    return where;
  }

  public void setWhere(String where) {
    this.where = where;
  }

  public AlterTableBackfillColumnsRequest concurrency(Integer concurrency) {
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

  public AlterTableBackfillColumnsRequest intraApplierConcurrency(Integer intraApplierConcurrency) {
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

  public AlterTableBackfillColumnsRequest minCheckpointSize(Integer minCheckpointSize) {
    this.minCheckpointSize = minCheckpointSize;
    return this;
  }

  /**
   * Optional minimum checkpoint size
   *
   * @return minCheckpointSize
   */
  @Schema(
      name = "min_checkpoint_size",
      description = "Optional minimum checkpoint size",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("min_checkpoint_size")
  public Integer getMinCheckpointSize() {
    return minCheckpointSize;
  }

  public void setMinCheckpointSize(Integer minCheckpointSize) {
    this.minCheckpointSize = minCheckpointSize;
  }

  public AlterTableBackfillColumnsRequest maxCheckpointSize(Integer maxCheckpointSize) {
    this.maxCheckpointSize = maxCheckpointSize;
    return this;
  }

  /**
   * Optional maximum checkpoint size
   *
   * @return maxCheckpointSize
   */
  @Schema(
      name = "max_checkpoint_size",
      description = "Optional maximum checkpoint size",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("max_checkpoint_size")
  public Integer getMaxCheckpointSize() {
    return maxCheckpointSize;
  }

  public void setMaxCheckpointSize(Integer maxCheckpointSize) {
    this.maxCheckpointSize = maxCheckpointSize;
  }

  public AlterTableBackfillColumnsRequest batchCheckpointFlushIntervalSeconds(
      BigDecimal batchCheckpointFlushIntervalSeconds) {
    this.batchCheckpointFlushIntervalSeconds = batchCheckpointFlushIntervalSeconds;
    return this;
  }

  /**
   * Optional batch checkpoint flush interval in seconds
   *
   * @return batchCheckpointFlushIntervalSeconds
   */
  @Valid
  @Schema(
      name = "batch_checkpoint_flush_interval_seconds",
      description = "Optional batch checkpoint flush interval in seconds",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("batch_checkpoint_flush_interval_seconds")
  public BigDecimal getBatchCheckpointFlushIntervalSeconds() {
    return batchCheckpointFlushIntervalSeconds;
  }

  public void setBatchCheckpointFlushIntervalSeconds(
      BigDecimal batchCheckpointFlushIntervalSeconds) {
    this.batchCheckpointFlushIntervalSeconds = batchCheckpointFlushIntervalSeconds;
  }

  public AlterTableBackfillColumnsRequest readVersion(Integer readVersion) {
    this.readVersion = readVersion;
    return this;
  }

  /**
   * Optional table version to read from
   *
   * @return readVersion
   */
  @Schema(
      name = "read_version",
      description = "Optional table version to read from",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("read_version")
  public Integer getReadVersion() {
    return readVersion;
  }

  public void setReadVersion(Integer readVersion) {
    this.readVersion = readVersion;
  }

  public AlterTableBackfillColumnsRequest taskSize(Integer taskSize) {
    this.taskSize = taskSize;
    return this;
  }

  /**
   * Optional task size
   *
   * @return taskSize
   */
  @Schema(
      name = "task_size",
      description = "Optional task size",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("task_size")
  public Integer getTaskSize() {
    return taskSize;
  }

  public void setTaskSize(Integer taskSize) {
    this.taskSize = taskSize;
  }

  public AlterTableBackfillColumnsRequest numFrags(Integer numFrags) {
    this.numFrags = numFrags;
    return this;
  }

  /**
   * Optional number of fragments
   *
   * @return numFrags
   */
  @Schema(
      name = "num_frags",
      description = "Optional number of fragments",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("num_frags")
  public Integer getNumFrags() {
    return numFrags;
  }

  public void setNumFrags(Integer numFrags) {
    this.numFrags = numFrags;
  }

  public AlterTableBackfillColumnsRequest checkpointSize(Integer checkpointSize) {
    this.checkpointSize = checkpointSize;
    return this;
  }

  /**
   * Optional checkpoint size
   *
   * @return checkpointSize
   */
  @Schema(
      name = "checkpoint_size",
      description = "Optional checkpoint size",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("checkpoint_size")
  public Integer getCheckpointSize() {
    return checkpointSize;
  }

  public void setCheckpointSize(Integer checkpointSize) {
    this.checkpointSize = checkpointSize;
  }

  public AlterTableBackfillColumnsRequest commitGranularity(Integer commitGranularity) {
    this.commitGranularity = commitGranularity;
    return this;
  }

  /**
   * Optional commit granularity
   *
   * @return commitGranularity
   */
  @Schema(
      name = "commit_granularity",
      description = "Optional commit granularity",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commit_granularity")
  public Integer getCommitGranularity() {
    return commitGranularity;
  }

  public void setCommitGranularity(Integer commitGranularity) {
    this.commitGranularity = commitGranularity;
  }

  public AlterTableBackfillColumnsRequest cluster(String cluster) {
    this.cluster = cluster;
    return this;
  }

  /**
   * Optional cluster name
   *
   * @return cluster
   */
  @Schema(
      name = "cluster",
      description = "Optional cluster name",
      requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("cluster")
  public String getCluster() {
    return cluster;
  }

  public void setCluster(String cluster) {
    this.cluster = cluster;
  }

  public AlterTableBackfillColumnsRequest manifest(String manifest) {
    this.manifest = manifest;
    return this;
  }

  /**
   * Optional manifest name
   *
   * @return manifest
   */
  @Schema(
      name = "manifest",
      description = "Optional manifest name",
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
    AlterTableBackfillColumnsRequest alterTableBackfillColumnsRequest =
        (AlterTableBackfillColumnsRequest) o;
    return Objects.equals(this.identity, alterTableBackfillColumnsRequest.identity)
        && Objects.equals(this.id, alterTableBackfillColumnsRequest.id)
        && Objects.equals(this.column, alterTableBackfillColumnsRequest.column)
        && Objects.equals(this.where, alterTableBackfillColumnsRequest.where)
        && Objects.equals(this.concurrency, alterTableBackfillColumnsRequest.concurrency)
        && Objects.equals(
            this.intraApplierConcurrency, alterTableBackfillColumnsRequest.intraApplierConcurrency)
        && Objects.equals(
            this.minCheckpointSize, alterTableBackfillColumnsRequest.minCheckpointSize)
        && Objects.equals(
            this.maxCheckpointSize, alterTableBackfillColumnsRequest.maxCheckpointSize)
        && Objects.equals(
            this.batchCheckpointFlushIntervalSeconds,
            alterTableBackfillColumnsRequest.batchCheckpointFlushIntervalSeconds)
        && Objects.equals(this.readVersion, alterTableBackfillColumnsRequest.readVersion)
        && Objects.equals(this.taskSize, alterTableBackfillColumnsRequest.taskSize)
        && Objects.equals(this.numFrags, alterTableBackfillColumnsRequest.numFrags)
        && Objects.equals(this.checkpointSize, alterTableBackfillColumnsRequest.checkpointSize)
        && Objects.equals(
            this.commitGranularity, alterTableBackfillColumnsRequest.commitGranularity)
        && Objects.equals(this.cluster, alterTableBackfillColumnsRequest.cluster)
        && Objects.equals(this.manifest, alterTableBackfillColumnsRequest.manifest);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        identity,
        id,
        column,
        where,
        concurrency,
        intraApplierConcurrency,
        minCheckpointSize,
        maxCheckpointSize,
        batchCheckpointFlushIntervalSeconds,
        readVersion,
        taskSize,
        numFrags,
        checkpointSize,
        commitGranularity,
        cluster,
        manifest);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlterTableBackfillColumnsRequest {\n");
    sb.append("    identity: ").append(toIndentedString(identity)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    where: ").append(toIndentedString(where)).append("\n");
    sb.append("    concurrency: ").append(toIndentedString(concurrency)).append("\n");
    sb.append("    intraApplierConcurrency: ")
        .append(toIndentedString(intraApplierConcurrency))
        .append("\n");
    sb.append("    minCheckpointSize: ").append(toIndentedString(minCheckpointSize)).append("\n");
    sb.append("    maxCheckpointSize: ").append(toIndentedString(maxCheckpointSize)).append("\n");
    sb.append("    batchCheckpointFlushIntervalSeconds: ")
        .append(toIndentedString(batchCheckpointFlushIntervalSeconds))
        .append("\n");
    sb.append("    readVersion: ").append(toIndentedString(readVersion)).append("\n");
    sb.append("    taskSize: ").append(toIndentedString(taskSize)).append("\n");
    sb.append("    numFrags: ").append(toIndentedString(numFrags)).append("\n");
    sb.append("    checkpointSize: ").append(toIndentedString(checkpointSize)).append("\n");
    sb.append("    commitGranularity: ").append(toIndentedString(commitGranularity)).append("\n");
    sb.append("    cluster: ").append(toIndentedString(cluster)).append("\n");
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
